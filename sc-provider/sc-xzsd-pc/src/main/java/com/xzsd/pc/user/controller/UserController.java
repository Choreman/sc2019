package com.xzsd.pc.user.controller;

import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.user.service.UserService;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户管理控制器
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
     * 新增用户接口
     *
     * @param user 用户信息
     * @return
     */
    @PostMapping("/addUser")
    public AppResponse addUser(@RequestParam("headImage") MultipartFile headImage, User user) {
        try {
            return userService.addUser(headImage, user);
        } catch (Exception e) {
            logger.error("新增用户异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据用户账号查询用户接口
     *
     * @param userLoginName 用户账号
     * @return
     */
    @PostMapping("/countUserByUserLoginName")
    public AppResponse countUserByUserLoginName(String userLoginName) {
        try {
            return userService.countUserByUserLoginName(userLoginName);
        } catch (Exception e) {
            logger.error("根据账号查询用户异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据用户信息条件查询用户信息（管理员、店长、司机）
     *
     * @param pageBean 分页信息
     * @param user 查询的用户信息条件
     * @return
     */
    @PostMapping("/listUsers")
    public AppResponse listUsers(PageBean pageBean, User user) {
        try {
            return userService.listUsers(pageBean, user);
        } catch (Exception e) {
            logger.error("查询用户信息列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改用户信息接口
     * @param user 要修改的用户信息
     * @return
     */
    @PostMapping("/updateUserById")
    public AppResponse updateUserById(@RequestParam("headImage") MultipartFile headImage, User user) {
        try {
            return userService.updateUserById(headImage, user);
        } catch (Exception e) {
            logger.error("修改用户信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除用户接口
     * @param ids 要删除的id（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteUserById")
    public AppResponse deleteUserById(String ids) {
        try {
            return userService.deleteUserById(ids);
        } catch (Exception e) {
            logger.error("删除用户信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}





















