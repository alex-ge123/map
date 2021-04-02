package com.wafersystems.virsical.map.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.dto.Page;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.entity.SysSpace;
import com.wafersystems.virsical.common.entity.UserVO;
import com.wafersystems.virsical.common.feign.RemoteSpaceService;
import com.wafersystems.virsical.map.common.BaseController;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.manager.MapCacheManager;
import com.wafersystems.virsical.map.model.dto.SpaceMapDTO;
import com.wafersystems.virsical.map.model.vo.MapSearchResultVO;
import com.wafersystems.virsical.map.service.IMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 地图 前端控制器
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Api(tags = "地图")
@AllArgsConstructor
@RestController
@RequestMapping("/map")
public class MapController extends BaseController {

  private final IMapService mapService;

  private final RemoteSpaceService remoteSpaceService;

  private final MapCacheManager cacheManager;

  private final StringRedisTemplate stringRedisTemplate;

  @ApiOperation(value = "添加地图", notes = "添加地图")
  @ApiImplicitParam(name = "map", value = "地图对象", required = true, dataType = "Map")
  @PostMapping("/add/{key}")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_upload')")
  public R add(@RequestBody Map map, @PathVariable String key) {
    List<Map> list = mapService.list(Wrappers.<Map>query().lambda().eq(Map::getFloorId, map.getFloorId()));
    if (list != null && !list.isEmpty()) {
      return update(map, key);
    }
    return mapService.save(map) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "修改地图", notes = "根据地图id修改地图")
  @ApiImplicitParam(name = "map", value = "地图对象", required = true, dataType = "Map")
  @PostMapping("/update/{key}")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_edit')")
  public R update(@RequestBody Map map, @PathVariable String key) {
    // 验证操作权限
    R r = cacheManager.checkMapEditPermission(map.getMapId(), key, false);
    if (r.getCode() == CommonConstants.FAIL) {
      return r;
    }
    return mapService.updateById(map) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "删除地图", notes = "根据地图id删除地图")
  @ApiImplicitParam(name = "id", value = "地图id", required = true, dataType = "Integer")
  @PostMapping("/delete/{id}")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_del')")
  public R delete(@PathVariable Integer id) {
    return mapService.removeById(id) ? R.ok() : R.fail();
  }

  @ApiOperation(value = "根据地图id获取地图", notes = "根据地图id获取地图")
  @ApiImplicitParam(name = "id", value = "地图id", required = true, dataType = "Integer")
  @GetMapping("/{id}")
  public R<Map> get(@PathVariable Integer id) {
    return R.ok(mapService.getById(id));
  }

  @ApiOperation(value = "根据地图id或区域id获取地图", notes = "根据地图id或区域id获取地图")
  @GetMapping("/getByMapIdOrSpaceId")
  public R getByMapIdOrSpaceId(@RequestParam(required = false) Integer mapId,
                               @RequestParam(required = false) Integer spaceId) {
    if (mapId == null && spaceId == null) {
      return R.fail(MapMsgConstants.PARAM_ERROR);
    }
    if (mapId == null) {
      return R.ok(mapService.getOne(Wrappers.<Map>query().lambda().eq(Map::getFloorId, spaceId)));
    }
    return R.ok(mapService.getById(mapId));
  }

  @ApiOperation(value = "根据空间id获取地图", notes = "根据空间id获取地图")
  @ApiImplicitParam(name = "spaceId", value = "空间id", required = true, dataType = "Integer")
  @GetMapping("/getBySpaceId/{spaceId}")
  public R<Map> getBySpaceId(@PathVariable Integer spaceId) {
    return R.ok(mapService.getOne(Wrappers.<Map>query().lambda().eq(Map::getFloorId, spaceId)));
  }

  @ApiOperation(value = "获取地图列表", notes = "根据地图对象条件获取地图列表")
  @GetMapping("/list")
  public R<List<Map>> list(Map map) {
    return R.ok(mapService.list(Wrappers.query(map)));
  }

