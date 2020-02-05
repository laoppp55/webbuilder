<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-3-27
  Time: 下午9:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    StringBuffer temp = new StringBuffer();
    URL url = new URL("http://www.egreatwall.com/getPurchaserData.do");
    // 构造Image对象
    HttpURLConnection con = (HttpURLConnection)(url.openConnection());
    con.setConnectTimeout(10000);
    con.setReadTimeout(10000);

    InputStream is = url.openStream();
    Reader rd = new InputStreamReader(is, "utf-8");
    int c = 0;
    while ((c = rd.read()) != -1) {
        temp.append((char) c);
    }

    String buf = temp.toString();

    if (temp.length()>0){
        out.print(buf);
        out.flush();
    } else {
        out.print("nodata");
        out.flush();
    }
%>