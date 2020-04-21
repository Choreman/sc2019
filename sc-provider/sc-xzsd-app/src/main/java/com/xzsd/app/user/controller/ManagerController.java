package com.xzsd.app.user.controller;

import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.order.entity.Order;
import com.xzsd.app.user.service.ManagerService;
import com.xzsd.app.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店长端管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@RestController
@RequestMapping("/manager")
public class ManagerController {

    private static final Logger logger = LoggerFactory.getLogger(ManagerController.class);

    @Autowired
    private ManagerService managerService;

    /**
     * 查询店铺订单列表接口
     *
     * @param pageBean       分页信息
     * @param userId         店长id
     * @param orderCondition 订单状态
     * @return
     */
    @PostMapping("/listStoreOrders")
    public AppResponse listStoreOrders(PageBean pageBean, String userId, int orderCondition) {
        try {
            return managerService.listStoreOrders(pageBean, userId, orderCondition);
        } catch (Exception e) {
            logger.error("查询门店订单信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改店铺订单状态接口
     *
     * @param order 包含订单id、订单状态、版本号
     * @return
     */
    @PostMapping("/updateStoreOrderCondition")
    public AppResponse updateStoreOrderCondition(Order order) {
        try {
            return managerService.updateStoreOrderCondition(order);
        } catch (Exception e) {
            logger.error("修改门店订单状态异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询店铺订单详情接口
     *
     * @param orderId 订单编号
     * @return
     */
    @PostMapping("/findStoreOrderDetail")
    public AppResponse findStoreOrderDetail(String orderId) {
        try {
            return managerService.findStoreOrderDetail(orderId);
        } catch (Exception e) {
            logger.error("查询门店订单详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据店长id查询店铺的司机信息接口
     *
     * @param userId 店长id
     * @return
     */
    @PostMapping("/findStoreDriverById")
    public AppResponse findStoreDriverById(String userId) {
        try {
            return managerService.findStoreDriverById(userId);
        } catch (Exception e) {
            logger.error("查询个人信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}






















