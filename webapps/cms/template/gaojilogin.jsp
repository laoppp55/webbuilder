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

<%@ taglib uri="/FCKeditor" prefix="FCK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    //System.out.println("authToken="+authToken);
    String sitename = authToken.getSitename();
    int id= ParamUtil.getIntParameter(request,"id",-1);
    int markID=ParamUtil.getIntParameter(request,"markid",-1);
    mark vmark=null;

    IMarkManager markMgr = markPeer.getInstance();
    int gaoji=0;
    String gjstr="";
    String gjform="";
    System.out.println("ss-------------------------s"+markID);
    if(markID>0)
    {
        vmark=markMgr.getAMark(markID);
        String stylecontent=vmark.getContent();
        System.out.println("id="+id+"    "+stylecontent);
        if(stylecontent.indexOf("[GAOJI]")!=-1)
        {
            gjstr=stylecontent.substring(stylecontent.indexOf("[GAOJI]")+"[GAOJI]".length(),stylecontent.indexOf("[/GAOJI]"));
            try{
                gaoji=Integer.parseInt(gjstr);
            }catch(Exception e){
                gaoji=-1;
            }

        }

        // if(gaoji!=-1)
        //{

        if(id==0){
            if(stylecontent.indexOf("[CONTENT]")>-1)
            {
                gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));
                gjform=gjform.substring(gjform.indexOf("<div id=\"biz_user_login_form\">")+"<div id=\"biz_user_login_form\">".length(),gjform.indexOf("<script language=javascript>checklogin();</script>"));

                System.out.println("gjform="+gjform);
            }
        }

        // }
        //用户自定义表单
        //System.out.println("gaoji="+gaoji);
        //System.out.println("id="+id);
        if(id==1){
            System.out.println("stylecontent="+stylecontent);
            if(stylecontent.indexOf("[CONTENT]")>-1)
            {
                gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));
                gjform=gjform.substring(gjform.indexOf("class=\"biz_table\">")+"class=\"biz_table\">".length());

                //System.out.println("gjform="+gjform);
            }
        }
        if(id==2)
        {
            if(stylecontent.indexOf("[CONTENT]")>-1)
            {
                gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));


                // System.out.println("gjform="+gjform);
            }
        }
        if(id==3)
        {
            if(stylecontent.indexOf("[CONTENT]")>-1)
            {
                gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));


                // System.out.println("gjform="+gjform);
            }
        }
        if(id==4)
        {
            if(stylecontent.indexOf("[CONTENT]")>-1)
            {
                gjform=stylecontent.substring(stylecontent.indexOf("[CONTENT]")+"[CONTENT]".length(),stylecontent.indexOf("[/CONTENT]"));


                // System.out.println("gjform="+gjform);
            }
        }


    }else{

        if(id==1)
        {
            gjform="<form name=\"form\" id=\"form\" method=\"POST\" >\r\n" +
                    "    <input type=\"hidden\" name=\"startflag\" value=\"1\">\r\n" +
                    "    <input type=\"hidden\" name=\"doCreate\" value=\"1\">\r\n" +
                    "    <table width=\"100%\" border=\"0\">\r\n" +
                    "        <tr>\r\n" +
                    "            <td >用&nbsp;户&nbsp;名:</td>\r\n" +
                    "            <td >&nbsp;&nbsp;\r\n" +
                    "                <input id=\"username\" name=\"username\" type=\"text\" value=\"请输入用户名\" onFocus=\"if (value =='请输入用户名'){value =''}\" onBlur=\"check_user()\"/></td>\r\n" +
                    "            <td ><div id=\"u_mag\"></div></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td >邮&nbsp;&nbsp;&nbsp;&nbsp;箱:</td>\r\n" +
                    "            <td >&nbsp;&nbsp;\r\n" +
                    "                <input id=\"email\" name=\"email\" type=\"text\" onBlur=\"check_email()\"/></td>\r\n" +
                    "            <td ><div id=\"e_mag\"></div></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>密&nbsp;&nbsp;&nbsp;&nbsp;码:</td>\r\n" +
                    "            <td>&nbsp;&nbsp;\r\n" +
                    "                <input name=\"passWord1\" id=\"passWord1\" type=\"password\" onBlur=\"javascript:checkPassword1()\"/></td>\r\n" +
                    "            <td><div id=\"p_mag1\"></div></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>确认密码:</td>\r\n" +
                    "            <td>&nbsp;&nbsp;\r\n" +
                    "                <input name=\"passWord2\" id=\"passWord2\" type=\"password\" onBlur=\"javascript:checkPassword2()\"/></td>\r\n" +
                    "            <td><div id=\"p_mag2\"></div></td>\r\n" +
                    "        </tr>\r\n" +
                    "<tr><td>真实姓名</td><td>&nbsp;&nbsp;<input type='text' name=realname></td></tr>"+
                    "<tr><td>地址</td><td>&nbsp;&nbsp;<input type='text' name=address></td></tr>"+
                    "<tr><td>邮政编码</td><td>&nbsp;&nbsp;<input type='text' name=zip></td></tr>"+
                    "<tr><td>固定电话</td><td>&nbsp;&nbsp;<input type='text' name=phone></td></tr>"+
                    "<tr><td>移动电话</td><td>&nbsp;&nbsp;<input type='text' name=mphone></td></tr>"+
                    "        <tr>\r\n" +
                    "            <td>验&nbsp;证&nbsp;码:</td>\r\n" +
                    "            <td>&nbsp;&nbsp;\r\n" +
                    "                <input id=\"txtVerify\" name=\"txtVerify\" type=\"text\" />&nbsp;<img  name=\"cod\" src=\"/_commons/drawImage.jsp\" border=1>&nbsp;&nbsp;<a href=\"#\" onclick=\"javascript:shuaxin()\">看不清楚?换下一张</a></td>\r\n" +
                    "            <td><div id=\"v_mag\"></div></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td><input type=\"button\" value=\"注册\"  onclick=\"javascript:check()\"/></td>\r\n" +
                    "            <td></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "    </table>\r\n" +
                    "</form>" ;
        }
        if(id==2)
        {
            String left="<";
            String right=">";
            gjform=  "<form name=\"form1\" id=\"form1\" method=\"POST\">\r\n" +
                    "    <input type=\"hidden\" name=\"memberid\" value=\"\"&lt;%= memberid%&gt;\"\">\r\n" +
                    "    <input type=\"hidden\" name=\"doUpdate\" value=\"1\">\r\n" +
                    "    <table width=\"100%\" border=\"0\" align=\"center\"    class=\"biz_table\">\r\n" +
                    "        <tr>\r\n" +
                    "            <td>用户名：</td>\r\n" +
                    "            <td><input type=\"text\" id=\"0\" name=\"0\" value=\"&lt;%=memberid%&gt;\" disabled=disabled></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>真实姓名</td>\r\n" +
                    "            <td><input type=\"text\" id=\"Rname\" name=\"Rname\" value=\"&lt;%=UserName%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>旧密码</td>\r\n" +
                    "            <td><input type=\"password\" id=\"pass0\" name=\"pass0\"></td>\r\n" +
                    "            <td>\r\n" +
                    "                <div id=\"tishi\"></div>\r\n" +
                    "            </td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>新密码</td>\r\n" +
                    "            <td><input type=\"password\" id=\"pass\" name=\"pass\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>确认新密码</td>\r\n" +
                    "            <td><input type=\"password\" id=\"pass1\" name=\"pass1\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>联系人</td>\r\n" +
                    "            <td><input type=\"text\" id=\"lianxi\" name=\"lianxi\" value=\"&lt;%=lianxi%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>国家</td>\r\n" +
                    "            <td><input type=\"text\" id=\"con\" name=\"con\" value=\"&lt;%=con%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>所在城市</td>\r\n" +
                    "            <td><input type=\"text\" id=\"city\" name=\"city\" value=\"&lt;%=city%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>邮编</td>\r\n" +
                    "            <td><input type=\"text\" id=\"post\" name=\"post\" value=\"&lt;%=post%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>电话号码</td>\r\n" +
                    "            <td><input type=\"text\" id=\"phone\" name=\"phone\" value=\"&lt;%=phone%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>移动电话</td>\r\n" +
                    "            <td><input type=\"yphone\" name=\"yphone\" value=\"&lt;%=yphone%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>传真</td>\r\n" +
                    "            <td><input type=\"text\" id=\"chuanzhen\" name=\"chuanzhen\" value=\"&lt;%=chuanzhen%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>出生日期</td>\r\n" +
                    "            <td><input type=\"text\" id=\"birth\" name=\"birth\" value=\"&lt;%=birth%&gt;\"></td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td><input type=\"button\" value=\"提交\" onclick=\"javascript:update_do()\"></td>\r\n" +
                    "            <td>&nbsp;</td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "        <tr>\r\n" +
                    "            <td>&nbsp;</td>\r\n" +
                    "            <td>&nbsp;</td>\r\n" +
                    "            <td></td>\r\n" +
                    "        </tr>\r\n" +
                    "    </table>\r\n" +
                    "</form>";
        }
        if(id==3)
        {
            gjform =
                    "        &lt;%\r\n" +
                            "            for(int i = 0;i &lt; list.size(); i++){\r\n" +
                            "                Word word = (Word)list.get(i);\r\n" +
                            "        %&gt;\r\n" +
                            "        &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                标题：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;%=word.getTitle()==null?\"\": StringUtil.gb2iso4View(word.getTitle())%&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "         &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                内容：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;%=word.getContent()==null?\"\": StringUtil.gb2iso4View(word.getContent())%&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "        &lt;%}%&gt;\r\n" +
                            "    &lt;/table&gt;\r\n" +
                            "    &lt;table width='70%'    class=\"biz_table\" &gt;\r\n" +
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
                            "[&lt;a href='leavemessage.jsp?startrow=&lt;%=startrow-range%&gt;' class='css_002'&gt;上一页&lt;/a&gt;]\r\n" +
                            "&lt;%}\r\n" +
                            "  if((startrow+range)&lt;rows){\r\n" +
                            "%&gt;\r\n" +
                            "[&lt;a href='leavemessage.jsp?startrow=&lt;%=startrow+range%&gt;' class='css_002'&gt;下一页&lt;/a&gt;]\r\n" +
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
                            "    &lt;table    class=\"biz_table\" &gt;\r\n" +
                            "        &lt;form name='form' action='leavemessage.jsp' onsubmit='return check();'&gt;\r\n" +
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
                            "                &lt;textarea rows='10' cols='50' &gt;&lt;/textarea&gt;\r\n" +
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
                            "            &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                公司：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;input type='text' name='company' value=''&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "            &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                联系人：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;input type='text' name='linkman' value=''&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "            &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                联系方式：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;input type='text' name='links' value=''&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "            &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                邮编：\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "            &lt;td valign='top'&gt;\r\n" +
                            "                &lt;input type='text' name='zip' value=''&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "        &lt;tr&gt;\r\n" +
                            "            &lt;td valign='top' colspan='2'&gt;\r\n" +
                            "                &lt;input type='submit' name='sub' value='提交'&gt;\r\n" +
                            "            &lt;/td&gt;\r\n" +
                            "        &lt;/tr&gt;\r\n" +
                            "            &lt;/form&gt;\r\n" +
                            "    &lt;/table&gt;\r\n" ;
            gjform = StringUtil.replace(gjform,"&lt;","<");
            gjform = StringUtil.replace(gjform,"&gt;",">");

        }
        if(id==4)
        {
            gjform="<table  class=\"biz_table\" border='0'>\r\n" +
                    "<form name=\"loginForm\" method=\"post\" action=\"/" + sitename +"/_prog/login.jsp\" onsubmit=\"return check();\">\r\n" +
                    "<input type=\"hidden\" name=\"doLogin\" value=\"true\">\r\n" +
                    "<input type=\"hidden\" name=\"refererurl\" value=\"&lt;%=fromurl%&gt;\">\r\n" +
                    "<TR><TD>用户名：</TD>\r\n" +
                    "<TD><INPUT type=\"text\" name=\"username\" value=\"\" > </TD></TR>\r\n" +
                    "<TR><TD>密&nbsp;&nbsp;码：</TD>\r\n" +
                    "<TD><INPUT type=\"password\" name=\"password\" ></TD></TR>\r\n" +
                    "<TR><TD>&nbsp;</TD><TD align=\"right\">\r\n" +
                    "<DIV align=\"center\" class=\"biz_table\"><input type=\"image\" src=\"" + "/_sys_images/20071114232308235.gif" +"\" value=\"提交\" /></DIV></TD></TR></form></TABLE>\r\n" +
                    "<TABLE cellSpacing=\"0\" cellPadding=\"5\" width=\"100%\"  border=\"0\" class=\"biz_table\">\r\n" +
                    "<TR><TD><A href=\"/"+sitename + "/_prog/register.jsp\" target=\"_blank\">" + "注册" +"</A></TD>\r\n" +
                    "<TD><a href=#>" + "忘记密码" + "</a></TD></TR></TABLE>\r\n" ;
        }
    }
