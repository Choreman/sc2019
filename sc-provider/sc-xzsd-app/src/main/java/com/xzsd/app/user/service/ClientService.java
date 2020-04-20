package com.xzsd.app.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.user.dao.ClientMapper;
import com.xzsd.app.user.dao.UserMapper;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.utils.AppResponse;
import com.xzsd.app.utils.AuthUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private UserMapper userMapper;

}















