package com.xzsd.app.goodscate.controller;


import com.xzsd.app.goodscate.service.GoodsCateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品分类管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@RestController
@RequestMapping("/goodsCate")
public class GoodsCateController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsCateController.class);

    @Autowired
    private GoodsCateService goodsCateService;



}


















