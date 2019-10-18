package com.wafersystems.virsical.map.controller;

import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.security.annotation.Inner;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapMsgConstants;
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
@Api(tags = "地图元素")
@AllArgsConstructor
@RestController
@RequestMapping("/map-element")
public class MapElementController extends BaseController {

  private final IMapElementService mapElementService;

  /**
   * 添加地图元素
   *
   * @param mapElementList 地图元素对象集合
   * @return R
   */
  @ApiOperation(value = "添加地图元素", notes = "添加地图元素（支持批量）")
  @ApiImplicitParam(name = "mapElementList", value = "地图元素对象集合", required = true, dataType = "MapElement")
  @PostMapping("/add")
  public R add(@RequestBody List<MapElement> mapElementList) {
    if (mapElementList.isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    return mapElementService.batchSaveMapElement(mapElementList) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "批量删除地图元素", notes = "批量根据地图id删除地图元素")
  @ApiImplicitParam(name = "ids", value = "地图元素id集合", required = true, dataType = "Integer")
  @PostMapping("/delete")
  public R delete(@RequestBody List<String> ids) {
    return mapElementService.removeByIds(ids) ? R.ok() : R.fail();
  }

  /**
   * 获取地图元素列表
   *
   * @param mapId mapId
   * @return R
   */
  @ApiOperation(value = "获取地图元素列表", notes = "根据地图元素对象条件获取地图元素列表")
  @GetMapping("/list")
  public R list(@RequestParam Integer mapId) {
    return R.ok(mapElementService.selectListByMapId(mapId));
  }

  /**
   * 地图元素资源绑定
   *
   * @param mapElementBindVoList 地图元素资源对象集合
   * @return R
   */
  @ApiOperation(value = "地图元素资源绑定", notes = "地图元素资源绑定（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素资源对象集合", required = true, dataType = "MapElement")
  @PostMapping("/bind")
  public R bind(@RequestBody List<MapElementBindVO> mapElementBindVoList) {
    if (mapElementBindVoList.isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    List<MapElement> mapElementList = new ArrayList<>();
    mapElementBindVoList.forEach(mapElementRouteVO -> {
      MapElement mapElement = new MapElement();
      BeanUtils.copyProperties(mapElementRouteVO, mapElement);
      mapElementList.add(mapElement);
    });
    return mapElementService.batchUpdateMapElement(mapElementList) ? R.ok() : R.fail();
  }

  /**
   * 地图元素绘制路径
   *
   * @param mapElementRouteVoList 地图元素路径对象集合
   * @return R
   */
  @ApiOperation(value = "地图元素绘制路径", notes = "地图元素绘制路径（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素路径对象集合", required = true, dataType = "MapElement")
  @PostMapping("/route")
  public R route(@RequestBody List<MapElementRouteVO> mapElementRouteVoList) {
    if (mapElementRouteVoList.isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    List<MapElement> mapElementList = new ArrayList<>();
    mapElementRouteVoList.forEach(mapElementRouteVO -> {
      MapElement mapElement = new MapElement();
      BeanUtils.copyProperties(mapElementRouteVO, mapElement);
      mapElementList.add(mapElement);
    });
    return mapElementService.batchUpdateMapElement(mapElementList) ? R.ok() : R.fail();
  }

  /**
   * 更新地图元素资源状态
   *
   * @param svgTypeCode                 素材类型code
   * @param mapElementObjectStateVoList 地图元素资源对象集合
   * @return R
   */
//  @Inner
  @ApiOperation(value = "更新地图元素资源状态", notes = "更新地图元素资源状态（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素资源对象集合", required = true, dataType = "MapElement")
  @PostMapping("/update-object-state/{svgTypeCode}")
  public R updateObjectState(@PathVariable String svgTypeCode,
                             @RequestBody List<MapElementObjectStateVO> mapElementObjectStateVoList) {
    if (mapElementObjectStateVoList.isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    return mapElementService.batchUpdateMapElementObjectState(svgTypeCode, mapElementObjectStateVoList)
      ? R.ok() : R.fail();
  }

}
