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
 * 楼层
 * </p>
 *
 * @author tandk
 * @since 2019-05-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Floor", description = "楼层")
public class Floor extends Model<Floor> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "楼层主键", example = "0")
  @TableId(value = "floor_id", type = IdType.AUTO)
  private Integer floorId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "楼宇id", example = "0")
  private Integer buildingId;

  @ApiModelProperty(value = "楼层", example = "0")
  private String floorNum;

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
    return this.floorId;
  }

  /**
   * 园区id
   */
  private transient String parkId;

  /**
   * 园区名称
   */
  private transient String parkName;

  /**
   * 楼宇名称
   */
  private transient String buildingName;
}
