package com.wafersystems.virsical.map.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wafersystems.virsical.map.entity.Svg;

/**
 * <p>
 * 素材 服务类
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
public interface ISvgService extends IService<Svg> {
  /**
   * 查询素材分页列表
   *
   * @param page 分页对象
   * @param svg  素材条件对象
   * @return 素材分类列表
   */
  IPage<Svg> selectSvgPage(Page page, Svg svg);
}
