package com.wafersystems.virsical.map.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 地图元素资源状态对象
 * </p>
 *
 * @author tandk
 * @since 2019-05-13
 */
@Data
@ApiModel(value = "MapElementObjectStateVO", description = "地图元素资源状态对象")
public class MapElementObjectStateVO implements Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "绑定对象id", example = "string")
  private String objectId;

  @ApiModelProperty(value = "绑定对象名称", example = "string")
  private String objectName;

  @ApiModelProperty(value = "绑定对象颜色", example = "string")
  private String objectColor;

  @ApiModelProperty(value = "绑定对象svg状态标识", example = "string")
  private String objectSvgStateCode;

}
