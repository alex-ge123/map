package com.wafersystems.virsical.map.common;

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
 * </p>
 *
 * @author tandk
 * @date 2019/4/30 10:13
 */
public interface MapMsgConstants {
  /**
   * 同一楼下楼层号不能重复
   */
  String FLOOR_NUM_NO_REPEAT = "104001";

  /**
   * 同一素材下素材状态标识不能重复
   */
  String SVG_STATE_NO_REPEAT = "104002";

  /**
   * SVG文件不能为空
   */
  String SVG_FILE_NO_NULL = "104003";

  /**
   * SVG文件解析异常
   */
  String SVG_FILE_PARSE_EXCEPTION = "104004";

  /**
   * 地图元素不能为空
   */
  String MAP_ELEMENT_NO_NULL = "104005";

  /**
   * 已存在此园区名称
   */
  String PARK_NAME_NO_REPEAT = "104006";

  /**
   * 当前园区下，楼宇名称已存在
   */
  String BUILDING_HAS_EXIST = "104007";

  /**
   * 一体机编号不能重复
   */
  String BUILDING_TOKEN_HAS_EXIST = "104008";

  /**
   * 当前园区存在楼宇，请先删除楼宇
   */
  String THIS_PARK_HAS_BUILDING = "104009";

  /**
   * 当前楼宇存在楼层，请先删除楼层
   */
  String THIS_BUILDING_HAS_FLOOR = "104010";

  /**
   * 当前楼层存在对应地图，请先删除地图
   */
  String THIS_FLOOR_HAS_MAP = "104011";
}
