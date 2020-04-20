package com.xzsd.app.driver.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.app.driver.dao.DriverMapper;
import com.xzsd.app.store.dao.StoreMapper;
import com.xzsd.app.store.entity.Store;
import com.xzsd.app.user.dao.UserMapper;
import com.xzsd.app.utils.AppResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
    private StoreMapper storeMapper;

    /**
     * 查询负责门店列表接口
     *
     * @param userId 要查询负责门店信息的司机id
     * @return
     */
    public AppResponse listDriverStores(String userId) {
        List<Store> stores = storeMapper.listDriverStores(userId);
        for (Store store : stores){
            //当查询的是门店信息时，拼接门店的省市区地址
            String address = store.getStoreAddress() == null ? "" : store.getStoreAddress();
            String provinceName = store.getProvinceName() == null ? "" : store.getProvinceName();
            String cityName = store.getCityName() == null ? "" : store.getCityName();
            String regionName = store.getRegionName() == null ? "" : store.getRegionName();
            address = provinceName + cityName + regionName + address;
            store.setStoreAddress(address);
        }
        return AppResponse.success("查询成功！", stores);
    }

}


















