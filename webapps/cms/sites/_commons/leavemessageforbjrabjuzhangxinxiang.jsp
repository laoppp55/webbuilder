<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.markManager.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.FileOutputStream" %>
<%@page contentType="text/html;charset=GBK" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    int range = 5;
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int siteid = 40;
    int audit_n = 1;
    int formid_n = 265;
    IWordManager wMgr = LeaveWordPeer.getInstance();
    List list = new ArrayList();
    audit_n = 1;
    list = wMgr.getCurrentWord(siteid, formid_n, audit_n, startrow, range);
    int total_word = wMgr.getTotalWord(siteid, formid_n, audit_n);
    int totalpages = 0;
  int currentpage = 0;
    if (total_word < range) {
      totalpages = 1;
      currentpage = 1;
    } else {
      if (total_word % range == 0)
        totalpages = total_word / range;
      else
        totalpages = total_word / range + 1;

      currentpage = startrow / range + 1;
    }
%>
<html>
<head>
    <title>北京市无线电管理局</title>
    <script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
    <meta content="text/html; charset=gb2312" http-equiv="Content-Type"/>
    <style type="text/css">
        <!--
        .btnOFF {
            padding: 3px;
            border: 1px outset;
            cursor: pointer;
            background-color: #ffffff;
            width: 180px;
            height: 18px;
            text-align: left;
            font-size: 12px;
            text-valign: middle;
        }

        .btnON {
            padding: 3px;
            border: 1px inset;
            background-color: #ffffff;
            width: 180px;
            height: 18px;
            text-align: left;
            color: red;
            cursor: hand;
            font-size: 12px;
            text-valign: middle;
        }

        .ab1 {
            font-size: 12px;
            color: #666666;
            text-decoration: none;
        }

        -->
    </style>
    <style type="text/css">
        Select {
            width: 219px;
        }
    </style>
    <link rel="stylesheet" type="text/css" href="/css/css.css"/>
    <script language="JavaScript" type="text/JavaScript">
        <!--
        <!--
        function MM_preloadImages() { //v3.0
            var d = document;
            if (d.images) {
                if (!d.MM_p) d.MM_p = new Array();
                var i,j = d.MM_p.length,a = MM_preloadImages.arguments;
                for (i = 0; i < a.length; i++)
                    if (a[i].indexOf("#") != 0) {
                        d.MM_p[j] = new Image;
                        d.MM_p[j++].src = a[i];
                    }
            }
        }
        //-->
    </script>
    <script language='javascript'>
        function getinfo(id)
        {
            window.open("getinfo.jsp?id=" + id, "", 'width=400,height=600,left=200,top=200');
        }
    </script>
    <style type="text/css">
        <!--
        a:link {
            text-decoration: none;
            color: #256ECC;
        }

        a:visited {
            text-decoration: none;
            color: #256ECC;
        }

        a:hover {
            text-decoration: none;
        }

        a:active {
            text-decoration: none;
        }

        body {
            background-color: #FFFFFF;
        }

        .STYLE16 {
            color: #256ECC
        }

        .STYLE17 {
            color: #FFFFFF
        }

        --></style>
