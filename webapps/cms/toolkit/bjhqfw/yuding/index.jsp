<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.IYuDingManager" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDingPeer" %>
<%@ page import="com.bizwink.cms.bjhqfw.yuding.YuDing" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String ydperson = ParamUtil.getParameter(request,"ydperson");
    IYuDingManager yMgr = YuDingPeer.getInstance();
    YuDing mon = new YuDing();
    String sqlstr = "select * from tbl_yuding order by id desc";
    String sql = "select count(id) from tbl_yuding";

    if(ydperson != null && ydperson != ""){
        sqlstr = "select * from tbl_yuding where ydperson like '@" + ydperson +"@' order by id desc";
        sql = "select count(id) from tbl_yuding where ydperson like '@" + ydperson +"@'";
    }

    List list = yMgr.getAllYuDing(sqlstr, start, range);
    int totalnum = yMgr.getAllYuDingNum(sql);
    int totalpages = 0;
    int currentpage = 0;
    if (totalnum < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (totalnum % range == 0)
            totalpages = totalnum / range;
        else
            totalpages = totalnum / range + 1;

        currentpage = start / range + 1;
    }
%>
<html>
<head>
<title>会议室预定管理</title>
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

     function DelYuDing(id){
      var val;
      val = confirm("你确定要删除吗？");
      if(val == 1){
        window.location = "delete.jsp?id="+id;
      }
    }

   function check()
   {
       if(searchFom.ydperson.value == "")
       {
           alert("请输入预定人名称");
           return false;
       }
       return true;
   }

  </script>
</head>
<body>
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td>
        <table width="100%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">会议室预定管理</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="5%" align="center">编号</td>
                    <td align="center" width="13%">预定人</td>
                    <td align="center" width="8%">预定会议室ID</td>
                    <td align="center" width="15%">开会时间</td>
                    <td align="center" width="15%">结束时间</td>
                    <td align="center" width="8%">审批状态</td>
                    <td align="center" width="10%">审批人</td>
                    <td align="center" width="14%">审批时间</td>
                    <td align="center" width="5%">修改</td>
                    <td align="center" width="5%">删除</td>
                </tr>
                  <%
                        for(int i = 0; i < list.size(); i++){
                            YuDing yd = (YuDing)list.get(i);
                  %>
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="5%" align="center"><%=i+1%></td>
                        <td align="center" width="13%"><%=yd.getYdperson()== null || yd.getYdperson().equalsIgnoreCase("null")?"--":yd.getYdperson()%></td>
                    <td align="center" width="8%"><%=yd.getJbxinxiid()==null || yd.getJbxinxiid().equalsIgnoreCase("null")?"--":yd.getJbxinxiid()%></td>
                    <td align="center" width="15%"><%=yd.getKhdate().toString().substring(0,19)%></td>
                    <td align="center" width="15%"><%=yd.getJsdate().toString().substring(0,19)%></td>
                    <td align="center" width="8%"><%if(yd.getFlag() == 1){%><a href="shenhe.jsp?id=<%=yd.getId()%>&&flag=1">审核已通过</a><%}else{%><a href="shenhe.jsp?id=<%=yd.getId()%>&&flag=2">审核未通过</a><%}%></td>
                    <td align="center" width="10%"><%=yd.getShperson() == null || yd.getShperson().equalsIgnoreCase("null")?"--":yd.getShperson()%></td>
                    <td align="center" width="14%"><%if(yd.getShdate() != null){%><%=yd.getShdate().toString().substring(0,19)%><%}%></td>
                    <td align="center" width="5%"><a href ="edit.jsp?id=<%=yd.getId()%>">修改</a></td>
                    <td align="center" width="5%"><a href="#" onclick="javascript:return DelYuDing(<%=yd.getId()%>);">删除</a></td>
                </tr>
                  <%}%>
               </table>
            </td>
          </tr>
            <tr>
                <td align="right">
                 <a href="../index.jsp"> 返 回 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <!--a href="create.jsp">会议室预定</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   <a href="information.jsp">会议室预定信息收集（前台）</a-->
                </td>
            </tr>
        </table>
                <p align=center>
                <TABLE>
                    <TBODY>
                    <TR>
                        <TD>总共<%=totalpages%>页&nbsp;&nbsp; 共<%=totalnum%>条&nbsp;&nbsp; 当前第<%=currentpage%>页&nbsp;
                            <%
                                if ((start - range) >= 0) {
                            %>
                            <a href="index.jsp?start=0">第一页</a>
                            <%}%>
                            <%if ((start - range) >= 0) {%>
                            <a href="index.jsp?start=<%=start-range%>">上一页</a>
                            <%}%>
                            <%if ((start + range) < totalnum) {%>
                            <A href="index.jsp?start=<%=start+range%>">下一页</A>
                            <%}%>
                            <%if (currentpage != totalpages) {%>
                            <A href="index.jsp?start=<%=(totalpages-1)*range%>">最后一页</A>
                            <%}%>
                        </TD>
                        <TD>&nbsp;</TD>
                    </TR>
                    </TBODY>
                </TABLE>
      </td>
</tr>
</table>
<table width="50%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<form action="index.jsp" name="searchFom" onsubmit="return check();">
<input type="hidden" name="searchflag" value="1">
    <input type="hidden" name="month" value="<%%>">
<tr>
  <td>
    <table width="100%" border="0" cellpadding="0">
      <tr bgcolor="#F4F4F4" align="center">
        <td height="30" valign="left" bgcolor="#F4F4F4" class="css_003">查询：</td>
      </tr>
      <tr bgcolor="#d4d4d4" align="right">
        <td>
          <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
            <tr  bgcolor="#FFFFFF" class="css_001">
              <td width="8%" align="center" bgcolor="#FFFFFF">预定人：</td>
              <td align="center" width="48%"><input name="ydperson" type="text"></td>
            </tr>

           </table>
        </td>
      </tr>
    </table>
  </td>
</tr>
<tr>
<td align=center><input type="submit" value="查询"></td>
</tr>
</form>
</table>
</center>
</body>
</html>