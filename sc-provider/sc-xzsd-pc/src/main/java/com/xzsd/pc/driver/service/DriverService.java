package com.xzsd.pc.driver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.driver.dao.DriverMapper;
import com.xzsd.pc.driver.entity.Driver;
import com.xzsd.pc.image.dao.ImageMapper;
import com.xzsd.pc.image.entity.Image;
import com.xzsd.pc.user.dao.UserMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 司机信息业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-03-29
 */
@Service
public class DriverService {

    @Resource
    private DriverMapper driverMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 新增司机信息接口
     * @param headImage 司机头像
     * @param user 存放司机关联的用户表的信息
     * @param driver 存放司机表的信息
     * @return
     */
    public AppResponse addDriver(MultipartFile headImage, User user, Driver driver) {
        //判断用户账号是否为null或者""
        if (user.getUserLoginName() == null || "".equals(user.getUserLoginName().trim())) {
            return AppResponse.bizError("司机账号输入有误，请重新输入");
        }
        //查询数据库中是否有该账号的用户
        int count = userMapper.countUserByUserLoginName(user.getUserLoginName());
        //数据库中存在相同账号的用户
        if (count != 0) {
            return AppResponse.Error("司机账号已存在");
        }
        //设置UUID为主键
        user.setUserId(UUIDUtils.getUUID());
        //设置用户展示的编号（年月日时分秒+2位随机数）
        user.setUserCode(UUIDUtils.getTimeRandom(2));
        //把用户密码加密
        user.setUserPassword(PasswordUtils.generatePassword(user.getUserPassword()));
        //设置司机角色编号
        user.setUserRole(3);
        //设置基本属性
        user.setCreateTime(new Date());
        user.setCreatePerson(AuthUtils.getCurrentUserId());
        user.setUpdateTime(new Date());
        user.setUpdatePerson(AuthUtils.getCurrentUserId());
        user.setVersion(1);
        user.setIsDeleted(1);
        //设置UUID为主键
        driver.setDriverId(UUIDUtils.getUUID());
        //设置关联用户表的用户编号
        driver.setDriverUserCode(user.getUserId());
        //设置基本属性
        driver.setCreateTime(new Date());
        driver.setCreatePerson(AuthUtils.getCurrentUserId());
        driver.setUpdateTime(new Date());
        driver.setUpdatePerson(AuthUtils.getCurrentUserId());
        driver.setIsDeleted(1);
        driver.setVersion(1);

        int status = userMapper.insertSelective(user);
        //司机信息在用户表新增成功
        if (status > 0) {
            //上传头像
            int headImageStatus = uploadHeadImage(headImage, user.getUserId());
            //-1表示上传图片出现异常，0表示新增图片信息失败
            if(headImageStatus == -1 || headImageStatus == 0){
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("图片上传失败，请重试");
            }
            int driverStatus = driverMapper.insertSelective(driver);
            if(driverStatus == 0){
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("新增司机信息失败，请重试");
            }
            return AppResponse.success("新增司机信息成功");
        }
        return AppResponse.bizError("新增司机信息失败，请重试");
    }

    /**
     * 上传头像
     * @param headImage 头像图片
     * @param imageCateCode 图片类别编号，此处是用户编号
     * @return
     */
    private int uploadHeadImage(MultipartFile headImage, String imageCateCode){
        Image image = new Image();
        //设置UUID为主键
        image.setImageId(UUIDUtils.getUUID());
        //设置图片类别为头像
        image.setImageCate(TencentCOSUtil.HEADIMAGECATE);
        //设置上传头像的用户编号
        image.setImageCateCode(imageCateCode);
        //设置基本属性
        image.setCreateTime(new Date());
        image.setCreatePerson(AuthUtils.getCurrentUserId());
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        image.setIsDeleted(1);
        image.setVersion(1);
        //当传入头像图片时
        if (!headImage.isEmpty()) {
            String url = null;
            try {
                //上传到指定的文件夹里面
                url = tencentCOSUtil.uploadImage(headImage, TencentCOSUtil.HEADIMAGEFOLDER);
            }catch (Exception e){
                //表示上传图片出现异常
                return -1;
            }
            //设置图片的url
            image.setImageUrl(url);
        }
        return imageMapper.insertSelective(image);
    }

    /**
     * 查询司机信息列表接口
     * @param pageBean 分页信息
     * @param user 查询条件，存放在user表里的司机信息
     * @param driver 查询条件，存放在driver表里的司机信息
     * @return
     */
    public AppResponse listDrivers(PageBean pageBean, User user, Driver driver){
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<User> users = driverMapper.listDrivers(user, driver);
        PageInfo<User> pageData = new PageInfo<User>(users);
        return AppResponse.success("查询成功", pageData);
    }

    /**
     * 查询司机信息详情接口（包含用户表、司机表、区域名称表里的信息）
     * @param userId 在user表中司机的id
     * @return
     */
    public AppResponse findDriverById(String userId){
        if(userId == null || "".equals(userId)){
            return AppResponse.Error("该司机信息不存在");
        }
        //获取司机信息（包含user表和driver表的信息）
        User user = driverMapper.findDriverById(userId);
        if(user == null){
            return AppResponse.Error("该司机信息不存在");
        }
        return AppResponse.success("查询成功", user);
    }



}


















