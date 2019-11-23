package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("{page}")
    public String main(@PathVariable String page) {
        return "/view/admin/"+page;
    }

    @RequestMapping("/login_list")
    @ResponseBody
    public ResultJson adminLogin(String adminName, String adminPassword){
        String dates=null;
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(adminName, adminPassword);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            dates="success";
            return new ResultJson("200","登录成功!",dates);
        }catch(Exception e){
            return new ResultJson("500","登录失败!",null);
        }
    }
}
