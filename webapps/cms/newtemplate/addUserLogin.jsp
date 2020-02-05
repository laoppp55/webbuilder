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

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int gaoji=-1;
    //修改编辑属性取值
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    System.out.println("markID="+markID);
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark=null;
    String viewtable="";
    String viewtable1="";
    String viewtable2="";
    String viewtable3="";
    String viewtable4="";
    String viewtable5="";

    String viewtd1="";
    String viewtd2="";
    String viewtd3="";

    String viewput1="";
    String viewput2="";

    String viewimg1="";
    String loginimg = "/images/login_go.gif";
    String regimg = "注册新用户";
    String mimaimg = "忘记密码";
    String s_loginimg = "";
    String s_regimg = "";
    String s_mimaimg = "";
    String gjform="";

    if(markID>0)
    {
        vmark=markMgr.getAMark(markID);
        String gjstr="";
        String content = vmark.getContent();
        String stylecontent=content;
          if(stylecontent.indexOf("[GAOJI]")!=-1)
        {
            gjstr=stylecontent.substring(stylecontent.indexOf("[GAOJI]")+"[GAOJI]".length(),stylecontent.indexOf("[/GAOJI]"));
           try{
            gaoji=Integer.parseInt(gjstr);
           }catch(Exception e){
               gaoji=-1;
           }

        }
        if(gaoji!=-1)
        {
           if(stylecontent.indexOf("[CONTENT]")>-1)
           {
               gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));
               gjform=gjform.substring(gjform.indexOf("class=\"biz_table\">")+"class=\"biz_table\">".length());

               System.out.println("gjform="+gjform);
           }
        }

        if(stylecontent.indexOf("[STYLES]")!=-1) {
            if(stylecontent.indexOf("class=\"biz_table\"")!=-1) {
                viewtable=stylecontent.substring(stylecontent.indexOf("<table"),stylecontent.indexOf("class=\"biz_table\""));
                if(viewtable.indexOf("cellpadding=")!=-1)
                {
                    // System.out.println("viewtable1="+viewtable1+" "+viewtable1.indexOf("cellpadding=")+"  "+viewtable1.lastIndexOf(" "));
                    viewtable1=viewtable.substring(viewtable.indexOf("cellpadding="),viewtable.lastIndexOf(" "));
                    if(viewtable1.indexOf("cellspacing=")!=-1)
                    {

                        viewtable2=viewtable1.substring(viewtable1.indexOf("cellspacing=")+12,viewtable1.lastIndexOf(" "));
                        if(viewtable2.indexOf(" ")!=-1)
                        {
                            viewtable2=viewtable2.replaceAll(" ","");
                        }
                    }
                    //获得 cellpadding的值
                    if(viewtable1.indexOf("cellpadding=")!=-1){
                        viewtable1=viewtable1.substring(viewtable1.indexOf("cellpadding=")+12,viewtable1.indexOf(" "));
                    }

                }
                if(viewtable.indexOf("cellspacing=")!=-1)
                {
                    // System.out.println("viewtable1="+viewtable1+" "+viewtable1.indexOf("cellpadding=")+"  "+viewtable1.lastIndexOf(" "));
                    viewtable=viewtable.substring(viewtable.indexOf("cellspacing="),viewtable.lastIndexOf(" "));
                    if(viewtable.indexOf("cellspacing=")!=-1)
                    {

                        viewtable2=viewtable.substring(viewtable.indexOf("cellspacing=")+12,viewtable.lastIndexOf(" "));
                        if(viewtable2.indexOf(" ")!=-1)
                        {
                            viewtable2=viewtable2.replaceAll(" ","");
                        }
                    }
                    //获得 cellpadding的值
                }
            }
            //取样式表[styles]里面的内容
            if(stylecontent.indexOf("<!--")!=-1)
            {
                stylecontent=stylecontent.substring(stylecontent.indexOf("<!--")+4,stylecontent.indexOf("-->"));
                //   System.out.println("stylecontent="+stylecontent);
            }
            //先取.biz_table这里面的内容
            if(stylecontent.indexOf("biz_table{")!=-1)
            {
                viewtable3=stylecontent.substring(stylecontent.indexOf("biz_table{")+11,stylecontent.indexOf("}"));
                //   System.out.println("viewtable3="+viewtable3);
                if(viewtable3.indexOf(" ")!=-1)
                {
                    viewtable4=viewtable3.substring(viewtable3.indexOf(" ")+1,viewtable3.lastIndexOf(";"));
                    if(viewtable4.indexOf(" ")!=-1)
                    {
                        viewtable5=viewtable4.substring(viewtable4.indexOf(" ")+1,viewtable4.length());
                        //       System.out.println("viewtable5="+viewtable5+"bb");
                    }
                    if(viewtable4.indexOf(" ")!=-1)
                    {
                        viewtable4=viewtable4.substring(0,viewtable4.indexOf(" "));
                        //        System.out.println("viewtable4="+viewtable4);
                    }
                }
                if(viewtable3.indexOf("border:")!=-1)
                {
                    viewtable3=viewtable3.substring(viewtable3.indexOf("border:")+7,viewtable3.indexOf(" "));
                    //      System.out.println("viewtable3="+viewtable3);
                }
            }
            // 先取.biz_table td这里面的内容
            if(stylecontent.indexOf("biz_table td{")!=-1)
            {
                viewtd1=stylecontent.substring(stylecontent.indexOf("biz_table td{")+14,stylecontent.indexOf(".biz_table input"));
                //     System.out.println("viewtd1="+viewtd1);
                if(viewtd1.indexOf("font-family:")!=-1)
                {
                    viewtd3=viewtd1.substring(viewtd1.indexOf("font-family:")+12,viewtd1.indexOf(" ; text-align"));
                    //System.out.println("viewtd3="+viewtd3);
                }
                if(viewtd1.indexOf("color:")!=-1)
                {
                    viewtd2=viewtd1.substring(viewtd1.indexOf("color:")+6,viewtd1.indexOf("font-family:")-2);
                    //       System.out.println("viewtd2="+viewtd2);
                }
                if(viewtd1.indexOf("font-size:")!=-1)
                {
                    viewtd1=viewtd1.substring(viewtd1.indexOf("font-size:")+10,viewtd1.indexOf(" ")-3);
                    //     System.out.println("viewtd1="+viewtd1);
                }
            }
            //取.biz_table input这里面的内容
            if(stylecontent.indexOf("biz_table input{")!=-1)
            {
                viewput1=stylecontent.substring(stylecontent.indexOf("biz_table input{")+17,stylecontent.indexOf("biz_table img"));
                //        System.out.println("viewput1="+viewput1);
                if(viewput1.lastIndexOf("size:")!=-1)
                {
                    viewput2=viewput1.substring(viewput1.lastIndexOf("size:")+5,viewput1.lastIndexOf("px;"));
                    //            System.out.println("viewput2="+viewput2);
                }
                if(viewput1.indexOf("font-size:")!=-1)
                {
                    viewput1=viewput1.substring(viewput1.indexOf("font-size:")+10,viewput1.lastIndexOf("px;  size:"));
                    //          System.out.println("viewput1="+viewput1);
                }

            }
            //biz_table img取这里面的样式
            if(stylecontent.indexOf("biz_table img{")!=-1)
            {
                viewimg1=stylecontent.substring(stylecontent.indexOf("biz_table img{")+15,stylecontent.lastIndexOf("}"));
                if(viewimg1.indexOf("border:")!=-1)
                {
                    viewimg1=viewimg1.substring(viewimg1.indexOf("border:")+7,viewimg1.lastIndexOf(";"));
                    //          System.out.println("viewimg1="+viewimg1);
                }
            }
        }

        int sposi = -1;
        int eposi = -1;
        //获取login图标
        sposi = content.indexOf("[LOGINIMG]");
        eposi = content.indexOf("[/LOGINIMG]");
        if (sposi != -1 && eposi != -1) {
            s_loginimg = content.substring(sposi + "[LOGINIMG]".length(),eposi);
            loginimg = s_loginimg;
        }
        //获取用户注册图标
        sposi = content.indexOf("[REGIMG]");
        eposi = content.indexOf("[/REGIMG]");
        if (sposi != -1 && eposi != -1) {
            s_regimg = content.substring(sposi + "[REGIMG]".length(),eposi);
            regimg = s_regimg;
        }
        //获取忘记密码图标
        sposi = content.indexOf("[MIMAIMG]");
        eposi = content.indexOf("[/MIMAIMG]");
        if (sposi != -1 && eposi != -1) {
            s_mimaimg = content.substring(sposi + "[MIMAIMG]".length(),eposi);
            mimaimg = s_mimaimg;
        }

        //System.out.println("loginimg=" + s_loginimg);
        //System.out.println("regimg=" + s_regimg);
        //System.out.println("mimaimg=" + s_mimaimg);
    }

    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "用户登录页";
     String gjcontent=ParamUtil.getParameter(request,"gjcontent");
    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");


    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        innerFlag = ParamUtil.getIntParameter(request, "innerFlag", 0);
        listType = ParamUtil.getIntParameter(request, "listType", 0);
        String tianchong=ParamUtil.getParameter(request,"tianchong");
        String jianju=ParamUtil.getParameter(request,"jianju");
        String biankuang=ParamUtil.getParameter(request,"biankuang");
        String biankuangstyle=ParamUtil.getParameter(request,"biankuangstyle");
        String ziti=ParamUtil.getParameter(request,"ziti");
        String zitidaxiao=ParamUtil.getParameter(request,"zitidaxiao");
        String ziticol=ParamUtil.getParameter(request,"ziticol");
        String inputsize=ParamUtil.getParameter(request,"inputsize");
        String inputzisize=ParamUtil.getParameter(request,"inputzisize");
        String biankuangcol=ParamUtil.getParameter(request,"biankuangcol");
        String imgbiankuang=ParamUtil.getParameter(request,"imgbiankuang");
        s_loginimg = ParamUtil.getParameter(request,"login_img");
        s_regimg = ParamUtil.getParameter(request,"reg_img");
        s_mimaimg = ParamUtil.getParameter(request,"mima_img");
        gaoji=ParamUtil.getIntParameter(request,"gaoji",-1);

        String style="";
        if(tianchong!=null)   style=style+"cellpadding="+tianchong+"   ";
        if(jianju!=null)      style=style+"cellspacing="+jianju+"   ";

        if(biankuang==null)       biankuang="1";
        if(biankuangstyle==null)  biankuangstyle="dashed";
        if(biankuangstyle==null)  biankuangcol="#CCCCCC";
        if(zitidaxiao==null)      zitidaxiao="12";
        if(ziticol==null)         ziticol="#000000";
        if(ziti==null)            ziti="宋体";
        if(inputsize==null)       inputsize="18";
        if(inputzisize==null)     inputzisize="12";
        if(imgbiankuang==null)    imgbiankuang="0";

        if (loginimg == null || loginimg == "")
            loginimg="/images/login_go.gif";
        else
            loginimg="/_sys_images/" + s_loginimg;
        if (regimg == null || regimg == "")
            regimg="注册新用户";
        else
            regimg="<img src=\"/_sys_images/" + s_regimg + "\" border=\"0\" />";
        if (mimaimg == null || mimaimg == "")
            mimaimg="忘记密码";
        else
            mimaimg="<img src=\"/_sys_images/" + s_mimaimg + "\" border=\"0\" />";

        String head="<style type=\"text/css\">\r\n" +
                "<!--";
        head=head+".biz_table{ border:"+biankuang+" "+biankuangstyle+" "+biankuangcol +";\r\n" +
                " } \r\n" +
                "";
        head=head+".biz_table td{ font-size:"+zitidaxiao+"px; color:"+ziticol+"; font-family:"+ziti+" ; text-align:left;\r\n" +
                "}\r\n";
        head=head+".biz_table input{ font-size:"+inputsize+"px;  size:"+inputzisize+"px;\r\n" +
                "\r\n" +
                "}\r\n";
        head=head+"biz_table img{ border:"+imgbiankuang+";\r\n" +
                "}\r\n";
        head=head+"-->\r\n" +
                "</style>";

        String content = "[TAG][HTMLCODE][MARKTYPE]" + type +"[/MARKTYPE][LOGINIMG]" + s_loginimg + "[/LOGINIMG][REGIMG]" + s_regimg + "[/REGIMG][MIMAIMG]" + s_mimaimg + "[/MIMAIMG][STYLES]"+head+"[/STYLES][CONTENT]" +
                "<table  class=\"biz_table\" border='0'>\r\n" +
                "<form name=\"loginForm\" method=\"post\" action=\"/" + sitename +"/_prog/login.jsp\" onsubmit=\"return check();\">\r\n" +
                "<input type=\"hidden\" name=\"doLogin\" value=\"true\">\r\n" +
                "<input type=\"hidden\" name=\"refererurl\" value=\"&lt;%=fromurl%&gt;\">\r\n" +
                "<TR><TD>用户名：</TD>\r\n" +
                "<TD><INPUT type=\"text\" name=\"username\" value=\"\" size=\"" + inputsize + "\"> </TD></TR>\r\n" +
                "<TR><TD>密&nbsp;&nbsp;码：</TD>\r\n" +
                "<TD><INPUT type=\"password\" name=\"password\" size=\""  +  inputsize + "\"></TD></TR>\r\n" +
                "<TR><TD>&nbsp;</TD><TD align=\"right\">\r\n" +
                "<DIV align=\"center\" class=\"biz_table\"><input type=\"image\" src=\"" + loginimg +"\" value=\"提交\" /></DIV></TD></TR></form></TABLE>\r\n" +
                "<TABLE cellSpacing=\"0\" cellPadding=\"5\" width=\"100%\"  border=\"0\" class=\"biz_table\">\r\n" +
                "<TR><TD><A href=\"/"+sitename + "/_prog/register.jsp\" target=\"_blank\">" + regimg +"</A></TD>\r\n" +
                "<TD><a href=#>" + mimaimg + "</a></TD></TR></TABLE>\r\n" +
                "[/CONTENT][/HTMLCODE][/TAG]";
        //高级自定义表单
         if(gjcontent!=null&&gaoji!=-1)
        {
            content = "[TAG][HTMLCODE][MARKTYPE]" + type +"[/MARKTYPE][STYLES]"+head+"[/STYLES][GAOJI]"+gaoji+"[/GAOJI][CONTENT]<table "+style+"   class=\"biz_table\">" +
            gjcontent+
                "[/CONTENT][/HTMLCODE][/TAG]";
        }
        boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
        String relatedCID = "(0)";

        mark mark = new mark();
        mark.setID(markID);
        mark.setColumnID(columnID);
        mark.setSiteID(siteID);
        mark.setContent(content);
        mark.setChinesename(cname);
        mark.setNotes(notes);
        mark.setInnerHTMLFlag(innerFlag);
        mark.setFormatFileNum(listType);
        mark.setRelatedColumnID(relatedCID);
        mark.setMarkType(19);
        int orgmarkID = markID;
        if (orgmarkID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String viewer = request.getHeader("user-agent");
        String markname = "用户登录";

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
        }
        return;
    }
