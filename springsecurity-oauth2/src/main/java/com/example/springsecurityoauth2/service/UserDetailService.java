package com.example.springsecurityoauth2.service;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author : XJC
 * @Time : 2021/12/19 9:02
 * @Description :
 */
@Service
public class UserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=new User("admin","123456", AuthorityUtils.
                commaSeparatedStringToAuthorityList("admin"));

        return user;
    }
}
