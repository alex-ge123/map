package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MsgConstants;
import com.wafersystems.virsical.map.entity.Building;
import com.wafersystems.virsical.map.entity.Park;
import com.wafersystems.virsical.map.service.IFloorService;
import com.wafersystems.virsical.map.service.IParkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 园区 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Api(tags = "园区")
@AllArgsConstructor
@RestController
@RequestMapping("/park")
public class ParkController extends BaseController {

  private final IParkService parkService;

  private final IFloorService floorService;

  @ApiOperation(value = "添加园区", notes = "添加园区")
  @ApiImplicitParam(name = "park", value = "园区对象", required = true, dataType = "Park")
  @PostMapping("/add")
  public R add(@RequestBody Park park) {
    Park one = parkService.getOne(Wrappers.<Park>query().lambda().eq(Park::getParkName, park.getParkName()));
    if (one != null) {
      return R.fail(MsgConstants.PARK_NAME_NO_REPEAT);
    }
    return parkService.save(park) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改园区", notes = "根据园区id修改园区")
  @ApiImplicitParam(name = "park", value = "园区对象", required = true, dataType = "Park")
  @PostMapping("/update")
  public R update(@RequestBody Park park) {
    Park one = parkService.getOne(Wrappers.<Park>query().lambda().eq(Park::getParkName, park.getParkName())
        .ne(Park::getParkId, park.getParkId()));
    if (one != null) {
      return R.fail(MsgConstants.PARK_NAME_NO_REPEAT);
    }
    return parkService.updateById(park) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除园区", notes = "根据园区id删除园区")
  @ApiImplicitParam(name = "id", value = "园区id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    return parkService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个园区", notes = "根据园区id获取园区")
  @ApiImplicitParam(name = "id", value = "园区id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Park> get(@PathVariable Integer id) {
    return R.ok(parkService.getById(id));
  }

  @ApiOperation(value = "获取园区列表", notes = "根据园区对象条件获取园区列表")
  @GetMapping("/list")
  public R<List<Park>> list(Park park) {
    return R.ok(parkService.list(Wrappers.query(park)));
  }

  /**
   * 获取分页园区列表
   *
   * @param page 分页对象
   * @param park 园区对象
   * @return R
   */
  @ApiOperation(value = "获取分页园区列表", notes = "根据分页条件、园区对象条件获取分页园区列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "park", value = "园区对象", dataType = "Park")
  })
  @GetMapping("/page")
  public R<IPage<Park>> page(Page page, Park park) {
    LambdaQueryWrapper<Park> lambdaQueryWrapper = Wrappers.lambdaQuery();
    if (park != null && park.getParkName() != null) {
      String parkName = park.getParkName();
      park.setParkName(null);
      lambdaQueryWrapper.like(Park::getParkName, parkName);
    }
    lambdaQueryWrapper.setEntity(park);
    lambdaQueryWrapper.orderByDesc(Park::getParkId);
    return R.ok(parkService.page(page, lambdaQueryWrapper));
  }

  /**
   * 获取园区，楼宇，楼层集合
   *
   * @return R
   */
  @ApiOperation(value = "获取园区，楼宇，楼层集合", notes = "获取园区，楼宇，楼层集合")
  @GetMapping("/building/floor")
  public R parkBuildingFloor() {
    Map<String, Object> map = new HashMap<>(3);
    map.put("park", new Park().selectList(Wrappers.<Park>lambdaQuery().select(Park::getParkId, Park::getParkName)));
    map.put("building", new Building().selectList(Wrappers.<Building>lambdaQuery()
      .select(Building::getParkId, Building::getBuildingId, Building::getBuildingName)));
    map.put("floor", floorService.selectFloorList());
    return R.ok(map);
  }
}
