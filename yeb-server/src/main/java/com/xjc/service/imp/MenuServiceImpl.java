package com.xjc.service.imp;

import com.xjc.mapper.MenuMapper;
import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.Menu;
import com.xjc.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.lettuce.core.RedisException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
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
//        ValueOperations valueOperations=redisTemplate.opsForValue();
//        List<Menu> menus= ((List<Menu>) valueOperations.get("menu_" + adminId));
//        //去数据库查找，并把结果存储在redis
//        if (null==menus){
//            menus=menuMapper.getMenuByAdminId(adminId);
//            valueOperations.set("menu_"+adminId,menus);
//        }
        List<Menu> menus;
//       经常报 RedisCommandTimeoutException,我认为可能是服务器的问题,加了个trycatch
        //问题：数据库和缓存一致性问题，数据库改了，但是缓存数据库没改，应该在update后失效的
        try {
            ListOperations listOperations=redisTemplate.opsForList();
            menus=  listOperations.range("menu_" + adminId,0,-1);
            //去数据库查找，并把结果存储在redis
            if (menus.size()<1){
                menus=menuMapper.getMenuByAdminId(adminId);
                menus.forEach(menu -> {
                    listOperations.rightPush("menu_"+adminId,menu);
                });
            }
        }catch (Exception e){
            System.out.println("{}============================>");
            log.error(e.getMessage()+e.getStackTrace());
            menus=menuMapper.getMenuByAdminId(adminId);
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
