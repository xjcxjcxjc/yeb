package com.xjc.controller;


import com.xjc.pojo.use.Admin;
import com.xjc.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/20 11:36
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IAdminService adminService;

    @GetMapping("/admin")
    @ApiOperation("获取全部操作员")
    public List<Admin> getallAdmin(String keywards){
        return adminService.getAllAdmin(keywards);
    }


}
