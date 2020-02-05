<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.viewFileManager.*"
        contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="com.bizwink.webapps.survey.define.Define" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    //     修改编辑属性取值
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    int siteID = authToken.getSiteID();
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark=null;
    IDefineManager defineMgr = DefinePeer.getInstance();
    List definelist = defineMgr.getAllDefineSurveyForMark(siteID);//有效的调查信息
    List list = new ArrayList();
    String url = application.getRealPath("/") + "sites" + java.io.File.separator + authToken.getSitename() + java.io.File.separator + "css\\";
    File file = new File(url);
    String[] f = file.list();
    for (int i=0; i<f.length; i++) {
        File tf = new File(url + f[i]);
        if (tf.exists()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url + f[i])));
            String css = "";
            int bposi = -1;
            while ((css = br.readLine()) != null) {
                bposi = css.indexOf("{");
                if (bposi > -1) {
                    css = css.substring(0,bposi).trim();
                    list.add(css);
                }
            }
        }
    }
    if(markID>0)
    {

        vmark=markMgr.getAMark(markID);

    }


    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    String notes = "";
    String cname = "调查表单";

    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");


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

        if(orgmarkid > 0){
            out.println("<script>top.close();</script>");
        }else{
            String returnvalue = "[TAG][MARKID]" + markID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + cname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }
        return;
    }
    String wstytles = "";
    String astytles = "";
    String defineid = "0";
    int userinfo = 0;
    int yangshi = 0;
    String submitinfo = "";
    String submitpic = "";
    String resultinfo = "";
    String resultpic = "";
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        defineid = properties.getProperty(properties.getName().concat(".DEFINE.ID"));
        IModelManager modelMgr = ModelPeer.getInstance();

        if (properties.getProperty(properties.getName().concat(".WSTYTLE")) != null)
            wstytles = properties.getProperty(properties.getName().concat(".WSTYTLE"));
        if (properties.getProperty(properties.getName().concat(".ASTYTLE")) != null)
            astytles = properties.getProperty(properties.getName().concat(".ASTYTLE"));
        if (properties.getProperty(properties.getName().concat(".YANGSHI")) != null)
            yangshi = Integer.parseInt(properties.getProperty(properties.getName().concat(".YANGSHI")));
        if(properties.getProperty(properties.getName().concat(".USERINFO")) != null)
            userinfo = Integer.parseInt(properties.getProperty(properties.getName().concat(".USERINFO")));
        if (properties.getProperty(properties.getName().concat(".SUBMITINFO")) != null)
            submitinfo = properties.getProperty(properties.getName().concat(".SUBMITINFO"));
        if (properties.getProperty(properties.getName().concat(".SUBMITPICINFO")) != null)
            submitpic = properties.getProperty(properties.getName().concat(".SUBMITPICINFO"));
        if (properties.getProperty(properties.getName().concat(".RESULTINFO")) != null)
            resultinfo = properties.getProperty(properties.getName().concat(".RESULTINFO"));
        if (properties.getProperty(properties.getName().concat(".RESULTPICINFO")) != null)
            resultpic = properties.getProperty(properties.getName().concat(".RESULTPICINFO"));
        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        if (notes == null) notes = "";
    }
    //System.out.println("gaoji="+gaoji);
%>

