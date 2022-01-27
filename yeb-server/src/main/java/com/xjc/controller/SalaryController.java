package com.xjc.controller;


import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Salary;
import com.xjc.service.ISalaryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : XJC
 * @Description :工资账套,工资账套就类似于一个工资模板
 * @create : 2022/1/19 20:26
 */
@RestController
@RequestMapping("/salary/sob")
public class SalaryController {

    @Autowired
    ISalaryService iSalaryService;
    @ApiOperation("获取全部的工资账套")
    @GetMapping("/")
    public List<Salary> getAllSalary(){
        return iSalaryService.list();
    }
    @ApiOperation("添加工资账套")
    @PostMapping("/")
    RespBean addSalary(@RequestBody Salary salary){
        salary.setCreateDate(LocalDateTime.now());
        if (iSalaryService.save(salary)){
            return RespBean.success("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }
    @ApiOperation("更新工资账套")
    @PutMapping("/")
    RespBean updateSalary(@RequestBody Salary salary){
        if (iSalaryService.updateById(salary)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }
    @ApiOperation("用id删除工资账套")
    @DeleteMapping("/{id}")
    RespBean deleteSalaryById(@PathVariable Integer id){
        if (iSalaryService.removeById(id)){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }



}

