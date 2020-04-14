package com.xzsd.pc.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    /**
     * 更新商品图片
     * @param goodsImage 要更新的商品图片
     * @param imageCateCode 图片分类的编号
     * @return
     */
    private int updateGoodsImage(MultipartFile goodsImage, String imageCateCode){
        Image image = new Image();
        //设置图片分类的编号
        image.setImageCateCode(imageCateCode);
        //设置基本属性
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        int status = 0;
        //当没有传入要修改的商品图片时
        if (goodsImage.isEmpty()) {
            //根据图片分类的编号修改图片信息
            status = imageMapper.updateByImageCateCodeSelective(image);
            //当传入要修改的商品图片时
        }else{
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
            //根据图片分类的编号修改图片信息
            status = imageMapper.updateByImageCateCodeSelective(image);
        }
        return status;
    }

    /**
     * 查询商品列表接口
     * @param pageBean 分页信息
     * @param goods 查询商品信息条件
     * @return
     */
    public AppResponse listGoods(PageBean pageBean, Goods goods){
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Goods> goodses = goodsMapper.listGoods(goods);
        PageInfo<Goods> pageData = new PageInfo<Goods>(goodses);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 修改商品信息接口
     * @param goodsImage 要修改的商品图片
     * @param goods 要修改的商品信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsById(MultipartFile goodsImage, Goods goods){
        //校验商品id不为null或着""
        if (goods.getGoodsId() == null || "".equals(goods.getGoodsId())) {
            return AppResponse.Error("没有该商品信息");
        }
        //校验数据库中有没有该id的记录
        Goods oldGoods = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (oldGoods == null) {
            return AppResponse.Error("没有该商品信息");
        } else if (!oldGoods.getVersion().equals(goods.getVersion())) {
            return AppResponse.Error("信息已更新，请重试");
        }
        //设置基本信息
        goods.setUpdatePerson(AuthUtils.getCurrentUserId());
        goods.setUpdateTime(new Date());
        goods.setVersion(oldGoods.getVersion() + 1);
        int status = goodsMapper.updateByPrimaryKeySelective(goods);
        if (status > 0) {
            //更新商品的图片
            int goodsImageStatus = updateGoodsImage(goodsImage, oldGoods.getGoodsId());
            //当为-1时，表示图片上传出现异常，为0时表示图片信息插入失败
            if(goodsImageStatus <= 0){
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("图片上传失败，请重试");
            }
            return AppResponse.success("修改商品信息成功");
        } else {
            return AppResponse.bizError("修改商品信息失败");
        }
    }

    /**
     * 修改商品状态接口
     * @param goods 要修改的商品信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsConditionById(Goods goods){
        if(goods.getGoodsId() == null || "".equals(goods.getGoodsId())){
            return AppResponse.Error("商品编号输入错误");
        }
        //当上架商品时
        if(goods.getGoodsCondition() == 1){
            //设置上架时间
            goods.setGoodsSaleTime(new Date());
        }
        //修改商品状态（上架、下架）
        int status = goodsMapper.updateByPrimaryKeySelective(goods);
        if(status > 0){
            return AppResponse.success("商品状态修改成功");
        }
        return AppResponse.bizError("商品状态修改失败");
    }

    /**
     * 删除商品接口
     * @param goodsIds 商品编号（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteGoodsById(String goodsIds){
        //检验要删除的goodsIds是否为null或者""
        if (goodsIds == null || "".equals(goodsIds)) {
            return AppResponse.Error("没有该商品信息，删除失败");
        }
        List<String> listIds = Arrays.asList(goodsIds.split(","));
        //删除商品信息列表集合，设置更新人id
        int count = goodsMapper.deleteGoodsById(listIds, AuthUtils.getCurrentUserId());
        //当要删除的商品总数和已删除的总数不等时，回滚事物，删除失败
        if (count != listIds.size()) {
            //回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        } else {
            //同时删除商品信息列表关联的商品图片
            imageMapper.deleteImageByGoodsId(listIds, AuthUtils.getCurrentUserId());
            return AppResponse.success("删除成功");
        }
    }

}






















