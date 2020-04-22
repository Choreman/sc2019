package com.xzsd.app.image.dao;


import com.xzsd.app.image.entity.Image;
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
     * 根据图片的id修改图片的分类编号
     *
     * @param image 包含图片的id和图片分类编号
     * @return
     */
    int updateByPrimaryKeySelective(Image image);

}