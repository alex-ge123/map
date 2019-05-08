package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.Building;
import com.wafersystems.virsical.map.mapper.BuildingMapper;
import com.wafersystems.virsical.map.service.IBuildingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 楼宇 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
@AllArgsConstructor
public class BuildingServiceImpl extends ServiceImpl<BuildingMapper, Building> implements IBuildingService {
  public final BuildingMapper buildingMapper;

  /**
   * 获取楼宇分页
   *
   * @param page     分页对象
   * @param building 楼宇对象
   * @return 楼宇分页
   */
  @Override
  public IPage<Building> selectBuildingPage(Page<Building> page, Building building) {
    return page.setRecords(buildingMapper.selectBuildingPage(page, building));
  }
}
