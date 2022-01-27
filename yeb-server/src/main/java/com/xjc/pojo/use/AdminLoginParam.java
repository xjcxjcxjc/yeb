package com.xjc.pojo.use;
/*
 * @Author : XJC
 * @Time : 2021/12/13 8:53
 * @Description :
 *
 */


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors
@ApiModel(value = "AdminLogin对象")
public class AdminLoginParam {

    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    @ApiModelProperty(value = "密码",required = true)
    private String password;
    @ApiModelProperty(value = "验证码",required = true)
    private String code;


}
