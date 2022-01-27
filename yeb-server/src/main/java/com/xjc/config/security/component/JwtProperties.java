package com.xjc.config.security.component;
/*
 * @Author : XJC
 * @Time : 2021/12/12 21:09
 * @Description :
 *
 */

import com.auth0.jwt.JWT;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    //key
    private String tokenHeader ;
    private String secret;
    private int expiration;

    //value tokenHead token
    private String tokenHead ;


    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getTokenHead() {
        return tokenHead;
    }

    public void setTokenHead(String tokenHead) {
        this.tokenHead = tokenHead;
    }
}
