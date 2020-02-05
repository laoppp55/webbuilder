package com.bizwink.cms.publish;

import com.bizwink.cms.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-1-1
 * Time: 20:59:27
 * To change this template use File | Settings | File Templates.
 */
public class getNavBarItemStyle {
    //type==0表示返回有链接的显示样式
    //type==1表示返回无链接的现实样式
    //flag表示HEAD、PREVIOUS、NEXT、BOTTOM
    String formatTheStyle(String content,int type) {
        String result = content;
        String buffer = content;
        String h_t_l_Style = "",h_t_ul_Style = "",h_s_Style="";
        String p_t_l_Style = "",p_t_ul_Style = "",p_s_Style="";
        String n_t_l_Style = "",n_t_ul_Style = "",n_s_Style="";
        String b_t_l_Style = "",b_t_ul_Style = "",b_s_Style="";
        result = StringUtil.replace(result,"<%if (pages < totalPages){%>","");
        result = StringUtil.replace(result,"<%if (pages > 1){%>","");
        try {
            Pattern p = Pattern.compile("<a href=[\"|']<%%HEAD%%>[\"|'][^<>]*>第一页</A><%\\}else\\{%>首页<%\\}%>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(result);

            if (m.find()) {
                int start = m.start();
                int end = m.end();
                result = result.substring(start,end);
            } else {
                p = Pattern.compile("<a href=<%%HEAD%%>[^<>]*>第一页</A><%\\}else\\{%>首页<%\\}%>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(result);
                if (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    result = result.substring(start,end);
                }
            }
            int posi = result.indexOf("<%}else{%>");
            if (posi > -1) {
                h_s_Style= "<%if (pages > 1){%>" + result;
                h_t_l_Style = result.substring(0,posi);
                h_t_ul_Style = result.substring(posi + "<%}else{%>".length());
                h_t_ul_Style = StringUtil.replace(h_t_ul_Style,"<%}%>","");
            }

            result = content;
            p = Pattern.compile("<a href=[\"|']<%%PREVIOUS%%>[\"|'][^<>]*>上一页</A><%\\}else\\{%>上一页<%\\}%>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(result);
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                result = result.substring(start,end);
            } else {
                p = Pattern.compile("<a href=<%%PREVIOUS%%>[^<>]*>上一页</A><%\\}else\\{%>上一页<%\\}%>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(result);
                if (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    result = result.substring(start,end);
                }
            }
            posi = result.indexOf("<%}else{%>");
            if (posi > -1) {
                p_s_Style= "<%if (pages > 1){%>" + result;
                p_t_l_Style = result.substring(0,posi);
                p_t_ul_Style = result.substring(posi + "<%}else{%>".length());
                p_t_ul_Style = StringUtil.replace(p_t_ul_Style,"<%}%>","");
            }

            result = content;
            p = Pattern.compile("<a href=[\"|']<%%NEXT%%>[\"|'][^<>]*>下一页</A><%\\}else\\{%>下一页<%\\}%>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(result);
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                result = result.substring(start,end);
            } else {
                p = Pattern.compile("<a href=<%%NEXT%%>[^<>]*>下一页</A><%\\}else\\{%>下一页<%\\}%>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(result);
                if (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    result = result.substring(start,end);
                }
            }
            posi = result.indexOf("<%}else{%>");
            if (posi > -1) {
                n_s_Style= "<%if (pages < totalPages){%>" + result;
                n_t_l_Style = result.substring(0,posi);
                n_t_ul_Style = result.substring(posi + "<%}else{%>".length());
                n_t_ul_Style = StringUtil.replace(n_t_ul_Style,"<%}%>","");
            }

            result = content;
            p = Pattern.compile("<a href=[\"|']<%%BOTTOM%%>[\"|'][^<>]*>最后页</A><%\\}else\\{%>尾页<%\\}%>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(result);
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                result = result.substring(start,end);
            } else {
                p = Pattern.compile("<a href=<%%BOTTOM%%>[^<>]*>最后页</A><%\\}else\\{%>尾页<%\\}%>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(result);
                if (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    result = result.substring(start,end);
                }
            }
            posi = result.indexOf("<%}else{%>");
            if (posi > -1) {
                b_s_Style= "<%if (pages < totalPages){%>" + result;
                b_t_l_Style = result.substring(0,posi);
                b_t_ul_Style = result.substring(posi + "<%}else{%>".length());
                b_t_ul_Style = StringUtil.replace(b_t_ul_Style,"<%}%>","");
            }

            if (type == 1) {
                buffer = StringUtil.replace(buffer,h_s_Style,h_t_ul_Style);
                buffer = StringUtil.replace(buffer,p_s_Style,p_t_ul_Style);
                buffer = StringUtil.replace(buffer,n_s_Style,n_t_l_Style);
                buffer = StringUtil.replace(buffer,b_s_Style,b_t_l_Style);
            } else if (type == 2) {
                buffer = StringUtil.replace(buffer,h_s_Style,h_t_l_Style);
                buffer = StringUtil.replace(buffer,p_s_Style,p_t_l_Style);
                buffer = StringUtil.replace(buffer,n_s_Style,n_t_ul_Style);
                buffer = StringUtil.replace(buffer,b_s_Style,b_t_ul_Style);
            } else {
                buffer = StringUtil.replace(buffer,h_s_Style,h_t_l_Style);
                buffer = StringUtil.replace(buffer,p_s_Style,p_t_l_Style);
                buffer = StringUtil.replace(buffer,n_s_Style,n_t_l_Style);
                buffer = StringUtil.replace(buffer,b_s_Style,b_t_l_Style);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //System.out.println(buffer);
        return buffer;
    }
}
