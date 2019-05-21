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
 * @param <T>
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

  public R() {
    super();
  }

  public R(T data) {
    super();
    this.data = data;
  }

  public R(T data, String msg) {
    super();
    this.data = data;
    this.msg = msg;
  }

  public R(Throwable e) {
    super();
    this.msg = e.getMessage();
    this.code = 1;
  }

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
