package com.xzsd.pc.image.dao;


import com.xzsd.pc.image.entity.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图片数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-11
 */
@Mapper
public interface ImageMapper {

    /**
     * 新增图片信息
     *
     * @param image 图片信息
     * @return
     */
    int insertSelective(Image image);

    /**
     * 删除单张图片信息（修改字段is_delete状态，并非真正删除）
     *
     * @param imageId 图片编号
     * @param updatePersonId 更新人信息
     * @return
     */
    int deleteByPrimaryKey(@Param("imageId") String imageId, @Param("updatePersonId") String updatePersonId);

    /**
     * 批量删除用户的头像图片信息（修改字段is_delete状态，并非真正删除）
     *
     * @param listIds        要删除用户的头像图片的图片分类编号
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteImageByUserId(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

    /**
     * 根据图片分类编号修改图片信息
     * @param image 要修改的图片信息
     * @return
     */
    int updateByImageCateCodeSelective(Image image);

    int insert(Image record);

    Image selectByPrimaryKey(String imageId);

    int updateByPrimaryKey(Image record);
}