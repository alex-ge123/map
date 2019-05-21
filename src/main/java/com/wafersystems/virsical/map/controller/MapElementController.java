package com.wafersystems.virsical.map.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MsgConstants;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.model.vo.MapElementBindVO;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import com.wafersystems.virsical.map.model.vo.MapElementRouteVO;
import com.wafersystems.virsical.map.service.IMapElementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 地图元素 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Api(tags = "地图元素", description = "mapElement")
@AllArgsConstructor
@RestController
@RequestMapping("/map-element")
public class MapElementController extends BaseController {

  private final IMapElementService mapElementService;

  @ApiOperation(value = "添加地图元素", notes = "添加地图元素（支持批量）")
  @ApiImplicitParam(name = "mapElementList", value = "地图元素对象集合", required = true, dataType = "MapElement")
  @PostMapping("/add")
  public R add(@RequestBody List<MapElement> mapElementList) {
    if (mapElementList.isEmpty()) {
      return R.fail(MsgConstants.MAP_ELEMENT_NO_NULL);
    }
    return mapElementService.batchSaveMapElement(mapElementList) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "获取地图元素列表", notes = "根据地图元素对象条件获取地图元素列表")
  @GetMapping("/list")
  public R<List<MapElement>> list(MapElement mapElement) {
    return R.ok(mapElementService.list(Wrappers.query(mapElement)));
  }

  @ApiOperation(value = "地图元素资源绑定", notes = "地图元素资源绑定（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素资源对象集合", required = true, dataType = "MapElement")
  @PostMapping("/bind")
  public R bind(@RequestBody List<MapElementBindVO> list) {
    if (list.isEmpty()) {
      return R.fail(MsgConstants.MAP_ELEMENT_NO_NULL);
    }
    List<MapElement> mapElementList = new ArrayList<>();
    list.forEach(mapElementRouteVO -> {
      MapElement mapElement = new MapElement();
      BeanUtils.copyProperties(mapElementRouteVO, mapElement);
      mapElementList.add(mapElement);
    });
    return mapElementService.batchUpdateMapElement(mapElementList) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "地图元素绘制路径", notes = "地图元素绘制路径（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素路径对象集合", required = true, dataType = "MapElement")
  @PostMapping("/route")
  public R route(@RequestBody List<MapElementRouteVO> list) {
    if (list.isEmpty()) {
      return R.fail(MsgConstants.MAP_ELEMENT_NO_NULL);
    }
    List<MapElement> mapElementList = new ArrayList<>();
    list.forEach(mapElementRouteVO -> {
      MapElement mapElement = new MapElement();
      BeanUtils.copyProperties(mapElementRouteVO, mapElement);
      mapElementList.add(mapElement);
    });
    return mapElementService.batchUpdateMapElement(mapElementList) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "更新地图元素资源状态", notes = "更新地图元素资源状态（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素资源对象集合", required = true, dataType = "MapElement")
  @PostMapping("/update-object-state/{svgTypeCode}")
  public R updateObjectState(@PathVariable String svgTypeCode,
                             @RequestBody List<MapElementObjectStateVO> list) {
    if (list.isEmpty()) {
      return R.fail(MsgConstants.MAP_ELEMENT_NO_NULL);
    }
    return mapElementService.batchUpdateMapElementObjectState(svgTypeCode, list) ?
      R.ok() : R.fail();
  }

}
