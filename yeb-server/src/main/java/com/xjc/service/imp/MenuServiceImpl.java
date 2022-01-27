package com.xjc.service.imp;

import com.xjc.mapper.MenuMapper;
import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.Menu;
import com.xjc.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;



    @Override
    @ApiOperation("通过用户id查询菜单列表")
    public List<Menu> getMenuByAdmin() {
        Integer adminId=((Admin)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations valueOperations=redisTemplate.opsForValue();
        List<Menu> menus= ((List<Menu>) valueOperations.get("menu_" + adminId));
        //去数据库查找，并把结果存储在redis
        if (null==menus){
            menus=menuMapper.getMenuByAdminId(adminId);
            valueOperations.set("menu_"+adminId,menus);
        }
        return menus;
    }

    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();

    }
}
