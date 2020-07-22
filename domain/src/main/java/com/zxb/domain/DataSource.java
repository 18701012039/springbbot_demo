package com.zxb.domain;


import lombok.Data;
import lombok.ToString;
import java.io.Serializable;

@Data
@ToString
public class DataSource implements Serializable {
    String dataSourceId;
    String url;
    String userName;
    String passWord;
    String code;
    String dataBaseType;

}
