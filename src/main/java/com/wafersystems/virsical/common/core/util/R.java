package com.wafersystems.virsical.common.core.util;

import com.wafersystems.virsical.common.core.exception.BusinessException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 响应信息主体
 *
 * @param <T> 泛型
 * @author tandk
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Builder
@ToString
@Accessors(chain = true)
public class R<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Getter
  @ApiModelProperty(value = "返回标记：成功标记=0，失败标记=1")
  private int code = 0;

  @Getter
  @ApiModelProperty(value = "返回信息")
  private String msg = "success";


  @Getter
  @ApiModelProperty(value = "数据")
  private transient T data;

  /**
   * 构造方法
   */
  public R() {
    super();
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

  /**
   * 创建成功返回对象
   *
   * @return R
   */
  public static R ok() {
    return R.builder().build();
  }

  /**
   * 创建成功返回对象
   *
   * @param data 数据
   * @return R
   */
  public static <T> R ok(T data) {
    return R.builder().data(data).build();
  }

  /**
   * 创建失败返回对象
   *
   * @return R
   */
  public static R fail() {
    throw new BusinessException();
  }

  /**
   * 创建失败返回对象
   *
   * @param msg 提示
   * @return R
   */
  public static R fail(String msg) {
    throw new BusinessException(msg);
  }
}
