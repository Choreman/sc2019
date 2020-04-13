package com.xzsd.pc.goodscate.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.area.entity.Area;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.goods.dao.GoodsMapper;
import com.xzsd.pc.goodscate.dao.GoodsCateMapper;
import com.xzsd.pc.goodscate.entity.GoodsCate;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 商品分类业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@Service
public class GoodsCateService {

    @Resource
    private GoodsCateMapper goodsCateMapper;

    @Resource
    private GoodsMapper goodsMapper;

    /**
     * 新增商品分类接口
     *
     * @param goodsCate 商品分类信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addGoodsCate(GoodsCate goodsCate) {
        //检验商品分类名称是否为null或者""
        if (goodsCate.getCateName() == null || "".equals(goodsCate.getCateName())) {
            return AppResponse.Error("商品分类名称输入错误");
        }
        //设置UUID为主键
        goodsCate.setGoodsCateId(UUIDUtils.getUUID());
        //设置基本属性
        goodsCate.setCreateTime(new Date());
        goodsCate.setCreatePerson(AuthUtils.getCurrentUserId());
        goodsCate.setUpdateTime(new Date());
        goodsCate.setUpdatePerson(AuthUtils.getCurrentUserId());
        goodsCate.setVersion(1);
        goodsCate.setIsDeleted(1);
        //设置为第一级商品分类
        goodsCate.setCateLevel(1);
        //有父级编号时
        if (goodsCate.getCateParent() != null && !"".equals(goodsCate.getCateParent())) {
            //根据父级编号查询父级商品分类是否存在
            int count = goodsCateMapper.countGoodsCateByCateParent(goodsCate.getCateParent());
            //当数据库中不存在该父级编号的商品分类
            if (count == 0) {
                return AppResponse.Error("父级商品分类信息输入错误");
            }
            //当父级分类存在，表示新增第二级分类
            goodsCate.setCateLevel(2);
        }
        int status = goodsCateMapper.insertSelective(goodsCate);
        if (status > 0) {
            return AppResponse.success("新增商品分类成功");
        }
        return AppResponse.bizError("新增商品分类失败");
    }

    /**
     * 查询树形商品分类列表接口
     *
     * @return
     */
    public AppResponse listTreeGoodsCates() {
        return AppResponse.success("查询成功", goodsCateMapper.listTreeGoodsCates());
    }

    /**
     * 查询商品分类详情接口
     *
     * @param goodsCateId 商品分类id
     * @return
     */
    public AppResponse findGoodsCateById(String goodsCateId) {
        //校验门店id不为null或着""
        if (goodsCateId == null || "".equals(goodsCateId)) {
            return AppResponse.Error("没有该商品分类信息");
        }
        GoodsCate goodsCate = goodsCateMapper.findGoodsCateById(goodsCateId);
        if (goodsCate == null) {
            return AppResponse.Error("没有该商品分类信息");
        }
        return AppResponse.success("查询成功!", goodsCate);
    }

    /**
     * 修改商品分类详情接口
     *
     * @param goodsCate 商品分类信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateGoodsCateById(GoodsCate goodsCate) {
        //校验商品分类id不为null或着""
        if (goodsCate.getGoodsCateId() == null || "".equals(goodsCate.getGoodsCateId())) {
            return AppResponse.Error("没有该商品分类信息");
        }
        //校验数据库中有没有该id的记录
        GoodsCate oldGoodsCate = goodsCateMapper.selectByPrimaryKey(goodsCate.getGoodsCateId());
        if (oldGoodsCate == null) {
            return AppResponse.Error("没有该商品分类信息");
        } else if (!oldGoodsCate.getVersion().equals(goodsCate.getVersion())) {
            return AppResponse.Error("信息已更新，请重试");
        }
        //设置基本信息
        goodsCate.setUpdatePerson(AuthUtils.getCurrentUserId());
        goodsCate.setUpdateTime(new Date());
        goodsCate.setVersion(oldGoodsCate.getVersion() + 1);
        int status = goodsCateMapper.updateByPrimaryKeySelective(goodsCate);
        if (status > 0) {
            return AppResponse.success("修改商品分类信息成功");
        }
        return AppResponse.bizError("修改商品分类信息失败");
    }

    /**
     * 删除商品分类接口
     *
     * @param goodsCateId 商品分类编号（没有批量删除）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteGoodsCateById(String goodsCateId) {
        //检验要删除的storeIds是否为null或者""
        if (goodsCateId == null || "".equals(goodsCateId)) {
            return AppResponse.Error("没有该商品分类信息，删除失败");
        }
        //查询该商品分类是否有子分类
        int goodsCateCount = goodsCateMapper.countSonGoodsCateById(goodsCateId);
        //如果有子分类
        if (goodsCateCount > 0) {
            return AppResponse.Error("该分类还有子分类信息，删除失败");
        }
        //查询该商品分类是否有商品存在
        int goodsCount = goodsMapper.countGoodsByGoodsCateCode(goodsCateId);
        if (goodsCount > 0) {
            return AppResponse.Error("该分类还有商品信息，删除失败");
        }
        //删除商品分类信息列表集合，设置更新人id
        int count = goodsCateMapper.deleteGoodsCateById(goodsCateId, AuthUtils.getCurrentUserId());
        //当要删除的门店个数和已删除的门店个数不等时，回滚事物，删除失败
        if (count == 0 ) {
            return AppResponse.bizError("商品分类删除失败");
        }
        return AppResponse.success("商品分类删除成功");
    }

}











