package com.xjc.service.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.pojo.Department;
import com.xjc.pojo.use.RespBean;
import com.xjc.mapper.DepartmentMapper;
import com.xjc.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2022-01-07
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {


    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartment(-1);
    }


    @Override
    public RespBean addDepartment(Department department) {
        department.setEnabled(true);
        departmentMapper.addDepartment(department);
        if (department.getResult()==1){
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败!");
    }

    @Override
    public RespBean deleteDepartment(Integer id) {
        Department department=new Department();
        department.setId(id);
        departmentMapper.deleteDep(department);
        if (department.getResult()==1){
            return RespBean.success("删除成功");
        }else if(department.getResult()==-2){
            return RespBean.error("部门还有子部门");
        }else if (department.getResult()==-1){
            return RespBean.error("部门还有员工");
        }else {
            return RespBean.error("删除错误");
        }
    }
}
