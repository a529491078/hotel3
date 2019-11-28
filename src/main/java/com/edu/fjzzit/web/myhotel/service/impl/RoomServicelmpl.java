package com.edu.fjzzit.web.myhotel.service.impl;

import com.edu.fjzzit.web.myhotel.dto.FreeRoomDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailDTO;
import com.edu.fjzzit.web.myhotel.mapper.*;
import com.edu.fjzzit.web.myhotel.model.*;
import com.edu.fjzzit.web.myhotel.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoomServicelmpl implements RoomService {

    @Autowired
    private FreeRoomCalendarMapper freeRoomCalendarMapper;

    @Autowired
    private RoomPriceMapper roomPriceMapper;

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Autowired
    private RoomOrderMapper roomOrderMapper;

    @Autowired
    private RoomOrderDetailMapper roomOrderDetailMapper;

    /**
     * 查找空房
     *
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    @Override
    public List<FreeRoomDTO> findFreeRoom(String checkInDate, String checkOutDate) {

        try {

            List<FreeRoomDTO> freeRoomDTOList = new ArrayList<>();
            FreeRoomDTO freeRoomDTO;

            List<FreeRoomCalendar> freeRoomCalendarList = freeRoomCalendarMapper.selectByCheckDate(checkInDate, checkOutDate);
            for (FreeRoomCalendar frc : freeRoomCalendarList
            ) {
                Integer freeCount = frc.getFreeCount();//获取最小空房数
                Long roomTypeNum = frc.getRoomTypeNum();//获取房间类型流水号

//                System.out.println("freeCount->"+freeCount);
//                System.out.println("roomTypeNum->"+roomTypeNum);

                List<FreeRoomDTO> freeRoomDTOS = roomPriceMapper.queryFreeRoomDTOByRoomTypeNum(roomTypeNum);
                for (FreeRoomDTO frd : freeRoomDTOS
                ) {
                    freeRoomDTO = new FreeRoomDTO();

                    String roomTypeName=frd.getRoomTypeName();
                    String breakfastType = frd.getBreakfastType();
                    String roomPriceName = frd.getRoomPriceName();
                    String bedType = frd.getBedType();
                    Integer roomPrice = frd.getRoomPrice();

                    freeRoomDTO.setRoomTypeName(roomTypeName);
                    freeRoomDTO.setBreakfastType(breakfastType);
                    freeRoomDTO.setRoomPriceName(roomPriceName);
                    freeRoomDTO.setBedType(bedType);
                    freeRoomDTO.setRoomPrice(roomPrice);
                    freeRoomDTO.setFreeCount(freeCount);

//                    System.out.println("roomTypeName->"+roomTypeName);
//                    System.out.println("breakfastType->"+breakfastType);
//                    System.out.println("roomPriceName->"+roomPriceName);
//                    System.out.println("bedType->"+bedType);
//                    System.out.println("roomPrice->"+roomPrice);

                    freeRoomDTOList.add(freeRoomDTO);
                }
            }

            return freeRoomDTOList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 订房
     * @param roomOrderDTO
     * @return
     */
    @Override
    public Long reserveRoom(RoomOrderDTO roomOrderDTO) {
        RoomOrder roomOrder;
        RoomOrderDetail roomOrderDetail;

        String customerName=roomOrderDTO.getCustomerName();
        String customerPhone= roomOrderDTO.getCustomerPhone();

        roomOrder=new RoomOrder();
        roomOrder.setCustomerName(customerName);
        roomOrder.setCustomerPhone(customerPhone);
        roomOrder.setRoomOrderState(Byte.parseByte("0"));
        roomOrderMapper.insert(roomOrder);//插入订单
        //获取生成的订单的流水号
        Long roomOrderNum=roomOrderMapper.selectRoomOrderNumByCustomerName(customerName);

        List<RoomOrderDetailDTO> roomOrderDetailDTOList=roomOrderDTO.getRoomOrderDetailDTOList();
        for (RoomOrderDetailDTO rddDTO:roomOrderDetailDTOList
             ) {
            String roomTypeName=rddDTO.getRoomTypeName();
            String roomPriceName=rddDTO.getRoomPriceName();
            String bedType=roomPriceName.substring(roomTypeName.length());//获取床型
            Long roomTypeNum=roomTypeMapper.getRoomTypeNumByRoomTypeNameAndBedType(roomTypeName,bedType);//1

            Integer roomPrice=rddDTO.getRoomPrice();
            String breakfastType=rddDTO.getBreakfastType();
            Integer roomCount=rddDTO.getRoomCount();
            String checkInDate=rddDTO.getCheckInDate();
            String checkOutDate=rddDTO.getCheckOutDate();
            //计算两个日期相差的天数
            int day=calculateDaysByDateTime(checkInDate,checkOutDate);
            if(day<=1){//若天数小于一天，则视为1天
                day=1;
            }

            Integer roomOrderDetailPrice=roomPrice*roomCount*day;//计算房间总价

            roomOrderDetail=new RoomOrderDetail();
            roomOrderDetail.setRoomOrderNum(roomOrderNum);
            roomOrderDetail.setRoomTypeNum(roomTypeNum);
            roomOrderDetail.setRoomPriceName(roomPriceName);
            roomOrderDetail.setRoomPrice(roomPrice);
            roomOrderDetail.setBreakfastType(breakfastType);
            roomOrderDetail.setRoomCount(roomCount);
            roomOrderDetail.setCheckInTime(checkInDate);
            roomOrderDetail.setCheckOutTime(checkOutDate);
            roomOrderDetail.setRoomOrderDetailPrice(roomOrderDetailPrice);

            roomOrderDetailMapper.insert(roomOrderDetail);//插入订单详情
            updateFreeCount(roomTypeNum,checkInDate,checkOutDate,roomCount);//更新空房数,保持数据正确性和一致性
        }

        return roomOrderNum;
    }

    /**
     * 计算时间毫秒
     * @param inVal
     * @return
     */
    private long fromDateStringToLong(String inVal) {
        Date date = null; // 定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = inputFormat.parse(inVal); // 将字符型转换成日期型
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.getTime(); // 返回毫秒数
    }

    /**
     * 计算两个日期相差多少天
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    private int calculateDaysByDateTime(String checkInDate,String checkOutDate){
        long checkInTime=fromDateStringToLong(checkInDate);
        long checkOutTime=fromDateStringToLong(checkOutDate);
        long times=checkOutTime-checkInTime;
        int day=Math.abs((int) (times/1000/60/60/24));
        return day;
    }

    /**
     * 取消订单
     * @param orderNum
     * @throws Exception
     */
    @Override
    public void cancelOrder(Long orderNum) throws Exception {
        Byte orderState=roomOrderMapper.findOrderState(orderNum);
        System.out.println("状态值为："+orderState);
        if (orderState==Byte.parseByte("0")) {
            roomOrderMapper.cancelOrderByNum(orderNum, Byte.parseByte("2"));
        }else if(orderState==Byte.parseByte("1")){
            throw new MyException(ErrorCodeEnum.IS_CHECKIN);
        }else if(orderState==Byte.parseByte("2")){
            throw new MyException(ErrorCodeEnum.IS_CHANCELED);
        }
    }

    /**
     * 查询房间类型信息
     * @param roomTypeNum
     * @return
     */
    @Override
    public RoomType findByRoomTypeNum(Long roomTypeNum) {
        return roomTypeMapper.selectByPrimaryKey(roomTypeNum);
    }

    /**
     * 查询房间套餐信息
     * @param roomPriceName
     * @return
     */
    @Override
    public RoomPrice findByRoomPriceName(String roomPriceName) {
        return roomPriceMapper.selectByRoomPriceName(roomPriceName);
    }

    /**
     * 计算房间总价
     * @param roomPrice
     * @param roomCount
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    @Override
    public Integer calculateRoomDetailPrice(Integer roomPrice, Integer roomCount, String checkInDate, String checkOutDate) {
        //计算两个日期相差的天数
        int day=calculateDaysByDateTime(checkInDate,checkOutDate);
        if(day<=1){//若天数小于一天，则视为1天
            day=1;
        }

        Integer roomOrderDetailPrice=roomPrice*roomCount*day;//计算房间总价

        return roomOrderDetailPrice;
    }

    /**
     * 更新空房数(成功订房，更新房间空房数)
     * @param roomTypeNum
     * @param checkInDate
     * @param checkOutCheck
     */
    private void updateFreeCount(Long roomTypeNum,String checkInDate,String checkOutCheck,Integer count){
        freeRoomCalendarMapper.updateFreeCount(roomTypeNum,checkInDate,checkOutCheck,count);
    }
}