%>

<html>
<head>
    <base target="_self" >
    <title>用户登录页</title>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script language="javascript" src="../js/color.js"></script>
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript">
        function cal() {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                window.returnValue = "";
                top.close();
            } else {
                top.close();
            }
        }

        function doit()
        {
            var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
            if (isMSIE) {
                markform.action = "addUserLogin.jsp";
                markform.method = "post";
                markform.target = "_self"
                markform.submit();
            }
        }

        function selpic(type)
        {
            winStr = "selectIcon.jsp";
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");

            var loginimg = document.getElementById("loginimg");
            var regimg = document.getElementById("regimg");
            var mimaimg = document.getElementById("mimaimg");
            if (type == 1) {
                markform.login_img.value=returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    loginimg.style.display = "";
                    document.getElementById("loginsrc_img").src="/_sys_images/" + returnvalue;
                }
            }
            else if (type == 2) {
                markform.reg_img.value = returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    regimg.style.display = "";
                    document.getElementById("regsrc_img").src="/_sys_images/" + returnvalue;
                }
            }
            else {
                markform.mima_img.value = returnvalue;
                if (returnvalue != null && returnvalue != "") {
                    mimaimg.style.display = "";
                    document.getElementById("mimasrc_img").src="http://www.coosite.com/webbuilder/sites/images/" + returnvalue;
                }
            }
        }
