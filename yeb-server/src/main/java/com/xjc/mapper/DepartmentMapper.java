package com.xjc.mapper;

import com.xjc.pojo.Department;
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
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> getAllDepartment(Integer parentId);

    void addDepartment(Department department);

    void deleteDep(Department department);


}
