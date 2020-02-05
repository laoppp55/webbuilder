<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.viewFileManager.*"
        contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="java.io.*" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    List list = new ArrayList();
    String url = application.getRealPath("/") + "sites" + java.io.File.separator + authToken.getSitename() + java.io.File.separator + "css\\";
    File file = new File(url);
    String[] f = file.list();
    if (f != null) {
        for (int i = 0; i < f.length; i++) {
            File tf = new File(url + f[i]);
            if (tf.exists()) {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url + f[i])));
                String css = "";
                int bposi = -1;
                while ((css = br.readLine()) != null) {
                    bposi = css.indexOf("{");
                    if (bposi > -1) {
                        css = css.substring(0, bposi).trim();
                        if (css.startsWith(".")) css = css.substring(1);
                        list.add(css);
                    }
                }
            }
        }
    }

    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "用户注册表单";
    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    IMarkManager markMgr = markPeer.getInstance();
    int markID = ParamUtil.getIntParameter(request, "mark", 0);

    //获取标记内容，将标记内容填写到本页的 表单中
    String global_desc_style = null;
    String global_desc_align = null;
    String global_content_style = null;
    String global_prompt_style = null;
    String global_prompt_align = null;
    String okimage = null;
    String cancelimage = null;
    String resetimage = null;
    int global_field_len = 0;
    int fieldnum = 38;

    String descstyle[] = new String [fieldnum + 1];
    String contentstyle[] = new String[fieldnum + 1];
    String chname[] = new String[fieldnum + 1];
    String enname[] = new String[fieldnum + 1];
    String ftype[] = new String[fieldnum + 1];
    String order[] = new String[fieldnum + 1];
    String iskong[] = new String[fieldnum + 1];
    int flen[] = new int[fieldnum + 1];

    for(int i=0; i<fieldnum + 1; i++) {
        order[i] = String.valueOf(i);
    }

    if (markID > 0 && !doCreate) {
        XMLProperties properties = null;
        mark vmark = markMgr.getAMark(markID);
        String markcontent = vmark.getContent();
        int sposi = markcontent.indexOf("[CONTENT]");
        int eposi = markcontent.indexOf("[/CONTENT]");
        markcontent = markcontent.substring(sposi + 9, eposi);
        properties = new XMLProperties(markcontent);
        global_desc_style = properties.getProperty("globalsetting.globaldescstyle");
        global_desc_align = properties.getProperty("globalsetting.globaldescalign");
        global_content_style = properties.getProperty("globalsetting.globalcontentstyle");
        global_prompt_style = properties.getProperty("globalsetting.globalpromptstyle");
        global_prompt_align = properties.getProperty("globalsetting.globalpromptalign");
        global_field_len = Integer.parseInt(properties.getProperty("globalsetting.globalfieldlen"));
        okimage = properties.getProperty("globalsetting.okimage");
        cancelimage = properties.getProperty("globalsetting.cancelimage");
        resetimage = properties.getProperty("globalsetting.resetimage");

        String b[] = properties.getChildrenProperties("fields");
        String not_null_field_name[] = new String[b.length + 1];
        for (int i = 1; i <= b.length; i++) {
            not_null_field_name[i] = null;
        }

        for (int j = 0; j < b.length; j++) {
            int numberOfAfield = Integer.parseInt(b[j].substring(5));
            descstyle[numberOfAfield] = properties.getProperty("fields." + b[j] + ".descstyle");
            contentstyle[numberOfAfield] = properties.getProperty("fields." + b[j] + ".contentstyle");
            chname[numberOfAfield] = properties.getProperty("fields." + b[j] + ".chinesename");
            enname[numberOfAfield] = properties.getProperty("fields." + b[j] + ".name");
            ftype[numberOfAfield] = properties.getProperty("fields." + b[j] + ".type.name");
            order[numberOfAfield] = properties.getProperty("fields." + b[j] + ".order");
            iskong[numberOfAfield] = properties.getProperty("fields." + b[j] + ".isnull");
            flen[numberOfAfield] = Integer.parseInt(properties.getProperty("fields." + b[j] + ".flen"));
        }
    }

    if (doCreate) {
        int radio = ParamUtil.getIntParameter(request, "radio", 0);
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        innerFlag = ParamUtil.getIntParameter(request, "innerFlag", 0);
        listType = ParamUtil.getIntParameter(request, "listType", 0);
        okimage = ParamUtil.getParameter(request, "okimage");
        if (okimage==null) okimage="";
        cancelimage = ParamUtil.getParameter(request, "cancelimage");
        if (cancelimage==null) cancelimage="";
        resetimage = ParamUtil.getParameter(request, "resetimage");
        if (resetimage==null) resetimage="";
        String field = "";

        //表单名称的显示样式和对齐方式
        String gdescstyle = ParamUtil.getParameter(request, "g_desc_style");
        if (gdescstyle == null) gdescstyle = "";
        String gdescalign = ParamUtil.getParameter(request, "descalign");
        if (gdescalign == null) gdescalign = "";

        //表单输入字段的显示样式
        String gcontentstyle = ParamUtil.getParameter(request, "gcontentstyle");
        if (gcontentstyle == null) gcontentstyle = "";

        //表单提示信息的显示样式和对齐方式
        String gpromptstyle = ParamUtil.getParameter(request, "g_prompt_style");
        if (gpromptstyle == null) gpromptstyle = "";
        String gpromptalign = ParamUtil.getParameter(request, "promptalign");
        if (gpromptalign == null) gpromptalign = "";

        //表单字段长度
        int gflen = ParamUtil.getIntParameter(request, "fieldlength", 0);

        String xmlstr = "<?xml version=\"1.0\" encoding=\"gb2312\"?>\r\n";
        xmlstr = xmlstr + "<form>\r\n";
        xmlstr = xmlstr + "<globalsetting>\r\n";
        xmlstr = xmlstr + "<globaldescstyle>" + gdescstyle + "</globaldescstyle>\r\n";
        xmlstr = xmlstr + "<globaldescalign>" + gdescalign + "</globaldescalign>\r\n";
        xmlstr = xmlstr + "<globalcontentstyle>" + gcontentstyle + "</globalcontentstyle>\r\n";
        xmlstr = xmlstr + "<globalfieldlen>" + gflen + "</globalfieldlen>\r\n";
        xmlstr = xmlstr + "<globalpromptstyle>" + gpromptstyle + "</globalpromptstyle>\r\n";
        xmlstr = xmlstr + "<globalpromptalign>" + gpromptalign + "</globalpromptalign>\r\n";
        xmlstr = xmlstr + "<okimage>" + okimage + "</okimage>\r\n";
        xmlstr = xmlstr + "<cancelimage>" + cancelimage + "</cancelimage>\r\n";
        xmlstr = xmlstr + "<resetimage>" + resetimage + "</resetimage>\r\n";
        xmlstr = xmlstr + "</globalsetting>\r\n";
        xmlstr = xmlstr + "<fields>\r\n";
        for (int i = 1; i <= fieldnum-1; i++) {
            field = request.getParameter("fcheck" + i);
            String ename =  ParamUtil.getParameter(request, "ename" + i);
            String d_style = ParamUtil.getParameter(request, "desc_style" + i);
            if (d_style == null) d_style = "";
            String c_style = ParamUtil.getParameter(request, "c_style" + i);
            if (c_style == null) c_style = "";
            String fieldtype = ParamUtil.getParameter(request, "t" + i);
            if (fieldtype == null) fieldtype = "";
            String is_null = ParamUtil.getParameter(request, "k" + i);
            if (is_null == null) is_null = "";
            int fieldlen = ParamUtil.getIntParameter(request, "l" + i, 0);
            int ordernum = ParamUtil.getIntParameter(request, "num" + i, 0);

            if (field != null) {
                xmlstr = xmlstr + "<field" + i + "><chinesename>" + field + "</chinesename><name>" + ename + "</name>\r\n" +
                        "<descstyle>" + d_style + "</descstyle>\r\n" +
                        "<contentstyle>" + c_style + "</contentstyle>\r\n" +
                        "<isnull>" + is_null + "</isnull>\r\n" +
                        "<order>" + ordernum + "</order>\r\n" +
                        "<flen>" + fieldlen + "</flen>\r\n" +
                        "<type><name>" + fieldtype + "</name><values>\r\n" +
                        "<value></value>\r\n" +
                        "</values></type>\r\n" +
                        "</field" + i + ">\r\n";
            }
        }
        xmlstr = xmlstr + "</fields>\r\n";
        xmlstr = xmlstr + "</form>\r\n";
        String content = "[TAG][HTMLCODE][MARKTYPE]" + type + "[/MARKTYPE][CONTENT]<![CDATA[" + xmlstr + "]]>[/CONTENT][/HTMLCODE][/TAG]";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(innerFlag);
        mark.setFormatFileNum(listType);
        mark.setMarkType(18);
        int orgmarkID = markID;
        if (orgmarkID > 0 && !saveas)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String markname = "用户注册";
        if (orgmarkID > 0 && !saveas) {
            out.println("<script>top.close();</script>");
        } else {
            String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.opener.document.createForm.cname.value='" + markname + "';" +
                    "window.parent.opener.document.createForm.modelname.value='register';" +
                    "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }

        /*String viewer = request.getHeader("user-agent");
        if (viewer.toLowerCase().indexOf("gecko") == -1)
            out.println("<script>window.returnValue=\"[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]\";top.close();</script>");
        else {
            if (orgmarkID > 0 && !saveas) {
                out.println("<script>top.close();</script>");
            } else {
                String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.opener.InsertHTML('content',returnvalue);top.close();</script>");
            }
        }*/
        return;
    }
%>

<html>
<head>
    <base target="_self">
    <title>用户注册表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/color.js"></script>
    <script language="javascript" src="../js/mark.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function cal() {
            top.close();
        }
        function doit()
        {
            markform.action = "addUserRegisterTest.jsp";
            markform.method = "post";
            markform.target = "_self"
            markform.submit();
        }

        function addextenattr() {
            <%if(markID==-1){%>
            showModalDialog("editzhuce.jsp?id=0", window, "font-family:Verdana; font-size:12; dialogWidth:50em; dialogHeight:30em;status:no");
            <%}else{%>
            showModalDialog("editzhuce.jsp?id=0&markid=<%=markID%>", window, "font-family:Verdana; font-size:12; dialogWidth:50em; dialogHeight:30em;status:no");
            <%}%>
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
            for (var i = 1; i <= <%=fieldnum%>; i++)
            {
                if (eval("markform.fcheck" + i).value != "用户ID" && eval("markform.fcheck" + i).value != "用户口令" && eval("markform.fcheck" + i).value != "确认" && eval("markform.fcheck" + i).value != "返回")
                    eval("markform.fcheck" + i).checked = false;
            }
        }

        function global_desc_style() {
            var i = 1;
            for (i = 1; i <= <%=fieldnum%> - 3; i++) {
                eval("markform.desc_style" + i).value = markform.g_desc_style.value;
            }
        }

        function global_content_style() {
            var i = 1;
            for (i = 1; i <= <%=fieldnum%> - 3; i++) {
                eval("markform.c_style" + i).value = markform.gcontentstyle.value;
            }
        }

        function set_field_length() {
            var i = 1;
            if (markform.fieldlength.value != null) {
                for (i = 1; i <= <%=fieldnum%> - 3; i++) {
                    eval("markform.l" + i).value = markform.fieldlength.value;
                }
            }
        }

        function selectpic(type) {
            winStr = "selectIcon.jsp";
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");

            var okimg = document.getElementById("okimgid");
            var cancelimg = document.getElementById("cancelimgid");
            var resetimg = document.getElementById("resetimgid");
            if (type == "ok") {
                if (returnvalue != null && returnvalue != "") {
                    markform.okimage.value=returnvalue;
                    okimg.style.display = "";
                    document.getElementById("okimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            } else if (type == "cancel") {
                if (returnvalue != null && returnvalue != "") {
                    markform.cancelimage.value = returnvalue;
                    cancelimg.style.display = "";
                    document.getElementById("cancelimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            } else {
                if (returnvalue != null && returnvalue != "") {
                    markform.resetimage.value = returnvalue;
                    resetimg.style.display = "";
                    document.getElementById("resetimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            }
        }

        function uploadbuttonimage(param) {
            winStr = "uploadIconFrame.jsp";
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");

            var okimg = document.getElementById("okimgid");
            var cancelimg = document.getElementById("cancelimgid");
            var resetimg = document.getElementById("resetimgid");

            if (param == "ok") {
                if (returnvalue != null && returnvalue != "") {
                    markform.okimage.value=returnvalue;
                    okimg.style.display = "";
                    document.getElementById("okimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            }
            else if (param =="cancel") {
                if (returnvalue != null && returnvalue != "") {
                    markform.cancelimage.value = returnvalue;
                    cancelimg.style.display = "";
                    document.getElementById("cancelimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            }
            else {
                if (returnvalue != null && returnvalue != "") {
                    markform.resetimage.value = returnvalue;
                    resetimg.style.display = "";
                    document.getElementById("resetimg_src").src="/_sys_images/buttons/" + returnvalue;
                }
            }
        }
    </script>
    <style type="text/css">
        .style1 {
            text-align: left;
        }

        .style2 {
            font-size: x-small;
            background-color: #00FFFF;
        }
    </style>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form action="addUserRegisterTest.jsp" method="POST" name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=18>
        <input type="hidden" name="mark" value="<%=markID%>">
        <tr>
            <td style="height: 31px" colspan="8">表单设置：</td>
        </tr>
        <tr>
            <td style="height: 32px; " colspan="2">名称格式：
                &nbsp;</td>
            <td style="height: 32px; width: 149px;">
                <SELECT NAME="g_desc_style"
                        id="gdescstyleid"
                        style="width: 122px"
                        onchange="javascript:global_desc_style();">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>"
                            <%if(global_desc_style!=null){ if(global_desc_style.equals(xzyangshi)){%>selected<%
                            }
                        }
                    %> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td style="height: 32px; width: 134px;">
                内容格式：
            </td>
            <td style="height: 32px; " colspan="2">
                <SELECT NAME="gcontentstyle"
                        id="gcontentstyleid"
                        style="width: 105px" onchange="javascript:global_content_style();">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>"
                            <%if(global_content_style!=null){ if(global_content_style.equals(xzyangshi)){%>selected<%
                            }
                        }
                    %> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td style="height: 32px; width: 148px;">
                提示信息样式：
            </td>
            <td style="height: 32px; ">
                <SELECT NAME="g_prompt_style" id="gpromptstyleid" style="width: 107px">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>"
                            <%if(global_prompt_style!=null){ if(global_prompt_style.equals(xzyangshi)){%>selected<%
                            }
                        }
                    %> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td style="height: 32px; " colspan="2">名称对齐方式：</td>
            <td style="height: 32px; width: 149px;">
                <select name="descalign" style="width: 118px">
                    <option value="right" <%if(global_desc_align!=null){ if(global_desc_align.equals("right")){%>selected<%
                            }
                        }
                    %> >右对齐
                    </option>
                    <option value="left" <%if(global_desc_align!=null){ if(global_desc_align.equals("left")){%>selected<%
                            }
                        }
                    %> >左对齐
                    </option>
                    <option value="center" <%if(global_desc_align!=null){ if(global_desc_align.equals("center")){%>selected<%
                            }
                        }
                    %> >居中
                    </option>
                </select></td>
            <td style="height: 32px; width: 134px;">内容长度：</td>
            <td style="height: 32px; " colspan="2">
                <input name="fieldlength" type="text" value="<%=global_field_len%>" onChange="javascript:set_field_length();">
            </td>
            <td style="height: 32px; width: 148px;">
                提示信息对齐：
            </td>
            <td style="height: 32px; ">
                <select name="promptalign" style="width: 105px">
                    <option value="right" <%if(global_prompt_align!=null){ if(global_prompt_align.equals("right")){%>selected<%
                            }
                        }
                    %> >右对齐
                    </option>
                    <option value="left" <%if(global_prompt_align!=null){ if(global_prompt_align.equals("left")){%>selected<%
                            }
                        }
                    %> >左对齐
                    </option>
                    <option value="center"
                            <%if(global_prompt_align!=null){ if(global_prompt_align.equals("center")){%>selected<%
                            }
                        }
                    %> >居中
                    </option>
                </select></td>
        </tr>
        <tr>
            <td style="height: 16px; width: 131px;" class="style2"><strong>注册信息</strong></td>
            <td style="height: 16px; width: 49px;" class="style2"><strong>序号</strong></td>
            <td style="width: 149px; height: 16px" class="style2"><strong>字段描述样式</strong></td>
            <td style="width: 134px; height: 16px" class="style2"><strong>类型</strong></td>
            <td style="width: 90px; height: 16px" class="style2">字段长度</td>
            <td style="height: 16px" class="style2" colspan="2">是否空值</td>
            <td style="height: 16px" class="style2"><strong>字段内容样式</strong></td>
        </tr>
        <tr>
            <td style="width: 131px">
                <input type="checkbox" name="fcheck1" value="用户名称" checked="checked">&nbsp;用户名称
                <input type="hidden" name="ename1" value="username">
            </td>
            <td style="width: 49px">
                <input name="num1" type="text" style="width: 52px" value="<%=order[1]%>"></td>
            <td style="width: 149px">
                <SELECT NAME="desc_style1" id="descstyleid1" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[1]!=null){ if(descstyle[1].equals(xzyangshi)){%>selected<% }
                    }%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td style="width: 134px">
                <SELECT NAME="t1" style="width: 58px">
                    <OPTION VALUE="text" <%if(ftype[1]!=null){ if(ftype[1].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td style="width: 90px">
                <input name="l1" type="text" value="<%=flen[1]%>" style="width: 71px"></td>
            <td colspan="2">
                <SELECT NAME="k1">
                    <OPTION VALUE="1" <%if(iskong[1]!=null){ if(iskong[1].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td>
                <SELECT NAME="c_style1" id="cstyleid1" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[1]!=null){if(contentstyle[1].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px; height: 26px;">
                <input type="checkbox" name="fcheck2" value="用户口令" checked="checked">&nbsp;用户口令&nbsp;&nbsp;
                <input type="hidden" name="ename2" value="password">
            </td>
            <td class="style1" style="width: 49px; height: 26px;">
                <input name="num2" type="text" style="width: 52px" value="<%=order[2]%>"></td>
            <td class="style1" style="height: 26px; width: 149px">

                <SELECT NAME="desc_style2" id="descstyleid2" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[2]!=null){ if(descstyle[2].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px; height: 26px;">
                <SELECT NAME="t2">
                    <OPTION VALUE="password" <%if(ftype[2]!=null){ if(ftype[2].equals("password")){%>selected<%}}%> >口令
                </SELECT></td>
            <td class="style1" style="width: 90px; height: 26px;">
                <input name="l2" type="text" value="<%=flen[2]%>" style="width: 71px"></td>
            <td class="style1" style="height: 26px;" colspan="2">
                <SELECT NAME="k2">
                    <OPTION VALUE="1" <%if(iskong[2]!=null){ if(iskong[2].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1" style="height: 26px">

                <SELECT NAME="c_style2" id="cstyleid2" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[2]!=null){if(contentstyle[2].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck3" value="用户真实姓名" <%if(chname[3]!=null){ if(chname[3].equals("用户真实姓名")){%>checked<% }}%> >&nbsp;用户真实姓名
                <input type="hidden" name="ename3" value="realname">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num3" type="text" style="width: 52px" value="<%=order[3]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style3" id="descstyleid3" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[3]!=null){ if(descstyle[3].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t3">
                    <OPTION VALUE="text" <%if(ftype[3]!=null){ if(ftype[3].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l3" type="text" value="<%=flen[3]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k3">
                    <OPTION VALUE="0" <%if(iskong[3]!=null){ if(iskong[3].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[3]!=null){ if(iskong[3].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style3" id="cstyleid3" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[3]!=null){if(contentstyle[3].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck4" value="身份证号" <%if(chname[4]!=null){ if(chname[4].equals("身份证号")){%>checked<% }}%> >
                身份证号
                <input type="hidden" name="ename4" value="idno">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num4" type="text" style="width: 52px" value="<%=order[4]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style4" id="descstyleid4" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[4]!=null){ if(descstyle[4].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t4" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[4]!=null){ if(ftype[4].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l4" type="text" value="<%=flen[4]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k4">
                    <OPTION VALUE="0" <%if(iskong[4]!=null){ if(iskong[4].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[4]!=null){ if(iskong[4].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style4" id="cstyleid4" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[4]!=null){if(contentstyle[4].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck5" value="民族" <%if(chname[5]!=null){ if(chname[5].equals("民族")){%>checked<% }}%> >
                民族
                <input type="hidden" name="ename5" value="nation"></td>
            <td class="style1" style="width: 49px">
                <input name="num5" type="text" style="width: 52px" value="<%=order[5]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style5" id="descstyleid5" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[5]!=null){ if(descstyle[5].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t5" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[5]!=null){ if(ftype[5].equals("text")){%>selected<%}}%> >文本
                    <OPTION VALUE="select" <%if(ftype[5]!=null){ if(ftype[5].equals("select")){%>selected<%}}%> >下拉列表
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l5" type="text" value="<%=flen[5]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k5">
                    <OPTION VALUE="0" <%if(iskong[5]!=null){ if(iskong[5].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[5]!=null){ if(iskong[5].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style5" id="cstyleid5" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[5]!=null){if(contentstyle[5].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck6" value="教育程度" <%if(chname[6]!=null){ if(chname[6].equals("教育程度")){%>checked<% }}%> >
                教育程度<input type="hidden" name="ename6" value="degree"></td>
            <td class="style1" style="width: 49px">
                <input name="num6" type="text" style="width: 52px" value="<%=order[6]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style6" id="descstyleid6" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[6]!=null){ if(descstyle[6].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t6" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[6]!=null){ if(ftype[6].equals("text")){%>selected<%}}%> >文本
                    <OPTION VALUE="select" <%if(ftype[6]!=null){ if(ftype[6].equals("select")){%>selected<%}}%> >下拉列表
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l6" type="text" value="<%=flen[6]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k6">
                    <OPTION VALUE="0" <%if(iskong[6]!=null){ if(iskong[6].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[6]!=null){ if(iskong[6].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style6" id="cstyleid6" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[6]!=null){if(contentstyle[6].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px; height: 26px;">
                <input type="checkbox" name="fcheck7" value="电子邮件" <%if(chname[7]!=null){ if(chname[7].equals("电子邮件")){%>checked<% }}%> >&nbsp;电子邮件
                <input type="hidden" name="ename7" value="email">
            </td>
            <td class="style1" style="width: 49px; height: 26px;">
                <input name="num7" type="text" style="width: 52px" value="<%=order[7]%>"></td>
            <td class="style1" style="height: 26px; width: 149px">
                <SELECT NAME="desc_style7" id="descstyleid7" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[7]!=null){ if(descstyle[7].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px; height: 26px;">
                <SELECT NAME="t7">
                    <OPTION VALUE="text" <%if(ftype[7]!=null){ if(ftype[7].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px; height: 26px;">
                <input name="l7" type="text" value="<%=flen[7]%>" style="width: 71px"></td>
            <td class="style1" style="height: 26px;" colspan="2">
                <SELECT NAME="k7">
                    <OPTION VALUE="0" <%if(iskong[7]!=null){ if(iskong[7].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[7]!=null){ if(iskong[7].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1" style="height: 26px">
                <SELECT NAME="c_style7" id="cstyleid7" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[7]!=null){if(contentstyle[7].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck8" value="联系人" <%if(chname[8]!=null){ if(chname[8].equals("联系人")){%>checked<% }}%> >&nbsp;联系人
                <input type="hidden" name="ename8" value="contactor"></td>
            <td class="style1" style="width: 49px">
                <input name="num8" type="text" style="width: 52px" value="<%=order[8]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style8" id="descstyleid8" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[8]!=null){ if(descstyle[8].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t8">
                    <OPTION VALUE="text" <%if(ftype[8]!=null){ if(ftype[8].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l8" type="text" value="<%=flen[8]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k8">
                    <OPTION VALUE="0" <%if(iskong[8]!=null){ if(iskong[8].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[8]!=null){ if(iskong[8].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style8" id="cstyleid8" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[8]!=null){if(contentstyle[8].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck9" value="国家" <%if(chname[9]!=null){ if(chname[9].equals("国家")){%>checked<% }}%> >&nbsp;国家
                <input type="hidden" name="ename9" value="country">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num9" type="text" style="width: 52px" value="<%=order[9]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style9" id="descstyleid9" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[9]!=null){ if(descstyle[9].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t9">
                    <OPTION VALUE="select" <%if(ftype[9]!=null){ if(ftype[9].equals("select")){%>selected<%}}%> >下拉列表
                    <OPTION VALUE="text" <%if(ftype[9]!=null){ if(ftype[9].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l9" type="text" value="<%=flen[9]%>"  style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k9">
                    <OPTION VALUE="0" <%if(iskong[9]!=null){ if(iskong[9].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[9]!=null){ if(iskong[9].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style9" id="cstyleid9" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[9]!=null){if(contentstyle[9].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px; height: 26px;">
                <input type="checkbox" name="fcheck10" value="省份" <%if(chname[10]!=null){ if(chname[10].equals("省份")){%>checked<% }}%> >&nbsp;省份
                <input type="hidden" name="ename10" value="province">
            </td>
            <td class="style1" style="width: 49px; height: 26px;">
                <input name="num10" type="text" style="width: 52px" value="<%=order[10]%>"></td>
            <td class="style1" style="height: 26px; width: 149px">
                <SELECT NAME="desc_style10" id="descstyleid10" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[10]!=null){ if(descstyle[10].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px; height: 26px;">
                <SELECT NAME="t10">
                    <OPTION VALUE="select" <%if(ftype[10]!=null){ if(ftype[10].equals("select")){%>selected<%}}%> >下拉列表
                    <OPTION VALUE="text" <%if(ftype[10]!=null){ if(ftype[10].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px; height: 26px;">
                <input name="l10" type="text" value="<%=flen[10]%>" style="width: 71px"></td>
            <td class="style1" style="height: 26px;" colspan="2">
                <SELECT NAME="k10">
                    <OPTION VALUE="0" <%if(iskong[10]!=null){ if(iskong[10].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[10]!=null){ if(iskong[10].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1" style="height: 26px">
                <SELECT NAME="c_style10" id="cstyleid10" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[10]!=null){if(contentstyle[10].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck11" value="城市" <%if(chname[11]!=null){ if(chname[11].equals("城市")){%>checked<% }}%> >&nbsp;所在城市
                <input type="hidden" name="ename11" value="city">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num11" type="text" style="width: 52px" value="<%=order[11]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style11" id="descstyleid11" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[11]!=null){ if(descstyle[11].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t11">
                    <OPTION VALUE="select" <%if(ftype[11]!=null){ if(ftype[11].equals("select")){%>selected<%}}%> >下拉列表
                    <OPTION VALUE="text" <%if(ftype[11]!=null){ if(ftype[11].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l11" type="text" value="<%=flen[11]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k11">
                    <OPTION VALUE="0" <%if(iskong[11]!=null){ if(iskong[11].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[11]!=null){ if(iskong[11].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style11" id="cstyleid11" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[11]!=null){if(contentstyle[11].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck12" value="所在街道" <%if(chname[12]!=null){ if(chname[12].equals("所在街道")){%>checked<% }}%> >&nbsp;所在街道
                <input type="hidden" name="ename12" value="street">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num12" type="text" style="width: 52px" value="<%=order[12]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style12" id="descstyleid12" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[12]!=null){ if(descstyle[12].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t12">
                <OPTION VALUE="text" <%if(ftype[12]!=null){ if(ftype[12].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l12" type="text" value="<%=flen[12]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k12">
                    <OPTION VALUE="0" <%if(iskong[12]!=null){ if(iskong[12].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[12]!=null){ if(iskong[12].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style12" id="cstyleid12" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[12]!=null){if(contentstyle[12].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck13" value="具体地址" <%if(chname[13]!=null){ if(chname[13].equals("具体地址")){%>checked<% }}%> >&nbsp;具体地址
                <input type="hidden" name="ename13" value="address">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num13" type="text" style="width: 52px" value="<%=order[13]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style13" id="descstyleid13" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[13]!=null){ if(descstyle[13].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t13">
                <OPTION VALUE="text" <%if(ftype[13]!=null){ if(ftype[13].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l13" type="text" value="<%=flen[13]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k13">
                    <OPTION VALUE="0" <%if(iskong[13]!=null){ if(iskong[13].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[13]!=null){ if(iskong[13].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style13" id="cstyleid13" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[13]!=null){if(contentstyle[13].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck14" value="邮政编码" <%if(chname[14]!=null){ if(chname[14].equals("邮政编码")){%>checked<% }}%> >&nbsp;邮政编码
                <input type="hidden" name="ename14" value="postcode">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num14" type="text" style="width: 52px" value="<%=order[14]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style14" id="descstyleid14" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[14]!=null){ if(descstyle[14].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t14">
                <OPTION VALUE="text" <%if(ftype[14]!=null){ if(ftype[14].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l14" type="text" value="<%=flen[14]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k14">
                    <OPTION VALUE="0" <%if(iskong[14]!=null){ if(iskong[14].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[14]!=null){ if(iskong[14].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style14" id="cstyleid14" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[14]!=null){if(contentstyle[14].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck15" value="电话" <%if(chname[15]!=null){ if(chname[15].equals("电话")){%>checked<% }}%> >&nbsp;电话
                <input type="hidden" name="ename15" value="telephone">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num15" type="text" style="width: 52px" value="<%=order[15]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style15" id="descstyleid15" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[15]!=null){ if(descstyle[15].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t15">
                    <OPTION VALUE="text" <%if(ftype[15]!=null){ if(ftype[15].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l15" type="text" value="<%=flen[15]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k15">
                    <OPTION VALUE="0" <%if(iskong[15]!=null){ if(iskong[15].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[15]!=null){ if(iskong[15].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style15" id="cstyleid15" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[15]!=null){if(contentstyle[15].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck16" value="传真" <%if(chname[16]!=null){ if(chname[16].equals("传真")){%>checked<% }}%> >&nbsp;传真
                <input type="hidden" name="ename16" value="fax">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num16" type="text" style="width: 52px" value="<%=order[16]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style16" id="descstyleid16" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[16]!=null){ if(descstyle[16].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t16">
                    <OPTION VALUE="text" <%if(ftype[16]!=null){ if(ftype[16].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l16" type="text" value="<%=flen[16]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k16">
                    <OPTION VALUE="0" <%if(iskong[16]!=null){ if(iskong[16].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[16]!=null){ if(iskong[16].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style16" id="cstyleid16" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[16]!=null){if(contentstyle[16].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck17" value="个人主页" <%if(chname[17]!=null){ if(chname[17].equals("个人主页")){%>checked<% }}%> >&nbsp;个人主页
                <input type="hidden" name="ename17" value="homepage">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num17" type="text" style="width: 52px" value="<%=order[17]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style17" id="descstyleid17" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[17]!=null){ if(descstyle[17].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t17">
                <OPTION VALUE="text" <%if(ftype[17]!=null){ if(ftype[17].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l17" type="text" value="<%=flen[17]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k17">
                    <OPTION VALUE="0" <%if(iskong[17]!=null){ if(iskong[17].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[17]!=null){ if(iskong[17].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style17" id="cstyleid17" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[17]!=null){if(contentstyle[17].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck18" value="个人留言" <%if(chname[18]!=null){ if(chname[18].equals("个人留言")){%>checked<% }}%> >&nbsp;个人留言
                <input type="hidden" name="ename18" value="message">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num18" type="text" style="width: 52px" value="<%=order[18]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style18" id="descstyleid18" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[18]!=null){ if(descstyle[18].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t18">
                <OPTION VALUE="text" <%if(ftype[18]!=null){ if(ftype[18].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l18" type="text" value="<%=flen[18]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k18">
                    <OPTION VALUE="0" <%if(iskong[18]!=null){ if(iskong[18].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[18]!=null){ if(iskong[18].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style18" id="cstyleid18" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[18]!=null){if(contentstyle[18].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck19" value="用户性别" <%if(chname[19]!=null){ if(chname[19].equals("用户性别")){%>checked<% }}%> >&nbsp;用户性别
                <input type="hidden" name="ename19" value="sex">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num19" type="text" style="width: 52px" value="<%=order[19]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style19" id="descstyleid19" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[19]!=null){ if(descstyle[19].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px"><SELECT NAME="t19">
                <OPTION VALUE="select" <%if(ftype[19]!=null){ if(ftype[19].equals("select")){%>selected<%}}%> >下拉列表
                <OPTION VALUE="radio" <%if(ftype[19]!=null){ if(ftype[19].equals("radio")){%>selected<%}}%> >单选按钮
                <OPTION VALUE="text" <%if(ftype[19]!=null){ if(ftype[19].equals("text")){%>selected<%}}%> >文本
            </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l19" type="text" value="<%=flen[19]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k19">
                    <OPTION VALUE="0" <%if(iskong[19]!=null){ if(iskong[19].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[19]!=null){ if(iskong[19].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style19" id="cstyleid19" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[19]!=null){if(contentstyle[19].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck20" value="用户QQ号码" <%if(chname[20]!=null){ if(chname[20].equals("用户QQ号码")){%>checked<% }}%> >&nbsp;用户QQ号码
                <input type="hidden" name="ename20" value="qq">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num20" type="text" style="width: 52px" value="<%=order[20]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style20" id="descstyleid20" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[20]!=null){ if(descstyle[20].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t20">
                    <OPTION VALUE="text" <%if(ftype[20]!=null){ if(ftype[20].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l20" type="text" value="<%=flen[20]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k20">
                    <OPTION VALUE="0" <%if(iskong[20]!=null){ if(iskong[20].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[20]!=null){ if(iskong[20].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style20" id="cstyleid20" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[20]!=null){if(contentstyle[20].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck21" value="出生日期" <%if(chname[21]!=null){ if(chname[21].equals("出生日期")){%>checked<% }}%> >&nbsp;出生日期
                <input type="hidden" name="ename21" value="birthdate">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num21" type="text" style="width: 52px" value="<%=order[21]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style21" id="descstyleid21" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[21]!=null){ if(descstyle[21].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t21">
                    <OPTION VALUE="date" <%if(ftype[21]!=null){ if(ftype[21].equals("date")){%>selected<%}}%> >日历
                    <OPTION VALUE="text" <%if(ftype[21]!=null){ if(ftype[21].equals("text")){%>selected<%}}%> >文本
                    <OPTION VALUE="select" <%if(ftype[21]!=null){ if(ftype[21].equals("select")){%>selected<%}}%> >下拉列表
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l21" type="text" value="<%=flen[21]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k21">
                    <OPTION VALUE="0" <%if(iskong[21]!=null){ if(iskong[21].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[21]!=null){ if(iskong[21].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style21" id="cstyleid21" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[21]!=null){if(contentstyle[21].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck22" value="用户头像" <%if(chname[22]!=null){ if(chname[22].equals("用户头像")){%>checked<% }}%> >&nbsp;用户头像
                <input type="hidden" name="ename22" value="image">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num22" type="text" style="width: 52px" value="<%=order[22]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style22" id="descstyleid22" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[22]!=null){ if(descstyle[22].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t22">
                    <OPTION VALUE="file" <%if(ftype[22]!=null){ if(ftype[22].equals("file")){%>selected<%}}%> >上传文件
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l22" type="text" value="<%=flen[22]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k22">
                    <OPTION VALUE="0" <%if(iskong[22]!=null){ if(iskong[22].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[22]!=null){ if(iskong[22].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style22" id="cstyleid22" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[22]!=null){if(contentstyle[22].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck23" value="移动电话" <%if(chname[23]!=null){ if(chname[23].equals("移动电话")){%>checked<% }}%> >&nbsp;移动电话
                <input type="hidden" name="ename23" value="mobilephone">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num23" type="text" style="width: 52px" value="<%=order[23]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style23" id="descstyleid23" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[23]!=null){ if(descstyle[23].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t23" style="width: 85px">
                    <OPTION VALUE="text" <%if(ftype[23]!=null){ if(ftype[23].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l23" type="text" value="<%=flen[23]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k23">
                    <OPTION VALUE="0" <%if(iskong[23]!=null){ if(iskong[23].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[23]!=null){ if(iskong[23].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style23" id="cstyleid23" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[23]!=null){if(contentstyle[23].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px"><input type="checkbox" name="fcheck24" value="工作单位" <%if(chname[24]!=null){ if(chname[24].equals("工作单位")){%>checked<% }}%> >&nbsp;工作单位
                <input type="hidden" name="ename24" value="company">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num24" type="text" style="width: 52px" value="<%=order[24]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style24" id="descstyleid24" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[24]!=null){ if(descstyle[24].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t24" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[24]!=null){ if(ftype[24].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l24" type="text" value="<%=flen[24]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k24">
                    <OPTION VALUE="0" <%if(iskong[24]!=null){ if(iskong[24].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[24]!=null){ if(iskong[24].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style24" id="cstyleid24" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[24]!=null){if(contentstyle[24].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck25" value="单位邮政编码" <%if(chname[25]!=null){ if(chname[25].equals("单位邮政编码")){%>checked<% }}%> >
                单位邮政编码
                <input type="hidden" name="ename25" value="unitpostcode">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num25" type="text" style="width: 52px" value="<%=order[25]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style25" id="descstyleid25" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[25]!=null){ if(descstyle[25].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t25" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[25]!=null){ if(ftype[25].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l25" type="text" value="<%=flen[25]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k25">
                    <OPTION VALUE="0" <%if(iskong[25]!=null){ if(iskong[25].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[25]!=null){ if(iskong[25].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style25" id="cstyleid25" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[25]!=null){if(contentstyle[25].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck26" value="单位电话" <%if(chname[26]!=null){ if(chname[26].equals("单位电话")){%>checked<% }}%> >
                单位电话
                <input type="hidden" name="ename26" value="unitphone">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num26" type="text" style="width: 52px" value="<%=order[26]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style26" id="descstyleid26" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[26]!=null){ if(descstyle[26].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t26" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[26]!=null){ if(ftype[26].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l26" type="text" value="<%=flen[26]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k26">
                    <OPTION VALUE="0" <%if(iskong[26]!=null){ if(iskong[26].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[26]!=null){ if(iskong[26].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style26" id="cstyleid26" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[26]!=null){if(contentstyle[26].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck27" value="站台类别" <%if(chname[27]!=null){ if(chname[27].equals("站台类别")){%>checked<% }}%> >
                站台类别
                <input type="hidden" name="ename27" value="stationtype">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num27" type="text" style="width: 52px" value="<%=order[27]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style27" id="descstyleid27" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[27]!=null){ if(descstyle[27].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t27" style="width: 81px">
                    <OPTION VALUE="select" <%if(ftype[27]!=null){ if(ftype[27].equals("text")){%>selected<%}}%> >下拉框
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l27" type="text" value="<%=flen[27]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k27">
                    <OPTION VALUE="0" <%if(iskong[27]!=null){ if(iskong[27].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[27]!=null){ if(iskong[27].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style27" id="cstyleid27" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[27]!=null){if(contentstyle[27].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck28" value="申请者类别" <%if(chname[28]!=null){ if(chname[28].equals("申请者类别")){%>checked<% }}%> >
                申请者类别
                <input type="hidden" name="ename28" value="entitytype">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num28" type="text" style="width: 52px" value="<%=order[28]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style28" id="descstyleid28" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[28]!=null){ if(descstyle[28].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t28" style="width: 81px">
                    <OPTION VALUE="select" <%if(ftype[28]!=null){ if(ftype[28].equals("select")){%>selected<%}}%> >下拉列表
                    <OPTION VALUE="radio" <%if(ftype[28]!=null){ if(ftype[28].equals("radio")){%>selected<%}}%> >单选按钮
                    <OPTION VALUE="text" <%if(ftype[28]!=null){ if(ftype[28].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l28" type="text" value="<%=flen[28]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k28">
                    <OPTION VALUE="0" <%if(iskong[28]!=null){ if(iskong[28].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[28]!=null){ if(iskong[28].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style28" id="cstyleid28" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[28]!=null){if(contentstyle[28].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck29" value="站台地址" <%if(chname[29]!=null){ if(chname[29].equals("站台地址")){%>checked<% }}%> >
                站台地址
                <input type="hidden" name="ename29" value="stationaddress">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num29" type="text" style="width: 52px" value="<%=order[29]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style29" id="descstyleid29" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[29]!=null){ if(descstyle[29].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t29" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[29]!=null){ if(ftype[29].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l29" type="text" value="<%=flen[29]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k29">
                    <OPTION VALUE="0" <%if(iskong[29]!=null){ if(iskong[29].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[29]!=null){ if(iskong[29].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style29" id="cstyleid29" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[29]!=null){if(contentstyle[29].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck30" value="操作证等级" <%if(chname[30]!=null){ if(chname[30].equals("操作证等级")){%>checked<% }}%> >
                操作证等级
                <input type="hidden" name="ename30" value="opedegree">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num30" type="text" style="width: 52px" value="<%=order[30]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style30" id="descstyleid30" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[30]!=null){ if(descstyle[30].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t30" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[30]!=null){ if(ftype[30].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l30" type="text" value="<%=flen[30]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k30">
                    <OPTION VALUE="0" <%if(iskong[30]!=null){ if(iskong[30].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[30]!=null){ if(iskong[30].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style30" id="cstyleid30" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[30]!=null){if(contentstyle[30].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck31" value="操作证书编号" <%if(chname[31]!=null){ if(chname[31].equals("操作证书编号")){%>checked<% }}%> >
                操作证书编号
                <input type="hidden" name="ename31" value="opecode">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num31" type="text" style="width: 52px" value="<%=order[31]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style31" id="descstyleid31" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[31]!=null){ if(descstyle[31].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t31" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[31]!=null){ if(ftype[31].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l31" type="text" value="<%=flen[31]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k31">
                    <OPTION VALUE="0" <%if(iskong[31]!=null){ if(iskong[31].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[31]!=null){ if(iskong[31].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style31" id="cstyleid31" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[31]!=null){if(contentstyle[31].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck32" value="呼号" <%if(chname[32]!=null){ if(chname[32].equals("呼号")){%>checked<% }}%> >
                呼号
                <input type="hidden" name="ename32" value="callsign">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num32" type="text" style="width: 52px" value="<%=order[32]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style32" id="descstyleid32" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[32]!=null){ if(descstyle[32].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t32" style="width: 81px">
                    <OPTION VALUE="text" <%if(ftype[32]!=null){ if(ftype[32].equals("text")){%>selected<%}}%> >文本
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l32" type="text" value="<%=flen[32]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k32">
                    <OPTION VALUE="0" <%if(iskong[32]!=null){ if(iskong[32].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[32]!=null){ if(iskong[32].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style32" id="cstyleid32" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[32]!=null){if(contentstyle[32].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck33" value="爱好" <%if(chname[33]!=null){ if(chname[33].equals("爱好")){%>checked<% }}%> >&nbsp;爱好
                <input type="hidden" name="ename33" value="fivorate">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num33" type="text" style="width: 52px" value="<%=order[33]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style33" id="descstyleid33" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[33]!=null){ if(descstyle[33].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t33" style="width: 81px">
                    <OPTION VALUE="checkbox" <%if(ftype[33]!=null){ if(ftype[33].equals("checkbox")){%>selected<%}}%> >多选项
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l33" type="text" value="<%=flen[33]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k33">
                    <OPTION VALUE="0" <%if(iskong[33]!=null){ if(iskong[33].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[33]!=null){ if(iskong[33].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style33" id="cstyleid33" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[33]!=null){if(contentstyle[33].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck34" value="收入" <%if(chname[34]!=null){ if(chname[34].equals("收入")){%>checked<% }}%> >&nbsp;收入水平
                <input type="hidden" name="ename34" value="salary">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num34" type="text" style="width: 52px" value="<%=order[34]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style34" id="descstyleid34" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[34]!=null){ if(descstyle[34].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t34" style="width: 81px">
                    <OPTION VALUE="select" <%if(ftype[34]!=null){ if(ftype[34].equals("select")){%>selected<%}}%> >下拉列表
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l34" type="text" value="<%=flen[34]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k34">
                    <OPTION VALUE="0" <%if(iskong[34]!=null){ if(iskong[34].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[34]!=null){ if(iskong[34].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style34" id="cstyleid34" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[34]!=null){if(contentstyle[34].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" style="width: 131px">
                <input type="checkbox" name="fcheck35" value="备注" <%if(chname[35]!=null){ if(chname[35].equals("备注")){%>checked<% }}%> >&nbsp;备注
                <input type="hidden" name="ename35" value="beizhu">
            </td>
            <td class="style1" style="width: 49px">
                <input name="num35" type="text" style="width: 52px" value="<%=order[35]%>"></td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="desc_style35" id="descstyleid35" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(descstyle[35]!=null){ if(descstyle[35].equals(xzyangshi)){%>selected<% }}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
            <td class="style1" style="width: 134px">
                <SELECT NAME="t35" style="width: 81px">
                    <OPTION VALUE="select" <%if(ftype[35]!=null){ if(ftype[35].equals("select")){%>selected<%}}%> >下拉列表
                </SELECT></td>
            <td class="style1" style="width: 90px">
                <input name="l35" type="text" value="<%=flen[35]%>" style="width: 71px"></td>
            <td class="style1" colspan="2">
                <SELECT NAME="k35">
                    <OPTION VALUE="0" <%if(iskong[35]!=null){ if(iskong[35].equals("0")){%>selected<%}}%> >空
                    <OPTION VALUE="1" <%if(iskong[35]!=null){ if(iskong[35].equals("1")){%>selected<%}}%> >非空
                </SELECT></td>
            <td class="style1">
                <SELECT NAME="c_style35" id="cstyleid35" style="width: 149px;">
                    <OPTION VALUE="-1" SELECTED>选择样式
                            <%
                                            for (int i = 0; i < list.size(); i++) {
                                                String xzyangshi = (String)list.get(i);
                                        %>
                    <OPTION VALUE="<%=xzyangshi%>" <%if(contentstyle[35]!=null){if(contentstyle[35].equals(xzyangshi)){%>selected<%}}%> ><%=xzyangshi%>
                    </OPTION>
                    <%}%>
                </SELECT></td>
        </tr>
        <tr>
            <td class="style1" colspan="2">
                <input type="checkbox" name="fcheck36" value="确认" checked="checked">&nbsp;确认按钮类型
                <input type="hidden" name="ename36" value="ok">
            </td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="t36" style="width: 81px">
                    <OPTION VALUE="submit" <%if(ftype[36]!=null){ if(ftype[36].equals("submit")){%>selected<%}}%> >提交
                    <OPTION VALUE="image" <%if(ftype[36]!=null){ if(ftype[36].equals("image")){%>selected<%}}%> >图片
                </SELECT></td>
            <td class="style1" style="width: 134px">
                选择按钮图片（双击框）
            </td>
            <td class="style1" style="width: 90px">
                <a href="#" onclick="javaacript:uploadbuttonimage('ok')">上传图片</a></td>
            <td class="style1" colspan="3">
                <input name="okimage" type="text" ondblclick="javascript:selectpic('ok')" value="<%=okimage%>" readonly>&nbsp;&nbsp;<div id="okimgid" style="position:absolute;display:<%=(okimage==null)?"none":""%>"><img id="okimg_src" src="/_sys_images/buttons/<%=okimage%>" /></div></td>
        </tr>
        <tr>
            <td class="style1" colspan="2">
                <input type="checkbox" name="fcheck37" value="返回" checked="checked">&nbsp;返回按钮类型
                <input type="hidden" name="ename37" value="cancel">
            </td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="t37" style="width: 81px">
                    <OPTION VALUE="submit" <%if(ftype[37]!=null){ if(ftype[37].equals("submit")){%>selected<%}}%> >提交
                    <OPTION VALUE="image" <%if(ftype[37]!=null){ if(ftype[37].equals("image")){%>selected<%}}%> >图片
                </SELECT></td>
            <td class="style1" style="width: 134px">
                选择按钮图片（双击框）
            </td>
            <td class="style1" style="width: 90px">
                <a href="#" onclick="javaacript:uploadbuttonimage('cancel')">上传图片</a></td>
            <td class="style1" colspan="3">
                <input name="cancelimage" type="text" ondblclick="javascript:selectpic('cancel')" value="<%=cancelimage%>" readonly>&nbsp;&nbsp;<div id="cancelimgid" style="position:absolute;display:<%=(cancelimage==null)?"none":""%>"><img id="cancelimg_src" src="/_sys_images/buttons/<%=cancelimage%>" /></div></td>
        </tr>
        <tr>
            <td class="style1" colspan="2">
                <input type="checkbox" name="fcheck38" value="重置" <%if(chname[38]!=null){ if(chname[38].equals("重置")){%>checked<% }}%> >&nbsp;重置按钮类型
                <input type="hidden" name="ename38" value="reset">
            </td>
            <td class="style1" style="width: 149px">
                <SELECT NAME="t37" style="width: 81px">
                    <OPTION VALUE="submit" <%if(ftype[38]!=null){ if(ftype[38].equals("submit")){%>selected<%}}%> >提交
                    <OPTION VALUE="image" <%if(ftype[38]!=null){ if(ftype[38].equals("image")){%>selected<%}}%> >图片
                </SELECT></td>
            <td class="style1" style="width: 134px">
                选择按钮图片（双击框）
            </td>
            <td class="style1" style="width: 90px">
                <a href="#" onclick="javaacript:uploadbuttonimage('reset')">上传图片</a></td>
            <td class="style1" colspan="3">
                <input name="resetimage" type="text" ondblclick="javascript:selectpic('reset')" value="<%=resetimage%>" readonly>&nbsp;&nbsp;<div id="resetimgid" style="position:absolute;display:<%=(resetimage==null)?"none":""%>"><img id="resetimg_src" src="/_sys_images/buttons/<%=resetimage%>" /></div></td>
        </tr>
        <tr>
            <td height="50" valign="middle" class="style1" colspan="8"><label>
                <input type="button" name="Submit" value="全选" onclick="quanxuan()">
                &nbsp;&nbsp;
                <input type="button" name="Submit2" value="反选" onclick="fanxuan()">
                &nbsp;&nbsp;
                <input type="button" name="Submit3" value="全不选" onclick="quanbuxuan()">
            </label></td>
        </tr>
        <tr>
            <td colspan="8">
                是否要生成包含文件：
                <input type=radio name=innerFlag value=0 <%if(innerFlag==0){%> checked<%}%>>否
                <input type=radio name=innerFlag value=1 <%if(innerFlag==1){%> checked<%}%>>是
            </td>
        </tr>
        <tr height=24>
            <td colspan="8">标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr height=80>
            <td colspan="8">标记描述：<br><textarea rows="3" id="notes" cols="38" class=tine><%=notes%>
    </textarea>&nbsp;&nbsp;<!--a href="javascript:addextenattr()">扩展属性</a--></td>
        </tr>
        <tr height="50">
            <td align=center colspan="8">
                <input type="button" value=" 确定 " onClick="doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick="javascript:cal();" class=tine>
            </td>
        </tr>
    </form>
</table>

</body>
</html>