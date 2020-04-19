package com.xzsd.pc.order.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.area.dao.AreaMapper;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.order.dao.OrderMapper;
import com.xzsd.pc.order.entity.Order;
import com.xzsd.pc.user.dao.UserMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.DateFormatUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 订单业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@Service
public class OrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 查询订单列表接口
     * - 管理员查询所有订单信息列表
     * - 店长查询门店的订单信息列表
     *
     * @param pageBean             分页信息
     * @param user                 要查询用户信息
     * @param order                要查询的订单信息
     * @param orderBeginPayTimeStr 订单起始时间
     * @param orderEndPayTimeStr   订单结束时间
     * @return
     */
    public AppResponse listOrders(PageBean pageBean, User user, Order order,
                                  String orderBeginPayTimeStr, String orderEndPayTimeStr) {
        //根据当前登录用户的id查找用户信息
        User loginUser = userMapper.selectByPrimaryKey(AuthUtils.getCurrentUserId());
        if (loginUser == null) {
            return AppResponse.Error("登录用户信息获取失败");
        }
        //时间转换String --> Date
        Date orderBiginPayTime = DateFormatUtil.string2date(orderBeginPayTimeStr, DateFormatUtil.YYYY_MM_DD);
        Date orderEndPayTime = DateFormatUtil.string2date(orderEndPayTimeStr, DateFormatUtil.YYYY_MM_DD);
        //获取登录用户的角色
        int userRole = loginUser.getUserRole();
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Order> orders = null;
        //当登录查询的是管理员角色
        if (userRole == 1) {
            //根据查询条件查询所有客户信息
            orders = orderMapper.listOrders(user, order, orderBiginPayTime, orderEndPayTime);
            //当登录查询的是店长角色
        } else if (userRole == 2) {
            //设置登录用户（店长）的编号进行关联查询其店铺下的订单列表
            user.setUserId(loginUser.getUserId());
            //根据查询条件查询店长的门店的订单信息
            orders = orderMapper.listStoreOrders(user, order, orderBiginPayTime, orderEndPayTime);
        }
        PageInfo<Order> pageData = new PageInfo<Order>(orders);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 查询订单详情列表接口
     *
     * @param orderId 订单id
     * @return
     */
    public AppResponse listOrderDetailsById(String orderId) {
        if (orderId == null || "".equals(orderId)) {
            return AppResponse.Error("订单编号错误");
        }
        List<Order> orders = orderMapper.listOrderDetailsById(orderId);
        return AppResponse.success("查询成功", orders);
    }

    /**
     * 修改订单状态接口
     *
     * @param orderIds       订单编号（批量修改用逗号分开）
     * @param orderCondition 订单状态
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateOrderConditionById(String orderIds, int orderCondition) {
        //检验要修改的orderIds是否为null或者""
        if (orderIds == null || "".equals(orderIds)) {
            return AppResponse.Error("没有该订单信息，修改失败");
        }
        List<String> listIds = Arrays.asList(orderIds.split(","));
        //根据订单id列表批量修改订单状态
        int count = orderMapper.updateOrderConditionById(listIds, AuthUtils.getCurrentUserId(), orderCondition);
        if(count != listIds.size()){
            //回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，修改失败");
        }
        return AppResponse.success("修改订单状态成功");
    }

}











