package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.map.entity.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 地图测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Slf4j
@Rollback
public class MapControllerTest extends BaseControllerTest {

  @Test
  public void add() throws Exception {
    String url = "/map/add";
    Map map = new Map();
    map.setFloorId(100);
    map.setBaseMapElement("test-eee");
    String content = JSON.toJSONString(map);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void list() throws Exception {
    String url = "/map/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/map/1";
    JSONObject jsonObject = doGet(url);
    Map map = JSON.parseObject(jsonObject.get("data").toString(), Map.class);
    log.info(map.toString());
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    String url = "/map/page?parkId=1&buildingId=1&floorNum=1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/map/update";
    Map map = new Map();
    map.setMapId(1);
    map.setBaseMapElement("test-eee-update");
    String content = JSON.toJSONString(map);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateForFail() throws Exception {
    String url = "/map/update";
    Map map = new Map();
    map.setMapId(0);
    map.setBaseMapElement("test-eee-update");
    String content = JSON.toJSONString(map);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void delete() throws Exception {
    String url = "/map/delete/1";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/map/delete/0";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
