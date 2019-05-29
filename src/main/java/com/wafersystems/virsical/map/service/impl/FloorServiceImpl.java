package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.Floor;
import com.wafersystems.virsical.map.mapper.FloorMapper;
import com.wafersystems.virsical.map.service.IFloorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 楼层 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
@AllArgsConstructor
public class FloorServiceImpl extends ServiceImpl<FloorMapper, Floor> implements IFloorService {
  private final FloorMapper floorMapper;

  /**
   * 获取楼层分页
   *
   * @param page  分页对象
   * @param floor 楼层对象
   * @return 楼层分页
   */
  @Override
  public IPage<Floor> selectFloorPage(Page<Floor> page, Floor floor) {
    return page.setRecords(floorMapper.selectFloorPage(page, floor));
  }

  /**
   * 获取楼层列表
   *
   * @return 楼层分页
   */
  @Override
  public List<Floor> selectFloorList() {
    return floorMapper.selectFloorList();
  }
}
