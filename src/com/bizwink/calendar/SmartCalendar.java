package com.bizwink.calendar;

/**
 * Title:        sales
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Michelle
 * @version 1.0
 */
import javax.servlet.http.HttpSession;
import javax.servlet.ServletConfig;
import java.util.ResourceBundle;
import com.bizwink.calendar.SmartDate;
import javax.servlet.jsp.JspWriter;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.lang.Object;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import java.io.PrintWriter;
import java.lang.String;
import java.util.Date;
import java.lang.Exception;
import javax.servlet.http.HttpServletResponse;

public interface SmartCalendar {
 // Fields

    void initialize(PageContext p0) throws IOException;
    void initialize(ServletContext application, HttpSession session, HttpServletRequest request, HttpServletResponse response, JspWriter out) throws IOException;
    void initialize(ServletConfig config, HttpServletRequest request, HttpServletResponse response)       throws IOException;
    void setJSEventHandler(String p0);
    void setDateIn(String strDateInput);
    void responseWriteLn(String str) throws IOException;
    void responseWrite(String str) throws IOException;
    public void showCalendar(String mode, String formName) throws IOException;


}