package com.xjc.controller;


import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Role;
import com.xjc.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/5 23:05
 * @Description :
 */
public class TestController {

    @Autowired
    IRoleService iRoleService;
    @ApiOperation("获取全部的role")
    @GetMapping("/role")
    public List<Role> getAllRole(){
        return iRoleService.list();
    }
    @ApiOperation("添加role")
    @PostMapping("/role")
    RespBean addRole(Role role){
        if (iRoleService.save(role)){
            return RespBean.success("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }
    @ApiOperation("更新role")
    @PutMapping("/role")
    RespBean updateRole(@RequestBody Role role){
        if (iRoleService.updateById(role)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("删除role")
    @DeleteMapping("/role/{id}")
    RespBean deleteRoleById(@PathVariable Integer id){
        if (iRoleService.removeById(id)){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }
    @ApiOperation("批量删除role")
    @DeleteMapping("/role")
    RespBean deleteRoleById(Integer[] ids){
        if (iRoleService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }
}
