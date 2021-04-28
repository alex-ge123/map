package com.wafersystems.virsical.map.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wafersystems.virsical.common.core.constant.CommonCacheConstants;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.constant.MapMqConstants;
import com.wafersystems.virsical.common.core.constant.UpmsMqConstants;
import com.wafersystems.virsical.common.core.constant.enums.MsgActionEnum;
import com.wafersystems.virsical.common.core.constant.enums.MsgTypeEnum;
import com.wafersystems.virsical.common.core.dto.MapElementObjectStateVO;
import com.wafersystems.virsical.common.core.dto.MapElementUpdateDTO;
import com.wafersystems.virsical.common.core.dto.MessageDTO;
import com.wafersystems.virsical.common.core.dto.Page;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.entity.SysSpace;
import com.wafersystems.virsical.common.entity.UserVO;
import com.wafersystems.virsical.common.feign.fallback.RemoteSpaceServiceFallbackImpl;
import com.wafersystems.virsical.map.BaseTest;
import com.wafersystems.virsical.map.entity.Map;
import com.wafersystems.virsical.map.entity.MapElement;
import lombok.extern.slf4j.Slf4j;
import org.mockito.Mockito;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 地图测试类
 *
 * @author tandk
 * @date 2019/5/15 15:54
 */
@Slf4j
@Rollback
public class MapControllerTest extends BaseTest {
  @Autowired
  StringRedisTemplate stringRedisTemplate;

  @Autowired
  AmqpTemplate amqpTemplate;

  @MockBean
  RemoteSpaceServiceFallbackImpl remoteSpaceServiceFallback;

  private Map map = new Map();
  private Map map2 = new Map();
  private Map map3 = new Map();

  @BeforeClass
  public void initData() {
    TenantContextHolder.setTenantId(1);
    map.setFloorId(1);
    map.setDelFlag(0);
    map.insert();
    map2.setFloorId(2);
    map2.setDelFlag(0);
    map2.insert();
    map3.setFloorId(3);
    map3.setDelFlag(0);
    map3.insert();
  }

  @AfterClass
  public void cleanData() {
    TenantContextHolder.setTenantId(1);
    map.deleteById();
  }

