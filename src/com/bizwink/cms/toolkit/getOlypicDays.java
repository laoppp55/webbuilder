package com.sinopec;

import java.sql.Date;
import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Du Zhenqiang
 * Date: 2008-3-5
 * Time: 16:31:56
 */
public class getOlypicDays {

    public long getPeriodDayCount() {
        Date d1 = new Date(System.currentTimeMillis());
        Date d2 = new Date(2008-1900, 8-1, 8);
        long periodTime = d2.getTime() - d1.getTime();
        return periodTime / 24 / 60 / 60 / 1000 + 1;
    }
}
