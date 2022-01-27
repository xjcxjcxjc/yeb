package com.xjc.service;

import com.xjc.pojo.use.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuByAdmin();


    List<Menu> getMenusWithRole();

    /**
     * 获取全部菜单
     * @return
     */
    List<Menu> getAllMenus();




}
