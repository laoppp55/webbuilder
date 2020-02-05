<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.news.Producttype" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page contentType="text/html;charset=utf-8" %>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    //读取该栏目的分类
    IColumnManager columnManager = ColumnPeer.getInstance();
    List pvalues = columnManager.getSecondType(id);
    if (id > 0) {
        String retstr = "<select name=typevalue style=\"width:120;font-size:9pt\"><option value=\"0\" selected>全部</option>";
        for (int i = 0; i < pvalues.size(); i++) {
            Producttype pv = (Producttype) pvalues.get(i);
            String vname = new String(pv.getValues().getBytes("iso8859_1"), "GBK");
            retstr = retstr + "<option value=\"" + pv.getValueid() + "\">" + vname + "</option>";
        }
        retstr = retstr + "</select>";
        out.print(retstr);
    } else {
        String retstr = "<select name=typevalue style=\"width:120;font-size:9pt\"><option value=\"-1\" selected>请选择</option>";
        retstr = retstr + "</select>";
        out.print(retstr);
    }
%>