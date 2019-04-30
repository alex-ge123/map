package com.wafersystems.virsical.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.wafersystems.virsical.common.core.constants.CommonConstants;
import com.wafersystems.virsical.map.config.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局过滤器，打印请求日志，最终清除TTL中信息
 *
 * @author tandk
 * @date 2019/1/25
 */
@Slf4j
@Component
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
          throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    String url = request.getServletPath();
    // 过滤服务监控请求
    if (url.startsWith(CommonConstants.ACTUATOR_PREFIX_URL)) {
      filterChain.doFilter(request, response);
      return;
    }

    String tenantId = "1";//request.getHeader(CommonConstants.TENANT_ID);
    if (StrUtil.isNotBlank(tenantId)) {
      TenantContextHolder.setTenantId(Integer.parseInt(tenantId));
    }

    log.info("【{}】【{}】【{}】【{}】", request.getMethod(), url, tenantId, JSONUtil.toJsonStr(request.getParameterMap()));
    filterChain.doFilter(request, response);
    TenantContextHolder.clear();
  }
}
