package com.wafersystems.virsical;

import com.wafersystems.virsical.common.security.annotation.EnableCustomFeignClients;
import com.wafersystems.virsical.common.security.annotation.EnableCustomResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * SpringBoot启动类
 *
 * @author tandk
 */
@SpringCloudApplication
@EnableCustomFeignClients
@EnableCustomResourceServer
@MapperScan("com.wafersystems.virsical.*.mapper")
public class MapApplication {

  public static void main(String[] args) {
    SpringApplication.run(MapApplication.class, args);
  }
}
