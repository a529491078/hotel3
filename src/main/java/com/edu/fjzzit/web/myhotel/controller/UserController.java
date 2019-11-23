package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.model.MyException;
import com.edu.fjzzit.web.myhotel.model.UserInfo;
import com.edu.fjzzit.web.myhotel.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName",value = "用户名",dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "string",required = true)
    })
    /**
     * 登录模块
     */
    public ResultJson login(String userName,String password){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            String sessionId=subject.getSession().getId().toString();//获取sessionId，实现授权
            HashMap<String,String> map=new HashMap<>();
            map.put("token",sessionId);
            return new ResultJson("200","登录成功!",map);
        }catch(UnknownAccountException e){
            //账户不存在
            return new ResultJson("400","用户名或密码错误!",null);
        }catch(DisabledAccountException e){
            //账户被禁用
            return new ResultJson("400","该账户已被禁用!",null);
        }catch(IncorrectCredentialsException e){
            //账户被禁用
            return new ResultJson("400","用户名或密码错误!",null);
        }catch (Exception e){
            return new ResultJson("400","未知错误，请联系管理员!",null);
        }
    }

    @PostMapping("/need_login")
    public ResultJson needLogin(){
        return new ResultJson("400","请先登陆!",null);
    }

    @PostMapping("/unauth")
    public ResultJson unauth(){
        return new ResultJson("400","您不具备相应权限!",null);
    }

    @PostMapping("/add_user")
    @ApiOperation("添加用户")
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户名",dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "string",required = true),
            @ApiImplicitParam(name = "roleName",value = "角色名",dataType = "string",required = true)
    })
    public ResultJson addUser(String userName,String password,String roleName){
        try {
            userService.addUser(userName, password, roleName);
            return new ResultJson("200","添加用户成功!",null);
        }catch(Exception e){
            return new ResultJson("400","添加用户失败!",null);
        }
    }

    @PostMapping("/delete_user")
    @ApiOperation("删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户名",dataType = "string",required = true),
    })
    public ResultJson deleteUser(String userName){
        try{
            userService.delUser(userName);
            return new ResultJson("200","删除用户成功!",null);
        }catch(Exception e){
            return new ResultJson("400","删除用户失败!",null);
        }
    }

    @PostMapping("/reset_password")
    @ApiOperation("管理员重置密码")
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户名",dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "新密码",dataType = "string",required = true),
    })
    public ResultJson resetPassword(String userName,String password){
        try{
            userService.resetPassword(userName,password);
            return new ResultJson("200","管理员重置密码成功!",null);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","管理员重置密码失败!",null);
        }
    }

    @PostMapping("/reset_self_password")
    @ApiOperation("用户修改密码")
    @RequiresAuthentication
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "oldPwd",value = "旧密码",dataType = "string",required = true),
            @ApiImplicitParam(name = "password",value = "新密码",dataType = "string",required = true),
    })
    public ResultJson resetSelfPassword(String oldPwd,String password){
        try {
            userService.resetSelfPassword(oldPwd, password);
            return new ResultJson("200", "用户修改密码成功!", null);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","用户修改密码失败!",null);
        }
    }

    @PostMapping("/disable_Account")
    @ApiOperation("禁用账户")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户",dataType = "string",required = true),
    })
    public ResultJson disableAccount(String userName){
        try{
            userService.disableAccount(userName);
            return new ResultJson("200","禁用用户成功!",null);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","禁用用户失败!",null);
        }
    }

    @PostMapping("/enable_Account")
    @ApiOperation("恢复账户")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户",dataType = "string",required = true),
    })
    public ResultJson enableAccount(String userName){
        try{
            userService.enableAccount(userName);
            return new ResultJson("200","恢复用户成功!",null);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","恢复用户失败!",null);
        }
    }

    @PostMapping("/reset_Roles")
    @ApiOperation("管理员重置角色")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户",dataType = "string",required = true),
            @ApiImplicitParam(name = "roleName",value = "角色",dataType = "string",required = true),
    })
    public ResultJson resetRoles(String userName,String roleName){
        try{
            userService.resetRoles(userName,roleName);
            return new ResultJson("200","重置角色成功!",null);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","重置角色失败!",null);
        }
    }

    @PostMapping("/find_Self_UserInfo")
    @ApiOperation("用户查询个人信息")
    @RequiresAuthentication
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
    })
    public ResultJson findSelfUserInfo(){
        try{
            Map<String,Object> userInfo=userService.findSelfUserInfo();
            return new ResultJson("200","查询信息成功!",userInfo);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","查询信息失败!",null);
        }
    }

    @PostMapping("/find_All_UserInfo")
    @ApiOperation("管理员查询用户信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "userName",value = "用户",dataType = "string",required = true),
    })
    public ResultJson findAllUserInfo(String userName){
        try{
            List<UserInfo> userAllInfo=userService.findAllUserInfo(userName);
            return new ResultJson("200","查询信息成功!",userAllInfo);
        }
        catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }
        catch(Exception e){
            return new ResultJson("400","查询信息失败!",null);
        }
    }
}
