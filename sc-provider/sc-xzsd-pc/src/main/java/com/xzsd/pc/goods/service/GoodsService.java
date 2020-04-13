package com.xzsd.pc.goods.service;

import com.xzsd.pc.goods.dao.GoodsMapper;
import com.xzsd.pc.goods.entity.Goods;
import com.xzsd.pc.image.dao.ImageMapper;
import com.xzsd.pc.image.entity.Image;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.TencentCOSUtil;
import com.xzsd.pc.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 商品信息业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@Service
public class GoodsService {

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 新增商品接口
     * @param goodsImage 商品图片
     * @param goods 商品信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addGoods(MultipartFile goodsImage, Goods goods) {
        if(goods.getGoodsIsbn() == null || "".equals(goods.getGoodsIsbn())){
            return AppResponse.Error("商品书号错误，新增失败");
        }
        int count = goodsMapper.countGoodsByIsbn(goods.getGoodsIsbn());
        //数据库中存在相同isbn的商品
        if (count != 0) {
            return AppResponse.Error("商品已存在");
        }
        //设置UUID为主键
        goods.setGoodsId(UUIDUtils.getUUID());
        //设置商品展示的编号（年月日时分秒+2位随机数）
        goods.setGoodsCode(UUIDUtils.getTimeRandom(2));
        //设置商品默认销售量为0
        goods.setGoodsSaleSum(0);
        //设置商品默认销售状态为0：未发布
        goods.setGoodsCondition(0);
        //设置商品默认总访问量为0
        goods.setGoodsVisitNum(0);
        //设置商品评价星级默认为0
        goods.setGoodsStar(0);
        //设置基本属性
        goods.setCreateTime(new Date());
        goods.setCreatePerson(AuthUtils.getCurrentUserId());
        goods.setUpdateTime(new Date());
        goods.setUpdatePerson(AuthUtils.getCurrentUserId());
        goods.setVersion(1);
        goods.setIsDeleted(1);
        int status = goodsMapper.insertSelective(goods);
        //商品新增成功
        if (status > 0) {
            //上传商品图片
            int headImageStatus = uploadGoodsImage(goodsImage, goods.getGoodsId());
            //-1表示上传图片出现异常，0表示新增图片信息失败
            if(headImageStatus == -1 || headImageStatus == 0){
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("图片上传失败，请重试");
            }
            return AppResponse.success("新增商品信息成功");
        }
        return AppResponse.bizError("新增商品信息失败");
    }

    /**
     * 上传商品图片
     * @param goodsImage 商品图片
     * @param imageCateCode 图片类别编号，此处是商品编号
     * @return
     */
    private int uploadGoodsImage(MultipartFile goodsImage, String imageCateCode){
        Image image = new Image();
        //设置UUID为主键
        image.setImageId(UUIDUtils.getUUID());
        //设置图片类别为商品
        image.setImageCate(TencentCOSUtil.GOODSIMAGECATE);
        //设置上传商品图片的商品编号
        image.setImageCateCode(imageCateCode);
        //设置基本属性
        image.setCreateTime(new Date());
        image.setCreatePerson(AuthUtils.getCurrentUserId());
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        image.setIsDeleted(1);
        image.setVersion(1);
        //当传入头像图片时
        if (!goodsImage.isEmpty()) {
            String url = null;
            try {
                //上传到指定的文件夹里面
                url = tencentCOSUtil.uploadImage(goodsImage, TencentCOSUtil.GOODSIMAGEFOLDER);
            }catch (Exception e){
                //表示上传图片出现异常
                return -1;
            }
            //设置图片的url
            image.setImageUrl(url);
        }
        return imageMapper.insertSelective(image);
    }


}
