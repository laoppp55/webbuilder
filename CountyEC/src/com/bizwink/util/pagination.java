package com.bizwink.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Administrator on 18-4-17.
 */
public class pagination {
    public static String makePagingForSearch(String infotype,int pageSize,int pageno, int totalcount,String keyword,String url) {
        int maxPage = (totalcount % pageSize) == 0 ? (totalcount / pageSize) : ((totalcount / pageSize) + 1);
        int nexPage = maxPage,prePage = 1;
        if(pageno >= 1) prePage = pageno - 1;
        if(pageno < maxPage) nexPage = pageno + 1;
        String html = "";
        if(totalcount != 0) {
            if (pageno > maxPage) pageno = maxPage;
            if (pageno < 1) pageno = 1;
            try {
                if (pageno <1) {
                } else {
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=" + (pageno-1) + "&infotype=" + infotype + "\">上一页</a>\r\n";
                }
                int start = 1;
                if (pageno > 4) {
                    start = prePage;
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=0" + "&infotype=" + infotype + "\">1</a>\r\n";
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=1" + "&infotype=" + infotype + "\">2</a>\r\n";
                    html = html + "…\r\n";
                }

                int end = pageno+1;

                if (end > maxPage) {
                    end = maxPage;
                }

                for (int i = start; i <= end; i++) {
                    if (pageno == i) {
                        html = html + "<a class=\"ts\" href=\"javascript:void(0)\">" + i + "</a>\r\n";
                    } else {
                        html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=" + (i-1) + "&infotype=" + infotype + "\">" + i + "</a>\r\n";
                    }
                }

                if (end <=  maxPage-2) {
                    html = html + "…\r\n";
                }

                if (end < prePage) {
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=" + (maxPage - 1) + "&infotype=" + infotype + "\">" + (maxPage - 1) + "</a>\r\n";
                }

                if (end < maxPage) {
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=" + (maxPage - 1) + "&infotype=" + infotype + "\">" + (maxPage - 1) + "</a>\r\n";
                }

                if (pageno == maxPage) {
                } else {
                    html = html + "<a href=\"" + url + "?keyword=" + URLEncoder.encode(keyword,"utf-8") + "&pageno=" + (nexPage-1) + "&infotype=" + infotype + "\">下一页</a>\r\n";
                }
            } catch (UnsupportedEncodingException exp) {
                exp.printStackTrace();
            }
        }

        return html;
    }
}
