package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constants.CommonConstants;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.model.vo.MapElementBindVO;
import com.wafersystems.virsical.map.model.vo.MapElementObjectStateVO;
import com.wafersystems.virsical.map.model.vo.MapElementRouteVO;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 地图元素测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Rollback
public class MapElementControllerTest extends BaseControllerTest {

  @Test
  public void add() throws Exception {
    String url = "/map-element/add";
    List<MapElement> list = new ArrayList<>();
    MapElement me = new MapElement();
    me.setMapId(1);
    me.setSvgId(1);
    me.setMapWebId("web001");
    list.add(me);
    MapElement me2 = new MapElement();
    me2.setMapId(1);
    me2.setSvgId(1);
    me2.setMapWebId("web002");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void addForParamErrorFail() throws Exception {
    String url = "/map-element/add";
    String content = JSON.toJSONString(new ArrayList<>());
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void list() throws Exception {
    String url = "/map-element/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void bind() throws Exception {
    String url = "/map-element/bind";
    List<MapElementBindVO> list = new ArrayList<>();
    MapElementBindVO me = new MapElementBindVO();
    me.setMapElementId(1);
    me.setObjectId("A001");
    me.setObjectColor("#111111");
    list.add(me);
    MapElementBindVO me2 = new MapElementBindVO();
    me2.setMapElementId(2);
    me2.setObjectId("A002");
    me2.setObjectColor("#222222");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void bindForParamErrorFail() throws Exception {
    String url = "/map-element/bind";
    String content = JSON.toJSONString(new ArrayList<>());
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void route() throws Exception {
    String url = "/map-element/route";
    List<MapElementRouteVO> list = new ArrayList<>();
    MapElementRouteVO me = new MapElementRouteVO();
    me.setMapElementId(1);
    me.setLineStart("s1");
    me.setLineMid("m1");
    me.setLineEnd("e1");
    list.add(me);
    MapElementRouteVO me2 = new MapElementRouteVO();
    me2.setMapElementId(2);
    me2.setLineStart("s2");
    me2.setLineMid("m2");
    me2.setLineEnd("e2");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void routeForParamErrorFail() throws Exception {
    String url = "/map-element/route";
    String content = JSON.toJSONString(new ArrayList<>());
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void updateObjectState() throws Exception {
    String url = "/map-element/update-object-state/meeting-room";
    List<MapElementObjectStateVO> list = new ArrayList<>();
    MapElementObjectStateVO me = new MapElementObjectStateVO();
    me.setObjectId("A1001");
    me.setObjectName("a1");
    me.setObjectColor("#111111");
    me.setObjectSvgStateCode("c1");
    list.add(me);
    MapElementObjectStateVO me2 = new MapElementObjectStateVO();
    me2.setObjectId("A1002");
    me2.setObjectName("a2");
    me2.setObjectColor("#222222");
    me2.setObjectSvgStateCode("c2");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateObjectStateForParamErrorFail() throws Exception {
    String url = "/map-element/update-object-state/meeting-room";
    String content = JSON.toJSONString(new ArrayList<>());
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
