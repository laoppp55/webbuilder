<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-3-26
  Time: 下午5:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    String username = null;
    if (cookies!=null) {
        for(Cookie c :cookies ){
            if (c.getName().equalsIgnoreCase("EGREATWALL_COOKIE_KEY_NAME")) {
                username = URLDecoder.decode(c.getValue(),"utf-8");
            }
            if (c.getName().equalsIgnoreCase("EGREATWALL_COOKIE_KEY")) {
                cookie_value = c.getValue();
            }
        }
    }

    String tbuf = "{\"userid\":\"" + cookie_value + "\",\"username\":\"" + username+"\",";

    StringBuffer temp = new StringBuffer();
    URL url = new URL("http://www.egreatwall.com/isLogin.do");
    // 构造Image对象
    HttpURLConnection con = (HttpURLConnection)(url.openConnection());
    con.setConnectTimeout(10000);
    con.setReadTimeout(10000);

    InputStream is = url.openStream();
    Reader rd = new InputStreamReader(is, "utf-8");
    int c1 = 0;
    while ((c1 = rd.read()) != -1) {
        temp.append((char) c1);
    }
    String buf = temp.toString();
    if (username != null) buf = buf.replace("\"status\":0","\"status\":1");
    buf = tbuf + buf.substring(1);

    System.out.println(buf);

    if (temp.length()>0){
        out.print(buf);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }
%>
