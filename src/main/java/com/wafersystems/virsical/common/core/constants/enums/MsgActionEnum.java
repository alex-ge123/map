package com.wafersystems.virsical.common.core.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息动作
 *
 * @author tandk
 * @date 2019/1/8
 */
@Getter
@AllArgsConstructor
public enum MsgActionEnum {
  /**
   * 新增
   */
  ADD,

  /**
   * 修改
   */
  UPDATE,

  /**
   * 删除
   */
  DELETE,

  /**
   * 展示
   */
  SHOW,

  /**
   * 无
   */
  NONE
}
