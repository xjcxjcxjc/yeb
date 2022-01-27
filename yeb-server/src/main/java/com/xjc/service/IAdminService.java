package com.xjc.service;

import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xjc.pojo.use.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface IAdminService extends IService<Admin> {

    RespBean login(String userName, String password, String code, HttpServletRequest httpServletRequest);


    Admin getAdminByUserName(String userName);


    int addAdmin(Admin admin);


    /**
     * 根据用户id获取需要的角色
     * @param adminId
     * @return
     */
    List<Role> getRoles(Integer adminId);

    List<Admin> getAllAdmin(String keywords);

    RespBean updatePass(String oldPass, String newPass, String checkPass, Integer adminId);

    RespBean updateAdminUserPace(String url, Integer id, Authentication authentication);
}

