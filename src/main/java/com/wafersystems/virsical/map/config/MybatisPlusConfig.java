package com.wafersystems.virsical.map.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis Plus配置
 *
 * @author tandk
 * @date 2019-4-30
 */
@Configuration
public class MybatisPlusConfig {

  /**
   * 创建租户维护处理器对象
   *
   * @return 处理后的租户维护处理器
   */
  @Bean
  @ConditionalOnMissingBean
  public TenantCustomHandler tenantCustomHandler() {
    return new TenantCustomHandler();
  }

  /**
   * 分页插件
   *
   * @return PaginationInterceptor
   */
  @Bean
  @ConditionalOnMissingBean
  public PaginationInterceptor paginationInterceptor(TenantCustomHandler tenantCustomHandler) {
    PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
    List<ISqlParser> sqlParserList = new ArrayList<>();
    TenantSqlParser tenantSqlParser = new TenantSqlParser();
    tenantSqlParser.setTenantHandler(tenantCustomHandler);
    sqlParserList.add(tenantSqlParser);
    paginationInterceptor.setSqlParserList(sqlParserList);
    return paginationInterceptor;
  }

}
