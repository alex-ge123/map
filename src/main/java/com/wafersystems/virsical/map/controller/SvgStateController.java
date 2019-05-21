package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MsgConstants;
import com.wafersystems.virsical.map.entity.SvgState;
import com.wafersystems.virsical.map.service.ISvgStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 素材状态 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Api(tags = "素材状态", description = "svgState")
@AllArgsConstructor
@RestController
@RequestMapping("/svg-state")
public class SvgStateController extends BaseController {

  private final ISvgStateService svgStateService;

  @ApiOperation(value = "添加素材状态", notes = "添加素材状态")
  @ApiImplicitParam(name = "svgState", value = "素材状态对象", required = true, dataType = "SvgState")
  @PostMapping("/add")
  public R add(@RequestBody SvgState svgState) {
    List<SvgState> list = svgStateService.list(Wrappers.<SvgState>lambdaQuery()
      .eq(SvgState::getSvgId, svgState.getSvgId())
      .eq(SvgState::getSvgStateCode, svgState.getSvgStateCode()));
    if (!list.isEmpty()) {
      return R.fail(MsgConstants.SVG_STATE_NO_REPEAT);
    }
    return svgStateService.save(svgState) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改素材状态", notes = "根据素材状态id修改素材状态")
  @ApiImplicitParam(name = "svgState", value = "素材状态对象", required = true, dataType = "SvgState")
  @PostMapping("/update")
  public R update(@RequestBody SvgState svgState) {
    List<SvgState> list = svgStateService.list(Wrappers.<SvgState>lambdaQuery()
      .eq(SvgState::getSvgId, svgState.getSvgId())
      .eq(SvgState::getSvgStateCode, svgState.getSvgStateCode())
      .ne(SvgState::getSvgStateId, svgState.getSvgStateId()));
    if (!list.isEmpty()) {
      return R.fail(MsgConstants.SVG_STATE_NO_REPEAT);
    }
    return svgStateService.updateById(svgState) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除素材状态", notes = "根据素材状态id删除素材状态")
  @ApiImplicitParam(name = "id", value = "素材状态id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    return svgStateService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取素材某个素材状态", notes = "根据素材id、素材状态标识获取素材状态")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "svgId", value = "素材id", required = true, dataType = "Integer"),
    @ApiImplicitParam(name = "code", value = "素材状态标识", required = true, dataType = "String")
  })
  @GetMapping("/{svgId}/{code}")
  public R<SvgState> get(@PathVariable Integer svgId, @PathVariable String code) {
    return R.ok(svgStateService.getOne(Wrappers.<SvgState>lambdaQuery()
      .eq(SvgState::getSvgId, svgId).eq(SvgState::getSvgStateCode, code)));
  }

  @ApiOperation(value = "获取素材状态列表", notes = "根据素材状态对象条件获取素材状态列表")
  @GetMapping("/list")
  public R<List<SvgState>> list(SvgState svgState) {
    return R.ok(svgStateService.list(Wrappers.query(svgState)));
  }

  @ApiOperation(value = "获取分页素材状态列表", notes = "根据分页条件、素材状态对象条件获取分页素材状态列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "svgState", value = "素材状态对象", dataType = "SvgState")
  })
  @GetMapping("/page")
  public R<IPage<SvgState>> page(Page page, SvgState svgState) {
    return R.ok(svgStateService.page(page, Wrappers.query(svgState)));
  }

}
