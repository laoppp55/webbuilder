<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.sitesetting.ISiteInfoManager" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoPeer" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfo" %>
<%@ page import="com.bizwink.cms.sitesetting.SiteInfoException" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int samsitetype = authToken.getSamSitetype();
    int tempnum=authToken.getShareTemplatenum();

    ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();
    int cssjsdir = 0;
    try {
        SiteInfo siteInfo = siteInfoMgr.getSiteInfo(siteid);
        cssjsdir = siteInfo.getCssjsDir();
    } catch (SiteInfoException e) {
        e.printStackTrace();
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int msgno = ParamUtil.getIntParameter(request, "msgno", 2);

    int modelType = ParamUtil.getIntParameter(request, "isArticle", 0);
    String templateName = "";
    if (modelType == 2)
        templateName = "首页模板";
    else if (modelType == 3)
        templateName = "专题模板";
    else if (modelType == 0)
        templateName = "栏目模板";
    else if (modelType == 1)
        templateName = "文章模板";
    //else if (modelType == 4)
    //    templateName = "WAP栏目模板";
    //else
    //    templateName = "WAP文章模板";

    int lockflag = 1;

    IModelManager modelManager = ModelPeer.getInstance();
    int total = modelManager.getModelCount(siteid,samsiteid,samsitetype,columnID,tempnum);
    List templateList = modelManager.getModels(siteid,samsiteid,samsitetype,columnID,tempnum, start, range);
    int templateCount = templateList.size();
%>

<html>
<head>
    <title>所有模板</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/setday.js"></script>
    <script language="javascript">
        <!--%if(cssjsdir == 1){%-->
        function UploadModel()
        {
            window.open("../upload/modelupload.jsp?column=<%=columnID%>", "", "width=650,height=520,left=200,top=100,status");
        }
        <!--%}else{%-->
        //function UploadModel()
        //{
        //    window.open("../upload/modelupload2.jsp?column=<%=columnID%>", "", "width=350,height=200,left=200,top=200");
        //}
        <!--%}%-->

        function SelectSystemModel()
        {
            window.open("cmstemplate.jsp?column=<%=columnID%>", "", "width=800,height=600,left=5,top=5,status");
        }

        function ReferModel(id)
        {
            window.open("refertemplate.jsp?column=<%=columnID%>&rightid=<%=rightid%>", "", "width=800,height=600,left=5,top=5,status");
        }

        function CreateModel()
        {
            window.open("createtemplate.jsp?column=<%=columnID%>", "", "width=1000,height=900,left=5,top=5,status");
        }

        function Preview(templateID, isArticle)
        {
            window.open("previewTemplate.jsp?column=<%=columnID%>&template=" + templateID + "&isArticle=" + isArticle, "PreviewTemplate", "width=800,height=600,left=0,top=0,scrollbars");
        }

        function EditTemplate(templateID, isArticle, referModelID)
        {
            if (referModelID > 0)
                window.open("editReferTemplate.jsp?template=" + templateID + "&column=<%=columnID%>", "", "width=400,height=200,left=300,top=200,status");
            else
                window.open("newedittemplate.jsp?template=" + templateID + "&column=<%=columnID%>&isArticle=" + isArticle + "&rightid=<%=rightid%>", "", "width=1000,height=900,left=5,top=5,status");
        }

        function ref() {
            alert("aaa");
        }
        function opencopytemplate(templateID)
        {
            if(templateID>0)
            {
                window.open("copytemplate.jsp?template=" + templateID + "&column=<%=columnID%>", "", "width=956,height=600,left=5,top=5,status");
            }
        }
    </script>
</head>

<BODY BGCOLOR=#ffffff LINK=#000099 ALINK=#cc0000 VLINK=#000099 TOMARGIN=8>
<%
    String[][] titlebars = {
            {"模板管理", ""}
    };
    String[][] operations = {
            {"<a href=\"javascript:ReferModel(" + rightid + ");\">引用模板</a>", ""},
            {"<a href=\"javascript:UploadModel();\">上传模板/图像</a>", ""},
            {"<a href=\"javascript:CreateModel();\">装入模板</a>", ""}
    };
%>
<%@ include file="../inc/titlebar.jsp" %>
<%
    out.println("<center>");
    if (msgno != 2) out.print("<span class=cur>" + templateName + "</span> ");
    if (msgno == 0)
        out.println("<span class=cur>发布成功</span>");
    else if (msgno == 1)
        out.println("<span class=cur>该模板被设置为默认模板</span>");
    else if (msgno == -1)
        out.println("<span class=cur>某栏目没有设置索引页模板</span>");
    else if (msgno == -2)
        out.println("<span class=cur>写文件时出现错误</span>");
    else if (msgno == -3)
        out.println("<span class=cur>生成文章列表文件时出现错误！</span>");
    else if (msgno == -4)
        out.println("<span class=cur>模板的文件名为空</span>");
    else if (msgno == -5)
        out.println("<span class=cur>模板被成功删除</span>");
    out.println("</center>");

    if (templateCount > 0) {
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% class=line>");
        if (start - range >= 0)
            out.println("<a href=templates.jsp?column=" + columnID + "&range=" + range + "&start=" + (start - range) + "><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=templates.jsp?column=" + columnID + "&range=" + range + "&start=" + (start + range) + "><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width='100%'>
    <tr bgcolor=#dddddd class=tine height=20>
        <td align=center width='15%'>中文名称</td>
        <td align=center width='11%'>英文名称</td>
        <td align=center width='12%'>模板类型</td>
        <td align=center width='10%'>修改时间</td>
        <td align=center width='12%'>编辑</td>
        <td align=center width='8%'>默认</td>     
        <td align=center width='8%'>修改</td>
        <td align=center width='8%'>删除</td>
        <td align=center width='8%'>预览</td>
        <td align=center width='8%'>发布</td>
    </tr>
    <%
        for (int i = 0; i < templateCount; i++) {
            Model template = (Model) templateList.get(i);

            int templateID = template.getID();
            lockflag = template.getLockStatus();
            int isArticle = template.getIsArticle();
            int referModelID = template.getReferModelID();
            String editor = template.getEditor();
            String lastupdated = template.getLastupdated().toString().substring(0, 10);
            templateName = template.getChineseName();
            if (templateName == null) templateName = "";
            templateName = StringUtil.gb2iso4View(templateName);

            String bgcolor = (i % 2 == 0) ? "#ffffff" : "#eeeeee";
            out.println("<tr bgcolor=" + bgcolor + " height=25 onmouseover=\"this.style.background='#CECEFF';\" onmouseout=\"this.style.background='" + bgcolor + "';\">");
            out.println("<td>&nbsp;" + templateName + "</td>");
            out.println("<td>&nbsp;" + template.getTemplateName() + "</td>");

            out.print("<td align=center>");
            if (isArticle == 2)
                out.print("首页模板");
            else if (isArticle == 3)
                out.print("专题模板");
            else if (isArticle == 0)
                out.print("栏目模板");
            else if (isArticle == 1)
                out.print("文章模板");
            else if (isArticle == 4)
                out.print("WAP栏目模板");
            else
                out.print("WAP文章模板");

            if (referModelID > 0) out.print("(引用)");
            out.println("</td>");

            out.println("<td align=center>" + lastupdated + "</td>");
            out.println("<td>&nbsp;" + editor + "</td>");

            if (isArticle != 3) {
                out.println("<td align=center><a href=\"setupDefaultTemplate.jsp?template=" + templateID + "&column=" + columnID + "&isArticle=" + isArticle + "&templateName=" + templateName + "\">");
                if (template.getDefaultTemplate() == 1)
                    out.println("<img src=\"../images/button/moren.gif\" align=bottom border=0 alt=\"默认模板\"></a></td>");
                else
                    out.println("<img src=\"../images/button/morenyuan.gif\" align=bottom border=0 alt=\"选为默认\"></a></td>");
            } else {
                out.println("<td align=center>&nbsp;</td>");
            }

            if(siteid==template.getSiteID()){
            if (lockflag == 0)
                out.println("<td align=center><a href=\"javascript:EditTemplate(" + templateID + "," + isArticle + "," + referModelID + ");\"><img src=\"../images/button/edit.gif\" align=bottom border=0 alt=\"修改模板\"></a>");
            else if (SecurityCheck.hasPermission(authToken, 2) || SecurityCheck.hasPermission(authToken, 54))
                out.println("<td align=center><a href=\"javascript:EditTemplate(" + templateID + "," + isArticle + "," + referModelID + ");\"><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"模板锁定\"></a></td>");
            else
                out.println("<td align=center><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"模板锁定\"></td>");

            out.println("<td align=center><a href=\"removetemplate.jsp?template=" + templateID + "\">");
            out.println("<img src=\"../images/button/del.gif\" border=0 alt=\"删除模板\"></a></td>");
            }
            else{
                out.println("<td align=center><img src=\"../images/button/lock.gif\" align=bottom border=0 alt=\"模板锁定\"></td>");
                out.println("<td align=center><a href='javascript:opencopytemplate("+templateID+")'>查看模版</a></td>");
            }
            out.println("<td align=center><a href=\"javascript:Preview(" + templateID + "," + isArticle + ");\">");
            out.println("<img src=\"../images/button/view.gif\" border=0 alt=\"预览模板\"></a></td>");
            if (isArticle != 1 && (SecurityCheck.hasPermission(authToken, 6) || SecurityCheck.hasPermission(authToken, 54)))
                out.println("<td align=center><a href=\"../publish/publish.jsp?column=" + columnID + "&template=" + templateID + "&modelType=" + isArticle + "&source=2&start=" + start + "&range=" + range + "&templateName=" + templateName + "\"><img src=\"../images/button/pub.gif\" border=0></a></td></tr>");
            else
                out.println("<td align=center>&nbsp;</td></tr>");
        }
    %>
</table>

</BODY>
</html>
