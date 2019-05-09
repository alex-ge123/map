package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.SvgState;

/**
 * <p>
 * 素材状态 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
public interface ISvgStateService extends IService<SvgState> {
  /**
   * 根据素材状态标识查询素材状态
   *
   * @param svgId        素材id
   * @param svgStateCode 素材状态标识
   * @return 素材状态
   */
  SvgState getBySvgIdAndStateCode(Integer svgId, String svgStateCode);
}
