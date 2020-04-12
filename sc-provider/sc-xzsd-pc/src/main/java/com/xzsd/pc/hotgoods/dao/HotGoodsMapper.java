package com.xzsd.pc.hotgoods.dao;


import com.xzsd.pc.hotgoods.entity.HotGoods;

public interface HotGoodsMapper {
    int deleteByPrimaryKey(String hotGoodsId);

    int insert(HotGoods record);

    int insertSelective(HotGoods record);

    HotGoods selectByPrimaryKey(String hotGoodsId);

    int updateByPrimaryKeySelective(HotGoods record);

    int updateByPrimaryKey(HotGoods record);
}