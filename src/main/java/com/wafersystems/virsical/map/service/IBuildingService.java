package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.Building;

/**
 * <p>
 * 楼宇 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface IBuildingService extends IService<Building> {

  /**
   * 获取楼宇分页
   *
   * @param page     分页对象
   * @param building 楼宇对象
   * @return 楼宇分页
   */
  IPage<Building> selectBuildingPage(Page<Building> page, Building building);

}
