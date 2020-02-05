<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.io.FileReader" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="java.util.List" %>
<%@ page import="org.jdom.Element" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.selfDefine.SelfDefine" %>
<%@ page import="com.bizwink.cms.selfDefine.ISelfDefineManager" %>
<%@ page import="com.bizwink.cms.selfDefine.SelfDefinePeer" %>
<%@page contentType="text/html;charset=GBK" %>
<%
    String realpath = request.getRealPath("/");
    FileReader fr = new FileReader(realpath + File.separator + "webfeedback" + File.separator + "sam1.xml");
    BufferedReader br = new BufferedReader(fr);

    int l;
    String readoneline = null;
    StringBuffer sb = new StringBuffer();
    while((readoneline = br.readLine()) != null){
        sb.append(readoneline+"\r\n");
    }
    br.close();

    ISelfDefineManager selfDefManager = SelfDefinePeer.getInstance();
    List nv = new ArrayList();
    SelfDefine sd = null;
    XMLProperties properties = new XMLProperties(StringUtil.iso2gb(sb.toString()),0);
    List fields = properties.getFirstLevelChildrens();
    String[] vv = new String[fields.size()];
    for(int i=0; i<fields.size(); i++) {
        sd = new SelfDefine();
        Element el = (Element) fields.get(i);
        List vl = el.getChildren();
        Element el1 = (Element) vl.get(0);
        vv[i] = ParamUtil.getParameter(request,el1.getValue());
        sd.setName(el1.getValue());
        sd.setValue(vv[i]);
        //System.out.println(el1.getName() + "=====" + el1.getValue() +"===" + vv[i]);

        el1 = (Element) vl.get(1);
        sd.setType(el1.getValue());
        //System.out.println(el1.getName() + "=====" + el1.getValue() +"===" + el1.getValue());

        el1 = (Element) vl.get(2);
        if (el1.getValue() != null) {
            sd.setSize(Integer.parseInt(el1.getValue()));
            //System.out.println(el1.getName() + "=====" + el1.getValue() +"===" + Integer.parseInt(el1.getValue()));
        }
        nv.add(sd);
    }

    //生成检查输入值合法性的javascript
    StringBuffer jsbf=new StringBuffer();
    String passwd1="";
    String passwd2="";
    for(int i=0;i<nv.size(); i++) {
        sd = new SelfDefine();
        sd = (SelfDefine)nv.get(i);
        if (sd.getType().equals("text")) {
            jsbf.append("   if (!InputValid(form."+sd.getName() + ", 1, \"string\", 0, 0, 0,\"" + sd.getName() + "\"))\r\n" +
                        "\t\treturn (false);\r\n");
        }
        if (sd.getName().equals("passwd")) {
            passwd1 =sd.getName();
        }
        if (sd.getName().equals("repasswd")) {
            passwd2 =sd.getName();
        }
    }

    if (passwd1!="" && passwd2!="") {
        //jsbf.append("alert(form."+ passwd1 + ".value);\r\n");
        //jsbf.append("alert(form."+ passwd2 + ".value);\r\n");
        jsbf.append("   if (!validpassword(form."+ passwd1 + ".value, " + "form." + passwd2 + ".value ))\r\n" +
              "\t\treturn (false);\r\n");
    }

    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    if (startflag == 1) {
        selfDefManager.insertData(nv);
    }
%>
<html>
<head>

</head>
<script LANGUAGE="JavaScript" SRC="../js/check.js"></script>
<script type="text/javascript">
    function golist(r){
        window.location = "index.jsp?startrow="+r;
    }
    function check(form){
        <%=jsbf.toString()%>
        return true;
    }
</script>
<body>
<center>
    <table cellpadding="0" cellspacing="0" border="0">
        <form name="form" method="post" action="sample.jsp" onsubmit="return check(form);">
            <input type="hidden" name="siteid" value="">
            <input type="hidden" name="startflag" value="1">
            <tr>
                <td valign="top">
                    用户名：
                </td>
                <td valign="top">
                    <input type="text" name="userid" value="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    用户口令：
                </td>
                <td valign="top">
                    <input type="password" name="passwd" value="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    确认口令：
                </td>
                <td valign="top">
                    <input type="password" name="repasswd" value="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    Email：
                </td>
                <td valign="top">
                    <input type="text" name="email" value="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    电话：
                </td>
                <td valign="top">
                    <input type="text" name="phone" value="">
                </td>
            </tr>
            <tr>
                <td valign="top">
                    性别：
                </td>
                <td valign="top">
                    <input type="radio" name="sex" value="1" checked="true">男
                    <input type="radio" name="sex" value="0">女
                </td>
            </tr>
            <tr>
                <td valign="top">
                    地址：
                </td>
                <td valign="top">
                    <select name="address">
                        <option value="1">北京</option>
                        <option value="2">天津</option>
                        <option value="3">上海</option>
                        <option value="4">广州</option>
                        <option value="4">重庆</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td valign="top" colspan="2">
                    <input type="submit" name="sub" value="提交">
                </td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>