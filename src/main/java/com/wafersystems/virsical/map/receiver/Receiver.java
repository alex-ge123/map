package com.wafersystems.virsical.map.receiver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.constant.MapMqConstants;
import com.wafersystems.virsical.common.core.constant.UpmsMqConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgActionEnum;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.dto.MapElementUpdateDTO;
import com.wafersystems.virsical.common.core.dto.MessageDTO;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import com.wafersystems.virsical.common.entity.SysSpace;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.service.IMapElementService;
import com.wafersystems.virsical.map.service.IMapService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 接收mq消息
 *
 * @author shennan
 * @date 2020/4/15
 */
@Slf4j
@Component
public class Receiver {
  @Autowired
  private IMapElementService mapElementService;

  @Autowired
  private IMapService mapService;

  /**
   * 接收队列
   */
  private static final String QUEUE_MAP_UPDATE_ELEMENT = "queue.map.update.element";

  /**
   * 接收区域上下线队列
   */
  private static final String QUEUE_UPMS_SPACE_ONLINE_OFFICE = "queue.upms.space.online.offline";

  /**
   * 监听地图元素更新消息
   *
   * @param message 消息
   */
  @RabbitHandler
  @RabbitListener(bindings = @QueueBinding(
    value = @Queue(QUEUE_MAP_UPDATE_ELEMENT),
    exchange = @Exchange(MapMqConstants.EXCHANGE_DIRECT_MAP),
    key = MapMqConstants.STATE_UPDATE_ROUTING_KEY
  ))
  public void updateElement(@Payload String message) {
    try {
      MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);
      // 业务追踪id
      MDC.put(CommonConstants.LOG_BIZ_ID, messageDTO.getBizId());
      log.info("【{}监听到更新元素消息】{}", QUEUE_MAP_UPDATE_ELEMENT, message);
      if (MsgTypeEnum.ONE.name().equals(messageDTO.getMsgType())) {
        final MapElementUpdateDTO dto = JSON.parseObject(messageDTO.getData().toString(), MapElementUpdateDTO.class);
        if (MsgActionEnum.DELETE.name().equals(messageDTO.getMsgAction())) {
          //解绑
          mapElementService.batchUnbind(dto.getSvgTypeCode(), dto.getMapElementObjectStateVoList());
        } else {
          if (!dto.getMapElementObjectStateVoList().isEmpty()) {
            mapElementService.batchUpdateMapElementObjectState(dto.getSvgTypeCode(),
              dto.getMapElementObjectStateVoList());
          }
        }
      } else {
        log.info("地图元素更新消息类型未识别，无法处理消息");
      }
    } catch (Exception e) {
      log.error("地图元素更新消息监听处理异常", e);
    }
  }

  /**
   * 监听区域上下线消息
   *
   * @param message 消息
   */
  @RabbitHandler
  @RabbitListener(bindings = @QueueBinding(
    value = @Queue(value = QUEUE_UPMS_SPACE_ONLINE_OFFICE, durable = "true"),
    exchange = @Exchange(value = UpmsMqConstants.EXCHANGE_FANOUT_UPMS_SPACE, type = ExchangeTypes.FANOUT)
  ))
  public void spaceOnlineOffline(@Payload String message) {
    try {
      MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);
      // 业务追踪id
      MDC.put(CommonConstants.LOG_BIZ_ID, messageDTO.getBizId());
      log.info("【{}监听区域上下线消息】{}", QUEUE_UPMS_SPACE_ONLINE_OFFICE, message);
      if (MsgTypeEnum.BATCH.name().equals(messageDTO.getMsgType())) {
        Type type = new TypeReference<ArrayList<SysSpace>>() {
        }.getType();
        List<SysSpace> list = JSON.parseObject(messageDTO.getData().toString(), type);
        if (!list.isEmpty()) {
          TenantContextHolder.setTenantId(list.get(0).getTenantId());
        }
        // 获取区域id集合
        List<Integer> spaceIdList = list.stream().map(SysSpace::getSpaceId).collect(Collectors.toList());
        if (MsgActionEnum.OFFLINE.name().equals(messageDTO.getMsgAction())) {
          // 批量下线地图
          new Map().delete(Wrappers.<Map>lambdaUpdate().in(Map::getFloorId, spaceIdList.toArray()));
        } else if (MsgActionEnum.ONLINE.name().equals(messageDTO.getMsgAction())) {
          // 批量上线地图
          mapService.online(spaceIdList);
        } else {
          log.info("区域消息不处理[{}]类型", messageDTO.getMsgAction());
        }
      } else {
        log.info("监听区域上下线消息类型[{}]不处理", messageDTO.getMsgType());
      }
    } catch (Exception e) {
      log.error("监听区域上下线消息监听处理异常", e);
    }
  }
}