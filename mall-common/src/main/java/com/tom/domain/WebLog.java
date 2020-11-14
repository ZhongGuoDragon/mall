package com.tom.domain;

import lombok.Data;

@Data
public class WebLog {
    private String description;
    private String username;
    private Long startTime;
    private Integer spendTime;
    private String basePath;
    private String url;
    private String uri;
    private String method;
    private String ip;
    private Object parameter;
    private Object result;
}
