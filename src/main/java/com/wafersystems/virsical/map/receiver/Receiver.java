package com.wafersystems.virsical.map.receiver;

import com.alibaba.fastjson.JSON;
import com.wafersystems.virsical.common.core.constant.MapMqConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.dto.MapElementUpdateDTO;
import com.wafersystems.virsical.common.core.dto.MessageDTO;
import com.wafersystems.virsical.map.service.IMapElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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

  /**
   * 接收队列
   */
  public static final String QUEUE_MAP_UPDATE_ELEMENT = "queue.map.update.element";

  /**
   * 监听消息队列
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
    log.info("【{}监听到更新元素消息】{}", QUEUE_MAP_UPDATE_ELEMENT, message);
    try {
      MessageDTO messageDTO = JSON.parseObject(message, MessageDTO.class);
      if (MsgTypeEnum.ONE.name().equals(messageDTO.getMsgType())) {
        final MapElementUpdateDTO dto = JSON.parseObject(messageDTO.getData().toString(), MapElementUpdateDTO.class);
        if (!dto.getMapElementObjectStateVoList().isEmpty()) {
          mapElementService.batchUpdateMapElementObjectState(dto.getSvgTypeCode(), dto.getMapElementObjectStateVoList());
        }
      } else {
        log.info("消息类型未识别，无法处理消息");
      }
    } catch (Exception e) {
      log.info("消息监听处理异常", e);
    }
  }
}