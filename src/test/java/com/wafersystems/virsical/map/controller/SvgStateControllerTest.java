package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.map.BaseTest;
import com.wafersystems.virsical.map.entity.SvgState;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 素材状态测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
public class SvgStateControllerTest extends BaseTest {

  @Test
  @Rollback(false)
  public void add() throws Exception {
    String url = "/svg-state/add";
    SvgState svgState = new SvgState();
    svgState.setSvgId(1);
    svgState.setSvgStateName("测试添加素材状态");
    svgState.setSvgStateCode("meeting-room");
    svgState.setSvgStateElement("test-eee");
    String content = JSON.toJSONString(svgState);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void list() throws Exception {
    String url = "/svg-state/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/svg-state/1/meeting-room";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/svg-state/page";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/svg-state/update";
    SvgState svgState = new SvgState();
    svgState.setSvgStateId(1);
    svgState.setSvgStateCode("meeting-room");
    svgState.setSvgStateName("会议室");
    String content = JSON.toJSONString(svgState);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateForFail() throws Exception {
    String url = "/svg-state/update";
    SvgState svgState = new SvgState();
    svgState.setSvgStateId(0);
    svgState.setSvgStateCode("test-aaa");
    svgState.setSvgStateName("会议室");
    String content = JSON.toJSONString(svgState);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void delete() throws Exception {
    String deleteUrl = "/svg-state/delete/1";
    JSONObject deleteJsonObject = doPost(deleteUrl, null, null);
    Assert.assertEquals(deleteJsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/svg-state/delete/0";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
