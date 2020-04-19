package com.xzsd.pc.goodscate.dao;


import com.xzsd.pc.goodscate.entity.GoodsCate;
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
     * 根据父级编号查询父级分类是否存在
     *
     * @param cateParent 父级编号
     * @return
     */
    int countGoodsCateByCateParent(@Param("cateParent") String cateParent);

    /**
     * 新增商品分类信息
     *
     * @param goodsCate 商品分类信息
     * @return
     */
    int insertSelective(GoodsCate goodsCate);

    /**
     * 查询树形商品分类信息（一级分类包含二级分类）
     *
     * @return
     */
    List<GoodsCate> listTreeGoodsCates();

    /**
     * 根据商品分类id查询商品分类信息
     * @param goodsCateId 商品分类id
     * @return
     */
    GoodsCate findGoodsCateById(@Param("goodsCateId") String goodsCateId);

    /**
     * 修改商品分类信息
     * @param goodsCate 商品分类信息
     * @return
     */
    int updateByPrimaryKeySelective(GoodsCate goodsCate);

    /**
     * 根据父级商品分类编号查询该分类下子分类的数量
     * @param goodsCateId 父级商品分类编号
     * @return
     */
    int countSonGoodsCateById(String goodsCateId);

    /**
     * 删除商品分类信息（修改字段is_delete状态，并非真正删除）
     * @param goodsCateId 要删除的商品分类编号
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteGoodsCateById(@Param("goodsCateId") String goodsCateId, @Param("updatePersonId") String updatePersonId);

    /**
     * 根据父级编号查询子商品分类信息列表，传入null表示查询第一级商品分类信息
     *
     * @return
     */
    List<GoodsCate> listGoodsCatesByParentCode(@Param("cateParent") String cateParent);

    int deleteByPrimaryKey(String goodsCateId);

    int insert(GoodsCate record);

    GoodsCate selectByPrimaryKey(String goodsCateId);

    int updateByPrimaryKey(GoodsCate record);
}