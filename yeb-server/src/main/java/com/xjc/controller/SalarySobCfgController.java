package com.xjc.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xjc.pojo.Employee;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.RespPageBean;
import com.xjc.pojo.use.Salary;
import com.xjc.service.IEmployeeService;
import com.xjc.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author : XJC
 * @Description :员工账套设置
 * @create : 2022/1/19 20:58
 */
@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

    @Autowired
    IEmployeeService employeeService;
    @Autowired
    ISalaryService salaryService;

    @ApiOperation("获取全部的工资账套")
    @GetMapping("/salaries")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @GetMapping("/")
    public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer page,
                                              @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeWithSalary(page,size);
    }


    @PutMapping("/")
    @ApiOperation("更新员工账套")
    public RespBean updateEmployeeWithSalary(Integer eid,Integer sid){
        if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId",sid).
                eq("id",eid))){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }



}
