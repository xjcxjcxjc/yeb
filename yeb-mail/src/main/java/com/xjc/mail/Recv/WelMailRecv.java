package com.xjc.mail.Recv;


import com.rabbitmq.client.Channel;
import com.xjc.pojo.Employee;
import com.xjc.pojo.use.MailConstants;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.PublisherCallbackChannel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;
import org.slf4j.Logger;



/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/15 21:27
 */
@Component
public class WelMailRecv {

    private static final Logger LOGGER= LoggerFactory.getLogger(WelMailRecv.class);
    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private RedisTemplate redisTemplate;


    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handleMail(Message message, Channel channel){
        Employee employee=((Employee) message.getPayload());
        MessageHeaders headers = message.getHeaders();
        //邮件设置
        long tag = (long) headers.get(AmqpHeaders.DELIVERY_TAG);
        String mId=((String) headers.get(PublisherCallbackChannel.RETURNED_MESSAGE_CORRELATION_KEY));
        HashOperations hashOperations=redisTemplate.opsForHash();
        try {
            if (hashOperations.entries("mail_log").containsKey(mId)){
                channel.basicAck(tag,false);
                LOGGER.info("{}===========>已经存在",mId);
                return;
            }
            MimeMessage msg=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(msg);
            Context context=new Context();
            context.setVariable("name",employee.getName());
            context.setVariable("posName",employee.getPosition().getName());
            context.setVariable("jobLevel",employee.getJoblevel().getName());
            context.setVariable("department",employee.getDepartment().getName());
            String mail=templateEngine.process("mail",context);


            helper.setSubject("欢迎入职yeb");
            helper.setTo(employee.getEmail());
            helper.setFrom(mailProperties.getUsername());
            helper.setText(mail,true);
            helper.setSentDate(new Date());
            javaMailSender.send(msg);
            hashOperations.put("mail_log",mId,"OK");
            channel.basicAck(tag,false);

        } catch (MessagingException e) {
            LOGGER.error("邮件发送失败======>{}",e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}