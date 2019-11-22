package com.edu.fjzzit.web.myhotel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @RequestMapping("/hello")
    public String adminLogin(String userName, String password){
        System.out.println("-------------");
        return "/view/admin/login";
    }

}
