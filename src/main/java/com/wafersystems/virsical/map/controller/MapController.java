package com.wafersystems.virsical.map.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.dto.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.entity.SysSpace;
import com.wafersystems.virsical.common.feign.RemoteSpaceService;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.model.dto.SpaceMapDTO;
import com.wafersystems.virsical.map.service.IMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 地图 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Api(tags = "地图")
@AllArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController extends BaseController {

  private final IMapService mapService;

  private final RemoteSpaceService remoteSpaceService;

  @ApiOperation(value = "添加地图", notes = "添加地图")
  @ApiImplicitParam(name = "map", value = "地图对象", required = true, dataType = "Map")
  @PostMapping("/add")
  public R add(@RequestBody Map map) {
    List<Map> list = mapService.list(Wrappers.<Map>query().lambda().eq(Map::getFloorId, map.getFloorId()));
    if (list != null && !list.isEmpty()) {
      return update(map);
    }
    return mapService.save(map) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改地图", notes = "根据地图id修改地图")
  @ApiImplicitParam(name = "map", value = "地图对象", required = true, dataType = "Map")
  @PostMapping("/update")
  public R update(@RequestBody Map map) {
    return mapService.updateById(map) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除地图", notes = "根据地图id删除地图")
  @ApiImplicitParam(name = "id", value = "地图id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    return mapService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取地图详情", notes = "根据地图id获取地图")
  @ApiImplicitParam(name = "id", value = "地图id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Map> get(@PathVariable Integer id) {
    return R.ok(mapService.getById(id));
  }

  @ApiOperation(value = "获取地图列表", notes = "根据地图对象条件获取地图列表")
  @GetMapping("/list")
  public R<List<Map>> list(Map map) {
    return R.ok(mapService.list(Wrappers.query(map)));
  }

  @ApiOperation(value = "获取分页地图列表", notes = "根据分页条件、地图对象条件获取分页地图列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "spaceId", value = "空间节点id", dataType = "Integer")
  })
  @GetMapping("/page")
  public R page(Page page, Integer spaceId) {
    // 从用户服务查询空间叶子节点分页
    R<Page<SysSpace>> r = remoteSpaceService.getLeafNodePage(page.getSize(), page.getCurrent(), spaceId);
    if (CommonConstants.SUCCESS != r.getCode()) {
      return R.fail(MapMsgConstants.QUERY_MAP_FAILED);
    }
    Page<SysSpace> spacePage = r.getData();
    if (spacePage.getTotal() == 0) {
      return R.ok(new Page<>());
    }
    Page<SpaceMapDTO> spaceMapPage = new Page<>();
    BeanUtil.copyProperties(spacePage, spaceMapPage);
    List<SpaceMapDTO> spaceMapList = new ArrayList<>();
    List<Integer> spaceIds = new ArrayList<>();
    for (SysSpace sysSpace : spacePage.getRecords()) {
      spaceIds.add(sysSpace.getSpaceId());
      SpaceMapDTO spaceMapDTO = new SpaceMapDTO();
      BeanUtil.copyProperties(sysSpace, spaceMapDTO);
      spaceMapList.add(spaceMapDTO);
    }
    // 根据空间节点查询地图
    List<Map> mapList = mapService.list(Wrappers.<Map>query().lambda().in(Map::getFloorId,
      spaceIds.toArray()));
    for (SpaceMapDTO dto : spaceMapList) {
      for (Map m : mapList) {
        if (dto.getSpaceId().equals(m.getFloorId())) {
          dto.setMap(m);
        }
      }
    }
    spaceMapPage.setRecords(spaceMapList);
    return R.ok(spaceMapPage);
  }

  @ApiOperation(value = "根据空间节点id查询地图", notes = "根据空间节点id查询地图")
  @GetMapping("/listBySpaceIds")
  public R<List<Map>> listBySpaceIds(Integer[] spaceIds) {
    return R.ok(mapService.list(Wrappers.<Map>query().lambda().in(Map::getFloorId, spaceIds)));
  }
}
