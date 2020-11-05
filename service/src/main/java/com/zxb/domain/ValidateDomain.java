package com.zxb.domain;/**
 * @author zxb
 * @date 2018/10/19
 */

import lombok.Data;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotBlank;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author admin
 * @create 2020/9/27
 * @since 1.0.0
 */
@Data
public class ValidateDomain {

    @NotBlank(message = "新增时不能为空",groups = Insert.class)
    @NotBlank(message = "更新时不能为空",groups = Update.class)
    public String name;
}
