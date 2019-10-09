package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 地图元素
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Setter
@Getter
@ToString
@ApiModel(value = "MapElement", description = "地图元素")
public class MapElement extends Model<MapElement> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图元素主键", example = "0")
  @TableId(value = "map_element_id", type = IdType.UUID)
  private String mapElementId;

  @ApiModelProperty(value = "地图id", example = "0")
  private Integer mapId;

  @ApiModelProperty(value = "素材类型标识", example = "string")
  private String svgTypeCode;

  @ApiModelProperty(value = "素材id", example = "0")
  private Integer svgId;

  @ApiModelProperty(value = "自定义素材", example = "string")
  private String customElement;

  @ApiModelProperty(value = "页面元素ID", example = "string")
  private String mapWebId;

  @ApiModelProperty(value = "旋转角度", example = "string")
  private String cycleValue;

  @ApiModelProperty(value = "X坐标", example = "string")
  private String stationx;

  @ApiModelProperty(value = "Y坐标", example = "string")
  private String stationy;

  @ApiModelProperty(value = "绑定对象id", example = "string")
  private String objectId;

  @ApiModelProperty(value = "绑定对象名称", example = "string")
  private String objectName;

  @ApiModelProperty(value = "绑定对象颜色", example = "string")
  private String objectColor;

  @ApiModelProperty(value = "绑定对象svg状态标识", example = "string")
  private String objectSvgStateCode;

  @ApiModelProperty(value = "引导线起点", example = "string")
  private String lineStart;

  @ApiModelProperty(value = "引导线主体", example = "string")
  private String lineMid;

  @ApiModelProperty(value = "引导线终点", example = "string")
  private String lineEnd;

  @ApiModelProperty(value = "扩展字段", example = "string")
  private String extend;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;


  @Override
  protected Serializable pkVal() {
    return this.mapElementId;
  }

  private transient String svgStateElement;
}
