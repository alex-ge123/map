package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.common.core.dto.MapElementObjectStateVO;
import com.wafersystems.virsical.map.entity.MapElement;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 地图元素 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface IMapElementService extends IService<MapElement> {
  /**
   * 地图元素集合
   *
   * @param mapId 地图id
   * @return 地图元素集合
   */
  List<MapElement> selectListByMapId(Integer mapId);

  /**
   * 批量保存地图元素
   *
   * @param mapId          地图id
   * @param mapElementList 地图元素集合
   * @return boolean
   */
  boolean batchSaveMapElement(Integer mapId, List<MapElement> mapElementList);

  /**
   * 批量更新地图元素
   *
   * @param mapElementList 地图元素集合
   * @return boolean
   */
  boolean batchUpdateMapElement(List<MapElement> mapElementList);

  /**
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode 地图元素类型
   * @param list        地图元素资源状态集合
   * @return boolean
   */
  boolean batchUpdateMapElementObjectState(String svgTypeCode,
                                           List<MapElementObjectStateVO> list);

  /**
   * 批量解绑
   *
   * @param svgTypeCode 地图元素类型
   * @param voList      地图元素资源状态集合
   * @return boolean
   */
  boolean batchUnbind(String svgTypeCode,
                      List<MapElementObjectStateVO> voList);

  /**
   * 消息推送
   *
   * @param msgType    消息类型
   * @param msgAction  消息动作
   * @param businessId 业务id
   * @param data       内容
   */
  void push(String msgType, String msgAction, String businessId, Serializable data);

  /**
   * 解绑
   *
   * @param collect id列表
   */
  void unBind(List<String> collect);
}
