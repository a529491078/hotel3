package com.edu.fjzzit.web.myhotel.service;

import com.edu.fjzzit.web.myhotel.model.CustomerInfo;
import com.edu.fjzzit.web.myhotel.model.Page;

public interface CustomerManagementService {
    /**
     * 分页查询顾客信息
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    Page findCustomerInfoAll(int pageNumber, int pageSize);

    /**
     * 根据主键删除顾客信息
     * @param customerNum 主键
     * @return
     */
    int delCustomerInfoById(Long customerNum);
    /**
     * 修改框显示顾客信息
     * @param customerNum 主键
     * @return 客房信息
     */
    CustomerInfo findCustomerInfoById(Long customerNum);

    /**
     * 修改顾客信息
     * @param customerInfo 所有订单信息
     * @return 受影响行数
     */
    int updCustomerInfoAll(CustomerInfo customerInfo);
    /**
     *  新增顾客信息
     * @param customerInfo 顾客信息
     * @return 受影响行数
     */
    int insCustomerInfo(CustomerInfo customerInfo);

    /**
     * 查看昵称是否存在
     * @param customerNickName 昵称
     * @return 条目数
     */
    Long findNotCustomerInfoById(String customerNickName);
}
