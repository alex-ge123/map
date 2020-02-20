package com.wafersystems.virsical.map.model.dto;

import com.wafersystems.virsical.map.entity.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 消息传输对象
 *
 * @author tandk
 * @date 2019/4/3 16:35
 */
@Setter
@Getter
@ToString
@Data
public class SpaceMapDTO {
  /**
   * 空间叶子节点id
   */
  private Integer spaceId;

  /**
   * 空间节点名称
   */
  private String name;

  /**
   * 空间路径名称
   */
  private String pathName;

  /**
   * 地图对象
   */
  private Map map;
}
