package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.entity.Building;
import com.wafersystems.virsical.map.service.IBuildingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 楼宇 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Api(tags = "楼宇", description = "building")
@AllArgsConstructor
@RestController
@RequestMapping("/building")
public class BuildingController extends BaseController {

  private final IBuildingService buildingService;

  @ApiOperation(value = "添加楼宇", notes = "添加楼宇")
  @ApiImplicitParam(name = "building", value = "楼宇对象", required = true, dataType = "Building")
  @PostMapping("/add")
  public R add(@RequestBody Building building) {
    return buildingService.save(building) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改楼宇", notes = "根据楼宇id修改楼宇")
  @ApiImplicitParam(name = "building", value = "楼宇对象", required = true, dataType = "Building")
  @PostMapping("/update")
  public R update(@RequestBody Building building) {
    return buildingService.updateById(building) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除楼宇", notes = "根据楼宇id删除楼宇")
  @ApiImplicitParam(name = "id", value = "楼宇id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    return buildingService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个楼宇", notes = "根据楼宇id获取楼宇")
  @ApiImplicitParam(name = "id", value = "楼宇id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Building> get(@PathVariable Integer id) {
    return R.ok(buildingService.getById(id));
  }

  @ApiOperation(value = "获取楼宇列表", notes = "根据楼宇对象条件获取楼宇列表")
  @GetMapping("/list")
  public R<List<Building>> list(Building building) {
    return R.ok(buildingService.list(Wrappers.query(building)));
  }

  @ApiOperation(value = "获取分页楼宇列表", notes = "根据分页条件、楼宇对象条件获取分页楼宇列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "building", value = "楼宇对象", dataType = "Building")
  })
  @GetMapping("/page")
  public R<IPage<Building>> page(Page page, Building building) {
    return R.ok(buildingService.selectBuildingPage(page, building));
  }

}
