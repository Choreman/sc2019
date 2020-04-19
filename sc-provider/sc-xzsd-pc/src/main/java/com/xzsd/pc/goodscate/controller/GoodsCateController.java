package com.xzsd.pc.goodscate.controller;


import com.xzsd.pc.goodscate.entity.GoodsCate;
import com.xzsd.pc.goodscate.service.GoodsCateService;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 新增商品分类接口
     * @param goodsCate 商品分类信息
     * @return
     */
    @PostMapping("/addGoodsCate")
    public AppResponse addGoodsCate(GoodsCate goodsCate){
        try {
            return goodsCateService.addGoodsCate(goodsCate);
        } catch (Exception e) {
            logger.error("新增商品分类异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询树形商品分类列表接口
     * @return
     */
    @PostMapping("/listTreeGoodsCates")
    public AppResponse listTreeGoodsCates(){
        try {
            return goodsCateService.listTreeGoodsCates();
        } catch (Exception e) {
            logger.error("查询树形商品分类列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询商品分类详情接口
     * @param goodsCateId 商品分类id
     * @return
     */
    @PostMapping("/findGoodsCateById")
    public AppResponse findGoodsCateById(String goodsCateId){
        try {
            return goodsCateService.findGoodsCateById(goodsCateId);
        } catch (Exception e) {
            logger.error("查询商品分类详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改商品分类详情接口
     * @param goodsCate 商品分类信息
     * @return
     */
    @PostMapping("/updateGoodsCateById")
    public AppResponse updateGoodsCateById(GoodsCate goodsCate){
        try {
            return goodsCateService.updateGoodsCateById(goodsCate);
        } catch (Exception e) {
            logger.error("修改商品分类信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除商品分类接口
     * @param goodsCateId 商品分类编号（没有批量删除）
     * @return
     */
    @PostMapping("/deleteGoodsCateById")
    public AppResponse deleteGoodsCateById(String goodsCateId){
        try {
            return goodsCateService.deleteGoodsCateById(goodsCateId);
        } catch (Exception e) {
            logger.error("删除商品分类信息列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据父级编号查询商品分类列表接口
     * @param cateParent 父级商品分类编号
     * @return
     */
    @PostMapping("/listGoodsCatesByParentCode")
    public AppResponse listGoodsCatesByParentCode(String cateParent){
        try {
            return goodsCateService.listGoodsCatesByParentCode(cateParent);
        } catch (Exception e) {
            logger.error("查询父级商品分类信息列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}


















