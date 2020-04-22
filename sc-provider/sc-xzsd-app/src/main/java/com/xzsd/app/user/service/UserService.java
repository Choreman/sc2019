package com.xzsd.app.user.service;


import com.xzsd.app.image.dao.ImageMapper;
import com.xzsd.app.user.dao.UserMapper;
import com.xzsd.app.user.entity.User;

import com.xzsd.app.utils.AppResponse;
import com.xzsd.app.utils.AuthUtils;
import com.xzsd.app.utils.PasswordUtils;
import com.xzsd.app.utils.TencentCOSUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 店长、客户、司机）公共接口业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 查询个人信息（店长、司机、客户）
     *
     * @return
     */
    public AppResponse findUser() {
        User user = userMapper.selectByPrimaryKey(AuthUtils.getCurrentUserId());
        if (user != null) {
            User userInfo = null;
            //当查询的是店长或者司机个人信息时
            if(user.getUserRole() == 2 || user.getUserRole() == 3){
                 userInfo = userMapper.findUserById(AuthUtils.getCurrentUserId());
            //当查询的是客户信息时
            }else if(user.getUserRole() == 4){
                userInfo = userMapper.findClientById(AuthUtils.getCurrentUserId());
            }
            return AppResponse.success("个人信息查询成功", userInfo);
        }
        return AppResponse.bizError("个人信息查询失败");
    }

    /**
     * 修改密码接口
     * @param user 用户编号和用户原密码
     * @param userNewPassword 用户新密码
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updatePasswordById(User user, String userNewPassword){
        if(user.getUserId() == null || "".equals(user.getUserId())){
            return AppResponse.Error("用户编号错误");
        }
        User oldUser = userMapper.selectByPrimaryKey(user.getUserId());
        if(oldUser == null){
            return AppResponse.Error("没有该用户信息");
        }
        //如果输入的原密码和数据库保存的密码不一致
        if(!PasswordUtils.equalPassword(user.getUserPassword(), oldUser.getUserPassword())){
            return AppResponse.Error("原密码输入错误");
        }
        //设置新密码加密
        user.setUserPassword(PasswordUtils.generatePassword(userNewPassword));
        //根据用户编号修改用户密码
        int count = userMapper.updatePasswordById(user, AuthUtils.getCurrentUserId());
        if (count > 0){
            return AppResponse.success("用户密码修改成功");
        }
        return AppResponse.bizError("用户密码修改失败");
    }

}















