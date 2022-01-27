package com.xjc.mapper;

import com.xjc.pojo.use.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     * @return
     */
    List<Menu> getMenusWithRole();


    List<Menu> getAllMenus();

}