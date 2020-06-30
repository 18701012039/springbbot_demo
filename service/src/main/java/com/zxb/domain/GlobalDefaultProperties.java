package com.zxb.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(GlobalDefaultProperties.PREFIX)
@Data
public class GlobalDefaultProperties {

    public static final String PREFIX = "dispose";

    @ApiModelProperty(value ="统一返回过滤包" )
    private List<String> adviceFilterPackage = new ArrayList<>();

    @ApiModelProperty(value = "统一返回过滤类")
    private List<String> adviceFilterClass = new ArrayList<>();


}