package com.xjc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.AdminRole;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 */
public interface IAdminRoleService extends IService<AdminRole> {

    /**
     * @param adminId 用户id
     * @param rids 所有的角色id
     * @return
     */
    RespBean upDateAdminRoles(Integer adminId, Integer[] rids);

}
