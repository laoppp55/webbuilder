package com.bizwink.webtrend;

import com.bizwink.cms.util.filter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-7-31
 * Time: 16:46:10
 * To change this template use File | Settings | FzA)sde-.ile Templates.
 */
public class testAnalyzer {
    public static void main(String[] args) {
        try {
            if (args.length == 0) {
                loganalyzer al = new loganalyzer();
                al.run();
            } else {
                loganalyzer al = new loganalyzer(args[0]);
                al.run();
            }
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }
}
