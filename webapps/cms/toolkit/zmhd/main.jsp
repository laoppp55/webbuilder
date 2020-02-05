<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    //System.out.println("==========="+siteid);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int status = ParamUtil.getIntParameter(request, "status", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int seltype = ParamUtil.getIntParameter(request, "seltype", 1);
    String querystr = ParamUtil.getParameter(request, "querystr");
    int qstr = ParamUtil.getIntParameter(request, "qstr",0);
    if (startrow < 0) {
        startrow = 0;
    }

    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    List list = new ArrayList();
    List currentlist = new ArrayList();

    int rows = 0;
    int totalpages = 0;
    int currentpage = 0;

    if(startflag == 1){
        String sqlstr = "";
        String sqlstr1 = "";
        String sqlstatus=" and statusflag="+status;

        if (seltype == 1) {
            sqlstr = "select * from tbl_letter  where title like '@" + querystr + "@'" + sqlstatus + "  order by createdate desc";
            sqlstr1 = "select count(id) from tbl_letter  where title like '@" + querystr + "@'" + sqlstatus;
        } else if (seltype == 2) {
            sqlstr = "select * from tbl_letter where searchmsg like '@" + querystr + "@'" + sqlstatus;
            sqlstr1 = "select count(id) from tbl_letter  where searchmsg like '@" + querystr + "@'" + sqlstatus;
        } else if (seltype == 3) {
            querystr=querystr.substring(2);
            System.out.println(querystr);
            sqlstr = "select * from tbl_letter where id =" + querystr;
            sqlstr1 = "select count(id) from tbl_letter  where id =" + querystr;
        }
        System.out.println(sqlstr);
        rows = wsbsMgr.getLetterCount(sqlstr1);
        currentlist = wsbsMgr.getCurrentQureyLetterList(sqlstr, startrow, range);
    }else{
        rows = wsbsMgr.getLetterCount(status);
        currentlist = wsbsMgr.getCurrentLetterList(startrow, range,status);
    }

    if (rows < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (rows % range == 0)
            totalpages = rows / range;
        else
            totalpages = rows / range + 1;

        currentpage = startrow / range + 1;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>政民互动信件管理</title>
    <style type="text/css">
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            font-size:14px;
        }
        a{ text-decoration:none; color:#000;}
        a:hover{ color:#03F;}

        .txtsel{ height:25px;}
        .txtinp{ height:21px;}
        .button_cx{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#db4b03; border:0px;}
        .button_blue{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#619cd9; border:0px;}
        .button_grey{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#666666; border:0px;}
    </style>
    <SCRIPT language=javascript>
        function DelLetter(id)
        {
            var bln = confirm("真的要删除吗？");
            if (bln)
            {
                window.location = "delete.jsp?id=" + id;
            }
        }

        function searchcheck() {
            if(form1.seltype.value != 3){
                if ((form1.querystr.value == null) || (form1.querystr.value == "")) {
                    alert("请输入要查询的内容！");
                    return false;
                }
            }
            form1.submit();
            return true;
        }
        function t(obj)
        {
            if(obj.value=="3"){
                document.getElementById("a").style.display = "";
                document.getElementById("b").style.display = "none";
            }else{
                document.getElementById("a").style.display = "none";
                document.getElementById("b").style.display = "";
            }
        }
        function CA()
        {
            for (var i = 0; i < document.form2.elements.length; i++)
            {
                var e = document.form2.elements[i];
                if (e.name != 'allbox' && e.type == 'checkbox')
                {
                    e.checked = document.form2.allbox.checked;
                }
            }
        }
        function tostatus(flag){
            window.location="main.jsp?status="+flag;
        }

    </SCRIPT>

</head>

<body>
<FORM name=form1 action=main.jsp method=post>
    <INPUT type=hidden value=1 name=startflag>
   <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="top">
            <FORM name=form1 action=main.jsp method=post>
                <INPUT type=hidden value=1 name=startflag>
                <INPUT type=hidden value=<%=status%> name=status>
            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="130"  height="60">检索：<select name="seltype"  onchange="t(this)">
                    <option value="1">标题</option>
                    <option value="2">查询码</option>
                    <option value="3">信件编码</option>
                </select></td>
                <td width="253">
                    <input name="querystr" type="text" id="textfield" size="30"   class="txtinp"/></td>
                <td width="549" ><input type="button" name="button"  value="查询"  class="button_cx"  onclick="javascript:return searchcheck();"/></td>
                <td width="68"><input type="button" name="button" value="返回"  class="button_grey" onclick="history.go(-1)"/></td>
            </tr>
        </table>
       </FORM>
        <FORM name=form2 action=doCreateWord.jsp method=post>
            <table width="100%"  align="center" cellpadding="0" cellspacing="1" bgcolor="#999999">
                <tr>
                    <td width="81" height="50" align="center" bgcolor="#e0f0fe"><input type="submit" name="button2" id="button2" value="批量导出" class="button_blue" /></td>
                    <td colspan="6" align="right" bgcolor="#e0f0fe">
                        <input type="button" name="button3"  value="待处理" class="button_blue" onclick="tostatus(0);" />&nbsp;&nbsp;
                        <input type="button" name="button3"  value="已经处理" class="button_blue"  onclick="tostatus(1);"/>&nbsp;&nbsp;
                        <input type="button" name="button3"  value="垃圾箱" class="button_blue" onclick="tostatus(-1);"/>&nbsp;&nbsp;
                        <input type="button" name="button3"  value="历史信件" class="button_blue" onclick="tostatus(2);"/>&nbsp;&nbsp;</td>
                </tr>
                <tr>
                    <td width="81" align="center" bgcolor="#cae4ff">
                        <input type="checkbox"name=allbox value="CheckAll" onClick="CA();" />全选</td>
                    <td width="80" height="30" align="center" bgcolor="#cae4ff"><strong>信件编码</strong></td>
                    <td width="461" align="center" bgcolor="#cae4ff"><strong>标题</strong></td>
                    <td width="146" align="center" bgcolor="#cae4ff"><strong>提交时间</strong></td>
                    <td width="79" align="center" bgcolor="#cae4ff"><strong>受理状态</strong></td>
                    <td width="79" align="center" bgcolor="#cae4ff"><strong>浏览操作</strong></td>
                    <td width="64" align="center" bgcolor="#cae4ff"><strong>删除</strong></td>
                </tr>
                <%
                    if (currentlist != null) {
                        for (int i = 0; i < currentlist.size(); i++) {
                            Letter letter = (Letter) currentlist.get(i);
                            int statusFlag = letter.getStatusflag().intValue();
                            String statusstr="待处理";
                            if(statusFlag == -1){
                                statusstr = "垃圾邮件";
                            }
                            if(statusFlag == 1){
                                statusstr = "已经处理";
                            }
                            if(statusFlag == 2){
                                statusstr = "历史信件";
                            }

                %>

                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkids" id="checkids" value="<%=letter.getId()%>"/></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm<%=letter.getId()%></td>
                    <td align="center" bgcolor="#e0f0fe"><%=letter.getTitle()==null?"--":letter.getTitle()%></td>
                    <td align="center" bgcolor="#e0f0fe"><%=letter.getCreatedate()==null?"--":letter.getCreatedate().toString().substring(0,19)%></td>
                    <td align="center" bgcolor="#e0f0fe"><%=statusstr%></td>
                    <td align="center" bgcolor="#e0f0fe"><a target="_blank" href="view.jsp?id=<%=letter.getId()%>"><img src="images/gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#" onclick="javascript:return DelLetter(<%=letter.getId()%>);"><img src="images/gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>

                <%
                        }
                    }
                %>

               </FORM>
                <tr>
                    <TD height="50" colspan="7" align="center" bgcolor="#e0f0fe">共<%=totalpages%>页&nbsp;&nbsp; 共<%=rows%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                        <%
                            if ((startrow - range) >= 0) {
                        %>
                        <a href="main.jsp?startrow=0&startflag=<%=startflag%>&seltype=<%=seltype%>&querystr=<%=querystr%>&qstr=<%=qstr%>&status=<%=status%>">第一页</a>
                        <%}%>
                        <%if ((startrow - range) >= 0) {%>
                        <a href="main.jsp?startrow=<%=startrow-range%>&startflag=<%=startflag%>&seltype=<%=seltype%>&querystr=<%=querystr%>&qstr=<%=qstr%>&status=<%=status%>">上一页</a>
                        <%}%>
                        <%if ((startrow + range) < rows) {%>
                        <A href="main.jsp?startrow=<%=startrow+range%>&startflag=<%=startflag%>&seltype=<%=seltype%>&querystr=<%=querystr%>&qstr=<%=qstr%>&status=<%=status%>">下一页</A>
                        <%}%>
                        <%if (currentpage != totalpages) {%>
                        <A href="main.jsp?startrow=<%=(totalpages-1)*range%>&startflag=<%=startflag%>&seltype=<%=seltype%>&querystr=<%=querystr%>&qstr=<%=qstr%>&status=<%=status%>">最后一页</A>
                        <%}%>
                    </TD>

                </tr>
            </table></td>
    </tr>
</table>
</body>
</html>
