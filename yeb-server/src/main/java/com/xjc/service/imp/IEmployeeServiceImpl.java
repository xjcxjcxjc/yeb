package com.xjc.service.imp;
import java.time.LocalDateTime;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjc.mapper.MailLogMapper;
import com.xjc.pojo.use.MailConstants;
import com.xjc.pojo.use.MailLog;
import com.xjc.pojo.use.RespBean;
import com.xjc.mapper.EmployeeMapper;
import com.xjc.pojo.Employee;
import com.xjc.pojo.use.RespPageBean;
import com.xjc.service.IEmployeeService;
import com.xjc.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author : XJC
 * @Description :员工的服务类
 * @create : 2022/1/9 20:07
 */
@Service
public class IEmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    MailLogMapper mailLogMapper;


    @Override
    public RespPageBean getEmploee(Integer currentPage, Integer size, Employee employee,
                                   LocalDate[] beginDateScope) {
        Page<Employee> page=new Page(currentPage,size);
        IPage<Employee> employeeByPage= employeeMapper.getEmploeeByPage(page,employee,beginDateScope);
        return new RespPageBean(employeeByPage.getTotal(),employeeByPage.getRecords());
    }

    @Override
    public RespBean getMaxWorkId() {
        List<Map<String, Object>> maps =employeeMapper.selectMaps(
                new QueryWrapper<Employee>().select("max(workID)"));
        return RespBean.success(null,
                String.format("%08d",Integer.parseInt(maps.get(0).get("max(workID)").toString())+1));
    }

    @Override
    public RespBean addEmplyoee(Employee employee) {
        LocalDate beginDate=employee.getBeginContract();
        LocalDate endDate=employee.getEndContract();
        Double days=(double) beginDate.until(endDate, ChronoUnit.DAYS);
        DecimalFormat decimalFormat=new DecimalFormat("##.00");
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(days/365)));

        if (1==employeeMapper.insert(employee)){
//            String id=UUID.randomUUID().toString();
            String id="123456";

            MailLog mailLog=new MailLog();
            mailLog.setMsgId(id);
            mailLog.setEid(employee.getId());
            mailLog.setStatus(0);
            mailLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailLog.setCount(0);
            mailLog.setTryTime(LocalDateTime.now().plusMinutes(MailConstants.MAS_TIMEOUT));
            mailLog.setCreateTime(LocalDateTime.now());
            mailLog.setUpdateTime(LocalDateTime.now());
            mailLogMapper.insert(mailLog);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME
                    ,MailConstants.MAIL_ROUTING_KEY_NAME,employee,new CorrelationData(id));
            return RespBean.success("添加成功");
        }
        return RespBean.error("添加失败");
    }


    @Override
    public List<Employee> getEmploeeById(Integer id) {
        return employeeMapper.getEmploeeById(id);
    }

    @Override
    public RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size) {
        Page page=new Page(currentPage,size);
        IPage<Employee> employeeIPage =employeeMapper.getEmployeeWithSalary(page);
        RespPageBean respPageBean=new RespPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
        return respPageBean;
    }

}