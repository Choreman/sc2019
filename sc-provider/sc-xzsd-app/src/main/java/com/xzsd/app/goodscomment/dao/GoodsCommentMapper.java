package com.xzsd.app.goodscomment.dao;


import com.xzsd.app.goodscomment.entity.GoodsComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品评价数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-17
 */
@Mapper
public interface GoodsCommentMapper {

    /**
     * 批量新增商品评价
     *
     * @param goodsCommentList 商品评价列表
     * @return
     */
    int insertGoodsCommentList(@Param("goodsCommentList") List<GoodsComment> goodsCommentList);

    /**
     * 根据商品编号和商品星级查询商品评价列表
     * @param goodsCommentGoodsCode 商品编号
     * @param goodsCommentStar 商品星级
     * @return
     */
    List<GoodsComment> listGoodsCommentsById(@Param("goodsCommentGoodsCode") String goodsCommentGoodsCode,
                                             @Param("goodsCommentStar") int goodsCommentStar);

}