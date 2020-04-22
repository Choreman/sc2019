package com.xzsd.app.hotgoods.controller;

import com.xzsd.app.hotgoods.service.HotGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 热门位商品控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-16
 */
@RestController
@RequestMapping("/hotGoods")
public class HotGoodsController {

    private static final Logger logger = LoggerFactory.getLogger(HotGoodsController.class);

    @Autowired
    private HotGoodsService hotGoodsService;

}


















