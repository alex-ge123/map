package com.wafersystems.virsical.map.config;

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
  private final ThreadLocal<Integer> THREAD_LOCAL_TENANT_ID = new TransmittableThreadLocal<>();

  /**
   * 存放租户域
   */
  private final ThreadLocal<String> THREAD_LOCAL_TENANT_DOMAIN = new TransmittableThreadLocal<>();

  /**
   * 存放用户id
   */
  private final ThreadLocal<Integer> THREAD_LOCAL_USER_ID = new TransmittableThreadLocal<>();

  /**
   * 存放用户名
   */
  private final ThreadLocal<String> THREAD_LOCAL_USERNAME = new TransmittableThreadLocal<>();

  /**
   * 存放部门id
   */
  private final ThreadLocal<Integer> THREAD_LOCAL_DEPT_ID = new TransmittableThreadLocal<>();

  /**
   * 登录类型
   */
  private final ThreadLocal<String> THREAD_LOCAL_LOGIN_TYPE = new TransmittableThreadLocal<>();

  /**
   * 清除TTL中数据
   */
  public void clear() {
    THREAD_LOCAL_TENANT_ID.remove();
    THREAD_LOCAL_TENANT_DOMAIN.remove();
    THREAD_LOCAL_USER_ID.remove();
    THREAD_LOCAL_USERNAME.remove();
    THREAD_LOCAL_DEPT_ID.remove();
    THREAD_LOCAL_LOGIN_TYPE.remove();
  }

  /**
   * TTL 设置租户ID
   *
   * @param tenantId 租户ID
   */
  public void setTenantId(Integer tenantId) {
    THREAD_LOCAL_TENANT_ID.set(tenantId);
  }

  /**
   * 获取TTL中的租户ID
   *
   * @return 租户ID
   */
  public Integer getTenantId() {
    return THREAD_LOCAL_TENANT_ID.get();
  }

  /**
   * 清除TTL中租户id
   */
  public void clearTenantId() {
    THREAD_LOCAL_TENANT_ID.remove();
  }

  /**
   * TTL 设置租户domain
   *
   * @param tenantDomain 租户domain
   */
  public void setTenantDomain(String tenantDomain) {
    THREAD_LOCAL_TENANT_DOMAIN.set(tenantDomain);
  }

  /**
   * 获取TTL中的租户domain
   *
   * @return 租户domain
   */
  public String getTenantDomain() {
    return THREAD_LOCAL_TENANT_DOMAIN.get();
  }

  /**
   * 清除TTL中租户domain
   */
  public void clearTenantDomain() {
    THREAD_LOCAL_TENANT_DOMAIN.remove();
  }

  /**
   * TTL 设置用户id
   *
   * @param userId 用户id
   */
  public void setUserId(Integer userId) {
    THREAD_LOCAL_USER_ID.set(userId);
  }

  /**
   * 获取TTL中的用户id
   *
   * @return 用户id
   */
  public Integer getUserId() {
    return THREAD_LOCAL_USER_ID.get();
  }

  /**
   * 清除TTL中用户id
   */
  public void clearUserId() {
    THREAD_LOCAL_USER_ID.remove();
  }

  /**
   * TTL 设置用户名
   *
   * @param username 用户名
   */
  public void setUsername(String username) {
    THREAD_LOCAL_USERNAME.set(username);
  }

  /**
   * 获取TTL中的用户名
   *
   * @return 用户名
   */
  public String getUsername() {
    return THREAD_LOCAL_USERNAME.get();
  }

  /**
   * 清除TTL中用户名
   */
  public void clearUsername() {
    THREAD_LOCAL_USERNAME.remove();
  }

  /**
   * TTL 设置部门id
   *
   * @param deptId 部门id
   */
  public void setDeptId(Integer deptId) {
    THREAD_LOCAL_DEPT_ID.set(deptId);
  }

  /**
   * 获取TTL中的部门id
   *
   * @return 部门id
   */
  public Integer getDeptId() {
    return THREAD_LOCAL_DEPT_ID.get();
  }

  /**
   * 清除TTL中部门id
   */
  public void clearDeptId() {
    THREAD_LOCAL_DEPT_ID.remove();
  }


  /**
   * TTL 设置登录类型
   *
   * @param loginType loginType
   */
  public void setLoginType(String loginType) {
    THREAD_LOCAL_LOGIN_TYPE.set(loginType);
  }

  /**
   * 获取TTL中的登录类型
   *
   * @return LoginType
   */
  public String getLoginType() {
    return THREAD_LOCAL_LOGIN_TYPE.get();
  }

  /**
   * 清除TTL中登录类型
   */
  public void clearLoginType() {
    THREAD_LOCAL_LOGIN_TYPE.remove();
  }
}
