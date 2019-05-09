package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MsgConstants;
import com.wafersystems.virsical.map.common.SvgUtils;
import com.wafersystems.virsical.map.entity.Svg;
import com.wafersystems.virsical.map.entity.SvgType;
import com.wafersystems.virsical.map.service.ISvgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 素材 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Api(tags = "素材", description = "svg")
@AllArgsConstructor
@RestController
@RequestMapping("/svg")
public class SvgController extends BaseController {

  private final ISvgService svgService;

  @ApiOperation(value = "解析SVG文件", notes = "解析SVG文件")
  @PostMapping("/parse")
  public R parse(@RequestParam MultipartFile svgFile) {
    if (svgFile == null) {
      return R.fail(MsgConstants.SVG_FILE_NO_NULL);
    }
    java.util.Map<String, String> svgMap;
    try {
      svgMap = SvgUtils.analyzeSvgFile(svgFile.getInputStream());
    } catch (Exception e) {
      return R.fail(MsgConstants.SVG_FILE_PARSE_EXCEPTION);
    }
    return R.ok(svgMap);
  }

  @ApiOperation(value = "添加素材", notes = "添加素材")
  @ApiImplicitParam(name = "svg", value = "素材对象", required = true, dataType = "Svg")
  @PostMapping("/add")
  public R add(@RequestBody Svg svg) {
    return svgService.save(svg) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改素材", notes = "根据素材id修改素材")
  @ApiImplicitParam(name = "svg", value = "素材对象", required = true, dataType = "Svg")
  @PostMapping("/update")
  public R update(@RequestBody Svg svg) {
    return svgService.updateById(svg) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除素材", notes = "根据素材id删除素材")
  @ApiImplicitParam(name = "id", value = "素材id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  public R delete(@PathVariable Integer id) {
    return svgService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取单个素材", notes = "根据素材id获取素材")
  @ApiImplicitParam(name = "id", value = "素材id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Svg> get(@PathVariable Integer id) {
    return R.ok(svgService.getById(id));
  }

  @ApiOperation(value = "获取启用的素材列表", notes = "获取启用的素材列表")
  @GetMapping("/enable-list")
  public R<List<Svg>> enableList() {
    return R.ok(svgService.list(Wrappers.<Svg>lambdaQuery().eq(Svg::getState, MapConstants.OPEN_STATE)));
  }

  @ApiOperation(value = "获取素材列表", notes = "根据素材对象条件获取素材列表")
  @GetMapping("/list")
  public R<List<Svg>> list(Svg svg) {
    return R.ok(svgService.list(Wrappers.query(svg)));
  }

  @ApiOperation(value = "获取分页素材列表", notes = "根据分页条件、素材对象条件获取分页素材列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "svg", value = "素材对象", dataType = "Svg")
  })
  @GetMapping("/page")
  public R<IPage<Svg>> page(Page page, Svg svg) {
    return R.ok(svgService.selectSvgPage(page, svg));
  }

  @ApiOperation(value = "获取素材分组列表", notes = "获取素材分组列表")
  @GetMapping("/group")
  public R<List<SvgType>> group() {
    List<SvgType> svgTypeList = new SvgType().selectList(Wrappers.<SvgType>lambdaQuery()
      .select(SvgType::getSvgTypeCode, SvgType::getSvgTypeName)
      .eq(SvgType::getSvgTypeState, MapConstants.OPEN_STATE));
    svgTypeList.forEach(svgType -> svgType.setSvgList(svgService.list(Wrappers.<Svg>lambdaQuery()
      .select(Svg::getSvgId, Svg::getSvgTypeCode, Svg::getSvgName, Svg::getSvgWidth, Svg::getSvgHeight, Svg::getSvgElement, Svg::getViewBox, Svg::getAxisx, Svg::getAxixy, Svg::getFontSize)
      .eq(Svg::getSvgTypeCode, svgType.getSvgTypeCode())
      .eq(Svg::getState, MapConstants.OPEN_STATE))));
    return R.ok(svgTypeList);
  }
}
