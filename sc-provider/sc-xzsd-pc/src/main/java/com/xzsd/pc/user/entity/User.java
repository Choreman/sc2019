package com.xzsd.pc.user.entity;

import com.xzsd.pc.base.entity.BaseEntity;
import com.xzsd.pc.driver.entity.Driver;
import com.xzsd.pc.image.entity.Image;

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
     * 一个用户关联一张头像图片，一对一
     */
    private Image image;
    /**
     * 关联司机表的省份编号
     */
    private String driverProvinceCode;
    /**
     * 关联司机表的市级编号
     */
    private String driverCityCode;
    /**
     * 关联司机表的区（县）编号
     */
    private String driverRegionCode;
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
     * 用户头像的存储url地址
     */
    private String imageUrl;

    //-----------------get和set方法-----------------


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDriverProvinceCode() {
        return driverProvinceCode;
    }

    public void setDriverProvinceCode(String driverProvinceCode) {
        this.driverProvinceCode = driverProvinceCode;
    }

    public String getDriverCityCode() {
        return driverCityCode;
    }

    public void setDriverCityCode(String driverCityCode) {
        this.driverCityCode = driverCityCode;
    }

    public String getDriverRegionCode() {
        return driverRegionCode;
    }

    public void setDriverRegionCode(String driverRegionCode) {
        this.driverRegionCode = driverRegionCode;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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