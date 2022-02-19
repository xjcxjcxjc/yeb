package com.xjc.controller;


import com.xjc.pojo.use.Admin;
import com.xjc.pojo.use.RespBean;
import com.xjc.service.IAdminService;
import com.xjc.util.FastDFSClient;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Map;

/**
 * @Author : XJC
 * @Description :用户个人信息
 * @create : 2022/1/22 15:53
 */

@RestController
@RequestMapping("/system/admin")
public class AdminInfoController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 两个问题
     * 1、前端发送的username一定要存在，不然新的token就无效，如果username更改，jwt令牌就无效了
     * 2、如果更改了权限，这个令牌还是旧的权限
     *
     * @param admin
     * @param authentication
     * @return
     */
    @ApiOperation("更新当前用户信息")
    @PutMapping("/info")
    public RespBean updateAdmin(@RequestBody Admin admin, Authentication authentication) {
        if (adminService.updateById(admin)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(admin,
                    null, authentication.getAuthorities()));
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败");
    }

    @ApiOperation("更新密码")
    @PutMapping("/pass")
    public RespBean updatePass(@RequestBody Map<String,Object> info){
        String oldPass= (String) info.get("oldPass");
        String newPass= (String) info.get("newPass");
        String checkPass= (String) info.get("checkPass");
        Integer adminId= (Integer) info.get("adminId");
        return adminService.updatePass(oldPass,newPass,checkPass,adminId);
    }

    @ApiOperation("更新头像")
    @PostMapping(value = "/img")
    public RespBean updateAdminUserPace(@RequestParam("file") MultipartFile file ,Integer id,@ApiIgnore Authentication authentication){
        String url= null;
        try {
            url = fastDFSClient.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return RespBean.error("上传失败");
        }
        return adminService.updateAdminUserPace(url,id,authentication);
    }

}
