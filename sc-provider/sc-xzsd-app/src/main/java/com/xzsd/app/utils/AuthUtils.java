package com.xzsd.app.utils;

import com.neusoft.security.client.utils.SecurityUtils;

/**
 * <p>权限工具类</p>
 * <p>创建日期：2018-03-06</p>
 * 
 * @author 杨洲 yangzhou@neusoft.com
 */
public class AuthUtils {

    /**
     * 获取当前登录用户id
     * @return 用户id
     */
    public static String getCurrentUserId(){
        return SecurityUtils.getCurrentUserId();
    }

}
