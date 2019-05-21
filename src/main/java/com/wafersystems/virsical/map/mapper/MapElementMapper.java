package com.wafersystems.virsical.map.mapper;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.wafersystems.virsical.map.entity.MapElement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import org.apache.ibatis.annotations.Param;

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
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode                 素材类型状态
   * @param mapElementObjectStateVOList 地图元素资源状态集合
   * @return 结果
   */
  @SqlParser(filter = true)
  Integer batchUpdateMapElementObjectState(@Param("svgTypeCode") String svgTypeCode,
                                           @Param("list") List<MapElementObjectStateVO> mapElementObjectStateVOList);
}
