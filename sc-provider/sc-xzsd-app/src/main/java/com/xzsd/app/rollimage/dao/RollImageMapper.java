package com.xzsd.app.rollimage.dao;


import com.xzsd.app.rollimage.entity.RollImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 轮播图数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-14
 */
@Mapper
public interface RollImageMapper {

    /**
     * 查询app端首页轮播图列表
     * @return
     */
    List<RollImage> listRollImages();

}