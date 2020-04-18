package com.xzsd.pc.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzsd.pc.base.entity.BaseEntity;
import com.xzsd.pc.orderdetail.entity.OrderDetail;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 订单信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Order extends BaseEntity {

    /**
     * 订单唯一标识，主键
     */
    private String orderId;
    /**
     * 用于展示的订单编号（组成：年月日时分秒+2位随机数）
     */
    private String orderCode;
    /**
     * 下单的客户的编号
     */
    private String orderClientCode;
    /**
     * 收货的门店铺编号
     */
    private String orderStoreCode;
    /**
     * 订单总共应付金额
     */
    private Float orderTotalPrice;
    /**
     * 订单的状态（0：已下单，1：已取消，2：已到货，3：已取货，4：已完成未评价，5：已完成已评价）
     */
    private Integer orderCondition;
    /**
     * 订单的支付状态（1：已支付）
     */
    private Integer orderPayCondition;
    /**
     * 订单的付款时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date orderPayTime;

    //-----------------关联关系-----------------

    /**
     * 用户的姓名
     */
    private String userName;
    /**
     * 用户的电话号码
     */
    private String userPhone;
    /**
     * 用于展示门店的编号（年月日时分秒+2位随机数）
     */
    private String storeCode;
    /**
     * 一个订单对应多条订单记录，一对多
     */
    private List<OrderDetail> orderDetailList;

    //-----------------get和set方法-----------------


    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getOrderClientCode() {
        return orderClientCode;
    }

    public void setOrderClientCode(String orderClientCode) {
        this.orderClientCode = orderClientCode == null ? null : orderClientCode.trim();
    }

    public String getOrderStoreCode() {
        return orderStoreCode;
    }

    public void setOrderStoreCode(String orderStoreCode) {
        this.orderStoreCode = orderStoreCode == null ? null : orderStoreCode.trim();
    }

    public Float getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(Float orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Integer getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(Integer orderCondition) {
        this.orderCondition = orderCondition;
    }

    public Integer getOrderPayCondition() {
        return orderPayCondition;
    }

    public void setOrderPayCondition(Integer orderPayCondition) {
        this.orderPayCondition = orderPayCondition;
    }

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }
}