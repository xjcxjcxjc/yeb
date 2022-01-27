package com.xjc.controller;


import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/20 11:39
 */
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/ws/chat")
    public void hanleMessage(Authentication authentication, ChatMsg chatMsg){
        Admin admin= (Admin) authentication.getPrincipal();
        chatMsg.setFrom(admin.getUsername());
        chatMsg.setFormNickName(admin.getName());
        chatMsg.setDate(LocalDateTime.now());
        simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(),"/queue/chat",chatMsg);
    }

}
