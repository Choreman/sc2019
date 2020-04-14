package com.xzsd.pc.goods.controller;

import com.github.pagehelper.Page;
import com.xzsd.pc.base.bean.PageBean;
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

    /**
     * 查询商品列表接口
     * @param pageBean 分页信息
     * @param goods 查询商品信息条件
     * @return
     */
    @PostMapping("/listGoods")
    public AppResponse listGoods(PageBean pageBean, Goods goods){
        try {
            return goodsService.listGoods(pageBean, goods);
        } catch (Exception e) {
            logger.error("查询商品信息列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改商品信息接口
     * @param goodsImage 要修改的商品图片
     * @param goods 要修改的商品信息
     * @return
     */
    @PostMapping("/updateGoodsById")
    public AppResponse updateGoodsById(@RequestParam("goodsImage") MultipartFile goodsImage, Goods goods){
        try {
            return goodsService.updateGoodsById(goodsImage, goods);
        } catch (Exception e) {
            logger.error("修改商品信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改商品状态接口
     * @param goods 要修改的商品信息
     * @return
     */
    @PostMapping("/updateGoodsConditionById")
    public AppResponse updateGoodsConditionById(Goods goods){
        try {
            return goodsService.updateGoodsConditionById(goods);
        } catch (Exception e) {
            logger.error("修改商品状态异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除商品接口
     * @param goodsIds 商品编号（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteGoodsById")
    public AppResponse deleteGoodsById(String goodsIds){
        try {
            return goodsService.deleteGoodsById(goodsIds);
        } catch (Exception e) {
            logger.error("删除商品信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }
}


















