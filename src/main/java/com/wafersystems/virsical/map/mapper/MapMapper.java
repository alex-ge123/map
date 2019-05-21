package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Map;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 地图 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface MapMapper extends BaseMapper<Map> {
  /**
   * 查询地图分页
   *
   * @param page 分页对象
   * @param map  地图条件对象
   * @return 地图分页列表
   */
  List<Map> selectMapPage(@Param("page") Page page, @Param("map") Map map);
}
