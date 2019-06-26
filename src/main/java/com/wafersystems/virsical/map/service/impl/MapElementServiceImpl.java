package com.wafersystems.virsical.map.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.common.core.constants.enums.MsgActionEnum;
import com.wafersystems.virsical.common.core.constants.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.constants.enums.ProductEnum;
import com.wafersystems.virsical.common.core.exception.BusinessException;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.mapper.MapElementMapper;
import com.wafersystems.virsical.map.model.dto.MessageDTO;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import com.wafersystems.virsical.map.service.IMapElementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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

  @Value("${push.service.enable}")
  private Boolean pushServiceEnable;

  @Value("${push.service.url}")
  private String pushServiceUrl;

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
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean batchSaveMapElement(List<MapElement> mapElementList) {
    // 删除此地图所有地图元素
    Integer mapId = mapElementList.get(0).getMapId();
    super.remove(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId, mapId));
    // 批量保存新地图元素
    boolean b = super.saveBatch(mapElementList);
    if (b) {
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
    wrapper.in(MapElement::getObjectId, objectIdList);
    // 查询对应地图id与元素id集合
    List<MapElement> mapElementList = mapElementMapper.selectList(wrapper);
    // 组装待更新对象集合
    mapElementList.forEach(me -> voList.forEach(vo -> {
      if (vo.getObjectId().equals(me.getObjectId())) {
        me.setObjectName(vo.getObjectName());
        me.setObjectColor(vo.getObjectColor());
        me.setObjectSvgStateCode(vo.getObjectSvgStateCode());
      }
    }));

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
    if (pushServiceEnable) {
      HashMap<String, Object> paramMap = new HashMap<>(1);
      paramMap.put("msg", new MessageDTO(ProductEnum.MAP.name(), msgType, msgAction, data));
      try {
        String result = HttpUtil.post(pushServiceUrl + ProductEnum.MAP.name(), paramMap, 20000);
        log.info("调用推送服务推送结果：{}，[{}] | [{}] | [{}]", result, msgType, msgAction, data);
      } catch (Exception e) {
        log.error("调用推送服务推送失败：{}", e);
        throw new BusinessException("调用推送服务推送失败");
      }
    }
  }
}
