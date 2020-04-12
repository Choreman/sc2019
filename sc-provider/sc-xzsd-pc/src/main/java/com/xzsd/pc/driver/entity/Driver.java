package com.xzsd.pc.driver.entity;

import com.xzsd.pc.area.entity.Area;
import com.xzsd.pc.base.entity.BaseEntity;

import java.util.Date;
import java.util.List;

/**
 * 司机信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Driver extends BaseEntity {

    /**
     * 司机信息的唯一标识，主键
     */
    private String driverId;
    /**
     * 关联用户信息表里的司机编号
     */
    private String driverUserCode;
    /**
     * 司机负责的店铺所属的省份编号
     */
    private String driverProvinceCode;
    /**
     * 司机负责的店铺所属的城市的编号
     */
    private String driverCityCode;
    /**
     * 司机负责的店铺所属的区的编号
     */
    private String driverRegionCode;

    //-----------------关联关系-----------------

    /**
     * 一个司机关联三个区域名称，省市区，一对多
     */
    private List<Area> areaList;

    //-----------------get和set方法-----------------


    public List<Area> getAreaList() {
        return areaList;
    }

    public void setAreaList(List<Area> areaList) {
        this.areaList = areaList;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId == null ? null : driverId.trim();
    }

    public String getDriverUserCode() {
        return driverUserCode;
    }

    public void setDriverUserCode(String driverUserCode) {
        this.driverUserCode = driverUserCode;
    }

    public String getDriverProvinceCode() {
        return driverProvinceCode;
    }

    public void setDriverProvinceCode(String driverProvinceCode) {
        this.driverProvinceCode = driverProvinceCode == null ? null : driverProvinceCode.trim();
    }

    public String getDriverCityCode() {
        return driverCityCode;
    }

    public void setDriverCityCode(String driverCityCode) {
        this.driverCityCode = driverCityCode == null ? null : driverCityCode.trim();
    }

    public String getDriverRegionCode() {
        return driverRegionCode;
    }

    public void setDriverRegionCode(String driverRegionCode) {
        this.driverRegionCode = driverRegionCode == null ? null : driverRegionCode.trim();
    }
}