package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Map;
import com.baomidou.mybatisplus.extension.service.IService;

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
   * 查询地图分页
   *
   * @param page 分页对象
   * @param map  地图条件对象
   * @return 地图分页列表
   */
  IPage<Map> selectMapPage(Page<Map> page, Map map);
}
