package com.zxb.anno;


import java.lang.annotation.*;

/**
 * 根据注解拦截来进行切换数据源
 * @author zxb
 * @date 2020/07/16
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceAnn {
    String value();
}
