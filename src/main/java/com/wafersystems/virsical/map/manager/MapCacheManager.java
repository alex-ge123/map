package com.wafersystems.virsical.map.manager;

import com.wafersystems.virsical.common.core.constant.CommonCacheConstants;
import com.wafersystems.virsical.common.core.constant.CommonConstants;
import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import com.wafersystems.virsical.common.core.util.R;
import com.wafersystems.virsical.common.entity.UserVO;
import com.wafersystems.virsical.map.common.MapConstants;
import com.wafersystems.virsical.map.common.MapMsgConstants;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tandk
 * @date 2020/5/14 15:15
 */
@Service
@AllArgsConstructor
public class MapCacheManager {
  private final StringRedisTemplate stringRedisTemplate;

  /**
   * 校验地图编辑权限
   *
   * @param mapId 地图id
   * @param key   权限key
   * @return 编辑权限过期时间
   */
  public R checkMapEditPermission(Integer mapId, String key, boolean isCache) {
    String cacheValue = stringRedisTemplate.opsForValue().get(MapConstants.MAP_EDIT_PERMISSION + mapId);
    // username,key
    key = TenantContextHolder.getUsername() + CommonConstants.COMMA + key;
    if (cacheValue != null && !key.equals(cacheValue)) {
      Long expire = stringRedisTemplate.getExpire(MapConstants.MAP_EDIT_PERMISSION + mapId, TimeUnit.SECONDS);
      if (expire != null && expire > 0) {
        Map<String, String> map = new HashMap<>(2);
        map.put("expire", expire.toString());
        map.put("username", cacheValue.split(CommonConstants.COMMA)[0]);
        return R.builder().code(CommonConstants.FAIL).msg(MapMsgConstants.MAP_EDIT_PERMISSION).data(map).build();
      }
    }
    if (isCache) {
      cacheMapEditPermission(mapId, key);
    }
    return R.ok();
  }

  /**
   * 缓存地图编辑权限
   *
   * @param mapId 地图id
   * @param key   权限key
   */
  public void cacheMapEditPermission(Integer mapId, String key) {
    stringRedisTemplate.opsForValue().set(MapConstants.MAP_EDIT_PERMISSION + mapId,
      key, MapConstants.MAP_EDIT_PERMISSION_TIMEOUT, TimeUnit.SECONDS);
  }

  /**
   * 释放地图编辑权限
   *
   * @param mapId 地图id
   */
  public void releaseEditPermission(Integer mapId) {
    Boolean b = stringRedisTemplate.hasKey(MapConstants.MAP_EDIT_PERMISSION + mapId);
    if (b != null && b) {
      stringRedisTemplate.delete(MapConstants.MAP_EDIT_PERMISSION + mapId);
    }
  }

  /**
   * 查询用户信息
   *
   * @return SysUser
   */
  public UserVO getUserFromRedis() {
    return (UserVO) stringRedisTemplate.opsForHash().get(CommonCacheConstants.USER_KEY + TenantContextHolder.getTenantId(),
      TenantContextHolder.getUserId() + "");
  }
}
