package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.dto.RoomPriceNameAndRoomTypeNumDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class RoomManagementController {
    @Autowired
    private RoomManagementService roomManagementService;

    @RequestMapping("/get_room_info_page")
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresUser
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @ResponseBody
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    public ResultJson insRoomTypeAndRoomPrice(MultipartFile file_img,RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
        try{
                String uuto =UUID.randomUUID().toString()+file_img.getOriginalFilename().substring(file_img.getOriginalFilename().lastIndexOf("."));
                String fileName ="/static/admin/login/upload/"+uuto;
                String classPath = ClassUtils.getDefaultClassLoader().getResource("static/admin/login/upload/").getPath()+uuto;
                InputStream is = file_img.getInputStream();
                FileOutputStream fos = new FileOutputStream(classPath);
                byte b[] = new byte[1024 * 1024];
                int length = 0;
                while (-1 != (length = is.read(b))) {
                    fos.write(b, 0, length);
                    fos.flush();
                    fos.close();
            }
            roomTypeAndRoomPriceDTO.setRoomTypeImg(fileName);
            roomManagementService.insRoomTypeAndRoomPrice(roomTypeAndRoomPriceDTO);
            return new ResultJson("200","添加成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","添加失败!",null);
        }
    }
    @RequestMapping("/get_room_info_byid")
    @ResponseBody
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    public ResultJson updRoomTypeAndRoomPriceAll(MultipartFile file_img,RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO){
        try{
            if (file_img!=null) {
                String uuto = UUID.randomUUID().toString() + file_img.getOriginalFilename().substring(file_img.getOriginalFilename().lastIndexOf("."));
                //数据库保存路径
                String fileName = "/static/admin/login/upload/" + uuto;
                //classPath路径
                String classPath = ClassUtils.getDefaultClassLoader().getResource("static/admin/login/upload/").getPath() + uuto;
                InputStream is = file_img.getInputStream();
                FileOutputStream fos = new FileOutputStream(classPath);
                byte b[] = new byte[1024 * 1024];
                int length = 0;
                while (-1 != (length = is.read(b))) {
                    fos.write(b, 0, length);
                    fos.flush();
                    fos.close();
                }
                roomTypeAndRoomPriceDTO.setRoomTypeImg(fileName);
            }
                roomManagementService.updRoomTypeAndRoomPriceAll(roomTypeAndRoomPriceDTO);
                return new ResultJson("200", "修改成功!", null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","修改失败!",null);
        }
    }

    @PostMapping("/del_room_info_byid")
    @ResponseBody
    @RequiresRoles(value={"admin"},logical = Logical.OR)
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
    @RequiresRoles(value={"admin"},logical = Logical.OR)
    public ResultJson delRoomTypeAndRoomPriceById(Long roomTypeNum){
        try{
            roomManagementService.delRoomTypeAndRoomPriceById(roomTypeNum);
            return new ResultJson("200","删除成功!",null);
        }catch(Exception e){
            e.printStackTrace();
            return new ResultJson("400","删除失败!",null);
        }
    }

    @RequiresUser
    @ExceptionHandler(value = {org.apache.shiro.authz.AuthorizationException.class})
    public String authorizationExceptionHandler(Exception e) {
        return "view/admin/error";
    }
}
