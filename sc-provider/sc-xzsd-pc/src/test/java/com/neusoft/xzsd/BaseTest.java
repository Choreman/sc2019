package com.neusoft.xzsd;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;


/**
 * 测试的基类（后续测试类只需要集成该类即可，免去添加注解）
 * 由于是Web项目，Junit需要模拟ServletContext，
 * 因此需要给测试类加上@WebAppConfiguration。
 *
 * 测试类继承本类，并在方法上添加@Test注解后运行
 * 这种方式相当于运行java web ,启动了spring容器，但速度比较慢，因为要加载spring容器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseTest {
    @Before
    public void init() {
        System.out.println("开始测试-----------------");
    }

    @After
    public void after() {
        System.out.println("测试结束-----------------");
    }
}

