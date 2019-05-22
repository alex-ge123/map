package com.wafersystems.virsical.common.core.util;

import com.wafersystems.virsical.common.core.constants.CommonConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T> 泛型
 * @author tandk
 */
@Builder
@ToString
@Accessors(chain = true)
public class R<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
  private int code = 0;

  @Getter
  @Setter
  @ApiModelProperty(value = "返回信息")
  private String msg = "success";


  @Getter
  @Setter
  @ApiModelProperty(value = "数据")
  private T data;

  /**
   * 构造方法
   */
  public R() {
    super();
  }

  /**
   * 构造方法
   *
   * @param data 数据
   */
  public R(T data) {
    super();
    this.data = data;
  }

  /**
   * 构造方法
   *
   * @param code 返回标记：成功标记=0，失败标记=1
   * @param msg  返回信息
   * @param data 数据
   */
  public R(int code, String msg, T data) {
    super();
    this.code = code;
    this.msg = msg;
    this.data = data;
  }

  public static <T> R<T> ok() {
    return new R();
  }

  public static <T> R<T> ok(T data) {
    return new R(data);
  }

  public static <T> R<T> fail() {
    return new R(CommonConstants.FAIL, null, null);
  }

  public static <T> R<T> fail(String msg) {
    return new R(CommonConstants.FAIL, msg, null);
  }

}
