package com.xzsd.pc.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.image.dao.ImageMapper;
import com.xzsd.pc.image.entity.Image;
import com.xzsd.pc.user.dao.UserMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 用户管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ImageMapper imageMapper;

    @Resource
    private TencentCOSUtil tencentCOSUtil;

    /**
     * 顶部栏接口
     *
     * @return
     */
    public AppResponse topInfo(){
        User user = userMapper.findUserById(AuthUtils.getCurrentUserId());
        if(user != null){
            return AppResponse.success("顶部栏信息查询成功", user);
        }
        return AppResponse.bizError("顶部栏信息查询失败");
    }

    /**
     * 新增用户接口
     * @param user 用户信息
     * @param imageId 上传的头像图片编号
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addUser(User user, String imageId) {
        //判断用户账号是否为null或者""
        if (user.getUserLoginName() == null || "".equals(user.getUserLoginName().trim())) {
            return AppResponse.bizError("用户账号输入有误，请重新输入");
        }
        //查询数据库中是否有该账号的用户
        int count = userMapper.countUserByUserLoginName(user.getUserLoginName());
        //数据库中存在相同账号的用户
        if (count != 0) {
            return AppResponse.Error("用户账号已存在");
        }
        //设置UUID为主键
        user.setUserId(UUIDUtils.getUUID());
        //设置用户展示的编号（年月日时分秒+2位随机数）
        user.setUserCode(UUIDUtils.getTimeRandom(2));
        //把用户密码加密
        user.setUserPassword(PasswordUtils.generatePassword(user.getUserPassword()));
        //设置基本属性
        user.setCreateTime(new Date());
        user.setCreatePerson(AuthUtils.getCurrentUserId());
        user.setUpdateTime(new Date());
        user.setUpdatePerson(AuthUtils.getCurrentUserId());
        user.setVersion(1);
        user.setIsDeleted(1);
        int status = userMapper.insertSelective(user);
        //用户新增成功
        if (status > 0) {
            //如果新增用户时有上传头像
            if(imageId != null && !"".equals(imageId)){
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(user.getUserId());
                //通过图片的id修改图片的分类编号，把用户表的用户信息和图片表的头像图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //头像和用户信息没有关联成功
                if(headImageStatus == 0){
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("新增用户信息失败，请输入正确的头像地址");
                }
            }
            return AppResponse.success("新增用户信息成功");
        }
        return AppResponse.bizError("新增用户信息失败");
    }

    /**
     * 上传头像（需求更改，此方法弃用）
     * @param headImage 头像图片
     * @param imageCateCode 图片类别编号，此处是用户编号
     * @return
     */
    @Deprecated
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
     * 更新头像图片（需求更改，此方法弃用）
     * @param headImage 要更新的头像图片
     * @param imageCateCode 图片分类的编号
     * @return
     */
    @Deprecated
    private int updateHeadImage(MultipartFile headImage, String imageCateCode){
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
        }else{
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
            //根据图片分类的编号修改图片信息
            status = imageMapper.updateByImageCateCodeSelective(image);
        }
        return status;
    }

    /**
     * 根据用户账号查询用户接口
     *
     * @param userLoginName 用户账号
     * @return
     */
    public AppResponse countUserByUserLoginName(String userLoginName) {
        //判断用户账号是否为null或者""
        if (userLoginName != null && !"".equals(userLoginName.trim())) {
            int count = userMapper.countUserByUserLoginName(userLoginName);
            //数据库中存在相同账号的用户
            if (count != 0) {
                return AppResponse.Error("用户账号已存在");
            } else {
                return AppResponse.success("用户账号可使用");
            }
        } else {
            return AppResponse.bizError("用户账号输入有误，请重新输入");
        }
    }

    /**
     * 根据用户信息条件查询用户信息（管理员、店长、司机）
     *
     * @param pageBean 分页信息
     * @param user     查询的用户信息条件
     * @return
     */
    public AppResponse listUsers(PageBean pageBean, User user) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<User> users = userMapper.listUsers(user);
        PageInfo<User> pageData = new PageInfo<User>(users);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 修改用户信息接口
     *
     * @param user 要修改的用户信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateUserById(User user, String imageId) {
        //校验用户id不为null或着""
        if (user.getUserId() == null || "".equals(user.getUserId())) {
            return AppResponse.Error("用户编号输入错误");
        }
        //校验用户角色不为null或着0
        if (user.getUserRole() == null || user.getUserRole() == 0) {
            return AppResponse.bizError("用户角色错误");
        }
        //校验数据库中有没有该id的记录
        User oldUser = userMapper.selectByPrimaryKey(user.getUserId());
        if (oldUser == null) {
            return AppResponse.Error("没有该用户信息");
        } else if (!oldUser.getVersion().equals(user.getVersion())) {
            return AppResponse.Error("信息已更新，请重试");
        }
        //查询数据库中是否有该账号的用户
        int count = userMapper.countUserByUserLoginNameAndUserId(user);
        //数据库中存在相同账号的用户
        if (count != 0) {
            return AppResponse.Error("用户账号已存在");
        }
        //加密密码
        user.setUserPassword(PasswordUtils.generatePassword(user.getUserPassword()));
        //设置基本信息
        user.setUpdatePerson(AuthUtils.getCurrentUserId());
        user.setUpdateTime(new Date());
        user.setVersion(oldUser.getVersion() + 1);
        int status = userMapper.updateByPrimaryKeySelective(user);
        if (status > 0) {
            //如果修改用户时有上传头像
            if(imageId != null && !"".equals(imageId)){
                //把之前的用户头像进行删除
                imageMapper.deleteImageByImageCateCode(user.getUserId(), AuthUtils.getCurrentUserId());
                Image image = new Image();
                image.setImageId(imageId);
                image.setImageCateCode(user.getUserId());
                //通过图片的id，把用户表的用户信息和图片表的头像图片关联起来
                int headImageStatus = imageMapper.updateByPrimaryKeySelective(image);
                //头像和用户信息没有关联成功
                if(headImageStatus == 0){
                    //回滚事务
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return AppResponse.bizError("修改用户信息失败，请输入正确的头像地址");
                }
            }
            return AppResponse.success("修改用户信息成功");
        } else {
            return AppResponse.bizError("修改用户信息失败");
        }
    }

    /**
     * 删除用户接口
     * @param ids 要删除的id
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteUserById(String ids){
        //检验要删除的ids是否为null或者""
        if (ids == null || "".equals(ids)) {
            return AppResponse.Error("没有该用户信息，删除失败");
        }
        List<String> listIds = Arrays.asList(ids.split(","));
        //删除用户信息列表集合，设置更新人id
        int count = userMapper.deleteUserById(listIds, AuthUtils.getCurrentUserId());
        //当要删除的用户总数和已删除的总数不等时，回滚事务，删除失败
        if (count != listIds.size()) {
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        } else {
            //同时删除用户信息列表关联的头像图片
            imageMapper.deleteImageByUserId(listIds, AuthUtils.getCurrentUserId());
            return AppResponse.success("删除成功");
        }
    }

}















