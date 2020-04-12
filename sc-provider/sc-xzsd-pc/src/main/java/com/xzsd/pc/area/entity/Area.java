package com.xzsd.pc.area.entity;


import com.xzsd.pc.base.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域名称实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Area extends BaseEntity {

    /**
     * 区域名称唯一标识，主键
     */
    private String areaNameId;
    /**
     * 全国省、市、县（区）的名称
     */
    private String areaName;
    /**
     * 各区域名称的级别（1：省，2：市，3：县、区）
     */
    private Integer areaNameLevel;
    /**
     * 该区域的上一级区域编号
     */
    private String areaNameParentCode;

    //-----------------关联关系-----------------

    /**
     * 该区域的子区域集合，省包含所有市，市包含所有区，
     * 自关联，一对多
     */
    private List<Area> childAreaList = new ArrayList<>();

    //-----------------get和set方法-----------------


    public List<Area> getChildAreaList() {
        return childAreaList;
    }

    public void setChildAreaList(List<Area> childAreaList) {
        this.childAreaList = childAreaList;
    }

    public String getAreaNameId() {
        return areaNameId;
    }

    public void setAreaNameId(String areaNameId) {
        this.areaNameId = areaNameId == null ? null : areaNameId.trim();
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public Integer getAreaNameLevel() {
        return areaNameLevel;
    }

    public void setAreaNameLevel(Integer areaNameLevel) {
        this.areaNameLevel = areaNameLevel;
    }

    public String getAreaNameParentCode() {
        return areaNameParentCode;
    }

    public void setAreaNameParentCode(String areaNameParentCode) {
        this.areaNameParentCode = areaNameParentCode == null ? null : areaNameParentCode.trim();
    }
}