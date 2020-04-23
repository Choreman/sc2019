package com.xzsd.app.shoppingcart.entity;

import com.xzsd.app.base.entity.BaseEntity;


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
    private Integer shoppingCartGoodsNum;

    //-----------------关联关系-----------------

    /**
     * 购物车的商品名称
     */
    private String goodsName;
    /**
     * 购物车的商品销售价格
     */
    private String goodsSalePrice;
    /**
     * 购物车的商品图片
     */
    private String imageUrl;

    //-----------------get和set方法-----------------


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(String goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

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

    public Integer getShoppingCartGoodsNum() {
        return shoppingCartGoodsNum;
    }

    public void setShoppingCartGoodsNum(Integer shoppingCartGoodsNum) {
        this.shoppingCartGoodsNum = shoppingCartGoodsNum;
    }
}