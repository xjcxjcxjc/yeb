package com.xjc.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.xjc.pojo.*;
import com.xjc.pojo.use.RespBean;
import com.xjc.pojo.use.Position;
import com.xjc.pojo.use.RespPageBean;
import com.xjc.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;

/**
 * @Author : XJC
 * @Description :员工请求
 * @create : 2022/1/9 20:03
 */
@RestController
@RequestMapping("/employee/basic")
public class EmploeeController {

    @Autowired
    private IEmployeeService iEmployeeService;
    @Autowired
    IDepartmentService iDepartmentService;
    @Autowired
    IJoblevelService iJoblevelService;
    @Autowired
    INationService iNationService;
    @Autowired
    IPositionService iPositionService;
    @Autowired
    IPoliticsStatusService iPoliticsStatusService;



    @ApiOperation("分页查询员工")
    @GetMapping("/")
    public RespPageBean getEmploee(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10") Integer size,
                                      Employee employee,
                                      LocalDate[] beginDateScope){
        return iEmployeeService.getEmploee(currentPage,size,employee,beginDateScope);
    }


    @ApiOperation("获取全部的部门")
    @GetMapping("/dep")
    public List<Department> getAllDepartment(){
        return iDepartmentService.list();
    }

    @ApiOperation("获取全部的职位")
    @GetMapping("/pos")
    public List<Position> getAllPosition(){
        return iPositionService.list();
    }
    @ApiOperation("获取全部的职称")
    @GetMapping("/jl")
    public List<Joblevel> getAllJoblevel(){
        return iJoblevelService.list();
    }

    @ApiOperation("获取全部的民族")
    @GetMapping("/na")
    public List<Nation> getAllNation(){
        return iNationService.list();
    }

    @ApiOperation("获取全部的政治面貌")
    @GetMapping("/pol")
    public List<PoliticsStatus> getAllPoliticsStatus(){
        return iPoliticsStatusService.list();
    }
    @ApiOperation("获取工号")
    @GetMapping("/maxWorkId")
    public RespBean getMaxWorkId(){
        return iEmployeeService.getMaxWorkId();
    }


    @ApiOperation(value = "添加员工")
    @PostMapping("/")
    public RespBean addEmplyoee(@RequestBody Employee employee){
        return iEmployeeService.addEmplyoee(employee);
    }

    @ApiOperation("更新员工")
    @PutMapping("/")
    public RespBean updateEmplyoee(@RequestBody Employee employee){
        if (iEmployeeService.updateById(employee)){
            return RespBean.success("更新成功");
        }

        return RespBean.error("更新失败");
    }

    @ApiOperation("删除员工")
    @DeleteMapping("/")
    public RespBean deleteEmplyoeeByid(@RequestParam Integer id){
        if (iEmployeeService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @ApiOperation("导出员工数据")
    @GetMapping(value = "/export",produces ="application/octet-stream" )
    public void export(HttpServletResponse httpResponse){
        List<Employee> employees=iEmployeeService.getEmploeeById(null);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("员工表",
                        "员工表", ExcelType.HSSF),
                Employee.class, employees);
        ServletOutputStream out=null;
        try {
            //定义流形式输出
            httpResponse.setHeader("content-type","application/octet-stream");
            //防止中文乱码
            httpResponse.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode("员工表.xls","UTF-8"));
            out=httpResponse.getOutputStream();
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @ApiOperation(value = "导入员工数据")
    @PostMapping("/import")
    public RespBean importEmployee( MultipartFile multipartFile){
        ImportParams importParams=new ImportParams();
        importParams.setTitleRows(1);

        List<Nation> nations= iNationService.list();
        List<Department> departments=iDepartmentService.list();
        List<Joblevel> joblevels=iJoblevelService.list();
        List<PoliticsStatus> politicsStatuses=iPoliticsStatusService.list();
        List<Position> positions=iPositionService.list();


        try {
            List<Employee> employees=ExcelImportUtil.importExcel(multipartFile.getInputStream(),Employee.class,importParams);
            employees.forEach(employee -> {
                employee.setNationId(nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
                employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
                    //政治面貌id
                employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
                //职称id
                employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
                //职位id
                employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
            });
            if (iEmployeeService.saveBatch(employees)){
                return RespBean.success("导入成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespBean.error("导入失败");
    }



}
