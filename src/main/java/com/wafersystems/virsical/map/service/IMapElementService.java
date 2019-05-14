package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;

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
   * 批量保存地图元素
   *
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  Boolean batchSaveMapElement(List<MapElement> mapElementList);

  /**
   * 批量更新地图元素
   *
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  Boolean batchUpdateMapElement(List<MapElement> mapElementList);

  /**
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode                 地图元素类型
   * @param mapElementObjectStateVOList 地图元素资源状态集合
   * @return Boolean
   */
  Boolean batchUpdateMapElementObjectState(String svgTypeCode, List<MapElementObjectStateVO> mapElementObjectStateVOList);

}
