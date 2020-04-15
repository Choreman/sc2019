package com.xzsd.pc.rollimage.controller;

import com.github.pagehelper.Page;
import com.xzsd.pc.area.service.AreaService;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.rollimage.entity.RollImage;
import com.xzsd.pc.rollimage.service.RollImageService;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

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

    /**
     * 新增轮播图接口
     * @param rollImageFile 轮播图图片
     * @param rollImage 轮播图信息
     * @return
     */
    @PostMapping("/addRollImage")
    public AppResponse addRollImage(@RequestParam("rollImageFile") MultipartFile rollImageFile, RollImage rollImage){
        try {
            System.out.println("11" + rollImage.getRollImageBeginDate());
            return rollImageService.addRollImage(rollImageFile, rollImage);
        } catch (Exception e) {
            logger.error("新增轮播图异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询轮播图列表接口
     * @param pageBean 分页信息
     * @param rollImageCondition 轮播图状态
     * @return
     */
    @PostMapping("/listRollImages")
    public AppResponse listRollImages(PageBean pageBean, String rollImageCondition){
        try {
            return rollImageService.listRollImages(pageBean, rollImageCondition);
        } catch (Exception e) {
            logger.error("查询轮播图列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改轮播图状态接口
     * @param rollImageIds 轮播图编号列表
     * @param rollImageCondition 轮播图状态
     * @return
     */
    @PostMapping("/updateRollImageConditionById")
    public AppResponse updateRollImageConditionById(String rollImageIds, int rollImageCondition){
        try {
            return rollImageService.updateRollImageConditionById(rollImageIds, rollImageCondition);
        } catch (Exception e) {
            logger.error("修改轮播图状态异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除轮播图接口
     * @param rollImageIds 轮播图编号列表（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteRollImageById")
    public AppResponse deleteRollImageById(String rollImageIds){
        try {
            return rollImageService.deleteRollImageById(rollImageIds);
        } catch (Exception e) {
            logger.error("删除轮播图列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}


















