package com.wafersystems.virsical.map.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.entity.SvgType;
import com.wafersystems.virsical.map.mapper.MapMapper;
import com.wafersystems.virsical.map.model.vo.MapSearchResultVO;
import com.wafersystems.virsical.map.service.IMapService;
import com.wafersystems.virsical.map.service.ISvgTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
   * 查询地图分页
   *
   * @param page 分页对象
   * @param map  地图条件对象
   * @return 地图分页列表
   */
  @Override
  public IPage<Map> selectMapPage(Page<Map> page, Map map) {
    return page.setRecords(mapMapper.selectMapPage(page, map));
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
   * @param key         关键字
   * @param spaceId     区域id
   * @param svgTypeCode 素材类型标识
   * @return List
   */
  @Override
  public List<MapSearchResultVO> search(String key, Integer spaceId, String svgTypeCode) {
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
    return mapMapper.search(key, spaceId, svgTypeCode);
  }
}
