package com.xzsd.app.order.dao;


import com.xzsd.app.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OrderMapper {

    /**
     * 根据订单id查询订单信息
     *
     * @param orderId 订单id
     * @return
     */
    Order selectByPrimaryKey(String orderId);

    /**
     * 根据订单id修改订单状态
     *
     * @param orderId        订单id
     * @param updatePersonId 更新人id
     * @param orderCondition 要修改的订单状态
     * @return
     */
    int updateOrderConditiionById(@Param("orderId") String orderId,
                                  @Param("updatePersonId") String updatePersonId,
                                  @Param("orderCondition") int orderCondition);

    /**
     * 新增订单信息
     *
     * @param order 订单信息
     * @return
     */
    int insertSelective(Order order);

    /**
     * 根据客户编号查询订单信息列表
     *
     * @param orderclientCode 客户编号
     * @param orderCondition  订单状态
     * @return
     */
    List<Order> listOrdersById(@Param("orderclientCode") String orderclientCode,
                               @Param("orderCondition") int orderCondition);

    /**
     * 根据订单编号查询订单详情
     *
     * @param orderId 订单编号
     * @return
     */
    Order findOrderDetailById(String orderId);

}