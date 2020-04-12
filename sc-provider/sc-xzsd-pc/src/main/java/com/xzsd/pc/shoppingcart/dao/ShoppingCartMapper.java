package com.xzsd.pc.shoppingcart.dao;


import com.xzsd.pc.shoppingcart.entity.ShoppingCart;

public interface ShoppingCartMapper {
    int deleteByPrimaryKey(String shoppingCartId);

    int insert(ShoppingCart record);

    int insertSelective(ShoppingCart record);

    ShoppingCart selectByPrimaryKey(String shoppingCartId);

    int updateByPrimaryKeySelective(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
}