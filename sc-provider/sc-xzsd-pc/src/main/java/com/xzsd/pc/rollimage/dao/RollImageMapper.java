package com.xzsd.pc.rollimage.dao;


import com.xzsd.pc.rollimage.entity.RollImage;

public interface RollImageMapper {
    int deleteByPrimaryKey(String rollImageId);

    int insert(RollImage record);

    int insertSelective(RollImage record);

    RollImage selectByPrimaryKey(String rollImageId);

    int updateByPrimaryKeySelective(RollImage record);

    int updateByPrimaryKey(RollImage record);
}