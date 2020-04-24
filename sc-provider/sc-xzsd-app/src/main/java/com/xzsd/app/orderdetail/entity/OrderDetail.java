package com.xzsd.app.orderdetail.entity;

import com.xzsd.app.base.entity.BaseEntity;

/**
 * 订单明细实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class OrderDetail extends BaseEntity {
    /**
     * 订单明细唯一标识，主键
     */
    private String orderDetailId;
    /**
     * 该明细所属的订单编号
     */
    private String orderDetailOrderCode;
    /**
     * 该明细中包含的商品编号
     */
    private String orderDetailGoodsCode;
    /**
     * 该明细中购买商品的总数量
     */
    private Integer orderDetailGoodsNum;
    /**
     * 该明细中购买商品的总价格
     */
    private Float orderDetailGoodsTotalPrice;
    /**
     * 该明细中购买商品的售价
     */
    private Float orderDetailGoodsSalePrice;
    /**
     * 该明细中购买商品的定价
     */
    private Float orderDetailGoodsFixPrice;
    /**
     * 该明细中购买商品的名称
     */
    private String orderDetailGoodsName;
    /**
     * 该明细中购买商品的展示编号
     */
    private String orderDetailGoodsDisplayCode;

    //-----------------关联关系-----------------

    /**
     * 用户展示的编号（年月日时分秒+2位随机数）
     */
    private String userCode;
    /**
     * 一个订单详情包含一种商品，一种商品一张图片
     */
    private String imageUrl;
    /**
     * 一个订单详情包含一种商品，一种商品一个商品介绍
     */
    private String goodsDescription;

    //-----------------get和set方法-----------------


    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId == null ? null : orderDetailId.trim();
    }

    public String getOrderDetailOrderCode() {
        return orderDetailOrderCode;
    }

    public void setOrderDetailOrderCode(String orderDetailOrderCode) {
        this.orderDetailOrderCode = orderDetailOrderCode == null ? null : orderDetailOrderCode.trim();
    }

    public String getOrderDetailGoodsCode() {
        return orderDetailGoodsCode;
    }

    public void setOrderDetailGoodsCode(String orderDetailGoodsCode) {
        this.orderDetailGoodsCode = orderDetailGoodsCode == null ? null : orderDetailGoodsCode.trim();
    }

    public Integer getOrderDetailGoodsNum() {
        return orderDetailGoodsNum;
    }

    public void setOrderDetailGoodsNum(Integer orderDetailGoodsNum) {
        this.orderDetailGoodsNum = orderDetailGoodsNum;
    }

    public Float getOrderDetailGoodsTotalPrice() {
        return orderDetailGoodsTotalPrice;
    }

    public void setOrderDetailGoodsTotalPrice(Float orderDetailGoodsTotalPrice) {
        this.orderDetailGoodsTotalPrice = orderDetailGoodsTotalPrice;
    }

    public Float getOrderDetailGoodsSalePrice() {
        return orderDetailGoodsSalePrice;
    }

    public void setOrderDetailGoodsSalePrice(Float orderDetailGoodsSalePrice) {
        this.orderDetailGoodsSalePrice = orderDetailGoodsSalePrice;
    }

    public Float getOrderDetailGoodsFixPrice() {
        return orderDetailGoodsFixPrice;
    }

    public void setOrderDetailGoodsFixPrice(Float orderDetailGoodsFixPrice) {
        this.orderDetailGoodsFixPrice = orderDetailGoodsFixPrice;
    }

    public String getOrderDetailGoodsName() {
        return orderDetailGoodsName;
    }

    public void setOrderDetailGoodsName(String orderDetailGoodsName) {
        this.orderDetailGoodsName = orderDetailGoodsName == null ? null : orderDetailGoodsName.trim();
    }

    public String getOrderDetailGoodsDisplayCode() {
        return orderDetailGoodsDisplayCode;
    }

    public void setOrderDetailGoodsDisplayCode(String orderDetailGoodsDisplayCode) {
        this.orderDetailGoodsDisplayCode = orderDetailGoodsDisplayCode == null ? null : orderDetailGoodsDisplayCode.trim();
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderDetailId='" + orderDetailId + '\'' +
                ", orderDetailOrderCode='" + orderDetailOrderCode + '\'' +
                ", orderDetailGoodsCode='" + orderDetailGoodsCode + '\'' +
                ", orderDetailGoodsNum=" + orderDetailGoodsNum +
                ", orderDetailGoodsTotalPrice=" + orderDetailGoodsTotalPrice +
                ", orderDetailGoodsSalePrice=" + orderDetailGoodsSalePrice +
                ", orderDetailGoodsFixPrice=" + orderDetailGoodsFixPrice +
                ", orderDetailGoodsName='" + orderDetailGoodsName + '\'' +
                ", orderDetailGoodsDisplayCode='" + orderDetailGoodsDisplayCode + '\'' +
                '}';
    }
}