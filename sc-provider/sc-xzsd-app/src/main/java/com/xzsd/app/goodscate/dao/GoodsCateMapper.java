package com.xzsd.app.goodscate.dao;


import com.xzsd.app.goodscate.entity.GoodsCate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品分类数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@Mapper
public interface GoodsCateMapper {

    /**
     * 根据父级编号查询子商品分类信息列表，传入null表示查询第一级商品分类信息
     *
     * @param cateParent 父级商品分类编号
     * @return
     */
    List<GoodsCate> listGoodsCatesByParentCode(@Param("cateParent") String cateParent);

}