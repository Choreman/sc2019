package com.neusoft.xzsd;

import com.xzsd.pc.utils.*;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 普通测试类
 *
 * @author 黄瑞穆
 * @date 2020年3月25日16:21:30
 */
public class TestDemo {

    @Test
    public void testRandomUtil() {
        System.out.println(RandomUtil.getRandom(20));
    }

    @Test
    public void testUUIDUtils() {
        System.out.println("UUIDUtils:");
        System.out.println(UUIDUtils.getUUID());
        System.out.println("UUID:");
        System.out.println(UUID.randomUUID().toString());
        System.out.println("UUID getTimeRandom:");
        System.out.println(UUIDUtils.getTimeRandom(2));
    }

    @Test
    public void testStringUtil() {
        System.out.println(StringUtils.getBillNo());
        System.out.println(StringUtils.getCommonCode(0));
    }

    @Test
    public void testDateFormatUtil() {
        System.out.println(DateFormatUtil.date2string(new Date(), DateFormatUtil.YYYYMMDDHHMMSS));

        String date1Str = "2018-01-01 00:00:00";
        String date2Str = "2018-01-02 00:00:00";

        Date date1 = DateFormatUtil.string2date(date1Str, DateFormatUtil.YYYYMMDDHHMMSS);
        Date date2 = DateFormatUtil.string2date(date2Str, DateFormatUtil.YYYYMMDDHHMMSS);

        System.out.println(date1.before(date2));
        System.out.println(date1.equals(date2));

    }

    @Test
    public void testShareCodeUtils() {
        String code = ShareCodeUtils.idToCode(453435L);
        System.out.println(code);
        System.out.println(ShareCodeUtils.codeToId(code));
    }

    @Test
    public void testArrayList() {
        int[] list1 = {1, 2};
        boolean contains = IntStream.of(list1).anyMatch(x -> x == 2);
        System.out.println(contains);
    }
}
