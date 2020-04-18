package com.xzsd.pc.area.controller;

import com.xzsd.pc.area.service.AreaService;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.utils.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 区域名称控制器
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@RestController
@RequestMapping("/area")
public class AreaController {

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * 查询区域名称列表接口
     *
     * @return
     */
    @PostMapping("/listAreas")
    public AppResponse listAreas(PageBean pageBean) {
        try {
            return areaService.listAreas(pageBean);
        } catch (Exception e) {
            logger.error("查询区域名称列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 根据父级编号查询区域名称列表接口
     *
     * @param areaNameParentCode 父级编号
     * @return
     */
    @PostMapping("/listAreasByParentCode")
    public AppResponse listAreasByParentCode(String areaNameParentCode) {
        try {
            return areaService.listAreasByParentCode(areaNameParentCode);
        } catch (Exception e) {
            logger.error("查询父级区域名称列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

    /**
     * 查询树形区域名称（省包含所有市，市包含所有区（县））
     * @return
     */
    @PostMapping("/listTreeAreas")
    public AppResponse listTreeAreas(){
        try {
            return areaService.listTreeAreas();
        } catch (Exception e) {
            logger.error("查询所有区域名称列表异常", e);
            System.out.println(e.toString());
            return AppResponse.bizError("出现异常");
        }
    }

}


















