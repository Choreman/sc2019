package com.xzsd.pc.store.controller;

import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.store.entity.Store;
import com.xzsd.pc.store.service.StoreService;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 门店信息控制器
 *
 * @author 黄瑞穆
 * @date 2020-03-29
 */
@RestController
@RequestMapping("/store")
public class StoreController {

    private static final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    /**
     * 新增门店信息接口
     *
     * @param store 门店信息
     * @return
     */
    @PostMapping("/addStore")
    public AppResponse addStore(Store store) {
        try {
            return storeService.addStore(store);
        } catch (Exception e) {
            logger.error("新增门店信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询门店信息列表接口（分页）
     *
     * @param pageBean   分页信息
     * @param store      门店信息查询条件
     * @param managerName 店长名称查询条件
     * @return
     */
    @PostMapping("/listStores")
    public AppResponse listStores(PageBean pageBean, Store store, String managerName) {
        try {
            return storeService.listStores(pageBean, store, managerName);
        } catch (Exception e) {
            logger.error("查询门店信息列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询门店信息详情接口
     * @param storeId 门店id
     * @return
     */
    @PostMapping("/findStoresById")
    public AppResponse findStoresById(String storeId) {
        try {
            return storeService.findStoresById(storeId);
        } catch (Exception e) {
            logger.error("查询门店信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 修改门店信息接口
     * @param store 要修改的门店信息
     * @return
     */
    @PostMapping("/updateStoreById")
    public AppResponse updateStoreById(Store store){
        try {
            return storeService.updateStoreById(store);
        } catch (Exception e) {
            logger.error("修改门店信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除门店信息接口
     * @param storeIds 门店id（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteStoreById")
    public AppResponse deleteStoreById(String storeIds){
        try {
            return storeService.deleteStoreById(storeIds);
        } catch (Exception e) {
            logger.error("删除门店信息异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}














