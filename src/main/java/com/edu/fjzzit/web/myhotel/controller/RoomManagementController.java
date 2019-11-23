package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomPriceNameAndRoomTypeNumDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/ins_room_type_price_info")
    @ApiOperation("添加客房套餐信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
    })
    public ResultJson insRoomTypeAndRoomPrice(@RequestBody RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
        System.out.println(roomTypeAndRoomPriceDTO);
        try{
            roomManagementService.insRoomTypeAndRoomPrice(roomTypeAndRoomPriceDTO);
            return new ResultJson("200","添加成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","添加失败!",null);
        }
    }

    @RequestMapping("/get_room_info_byid")
    @ResponseBody
    public ResultJson findRoomInfoById(Integer roomNum){
        try{
            RoomInfo roomInfoById = roomManagementService.findRoomInfoById(roomNum);
            return new ResultJson("200", "查询成功!", roomInfoById);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","查询失败!",null);
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

    @PostMapping("/get_room_type_price_byid")
    @ApiOperation("根据ID查询客房套餐信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "roomTypeNum",value = "客房ID",dataType = "string",required = true),
    })
    public ResultJson findRoomTypeAndRoomPriceById(Long roomTypeNum){
        try{
            RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceById = roomManagementService.findRoomTypeAndRoomPriceById(roomTypeNum);
            return new ResultJson("200","查询成功!",roomTypeAndRoomPriceById);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","查询失败!",null);
        }
    }



    @PostMapping("/upd_room_info_byid")
    @ApiOperation("根据ID修改客房信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
    })
    public ResultJson updRoomInfoAll(@RequestBody RoomInfo roomInfo){
        try{
            roomManagementService.updRoomInfoAll(roomInfo);
            return new ResultJson("200","修改成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }

    @PostMapping("/upd_room_type_price_byid")
    @ApiOperation("根据ID修改客房套餐信息")
    @RequiresRoles(value={"admin"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
    })
    public ResultJson updRoomTypeAndRoomPriceAll(@RequestBody RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
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
