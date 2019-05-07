package com.wafersystems.virsical;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SpringBoot启动类
 *
 * @author tandk
 */
@SpringBootApplication
@MapperScan("com.wafersystems.virsical.*.mapper")
public class MapApplication {

  public static void main(String[] args) {
    SpringApplication.run(MapApplication.class, args);
  }
}
