package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.map.BaseTest;
import com.wafersystems.virsical.map.entity.SvgType;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 素材分类测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Rollback
public class SvgTypeControllerTest extends BaseTest {

  @Test
  public void add() throws Exception {
    String url = "/svg-type/add";
    SvgType svgType = new SvgType();
    svgType.setSvgTypeCode("test-svg-type");
    svgType.setSvgTypeName("测试添加素材分类");
    String content = JSON.toJSONString(svgType);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void addForFail() throws Exception {
    String url = "/svg-type/add";
    SvgType svgType = new SvgType();
    svgType.setSvgTypeCode("meeting-room");
    svgType.setSvgTypeName("测试添加素材分类");
    String content = JSON.toJSONString(svgType);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void list() throws Exception {
    String url = "/svg-type/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/svg-type/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/svg-type/page?svgTypeName=素材分类";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/svg-type/update";
    SvgType svgType = new SvgType();
    svgType.setSvgTypeCode("meeting-room");
    svgType.setSvgTypeName("会议室");
    String content = JSON.toJSONString(svgType);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void delete() throws Exception {
    String url = "/svg-type/delete/meeting-room";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/svg-type/delete/aaa";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }
}