function openlogin()
        {
            <%if(gaoji==-1){%>
            showModalDialog("gaojilogin.jsp?id=4",window,"font-family:Verdana; font-size:12; dialogWidth:72em; dialogHeight:54em;status:no");
            <%}else{%>
        showModalDialog("gaojilogin.jsp?id=4&markid=<%=markID%>",window,"font-family:Verdana; font-size:12; dialogWidth:72em; dialogHeight:54em;status:no");
        <%}%>
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=19>
         <input type="hidden" name="gaoji" value="<%=gaoji%>">
        <input type="hidden" name="gjcontent" id="gjcontent">
        <input type="hidden" name="mark" value="<%=markID%>">
        <tr height="30">
            <td>请选择表格背景色：</td>
        </tr>
        <tr height=24>
            <td>填充值</td>
            <td><input type="text" size="8" name="tianchong" <%if(viewtable1!=null){%>value=<%=viewtable1%><%}%>>px</td>
            <td>间距值</td>
            <td><input type="text" size="8" name="jianju" <%if(viewtable2!=null){%>value=<%=viewtable2%><%}%>>px</td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>边框宽度</td>
            <td><input type="text" size="8" name="biankuang" <%if(viewtable3!=null){%>value=<%=viewtable3%><%}%>>px</td>
            <td>边框样式</td>
            <td><label>
                <select name="biankuangstyle">
                    <option value="dashed" <%if(viewtable4!=null){ if(viewtable4.equals("dashed")){%>selected<%}}%> >虚线</option>
                    <option value="dotted" <%if(viewtable4!=null){if(viewtable4.equals("dotted")){%>selected<%}}%>> 点划线</option>
                    <option value="solid" <%if(viewtable4!=null){if(viewtable4.equals("solid")){%>selected<%}}%>> 实线</option>
                    <option value="double" <%if(viewtable4!=null){if(viewtable4.equals("double")){%>selected<%}}%>> 双线</option>
                </select>
            </label>
            </td>
            <td>边框颜色<div id="colorpanel" style="position:absolute;display:none"></div>
                <input type="text" size="8" alt="color2" name="biankuangcol" <%if(viewtable5!=null){ if(!viewtable5.equals("null")){%>value="<%=viewtable5%>"<%}}%>  onclick="OnDocumentClick();"><script language="javascript">
                intocolor();
            </script> </td>
        </tr>
        <tr height=24>
            <td>字体</td>
            <td><select ID="zitiid" name="ziti" style="width:90">
                <option value="宋体" <%=(viewtd3.equals("宋体"))?"selected":""%>>宋体
                <option value="黑体" <%=(viewtd3.equals("黑体"))?"selected":""%>>黑体
                <option value="隶书" <%=(viewtd3.equals("隶书"))?"selected":""%>>隶书
                <option value="楷体_GB2312" <%=(viewtd3.equals("楷体_GB2312"))?"selected":""%>>楷体
                <option value="仿宋_GB2312" <%=(viewtd3.equals("仿宋_GB2312"))?"selected":""%>>仿宋
                <option value="MS Song" <%=(viewtd3.equals("MS Song"))?"selected":""%>>MS宋体
                <option value="MS Hei" <%=(viewtd3.equals("MS Hei"))?"selected":""%>>MS黑体
                <option value="Arial" <%=(viewtd3.equals("Arial"))?"selected":""%>>Arial
                <option value="Courier" <%=(viewtd3.equals("Courier"))?"selected":""%>>Courier
                <option value="Tahoma" <%=(viewtd3.equals("Tahoma"))?"selected":""%>>Tahoma
                <option value="Times New Roman" <%=(viewtd3.equals("Times New Roman"))?"selected":""%>>Times New Roman
                <option value="Verdana" <%=(viewtd3.equals("Verdana"))?"selected":""%>>Verdana
            </select>
            </td>

            <!--input type="text" size="8" name="ziti" <%if(viewtd3!=null){%>value=<%=viewtd3%><%}%>-->
            <td>字体大小</td>
            <td><input type="text" size="8" name="zitidaxiao" <%if(viewtd1!=null){%>value=<%=viewtd1%><%}%>>px</td>
            <td>文字颜色</td>
            <td><div id="colorpane2" style="position:absolute;display:none"></div>
                <input type="text" size="8" alt="color1" name="ziticol" <%if(viewtd2!=null){%>value="<%=viewtd2%>"<%}%>  onclick="OnDocumentClick();"><script language="javascript">
                intocolor();
            </script></td>
        </tr>
        <tr>
            <td>文本框长度</td>
            <td><input type="text" size="8" name="inputsize" <%if(viewput2!=null){%>value=<%=viewput2%><%}%>></td>
            <td>输入文字大小</td>
            <td><input type="text" size="8" name="inputzisize" <%if(viewput1!=null){%>value=<%=viewput1%><%}%>>px</td>
            <td>图片边框</td>
            <td><input type="text" size="8" name="imgbiankuang" <%if(viewimg1!=null){%>value=<%=viewimg1%><%}%>>px</td>
        </tr>
        <tr>
            <td>登录图片(<a href="javascript:selpic(1)">选择</a>)</td>
            <td><input type="text" size="8" name="login_img" value="<%=(loginimg.equals("/images/login_go.gif")?"":s_loginimg)%>" readonly><div id="loginimg" style="position:absolute;display:<%=(loginimg.equals("/images/login_go.gif")?"none":"")%>"><img id="loginsrc_img" src="<%=(loginimg.equals("/images/login_go.gif")?"":"/_sys_images/" + s_loginimg)%>" /></div></td>
            <td>注册图片(<a href="javascript:selpic(2)">选择</a>)</td>
            <td><input type="text" size="8" name="reg_img" value="<%=(regimg.equals("注册新用户")?"":s_regimg)%>" readonly><div id="regimg" style="position:absolute;display:<%=(regimg.equals("注册新用户")?"none":"\"\"")%>"><img id="regsrc_img" src="<%=(regimg.equals("注册新用户")?"":"/_sys_images/" + s_regimg)%>" /></div></td>
            <td>忘记密码图片(<a href="javascript:selpic(3)">选择</a>)</td>
            <td><input type="text" size="8" name="mima_img" value="<%=(mimaimg.equals("忘记密码")?"":s_mimaimg)%>" readonly><div id="mimaimg" style="position:absolute;display:<%=(mimaimg.equals("忘记密码")?"none":"\"\"")%>"><img id="mimasrc_img" src="<%=(mimaimg.equals("忘记密码")?"":"/_sys_images/" + s_mimaimg)%>" /></div></td>
        </tr>
        <tr>
            <td>标记中文名称</td>
            <td><input name=chineseName size=20 value="<%=cname%>" class=tine></td>
            <td>标记描述</td>
            <td><textarea rows="3" id="notes" cols="38" class=tine><%=notes%></textarea>&nbsp;&nbsp;<a href="javascript:openlogin()">高级选项</a></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
        <tr height="50">
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td align=center><input type="button" value=" 确定 " onClick = "doit();" class=tine></td>
            <td align=center><input type="button" value=" 取消 " onClick = "javascript:cal();" class=tine></td>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
        </tr>
    </form>
</table>

</body>
</html>