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
     * @param goodsId
     * @return
     */
    Goods findGoodsById(String goodsId);

}