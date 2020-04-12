package com.neusoft.xzsd;

import org.junit.Test;

/**
 * 继承了BaseTest类
 * 1.可以启动spring容器，获取到其中的bean
 * 2.测试速度会较慢，因为要启动spring容器
 */
public class BaseTestSon extends BaseTest {

    @Test
    public void test() {
        System.out.println("本类方法继承BaseTest");
    }

}
