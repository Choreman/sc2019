package com.xzsd.pc.menu.service;

import com.xzsd.pc.menu.dao.MenuMapper;
import com.xzsd.pc.menu.entity.Menu;
import com.xzsd.pc.utils.AppResponse;
import com.xzsd.pc.utils.AuthUtils;
import com.xzsd.pc.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 菜单管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-10
 */
@Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 新增菜单接口
     *
     * @param menu 菜单信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse addMenu(Menu menu) {
        if (menu.getMenuName() != null && !"".equals(menu.getMenuName())) {
            //计算数据库该菜单名称的数量
            int count = menuMapper.countMenuByMenuName(menu.getMenuName());
            //数据库中没有该菜单名称
            if (count == 0) {
                //设置UUID为主键
                menu.setMenuId(UUIDUtils.getUUID());
                //设置默认为菜单
                menu.setIsMenu(1);
                //设置基本属性
                menu.setCreateTime(new Date());
                menu.setCreatePerson(AuthUtils.getCurrentUserId());
                menu.setUpdateTime(new Date());
                menu.setUpdatePerson(AuthUtils.getCurrentUserId());
                menu.setIsDeleted(1);
                menu.setVersion(1);
                int status = menuMapper.insertSelective(menu);
                if (status > 0) {
                    return AppResponse.success("新增菜单信息成功");
                } else {
                    return AppResponse.bizError("新增菜单信息失败");
                }
            } else {
                return AppResponse.Error("菜单名称已存在，请重新输入");
            }
        } else {
            return AppResponse.Error("菜单名称输入有误，请重新输入");
        }
    }

    /**
     * 查询菜单列表接口
     *
     * @return
     */
    public AppResponse listMenus() {
        List<Menu> menus = menuMapper.listMenus();
        return AppResponse.success("查询成功", menus);
    }

    /**
     * 查询菜单详情接口
     *
     * @param menuId 菜单id
     * @return
     */
    public AppResponse findMenuById(String menuId) {
        //校验传入的菜单id
        if (menuId != null && !"".equals(menuId)) {
            //查询该菜单id的菜单信息
            Menu menu = menuMapper.selectByPrimaryKey(menuId);
            //查询有该菜单信息
            if (menu != null) {
                return AppResponse.success("查询成功", menu);
                //没有该菜单信息
            } else {
                return AppResponse.Error("菜单信息不存在");
            }
        } else {
            return AppResponse.Error("输入菜单信息错误");
        }
    }

    /**
     * 修改菜单详情接口
     *
     * @param menu 菜单信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse updateMenuById(Menu menu) {
        //校验菜单名称
        if(menu.getMenuName() != null && !"".equals(menu.getMenuName())){
            //校验菜单id是否存在
            if(menu.getMenuId() != null && !"".equals(menu.getMenuId())){
                Menu oldMenu = menuMapper.selectByPrimaryKey(menu.getMenuId());
                //校验数据库中有没有该id的记录
                if(oldMenu == null){
                    return AppResponse.Error("没有该菜单信息");
                //如果修改的菜单名称和原来的名称不一致，则检验数据库中菜单名称是否存在
                }else if(!menu.getMenuName().equals(oldMenu.getMenuName())){
                    int count = menuMapper.countMenuByMenuName(menu.getMenuName());
                    if(count != 0){
                        return AppResponse.Error("菜单名称已存在，请重新输入");
                    }
                }
                //如果数据库中的版本已经更新，则修改失败
                if(!oldMenu.getVersion().equals(menu.getVersion())){
                    return AppResponse.Error("信息已更新，请重试");
                }
                //默认只有菜单选项
                menu.setIsMenu(1);
                //设置基本信息
                menu.setUpdateTime(new Date());
                menu.setUpdatePerson(AuthUtils.getCurrentUserId());
                menu.setVersion(oldMenu.getVersion() + 1);
                int status = menuMapper.updateByPrimaryKeySelective(menu);
                if(status != 0){
                    return AppResponse.success("修改成功");
                }else {
                    return AppResponse.bizError("修改失败");
                }
            }else{
                return AppResponse.Error("菜单信息输入有误，请重新输入");
            }
        }else{
            return AppResponse.Error("菜单名称输入有误，请重新输入");
        }
    }

    /**
     * 删除菜单接口
     * @param menuIds 菜单id（批量删除用逗号分开）
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse deleteMenuById(String menuIds){
        //检验要删除的ids是否为null或者""
        if (menuIds != null && !"".equals(menuIds)) {
            List<String> listIds = Arrays.asList(menuIds.split(","));
            //删除菜单信息列表集合，设置更新人id
            int count = menuMapper.deleteMenuById(listIds, AuthUtils.getCurrentUserId());
            //当要删除的用户总数和已删除的总数不等时，回滚事物，删除失败
            if (count != listIds.size()) {
                //回滚事物
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return AppResponse.bizError("所选列表有未存在数据，删除失败");
            } else {
                return AppResponse.success("删除成功");
            }
        } else {
            return AppResponse.Error("没有该菜单信息，删除失败");
        }
    }

    /**
     * 根据角色获取菜单管理列表接口
     * @param userRole 用户角色
     * @return
     */
    public AppResponse listMenusByUserRole(int userRole){
        List<Menu> menus = new ArrayList<>();
        //管理员登录
        if(userRole == 1){
            //获取所有的菜单管理
            //用户管理
            menus.add(menuMapper.selectByPrimaryKey("84df3e18781e4a429f71be59ff398564"));
            //菜单管理
            menus.add(menuMapper.selectByPrimaryKey("bf4c65bf987f40208e523b2f36684b61"));
            //商品管理
            menus.add(menuMapper.selectByPrimaryKey("b76d8707354e4e94a7d22a4af878c63a"));
            //首页轮播图管理
            menus.add(menuMapper.selectByPrimaryKey("71ddeb86cb6343d98172ede6f5367046"));
            //商品分类管理
            menus.add(menuMapper.selectByPrimaryKey("b1e37dd0cb7442c1be7903e91dcf49d4"));
            //客户管理
            menus.add(menuMapper.selectByPrimaryKey("fc0178ce8d3242f786ed65d81def650f"));
            //订单管理
            menus.add(menuMapper.selectByPrimaryKey("16c2299d0f6744ba941d1abae2b5ab3c"));
            //热门位管理
            menus.add(menuMapper.selectByPrimaryKey("62058671ac4343d0baadf95f6ae9ef8c"));
            //门店管理
            menus.add(menuMapper.selectByPrimaryKey("c6f7d04cf7b24315b796e0ba8ba75216"));
            //司机信息管理
            menus.add(menuMapper.selectByPrimaryKey("6aa3f4b4cc7a47348788ed6600fa96c3"));
        //店长登录
        }else if(userRole == 2){
            //获取订单管理和客户管理
            Menu orderMenu = menuMapper.selectByPrimaryKey("16c2299d0f6744ba941d1abae2b5ab3c");
            Menu clientMenu = menuMapper.selectByPrimaryKey("fc0178ce8d3242f786ed65d81def650f");
            menus.add(orderMenu);
            menus.add(clientMenu);
        //司机登录
        }else if(userRole == 3){
            Menu driverMenu = menuMapper.selectByPrimaryKey("6aa3f4b4cc7a47348788ed6600fa96c3");
            menus.add(driverMenu);
        }else{
            return AppResponse.Error("用户角色输入错误");
        }
        return AppResponse.success("查询成功", menus);
    }

}
















