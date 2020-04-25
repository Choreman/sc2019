package com.xzsd.app.goodscomment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzsd.app.base.entity.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date goodsCommentTime;

    //-----------------关联关系-----------------

    /**
     * 评价客户的姓名
     */
    private String userName;

    //-----------------get和set方法-----------------


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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