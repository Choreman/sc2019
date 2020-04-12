package com.xzsd.pc.area.dao;


import com.xzsd.pc.area.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域名称数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@Mapper
public interface AreaMapper {
    /**
     * 查询所有区域名称列表
     *
     * @return
     */
    List<Area> listAreas();

    /**
     * 根据父级编号查询子区域名称列表，传入null表示查询第一级区域名称
     *
     * @return
     */
    List<Area> listAreasByParentCode(@Param("areaNameParentCode") String areaNameParentCode);

    /**
     * 查询树形区域名称（省包含所有市，市包含所有区（县））
     *
     * @return
     */
    List<Area> listTreeAreas();

    int deleteByPrimaryKey(String areaNameId);

    int insert(Area record);

    int insertSelective(Area record);

    Area selectByPrimaryKey(String areaNameId);

    int updateByPrimaryKeySelective(Area record);

    int updateByPrimaryKey(Area record);

}