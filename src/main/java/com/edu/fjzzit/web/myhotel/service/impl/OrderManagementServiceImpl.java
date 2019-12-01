package com.edu.fjzzit.web.myhotel.service.impl;

import com.edu.fjzzit.web.myhotel.dto.RoomOrderDetailAndRoomOrderDTO;
import com.edu.fjzzit.web.myhotel.mapper.RoomOrderDetailMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomOrderMapper;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomOrder;
import com.edu.fjzzit.web.myhotel.model.RoomOrderDetail;
import com.edu.fjzzit.web.myhotel.service.OrderManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderManagementServiceImpl implements OrderManagementService {
    @Autowired
    private RoomOrderDetailMapper roomOrderDetailMapper;
    @Autowired
    private RoomOrderMapper roomOrderMapper;

    /**
     * 分页查询订单信息
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    @Override
    @Cacheable(value = "orderManagement")
    public Page findRoomOrderDetailAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        //分页查询订单详情表
        List<RoomOrderDetail> roomOrderDetailAll = roomOrderDetailMapper.findRoomOrderDetailAll(pageSize * (pageNumber - 1), pageSize);
        //创建订单传输对象数组
        List<RoomOrderDetailAndRoomOrderDTO> roomInfoRoomTypeRoomPriceDTOS=new ArrayList<>();
        for (RoomOrderDetail roomOrderDetails:roomOrderDetailAll) {
            //创建订单传输对象
            RoomOrderDetailAndRoomOrderDTO roomOrderDetailAndRoomOrderDTO=new RoomOrderDetailAndRoomOrderDTO();
            //根据主键查询订单表
            RoomOrder roomOrderByRoomOrderNum = roomOrderDetailMapper.findRoomOrderByRoomOrderNum(roomOrderDetails.getRoomOrderNum());
            //为订单传输对象赋值
            roomOrderDetailAndRoomOrderDTO.setRoomOrderDetailNum(roomOrderDetails.getRoomOrderDetailNum());
            roomOrderDetailAndRoomOrderDTO.setRoomOrderNum(roomOrderDetails.getRoomOrderNum());
            roomOrderDetailAndRoomOrderDTO.setRoomTypeNum(roomOrderDetails.getRoomTypeNum());
            roomOrderDetailAndRoomOrderDTO.setRoomPriceName(roomOrderDetails.getRoomPriceName());
            roomOrderDetailAndRoomOrderDTO.setRoomPrice(roomOrderDetails.getRoomPrice());
            roomOrderDetailAndRoomOrderDTO.setRoomCount(roomOrderDetails.getRoomCount());
            roomOrderDetailAndRoomOrderDTO.setBreakfastType(roomOrderDetails.getBreakfastType());
            roomOrderDetailAndRoomOrderDTO.setCheckInTime(roomOrderDetails.getCheckInTime());
            roomOrderDetailAndRoomOrderDTO.setCheckOutTime(roomOrderDetails.getCheckOutTime());
            roomOrderDetailAndRoomOrderDTO.setRoomOrderDetailPrice(roomOrderDetails.getRoomOrderDetailPrice());
            roomOrderDetailAndRoomOrderDTO.setCustomerName(roomOrderByRoomOrderNum.getCustomerName());
            roomOrderDetailAndRoomOrderDTO.setCustomerPhone(roomOrderByRoomOrderNum.getCustomerPhone());
            roomOrderDetailAndRoomOrderDTO.setRoomOrderState(roomOrderByRoomOrderNum.getRoomOrderState());
            roomInfoRoomTypeRoomPriceDTOS.add(roomOrderDetailAndRoomOrderDTO);
        }
        page.setList(roomInfoRoomTypeRoomPriceDTOS);
        long total = roomOrderDetailMapper.selCount();
        //设置总个数
        page.setTotalNumber(total);
        //查询总页数
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    /**
     * 删除订单
     * @param roomOrderDetailId 主键
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "orderManagement")
    public int delRoomOrderDetailById(Long roomOrderDetailId) {
        return roomOrderDetailMapper.deleteByPrimaryKey(roomOrderDetailId);
    }

    /**
     * 根据主键回显订单详情信息
     * @param roomOrderDetailId 主键
     * @return
     */
    @Override
    @Cacheable(value = "orderManagement")
    public RoomOrderDetailAndRoomOrderDTO findRoomOrderDetailById(Long roomOrderDetailId) {
        //根据主键查询订单详情表
        RoomOrderDetail roomOrderDetail = roomOrderDetailMapper.selectByPrimaryKey(roomOrderDetailId);
        //根据主键查询订单表
        RoomOrder roomOrder = roomOrderMapper.selectByPrimaryKey(roomOrderDetail.getRoomOrderNum());
        //创建订单详情传输对象
        RoomOrderDetailAndRoomOrderDTO roomOrderDetailAndRoomOrderDTO=new RoomOrderDetailAndRoomOrderDTO();
        roomOrderDetailAndRoomOrderDTO.setRoomOrderDetailNum(roomOrderDetail.getRoomOrderDetailNum());
        roomOrderDetailAndRoomOrderDTO.setRoomOrderNum(roomOrderDetail.getRoomOrderNum());
        roomOrderDetailAndRoomOrderDTO.setRoomTypeNum(roomOrderDetail.getRoomTypeNum());
        roomOrderDetailAndRoomOrderDTO.setRoomPriceName(roomOrderDetail.getRoomPriceName());
        roomOrderDetailAndRoomOrderDTO.setRoomPrice(roomOrderDetail.getRoomPrice());
        roomOrderDetailAndRoomOrderDTO.setRoomCount(roomOrderDetail.getRoomCount());
        roomOrderDetailAndRoomOrderDTO.setBreakfastType(roomOrderDetail.getBreakfastType());
        roomOrderDetailAndRoomOrderDTO.setCheckInTime(roomOrderDetail.getCheckInTime());
        roomOrderDetailAndRoomOrderDTO.setCheckOutTime(roomOrderDetail.getCheckOutTime());
        roomOrderDetailAndRoomOrderDTO.setRoomOrderDetailPrice(roomOrderDetail.getRoomOrderDetailPrice());
        roomOrderDetailAndRoomOrderDTO.setCustomerName(roomOrder.getCustomerName());
        roomOrderDetailAndRoomOrderDTO.setCustomerPhone(roomOrder.getCustomerPhone());
        roomOrderDetailAndRoomOrderDTO.setRoomOrderState(roomOrder.getRoomOrderState());
        return roomOrderDetailAndRoomOrderDTO;
    }

    /**
     * 修改订单信息
     * @param roomOrderDetailAndRoomOrderDTO 所有订单信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "orderManagement")
    public int updRoomOrderDetailAll(RoomOrderDetailAndRoomOrderDTO roomOrderDetailAndRoomOrderDTO) {
        return roomOrderDetailMapper.updRoomOrderDetailAll(roomOrderDetailAndRoomOrderDTO);
    }
}
