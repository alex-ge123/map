package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;

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
 * @since 2019-04-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "Park对象", description = "园区")
public class Park extends Model<Park> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "主键")
  @TableId(value = "park_id", type = IdType.AUTO)
  private Integer parkId;

  @ApiModelProperty(value = "租户id")
  @TableField("tenant_id")
  private Integer tenantId;

  @ApiModelProperty(value = "国家code")
  @TableField("country")
  private String country;

  @ApiModelProperty(value = "国家名称")
  @TableField("country_name")
  private String countryName;

  @ApiModelProperty(value = "删除标记")
  @TableField("del_flag")
  @TableLogic
  private String delFlag;


  @Override
  protected Serializable pkVal() {
    return this.parkId;
  }

}
