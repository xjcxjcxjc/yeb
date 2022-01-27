package com.xjc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.Department;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 */
public interface IDepartmentService extends IService<Department> {

	/**
	 * 获取所有部门
	 * @return
	 */
	List<Department> getAllDepartments();


	RespBean addDepartment(Department department);

	RespBean deleteDepartment(Integer id);



}
