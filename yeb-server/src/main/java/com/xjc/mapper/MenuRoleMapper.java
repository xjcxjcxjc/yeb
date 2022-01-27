package com.xjc.mapper;

import com.xjc.pojo.MenuRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    public Integer insertRecords(@Param("rid") Integer rid, @Param("mids")Integer[] mids);
}
