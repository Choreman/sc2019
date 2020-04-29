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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
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
     *
     * @param user    存放司机关联的用户表的信息
     * @param driver  存放司机表的信息
     * @param imageId 头像图片id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addDriver(User user, Driver driver, String imageId) {
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
            //如果修改用户时有上传头像
            if (imageId != null && !"".equals(imageId)) {
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(user.getUserId());
                //通过图片的id修改图片的分类编号，把用户表的用户信息和图片表的头像图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //头像和用户信息没有关联成功
                if (headImageStatus == 0) {
                    //回滚事物
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("新增司机信息失败，请输入正确的头像地址");
                }
            }
            int driverStatus = driverMapper.insertSelective(driver);
            if (driverStatus == 0) {
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
     *
     * @param headImage     头像图片
     * @param imageCateCode 图片类别编号，此处是用户编号
     * @return
     */
    private int uploadHeadImage(MultipartFile headImage, String imageCateCode) {
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
            } catch (Exception e) {
                //表示上传图片出现异常
                return -1;
            }
            //设置图片的url
            image.setImageUrl(url);
        }
        return imageMapper.insertSelective(image);
    }

    /**
     * 更新头像图片
     *
     * @param headImage     要更新的头像图片
     * @param imageCateCode 图片分类的编号
     * @return
     */
    private int updateHeadImage(MultipartFile headImage, String imageCateCode) {
        Image image = new Image();
        //设置图片分类的编号
        image.setImageCateCode(imageCateCode);
        //设置基本属性
        image.setUpdateTime(new Date());
        image.setUpdatePerson(AuthUtils.getCurrentUserId());
        int status = 0;
        //当没有传入要修改的头像时
        if (headImage.isEmpty()) {
            //根据图片分类的编号修改图片信息
            status = imageMapper.updateByImageCateCodeSelective(image);
            //当传入要修改的头像时
        } else {
            String url = null;
            try {
                //上传到指定的文件夹里面
                url = tencentCOSUtil.uploadImage(headImage, TencentCOSUtil.HEADIMAGEFOLDER);
            } catch (Exception e) {
                //表示上传图片出现异常
                return -1;
            }
            //设置图片的url
            image.setImageUrl(url);
            //根据图片分类的编号修改图片信息
            status = imageMapper.updateByImageCateCodeSelective(image);
        }
        return status;
    }

    /**
     * 查询司机信息列表接口
     * - 管理员查询所有司机信息列表
     * - 店长查询自己的信息列表
     *
     * @param pageBean 分页信息
     * @param user     查询条件，存放在user表里的司机信息
     * @param driver   查询条件，存放在driver表里的司机信息
     * @return
     */
    public AppResponse listDrivers(PageBean pageBean, User user, Driver driver) {
        //根据当前登录用户的id查找用户信息
        User loginUser = userMapper.selectByPrimaryKey(AuthUtils.getCurrentUserId());
        if (loginUser == null) {
            return AppResponse.Error("登录用户信息获取失败");
        }
        //获取登录用户的角色
        int userRole = loginUser.getUserRole();
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<User> users = null;
        //当登录查询的是管理员角色
        if (userRole == 1) {
            //根据查询条件查询所有司机信息
            users = driverMapper.listDrivers(user, driver);
            //当登录查询的是店长角色
        } else if (userRole == 2) {
            //获取的登录的店长id
            user.setUserId(AuthUtils.getCurrentUserId());
            //根据查询条件查询店长的门店的司机信息
            users = driverMapper.listStoreDrivers(user, driver);
        }
        PageInfo<User> pageData = new PageInfo<User>(users);
        return AppResponse.success("查询成功", pageData);
    }

    /**
     * 查询司机信息详情接口（包含用户表、司机表、区域名称表里的信息）
     *
     * @param userId 在user表中司机的id
     * @return
     */
    public AppResponse findDriverById(String userId) {
        if (userId == null || "".equals(userId)) {
            return AppResponse.Error("该司机信息不存在");
        }
        //获取司机信息（包含user表和driver表的信息）
        User user = driverMapper.findDriverById(userId);
        if (user == null) {
            return AppResponse.Error("该司机信息不存在");
        }
        return AppResponse.success("查询成功", user);
    }

    /**
     * 修改司机信息接口
     *
     * @param user    要修改的在用户表的信息
     * @param driver  要修改的在司机表的信息
     * @param imageId 头像图片的id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateDriversById(User user, Driver driver, String imageId) {
        //校验用户id不为null或着""
        if (user.getUserId() == null || "".equals(user.getUserId())) {
            return AppResponse.Error("没有该司机信息");
        }
        //校验数据库中有没有该id的记录
        User oldUser = userMapper.selectByPrimaryKey(user.getUserId());
        if (oldUser == null) {
            return AppResponse.Error("没有该司机信息");
        } else if (!oldUser.getVersion().equals(user.getVersion())) {
            return AppResponse.Error("信息已更新，请重试");
        }
        //查询数据库中是否有该账号的用户
        int count = userMapper.countUserByUserLoginNameAndUserId(user);
        //数据库中存在相同账号的用户
        if (count != 0) {
            return AppResponse.Error("用户账号已存在");
        }
        //更新user表的信息
        //加密密码
        user.setUserPassword(PasswordUtils.generatePassword(user.getUserPassword()));
        //设置基本信息
        user.setUpdatePerson(AuthUtils.getCurrentUserId());
        user.setUpdateTime(new Date());
        user.setVersion(oldUser.getVersion() + 1);
        //更新driver表的信息
        driver.setDriverUserCode(oldUser.getUserId());
        driver.setUpdateTime(new Date());
        driver.setUpdatePerson(AuthUtils.getCurrentUserId());
        //修改司机在用户表的信息
        int status = userMapper.updateByPrimaryKeySelective(user);
        if (status > 0) {
            //如果修改司机时有上传头像
            if (imageId != null && !"".equals(imageId)) {
                //把之前的司机头像进行删除
                imageMapper.deleteImageByImageCateCode(user.getUserId(), AuthUtils.getCurrentUserId());
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(user.getUserId());
                //通过图片的id，把用户表的用户信息和图片表的头像图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //头像和用户信息没有关联成功
                if (headImageStatus == 0) {
                    //回滚事物
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("修改用户信息失败，请输入正确的头像地址");
                }
            }
            //根据司机表关联的用户表编号修改司机信息
            int driverStatus = driverMapper.updateByDriverUserCodeSelective(driver);
            if (driverStatus == 0) {
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("修改司机信息失败，请重试");
            }
            return AppResponse.success("修改司机信息成功");
        }
        return AppResponse.bizError("修改司机信息失败");
    }

    /**
     * 删除司机接口
     *
     * @param userIds 要删除的用户表的id（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteDriverByUserId(String userIds) {
        //检验要删除的ids是否为null或者""
        if (userIds == null || "".equals(userIds)) {
            return AppResponse.Error("没有该司机信息，删除失败");
        }
        List<String> listIds = Arrays.asList(userIds.split(","));
        int count = userMapper.deleteUserById(listIds, AuthUtils.getCurrentUserId());
        //当要删除的用户总数和已删除的总数不等时，回滚事物，删除失败
        if (count != listIds.size()) {
            //回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        } else {
            //同时删除用户信息列表关联的头像图片
            imageMapper.deleteImageByUserId(listIds, AuthUtils.getCurrentUserId());
            //同时删除用户信息列表关联的司机表信息
            driverMapper.deleteDriverByUserId(listIds, AuthUtils.getCurrentUserId());
            return AppResponse.success("删除成功");
        }
    }

}


















