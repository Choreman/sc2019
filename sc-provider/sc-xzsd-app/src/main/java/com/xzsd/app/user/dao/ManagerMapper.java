package com.xzsd.app.user.dao;


import com.xzsd.app.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 店长管理数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@Mapper
public interface ManagerMapper {

    /**
     * 根据店长id查询门店特定订单状态的订单信息
     * @param userId 店长id
     * @param orderCondition 订单状态
     * @return
     */
    List<Order> listStoreOrders(@Param("userId") String userId, @Param("orderCondition") int orderCondition);

    /**
     * 根据店长编号查询门店的司机信息
     *
     * @param userId 店长编号
     * @return
     */
    List<User> findStoreDriverById(String userId);

}















