package com.xjc.controller;


import com.xjc.pojo.use.Menu;
import com.xjc.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService iMenuService;

    @ApiOperation("根据用户id获取menu")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdmin(){
        return iMenuService.getMenuByAdmin();
    }



}
