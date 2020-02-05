<%@ page import="com.bizwink.webapps.register.*,
                 com.bizwink.cms.security.Auth,
                 com.bizwink.cms.util.ParamUtil,
                 com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=gbk"
        %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp");
        return;
    }

    String userid1 = ParamUtil.getParameter(request, "userid");
	String shijian1 = ParamUtil.getParameter(request, "shijian1");
	String shijian2 = ParamUtil.getParameter(request, "shijian2");
    int siteid = authToken.getSiteID();
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    if (startrow < 0) {
        startrow = 0;
    }

    IUregisterManager regMgr = UregisterPeer.getInstance();
    String sqlstr = "";
        if (userid1 != null && userid1 != "") {
            sqlstr = "select * from tbl_rsbt_org where userid like '@" + userid1 + "@' and ";
        } else {
            sqlstr = "select * from tbl_rsbt_org where ";
        }
        if (shijian1 != null && shijian1 != "") {
            sqlstr += "createdate > to_date('" + shijian1 + "' ,'yyyy-mm-dd') and createdate < to_date('" + shijian2 + "','yyyy-mm-dd') ";
        }else{
            sqlstr = sqlstr.substring(0,sqlstr.lastIndexOf("and"));
        }
    List list = new ArrayList();
    List currentlist = new ArrayList();

    // list = regMgr.getAllRsbt();
    currentlist = regMgr.getCurrentQueryRsbtList(siteid,sqlstr, startrow, range);

    //int row = 0;
    int rows;
    int totalpages = 0;
    int currentpage = 0;

    //row = currentlist.size();
    rows = regMgr.getAllRsbtNum(siteid);

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

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel="stylesheet" type="text/css" href="global.css">
    <script language="JavaScript" src="setday.js"></script>
    <script language="javascript">
        function searchcheck() {
            var a = form1.userid.value;
            var b = form1.shijian1.value;
            var c = form1.shijian2.value;
            if (a + b + c  == null || a + b + c  == "") {
                alert("请至少输入一个条件");
                return false;
            }
            form1.submit();
            return true;
        }
        function DelSupplier(id)
        {
            var bln = confirm("真的要删除吗？");
            if (bln)
            {
                window.location = "delete.jsp?id=" + id;
            }
        }
    </script>
</head>
<font class=line>用户列表</font>

<form name="form1" method="post" action="query.jsp">
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width=100%>
        <tr bgcolor="#eeeeee" class=tine>
            <td align=center>编号</td>
            <td align=center>用户ID</td>
            <td align=center>组织机构代码</td>
            <td align=center>组织机构名称</td>
            <td align=center>地区代码</td>
            <td align=center>系统/行业代码</td>
            <td align=center>单位类型</td>
            <td align=center>单位联系人</td>
            <td align=center>联系人身份证号码</td>
            <td align=center>上级组织机构</td>
            <td align=center>组织机构地址</td>
            <td align=center>修改</td>
            <td align=center>删除</td>
        </tr>

        <%
            if (currentlist != null) {
                for (int i = 0; i < currentlist.size(); i++) {
                    int id = 0;
                    String userid = "";
                    String org_gode = "";
                    String org_name = "";
                    String org_area_code = "";
                    String org_sys_code = "";
                    String org_type = "";
                    String org_link_person = "";
                    String org_person_id = "";
                    String org_sup_code = "";
                    String org_addr = "";
                    Uregister reg = (Uregister) currentlist.get(i);
                    id = reg.getId();
                    userid = reg.getMemberid();
                    org_gode = reg.getOrggode();
                    org_name = reg.getOrgname();
                    org_area_code = reg.getOrgareacode();
                    org_sys_code = reg.getOrgsyscode();
                    org_type = reg.getOrgtype();
                    org_link_person = reg.getOrglinkperson();
                    org_person_id = reg.getOrgpersonid();
                    org_sup_code = reg.getOrgsupcode();
                    org_addr = reg.getOrgaddr();
        %>
        <tr bgcolor="#ffffff" class=line>
            <td align=center><%=i%>
            </td>
            <td align=center><%=userid == null ? "--" : userid%>
            </td>
            <td align=center><%=org_gode == null ? "--" : org_gode%>
            </td>
            <td align=center><%=org_name == null ? "--" : org_name%>
            </td>
            <td align=center><%=org_area_code == null ? "--" : org_area_code%>
            </td>
            <td align=center><%=org_sys_code == null ? "--" : org_sys_code%>
            </td>
            <td align=center><%=org_type == null ? "--" : org_type%>
            </td>
            <td align=center><%=org_link_person == null ? "--" : org_link_person%>
            </td>
            <td align=center><%=org_person_id == null ? "--" : org_person_id%>
            </td>
            <td align=center><%=org_sup_code == null ? "--" : org_sup_code%>
            </td>
            <td align=center><%=org_addr == null ? "--" : org_addr%>
            </td>
            <td align=center><a href="edit.jsp?id=<%=id%>">修改</a></td>
            <td align=center><A href="index.jsp#" onclick="javascript:return DelSupplier(<%=id%>);">删除</A></td>
        </tr>

        <%
                }
            }
        %>
    </table>
    <br><br>
    <p align=left>
    <TABLE width="60%" align="center">
        <TBODY>
        <TR height=35>
            <TD align=center>用户ID：<input type="text" size=20 name="userid"></TD>
            <TD align=center>创建时间：<input type="text" size=10 name="shijian1" readonly
                                         onfocus="setday(this)"> -- <input type="text" size=10 name="shijian2"
                                                                           readonly onfocus="setday(this)">
            </TD>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td colspan="2" align=center>
                <input type="button" value=" 查 询 " onclick="javascript:return searchcheck();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <INPUT onclick=javascript:history.go(-1); type=button value=" 返 回 ">
            </td>
        </tr>
        </tbody>
    </table>
</form>

</html>