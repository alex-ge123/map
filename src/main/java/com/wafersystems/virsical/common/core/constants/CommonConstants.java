package com.wafersystems.virsical.common.core.constants;

/**
 * @author tandk
 * @date 2017/10/29
 */
public interface CommonConstants {

  /**
   * 服务监控请求前缀
   */
  String ACTUATOR_PREFIX_URL = "/actuator";

  /**
   * header 中feign标识
   */
  String FEIGN = "FEIGN";

  /**
   * 用户id
   */
  String USER_ID = "user_id";

  /**
   * 用户名
   */
  String USERNAME = "username";

  /**
   * 部门id
   */
  String DEPT_ID = "dept_id";

  /**
   * 租户id
   */
  String TENANT_ID = "tenant_id";

  /**
   * 租户域
   */
  String TENANT_DOMAIN = "tenant_domain";

  /**
   * license信息
   */
  String LICENSE = "license";


  /**
   * 平台管理员最高权限用户id
   */
  Integer PLATFORM_ADMIN_HIGHEST_USERID_ID = 0;

  /**
   * 平台管理员租户id
   */
  Integer PLATFORM_ADMIN_TENANT_ID = 0;

  /**
   * 平台管理员域
   */
  String PLATFORM_ADMIN_DOMAIN = "virsical.com";

  /**
   * 删除
   */
  String STATUS_DEL = "1";
  /**
   * 正常
   */
  String STATUS_NORMAL = "0";

  /**
   * 锁定
   */
  String STATUS_LOCK = "9";

  /**
   * 菜单
   */
  String MENU = "0";

  /**
   * 编码
   */
  String UTF8 = "UTF-8";

  /**
   * JSON 资源
   */
  String CONTENT_TYPE = "application/json; charset=utf-8";

  /**
   * 路由存放
   */
  String ROUTE_KEY = "gateway_route_key";

  /**
   * 用户信息
   */
  String USER_KEY = "user_info-";

  /**
   * spring boot admin 事件key
   */
  String EVENT_KEY = "event_key";

  /**
   * 验证码前缀
   */
  String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

  /**
   * 成功标记
   */
  Integer SUCCESS = 0;
  /**
   * 失败标记
   */
  Integer FAIL = 1;

  /**
   * Email正则表达式
   */
  String EMAIL_REGEX = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";

  /**
   * 字符@
   */
  String ALT = "@";
}
