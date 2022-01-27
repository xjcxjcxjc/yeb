package com.xjc;
/*
 * @Author : XJC
 * @Time : 2021/12/12 15:24
 * @Description :
 *
 */

import com.xjc.config.security.component.JwtProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;


@SpringBootApplication
@MapperScan("com.xjc.mapper")
@EnableWebSecurity
@EnableWebMvc
@EnableConfigurationProperties({JwtProperties.class})
@EnableScheduling
@EnableWebSocketMessageBroker
public class YebApplication {

    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
