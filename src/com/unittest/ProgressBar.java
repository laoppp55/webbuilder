package com.unittest;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class ProgressBar extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=GBK";
    //计数器
    private int counter = 1;

    //Initialize global variables
    public void init() throws ServletException {

    }

    //处理get方法
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //得到任务类型
        String task = request.getParameter("task");
        //返回的xml字符串
        String res = "";

        //第一次，创建进度条
        if (task.equals("create")){
            res = "<key>1</key>";
            counter = 1;
        }else{
            //完成百分比
            String percent = "";
            switch(counter){
                case 1:percent = "10";break;
                case 2:percent = "23";break;
                case 3:percent = "35";break;
                case 4:percent = "51";break;
                case 5:percent = "64";break;
                case 6:percent = "73";break;
                case 7:percent = "89";break;
                case 8:percent = "100";break;
            }
            counter++;

            res = "<percent>" + percent + "</percent>";
        }

        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        out.println("<response>");
        out.println(res);
        out.println("</response>");
        out.close();
    }

    public void destroy() {

    }
}
 