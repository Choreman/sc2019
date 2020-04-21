package com.xzsd.app.order.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.order.dao.OrderMapper;
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


}











