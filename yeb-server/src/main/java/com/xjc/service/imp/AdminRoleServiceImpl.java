package com.xjc.service.imp;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.pojo.use.RespBean;
import com.xjc.mapper.AdminRoleMapper;
import com.xjc.pojo.AdminRole;
import com.xjc.service.IAdminRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author : XJC
 * @Time : 2022/1/9 16:15
 * @Description :
 */
@Service
public class AdminRoleServiceImpl  extends ServiceImpl<AdminRoleMapper,AdminRole> implements IAdminRoleService {

    @Autowired
    AdminRoleMapper adminRoleMapper;

    @Override
    @Transactional
    public RespBean upDateAdminRoles(Integer adminId, Integer[] rids) {
        adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId",adminId));
        Integer result= adminRoleMapper.insertRids(adminId,rids);
        if (result==rids.length){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }
}
