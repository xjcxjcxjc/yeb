package com.xjc.task;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xjc.pojo.Employee;
import com.xjc.pojo.use.MailConstants;
import com.xjc.pojo.use.MailLog;
import com.xjc.service.IEmployeeService;
import com.xjc.service.IMailLogService;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author : XJC
 * @Description :邮件定时任务
 * @create : 2022/1/19 12:59
 */

//@Service
public class MailTask {

    @Autowired
    IMailLogService mailLogService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    IEmployeeService iEmployeeService;


    @Scheduled(cron = "*/5 * * * * ?")
    public void mailtask(){
        List<MailLog> mailLogs= mailLogService.list(new QueryWrapper<MailLog>()
        .eq("status",0).lt("tryTime", LocalDateTime.now()));
        mailLogs.forEach(mailLog -> {
            if (mailLog.getCount()<=3){
                /**
                 * 更新mail状态
                 */
                mailLogService.update(new UpdateWrapper<MailLog>()
                        .eq("msgId",mailLog.getMsgId())
                        .set("count",mailLog.getCount()+1)
                        .set("tryTime",LocalDateTime.now().plusMinutes(MailConstants.MAX_TRY_COUNT)));
                Employee employee=iEmployeeService.getEmploeeById(mailLog.getEid()).get(0);

                /**
                 * 重新发送
                 */
                rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME
                        ,MailConstants.MAIL_ROUTING_KEY_NAME,
                        employee,new CorrelationData(mailLog.getMsgId()));
            }
        });
    }


}