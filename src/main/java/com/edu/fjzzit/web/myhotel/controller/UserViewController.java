package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.MyOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.*;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import com.edu.fjzzit.web.myhotel.service.RoomService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresUser;
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
    public ModelAndView bookOrder(@RequestParam("userName")String userName,Long roomTypeId,HttpSession session){
        try{
            RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO=roomManagementService.findRoomTypeAndRoomPriceById(roomTypeId);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("roomTypePrice",roomTypeAndRoomPriceDTO);
            session.setAttribute("user",userName);
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
    public ResultJson orderConfirm(@RequestParam("userName")String userName,Long roomTypeNum,String customerName,String customerPhone,String checkInDate,
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
            Long roomOrderNum=roomService.reserveRoomM(roomOrderDTO,userName);

            return new ResultJson("200","预定成功!",roomOrderNum);
        }catch(Exception e){
            return new ResultJson("400","预定失败!",null);
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

    /**
     * 用户中心->我的订单模块
     * @return
     */
    @RequestMapping("/use_center_order")
    public ModelAndView myOrder(@RequestParam("userName")String userName,HttpSession session){
        session.setAttribute("user",userName);
        ModelAndView modelAndView = new ModelAndView();
        try {
            //1.查询订单信息->入住人，手机号,订单状态
            List<MyOrderDTO> myOrderDTOList = roomService.findUserOrderByCustomerName(userName);
            //2.根据订单详细信息的房型流水号，查询房间类型->房型名，房型图片
            Long roomTypeNum;//存储房间类型流水号
            String  roomTypeImg="/static/admin/login/images/company1.jpg";//存储房图
            for (MyOrderDTO myOrderDTO : myOrderDTOList
            ) {
                for (RoomOrderDetailDTO roomOrderDetailDTO : myOrderDTO.getRoomOrderDetailDTOList()
                ) {
                    String roomPriceName=roomOrderDetailDTO.getRoomPriceName();
                    //获取床型
                    String bedType=roomPriceName.substring(roomOrderDetailDTO.getRoomTypeName().length());
                    //获取房间类型流水号
                    roomTypeNum = roomService.findRoomTypeNum(roomOrderDetailDTO.getRoomTypeName(),bedType);
                    //获取房图
                    roomTypeImg= roomService.findRoomTypeImgByRoomTypeNum(roomTypeNum);
                }
            }

            modelAndView.addObject("myOrderDTOList", myOrderDTOList);
            modelAndView.addObject("roomTypeImg", roomTypeImg);
            modelAndView.setViewName("/view/home/account/index");
        }catch(MyException e){
            System.out.println("e_code->"+e.getErrorCode()+"e_mess->"+e.getDescription());
            modelAndView.addObject("myOrderDTOList", null);
            modelAndView.setViewName("/view/home/account/index");
        }catch (Exception e){
            e.printStackTrace();
            modelAndView.addObject("myOrderDTOList", null);
            modelAndView.setViewName("/view/home/account/index");
        }finally {
            return modelAndView;
        }
    }

    /**
     * 取消订单
     * @param orderNum
     * @return
     */
    @RequestMapping("/cancel_order")
    @ResponseBody
    public ResultJson cancelOrder(Long orderNum){
        try{
            roomService.cancelOrder(orderNum);
            return new ResultJson("200","取消成功!",null);
        }catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","取消失败!",null);
        }
    }
}
