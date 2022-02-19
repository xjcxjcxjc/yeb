package com.xjc.controller;


import com.xjc.pojo.Department;
import com.xjc.pojo.use.RespBean;
import com.xjc.service.IDepartmentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/7 9:40
 * @Description :
 */
@RestController
@RequestMapping("/system/basic/department")
public class DepartMentController {

    @Autowired
    IDepartmentService iDepartmentService;

    @ApiOperation("获取全部的department")
    @GetMapping("/")
    public List<Department> getAllDepartment(){
        return iDepartmentService.getAllDepartments();
    }

    @ApiOperation("添加department")
    @PostMapping("/")
    RespBean addDepartment(@RequestBody Department department){
        return iDepartmentService.addDepartment(department);
    }

    @ApiOperation("删除department")
    @DeleteMapping("/{id}")
    RespBean deleteDepartmentById(@PathVariable Integer id){
        return iDepartmentService.deleteDepartment(id);
    }


}
