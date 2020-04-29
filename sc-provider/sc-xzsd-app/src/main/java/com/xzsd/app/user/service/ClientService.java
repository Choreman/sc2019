package com.xzsd.app.user.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.dict.dao.DictMapper;
import com.xzsd.app.dict.entity.Dict;
import com.xzsd.app.goods.dao.GoodsMapper;
import com.xzsd.app.goods.entity.Goods;
import com.xzsd.app.goodscate.dao.GoodsCateMapper;
import com.xzsd.app.goodscate.entity.GoodsCate;
import com.xzsd.app.goodscomment.dao.GoodsCommentMapper;
import com.xzsd.app.goodscomment.entity.GoodsComment;
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
import java.util.*;
import java.util.stream.Collectors;

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

    @Resource
    private GoodsCommentMapper goodsCommentMapper;


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
     * 新增订单信息
     *
     * @param orderClientCode       下单客户编号
     * @param orderDetailGoodsCodes 下单商品编号（多个编号，用逗号隔开）
     * @param orderDetailGoodsNums  下单商品数量（多个编号，用逗号隔开）
     * @param orderStoreCode        收货的门店编号
     * @param shoppingCartIds       购物车编号（多个编号，用逗号隔开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addOrder(String orderClientCode, String orderDetailGoodsCodes,
                                String orderDetailGoodsNums, String orderStoreCode, String shoppingCartIds) {
        if (orderStoreCode == null || "".equals(orderStoreCode)) {
            return AppResponse.Error("门店信息不存在，请先绑定门店");
        }
        //从商品详情页下单
        if (shoppingCartIds == null || "".equals(shoppingCartIds)) {
            Goods goods = goodsMapper.findGoodsById(orderDetailGoodsCodes);
            //如果下单数量大于库存
            if (Integer.parseInt(orderDetailGoodsNums) > goods.getGoodsStock()) {
                return AppResponse.Error("下单数量过大，库存不足");
            }
            //简化代码，调用方法设置订单属性
            Order order = getOrder(orderClientCode, orderDetailGoodsNums, orderStoreCode, goods.getGoodsSalePrice());
            //简化代码，调用方法设置订单详情属性
            OrderDetail orderDetail = getOrderDetail(orderDetailGoodsCodes, orderDetailGoodsNums, order.getOrderId(), goods);
            int orderStatus = orderMapper.insertSelective(order);
            int orderDetailStatus = orderDetailMapper.insertSelective(orderDetail);
            if (orderStatus > 0 && orderDetailStatus > 0) {
                //下单成功后减少商品的库存
                int count = goodsMapper.updateGoodsStockById(goods.getGoodsId(),
                        goods.getGoodsStock() - Integer.parseInt(orderDetailGoodsNums),
                        AuthUtils.getCurrentUserId());
                if (count == 0) {
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("下单异常");
                }
                return AppResponse.success("下单成功");
            }
            return AppResponse.bizError("下单失败");
            //从购物车下单
        } else {
            //1.获取商品id列表、商品数量列表、购物车编号列表
            //1.1获取每一个下单的商品编号
            List<String> orderDetailGoodsCode = Arrays.asList(orderDetailGoodsCodes.split(","));
            //1.2获取每一个下单的商品数量
            List<String> orderDetailGoodsNum = Arrays.asList(orderDetailGoodsNums.split(","));
            //1.3获取每一个下单的购物车编号
            List<String> shoppingCartId = Arrays.asList(shoppingCartIds.split(","));
            //1.4要购买的商品Map（商品id : 商品数量）
            Map<String, String> buyGoodsNumMap = new HashMap<>();
            //1.5查询出来的商品Map（商品id : 商品库存）
            Map<String, String> oldGoodsNumMap = new HashMap<>();
            //1.6下单失败商品信息集合Map(商品编号 ： 超出库存数量)
            Map<String, Integer> failureGoodsMap = new HashMap<String, Integer>();

            //2.把传入的下单商品id和数量组成Map
            for (int i = 0; i < orderDetailGoodsCode.size(); i++) {
                buyGoodsNumMap.put(orderDetailGoodsCode.get(i), orderDetailGoodsNum.get(i));
            }

            //3.根据传入的商品id列表查询出商品信息列表
            List<Goods> goodsList = goodsMapper.listGoodsById(orderDetailGoodsCode);

            //4.查询出的商品列表也组成Map(商品id ：商品库存)
            for (Goods goods : goodsList) {
                oldGoodsNumMap.put(goods.getGoodsId(), String.valueOf(goods.getGoodsStock()));
            }
            //可下单的商品信息列表
            List<Goods> successGoodsList = new ArrayList<>();
            //不可下单的商品信息列表
            List<Goods> failureGoodsList = new ArrayList<>();

            //5.循环Map，比较商品数量和库存
            for (Map.Entry<String, String> entry : buyGoodsNumMap.entrySet()) {
                //购买商品的数量大于库存
                if (Integer.parseInt(entry.getValue()) > Integer.parseInt(oldGoodsNumMap.get(entry.getKey()))) {
                    //计算超出库存的数量
                    int outOfSize = Integer.parseInt(entry.getValue()) - Integer.parseInt(oldGoodsNumMap.get(entry.getKey()));
                    //把超出库存的商品id和数量记录
                    failureGoodsMap.put(entry.getKey(), outOfSize);
                    //保存库存不足的商品列表
                    List<Goods> tempGoodsList = goodsList.stream().
                            filter(a -> a.getGoodsId().equals(entry.getKey())).collect(Collectors.toList());
                    failureGoodsList.addAll(tempGoodsList);
                    //购买商品的数量不大于库存
                } else {
                    //保存库存充足的商品列表
                    List<Goods> tempGoodsList = goodsList.stream().
                            filter(g -> g.getGoodsId().equals(entry.getKey())).collect(Collectors.toList());
                    successGoodsList.addAll(tempGoodsList);
                }
            }

            //订单信息
            Order order = getOrder(orderClientCode, "0", orderStoreCode, 0);
            //生成多个订单详情
            List<OrderDetail> orderDetails = new ArrayList<>();
            float orderTotalPrice = 0;
            //6.新增订单和订单详情信息
            for (Goods goods : successGoodsList) {
                OrderDetail orderDetail = getOrderDetail(goods.getGoodsId(),
                        buyGoodsNumMap.get(goods.getGoodsId()), order.getOrderId(), goods);
                orderDetails.add(orderDetail);
                //把下单的所有商品价格加起来
                orderTotalPrice += orderDetail.getOrderDetailGoodsTotalPrice();
            }
            order.setOrderTotalPrice(orderTotalPrice);
            int orderStatus = orderMapper.insertSelective(order);
            int orderDetailStatus = orderDetailMapper.insertOrderDetailList(orderDetails);
            if (orderStatus == 0 || orderDetailStatus != successGoodsList.size()) {
                //回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("新增订单异常");
            }
            //7.修改商品库存
            int goodsStatus = goodsMapper.updateGoodsStockByOrderDetails(orderDetails);
            if (goodsStatus == 0) {
                //回滚事务
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("修改库存异常");
            }
            //8.删除下单成功商品的购物车
            List<ShoppingCart> shoppingCarts = shoppingCartMapper.listShoppingCartById(shoppingCartId);
            List<String> shoppingCartIdList = new ArrayList<>(shoppingCartId);
            //循环购物车信息，删除不能下单的商品的购物车编号，剩下的即为下单成功购物车编号
            for (ShoppingCart shoppingCart : shoppingCarts) {
                if (failureGoodsMap.get(shoppingCart.getShoppingCartGoodsCode()) != null) {
                    shoppingCartIdList.removeIf(s -> s.equals(shoppingCart.getShoppingCartId()));
                }
            }
            //9.删除购物车
            shoppingCartMapper.deleteShoppingCartById(shoppingCartIdList, AuthUtils.getCurrentUserId());

            //10.处理返回错误数据
            //下单的购物车商品中有库存不足的商品
            if (successGoodsList.size() != orderDetailGoodsCode.size()) {
                String failureMsg = "";
                for (Goods goods : failureGoodsList) {
                    failureMsg = failureMsg + "商品：《" + goods.getGoodsName() + "》，超出库存"
                            + failureGoodsMap.get(goods.getGoodsId()) + "本\n";
                }
                //没有成功下单的商品
                if (successGoodsList.size() == 0) {
                    return AppResponse.Error(failureMsg);
                }
                //部分商品成功下单
                return AppResponse.success("部分商品成功下单\n" + failureMsg);
            }
            return AppResponse.success("下单成功");
        }
    }

    /**
     * 设置订单属性，简化业务方法
     *
     * @param orderClientCode      下单客户编号
     * @param orderDetailGoodsNums 下单商品数量
     * @param orderStoreCode       收获门店信息
     * @param goodsSalePrice       下单商品价格
     * @return
     */
    private Order getOrder(String orderClientCode, String orderDetailGoodsNums, String orderStoreCode,
                           float goodsSalePrice) {
        //设置订单属性
        Order order = new Order();
        //设置UUID
        order.setOrderId(UUIDUtils.getUUID());
        //设置订单展示编号（组成：年月日时分秒+2位随机数）
        order.setOrderCode(UUIDUtils.getTimeRandom(2));
        //设置下单的客户编号
        order.setOrderClientCode(orderClientCode);
        //设置送货的门店编号
        order.setOrderStoreCode(orderStoreCode);
        //设置购买商品的总价格
        order.setOrderTotalPrice(goodsSalePrice * Integer.parseInt(orderDetailGoodsNums));
        //设置订单状态为0：已下单
        order.setOrderCondition(0);
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
        return order;
    }

    /**
     * 设置订单详情属性，简化业务方法
     *
     * @param orderDetailGoodsCodes 下单商品编号
     * @param orderDetailGoodsNums  下单商品数量
     * @param orderId               订单编号
     * @param goods                 下单商品信息
     * @return
     */
    private OrderDetail getOrderDetail(String orderDetailGoodsCodes, String orderDetailGoodsNums,
                                       String orderId, Goods goods) {
        //设置订单详情属性
        OrderDetail orderDetail = new OrderDetail();
        //设置UUID
        orderDetail.setOrderDetailId(UUIDUtils.getUUID());
        //设置订单编号
        orderDetail.setOrderDetailOrderCode(orderId);
        //设置商品编号
        orderDetail.setOrderDetailGoodsCode(orderDetailGoodsCodes);
        //设置商品数量
        orderDetail.setOrderDetailGoodsNum(Integer.parseInt(orderDetailGoodsNums));
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
        return orderDetail;
    }

    /**
     * 查询客户订单列表接口
     *
     * @param pageBean        分页信息
     * @param orderclientCode 客户编号
     * @param orderCondition  订单状态
     * @return
     */
    public AppResponse listOrdersById(PageBean pageBean, String orderclientCode, int orderCondition) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Order> orders = orderMapper.listOrdersById(orderclientCode, orderCondition);
        PageInfo<Order> pageData = new PageInfo<Order>(orders);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 查询客户订单详情接口
     *
     * @param orderId 订单编号
     * @return
     */
    public AppResponse findOrderDetailById(String orderId) {
        Order order = orderMapper.findOrderDetailById(orderId);
        if (order != null) {
            return AppResponse.success("查询成功", order);
        }
        return AppResponse.bizError("订单不存在");
    }

    /**
     * 修改客户订单状态接口
     *
     * @param orderId        订单编号
     * @param orderCondition 订单状态
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateOrderConditionById(String orderId, int orderCondition) {
        int count = orderMapper.updateOrderConditiionById(orderId, AuthUtils.getCurrentUserId(), orderCondition);
        if (count > 0) {
            return AppResponse.success("订单状态修改成功");
        }
        return AppResponse.bizError("订单状态修改失败");
    }

    /**
     * 新增商品评价接口
     *
     * @param JSONStr 前端传的JSON字符串（包含评价的客户编号、商品评价内容）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addGoodsCommentsByGoodsId(String JSONStr) {
        //1.先把JSON格式字符串转换成JSON对象
        JSONObject JSONObj = JSON.parseObject(JSONStr);
        //2.可以根据JSON对象获取里面的指定参数
        String clientId = (String) JSONObj.get("clientId");
        String orderId = (String) JSONObj.get("orderId");
        //3.把里面的List列表参数变成JSONArray对象
        JSONArray jsonArray = JSONObj.getJSONArray("goodsCommentList");
        //4.通过JSONObject把JSONArray对象变成list<T>的列表对象
        List<GoodsComment> goodsCommentList = JSONObject.parseArray(jsonArray.toJSONString(), GoodsComment.class);
        //校验客户编号是否存在
        if(clientId == null || "".equals(clientId)){
            return AppResponse.Error("客户编号错误");
        }
        for (GoodsComment goodsComment : goodsCommentList){
            //设置UUID
            goodsComment.setGoodsCommentId(UUIDUtils.getUUID());
            //设置商品评价的客户编号
            goodsComment.setGoodsCommentClientCode(clientId);
            //设置商品评价的时间
            goodsComment.setGoodsCommentTime(new Date());
            //对商品评价内容做去除前后空格处理
            String comment = goodsComment.getGoodsComment() == null ? "" : goodsComment.getGoodsComment();
            goodsComment.setGoodsComment(comment.trim());
            //设置基本属性
            goodsComment.setCreatePerson(AuthUtils.getCurrentUserId());
            goodsComment.setCreateTime(new Date());
            goodsComment.setUpdatePerson(AuthUtils.getCurrentUserId());
            goodsComment.setUpdateTime(new Date());
            goodsComment.setIsDeleted(1);
            goodsComment.setVersion(1);
        }
        int count = goodsCommentMapper.insertGoodsCommentList(goodsCommentList);
        //已经新增的评价和要新增的评价数量不一致
        if(count != goodsCommentList.size()){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("新增商品评价异常");
        }
        //商品评价完，表示购买成功，修改商品的销量
        int saleNumCount = goodsMapper.updateGoodsSaleNumList(goodsCommentList, orderId);
        if(saleNumCount != goodsCommentList.size()){
            return AppResponse.bizError("商品销量修改失败");
        }
        //去除没有评价星级的商品评价信息
        List<GoodsComment> starGoodsComment = goodsCommentList.stream().
                filter(a -> a.getGoodsCommentStar() != 0).collect(Collectors.toList());
        int starCount = 0;
        if (starGoodsComment.size() != 0){
            //根据评价的星级更新商品的星级
            starCount = goodsMapper.updateGoodsStarList(starGoodsComment);
        }
        //已经修改的商品星级和要修改的商品星级数量不一致
        if(starCount != starGoodsComment.size()){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("修改商品星级异常");
        }
        //评价完成，修改订单状态为5：已完成已评价
        int orderCount = orderMapper.updateOrderConditiionById(orderId, AuthUtils.getCurrentUserId(), 5);
        if (orderCount == 0) {
            return AppResponse.bizError("订单状态修改异常");
        }
        return AppResponse.success("新增商品评价成功");
    }

    /**
     * 查询商品评价列表接口
     *
     * @param pageBean     分页信息
     * @param goodsComment 商品评价信息（包含商品编号、商品星级）
     * @return
     */
    public AppResponse listGoodsCommentsById(PageBean pageBean, GoodsComment goodsComment) {
        if (goodsComment.getGoodsCommentGoodsCode() == null || "".equals(goodsComment.getGoodsCommentGoodsCode())){
            return AppResponse.Error("商品编号错误");
        }
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<GoodsComment> goodsComments = goodsCommentMapper.listGoodsCommentsById(
                goodsComment.getGoodsCommentGoodsCode(),
                goodsComment.getGoodsCommentStar());
        PageInfo<GoodsComment> pageData = new PageInfo<GoodsComment>(goodsComments);
        return AppResponse.success("查询成功!", pageData);
    }

}















