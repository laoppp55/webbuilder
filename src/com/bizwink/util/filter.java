package com.bizwink.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-5
 * Time: 19:57:04
 * To change this template use File | Settings | File Templates.
 */
public class filter {
    public static String excludeHTMLCode(String param ) {
        String content = param;

        if (content != null && content!="") {
            Pattern p = Pattern.compile("<script[^>]*>[\\d\\D]*?</script>", Pattern.CASE_INSENSITIVE);
            java.util.regex.Matcher matcher = p.matcher(content);
            String matchStr=null;
            String tbuf = content;
            while (matcher.find()) {
                matchStr = tbuf.substring(matcher.start(), matcher.end());
                content = content.replace(matchStr,"");
            }

            p = Pattern.compile("<iframe[^>]*>[\\d\\D]*?</iframe>", Pattern.CASE_INSENSITIVE);
            matcher = p.matcher(content);
            matchStr=null;
            tbuf = content;
            while (matcher.find()) {
                matchStr = tbuf.substring(matcher.start(), matcher.end());
                content = content.replace(matchStr,"");
            }

            boolean is_email = checkEmail(content);

            //net user,xp_cmdshell
            ///add,
            //exec master.dbo.xp_cmdshell
            //net localgroup administrators
            //select,count,Asc,char,mid
            //insert,delete from,drop table,update,truncate,from
            //%,",',<,>,%,&,(,),;,+,-,[,],{,},:,?
            if (content!=null && content !="") {
                content = content.replace("eval","");
                content = content.replace("document","");
                content = content.replace(">","");
                content = content.replace("<","");
                content = content.replace("%","");
                content = content.replace("$","");
                content = content.replace("\"","");
                content = content.replace("(","");
                content = content.replace(")","");
                content = content.replace("|","");
                content = content.replace("@","");
                content = content.replace("\'","");
                content = content.replace("+","");
                content = content.replace("0x0d","");            //回车符
                content = content.replace("0x0a","");            //换行符
                content = content.replace(",","");
                content = content.replace("\\","");
                content = content.replace("<>","");
                content = content.replace("()","");

                Pattern p1 = Pattern.compile("net user|xp_cmdshell|add|exec master.dbo.xp_cmdshell|net localgroup administrators|select|count|asc|char|drop table|update|truncate|from",
                        Pattern.CASE_INSENSITIVE);
                java.util.regex.Matcher matcher1 = p1.matcher(content);
                String matchStr1=null;
                String tbuf1 = content;
                while (matcher1.find()) {
                    matchStr1 = tbuf1.substring(matcher1.start(), matcher1.end());
                    content = content.replace(matchStr1,"");
                }
            }
        }

        return content;
    }

    public static boolean checkEmail(String email) {// 验证邮箱的正则表达式
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }
}
