<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jdom.input.SAXBuilder" %>
<%@ page import="java.io.Reader" %>
<%@ page import="java.io.StringReader" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttr" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="com.bizwink.util.zTreeNodeObj" %>
<%@ page import="com.bizwink.util.JSON_Str_To_ObjArray" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-1-14
  Time: 下午4:32
  To change this template use File | Settings | File Templates.
--%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    String ename = ParamUtil.getParameter(request,"ename");
    String textval = ParamUtil.getParameter(request,"textval");

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String xmlTemplate = column.getXMLTemplate();
    ExtendAttr extend = null;
    List<zTreeNodeObj> nodeObjs = null;

    if (xmlTemplate != null && xmlTemplate.trim().length() > 0) {
        SAXBuilder builder = new SAXBuilder();
        Reader in = new StringReader(xmlTemplate);
        org.jdom.Document doc = builder.build(in);

        List nodeList = doc.getRootElement().getChildren();
        for (int i = 0; i < nodeList.size(); i++) {
            org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
            int type = Integer.parseInt(e.getAttributeValue("type"));

            if (e.getName().equals(ename)) {
                extend = new ExtendAttr();
                extend.setCName(e.getText());
                extend.setEName(e.getName());
                extend.setControlType(type);
                extend.setDefaultValue(e.getAttributeValue("defaultval"));
                extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));
                break;
            }
        }

        if (extend!=null) {
            String option_key_values = extend.getDefaultValue();
            JSONObject jsStr = JSONObject.fromObject(option_key_values);
            JSONArray jsonArray = jsStr.getJSONArray("data");
            nodeObjs = JSON_Str_To_ObjArray.Transfer_JsonStr_To_ObjArray(jsonArray);
        }
    }

%>
<html>
<head>
    <title>请选择<%=(nodeObjs!=null)?nodeObjs.get(0).getName():""%></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="../js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script language=javascript>
        function doSelect() {
            var ename = "<%=ename%>";
            var selectval = "[";
            var checkboxObj=document.getElementsByName("types");
            var textval = "";
            var values = "";
            for(var i=0; i<checkboxObj.length; i++){
                if(checkboxObj[i].checked){
                    //alert(checkboxObj[i].value+","+checkboxObj[i].nextSibling.nodeValue);
                    textval = textval + checkboxObj[i].nextSibling.nodeValue + ",";
                    values = values + checkboxObj[i].value + ",";
                    selectval = selectval + "{name:'" + checkboxObj[i].nextSibling.nodeValue  + "',value:'" + checkboxObj[i].value + "'},";
                }
            }
            if (selectval.length>1) selectval = selectval.substring(0,selectval.length-1);
            selectval = selectval + "]";
            /*var nodes = eval('(' + selectval + ')');
            for(var ii=0; ii<nodes.length; ii++) {
                textval = textval + nodes[ii].name + ",";
                values = values + nodes[ii].value + ",";
            }*/
            if (textval.length>0) {
                textval = textval.substring(0,textval.length-1);
                eval("window.opener.document.createForm." + ename + "txt").value = textval;
            }
            if (values.length>0) {
                values = values.substring(0,values.length-1);
                eval("window.opener.document.createForm." + ename).value = values;
            }
            window.close();
        }
    </script>
</head>
<body>
<form name="selform">
    <%
        if (nodeObjs!=null) {
            int rows = nodeObjs.size()/4;
            int more = nodeObjs.size() % 4;
            String[] t_values = null;
            if (textval !=null) t_values = textval.split(",");
            for(int row=0;row<rows;row++){
                int start_row = row*4+1;
                int end_row = row*4+5;
                for(int ii=start_row;ii<end_row;ii++) {
                    zTreeNodeObj nodeObj = nodeObjs.get(ii);
                    String tbuf = nodeObj.getName();
                    int posi = tbuf.indexOf("|");
                    String text = tbuf.substring(0,posi);
                    String keyval = tbuf.substring(posi+1);
                    //判断当前项是否已经被选择
                    boolean the_item_checked_flag = false;
                    if (t_values!=null) {
                        for(int jj=0;jj<t_values.length;jj++) {
                            if (keyval.trim().equals(t_values[jj].trim())) {
                                the_item_checked_flag = true;
                                break;
                            }
                        }
                    }
                    if (the_item_checked_flag)
                        out.println("<input type='checkbox' name='types' value='" + keyval +"' checked>" + text);
                    else
                        out.println("<input type='checkbox' name='types' value='" + keyval +"'>" + text);
                }
                out.println("</br>");
            }

            if (more>0) {
                int start_row = rows*4+1;
                int end_row = nodeObjs.size();
                for(int ii=start_row;ii<end_row;ii++) {
                    zTreeNodeObj nodeObj = nodeObjs.get(ii);
                    String tbuf = nodeObj.getName();
                    int posi = tbuf.indexOf("|");
                    String text = tbuf.substring(0,posi);
                    String keyval = tbuf.substring(posi+1);
                    //判断当前项是否已经被选择
                    boolean the_item_checked_flag = false;
                    if (t_values!=null) {
                        for(int jj=0;jj<t_values.length;jj++) {
                            if (keyval.trim().equals(t_values[jj].trim())) {
                                the_item_checked_flag = true;
                                break;
                            }
                        }
                    }
                    if (the_item_checked_flag)
                        out.println("<input type='checkbox' name='types' value='" + keyval +"' checked>" + text);
                    else
                        out.println("<input type='checkbox' name='types' value='" + keyval +"'>" + text);
                }
            }
        }
    %>
    </br>
    <input type="button" name="dosel" value="确认" onclick="javascript:doSelect();"/>
    <input type="button" name="cancel" value="关闭"  onclick="javascript:window.close();"/>
</form>
</body>
</html>
