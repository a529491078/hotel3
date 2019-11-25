package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomPriceNameAndRoomTypeNumDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.channels.MulticastChannel;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class RoomManagementController {
    @Autowired
    private RoomManagementService roomManagementService;

    @RequestMapping("/get_room_info_page")
    public ModelAndView fingRoomInfoAllByPage(@RequestParam(defaultValue="1")Integer pageNumber, @RequestParam(defaultValue="5")Integer pageSize){
        try{
            Page page = roomManagementService.findRoomInfoAll(pageNumber, pageSize);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("page",page);
            modelAndView.setViewName("/view/admin/room_management_page");
            return modelAndView;
        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/get_room_type_price_page")
    public ModelAndView findRoomTypeAndRoomPriceAll(@RequestParam(defaultValue="1")Integer pageNumber, @RequestParam(defaultValue="5")Integer pageSize){
        try{
            Page page = roomManagementService.findRoomTypeAndRoomPriceAll(pageNumber,pageSize);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("page",page);
            modelAndView.setViewName("/view/admin/room_type_price_management_page");
            return modelAndView;
        }catch(Exception e){
            return null;
        }
    }

    @RequestMapping("/get_roomPriceName_roomTypeNum")
    @ResponseBody
    public List<RoomPriceNameAndRoomTypeNumDTO> findRoomPriceNameAll(){
        List<RoomPriceNameAndRoomTypeNumDTO> roomPriceNameAll=null;
        try{
            roomPriceNameAll = roomManagementService.findRoomPriceNameAll();
            return roomPriceNameAll;
        }catch(Exception e){
            return roomPriceNameAll;
        }
    }

    @RequestMapping("/ins_room_info")
    @ResponseBody
    public ResultJson insRoomInfoById(RoomInfo roomInfo){
        try{
            roomManagementService.insRoomInfo(roomInfo);
            return new ResultJson("200","添加成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","添加失败!",null);
        }
    }

    @RequestMapping("/ins_room_type_price_info")
    @ResponseBody
    public ResultJson insRoomTypeAndRoomPrice(MultipartFile file, RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
        try{
            //roomManagementService.insRoomTypeAndRoomPrice(roomTypeAndRoomPriceDTO);
            System.out.println(file.getOriginalFilename());
            System.out.println(roomTypeAndRoomPriceDTO);
            return new ResultJson("200","添加成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","添加失败!",null);
        }
    }
    @RequestMapping("/get_room_info_byid")
    @ResponseBody
    public ResultJson findRoomInfoById(Integer roomId){
        try{
            RoomInfo roomInfoById = roomManagementService.findRoomInfoById(roomId);
            return new ResultJson("200", "查询成功!", roomInfoById);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","查询失败!",null);
        }
    }

    @RequestMapping("/get_upd_room_info_byid")
    public ModelAndView findUpdRoomInfoById(Integer roomId){
        try{
            RoomInfo roomInfoById = roomManagementService.findRoomInfoById(roomId);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("roomInfo",roomInfoById);
            modelAndView.setViewName("/view/admin/edit_room_info");
            return modelAndView;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断房间名是否重复
     * @param roomNum
     * @return
     */
    @RequestMapping("/get_not_room_id_byid")
    @ResponseBody
    public ResultJson findNotRoomIdById(String roomNum,String buildingNum){
        try{
            int notRoomIdById = roomManagementService.findNotRoomIdById(roomNum,buildingNum);
            if (notRoomIdById==0) {
                return new ResultJson("200", "查询成功!", null);
            }else {
                return new ResultJson("400","楼栋房间号已存在!",null);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","楼栋房间号已存在!",null);
        }
    }

    @RequestMapping("/get_upd_room_type_price_byid")
    public ModelAndView findRoomTypeAndRoomPriceById(Long roomTypeNum){
        try{
            RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceById = roomManagementService.findRoomTypeAndRoomPriceById(roomTypeNum);
            ModelAndView modelAndView=new ModelAndView();
            modelAndView.addObject("roomTypePrice",roomTypeAndRoomPriceById);
            modelAndView.setViewName("/view/admin/edit_room_type_price");
            return modelAndView;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    @RequestMapping("/upd_room_info_byid")
    @ResponseBody
    public ResultJson updRoomInfoAll(RoomInfo roomInfo){
        try{
            roomManagementService.updRoomInfoAll(roomInfo);
            return new ResultJson("200","修改成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }

    @RequestMapping("/upd_room_type_price_byid")
    @ResponseBody
    public ResultJson updRoomTypeAndRoomPriceAll(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
        try{
            roomManagementService.updRoomTypeAndRoomPriceAll(roomTypeAndRoomPriceDTO);
            return new ResultJson("200","修改成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }

    @PostMapping("/del_room_info_byid")
    @ResponseBody
    public ResultJson delRoomInfoById(Integer roomId){
        try{
            roomManagementService.delRoomInfoById(roomId);
            return new ResultJson("200","删除成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","删除失败!",null);
        }
    }

    @RequestMapping("/del_room_type_price_byid")
    @ResponseBody
    public ResultJson delRoomTypeAndRoomPriceById(Long roomTypeNum){
        try{
            roomManagementService.delRoomTypeAndRoomPriceById(roomTypeNum);
            return new ResultJson("200","删除成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","删除失败!",null);
        }
    }
}
