package com.xzsd.pc.store.entity;

import com.xzsd.pc.area.entity.Area;
import com.xzsd.pc.base.entity.BaseEntity;
import com.xzsd.pc.user.entity.User;

import java.util.List;


/**
 * 门店信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Store extends BaseEntity {

    /**
     * 门店唯一标识，主键
     */
    private String storeId;
    /**
     * 用于展示门店的编号（年月日时分秒+2位随机数）
     */
    private String storeCode;
    /**
     * 门店的名称
     */
    private String storeName;
    /**
     * 门店的联系电话
     */
    private String storePhone;
    /**
     * 门店的店长编号
     */
    private String storeManagerId;
    /**
     * 门店的营业执照编码
     */
    private String storeLicenseCode;
    /**
     * 门店的详细地址
     */
    private String storeAddress;
    /**
     * 门店的邀请码
     */
    private String storeInvitationCode;
    /**
     * 门店所在的省份名称编号
     */
    private String storeProvinceCode;
    /**
     * 门店所在的城市名称编号
     */
    private String storeCityCode;
    /**
     * 店所在的区的编号
     */
    private String storeRegionCode;

    //-----------------关联关系-----------------

    /**
     * 关联区域表的省份名称
     */
    private String provinceName;
    /**
     * 关联区域表的市级名称
     */
    private String cityName;
    /**
     * 关联区域表的区（县）名称
     */
    private String regionName;
    /**
     * 门店关联的用户名称
     */
    private String userName;
    /**
     * 门店关联的用户账号
     */
    private String userLoginName;

    //-----------------get和set方法-----------------


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone == null ? null : storePhone.trim();
    }

    public String getStoreManagerId() {
        return storeManagerId;
    }

    public void setStoreManagerId(String storeManagerId) {
        this.storeManagerId = storeManagerId == null ? null : storeManagerId.trim();
    }

    public String getStoreLicenseCode() {
        return storeLicenseCode;
    }

    public void setStoreLicenseCode(String storeLicenseCode) {
        this.storeLicenseCode = storeLicenseCode == null ? null : storeLicenseCode.trim();
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress == null ? null : storeAddress.trim();
    }

    public String getStoreInvitationCode() {
        return storeInvitationCode;
    }

    public void setStoreInvitationCode(String storeInvitationCode) {
        this.storeInvitationCode = storeInvitationCode == null ? null : storeInvitationCode.trim();
    }

    public String getStoreProvinceCode() {
        return storeProvinceCode;
    }

    public void setStoreProvinceCode(String storeProvinceCode) {
        this.storeProvinceCode = storeProvinceCode == null ? null : storeProvinceCode.trim();
    }

    public String getStoreCityCode() {
        return storeCityCode;
    }

    public void setStoreCityCode(String storeCityCode) {
        this.storeCityCode = storeCityCode == null ? null : storeCityCode.trim();
    }

    public String getStoreRegionCode() {
        return storeRegionCode;
    }

    public void setStoreRegionCode(String storeRegionCode) {
        this.storeRegionCode = storeRegionCode == null ? null : storeRegionCode.trim();
    }

}