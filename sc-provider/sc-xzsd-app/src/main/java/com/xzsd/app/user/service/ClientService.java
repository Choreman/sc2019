package com.xzsd.app.user.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.dict.dao.DictMapper;
import com.xzsd.app.dict.entity.Dict;
import com.xzsd.app.goods.dao.GoodsMapper;
import com.xzsd.app.goods.entity.Goods;
import com.xzsd.app.goodscate.dao.GoodsCateMapper;
import com.xzsd.app.goodscate.entity.GoodsCate;
import com.xzsd.app.hotgoods.dao.HotGoodsMapper;
import com.xzsd.app.hotgoods.entity.HotGoods;
import com.xzsd.app.image.dao.ImageMapper;
import com.xzsd.app.image.entity.Image;
import com.xzsd.app.order.dao.OrderMapper;
import com.xzsd.app.order.entity.Order;
import com.xzsd.app.orderdetail.dao.OrderDetailMapper;
import com.xzsd.app.orderdetail.entity.OrderDetail;
import com.xzsd.app.rollimage.dao.RollImageMapper;
import com.xzsd.app.rollimage.entity.RollImage;
import com.xzsd.app.shoppingcart.dao.ShoppingCartMapper;
import com.xzsd.app.shoppingcart.entity.ShoppingCart;
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
import java.util.ArrayList;
import java.util.Arrays;
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

    @Resource
    private GoodsCateMapper goodsCateMapper;

    @Resource
    private ShoppingCartMapper shoppingCartMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderDetailMapper orderDetailMapper;


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
        if (user.getStoreInvitationCode() == null || "".equals(user.getStoreInvitationCode())) {
            return AppResponse.Error("门店编号错误");
        }
        //查询是否有该门店的邀请码
        Store store = storeMapper.selectStoreByStoreInvitationCode(user.getStoreInvitationCode());
        if (store == null) {
            return AppResponse.Error("该门店信息不存在");
        }
        //设置该门店邀请码的店铺id
        user.setUserStoreId(store.getStoreId());
        int count = clientMapper.updateClientStoreInvitationCodeById(user);
        if (count > 0) {
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
        if (goodsId == null || "".equals(goodsId)) {
            return AppResponse.Error("商品编号错误");
        }
        Goods goods = goodsMapper.findGoodsById(goodsId);
        if (goods != null) {
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
        if (dict != null) {
            //获取字典表里的热门位商品展示数量
            hotGoodsDisplayNum = Integer.parseInt(dict.getDictValue());
        }
        //如果获取的热门位商品展示数量为0，则设置默认展示值为10
        if (hotGoodsDisplayNum == 0) {
            hotGoodsDisplayNum = 10;
        }
        List<HotGoods> hotGoods = hotGoodsMapper.listHotGoodsByHotGoodsDisplayNum(hotGoodsDisplayNum);
        return AppResponse.success("查询成功", hotGoods);
    }

    /**
     * 根据父级商品编号查询商品分类列表接口
     *
     * @param cateParent 父级商品分类编号
     * @return
     */
    public AppResponse listGoodsCatesByParentCode(String cateParent) {
        List<GoodsCate> goodsCates = goodsCateMapper.listGoodsCatesByParentCode(cateParent);
        return AppResponse.success("查询成功", goodsCates);
    }

    /**
     * 根据一级商品分类编号查询一级分类下所有二级分类的所有商品列表接口
     *
     * @param goodsCateCode 一级商品分类编号
     * @return
     */
    public AppResponse listGoodsByGoodsCateCode(String goodsCateCode) {
        List<Goods> goodsList = goodsMapper.listGoodsByGoodsCateCode(goodsCateCode);
        return AppResponse.success("查询成功", goodsList);
    }

    /**
     * 新增购物车商品接口
     *
     * @param shoppingCart 购物车信息（包含商品编号、添加客户信息、商品数量）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addShoppingCart(ShoppingCart shoppingCart) {
        //根据添加购物车的客户编号和商品编号查询该是否已经添加过
        ShoppingCart oldShoppingCart = shoppingCartMapper.findClientShoppingCart(shoppingCart.getShoppingCartClientCode(),
                shoppingCart.getShoppingCartGoodsCode());
        //表示该客户已经添加过购物车，只需要修改购物车信息即可
        if (oldShoppingCart != null) {
            //如果添加过，则修改购物车该商品的数量即可
            shoppingCart.setShoppingCartGoodsNum(shoppingCart.getShoppingCartGoodsNum()
                    + oldShoppingCart.getShoppingCartGoodsNum());
            //设置基本属性
            shoppingCart.setShoppingCartId(oldShoppingCart.getShoppingCartId());
            shoppingCart.setUpdatePerson(AuthUtils.getCurrentUserId());
            shoppingCart.setUpdateTime(new Date());
            shoppingCart.setVersion(oldShoppingCart.getVersion() + 1);
            int status = shoppingCartMapper.updateByPrimaryKeySelective(shoppingCart);
            if (status > 0) {
                return AppResponse.success("添加购物车成功");
            } else {
                return AppResponse.bizError("添加购物车失败");
            }
        }
        Goods goods = goodsMapper.findGoodsById(shoppingCart.getShoppingCartGoodsCode());
        if (goods != null) {
            if (shoppingCart.getShoppingCartGoodsNum() > goods.getGoodsStock()) {
                return AppResponse.Error("添加购物车数量大于商品库存");
            }
        }
        //设置UUID为主键
        shoppingCart.setShoppingCartId(UUIDUtils.getUUID());
        //设置基本属性
        shoppingCart.setCreateTime(new Date());
        shoppingCart.setCreatePerson(AuthUtils.getCurrentUserId());
        shoppingCart.setUpdateTime(new Date());
        shoppingCart.setUpdatePerson(AuthUtils.getCurrentUserId());
        shoppingCart.setVersion(1);
        shoppingCart.setIsDeleted(1);
        int count = shoppingCartMapper.insertSelective(shoppingCart);
        if (count > 0) {
            return AppResponse.success("新增购物车成功");
        }
        return AppResponse.bizError("新增购物车失败");
    }

    /**
     * 查询购物车列表接口
     *
     * @param pageBean               分页信息
     * @param shoppingCartClientCode 购物车所属的客户编号
     * @return
     */
    public AppResponse listShoppingCartsById(PageBean pageBean, String shoppingCartClientCode) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<ShoppingCart> shoppingCarts = shoppingCartMapper.listShoppingCartByClientId(shoppingCartClientCode);
        PageInfo<ShoppingCart> pageData = new PageInfo<ShoppingCart>(shoppingCarts);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 修改购物车商品数量接口
     *
     * @param shoppingCartId       购物车编号
     * @param shoppingCartGoodsNum 购物车商品数量
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateShoppingCartById(String shoppingCartId, int shoppingCartGoodsNum) {
        //修改购物车数量
        int count = shoppingCartMapper.updateShoppingCartGoodsNumById(shoppingCartId,
                shoppingCartGoodsNum, AuthUtils.getCurrentUserId());
        if (count > 0) {
            return AppResponse.success("修改购物车商品数量成功");
        }
        return AppResponse.bizError("修改购物车商品数量失败");
    }

    /**
     * 删除购物车商品接口
     *
     * @param shoppingCartIds 购物车编号（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteShoppingCartById(String shoppingCartIds) {
        //检验要删除的ids是否为null或者""
        if (shoppingCartIds == null || "".equals(shoppingCartIds)) {
            return AppResponse.Error("没有该购物车信息，删除失败");
        }
        List<String> listIds = Arrays.asList(shoppingCartIds.split(","));
        //删除购物车信息列表集合，设置更新人id
        int count = shoppingCartMapper.deleteShoppingCartById(listIds, AuthUtils.getCurrentUserId());
        //当要删除的购物车总数和已删除的总数不等时，回滚事务，删除失败
        if (count != listIds.size()) {
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        }
        return AppResponse.success("删除成功");
    }

    /**
     * 新增客户订单接口
     *
     * @param order                 订单信息
     * @param orderDetail           订单详情
     * @param shoppingCartIds       购物车商品id（购物车的批量购买用逗号分割）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addOrder(Order order, OrderDetail orderDetail, String shoppingCartIds) {
        //判断输入的门店是否为空
        if(order.getOrderStoreCode() == null || "".equals(order.getOrderStoreCode())){
            return AppResponse.Error("门店编号不存在");
        }
        //不是从购物车进行下单
        if(shoppingCartIds == null || "".equals(shoppingCartIds)){
            Goods goods = goodsMapper.findGoodsById(orderDetail.getOrderDetailGoodsCode());
            //如果下单数量大于库存
            if(orderDetail.getOrderDetailGoodsNum() > goods.getGoodsStock()){
                return AppResponse.Error("下单数量过大，库存不足");
            }
            //设置订单属性
            //设置UUID
            order.setOrderId(UUIDUtils.getUUID());
            //设置订单展示编号（组成：年月日时分秒+2位随机数）
            order.setOrderCode(UUIDUtils.getTimeRandom(2));
            //设置购买商品的总价格
            order.setOrderTotalPrice(goods.getGoodsSalePrice() * orderDetail.getOrderDetailGoodsNum());
            //设置订单状态为1：已下单
            order.setOrderCondition(1);
            //设置订单支付状态为1：已支付
            order.setOrderPayCondition(1);
            //设置订单支付时间
            order.setOrderPayTime(new Date());
            //设置基本属性
            order.setCreatePerson(AuthUtils.getCurrentUserId());
            order.setCreateTime(new Date());
            order.setUpdatePerson(AuthUtils.getCurrentUserId());
            order.setUpdateTime(new Date());
            order.setIsDeleted(1);
            order.setVersion(1);
            //设置订单详情属性
            //设置UUID
            orderDetail.setOrderDetailId(UUIDUtils.getUUID());
            //设置订单编号
            orderDetail.setOrderDetailOrderCode(order.getOrderId());
            //设置购买该商品的总价格
            orderDetail.setOrderDetailGoodsTotalPrice(goods.getGoodsSalePrice() * orderDetail.getOrderDetailGoodsNum());
            //设置购买该商品的销售价格
            orderDetail.setOrderDetailGoodsSalePrice(goods.getGoodsSalePrice());
            //设置购买该商品的定价
            orderDetail.setOrderDetailGoodsFixPrice(goods.getGoodsFixPrice());
            //设置购买该商品的名称
            orderDetail.setOrderDetailGoodsName(goods.getGoodsName());
            //设置购买该商品的展示编号
            orderDetail.setOrderDetailGoodsDisplayCode(goods.getGoodsCode());
            //设置基本属性
            orderDetail.setCreatePerson(AuthUtils.getCurrentUserId());
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdatePerson(AuthUtils.getCurrentUserId());
            orderDetail.setUpdateTime(new Date());
            orderDetail.setIsDeleted(1);
            orderDetail.setVersion(1);
            int orderStatus = orderMapper.insertSelective(order);
            int orderDetailStatus = orderDetailMapper.insertSelective(orderDetail);
            if(orderStatus > 0 && orderDetailStatus > 0){
                //下单成功后减少商品的库存
                goodsMapper.updateGoodsStockById(goods.getGoodsId(),
                        goods.getGoodsStock() - orderDetail.getOrderDetailGoodsNum(),
                        AuthUtils.getCurrentUserId());
                return AppResponse.success("下单成功");
            }
            return AppResponse.bizError("下单失败");
        //从购物车下单
        }else {
            //获取购物车编号列表
            List<String> shoppingCartId = Arrays.asList(shoppingCartIds.split(","));
            //获取购物车信息列表
            List<ShoppingCart> shoppingCarts = shoppingCartMapper.listShoppingCartById(shoppingCartId);
            List<String> goodsIds = new ArrayList<>();
            for(ShoppingCart shoppingCart : shoppingCarts){
                goodsIds.add(shoppingCart.getShoppingCartGoodsCode());
            }



        }
        return null;
    }

}















