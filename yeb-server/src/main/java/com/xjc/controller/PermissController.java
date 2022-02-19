package com.xjc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.MenuRole;
import com.xjc.pojo.use.Menu;
import com.xjc.pojo.use.Role;
import com.xjc.service.IRoleService;
import com.xjc.service.IMenuRoleService;
import com.xjc.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : XJC
 * @Time : 2022/1/5 19:57
 * @Description : controller生成需要设置的参数：Api，uri，service，操作的对象，需要生成哪些方法
 */
@RestController
@RequestMapping("/system/basic/permiss")
public class PermissController {


    @Autowired
    IRoleService iRoleService;
    @Autowired
    IMenuService iMenuService;
    @Autowired
    IMenuRoleService iMenuRoleService;


    @ApiOperation("获取全部的roles")
    @GetMapping("/role")
    public List<Role> getAllRoles(){
        return iRoleService.list();
    }


    @ApiOperation("添加角色")
    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role){
        role.setName("ROLE_"+role.getName());
        if (iRoleService.save(role)){
            return RespBean.success("添加成功");
        }else{
            return RespBean.error("添加失败");
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

    /**
     * 对菜单的操作
     */
    @ApiOperation("获取全部菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenu(){
        return iMenuService.getAllMenus();
    }


    @ApiOperation("根据用户角色id获取菜单id")
    @GetMapping("/menus/{rid}")
    private List<Integer> getMenusByRid(@PathVariable Integer rid){
        return iMenuRoleService.list(new QueryWrapper<MenuRole>().eq("rid",rid)).stream().
                map(MenuRole::getMid).collect(Collectors.toList());
    }


    @ApiOperation("更新用户的菜单")
    @PutMapping("/")
    public RespBean updateMenus(Integer rid,Integer[] mids){
        return iMenuRoleService.updateRoles(rid,mids);
    }

}
