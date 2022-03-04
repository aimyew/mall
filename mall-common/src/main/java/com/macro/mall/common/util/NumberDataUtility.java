package com.macro.mall.common.util;

import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @date 2022/2/14 10:03
 * @apiNote 数字类型处理
 */
public class NumberDataUtility {

    public static double formatDecimalNumber(double number) {
        return formatDecimalNumber(number, 2);
    }

    /**
     * 对 double | long 的小数 保留 n 位小数 四舍五入
     *
     * @param number
     * @param n
     * @return
     */
    public static double formatDecimalNumber(double number, int n) {
        BigDecimal numberVal = new BigDecimal(number);
        // 四舍五入
        return numberVal.setScale(n, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * @param number
     * @return
     */
    public static String embedAsterRiskInNumber(String number) {
        if (!StringUtils.hasText(number)) {
            return number;
        }
        long count = number.chars().filter(Character::isDigit).count();
        if (number.length() == 11 && number.length() == count) {
            // phone number 手机号前三后四脱敏
            return number.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
        } else if (number.length() == 18 && (number.length() == count || count == 17)) {
            // id card number 身份证前三后四脱敏
            return number.replaceAll("(?<=\\d{3})\\d(?=\\w{4})", "*");
        } else {
            // other 其它皆为四个星号
            return number.replaceAll("\\d+", "****");
        }
    }

    /**
     * 18位身份证号 最后一位校验码 判断方法
     * 逻辑：
     * 1：身份证号前17位数分别乘不同的系数
     * 从第1位到17位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * 2：将乘积之和除以11，余数可能为：0 1 2 3 4 5 6 7 8 9 10
     * 3：根据余数，分别对应最后一位身份证号：1 0 X 9 8 7 6 5 4 3 2
     * 余数与校验码对应关系：0:1,1:0,2:X,3:9,4:8,5:7,6:6,7:5,8:4,9:3:10:2
     *
     * @param idCard 身份证号
     */
    public static boolean isIDCard(String idCard) {
        if (!(StringUtils.hasText(idCard) && idCard.length() == 18 && idCard.matches("\\d{17}([0-9xX])?"))) {
            return false;
        }
        char[] chars = idCard.toCharArray();
        int charsLength = chars.length - 1;
        int count = 0;
        for (int i = 0; i < charsLength; i++) {
            int charI = Integer.parseInt(String.valueOf(chars[i]));
            count += charI * (Math.pow(2, 17 - i) % 11);
        }
        String idCard18 = String.valueOf(chars[17]).toUpperCase();
        String idCardLast;
        switch (count % 11) {
            case 0:
                idCardLast = "1";
                break;
            case 1:
                idCardLast = "0";
                break;
            case 2:
                idCardLast = "X";
                break;
            default:
                idCardLast = 12 - (count % 11) + "";
                break;
        }
        return idCard18.equals(idCardLast);
    }
}
