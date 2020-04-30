package com.wafersystems.virsical.map.common;

import lombok.experimental.UtilityClass;

/**
 * 地图常量
 *
 * @author tandk
 * @date 2019/4/30 10:13
 */
@UtilityClass
public final class MapConstants {
  /**
   * 默认地图
   */
  public static final Integer MAP_TYPE_DEFAULT = 0;

  /**
   * 蜂鸟地图
   */
  public static final Integer MAP_TYPE_FENG_MAP = 1;

  /**
   * 启用
   */
  public static final Integer OPEN_STATE = 0;

  /**
   * 关闭
   */
  public static final Integer CLOSE_STATE = 1;

  /**
   * 重新加载消息动作
   */
  public static final String ACTION_STATE_RELOAD = "reload";

  /**
   * 状态更新消息动作
   */
  public static final String ACTION_STATE_UPDATE = "state-update";

  /**
   * 引导线消息动作
   */
  public static final String ACTION_GUIDE_LINE = "guide-line";
}
