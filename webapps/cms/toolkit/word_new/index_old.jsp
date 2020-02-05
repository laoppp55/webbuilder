<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    // int siteid = 1411;


    //获取留言板表单的标记定义
    IMarkManager markMgr = markPeer.getInstance();
    List marks = markMgr.getMarksByType(siteid,21);
%>
<html>
<head>
    <title>用户留言管理</title>
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
        function golist(r){
            window.location = "index.jsp?startrow="+r;
        }




        function del(id){
            var val;
            val = confirm("你确定要删除吗？");
            if(val == 1){
                window.location = "delete.jsp?startflag=1"+"&id="+id;
            }
        }
        function ret(id){
            window.location = "retcontent.jsp?startflag=0" + "&id=" + id;
        }



        function upflag(id,flag){
            var flags;
            if(flag == 0){
                flags = 1;
            }
            if(flag == 1){
                flags = 0;
            }
            var val;
            val = confirm("你确定更改显示方式？");
            if(val == 1){
                window.location = "flag.jsp?startflag=1"+"&flag="+flags+"&id="+id;
            }
        }

        function edit(id){
            window.location = "outlinecard.jsp?id="+id;
        }
        function add(id){
            window.open("add.jsp?id="+id,"","height=500,width=800");
        }
        function tj(id){
            window.open("tj.jsp?id="+id,"","height=500,width=800");
        }
    </script>
</head>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
    <form action="index.jsp" method="post" name="form">
        <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">用户留言管理</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        
                                        <td align="center" width="30%">留言表单名称</td>


                                    </tr>
                                    <%
                                        for(int i = 0; i < marks.size();i++){
                                            mark mk = (mark)marks.get(i);
                                                                                                                 
                                    %>
                                    <tr  bgcolor="#FFFFFF" class="css_001">

                                          <td align="center" width="30%"><a href="index1.jsp?markid=<%=mk.getID()%>" target="_blank"><%=mk.getChineseName()%></a> </td>

                                    </tr>
                                    <%}%>
                                    <tr  bgcolor="#FFFFFF" class="css_001">

                                          <td align="center" width="30%"><a href="index1.jsp?markid=0">原有留言</a> </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>

    </form>
</center>
</body>
</html>