package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.MyException;
import com.edu.fjzzit.web.myhotel.model.RoomPrice;
import com.edu.fjzzit.web.myhotel.model.RoomType;
import com.edu.fjzzit.web.myhotel.model.UserInfo;
import com.edu.fjzzit.web.myhotel.service.CheckInInfoService;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import com.edu.fjzzit.web.myhotel.service.RoomService;
import com.edu.fjzzit.web.myhotel.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminViewController {
    @Autowired
    private CheckInInfoService checkInInfoService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomManagementService roomManagementService;

    @Autowired
    private RoomService roomService;

    @RequestMapping("{page}")
    public String main(@PathVariable String page) {
        return "/view/admin/"+page;
    }

    @RequestMapping("/login_list")
    @ResponseBody
    public ResultJson adminLogin(String adminName, String adminPassword,HttpSession httpSession){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(adminName, adminPassword);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            httpSession.setAttribute("admin",user.getUserName());
            return new ResultJson("200","登录成功!",null);
        }catch(Exception e){
            return new ResultJson("500","登录失败!",null);
        }
    }

    @RequestMapping("/get_main")
    @RequiresUser
    public String get_main(){
        return "/view/admin/main";
    }

    @RequestMapping("/checkIn")
    @ResponseBody
    @RequiresUser
    public ResultJson checkIn(String customerName, String customerPhone, String customerId){
        try {
            String s = checkInInfoService.checkIn(customerName, customerPhone, customerId);
            return new ResultJson("200","入住成功",s);
        } catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResultJson("400","入住失败",null);
        }
    }

    @RequestMapping("/upd_password")
    @ResponseBody
    @RequiresUser
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
    @RequestMapping("/room_check_out")
    @ResponseBody
    @RequiresUser
    public ResultJson roomCheckOut(String roomNum){
        try{
            //输入房号，办理退房
            checkInInfoService.checkOut(roomNum);
            return new ResultJson("200",roomNum+"->办理退房成功!",null);
        }catch(MyException e){
            e.printStackTrace();
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400",roomNum+"->办理退房失败!",null);
        }
    }

    /**
     * 前端页面实时计算房间总价
     * @return
     */
    @RequestMapping("/calculate_roomDetailPrice")
    @ResponseBody
    @RequiresUser
    public ResultJson calculateRoomDetailPrice(Integer roomPrice,Integer roomCount,String checkInDate,String checkOutDate){
        try{
            Integer roomDetailPrice=roomService.calculateRoomDetailPrice(roomPrice,roomCount,checkInDate,checkOutDate);

            return new ResultJson("200","房间总价计算成功!",roomDetailPrice);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","房间总价计算失败!",null);
        }
    }
    /**
     * 查询订单所需参数
     * @return
     */
    @RequestMapping("/get_price_byTypeNum")
    @ResponseBody
    @RequiresUser
    public RoomTypeAndRoomPriceDTO calculateRoomDetailPrice(Long roomTypeNum){
        try{
            RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO=roomManagementService.findRoomTypeAndRoomPriceById(roomTypeNum);
            return roomTypeAndRoomPriceDTO;
        }catch(Exception e){
            return null;
        }
    }
    /**
     * 确认预定信息
     * @return
     */
    @RequestMapping("/order_confirm")
    @ResponseBody
    @RequiresUser
    public ResultJson orderConfirm(Long roomTypeNum,String customerName,String customerPhone,String checkInDate,
                                   String checkOutDate,Integer roomCount,String roomPriceName){
        try {
            //1.查找房间类型名称->roomType
            RoomType roomType=roomService.findByRoomTypeNum(roomTypeNum);
            //2.查找房间套餐信息->roomPrice
            RoomPrice roomPrice=roomService.findByRoomPriceName(roomPriceName);
            //3.生成订单信息
            RoomOrderDetailDTO roomOrderDetailDTO=new RoomOrderDetailDTO();
            roomOrderDetailDTO.setRoomTypeName(roomType.getRoomTypeName());
            roomOrderDetailDTO.setRoomPriceName(roomPriceName);
            roomOrderDetailDTO.setBreakfastType(roomPrice.getBreakfastType());
            roomOrderDetailDTO.setRoomPrice(roomPrice.getRoomPrice());
            roomOrderDetailDTO.setRoomCount(roomCount);
            roomOrderDetailDTO.setCheckInDate(checkInDate);
            roomOrderDetailDTO.setCheckOutDate(checkOutDate);

            List<RoomOrderDetailDTO> roomOrderDetailDTOList=new ArrayList<>();
            roomOrderDetailDTOList.add(roomOrderDetailDTO);

            RoomOrderDTO roomOrderDTO=new RoomOrderDTO();
            roomOrderDTO.setCustomerName(customerName);
            roomOrderDTO.setCustomerPhone(customerPhone);
            roomOrderDTO.setRoomOrderDetailDTOList(roomOrderDetailDTOList);
            //4.预定房间
            Long roomOrderNum=roomService.reserveRoom(roomOrderDTO);

            return new ResultJson("200","预定成功!请重新为客户办理入住！",roomOrderNum);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","预定失败!",null);
        }
    }

}
