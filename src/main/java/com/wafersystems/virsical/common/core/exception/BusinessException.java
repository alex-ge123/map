package com.wafersystems.virsical.common.core.exception;

import lombok.NoArgsConstructor;

/**
 * 业务异常
 *
 * @author tandk
 * @date 2019-04-30
 */
@NoArgsConstructor
public class BusinessException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public BusinessException(String message) {
    super(message);
  }
}
