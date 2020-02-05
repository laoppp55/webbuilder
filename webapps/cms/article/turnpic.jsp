<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.Turnpic" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IArticleManager" %>
<%@ page import="com.bizwink.cms.news.ArticlePeer" %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    List list = (List)session.getAttribute("turn_pic");
    int articleid = ParamUtil.getIntParameter(request,"id",0);
    IArticleManager aMgr = ArticlePeer.getInstance();
    List olist = aMgr.getArticleTurnPic(articleid);
    int columnID = ParamUtil.getIntParameter(request,"column",0);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <script language="JavaScript" src="include/setday.js" ></script>
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
</head>
<script type="text/javascript">
    function upload_turn_pic(id){
        window.open("../upload/upload_turn_pic.jsp?column=<%=columnID%>&article="+id, "", 'width=400,height=400,left=200,top=200');
    }
    function update(articleid,columnid,i,notes){
        window.location = "update_turn_pic.jsp?articleid="+articleid+"&column="+columnid+"&sortid="+i+"&notes="+notes;
    }
</script>
<body>
<input type="hidden" name="updateflag" value="1">
<center>
    <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">文章原有图片</td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <tr  bgcolor="#FFFFFF" class="css_001">
                                    <td width="30%" align="center" bgcolor="#FFFFFF">图片路径</td>
                                    <td align="center" width="50%">图片描述</td>
                                    <td align="center" width="10%">删除</td>
                                    <td align="center" width="10%">修改</td>
                                </tr>
                                <%
                                    if(olist != null){
                                        for(int i = 0; i < olist.size();i++){
                                            Turnpic tpic = (Turnpic)olist.get(i);
                                %>
                                <tr  bgcolor="#FFFFFF" class="css_001">
                                    <td width="30%" align="center" bgcolor="#FFFFFF"><%=tpic.getPicname()==null?"":tpic.getPicname()%></td>
                                    <td align="center" width="50%"><%=tpic.getNotes()==null?"": StringUtil.gb2iso4View(tpic.getNotes())%></td>
                                    <td align="center" width="10%"><a href="deletePic.jsp?startflag=1&id=<%=tpic.getId()%>&articleid=<%=articleid%>&column=<%=columnID%>">删除</a></td>
                                    <td align="center" width="10%"><a href="javascript:upload_turn_pic(<%=tpic.getId()%>);">修改</a></td>
                                </tr>
                                <%}}%>
                            </table>

                        </td>
                    </tr>

                </table>
            </td>
        </tr>
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#F4F4F4" align="center">
                        <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">已上传图片</td>
                    </tr>
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <tr  bgcolor="#FFFFFF" class="css_001">
                                    <td width="30%" align="center" bgcolor="#FFFFFF">图片路径</td>
                                    <td align="center" width="50%">图片描述</td>
                                    <td align="center" width="10%">删除</td>
                                    <td align="center" width="10%">修改</td>
                                </tr>
                                <%
                                    if(list != null){
                                        for(int i = 0; i < list.size();i++){
                                            Turnpic tpic = (Turnpic)list.get(i);
                                %>
                                <tr  bgcolor="#FFFFFF" class="css_001">
                                    <td width="30%" align="center" bgcolor="#FFFFFF"><%=tpic.getPicname()==null?"":tpic.getPicname()%></td>
                                    <td align="center" width="50%"><input name="pic<%=i%>" type="text" value="<%=tpic.getNotes()==null?"": StringUtil.gb2iso4View(tpic.getNotes())%>"></td>
                                    <td align="center" width="10%"><a href="deletePic.jsp?url=<%=tpic.getPicname()%>&articleid=<%=articleid%>&column=<%=columnID%>">删除</a></td>
                                    <td align="center" width="10%"><input type="button" name="Submit3" value="修改"
                                                                          onclick='javascript:update(<%=articleid%>,<%=columnID%>,<%=i%>,document.all("pic<%=i%>").value);'/></td>
                                </tr>
                                <%}}%>
                            </table>

                        </td>
                    </tr>

                </table>
            </td>
        </tr>
        <tr>
            <td align="center">
                <input type="button" name="button" value=" 关闭 " onclick="javascript:window.close();">
            </td>
        </tr>
    </table>

</center>
</body>
</html>