package com.wafersystems.virsical.map.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 地图搜索结果
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MapSearchResultVO", description = "地图搜索结果")
public class MapSearchResultVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图id", example = "0")
  private Integer mapId;

  @ApiModelProperty(value = "地图类型", example = "0")
  private Integer mapType;

  @ApiModelProperty(value = "区域id", example = "0")
  private Integer floorId;

  @ApiModelProperty(value = "地图元素id", example = "0")
  private String mapElementId;

  @ApiModelProperty(value = "绑定对象id", example = "0")
  private String objectId;

  @ApiModelProperty(value = "绑定对象名称", example = "string")
  private String objectName;

  @ApiModelProperty(value = "绑定对象颜色", example = "string")
  private String objectColor;

  @ApiModelProperty(value = "绑定对象svg状态标识", example = "string")
  private String objectSvgStateCode;

  @ApiModelProperty(value = "素材类型标识", example = "string")
  private String svgTypeCode;

}
