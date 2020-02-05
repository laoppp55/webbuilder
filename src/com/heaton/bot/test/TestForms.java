/*
 * TestForms.java
 *
 * Created on August 18, 2003, 9:03 PM
 */

package com.heaton.bot.test;
import com.heaton.bot.*;
import java.util.*;
import java.io.*;
import javax.swing.text.*;
import java.util.regex.*;
/**
 *
 * @author  jheaton
 */
public class TestForms {

    /** Creates a new instance of TestForms */
    public TestForms() {

    }

    public static void test() {
        HTTP http=new HTTPSocket();
        http.setAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        http.SetAutoRedirect(true);
        http.setMaxBody(1024000);
        http.setTimeout(30000);
        http.setUser("y2301");
        http.setPassword("tinghsin");
        http.setUseCookies(true,true);

        HTMLPage page=new HTMLPage(http);
        try {
            page.open("http://www.cnewsbank.com",null);
        }
        catch (Exception ioexp) {}
        String result = http.getBody();

        //let's get the forms
        Vector vec = page.getForms();
        HTMLForm form = null;
        form = (HTMLForm)page.getForms().elementAt(3);

        Attribute field1 = form.get("username");
        Attribute field2 = form.get("password");

        field1.setValue("y2301");
        field2.setValue("tinghsin");
        AttributeList cookies = http.getCookies();
        for(int i=0;i<cookies.length();i++) {
            Attribute a1 = cookies.get(i);
        }

        System.out.println("form-string:" + form.toString());

        //登录到铱星系统
        try {
            page.post(form);
            String str = page.getHTTP().getBody();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        //开始抓取信息
        PrintWriter pw = null;
        List linkList= new ArrayList();
        try {
            //<a href="info_more.php?type=top&role_id=28283&name=珠海中粤纸杯容器有限公司&mrnum=1"><img src="images/ico_more.gif" alt="更多文章" width="48" height="13" border="0"></a>
            Pattern p = Pattern.compile("<a href=[^<>]*><img src=\"images/ico_more.gif\"[^<>]*>", Pattern.CASE_INSENSITIVE);
            page.open("http://www.cnewsbank.com/infocenter/info_main.php",null);
            result = page.getHTTP().getBody();
            java.util.regex.Matcher matcher = p.matcher(result);
            String matchStr = "";
            int posi = -1;
            while (matcher.find())
            {
                matchStr = result.substring(matcher.start(), matcher.end());
                posi = matchStr.lastIndexOf("<img");
                matchStr = "http://www.cnewsbank.com/infocenter/" + matchStr.substring(9,posi-2);
                linkList.add(matchStr);
            }
            pw = new PrintWriter(new FileOutputStream("c:\\1.html"));
            pw.write(result);
            pw.close();

            String url_str = "";
            System.out.println("索引页数量=" + linkList.size());
            for(int i=0; i<linkList.size(); i++) {
                url_str = (String)linkList.get(i);
                System.out.println(url_str);
                try {
                    page.open(url_str,null);
                    http.getServerHeaders();
                    result = page.getHTTP().getBody();
                    pw = new PrintWriter(new FileOutputStream("c:\\" + i+"20.html"));
                    pw.write(result);
                    pw.close();
                } catch (IOException iop) {}
            }

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=14816743&role_id=28282");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=14816743&role_id=28282",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\2.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15331274&role_id=3650");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15331274&role_id=3650",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\3.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15338585&role_id=930");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15338585&role_id=930",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\4.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15337901&role_id=17011");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15337901&role_id=17011",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\5.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15339102&role_id=582");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15339102&role_id=582",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\6.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15351059&role_id=930");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15351059&role_id=930",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\7.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15351321&role_id=10010");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15351321&role_id=10010",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\8.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15348065&role_id=3650");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15348065&role_id=3650",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\9.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15353632&role_id=3650");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15353632&role_id=3650",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\10.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15366370&role_id=502036");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15366370&role_id=502036",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\11.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15364492&role_id=16650");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15364492&role_id=16650",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\12.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15364519&role_id=10193");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15364519&role_id=10193",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\13.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15366115&role_id=506733");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15366115&role_id=506733",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\14.html"));
            pw.write(result);
            pw.close();

            System.out.println("http://www.cnewsbank.com/show_info.php?i_id=15365987&role_id=4667");
            page.open("http://www.cnewsbank.com/show_info.php?i_id=15365987&role_id=4667",null);
            result = page.getHTTP().getBody();
            pw = new PrintWriter(new FileOutputStream("c:\\15.html"));
            pw.write(result);
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}