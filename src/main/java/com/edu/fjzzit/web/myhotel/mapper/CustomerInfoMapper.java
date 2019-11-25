package com.edu.fjzzit.web.myhotel.mapper;

import com.edu.fjzzit.web.myhotel.model.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerInfoMapper {
    /**
     * 分页查询顾客信息表
     * @param pageNumber
     * @param pageSize
     * @return
     */
    List<CustomerInfo> findCustomerInfoAll(@Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);

    /**
     * 查询条目数
     * @return 条目数
     */
    Long selCount();

    /**
     * 添加顾客信息
     * @param customerInfo 顾客信息
     * @return
     */
    int insertSelective(CustomerInfo customerInfo);

    /**
     * 判断昵称是否重复
     * @param customerNickName
     * @return
     */
    Long queryCouldByCustomerNickName(@Param("customerNickName")String customerNickName);

    /**
     * 删除顾客信息
     * @param customerNum 主键
     * @return
     */
    int deleteByPrimaryKey(@Param("customerNum")Long customerNum);
    /**
     * 根据主键查询顾客信息
     * @param customerNum 主键
     * @return
     */
    CustomerInfo selectByPrimaryKey(@Param("customerNum")Long customerNum);
    /**
     * 修改顾客信息
     * @param customerInfo 顾客信息
     * @return
     */
    int updateByPrimaryKeySelective(CustomerInfo customerInfo);
}
