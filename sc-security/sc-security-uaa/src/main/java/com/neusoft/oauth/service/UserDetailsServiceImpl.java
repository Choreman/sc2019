package com.neusoft.oauth.service;

import com.neusoft.oauth.dao.SysUserDao;
import com.neusoft.oauth.dao.UserDao;
import com.neusoft.oauth.entity.SysUser;
import com.neusoft.oauth.entity.User;
import com.neusoft.security.core.entity.SecurityUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>UserDetailsService实现类</p>
 * <p>创建日期：2017-12-27</p>
 *
 * @author 杨洲 yangzhou@neusoft.com
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowCaseUsername = username.toLowerCase();
        //根据传入的用户账号获取用户信息
        User user = userDao.getUserByUserLoginName(username);
        if (user != null) {
            return new SecurityUser(user.getUserId(), user.getUserLoginName(), user.getUserName(), user.getUserPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("web,app"));
        }
        throw new UsernameNotFoundException("用户" + lowCaseUsername + "不存在!");
    }

}
