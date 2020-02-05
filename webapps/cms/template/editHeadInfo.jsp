<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.viewFileManager.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int listType = 0;
    List styleList = new ArrayList();
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    try {
        styleList = viewfileMgr.getViewFileC(authToken.getSiteID(), 9);
    } catch (viewFileException e) {
        e.printStackTrace();
    }
%>

<html>
<head>
    <title>编辑页面头样式</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/global.css">
    <script language="javascript" src="../js/mark.js"></script>

    <script type="text/javascript">
        function addHeadStyle() {            
            var content = "[TITLESTYLE]" + document.getElementById('listType').options[document.getElementById('listType').selectedIndex].value + "[/TITLESTYLE]";
            window.returnValue = content;
            window.close();
        }
    </script>
</head>

<body>
<form action="editHeadInfo.jsp" method="Post" name=editForm>
    <input type=hidden name=doSave value=true>
    <br>
    <table border=0 width="98%" align=center>
        <tr height=25>
            <td align=right>选择样式文件：</td>
            <td><select id="listType" style="width:160;font-size:9pt">
                <option value="0">选择页面标题样式</option>
                <%
                    for (int i = 0; i < styleList.size(); i++) {
                        ViewFile viewfile = (ViewFile) styleList.get(i);
                %>
                <option value="<%=viewfile.getID()%>"
                        <%if(viewfile.getID()==listType){%>selected<%}%>><%=StringUtil.gb2iso4View(viewfile.getChineseName())%>
                </option>
                <%
                    }
                %>
            </select>
                <button id="button1" style="height:20;width:30;font-size:9pt" onClick="createStyle(9,0)">新建</button>
                <button id="button2" style="height:20;width:30;font-size:9pt"
                        onClick="updateStyle(9,listType.options[listType.selectedIndex].value,0)">修改
                </button>
                <button id="button3" style="height:20;width:30;font-size:9pt"
                        onClick="previewStyle(9,listType.options[listType.selectedIndex].value)">预览
                </button>
            </td>
        </tr>
        <tr height=40>
            <td width="100%" align=center colspan=2>
                <input type=button value="  确定  " class=tine onclick="addHeadStyle();">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type=button value="  取消  " class=tine onclick="top.close();">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
