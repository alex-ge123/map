package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@Setter
@Getter
@ToString
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

  @ApiModelProperty(value = "地图类型：0=基础版，1=专业版", example = "0")
  private Integer mapType;

  @ApiModelProperty(value = "创互地图项目ID", example = "string")
  private String cmapProjectId;

  @ApiModelProperty(value = "创互地图建筑ID", example = "string")
  private String cmapBuildingId;

  @ApiModelProperty(value = "创互地图楼层ID", example = "string")
  private String cmapFloorId;

  @ApiModelProperty(value = "创互地图云ID", example = "string")
  private String cmapCloudId;

  @ApiModelProperty(value = "创互地图提供的验证key", example = "string")
  private String cmapAccesstoken;

  @ApiModelProperty(value = "创互地图接口服务地址", example = "string")
  private String cmapServer;

  @ApiModelProperty(value = "创互地图配置参数", example = "string")
  private String cmapConfigParam;

  @ApiModelProperty(value = "逻辑删除：0-正常，1-删除", example = "0")
  @JsonIgnore
  @TableLogic
  private Integer delFlag;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;

  @ApiModelProperty(value = "地图颜色设置")
  @TableField(exist = false)
  private String colors;

  @ApiModelProperty(value = "地图图标设置")
  @TableField(exist = false)
  private String icon;


  @Override
  protected Serializable pkVal() {
    return this.mapId;
  }

}
