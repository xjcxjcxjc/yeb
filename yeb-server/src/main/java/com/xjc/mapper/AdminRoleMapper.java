package com.xjc.mapper;

import com.xjc.pojo.AdminRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer insertRids(Integer adminId,Integer[] rids);

}
