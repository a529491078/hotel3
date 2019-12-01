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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    /**
     * 分页查询客房信息
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    @Override
    @Cacheable(value = "roomManagement")
    public Page findRoomInfoAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        List<RoomInfo> roomInfoAll = roomInfoMapper.findRoomInfoAll(pageSize * (pageNumber - 1), pageSize);
        //创建客房详情传输对象数组
        List<RoomInfoRoomTypeRoomPriceDTO> roomInfoRoomTypeRoomPriceDTOS=new ArrayList<>();
        for (RoomInfo roomInfos:roomInfoAll) {
            Long roomTypeNum = roomInfos.getRoomTypeNum();
            //根据客房类型主键查询客房价格表
            RoomPrice roomPriceByRoomTypeNum = roomInfoMapper.findRoomPriceByRoomTypeNum(roomTypeNum);
            //根据客房类型主键查询客房类型表
            RoomType roomTypeByRoomTypeNum = roomInfoMapper.findRoomTypeByRoomTypeNum(roomTypeNum);
            //创建客房详情传输对象
            RoomInfoRoomTypeRoomPriceDTO roomInfoRoomTypeRoomPriceDTO=new RoomInfoRoomTypeRoomPriceDTO();
            //为客房传详情输对象赋值
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
        //设置总个数
        page.setTotalNumber(total);
        //查询总页数
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    /**
     * 分页查询客房套餐
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    @Override
    @Cacheable(value = "roomTypePriceManagement")
    public Page findRoomTypeAndRoomPriceAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        //创建传输对象数组
        List<RoomTypeAndRoomPriceDTO> roomTypeAndRoomPriceDTOs = roomInfoMapper.findRoomTypeAndRoomPriceAll(pageSize * (pageNumber - 1),pageSize);
        page.setList(roomTypeAndRoomPriceDTOs);
        //查询条目数
        long total = roomInfoMapper.selRoomTypeAndRoomPriceCount();
        //设置总个数
        page.setTotalNumber(total);
        //设置总页数
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    /**
     *  添加客房信息
     * @param roomInfo 客房信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value="roomManagement",allEntries=true)
    public int insRoomInfo(RoomInfo roomInfo) {
        return roomInfoMapper.insert(roomInfo);
    }

    /**
     * 查询所有套餐名
     * @return
     */
    @Override
    @Cacheable(value = "roomTypePriceManagement")
    public List<RoomPriceNameAndRoomTypeNumDTO> findRoomPriceNameAll() {
        return roomInfoMapper.findRoomPriceNameAll();
    }

    /**
     * 删除客房信息
     * @param roomId 主键
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value="roomManagement",allEntries=true)
    public int delRoomInfoById(Integer roomId) {
        return roomInfoMapper.deleteByPrimaryKey(roomId);
    }

    /**
     * 根据主键查询客房信息
     * @param roomId 主键
     * @return
     */
    @Override
    @Cacheable(value = "roomManagement")
    public RoomInfo findRoomInfoById(Integer roomId) {
        return roomInfoMapper.selectByPrimaryKey(roomId);
    }

    /**
     * 根据主键和楼栋查询客房信息
     * @param roomId 主键
     * @param buildingNum 楼栋
     * @return
     */
    @Override
    @Cacheable(value = "roomTypePriceManagement",key="#roomIdbuildingNum")
    public int findNotRoomIdById(String roomId,String buildingNum) {
        return roomInfoMapper.queryRoomNumByRoomNumAndBuildingNum(roomId,buildingNum);
    }

    /**
     * 修改客房信息
     * @param roomInfo 所有客房信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value="roomManagement",allEntries=true)
    public int updRoomInfoAll(RoomInfo roomInfo) {
        return roomInfoMapper.updateByPrimaryKey(roomInfo);
    }

    /**
     * 添加客房套餐
     * @param roomTypeAndRoomPriceDTO 客房套餐信息
     */
    @Override
    @Transactional
    @CacheEvict(value="roomTypePriceManagement",allEntries=true)
    public void insRoomTypeAndRoomPrice(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO) {
        RoomType roomType=new RoomType();
        String roomTypeName = roomTypeAndRoomPriceDTO.getRoomTypeName();
        roomType.setRoomTypeName(roomTypeName);
        roomType.setBedType(roomTypeAndRoomPriceDTO.getBedType());
        roomType.setFloor(roomTypeAndRoomPriceDTO.getFloor());
        roomType.setRoomTypeDecs(roomTypeAndRoomPriceDTO.getRoomTypeDecs());
        roomType.setRoomTypeImg(roomTypeAndRoomPriceDTO.getRoomTypeImg());
        //添加客房类型表
        roomTypeMapper.insertSelective(roomType);
        //查询新添加的套餐主键
        Long roomTypeNumByRoomTypeNanme = roomInfoMapper.findRoomTypeNumByRoomTypeNanme(roomTypeName);
        RoomPrice roomPrice =new RoomPrice();
        roomPrice.setRoomTypeNum(roomTypeNumByRoomTypeNanme);
        roomPrice.setBreakfastType(roomTypeAndRoomPriceDTO.getBreakfastType());
        roomPrice.setRoomPrice(roomTypeAndRoomPriceDTO.getRoomPrice());
        roomPrice.setRoomPriceName(roomTypeAndRoomPriceDTO.getRoomPriceName());
        //添加客房价格表
        roomPriceMapper.insertSelective(roomPrice);
    }

    /**
     * 修改客房套餐
     * @param roomTypeNum 主键
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value="roomTypePriceManagement",allEntries=true)
    public int delRoomTypeAndRoomPriceById(Long roomTypeNum) {
        return roomInfoMapper.delRoomTypeAndRoomPriceById(roomTypeNum);
    }

    /**
     * 根据主键查询客房套餐
     * @param roomTypeNum 主键
     * @return
     */
    @Override
    @Cacheable(value="roomTypePriceManagement")
    public RoomTypeAndRoomPriceDTO findRoomTypeAndRoomPriceById(Long roomTypeNum) {
        return roomInfoMapper.findRoomTypeAndRoomPriceById(roomTypeNum);
    }

    /**
     * 修改客房套餐
     * @param roomTypeAndRoomPriceDTO 需要修改的客房套餐信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value="roomTypePriceManagement",allEntries=true)
    public int updRoomTypeAndRoomPriceAll(RoomTypeAndRoomPriceDTO roomTypeAndRoomPriceDTO) {
        return roomInfoMapper.updRoomTypeAndRoomPriceAll(roomTypeAndRoomPriceDTO);
    }
}
