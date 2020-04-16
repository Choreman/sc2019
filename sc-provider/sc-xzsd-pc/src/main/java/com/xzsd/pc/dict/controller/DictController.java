package com.xzsd.pc.dict.controller;

import com.xzsd.pc.area.service.AreaService;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.dict.service.DictService;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典控制器
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    private static final Logger logger = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;

}


















