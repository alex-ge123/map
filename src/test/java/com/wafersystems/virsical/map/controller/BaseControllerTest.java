package com.wafersystems.virsical.map.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.filter.TenantContextHolderFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

import java.nio.charset.StandardCharsets;

/**
 * 接口测试基类
 *
 * @author tandk
 * @date 2019-5-8
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest extends AbstractTransactionalTestNGSpringContextTests {

  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext wac;

  @Autowired
  private TenantContextHolderFilter tenantContextHolderFilter;

  /**
   * 在测试类中的Test开始运行前执行
   */
  @BeforeClass
  void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(this.tenantContextHolderFilter).build();
  }

  /**
   * post requestBuilder
   *
   * @param url     url
   * @param content 请求内容体
   * @return JSONObject
   * @throws Exception Exception
   */
  JSONObject doMultipartPost(String url, String content, MockMultipartFile mockMultipartFile) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart(url).file(mockMultipartFile);
    return doCall(requestBuilder, content, null);
  }

  /**
   * post requestBuilder
   *
   * @param url     url
   * @param content 请求内容体
   * @param params  参数
   * @return JSONObject
   * @throws Exception Exception
   */
  JSONObject doPost(String url, String content, MultiValueMap<String, String> params) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
    return doCall(requestBuilder, content, params);
  }

  /**
   * get requestBuilder
   *
   * @param url url
   * @return JSONObject JSONObject
   * @throws Exception Exception
   */
  JSONObject doGet(String url) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(url);
    return doCall(requestBuilder, null, null);
  }

  /**
   * put requestBuilder
   *
   * @param url     url
   * @param content content
   * @return JSONObject
   * @throws Exception Exception
   */
  JSONObject doPut(String url, String content) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put(url);
    return doCall(requestBuilder, content, null);
  }

  /**
   * delete requestBuilder
   *
   * @param url url
   * @return JSONObject
   * @throws Exception Exception
   */
  JSONObject doDelete(String url) throws Exception {
    MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete(url);
    return doCall(requestBuilder, null, null);
  }

  /**
   * 设置请求头，发起请求，返回处理结果
   *
   * @param requestBuilder requestBuilder
   * @param content        content
   * @return JSONObject
   * @throws Exception Exception
   */
  private JSONObject doCall(MockHttpServletRequestBuilder requestBuilder, String content, MultiValueMap<String, String> params) throws Exception {
    if (StrUtil.isNotBlank(content)) {
      requestBuilder.contentType(MediaType.APPLICATION_JSON);
      requestBuilder.content(content);
    }
    if (params != null) {
      requestBuilder.params(params);
    }
    // 执行一个请求；
    ResultActions result = mockMvc.perform(requestBuilder);

    // 添加一个结果处理器，表示要对结果做点什么事情，比如此处使用MockMvcResultHandlers.print()输出整个响应结果信息。
    result.andDo(MockMvcResultHandlers.print());
    // 添加执行完成后的断言
    result.andExpect(MockMvcResultMatchers.status().isOk());

    // 表示执行完成后返回相应的结果。
    MvcResult mvcResult = result.andReturn();
    String respContent = mvcResult.getResponse().getContentAsString();
    return JSON.parseObject(respContent);
  }

}
