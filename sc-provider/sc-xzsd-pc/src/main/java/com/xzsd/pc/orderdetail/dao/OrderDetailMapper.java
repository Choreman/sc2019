package com.xzsd.pc.orderdetail.dao;


import com.xzsd.pc.orderdetail.entity.OrderDetail;

public interface OrderDetailMapper {
    int deleteByPrimaryKey(String orderDetailId);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String orderDetailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}