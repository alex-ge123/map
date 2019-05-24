package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.service.IMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

  @ApiOperation(value = "添加地图", notes = "添加地图")
  @ApiImplicitParam(name = "map", value = "地图对象", required = true, dataType = "Map")
  @PostMapping("/add")
  public R add(@RequestBody Map map) {
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
    @ApiImplicitParam(name = "map", value = "地图对象", dataType = "Map")
  })
  @GetMapping("/page")
  public R<IPage<Map>> page(Page page, Map map) {
    return R.ok(mapService.selectMapPage(page, map));
  }

}
