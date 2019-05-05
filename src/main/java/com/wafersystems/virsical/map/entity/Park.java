package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;

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
 * @since 2019-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Park对象", description = "园区")
public class Park extends Model<Park> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键", example = "1")
  @TableId(value = "park_id", type = IdType.AUTO)
  private Integer parkId;

  @ApiModelProperty(value = "租户id", example = "1")
  private Integer tenantId;

  @ApiModelProperty(value = "国家code", example = "1")
  private String country;

  @ApiModelProperty(value = "国家名称", example = "1")
  private String countryName;

  @ApiModelProperty(value = "删除标记", example = "1")
  @TableLogic
  private String delFlag;


  @Override
  protected Serializable pkVal() {
    return this.parkId;
  }

}
