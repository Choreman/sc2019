package com.xzsd.app.shoppingcart.dao;


import com.xzsd.app.shoppingcart.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 购物车据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     * 新增购物车信息
     *
     * @param shoppingCart 购物车信息
     * @return
     */
    int insertSelective(ShoppingCart shoppingCart);

    /**
     * 根据添加购物车的客户编号和添加购物车的商品查询该是否已经存在该购物车商品信息
     *
     * @param clientId 添加购物车的客户编号
     * @param goodsId  添加购物车的商品编号
     * @return
     */
    ShoppingCart findClientShoppingCart(@Param("clientId") String clientId, @Param("goodsId") String goodsId);

    /**
     * 修改购物车信息
     *
     * @param shoppingCart 要修改的购物车信息
     * @return
     */
    int updateByPrimaryKeySelective(ShoppingCart shoppingCart);

    /**
     * 根据客户的编号查询购物车列表
     *
     * @param shoppingCartClientCode 客户的编号
     * @return
     */
    List<ShoppingCart> listShoppingCartByClientId(@Param("shoppingCartClientCode") String shoppingCartClientCode);

    /**
     * 修改购物车的商品数量
     *
     * @param shoppingCartId       购物车编号
     * @param shoppingCartGoodsNum 购物车商品数量
     * @param updatePersonId       更新人id
     * @return
     */
    int updateShoppingCartGoodsNumById(@Param("shoppingCartId") String shoppingCartId,
                                       @Param("shoppingCartGoodsNum") int shoppingCartGoodsNum,
                                       @Param("updatePersonId") String updatePersonId);

    /**
     * 批量删除购物车信息（修改字段is_delete状态，并非真正删除）
     *
     * @param listIds        要删除的购物车信息列表
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteShoppingCartById(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

    /**
     * 根据购物车编号列表查询购物车信息
     *
     * @param listIds 购物车编号列表
     * @return
     */
    List<ShoppingCart> listShoppingCartById(@Param("listIds") List<String> listIds);

    /**
     * 根据购物车编号查询购物车信息
     *
     * @param shoppingCartId 购物车编号
     * @return
     */
    ShoppingCart selectByPrimaryKey(String shoppingCartId);

    int deleteByPrimaryKey(String shoppingCartId);

    int insert(ShoppingCart record);

    int updateByPrimaryKey(ShoppingCart record);
}
