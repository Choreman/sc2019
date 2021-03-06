package com.xzsd.app.goods.dao;


import com.xzsd.app.goods.entity.Goods;
import com.xzsd.app.goodscomment.entity.GoodsComment;
import com.xzsd.app.orderdetail.entity.OrderDetail;
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
     *
     * @param goodsId        商品id
     * @param goodsStock     商品库存
     * @param updatePersonId 新增人id
     * @return
     */
    int updateGoodsStockById(@Param("goodsId") String goodsId,
                             @Param("goodsStock") int goodsStock,
                             @Param("updatePersonId") String updatePersonId);

    /**
     * 根据商品id列表，查询商品信息列表
     *
     * @param listIds 商品id列表
     * @return
     */
    List<Goods> listGoodsById(@Param("listIds") List<String> listIds);

    /**
     * 根据订单详情里的商品编号和商品数量修改商品库存
     *
     * @param orderDetails 订单详情列表
     * @return
     */
    int updateGoodsStockByOrderDetails(@Param("orderDetails") List<OrderDetail> orderDetails);

    /**
     * 根据商品评价里的商品编号批量更新商品星级
     *
     * @param goodsComments 商品评价列表
     * @return
     */
    int updateGoodsStarList(@Param("goodsComments") List<GoodsComment> goodsComments);

    /**
     * 根据订单编号和商品评价里的商品编号修改商品的销量
     *
     * @param goodsComments 包含商品编号
     * @param orderId       订单编号
     * @return
     */
    int updateGoodsSaleNumList(@Param("goodsComments") List<GoodsComment> goodsComments,
                               @Param("orderId") String orderId);

}