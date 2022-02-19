package com.xjc.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.Joblevel;
import com.xjc.service.IJoblevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/5 18:22
 * @Description :
 */
@RestController
@RequestMapping("/system/cfg/joblev")
public class JobLevelController {


    @Autowired
    private IJoblevelService iJoblevelService;


    @ApiOperation("获取全部的职称")
    @GetMapping("/")
    public List<Joblevel> getAllJonLevel(){
        return iJoblevelService.list();
    }

    @ApiOperation("添加职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if (iJoblevelService.save(joblevel)){
            return RespBean.success("添加成功");
        }else {
            return RespBean.error("添加失败");
        }
    }

    @ApiOperation("更新职称")
    @PutMapping("/")
    public RespBean updateJoblevel(@RequestBody Joblevel joblevel){
        if (iJoblevelService.updateById(joblevel)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }

    @ApiOperation("删除职位")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevById(@PathVariable Integer id){
        if (iJoblevelService.removeById(id)){
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("/")
    public RespBean deleteJobLevelsByIds(Integer[] ids){
        if (iJoblevelService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }

}
