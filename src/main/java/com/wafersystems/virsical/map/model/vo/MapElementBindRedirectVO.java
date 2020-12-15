package com.wafersystems.virsical.map.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 地图元素区域跳转绑定对象
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MapElementBindRedirectVO", description = "地图元素区域跳转绑定对象")
public class MapElementBindRedirectVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图元素主键", example = "0")
  private String mapElementId;

  @ApiModelProperty(value = "扩展字段extend={\"spaceId\":\"1\",\"mapId\":\"2\"}", example = "0")
  private String extend;

}
