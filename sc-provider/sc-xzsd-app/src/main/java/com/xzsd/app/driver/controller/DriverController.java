package com.xzsd.app.driver.controller;

import com.xzsd.app.driver.service.DriverService;
import com.xzsd.app.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司机信息控制器
 *
 * @author 黄瑞穆
 * @date 2020-03-29
 */
@RestController
@RequestMapping("/driver")
public class DriverController {

    private static final Logger logger = LoggerFactory.getLogger(DriverController.class);

    @Autowired
    private DriverService driverService;

    /**
     * 查询负责门店列表接口
     *
     * @param userId 要查询负责门店信息的司机id
     * @return
     */
    @PostMapping("/listDriverStores")
    public AppResponse listDriverStores(String userId) {
        try {
            return driverService.listDriverStores(userId);
        } catch (Exception e) {
            logger.error("查询门店列表信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }


}


















