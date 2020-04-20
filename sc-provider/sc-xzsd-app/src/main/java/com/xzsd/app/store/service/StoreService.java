package com.xzsd.app.store.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.store.dao.StoreMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;


/**
 * 门店信息业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-03-29
 */
@Service
public class StoreService {

    @Resource
    private StoreMapper storeMapper;


}


















