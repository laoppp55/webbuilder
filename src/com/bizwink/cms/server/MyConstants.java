package com.bizwink.cms.server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-4-4.
 */
public class MyConstants {
    private static List<String> columns = new ArrayList<String>();
    private static int totalNum;
    private static boolean initFlag;

    public static List<String> getColumns() {
        return columns;
    }

    public static void setColumns(List<String> columns) {
        MyConstants.columns = columns;
    }

    public static int getTotalNum() {
        return totalNum;
    }

    public static void setTotalNum(int totalNum) {
        MyConstants.totalNum = totalNum;
    }

    public static boolean isInitFlag() {
        return initFlag;
    }

    public static void setInitFlag(boolean initFlag) {
        MyConstants.initFlag = initFlag;
    }
}
