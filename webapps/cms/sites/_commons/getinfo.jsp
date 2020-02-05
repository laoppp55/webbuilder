<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    int id = ParamUtil.getIntParameter(request,"id",0);
    IWordManager wMgr = LeaveWordPeer.getInstance();
    Word word = wMgr.getAWord(id);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<table border="1" width="100%">
<tr>
<td width="20%" align="left" bgcolor="#CCCCCC" class="black1">信件标题：</td>
<td width="80%" align="left" bgcolor="#CCCCCC"><p align="left"><%=word.getTitle()==null?"": StringUtil.gb2iso4View(word.getTitle() )%></p></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">发信公司：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getCompany()==null?"":StringUtil.gb2iso4View(word.getCompany())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">电子邮件：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getEmail()==null?"":StringUtil.gb2iso4View(word.getEmail())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">姓    名：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getLinkman()==null?"":StringUtil.gb2iso4View(word.getLinkman())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">联系地址：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getLinks()==null?"":StringUtil.gb2iso4View(word.getLinks())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">邮政编码：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getZip()==null?"":StringUtil.gb2iso4View(word.getZip())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">联系电话：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getPhone()==null?"":StringUtil.gb2iso4View(word.getPhone())%></td>
</tr>
<tr>
<td align="left" bgcolor="#CCCCCC" class="black1">信件内容：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getContent()==null?"":StringUtil.gb2iso4View(word.getContent())%></td>
</tr>
    <td align="left" bgcolor="#CCCCCC" class="black1">回复：</td>
<td align="left" bgcolor="#CCCCCC"><%=word.getRetcontent()==null?"":StringUtil.gb2iso4View(word.getRetcontent())%></td>
</tr>
<tr bgcolor="#CCCCCC"><td colspan="2" align="center"><input type="button" name="button1" value=" 关闭 " onclick="javascript:window.close();"></td></tr></table>
</body>
</html>