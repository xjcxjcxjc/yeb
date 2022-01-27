package com.xjc.util;


import com.xjc.pojo.use.Admin;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Author : XJC
 * @Time : 2022/1/7 19:16
 * @Description :
 */
public class AdminUtils {

    public static Admin getAdmin(){
        return (Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
