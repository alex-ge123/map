package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 楼宇
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Building", description = "楼宇")
public class Building extends Model<Building> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "楼宇主键", example = "0")
  @TableId(value = "building_id", type = IdType.AUTO)
  private Integer buildingId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "园区id", example = "0")
  private Integer parkId;

  @ApiModelProperty(value = "办公楼名称,B1", example = "string")
  private String buildingName;

  @ApiModelProperty(value = "办公楼与一体机唯一对应", example = "string")
  private String buildingToken;

  @ApiModelProperty(value = "办公楼与楼宇外景图一一对应", example = "string")
  private String buildingImg;

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
    return this.buildingId;
  }

  /**
   * 园区名称
   */
  private transient String parkName;
}
