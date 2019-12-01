package com.edu.fjzzit.web.myhotel.service.impl;

import com.edu.fjzzit.web.myhotel.mapper.CustomerInfoMapper;
import com.edu.fjzzit.web.myhotel.model.CustomerInfo;
import com.edu.fjzzit.web.myhotel.model.Page;
import com.edu.fjzzit.web.myhotel.service.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CustomerManagementServiceImpl implements CustomerManagementService {
    @Autowired
    private CustomerInfoMapper customerMapper;
    /**
     * 分页查询顾客信息
     * @param pageNumber 当前页
     * @param pageSize 每页个数
     * @return
     */
    @Override
    @Cacheable(value = "customerManagement")
    public Page findCustomerInfoAll(int pageNumber, int pageSize) {
        Page page=new Page();
        page.setPageSize(pageSize);
        page.setPageNumber(pageNumber);
        //分页查询客户详情表
        page.setList(customerMapper.findCustomerInfoAll(pageSize * (pageNumber - 1),pageSize));
        long total = customerMapper.selCount();
        //设置总个数
        page.setTotalNumber(total);
        //查询总页数
        page.setTotal(total%pageSize==0?total/pageSize:total/pageSize+1);
        return page;
    }

    /**
     * 根据主键删除顾客信息
     * @param customerNum 主键
     * @return
     */
    @Transactional
    @Override
    @CacheEvict(value = "customerManagement")
    public int delCustomerInfoById(Long customerNum) {
        return customerMapper.deleteByPrimaryKey(customerNum);
    }

    /**
     * 根据主键查询顾客信息
     * @param customerNum 主键
     * @return
     */
    @Override
    @Cacheable(value = "customerManagement")
    public CustomerInfo findCustomerInfoById(Long customerNum) {
        return customerMapper.selectByPrimaryKey(customerNum);
    }

    /**
     * 修改顾客信息
     * @param customerInfo 所有订单信息
     * @return 受影响行数
     */
    @Transactional
    @Override
    @CacheEvict(value = "customerManagement")
    public int updCustomerInfoAll(CustomerInfo customerInfo) {
        return customerMapper.updateByPrimaryKeySelective(customerInfo);
    }


    /**
     * 添加顾客信息
     * @param customerInfo 顾客信息
     * @return
     */
    @Override
    @Transactional
    @CacheEvict(value = "customerManagement")
    public int insCustomerInfo(CustomerInfo customerInfo) {
        return customerMapper.insertSelective(customerInfo);
    }

    /**
     * 判断昵称是否重复
     * @param customerNickName 昵称
     * @return
     */
    @Override
    public Long findNotCustomerInfoById(String customerNickName) {
        return customerMapper.queryCouldByCustomerNickName(customerNickName);
    }
}
