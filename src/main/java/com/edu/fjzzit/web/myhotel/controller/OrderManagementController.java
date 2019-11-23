package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailAndRoomOrderDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.service.OrderManagementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class OrderManagementController  {
    @Autowired
    private OrderManagementService orderManagementService;

    @RequestMapping("/get_room_order_page")
    public ModelAndView fingOrderInfoAllByPage(@RequestParam(defaultValue="1")Integer pageNumber, @RequestParam(defaultValue="5")Integer pageSize){
        try{
            Page page = orderManagementService.findRoomOrderDetailAll(pageNumber, pageSize);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("page",page);
            modelAndView.setViewName("/view/admin/order_management_page");
            return modelAndView;
        }catch(Exception e){
            return null;
        }
    }

    @PostMapping("/get_room_order_byid")
    @ApiOperation("根据主键查询订单信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "roomOrderDetailNum",value = "订单详情主键",dataType = "string",required = true),
    })
    public ResultJson findRoomOrderDetailById(Long roomOrderDetailNum){
        try{
            RoomOrderDetailAndRoomOrderDTO roomOrderDetailById = orderManagementService.findRoomOrderDetailById(roomOrderDetailNum);
            return new ResultJson("200","查询成功!",roomOrderDetailById);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","查询失败!",null);
        }
    }
    @PostMapping("/upd_room_order_byid")
    @ApiOperation("根据主键修改订单信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
    })
    public ResultJson updRoomOrderDetailAll(@RequestBody RoomOrderDetailAndRoomOrderDTO roomOrderDetailAndRoomOrderDTO){
        try{
            orderManagementService.updRoomOrderDetailAll(roomOrderDetailAndRoomOrderDTO);
            return new ResultJson("200","修改成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }

    @RequestMapping("/del_room_order_byid")
    @ResponseBody
    public ResultJson delRoomOrderDetailById(Long roomOrderDetailNum) {
        try {
            orderManagementService.delRoomOrderDetailById(roomOrderDetailNum);
            return new ResultJson("200", "删除成功!", null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultJson("400", "删除失败!", null);
        }
    }
}
