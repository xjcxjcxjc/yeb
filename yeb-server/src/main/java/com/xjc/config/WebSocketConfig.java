package com.xjc.config;


import com.alibaba.druid.util.StringUtils;
import com.xjc.config.security.component.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Author : XJC
 * @Description :
 * @create : 2022/1/20 9:11
 */
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1、将/ws/ep注册为stomp的端点，用户连接这个端点就可以进行websocket的通讯，支持socketJS
         * 2、允许跨域
         * 3、支持socketJS访问
         */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 方法用于配置客户端传入信息的通道
     * 一般情况不用配置，但是项目使用了jwt令牌，需要在接收前加入jwt token的验证
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {

        /**
         * 增加了一个拦截器，设置令牌
         */
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor stompHeaderAccessor=
                        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                /**
                 * 判断是否连接
                 */
                if (StompCommand.CONNECT.equals(stompHeaderAccessor.getCommand())){
                    String header=stompHeaderAccessor.getFirstNativeHeader(jwtTokenUtil.getTokenHeader());
                    String userName=jwtTokenUtil.getUserNameFromToken(
                            header.substring(jwtTokenUtil.getTokenHead().length()));
                    /**
                     * 判断是否正确token
                     */
                    if (!StringUtils.isEmpty(userName)){
                        UserDetails userDetails=userDetailsService.loadUserByUsername(userName);
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                                new UsernamePasswordAuthenticationToken(userDetails,null,
                                        userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        stompHeaderAccessor.setUser(usernamePasswordAuthenticationToken);
                    }
                }
                return message;
            }
        });
    }


    /**
     * 配置消息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理域，服务端通过这个域向客户端发送消息
        registry.enableSimpleBroker("/queue");
    }

}
