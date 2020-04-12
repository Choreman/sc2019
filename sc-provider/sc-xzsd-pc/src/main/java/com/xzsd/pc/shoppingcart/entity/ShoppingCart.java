package com.xzsd.pc.shoppingcart.entity;

import com.xzsd.pc.base.entity.BaseEntity;


/**
 * 商品购物车实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class ShoppingCart extends BaseEntity {

    /**
     * 购物车唯一标识，主键
     */
    private String shoppingCartId;
    /**
     * 购物车所属用户的编号
     */
    private String shoppingCartClientCode;
    /**
     * 购物车里包含商品的编号
     */
    private String shoppingCartGoodsCode;
    /**
     * 购物车里包含商品的总数量
     */
    private String shoppingCartGoodsNum;

    public String getShoppingCartId() {
        return shoppingCartId;
    }

    public void setShoppingCartId(String shoppingCartId) {
        this.shoppingCartId = shoppingCartId == null ? null : shoppingCartId.trim();
    }

    public String getShoppingCartClientCode() {
        return shoppingCartClientCode;
    }

    public void setShoppingCartClientCode(String shoppingCartClientCode) {
        this.shoppingCartClientCode = shoppingCartClientCode == null ? null : shoppingCartClientCode.trim();
    }

    public String getShoppingCartGoodsCode() {
        return shoppingCartGoodsCode;
    }

    public void setShoppingCartGoodsCode(String shoppingCartGoodsCode) {
        this.shoppingCartGoodsCode = shoppingCartGoodsCode == null ? null : shoppingCartGoodsCode.trim();
    }

    public String getShoppingCartGoodsNum() {
        return shoppingCartGoodsNum;
    }

    public void setShoppingCartGoodsNum(String shoppingCartGoodsNum) {
        this.shoppingCartGoodsNum = shoppingCartGoodsNum == null ? null : shoppingCartGoodsNum.trim();
    }
}