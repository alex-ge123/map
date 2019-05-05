package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.entity.Park;
import com.wafersystems.virsical.map.service.IParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import com.wafersystems.virsical.map.common.BaseController;

/**
 * <p>
 * 园区 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-05
 */
@Api(value = "park", description = "园区 接口")
@AllArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkController extends BaseController {

  private final IParkService parkService;

  @ApiOperation(value = "添加园区", notes = "添加园区")
  @ApiImplicitParam(name = "park", value = "园区对象", required = true, dataType = "Park")
  @PostMapping()
  public R add(@RequestBody Park park) {
    return park.insert() ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改园区", notes = "修改园区")
  @ApiImplicitParam(name = "park", value = "园区对象", required = true, dataType = "Park")
  @PutMapping()
  public R update(@RequestBody Park park) {
    return park.updateById() ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除园区", notes = "删除园区")
  @ApiImplicitParam(name = "id", value = "园区id", required = true, dataType = "Integer")
  @DeleteMapping("/{id}")
  public R delete(@PathVariable Integer id) {
    return new Park().deleteById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个园区", notes = "根据id获取园区")
  @ApiImplicitParam(name = "id", value = "园区id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R get(@PathVariable Integer id) {
    return new Park().deleteById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取所有园区", notes = "获取所有园区")
  @GetMapping("/list")
  public R list() {
    return R.ok(parkService.list(Wrappers.emptyWrapper()));
  }

  @ApiOperation(value = "获取分页园区", notes = "获取分页园区")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "park", value = "园区对象", dataType = "Park")
  })
  @GetMapping("/page")
  public R page(Page page, Park park) {
    return R.ok(new Park().selectPage(page, Wrappers.query(park)));
  }

}
