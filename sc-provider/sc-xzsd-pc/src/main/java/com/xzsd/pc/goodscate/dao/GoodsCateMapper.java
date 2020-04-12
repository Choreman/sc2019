package com.xzsd.pc.goodscate.dao;


import com.xzsd.pc.goodscate.entity.GoodsCate;

public interface GoodsCateMapper {
    int deleteByPrimaryKey(String goodsCateId);

    int insert(GoodsCate record);

    int insertSelective(GoodsCate record);

    GoodsCate selectByPrimaryKey(String goodsCateId);

    int updateByPrimaryKeySelective(GoodsCate record);

    int updateByPrimaryKey(GoodsCate record);
}