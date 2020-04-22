package com.xzsd.app.dict.entity;

import com.xzsd.app.base.entity.BaseEntity;

/**
 * 字典实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Dict extends BaseEntity{

    /**
     * 字典表唯一标识，主键
     */
    private String dictId;
    /**
     * 字典的键
     */
    private String dictKey;
    /**
     * 字典的值
     */
    private String dictValue;
    /**
     * 备注
     */
    private String dictComment;

    public String getDictId() {
        return dictId;
    }

    public void setDictId(String dictId) {
        this.dictId = dictId == null ? null : dictId.trim();
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey == null ? null : dictKey.trim();
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue == null ? null : dictValue.trim();
    }

    public String getDictComment() {
        return dictComment;
    }

    public void setDictComment(String dictComment) {
        this.dictComment = dictComment == null ? null : dictComment.trim();
    }
}