package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.entity.SvgType;
import com.wafersystems.virsical.map.service.ISvgTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 素材类型 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Api(tags = "素材类型")
@AllArgsConstructor
@RestController
@RequestMapping("/svg-type")
public class SvgTypeController extends BaseController {

  private final ISvgTypeService svgTypeService;

  /**
   * 添加素材类型
   *
   * @param svgType 素材类型对象
   * @return R
   */
  @ApiOperation(value = "添加素材类型", notes = "添加素材类型")
  @ApiImplicitParam(name = "svgType", value = "素材类型对象", required = true, dataType = "SvgType")
  @PostMapping("/add")
  public R add(@RequestBody SvgType svgType) {
    if (svgTypeService.getById(svgType.getSvgTypeCode()) != null) {
      return R.fail(MapMsgConstants.MATERIAL_TYPE_REPETITION);
    }
    return svgTypeService.save(svgType) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改素材类型", notes = "根据素材类型id修改素材类型")
  @ApiImplicitParam(name = "svgType", value = "素材类型对象", required = true, dataType = "SvgType")
  @PostMapping("/update")
  public R update(@RequestBody SvgType svgType) {
    return svgTypeService.updateById(svgType) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除素材类型", notes = "根据素材类型标识删除素材类型")
  @ApiImplicitParam(name = "code", value = "素材类型标识", required = true, dataType = "String")
  @PostMapping("/delete/{code}")
  public R delete(@PathVariable String code) {
    return svgTypeService.removeById(code) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个素材类型", notes = "根据素材类型id获取素材类型")
  @ApiImplicitParam(name = "code", value = "素材类型标识", required = true, dataType = "String")
  @GetMapping("/{code}")
  public R<SvgType> get(@PathVariable String code) {
    return R.ok(svgTypeService.getById(code));
  }

  @ApiOperation(value = "获取启用的素材类型列表", notes = "获取启用的素材类型列表")
  @GetMapping("/list")
  public R<List<SvgType>> list() {
    return R.ok(svgTypeService.list(
      Wrappers.<SvgType>lambdaQuery().eq(SvgType::getSvgTypeState, MapConstants.OPEN_STATE)));
  }

  @ApiOperation(value = "获取分页素材类型列表", notes = "根据分页条件、素材类型对象条件获取分页素材类型列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "svgType", value = "素材类型对象", dataType = "SvgType")
  })
  @GetMapping("/page")
  public R<IPage<SvgType>> page(Page page, SvgType svgType) {
    return R.ok(svgTypeService.page(page, Wrappers.query(svgType).orderByDesc("create_time")));
  }

}
