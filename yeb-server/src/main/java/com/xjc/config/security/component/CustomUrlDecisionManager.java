package com.xjc.config.security.component;


import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * @Author : XJC
 * @Time : 2022/1/4 17:08
 * @Description : 自定义AccessDecisionManager
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        /**
         * needurl访问需要的role，
         * ROLE_LOGIN就是数据库没有匹配的url，
         */
        for (ConfigAttribute configAttribute:configAttributes ) {
            String needUrl= configAttribute.getAttribute();
            if ("ROLE_LOGIN".equals(needUrl)){
                //未登录用户点了不存在的url
                if (authentication instanceof AnonymousAuthenticationToken){
                    throw new AccessDeniedException("未登录");
                }else {
                    throw new AccessDeniedException("你请求的页面不存在");
                }
            }else{
                Collection<? extends GrantedAuthority> grantedAuthorities=
                        authentication.getAuthorities();
                /**
                 * 循环用户的角色有无匹配的
                 */
                for (GrantedAuthority grantedAuthority:grantedAuthorities){
                    if (needUrl.equals(grantedAuthority.getAuthority())){
                        return;
                    }
                }
            }
        }
        throw new  AccessDeniedException("权限不足，请联系管理员");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

}
