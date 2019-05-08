package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;

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
 * 园区
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Park", description = "园区")
public class Park extends Model<Park> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "园区主键", example = "0")
  @TableId(value = "park_id", type = IdType.AUTO)
  private Integer parkId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "国家标识", example = "string")
  private String country;

  @ApiModelProperty(value = "国家名称", example = "string")
  private String countryName;

  @ApiModelProperty(value = "省份标识", example = "string")
  private String province;

  @ApiModelProperty(value = "省份名称", example = "string")
  private String provinceName;

  @ApiModelProperty(value = "城市标识", example = "string")
  private String city;

  @ApiModelProperty(value = "城市名称", example = "string")
  private String cityName;

  @ApiModelProperty(value = "园区名字", example = "string")
  private String parkName;

  @ApiModelProperty(value = "时区", example = "string")
  private String timeZone;

  @ApiModelProperty(value = "时区名称", example = "string")
  private String timeZoneName;

  @ApiModelProperty(value = "上午时间段", example = "string")
  private String amTime;

  @ApiModelProperty(value = "下午时间段", example = "string")
  private String pmTime;

  @ApiModelProperty(value = "全天时间段", example = "string")
  private String allDay;

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
    return this.parkId;
  }

}
