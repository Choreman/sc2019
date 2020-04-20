package com.xzsd.app.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.base.bean.PageBean;
import com.xzsd.app.user.dao.ManagerMapper;
import com.xzsd.app.user.dao.UserMapper;
import com.xzsd.app.user.entity.User;
import com.xzsd.app.utils.AppResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 店长管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@Service
public class ManagerService {

    @Resource
    private ManagerMapper managerMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 查询店铺订单列表接口
     *
     * @param pageBean       分页信息
     * @param userId         店长id
     * @param orderCondition 订单状态
     * @return
     */
    public AppResponse listStoreOrders(PageBean pageBean, String userId, int orderCondition) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Order> orders = managerMapper.listStoreOrders(userId, orderCondition);
        PageInfo<Order> pageData = new PageInfo<Order>(orders);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 根据店长id查询店铺的司机信息接口
     *
     * @param userId 店长id
     * @return
     */
    public AppResponse findStoreDriverById(String userId) {
        if (userId == null || "".equals(userId)) {
            return AppResponse.Error("店长编号错误");
        }
        User user = userMapper.findUserById(userId);
        if (user == null) {
            return AppResponse.Error("该店长信息不存在");
        }
        //根据店长编号查询门店的司机信息
        List<User> drivers = managerMapper.findStoreDriverById(userId);
        return AppResponse.success("查询成功！", drivers);
    }

}















