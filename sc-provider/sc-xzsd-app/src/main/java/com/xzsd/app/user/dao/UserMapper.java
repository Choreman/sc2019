package com.xzsd.app.user.dao;


import com.xzsd.app.user.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 店长、客户、司机）公共接口管理数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@Mapper
public interface UserMapper {

    /**
     * 根据id查询用户信息关联查询头像和门店和区域名称信息（店长、司机）
     *
     * @param userId 用户id
     * @return
     */
    User findUserById(@Param("userId") String userId);

    /**
     * 根据id查询客户信息关联查询头像和门店和区域名称信息（客户）
     * @param userId 客户id
     * @return
     */
    User findClientById(@Param("userId") String userId);

    /**
     * 根据id查询用户信息
     *
     * @param userId 用户id
     * @return
     */
    User selectByPrimaryKey(String userId);

    /**
     * 根据用户id修改用户密码
     * @param user 包含用户id和用户新的密码
     * @param updatePersonId 修改人id
     * @return
     */
    int updatePasswordById(@Param("user") User user, @Param("updatePersonId") String updatePersonId);

}