package com.charts;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

public interface IChartManager {
    String generateWTI(HttpSession session, PrintWriter pw);
    String generateBOLENTE(HttpSession session, PrintWriter pw);
    String generatePricePersent(HttpSession session, PrintWriter pw);
    String genStockLineImage(HttpSession session, PrintWriter pw,String stockcode,String cname);
    List getCrudeForBlt();
    List getCrudeForWTI();
    List getStockInfo(String stockcode);
    String createSmallDayK_LineImage(OutputStream stream,String stockcode) throws IOException;
}

