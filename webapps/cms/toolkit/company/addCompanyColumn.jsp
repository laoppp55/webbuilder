<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int siteid = ParamUtil.getIntParameter(request,"siteid", 0);
    String editor = authToken.getUserID();
    

    ICompanyinfoManager comMgr = CompanyinfoPeer.getInstance();
    CompanyColumn comcolumn = new CompanyColumn();
    int parentID = comMgr.getParentID(siteid);



    if(startflag == 1){
        String cname = ParamUtil.getParameter(request,"cname");
        String ename = ParamUtil.getParameter(request,"ename");
        int orderid = ParamUtil.getIntParameter(request,"orderid",0);

        comcolumn.setSiteid(siteid);
        comcolumn.setDirName("/"+ename+"/");
        comcolumn.setCName(cname);
        comcolumn.setEName(ename);
        comcolumn.setOrderID(orderid);
        comcolumn.setCreateDate(new Timestamp(System.currentTimeMillis()));
        comcolumn.setLastUpdated(new Timestamp(System.currentTimeMillis()));
        comcolumn.setEditor(editor);
        comcolumn.setParentID(parentID);

        comMgr.CreateColumn(comcolumn);
        response.sendRedirect("index.jsp?siteid=" + siteid);
    }

    
%>

<html>
<head>
 <title>添加公司分类</title>
    <style type="text/css">
        td {
            font-size: 12px
        }
    </style>
</head>
<body>
 <center>
<table>
<form name="addForm" action="addCompanyColumn.jsp" method="post" onsubmit="javascript:return check();">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="siteid" value="<%=siteid%>">
<tr>
    <td>分类名称:</td>
    <td><input type="text" name="cname" size="30" ></td>
</tr>
 <tr>
    <td>分类目录:</td>
    <td><input type="text" name="ename" size="30" ></td>
</tr>
<tr>
    <td>分类排序:</td>
    <td><input type="text" name="orderid" size="30" ></td>
</tr>

<tr>
    <td align="right" colspan="2"><input type="submit" value="提交"></td>
</tr>
</form>
</table>
</center>
</body>
</html>