package com.wafersystems.virsical.map.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.dto.MapElementObjectStateVO;
import com.wafersystems.virsical.common.core.dto.MapElementUpdateDTO;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.security.annotation.Inner;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.manager.MapCacheManager;
import com.wafersystems.virsical.map.model.vo.MapElementBindRedirectVO;
import com.wafersystems.virsical.map.model.vo.MapElementBindVO;
import com.wafersystems.virsical.map.model.vo.MapElementRouteVO;
import com.wafersystems.virsical.map.service.IMapElementService;
import com.wafersystems.virsical.map.service.IMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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

  private final IMapService mapService;

  private final MapCacheManager cacheManager;

  /**
   * 添加地图元素
   *
   * @param mapId          地图id
   * @param mapElementList 地图元素对象集合
   * @param key            地图编辑权限key
   * @return R
   */
  @ApiOperation(value = "添加地图元素", notes = "添加地图元素（支持批量）")
  @ApiImplicitParam(name = "mapElementList", value = "地图元素对象集合", required = true, dataType = "MapElement")
  @PostMapping("/add/{mapId}/{key}")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_edit')")
  public R add(@PathVariable Integer mapId, @PathVariable String key, @RequestBody List<MapElement> mapElementList) {
    R r = cacheManager.checkMapEditPermission(mapId, key, true);
    if (r.getCode() == CommonConstants.FAIL) {
      return r;
    }
    int i = 0;
    for (MapElement mapElement : mapElementList) {
      mapElement.setSort(i++);
    }
    boolean b = mapElementService.batchSaveMapElement(mapId, mapElementList);
    if (b) {
      //推送地图更新消息
      mapElementService.push(MsgTypeEnum.BATCH.name(), MapConstants.ACTION_STATE_RELOAD, mapId.toString(), mapId);
    }
    return b ? R.ok() : R.fail();
  }

  @ApiOperation(value = "批量删除地图元素", notes = "批量根据地图id删除地图元素")
  @ApiImplicitParam(name = "ids", value = "地图元素id集合", required = true, dataType = "Integer")
  @PostMapping("/delete/{mapId}/{key}")
  @PreAuthorize("@pms.hasPermission('')")
  public R delete(@PathVariable Integer mapId, @PathVariable String key, @RequestBody List<String> ids) {
    R r = cacheManager.checkMapEditPermission(mapId, key, true);
    if (r.getCode() == CommonConstants.FAIL) {
      return r;
    }
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
  public R list(@RequestParam(required = false) Integer mapId, @RequestParam(required = false) Integer spaceId) {
    if (mapId == null && spaceId == null) {
      return R.fail(MapMsgConstants.PARAM_ERROR);
    }
    if (mapId == null) {
      List<Map> list = mapService.list(Wrappers.<Map>query().lambda().eq(Map::getFloorId, spaceId));
      if (list.size() == 1) {
        mapId = list.get(0).getMapId();
      }
    }
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
  @PreAuthorize("@pms.hasPermission('')")
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
    boolean b = mapElementService.batchUpdateMapElement(mapElementList);
    return b ? R.ok() : R.fail();
  }

  /**
   * 地图元素区域地图绑定
   *
   * @param mapElementBindRedirectVO 地图元素区域跳转绑定对象
   * @return R
   */
  @PostMapping("/bind-redirect")
  @PreAuthorize("@pms.hasPermission('')")
  public R bindRedirect(@RequestBody MapElementBindRedirectVO mapElementBindRedirectVO) {
    MapElement mapElement = mapElementService.getById(mapElementBindRedirectVO.getMapElementId());
    Assert.notNull(mapElement, MapMsgConstants.PARAM_ERROR);
    mapElement.setExtend(mapElementBindRedirectVO.getExtend());
    boolean b = mapElementService.batchUpdateMapElement(CollUtil.newArrayList(mapElement));
    return b ? R.ok() : R.fail();
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
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_path')")
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
    boolean b = mapElementService.batchUpdateMapElement(mapElementList);
    return b ? R.ok() : R.fail();
  }

  /**
   * 更新地图元素资源状态
   *
   * @param svgTypeCode                 素材类型code
   * @param mapElementObjectStateVoList 地图元素资源对象集合
   * @return R
   */
  @Inner
  @ApiOperation(value = "更新地图元素资源状态", notes = "更新地图元素资源状态（支持批量）")
  @ApiImplicitParam(name = "list", value = "地图元素资源对象集合", required = true, dataType = "MapElement")
  @PostMapping("/update-object-state/{svgTypeCode}")
  public R updateObjectState(@PathVariable String svgTypeCode,
                             @RequestBody List<MapElementObjectStateVO> mapElementObjectStateVoList) {
    if (mapElementObjectStateVoList.isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    boolean b = mapElementService.batchUpdateMapElementObjectState(svgTypeCode, mapElementObjectStateVoList);
    return b ? R.ok() : R.fail();
  }

  /**
   * 更新地图元素资源状态
   *
   * @param mapElementUpdateDTO 地图元素资源对象集合
   * @return R
   */
  @Inner
  @PostMapping("/update-object-state")
  public R updateObjectState(@RequestBody MapElementUpdateDTO mapElementUpdateDTO) {
    if (mapElementUpdateDTO.getMapElementObjectStateVoList().isEmpty()) {
      return R.fail(MapMsgConstants.MAP_ELEMENT_NO_NULL);
    }
    boolean b = mapElementService.batchUpdateMapElementObjectState(mapElementUpdateDTO.getSvgTypeCode(),
      mapElementUpdateDTO.getMapElementObjectStateVoList());
    return b ? R.ok() : R.fail();
  }

}
