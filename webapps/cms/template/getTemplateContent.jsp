<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.modelManager.*" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int imgflag = authToken.getImgSaveFlag();
    int cssjsdir = authToken.getCssJsDir();

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String path = ParamUtil.getParameter(request, "path");
    String fileDir = "";
    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = null;
    if (columnID>0)
        column = columnMgr.getColumn(columnID);
    else
        fileDir = "_prog";

    IModelManager modelPeer = ModelPeer.getInstance();
    String content = modelPeer.readModelFile(columnID,path, sitename, siteid, imgflag, cssjsdir);

    int posi = -1;
    while ((posi = content.toLowerCase().indexOf("<textarea")) > -1)
        content = content.substring(0, posi) + "<cmstextarea" + content.substring(posi + 9);
    while ((posi = content.toLowerCase().indexOf("</textarea>")) > -1)
        content = content.substring(0, posi) + "</cmstextarea>" + content.substring(posi + 11);

    StringBuffer sb = new StringBuffer();
    char[] c = content.toCharArray();
    for (int i = 0; i < c.length; i++) {
        if ((c[i] > 65376 && c[i] < 65440) || (c[i] > 1000 && c[i] < 10000))  //片假名及特殊符号
            sb.append("&#" + Integer.toString(c[i]) + ";");
        else
            sb.append(c[i]);
    }
    content = sb.toString();
    out.println(content);
%>
