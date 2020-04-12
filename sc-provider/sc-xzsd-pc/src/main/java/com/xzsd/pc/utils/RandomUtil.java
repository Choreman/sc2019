package com.xzsd.pc.utils;


/**
 * 随机数工具类
 *
 * @author 戴顺
 * @date 2015-08-14
 */
public class RandomUtil {

    /**
     * 生成随机数
     *
     * @param count 需要产生随机数的个数
     * @return
     */
    public static String getRandom(int count) {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < count; i++) {
            int num = (int) (Math.random() * 10);
            sbf.append(num);
        }
        return sbf.toString();
    }

}
