package com.wafersystems.virsical.map.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * 默认配置
 *
 * @author tandk
 * @date 2019/8/15 17:04
 */
@Getter
@Setter
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "push")
public class PushProperties {
  /**
   * 推送开关
   */
  private boolean enable;

  /**
   * 通道名称
   */
  private String destination;
}
