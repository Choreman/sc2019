package com.xzsd.pc.user.dao;


import com.xzsd.pc.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
     * 根据店长编号查询是否有该店长
     *
     * @param managerId 店长编号
     * @return
     */
    int countManagerByManagerId(@Param("managerId") String managerId);

    /**
     * 根据店长编号查询该店长是否已经绑定门店
     *
     * @param managerId 店长编号
     * @return
     */
    int countStoreManager(@Param("managerId") String managerId);

}