package com.heaton.bot;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import java.net.*;

public class ParsePage {
    private String buf;

    public ParsePage(String content) {
        this.buf = content;
    }

    public List resultByFilter(String url,List matchurls,String startStr,String endStr) {
        String result = this.buf.toLowerCase();
        String lowcase_startStr = startStr.toLowerCase();
        String lowcase_endStr = endStr.toLowerCase();
        int num = 0;
        int posi = 0;
        String title = null;
        List hrefs = new ArrayList();
        URLInfo urlinfo = null;

        while (result.indexOf(lowcase_endStr) != -1)
        {
            num = num +1;
            posi = result.indexOf(lowcase_endStr);
            result = result.substring(posi + lowcase_endStr.length());
        }

        //System.out.println("num=" + num);

        String[] buf = new String[num + 1];
        int startPosi = 0;
        int endPosi = 0;
        String href= null;
        int i = 0;

        if (num > 0){
            String tempBuf = this.buf;                           //用于截取信息的字符串
            String lowcase_content = this.buf.toLowerCase();     //用于获取位置信息的字符串
            for (i=0; i<num; i++){
                startPosi = lowcase_content.indexOf(lowcase_startStr);
                endPosi = lowcase_content.indexOf(lowcase_endStr);
                if (startPosi > -1 && endPosi > -1) {
                    if (endPosi > startPosi) {
                        buf[i] = lowcase_content.substring(startPosi,endPosi);
                        lowcase_content = lowcase_content.substring(endPosi + lowcase_endStr.length());
                        href = buf[i];
                        posi = href.toLowerCase().indexOf("href");
                        if (posi > -1) href = href.substring(posi+4);
                        posi = href.indexOf("=");
                        href = href.substring(posi+1).trim();
                        posi = href.indexOf(">");
                        if (posi > -1) {
                            title = href.substring(posi + 1).trim();
                            href = href.substring(0,posi);
                        }
                        posi = href.indexOf(" ");
                        if (posi > -1) href = href.substring(0,posi);
                        href = StringUtil.replace(href,"\"","");
                        href = StringUtil.replace(href,"'","");
                        if (href.toLowerCase().indexOf("http") == -1 && href.toLowerCase().indexOf("javascript") == -1)
                            href = formatHref(url,href);
                        if (title != null && href != null) {
                            if (title.toLowerCase().indexOf("img") == -1 && href.toLowerCase().indexOf("javascript") == -1
                                    //&& href.indexOf(matchurl) != -1 && href.indexOf("pdf") == -1 && href.indexOf("doc") == -1
                                    && href.indexOf("ppt") == -1 && href.indexOf("rar") == -1 && href.indexOf("zip") == -1
                                    && href.indexOf("exe") == -1 && href.indexOf("xls") == -1 && href.indexOf("??") == -1) {
                                urlinfo = new URLInfo();
                                try{
                                    urlinfo.setURLInfo(new URL(href));
                                    urlinfo.setTitle(title);
                                    hrefs.add(urlinfo);
                                    //System.out.println(href + "====" + title);
                                } catch (Exception exp) {}
                            }
                        }
                    } else {
                        lowcase_content = lowcase_content.substring(endPosi + lowcase_endStr.length());
                    }
                }
            }
        }

        return hrefs;
    }

    public List getHREFs(String baseurl,String matchurl) {
        List hrefs = new ArrayList();
        String tbuf = this.buf;
        String ahref= null;
        String title = null;
        int posi = -1;

        Pattern p = Pattern.compile("<a[^<>]*\\s+href(\\s*)=(\\s*)[^<>]*>[^<>]*</a>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tbuf);
        while(m.find()) {
            ahref = tbuf.substring(m.start(),m.end());
            posi = ahref.toLowerCase().indexOf("href");
            ahref = ahref.substring(posi+4);
            posi = ahref.indexOf("=");
            ahref = ahref.substring(posi+1).trim();
            ahref = StringUtil.replace(ahref,"</a>","");
            ahref = StringUtil.replace(ahref,"</A>","");
            posi = ahref.lastIndexOf(">");
            title = ahref.substring(posi+1).trim();
            ahref = ahref.substring(0,posi);
            posi = ahref.indexOf(" ");
            if (posi > -1) ahref = ahref.substring(0,posi);
            ahref = StringUtil.replace(ahref,"\"","");
            ahref = StringUtil.replace(ahref,"'","");
            if (ahref.toLowerCase().indexOf("http") == -1)
                ahref = formatHref(baseurl,ahref);
            if (title != null && title != "")
                System.out.println(ahref + "====" + title);
        }

        return hrefs;
    }

    private String formatHref(String baseurl,String href) {
        String buf = href;
        int posi = -1;
        int double_point_slash = 0;
        String protocal = null;
        String host = null;

        posi = baseurl.lastIndexOf("://");
        if(posi > -1) {
            protocal = baseurl.substring(0,posi);
            host = baseurl.substring(posi + 3);
        }

        posi = host.lastIndexOf("/");
        if (posi != -1 && posi < host.length()) host = host.substring(0,posi+1);

        if (href != null && href != "") {
            if (href.length() >= 1) {
                posi = href.indexOf("../");
                if (posi == -1) {
                    //判断路径的第一个字符是否是斜杠，如果是斜杠的话，host只取主机名称部分
                    if (href.substring(0,1).equalsIgnoreCase("/")) {
                        posi = host.indexOf("/");
                        if (posi > -1) host = host.substring(0,posi);
                        href = protocal + "://" + host + href;
                    }
                    else {
                        href = protocal + "://" + host + href;
                    }
                }else {
                    while (posi > -1) {
                        double_point_slash = double_point_slash + 1;
                        href = href.substring(posi+3);
                        posi = href.indexOf("../");
                    }

                    for (int i=0; i<double_point_slash; i++) {
                        posi = host.lastIndexOf("/");
                        host = host.substring(0,posi);
                    }

                    href = protocal + "://" + host + href;
                }
            }
        }
        return href;
    }
}
