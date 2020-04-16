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
     * @param imageFile 图片文件
     * @param imageCate 图片类别
     * @return
     */
    @PostMapping("/uploadImage")
    public AppResponse uploadImage(@RequestParam("imageFile") MultipartFile imageFile, int imageCate){
        try {
            return imageService.uploadImage(imageFile, imageCate);
        } catch (Exception e) {
            logger.error("上传图片异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}





















