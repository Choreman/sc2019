package com.xzsd.app.user.controller;

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

    /**
     * 客户注册接口
     *
     * @param user 包含客户信息、头像编号、门店邀请码
     * @return
     */
    @PostMapping("/registerClient")
    public AppResponse registerClient(User user) {
        try {
            return clientService.registerClient(user);
        } catch (Exception e) {
            logger.error("注册客户信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改客户店铺邀请码接口
     *
     * @param user 包含客户id、门店邀请码
     * @return
     */
    @PostMapping("/updateClientStoreInvitationCodeById")
    public AppResponse updateClientStoreInvitationCodeById(User user) {
        try {
            return clientService.updateClientStoreInvitationCodeById(user);
        } catch (Exception e) {
            logger.error("修改客户邀请码异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询轮播图列表接口
     *
     * @return
     */
    @PostMapping("/listRollImages")
    public AppResponse listRollImages() {
        try {
            return clientService.listRollImages();
        } catch (Exception e) {
            logger.error("查询轮播图信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询单个商品接口
     *
     * @param goodsId 商品id
     * @return
     */
    @PostMapping("/findGoodsById")
    public AppResponse findGoodsById(String goodsId) {
        try {
            return clientService.findGoodsById(goodsId);
        } catch (Exception e) {
            logger.error("查询商品详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询热门商品列表接口
     *
     * @return
     */
    @PostMapping("/listHotGoods")
    public AppResponse listHotGoods() {
        try {
            return clientService.listHotGoods();
        } catch (Exception e) {
            logger.error("查询热门商品列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }
}




















