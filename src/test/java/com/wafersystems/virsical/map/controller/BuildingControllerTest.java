package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constants.CommonConstants;
import com.wafersystems.virsical.map.entity.Building;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 楼宇测试类
 *
 * @author tandk
 * @date 2019/5/16 15:54
 */
@Rollback
public class BuildingControllerTest extends BaseControllerTest {

  @Test(priority = 1, groups = {"building"})
  public void add() throws Exception {
    String url = "/building/add";
    Building building = new Building();
    building.setParkId(1);
    building.setBuildingName("测试添加楼宇");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 2, groups = {"building"})
  public void list() throws Exception {
    String url = "/building/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 3, groups = {"building"})
  public void get() throws Exception {
    String url = "/building/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 4, groups = {"building"})
  public void page() throws Exception {
    String url = "/building/page?buildingName=楼宇";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 6, groups = {"building"})
  public void update() throws Exception {
    String url = "/building/update";
    Building building = new Building();
    building.setBuildingId(1);
    building.setBuildingName("测试修改楼宇");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 7, groups = {"building"})
  public void delete() throws Exception {
    String url = "/building/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }


}
