<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 下面设置Content-Type:application/x-javascript 是为了适应Webkit的浏览器(chrome,safari)
    response.setHeader("Content-Type","application/x-javascript");
    int count = 6;    //  处理6条数据
    for(int i=0;i<count;i++){
        // 处理完毕一条，输出结果到客户端
        out.println(i+1);
        out.flush();
        // 这里假设每条数据处理时间为1秒
        Thread.currentThread().sleep(1000);
    }
%>
