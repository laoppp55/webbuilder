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

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    //     修改编辑属性取值
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
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
    if(markID>0)
    {

        vmark=markMgr.getAMark(markID);
        String stylecontent=vmark.getContent();

        if(stylecontent.indexOf("[STYLES]")!=-1)
        {
            if(stylecontent.indexOf("class=\"biz_table\"")!=-1)
            {

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
                    //        System.out.println("viewtd3="+viewtd3);
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
    }




    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);

    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "信息反馈标记";

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
        String style="";
        if(tianchong!=null)
        {
            style=style+"cellpadding="+tianchong+"   ";
        }
        if(jianju!=null)
        {
            style=style+"cellspacing="+jianju+"   ";
        }

        if(biankuang==null)
        {
            biankuang="1";
        }
        if(biankuangstyle==null)
        {
            biankuangstyle="dashed";
        }
        if(biankuangstyle==null)
        {
            biankuangcol="#CCCCCC";
        }
        if(zitidaxiao==null)
        {
            zitidaxiao="12";
        }
        if(ziticol==null)
        {
            ziticol="#000000";
        }
        if(ziti==null)
        {
            ziti="宋体";
        }
        if(inputsize==null)
        {
            inputsize="18";
        }
        if(inputzisize==null)
        {
            inputzisize="12";
        }
        if(imgbiankuang==null)
        {
            imgbiankuang="0";
        }


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

        String content = "[TAG][HTMLCODE][MARKTYPE]16[/MARKTYPE][STYLES]"+head+"[/STYLES][CONTENT]&lt;table "+style+"   class=\"biz_table\" &gt;\r\n" +
                "        &lt;%\r\n" +
                "            for(int i = 0;i &lt; list.size(); i++){\r\n" +
                "                FeedBack fd = (FeedBack)list.get(i);\r\n" +
                "        %&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                标题：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;%=fd.getTitle()==null?\"\": StringUtil.gb2iso4View(fd.getTitle())%&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "         &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                内容：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;%=fd.getContent()==null?\"\": StringUtil.gb2iso4View(fd.getContent())%&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                回复：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;%=fd.getAnswer()==null?\"\": StringUtil.gb2iso4View(fd.getAnswer())%&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "        &lt;%}%&gt;\r\n" +
                "&lt;/table&gt;\r\n" +
                "&lt;table width='70%' "+style+"   class=\"biz_table\" &gt;\r\n" +
                "&lt;tr valign='bottom' width=100%&gt;\r\n" +
                "&lt;td&gt;\r\n" +
                " 总&lt;%=totalpages%&gt;页&nbsp; 第&lt;%=currentpage%&gt;页\r\n" +
                "&lt;/td&gt;\r\n" +
                "&lt;td&gt;\r\n" +
                "&lt;/td&gt;\r\n" +
                "&lt;td&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/td&gt;\r\n" +
                "&lt;td class='css_002'&gt;\r\n" +
                "&lt;%\r\n" +
                "    if((startrow-range)&gt;=0){\r\n" +
                "%&gt;\r\n" +
                "[&lt;a href='index.jsp?startrow=&lt;%=startrow-range%&gt;' class='css_002'&gt;上一页&lt;/a&gt;]\r\n" +
                "&lt;%}\r\n" +
                "  if((startrow+range)&lt;rows){\r\n" +
                "%&gt;\r\n" +
                "[&lt;a href='index.jsp?startrow=&lt;%=startrow+range%&gt;' class='css_002'&gt;下一页&lt;/a&gt;]\r\n" +
                "&lt;%}\r\n" +
                "  if(totalpages&gt;1){%&gt;\r\n" +
                "  &nbsp;&nbsp;第&lt;input type='text' name='jump' value='&lt;%=currentpage%&gt;' size='3'&gt;页&nbsp;\r\n" +
                "  &lt;a href='#' class='css_002' onclick='golist((document.all('jump').value-1) * &lt;%=range%&gt;);'&gt;GO&lt;/a&gt;\r\n" +
                "  &lt;%}%&gt;\r\n" +
                "&lt;/td&gt;\r\n" +
                "&lt;td align='right'&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/td&gt;\r\n" +
                "&lt;td align='right'&gt;&nbsp;&nbsp;&nbsp;&nbsp;&lt;/td&gt;\r\n" +
                "&lt;/tr&gt;\r\n" +
                "&lt;/table&gt;\r\n" +
                "&lt;table "+style+"   class=\"biz_table\" &gt;\r\n" +
                "        &lt;form name='form' action='feedback.jsp' onsubmit='return check();'&gt;\r\n" +
                "            &lt;input type='hidden' name='siteid' value='&lt;%=siteid%&gt;'&gt;\r\n" +
                "            &lt;input type='hidden' name='startflag' value='1'&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                标题：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;input type='text' name='title' value=''&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "         &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                内容：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;textarea rows='10' cols='50' name='content'&gt;&lt;/textarea&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                Email：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;input type='text' name='email' value=''&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                电话：\r\n" +
                "            &lt;/td&gt;\r\n" +
                "            &lt;td valign='top'&gt;\r\n" +
                "                &lt;input type='text' name='phone' value=''&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "        &lt;tr&gt;\r\n" +
                "            &lt;td valign='top' colspan='2'&gt;\r\n" +
                "                &lt;input type='submit' name='sub' value='提交'&gt;\r\n" +
                "            &lt;/td&gt;\r\n" +
                "        &lt;/tr&gt;\r\n" +
                "            &lt;/form&gt;\r\n" +
                "&lt;/table&gt;[/CONTENT][/HTMLCODE][/TAG]";

        content = StringUtil.replace(content,"&lt;","<");
        content = StringUtil.replace(content,"&gt;",">");
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
        mark.setMarkType(16);
        int orgmarkID = markID;
        if (orgmarkID > 0)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String viewer = request.getHeader("user-agent");
        String markname = "信息反馈";

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
    <title>定义用户评论表单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
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
                markform.action = "addFeedback.jsp";
                markform.method = "post";
                markform.target = "_self"
                markform.submit();
            }
        }
    </script>
