package com.xzsd.pc.goodscomment.entity;

import com.xzsd.pc.base.entity.BaseEntity;

import java.util.Date;

/**
 * 商品评价实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class GoodsComment extends BaseEntity {

    /**
     * 商品评价唯一标识，主键
     */
    private String goodsCommentId;
    /**
     * 要评价的商品的编号
     */
    private String goodsCommentGoodsCode;
    /**
     * 评价商品的客户编号
     */
    private String goodsCommentClientCode;
    /**
     * 评价商品的内容
     */
    private String goodsComment;
    /**
     * 评价商品的星级（1：一星，2：两星，3：三星，4：四星，5：五星）
     */
    private Integer goodsCommentStar;
    /**
     * 评价商品的时间
     */
    private Date goodsCommentTime;

    public String getGoodsCommentId() {
        return goodsCommentId;
    }

    public void setGoodsCommentId(String goodsCommentId) {
        this.goodsCommentId = goodsCommentId == null ? null : goodsCommentId.trim();
    }

    public String getGoodsCommentGoodsCode() {
        return goodsCommentGoodsCode;
    }

    public void setGoodsCommentGoodsCode(String goodsCommentGoodsCode) {
        this.goodsCommentGoodsCode = goodsCommentGoodsCode == null ? null : goodsCommentGoodsCode.trim();
    }

    public String getGoodsCommentClientCode() {
        return goodsCommentClientCode;
    }

    public void setGoodsCommentClientCode(String goodsCommentClientCode) {
        this.goodsCommentClientCode = goodsCommentClientCode == null ? null : goodsCommentClientCode.trim();
    }

    public String getGoodsComment() {
        return goodsComment;
    }

    public void setGoodsComment(String goodsComment) {
        this.goodsComment = goodsComment == null ? null : goodsComment.trim();
    }

    public Integer getGoodsCommentStar() {
        return goodsCommentStar;
    }

    public void setGoodsCommentStar(Integer goodsCommentStar) {
        this.goodsCommentStar = goodsCommentStar;
    }

    public Date getGoodsCommentTime() {
        return goodsCommentTime;
    }

    public void setGoodsCommentTime(Date goodsCommentTime) {
        this.goodsCommentTime = goodsCommentTime;
    }
}