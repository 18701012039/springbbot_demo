package com.zxb.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(GlobalDefaultProperties.PREFIX)
public class GlobalDefaultProperties {

    public static final String PREFIX = "dispose";

    /**
     * 统一返回过滤包
     */
    private List<String> adviceFilterPackage = new ArrayList<>();

    /**
     * 统一返回过滤类
     */
    private List<String> adviceFilterClass = new ArrayList<>();


}