package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.SvgState;
import com.wafersystems.virsical.map.mapper.SvgStateMapper;
import com.wafersystems.virsical.map.service.ISvgStateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 素材状态 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Service
@AllArgsConstructor
public class SvgStateServiceImpl extends ServiceImpl<SvgStateMapper, SvgState> implements ISvgStateService {
  private final SvgStateMapper svgStateMapper;

  /**
   * 根据素材状态标识查询素材状态
   *
   * @param svgId        素材id
   * @param svgStateCode 素材状态标识
   * @return 素材状态
   */
  @Override
  public SvgState getBySvgIdAndStateCode(Integer svgId, String svgStateCode) {
    return svgStateMapper.getBySvgIdAndStateCode(svgId, svgStateCode);
  }
}
