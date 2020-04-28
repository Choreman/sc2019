package com.xzsd.pc.store.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.store.dao.StoreMapper;
import com.xzsd.pc.store.entity.Store;
import com.xzsd.pc.user.dao.ClientMapper;
import com.xzsd.pc.user.dao.ManagerMapper;
import com.xzsd.pc.utils.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private ManagerMapper managerMapper;

    @Resource
    private ClientMapper clientMapper;

    /**
     * 新增门店信息接口
     *
     * @param store 门店信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addStore(Store store) {
        //查询是否有该店长信息
        int count = managerMapper.countManagerByManagerId(store.getStoreManagerId());
        //数据库中有该店长的信息
        if (count != 0) {
            //查询该店长是否已经绑定门店
            int storeManagerCount = managerMapper.countStoreManager(store.getStoreManagerId());
            //该店长未绑定门店，可以进行门店绑定操作
            if(storeManagerCount == 0){
                //设置UUID为主键
                store.setStoreId(UUIDUtils.getUUID());
                //生成门店邀请码
                //1.先生成6位的随机数
                Long random = Long.valueOf(RandomUtil.getRandom(6));
                //2.利用随机数生成邀请码
                String invitationcode = ShareCodeUtils.idToCode(random);
                //设置门店邀请码
                store.setStoreInvitationCode(invitationcode);
                //添加门店展示编号（年月日时分秒+2位随机数）
                store.setStoreCode(UUIDUtils.getTimeRandom(2));
                //设置基本属性
                store.setCreateTime(new Date());
                store.setCreatePerson(AuthUtils.getCurrentUserId());
                store.setUpdateTime(new Date());
                store.setUpdatePerson(AuthUtils.getCurrentUserId());
                store.setVersion(1);
                store.setIsDeleted(1);
                int status = storeMapper.insertSelective(store);
                if (status > 0) {
                    return AppResponse.success("新增门店信息成功");
                }
                return AppResponse.bizError("新增门店信息失败");
            }else{
                return AppResponse.Error("该店长已经绑定过门店");
            }
        }
        return AppResponse.Error("店长编号不存在，新增失败");
    }

    /**
     * 查询门店信息列表接口（分页）
     *
     * @param pageBean    分页信息
     * @param store       门店信息查询条件
     * @param managerName 店长名称查询条件
     * @return
     */
    public AppResponse listStores(PageBean pageBean, Store store, String managerName) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Store> stores = storeMapper.listStores(store, managerName);
        PageInfo<Store> pageData = new PageInfo<Store>(stores);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 查询门店信息详情接口
     *
     * @param storeId 门店id
     * @return
     */
    public AppResponse findStoresById(String storeId) {
        //校验门店id不为null或着""
        if (storeId == null || "".equals(storeId)) {
            return AppResponse.Error("没有该门店信息");
        }
        Store store = storeMapper.findStoresById(storeId);
        if (store == null) {
            return AppResponse.Error("没有该门店信息");
        }
        return AppResponse.success("查询成功!", store);
    }

    /**
     * 修改门店信息接口
     *
     * @param store 要修改的门店信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateStoreById(Store store) {
        //校验门店id不为null或着""
        if (store.getStoreId() == null || "".equals(store.getStoreId())) {
            return AppResponse.Error("没有该门店信息");
        }
        //校验数据库中有没有该id的记录
        Store oldStore = storeMapper.selectByPrimaryKey(store.getStoreId());
        if (oldStore == null) {
            return AppResponse.Error("没有该门店信息");
        } else if (!oldStore.getVersion().equals(store.getVersion())) {
            return AppResponse.Error("信息已更新，请重试");
        }
        //查询是否有该店长信息
        int count = managerMapper.countManagerByManagerId(store.getStoreManagerId());
        if(count == 0){
            return AppResponse.Error("店长编号不存在");
        }
        //如果传入的新店长编号和数据库中的店长编号不一致，表示修改了门店的绑定店长
        if(!oldStore.getStoreManagerId().equals(store.getStoreManagerId())){
            //查询该店长是否已经绑定门店
            int storeManagerCount = managerMapper.countStoreManager(store.getStoreManagerId());
            if(storeManagerCount != 0){
                return AppResponse.Error("该店长已经绑定过门店");
            }
        }
        //设置基本信息
        store.setUpdatePerson(AuthUtils.getCurrentUserId());
        store.setUpdateTime(new Date());
        store.setVersion(oldStore.getVersion() + 1);
        int status = storeMapper.updateByPrimaryKeySelective(store);
        if (status > 0) {
            return AppResponse.success("修改门店信息成功");
        }
        return AppResponse.bizError("修改门店信息失败");
    }

    /**
     * 删除门店信息接口
     *
     * @param storeIds 门店id（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteStoreById(String storeIds) {
        //检验要删除的storeIds是否为null或者""
        if (storeIds == null || "".equals(storeIds)) {
            return AppResponse.Error("没有该门店信息，删除失败");
        }
        //按照逗号分割门店id
        List<String> listIds = Arrays.asList(storeIds.split(","));
        //删除门店信息列表集合，设置更新人id
        int count = storeMapper.deleteStoreById(listIds, AuthUtils.getCurrentUserId());
        //删除门店同时修改客户对门店的关联关系
        clientMapper.updateClientStoreIdByStoreId(listIds);
        //当要删除的门店个数和已删除的门店个数不等时，回滚事物，删除失败
        if (count != listIds.size()) {
            //回滚事物
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return AppResponse.bizError("所选列表有未存在数据，删除失败");
        } else {
            return AppResponse.success("删除成功");
        }
    }

}