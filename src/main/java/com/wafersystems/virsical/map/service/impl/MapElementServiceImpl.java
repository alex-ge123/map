package com.wafersystems.virsical.map.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.mapper.MapElementMapper;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import com.wafersystems.virsical.map.service.IMapElementService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 地图元素 服务实现类
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Service
@AllArgsConstructor
public class MapElementServiceImpl extends ServiceImpl<MapElementMapper, MapElement> implements IMapElementService {
  private final MapElementMapper mapElementMapper;

  /**
   * 批量保存地图元素（先删再存）
   *
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean batchSaveMapElement(List<MapElement> mapElementList) {
    // 删除此地图所有地图元素
    super.remove(Wrappers.<MapElement>lambdaQuery().eq(MapElement::getMapId, mapElementList.get(0).getMapId()));
    // 批量保存新地图元素
    return super.saveBatch(mapElementList);
  }

  /**
   * 批量更新地图元素（资源绑定、路径保存）
   *
   * @param mapElementList 地图元素集合
   * @return Boolean
   */
  @Override
  public Boolean batchUpdateMapElement(List<MapElement> mapElementList) {
    return super.updateBatchById(mapElementList);
  }

  /**
   * 批量更新地图元素资源状态
   *
   * @param svgTypeCode                 地图元素类型
   * @param mapElementObjectStateVoList 地图元素资源状态集合
   * @return Boolean
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Boolean batchUpdateMapElementObjectState(String svgTypeCode,
                                                  List<MapElementObjectStateVO> mapElementObjectStateVoList) {
    Integer result = mapElementMapper.batchUpdateMapElementObjectState(svgTypeCode, mapElementObjectStateVoList);
    return result > 0 ? Boolean.TRUE : Boolean.FALSE;
  }
}
