package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.common.core.exception.BusinessException;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.common.SvgUtils;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.entity.Svg;
import com.wafersystems.virsical.map.entity.SvgState;
import com.wafersystems.virsical.map.entity.SvgType;
import com.wafersystems.virsical.map.service.IMapElementService;
import com.wafersystems.virsical.map.service.ISvgService;
import com.wafersystems.virsical.map.service.ISvgStateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "素材")
@AllArgsConstructor
@RestController
@RequestMapping("/svg")
public class SvgController extends BaseController {

  private final ISvgService svgService;
  private final ISvgStateService svgStateService;
  private final IMapElementService mapElementService;

  /**
   * 解析SVG文件
   *
   * @param svgFile SVG文件
   * @return R
   */
  @ApiOperation(value = "解析SVG文件", notes = "解析SVG文件")
  @PostMapping("/parse")
  @PreAuthorize("@pms.hasPermission('')")
  public R parse(@RequestParam MultipartFile svgFile) {
    java.util.Map<String, String> svgMap;
    try {
      svgMap = SvgUtils.analyzeSvgFile(svgFile.getInputStream());
    } catch (Exception e) {
      throw new BusinessException(MapMsgConstants.SVG_FILE_PARSE_EXCEPTION);
    }
    return R.ok(svgMap);
  }

  @ApiOperation(value = "添加素材", notes = "添加素材")
  @ApiImplicitParam(name = "svg", value = "素材对象", required = true, dataType = "Svg")
  @PostMapping("/add")
  @PreAuthorize("@pms.hasPermission('')")
  public R add(@RequestBody Svg svg) {
    return svgService.save(svg) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改素材", notes = "根据素材id修改素材")
  @ApiImplicitParam(name = "svg", value = "素材对象", required = true, dataType = "Svg")
  @PostMapping("/update")
  @PreAuthorize("@pms.hasPermission('')")
  public R update(@RequestBody Svg svg) {
    return svgService.updateById(svg) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除素材", notes = "根据素材id删除素材")
  @ApiImplicitParam(name = "id", value = "素材id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  @PreAuthorize("@pms.hasPermission('')")
  public R delete(@PathVariable Integer id) {
    List<MapElement> list = mapElementService.list(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getSvgId, id));
    if (!list.isEmpty()) {
      return R.fail(MapMsgConstants.MATERIAL_USED);
    }
    if (svgService.removeById(id)) {
      return R.ok();
    } else {
      return R.fail();
    }
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

  @ApiOperation(value = "获取启用的素材列表及对应素材状态集合", notes = "获取启用的素材列表及对应素材状态集合")
  @GetMapping("/enable-list-and-svg-state")
  public R<List<Svg>> enableListAndSvgState() {
    List<Svg> list = svgService.list(Wrappers.<Svg>lambdaQuery().eq(Svg::getState, MapConstants.OPEN_STATE));
    for (Svg svg : list) {
      svg.setSvgStateList(svgStateService.list(Wrappers.<SvgState>lambdaQuery().eq(SvgState::getSvgId,
        svg.getSvgId())));
    }
    return R.ok(list);
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

  /**
   * 获取素材分组列表
   *
   * @return R
   */
  @ApiOperation(value = "获取素材分组列表", notes = "获取素材分组列表")
  @GetMapping("/group")
  public R<List<SvgType>> group() {
    List<SvgType> svgTypeList = new SvgType().selectList(Wrappers.<SvgType>lambdaQuery()
      .select(SvgType::getSvgTypeCode, SvgType::getSvgTypeName)
      .eq(SvgType::getSvgTypeState, MapConstants.OPEN_STATE));
    svgTypeList.forEach(svgType -> svgType.setSvgList(svgService.list(Wrappers.<Svg>lambdaQuery()
      .select(Svg::getSvgId, Svg::getSvgTypeCode, Svg::getSvgName, Svg::getSvgWidth,
        Svg::getSvgHeight, Svg::getSvgElement, Svg::getViewBox, Svg::getAxisx, Svg::getAxixy, Svg::getFontSize)
      .eq(Svg::getSvgTypeCode, svgType.getSvgTypeCode())
      .eq(Svg::getState, MapConstants.OPEN_STATE))));
    return R.ok(svgTypeList);
  }
}
