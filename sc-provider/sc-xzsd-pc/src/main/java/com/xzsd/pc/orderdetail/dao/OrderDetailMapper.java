package com.xzsd.pc.orderdetail.dao;

import com.xzsd.pc.orderdetail.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单详情数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-17
 */
@Mapper
public interface OrderDetailMapper {

    int deleteByPrimaryKey(String orderDetailId);

    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String orderDetailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}