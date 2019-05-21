package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wafersystems.virsical.map.entity.SvgState;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 素材状态 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
public interface SvgStateMapper extends BaseMapper<SvgState> {
  /**
   * 根据素材状态标识查询素材状态
   *
   * @param svgId        素材id
   * @param svgStateCode 素材状态标识
   * @return 素材状态
   */
  SvgState getBySvgIdAndStateCode(@Param("svgId") Integer svgId, @Param("svgStateCode") String svgStateCode);

}
