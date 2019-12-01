package com.edu.fjzzit.web.myhotel.controller;

import com.edu.fjzzit.web.myhotel.config.ResultJson;
import com.edu.fjzzit.web.myhotel.model.MyException;
import com.edu.fjzzit.web.myhotel.service.CheckInInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room_check_in_manager")
@Api(tags = "房间入住登记管理")
public class RoomCheckInController {

    //测试分支

    @Autowired
    private CheckInInfoService checkInInfoService;

    @PostMapping("/room_check_in")
    @ApiOperation("入住登记")
    @RequiresAuthentication
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token",value = "鉴权Token",dataType = "string",required = true,paramType = "header"),
            @ApiImplicitParam(name = "customerName",value = "客户姓名",dataType = "string",required = true),
            @ApiImplicitParam(name = "customerPhone",value = "联系电话",dataType = "string",required = true),
            @ApiImplicitParam(name = "customerID",value = "身份证号",dataType = "string",required = true),
    })
    public ResultJson roomCheckIn(String customerName,String customerPhone,String customerID){
        try{
            //客户输入相关信息
            String roomNum=checkInInfoService.checkIn(customerName,customerPhone,customerID);
            return new ResultJson("200","入住登记成功!","房间号->"+roomNum);
        }catch(MyException e){
            return new ResultJson(e.getErrorCode(),e.getDescription(),null);
        }catch(Exception e){
            return new ResultJson("400","入住登记失败!",null);
        }
    }
}
