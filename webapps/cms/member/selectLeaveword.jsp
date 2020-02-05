<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.markManager.*" %>
<%@ page import="java.util.ArrayList" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int roletype = ParamUtil.getIntParameter(request,"type",0);
    String userid = ParamUtil.getParameter(request, "userid");

    //获取留言板表单的标记定义
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    IMarkManager markMgr = markPeer.getInstance();
    List words = new ArrayList();
    List marks = new ArrayList();
    try {
        words = wordMgr.getWordAuthorizedUser(userid,siteid);
        marks = markMgr.getMarksByType(siteid,21);
    } catch (Exception exp) {
        exp.printStackTrace();
    }
%>
<html>
<head>
    <title>用户留言管理</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <meta http-equiv="Pragma" content="no-cache">
    <style type="text/css">
        TABLE {FONT-SIZE: 12px;word-break:break-all}
        BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
        A:link {text-decoration:none;line-height:20px;}
        A:visited {text-decoration:none;line-height:20px;}
        A:active {text-decoration:none;line-height:20px; font-weight:bold;}
        A:hover {text-decoration:none;line-height:20px;}
    </style>
    <script language="javascript">
        function sellw(){
            var roletype = form.roletype.value;
            var num = form.lwnum.value;
            var i = 0;
            var retvalue="";
            for(i=0; i<num; i++) {
                if (form.n[i].checked) retvalue = retvalue + form.n[i].value + "||";
            }

            retvalue = retvalue.substring(0,retvalue.length-2);
            if (retvalue != "") {
                if (roletype == 0) {                                        //留言板----管理员
                    window.opener.userForm.lw.value = retvalue;
                } else {                                                   //留言板----部门管理员
                    window.opener.userForm.deptlw.value = retvalue;
                }
            }
            window.close();
        }
    </script>
</head>
<body>
<center>
    <form method="post" name="form">
        <input type="hidden" name="roletype" value="<%=roletype%>">
        <input type="hidden" name="doit" value="true">
        <input type="hidden" name="lwnum" value="<%=marks.size()%>">
        <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td align="center" width="100%">请选择用户管理的留言板</td>
                                    </tr>
                                    <%
                                        for(int i = 0; i < marks.size();i++){
                                            mark mk = (mark)marks.get(i);
                                            mk = markMgr.getAMark(mk.getID());
                                            boolean qq = false;
                                            for (int j=0; j<words.size(); j++) {
                                                authorizedform fl = (authorizedform)words.get(j);
                                                if (fl.getLwid() == mk.getID()) qq = true;
                                            }
                                    %>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td align="center" width="100%"><input type="checkbox" name="n" value="<%=mk.getID()%>" <%=(qq==true)?"checked":""%>><%=mk.getChineseName()%> </td>
                                    </tr>
                                    <%}%>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td align="center" width="100%"><input type="button" name="ok" value="确定" onclick="javascript:sellw()">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" name="cancel" value="返回" onclick="javascript:window.close();"></td>
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