<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.wenba.Answer" %>
<%@ page import="com.bizwink.wenba.IWenbaManager" %>
<%@ page import="com.bizwink.wenba.wenbaManagerImpl" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=gbk" language="java" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", -1);
    int dpage = ParamUtil.getIntParameter(request, "dpage", -1);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    IWenbaManager columnMgr = wenbaManagerImpl.getInstance();
    List list = columnMgr.getAnswer(id);
    String wenti = columnMgr.getWenti(id);


%>
<html>
<head><title>Simple jsp page</title>
    <link rel=stylesheet type=text/css href="../../style/global.css">
</head>
<body BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>

    <br/>
    <br/>
    <br/>
    <br/>------------------------------------------问题：----------------------------<br/>
           <br /><br />
          <%=wenti%> <br /><br /><br />


         ------------------------------------------答案--------------------------------
    <br /><br /><br />
    <table width="1000" border=1 borderColorDark=#ffffec borderColorLight=#5e5e00>
        <tr>
            <td></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <%
            for (int i = 0; i < list.size(); i++) {
                Answer a = (Answer) list.get(i);
        %>
        <tr>
            <td><%=a.getAnwser()%>
            </td>
            <td>IP地址：<%=a.getIpaddress()%>
            </td>
            <td><%=a.getUsername()%>
            </td>
            <td>创建日期：<%=a.getCreatedate()%>
            </td>
            <td>参考资料：<%=a.getCankaoziliao()%>
            </td>
            <td><a href="deleteanswer.jsp?id=<%=a.getId()%>&qid=<%=id%>&username=<%=a.getUsername()%>&userid=<%=a.getUserid()%>">删除</a></td>
        </tr>
        <%}%>
    </table>

</center>
</body>
</html>