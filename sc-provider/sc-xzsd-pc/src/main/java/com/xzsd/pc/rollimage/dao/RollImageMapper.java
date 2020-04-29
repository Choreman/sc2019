package com.xzsd.pc.rollimage.dao;


import com.xzsd.pc.rollimage.entity.RollImage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 轮播图数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-14
 */
@Mapper
public interface RollImageMapper {

    /**
     * 根据所选商品编号查询是否已经存在该商品的轮播图
     *
     * @param rollImageGoodsCode 所选商品编号
     * @return
     */
    int countRollImageByRollImageGoodsCode(@Param("rollImageGoodsCode") String rollImageGoodsCode);

    /**
     * 根据商品的排序查询是否已经存在该排序的轮播图
     *
     * @param rollImageWeight 排序
     * @return
     */
    int countRollImageByWeight(@Param("rollImageWeight") int rollImageWeight);

    /**
     * 新增轮播图
     *
     * @param rollImage 轮播图信息
     * @return
     */
    int insertSelective(RollImage rollImage);

    /**
     * 根据轮播图状态查询轮播图列表
     *
     * @param rollImageCondition 轮播图状态
     * @return
     */
    List<RollImage> listRollImages(@Param("rollImageCondition") String rollImageCondition);

    /**
     * 根据轮播图编号列表查询轮播图列表信息
     *
     * @param rollImageIds 轮播图编号列表
     * @return
     */
    List<RollImage> listRollImagesByIds(@Param("rollImageIds") List<String> rollImageIds);

    /**
     * 根据商品编号列表查询轮播图信息
     *
     * @param listIds 商品编号列表
     * @return
     */
    List<RollImage> listRollImageByIds(@Param("listIds") List<String> listIds);

    /**
     * 批量修改轮播图状态信息
     *
     * @param rollImageList      轮播图列表信息
     * @param updatePersonId     更新人编号
     * @param rollImageCondition 轮播图状态
     * @return
     */
    int updateRollImageListCondition(@Param("rollImageList") List<RollImage> rollImageList,
                                     @Param("updatePersonId") String updatePersonId,
                                     @Param("rollImageCondition") int rollImageCondition);

    /**
     * 删除轮播图信息（修改字段is_delete状态，并非真正删除）
     *
     * @param listIds        要删除的轮播图信息列表
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteRollImageById(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

    int deleteByPrimaryKey(String rollImageId);

    RollImage selectByPrimaryKey(String rollImageId);

    int updateByPrimaryKeySelective(RollImage rollImage);

    int insert(RollImage record);

    int updateByPrimaryKey(RollImage record);
}