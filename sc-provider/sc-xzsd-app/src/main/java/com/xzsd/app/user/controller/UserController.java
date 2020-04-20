package com.xzsd.app.user.controller;

import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.user.service.UserService;
import com.xzsd.app.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * （店长、客户、司机）公共接口管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**
     * 查询个人信息（店长、司机、客户）
     * @return
     */
    @PostMapping("/findUser")
    public AppResponse findUser(){
        try {
            return userService.findUser();
        } catch (Exception e) {
            logger.error("查询个人信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改密码接口
     * @param user 用户编号和用户原密码
     * @param userNewPassword 用户新密码
     * @return
     */
    @PostMapping("/updatePasswordById")
    public AppResponse updatePasswordById(User user, String userNewPassword){
        try {
            return userService.updatePasswordById(user, userNewPassword);
        } catch (Exception e) {
            logger.error("修改个人密码异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}





















