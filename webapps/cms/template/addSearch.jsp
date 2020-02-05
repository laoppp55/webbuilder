<%@page import="java.util.*,
                com.bizwink.cms.security.*,
                com.bizwink.cms.util.*,
                com.bizwink.cms.xml.*,
                com.bizwink.cms.markManager.*,
                com.bizwink.cms.viewFileManager.*"
        contentType="text/html;charset=utf-8"
%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    //     修改编辑属性取值
    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String s_sitename = StringUtil.replace(sitename, "_", ".");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    IMarkManager markMgr = markPeer.getInstance();
    mark vmark = null;
    String liststyle = "";
    int navbar = 0;
    int articleNum = 0;
    IViewFileManager viewfileMgr = viewFilePeer.getInstance();
    List navList = viewfileMgr.getViewFileC(siteID, 2);        //获得导航条样式
    ViewFile viewfile = new ViewFile();

    if (markID > 0) {
        vmark = markMgr.getAMark(markID);
        String stylecontent = vmark.getContent();
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(markID));

        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");

        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        liststyle = properties.getProperty(properties.getName().concat(".LISTSTYLE"));
        liststyle = liststyle.substring(0,liststyle.length() - 1);
        String  navbar_s = properties.getProperty(properties.getName().concat(".NAVBAR"));
        if (navbar_s!=null && navbar_s!="") navbar = Integer.parseInt(navbar_s);
        String articleNum_s = properties.getProperty(properties.getName().concat(".ARTICLENUM"));
        if (articleNum_s!=null && articleNum_s!="") articleNum = Integer.parseInt(articleNum_s);
    }

    int listType = 0;
    int innerFlag = 0;
    String notes = "";
    String cname = "搜索标记";
    //int markID = ParamUtil.getIntParameter(request, "mark", 0);
    int type = ParamUtil.getIntParameter(request, "type", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    //IMarkManager markMgr = markPeer.getInstance();

    if (doCreate) {
        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        innerFlag = ParamUtil.getIntParameter(request, "innerFlag", 0);
        listType = ParamUtil.getIntParameter(request, "listType", 0);
        navbar = ParamUtil.getIntParameter(request, "navbarview", 0);
        articleNum = ParamUtil.getIntParameter(request, "articleNumInPage", 0);

        String titlestyle = ParamUtil.getParameter(request, "titlestyle");
        String summarystyle = ParamUtil.getParameter(request, "summarystyle");
        liststyle = ParamUtil.getParameter(request, "content");
        String title = titlestyle;
        if (title != null) {
            if (title.indexOf(".") > -1) {
                title = title.substring(title.indexOf(".") + 1);
            }
            if (title.indexOf("#") > -1) {
                title = title.substring(title.indexOf("#") + 1);
            }
        }
        String summarys = summarystyle;
        if (summarys != null) {
            if (summarys.indexOf(".") > -1) {
                summarys = summarys.substring(summarys.indexOf(".") + 1);
            }
            if (summarys.indexOf("#") > -1) {
                summarys = summarys.substring(summarys.indexOf("#") + 1);
            }
        }

        String content = "[TAG][HTMLCODE][MARKTYPE]" + type + "[/MARKTYPE][NAVBAR]" + navbar + "[/NAVBAR][ARTICLENUM]" + articleNum + "[/ARTICLENUM][LISTSTYLE]<!{^CDATA{^" + liststyle + ">^}^}>[/LISTSTYLE]" +
                "[/HTMLCODE][/TAG]";
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
        mark.setMarkType(11);
        int orgmarkID = markID;
        if (orgmarkID > 0 && !saveas)
            markMgr.Update(mark);
        else
            markID = markMgr.Create(mark);

        String markname = "信息检索";
        if (orgmarkID > 0 && !saveas) {
            out.println("<script>top.close();</script>");
        } else {
            String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.opener.document.createForm.cname.value='" + markname + "';" +
                    "window.parent.opener.document.createForm.modelname.value='search';" +
                    "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }
        return;
    }
%>

<html>
<head>
    <base target="_self">
    <title>定义信息检索</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <meta http-equiv="Pragma" content="no-cache">
    <script language="javascript" src="../js/color.js"></script>

    <script type="text/javascript">
        function createStyle(type, columnID) {
            var retstr = "";
            var winstrs = "";

            if (type == 1 || type == 3 || type == 4) {
                winstrs = "../template1/editStyle.jsp?column=" + columnID + "&type=" + type;
                listwin = window.open(winstrs, "createStyle", "font-family:Verdana;font-size:12;dialogWidth:650px;dialogHeight:440px;status:no");
                listwin.focus();
            } else if (type == 6) {
                winstrs = "../template1/editColumnStyleRight.jsp?type=" + type;
                wins = window.open(winstrs, "createStyle", "font-family:Verdana;font-size:12;dialogWidth:500px;dialogHeight:420px;status:no");
                wins.focus();
            } else {
                winstrs = "../template1/editOtherStyle.jsp?column=" + columnID + "&type=" + type;
                artwin = window.open(winstrs, "createStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                artwin.focus();
            }

            if (retstr != "" && retstr != undefined) {
                var newoption = document.createElement("OPTION");
                newoption.value = retstr.substring(0, retstr.indexOf(","));
                newoption.text = retstr.substring(retstr.indexOf(",") + 1);

                //article list  //relate article list  //column list  //path  //topstories
                if (type == 1 || type == 3 || type == 4 || type == 6 || type == 8 || type == 9)
                {
                    document.all("listType").add(newoption);
                    document.all("listType").options[document.all("listType").length - 1].selected = true;
                }
                if (type == 2)    //navbar
                {
                    document.all("navbar").add(newoption);
                    document.all("navbar").options[document.all("navbar").length - 1].selected = true;
                }
                if (type == 5)    //readed title
                {
                    document.all("sellink").add(newoption);
                    document.all("sellink").options[document.all("sellink").length - 1].selected = true;
                }
                if (type == 7)    //new article
                {
                    document.all("seldays").add(newoption);
                    document.all("seldays").options[document.all("seldays").length - 1].selected = true;
                }
                if (type == 10)   //next article
                {
                    document.all("nextarticle").add(newoption);
                    document.all("nextarticle").options[document.all("nextarticle").length - 1].selected = true;
                }
            }
        }

        function updateStyle(type, styleID, columnID)
        {
            if (styleID > 0)
            {
                if (type == 1 || type == 3 || type == 4) {
                    wins = window.open("../template1/editStyle.jsp?column=" + columnID + "&ID=" + styleID + "&type=" + type, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                    wins.focus();
                } else if (type == 6) {
                    wins = window.open("../template1/editColumnStyle.jsp?ID=" + styleID + "&type=" + type, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                    wins.focus();
                } else {
                    wins = window.open("../template1/editOtherStyle.jsp?column=" + columnID + "&type=" + type + "&ID=" + styleID, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                    wins.focus();
                }
            }
        }

        function previewStyle(type, styleID)
        {
            if (styleID > 0) {
                wins = window.open("../template1/getviewfile.jsp?id=" + styleID + "&type=" + type, "updateStyle", "font-family:Verdana;font-size:12;dialogWidth:560px;dialogHeight:390px;status:no");
                wins.focus();
            }
        }

        function selectNavStyle()
        {
            if (markform.navPosition[0].checked) {
                markform.navbar.disabled = 0;
                markform.button4.disabled = 0;
                markform.button5.disabled = 0;
                markform.button6.disabled = 0;
            } else {
                markform.navbar.disabled = 1;
                markform.button4.disabled = 1;
                markform.button5.disabled = 1;
                markform.button6.disabled = 1;
                markform.navbarview.value = 0;
            }
        }

        function cal() {
            top.close();
        }

        function doit()
        {
            markform.action = "addSearch.jsp";
            markform.method = "post";
            markform.target = "_self"
            markform.submit();
        }

        function SelectMarkName()
        {
            var i = document.markform.items.selectedIndex;
            var str = document.markform.items.options[i].value;
            if (str != "")
            {
                insertAtCaret(document.markform.content, str);
            }
            document.markform.items.options[0].selected = true;
        }

        function storeCaret(textEl)
        {
            if (textEl.createTextRange)
            {
                textEl.caretPos = document.selection.createRange().duplicate();
            }
        }

        function insertAtCaret(textEl, text)
        {
            if (textEl.createTextRange && textEl.caretPos)
            {
                var caretPos = textEl.caretPos;
                caretPos.text = caretPos.text.charAt(caretPos.text.length - 1) == ' ' ?text + ' ' : text;
            }
            else
            {
                textEl.value = text;
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
        <input type=hidden name=type value=11>
        <input type="hidden" name="mark" value="<%=markID%>">

        <tr>
            <td width="20%"  height=30>检索结果列表项：</td>
            <td width="80%"><select name="items" id="itemsid" style="width:200px" onchange="SelectMarkName();">
                <option value="0">请选择</option>
                <%
                    out.println("<option value=\"<" + "%%URL%%" + ">\">链接地址</option>");
                    out.println("<option value=\"<" + "%%DATA%%" + ">\">文章标题</option>");
                    out.println("<option value=\"<" + "%%VICETITLE%%"+">\">文章副标题</option>");
                    out.println("<option value=\"<" + "%%ASUMMARY%%"+">\">文章描述</option>");
                    out.println("<option value=\"<" + "%%PT%%"+">\">发布日期</option>");
                    out.println("<option value=\"<" + "%%SALEPRICE%%" + ">\">商品价格</option>");
                    out.println("<option value=\"<" + "%%MARKETPRICE%%" + ">\">商品市场价格</option>");
                    out.println("<option value=\"<" + "%%VIPPRICE%%" +">\">商品VIP价格</option>");
                    out.println("<option value=\"<" + "%%STOCK%%" + ">\">商品库存</option>");
                    out.println("<option value=\"<" + "%%SMALLPIC%%" + ">\">商品小图片</option>");
                    out.println("<option value=\"<" + "%%LARGEPIC%%" + ">\">商品大图片</option>");
                    out.println("<option value=\"<" + "%%SPECPIC%%" + ">\">特效图片</option>");
                    out.println("<option value=\"<" + "%%SHOPPINGCARURL%%" + ">\">购物车链接地址</option>");
                    out.println("<option value=\"<" + "%%BEGIN%%"+">\">循环开始</option>");
                    out.println("<option value=\"<" + "%%END%%"+">\">循环结束</option>");
                %>
            </select>          </td>
        </tr>
        <tr>
            <td height=30>检索结果行格式描述：</td>
            <td><label>
                <textarea name="content" id="contentid" cols="80" rows="10" WRAP="soft" ONSELECT="storeCaret(this);" ONCLICK="storeCaret(this);" ONKEYUP="storeCaret(this);" style="font-size: 8pt; font-family: Georgia"><%=liststyle%></textarea>
            </label></td>
        </tr>
        <tr>
            <td>
                每页显示：<input name="articleNumInPage" style="width:30px" value="<%=articleNum%>">篇信息
            </td>
            <td><input type="radio" name="navPosition" value="0" <%if(navbar!=0){%>checked<%}%>  onclick="selectNavStyle();">默认导航条位置&nbsp;&nbsp;
                <input type="radio" name="navPosition" value="1" <%if(navbar==0){%>checked<%}%>  onclick="selectNavStyle();">自定义导航条位置
            </td>
        </tr>
        <tr>
            <td>分页导航条样式：</td>>
            <td>
                <select id="navbar" name="navbarview" style="width:120px;font-size:9pt"
                        <%if(navbar==0){%>disabled<%}%>>
                    <option value="0">选择分页条样式</option>
                    <%
                        for (int i = 0; i < navList.size(); i++) {
                            viewfile = (ViewFile) navList.get(i);
                    %>
                    <option value="<%=viewfile.getID()%>"
                            <%if(navbar==viewfile.getID()){%>selected<%}%>><%=StringUtil.gb2iso4View(viewfile.getChineseName())%>
                    </option>
                    <%}%>
                </select>
                <input id=button4 type=button onclick="createStyle(2,0)" <%if(navbar==0){%>disabled<%}%> value="新建" style="height:20px;width:50px;font-size:9pt">
                <input id=button5 type=button onclick="updateStyle(2,navbar.options[navbar.selectedIndex].value,0)" <%if(navbar==0){%>disabled<%}%> value="修改" style="height:20px;width:50px;font-size:9pt">
                <input id=button6 type=button onclick="previewStyle(2,navbar.options[navbar.selectedIndex].value)" <%if(navbar==0){%>disabled<%}%> value="预览" style="height:20px;width:50px;font-size:9pt">
            </td>
        </tr>
        <tr>
            <td height=30>标记中文名称：</td><td><input name=chineseName size=20 value="<%=cname%>"></td>
        </tr>
        <tr height=80>
            <td valign="top"  height=30>标记描述：</td><td valign="top"><br><textarea rows="3" id="notes" cols="38"><%=notes%>
        </textarea></td>
        </tr>
        <tr height="50">
            <td align=center  height=30>
                <input type="button" value=" 确定 " onClick="doit();">&nbsp;&nbsp;&nbsp;&nbsp;                </td><td><input type="button" value=" 取消 " onClick="javascript:cal();">            </td>
        </tr>
    </form>
</table>
</body>
</html>