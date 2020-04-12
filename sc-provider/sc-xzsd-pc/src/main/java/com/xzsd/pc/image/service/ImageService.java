package com.xzsd.pc.image.service;

import com.xzsd.pc.image.dao.ImageMapper;
import com.xzsd.pc.image.entity.Image;
import com.xzsd.pc.user.dao.UserMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.TencentCOSUtil;
import com.xzsd.pc.utils.UUIDUtils;
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
    private UserMapper userMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 上传头像接口
     * @param headImage 头像图片文件
     * @param imageCateCode 上传头像的用户编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse uploadheadImage(MultipartFile headImage, String imageCateCode) {
        //校验头像图片是否存在
        if (headImage.isEmpty()) {
            return AppResponse.Error("上传失败，请选择文件");
        }
        //校验用户编号是否存在
        if (imageCateCode == null || "".equals(imageCateCode)){
            return AppResponse.Error("上传失败，请输入用户编号");
        }
        User user = userMapper.selectByPrimaryKey(imageCateCode);
        if(user == null){
            return AppResponse.Error("上传失败，没有该用户编号");
        }
        String url = null;
        try {
            //上传到指定的文件夹里面
            url = tencentCOSUtil.uploadImage(headImage, TencentCOSUtil.HEADIMAGEFOLDER);
        }catch (IOException e){
            return AppResponse.bizError("图片上传出现异常");
        }
        Image image = new Image();
        //设置UUID为主键
        image.setImageId(UUIDUtils.getUUID());
        //设置图片类别为头像
        image.setImageCate(TencentCOSUtil.HEADIMAGECATE);
        //设置上传头像的用户编号
        image.setImageCateCode(imageCateCode);
        //设置图片的url
        image.setImageUrl(url);
        //设置基本属性
        image.setCreateTime(new Date());
        image.setCreatePerson(AuthUtils.getCurrentUserId());
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        image.setIsDeleted(1);
        image.setVersion(1);

        int status = imageMapper.insertSelective(image);
        if (status > 0) {
            return AppResponse.success("用户头像上传成功", image);
        } else {
            return AppResponse.bizError("用户头像上传失败");
        }
    }

}






















