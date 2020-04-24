package com.xzsd.app.user.controller;

import com.github.pagehelper.Page;
import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.order.entity.Order;
import com.xzsd.app.orderdetail.entity.OrderDetail;
import com.xzsd.app.shoppingcart.entity.ShoppingCart;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.user.service.ClientService;
import com.xzsd.app.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 客户管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@RestController
@RequestMapping("/client")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    /**
     * 客户注册接口
     *
     * @param user 包含客户信息、头像编号、门店邀请码
     * @return
     */
    @PostMapping("/registerClient")
    public AppResponse registerClient(User user) {
        try {
            return clientService.registerClient(user);
        } catch (Exception e) {
            logger.error("注册客户信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改客户店铺邀请码接口
     *
     * @param user 包含客户id、门店邀请码
     * @return
     */
    @PostMapping("/updateClientStoreInvitationCodeById")
    public AppResponse updateClientStoreInvitationCodeById(User user) {
        try {
            return clientService.updateClientStoreInvitationCodeById(user);
        } catch (Exception e) {
            logger.error("修改客户邀请码异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询轮播图列表接口
     *
     * @return
     */
    @PostMapping("/listRollImages")
    public AppResponse listRollImages() {
        try {
            return clientService.listRollImages();
        } catch (Exception e) {
            logger.error("查询轮播图信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询单个商品接口
     *
     * @param goodsId 商品id
     * @return
     */
    @PostMapping("/findGoodsById")
    public AppResponse findGoodsById(String goodsId) {
        try {
            return clientService.findGoodsById(goodsId);
        } catch (Exception e) {
            logger.error("查询商品详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询热门商品列表接口
     *
     * @return
     */
    @PostMapping("/listHotGoods")
    public AppResponse listHotGoods() {
        try {
            return clientService.listHotGoods();
        } catch (Exception e) {
            logger.error("查询热门商品列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据父级商品编号查询商品分类列表接口
     *
     * @param cateParent 父级商品分类编号
     * @return
     */
    @PostMapping("/listGoodsCatesByParentCode")
    public AppResponse listGoodsCatesByParentCode(String cateParent) {
        try {
            return clientService.listGoodsCatesByParentCode(cateParent);
        } catch (Exception e) {
            logger.error("根据父级商品编号查询商品分类信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据一级商品分类编号查询一级分类下所有二级分类的所有商品列表接口
     *
     * @param goodsCateCode 一级商品分类编号
     * @return
     */
    @PostMapping("/listGoodsByGoodsCateCode")
    public AppResponse listGoodsByGoodsCateCode(String goodsCateCode) {
        try {
            return clientService.listGoodsByGoodsCateCode(goodsCateCode);
        } catch (Exception e) {
            logger.error("根据商品分类查询商品列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 新增购物车商品接口
     *
     * @param shoppingCart 购物车信息（包含商品编号、添加客户信息、商品数量）
     * @return
     */
    @PostMapping("/addShoppingCart")
    public AppResponse addShoppingCart(ShoppingCart shoppingCart) {
        try {
            return clientService.addShoppingCart(shoppingCart);
        } catch (Exception e) {
            logger.error("新增商品购物车异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询购物车列表接口
     *
     * @param pageBean               分页信息
     * @param shoppingCartClientCode 购物车所属的客户编号
     * @return
     */
    @PostMapping("/listShoppingCartsById")
    public AppResponse listShoppingCartsById(PageBean pageBean, String shoppingCartClientCode) {
        try {
            return clientService.listShoppingCartsById(pageBean, shoppingCartClientCode);
        } catch (Exception e) {
            logger.error("查询客户购物车列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改购物车商品数量接口
     *
     * @param shoppingCartId       购物车编号
     * @param shoppingCartGoodsNum 购物车商品数量
     * @return
     */
    @PostMapping("/updateShoppingCartById")
    public AppResponse updateShoppingCartById(String shoppingCartId, int shoppingCartGoodsNum) {
        try {
            return clientService.updateShoppingCartById(shoppingCartId, shoppingCartGoodsNum);
        } catch (Exception e) {
            logger.error("修改购物车商品数量异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除购物车商品接口
     *
     * @param shoppingCartIds 购物车编号（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteShoppingCartById")
    public AppResponse deleteShoppingCartById(String shoppingCartIds) {
        try {
            return clientService.deleteShoppingCartById(shoppingCartIds);
        } catch (Exception e) {
            logger.error("删除购物车商品异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
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
    @PostMapping("/addOrder")
    public AppResponse addOrder(String orderClientCode, String orderDetailGoodsCodes,
                                String orderDetailGoodsNums, String orderStoreCode, String shoppingCartIds) {
        try {
            return clientService.addOrder(orderClientCode, orderDetailGoodsCodes,
                    orderDetailGoodsNums, orderStoreCode, shoppingCartIds);
        } catch (Exception e) {
            logger.error("新增订单异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }
    //todo

    /**
     * 查询客户订单列表接口
     *
     * @param pageBean        分页信息
     * @param orderclientCode 客户编号
     * @param orderCondition  订单状态
     * @return
     */
    @PostMapping("/listOrdersById")
    public AppResponse listOrdersById(PageBean pageBean, String orderclientCode, int orderCondition) {
        try {
            return clientService.listOrdersById(pageBean, orderclientCode, orderCondition);
        } catch (Exception e) {
            logger.error("删除购物车商品异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询客户订单详情接口
     *
     * @param orderId 订单编号
     * @return
     */
    @PostMapping("/findOrderDetailById")
    public AppResponse findOrderDetailById(String orderId) {
        try {
            return clientService.findOrderDetailById(orderId);
        } catch (Exception e) {
            logger.error("查询客户订单详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改客户订单状态接口
     *
     * @param orderId        订单编号
     * @param orderCondition 订单状态
     * @return
     */
    @PostMapping("/updateOrderConditionById")
    public AppResponse updateOrderConditionById(String orderId, int orderCondition) {
        try {
            return clientService.updateOrderConditionById(orderId, orderCondition);
        } catch (Exception e) {
            logger.error("查询客户订单详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}




















