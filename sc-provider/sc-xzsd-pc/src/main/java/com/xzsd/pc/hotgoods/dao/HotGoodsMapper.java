package com.xzsd.pc.hotgoods.dao;


import com.xzsd.pc.goods.entity.Goods;
import com.xzsd.pc.hotgoods.entity.HotGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 热门位商品数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-16
 */
@Mapper
public interface HotGoodsMapper {

    /**
     * 根据商品编号查询商品是否存在热门位
     *
     * @param hotGoodsGoodsCode 商品编号
     * @return
     */
    int countHotGoodsByGoodsCode(@Param("hotGoodsGoodsCode") String hotGoodsGoodsCode);

    /**
     * 根据商品编号查询商品是否存在热门位（排除热门商品本身）
     *
     * @param hotGoods 包含热门位的编号和商品的编号
     * @return
     */
    int countHotGoodsSelfByGoodsCode(HotGoods hotGoods);

    /**
     * 根据热门商品的排序查询是否已经存在该排序的热门商品
     *
     * @param hotGoodsWeight 排序
     * @return
     */
    int countHotGoodsByWeight(@Param("hotGoodsWeight") int hotGoodsWeight);

    /**
     * 根据热门商品的排序查询是否已经存在该排序的热门商品（排除热门商品本身）
     *
     * @param hotGoods 包含热门位的编号和商品的排序
     * @return
     */
    int countHotGoodsSelfByWeight(HotGoods hotGoods);

    /**
     * 新增热门位商品信息
     *
     * @param hotGoods 热门位商品信息
     * @return
     */
    int insertSelective(HotGoods hotGoods);

    /**
     * 根据商品信息条件查询热门位商品关联查询商品信息
     *
     * @param goods 要查询的商品信息
     * @return
     */
    List<HotGoods> listHotGoods(Goods goods);

    /**
     * 根据商品编号列表查询热门商品信息
     *
     * @param listIds 商品编号列表
     * @return
     */
    List<HotGoods> listHotGoodsByIds(@Param("listIds") List<String> listIds);

    /**
     * 根据id查询热门位商品信息
     *
     * @param hotGoodsId 热门位商品id
     * @return
     */
    HotGoods selectByPrimaryKey(String hotGoodsId);

    /**
     * 删除热门位信息（修改字段is_delete状态，并非真正删除）
     *
     * @param listIds        要删除的热门位信息列表
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteHotGoodsById(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

    /**
     * 修改热门位商品信息
     *
     * @param hotGoods 热门位商品信息
     * @return
     */
    int updateByPrimaryKeySelective(HotGoods hotGoods);

    int deleteByPrimaryKey(String hotGoodsId);

    int insert(HotGoods record);

    int updateByPrimaryKey(HotGoods record);
}