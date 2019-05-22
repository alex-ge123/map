package com.wafersystems.virsical.map.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 地图元素路径对象
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MapElementRouteVO", description = "地图元素路径对象")
public class MapElementRouteVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图元素主键", example = "0")
  private Integer mapElementId;

  @ApiModelProperty(value = "引导线起点", example = "string")
  private String lineStart;

  @ApiModelProperty(value = "引导线主体", example = "string")
  private String lineMid;

  @ApiModelProperty(value = "引导线终点", example = "string")
  private String lineEnd;
}
