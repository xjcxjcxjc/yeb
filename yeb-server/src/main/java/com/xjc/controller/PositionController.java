package com.xjc.controller;


import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Position;
import com.xjc.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/4 21:13
 * @Description :
 */
@RestController
@RequestMapping("/system/cfg/pos")
public class PositionController {

    @Autowired
    IPositionService iPositionService;

    @ApiOperation("获取全部职位")
    @GetMapping("/")
    public List<Position> getAllPosition(){
        List<Position> positions=iPositionService.list();
        return positions;
    }

    @ApiOperation("添加职位")
    @PostMapping("/")
    public RespBean addPostion(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if (iPositionService.save(position)){
            return RespBean.success("添加成功");
        }else{
            return RespBean.success("添加失败");
        }
    }

    @ApiOperation("更新职位")
    @PutMapping("/")
    public RespBean updatePostion(@RequestBody Position position){
        if (iPositionService.updateById(position)){
            return RespBean.success("更新成功");
        }else {
            return RespBean.error("更新失败");
        }
    }

    @ApiOperation("删除职位")
    @DeleteMapping("/{id}")
    public RespBean deletePostionById(@PathVariable Integer id){
        if (iPositionService.removeById(id)){
            return RespBean.success("删除成功");
        }else{
            return RespBean.error("删除失败");
        }
    }

    @ApiOperation("批量删除职位")
    @DeleteMapping("/")
    public RespBean deleteByIds(Integer[] ids){
        if (iPositionService.removeByIds(Arrays.asList(ids))){
            return RespBean.success("删除成功");
        }else {
            return RespBean.error("删除失败");
        }
    }

}
