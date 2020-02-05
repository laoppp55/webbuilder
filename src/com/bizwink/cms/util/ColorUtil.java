package com.bizwink.cms.util;

import java.awt.*;

/**
 * Created by IntelliJ IDEA.
 * User: EricDu
 * Date: 2007-9-6
 * Time: 17:11:27
 */
public class ColorUtil {

    /**
     * 将16进制的颜色转换为Color对象
     * @param c 16进制的颜色字符串
     * @return Color对象
     */
    public static Color parseToColor(String c) {
        Color convertedColor = Color.ORANGE;

        if ((c != null) && (isNumber(c)))
            try {
                convertedColor = new Color(Integer.parseInt(c, 16));
            } catch (NumberFormatException e) {}
        return convertedColor;
    }

    /**
     * 判断一个字符串是否是数字
     * @param str 要判断的字符串
     * @return true-是数字 false-不是数字
     */
    public static boolean isNumber(String str) {
        boolean isnumber = false;
        try {
            Integer.parseInt(str);
            isnumber = true;
        }
        catch (NumberFormatException e) {
            isnumber = false;
        }
        return isnumber;
    }
}
