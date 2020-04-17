package com.xzsd.pc.hotgoods.entity;

import com.xzsd.pc.base.entity.BaseEntity;
import com.xzsd.pc.goods.entity.Goods;

import java.util.Date;

/**
 * 热门商品实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class HotGoods extends BaseEntity {

    /**
     * 热门商品唯一标识，主键
     */
    private String hotGoodsId;
    /**
     * 热门商品的商品编号
     */
    private String hotGoodsGoodsCode;
    /**
     * 热门商品的权重（权值越小排名越前，默认为：99999）
     */
    private Integer hotGoodsWeight;

    //-----------------关联关系-----------------

    /**
     * 用于展示商品的编号（年月日时分秒+2位随机数）
     */
    private String goodsCode;
    /**
     * 商品的名称
     */
    private String goodsName;
    /**
     * 商品的销售价格
     */
    private Float goodsSalePrice;
    /**
     * 商品的详细介绍
     */
    private String goodsDescription;

    //-----------------get和set方法-----------------


    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Float getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(Float goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }

    public String getHotGoodsId() {
        return hotGoodsId;
    }

    public void setHotGoodsId(String hotGoodsId) {
        this.hotGoodsId = hotGoodsId == null ? null : hotGoodsId.trim();
    }

    public String getHotGoodsGoodsCode() {
        return hotGoodsGoodsCode;
    }

    public void setHotGoodsGoodsCode(String hotGoodsGoodsCode) {
        this.hotGoodsGoodsCode = hotGoodsGoodsCode == null ? null : hotGoodsGoodsCode.trim();
    }

    public Integer getHotGoodsWeight() {
        return hotGoodsWeight;
    }

    public void setHotGoodsWeight(Integer hotGoodsWeight) {
        this.hotGoodsWeight = hotGoodsWeight;
    }
}