</head>

<body bgcolor="#CCCCCC">
<table width="100%" border="0" align="center">
    <form name="markform">
        <input type=hidden name=doCreate value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=column value="<%=columnID%>">
        <input type=hidden name=type value=17>
        <input type="hidden" name="mark" value="<%=markID%>">
        <tr height="30">
            <td>请选择表格背景色：</td>
        </tr>
        <tr height=24>
            <td>填充值&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="text" size="8" name="tianchong" <%if(viewtable1!=null){%>value=<%=viewtable1%><%}%>>
                px
                &nbsp;&nbsp;间距值&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;
                <input type="text" size="8" name="jianju" <%if(viewtable2!=null){%>value=<%=viewtable2%><%}%>>
                px
                &nbsp; 边框值&nbsp;
                <input type="text" size="8" name="biankuang" <%if(viewtable3!=null){%>value=<%=viewtable3%><%}%>>
                px
                &nbsp;&nbsp;边框样式
                <label>
                    <select name="biankuangstyle">
                        <option value="dashed" <%if(viewtable4!=null){ if(viewtable4.equals("dashed")){%>selected<%}}%> >虚线</option>
                        <option value="dotted" <%if(viewtable4!=null){if(viewtable4.equals("dotted")){%>selected<%}}%>> 点划线</option>
                        <option value="solid" <%if(viewtable4!=null){if(viewtable4.equals("solid")){%>selected<%}}%>> 实线</option>
                        <option value="double" <%if(viewtable4!=null){if(viewtable4.equals("double")){%>selected<%}}%>> 双线</option>
                    </select>
                </label>边框颜色<div id="colorpanel" style="position:absolute;display:none;width:253px;height:177px;"></div><input type="text" size="8" alt="color2" name="biankuangcol" <%if(viewtable5!=null){ if(!viewtable5.equals("null")){%>value="<%=viewtable5%>"<%}}%>  onclick="OnDocumentClick();"><script language="javascript">
                    intocolor();
                </script> </td>
        </tr>
        <tr height=24>
            <td>字体&nbsp;&nbsp;&nbsp;
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="text" size="8" name="ziti" <%if(viewtd3!=null){%>value=<%=viewtd3%><%}%>>
                px              &nbsp;&nbsp;字体大小px&nbsp;&nbsp;
                <input type="text" size="8" name="zitidaxiao" <%if(viewtd1!=null){%>value=<%=viewtd1%><%}%>>
                px
                文字颜色&nbsp;
                <div id="colorpanel" style="position:absolute;display:none;width:253px;height:177px;"></div><input type="text" size="8" alt="color1" name="ziticol" <%if(viewtd2!=null){%>value="<%=viewtd2%>"<%}%>  onclick="OnDocumentClick();"><script language="javascript">
                    intocolor();
                </script></td>
        </tr>
        <tr><td>文本框大小&nbsp;&nbsp;
            <input type="text" size="8" name="inputsize" <%if(viewput2!=null){%>value=<%=viewput2%><%}%>>
            px 文本框文字大小
            <input type="text" size="8" name="inputzisize" <%if(viewput1!=null){%>value=<%=viewput1%><%}%>>
            px </td>
        </tr>
        <tr><td>图片边框&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="text" size="8" name="imgbiankuang" <%if(viewimg1!=null){%>value=<%=viewimg1%><%}%>></td></tr>
        <tr height=24>
            <td>标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr height=80>
            <td>标记描述：<br><textarea rows="3" id="notes" cols="38" class=tine><%=notes%>
            </textarea></td>
        </tr>
        <tr height="50">
            <td align=center>
                <input type="button" value=" 确定 " onClick = "doit();" class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value=" 取消 " onClick = "javascript:cal();" class=tine>
            </td>
        </tr>
    </form>
</table>

</body>
</html>