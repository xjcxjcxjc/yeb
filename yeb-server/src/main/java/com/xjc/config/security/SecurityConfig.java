package com.xjc.config.security;
/*
 * @Author : XJC
 * @Time : 2021/12/13 13:58
 * @Description :
 *
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjc.config.security.component.*;
import com.xjc.mapper.AdminMapper;
import com.xjc.mapper.RoleMapper;
import com.xjc.pojo.use.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUrlDecisionManager customUrlDecisionManager;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring().antMatchers(
                        "/login",
                "/doc.html",
                "/doc.html/**",
                "/swagger-ui.html",
                "/register",
                "/captcha",
                "/admin/info",
                "/system/basic/login",
                "/logout",
                "/css/**",
                "/js/**",
                "/admin/info",
                "/favicon.ico",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/chat/*",
                "/ws/**"
        );
    }



    /**
     * springsecurity?????????????????????userdetailsservice
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //????????????
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customUrlDecisionManager);
                        object.setSecurityMetadataSource(customFilter);
                        return object;
                    }
                })
                .and()
                .headers()
                .cacheControl();
        //??????jwt ?????????????????????
        http.addFilterBefore(jwtAuthencationTokenFilter(),
                UsernamePasswordAuthenticationFilter.class);
        //????????????????????????????????????????????????
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler())
                .authenticationEntryPoint(getEntryPoint());
    }


    @Override
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            Admin admin=adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username));
            if (null!=admin){
                //??????????????????????????????
                admin.setRoles(roleMapper.getRoles(admin.getId()));
                return admin;
            }
            throw new UsernameNotFoundException("???????????????????????????");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthencationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    public RestAuthorizationEntryPoint getEntryPoint(){
        return new RestAuthorizationEntryPoint();
    }

    public RestfulAccessDeniedHandler restfulAccessDeniedHandler(){
        return new RestfulAccessDeniedHandler();
    }

}
