package com.xjc.controller;


import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.Role;
import com.xjc.service.IAdminRoleService;
import com.xjc.service.IRoleService;
import com.xjc.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/7 15:43
 * @Description :
 */
@RestController
@RequestMapping("/system/admin")
public class AdminController {

    @Autowired
    private IAdminService iAdminService;

    @Autowired
    private IAdminRoleService iAdminRoleService;

    @Autowired
    private IRoleService iRoleService;



    @ApiOperation("获取全部操作员 ")
    @GetMapping("/")
    public List<Admin> getAllAdmin(String keywords){
        return iAdminService.getAllAdmin(keywords);
    }

    @ApiOperation("更新操作员")
    @PutMapping("/")
    RespBean updateAdmin(@RequestBody Admin admin){
        if (iAdminService.updateById(admin)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }

    @ApiOperation("删除操作员")
    @DeleteMapping("/")
    public RespBean deleteAdmin(Integer id){
        if (iAdminService.removeById(id)){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }


    @ApiOperation("获取全部角色")
    @GetMapping("/role")
    public List<Role> getAllRoles(){
        return iRoleService.list();
    }

    @ApiOperation("更新操作员角色")
    @PutMapping("/role")
    public RespBean updateAdminRole(Integer adminId,Integer[] ids){
        return  iAdminRoleService.upDateAdminRoles(adminId,ids);
    }


}
