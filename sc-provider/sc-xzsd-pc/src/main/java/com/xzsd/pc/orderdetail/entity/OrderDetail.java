package com.xzsd.pc.orderdetail.entity;

import com.xzsd.pc.base.entity.BaseEntity;


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
    private Float orderDetailGoodsPrice;

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

    public Float getOrderDetailGoodsPrice() {
        return orderDetailGoodsPrice;
    }

    public void setOrderDetailGoodsPrice(Float orderDetailGoodsPrice) {
        this.orderDetailGoodsPrice = orderDetailGoodsPrice;
    }
}