package com.aprilz.tiny.common.utils;


import cn.hutool.core.util.StrUtil;

import java.util.stream.IntStream;

/**
 * @Description 身份证工具类
 **/
public class IdCardUtil {

    // 身份证校验码
    private static final int[] COEFFICIENT_ARRAY = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    // 身份证号的尾数规则
    private static final String[] IDENTITY_MANTISSA = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
    private static final String IDENTITY_PATTERN = "^[0-9]{17}[0-9Xx]$";

    //身份证号校验 校验通过返回true
    public static boolean isLegalPattern(String identity) {
        if (StrUtil.isBlank(identity)) {
            return false;
        }

        if (identity.length() != 18) {
            return false;
        }

        if (!identity.matches(IDENTITY_PATTERN)) {
            return false;
        }
        // 将字符串对象中的字符转换为一个字符数组
        char[] chars = identity.toCharArray();
        long sum = IntStream.range(0, 17).map(index -> {
            char ch = chars[index];
            // 通俗理解：digit()是个边界值判断，不过边界返回字符数字本身数值，超过边界即返回 -1.
            int digit = Character.digit(ch, 10);
            int coefficient = COEFFICIENT_ARRAY[index];
            return digit * coefficient;
        }).summaryStatistics().getSum();

        // 计算出的尾数索引
        int mantissaIndex = (int) (sum % 11);
        String mantissa = IDENTITY_MANTISSA[mantissaIndex];

        String lastChar = identity.substring(17);
        if (lastChar.equalsIgnoreCase(mantissa)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取性别， 1-男，2-女
     */
    public static int getSex(String identity) {
        //倒数第二位奇数为男生，偶数为女生
        if (Integer.parseInt(identity.substring(identity.length() - 2, identity.length() - 1)) % 2 == 0) {
            return 2;
        }
        return 1;
    }

}
