package com.xzsd.pc.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xzsd.pc.base.bean.PageBean;
import com.xzsd.pc.user.dao.ClientMapper;
import com.xzsd.pc.user.entity.User;
import com.xzsd.pc.utils.AppResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 客户管理业务处理类
 *
 * @author 黄瑞穆
 * @date 2020-04-12
 */
@Service
public class ClientService {

    @Resource
    private ClientMapper clientMapper;

    /**
     * 查询客户列表接口（分页）
     * @param pageBean 分页信息
     * @param user 客户信息查询条件
     * @return
     */
    public AppResponse listClients(PageBean pageBean, User user){
        PageHelper.startPage(pageBean.getPageNum(), pageBean.getPageSize());
        List<User> users = clientMapper.listClients(user);
        PageInfo<User> pageData = new PageInfo<User>(users);
        return AppResponse.success("查询成功!", pageData);
    }

}















