package com.wafersystems.virsical.map.model;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * TTL工具类
 *
 * @author tandk
 * @date 2019/2/20
 */
@UtilityClass
public class TestCaseThreadLocal {

  /**
   * 上下文
   */
  private final ThreadLocal<Map<String, Object>> context = new TransmittableThreadLocal<>();

  /**
   * 清除TTL中数据
   */
  public void clear() {
    context.remove();
  }

  /**
   * TTL 设置context
   *
   * @param context context
   */
  public void setContext(Map<String, Object> context) {
    TestCaseThreadLocal.context.set(context);
  }

  /**
   * 获取TTL中的context
   *
   * @return LoginType
   */
  public Map<String, Object> getContext() {
    return context.get();
  }

  /**
   * context设置值
   *
   * @param key   key
   * @param value value
   */
  public void putContext(String key, Object value) {
    Map<String, Object> contextMap = getContext();
    if (ObjectUtil.isNull(contextMap)) {
      contextMap = new HashMap<>(5);
      setContext(contextMap);
    }
    contextMap.put(key, value);
  }

  /**
   * context获取值
   *
   * @return Object
   */
  public Object getContextValue(String key) {
    final Map<String, Object> map = getContext();
    if (ObjectUtil.isNull(map)) {
      return null;
    }
    return map.get(key);
  }

  /**
   * 清除TTL中context
   */
  public void clearContext() {
    context.remove();
  }
}
