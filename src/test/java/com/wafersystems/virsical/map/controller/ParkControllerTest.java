package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constants.CommonConstants;
import com.wafersystems.virsical.map.entity.Park;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 园区测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Rollback
public class ParkControllerTest extends BaseControllerTest {

  @Test(priority = 1, groups = {"park"})
  public void add() throws Exception {
    String url = "/park/add";
    Park park = new Park();
    park.setParkName("测试添加园区");
    String content = JSON.toJSONString(park);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 2, groups = {"park"})
  public void list() throws Exception {
    String url = "/park/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 3, groups = {"park"})
  public void get() throws Exception {
    String url = "/park/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 4, groups = {"park"})
  public void page() throws Exception {
    String url = "/park/page?parkName=园区";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 5, groups = {"park"})
  public void parkBuildingFloor() throws Exception {
    String url = "/park/building/floor";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 6, groups = {"park"})
  public void update() throws Exception {
    String url = "/park/update";
    Park park = new Park();
    park.setParkId(1);
    park.setParkName("测试修改园区");
    String content = JSON.toJSONString(park);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 7, groups = {"park"})
  public void delete() throws Exception {
    String url = "/park/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }


}
