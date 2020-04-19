package com.xzsd.pc.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.user.dao.ClientMapper;
import com.xzsd.pc.user.dao.UserMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 分角色查询客户列表接口（分页）
     * - 管理员查询所有客户信息列表
     * - 店长查询门店所有客户信息列表
     *
     * @param pageBean 分页信息
     * @param user     客户信息查询条件
     * @return
     */
    public AppResponse listClients(PageBean pageBean, User user){
        //根据当前登录用户的id查找用户信息
        User loginUser = userMapper.selectByPrimaryKey(AuthUtils.getCurrentUserId());
        if(loginUser == null){
            return AppResponse.Error("登录用户信息获取失败");
        }
        //获取登录用户的角色
        int userRole = loginUser.getUserRole();
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<User> users = null;
        //当登录查询的是管理员角色
        if(userRole == 1){
            //根据查询条件查询所有客户信息
            users = clientMapper.listClients(user);
        //当登录查询的是店长角色
        }else if(userRole == 2){
            //设置登录用户（店长）的编号进行关联查询其店铺下的客户列表
            user.setUserId(loginUser.getUserId());
            //根据查询条件查询店长的门店的客户信息
            users = clientMapper.listStoreClients(user);
        }
        PageInfo<User> pageData = new PageInfo<User>(users);
        return AppResponse.success("查询成功!", pageData);
    }

}















