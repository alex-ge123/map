package com.wafersystems.virsical.common.core.tenant;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * TTL工具类
 *
 * @author tandk
 * @date 2019/4/30
 */
@UtilityClass
public class TenantContextHolder {

  /**
   * 存放租户id
   */
  private final ThreadLocal<Integer> tenantId = new TransmittableThreadLocal<>();


  /**
   * 清除TTL中数据
   */
  public void clear() {
    tenantId.remove();
  }

  /**
   * TTL 设置租户ID
   *
   * @param tenantId 租户ID
   */
  public void setTenantId(Integer tenantId) {
    TenantContextHolder.tenantId.set(tenantId);
  }

  /**
   * 获取TTL中的租户ID
   *
   * @return 租户ID
   */
  public Integer getTenantId() {
    return tenantId.get();
  }

}
