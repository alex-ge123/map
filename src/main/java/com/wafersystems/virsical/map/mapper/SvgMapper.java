package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Svg;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 素材 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
public interface SvgMapper extends BaseMapper<Svg> {
  /**
   * 查询素材分页列表
   *
   * @param page 分页对象
   * @param svg  素材条件对象
   * @return 素材分类列表
   */
  List<Svg> selectSvgPage(@Param("page") Page page, @Param("svg") Svg svg);
}
