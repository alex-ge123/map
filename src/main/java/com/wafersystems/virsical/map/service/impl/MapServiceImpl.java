package com.wafersystems.virsical.map.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.common.core.constant.CommonCacheConstants;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import com.wafersystems.virsical.common.core.vo.UserSimpleVO;
import com.wafersystems.virsical.common.entity.SysDept;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.entity.SvgType;
import com.wafersystems.virsical.map.mapper.MapMapper;
import com.wafersystems.virsical.map.model.vo.MapSearchResultVO;
import com.wafersystems.virsical.map.service.IMapService;
import com.wafersystems.virsical.map.service.ISvgTypeService;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 地图 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
@AllArgsConstructor
public class MapServiceImpl extends ServiceImpl<MapMapper, Map> implements IMapService {
  private final MapMapper mapMapper;

  private final ISvgTypeService svgTypeService;

  private final StringRedisTemplate stringRedisTemplate;

  /**
   * 地图上线
   *
   * @param spaceIds 区域id集合
   */
  @Override
  public void online(List<Integer> spaceIds) {
    mapMapper.online(spaceIds);
  }

  /**
   * 根据空间节点id查询地图
   *
   * @param spaceIds 空间id集合
   * @return mapList
   */
  @Override
  public List<Map> selectMapListBySpaceId(Integer[] spaceIds) {
    return mapMapper.selectMapListBySpaceId(Arrays.asList(spaceIds));
  }

  /**
   * 模糊搜索地图元素
   *
   * @param page        分页参数
   * @param key         关键字
   * @param spaceId     区域id
   * @param svgTypeCode 素材类型标识
   * @return IPage
   */
  @Override
  public IPage<MapSearchResultVO> search(Page<MapSearchResultVO> page, String key, Integer spaceId, String svgTypeCode) {
    if (StrUtil.isNotBlank(key)) {
      // 判断搜索关键字是否是类型名称，是则进行类型搜索，不是进行模糊搜索
      List<SvgType> list = svgTypeService.list(Wrappers.<SvgType>lambdaQuery().eq(SvgType::getSvgTypeState,
        MapConstants.OPEN_STATE));
      for (SvgType svgType : list) {
        if (StrUtil.equalsAny(key, svgType.getSvgTypeName(), svgType.getSvgTypeNameEn(), svgType.getSvgTypeNameTw())) {
          key = "";
          svgTypeCode = svgType.getSvgTypeCode();
          break;
        }
      }
    }
    // 搜素地图资源
    List<MapSearchResultVO> list = mapMapper.search(page, key, spaceId, svgTypeCode);

    // 根据userId（objectBusiness）获取全路径部门名称
    HashMap<String, List<String>> userDeptPathList = new HashMap<>(list.size());
    list.forEach(mapSearchResultVO -> {
      if (StrUtil.isEmpty(mapSearchResultVO.getObjectBusiness())) {
        return;
      }
      try {
        if (userDeptPathList.containsKey(mapSearchResultVO.getObjectBusiness())) {
          mapSearchResultVO.setTags(userDeptPathList.get(mapSearchResultVO.getObjectBusiness()));
        } else {
          // 通过用户id查询用户缓存，获取部门id
          Object userObject =
            stringRedisTemplate.opsForHash().get(CommonCacheConstants.USER_KEY + TenantContextHolder.getTenantId(),
              mapSearchResultVO.getObjectBusiness());
          if (userObject != null) {
            UserSimpleVO userSimpleVO = JSON.parseObject((String) userObject, UserSimpleVO.class);
            Assert.notNull(userSimpleVO.getDeptId(), "userSimpleVO.getDeptId() is null");
            // 通过部门id查询部门缓存，获取部门全路径名称
            Object deptObject = stringRedisTemplate.opsForHash().get(
              CommonConstants.DEPT_KEY + TenantContextHolder.getTenantId(), userSimpleVO.getDeptId() + "");
            if (deptObject != null) {
              SysDept sysDept = JSON.parseObject((String) deptObject, SysDept.class);
              List<String> tags = new ArrayList<>();
              tags.add(sysDept.getPathName());
              userDeptPathList.put(mapSearchResultVO.getObjectBusiness(), tags);
              mapSearchResultVO.setTags(userDeptPathList.get(mapSearchResultVO.getObjectBusiness()));
            }
          }
        }
      } catch (Exception e) {
        log.error("根据userId[" + mapSearchResultVO.getObjectBusiness() + "]（objectBusiness）获取全路径部门名称异常", e);
      }
    });
    return page.setRecords(list);
  }
}
