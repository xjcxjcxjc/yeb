package com.xjc.config.security.component;
/*
 * @Author : XJC
 * @Time : 2021/12/14 10:21
 * @Description :
 *
 */


import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjc.pojo.use.RespBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当用户token失效或没有登录时候访问接口，自定义返回结果
 */
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {


    Logger LOGGER= LoggerFactory.getLogger(RestAuthorizationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter printWriter=response.getWriter();

        RespBean respBean=RespBean.error("请先登录--RestAuthorizationEntryPoint");
        LOGGER.info("{}===========>",request.getRequestURI(),authException.getMessage());
        System.out.println(request.getRequestURI());
        respBean.setCode(response.getStatus());
        printWriter.write(new  ObjectMapper().writeValueAsString(respBean));
        printWriter.flush();
        printWriter.close();
    }


}
