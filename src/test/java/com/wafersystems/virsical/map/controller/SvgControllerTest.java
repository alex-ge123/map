package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.map.BaseTest;
import com.wafersystems.virsical.map.entity.Svg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.charset.StandardCharsets;

/**
 * 素材测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Slf4j
@Rollback
public class SvgControllerTest extends BaseTest {

  @Test
  public void add() throws Exception {
    String url = "/svg/add";
    Svg svg = new Svg();
    svg.setSvgTypeCode("meeting-room");
    svg.setSvgName("测试添加素材");
    svg.setSvgWidth("100");
    svg.setSvgHeight("100");
    svg.setSvgElement("test-eee");
    svg.setDirection(888);
    svg.setState(0);
    svg.setFontSize(12);
    String content = JSON.toJSONString(svg);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void parse() throws Exception {
    String url = "/svg/parse";
    String svgElement = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"40\" height=\"40\" viewBox=\"0 0 40 40\">\n" +
      "      <rect x=\"0.25\" y=\"0.25\" width=\"40\" height=\"40\"/>\n" +
      "      <rect fill=\"#ccc\" x=\"37.44\" y=\"12.75\" width=\"1.25\" height=\"15\"/>\n" +
      "    </svg>";
    MockMultipartFile mockMultipartFile = new MockMultipartFile("svgFile", "test.svg", ",multipart/form-data", svgElement.getBytes(StandardCharsets.UTF_8));
    JSONObject jsonObject = doMultipartPost(url, null, mockMultipartFile);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void parseForFail() throws Exception {
    String url = "/svg/parse";
    MockMultipartFile mockMultipartFile = new MockMultipartFile("svgFile", "test.svg", ",multipart/form-data", "abc".getBytes());
    JSONObject jsonObject = doMultipartPost(url, null, mockMultipartFile);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void enableList() throws Exception {
    String url = "/svg/enable-list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void list() throws Exception {
    String url = "/svg/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/svg/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/svg/page?svgName=素材";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void group() throws Exception {
    String url = "/svg/group";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/svg/update";
    Svg svg = new Svg();
    svg.setSvgId(1);
    svg.setSvgTypeCode("meeting-room");
    svg.setSvgName("测试修改素材");
    svg.setSvgWidth("100");
    svg.setSvgHeight("100");
    svg.setSvgElement("test-eee");
    String content = JSON.toJSONString(svg);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateForFail() throws Exception {
    String url = "/svg/update";
    Svg svg = new Svg();
    svg.setSvgId(0);
    svg.setSvgTypeCode("meeting-room");
    svg.setSvgName("测试修改素材");
    svg.setSvgWidth("100");
    svg.setSvgHeight("100");
    svg.setSvgElement("test-eee");
    String content = JSON.toJSONString(svg);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void delete() throws Exception {
    String url = "/svg/delete/3";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/svg/delete/100";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
