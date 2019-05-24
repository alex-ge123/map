package com.wafersystems.virsical.map.common;

import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;

/**
 * @author tandk
 * @date 2019/4/30 11:21
 */
public class BaseController {

  /**
   * 获取租户id
   *
   * @return 租户id
   */
  public Integer getTenantId() {
    return TenantContextHolder.getTenantId();
  }
}
