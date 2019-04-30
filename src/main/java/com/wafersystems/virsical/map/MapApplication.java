package com.wafersystems.virsical.map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * SpringBoot启动类
 *
 * @author tandk
 */
@SpringBootApplication
@MapperScan("com.wafersystems.virsical.*.mapper")
@ServletComponentScan(basePackages = {"com.wafersystems.virsical.filter"})
public class MapApplication {

  public static void main(String[] args) {
    SpringApplication.run(MapApplication.class, args);
  }
}