<html>
<head>
    <base target="_self" >
    <title>定义调查表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/mark.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function view(){
            var defineid = document.getElementById("defineinfo").value;
            var definestytle = document.getElementById("wstytle").value;
            var answerstytle = document.getElementById("astytle").value;
            var len = 0;
            var y = 0;
            len = markForm.yangshi.length;
            for (var i = 0; i < len; i++) {
                if (markForm.yangshi[i].checked) {
                    y = markForm.yangshi[i].value;
                    break;
                }
            }
            window.open("defineview.jsp?id="+defineid+"&wstytle="+definestytle+"&astytle="+answerstytle+"&yangshi="+y,"","width=400,height=500")
        }

        function selectpic(type){
            //alert("hello word");
            var returnval = window.showModalDialog("selectsubmitframe.jsp","","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
            if(type==0){
                document.getElementById("okimage").value = returnval;
            }else if(type==1){
                document.getElementById("cancelimage").value = returnval;
            }else if(type==2){
                document.getElementById("addressimage").value = returnval;
            }else if(type==6){
                document.getElementById("submitsimage").value = returnval;
            }else if(type==3){
                document.getElementById("sendwayimage").value = returnval;
            }else if(type==4){
                document.getElementById("paywayimage").value = returnval;
            }else if(type==5){
                document.getElementById("orderimage").value = returnval;
            }else if(type==7){
                document.getElementById("editsendwayimage").value = returnval;
            }else if(type==8){
                document.getElementById("editpaywayimage").value = returnval;
            }else if(type==9){
                document.getElementById("invoiceimage").value = returnval;
            }else if(type==10){
                document.getElementById("editinvoiceimage").value = returnval;
            }
            //window.open("selectsubmitpic.jsp?type="+type, "", "height=500, width=800, toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }
        function upload(type)
        {
            //alert("hello word");
            var returnval = window.showModalDialog("uploadsubmitpicframe.jsp","","font-family:Verdana;font-size:12;dialogWidth:900px; dialogHeight:400px;status:no");
            if(type==0){
                document.getElementById("okimage").value = returnval;
            }else if(type==1){
                document.getElementById("cancelimage").value = returnval;
            }else if(type==2){
                document.getElementById("addressimage").value = returnval;
            }else if(type==6){
                document.getElementById("submitsimage").value = returnval;
            }else if(type==3){
                document.getElementById("sendwayimage").value = returnval;
            }else if(type==4){
                document.getElementById("paywayimage").value = returnval;
            }else if(type==5){
                document.getElementById("orderimage").value = returnval;
            }else if(type==7){
                document.getElementById("editsendwayimage").value = returnval;
            }else if(type==8){
                document.getElementById("editpaywayimage").value = returnval;
            }else if(type==9){
                document.getElementById("invoiceimage").value = returnval;
            }else if(type==10){
                document.getElementById("editinvoiceimage").value = returnval;
            }
            //window.open("uploadsubmitpic.jsp?type="+type, "", "height=200, width=600 toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form name="markForm">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=23>
        <input type="hidden" name="mark" value="<%=markID%>">

        <input type="hidden" name="contents" id="contents">
        <tr height="30">
            <td>请选择调查信息：<select name="defineinfo" id="defineinfo">
                <%
                    for(int i=0;i<definelist.size();i++){
                        Define define= (Define)definelist.get(i);
                        if(define != null){
                            if(define.getId() == Integer.parseInt(defineid)){
                                out.print("<option value=\""+define.getId()+"\" selected>"+StringUtil.gb2iso4View(define.getSurveyname())+"</option>");
                            }else{
                                out.print("<option value=\""+define.getId()+"\">"+StringUtil.gb2iso4View(define.getSurveyname())+"</option>");
                            }
                        }
                %>
                <%}%>
            </select></td>
        </tr>
        <tr height="30">
            <td>请选择问题样式文件：<select name="wstytle" id="wstytle">
                <OPTION VALUE="-1">选择样式
                        <%
                                            for (int i = 0; i < list.size(); i++) {

                                                String xzyangshi = (String)list.get(i);
                                                String selects = "";
                                                if(xzyangshi.equals(wstytles)){
                                                     selects = " selected";
                                                }
                                        %>
                <OPTION VALUE="<%=xzyangshi%>" <%=selects%>><%=xzyangshi%>
                </OPTION>
                <%}%>
            </select></td>
        </tr>
        <tr height="30">
            <td>请选择答案样式文件：<select name="astytle" id="astytle">
                <OPTION VALUE="-1">选择样式
                        <%
                                            for (int i = 0; i < list.size(); i++) {

                                                String xzyangshi = (String)list.get(i);
                                                String selects = "";
                                                if(xzyangshi.equals(astytles)){
                                                     selects = " selected";
                                                }
                                        %>
                <OPTION VALUE="<%=xzyangshi%>" <%=selects%>><%=xzyangshi%>
                </OPTION>
                <%}%>
            </select></td>
        </tr>
        <tr>
            <td class="style1">
                确认按钮类型:<SELECT NAME="t24">
                <OPTION VALUE="submit"<%if(submitinfo.equals("submit")){%> SELECTED<%}%>>提交
                <OPTION VALUE="image"<%if(submitinfo.equals("image")){%> SELECTED<%}%>>图片
            </SELECT></td>
        </tr>
        <tr>
            <td class="style1">
                选择按钮图片：<input id="okimage" name="okiamge" type="text" value="<%=submitpic%>"><a href="javascript:selectpic(0);">选择已有图片</a>  <a href="javascript:upload(0);">上传新图片</a></td>
        </tr>
        <tr>
            <td class="style1">
                <input type="checkbox" name="fcheck25" value="返回"<%if(resultinfo!= null && !resultinfo.equals("") && resultpic!=null && !resultpic.equals("null")){%> checked="true"<%}%>>&nbsp;查看结果按钮类型:<SELECT NAME="t25">
                <OPTION VALUE="submit"<%if(resultinfo!=null && resultinfo.equals("submit")){%> SELECTED<%}%>>提交
                <OPTION VALUE="image"<%if(resultinfo!=null && resultinfo.equals("image")){%> SELECTED<%}%>>图片
            </SELECT></td>
        </tr>
        <tr>
            <td class="style1">
                选择按钮图片：<input id="cancelimage" name="cancelimage" type="text" value="<%=resultpic%>"><a href="javascript:selectpic(1);">选择已有图片</a> <a href="javascript:upload(1);">上传新图片</a></td>
        </tr>
        <tr><td>是否需要收集用户信息：<input type="radio" <%if(userinfo ==0){%>checked="checked"<%}%> value="0" name="userinfo">否&nbsp;&nbsp;<input type="radio" name="userinfo" value="1" <%if(userinfo ==1){%>checked="checked"<%}%>>是 </td><td></td></tr>
        <tr><td ><input type="radio" <%if(yangshi ==0){%>checked="checked"<%}%> value="0" name="yangshi">横向显示&nbsp;&nbsp;<input type="radio" name="yangshi" value="1" <%if(yangshi ==1){%>checked="checked"<%}%>>竖向显示 </td><td></td></tr>
        <tr height=24>
            <td>标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr height=80>
            <td>标记描述：<br><textarea rows="3" id="notes" cols="38" class=tine><%=notes%></textarea>
            </td>
        </tr>
        <tr height="50">
            <td align=center>
                <input type="button" value=" 预览 " onclick="view();">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 确定 " onClick = "createDefine();">&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "window.close();">
            </td>
        </tr>
    </form>
</table>
</body>
</html>