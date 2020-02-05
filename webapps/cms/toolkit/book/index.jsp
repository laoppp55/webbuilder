<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.xml.IFormManager" %>
<%@ page import="com.xml.FormPeer" %>
<%@ page import="com.xml.formfields" %>
<%@page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

  //  int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 5);

    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    
    IFormManager BMgr = FormPeer.getInstance();
    List list = new ArrayList();

    int rows = 0;

    String jumpstr = "";
    if(startflag != 1){
        String sqlstr = "select * from tbl_www_shwzg_com_130322_1 order by createdate desc";
        String sqlcount = "select count(*) from tbl_www_shwzg_com_130322_1";
        rows = BMgr.getBookCount(sqlcount);
        list = BMgr.getBookList(startrow,range,sqlstr);

    } else{

           int radio = ParamUtil.getIntParameter(request,"radio",0);
           String searchname = ParamUtil.getParameter(request,"searchname");
           jumpstr = "&startflag=" + startflag + "&searchname=" + searchname + "&radio=" + radio;
           if(radio == 1){
               String sqlsearch = "select * from tbl_www_shwzg_com_130322_1 where bookname like '@" + searchname + "@' order by createdate desc";
               String sqlsearchcount = "select count(*) from tbl_www_shwzg_com_130322_1 where bookname like '@" +searchname +"@'";
               rows = BMgr.getBookCount(sqlsearchcount);
               list = BMgr.getBookList(startrow,range,sqlsearch);

           }else{
               String sqlsearch = "select * from tbl_www_shwzg_com_130322_1 where username like '@" + searchname + "@' order by createdate desc";
               String sqlsearchcount = "select count(*) from tbl_www_shwzg_com_130322_1 where username like '@" + searchname + "@'";
               rows = BMgr.getBookCount(sqlsearchcount);
               list = BMgr.getBookList(startrow,range,sqlsearch);
           }
    }
    int totalpages = 0;
    int currentpage = 0;
    if(rows < range){
        totalpages = 1;
        currentpage = 1;
    }else{
        if(rows%range == 0)
            totalpages = rows/range;
        else
            totalpages = rows/range + 1;
        currentpage = startrow/range + 1;
    }


%>

<html>
  <head>
      <title>赠书管理</title>
      <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <meta http-equiv="Pragma" content="no-cache">
    <style type="text/css">
        TABLE {FONT-SIZE: 12px;word-break:break-all}
        BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
        .TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
        .FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
        .FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
        .FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
        A:link {text-decoration:none;line-height:20px;}
        A:visited {text-decoration:none;line-height:20px;}
        A:active {text-decoration:none;line-height:20px; font-weight:bold;}
        A:hover {text-decoration:none;line-height:20px;}
        .pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
        .form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
        .botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
    </style>
       <script language="javascript">
        function golist(r,jumpstr){
            window.location = "index.jsp?startrow="+r + jumpstr;
        }

        function del(id){
            var val;
            val = confirm("你确定要删除吗？");
            if(val == 1){
                window.location = "delete.jsp?startflag=1"+"&id="+id;
            }
        }

        
    </script>


  </head>
  <body>
       <form action="index.jsp" method="post" name="form">
           <input type="hidden" name="startflag" value="1">
          <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">赠书管理</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="20%" align="center">书名</td>
                                        <td align="center" width="10%">用户名</td>
                                        <td align="center" width="10%">联系电话</td>
                                        <td align="center" width="10%">邮编</td>
                                        <td align="center" width="25%">联系地址</td>
                                        <td align="center" width="15%">创建时间</td>
                                        <td align="center" width="5%">删除</td>

                                    </tr>

                                    <%
                                        for(int i=0;i< list.size(); i++){
                                            formfields book = (formfields)list.get(i);

                                    %>
                                     <tr  bgcolor="#FFFFFF" class="css_001">
                                       <td width="20%" align="center"><%=book.getBookname()==null?"-":book.getBookname()%></td>
                                       <td align="center" width="10%"><%=book.getUsername()==null?"-":book.getUsername()%></td>
                                       <td align="center" width="10%"><%=book.getTelephone()==null?"-":book.getTelephone()%></td>
                                         <td align="center" width="10%"><%=book.getPostcode()==null?"-":book.getPostcode()%></td>
                                        <td align="center" width="25%"><%=book.getAddress()==null?"-":book.getAddress()%></td>
                                        <td align="center" width="15%"><%=book.getCreatedate()==null?"-":book.getCreatedate()%></td>
                                        <td align="center" width="5%"><a href="javascript:del(<%=book.getId()%>)">删除</a></td>
                                        
                                    </tr>
                                    <%}%>
                                   </table></td></tr></table></td></tr></table>
          <table width="70%" class="css_002">
            <tr valign="bottom" width=100%>
                <td width="30%" align="center" colspan="2">
                    总<%=totalpages%>页&nbsp; 第<%=currentpage%>页&nbsp;  总<%=rows%>条&nbsp;   每页显示<%=range%>条
                </td>
               
                <td class="css_002" width="30%" align="center" colspan="2" >
                    <%
                        if((startrow-range)>=0){
                    %>
                    [<a href="index.jsp?startrow=<%=startrow-range%><%=jumpstr%>" class="css_002">上一页</a>]
                    <%}
                        if((startrow+range)<rows){
                    %>
                    [<a href="index.jsp?startrow=<%=startrow+range%><%=jumpstr%>" class="css_002">下一页</a>]
                    <%}

                        if(totalpages>1){%>
                    &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                    <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
                    <%}%>
                </td>

                <td align="right" width="40%" colspan="3">
                    <input type="radio" name="radio" value="1" checked="">按书名
                    <input type="radio" name="radio" value="2">按用户名
                    <input type="text" name="searchname" >
                    <input type="submit" value="查询">
                </td>
            </tr>
        </table>


       </form>
  </body>  
</html>