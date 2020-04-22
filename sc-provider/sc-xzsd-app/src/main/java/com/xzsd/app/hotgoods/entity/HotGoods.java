package com.xzsd.app.hotgoods.entity;

import com.xzsd.app.base.entity.BaseEntity;

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
     * 商品的编号id
     */
    private String goodsId;
    /**
     * 商品的名称
     */
    private String goodsName;
    /**
     * 商品的销售价格
     */
    private Float goodsSalePrice;
    /**
     * 商品的定价
     */
    private Float goodsFixPrice;
    /**
     * 商品的图片
     */
    private String imageUrl;

    //-----------------get和set方法-----------------


    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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

    public Float getGoodsFixPrice() {
        return goodsFixPrice;
    }

    public void setGoodsFixPrice(Float goodsFixPrice) {
        this.goodsFixPrice = goodsFixPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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