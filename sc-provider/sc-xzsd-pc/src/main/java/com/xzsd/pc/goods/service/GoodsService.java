package com.xzsd.pc.goods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.goods.dao.GoodsMapper;
import com.xzsd.pc.goods.entity.Goods;
import com.xzsd.pc.hotgoods.dao.HotGoodsMapper;
import com.xzsd.pc.hotgoods.entity.HotGoods;
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
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    private HotGoodsMapper hotGoodsMapper;

    @Resource
    private RollImageMapper rollImageMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 新增商品接口
     *
     * @param goods   商品信息
     * @param imageId 商品图片编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addGoods(Goods goods, String imageId) {
        if (goods.getGoodsIsbn() == null || "".equals(goods.getGoodsIsbn())) {
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
            //如果新增商品时有上传商品图片
            if (imageId != null && !"".equals(imageId)) {
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(goods.getGoodsId());
                //通过图片的id修改图片的分类编号，把商品表的商品信息和图片表的商品图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //商品图片和商品信息没有关联成功
                if (headImageStatus == 0) {
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("新增商品信息失败，请输入正确的头像地址");
                }
            }
            return AppResponse.success("新增商品信息成功");
        }
        return AppResponse.bizError("新增商品信息失败");
    }

    /**
     * 上传商品图片（需求更改，此方法弃用）
     *
     * @param goodsImage    商品图片
     * @param imageCateCode 图片类别编号，此处是商品编号
     * @return
     */
    @Deprecated
    private int uploadGoodsImage(MultipartFile goodsImage, String imageCateCode) {
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
     * 更新商品图片（需求更改，此方法弃用）
     *
     * @param goodsImage    要更新的商品图片
     * @param imageCateCode 图片分类的编号
     * @return
     */
    @Deprecated
    private int updateGoodsImage(MultipartFile goodsImage, String imageCateCode) {
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
        } else {
            String url = null;
            try {
                //上传到指定的文件夹里面
                url = tencentCOSUtil.uploadImage(goodsImage, TencentCOSUtil.GOODSIMAGEFOLDER);
            } catch (Exception e) {
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
     *
     * @param pageBean 分页信息
     * @param goods    查询商品信息条件
     * @return
     */
    public AppResponse listGoods(PageBean pageBean, Goods goods) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Goods> goodses = goodsMapper.listGoods(goods);
        PageInfo<Goods> pageData = new PageInfo<Goods>(goodses);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 查询商品列表接口
     *
     * @param pageBean 分页信息
     * @param goods    查询商品信息条件
     * @return
     */
    public AppResponse listAllGoods(PageBean pageBean, Goods goods) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Goods> goodses = goodsMapper.listAllGoods(goods);
        PageInfo<Goods> pageData = new PageInfo<Goods>(goodses);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 修改商品信息接口
     *
     * @param goods   要修改的商品信息
     * @param imageId 要修改的商品图片编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsById(Goods goods, String imageId) {
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
            //如果修改商品时有上传商品图
            if (imageId != null && !"".equals(imageId)) {
                //把之前的商品图片进行删除
                imageMapper.deleteImageByImageCateCode(goods.getGoodsId(), AuthUtils.getCurrentUserId());
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(goods.getGoodsId());
                //通过图片的id，把商品表的商品信息和图片表的商品图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //商品图片和商品信息没有关联成功
                if (headImageStatus == 0) {
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("修改商品信息失败，请输入正确的头像地址");
                }
            }
            return AppResponse.success("修改商品信息成功");
        } else {
            return AppResponse.bizError("修改商品信息失败");
        }
    }

    /**
     * 修改商品状态接口
     *
     * @param goodsIds       要修改的商品编号（批量修改用逗号分开）
     * @param goodsCondition 要修改的商品状态
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsConditionById(String goodsIds, int goodsCondition) {
        if (goodsIds == null || "".equals(goodsIds)) {
            return AppResponse.Error("商品编号输入错误");
        }
        //获取商品id列表
        List<String> listIds = Arrays.asList(goodsIds.split(","));
        //当上架商品时，需要剔除库存小于0的商品
        if (goodsCondition == 1) {
            //查询商品id列表里的所有商品列表信息
            List<Goods> goodsList = goodsMapper.findGoodsById(listIds);
            //初始化商品id列表，可以重新添加剔除后的商品id列表
            listIds = new ArrayList<String>();
            //循环查询商品列表
            for (Goods goods : goodsList) {
                //当商品还有库存时才符合上架条件
                if (goods.getGoodsStock() > 0) {
                    listIds.add(goods.getGoodsId());
                }
            }
        }
        if(listIds.size() != 0){
            //修改商品信息（上架、下架、上架时间）
            int status = goodsMapper.updateGoodsListCondition(listIds, goodsCondition, AuthUtils.getCurrentUserId());
            if (status > 0) {
                return AppResponse.success("商品状态修改成功");
            }
            return AppResponse.bizError("商品状态修改失败");
        }
        return AppResponse.Error("所选商品库存不足，无法上架");
    }

    /**
     * 删除商品接口
     *
     * @param goodsIds 商品编号（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteGoodsById(String goodsIds) {
        //检验要删除的goodsIds是否为null或者""
        if (goodsIds == null || "".equals(goodsIds)) {
            return AppResponse.Error("没有该商品信息，删除失败");
        }
        List<String> listIds = Arrays.asList(goodsIds.split(","));
        //设置商品存在热门位或轮播图标记
        boolean flag = false;
        //1.检查商品编号列表中是否添加热门位，有则剔除该商品编号，不进行删除
        //获取要删除的商品中在热门位中的信息
        List<HotGoods> hotGoodsList = hotGoodsMapper.listHotGoodsByIds(listIds);
        listIds = new ArrayList<>(listIds);
        for (HotGoods hotGoods : hotGoodsList){
            //如果要删除的商品编号列表中存在热门位，则在要删除的商品编号列表中删除该商品编号
            if(listIds.contains(hotGoods.getHotGoodsGoodsCode())){
                listIds.remove(hotGoods.getHotGoodsGoodsCode());
                flag = true;
            }
        }
        //2.检查商品编号列表中是否添加轮播图，有则剔除该商品编号，不进行删除
        if (listIds.size() != 0){
            //获取要删除的商品中在轮播图中的信息
            List<RollImage> rollImageList = rollImageMapper.listRollImageByIds(listIds);
            for(RollImage rollImage : rollImageList){
                //如果要删除的商品编号列表中存在轮播图，则在要删除的商品编号列表中删除该商品编号
                if(listIds.contains(rollImage.getRollImageGoodsCode())){
                    listIds.remove(rollImage.getRollImageGoodsCode());
                    flag = true;
                }
            }
        }
        //3.删除商品信息
        if(listIds.size() != 0){
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
                if (flag){
                    return AppResponse.success("部分商品存在于热门位或轮播图无法删除，部分删除成功");
                }
                return AppResponse.success("删除成功");
            }
        }
        return AppResponse.Error("所选商品存在于热门位或轮播图，删除失败");
    }

}























