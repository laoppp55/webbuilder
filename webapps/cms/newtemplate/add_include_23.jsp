<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.xml.*,
                 com.bizwink.cms.news.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>
<%@ page import="com.bizwink.cms.modelManager.Model" %>

<%
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

    int listType = 0;
    int order = 0;
    String notes = "";
    String cname = "包含文件";
    List ids = new ArrayList();

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        String content = ParamUtil.getParameter(request, "contents");
        String relatedCID = "()";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setMarkType(3);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(0);
        mark.setRelatedColumnID(relatedCID);

        int orgmarkid = markID;
        if (markID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String viewer = request.getHeader("user-agent");
        if (viewer.toLowerCase().indexOf("gecko") == -1)
            out.println("<script>window.returnValue=\"[TAG][MARKID]" + markID+"_"+columnID + "[/MARKID][/TAG]\";window.close();</script>");
        else {
            if(orgmarkid > 0){
                out.println("<script>top.close();</script>");
            }else{
                String returnvalue = "[TAG][MARKID]" + markID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + cname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.parent.opener.InsertHTML('content',returnvalue);top.close();</script>");
            }
        }
        return;
    }

    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        String articleIDs = properties.getProperty(properties.getName().concat(".INLUCDE.ID"));
        IModelManager modelMgr = ModelPeer.getInstance();
        ids = modelMgr.getIncludeFileMaintitleString(articleIDs);

        if (properties.getProperty(properties.getName().concat(".ORDER")) != null)
            order = Integer.parseInt(properties.getProperty(properties.getName().concat(".ORDER")));
        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null) notes = "";
    }

%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type="text/css" href="style.css">
    <script language="javascript" src="../js/mark.js"></script>
</head>

<body>
<form action="add_include_23.jsp" method="POST" name=markForm>
<input type=hidden name=doCreate value=true>
<input type=hidden name=column value="<%=columnID%>">
<input type=hidden name=mark value="<%=markID%>">
<input type=hidden name=contents>
<table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">
        <td width="100%">选中的文件：<br>
            <table>
                <tr>
                    <td>
                        <select id="selectedArticle" name="selectedArticle" style="width:260" size="4">
                            <%
                                for (int i = 0; i < ids.size(); i++) {
                                    String temp = (String) ids.get(i);
                                    IModelManager modelMgr = ModelPeer.getInstance();

                                    Model model = modelMgr.getModel(Integer.parseInt(temp.substring(0, temp.indexOf("|"))));
                                    IColumnManager columnManager = ColumnPeer.getInstance();
                                    Column column = columnManager.getColumn(model.getColumnID());

                                    out.println("<option value=\"" + temp.substring(0, temp.indexOf("|")) + "\">" + StringUtil.gb2iso4View(temp.substring(temp.indexOf("|") + 1))+"("+column.getDirName()+model.getTemplateName()+"."+column.getExtname()+")" + "</option>");
                                }
                            %>
                        </select></td>
                    <td>
                        <input type="button" name="delete" value="删除" onclick="delArticleItem();"
                               style="height:20;width:30;font-size:9pt">
                    </td>
                </tr>
            </table>
        </td>
        
    </tr>
</table>
    <table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">

        <td width="100%">
            <table width="100%" border=0>
                <tr>
                    <td>包含类型：<br>
                        <input type=radio name=order value=0 <%if(order==0){%> checked<%}%>>jsp包含<input type=radio name=order value=1 <%if(order==1){%> checked<%}%> checked>shtml包含
                        <input type=radio name=order value=2 <%if(order==2){%> checked<%}%>>html包含<input type=radio name=order value=3 <%if(order==3){%> checked<%}%>>asp包含
                        <input type=radio name=order value=4 <%if(order==4){%> checked<%}%>>php包含
                    </td>
                </tr>

            </table>
        </td>
    </tr>
</table>
<table width="100%" border=0 cellpadding=4 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">
        <td width="50%">
            <p>标记中文名称：<br>&nbsp;<input name=chineseName size=20 value="<%=cname%>"></p>
        </td>
        <td width="50%">
            标记描述：<br><textarea rows="4" id="notes" cols="37"><%=notes%>
        </textarea>
        </td>
    </tr>
    <tr bgcolor="#F5F5F5" height=40>
        <td align="center" colspan=2>
            <input type="button" value="  确定  " onclick="createInlucdeFile(0);">&nbsp;&nbsp;
            <input type="button" value="  取消  " onclick="top.close();">
        </td>
    </tr>
</table>
</form>
</body>
</html>
