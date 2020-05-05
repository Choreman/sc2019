package com.xzsd.pc.order.dao;


import com.xzsd.pc.order.entity.Order;
import com.xzsd.pc.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 根据传入条件查询全部订单列表
     *
     * @param user              要查询的用户信息
     * @param order             要查询的订单信息
     * @param orderBiginPayTime 订单付款的起始时间
     * @param orderEndPayTime   订单付款的结束时间
     * @return
     */
    List<Order> listOrders(@Param("user") User user, @Param("order") Order order,
                           @Param("orderBiginPayTime") Date orderBiginPayTime,
                           @Param("orderEndPayTime") Date orderEndPayTime);


    /**
     * 根据传入条件查询门店所有订单信息
     * 通过店长编号 --> 门店编号 --> 门店所有订单信息
     *
     * @param user              要查询的用户信息（包含店长编号）
     * @param order             要查询的订单信息
     * @param orderBiginPayTime 订单付款的起始时间
     * @param orderEndPayTime   订单付款的结束时间
     * @return
     */
    List<Order> listStoreOrders(@Param("user") User user, @Param("order") Order order,
                                @Param("orderBiginPayTime") Date orderBiginPayTime,
                                @Param("orderEndPayTime") Date orderEndPayTime);

    /**
     * 根据订单id关联查询订单详情关联查询用户编号
     *
     * @param orderId 订单id
     * @return
     */
    List<Order> listOrderDetailsById(@Param("orderId") String orderId);

    /**
     * 批量修改订单状态信息
     *
     * @param listIds        要修改的订单信息列表
     * @param updatePersonId 更新人id
     * @param orderCondition 订单状态
     * @return
     */
    int updateOrderConditionById(@Param("listIds") List<String> listIds,
                                 @Param("updatePersonId") String updatePersonId,
                                 @Param("orderCondition") int orderCondition);

}