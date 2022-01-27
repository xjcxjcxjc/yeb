package com.xjc.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.service.ApiKey;

/**
 * @Author : XJC
 * @Description :封装代码生成需要的参数
 * @create : 2022/1/10 10:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpsBean {

    private String apiKey;
    private String service;
    private String pojo;
    private String url;

    private boolean selectAll;
    private boolean add;
    private boolean update;
    private boolean delete;
    private boolean deletebouch;




}
