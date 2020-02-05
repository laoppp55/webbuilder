package com.bizwink.calendar;
import java.util.Date;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Michelle Liu
 * @version 1.0
 */

public interface MyCalendar {
     void setYear(int y);
    void setMonth(int m);
    void setDay(int d);
    int getYear();
    int getMonth();
    int getDay();
    Date getRequestedDate();
    void setFormName(String form);
    String getFormName();
    String getDI();
    void setDI(String di);
}