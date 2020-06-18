package com.zxb.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * 返回统一数据结构
 * @author purgeyao
 * @since 1.0
 */
public class ResultModel<T> implements Serializable {

    @ApiModelProperty(value = "是否成功")
    private Boolean succ;

    @ApiModelProperty(value = "服务器当前时间戳")
    private Long ts = System.currentTimeMillis();

    @ApiModelProperty(value = "成功数据")
    private T data;

    @ApiModelProperty(value = "错误码")
    private String code;

    @ApiModelProperty(value = "错误描述")
    private String msg;

    public Boolean getSucc() {
        return succ;
    }

    public void setSucc(Boolean succ) {
        this.succ = succ;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultModel() {
    }

    public ResultModel(Boolean succ, Long ts, T data, String code, String msg) {
        this.succ = succ;
        this.ts = ts;
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static ResultModel ofSuccess() {
        ResultModel result = new ResultModel();
        result.succ = true;
        return result;
    }

    public static ResultModel ofSuccess(Object data) {
        ResultModel result = new ResultModel();
        result.succ = true;
        result.setData(data);
        return result;
    }

    public static ResultModel ofFail(String code, String msg) {
        ResultModel result = new ResultModel();
        result.succ = false;
        result.code = code;
        result.msg = msg;
        return result;
    }

    public static ResultModel ofFail(String code, String msg, Object data) {
        ResultModel result = new ResultModel();
        result.succ = false;
        result.code = code;
        result.msg = msg;
        result.setData(data);
        return result;
    }

    public static ResultModel ofFail(CommonErrorCode resultEnum) {
        ResultModel result = new ResultModel();
        result.succ = false;
        result.code = resultEnum.getCode();
        result.msg = resultEnum.getMessage();
        return result;
    }

    public String buildResultJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("succ", this.succ);
        jsonObject.put("code", this.code);
        jsonObject.put("ts", this.ts);
        jsonObject.put("msg", this.msg);
        jsonObject.put("data", this.data);
        return JSON.toJSONString(jsonObject, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String toString() {
        return "Result{" +
                "succ=" + succ +
                ", ts=" + ts +
                ", data=" + data +
                ", code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
