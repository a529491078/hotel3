package com.edu.fjzzit.web.myhotel.service;

import com.edu.fjzzit.web.myhotel.dto.RoomPriceNameAndRoomTypeNumDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;

import java.util.List;

public interface RoomManagementService {
    /**
     * 分页查询客房信息
     * @param pageNumber 当前页
     * @param pageNumber 每页个数
     * @return 分页对象
     */
    Page findRoomInfoAll(int pageNumber, int pageSize);

    /**
     * 分页查询客房套餐信息
     * @param pageNumber 当前页
     * @param pageNumber 每页个数
     * @return 分页对象
     */
    Page findRoomTypeAndRoomPriceAll(int pageNumber, int pageSize);


    /**
     *  新增客房信息
     * @param roomInfo 客房信息
     * @return 受影响行数
     */
    int insRoomInfo(RoomInfo roomInfo);

    /**
     * 查询所有客房类型名称和客房类型ID
     * @return 所有客房类型名称和客房类型ID
     */
    List<RoomPriceNameAndRoomTypeNumDTO> findRoomPriceNameAll();
    /**
     * 删除客房信息
     * @param roomId 主键
     * @return 受影响行数
     */
    int delRoomInfoById(Integer roomId);

    /**
     * 修改框显示客房信息
     * @param roomId 主键
     * @return 客房信息
     */
    RoomInfo findRoomInfoById(Integer roomId);

    /**
     * 查看房间号是否存在
     * @param roomId 主键
     * @return 房间号
     */
    int findNotRoomIdById(String roomId,String buildingNum);

    /**
     * 修改客房信息
     * @param roomInfo 所有客房信息
     * @return 受影响行数
     */
    int updRoomInfoAll(RoomInfo roomInfo);

    /**
     *  新增客房套餐信息
     * @param roomTypeAndRoomPriceDTO 客房套餐信息
     * @return 受影响行数
     */
    void insRoomTypeAndRoomPrice(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO);

    /**
     * 删除客房套餐信息
     * @param roomTypeNum 主键
     * @return 受影响行数
     */
    int delRoomTypeAndRoomPriceById(Long roomTypeNum);

    /**
     * 修改框显示客房套餐信息
     * @param roomTypeNum 主键
     * @return 客房套餐信息
     */
    RoomTypeAndRoomPriceDTO findRoomTypeAndRoomPriceById(Long roomTypeNum);

    /**
     * 修改客房套餐信息
     * @param roomTypeAndRoomPriceDTO 需要修改的客房套餐信息
     * @return 受影响行数
     */
    int updRoomTypeAndRoomPriceAll(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO);
}
