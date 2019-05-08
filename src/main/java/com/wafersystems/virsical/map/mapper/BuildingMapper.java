package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Building;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 楼宇 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface BuildingMapper extends BaseMapper<Building> {

  /**
   * 获取楼宇分页
   *
   * @param page     分页对象
   * @param building 楼宇对象
   * @return 楼宇分页
   */
  List<Building> selectBuildingPage(@Param("page") Page page, @Param("building") Building building);
}
