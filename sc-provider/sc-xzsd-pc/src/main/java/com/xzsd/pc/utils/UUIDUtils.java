package com.xzsd.pc.utils;

import java.util.Date;
import java.util.UUID;

/**
 * UUID工具类
 *
 * @author 黄瑞穆
 * @date 2020-03-26
 */
public class UUIDUtils {

    /**
     * 生成UUID
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成"日期+随机数"的编号
     * @param count 需要产生随机数的个数
     * @return
     */
    public static String getTimeRandom(int count) {
        //生成count位的随机数
        String random = RandomUtil.getRandom(count);
        //生成此刻的时间，格式为：YYYYMMDDHHMMSS
        String time = DateFormatUtil.date2string(new Date(), DateFormatUtil.YYYYMMDDHHMMSS);
        return time+random;
    }


}
