package com.xzsd.app.order.dao;


import com.xzsd.app.order.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface OrderMapper {

    /**
     * 根据订单id查询订单信息
     * @param orderId 订单id
     * @return
     */
    Order selectByPrimaryKey(String orderId);

    /**
     * 根据订单id修改订单状态
     * @param orderId 订单id
     * @param updatePersonId 更新人id
     * @param orderCondition 要修改的订单状态
     * @return
     */
    int updateOrderConditiionById(@Param("orderId") String orderId,
                                  @Param("updatePersonId") String updatePersonId,
                                  @Param("orderCondition") int orderCondition);

}