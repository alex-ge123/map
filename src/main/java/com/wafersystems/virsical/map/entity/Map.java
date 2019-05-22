package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 地图
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Map", description = "地图")
public class Map extends Model<Map> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "地图主键", example = "0")
  @TableId(value = "map_id", type = IdType.AUTO)
  private Integer mapId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "楼层id", example = "0")
  private Integer floorId;

  @ApiModelProperty(value = "地图底图url", example = "string")
  private String baseMapUrl;

  @ApiModelProperty(value = "SVG内容", example = "string")
  private String baseMapElement;

  @ApiModelProperty(value = "SVG属性", example = "string")
  private String viewBox;

  @ApiModelProperty(value = "地图缩略图url", example = "string")
  private String zoomImageUrl;

  @ApiModelProperty(value = "面积", example = "string")
  private String areaAge;

  @ApiModelProperty(value = "高度", example = "string")
  private String height;

  @ApiModelProperty(value = "宽度", example = "string")
  private String width;

  @ApiModelProperty(value = "地图状态：0=正常，1=停用", example = "0")
  private Integer mapState;

  @ApiModelProperty(value = "地图类型：默认0=wafer2D，1=蜂鸟map", example = "0")
  private Integer mapType;

  @ApiModelProperty(value = "蜂鸟mapKey", example = "string")
  private String fmapKey;

  @ApiModelProperty(value = "蜂鸟fmapID 即上传的文件名", example = "string")
  private String fmapId;

  @ApiModelProperty(value = "蜂鸟appName", example = "string")
  private String fmapAppName;

  @ApiModelProperty(value = "蜂鸟地图路径 无文件类型后缀", example = "string")
  private String fmapServerUrl;

  @ApiModelProperty(value = "蜂鸟地图路径起点", example = "string")
  private String fmapStartPoint;

  @ApiModelProperty(value = "蜂鸟地图名称", example = "string")
  private String fmapName;

  @ApiModelProperty(value = "蜂鸟地图上传后地址", example = "string")
  private String fmapUploadUrl;

  @ApiModelProperty(value = "逻辑删除：0-正常，1-删除", example = "0")
  @JsonIgnore
  @TableLogic
  private Integer delFlag;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;


  @Override
  protected Serializable pkVal() {
    return this.mapId;
  }

  /**
   * 园区id
   */
  private transient Integer parkId;

  /**
   * 园区名称
   */
  private transient String parkName;

  /**
   * 楼宇id
   */
  private transient Integer buildingId;

  /**
   * 楼宇名称
   */
  private transient String buildingName;

  /**
   * 楼层号
   */
  private transient String floorNum;
}
