<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.webapps.leaveword.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="java.text.*" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String userid = authToken.getUserID();
    int updateflag = ParamUtil.getIntParameter(request,"updateflag",0);
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 100);
    IWordManager wordMgr = LeaveWordPeer.getInstance();
    int markid = ParamUtil.getIntParameter(request,"markid",0);

    if(updateflag == 1)
    {
        String processor = "";
        String[] ids = request.getParameterValues("ids");
        int departmentid = ParamUtil.getIntParameter(request,"department",0);
        processor = ParamUtil.getParameter(request,"yuangong");                                              //留言板管理员将问题提交给部门留言板管理员
        if (processor == null) processor = ParamUtil.getParameter(request,"employeeofthedept");           //留言板部门管理员不能回答，将问题提交给本部门的其他员工回答
        for (int i = 0; i < ids.length; i++) {
            wordMgr.updateDepartmentForLeaveWord(siteid,markid,Integer.parseInt(ids[i]),departmentid,processor);
        }
    }
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
    String userrole = "";
    List fl = wordMgr.getWordAuthorizedUser(userid,siteid);
    for (int i =0; i<fl.size(); i++) {
        authorizedform f1 = (authorizedform)fl.get(i);
        if (f1.getLwid() == markid) {
            userrole = f1.getLwrole();
            break;
        }
    }

    if(userrole.equalsIgnoreCase("留言板管理员")){
        sqlstr = "select * from tbl_leaveword where siteid = "+siteid+" and formid = "+markid+" order by writedate desc";
    }else if (userrole.equalsIgnoreCase("留言板部门管理员")){
        sqlstr = "select * from tbl_leaveword where siteid = "+siteid+" and formid = "+markid+" and departmentid = " + departments + " order by writedate desc";
    }else {
        sqlstr = "select * from tbl_leaveword where siteid = "+siteid+" and formid = "+markid+" and departmentid = " + departments + "and processor='" + userid + "' order by writedate desc";
    }


    int totalpages = 0;
    int currentpage = 0;
    String sdate = ParamUtil.getParameter(request,"startdate");
    String edate = ParamUtil.getParameter(request,"enddate");
    int infotype = ParamUtil.getIntParameter(request,"seachclassinfo",0);
    String keyword = ParamUtil.getParameter(request,"keyword");
    int dosearch = ParamUtil.getIntParameter(request,"dosearch",0);
    if (dosearch == 0) {
        if (sqlstr != "" && sqlstr!=null) {
            list = wordMgr.getCurrentWord(sqlstr,startrow,range);
            rowsList = wordMgr.getAllWord(sqlstr);
            rows = rowsList.size();
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
        }
    } else { //执行信息检索
        int usertype = 0;
        if (userrole.equalsIgnoreCase("留言板管理员"))
            usertype = 0;
        else if (userrole.equalsIgnoreCase("留言板部门管理员"))
            usertype = 1;
        else
            usertype = 2;
        list = wordMgr.searchLW(sdate,edate,keyword,infotype,user.getDepartment(),userid,startrow,range,siteid,markid,usertype);
    }

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    Calendar cal_today = Calendar.getInstance();
    String dd = formatter.format(cal_today.getTime());
