package com.ysgsignin.wxinfo.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  //实体类上忽略解析字段注解
public class WXSessionModel {

    private String session_key;
    private String openid;

}
