<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.update.UpdatePeer" %>
<%@ page import="com.bizwink.update.IUpdateManager" %>
<%@ page import="com.bizwink.update.Update" %>
<%@ page import="com.bizwink.cms.util.CmsException" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    String content = "";
    int columnid = 0;
    int id = 0;

    IUpdateManager updateMgr = UpdatePeer.getInstance();
    List list = new ArrayList();

    try {
        list = updateMgr.getAllColumnMark();
    } catch (CmsException e) {
        e.printStackTrace();
    }

    //修改模板中有MARKID的TAG
    if (list != null) {
        for (int i = 0; i < list.size(); i++) {
            Update update = (Update) list.get(i);
            id = update.getId();
            content = update.getContent();
            content = StringUtil.gb2iso4View(content);

            try {
                updateMgr.updateMarkContent(id, content);

                out.println("标记 id=" + id + " 更新完成<br>");
            } catch (CmsException e) {
                e.printStackTrace();
            }
        }
    }
%>