package com.wafersystems.virsical.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger配置
 *
 * @author tandk
 * @date 2019/4/30 13:40
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /**
   * swagger配置
   *
   * @return Docket
   */
  @Bean
  public Docket createRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
      .apiInfo(apiInfo())
      .select()
      //swagger要扫描的包路径
      .apis(RequestHandlerSelectors.basePackage("com.wafersystems.virsical.map"))
      .paths(PathSelectors.any())
      .build();
  }

  /**
   * api信息
   *
   * @return ApiInfo
   */
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("地图服务")
      .description("公共模块地图服务")
      .version("1.0")
      .build();
  }
}
