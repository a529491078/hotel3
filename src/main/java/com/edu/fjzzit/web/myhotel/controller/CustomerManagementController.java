package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.model.CustomerInfo;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.service.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class CustomerManagementController {
    @Autowired
    private CustomerManagementService customerManagementService;

    @RequestMapping("/get_customer_info_page")
    public ModelAndView findCustomerInfoAll(@RequestParam(defaultValue="1")Integer pageNumber, @RequestParam(defaultValue="5")Integer pageSize){
        try {
            Page customerInfoAll = customerManagementService.findCustomerInfoAll(pageNumber, pageSize);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("page",customerInfoAll);
            modelAndView.setViewName("/view/admin/customer_info_management_page");
            return modelAndView;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/del_customer_info_byid")
    @ResponseBody
    public ResultJson delCustomerInfoById(Long customerNum) {
        try {
            customerManagementService.delCustomerInfoById(customerNum);
            return new ResultJson("200", "删除成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultJson("400", "删除失败!", null);
        }
    }
    @RequestMapping("/get_customer_info_byid")
    public ModelAndView findCustomerInfoById(Long customerNum){
        try{
            CustomerInfo customerInfoById = customerManagementService.findCustomerInfoById(customerNum);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("customerInfo",customerInfoById);
            modelAndView.setViewName("/view/admin/edit_customer_info");
            return modelAndView;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("/get_not_customer_info_byid")
    @ResponseBody
    public ResultJson findNotCustomerInfoById(String customerNickName){
        try{
            Long i = customerManagementService.findNotCustomerInfoById(customerNickName);
            if (i==0) {
                return new ResultJson("200", "查询成功!", null);
            }else {
                return new ResultJson("400","昵称已存在!",null);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","查询失败!",null);
        }
    }
    @RequestMapping("/upd_customer_info_byid")
    @ResponseBody
    public ResultJson updCustomerInfoAll(CustomerInfo customerInfo){
        try{
            customerManagementService.updCustomerInfoAll(customerInfo);
            return new ResultJson("200","修改成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }
    @RequestMapping("/ins_customer_info")
    @ResponseBody
    public ResultJson insCustomerInfo(CustomerInfo customerInfo){
        try{
            customerManagementService.insCustomerInfo(customerInfo);
            return new ResultJson("200","添加成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","添加失败!",null);
        }
    }
}
