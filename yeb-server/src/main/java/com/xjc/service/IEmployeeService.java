package com.xjc.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xjc.pojo.Employee;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.RespPageBean;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 */
public interface IEmployeeService extends IService<Employee> {

	RespPageBean getEmploee(Integer currentPage, Integer size,
							Employee employee, LocalDate[] beginDateScope);

	RespBean getMaxWorkId();

	RespBean addEmplyoee(Employee employee);


    List<Employee> getEmploeeById(Integer id);

    RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
