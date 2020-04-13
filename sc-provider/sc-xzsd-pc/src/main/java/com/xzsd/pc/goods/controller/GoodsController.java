package com.xzsd.pc.goods.controller;

import com.xzsd.pc.goods.entity.Goods;
import com.xzsd.pc.goods.service.GoodsService;
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
 * 商品信息控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    private static final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 新增商品接口
     * @param goodsImage 商品图片
     * @param goods 商品信息
     * @return
     */
    @PostMapping("/addGoods")
    public AppResponse addGoods(@RequestParam("goodsImage") MultipartFile goodsImage, Goods goods) {
        try {
            return goodsService.addGoods(goodsImage, goods);
        } catch (Exception e) {
            logger.error("新增商品信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}
