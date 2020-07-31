package com.wafersystems.virsical.map.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.common.core.constant.PushMqConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.dto.MapElementObjectStateVO;
import com.wafersystems.virsical.common.core.dto.MessageDTO;
import com.wafersystems.virsical.common.core.exception.BusinessException;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.config.PushProperties;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.mapper.MapElementMapper;
import com.wafersystems.virsical.map.service.IMapElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

  @Autowired
  private PushProperties pushProperties;

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
   * 已绑定的地图元素不能删除
   * 更新已绑定的地图元素，绑定信息不能清空
   *
   * @param mapId          地图Id
   * @param mapElementList 地图元素集合
   * @return boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean batchSaveMapElement(Integer mapId, List<MapElement> mapElementList) {
    List<MapElement> oldMapElementList =
      baseMapper.selectList(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId,
        mapId));
    for (MapElement oldMe : oldMapElementList) {
      if (StrUtil.isNotBlank(oldMe.getObjectId())) {
        for (MapElement newMe : mapElementList) {
          if (oldMe.getMapElementId().equals(newMe.getMapElementId())) {
            newMe.setObjectId(oldMe.getObjectId());
            newMe.setObjectName(oldMe.getObjectName());
            newMe.setObjectColor(oldMe.getObjectColor());
            newMe.setObjectSvgStateCode(oldMe.getObjectSvgStateCode());
          }
        }
      }
    }

    super.remove(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId, mapId));
    // 批量保存新地图元素
    return super.saveBatch(mapElementList);
  }

  /**
   * 批量更新地图元素（资源绑定、路径保存）
   *
   * @param mapElementList 地图元素集合
   * @return boolean
   */
  @Override
  public boolean batchUpdateMapElement(List<MapElement> mapElementList) {
    boolean b = super.updateBatchById(mapElementList);
    if (b) {
      MapElement me = mapElementMapper.selectById(mapElementList.get(0).getMapElementId());
      if (me != null) {
        mapElementList.forEach(mapElement -> mapElement.setMapId(me.getMapId()));
        // 消息推送
        if (StrUtil.isNotBlank(mapElementList.get(0).getLineStart())) {
          push(MsgTypeEnum.BATCH.name(), MapConstants.ACTION_GUIDE_LINE,
            me.getMapId().toString(), (ArrayList) mapElementList);
        } else {
          push(MsgTypeEnum.BATCH.name(), MapConstants.ACTION_STATE_UPDATE,
            me.getMapId().toString(), (ArrayList) mapElementList);
        }
      }
    }
    return b;
  }

  /**
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode 地图元素类型
   * @param voList      地图元素资源状态集合
   * @return boolean
   */
  @Override
  public boolean batchUpdateMapElementObjectState(String svgTypeCode,
                                                  List<MapElementObjectStateVO> voList) {
    LambdaQueryWrapper<MapElement> wrapper = Wrappers.lambdaQuery();
    if (StrUtil.isNotBlank(svgTypeCode)) {
      wrapper.eq(MapElement::getSvgTypeCode, svgTypeCode);
    }
    List<String> objectIdList = new ArrayList<>();
    voList.forEach(vo -> objectIdList.add(vo.getObjectId()));
    wrapper.in(MapElement::getObjectId, objectIdList);
    // 查询对应地图id与元素id集合
    List<MapElement> mapElementList = mapElementMapper.selectList(wrapper);
    if (mapElementList.isEmpty()) {
      return false;
    }
    // 组装待更新对象集合
    mapElementList.forEach(me -> voList.forEach(vo -> {
      if (vo.getObjectId().equals(me.getObjectId())) {
        me.setObjectColor(vo.getObjectColor());
        me.setObjectSvgStateCode(vo.getObjectSvgStateCode());
        // 当是文字素材时，需要修改custom_element中文字内容，object_name置为空
        String text = "text";
        if (me.getMapWebId().startsWith(text)) {
          me.setCustomElement(me.getCustomElement()
            .replaceFirst(">.*</text>", ">" + vo.getObjectName() + "</text>"));
          me.setObjectName("");
        } else {
          me.setObjectName(vo.getObjectName());
        }
      }
    }));
    // 批量更新地图元素
    boolean b = this.updateBatchById(mapElementList);
    if (b) {
      // 消息推送
      push(MsgTypeEnum.BATCH.name(), MapConstants.ACTION_STATE_UPDATE,
        mapElementList.get(0).getMapId().toString(), (ArrayList) mapElementList);
    }
    return true;
  }

  /**
   * 消息推送
   *
   * @param msgType    消息类型
   * @param msgAction  消息动作
   * @param businessId 业务id
   * @param data       内容
   */
  @Override
  public void push(String msgType, String msgAction, String businessId, Serializable data) {
    MessageDTO messageDTO = new MessageDTO(null, null,
      businessId, pushProperties.getDestination(), msgType, msgAction, "zh_CN", data);
    if (pushProperties.isEnable()) {
      try {
        String body = JSON.toJSONString(messageDTO);
        amqpTemplate.convertAndSend(PushMqConstants.EXCHANGE_FANOUT_PUSH_MESSAGE, "", body);
        log.info("推送完成：[{}] | [{}]", PushMqConstants.EXCHANGE_FANOUT_PUSH_MESSAGE, body);
      } catch (Exception e) {
        log.error("推送失败", e);
        throw new BusinessException("调用推送服务推送失败");
      }
    }
  }
}
