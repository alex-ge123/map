package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.Floor;

/**
 * <p>
 * 楼层 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface IFloorService extends IService<Floor> {
  /**
   * 获取楼层分页
   *
   * @param page  分页对象
   * @param floor 楼层对象
   * @return 楼层分页
   */
  IPage<Floor> selectFloorPage(Page<Floor> page, Floor floor);
}
