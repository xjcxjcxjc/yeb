package com.xjc.mail;


import com.xjc.pojo.use.MailConstants;
import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;


/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/15 20:01
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    @Bean
    public Queue queue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }


}