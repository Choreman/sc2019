package com.xzsd.pc.hotgoods.entity;

import com.xzsd.pc.base.entity.BaseEntity;

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