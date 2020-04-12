package com.xzsd.pc.image.controller;

import com.xzsd.pc.image.service.ImageService;
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
 * 图片控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    /**
     * 上传头像接口
     * @param headImage 头像图片文件
     * @param imageCateCode 上传头像的用户编号
     * @return
     */
    @PostMapping("/uploadheadImage")
    public AppResponse uploadheadImage(@RequestParam("headImage") MultipartFile headImage, String imageCateCode){
        try {
            return imageService.uploadheadImage(headImage, imageCateCode);
        } catch (Exception e) {
            logger.error("上传头像异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }


}





















