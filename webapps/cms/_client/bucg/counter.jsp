<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=GBK"%>
<jsp:useBean id="counter" scope="page" class="com.bizwink.cms.util.counter"/>
<%
    //调用counter对象的ReadFile方法来读取文件lyfcount.txt中的计数
    String path = request.getRealPath("/");
    String url=path + "_prog" + File.separator + "count.txt";
    //String url=request.getRealPath("count.txt");
    String cont=counter.ReadFile(url);
    //调用counter对象的ReadFile方法来将计数器加一后写入到文件lyfcount.txt中
    counter.WriteFile(url,cont);
    out.write("您是第<font color=\"red\">" + cont + "</font>位访问者");
%>
