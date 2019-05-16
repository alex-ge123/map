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

  @Test(priority = 1, groups = {"floor"})
  public void add() throws Exception {
    String url = "/floor/add";
    Floor floor = new Floor();
    floor.setBuildingId(1);
    floor.setFloorNum("测试楼层1");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 2, groups = {"floor"})
  public void list() throws Exception {
    String url = "/floor/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 3, groups = {"floor"})
  public void get() throws Exception {
    String url = "/floor/1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 4, groups = {"floor"})
  public void page() throws Exception {
    String url = "/floor/page?floorName=楼层";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 6, groups = {"floor"})
  public void update() throws Exception {
    String url = "/floor/update";
    Floor floor = new Floor();
    floor.setFloorId(1);
    floor.setFloorNum("修改测试楼层1");
    String content = JSON.toJSONString(floor);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test(priority = 7, groups = {"floor"})
  public void delete() throws Exception {
    String url = "/floor/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }


}
