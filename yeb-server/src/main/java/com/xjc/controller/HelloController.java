package com.xjc.controller;
/*
 * @Author : XJC
 * @Time : 2021/12/14 11:48
 * @Description :
 *
 */


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello你好";
    }


    @GetMapping("/employee/basic/hello")
    public String basic(){
        return "/employee/basic/";
    }
    @GetMapping("/employee/advanced/hello")
    public String advanced(){
        return "/employee/advanced/";
    }
    @GetMapping("/salary/month/hello")
    public String salary(){
        return "/salary/month/";
    }

}
