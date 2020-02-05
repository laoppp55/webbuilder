<%@ page import="java.util.*,
                 com.bizwink.cms.xml.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.cms.server.CmsServer"
         contentType="text/html;charset=utf-8"
        %>

<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");

    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    IMarkManager markMgr = markPeer.getInstance();
    ViewFile viewfile = new ViewFile();

    int area = 1;
    int artNum = 4;
    int letterNum = 0;
    int listType = 0;
    int matchType = 0;
    String keyword = "";
    String columnIDs = "";
    String columns = "";
    String[] columnsID = null;
    String[] columnsName = null;
    String cname = "相关文章";
    String notes = "";

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        listType = ParamUtil.getIntParameter(request, "listType", 0);
        String content = ParamUtil.getParameter(request, "content");

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setMarkType(4);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(0);
        mark.setFormatFileNum(listType);
        mark.setRelatedColumnID("()");

        int orgmarkid = markID;
        if (markID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);


        if (orgmarkid > 0) {
            out.println("<script>top.close();</script>");
        } else {
            String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + cname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }
    }

    if (markID > 0) {
        String str = markMgr.getAMarkContent(markID);
        if (CmsServer.getInstance().getCustomer().equalsIgnoreCase("linktone"))
            str = StringUtil.gb2iso(str);
        else
            str = StringUtil.gb2iso4View(str);

        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);

        artNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLENUM")));
        if (str.indexOf("<LETTERNUM>") > -1)
            letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".LETTERNUM")));
        listType = Integer.parseInt(properties.getProperty(properties.getName().concat(".LISTTYPE")));
        area = Integer.parseInt(properties.getProperty(properties.getName().concat(".AREA")));
        if (area == 1) {
            columnIDs = properties.getProperty(properties.getName().concat(".COLUMNIDS"));
            if (str.indexOf("<COLUMNS>") > -1)
                columns = properties.getProperty(properties.getName().concat(".COLUMNS"));
            columnsName = columns.split(",");
            columnsID = columnIDs.substring(1, columnIDs.length() - 1).split(",");
        }
        if (str.indexOf("<KEYWORD>") > -1) {
            keyword = properties.getProperty(properties.getName().concat(".KEYWORD"));
            if (keyword == null) keyword = "";
        }
        if (str.indexOf("<MATCHTYPE>") > -1) {
            matchType = Integer.parseInt(properties.getProperty(properties.getName().concat(".MATCHTYPE")));
        }

        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null) notes = "";
    }

    List list = viewfileMgr.getViewFileC(siteID, 3);
%>

<html>
<head>
    <title>相关文章列表</title>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/mark.js"></script>
</head>

<body bgcolor="#CCCCCC">
<form action="addRelateRight.jsp" method="POST" name=markForm>
<input type=hidden name=doCreate value=true>
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=mark value="<%=markID%>">
<input type=hidden name=content>
<table width="100%" border="1">
    <tr height=20>
        <td>
            <input type="radio" name="matchType" value="0" <%if(matchType==0){%> checked<%}%>>模糊匹配&nbsp;&nbsp;
            <input type="radio" name="matchType" value="1" <%if(matchType==1){%> checked<%}%>>精确匹配
        </td>
    </tr>
    <tr height=30>
        <td>
            <input type="radio" name="keytype" value="0" <%if(keyword==""){%> checked<%}%>
                   onclick="keyword.disabled=true;">按文章关键字&nbsp;&nbsp;
            <input type="radio" name="keytype" value="1" <%if(keyword!=""){%> checked<%}%>
                   onclick="keyword.disabled=false;">指定关键字
            <input name="keyword" size=20 value="<%=keyword%>" <%if(keyword==""){%> disabled<%}%>>
        </td>
    </tr>
    <tr height=30>
        <td>
            选择前&nbsp;<input name="artNum" size=3 value=<%=artNum%>>&nbsp;篇文章&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" name="area" value="0" <%if(area==0){%> checked<%}%> onclick="selectArea(1);">整个站点
            <input type="radio" name="area" value="1" <%if(area==1){%> checked<%}%> onclick="selectArea(0);">指定栏目
        </td>
    </tr>
    <tr height=30>
        <td>列表类型：
            <select id="listType" name="listType" style="width:140px;font-size:9pt">
                <option value="0">选择列表样式</option>
                <%
                    for (int i = 0; i < list.size(); i++) {
                        viewfile = (ViewFile) list.get(i);
                %>
                <option value="<%=viewfile.getID()%>" <%if (viewfile.getID() == listType) {%> selected<%}%>><%=
                    StringUtil.gb2iso4View(viewfile.getChineseName())%>
                </option>
                <%}%>
            </select>
            <input type="button" style="height:20px;width:50px;font-size:9pt" onclick="createStyle(3,<%=columnID%>);" value="新建">
            <input type="button" style="height:20px;width:50px;font-size:9pt" onclick="updateStyle(3,listType.options[listType.selectedIndex].value,<%=columnID%>);" value="修改">
            <input type="button" style="height:20px;width:50px;font-size:9pt" onclick="previewStyle(3,listType.options[listType.selectedIndex].value);" value="预览">
        </td>
    </tr>
    <tr height=30>
        <td>
            标题字数：<input name="letterNum" size=6 value=<%=letterNum%>>
        </td>
    </tr>
    <tr>
        <td align=center>
            <table width="100%">
                <tr>
                    <td width=145 align="center">
                        <select id="selectedColumn" name="selectedColumn" style="width:140px" size="8" onDblClick="defineAttr();"
                                class=tine <%if (area == 0) {%> disabled<%}%>>
                            <%
                                if (markID > 0 && area == 1) {
                                    for (int i = 0; i < columnsID.length; i++) {
                                        if (!columnsName[i].equals("*")) {
                                            if (columnsID[i].indexOf("-getAllSubArticle") > -1) {
                                                columnsID[i] = columnsID[i].substring(0, columnsID[i].indexOf("-"));
                                                columnsName[i] = columnsName[i] + "-getAllSubArticle";
                                            }
                            %>
                            <option value="<%=columnsID[i]%>"><%=columnsName[i]%>
                            </option>
                            <%
                                        }
                                    }
                                }
                            %>
                        </select>
                        <input type="button" name="deleteButton" value="删除" onclick="delItem();"
                               style="height:20px;width:50px;font-size:9pt">
                    </td>
                    <td valign=top>
                        <table width="100%">
                            <tr>
                                <td>中文名称：<input name="chineseName" value="<%=cname%>" ></td>
                            </tr>
                            <tr>
                                <td>标记描述：<br><textarea rows=5 cols=30 name="notes" ><%=notes%>
                                </textarea></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr height=40>
        <td align="center">
            <input type=button class=tine onclick="createRelateList();" value="确 定">
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type=button class=tine onclick="cal();" value="取 消">
        </td>
    </tr>
</table>
</form>
</body>
</html>