package com.wafersystems.virsical.map.common;

/**
 * 接口返回提示信息编号，用于前端国际化
 *
 * @author tandk
 * @date 2019/4/30 10:13
 */
public interface MsgConstants {
  /**
   * 同一楼下楼层号不能重复
   */
  String FLOOR_NUM_NO_REPEAT = "2001";

  /**
   * 同一素材下素材状态标识不能重复
   */
  String SVG_STATE_NO_REPEAT = "2002";

  /**
   * SVG文件不能为空
   */
  String SVG_FILE_NO_NULL = "2003";

  /**
   * SVG文件解析异常
   */
  String SVG_FILE_PARSE_EXCEPTION = "2004";

  /**
   * 地图元素不能为空
   */
  String MAP_ELEMENT_NO_NULL = "2005";
}
