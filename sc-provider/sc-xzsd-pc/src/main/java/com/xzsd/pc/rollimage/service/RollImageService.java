package com.xzsd.pc.rollimage.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.area.dao.AreaMapper;
import com.xzsd.pc.area.entity.Area;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.goods.dao.GoodsMapper;
import com.xzsd.pc.goods.entity.Goods;
import com.xzsd.pc.image.dao.ImageMapper;
import com.xzsd.pc.image.entity.Image;
import com.xzsd.pc.rollimage.dao.RollImageMapper;
import com.xzsd.pc.rollimage.entity.RollImage;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.TencentCOSUtil;
import com.xzsd.pc.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 轮播图业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-14
 */
@Service
public class RollImageService {

    @Resource
    private RollImageMapper rollImageMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 新增轮播图接口
     *
     * @param rollImageFile 轮播图图片
     * @param rollImage     轮播图信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addRollImage(MultipartFile rollImageFile, RollImage rollImage) {
        //判断选择商品编号是否为null或者""
        if (rollImage.getRollImageGoodsCode() == null || "".equals(rollImage.getRollImageGoodsCode())) {
            return AppResponse.bizError("商品编号输入有误，请重新输入");
        }
        int count = rollImageMapper.countRollImageByRollImageGoodsCode(rollImage.getRollImageGoodsCode());
        //如果已经存在该商品的轮播图
        if (count > 0) {
            return AppResponse.Error("已经存在该商品的轮播图，新增失败");
        }
        //校验开始和结束时间是否符合规定
        if (rollImage.getRollImageBeginDate().equals(rollImage.getRollImageEndDate())) {
            return AppResponse.Error("开始和结束时间不能一致");
        } else {
            if (rollImage.getRollImageBeginDate().after(rollImage.getRollImageEndDate())) {
                return AppResponse.Error("开始时间不能在结束时间之后");
            }
        }
        //轮播图排序不能小于0
        if (rollImage.getRollImageWeight() < 0) {
            return AppResponse.Error("排序小于0");
        }
        //查询是否有相同排序的轮播图
        int weightCount = rollImageMapper.countRollImageByWeight(rollImage.getRollImageWeight());
        if (weightCount > 0) {
            return AppResponse.Error("排序已存在，新增失败");
        }
        //设置UUID为主键
        rollImage.setRollImageId(UUIDUtils.getUUID());
        //设置图片状态默认为1：启用
        rollImage.setRollImageCondition(1);
        //设置基本属性
        rollImage.setCreateTime(new Date());
        rollImage.setCreatePerson(AuthUtils.getCurrentUserId());
        rollImage.setUpdateTime(new Date());
        rollImage.setUpdatePerson(AuthUtils.getCurrentUserId());
        rollImage.setVersion(1);
        rollImage.setIsDeleted(1);
        int status = rollImageMapper.insertSelective(rollImage);
        //轮播图新增成功
        if (status > 0) {
            //上传轮播图
            int headImageStatus = uploadHeadImage(rollImageFile, rollImage.getRollImageId());
            //-1表示上传图片出现异常，0表示新增图片信息失败
            if (headImageStatus == -1 || headImageStatus == 0) {
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("图片上传失败，请重试");
            }
            return AppResponse.success("新增轮播图信息成功");
        }
        return AppResponse.bizError("新增轮播图信息失败");
    }

    /**
     * 上传轮播图
     *
     * @param rollImage     轮播图图片
     * @param imageCateCode 图片类别编号，此处是轮播图编号
     * @return
     */
    private int uploadHeadImage(MultipartFile rollImage, String imageCateCode) {
        Image image = new Image();
        //设置UUID为主键
        image.setImageId(UUIDUtils.getUUID());
        //设置图片类别为头像
        image.setImageCate(TencentCOSUtil.ROLLIMAGECATE);
        //设置上传头像的用户编号
        image.setImageCateCode(imageCateCode);
        //设置基本属性
        image.setCreateTime(new Date());
        image.setCreatePerson(AuthUtils.getCurrentUserId());
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        image.setIsDeleted(1);
        image.setVersion(1);
        //当传入头像图片时
        if (!rollImage.isEmpty()) {
            String url = null;
            try {
                //上传到指定的文件夹里面
                url = tencentCOSUtil.uploadImage(rollImage, TencentCOSUtil.ROLLIMAGEFOLDER);
            } catch (Exception e) {
                //表示上传图片出现异常
                return -1;
            }
            //设置图片的url
            image.setImageUrl(url);
        }
        return imageMapper.insertSelective(image);
    }

    /**
     * 查询轮播图列表接口
     *
     * @param pageBean           分页信息
     * @param rollImageCondition 轮播图状态
     * @return
     */
    public AppResponse listRollImages(PageBean pageBean, String rollImageCondition) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<RollImage> rollImages = rollImageMapper.listRollImages(rollImageCondition);
        PageInfo<RollImage> pageData = new PageInfo<RollImage>(rollImages);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 修改轮播图状态接口
     * @param rollImageIds 轮播图编号列表
     * @param rollImageCondition 轮播图状态
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateRollImageConditionById(String rollImageIds, int rollImageCondition) {
        if(rollImageIds == null || "".equals(rollImageIds)){
            return AppResponse.Error("轮播图编号错误");
        }
        List<String> listIds = Arrays.asList(rollImageIds.split(","));
        //根据轮播图编号查询出轮播图列表
        List<RollImage> rollImageList = rollImageMapper.listRollImagesByIds(listIds);
        //批量修改轮播图信息
        int status = rollImageMapper.updateRollImageListCondition(rollImageList, AuthUtils.getCurrentUserId(), rollImageCondition);
        //当修改的轮播图信息和查询出来的轮播图信息不一致
        if (status != rollImageList.size()) {
            //回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("批量修改轮播图状态失败");
        }
        return AppResponse.success("批量修改轮播图状态成功");
    }

    /**
     * 删除轮播图接口
     * @param rollImageIds 轮播图编号列表（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteRollImageById(String rollImageIds){
        //检验要删除的ids是否为null或者""
        if (rollImageIds == null || "".equals(rollImageIds)) {
            return AppResponse.Error("没有该轮播图信息，删除失败");
        }
        List<String> listIds = Arrays.asList(rollImageIds.split(","));
        //删除轮播图信息列表集合，设置更新人id
        int count = rollImageMapper.deleteRollImageById(listIds, AuthUtils.getCurrentUserId());
        //当要删除的轮播图总数和已删除的总数不等时，回滚事物，删除失败
        if (count != listIds.size()) {
            //回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        }
        return AppResponse.success("删除成功");
    }

}











