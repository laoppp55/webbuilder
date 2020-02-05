<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*"
        contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.webapps.survey.define.IDefineManager" %>
<%@ page import="com.bizwink.webapps.survey.define.DefinePeer" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.FileInputStream" %>

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
    int fieldnum = 12;
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark=null;
    IDefineManager defineMgr = DefinePeer.getInstance();
    List definelist = defineMgr.getAllDefineSurveyForMark(siteID);//有效的调查信息
    List list = new ArrayList();
    String url = application.getRealPath("/") + "sites" + java.io.File.separator + authToken.getSitename() + java.io.File.separator + "css\\";
    File file = new File(url);
    String[] f = file.list();
    if (f!=null) {
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
                        if (css.startsWith(".")) css = css.substring(1);
                        list.add(css);
                    }
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
    String auditRule = "";
    int auditflag = 0;
    String notes = "";
    String cname = "用户留言表单";
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        String content = "";
        String relatedCID = "()";
        String field = "";
        String gdescstyle = ParamUtil.getParameter(request,"g_desc_style");
        if (gdescstyle == null) gdescstyle="";
        String gcontentstyle=ParamUtil.getParameter(request,"gcontentstyle");
        if (gcontentstyle == null) gcontentstyle="";
        String gdescalign = ParamUtil.getParameter(request,"descalign");
        if (gdescalign == null) gdescalign="";
        String gcontentalign = ParamUtil.getParameter(request,"contentalign");
        if (gcontentalign == null) gcontentalign="";
        String xmlstr = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\r\n";
        xmlstr = xmlstr + "<form>\r\n";
        xmlstr = xmlstr + "<globalsetting>\r\n";
        xmlstr = xmlstr + "<gdescstyle>" + gdescstyle + "</gdescstyle>\r\n";

        xmlstr = xmlstr + "<globaldescalign>" + gdescalign + "</globaldescalign>\r\n";
        xmlstr = xmlstr + "<globalcontentalign>" + gcontentalign + "</globalcontentalign>\r\n";
        xmlstr = xmlstr + "<gcontentstyle>" + gcontentstyle + "</gcontentstyle>\r\n";
        xmlstr = xmlstr + "</globalsetting>\r\n";
        xmlstr = xmlstr + "<fields>\r\n";
        for (int i = 1; i <= fieldnum-2; i++) {
            field = request.getParameter("fcheck" + i);
            String ename = ParamUtil.getParameter(request,"ename" + i);
            String d_style = ParamUtil.getParameter(request,"desc_style" + i);
            if (d_style == null) d_style = "";
            String c_style = ParamUtil.getParameter(request,"c_style" + i);
            if (c_style == null) c_style = "";
            String ftype = ParamUtil.getParameter(request,"t" + i);
            if (ftype == null) ftype = "";
            String is_null = ParamUtil.getParameter(request,"k" + i);
            if (is_null == null) is_null = "";
            int fieldlen = ParamUtil.getIntParameter(request, "l" + i, 0);
            String order = ParamUtil.getParameter(request,"order" + i);
            if (field != null) {
                xmlstr = xmlstr +  "<field" + i + "><chinesename>" + field + "</chinesename><name>" + ename + "</name>\r\n" +
                        "<descstyle>" + d_style + "</descstyle>\r\n" +
                        "<contentstyle>" + c_style + "</contentstyle>\r\n" +
                        "<isnull>" + is_null + "</isnull>\r\n" +
                        "<order>" + order + "</order>\r\n" +
                        "<flen>" + fieldlen + "</flen>\r\n" +
                        "<type><name>"+ ftype +"</name><values>\r\n" +
                        "<value></value>\r\n" +
                        "</values></type>\r\n" +
                        "</field" + i + ">\r\n";
            }
        }

        //提交按钮
        String submittype = ParamUtil.getParameter(request,"t11");
        String submitimage = ParamUtil.getParameter(request,"okimage");
        if(submitimage == null) submitimage = "";
        xmlstr = xmlstr +  "<field11><chinesename>提交</chinesename><name>submits</name>\r\n" +
                "<descstyle>" + submittype + "</descstyle>\r\n" +
                "<contentstyle>" + submitimage + "</contentstyle>\r\n" +
                "<isnull>1</isnull>\r\n" +
                "<type><name></name><values>\r\n" +
                "<value></value>\r\n" +
                "</values></type>\r\n" +
                "</field11>\r\n";

        field = request.getParameter("fcheck12");
        if (field != null) {
            if (field.equals("重置")) {
                String submit = ParamUtil.getParameter(request,"t12");
                String images = ParamUtil.getParameter(request,"cancelimage");
                if(images == null) images = "";
                xmlstr = xmlstr +  "<field12><chinesename>重置</chinesename><name>reset</name>\r\n" +
                        "<descstyle>" + submit + "</descstyle>\r\n" +
                        "<contentstyle>" + images + "</contentstyle>\r\n" +
                        "<isnull>1</isnull>\r\n" +
                        "<type><name>"+ submit +"</name><values>\r\n" +
                        "<value></value>\r\n" +
                        "</values></type>\r\n" +
                        "</field12>\r\n";
            }
        }

        xmlstr = xmlstr +  "</fields>\r\n";
        xmlstr = xmlstr +  "</form>\r\n";

        auditRule = ParamUtil.getParameter(request, "auditrule");
        auditflag = ParamUtil.getIntParameter(request, "auditor",0);

        content = "[TAG][LEAVEMESSAGEINFO][SITEID]"+authToken.getSiteID()+"[/SITEID][AUDITFLAG]" + auditflag + "[/AUDITFLAG][AUDITRULE]<![CDATA[" + (auditRule==null?"":auditRule) + "]]>[/AUDITRULE][CONTENT]<![CDATA[" + (xmlstr==null?"":xmlstr) + "]]>[/CONTENT][CHINESENAME]"+cname+"[/CHINESENAME][NOTES]"+notes+"[/NOTES][/LEAVEMESSAGEINFO][/TAG]";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setMarkType(21);
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
    String global_desc_style = null;
    String global_content_style = null;
    String global_desc_align = null;
    String global_content_align = null;

    boolean titleflag = true;
    String title_desc_style = null;
    String title_content_style = null;
    String title_isnull = null;
    String title_len = null;
    String title_num = null;

    //内容
    boolean contentflag= true;
    String content_desc_style = null;
    String content_content_style = null;
    String content_isnull = null;
    String content_len = null;
    String content_num = null;
    //
    boolean companyflag= false;
    String company_desc_style = null;
    String company_content_style = null;
    String company_isnull = null;
    String company_len = null;
    String company_num = null;

    boolean emailflag= false;
    String email_desc_style = null;
    String email_content_style = null;
    String email_isnull = null;
    String email_len = null;
    String email_num = null;

    boolean linkmanflag= false;
    String linkman_desc_style = null;
    String linkman_content_style = null;
    String linkman_isnull = null;
    String linkman_len = null;
    String linkman_num = null;

    boolean linksflag= false;
    String links_desc_style = null;
    String links_content_style = null;
    String links_isnull = null;
    String links_len = null;
    String links_num = null;

    boolean zipflag= false;
    String zip_desc_style = null;
    String zip_content_style = null;
    String zip_isnull = null;
    String zip_len = null;
    String zip_num = null;

    boolean phoneflag= false;
    String phone_desc_style = null;
    String phone_content_style = null;
    String phone_isnull = null;
    String phone_len = null;
    String phone_num = null;

    boolean publishContactInfomationflag=false;
    String publishContactInfomation_desc_style = null;
    String publishContactInfomation_content_style = null;
    String publishContactInfomation_isnull = null;
    String publishContactInfomation_len = null;
    String publishContactInfomation_num = null;

    boolean varifycodeflag = false;
    String varifycode_desc_style = null;
    String varifycode_content_style = null;
    String varifycode_isnull = null;
    String varifycode_len = null;
    String varifycode_num = null;

    boolean submitflag = false;
    String submit = null;
    String submitimage = null;

    boolean resetflag = false;
    String reset = null;
    String resetimage = null;
    if (markID > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);

        cname = properties.getProperty(properties.getName().concat(".CHINESENAME"));
        notes = properties.getProperty(properties.getName().concat(".NOTES"));
        auditRule = properties.getProperty(properties.getName().concat(".AUDITRULE"));
        auditflag = Integer.parseInt(properties.getProperty(properties.getName().concat(".AUDITFLAG")));
        if (notes == null) notes = "";
        String content = properties.getProperty(properties.getName().concat(".CONTENT"));
        XMLProperties contentProperties = null;
        contentProperties = new XMLProperties(content.substring(0,content.length() -1));
        global_desc_style = contentProperties.getProperty("globalsetting.gdescstyle");
        global_content_style = contentProperties.getProperty("globalsetting.gcontentstyle");
        String b[] = contentProperties.getChildrenProperties("fields");
        global_desc_align = contentProperties.getProperty("globalsetting.globaldescalign");
        global_content_align = contentProperties.getProperty("globalsetting.globalcontentalign");
        for (int j = 0; j < b.length; j++) {
            String desc_style = contentProperties.getProperty("fields." + b[j] + ".descstyle");
            String content_style = contentProperties.getProperty("fields." + b[j] + ".contentstyle");
            String chinesename = contentProperties.getProperty("fields." + b[j] + ".chinesename");
            String name = contentProperties.getProperty("fields." + b[j] + ".name");
            String fieldtype = contentProperties.getProperty("fields." + b[j] + ".type.name");
            String fieldlen = contentProperties.getProperty("fields." + b[j] + ".flen");
            String isnull = contentProperties.getProperty("fields." + b[j] + ".isnull");
            String order = contentProperties.getProperty("fields." + b[j] + ".order");
            if (order == null || order.equalsIgnoreCase("null") || order.equals("")) order = "0";
            if (chinesename.equals("信件标题")) {
                System.out.println("order=" + order);
                titleflag = true;
                title_desc_style = desc_style;
                title_content_style = content_style;
                title_isnull = isnull;
                title_len = fieldlen;
                title_num = order;
            }
            if (chinesename.equals("信件内容")) {
                contentflag = true;
                content_desc_style = desc_style;
                content_content_style = content_style;
                content_isnull = isnull;
                content_len = fieldlen;
                content_num = order;
            }
            if (chinesename.equals("发信公司")) {
                companyflag = true;
                company_desc_style = desc_style;
                company_content_style = content_style;
                company_isnull = isnull;
                company_len = fieldlen;
                company_num = order;
            }
            if (chinesename.equals("电子邮件")) {
                emailflag = true;
                email_desc_style = desc_style;
                email_content_style = content_style;
                email_isnull = isnull;
                email_len = fieldlen;
                email_num = order;
            }
            if (chinesename.equals("姓    名")) {
                linkmanflag = true;
                linkman_desc_style = desc_style;
                linkman_content_style = content_style;
                linkman_isnull = isnull;
                linkman_len = fieldlen;
                linkman_num = order;
            }
            if (chinesename.equals("联系地址")) {
                linksflag = true;
                links_desc_style = desc_style;
                links_content_style = content_style;
                links_isnull = isnull;
                links_len = fieldlen;
                links_num = order;
            }
            if (chinesename.equals("邮政编码")) {
                zipflag = true;
                zip_desc_style = desc_style;
                zip_content_style = content_style;
                zip_isnull = isnull;
                zip_len = fieldlen;
                zip_num = order;
            }
            if (chinesename.equals("联系电话")) {
                phoneflag = true;
                phone_desc_style = desc_style;
                phone_content_style = content_style;
                phone_isnull = isnull;
                phone_len = fieldlen;
                phone_num = order;
            }
            if (chinesename.equals("是否对外公开联系信息")) {
                publishContactInfomationflag = true;
                publishContactInfomation_desc_style = desc_style;
                publishContactInfomation_content_style = content_style;
                publishContactInfomation_isnull = isnull;
                publishContactInfomation_len = fieldlen;
                publishContactInfomation_num = order;
            }
            if (chinesename.equals("校验码")) {
                varifycodeflag = true;
                varifycode_desc_style = desc_style;
                varifycode_content_style = content_style;
                varifycode_isnull = isnull;
                varifycode_len = fieldlen;
                varifycode_num = order;
            }
            if (chinesename.equals("提交")) {
                submitflag = true;
                submit = desc_style;
                submitimage = content_style;

            }
            if (chinesename.equals("重置")) {
                resetflag = true;
                reset = desc_style;
                resetimage = content_style;
            }
        }
    }
    //System.out.println("gaoji="+gaoji);
