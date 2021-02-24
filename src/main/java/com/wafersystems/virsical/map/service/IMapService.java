package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.model.vo.MapSearchResultVO;

import java.util.List;

/**
 * <p>
 * 地图 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface IMapService extends IService<Map> {

  /**
   * 地图上线
   * @param spaceIds 区域id集合
   */
  void online(List<Integer> spaceIds);

  /**
   * 根据空间节点id查询地图
   *
   * @param spaceIds 空间id集合
   * @return mapList
   */
  List<Map> selectMapListBySpaceId(Integer[] spaceIds);

  /**
   * 模糊搜索地图元素
   *
   * @param key         关键字
   * @param spaceId     区域id
   * @param svgTypeCode 素材类型标识
   * @return List
   */
  List<MapSearchResultVO> search(String key, Integer spaceId, String svgTypeCode);
}
