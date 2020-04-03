package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.mapper.MapMapper;
import com.wafersystems.virsical.map.service.IMapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 地图 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
@AllArgsConstructor
public class MapServiceImpl extends ServiceImpl<MapMapper, Map> implements IMapService {
  private final MapMapper mapMapper;

  /**
   * 查询地图分页
   *
   * @param page 分页对象
   * @param map  地图条件对象
   * @return 地图分页列表
   */
  @Override
  public IPage<Map> selectMapPage(Page<Map> page, Map map) {
    return page.setRecords(mapMapper.selectMapPage(page, map));
  }

  /**
   * 根据空间节点id查询地图
   *
   * @param spaceIds 空间id集合
   * @return mapList
   */
  @Override
  public List<Map> selectMapListBySpaceId(Integer[] spaceIds) {
    return mapMapper.selectMapListBySpaceId(Arrays.asList(spaceIds));
  }
}
