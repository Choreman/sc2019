package com.xzsd.pc.goods.dao;


import com.xzsd.pc.goods.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@Mapper
public interface GoodsMapper {

    /**
     * 根据商品分类编号查询商品数量
     * @param goodsCateCode 商品分类编号
     * @return
     */
    int countGoodsByGoodsCateCode(@Param("goodsCateCode") String goodsCateCode);

    int deleteByPrimaryKey(String goodsId);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(String goodsId);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}