%>

<html>
<head>
    <base target="_self" >
    <title>定义留言表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/mark.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function doit()
        {
            markform.action = "addUserLeavemessageForm.jsp";
            markform.method = "post";
            markform.target = "_self"
            markform.submit();
        }

        function quanxuan() {
            for (var i = 1; i <= <%=fieldnum%>; i++) {
                eval("markform.fcheck" + i).checked = true;
            }
        }

        function fanxuan() {
            for (var i = 1; i <= <%=fieldnum%>; i++) {
                if (eval("markform.fcheck" + i).checked == true) {
                    if (eval("markform.fcheck" + i).value != "用户ID" && eval("markform.fcheck" + i).value != "用户口令" && eval("markform.fcheck" + i).value != "确认" && eval("markform.fcheck" + i).value != "返回")
                        eval("markform.fcheck" + i).checked = false;
                } else
                    eval("markform.fcheck" + i).checked = true;
            }
        }

        function quanbuxuan() {
            for (var i = 1; i <= <%=fieldnum%>; i++) {
                if (eval("markform.fcheck" + i).value != "信件标题" && eval("markform.fcheck" + i).value != "信件内容")
                    eval("markform.fcheck" + i).checked = false;
            }
        }

        function global_desc_style() {
            var i = 1;
            for (i = 1; i <= <%=fieldnum%> - 2; i++) {
                eval("markform.desc_style" + i).value = markform.g_desc_style.value;
            }
        }

        function global_content_style() {
            var i = 1;
            for (i = 1; i <= <%=fieldnum%> - 2; i++) {
                eval("markform.c_style" + i).value = markform.gcontentstyle.value;
            }
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
        function upload(type) {
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
        function define_audit_rule() {
            var returnvalue = window.showModalDialog("addUserLeavemessageFormAuditFrame.jsp?column=<%=columnID%>", "", "height=200, width=600 toolbar=yes, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
            if (returnvalue != undefined) markform.auditrule.value = returnvalue;
            //alert(returnvalue);
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=23>
        <input type="hidden" name="mark" value="<%=markID%>">

        <input type="hidden" name="contents" id="contents">
        <tr>
            <td style="height: 31px" colspan="9">请选择以下项：</td>
        </tr>
        <tr>
            <td style="height: 31px" colspan="9" align="left">
                <table border="0">
                    <td style="height: 32px; width: 181px;">表单字段描述格式： </td>
                    <td style="height: 32px; ">
                        <SELECT NAME="g_desc_style" id="gdescstyleid" style="width: 181px;" onChange="javascript:global_desc_style();">
                            <OPTION VALUE="-1" SELECTED>选择样式
                                    <%
                                                    for (int i = 0; i < list.size(); i++) {
                                                        String xzyangshi = (String)list.get(i);
                                                %>
                            <OPTION VALUE="<%=xzyangshi%>"
                                    <%if(global_desc_style!=null){ if(global_desc_style.equals(xzyangshi)){%>selected<%
                                    }
                                }
                            %> ><%=xzyangshi%>                    </OPTION>
                            <%}%>
                        </SELECT></td>
                    <td style="height: 32px; width: 164px;">
                        表单字段内容格式：            </td>
                    <td style="height: 32px; ">
                        <SELECT NAME="gcontentstyle" id="gcontentstyleid" style="width: 181px;" onChange="javascript:global_content_style();">
                            <OPTION VALUE="-1" SELECTED>选择样式
                                    <%
                                                    for (int i = 0; i < list.size(); i++) {
                                                        String xzyangshi = (String)list.get(i);
                                                %>
                            <OPTION VALUE="<%=xzyangshi%>"
                                    <%if(global_content_style!=null){ if(global_content_style.equals(xzyangshi)){%>selected<%
                                    }
                                }
                            %> ><%=xzyangshi%>                    </OPTION>
                            <%}%>
                        </SELECT></td>
                    <tr>
                        <td style="height: 32px; width: 181px;">表单字段描述名称对齐方式：                </td>
                        <td style="height: 32px; ">
                            <select name="descalign" style="width: 118px">
                                <option value="right"<%if(global_desc_align != null && !global_desc_align.equals("") && global_desc_align.equals("right")){%> selected<%}%>>右对齐</option>
                                <option value="left"<%if(global_desc_align != null && !global_desc_align.equals("") && global_desc_align.equals("left")){%> selected<%}%>>左对齐</option>
                                <option value="center"<%if(global_desc_align != null && !global_desc_align.equals("") && global_desc_align.equals("center")){%> selected<%}%>>居中</option>
                            </select></td>
                        <td style="height: 32px; width: 251px;">表单字段内容对齐方式：                </td>
                        <td style="height: 32px; ">
                            <select name="contentalign" style="width: 118px">
                                <option value="left"<%if(global_content_align != null && !global_content_align.equals("") && global_content_align.equals("left")){%> selected<%}%>>左对齐</option>
                                <option value="right"<%if(global_content_align != null && !global_content_align.equals("") && global_content_align.equals("right")){%> selected<%}%>>右对齐</option>
                                <option value="center"<%if(global_content_align != null && !global_content_align.equals("") && global_content_align.equals("center")){%> selected<%}%>>居中</option>
                            </select></td>
                    </tr>
                </table>    </td>
        </tr>
        <tr>
            <td style="height: 16px; width: 394px;" class="style2"><strong>留言信息</strong></td>
            <td style="height: 16px; width: 132px;" class="style2"><strong>序号</strong></td>
            <td style="height: 16px; width: 159px;" class="style2"><strong>字段描述样式</strong></td>
            <td style="width: 187px; height: 16px" class="style2" colspan="2"><strong>字段长度</strong></td>
            <td style="width: 164px; height: 16px" class="style2" colspan="2"><strong>类型</strong></td>
            <td style="width: 84px; height: 16px" class="style2">是否空值</td>
            <td style="height: 16px" class="style2"><strong>字段内容样式</strong></td>
        </tr>
        <tr>
            <td style="width: 394px; height: 26px;">
                <input type="checkbox" name="fcheck1" value="信件标题"<%if(titleflag){%> checked="checked"<%}%>>&nbsp;信件标题
                <input type="hidden" name="ename1" value="title">    </td>
            <td style="width: 132px; height: 26px;">
                <input name="order1" type="text" style="width: 49px" value="<%=(title_num==null)?"":title_num%>"></td>
            <td style="width: 159px; height: 26px;">
                <SELECT NAME="desc_style1" id="descstyleid1" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                       for (int i = 0; i < list.size(); i++) {
                            String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(title_desc_style != null && !title_desc_style.equals("") && xzyangshi.equals(title_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td style="width: 187px; height: 26px;" colspan="2">
                <input name="l1" type="text" value="<%=(title_len!=null)?title_len:""%>" style="width: 47px"></td>
            <td style="width: 164px; height: 26px;" colspan="2">
                <SELECT NAME="t1" style="width: 93px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td style="width: 84px; height: 26px;">
                <SELECT NAME="k1">
                    <OPTION VALUE="1">非空</OPTION>
                </SELECT></td>
            <td style="height: 26px">
                <SELECT NAME="c_style1" id="cstyleid1" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        boolean flag = false;
                            if(title_content_style != null && !title_content_style.equals("") && xzyangshi.equals(title_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px">
                <input type="checkbox" name="fcheck3" value="发信公司"<%if(companyflag){%> checked<%}%>>&nbsp;发信公司</td>
            <input type="hidden" name="ename3" value="company">
            <td class="style1" style="width: 132px">
                <input name="order3" type="text" style="width: 49px" value="<%=(company_num==null)?"":company_num%>"></td>
            <td class="style1" style="width: 159px">
                <SELECT NAME="desc_style3" id="descstyleid3" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(company_desc_style != null && !company_desc_style.equals("") && xzyangshi.equals(company_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px" colspan="2">
                <input name="l3" type="text" value="<%=(company_len!=null)?company_len:""%>" style="width: 46px"></td>
            <td class="style1" style="width: 164px" colspan="2">
                <SELECT NAME="t3" style="width: 93px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px">
                <SELECT NAME="k3">
                    <OPTION VALUE="0"<%if(company_isnull != null && company_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(company_isnull != null && company_isnull.equals("1")){%> selected <%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style3" id="cstyleid3" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(company_content_style != null && !company_content_style.equals("") && xzyangshi.equals(company_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px; height: 26px;">
                <input type="checkbox" name="fcheck4" value="电子邮件"<%if(emailflag){%> checked  <%}%>>&nbsp;电子邮件</td>
            <input type="hidden" name="ename4" value="email">
            <td class="style1" style="width: 132px; height: 26px;">
                <input name="order4" type="text" style="width: 49px" value="<%=(email_num==null)?"":email_num%>"></td>
            <td class="style1" style="width: 159px; height: 26px;">
                <SELECT NAME="desc_style4" id="descstyleid4" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(email_desc_style != null && !email_desc_style.equals("") && xzyangshi.equals(email_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="height: 26px; width: 187px" colspan="2">
                <input name="l4" type="text" value="<%=(email_len!=null)?email_len:""%>" style="width: 45px"></td>
            <td class="style1" style="width: 164px; height: 26px;" colspan="2">
                <SELECT NAME="t4" style="width: 94px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px; height: 26px;">
                <SELECT NAME="k4">
                    <OPTION VALUE="0"<%if(email_isnull!=null && email_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(email_isnull !=null && email_isnull.equals("1")){%> selected  <%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1" style="height: 26px">
                <SELECT NAME="c_style4" id="cstyleid4" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                 String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(email_content_style != null && !email_content_style.equals("") && xzyangshi.equals(email_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px">
                <input type="checkbox" name="fcheck5" value="姓    名"<%if(linkmanflag){out.print(" checked");}%>>&nbsp;姓    名</td>
            <input type="hidden" name="ename5" value="name">
            <td class="style1" style="width: 132px">
                <input name="order5" type="text" style="width: 49px" value="<%=(linkman_num==null)?"":linkman_num%>"></td>
            <td class="style1" style="width: 159px">
                <SELECT NAME="desc_style5" id="descstyleid5" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                 String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(linkman_desc_style != null && !linkman_desc_style.equals("") && xzyangshi.equals(linkman_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px" colspan="2">
                <input name="l5" type="text" value="<%=(linkman_len!=null)?linkman_len:""%>" style="width: 44px"></td>
            <td class="style1" style="width: 164px" colspan="2">
                <SELECT NAME="t5" style="width: 93px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px">
                <SELECT NAME="k5">
                    <OPTION VALUE="0"<%if(linkman_isnull != null && linkman_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(linkman_isnull != null && linkman_isnull.equals("1")){%> SELECTED<%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style5" id="cstyleid5" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(linkman_content_style != null && !linkman_content_style.equals("") && xzyangshi.equals(linkman_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px">
                <input type="checkbox" name="fcheck6" value="联系地址"<%if(linksflag){out.print(" checked");}%>>&nbsp;联系地址</td>
            <input type="hidden" name="ename6" value="links">
            <td class="style1" style="width: 132px">
                <input name="order6" type="text" style="width: 49px" value="<%=(links_num==null)?"":links_num%>"></td>
            <td class="style1" style="width: 159px">
                <SELECT NAME="desc_style6" id="descstyleid6" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                 String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(links_desc_style != null && !links_desc_style.equals("") && xzyangshi.equals(links_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px" colspan="2">
                <input name="l6" type="text" value="<%=(links_len!=null)?links_len:""%>" style="width: 44px"></td>
            <td class="style1" style="width: 164px" colspan="2">
                <SELECT NAME="t6" style="width: 93px">

                    <OPTION VALUE="text">文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px">
                <SELECT NAME="k6">
                    <OPTION VALUE="0"<%if(links_isnull != null && links_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(links_isnull != null && links_isnull.equals("1")){%> SELECTED<%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style6" id="cstyleid6" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(links_content_style != null && !links_content_style.equals("") && xzyangshi.equals(links_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px; height: 26px;">
                <input type="checkbox" name="fcheck7" value="邮政编码"<%if(zipflag){%> checked  <%}%>>&nbsp;邮政编码
                <input type="hidden" name="ename7" value="zip">    </td>
            <td class="style1" style="width: 132px; height: 26px;">
                <input name="order7" type="text" style="width: 49px" value="<%=(zip_num==null)?"":zip_num%>"></td>
            <td class="style1" style="width: 159px; height: 26px;">
                <SELECT NAME="desc_style7" id="descstyleid7" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(zip_desc_style != null && !zip_desc_style.equals("") && xzyangshi.equals(zip_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px; height: 26px;" colspan="2">
                <input name="l7" type="text" value="<%=(zip_len!=null)?zip_len:""%>" style="width: 45px"></td>
            <td class="style1" style="width: 164px; height: 26px;" colspan="2">
                <SELECT NAME="t7" style="width: 94px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px; height: 26px;">
                <SELECT NAME="k7">
                    <OPTION VALUE="0"<%if(zip_isnull != null && zip_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(zip_isnull != null && zip_isnull.equals("1")){%> SELECTED<%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1" style="height: 26px">
                <SELECT NAME="c_style7" id="cstyleid7" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(zip_content_style != null && !zip_content_style.equals("") && xzyangshi.equals(zip_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px; height: 26px;">
                <input type="checkbox" name="fcheck8" value="  联系电话"<%if(phoneflag){out.print(" checked");}%>>&nbsp;联系电话
                <input type="hidden" name="ename8" value="phone">    </td>
            <td class="style1" style="width: 132px; height: 26px;">
                <input name="order8" type="text" style="width: 49px" value="<%=(phone_num==null)?"":phone_num%>"></td>
            <td class="style1" style="width: 159px; height: 26px;">
                <SELECT NAME="desc_style8" id="descstyleid8" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(phone_desc_style != null && !phone_desc_style.equals("") && xzyangshi.equals(phone_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px; height: 26px;" colspan="2">
                <input name="l8" type="text" value="<%=(phone_len!=null)?phone_len:""%>" style="width: 45px"></td>
            <td class="style1" style="width: 164px; height: 26px;" colspan="2">
                <SELECT NAME="t8" style="width: 95px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px; height: 26px;">
                <SELECT NAME="k8">
                    <OPTION VALUE="0"<%if(phone_isnull !=null && phone_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(phone_isnull !=null && phone_isnull.equals("1")){%> selected  <%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1" style="height: 26px">
                <SELECT NAME="c_style8" id="cstyleid8" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(phone_content_style != null && !phone_content_style.equals("") && xzyangshi.equals(phone_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 394px">
                <input type="checkbox" name="fcheck9" value="是否对外公开联系信息"<%if(publishContactInfomationflag){out.print(" checked");}%>>&nbsp;是否对外公开联系信息
                <input type="hidden" name="ename9" value="opencontactor">    </td>
            <td class="style1" style="width: 132px">
                <input name="order9" type="text" style="width: 49px" value="<%=(publishContactInfomation_num==null)?"":publishContactInfomation_num%>"></td>
            <td class="style1" style="width: 159px">
                <SELECT NAME="desc_style9" id="descstyleid9" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(publishContactInfomation_desc_style != null && !publishContactInfomation_desc_style.equals("") && xzyangshi.equals(publishContactInfomation_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px" colspan="2">
                <input name="l9" type="text" value="<%=(publishContactInfomation_len!=null)?publishContactInfomation_len:""%>" style="width: 44px"></td>
            <td class="style1" style="width: 164px" colspan="2">
                <SELECT NAME="t9" style="width: 95px">
                    <OPTION VALUE="radio" SELECTED>单选项</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px">
                <SELECT NAME="k9">
                    <OPTION VALUE="0"<%if(publishContactInfomation_isnull !=null && publishContactInfomation_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(publishContactInfomation_isnull !=null && publishContactInfomation_isnull.equals("1")){%> selected  <%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style9" id="cstyleid9" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(publishContactInfomation_content_style != null && !publishContactInfomation_content_style.equals("") && xzyangshi.equals(publishContactInfomation_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>

        <tr>
            <td class="style1" style="width: 394px; height: 26px;">
                <input type="checkbox" name="fcheck2" value="信件内容"<%if(contentflag){%> checked="checked"<%}%>>&nbsp;信件内容&nbsp;&nbsp;
                <input type="hidden" name="ename2" value="content">    </td>
            <td class="style1" style="width: 132px; height: 26px;">
                <input name="order2" type="text" style="width: 49px" value="<%=(content_num==null)?"":content_num%>"></td>
            <td class="style1" style="width: 159px; height: 26px;">

                <SELECT NAME="desc_style2" id="descstyleid2" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(content_desc_style != null && !content_desc_style.equals("") && xzyangshi.equals(content_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="height: 26px; width: 187px" colspan="2">

                <input name="l2" type="text" value="<%=(content_len!=null)?content_len:""%>" style="width: 47px"></td>
            <td class="style1" style="width: 164px; height: 26px;" colspan="2">
                <SELECT NAME="t2" style="width: 92px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px; height: 26px;">
                <SELECT NAME="k2">
                    <OPTION VALUE="1">非空</OPTION>
                </SELECT></td>
            <td class="style1" style="height: 26px">

                <SELECT NAME="c_style2" id="cstyleid2" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(content_content_style != null && !content_content_style.equals("") && xzyangshi.equals(content_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>

        <tr>
            <td class="style1" style="width: 394px">
                <input type="checkbox" name="fcheck10" value="校验码"<%if(varifycodeflag){out.print(" checked");}%>>&nbsp;校验码
                <input type="hidden" name="ename10" value="varifycode">    </td>
            <td class="style1" style="width: 132px">
                <input name="order10" type="text" style="width: 49px" value="<%=(varifycode_num==null)?"":varifycode_num%>"></td>
            <td class="style1" style="width: 159px">
                <SELECT NAME="desc_style10" id="descstyleid10" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(varifycode_desc_style != null && !varifycode_desc_style.equals("") && xzyangshi.equals(varifycode_desc_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 187px" colspan="2">
                <input name="l10" type="text" value="<%=(varifycode_len!=null)?varifycode_len:""%>" style="width: 47px"></td>
            <td class="style1" style="width: 164px" colspan="2">
                <SELECT NAME="t10" style="width: 95px">
                    <OPTION VALUE="text" SELECTED>文本</OPTION>
                </SELECT></td>
            <td class="style1" style="width: 84px">
                <SELECT NAME="k10">
                    <OPTION VALUE="0"<%if(varifycode_isnull !=null && varifycode_isnull.equals("0")){%> SELECTED<%}%>>空</OPTION>
                    <OPTION VALUE="1"<%if(varifycode_isnull !=null && varifycode_isnull.equals("1")){%> selected  <%}%>>非空</OPTION>
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style10" id="cstyleid10" style="width: 181px;">
                    <OPTION VALUE="-1">选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                            boolean flag = false;
                            if(varifycode_content_style != null && !varifycode_content_style.equals("") && xzyangshi.equals(varifycode_content_style)){
                            flag = true;
                            }
                       %>
                    <OPTION VALUE="<%=xzyangshi%>"<%if(flag){%> selected  <%}%>><%=xzyangshi%>            </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>


        <tr>
            <td valign="middle" class="style1" style="height: 27px; " colspan="2">
                <input type="checkbox" name="fcheck11" value="确认" checked="checked">
                确认按钮类型:    </td>
            <td valign="middle" class="style1" style="width: 159px; height: 27px">
                <SELECT NAME="t11" style="width: 102px">
                    <OPTION VALUE="submit"<%if(submit!=null && submit.equals("submit")){%> SELECTED<%}%>>提交
                    <OPTION VALUE="image"<%if(submit!=null && submit.equals("image")){%> SELECTED<%}%>>图片
                </SELECT></td>
            <td valign="middle" class="style1" style="height: 27px">
                选择图片：</td>
            <td valign="middle" class="style1" colspan="2" style="height: 27px">
                <input id="okimage" name="okiamge" type="text" value="<%=submitimage == null?"":submitimage%>"></td>
            <td valign="middle" class="style1" colspan="3" style="height: 27px">
                <a href="#" onClick="javascript:selectpic(0);">选择已有图片</a>  <a href="#" onClick="javascript:upload(0);">上传新图片</a></td>
        </tr>
        <tr>
            <td valign="middle" class="style1" style="height: 27px; " colspan="2">
                <input type="checkbox" name="fcheck12" value="重置"<%if(resetflag){%> checked="true"<%}%>> 重置按钮类型:</td>
            <td valign="middle" class="style1" style="height: 27px; width: 159px">
                <SELECT NAME="t12" style="width: 104px">
                    <OPTION VALUE="submit"<%if(reset!=null && reset.equals("submit")){%> SELECTED<%}%>>提交
                    <OPTION VALUE="image"<%if(reset!=null && reset.equals("image")){%> SELECTED<%}%>>图片
                </SELECT></td>
            <td valign="middle" class="style1" style="height: 27px">
                选择图片：</td>
            <td valign="middle" class="style1" colspan="2" style="height: 27px">
                <input id="cancelimage" name="cancelimage" type="text" value="<%=resetimage == null?"":resetimage%>"></td>
            <td valign="middle" class="style1" colspan="3" style="height: 27px">
                <a href="#" onClick="javascript:selectpic(1);">选择已有图片</a>  <a href="#" onClick="javascript:upload(1);">上传新图片</a></td>
        </tr>
        <tr>
            <td height="50" valign="middle" class="style1" colspan="9"><label>
                <input type="button" name="Submit" value="全选" onClick="quanxuan()">
                &nbsp;&nbsp;
                <input type="button" name="Submit2" value="反选" onClick="fanxuan()">
                &nbsp;&nbsp;
                <input type="button" name="Submit3" value="全不选" onClick="quanbuxuan()">
            </label></td>
        </tr>
        <tr height=24>
            <td colspan="4">标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
            <td colspan="5"><label>
                <input type="radio" name="auditor" id="auditorid1" value="0" <%=(auditflag==0)?"checked":""%>>不审核回复信息
                <input type="radio" name="auditor" id="auditorid2" value="1" <%=(auditflag==1)?"checked":""%> onclick="define_audit_rule()">审核回复信息
            </label></td>
        </tr>

        <tr height=80>
            <td colspan="4">标记描述：<br><textarea rows="3" id="notes" cols="38" class=tine><%=(notes==null || notes=="null")?"":notes%></textarea>    </td>
            <td colspan="5"><label>审核规则：<br>
                <textarea name="auditrule" id="auditruleid" cols="45" rows="3" readonly><%=(auditflag==1)?auditRule:""%></textarea>
            </label></td>
        </tr>
        <tr height="50">
            <td align=center colspan="9">
                <input type="button" value=" 确定 " onClick = "doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "window.close();" class=tine>    </td>
        </tr>
    </form>
</table>

</body>
</html>