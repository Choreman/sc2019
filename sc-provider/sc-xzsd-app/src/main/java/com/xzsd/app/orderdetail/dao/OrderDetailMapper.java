package com.xzsd.app.orderdetail.dao;

import com.xzsd.app.orderdetail.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单详情数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-17
 */
@Mapper
public interface OrderDetailMapper {

    /**
     * 新增订单详情
     *
     * @param orderDetail 订单详情信息
     * @return
     */
    int insertSelective(OrderDetail orderDetail);

    /**
     * 批量新增订单详情
     *
     * @param OrderDetailList 订单详情列表
     * @return
     */
    int insertOrderDetailList(@Param("OrderDetailList") List<OrderDetail> OrderDetailList);


    int deleteByPrimaryKey(String orderDetailId);

    int insert(OrderDetail record);

    OrderDetail selectByPrimaryKey(String orderDetailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    int updateByPrimaryKey(OrderDetail record);
}