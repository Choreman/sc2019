package com.xzsd.pc.area.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.area.dao.AreaMapper;
import com.xzsd.pc.area.entity.Area;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.utils.AppResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 区域名称业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-03-27
 */
@Service
public class AreaService {

    @Resource
    private AreaMapper areaMapper;

    /**
     * 查询区域名称列表接口（分页）
     *
     * @param pageBean 分页信息
     * @return
     */
    public AppResponse listAreas(PageBean pageBean) {
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Area> areas = areaMapper.listAreas();
        PageInfo<Area> pageData = new PageInfo<Area>(areas);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 根据父级编号查询区域名称列表接口
     *
     * @param pageBean           分页信息
     * @param areaNameParentCode 父级编号
     * @return
     */
    public AppResponse listAreasByParentCode(PageBean pageBean, String areaNameParentCode) {
        //当传入的父级编号为空字符串""或者"null"时，设置为null
        if ("".equals(areaNameParentCode) || "null".equals(areaNameParentCode)) {
            areaNameParentCode = null;
        }
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<Area> areas = areaMapper.listAreasByParentCode(areaNameParentCode);
        PageInfo<Area> pageData = new PageInfo<Area>(areas);
        return AppResponse.success("查询成功!", pageData);
    }

    /**
     * 查询树形区域名称（省包含所有市，市包含所有区（县））
     * @return
     */
    public AppResponse listTreeAreas(){
        return AppResponse.success("查询成功", areaMapper.listTreeAreas());
    }

}











