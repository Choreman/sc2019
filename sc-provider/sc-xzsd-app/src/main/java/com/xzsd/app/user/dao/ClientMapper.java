package com.xzsd.app.user.dao;

import com.xzsd.app.user.entity.User;
import com.xzsd.app.utils.AppResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户管理数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Mapper
public interface ClientMapper {

    /**
     * 根据用户账号信息计算用户数量
     *
     * @param userLoginName 用户账号
     * @return
     */
    int countUserByUserLoginName(@Param("userLoginName") String userLoginName);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return
     */
    int insertSelective(User user);

    /**
     * 根据客户编号修改客户的门店邀请码
     *
     * @param user 包含客户编号id、门店邀请码
     * @return
     */
    int updateClientStoreInvitationCodeById(User user);


}













