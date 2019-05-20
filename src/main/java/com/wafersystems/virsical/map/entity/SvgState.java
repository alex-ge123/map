package com.wafersystems.virsical.map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 素材状态
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SvgState", description = "素材状态")
public class SvgState extends Model<SvgState> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "素材状态主键", example = "0")
  @TableId(value = "svg_state_id", type = IdType.AUTO)
  private Integer svgStateId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "素材id", example = "0")
  private Integer svgId;

  @ApiModelProperty(value = "素材状态名称", example = "string")
  private String svgStateName;

  @ApiModelProperty(value = "素材状态标识", example = "string")
  private String svgStateCode;

  @ApiModelProperty(value = "SVG内容", example = "string")
  private String svgStateElement;

  @ApiModelProperty(value = "SVG属性", example = "string")
  private String viewBox;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;


  @Override
  protected Serializable pkVal() {
    return this.svgStateId;
  }

}
