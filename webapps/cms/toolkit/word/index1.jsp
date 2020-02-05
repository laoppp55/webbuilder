<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    String userid = authToken.getUserID();
    // int siteid = 1411;
    int updateflag = ParamUtil.getIntParameter(request,"updateflag",0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    int markid = ParamUtil.getIntParameter(request,"markid",0);

    /*if(updateflag == 1)
    {
        String[] ids = request.getParameterValues("ids");
        int departmentid = ParamUtil.getIntParameter(request,"department",0);
        for (int i = 0; i < ids.length; i++) {
            wordMgr.updateDepartmentForLeaveWord(Integer.parseInt(ids[i]),departmentid);
        }
    }*/

    IUserManager uMgr = UserPeer.getInstance();
    User user = new User();
    user = uMgr.getUser(authToken.getUserID(), siteid);
    Department dept = null;
    if (user.getDepartment() != null) {
        dept = uMgr.getOneDepartmentInfoById(Integer.parseInt(user.getDepartment()));
    }
    int departments = 0;
    if (dept != null) {
        departments = dept.getId();
    }
    List list = new ArrayList();
    List rowsList = new ArrayList();
    int rows = 0;
    String sqlstr = "";
    if(departments == 0){
        sqlstr = "select * from tbl_leaveword where siteid = "+siteid+" and formid = "+markid+" order by writedate desc";
    }else{
        sqlstr = "select * from tbl_leaveword where siteid = "+siteid+" and formid = "+markid+" and departmentid = "+departments+" order by writedate desc";
    }

    list = wordMgr.getCurrentWord(sqlstr,startrow,range);
    rowsList = wordMgr.getAllWord(sqlstr);

    rows = rowsList.size();
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
    <title>用户留言</title>
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
        function golist(r,markid){
            window.location = "index1.jsp?startrow="+r+"&markid="+markid;
        }
        function CheckAll(form) {
            for (var i = 0; i < form.elements.length; i++) {
                var e = form.elements[i];
                if (e.name != 'chkAll')
                    e.checked = form.chkAll.checked;
            }
        }

        function del(id,markid){
            var val;
            val = confirm("你确定要删除吗？");
            if(val == 1){
                window.location = "delete.jsp?startflag=1"+"&id="+id+"&markid="+markid;
            }
        }

        function ret(id,markid){
            window.location = "retcontent.jsp?startflag=0" + "&id=" + id+"&markid="+markid;
        }

        function audit(id,markid){
            var str = "auditLeaveword.jsp?act=add&startflag=0&id=" + id + "&markid=" + markid;
            window.open(str,"auditlw","height=500,width=800");
        }

        function check(form) {
            var flag = false;
            for (var i = 0; i < form.elements.length; i++) {
                if (form.elements[i].checked) {
                    flag = true;
                }
            }
            if (!flag) {
                alert("请选择留言！");
                return false;
            } else {
                var val;

                val = confirm("为选中留言分配部门？");
                if (val) {
                    form.action = "index1.jsp?updateflag=1&markid=<%=markid%>";
                    form.submit();
                }

            }
        }


        function upflag(id,flag,markid){
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
                window.location = "flag.jsp?startflag=1"+"&flag="+flags+"&id="+id+"&markid="+markid;
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

        var req;
        function getXMLHTTPObj()
        {
            var C = null;
            try{
                C = new ActiveXObject("Msxml2.XMLHTTP");
            } catch(e) {
                try {
                    C = new ActiveXObject("Microsoft.XMLHTTP");
                } catch(sc) {
                    C = null;
                }
            }
            if( !C && typeof XMLHttpRequest != "undefined" )
            {
                C = new XMLHttpRequest();
            }
            return C;
        }

        function onReadyStateChange(){
            var ready=req.readyState;
            var data=null;
            if (ready ==4) {
                data = req.responseText;
                var userArray = data.split(";");
                var i = 0;
                document.getElementById("yuangongid").options.length = 0;
                document.getElementById("yuangongid").options.add(new Option("请选择","0"));
                for (i=0; i<userArray.length; i++) {
                    posi = userArray[i].indexOf(":");
                    key = userArray[i].substring(0,posi);
                    value = userArray[i].substring(posi + 1);
                    document.getElementById("yuangongid").options.add(new Option(key,value));
                }
            }
        }

        function getEmployee(){
            req = getXMLHTTPObj();
            if (req) {
                req.onreadystatechange=onReadyStateChange;
                req.Open("post","getEmployeeByDept.jsp?dept="+form.department.value,true);
                req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                req.send(null);
            }
        }

    </script>
</head>
<body>
<center>
    <form action="index1.jsp" method="post" name="form">
        <table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0">
                        <tr bgcolor="#F4F4F4" align="center">
                            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">用户留言</td>
                        </tr>
                        <tr bgcolor="#d4d4d4" align="right">
                            <td>
                                <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td width="5%" align="center">&nbsp;</td>
                                        <td align="center" width="5%">部门</td>
                                        <td width="10%" align="center">用户名</td>
                                        <td align="center" width="10%">标题</td>
                                        <td align="center" width="23%">内容</td>
                                        <td align="center" width="5%">联系人</td>
                                        <td align="center" width="5%">邮编</td>
                                        <td align="center" width="5%">电子邮件</td>
                                        <td align="center" width="5%">电话</td>
                                        <td align="center" width="7%">留言时间</td>
                                        <td align="center" width="5%">是否显示</td>
                                        <td align="center" width="5%">删除</td>
                                        <td align="center" width="5%">回复</td>
                                        <td align="center" width="5%">审核</td>

                                    </tr>
                                    <%
                                        for(int i = 0; i < list.size();i++){
                                            Word word = (Word)list.get(i);
                                            Department d = null;
                                            d = uMgr.getOneDepartmentInfoById(word.getDepartmentid());
                                            String dname = null;
                                            if(d != null ){
                                                dname = d.getCname();
                                            }
                                    %>
                                    <tr  bgcolor="#FFFFFF" class="css_001">
                                        <td align="left" width="5%"><%if(departments == 0){%><input type="checkbox" name="ids" value="<%=word.getId()%>"><%}else{%>&nbsp;<%}%> </td>
                                        <td align="left" width="5%"><%=dname == null?"--":StringUtil.gb2iso4View(dname)%></td>
                                        <td align="left" width="10%"><%=word.getUserid()==null?"--": StringUtil.gb2iso4View(word.getUserid())%></td>
                                        <td align="left" width="10%"><%=word.getTitle()==null?"--":StringUtil.gb2iso4View(word.getTitle())%></td>
                                        <td align="left" width="23%"><%=word.getContent()==null?"--":StringUtil.gb2iso4View(word.getContent())%></td>
                                        <td align="left" width="5%"><%=word.getLinkman()==null?"--":StringUtil.gb2iso4View(word.getLinkman())%></td>
                                        <td align="left" width="5%"><%=word.getZip()==null?"--":StringUtil.gb2iso4View(word.getZip())%></td>
                                        <td align="left" width="5%"><%=word.getEmail()==null?"--":StringUtil.gb2iso4View(word.getEmail())%></td>
                                        <td align="left" width="5%"><%=word.getPhone()==null?"--":StringUtil.gb2iso4View(word.getPhone())%></td>
                                        <td align="left" width="7%"><%=word.getWritedate().toString().substring(0,10)%></td>
                                        <td align="left" width="5%"><a href="javascript:upflag(<%=word.getId()%>,<%=word.getFlag()%>,<%=markid%>)"><%if(word.getFlag()==0){%>不显示<%}else{%>显示<%}%></a></td>
                                        <td align="left" width="5%"><a href="javascript:del(<%=word.getId()%>,<%=markid%>)">删除</a></td>
                                        <td align="left" width="5%"><a href="javascript:ret(<%=word.getId()%>,<%=markid%>)"><%if(word.getRetcontent()==null || word.getRetcontent().trim().equals("")){%>回复<%}else{%>查看回复<%}%></a></td>
                                        <% if (word.getAuditflag() == 1 && word.getRetcontent()!=null && word.getAuditor().indexOf(userid)>-1) {%>
                                        <td align="left" width="5%"><a href="javascript:audit(<%=word.getId()%>,<%=markid%>)">审核</a></td>
                                        <%} else {%>
                                        <td align="left" width="5%">&nbsp;</td>
                                        <%}%>
                                    </tr>
                                    <%}%>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <table width="70%" class="css_002">
            <tr valign="bottom" width=100%>
                <td>
                    总<%=totalpages%>页&nbsp; 第<%=currentpage%>页
                </td>
                <td>
                </td>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td class="css_002">
                    <%
                        if((startrow-range)>=0){
                    %>
                    [<a href="index1.jsp?startrow=<%=startrow-range%>&markid=<%=markid%>" class="css_002">上一页</a>]
                    <%}
                        if((startrow+range)<rows){
                    %>
                    [<a href="index1.jsp?startrow=<%=startrow+range%>&markid=<%=markid%>" class="css_002">下一页</a>]
                    <%}

                        if(totalpages>1){%>
                    &nbsp;&nbsp;第<input type="text" name="jump" value="<%=currentpage%>" size="3">页&nbsp;
                    <a href="#" class="css_002" onclick="golist((document.all('jump').value-1) * <%=range%>,<%=markid%>);">GO</a>
                    <%}%>
                </td>
                <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
                <td align="right">&nbsp;&nbsp;&nbsp;&nbsp;</td>
            </tr>
            <%if(departments == 0){%>
            <tr valign="bottom" width=100%>
                <td colspan="6">
                    <input type="checkbox" name="chkAll" value="on" onclick="javascript:CheckAll(this.form);">全部选中&nbsp;&nbsp;&nbsp;&nbsp;分配部门：
                    <select name="department" id="department" onchange="javascript:getEmployee()">
                        <option value ="0">请选择</option>
                        <%
                            List departmentList = uMgr.getDepartments(siteid);
                            for(int i = 0; i < departmentList.size();i++){

                                Department depts = (Department)departmentList.get(i);
                                if(depts!=null){
                        %>

                        <option value="<%=depts.getId()%>"><%=depts.getCname()==null?"":StringUtil.gb2iso4View(depts.getCname())%></option>
                        <%}}%>
                    </select>&nbsp;&nbsp;&nbsp;&nbsp;
                    <select name="yuangong" id="yuangongid">
                        <option value ="0">请选择</option>
                    </select>
                    <input type="button" name="button" value="提交" onclick="javascript:return check(this.form);">
                </td>
            </tr>
            <%}%>
        </table>
    </form>
    <table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
    </table>
</center>
</body>
</html>