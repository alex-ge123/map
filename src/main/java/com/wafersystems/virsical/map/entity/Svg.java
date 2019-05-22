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
 * 素材
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Svg", description = "素材")
public class Svg extends Model<Svg> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "素材主键", example = "0")
  @TableId(value = "svg_id", type = IdType.AUTO)
  private Integer svgId;

  @ApiModelProperty(value = "租户id", example = "0")
  private Integer tenantId;

  @ApiModelProperty(value = "素材类型标识", example = "string")
  private String svgTypeCode;

  @ApiModelProperty(value = "素材名称", example = "string")
  private String svgName;

  @ApiModelProperty(value = "图像宽度", example = "string")
  private String svgWidth;

  @ApiModelProperty(value = "图像高度", example = "string")
  private String svgHeight;

  @ApiModelProperty(value = "SVG内容", example = "string")
  private String svgElement;

  @ApiModelProperty(value = "SVG属性", example = "string")
  private String viewBox;

  @ApiModelProperty(value = "文字坐标轴X", example = "string")
  private String axisx;

  @ApiModelProperty(value = "文字坐标轴Y", example = "string")
  private String axixy;

  @ApiModelProperty(value = "启用状态：0—启用，1—停用", example = "0")
  private Integer state;

  @ApiModelProperty(value = "字体大小", example = "0")
  private Integer fontSize;

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
    return this.svgId;
  }

  /**
   * 素材分类名称
   */
  private transient String svgTypeName;

}
