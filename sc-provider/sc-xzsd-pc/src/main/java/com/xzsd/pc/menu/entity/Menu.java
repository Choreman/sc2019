package com.xzsd.pc.menu.entity;


import com.xzsd.pc.base.entity.BaseEntity;

import java.util.Date;

/**
 * 菜单实体类
 * 继承BaseEntity获取公有属性
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
public class Menu extends BaseEntity {

    /**
     * 菜单唯一标识，主键
     */
    private String menuId;
    /**
     * 菜单的名称
     */
    private String menuName;
    /**
     * 菜单路由路径
     */
    private String menuRoute;
    /**
     * 是否是菜单（1：菜单，2：目录）
     */
    private Integer isMenu;
    /**
     * 备注其他信息
     */
    private String menuComment;
    /**
     * 该记录的父级编号（null：没有父级编号）
     */
    private String menuParent;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuRoute() {
        return menuRoute;
    }

    public void setMenuRoute(String menuRoute) {
        this.menuRoute = menuRoute == null ? null : menuRoute.trim();
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public String getMenuComment() {
        return menuComment;
    }

    public void setMenuComment(String menuComment) {
        this.menuComment = menuComment == null ? null : menuComment.trim();
    }

    public String getMenuParent() {
        return menuParent;
    }

    public void setMenuParent(String menuParent) {
        this.menuParent = menuParent == null ? null : menuParent.trim();
    }
}