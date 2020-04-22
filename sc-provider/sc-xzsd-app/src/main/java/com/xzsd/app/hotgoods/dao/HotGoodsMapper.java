package com.xzsd.app.hotgoods.dao;

import com.xzsd.app.hotgoods.entity.HotGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 热门位商品数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-16
 */
@Mapper
public interface HotGoodsMapper {

    /**
     * 根据热门位商品展示数量查询热门商品信息
     * @param hotGoodsDisplayNum 热门位商品展示数量
     * @return
     */
    List<HotGoods> listHotGoodsByHotGoodsDisplayNum(int hotGoodsDisplayNum);

}