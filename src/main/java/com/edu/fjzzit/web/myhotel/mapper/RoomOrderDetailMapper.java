package com.edu.fjzzit.web.myhotel.mapper;

import com.edu.fjzzit.web.myhotel.model.RoomOrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoomOrderDetailMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long roomOrderDetailNum);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    int insert(RoomOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    int insertSelective(RoomOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    RoomOrderDetail selectByPrimaryKey(Long roomOrderDetailNum);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RoomOrderDetail record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table room_order_detail
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RoomOrderDetail record);
}