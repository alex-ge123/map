package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wafersystems.virsical.map.entity.MapElement;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * 地图元素 Mapper 接口
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
public interface MapElementMapper extends BaseMapper<MapElement> {

  /**
   * 查询地图元素集合
   *
   * @param mapId mapId
   * @return 地图元素集合
   */
  List<MapElement> selectListByMapId(Integer mapId);

  /**
   * 更新地图元素资源状态
   *
   * @param svgTypeCode        素材类型状态
   * @param objectId           地图元素资源id
   * @param objectName         地图元素资源名称
   * @param objectColor        地图元素资源颜色
   * @param objectSvgStateCode 地图元素资源状态标识
   * @return 结果
   */
  Integer updateMapElementObjectState(@Param("svgTypeCode") String svgTypeCode,
                                      @Param("objectId") String objectId,
                                      @Param("objectName") String objectName,
                                      @Param("objectColor") String objectColor,
                                      @Param("objectSvgStateCode") String objectSvgStateCode);

  /**
   * 解绑
   *
   * @param collect idList
   */
  @Update("UPDATE map_element SET object_id = NULL,object_name = NULL,object_color = NULL,object_svg_state_code = NULL,object_business = '' WHERE map_element_id IN (#{collect})")
  void unBind(@Param("collect") List<String> collect);
}
