package com.neusoft.oauth.dao;

import com.neusoft.oauth.entity.User;
import org.springframework.data.repository.query.Param;

/**
 * 用户信息数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-09
 */
public interface UserDao {

    /**
     * 根据用户账号获取用户信息
     *
     * @param username 用户账号
     * @return 用户信息
     */
    User getUserByUserLoginName(@Param("username") String username);

}
