package com.xjc.service.imp;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.mapper.SalaryAdjustMapper;
import com.xjc.mapper.SalaryMapper;
import com.xjc.pojo.use.Salary;
import com.xjc.service.ISalaryService;
import org.springframework.stereotype.Service;

/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/19 20:28
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {
}
