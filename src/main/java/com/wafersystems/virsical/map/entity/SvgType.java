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
import java.util.List;

/**
 * <p>
 * 素材类型
 * </p>
 *
 * @author tandk
 * @since 2019-05-08
 */
@Setter
@Getter
@ToString
@ApiModel(value = "SvgType", description = "素材类型")
public class SvgType extends Model<SvgType> {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "素材类型标识", example = "string")
  @TableId(value = "svg_type_code", type = IdType.INPUT)
  private String svgTypeCode;

  @ApiModelProperty(value = "素材类型名称", example = "string")
  private String svgTypeName;

  @ApiModelProperty(value = "素材类型状态，0-正常，1-停用", example = "0")
  private Integer svgTypeState;

  @ApiModelProperty(value = "创建时间")
  private LocalDateTime createTime;

  @ApiModelProperty(value = "修改时间")
  private LocalDateTime updateTime;


  @Override
  protected Serializable pkVal() {
    return this.svgTypeCode;
  }

  /**
   * 当前素材类型下的素材
   */
  private transient List<Svg> svgList;
}
