package com.wafersystems.virsical.map.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 地图元素绑定对象
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MapElementBindVO", description = "地图元素绑定对象")
public class MapElementBindVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图主键", example = "0")
  private Integer mapId;

  @ApiModelProperty(value = "地图元素主键", example = "0")
  private String mapElementId;

  @ApiModelProperty(value = "绑定对象id", example = "0")
  private String objectId;

  @ApiModelProperty(value = "绑定对象名称", example = "string")
  private String objectName;

  @ApiModelProperty(value = "绑定对象颜色", example = "string")
  private String objectColor;

  @ApiModelProperty(value = "绑定对象svg状态标识", example = "string")
  private String objectSvgStateCode;

  @ApiModelProperty(value = "绑定对象业务信息", example = "string")
  private String objectBusiness;

  @ApiModelProperty(value = "素材类型标识", example = "string")
  private String svgTypeCode;

  @ApiModelProperty(value = "扩展字段,用于区域跳转：extend={\"spaceId\":\"1\",\"mapId\":\"2\"}", example = "0")
  private String extend;
}
