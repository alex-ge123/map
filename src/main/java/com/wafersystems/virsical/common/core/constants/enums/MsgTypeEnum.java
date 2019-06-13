package com.wafersystems.virsical.common.core.constants.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型
 *
 * @author tandk
 * @date 2019/1/8
 */
@Getter
@AllArgsConstructor
public enum MsgTypeEnum {
  /**
   * 单条
   */
  ONE,

  /**
   * 批量
   */
  BATCH,

  /**
   * 全部
   */
  ALL
}
