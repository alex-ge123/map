package com.wafersystems.virsical.map.common;

import lombok.experimental.UtilityClass;

/**
 * 接口返回提示信息编号，用于前端国际化
 * <p>
 * 104001: 同一楼下楼层号不能重复
 * 104002: 同一素材下素材状态标识不能重复
 * 104003: SVG文件不能为空
 * 104004: SVG文件解析异常
 * 104005: 地图元素不能为空
 * 104006: 已存在此园区名称
 * 104007: 当前园区下，楼宇名称已存在
 * 104008: 一体机编号不能重复
 * 104009: 当前园区存在楼宇，请先删除楼宇
 * 104010: 当前楼宇存在楼层，请先删除楼层
 * 104011: 当前楼层存在对应地图，请先删除地图
 * 104012: 查询地图失败
 * 104013: 查询空间叶子节点为空
 * 104014: 素材已被使用，不能删除
 * 104015: 素材类型标识不能重复
 * </p>
 *
 * @author tandk
 * @date 2019/4/30 10:13
 */
@UtilityClass
public final class MapMsgConstants {
  /**
   * 非法操作
   */
  public static final String ILLEGAL_OPT = "104000";

  /**
   * 参数不正确
   */
  public static final String PARAM_ERROR = "104001";

  /**
   * 同一素材下素材状态标识不能重复
   */
  public static final String SVG_STATE_NO_REPEAT = "104002";

  /**
   * SVG文件不能为空
   */
  public static final String SVG_FILE_NO_NULL = "104003";

  /**
   * SVG文件解析异常
   */
  public static final String SVG_FILE_PARSE_EXCEPTION = "104004";

  /**
   * 地图元素不能为空
   */
  public static final String MAP_ELEMENT_NO_NULL = "104005";

  /**
   * 已存在此园区名称
   */
  public static final String PARK_NAME_NO_REPEAT = "104006";

  /**
   * 当前园区下，楼宇名称已存在
   */
  public static final String BUILDING_HAS_EXIST = "104007";

  /**
   * 一体机编号不能重复
   */
  public static final String BUILDING_TOKEN_HAS_EXIST = "104008";

  /**
   * 当前园区存在楼宇，请先删除楼宇
   */
  public static final String THIS_PARK_HAS_BUILDING = "104009";

  /**
   * 当前楼宇存在楼层，请先删除楼层
   */
  public static final String THIS_BUILDING_HAS_FLOOR = "104010";

  /**
   * 当前楼层存在对应地图，请先删除地图
   */
  public static final String THIS_FLOOR_HAS_MAP = "104011";

  /**
   * 查询地图失败
   */
  public static final String QUERY_MAP_FAILED = "104012";

  /**
   * 查询空间叶子节点为空
   */
  public static final String QUERY_LEAFNODE_NULL = "104013";

  /**
   * 素材已被使用，不能删除
   */
  public static final String MATERIAL_USED = "104014";

  /**
   * 素材类型标识不能重复
   */
  public static final String MATERIAL_TYPE_REPETITION = "104015";

  /**
   * 此地图当前正在编辑，请{}秒后重试
   */
  public static final String MAP_EDIT_PERMISSION = "104016";

  /**
   * 地图素材上传数量已达到限制
   */
  public static final String MAP_SVG_UPLOAD_LIMIT_TIP = "104017";
}
