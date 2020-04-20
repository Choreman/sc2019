package com.xzsd.app.store.dao;


import com.xzsd.app.store.entity.Store;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 门店信息数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-03-30
 */
@Mapper
public interface StoreMapper {

    /**
     * 根据司机的用户id获取司机负责的门店列表信息
     *
     * @param userId 要查询负责门店信息的司机id
     * @return
     */
    List<Store> listDriverStores(String userId);

}