  @ApiOperation(value = "获取分页地图列表", notes = "根据分页条件、地图对象条件获取分页地图列表")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "page", value = "分页对象", required = true, dataType = "Page"),
    @ApiImplicitParam(name = "spaceId", value = "空间节点id", dataType = "Integer")
  })
  @GetMapping("/page")
  public R page(Page page, Integer spaceId) {
    // 从用户服务查询空间叶子节点分页
    R<Page<SysSpace>> r = remoteSpaceService.getNodePage(page.getSize(), page.getCurrent(), spaceId);
    if (CommonConstants.SUCCESS != r.getCode()) {
      return R.fail(MapMsgConstants.QUERY_MAP_FAILED);
    }
    Page<SysSpace> spacePage = r.getData();
    if (spacePage.getTotal() == 0) {
      return R.ok(new Page<>());
    }
    Page<SpaceMapDTO> spaceMapPage = new Page<>();
    BeanUtil.copyProperties(spacePage, spaceMapPage);
    List<SpaceMapDTO> spaceMapList = new ArrayList<>();
    List<Integer> spaceIds = new ArrayList<>();
    for (SysSpace sysSpace : spacePage.getRecords()) {
      spaceIds.add(sysSpace.getSpaceId());
      SpaceMapDTO spaceMapDTO = new SpaceMapDTO();
      BeanUtil.copyProperties(sysSpace, spaceMapDTO);
      spaceMapList.add(spaceMapDTO);
    }
    // 根据空间节点查询地图
    List<Map> mapList = mapService.list(Wrappers.<Map>query().lambda().in(Map::getFloorId,
      spaceIds.toArray()));
    // 组装地图返回对象，包含地图详情
    for (SpaceMapDTO dto : spaceMapList) {
      for (Map m : mapList) {
        if (dto.getSpaceId().equals(m.getFloorId())) {
          dto.setMap(m);
          // 判断当前地图是否编辑中，获取剩余编辑时间
          Long expire = stringRedisTemplate.getExpire(MapConstants.MAP_EDIT_PERMISSION + m.getMapId(),
            TimeUnit.SECONDS);
          if (expire != null && expire > 0) {
            dto.setExpire(expire);
          } else {
            break;
          }
          // 获取对应编辑人
          String cacheValue = stringRedisTemplate.opsForValue().get(MapConstants.MAP_EDIT_PERMISSION + m.getMapId());
          if (cacheValue != null && cacheValue.contains(CommonConstants.COMMA)) {
            dto.setUsername(cacheValue.split(CommonConstants.COMMA)[0]);
          }
        }
      }
    }
    spaceMapPage.setRecords(spaceMapList);
    return R.ok(spaceMapPage);
  }

  @ApiOperation(value = "根据空间节点id查询地图", notes = "根据空间节点id查询地图")
  @GetMapping("/listBySpaceIds")
  public R<List<Map>> listBySpaceIds(Integer[] spaceIds) {
    if (spaceIds == null || spaceIds.length == 0) {
      return R.ok(new ArrayList<>());
    }
    return R.ok(mapService.selectMapListBySpaceId(spaceIds));
  }

  /**
   * 根据地图id获取编辑权限
   *
   * @param mapId 地图id
   * @param key   权限key（前端生成uuid）
   * @return R
   */
  @GetMapping("/getEditPermission")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage')")
  public R getEditPermission(@RequestParam Integer mapId, @RequestParam String key) {
    R r = cacheManager.checkMapEditPermission(mapId, key, true);
    if (r.getCode() == CommonConstants.FAIL) {
      return r;
    }
    return R.ok();
  }

  /**
   * 强制获取地图编辑权限
   *
   * @param mapId 地图id
   * @param key   权限key（前端生成uuid）
   * @return R
   */
  @GetMapping("/forceGetEditPermission")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage')")
  public R forceGetEditPermission(@RequestParam Integer mapId, @RequestParam String key) {
    key = TenantContextHolder.getUsername() + CommonConstants.COMMA + key;
    cacheManager.cacheMapEditPermission(mapId, key);
    return R.ok();
  }

  /**
   * 释放地图编辑权限
   *
   * @param mapId 地图id
   * @return R
   */
  @GetMapping("/releaseEditPermission")
  @PreAuthorize("@pms.hasPermission('admin@common@map_manage_release')")
  public R releaseEditPermission(@RequestParam Integer mapId) {
    cacheManager.releaseEditPermission(mapId);
    return R.ok();
  }

  /**
   * 查询首页地图，先取用户默认区域地图，不存在则取第一张地图
   *
   * @param spaceId 指定区域id
   * @return R
   */
  @GetMapping("/index")
  public R index(Integer spaceId) {
    LambdaQueryWrapper<Map> wrapper = Wrappers.<Map>query().lambda();
    if (spaceId != null) {
      // 查询指定区域地图
      wrapper.eq(Map::getFloorId, spaceId);
    } else {
      wrapper.orderByAsc(Map::getFloorId);
    }
    List<Map> list = mapService.list(wrapper);
    if (list == null || list.isEmpty()) {
      if (spaceId != null) {
        return R.builder().code(CommonConstants.FAIL).msg(MapMsgConstants.SPACE_OFFLINE_DO_NOT_JUMP).build();
      }
      return R.ok();
    }
    if (spaceId == null) {
      // 获取用户默认区域地图
      final UserVO userVO = cacheManager.getUserFromRedis();
      if (userVO.getDefaultZone() != null && userVO.getDefaultZone() > 0) {
        for (Map m : list) {
          if (userVO.getDefaultZone().equals(m.getFloorId())) {
            return R.ok(m);
          }
        }
      }
    }
    return R.ok(list.get(0));
  }

  /**
   * 模糊搜索地图元素
   *
   * @param page        分页参数
   * @param key         关键字
   * @param spaceId     区域id
   * @param svgTypeCode 素材类型标识
   * @return R
   */
  @GetMapping("/search")
  public R search(com.baomidou.mybatisplus.extension.plugins.pagination.Page<MapSearchResultVO> page,
                  String key, Integer spaceId, String svgTypeCode) {
    if (StrUtil.isAllEmpty(key, svgTypeCode) && spaceId == null) {
      return R.ok();
    }
    return R.ok(mapService.search(page, key, spaceId, svgTypeCode));
  }

  /**
   * 查询树形菜单集合
   */
  @GetMapping(value = "/space-tree")
  public R getTree() {
    List<Map> list = mapService.list();
    JSONArray jsonArray = JSON.parseArray(cacheManager.getSpaceTreeFromRedis());
    Assert.notNull(jsonArray, MapMsgConstants.PARAM_ERROR);
    setMapId(jsonArray, list);
    return R.ok(jsonArray);
  }

  /**
   * 区域树设置地图id
   *
   * @param jsonArray jsonArray
   * @param list      地图列表
   */
  private void setMapId(JSONArray jsonArray, List<Map> list) {
    for (Object o : jsonArray) {
      JSONObject jsonObject = (JSONObject) o;
      Integer id = jsonObject.getInteger("id");
      for (Map map : list) {
        if (map.getFloorId().equals(id)) {
          jsonObject.put("mapId", map.getMapId());
          break;
        }
      }
      JSONArray children = jsonObject.getJSONArray("children");
      setMapId(children, list);
    }
  }
}
