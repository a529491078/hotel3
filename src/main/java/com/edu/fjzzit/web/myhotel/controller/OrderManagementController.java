package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailAndRoomOrderDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.service.OrderManagementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class OrderManagementController  {
    @Autowired
    private OrderManagementService orderManagementService;

    /**
     * 显示所有订单信息
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    @RequestMapping("/get_room_order_page")
    @RequiresRoles(value={"admin","manager"},logical = Logical.OR)
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

    /**
     * 根据主键回显订单信息
     * @param roomOrderDetailNum 主键
     * @return
     */
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

    /**
     * 根据主键修改订单信息
     * @param roomOrderDetailAndRoomOrderDTO 主键
     * @return
     */
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

    /**
     * 根据主键删除订单信息
     * @param roomOrderDetailNum 主键
     * @return
     */
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

    /**
     * 权限不够
     * @param e
     * @return
     */
    @RequiresUser
    @ExceptionHandler(value = {org.apache.shiro.authz.AuthorizationException.class})
    public String authorizationExceptionHandler(Exception e) {
        return "view/admin/error";
    }
}
