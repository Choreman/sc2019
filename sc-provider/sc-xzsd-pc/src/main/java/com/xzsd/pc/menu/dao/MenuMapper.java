package com.xzsd.pc.menu.dao;


import com.xzsd.pc.menu.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * 菜单管理数据库接口类
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@Mapper
public interface MenuMapper {

    /**
     * 新增菜单信息
     * @param menu 菜单信息
     * @return
     */
    int insertSelective(Menu menu);

    /**
     * 根据菜单名称计算菜单数
     * @param menuName 菜单名称
     * @return
     */
    int countMenuByMenuName(@Param("menuName") String menuName);

    /**
     * 查询菜单列表接口
     * @return
     */
    List<Menu> listMenus();

    /**
     * 根据菜单id查找菜单信息
     * @param menuId 菜单id
     * @return
     */
    Menu selectByPrimaryKey(@Param("menuId") String menuId);

    /**
     * 修改菜单信息
     * @param menu 菜单信息
     * @return
     */
    int updateByPrimaryKeySelective(Menu menu);

    /**
     * 删除菜单信息（修改字段is_delete状态，并非真正删除）
     * @param listIds 要删除的菜单信息列表
     * @param updatePersonId 更新人id
     * @return
     */
    int deleteMenuById(@Param("listIds") List<String> listIds, @Param("updatePersonId") String updatePersonId);

}