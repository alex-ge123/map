package com.wafersystems.virsical.map.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 地图配置
 *
 * @author tandk
 * @date 2019/8/15 17:04
 */
@Getter
@Setter
@Configuration
@RefreshScope
@ConfigurationProperties(prefix = "map")
public class MapProperties {
  /**
   * svg上传数量限制
   */
  private int svgUploadLimitAmount;

  /**
   * 地图首页根据类型查询项
   */
  private List<String> indexSearchSvgTypeCode;

}
