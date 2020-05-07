package com.wafersystems.virsical.map.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.dto.MapElementObjectStateVO;
import com.wafersystems.virsical.map.BaseTest;
import com.wafersystems.virsical.map.entity.MapElement;
import com.wafersystems.virsical.map.model.vo.MapElementBindVO;
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
public class MapElementControllerTest extends BaseTest {

  @Test
  public void add() throws Exception {
    String url = "/map-element/add/1/111111111";
    List<MapElement> list = new ArrayList<>();
    MapElement me = new MapElement();
    me.setMapId(1);
    me.setSvgId(1);
    me.setMapWebId("web001");
    me.setObjectId("1");
    me.setMapElementId("01ea73b494b75f59bcd90ba2ee6d6bf4");
    list.add(me);
    MapElement me2 = new MapElement();
    me2.setMapId(2);
    me2.setSvgId(1);
    me2.setMapWebId("web002");
    me2.setObjectId("2");
    me2.setMapElementId("03740626bab2ea5f01106456dafc957e");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

//  @Test
//  public void addForParamErrorFail() throws Exception {
//    String url = "/map-element/add/8";
//    String content = JSON.toJSONString(new ArrayList<>());
//    JSONObject jsonObject = doPost(url, content, null);
//    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
//  }

  @Test
  public void list() throws Exception {
    String url = "/map-element/list?mapId=1";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void bind() throws Exception {
    String url = "/map-element/bind";
    List<MapElementBindVO> list = new ArrayList<>();
    MapElementBindVO me = new MapElementBindVO();
    me.setMapElementId("1");
    me.setObjectId("A001");
    me.setObjectColor("#111111");
    list.add(me);
    MapElementBindVO me2 = new MapElementBindVO();
    me2.setMapElementId("2");
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
    me.setMapElementId("1");
    me.setLineStart("s1");
    me.setLineMid("m1");
    me.setLineEnd("e1");
    list.add(me);
    MapElementRouteVO me2 = new MapElementRouteVO();
    me2.setMapElementId("2");
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
    me.setObjectId("1");
    me.setObjectName("a1");
    me.setObjectColor("#111111");
    me.setObjectSvgStateCode("c1");
    list.add(me);
    MapElementObjectStateVO me2 = new MapElementObjectStateVO();
    me2.setObjectId("2");
    me2.setObjectName("a2");
    me2.setObjectColor("#222222");
    me2.setObjectSvgStateCode("c2");
    list.add(me2);
    String content = JSON.toJSONString(list);
    JSONObject jsonObject = doPost(url, content, null, true, false);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void updateObjectStateForParamErrorFail() throws Exception {
    String url = "/map-element/update-object-state/meeting-room";
    String content = JSON.toJSONString(new ArrayList<>());
    JSONObject jsonObject = doPost(url, content, null, true, false);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }


}
