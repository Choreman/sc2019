package com.xzsd.app.rollimage.controller;

import com.xzsd.app.rollimage.service.RollImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-14
 */
@RestController
@RequestMapping("/rollImage")
public class RollImageController {

    private static final Logger logger = LoggerFactory.getLogger(RollImageController.class);

    @Autowired
    private RollImageService rollImageService;



}


















