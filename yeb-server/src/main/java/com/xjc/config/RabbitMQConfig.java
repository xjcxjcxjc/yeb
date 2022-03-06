package com.xjc.config;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xjc.pojo.use.MailConstants;
import com.xjc.pojo.use.MailLog;
import com.xjc.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Author : XJC
 * @Description :rabbitmq配置信息
 * @create : 2022/1/19 12:06
 */
@Configuration
public class RabbitMQConfig {

    @Autowired
    CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    IMailLogService mailLogService;

    Logger log= LoggerFactory.getLogger(RabbitMQConfig.class);


    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(cachingConnectionFactory);
        /**
         * 确认消息是否到达broker
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String id=correlationData.getId();
            if (ack){
                mailLogService.update(new UpdateWrapper<MailLog>().set("status",1)
                        .eq("msgId",id));
            }else{
                log.info("{}==============>",id,cause);
            }
        });

        /**
         * 消息失败回调，比如routing找不到queue
         */
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            log.info("{}=============>",returnedMessage);
        });
        return rabbitTemplate;
    }


    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }

}
