package com.edu.fjzzit.web.myhotel.service.impl;

import com.edu.fjzzit.web.myhotel.dto.RoomInfoRoomTypeRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomPriceNameAndRoomTypeNumDTO;
import com.edu.fjzzit.web.myhotel.dto.RoomTypeAndRoomPriceDTO;
import com.edu.fjzzit.web.myhotel.mapper.RoomInfoMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomPriceMapper;
import com.edu.fjzzit.web.myhotel.mapper.RoomTypeMapper;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.model.RoomInfo;
import com.edu.fjzzit.web.myhotel.model.RoomPrice;
import com.edu.fjzzit.web.myhotel.model.RoomType;
import com.edu.fjzzit.web.myhotel.service.RoomManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomManagementServiceImpl implements RoomManagementService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Autowired
    private RoomPriceMapper roomPriceMapper;

    @Override
    public Page findRoomInfoAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        List<RoomInfo> roomInfoAll = roomInfoMapper.findRoomInfoAll(pageSize * (pageNumber - 1), pageSize);
        List<RoomInfoRoomTypeRoomPriceDTO> roomInfoRoomTypeRoomPriceDTOS=new ArrayList<>();
        for (RoomInfo roomInfos:roomInfoAll) {
            Long roomTypeNum = roomInfos.getRoomTypeNum();
            //根据客房类型主键查询客房价格表
            RoomPrice roomPriceByRoomTypeNum = roomInfoMapper.findRoomPriceByRoomTypeNum(roomTypeNum);
            //根据客房类型主键查询客房类型表
            RoomType roomTypeByRoomTypeNum = roomInfoMapper.findRoomTypeByRoomTypeNum(roomTypeNum);
            RoomInfoRoomTypeRoomPriceDTO roomInfoRoomTypeRoomPriceDTO=new RoomInfoRoomTypeRoomPriceDTO();
            roomInfoRoomTypeRoomPriceDTO.setRoomId(roomInfos.getRoomId());
            roomInfoRoomTypeRoomPriceDTO.setBuildingNum(roomInfos.getBuildingNum());
            roomInfoRoomTypeRoomPriceDTO.setBedType(roomTypeByRoomTypeNum.getBedType());
            roomInfoRoomTypeRoomPriceDTO.setBreakfastType(roomPriceByRoomTypeNum.getBreakfastType());
            roomInfoRoomTypeRoomPriceDTO.setFloor(roomTypeByRoomTypeNum.getFloor());
            roomInfoRoomTypeRoomPriceDTO.setRoomNum(roomInfos.getRoomNum());
            roomInfoRoomTypeRoomPriceDTO.setRoomPrice(roomPriceByRoomTypeNum.getRoomPrice());
            roomInfoRoomTypeRoomPriceDTO.setRoomPriceName(roomPriceByRoomTypeNum.getRoomPriceName());
            roomInfoRoomTypeRoomPriceDTO.setRoomState(roomInfos.getRoomState());
            roomInfoRoomTypeRoomPriceDTO.setRoomTypeDecs(roomTypeByRoomTypeNum.getRoomTypeDecs());
            roomInfoRoomTypeRoomPriceDTO.setRoomTypeName(roomTypeByRoomTypeNum.getRoomTypeName());
            roomInfoRoomTypeRoomPriceDTO.setRoomTypeNum(roomInfos.getRoomTypeNum());
            roomInfoRoomTypeRoomPriceDTOS.add(roomInfoRoomTypeRoomPriceDTO);
        }
        page.setList(roomInfoRoomTypeRoomPriceDTOS);
        long total = roomInfoMapper.selCount();
        page.setTotalNumber(total);
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    @Override
    public Page findRoomTypeAndRoomPriceAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        List<RoomTypeAndRoomPriceDTO> roomTypeAndRoomPriceDTOs = roomInfoMapper.findRoomTypeAndRoomPriceAll(pageSize * (pageNumber - 1),pageSize);
        page.setList(roomTypeAndRoomPriceDTOs);
        long total = roomInfoMapper.selRoomTypeAndRoomPriceCount();
        page.setTotalNumber(total);
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    @Override
    @Transactional
    public int insRoomInfo(RoomInfo roomInfo) {
        return roomInfoMapper.insert(roomInfo);
    }

    @Override
    public List<RoomPriceNameAndRoomTypeNumDTO> findRoomPriceNameAll() {
        return roomInfoMapper.findRoomPriceNameAll();
    }

    @Override
    @Transactional
    public int delRoomInfoById(Integer roomId) {
        return roomInfoMapper.deleteByPrimaryKey(roomId);
    }

    @Override
    public RoomInfo findRoomInfoById(Integer roomId) {
        return roomInfoMapper.selectByPrimaryKey(roomId);
    }

    @Override
    public int findNotRoomIdById(String roomId,String buildingNum) {
        return roomInfoMapper.queryRoomNumByRoomNumAndBuildingNum(roomId,buildingNum);
    }

    @Override
    @Transactional
    public int updRoomInfoAll(RoomInfo roomInfo) {
        return roomInfoMapper.updateByPrimaryKey(roomInfo);
    }

    @Override
    @Transactional
    public void insRoomTypeAndRoomPrice(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO) {
        RoomType roomType=new RoomType();
        String roomTypeName = roomTypeAndRoomPriceDTO.getRoomTypeName();
        roomType.setRoomTypeName(roomTypeName);
        roomType.setBedType(roomTypeAndRoomPriceDTO.getBedType());
        roomType.setFloor(roomTypeAndRoomPriceDTO.getFloor());
        roomType.setRoomTypeDecs(roomTypeAndRoomPriceDTO.getRoomTypeDecs());
        roomTypeMapper.insertSelective(roomType);
        Long roomTypeNumByRoomTypeNanme = roomInfoMapper.findRoomTypeNumByRoomTypeNanme(roomTypeName);
        RoomPrice roomPrice =new RoomPrice();
        roomPrice.setRoomTypeNum(roomTypeNumByRoomTypeNanme);
        roomPrice.setBreakfastType(roomTypeAndRoomPriceDTO.getBreakfastType());
        roomPrice.setRoomPrice(roomTypeAndRoomPriceDTO.getRoomPrice());
        roomPrice.setRoomPriceName(roomTypeAndRoomPriceDTO.getRoomPriceName());
        roomPriceMapper.insertSelective(roomPrice);
    }

    @Override
    @Transactional
    public int delRoomTypeAndRoomPriceById(Long roomTypeNum) {
        return roomInfoMapper.delRoomTypeAndRoomPriceById(roomTypeNum);
    }

    @Override
    public RoomTypeAndRoomPriceDTO findRoomTypeAndRoomPriceById(Long roomTypeNum) {
        return roomInfoMapper.findRoomTypeAndRoomPriceById(roomTypeNum);
    }

    @Override
    @Transactional
    public int updRoomTypeAndRoomPriceAll(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO) {
        return roomInfoMapper.updRoomTypeAndRoomPriceAll(roomTypeAndRoomPriceDTO);
    }
}
