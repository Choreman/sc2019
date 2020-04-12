package com.xzsd.pc.goodscomment.dao;


import com.xzsd.pc.goodscomment.entity.GoodsComment;

public interface GoodsCommentMapper {
    int deleteByPrimaryKey(String goodsCommentId);

    int insert(GoodsComment record);

    int insertSelective(GoodsComment record);

    GoodsComment selectByPrimaryKey(String goodsCommentId);

    int updateByPrimaryKeySelective(GoodsComment record);

    int updateByPrimaryKey(GoodsComment record);
}