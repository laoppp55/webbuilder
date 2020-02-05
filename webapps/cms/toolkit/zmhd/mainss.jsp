<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%@ page import="java.sql.Timestamp" %>
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
            sqlstr = "select * from tbl_letter  where title like '@" + querystr + "@'" + sqlstatus + "order by createdate desc";
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
<HTML>
<HEAD><TITLE>政民互动信件管理</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="images/common.css" type=text/css rel=stylesheet>
    <LINK href="images/forum.css" type=text/css rel=stylesheet>
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
        function createRAR(){

        }
    </SCRIPT>
</HEAD>
<BODY>
<P align=right><FONT color=#0000ff>
    <A href="main.jsp?status=0">未受理</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="main.jsp?status=1">完结</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <A href="main.jsp?status=-1">垃圾箱</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <%--<A href="main.jsp">查看全部</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;--%>
    <A href="../main.jsp">返回</A>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </FONT> <BR></P>

<FORM name=form2 action=doCreateWord.jsp method=post>
    <p align=center>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD align=middle width="4%"><input type=checkbox name=allbox value="CheckAll" onClick="CA();">选中</TD>
            <TD align=middle width="4%">信件编码</TD>
            <TD align=middle width="5%">标题</TD>
            <TD align=middle width="5%">提交时间</TD>
            <TD align=middle width="5%">受理状态</TD>
            <TD align=middle width="4%">浏览操作</TD>
            <TD align=middle width="4%">删除</TD>

        </TR>
        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    Letter letter = (Letter) currentlist.get(i);
                    int statusFlag = letter.getStatusflag().intValue();
                    String statusstr="未受理";
                    if(statusFlag == -1){
                        statusstr = "垃圾邮件";
                    }
                    if(statusFlag == 1){
                        statusstr = "完结";
                    }

        %>
        <TR height=35>
            <TD align=middle width="4%"><input type="checkbox" name="checkids" value="<%=letter.getId()%>">
            </TD>
            <TD align=middle width="4%">zm<%=letter.getId()%>
            </TD>
            <TD align=middle width="5%"><%=letter.getTitle()==null?"--":letter.getTitle()%>
            </TD>
            <TD align=middle width="10%"><%=letter.getCreatedate()==null?"--":letter.getCreatedate().toString().substring(0,19)%>
            </TD>
            <TD align=middle width="10%"><%=statusstr%>
            </TD>
            <TD align=middle width="4%"><A
                    target="_blank" href="view.jsp?id=<%=letter.getId()%>"><IMG
                    src="images/preview.gif" border=0></A></TD>

            <TD align=middle width="4%">
                <A href="#" onclick="javascript:return DelLetter(<%=letter.getId()%>);">
                    <IMG src="images/del.gif" border=0></A></TD>

        </TR>
        <%
                }
            }
        %>
        <input type=submit value=" 批量导出 "/>
        </TBODY>
    </TABLE></p>
    <BR>
</form>
    <p align=center>
    <TABLE>
        <TBODY>
        <TR>
            <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=rows%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
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
            <TD>&nbsp;</TD>
        </TR>
        </TBODY>
    </TABLE></p>
    <BR><BR>

    <FORM name=form1 action=main.jsp method=post>
        <INPUT type=hidden value=1 name=startflag>
    <p align=center>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR height=35>
            <TD>&nbsp;&nbsp;检索：<select name="seltype"  onchange="t(this)">
                <option value="1">标题</option>
                <option value="2">查询码</option>
                <option value="3">信件编码</option>
            </select>
               <%-- <select name="qstr" id="a" style="display:none">
                    <%
                        if(deptlist != null){
                            for(int i = 0; i < deptlist.size(); i++){
                                catg = (CatgEntity)deptlist.get(i);
                    %>
                    <option value="<%=catg.getId()%>"><%=catg.getName()%></option>
                    <%}}%>
                </select>--%>
                <input type="text" id="b" size=40 name="querystr">&nbsp;&nbsp;<input type="button" value=" 查 询 "
                                                                                     onclick="javascript:return searchcheck();">

            </TD>
        </tr>
        </tbody>
    </table></p>

</FORM>
</BODY>
</HTML>
