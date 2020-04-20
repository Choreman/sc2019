package com.xzsd.app.user.entity;

import com.xzsd.app.base.entity.BaseEntity;
import com.xzsd.app.image.entity.Image;

/**
 * 用户信息实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class User extends BaseEntity {

    /**
     * 用户唯一标识，主键
     */
    private String userId;
    /**
     * 用户展示的编号（年月日时分秒+2位随机数）
     */
    private String userCode;
    /**
     * 用户进行登陆的账号
     */
    private String userLoginName;
    /**
     * 用户进行登陆的密码
     */
    private String userPassword;
    /**
     * 用户的角色（1：普通管理员，2：店长，3：司机，4：购买的客户）
     */
    private Integer userRole;
    /**
     * 用户的姓名
     */
    private String userName;
    /**
     * 用户的性别（0：未知，1：男，2：女）
     */
    private Integer userSex;
    /**
     * 用户的电话号码
     */
    private String userPhone;
    /**
     * 用户的邮箱地址
     */
    private String userMail;
    /**
     * 用户的身份证号码
     */
    private String userIdcard;
    /**
     * 每个客户对应的门店编号（其他角色没有该值）
     */
    private String userStoreId;

    //-----------------关联关系-----------------
    /**
     * 头像图片的id
     */
    private String imageId;
    /**
     * 头像图片的url
     */
    private String imageUrl;
    /**
     * 关联店铺的店铺id
     */
    private String storeId;
    /**
     * 关联店铺的店铺名称
     */
    private String storeName;
    /**
     * 关联店铺的店铺邀请码
     */
    private String storeInvitationCode;
    /**
     * 关联店铺的店铺地址
     */
    private String storeAddress;
    /**
     * 关联店铺的省份
     */
    private String provinceName;
    /**
     * 关联店铺的城市
     */
    private String cityName;
    /**
     * 关联店铺的区（县）
     */
    private String regionName;



    //-----------------get和set方法-----------------


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

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreInvitationCode() {
        return storeInvitationCode;
    }

    public void setStoreInvitationCode(String storeInvitationCode) {
        this.storeInvitationCode = storeInvitationCode;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode == null ? null : userCode.trim();
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName == null ? null : userLoginName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public Integer getUserSex() {
        return userSex;
    }

    public void setUserSex(Integer userSex) {
        this.userSex = userSex;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail == null ? null : userMail.trim();
    }

    public String getUserIdcard() {
        return userIdcard;
    }

    public void setUserIdcard(String userIdcard) {
        this.userIdcard = userIdcard == null ? null : userIdcard.trim();
    }

    public String getUserStoreId() {
        return userStoreId;
    }

    public void setUserStoreId(String userStoreId) {
        this.userStoreId = userStoreId == null ? null : userStoreId.trim();
    }
}