<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.toolkit.gwcase.*" %>
<%@ page import="java.io.*" %>
<%@page contentType="text/html;charset=GBK" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 5);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
    IGWcaseManager GWMgr = GwcasePeer.getInstance();
    List list = new ArrayList();
    int rows = 0;

    String jumpstr = "";
    if(startflag != 1){
        list = GWMgr.getgwUploadFiles(startrow,range);
    }else{
        int radio = ParamUtil.getIntParameter(request,"radio",0);
        String searchname = ParamUtil.getParameter(request,"searchname");
        jumpstr = "&startflag=" + startflag + "&searchname=" + searchname + "&radio=" + radio;
        if(radio == 1){
            String sqlsearch = "select * from gw_case_info where operater like '@" + searchname + "@'";
            String sqlsearchcount = "select count(*) from gw_case_info where operater like '@" +searchname +"@'";
            rows = GWMgr.getGwCount(sqlsearchcount);
            list = GWMgr.getgw(startrow,range,sqlsearch);

        }else if(radio == 2){
            String sqlsearch = "select * from gw_case_info where telephone like '@" + searchname + "@'";
            String sqlsearchcount = "select count(*) from gw_case_info where telephone like '@" + searchname + "@'";
            rows = GWMgr.getGwCount(sqlsearchcount);
            list = GWMgr.getgw(startrow,range,sqlsearch);
        } else{
            String sqlsearch = "select * from gw_case_info where applicant like '@" + searchname + "@'";
            String sqlsearchcount = "select count(*) from gw_case_info where applicant like '@" + searchname + "@'";
            rows = GWMgr.getGwCount(sqlsearchcount);
            list = GWMgr.getgw(startrow,range,sqlsearch);
        }
    }


    int gwCount = list.size();
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
    <title>无线电管理</title>
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
        function CA(){
            for (var i = 0; i < document.form1.elements.length; i++)
            {
                var e = document.form1.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox')
                {
                    e.checked = document.form1.allbox.checked;
                }
            }
        }

        function checksearch(){
            var search  = document.form2.searchname.value;
            if( search == "" ){
                alert("请输入搜索条件");
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form action="doGwpublish.jsp" method="post" name="form1">
    <input type=hidden name=startrow value="<%=startrow%>">
    <input type=hidden name=range value="<%=range%>">
    <input type="hidden" name="startflag" value="1">
    <table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
        <tr>
            <td>
                <table width="100%" border="0" cellpadding="0">
                    <tr bgcolor="#d4d4d4" align="right">
                        <td>
                            <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                <tr  bgcolor="#FFFFFF" class="css_001">
                                    <td align="center" width="20%">说明</td>
                                    <td width="20%" align="center">文件名称</td>
                                    <td align="center" width="20%">上传文件时间</td>
                                    <td align="center" width="20%">修改时间</td>
                                    <td align="center" width="10%">文件上传人</td>
                                    <td align="center" width="10%">是否成功转入</td>
                                </tr>

                                <%
                                    String filename = "";
                                    int posi =0;
                                    for(int i=0;i< list.size(); i++){
                                        uploadfile ufile = (uploadfile)list.get(i);
                                        filename = ufile.getFilename();
                                        posi = filename.lastIndexOf(java.io.File.separator);
                                        filename = filename.substring(posi+1);
                                        String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
                                        out.println("<tr bgcolor=" + bgcolor + " class=itm onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "';\" height=25>");
                                %>

                                <td align="center" width="20%">&nbsp;</td>
                                <td align="center" width="20%"><%=filename%></td>
                                <td width="20%" align="center"><%=ufile.getCreatedate()==null?"-":ufile.getCreatedate().toString()%></td>
                                <td align="center" width="20%"><%=ufile.getLastupdate()==null?"-":ufile.getLastupdate().toString()%></td>
                                <td align="center" width="10%"><%=ufile.getUsername()==null?"-":ufile.getUsername()%></td>
                                <td align="center" width="10%"><%=ufile.getTflag()==0?"成功":"未成功"%></td></tr>
                                <%}%>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</form>

<form action="uploadlist.jsp" method="post" name="form2">
    <table width="70%" class="css_002">
        <tr valign="bottom" width=100%>
            <td width="30%" align="center" colspan="2">
                总<%=totalpages%>页&nbsp; 第<%=currentpage%>页&nbsp;  总<%=rows%>条&nbsp;   每页显示<%=range%>条
            </td>

            <td class="css_002" width="30%" align="center" colspan="2" >
                <%
                    if((startrow-range)>=0){
                %>
                [<a href="uploadlist.jsp?startrow=<%=startrow-range%><%=jumpstr%>" class="css_002">上一页</a>]
                <%}
                    if((startrow+range)<rows){
                %>
                [<a href="uploadlist.jsp?startrow=<%=startrow+range%><%=jumpstr%>" class="css_002">下一页</a>]
                <%}

                    if(totalpages>1){%>
                &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,'<%=jumpstr%>');">GO</a>
                <%}%>
            </td>

            <td align="right" width="40%" colspan="3">
                <input type="hidden" name="startflag" value="1">
                <select name="radio">
                    <option value="1">申报经办人姓名</option>
                    <option value="2">申报经办人联系电话</option>
                    <option value="3">行政相对人名称(单位名称)</option>
                </select>

                <input type="text" name="searchname" >
                <input type="submit" value="查询" onclick="return checksearch();">
            </td>
        </tr>
    </table>
</form>

</body>
</html>