package com.xjc.controller;
/*
 * @Author : XJC
 * @Time : 2021/12/13 9:02
 * @Description :
 *
 */
import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.AdminLoginParam;
import com.xjc.pojo.use.RespBean;
import com.xjc.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@RestController
@Api(value = "登录控制器")
@RequestMapping("/system/basic")
public class LoginController {

    @Autowired
    IAdminService iAdminService;


    @PostMapping("/register")
    public String register(Admin admin){
        int a=iAdminService.addAdmin(admin);
        return String.valueOf(a) ;
    }


    @PostMapping("/login")
    public RespBean login(AdminLoginParam adminLoginParam, HttpServletRequest httpServletRequest){
        return iAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),
                adminLoginParam.getCode(),httpServletRequest);
    }

    @GetMapping("/admin/info")
    public Admin info(Principal principal){
        if (null==principal){
            return null;
        }
        String username = principal.getName();
        Admin admin = iAdminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(iAdminService.getRoles(admin.getId()));
        return admin;
    }



    /**
     * 只要前端把token注销了就行，
     * 这里只需要返回一个200就行
     * @return
     */
    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功！");
    }


}
