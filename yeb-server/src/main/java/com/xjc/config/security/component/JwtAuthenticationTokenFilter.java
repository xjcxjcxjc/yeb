package com.xjc.config.security.component;
/*
 * @Author : XJC
 * @Time : 2021/12/14 9:35
 * @Description :Jwt认证过滤器，为那些存在token但是没有登录的用户提供登录
 *
 */


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {



    @Autowired
    JwtTokenUtil jwtTokenUtil;



    @Autowired
    UserDetailsService userDetailsService;


    /**
     * 是否有jwt token
     * 是否有username
     * token是否有效时间
     * 是否登录
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String header= request.getHeader(jwtTokenUtil.getTokenHeader());
        //token存在用户名但未登录
        if (null!=header&&header.startsWith(jwtTokenUtil.getTokenHead())){
            String token= header.substring(jwtTokenUtil.getTokenHead().length()+1);
            String userName= jwtTokenUtil.getUserNameFromToken(token);
            if (null!=userName&&null== SecurityContextHolder.getContext().getAuthentication()){
                UserDetails userDetails=userDetailsService.loadUserByUsername(userName);


                //验证token是否有效，重新设置用户对象
                if (jwtTokenUtil.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToke
                            =new UsernamePasswordAuthenticationToken(userDetails
                    ,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToke.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToke);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
