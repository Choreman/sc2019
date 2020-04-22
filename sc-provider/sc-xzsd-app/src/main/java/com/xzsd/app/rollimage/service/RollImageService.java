package com.xzsd.app.rollimage.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.rollimage.dao.RollImageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 轮播图业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-14
 */
@Service
public class RollImageService {

    @Resource
    private RollImageMapper rollImageMapper;


}











