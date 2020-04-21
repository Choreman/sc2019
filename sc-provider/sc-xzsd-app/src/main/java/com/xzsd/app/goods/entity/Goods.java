package com.xzsd.app.goods.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzsd.app.base.entity.BaseEntity;
import com.xzsd.app.goodscate.entity.GoodsCate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 商品信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Goods extends BaseEntity {

    /**
     * 商品唯一标识，主键
     */
    private String goodsId;
    /**
     * 用于展示商品的编号（年月日时分秒+2位随机数）
     */
    private String goodsCode;
    /**
     * 商品的名称
     */
    private String goodsName;
    /**
     * 商品的定价
     */
    private Float goodsFixPrice;
    /**
     * 商品的销售价格
     */
    private Float goodsSalePrice;
    /**
     * 商品的现有库存数量
     */
    private Integer goodsStock;
    /**
     * 商品已经销售的数量
     */
    private Integer goodsSaleSum;
    /**
     * 商品的分类编号
     */
    private String goodsCateCode;
    /**
     * 商品的广告词介绍
     */
    private String goodsAdvertisement;
    /**
     * 商品的详细介绍
     */
    private String goodsDescription;
    /**
     * 商品的销售状态（0：未发布，1：在售，2：已下架，3：库存不足）
     */
    private Integer goodsCondition;
    /**
     * 商品的上架时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goodsSaleTime;
    /**
     * 商品总的访问量
     */
    private Integer goodsVisitNum;
    /**
     * 商品的供应商名称
     */
    private String goodsBusiness;
    /**
     * 商品的isbn编号
     */
    private String goodsIsbn;
    /**
     * 商品的出版社
     */
    private String goodsPublisher;
    /**
     * 商品的作者
     */
    private String goodsAuthor;
    /**
     * 商品的评价星级（1：一星，2：两星，3：三星，4：四星，5：五星）
     */
    private Integer goodsStar;


    //-----------------关联关系-----------------

    /**
     * 一个商品关联一个二级商品分类，一对一
     * （二级分类关联一个一级分类，一对一）
     * 一个商品有第一级、第二级分类
     */
    private GoodsCate goodsCate;
    /**
     * 商品对应的二级分类名称
     */
    private String secondGoodsCateName;
    /**
     * 商品对应的一级分类名称
     */
    private String firstGoodsCateName;
    /**
     * 商品对应的一级分类编号id
     */
    private String firstGoodsCateId;

    //-----------------get和set方法-----------------


    public String getFirstGoodsCateId() {
        return firstGoodsCateId;
    }

    public void setFirstGoodsCateId(String firstGoodsCateId) {
        this.firstGoodsCateId = firstGoodsCateId;
    }

    public String getSecondGoodsCateName() {
        return secondGoodsCateName;
    }

    public void setSecondGoodsCateName(String secondGoodsCateName) {
        this.secondGoodsCateName = secondGoodsCateName;
    }

    public String getFirstGoodsCateName() {
        return firstGoodsCateName;
    }

    public void setFirstGoodsCateName(String firstGoodsCateName) {
        this.firstGoodsCateName = firstGoodsCateName;
    }

    public GoodsCate getGoodsCate() {
        return goodsCate;
    }

    public void setGoodsCate(GoodsCate goodsCate) {
        this.goodsCate = goodsCate;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId == null ? null : goodsId.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName == null ? null : goodsName.trim();
    }

    public Float getGoodsFixPrice() {
        return goodsFixPrice;
    }

    public void setGoodsFixPrice(Float goodsFixPrice) {
        this.goodsFixPrice = goodsFixPrice;
    }

    public Float getGoodsSalePrice() {
        return goodsSalePrice;
    }

    public void setGoodsSalePrice(Float goodsSalePrice) {
        this.goodsSalePrice = goodsSalePrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Integer getGoodsSaleSum() {
        return goodsSaleSum;
    }

    public void setGoodsSaleSum(Integer goodsSaleSum) {
        this.goodsSaleSum = goodsSaleSum;
    }

    public String getGoodsCateCode() {
        return goodsCateCode;
    }

    public void setGoodsCateCode(String goodsCateCode) {
        this.goodsCateCode = goodsCateCode == null ? null : goodsCateCode.trim();
    }

    public String getGoodsAdvertisement() {
        return goodsAdvertisement;
    }

    public void setGoodsAdvertisement(String goodsAdvertisement) {
        this.goodsAdvertisement = goodsAdvertisement == null ? null : goodsAdvertisement.trim();
    }

    public String getGoodsDescription() {
        return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription == null ? null : goodsDescription.trim();
    }

    public Integer getGoodsCondition() {
        return goodsCondition;
    }

    public void setGoodsCondition(Integer goodsCondition) {
        this.goodsCondition = goodsCondition;
    }

    public Date getGoodsSaleTime() {
        return goodsSaleTime;
    }

    public void setGoodsSaleTime(Date goodsSaleTime) {
        this.goodsSaleTime = goodsSaleTime;
    }

    public Integer getGoodsVisitNum() {
        return goodsVisitNum;
    }

    public void setGoodsVisitNum(Integer goodsVisitNum) {
        this.goodsVisitNum = goodsVisitNum;
    }

    public String getGoodsBusiness() {
        return goodsBusiness;
    }

    public void setGoodsBusiness(String goodsBusiness) {
        this.goodsBusiness = goodsBusiness == null ? null : goodsBusiness.trim();
    }

    public String getGoodsIsbn() {
        return goodsIsbn;
    }

    public void setGoodsIsbn(String goodsIsbn) {
        this.goodsIsbn = goodsIsbn == null ? null : goodsIsbn.trim();
    }

    public String getGoodsPublisher() {
        return goodsPublisher;
    }

    public void setGoodsPublisher(String goodsPublisher) {
        this.goodsPublisher = goodsPublisher == null ? null : goodsPublisher.trim();
    }

    public String getGoodsAuthor() {
        return goodsAuthor;
    }

    public void setGoodsAuthor(String goodsAuthor) {
        this.goodsAuthor = goodsAuthor == null ? null : goodsAuthor.trim();
    }

    public Integer getGoodsStar() {
        return goodsStar;
    }

    public void setGoodsStar(Integer goodsStar) {
        this.goodsStar = goodsStar;
    }
}