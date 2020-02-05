package com.bizwink.stockinfo;

import com.bizwink.images.StockGraphProducer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-1-3
 * Time: 9:29:15
 * To change this template use File | Settings | File Templates.
 */
public class genkline {
    public static void main(String[] args) throws Exception {
        //声成K线图
        try {
            com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
            String klinepath = props.getProperty("main.db.k_line_path");

            FileOutputStream day_k_line = new FileOutputStream(klinepath + File.separator  + "600266.SS_day.jpg");
            FileOutputStream small_day_k_line = new FileOutputStream(klinepath + File.separator  + "600266.SS_small_day.jpg");
            FileOutputStream week_k_line = new FileOutputStream(klinepath + File.separator + "600266.SS_week.jpg");
            //FileOutputStream month_k_line = new FileOutputStream(klinepath + File.separator  + stockcode + "_month.jpg");
            StockGraphProducer producer = new StockGraphProducer();
            producer.createDayK_LineImage(day_k_line, "600266.SS");
            producer.createSmallDayK_LineImage(small_day_k_line,"600266.SS");
            producer.createWeekK_LineImage(week_k_line, "600266.SS");

            //producer.createMonthK_LineImage(month_k_line, stockcode);
            day_k_line.close();
            week_k_line.close();
            //month_k_line.close();
        } catch (IOException ioexp) {
            ioexp.printStackTrace();
        }
    }
}
