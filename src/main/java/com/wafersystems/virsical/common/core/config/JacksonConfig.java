package com.wafersystems.virsical.common.core.config;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wafersystems.virsical.common.core.jackson.CustomJavaTimeModule;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;

/**
 * JacksonConfig
 *
 * @author tandk
 * @date 2018/10/22
 */
@Configuration
@ConditionalOnClass(ObjectMapper.class)
@AutoConfigureBefore(JacksonAutoConfiguration.class)
public class JacksonConfig {

  /**
   * Jackson to Object映射自定义生成器配置
   *
   * @return Jackson2ObjectMapperBuilderCustomizer
   */
  @Bean
  public Jackson2ObjectMapperBuilderCustomizer customizer() {
    return builder -> {
      builder.locale(Locale.CHINA);
      builder.timeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
      builder.simpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
      builder.modules(new CustomJavaTimeModule());
    };
  }
}
