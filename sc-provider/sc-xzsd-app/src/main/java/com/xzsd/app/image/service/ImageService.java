package com.xzsd.app.image.service;

import com.xzsd.app.image.dao.ImageMapper;
import com.xzsd.app.image.entity.Image;
import com.xzsd.app.utils.AppResponse;
import com.xzsd.app.utils.AuthUtils;
import com.xzsd.app.utils.TencentCOSUtil;
import com.xzsd.app.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;

/**
 * 图片业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@Service
public class ImageService {

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 上传头像接口
     * @param imageFile 图片文件
     * @param imageCate 图片类别
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse uploadImage(MultipartFile imageFile, int imageCate) {
        //校验图片是否存在
        if (imageFile.isEmpty()) {
            return AppResponse.Error("上传失败，请选择图片");
        }
        //给上传的图片分文件夹
        String imageFolder = "default";
        switch (imageCate){
            case 1 :
                imageFolder = TencentCOSUtil.GOODSIMAGEFOLDER;
                break;
            case 2 :
                imageFolder = TencentCOSUtil.ROLLIMAGEFOLDER;
                break;
            case 3 :
                imageFolder = TencentCOSUtil.GOODSCOMMENTIMAGEFOLDER;
                break;
            case 4 :
                imageFolder = TencentCOSUtil.HEADIMAGEFOLDER;
                break;
        }
        String url = null;
        try {
            //上传到指定的文件夹里面
            url = tencentCOSUtil.uploadImage(imageFile, imageFolder);
        }catch (IOException e){
            return AppResponse.bizError("图片上传出现异常");
        }
        Image image = new Image();
        //设置UUID为主键
        image.setImageId(UUIDUtils.getUUID());
        //设置图片类别
        image.setImageCate(imageCate);
        //设置图片的url
        image.setImageUrl(url);
        //设置基本属性
        image.setCreateTime(new Date());
        //由于上传头像时无需登录，所以获取不到登录人的id，此处随意填写
        image.setCreatePerson("1");
        image.setUpdateTime(new Date());
        //由于上传头像时无需登录，所以获取不到登录人的id，此处随意填写
        image.setUpdatePerson("1");
        image.setIsDeleted(1);
        image.setVersion(1);

        int status = imageMapper.insertSelective(image);
        if (status > 0) {
            return AppResponse.success("图片上传成功", image);
        }
        return AppResponse.bizError("图片上传失败");
    }

}






















