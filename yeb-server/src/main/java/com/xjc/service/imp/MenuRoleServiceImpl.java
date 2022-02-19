package com.xjc.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjc.mapper.MenuRoleMapper;
import com.xjc.mapper.RoleMapper;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.MenuRole;
import com.xjc.service.IMenuRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    @Autowired
    private RoleMapper roleMapper;


    @Override
    /**
     * 这里先执行了删除所有menu然后插入，这应该是事务的
     * 问题：数据库和缓存一致性问题，数据库改了，但是缓存数据库没改，应该在update后失效的
     */
    @Transactional
    public RespBean updateRoles(Integer rid, Integer[] mids) {
        if (null==roleMapper.selectById(rid)){
            return RespBean.error("角色不存在");
        }
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        if (null==mids||mids.length==0){
            return RespBean.success("更新成功");
        }
        Integer result=menuRoleMapper.insertRecords(rid,mids);
        if (result==mids.length){
            return RespBean.success("添加成功");
        }else{
            throw new RuntimeException("数据添加失败");
        }

    }
}
