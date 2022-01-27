package com.xjc.mapper;

import com.xjc.pojo.use.Admin;
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
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> getAllAdmin(Integer curtId,String keywords);


}
