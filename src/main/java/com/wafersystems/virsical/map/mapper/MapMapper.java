package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.model.vo.MapSearchResultVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 地图 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
public interface MapMapper extends BaseMapper<Map> {
  /**
   * 地图上线
   *
   * @param ids 区域id集合
   */
  void online(@Param("ids") List<Integer> ids);

  /**
   * 查询地图分页
   *
   * @param page 分页对象
   * @param map  地图条件对象
   * @return 地图分页列表
   */
  List<Map> selectMapPage(@Param("page") Page page, @Param("map") Map map);

  /**
   * 根据空间节点id查询地图
   *
   * @param ids 空间id集合
   * @return mapList
   */
  @SqlParser(filter = true)
  List<Map> selectMapListBySpaceId(@Param("ids") List<Integer> ids);

  /**
   * 模糊搜索地图元素
   *
   * @param page        分页参数
   * @param key         关键字
   * @param spaceId     空间id
   * @param svgTypeCode 素材类型标识
   * @return List
   */
  List<MapSearchResultVO> search(@Param("page") Page page, @Param("key") String key,
                                 @Param("spaceId") Integer spaceId, @Param("svgTypeCode") String svgTypeCode);
}
