package com.edu.fjzzit.web.myhotel.service;

import com.edu.fjzzit.web.myhotel.dto.FreeRoomDTO;
import com.edu.fjzzit.web.myhotel.dto.MyOrderDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomOrderDTO;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;
import com.edu.fjzzit.web.myhotel.model.RoomOrder;
import com.edu.fjzzit.web.myhotel.model.RoomPrice;
import com.edu.fjzzit.web.myhotel.model.RoomType;

import java.util.List;

public interface RoomService {

    /**
     * 查找空房
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    List<FreeRoomDTO> findFreeRoom(String checkInDate, String checkOutDate);

    /**
     * 订房
     * @param roomOrderDTO
     * @return
     */
    Long reserveRoom(RoomOrderDTO roomOrderDTO);

    /**
     * 订房
     * @param roomOrderDTO
     * @param userName
     * @return
     */
    Long reserveRoomM(RoomOrderDTO roomOrderDTO,String userName);

    /**
     * 取消订单
     * @param orderNum
     * @throws Exception
     */
    void cancelOrder(Long orderNum) throws Exception;

    /**
     * 查询房间类型信息
     * @param roomTypeNum
     * @return
     */
    RoomType findByRoomTypeNum(Long roomTypeNum);

    /**
     * 查询房间套餐信息
     * @param roomPriceName
     * @return
     */
    RoomPrice findByRoomPriceName(String roomPriceName);

    /**
     * 计算房间总价
     * @param roomPrice
     * @param roomCount
     * @param checkInDate
     * @param checkOutDate
     * @return
     */
    Integer calculateRoomDetailPrice(Integer roomPrice,Integer roomCount,String checkInDate,String checkOutDate);

    /**
     * 查找客户订单信息
     * @return
     */
    List<MyOrderDTO> findUserOrderByCustomerName(String customerName) throws Exception ;

    /**
     * 根据房间类型名称和床型查找房间类型流水号
     * @param roomTypeName
     * @param bedType
     * @return
     */
    Long findRoomTypeNum(String roomTypeName,String bedType);

    /**
     * 根据房间类型流水号查询房图
     * @param roomTypeNum
     * @return
     */
    String findRoomTypeImgByRoomTypeNum(Long roomTypeNum);

    Long orderConfirm (Long roomTypeNum, String customerName, String customerPhone, String checkInDate,
                       String checkOutDate, Integer roomCount, String roomPriceName);
}
