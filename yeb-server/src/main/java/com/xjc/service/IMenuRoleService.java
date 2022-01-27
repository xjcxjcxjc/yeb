package com.xjc.service;

import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.MenuRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface IMenuRoleService extends IService<MenuRole> {


    public RespBean updateRoles(Integer rid, Integer[] mids);


}