%>
<html>
<head>
    <title>用户留言</title>
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
    <script language=JavaScript1.2 src="../../js/functions.js"></script>
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
            var str = "retcontent.jsp?startrow=<%=startrow%>&range=<%=range%>" + "&id=" + id+"&markid="+markid;
            window.open(str,"returntouser","height=500,width=800");
        }

        function retfromdept(id,markid){
            var str = "retcontentfromdept.jsp?startrow=<%=startrow%>&range=<%=range%>" + "&id=" + id+"&markid="+markid;
            window.open(str,"returntouser","height=500,width=800");
        }

        function finalresult(id,markid){
            var str = "finalresult.jsp?startrow=<%=startrow%>&range=<%=range%>" + "&id=" + id+"&markid="+markid;
            window.open(str,"finnalresult","height=500,width=800");
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

        function setvalid(id,lwid,validflag){
            window.open("setvalid.jsp?startrow=<%=startrow%>&range=<%=range%>&id="+id + "&lwid=" + lwid,"setvalidinfo","height=500,width=800");
        }

        function daochu_lw_to_text(lwid,type) {
            window.open("exportLWToText.jsp?markid=" + lwid + "&type=" + type,"columnsforlw","height=500,width=800");
        }

        function manageLWColumn() {
            window.open("columns.jsp","columnsforlw","height=500,width=800");
        }

        function manageLW_vilad() {
            window.open("citiaos.jsp","citiao","height=500,width=800");
        }

        function ret_to_manager(id,lwid)  {
            window.open("rettomanager.jsp?startrow=<%=startrow%>&range=<%=range%>&id="+id + "&lwid=" + lwid,"returnlwtomanager","height=500,width=800");
        }

        var req,reqfordept;
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

        //获取某个部门的留言板信息管理员
        function getEmployee(){
            req = getXMLHTTPObj();
            if (req) {
                req.onreadystatechange=onReadyStateChange;
                req.Open("post","getEmployeeByDept.jsp?dept="+form.department.value + "&lwid=<%=markid%>",true);
                req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                req.send(null);
            }
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

        //获取某个部门的所有员工
        function getEmployeeByDeptid(){
            reqfordept = getXMLHTTPObj();
            if (reqfordept) {
                reqfordept.onreadystatechange=onReadyStateChangeForGetAllEmployeeByDept;
                reqfordept.Open("post","getAllEmployeeByDept.jsp?dept="+form.department.value + "&lwid=<%=markid%>",true);
                reqfordept.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
                reqfordept.send(null);
            }
        }

        function onReadyStateChangeForGetAllEmployeeByDept(){
            var ready=reqfordept.readyState;
            var data=null;
            if (ready ==4) {
                data = reqfordept.responseText;
                var userArray = data.split(";");
                var i = 0;
                document.getElementById("employeeofthedeptid").options.length = 0;
                document.getElementById("employeeofthedeptid").options.add(new Option("请选择","0"));
                for (i=0; i<userArray.length; i++) {
                    posi = userArray[i].indexOf(":");
                    key = userArray[i].substring(0,posi);
                    value = userArray[i].substring(posi + 1);
                    document.getElementById("employeeofthedeptid").options.add(new Option(key,value));
                }
            }
        }

        function opencalendar(baseURL)
        {
            openPopup(baseURL, "小日历", 350, 300, "width=300,height=200,toolbar=no,status=no,directories=no,menubar=no,resizable=yes,scrollable=no", true);
        }

        //留言板部门管理员设置留言归类
        function set_info_class(id,markid) {
            window.open("makeColumn.jsp?startrow=<%=startrow%>&range=<%=range%>&markid=" + markid + "&id=" + id ,"columnsforlw","height=500,width=800");
        }
        
        //修改我对用户的回复
        function changelw(id,markid) {
            alert("hello word");
        }
    </script>
</head>
<body>
<center>
<table width="100%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
    <form action="index1.jsp" method="post" name="searchform">
        <input type="hidden" name="markid" value="<%=markid%>">
        <input type="hidden" name="startrow" value="<%=startrow%>">
        <input type="hidden" name="range" value="<%=range%>">
        <input type="hidden" name="dosearch" value="1">
        <tr align="right">
            <td colspan="5" >
                <%if(userrole.equalsIgnoreCase("留言板管理员")) {%>
                <a href="#" onclick="javascript:manageLWColumn()">留言分类管理</a>                   <a href="#" onclick="javascript:manageLW_vilad()">无效留言词条管理</a>
                <%}%>
                <a href="#" onclick="javascript:window.close()">退出</a>
            </td>
        </tr>
        <tr>
            <td align="left" width="20%">开始日期：<input type="text" name="startdate" value="<%=(sdate!=null)?sdate:""%>" readonly="true"><a href="JavaScript:opencalendar('calendar.jsp?form=searchform&ip=startdate&d=<%=dd%>')" onclick="setLastMousePosition(event)" tabindex="3"><img src="../../images/date_picker.gif" border="0" width="34" height="21" align="absmiddle" border=0></a>
            </td>
            <td align="left" width="20%"> 结束日期：<input type="text" name="enddate" value="<%=(edate!=null)?edate:""%>"  readonly="true"><a href="JavaScript:opencalendar('calendar.jsp?form=searchform&ip=enddate&d=<%=dd%>')" onclick="setLastMousePosition(event)" tabindex="3"><img src="../../images/date_picker.gif" border="0" width="34" height="21" align="absmiddle" border=0></a>
            </td>
            <td align="left" width="10%">
                类    别：
                <select name="seachclassinfo" id="classinfoid">
                    <option value="0" <%=(infotype==0)?"selected":""%>>请选择</option>
                    <option value="1" <%=(infotype==1)?"selected":""%>>已回答</option>
                    <option value="2" <%=(infotype==2)?"selected":""%>>未回答</option>
                    <option value="3" <%=(infotype==3)?"selected":""%>>有效</option>
                    <option value="4" <%=(infotype==4)?"selected":""%>>无效</option>
                    <option value="5" <%=(infotype==5)?"selected":""%>>全部</option>
                </select>
            </td>
            <td align="left" width="20%">关键字：<input type="text" value="<%=(keyword!=null)?keyword:""%>" name="keyword" size="20" maxlength="50"></td>
            <td align="left"> <input type="submit" name="search" value="搜索"></td>
        </tr>
    </form>
</table>
<form action="index1.jsp" method="post" name="form">
<input type="hidden" name="markid" value="<%=markid%>">
<input type="hidden" name="startrow" value="<%=startrow%>">
<input type="hidden" name="range" value="<%=range%>">
<input type="hidden" name="doflag" value="1">

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
                                <%if (userrole.equalsIgnoreCase("留言板管理员")) {%>
                                <td width="1%" align="center">&nbsp;</td>
                                <td width="5%" align="center">部门</td>
                                <td width="5%" align="center">处理人</td>
                                <td width="5%" align="center">用户名</td>
                                <td width="5%" align="center">编码</td>
                                <td width="5%" align="center">类别</td>
                                <td align="center" width="10%">标题</td>
                                <td align="center" width="15%">内容</td>
                                <td align="center" width="5%">联系人</td>
                                <td align="center" width="5%">邮编</td>
                                <td align="center" width="5%">电子邮件</td>
                                <td align="center" width="5%">电话</td>
                                <td align="center" width="5%">留言时间</td>
                                <td align="center" width="5%">部门回复</td>
                                <td align="center" width="4%">是否显示</td>
                                <td align="center" width="4%">删除</td>
                                <td align="center" width="4%">回复</td>
                                <td align="center" width="4%">有效</td>
                                <td align="center" width="4%">归类</td>
                                <%} else if (userrole.equalsIgnoreCase("留言板部门管理员")){%>
                                <td width="1%" align="center">&nbsp;</td>
                                <td align="center" width="4%">部门</td>
                                <td width="4%" align="center">处理人</td>
                                <td width="5%" align="center">用户名</td>
                                <td width="7%" align="center">编码</td>
                                <td width="5%" align="center">类别</td>
                                <td align="center" width="10%">标题</td>
                                <td align="center" width="22%">内容</td>
                                <td align="center" width="5%">联系人</td>
                                <td align="center" width="4%">邮编</td>
                                <td align="center" width="5%">电子邮件</td>
                                <td align="center" width="5%">电话</td>
                                <td align="center" width="7%">留言时间</td>
                                <td align="center" width="4%">确认</td>
                                <td align="center" width="4%">归类</td>
                                <td align="center" width="4%">回退</td>
                                <td align="center" width="4%">回复</td>
                                <%} else {%>
                                <td width="1%" align="center">&nbsp;</td>
                                <td align="center" width="5%">部门</td>
                                <td width="5%" align="center">处理人</td>
                                <td width="5%" align="center">用户名</td>
                                <td width="7%" align="center">编码</td>
                                <td width="5%" align="center">类别</td>
                                <td align="center" width="10%">标题</td>
                                <td align="center" width="27%">内容</td>
                                <td align="center" width="7%">联系人</td>
                                <td align="center" width="4%">邮编</td>
                                <td align="center" width="5%">电子邮件</td>
                                <td align="center" width="5%">电话</td>
                                <td align="center" width="7%">留言时间</td>
                                <td align="center" width="4%">回退</td>
                                <td align="center" width="5%">回复</td>
                                <%}%>
                            </tr>
                            <%
                                for(int i = 0; i < list.size();i++){
                                    Word word = (Word)list.get(i);
                                    int columnid_for_item = word.getColumnid();
                                    String cname = wordMgr.getAColumnName(siteid,columnid_for_item);
                                    Department d = null;
                                    d = uMgr.getOneDepartmentInfoById(word.getDepartmentid());
                                    String dname = null;
                                    if(d != null ){
                                        dname = d.getCname();
                                    }
                                    String processor = word.getProcessor();
                                    int have_anwser = wordMgr.haveAnwserFromEmployee(word.getId(),userid);
                            %>
                            <tr  bgcolor="#FFFFFF" class="css_001">
                                <%if (userrole.equalsIgnoreCase("留言板管理员")) {%>
                                <td align="left" width="1%"><%if(d==null && word.getValid() == 0){%><input type="checkbox" name="ids" value="<%=word.getId()%>"><%}else{%>&nbsp;<%}%> </td>
                                <td align="left" width="5%"><%=dname == null?"--":StringUtil.gb2iso4View(dname)%></td>
                                <td align="left" width="5%"><%=processor == null?"--":StringUtil.gb2iso4View(processor)%></td>
                                <td align="left" width="5%"><%=word.getUserid()==null?"--": StringUtil.gb2iso4View(word.getUserid())%></td>
                                <td align="left" width="5%"><%=(word.getCode()==null)?"--":word.getCode()%></td>
                                <td align="left" width="5%"><%=(cname==null)?"--":cname%></td>
                                <td align="left" width="10%"><font color='<%=(word.getFinalflag()==1)?"red":""%>'><%=word.getTitle()==null?"--":StringUtil.gb2iso4View(word.getTitle())%><font></font></td>
                                <td align="left" width="15%"><%=word.getContent()==null?"--":StringUtil.gb2iso4View(word.getContent())%></td>
                                <td align="left" width="5%"><%=word.getLinkman()==null?"--":StringUtil.gb2iso4View(word.getLinkman())%></td>
                                <td align="left" width="5%"><%=word.getZip()==null?"--":StringUtil.gb2iso4View(word.getZip())%></td>
                                <td align="left" width="5%"><%=word.getEmail()==null?"--":StringUtil.gb2iso4View(word.getEmail())%></td>
                                <td align="left" width="5%"><%=word.getPhone()==null?"--":StringUtil.gb2iso4View(word.getPhone())%></td>
                                <td align="left" width="5%"><%=word.getWritedate().toString().substring(0,10)%></td>
                                <td align="left" width="5%"><font color="red"><%=(word.getDatefromdept()!=null)?(word.getDatefromdept().toString().substring(0,10)):""%></font></td>
                                <%if (word.getFinalflag() == 1) {%>
                                <td align="left" width="4%"><a href="javascript:upflag(<%=word.getId()%>,<%=word.getFlag()%>,<%=markid%>)"><%if(word.getFlag()==0){%>不显示<%}else{%>显示<%}%></a></td>
                                <%} else {%>
                                <td align="left" width="4%">&nbsp;</td>
                                <%}%>
                                <td align="left" width="4%"><a href="javascript:del(<%=word.getId()%>,<%=markid%>)">删除</a></td>
                                <td align="left" width="4%"><a href="javascript:ret(<%=word.getId()%>,<%=markid%>)"><%if(word.getRetcontent()==null || word.getRetcontent().trim().equals("")){%>回复<%}else{%>查看回复<%}%></a></td>
                                <td align="left" width="4%"><a href="javascript:setvalid(<%=word.getId()%>,<%=markid%>,<%=word.getValid()%>)"><%if(word.getValid() == 0){%>有效<%}else{%>无效<%}%><!--/abbr--></a></td>
                                <!--% if (word.getAuditflag() == 1 && word.getRetcontent()!=null && word.getAuditor().indexOf(userid)>-1) {%-->
                                <!--td align="left" width="4%"><a href="javascript:audit(<%=word.getId()%>,<%=markid%>)">审核</a></td-->
                                <td align="left" width="4%"><a href="javascript:set_info_class(<%=word.getId()%>,<%=markid%>)">归类</a></td>
                                <!--%} else {%-->
                                <!--td align="left" width="4%">&nbsp;</td-->
                                <!--%}%-->
                                <%} else if (userrole.equalsIgnoreCase("留言板部门管理员")) {%>
                                <td align="left" width="1%"><input type="checkbox" name="ids" value="<%=word.getId()%>"></td>
                                <td align="left" width="4%"><%=dname == null?"--":StringUtil.gb2iso4View(dname)%></td>
                                <td align="left" width="4%"><%=processor == null?"--":StringUtil.gb2iso4View(processor)%></td>
                                <td align="left" width="5%"><%=word.getUserid()==null?"--": StringUtil.gb2iso4View(word.getUserid())%></td>
                                <td align="left" width="7%"><%=(word.getCode()==null)?"--":word.getCode()%></td>
                                <td align="left" width="5%"><%=(cname==null)?"--":cname%></td>
                                <td align="left" width="10%"><font color='<%=(word.getFinalflag()==1)?"red":""%>'><%=word.getTitle()==null?"--":StringUtil.gb2iso4View(word.getTitle())%></font></td>
                                <td align="left" width="22%"><%=word.getContent()==null?"--":StringUtil.gb2iso4View(word.getContent())%></td>
                                <td align="left" width="5%"><%=word.getLinkman()==null?"--":StringUtil.gb2iso4View(word.getLinkman())%></td>
                                <td align="left" width="4%"><%=word.getZip()==null?"--":StringUtil.gb2iso4View(word.getZip())%></td>
                                <td align="left" width="5%"><%=word.getEmail()==null?"--":StringUtil.gb2iso4View(word.getEmail())%></td>
                                <td align="left" width="5%"><%=word.getPhone()==null?"--":StringUtil.gb2iso4View(word.getPhone())%></td>
                                <td align="left" width="7%"><%=word.getWritedate().toString().substring(0,10)%></td>
                                <td align="center" width="4%"><a href="javascript:finalresult(<%=word.getId()%>,<%=markid%>)">确认</a></td>
                                <td align="center" width="4%"><a href="javascript:set_info_class(<%=word.getId()%>,<%=markid%>)">归类</a></td>
                                <td align="center" width="4%"><a href="javascript:ret_to_manager(<%=word.getId()%>,<%=markid%>)">回退</a></td>
                                <td align="center" width="4%"><a href="javascript:retfromdept(<%=word.getId()%>,<%=markid%>)"><%if(have_anwser==0){%>回复<%}else{%>修改<%}%></a></td>
                                <%} else {%>
                                <td align="left" width="1%"><input type="checkbox" name="ids" value="<%=word.getId()%>"></td>
                                <td align="left" width="5%"><%=dname == null?"--":StringUtil.gb2iso4View(dname)%></td>
                                <td align="left" width="5%"><%=processor == null?"--":StringUtil.gb2iso4View(processor)%></td>
                                <td align="left" width="5%"><%=word.getUserid()==null?"--": StringUtil.gb2iso4View(word.getUserid())%></td>
                                <td align="left" width="7%"><%=(word.getCode()==null)?"--":word.getCode()%></td>
                                <td align="left" width="5%"><%=(cname==null)?"--":cname%></td>
                                <td align="left" width="10%"><font color='<%=(word.getFinalflag()==1)?"red":""%>'><%=word.getTitle()==null?"--":StringUtil.gb2iso4View(word.getTitle())%></font></td>
                                <td align="left" width="27%"><%=word.getContent()==null?"--":StringUtil.gb2iso4View(word.getContent())%></td>
                                <td align="left" width="5%"><%=word.getLinkman()==null?"--":StringUtil.gb2iso4View(word.getLinkman())%></td>
                                <td align="left" width="4%"><%=word.getZip()==null?"--":StringUtil.gb2iso4View(word.getZip())%></td>
                                <td align="left" width="5%"><%=word.getEmail()==null?"--":StringUtil.gb2iso4View(word.getEmail())%></td>
                                <td align="left" width="5%"><%=word.getPhone()==null?"--":StringUtil.gb2iso4View(word.getPhone())%></td>
                                <td align="left" width="7%"><%=word.getWritedate().toString().substring(0,10)%></td>
                                <td align="left" width="4%"><a href="javascript:ret_to_manager(<%=word.getId()%>,<%=markid%>)">回退</a></td>
                                <td align="left" width="5%"><a href="javascript:retfromdept(<%=word.getId()%>,<%=markid%>)"><%if(have_anwser==0){%>回复<%}else{%>修改<%}%></a></td>
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
    <%if(userrole.equalsIgnoreCase("留言板管理员")){%>
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
            &nbsp;&nbsp;&nbsp;&nbsp;
             <input type="button" name="button" value="导出留言板信息" onclick="javascript:daochu_lw_to_text(<%=markid%>,1);">
        </td>
    </tr>
    <%} else  if (userrole.equalsIgnoreCase("留言板部门管理员")){%>
    <tr valign="bottom" width=100%>
        <td colspan="6">
            <input type="checkbox" name="chkAll" value="on" onclick="javascript:CheckAll(this.form);">全部选中&nbsp;&nbsp;&nbsp;&nbsp;转发其他员工：
            <!--input type="hidden" name="department" value="<!--%=departments%>"-->
            <select name="department" id="departmentid" onchange="javascript:getEmployeeByDeptid()">
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
            <select name="employeeofthedept" id="employeeofthedeptid">
                <option value ="0">请选择</option>
                <!--%
                    List usersBydept = uMgr.getEmployeesBYDepartmentID(departments,siteid);
                    for(int i = 0; i < usersBydept.size();i++){
                        user = (User)usersBydept.get(i);
                        if(user!=null){
                %>

                <option value="<!--%=user.getUserID()%>"><!--%=user.getNickName()==null?"":StringUtil.gb2iso4View(user.getNickName())%></option>
                <!--%}}%-->
            </select>

            <input type="button" name="button" value="提交" onclick="javascript:return check(this.form);">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" name="button" value="导出留言板信息" onclick="javascript:daochu_lw_to_text(<%=markid%>,0);">
        </td>
    </tr>
    <%}%>
</table>
</form>
</center>
</body>
</html>