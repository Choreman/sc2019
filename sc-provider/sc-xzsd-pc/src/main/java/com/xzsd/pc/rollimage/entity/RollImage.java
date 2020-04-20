package com.xzsd.pc.rollimage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xzsd.pc.base.entity.BaseEntity;
import com.xzsd.pc.image.entity.Image;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 轮播图实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class RollImage extends BaseEntity {

    /**
     * 轮播图唯一标识，主键
     */
    private String rollImageId;
    /**
     * 轮播图里的图片编号
     */
    private String rollImageCode;
    /**
     * 轮播图的图片权值（权值越小排名越前，默认为：99999）
     */
    private Integer rollImageWeight;
    /**
     * 轮播图的图片对应的商品编号
     */
    private String rollImageGoodsCode;
    /**
     * 轮播图的图片起始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rollImageBeginDate;
    /**
     * 轮播图的图片终止时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date rollImageEndDate;
    /**
     * 轮播图的图片状态（0：禁用，1：启用）
     */
    private Integer rollImageCondition;

    //-----------------关联关系-----------------

    /**
     * 轮播图对应的图片id
     */
    private String imageId;
    /**
     * 轮播图对应的图片url
     */
    private String imageUrl;
    /**
     * 前端说用来展示预览效果需要传递一个List
     */
    private List<Image> imageList;


    //-----------------get和set方法-----------------


    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRollImageId() {
        return rollImageId;
    }

    public void setRollImageId(String rollImageId) {
        this.rollImageId = rollImageId == null ? null : rollImageId.trim();
    }

    public String getRollImageCode() {
        return rollImageCode;
    }

    public void setRollImageCode(String rollImageCode) {
        this.rollImageCode = rollImageCode == null ? null : rollImageCode.trim();
    }

    public Integer getRollImageWeight() {
        return rollImageWeight;
    }

    public void setRollImageWeight(Integer rollImageWeight) {
        this.rollImageWeight = rollImageWeight;
    }

    public String getRollImageGoodsCode() {
        return rollImageGoodsCode;
    }

    public void setRollImageGoodsCode(String rollImageGoodsCode) {
        this.rollImageGoodsCode = rollImageGoodsCode == null ? null : rollImageGoodsCode.trim();
    }

    public Date getRollImageBeginDate() {
        return rollImageBeginDate;
    }

    public void setRollImageBeginDate(Date rollImageBeginDate) {
        this.rollImageBeginDate = rollImageBeginDate;
    }

    public Date getRollImageEndDate() {
        return rollImageEndDate;
    }

    public void setRollImageEndDate(Date rollImageEndDate) {
        this.rollImageEndDate = rollImageEndDate;
    }

    public Integer getRollImageCondition() {
        return rollImageCondition;
    }

    public void setRollImageCondition(Integer rollImageCondition) {
        this.rollImageCondition = rollImageCondition;
    }
}