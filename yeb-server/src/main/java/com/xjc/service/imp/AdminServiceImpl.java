package com.xjc.service.imp;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xjc.mapper.AdminMapper;
import com.xjc.mapper.RoleMapper;
import com.xjc.pojo.use.RespBean;
import com.xjc.config.security.component.JwtTokenUtil;
import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.Role;
import com.xjc.service.IAdminService;
import com.xjc.util.AdminUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjc
 * @since 2021-12-12
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {


    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    AdminMapper adminMapper;
    //数据库只存储加密后的密码
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RoleMapper roleMapper;
    /**
     * 登录逻辑：
     * 验证码，验证用户名，密码
     * 生成token，返回token
     * @param userName
     * @param password
     * @param code
     * @param httpServletRequest
     * @return
     */
    @Override
    public RespBean login(String userName, String password, String code, HttpServletRequest httpServletRequest) {
        String chapcha= (String) httpServletRequest.getSession().getAttribute("captcha");
        if (null==chapcha||!chapcha.equals(code)){
            return RespBean.error("验证码不正确");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("用户被禁止访问");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationTok
                =new UsernamePasswordAuthenticationToken(userDetails,null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationTok);


        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",jwtTokenUtil.getTokenHead());

        return RespBean.success("登录成功",tokenMap);
    }

    @Override
    public Admin getAdminByUserName(String userName) {
        Admin admin=adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",userName)
        .eq("enabled",true));
        admin.setPassword(null);
        return admin;
    }

    @Override
    public int addAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setEnabled(true);
        return adminMapper.insert(admin);
    }

    /**
     * 根据用户id获取需要的角色
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

    @Override
    public List<Admin> getAllAdmin(String keywords) {
        return adminMapper.getAllAdmin(AdminUtils.getAdmin().getId(),keywords);
    }

    @Override
    public RespBean updatePass(String oldPass, String newPass, String checkPass, Integer adminId) {
        Admin admin= adminMapper.selectById(adminId);
        if (null==admin){
            return RespBean.error("用户不存在");
        }
        if (passwordEncoder.matches(oldPass,admin.getPassword())){
            if (StringUtils.equals(newPass,checkPass)){
                admin.setPassword(passwordEncoder.encode(newPass));
                if (1==adminMapper.updateById(admin)){
                    return RespBean.success("更新成功");
                }else {
                    return RespBean.error("数据库错误");
                }

            }else{
                return RespBean.error("两次输入密码不同");
            }
        }
        return RespBean.error("密码错误");
    }

    @Override
    public RespBean updateAdminUserPace(String url, Integer id, Authentication authentication) {
        Admin admin=adminMapper.selectById(id);
        if (null!=admin){
            admin.setUserFace(url);
            Integer result= adminMapper.updateById(admin);
            if (1==result){
                Admin principal=((Admin) authentication.getPrincipal());
                principal.setUserFace(url);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities()));
                return RespBean.success(url);
            }
        }
        return RespBean.error("更新失败");
    }

}
