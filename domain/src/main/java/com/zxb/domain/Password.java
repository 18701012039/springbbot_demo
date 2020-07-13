package com.zxb.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * 用户名密码
 * @author zxb
 * @create 2020/7/13
 * @since 1.0.0
 */
@Data
public class Password implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String password;
}
