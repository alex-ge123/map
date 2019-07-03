package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
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

  @Test
  public void add() throws Exception {
    String url = "/building/add";
    Building building = new Building();
    building.setParkId(1);
    building.setBuildingName("测试添加楼宇");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void addForFail() throws Exception {
    String url = "/building/add";
    Building building = new Building();
    building.setParkId(1);
    building.setBuildingName("1号楼");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
    Assert.assertEquals(jsonObject.get("msg"), MapMsgConstants.BUILDING_HAS_EXIST);
  }

  @Test
  public void addForFail2() throws Exception {
    String url = "/building/add";
    Building building = new Building();
    building.setParkId(1);
    building.setBuildingName("123号楼");
    building.setBuildingToken("123");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
    Assert.assertEquals(jsonObject.get("msg"), MapMsgConstants.BUILDING_TOKEN_HAS_EXIST);
  }

  @Test
  public void list() throws Exception {
    String url = "/building/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/building/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/building/page?buildingName=楼宇";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/building/update";
    Building building = new Building();
    building.setBuildingId(1);
    building.setBuildingName("测试修改楼宇");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateForFail() throws Exception {
    String url = "/building/update";
    Building building = new Building();
    building.setBuildingId(0);
    building.setBuildingName("测试修改楼宇");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void updateForFail2() throws Exception {
    String url = "/building/update";
    Building building = new Building();
    building.setBuildingId(1);
    building.setBuildingName("2号楼");
    building.setParkId(1);
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
    Assert.assertEquals(jsonObject.get("msg"), MapMsgConstants.BUILDING_HAS_EXIST);
  }

  @Test
  public void updateForFail3() throws Exception {
    String url = "/building/update";
    Building building = new Building();
    building.setBuildingId(2);
    building.setBuildingName("111号楼");
    building.setBuildingToken("123");
    String content = JSON.toJSONString(building);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
    Assert.assertEquals(jsonObject.get("msg"), MapMsgConstants.BUILDING_TOKEN_HAS_EXIST);
  }

  @Test
  public void delete() throws Exception {
    String url = "/building/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
    Assert.assertEquals(jsonObject.get("msg"), MapMsgConstants.THIS_BUILDING_HAS_FLOOR);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/building/delete/0";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }
}
