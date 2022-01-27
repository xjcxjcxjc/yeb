package com.xjc.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.mapper.RoleMapper;
import com.xjc.pojo.use.Role;
import com.xjc.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
