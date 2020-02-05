<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String username = authToken.getUserID();
    IModelManager modelManager = ModelPeer.getInstance();

    int columnID1 = ParamUtil.getIntParameter(request, "column1", 0);   //源栏目
    int columnID2 = ParamUtil.getIntParameter(request, "column2", 0);   //目的栏目
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

    if (doCreate)
    {
        int templateID = ParamUtil.getIntParameter(request, "template", 0);
        Model model = modelManager.getModel(templateID);
        String cname = model.getChineseName();
        if (cname != null) cname = StringUtil.gb2iso4View(cname);

        model.setColumnID(columnID2);
        model.setCreatedate(new Timestamp(System.currentTimeMillis()));
        model.setLastupdated(new Timestamp(System.currentTimeMillis()));
        model.setEditor(username);
        model.setCreator(username);
        model.setLockstatus(0);
        model.setReferModelID(templateID);
        model.setChineseName(cname);
        model.setDefaultTemplate(0);

        modelManager.Create(model,siteID,samsiteid);
        out.println("<script language=javascript>window.parent.opener.top.frames[1].frames[2].location.reload();top.close();</script>");
        return;
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID1);
    String cname = StringUtil.gb2iso4View(column.getCName());

    int total = modelManager.getNotReferredModelCount(columnID1);
    List templateList = modelManager.getNotReferredModels(columnID1, start, range);
    int templateCount = templateList.size();
%>

<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript">
        function ReferModel(templateID)
        {
            var ret = confirm("您确定要引用当前模板吗？");
            if (ret)
                window.location = "referTemplateRight.jsp?column1=<%=columnID1%>&column2=<%=columnID2%>&template="+templateID+"&doCreate=true";
        }

        function Preview(templateID, isArticle)
        {
            window.open("previewTemplate.jsp?column=<%=columnID1%>&template="+templateID+"&isArticle="+isArticle,"PreviewTemplate","width=800,height=600,left=0,top=0,scrollbars");
        }
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<center>
    <%
        if (templateCount > 0)
        {
            out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
            out.println("<tr><td width=50% class=line>");
            if (start - range >= 0)
                out.println("<a href=referTemplateRight.jsp?column1="+columnID1+"&column2="+columnID2+"&range="+range+"&start="+(start-range)+"><img src=../images/btn_previous.gif align=bottom border=0></a>"+start);
            out.println("</td><td width=50% align=right class=line>");
            if (start + range < total)
            {
                int remain = ((start+range-total)<range)?(total-start-range):range;
                out.println(remain+"<a href=referTemplateRight.jsp?column1="+columnID1+"&column2="+columnID2+"&range="+range+"&start="+(start+range)+"><img src=../images/btn_next.gif align=bottom border=0></a>");
            }
            out.println("</td></tr></table>");
        }
    %>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
        <tr class=tine><td colspan=8>当前栏目：<%=cname%></td></tr>
        <tr bgcolor=#dddddd class=tine height=20>
            <td align=center width='20%'>中文名称</td>
            <td align=center width='14%'>英文名称</td>
            <td align=center width='14%'>模板类型</td>
            <td align=center width='14%'>修改时间</td>
            <td align=center width='14%'>编辑</td>
            <td align=center width='8%'>默认</td>
            <td align=center width='8%'>预览</td>
            <td align=center width='8%'>引用</td>
        </tr>
        <%
            for (int i=0; i<templateCount; i++)
            {
                Model template = (Model)templateList.get(i);

                int templateID = template.getID();
                int isArticle = template.getIsArticle();
                String editor = template.getEditor();
                String lastupdated = template.getLastupdated().toString().substring(0,10);
                String templateName = template.getChineseName();
                if (templateName == null) templateName = "";
                templateName = StringUtil.gb2iso4View(templateName);

                String bgcolor = (i%2==0) ? "#ffffff" : "#eeeeee";
                out.println("<tr bgcolor=" + bgcolor + " height=22 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "'\">");
                out.println("<td>&nbsp;" + templateName + "</td>");
                out.println("<td>&nbsp;" + template.getTemplateName() + "</td>");

                if (isArticle == 2)
                    out.println("<td align=center>首页模板</td>");
                else if (isArticle == 3)
                    out.println("<td align=center>专题模板</td>");
                else if (isArticle == 0)
                    out.println("<td align=center>栏目模板</td>");
                else
                    out.println("<td align=center>文章模板</td>");

                out.println("<td align=center>" + lastupdated + "</td>");
                out.println("<td>&nbsp;" + editor + "</td>");

                if (isArticle != 3 && template.getDefaultTemplate() == 1)
                    out.println("<td align=center><img src='../images/button/moren.gif' align=bottom border=0></td>");
                else
                    out.println("<td align=center>&nbsp;</td>");
                out.println("<td align=center><a href=javascript:Preview("+templateID+","+isArticle+");><img src=../images/button/view.gif border=0></a></td>");
                out.println("<td align=center><a href=javascript:ReferModel("+templateID+");><img src=../images/button/edit.gif border=0></a></td></tr>");
            }
        %>
    </table>
</center>
</BODY>
</html>
