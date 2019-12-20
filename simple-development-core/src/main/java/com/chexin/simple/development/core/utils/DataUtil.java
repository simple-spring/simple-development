package com.chexin.simple.development.core.utils;

import java.util.UUID;

/**
 * desc:    数据工具类
 */
public class DataUtil {

    /**
     * 根据uuid生成订单Id
     *
     * @return
     */
    public static long getOrderIdByUUId(String deviceCode) {
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            hashCodeV = -hashCodeV;
        }

        int deviceCodeV = deviceCode.hashCode();
        if (hashCodeV < 0) {
            //有可能是负数
            deviceCodeV = -deviceCodeV;
        }

        long num = hashCodeV + deviceCodeV;
        if (num < 0) {
            num = -num;
        }

        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return (num + RandomUtil.getRandomInt(5));
    }
}