</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="960" align="center">
    <tbody>
    <tr>
        <td colspan="4"><%@include file="/www_bjrab_gov_cn/inc/head.shtml"%></td>
    </tr>
    <tr>
        <td valign="top" width="234"><%@include file="/www_bjrab_gov_cn/inc/zmhd_left.shtml"%></td>
        <td rowspan="5"><img alt="" width="6" height="1" src="/images/spxt.gif"/></td>
        <td valign="top" rowspan="5" width="720">
            <table border="0" cellspacing="0" cellpadding="0" width="720">
                <tbody>
                <tr>
                    <td colspan="3">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tbody>
                            <tr>
                                <td><img alt="" width="3" height="30" src="/images/ejym4.jpg"/></td>
                                <td background="/images/ejym5.jpg" width="719"><span class="STYLE17">　<A
                                        class="head_STYLE10" HREF=/index.shtml>首页</A>--<A class="head_STYLE10"
                                                                                          HREF=/zmhd/index.shtml>政民互动</A>--在线咨询</span>
                                </td>
                                <td><img alt="" width="4" height="30" src="/images/ejym6.jpg"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                <tr>

                    <td height="600" valign="top" width="100%" align="center">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%" height="30">
                            <tbody>
                            <tr>
                                <td align="left">
                                    <!--留言-->
                                    <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#0886DD">
                                        <tr>
                                            <td align="center" valign="top" bgcolor="#FFFFFF">
                                                <table width="690" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td>&nbsp;</td>
                                                    </tr>
                                                    
                                                </table>
                                                <table width="690" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="50" align="right" valign="bottom"><a href="/zmhd/zxzx/jzemail.shtml"><img
                                                                src="/images/20101026-1.jpg" width="78" height="25"
                                                                border="0" style=" margin-bottom:5px;"></a></td>
                                                    </tr>
                                                </table>
                                                <table width="690" border="0" cellpadding="0" cellspacing="1"
                                                       bgcolor="#E5E3E4">
                                                    <tr>
                                                        <td height="34" colspan="2" align="center" valign="bottom"
                                                            background="../images/20101026-2.jpg">
                                                            <table width="95%" height="22" border="0" cellpadding="0"
                                                                   cellspacing="0">
                                                                <tr>
                                                                    <td align="left" valign="top" class="title20101026">
                                                                        在线咨询
                                                                    </td>
                                                                    <td align="right" valign="top" class="a5">&nbsp;</td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td width="555" align="center" valign="top" bgcolor="#F5F5F5"
                                                            style="padding:10px 10px 10px 10px;"><strong>标题</strong>
                                                        </td>
                                                        <td width="132" align="center" valign="top" bgcolor="#F5F5F5"
                                                            style="padding:10px 10px 10px 10px;"><strong>时间</strong>
                                                        </td>
                                                    </tr>
                                                    <%
                                                        for (int i = 0; i < list.size(); i++) {
                                                            Word word = (Word) list.get(i);
                                                    %>
                                                    <tr>
                                                        <td align="left" valign="top" bgcolor="#FFFFFF"
                                                            style="padding:10px 10px 10px 10px;"><a href="#" onclick="javascript:getinfo(<%=word.getId()%>);"><%=word.getTitle() == null?"":StringUtil.gb2iso4View(word.getTitle())%></a> 
                                                        </td>
                                                        <td align="center" valign="top" bgcolor="#FFFFFF"
                                                            style="padding:10px 10px 10px 10px;"><%=word.getWritedate().toString().substring(0,10)%>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="left" valign="middle" bgcolor="#FAFAFA"
                                                            style="padding:10px 10px 10px 10px;">&nbsp;</td>
                                                        <td align="center" valign="middle" bgcolor="#FAFAFA"
                                                            style="padding:10px 10px 10px 10px;">&nbsp;</td>
                                                    </tr>
                                                    <%
                                                        }
                                                    %>
                                                    
                                                </table>
                                                <table width="690" border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td height="30" align="center" valign="bottom">共<%=totalpages%>页 <a href="leavemessageforbjrab.jsp?startrow=0" class="a2">首页</a>
                                                            | <%if ((startrow - range) >= 0) {%><a href="leavemessageforbjrab.jsp?startrow=<%=startrow-range%>" class="a2">上一页</a><%}else{%>上一页<%}%> |
                                                            <%if ((startrow + range) < total_word) {%><a href="leavemessageforbjrab.jsp?startrow=<%=startrow+range%>" class="a2">下一页</a><%}else{%>下一页<%}%>
                                                            | <a href="leavemessageforbjrab.jsp?startrow=<%=(totalpages -1)*range %>" class="a2">尾页</a></td>
                                                    </tr>
                                                </table>
                                                
                                            </td>
                                        </tr>
                                    </table>
                                    
                                </td>
                            </tr>

                            </tbody>
                        </table>
                        <table border="0" cellspacing="0" cellpadding="0" width="100%" height="30">
                            <tbody>
                            <tr>
                                <td align="center"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>

                </tr>

                </tbody>
            </table>

        </td>
    </tr>
    </tbody>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="960" align="center">
    <tbody>
    <tr>
        <td colspan="3"><%@include file="/www_bjrab_gov_cn/inc/tail.shtml"%></td>
    </tr>
    </tbody>
</table>
</body>
</html>