%>
<script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
<script type="text/javascript">
    function closewin()
    {
        var oEditor = FCKeditorAPI.GetInstance('content');
        var content = oEditor.GetXHTML(true);
        if(content.indexOf("<body>")>-1&&content.indexOf("</body>")>-1)
        {
            content=content.substring(content.indexOf("<body>")+"<body>".length,content.indexOf("</body>"))
        }
        <%if(id==0){%>
        if(content.indexOf("form")==-1)
        {

            alert("您需要自己加入form表单");
            return;

        }
        if(content.indexOf("name=\"username\"")==-1)
        {
            if(confirm("没有加入姓名字段，请您把鼠标放在FORM里面，系统是否自动加入")){
                oEditor.InsertHtml("姓名<input type=text name=\"username\">");
                alert("姓名字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"password\"")==-1)
        {
            if(confirm("没有加入密码字段，请您把鼠标放在FORM里面，系统是否自动加入")){
                oEditor.InsertHtml("密码<input type=password name=\"password\">");
                alert("密码字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }

        }
        if(content.indexOf("name=\"doLogin\"")==-1)
        {

            if(confirm("没有加入隐含字段，请您把鼠标放在FORM里面，系统是否自动加入")){
                oEditor.InsertHtml("<input type=hidden name=\"doLogin\" value=true>");
                oEditor.UpdateLinkedField();
                alert("隐含字段已加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }

        }
        <%}%>
        <%if(id==1){%>
        if(content.indexOf("form")==-1)
        {

            alert("您需要自己加入form表单");
            return;

        }
        if(content.indexOf("name=\"username\"")==-1)
        {

            if(confirm("没有加入姓名字段，请您把鼠标放在FORM里面，系统是否自动加入")){
                oEditor.InsertHtml("姓名<input id=\"username\" onFocus=\"if (value =='请输入用户名'){value =''}\" onBlur=\"check_user()\" name=\"username\" value=\"请输入用户名\" type=\"text\" /><div id=\"u_mag\"></div>");
                alert("姓名字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"email\"")==-1)
        {
            if(confirm("没有加入邮箱字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("邮箱<input id=\"email\" onblur=\"check_email()\" name=\"email\" type=\"text\" /><div id=\"e_mag\"></div>");
                alert("邮箱字段已经加上");
            }else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"passWord1\"")==-1)
        {
            if(confirm("没有加入密码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("密码<input id=\"passWord1\" type=\"password\" onblur=\"javascript:checkPassword1()\" name=\"passWord1\" /> <div id=\"p_mag1\"></div>") ;
                alert("密码字段已经加上");
            }else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"passWord2\"")==-1)
        {
            if(confirm("没有加入确认密码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("确认密码<input id=\"passWord2\" type=\"password\" onblur=\"javascript:checkPassword2()\" name=\"passWord2\" /> <div id=\"p_mag2\"></div>");
                alert("确认密码字段已经加上");

            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"txtVerify\"")==-1)
        {
            if(confirm("没有加入验证码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("code<input id=\"txtVerify\" name=\"txtVerify\" type=\"text\" /><img alt=\"\" border=\"1\" name=\"cod\" src=\"/_commons/drawImage.jsp\" />&nbsp;&nbsp;<a href=\"javascript:shuaxin()\">看不清楚?换下一张</a>");
                alert("验证码字段已经加上");

            }else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"doCreate\"")==-1)
        {
            if(confirm("没有加入隐含字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("<input type=\"hidden\" name=\"doCreate\" value=\"1\" />");
                alert("隐含字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"startflag\"")==-1)
        {
            if(confirm("没有加入隐含字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("<input type=\"hidden\" name=\"startflag\" value=\"1\" />");
                alert("隐含字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        <%}%>
        <%if(id==2){%>
        if(content.indexOf("name=\"doUpdate\"")==-1)
        {
            if(confirm("没有加入隐含字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("<input type=\"hidden\" name=\"doUpdate\" value=\"1\" />");
                alert("隐含字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"0\"")==-1)
        {
            if(confirm("没有加入用户ID，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("用户ID<input type=\"text\" id=\"0\" disabled=\"disabled\" name=\"0\" value=\"&lt;%=memberid%&gt;\"  />");
                alert("用户ID字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"birth\"")==-1)
        {
            if(confirm("没有加入出生日期，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("出生日期<input id=\"birth\" name=\"birth\" value=\"&lt;%=birth%&gt;\" type=\"text\" />");
                alert("出生日期字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"chuanzhen\"")==-1)
        {
            if(confirm("没有加入传真，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("传真<input id=\"chuanzhen\" name=\"chuanzhen\" value=\"&lt;%=chuanzhen%&gt;\" type=\"text\" />");
                alert("传真字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"yphone\"")==-1)
        {
            if(confirm("没有加入移动电话，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("移动电话<input name=\"yphone\" value=\"&lt;%=yphone%&gt;\" type=\"text\" />");
                alert("移动电话字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"phone\"")==-1)
        {
            if(confirm("没有加入电话号码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("电话<input id=\"phone\" name=\"phone\" value=\"&lt;%=phone%&gt;\" type=\"text\" />");
                alert("电话号码字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"city\"")==-1)
        {
            if(confirm("没有加入所在城市字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("城市<input id=\"city\" name=\"city\" value=\"&lt;%=city%&gt;\" type=\"text\" />");
                alert("所在城市字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"post\"")==-1)
        {
            if(confirm("没有加入邮编字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("邮编<input id=\"post\" name=\"post\" value=\"&lt;%=post%&gt;\" type=\"text\" />");
                alert("邮编字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"lianxi\"")==-1)
        {
            if(confirm("没有加入联系人字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("联系人<input id=\"lianxi\" name=\"lianxi\" value=\"&lt;%=lianxi%&gt;\" type=\"text\" />");
                alert("联系人字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"pass1\"")==-1)
        {
            if(confirm("没有加入确认新密码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("确认密码<input id=\"pass1\" type=\"password\" name=\"pass1\" />");
                alert("确认新密码字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"pass\"")==-1)
        {
            if(confirm("没有加入新密码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("新密码<input id=\"pass\" type=\"password\" name=\"pass\" />");
                alert("新密码字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"pass0\"")==-1)
        {
            if(confirm("没有加入旧密码字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("旧密码<input id=\"pass0\" type=\"password\" name=\"pass0\" /><div id=\"tishi\"></div>");
                alert("旧密码字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"Rname\"")==-1)
        {
            if(confirm("没有加入真实姓名字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("真实姓名<input id=\"Rname\" name=\"Rname\" value=\"&lt;%=UserName%&gt;\" type=\"text\" />");
                alert("真实姓名字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        if(content.indexOf("name=\"con\"")==-1)
        {
            if(confirm("没有加入国家字段，请您把鼠标放在FORM里面，系统是否自动加入"))
            {
                oEditor.InsertHtml("国家<input id=\"con\" name=\"con\" value=\"&lt;%=con%&gt;\" type=\"text\" />");
                alert("国家字段已经加上");
            }
            else{
                alert("您需要自己加入");
                return;
            }
        }
        //<input type="button" value="提交" onclick="javascript:update_do()">
        if(content.indexOf("onclick=\"javascript:update_do()")==-1){
            oEditor.InsertHtml("<input type=\"button\" value=\"提交\" onclick=\"javascript:update_do()\">");
        }
        <%}%>
        if(confirm("是否保存")){
            var content = oEditor.GetXHTML(true);
            if(content.indexOf("<body>")>-1&&content.indexOf("</body>")>-1)
            {
                content=content.substring(content.indexOf("<body>")+"<body>".length,content.indexOf("</body>"))
            }
            //window.parent
            //alert( window.parent.document.getElementById("gjcontent").value);
            window.dialogArguments.document.getElementById("gjcontent").value=content;
            window.dialogArguments.document.getElementById("gaoji").value=1;
            // alert(content);
            window.close();
        }else{
            return;
        }
    }
    function quxiao()
    {
        alert(window.dialogArguments.document.getElementById("gaoji").value);
        window.dialogArguments.document.getElementById("gaoji").value=-1;
        window.close();
    }
</script>
<input type="button" onclick="closewin()" value="关闭">  <input type="button" onclick="quxiao()" value="不使用高级表单">
<form name="createForm"><input type="hidden" value="<%=id%>" name="id">
    <input type="hidden" name="column" value="-100">
    <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"><%=gjform%>
 </textarea>
    <script type="text/javascript">
        var oFCKeditor = new FCKeditor('content') ;
        oFCKeditor.BasePath = "../fckeditor/";
        oFCKeditor.Height = 530;
        oFCKeditor.ToolbarSet = "Programform";
        oFCKeditor.ReplaceTextarea();
    </script>
</form>
