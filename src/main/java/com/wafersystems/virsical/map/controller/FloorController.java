package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.entity.Floor;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.service.IFloorService;
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
 * 楼层 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Api(tags = "楼层")
@AllArgsConstructor
@RestController
@RequestMapping("/floor")
public class FloorController extends BaseController {

  private final IFloorService floorService;

  private final IMapService mapService;

  /**
   * 添加楼层
   *
   * @param floor 楼层对象
   * @return R
   */
  @ApiOperation(value = "添加楼层", notes = "添加楼层")
  @ApiImplicitParam(name = "floor", value = "楼层对象", required = true, dataType = "Floor")
  @PostMapping("/add")
  public R add(@RequestBody Floor floor) {
    if (floorService.getOne(Wrappers.<Floor>lambdaQuery()
      .eq(Floor::getBuildingId, floor.getBuildingId())
      .eq(Floor::getFloorNum, floor.getFloorNum())) != null) {
      return R.fail(MapMsgConstants.FLOOR_NUM_NO_REPEAT);
    }
    return floorService.save(floor) ? R.ok() : R.fail();
  }

  /**
   * 修改楼层
   *
   * @param floor 楼层对象
   * @return R
   */
  @ApiOperation(value = "修改楼层", notes = "根据楼层id修改楼层")
  @ApiImplicitParam(name = "floor", value = "楼层对象", required = true, dataType = "Floor")
  @PostMapping("/update")
  public R update(@RequestBody Floor floor) {
    if (floorService.getOne(Wrappers.<Floor>lambdaQuery()
      .ne(Floor::getFloorId, floor.getFloorId())
      .eq(Floor::getBuildingId, floor.getBuildingId())
      .eq(Floor::getFloorNum, floor.getFloorNum())) != null) {
      return R.fail(MapMsgConstants.FLOOR_NUM_NO_REPEAT);
    }
    return floorService.updateById(floor) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除楼层", notes = "根据楼层id删除楼层")
  @ApiImplicitParam(name = "id", value = "楼层id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    int count = mapService.count(Wrappers.<Map>query().lambda().eq(Map::getFloorId, id));
    if (count > 0) {
      return R.fail(MapMsgConstants.THIS_FLOOR_HAS_MAP);
    }
    return floorService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个楼层", notes = "根据楼层id获取楼层")
  @ApiImplicitParam(name = "id", value = "楼层id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Floor> get(@PathVariable Integer id) {
    return R.ok(floorService.getById(id));
  }

  @ApiOperation(value = "获取楼层列表", notes = "根据楼层对象条件获取楼层列表")
  @GetMapping("/list")
  public R<List<Floor>> list(Floor floor) {
    return R.ok(floorService.list(Wrappers.query(floor)));
  }

  @ApiOperation(value = "获取分页楼层列表", notes = "根据分页条件、楼层对象条件获取分页楼层列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "floor", value = "楼层对象", dataType = "Floor")
  })
  @GetMapping("/page")
  public R<IPage<Floor>> page(Page page, Floor floor) {
    return R.ok(floorService.selectFloorPage(page, floor));
  }

}
