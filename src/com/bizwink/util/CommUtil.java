package com.bizwink.util;


import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: jackzhang
 * Date: 13-10-17
 * Time: 下午1:46
 * To change this template use File | Settings | File Templates.
 */
public class CommUtil {

    public static String replace(String line, String oldString, String newString) {
        int i = 0;
        if (line != null) {
            if ((i = line.indexOf(oldString, i)) >= 0) {
                char[] line2 = line.toCharArray();
                char[] newString2 = newString.toCharArray();
                int oLength = oldString.length();
                StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);
                i += oLength;
                int j = i;
                while ((i = line.indexOf(oldString, i)) > 0) {
                    buf.append(line2, j, i - j).append(newString2);
                    i += oLength;
                    j = i;
                }
                buf.append(line2, j, line2.length - j);
                return buf.toString();
            }
        }
        return line;
    }

    public static String replace(String line, String oldString, String newString, int[] count) {
        if (line == null) {
            return null;
        }

        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    //获取HTML文件的文本内容
    public static final String getContentFromHTML(String content) {
        String tempBuf = "";
        Pattern p = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
        if (content != null) {
            String buf[] = p.split(content);

            for(int i=0; i<buf.length; i++) {
                tempBuf = tempBuf + buf[i];
            }
        }

        return tempBuf;
    }

   // public static Logger logger = Logger.getLogger(CommUtil.class.getName());
    public static String timestamp2string(Timestamp tm,String fmt){
        try{
            SimpleDateFormat df = new SimpleDateFormat(fmt);//定义格式，不显示毫秒
            return df.format(tm);
        } catch(Exception e)  {
             return "time error";
        }
    }

    public static String processXSS(String s) {
        try {
            s = s.replaceAll("\"", "");
            s = s.replaceAll("'", "");
            s = s.replaceAll("&", "");
            s = s.replaceAll("<", "");
            s = s.replaceAll(">", "");
            s = s.replaceAll("%", "");
            s = s.replaceAll("\\*", "");
            return s; } catch (Exception e) {
        }
        return "";
    }

    public static Timestamp string2timestamp(String tm,String fmt ){
        try {
            SimpleDateFormat df = new SimpleDateFormat(fmt);
            java.util.Date date = df.parse(tm);
            return   new Timestamp(date.getTime());
        }catch (Exception e){
          // logger.debug("时间类型转换错误");
            e.printStackTrace();
           return null;
        }
    }
    public static  String html2Text(String inputString) {
        String htmlStr = inputString; //含html标签的字符串
        String textStr ="";
        java.util.regex.Pattern p_script;
        java.util.regex.Matcher m_script;
        java.util.regex.Pattern p_style;
        java.util.regex.Matcher m_style;
        java.util.regex.Pattern p_html;
        java.util.regex.Matcher m_html;

        try {
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            htmlStr = m_script.replaceAll(""); //过滤script标签

            p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            htmlStr = m_style.replaceAll(""); //过滤style标签

            p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            htmlStr = m_html.replaceAll(""); //过滤html标签

            textStr = htmlStr;

        }catch(Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        return textStr;//返回文本字符串
    }
    public static void main(String[] argv) {
        System.out.println(CommUtil.string2timestamp("20110617","yyyyMMdd" ));

    }
}
