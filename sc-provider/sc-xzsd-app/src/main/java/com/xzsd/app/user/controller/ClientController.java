package com.xzsd.app.user.controller;

import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.user.service.ClientService;
import com.xzsd.app.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

}




















