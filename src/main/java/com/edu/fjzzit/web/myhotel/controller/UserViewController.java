package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomPrice;
import com.edu.fjzzit.web.myhotel.model.RoomType;
import com.edu.fjzzit.web.myhotel.model.UserInfo;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import com.edu.fjzzit.web.myhotel.service.RoomService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private RoomManagementService roomManagementService;

    @Autowired
    private RoomService roomService;

    /**
     * 首页
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView RommInfoDisplay(@RequestParam("userName") String userName,@RequestParam(defaultValue="1")Integer pageNumber,
                                        @RequestParam(defaultValue="5")Integer pageSize,HttpSession session){
        try{
            //@RequestParam("userName") String userName,
            Page page = roomManagementService.findRoomTypeAndRoomPriceAll(pageNumber,pageSize);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("page",page);
            modelAndView.setViewName("/view/home/index/index");
            session.setAttribute("user",userName);

            return modelAndView;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 预定界面显示
     * @return
     */
    @RequestMapping("/book_order")
    public ModelAndView bookOrder(Long roomTypeId){
        try{
            RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO=roomManagementService.findRoomTypeAndRoomPriceById(roomTypeId);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("roomTypePrice",roomTypeAndRoomPriceDTO);
            modelAndView.setViewName("/view/home/account/book_order");
            return modelAndView;
        }catch(Exception e){
            return null;
        }
    }

    /**
     * 前端页面实时计算房间总价
     * @return
     */
    @RequestMapping("/calculate_roomDetailPrice")
    @ResponseBody
    public ResultJson calculateRoomDetailPrice(Integer roomPrice,Integer roomCount,String checkInDate,String checkOutDate){
        try{
            Integer roomDetailPrice=roomService.calculateRoomDetailPrice(roomPrice,roomCount,checkInDate,checkOutDate);

            return new ResultJson("200","房间总价计算成功!",roomDetailPrice);
        }catch(Exception e){
            return new ResultJson("400","房间总价计算失败!",null);
        }
    }

    /**
     * 确认预定信息
     * @return
     */
    @RequestMapping("/order_confirm")
    @ResponseBody
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

            return new ResultJson("200","预定成功!",roomOrderNum);
        }catch(Exception e){
            return new ResultJson("400","预定失败!",null);
        }
    }

    /**
     * 用户中心
     * @return
     */
    @RequestMapping("/use_center")
    public String userCenter(){
        return "/view/home/account/index";
    }

    /**
     * 注销登录
     * @return
     */
    @RequestMapping("/log_out")
    @ResponseBody
    public ResultJson logOut(HttpSession session){
        try {
            session.setAttribute("user",null);

            return new ResultJson("200","注销成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","注销失败!",null);
        }
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/user_login")
    @ResponseBody
    public ResultJson UserLogin(String userName, String password){
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            return new ResultJson("200","登录成功!",null);
        }catch(Exception e){
            return new ResultJson("500","登录失败!",null);
        }
    }

    /**
     * 转跳到登录页面
     * @return
     */
    @RequestMapping("/user_to_login")
    public String UserToLogin(){
        return "/view/home/index/login";
    }
    /**
     * 注册
     * @return
     */
    @RequestMapping("/user_register")
    public String UserRegister(){
        return "/view/home/index/reg";
    }
}
