package com.xzsd.pc.goodscate.entity;

import com.xzsd.pc.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品分类实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class GoodsCate extends BaseEntity {

    /**
     * 商品分类唯一标识，主键
     */
    private String goodsCateId;
    /**
     * 商品各种类别的名称
     */
    private String cateName;
    /**
     * 该类别的层级（1：第一级，2：第二级）
     */
    private Integer cateLevel;
    /**
     * 该类别的上一级编号（null：没有上一级，则该记录为第一级）
     */
    private String cateParent;
    /**
     * 该记录的备注信息
     */
    private String cateComment;

    //-----------------关联关系-----------------

    /**
     * 该商品分类的子分类集合，一级分类包含二级分类
     * 自关联，一对多
     */
    private List<GoodsCate> childGoodsCateList = new ArrayList<>();

    //-----------------get和set方法-----------------


    public List<GoodsCate> getChildGoodsCateList() {
        return childGoodsCateList;
    }

    public void setChildGoodsCateList(List<GoodsCate> childGoodsCateList) {
        this.childGoodsCateList = childGoodsCateList;
    }

    public String getGoodsCateId() {
        return goodsCateId;
    }

    public void setGoodsCateId(String goodsCateId) {
        this.goodsCateId = goodsCateId == null ? null : goodsCateId.trim();
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName == null ? null : cateName.trim();
    }

    public Integer getCateLevel() {
        return cateLevel;
    }

    public void setCateLevel(Integer cateLevel) {
        this.cateLevel = cateLevel;
    }

    public String getCateParent() {
        return cateParent;
    }

    public void setCateParent(String cateParent) {
        this.cateParent = cateParent == null ? null : cateParent.trim();
    }

    public String getCateComment() {
        return cateComment;
    }

    public void setCateComment(String cateComment) {
        this.cateComment = cateComment == null ? null : cateComment.trim();
    }
}