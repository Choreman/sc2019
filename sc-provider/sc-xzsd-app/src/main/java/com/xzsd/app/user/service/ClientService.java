package com.xzsd.app.user.service;


import com.xzsd.app.dict.dao.DictMapper;
import com.xzsd.app.dict.entity.Dict;
import com.xzsd.app.goods.dao.GoodsMapper;
import com.xzsd.app.goods.entity.Goods;
import com.xzsd.app.hotgoods.dao.HotGoodsMapper;
import com.xzsd.app.hotgoods.entity.HotGoods;
import com.xzsd.app.image.dao.ImageMapper;
import com.xzsd.app.image.entity.Image;
import com.xzsd.app.rollimage.dao.RollImageMapper;
import com.xzsd.app.rollimage.entity.RollImage;
import com.xzsd.app.store.dao.StoreMapper;
import com.xzsd.app.store.entity.Store;
import com.xzsd.app.user.dao.ClientMapper;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.utils.AppResponse;
import com.xzsd.app.utils.AuthUtils;
import com.xzsd.app.utils.PasswordUtils;
import com.xzsd.app.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 客户管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private StoreMapper storeMapper;

    @Resource
    private RollImageMapper rollImageMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private DictMapper dictMapper;

    @Resource
    private HotGoodsMapper hotGoodsMapper;

    /**
     * 客户注册接口
     *
     * @param user 包含客户信息、头像编号、门店邀请码
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse registerClient(User user) {
        //判断用户账号是否为null或者""
        if (user.getUserLoginName() == null || "".equals(user.getUserLoginName().trim())) {
            return AppResponse.bizError("客户账号输入有误，请重新输入");
        }
        //查询数据库中是否有该账号的用户
        int count = clientMapper.countUserByUserLoginName(user.getUserLoginName());
        //数据库中存在相同账号的用户
        if (count != 0) {
            return AppResponse.Error("客户账号已存在");
        }
        //校验门店邀请码
        if (user.getStoreInvitationCode() != null && !"".equals(user.getStoreInvitationCode())) {
            //查询是否有该门店的邀请码
            Store store = storeMapper.selectStoreByStoreInvitationCode(user.getStoreInvitationCode());
            if (store != null) {
                //设置客户关联门店编号
                user.setUserStoreId(store.getStoreId());
            } else {
                return AppResponse.Error("门店邀请码错误");
            }
        }
        //设置UUID为主键
        user.setUserId(UUIDUtils.getUUID());
        //设置用户展示的编号（年月日时分秒+2位随机数）
        user.setUserCode(UUIDUtils.getTimeRandom(2));
        //把用户密码加密
        user.setUserPassword(PasswordUtils.generatePassword(user.getUserPassword()));
        //设置用户角色为客户
        user.setUserRole(4);
        //设置基本属性
        user.setCreateTime(new Date());
        //由于新增客户时无需登录，所以获取不到登录人的id，此处随意填写
        user.setCreatePerson("1");
        user.setUpdateTime(new Date());
        //由于新增客户时无需登录，所以获取不到登录人的id，此处随意填写
        user.setUpdatePerson("1");
        user.setVersion(1);
        user.setIsDeleted(1);
        int status = clientMapper.insertSelective(user);
        if (status > 0) {
            //如果新增用户时有上传头像
            if (user.getImageId() != null && !"".equals(user.getImageId())) {
                Image image = new Image();
                image.setImageId(user.getImageId());
                image.setImageCateCode(user.getUserId());
                //通过图片的id修改图片的分类编号，把用户表的用户信息和图片表的头像图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //头像和用户信息没有关联成功
                if (headImageStatus == 0) {
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("新增客户信息失败，请输入正确的头像地址");
                }
            }
            return AppResponse.success("新增客户信息成功");
        }
        return AppResponse.bizError("新增客户信息失败");
    }

    /**
     * 修改客户店铺邀请码接口
     *
     * @param user 包含客户id、门店邀请码
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateClientStoreInvitationCodeById(User user) {
        if(user.getStoreInvitationCode() == null || "".equals(user.getStoreInvitationCode())){
            return AppResponse.Error("门店编号错误");
        }
        //查询是否有该门店的邀请码
        Store store = storeMapper.selectStoreByStoreInvitationCode(user.getStoreInvitationCode());
        if(store == null){
            return AppResponse.Error("该门店信息不存在");
        }
        //设置该门店邀请码的店铺id
        user.setUserStoreId(store.getStoreId());
        int count = clientMapper.updateClientStoreInvitationCodeById(user);
        if(count > 0){
            return AppResponse.success("修改门店邀请码成功");
        }
        return AppResponse.bizError("修改门店邀请码失败");
    }

    /**
     * 查询轮播图列表接口
     *
     * @return
     */
    public AppResponse listRollImages() {
        List<RollImage> rollImages = rollImageMapper.listRollImages();
        return AppResponse.success("查询成功", rollImages);
    }

    /**
     * 查询单个商品接口
     *
     * @param goodsId 商品id
     * @return
     */
    public AppResponse findGoodsById(String goodsId) {
        if(goodsId == null || "".equals(goodsId)){
            return AppResponse.Error("商品编号错误");
        }
        Goods goods = goodsMapper.findGoodsById(goodsId);
        if(goods != null){
            return AppResponse.success("商品详情查询成功", goods);
        }
        return AppResponse.bizError("商品详情查询失败");
    }

    /**
     * 查询热门商品列表接口
     *
     * @return
     */
    public AppResponse listHotGoods() {
        Dict dict = dictMapper.selectDictByKey("hotGoodsDisplayNum");
        int hotGoodsDisplayNum = 0;
        if(dict != null){
            //获取字典表里的热门位商品展示数量
            hotGoodsDisplayNum = Integer.parseInt(dict.getDictValue());
        }
        //如果获取的热门位商品展示数量为0，则设置默认展示值为10
        if(hotGoodsDisplayNum == 0){
            hotGoodsDisplayNum = 10;
        }
        List<HotGoods> hotGoods = hotGoodsMapper.listHotGoodsByHotGoodsDisplayNum(hotGoodsDisplayNum);
        return AppResponse.success("查询成功", hotGoods);
    }

}















