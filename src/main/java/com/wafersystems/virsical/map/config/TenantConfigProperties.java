package com.wafersystems.virsical.map.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author tandk
 * @date 2019-4-30
 */
@Configuration
@ConfigurationProperties(prefix = "tenant")
public class TenantConfigProperties {

  /**
   * 多租户的数据表集合
   */
  private List<String> tables = new ArrayList<>();

  /**
   * 维护租户列名称
   */
  public String getColumn() {
    return "tenant_id";
  }

  public List<String> getTables() {
    return tables;
  }
}
