package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constants.CommonConstants;
import com.wafersystems.virsical.map.entity.Floor;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 楼层测试类
 *
 * @author tandk
 * @date 2019/5/16 15:54
 */
@Rollback
public class FloorControllerTest extends BaseControllerTest {

  @Test
  public void add() throws Exception {
    String url = "/floor/add";
    Floor floor = new Floor();
    floor.setBuildingId(1);
    floor.setFloorNum("100F");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void addForFail() throws Exception {
    String url = "/floor/add";
    Floor floor = new Floor();
    floor.setBuildingId(1);
    floor.setFloorNum("1F");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void list() throws Exception {
    String url = "/floor/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/floor/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/floor/page?floorName=楼层";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/floor/update";
    Floor floor = new Floor();
    floor.setFloorId(1);
    floor.setBuildingId(1);
    floor.setFloorNum("修改测试楼层1");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateForFail() throws Exception {
    String url = "/floor/update";
    Floor floor = new Floor();
    floor.setFloorId(0);
    floor.setBuildingId(1);
    floor.setFloorNum("修改测试楼层1");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void updateForExistFail() throws Exception {
    String url = "/floor/update";
    Floor floor = new Floor();
    floor.setFloorId(2);
    floor.setBuildingId(1);
    floor.setFloorNum("1F");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void delete() throws Exception {
    String url = "/floor/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/floor/delete/0";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
