package com.wafersystems.virsical.map.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.common.core.constant.MapMqConstants;
import com.wafersystems.virsical.common.core.constant.SecurityConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgActionEnum;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.constant.enums.ProductCodeEnum;
import com.wafersystems.virsical.common.core.exception.BusinessException;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.feign.RemotePushService;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.mapper.MapElementMapper;
import com.wafersystems.virsical.map.model.dto.MessageDTO;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import com.wafersystems.virsical.map.service.IMapElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 地图元素 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Slf4j
@Service
public class MapElementServiceImpl extends ServiceImpl<MapElementMapper, MapElement> implements IMapElementService {

  @Autowired
  private MapElementMapper mapElementMapper;

  @Value("${push.service.enable}")
  private Boolean pushServiceEnable;

  @Autowired
  private RemotePushService remotePushService;

  @Autowired
  private AmqpTemplate amqpTemplate;

  /**
   * 地图元素集合
   *
   * @param mapId 地图id
   * @return 地图元素集合
   */
  @Override
  public List<MapElement> selectListByMapId(Integer mapId) {
    return mapElementMapper.selectListByMapId(mapId);
  }

  /**
   * 批量保存地图元素（先删再存）
   *
   * @param mapId 地图Id
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean batchSaveMapElement(Integer mapId, List<MapElement> mapElementList) {
    List<MapElement> mapElements = baseMapper.selectList(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId,
      mapId));
    //查询老的地图元素id列表
    Set<String> oldIds = mapElements.stream().map(MapElement::getMapElementId).collect(Collectors.toSet());
    ArrayList<String> delIds = new ArrayList<>(oldIds.size());
    //获取新的地图元素id列表
    Set<String> newIds = mapElementList.stream().map(MapElement::getMapElementId).collect(Collectors.toSet());
    //查询出删除掉的地图元素id
    oldIds.forEach(i -> {
      if (!newIds.contains(i)) {
        delIds.add(i);
      }
    });
    super.remove(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId, mapId));
    // 批量保存新地图元素
    boolean b = super.saveBatch(mapElementList);
    if (b) {
      //发送删除通知
      MessageDTO messageDTO = new MessageDTO(MsgTypeEnum.ONE.name(), MsgActionEnum.DELETE.name(), delIds);
      amqpTemplate.convertAndSend(MapMqConstants.EXCHANGE_FANOUT_MAP_SVG, "", JSON.toJSONString(messageDTO));
      //推送地图更新消息
      push(MsgTypeEnum.ALL.name(), MsgActionEnum.UPDATE.name(), mapId + "");
    }
    return b;
  }

  /**
   * 批量更新地图元素（资源绑定、路径保存）
   *
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  @Override
  public Boolean batchUpdateMapElement(List<MapElement> mapElementList) {
    boolean b = super.updateBatchById(mapElementList);
    if (b) {
      MapElement me = mapElementMapper.selectById(mapElementList.get(0).getMapElementId());
      if (me != null) {
        mapElementList.forEach(mapElement -> mapElement.setMapId(me.getMapId()));
        // 消息推送
        push(MsgTypeEnum.BATCH.name(), MsgActionEnum.UPDATE.name(), JSON.toJSONString(mapElementList));
      }
    }
    return b;
  }

  /**
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode 地图元素类型
   * @param voList      地图元素资源状态集合
   * @return Boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean batchUpdateMapElementObjectState(String svgTypeCode,
                                                  List<MapElementObjectStateVO> voList) {
    LambdaQueryWrapper<MapElement> wrapper = Wrappers.lambdaQuery();
    wrapper.select(MapElement::getMapId, MapElement::getMapElementId, MapElement::getObjectId);
    if (StrUtil.isNotBlank(svgTypeCode)) {
      wrapper.eq(MapElement::getSvgTypeCode, svgTypeCode);
    }
    List<String> objectIdList = new ArrayList<>();
    voList.forEach(vo -> objectIdList.add(vo.getObjectId()));
    wrapper.in(MapElement::getMapElementId, objectIdList);
    // 查询对应地图id与元素id集合
    List<MapElement> mapElementList = mapElementMapper.selectList(wrapper);
    // 组装待更新对象集合
    mapElementList.forEach(me -> voList.forEach(vo -> {
      if (vo.getObjectId().equals(me.getMapElementId())) {
        me.setObjectName(vo.getObjectName());
        me.setObjectColor(vo.getObjectColor());
        me.setObjectSvgStateCode(vo.getObjectSvgStateCode());
      }
    }));
    if (mapElementList.isEmpty()) {
      return Boolean.FALSE;
    }
    // 批量更新地图元素
    boolean b = this.updateBatchById(mapElementList);
    if (b) {
      // 消息推送
      push(MsgTypeEnum.BATCH.name(), MsgActionEnum.UPDATE.name(), JSON.toJSONString(mapElementList));
    }
    return Boolean.TRUE;
  }

  /**
   * 消息推送
   *
   * @param msgType   消息类型
   * @param msgAction 消息动作
   * @param data      内容
   */
  private void push(String msgType, String msgAction, String data) {
    MessageDTO messageDTO = new MessageDTO(null, null, ProductCodeEnum.COMMON.name(), msgType, msgAction, data);
    if (pushServiceEnable) {
      try {
        R r = remotePushService.sendTopic(ProductCodeEnum.COMMON.name(), JSONUtil.toJsonStr(messageDTO),
          SecurityConstants.FROM_IN);
        log.info("调用推送服务推送结果：{}，[{}] | [{}] | [{}]", r, msgType, msgAction, data);
      } catch (Exception e) {
        log.error("调用推送服务推送失败：{}", e);
        throw new BusinessException("调用推送服务推送失败");
      }
    }
  }
}
