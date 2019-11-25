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

    @RequestMapping("/get_upd_room_order_byid")
    public ModelAndView findRoomOrderDetailById(Long roomOrderDetailNum){
        try{
            RoomOrderDetailAndRoomOrderDTO roomOrderDetailById = orderManagementService.findRoomOrderDetailById(roomOrderDetailNum);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("orderInfo",roomOrderDetailById);
            modelAndView.setViewName("/view/admin/edit_order_indo");
            return modelAndView;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    @RequestMapping("/upd_room_order_byid")
    @ResponseBody
    public ResultJson updRoomOrderDetailAll(RoomOrderDetailAndRoomOrderDTO roomOrderDetailAndRoomOrderDTO){
        try{
            //计算总价格
            roomOrderDetailAndRoomOrderDTO.setRoomOrderDetailPrice(
                    roomOrderDetailAndRoomOrderDTO.getRoomCount()*roomOrderDetailAndRoomOrderDTO.getRoomPrice());
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
