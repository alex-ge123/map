package com.wafersystems.virsical.map.model.dto;

import com.wafersystems.virsical.common.core.tenant.TenantContextHolder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

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
public class MessageDTO {

  /**
   * 消息id
   */
  private String msgId;

  /**
   * 消息发送时间
   */
  private Long msgTime;

  /**
   * 消息所属产品（会议-smt，工位-smw，地图-map）
   */
  private String product;

  /**
   * 消息类型（ONE单条(点对点)|BATCH批量|ALL(广播)）
   */
  private String msgType;

  /**
   * 消息动作（ADD|DELETE|UPDATE|SHOW|NONE: 增|删|改|展示|无）
   */
  private String msgAction;

  /**
   * 操作人用户id
   */
  private Integer userId;

  /**
   * 终端id
   */
  private String clientId;

  /**
   * 业务信息
   */
  private String business;

  /**
   * 消息体
   */
  private Serializable data;

  public MessageDTO() {
  }

  /**
   * constructor
   *
   * @param userId    userId
   * @param clientId  clientId
   * @param business  业务信息
   * @param product   消息所属产品（会议-smt，工位-smw，地图-map）
   * @param msgType   消息类型（ONE单条(点对点)|BATCH批量|ALL(广播)）
   * @param msgAction 消息动作（ADD|DELETE|UPDATE|NONE: 增|删|改|无）
   * @param data      消息体
   */
  public MessageDTO(Integer userId, String clientId, String business, String product, String msgType, String msgAction,
                    Serializable data) {
    this.msgId = UUID.randomUUID().toString();
    this.msgTime = System.currentTimeMillis();
    this.userId = userId;
    this.clientId = clientId;
    this.business = business;
    this.product = product;
    this.msgType = msgType;
    this.msgAction = msgAction;
    this.data = data;
  }

  /**
   * constructor
   * @param msgType 消息类型（ONE单条|BATCH批量）
   * @param msgAction 消息动作（ADD|DELETE|UPDATE: 增删改）
   * @param data 消息体
   */
  public MessageDTO(String msgType, String msgAction, Serializable data) {
    this.msgType = msgType;
    this.msgId = UUID.randomUUID().toString();
    this.msgTime = System.currentTimeMillis();
    this.msgAction = msgAction;
    this.userId = TenantContextHolder.getUserId();
    this.data = data;
  }
}
