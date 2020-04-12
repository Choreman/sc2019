package com.xzsd.pc.menu.controller;

import com.xzsd.pc.menu.entity.Menu;
import com.xzsd.pc.menu.service.MenuService;
import com.xzsd.pc.utils.AppResponse;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单管理控制器
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 新增菜单接口
     * @param menu 菜单信息
     * @return
     */
    @PostMapping("/addMenu")
    public AppResponse addMenu(Menu menu){
        try {
            return menuService.addMenu(menu);
        } catch (Exception e) {
            logger.error("新增菜单异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询菜单列表接口
     * @return
     */
    @PostMapping("/listMenus")
    public AppResponse listMenus(){
        try {
            return menuService.listMenus();
        } catch (Exception e) {
            logger.error("查询菜单列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询菜单详情接口
     * @param menuId 菜单id
     * @return
     */
    @PostMapping("/findMenuById")
    public AppResponse findMenuById(String menuId){
        try {
            return menuService.findMenuById(menuId);
        } catch (Exception e) {
            logger.error("查询菜单详情异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     *  修改菜单详情接口
     * @param menu 菜单信息
     * @return
     */
    @PostMapping("/updateMenuById")
    public AppResponse updateMenuById(Menu menu){
        try {
            return menuService.updateMenuById(menu);
        } catch (Exception e) {
            logger.error("修改菜单异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 删除菜单接口
     * @param menuIds 菜单id（批量删除用逗号分开）
     * @return
     */
    @PostMapping("/deleteMenuById")
    public AppResponse deleteMenuById(String menuIds){
        try {
            return menuService.deleteMenuById(menuIds);
        } catch (Exception e) {
            logger.error("新增菜单异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据角色获取菜单管理列表接口
     * @param userRole 用户角色
     * @return
     */
    @PostMapping("/listMenusByUserRole")
    public AppResponse listMenusByUserRole(String userRole){
        try {
            return menuService.listMenusByUserRole(Integer.valueOf(userRole));
        }catch (NumberFormatException e){
            logger.error("根据角色查询菜单列表异常", e);
            System.out.println(e.toString());
            return AppResponse.Error("用户角色输入错误");
        }
        catch (Exception e) {
            logger.error("查询菜单列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}





















