<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
                 contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.cms.news.Producttype"%>
<%@ page import="com.bizwink.cms.news.IColumnManager"%>
<%@ page import="com.bizwink.cms.news.ColumnPeer"%>
<%@ page import="com.bizwink.cms.server.CmsServer"%>
<%@ page import="com.bizwink.cms.xml.XMLProperties"%>
<%@ page import="com.bizwink.cms.markManager.markPeer"%>
<%@ page import="com.bizwink.cms.markManager.IMarkManager"%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect( "../login.jsp?url=member/createMember.jsp" );
    return;
  }

  IMarkManager markMgr = markPeer.getInstance();

    int columnID = ParamUtil.getIntParameter(request,"column",0);
    int markID = ParamUtil.getIntParameter(request,"mark",0);
    int startflag = ParamUtil.getIntParameter(request,"startflag",-1);
  String str = "";
  XMLProperties properties = null;

    String pvalue = "";

    int type = ParamUtil.getIntParameter(request, "type", 0);
    if (markID > 0)
  {
    str = markMgr.getAMarkContent(markID);
    if (str != null && str != "") {
      if (CmsServer.getInstance().getCustomer().equalsIgnoreCase("linktone"))
        str = StringUtil.gb2iso(str);
      else
        str = StringUtil.gb2iso4View(str);

      str = StringUtil.replace(str, "[", "<");
      str = StringUtil.replace(str, "]", ">");
      str = StringUtil.replace(str, "&", "&amp;");
      properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);

        pvalue = properties.getProperty(properties.getName().concat(".ARTICLESECONDTYPE"));

    }
  } else {
    str = "标记不存在";
  }
    IColumnManager columnManager = ColumnPeer.getInstance();
    List articletype = new ArrayList();
    if(pvalue !=null && !pvalue.equals("")){
        articletype = columnManager.getTypes(pvalue);
    }
    //List spt = new ArrayList();
    String spt = (String)session.getAttribute("atids");
    if(spt !=null&&spt.length()>0){
        articletype = columnManager.getTypes(spt);
    }
    if (startflag == 1) {
        List ptypelist = new ArrayList();
        String[] ordergo = request.getParameterValues("columnList");
        String orderids = "";
        for (int i = 0; i < ordergo.length; i++) {
            if (i == 0) {
                orderids = orderids + ordergo[i];
            } else {
                orderids = orderids + "," + ordergo[i];
            }
                Producttype pvt = new Producttype();
                pvt.setValueid(Integer.parseInt(ordergo[i]));
                pvt.setColumnID(columnID);
                ptypelist.add(pvt);
        }
        String atypestr = columnManager.getTypeNames(orderids);
        atypestr = StringUtil.gb2iso4View(atypestr);
        session.setAttribute("atids",orderids);
        out.print("<script type=\"text/javascript\">");
        out.println("parent.window.returnValue = \"" + atypestr + "," + orderids + "\";");
        out.println("parent.window.close();");
        out.print("</script>");
    }
%>

<html>
<head>
<title>文章分类</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="../style/global.css">
<script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
<SCRIPT LANGUAGE=javascript>
function checkFrm(frm)
{
  if (frm.columnList.length > 0)
  {
    var el = form1.columnList;
    for (var i = el.options.length-1; i >=0; i--)
      el.options[i].selected = true;
  }
}

function delElement()
{
  var el = form1.columnList;
  for (var i = el.options.length-1; i >=0; i--) {
    if (el.options[i].selected) el.options[i] = null;
  }
}

function selectRight()
{
  var rightID = form1.rightList.value;
  //window.location = "doGrantForUser.jsp?userid=&rightID="+rightID;
}
</SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="form1" method="post" action="doArticletype.jsp">
<input type=hidden name=startflag value=1>
<input type=hidden name=column value=<%=columnID%>>
    <input type=hidden name=mark value=<%=markID%>>
<table width="90%" border="0" align=center>
  <tr>
    <td>
      <table width="100%" border=0>
        <tr>
          <td width="65%">文章分类：<br>
            <select name="columnList" size="15" style="width:150" multiple>
            <%for (int i=0; i<articletype.size(); i++) {
	              Producttype pt = (Producttype)articletype.get(i);
            %>
            <option value="<%=pt.getValueid()%>"><%=StringUtil.gb2iso4View(pt.getCname())%></option>
            <%}%>
            </select>
          </td>
          <td width="35%"><input type=button onclick="delElement();" value="删除" class=tine></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td height="40" align="center">
      <input type="submit" value=" 确定 " class=tine onclick="return checkFrm(this.form);">&nbsp;&nbsp;
      <input type="button" value=" 返回 " class=tine onclick="parent.window.close();">
    </td>
  </tr>
</table>
</form>

</body>
</html>
