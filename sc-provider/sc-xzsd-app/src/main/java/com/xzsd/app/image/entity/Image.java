package com.xzsd.app.image.entity;

import com.xzsd.app.base.entity.BaseEntity;

/**
 * 图片信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Image extends BaseEntity {

    /**
     * 图片唯一标识，主键
     */
    private String imageId;
    /**
     * 图片使用类别（1：商品图，2：轮播图，3：商品评论图，4：订单评论图，5：头像）
     */
    private Integer imageCate;
    /**
     * 具体的图片类别的编号（例如商品图的编号、轮播图的编号）
     */
    private String imageCateCode;
    /**
     * 图片的存储url地址
     */
    private String imageUrl;
    /**
     * 图片权值（权值越小排名越前，默认为：99999）
     */
    private Integer imageWeight;

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId == null ? null : imageId.trim();
    }

    public Integer getImageCate() {
        return imageCate;
    }

    public void setImageCate(Integer imageCate) {
        this.imageCate = imageCate;
    }

    public String getImageCateCode() {
        return imageCateCode;
    }

    public void setImageCateCode(String imageCateCode) {
        this.imageCateCode = imageCateCode == null ? null : imageCateCode.trim();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
    }

    public Integer getImageWeight() {
        return imageWeight;
    }

    public void setImageWeight(Integer imageWeight) {
        this.imageWeight = imageWeight;
    }
}