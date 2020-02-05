package com.bizwink.util.pub;

/**
 * 字符串工具类
 */
public class StrUtil {
    public static String toStr(Object obj) {
        if(obj == null)
            return "";
        else
            return obj.toString();
    }
}
