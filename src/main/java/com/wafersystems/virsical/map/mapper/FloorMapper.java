package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Floor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 楼层 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface FloorMapper extends BaseMapper<Floor> {

  /**
   * 获取楼层分页
   *
   * @param page  分页对象
   * @param floor 楼层对象
   * @return 楼层分页
   */
  List<Floor> selectFloorPage(@Param("page") Page page, @Param("floor") Floor floor);
}