  @Test
  public void add() throws Exception {
    String url = "/map/add/123456";
    Map m = new Map();
    m.setFloorId(100);
    m.setBaseMapElement("test-eee");
    String content = JSON.toJSONString(m);
    JSONObject jsonObject = doPost(url, content, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);

    String urlUpdate = "/map/add/123456";
    Map m1 = new Map();
    m1.setMapId(1);
    m1.setFloorId(1);
    m1.setViewBox("aaa");
    String contentUpdate = JSON.toJSONString(m1);
    JSONObject jsonObjectUpdate = doPost(urlUpdate, contentUpdate, null);
    Assert.assertEquals(jsonObjectUpdate.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void list() throws Exception {
    String url = "/map/list";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void get() throws Exception {
    String url = "/map/" + map.getMapId();
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void page() throws Exception {
    Page<SysSpace> page = new Page<>();
    SysSpace space = new SysSpace();
    space.setSpaceId(1);
    space.setName("test");
    space.setPathName("-1-");
    space.setParentId(0);
    page.setRecords(Arrays.asList(space));
    page.setTotal(1);
    Mockito.when(remoteSpaceServiceFallback.getNodePage(Mockito.anyLong(), Mockito.anyLong(), Mockito.any()))
      .thenReturn(R.ok(page));

    String url = "/map/page";
    JSONObject jsonObject = doGet(url);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void update() throws Exception {
    String url = "/map/update/123456";
    Map map = new Map();
    map.setMapId(map2.getMapId());
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
    String url = "/map/delete/" + map3.getMapId();
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void deleteForFail() throws Exception {
    String url = "/map/delete/0";
    JSONObject jsonObject = doPost(url, null, null);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void getEditPermission() throws Exception {
    String url = "/map/getEditPermission";
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("mapId", "1");
    params.add("key", "123456");
    JSONObject jsonObject = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> paramsFail = new LinkedMultiValueMap<>();
    paramsFail.add("mapId", "1");
    paramsFail.add("key", "111111");
    JSONObject jsonObjectFail = doGet(url, false, false, paramsFail);
    Assert.assertEquals(jsonObjectFail.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void getByMapIdOrSpaceId() throws Exception {
    String url = "/map/getByMapIdOrSpaceId/";
    JSONObject jsonObjectFail = doGet(url, false, false);
    Assert.assertEquals(jsonObjectFail.get("code"), CommonConstants.FAIL);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("mapId", "1");
    JSONObject jsonObject = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> params1 = new LinkedMultiValueMap<>();
    params1.add("spaceId", "1");
    JSONObject jsonObject1 = doGet(url, false, false, params1);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void getBySpaceId() throws Exception {
    String url = "/map/getBySpaceId/1";
    JSONObject jsonObjectFail = doGet(url, false, false);
    Assert.assertEquals(jsonObjectFail.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void listBySpaceIds() throws Exception {
    String url = "/map/listBySpaceIds";
    JSONObject jsonObject = doGet(url, false, false);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("spaceIds", "1");
    JSONObject jsonObject1 = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void forceGetEditPermission() throws Exception {
    String url = "/map/forceGetEditPermission";
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("mapId", "1");
    params.add("key", "123456");
    JSONObject jsonObject1 = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void releaseEditPermission() throws Exception {
    String url = "/map/releaseEditPermission";
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("mapId", "1");
    JSONObject jsonObject1 = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void index() throws Exception {
    UserVO userVO = new UserVO();
    userVO.setUserId(2);
    userVO.setDefaultZone(2);
    stringRedisTemplate.opsForHash().put(CommonCacheConstants.USER_KEY + TenantContextHolder.getTenantId(),
      "2", JSONUtil.toJsonStr(userVO));

    String url = "/map/index";
    JSONObject jsonObject = doGet(url, false, false);
    Assert.assertEquals(jsonObject.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("spaceId", "2");
    JSONObject jsonObject1 = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> params2 = new LinkedMultiValueMap<>();
    params2.add("spaceId", "11");
    JSONObject jsonObject2 = doGet(url, false, false, params2);
    Assert.assertEquals(jsonObject2.get("code"), CommonConstants.FAIL);
  }

  @Test
  public void search() throws Exception {
    TenantContextHolder.setTenantId(1);
    MapElement mapElement = new MapElement();
    mapElement.setMapId(1);
    mapElement.setSvgId(1);
    mapElement.setSvgTypeCode("meeting-room");
    mapElement.setMapWebId("web111");
    mapElement.setObjectId("111");
    mapElement.setObjectName("张三");
    mapElement.setObjectColor("#123456");
    mapElement.setObjectBusiness("1");
    mapElement.setMapElementId("01ea73b494b75f59bcd90ba2ee6d6bf4");
    mapElement.insert();

    String url = "/map/search";
    JSONObject jsonObjectFail = doGet(url, false, false);
    Assert.assertEquals(jsonObjectFail.get("code"), CommonConstants.SUCCESS);

    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    params.add("spaceId", "1");
    params.add("key", "张三");
    JSONObject jsonObject1 = doGet(url, false, false, params);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void getTree() throws Exception {
    String json = "[{\"children\":[],\"delFlag\":\"0\",\"holidayCode\":\"\",\"id\":1,\"location\":\"\"," +
      "\"name\":\"区域\",\"nameEn\":\"\",\"parentId\":0,\"parentName\":\"\",\"path\":\"-1-\",\"pathName\":\"区域\"," +
      "\"sort\":1,\"structure\":0,\"timeZone\":\"\"}]";
    stringRedisTemplate.opsForValue().set(
      CommonConstants.SPACE_TREE_KEY + TenantContextHolder.getTenantId(), json);
    String url = "/map/space-tree";
    JSONObject jsonObject1 = doGet(url, false, false);
    Assert.assertEquals(jsonObject1.get("code"), CommonConstants.SUCCESS);
  }

  @Test
  public void testUpdateElementMq() {
    TenantContextHolder.setTenantId(1);
    MapElement mapElement = new MapElement();
    mapElement.setMapId(1);
    mapElement.setSvgId(1);
    mapElement.setSvgTypeCode("meeting-room");
    mapElement.setMapWebId("text001");
    mapElement.setObjectId("M001");
    mapElement.setMapElementId("01ea73b494b75f59bcd90ba2ee6d6123");
    mapElement.insert();

    MapElementObjectStateVO vo = new MapElementObjectStateVO();
    vo.setObjectId("M001");
    vo.setObjectName("华山");
    vo.setObjectColor("#123456");
    vo.setObjectBusiness("1");

    List<MapElementObjectStateVO> mapElementObjectStateVoList = new ArrayList<>();
    mapElementObjectStateVoList.add(vo);

    MapElementUpdateDTO mapElementUpdateDTO = new MapElementUpdateDTO();
    mapElementUpdateDTO.setSvgTypeCode("meeting-room");
    mapElementUpdateDTO.setMapElementObjectStateVoList(mapElementObjectStateVoList);

    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setMsgType(MsgTypeEnum.ONE.name());
    messageDTO.setMsgAction(MsgActionEnum.ADD.name());
    messageDTO.setData(JSONUtil.toJsonStr(mapElementUpdateDTO));

    this.amqpTemplate.convertAndSend(MapMqConstants.EXCHANGE_DIRECT_MAP, MapMqConstants.STATE_UPDATE_ROUTING_KEY,
      JSON.toJSONString(messageDTO));

    messageDTO.setMsgAction(MsgActionEnum.DELETE.name());
    this.amqpTemplate.convertAndSend(MapMqConstants.EXCHANGE_DIRECT_MAP, MapMqConstants.STATE_UPDATE_ROUTING_KEY,
      JSON.toJSONString(messageDTO));

    messageDTO.setMsgType(MsgTypeEnum.BATCH.name());
    this.amqpTemplate.convertAndSend(MapMqConstants.EXCHANGE_DIRECT_MAP, MapMqConstants.STATE_UPDATE_ROUTING_KEY,
      JSON.toJSONString(messageDTO));
  }

  @Test
  public void testSpaceOnlineOfflineMq() {
    ArrayList<SysSpace> spaceList = new ArrayList<>();
    SysSpace sysSpace = new SysSpace();
    sysSpace.setSpaceId(1);
    spaceList.add(sysSpace);

    MessageDTO messageDTO = new MessageDTO();
    messageDTO.setMsgType(MsgTypeEnum.BATCH.name());
    messageDTO.setMsgAction(MsgActionEnum.OFFLINE.name());
    messageDTO.setData(JSONUtil.toJsonStr(spaceList));

    this.amqpTemplate.convertAndSend(UpmsMqConstants.EXCHANGE_FANOUT_UPMS_SPACE, "", JSON.toJSONString(messageDTO));

    messageDTO.setMsgAction(MsgActionEnum.ONLINE.name());
    this.amqpTemplate.convertAndSend(UpmsMqConstants.EXCHANGE_FANOUT_UPMS_SPACE, "", JSON.toJSONString(messageDTO));

    messageDTO.setMsgAction(MsgActionEnum.ADD.name());
    this.amqpTemplate.convertAndSend(UpmsMqConstants.EXCHANGE_FANOUT_UPMS_SPACE, "", JSON.toJSONString(messageDTO));
  }
}
