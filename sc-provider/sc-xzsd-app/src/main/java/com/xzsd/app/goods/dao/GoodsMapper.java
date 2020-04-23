package com.xzsd.app.goods.dao;


import com.xzsd.app.goods.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-13
 */
@Mapper
public interface GoodsMapper {

    /**
     * 根据商品id查询商品详情关联查询商品图片
     *
     * @param goodsId
     * @return
     */
    Goods findGoodsById(String goodsId);

    /**
     * 根据一级商品分类编号查询一级分类下所有二级分类的所有商品列表接口
     *
     * @param goodsCateCode 一级商品分类编号
     * @return
     */
    List<Goods> listGoodsByGoodsCateCode(String goodsCateCode);

    /**
     * 根据商品id修改商品的库存信息
     * @param goodsId 商品id
     * @param goodsStock 商品库存
     * @param updatePersonId 新增人id
     * @return
     */
    int updateGoodsStockById(@Param("goodsId") String goodsId,
                             @Param("goodsStock") int goodsStock,
                             @Param("updatePersonId") String updatePersonId);

}