package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.Svg;
import com.wafersystems.virsical.map.mapper.SvgMapper;
import com.wafersystems.virsical.map.service.ISvgService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 素材 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Service
@AllArgsConstructor
public class SvgServiceImpl extends ServiceImpl<SvgMapper, Svg> implements ISvgService {
  private final SvgMapper svgMapper;

  /**
   * 查询素材分页列表
   *
   * @param page 分页对象
   * @param svg  素材条件对象
   * @return 素材分类列表
   */
  @Override
  public IPage<Svg> selectSvgPage(Page page, Svg svg) {
    return page.setRecords(svgMapper.selectSvgPage(page, svg));
  }
}
