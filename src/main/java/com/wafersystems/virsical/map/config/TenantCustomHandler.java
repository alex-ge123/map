package com.wafersystems.virsical.map.config;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 租户维护处理器
 *
 * @author tandk
 * @date 2019/4/30
 */
@Slf4j
public class TenantCustomHandler implements TenantHandler {
  /**
   * 配置需要进行增强租户sql的表
   */
  @Autowired
  private TenantConfigProperties properties;

  /**
   * 获取租户值
   *
   * @return 租户值
   */
  @Override
  public Expression getTenantId() {
    Integer tenantId = TenantContextHolder.getTenantId();
    log.debug("当前租户为 >> {}", tenantId);
    return new LongValue(tenantId);
  }

  /**
   * 获取租户字段名
   *
   * @return 租户字段名
   */
  @Override
  public String getTenantIdColumn() {
    return properties.getColumn();
  }

  /**
   * 根据表名判断是否进行过滤
   *
   * @param tableName 表名
   * @return 是否进行过滤
   */
  @Override
  public boolean doTableFilter(String tableName) {
    return !properties.getTables().contains(tableName);
  }
}
