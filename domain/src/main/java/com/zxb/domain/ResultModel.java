package com.zxb.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一返回类
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
@Data
public class ResultModel<T> {
    @ApiModelProperty(value = "错误码")
    private Integer code;

    @ApiModelProperty(value = "返回的数据")
    private T data;

    @ApiModelProperty(value = "提示信息")
    private String message;
}
