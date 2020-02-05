<%@ page contentType="text/html;charset=utf-8" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.modelManager.IModelManager" %>
<%@ page import="com.bizwink.cms.modelManager.ModelPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%@ page import="com.bizwink.cms.modelManager.Model" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.cms.modelManager.ModelException" %>
<%@ page import="com.bizwink.upload.RandomStrg" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.extendAttr.ExtendAttrPeer" %>
<%@ page import="com.bizwink.cms.extendAttr.IExtendAttrManager" %>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<!--%@ taglib uri="/FCKeditor" prefix="FCK" %-->

<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String username = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int cssjsdir = authToken.getCssJsDir();
    String sitename = authToken.getSitename();
    String appPath = application.getRealPath("/");
    int imgflag = authToken.getImgSaveFlag();
    boolean success = false;
    boolean errors = false;

    int tempnum=ParamUtil.getIntParameter(request,"tempnum",0);
    int modelType = ParamUtil.getIntParameter(request, "modelType", 0);
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    String filename = ParamUtil.getParameter(request, "modelfilename");
    String cname = ParamUtil.getParameter(request, "cname");
    String modelname = ParamUtil.getParameter(request, "modelname");
    int isIncluded = ParamUtil.getIntParameter(request, "isIncluded", 0);
    String content = "";
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));

    IColumnManager columnMgr = ColumnPeer.getInstance();
    Column column = columnMgr.getColumn(columnID);
    String tempDir = column.getDirName();
    int lang = column.getLanguageType();

    //如果模板文件名非空，读取模板文件的内容
    if (filename != null && !filename.equalsIgnoreCase("undefined") && !doCreate) {
        IModelManager modelPeer = ModelPeer.getInstance();
        content = modelPeer.readModelFile(filename, appPath, sitename, siteid, tempDir, imgflag, lang, cssjsdir);
        int posi = -1;
        while ((posi = content.toLowerCase().indexOf("<textarea")) > -1)
            content = content.substring(0, posi) + "<cmstextarea" + content.substring(posi + 9);
        while ((posi = content.toLowerCase().indexOf("</textarea>")) > -1)
            content = content.substring(0, posi) + "</cmstextarea>" + content.substring(posi + 11);

        /*if (lang == 2) {
            StringBuffer sb = new StringBuffer();
            char[] c = content.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if ((c[i] > 65376 && c[i] < 65440) || (c[i] > 1000 && c[i] < 10000))  //片假名及特殊符号
                    sb.append("&#" + Integer.toString(c[i]) + ";");
                else
                    sb.append(c[i]);
            }
            content = sb.toString();
        }*/
    }

    IModelManager modelManager;
    if (doCreate) {
        modelManager = ModelPeer.getInstance();
        if (modelname == null || modelname.length() < 1) {
            out.println("模板文件名不能为空！请<a href=javascript:history.go(-1);>返回</a>");
            return;
        } else if (modelManager.hasSameModelName(siteid,columnID, modelname)) {
            out.println("模板文件名称不能重复！请<a href=javascript:history.go(-1);>返回</a>");
            return;
        }

        int posi;
        content = ParamUtil.getParameter(request, "content");
        while ((posi = content.toLowerCase().indexOf("<cmstextarea")) > -1)
            content = content.substring(0, posi) + "<textarea" + content.substring(posi + 12);
        while ((posi = content.toLowerCase().indexOf("</cmstextarea>")) > -1)
            content = content.substring(0, posi) + "</textarea>" + content.substring(posi + 14);

        try {
            Model model = new Model();

            model.setColumnID(columnID);
            model.setIsArticle(modelType);
            model.setContent(content);
            model.setCreatedate(new Timestamp(System.currentTimeMillis()));
            model.setLastupdated(new Timestamp(System.currentTimeMillis()));
            model.setEditor(username);
            model.setCreator(username);
            model.setLockstatus(0);
            model.setChineseName(cname);
            model.setTemplateName(modelname);
            model.setIncluded(isIncluded);
            model.setTempnum(tempnum);
            modelManager.Create(model, siteid,samsiteid,0);
            success = true;
        } catch (ModelException uaee) {
            errors = true;
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?column=" + columnID + "&rightid=" + rightid));
        return;
    }

//获得随机字符串作为模板文件名称
    RandomStrg rstr = new RandomStrg();
    rstr.setCharset("a-z0-9");
    rstr.setLength(8);
    try {
        rstr.generateRandomObject();
    } catch (Exception e) {
        e.printStackTrace();
    }
    modelname = rstr.getRandom();

//读出文章扩展属性，用于文章模板的标记
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    List attrList = extendMgr.getAttrForTemplate(columnID);
    attrList.add(0, "NO_SELECT,选择标记");
    attrList.add("ARTICLEPIC,文章图片");
    attrList.add("ARTICLE_SUMMARY,文章概述");
    attrList.add("ARTICLE_MEDIA,视频播放");
    attrList.add("ARTICLE_PT,发布时间");
    attrList.add("ARTICLE_LIST,文章列表");
    attrList.add("RELATED_ARTICLE,相关文章");
    //attrList.add("COMMEND_ARTICLE,推荐文章列表");
    attrList.add("TOP_STORIES,热点文章");
    attrList.add("COLUMN_LIST,栏目列表");
    attrList.add("ARTICLE_PATH,文章路径");
    attrList.add("CHINESE_PATH,中文路径");
    attrList.add("ENGLISH_PATH,英文路径");
    attrList.add("NAVBAR,导航条");
    attrList.add("SUBARTICLE_LIST,子文章列表");
    attrList.add("BROTHER_LIST,兄弟文章列表");
    attrList.add("SUBCOLUMN_LIST,子栏目列表");
    attrList.add("ARTICLE_COUNT,文章条数");
    attrList.add("SUBARTICLE_COUNT,子文章条数");
    attrList.add("ARTICLEID,文章ID");
    attrList.add("ARTICLESTATUS,文章状态");
    attrList.add("COLUMNID,栏目ID");
    attrList.add("RELATEID,父文章ID");
    attrList.add("COLUMNNAME,栏目名称");
    attrList.add("PARENT_COLUMNNAME,父栏目名称");
    attrList.add("COLUMNURL,栏目URL");
    attrList.add("PARENT_PATH,父文章路径");
    attrList.add("ARTICLE_DOCLEVEL,文章权重");
    attrList.add("PAGINATION,分页标记");
    attrList.add("PREV_ARTICLE,上一篇");
    attrList.add("NEXT_ARTICLE,下一篇");
    attrList.add("TURN_PIC,文章附图列表");
    attrList.add("COMPANYNAME,公司名称");
    attrList.add("TELEPHONE,公司联系电话");
    attrList.add("EMAIL,公司邮件");
    attrList.add("WEIBO,公司微博");
    attrList.add("SEARCH,检索表单");
    attrList.add("ORDERSEARCH_RESULT,订单查询表单");
    attrList.add("LOGIN_FORM,用户登录表单");
    attrList.add("USER_LOGIN_DISPLAY,用户登录页");
    attrList.add("DEFINEINFO,调查表单");
    attrList.add("ARTICLE_COMMENT,文章评论表单");
    attrList.add("QQ,QQ号码");
    attrList.add("INCLUDE_FILE,加入包含文件");
    attrList.add("SEECOOKIE,最近浏览");
    attrList.add("TURNPIC,图片轮换效果");
    attrList.add("LEAVEMESSAGE,用户留言表单");
    attrList.add("LEAVEMESSAGELIST,用户留言列表");
    attrList.add("USED_MARK,选择已有标记");

    if (column.getParentID() == 0) modelType = 2;
%>

<html>
<head>
    <title>创建模板</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Pragma" content="no-cache">
    <script type="text/javascript" src="../toolbars/btnclick.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>
    <link rel=stylesheet type=text/css href="../style/editor.css">
    <script type="text/javascript">
        $(document).ready(function() {
            checkoption();
        });

        function checkoption() {
            var para;
            for (var i = 0; i < document.createForm.modelType.length; i++) {
                if (document.createForm.modelType[i].selected)
                    para = document.createForm.modelType[i].value;
            }

            var param = parseInt(para);
            if (param == 0)
                document.createForm.cname.value = "栏目模板";
            else if (param == 1)
                document.createForm.cname.value = "文章模板";
            else if (param == 2)
                document.createForm.cname.value = "首页模板";
            else if (param == 3)
                document.createForm.cname.value = "专题模板";
            else if (param == 4)
                document.createForm.cname.value = "手机栏目模板";
            else if (param == 5)
                document.createForm.cname.value = "手机首页模板";
            else if (param == 6)
                document.createForm.cname.value = "手机专题模板";

            var selectlens = new Array;
            selectlens[0] = 33;
            selectlens[1] = <%=attrList.size() - 1%>;
            var optionsname = new Array();
            var optionsvalue = new Array();

            optionsname[0] = new Array();
            optionsname[0][0] = "选择标记";
            optionsname[0][1] = "文章列表";
            optionsname[0][2] = "推荐文章列表";
            optionsname[0][3] = "子文章列表";
            optionsname[0][4] = "兄弟文章列表";
            optionsname[0][5] = "热点文章";
            optionsname[0][6] = "相关文章";
            optionsname[0][7] = "栏目列表";
            optionsname[0][8] = "子栏目列表";
            optionsname[0][9] = "中文路径";
            optionsname[0][10] = "英文路径";
            optionsname[0][11] = "文章条数";
            optionsname[0][12] = "子文章条数";
            optionsname[0][13] = "导航条";
            optionsname[0][14] = "栏目名称";
            optionsname[0][15] = "父栏目名称";
            optionsname[0][16] = "栏目URL";
            optionsname[0][17] = "公司名称";
            optionsname[0][18] = "公司联系电话";
            optionsname[0][19] = "公司邮件";
            optionsname[0][20] = "公司微博";
            optionsname[0][21] = "检索表单";
            optionsname[0][22] = "订单查询表单";
            optionsname[0][23] = "用户登录表单";
            optionsname[0][24] = "调查表单";
            optionsname[0][25] = "图片特效";
            optionsname[0][26] = "QQ号码";
            optionsname[0][27] = "加入包含文件";
            optionsname[0][28] = "最近浏览";
            optionsname[0][29] = "图片轮换效果";
            optionsname[0][30] = "用户留言表单";
            optionsname[0][31] = "用户留言列表";
            optionsname[0][32] = "用户登录页";
            optionsname[0][33] = "选择已有标记";

            optionsvalue[0] = new Array();
            optionsvalue[0][0] = "NO_SELECT";
            optionsvalue[0][1] = "ARTICLE_LIST";
            optionsvalue[0][2] = "COMMEND_ARTICLE";
            optionsvalue[0][3] = "SUBARTICLE_LIST";
            optionsvalue[0][4] = "BROTHER_LIST";
            optionsvalue[0][5] = "TOP_STORIES";
            optionsvalue[0][6] = "RELATED_ARTICLE";
            optionsvalue[0][7] = "COLUMN_LIST";
            optionsvalue[0][8] = "SUBCOLUMN_LIST";
            optionsvalue[0][9] = "CHINESE_PATH";
            optionsvalue[0][10] = "ENGLISH_PATH";
            optionsvalue[0][11] = "ARTICLE_COUNT";
            optionsvalue[0][12] = "SUBARTICLE_COUNT";
            optionsvalue[0][13] = "NAVBAR";
            optionsvalue[0][14] = "COLUMNNAME";
            optionsvalue[0][15] = "PARENT_COLUMNNAME";
            optionsvalue[0][16] = "COLUMNURL";
            optionsvalue[0][17] = "COMPANYNAME";
            optionsvalue[0][18] = "TELEPHONE";
            optionsvalue[0][19] = "EMAIL";
            optionsvalue[0][20] = "WEIBO";
            optionsvalue[0][21] = "SEARCH_FORM";
            optionsvalue[0][22] = "ORDERSEARCH_RESULT";
            optionsvalue[0][23] = "LOGIN_FORM";
            optionsvalue[0][24] = "DEFINEINFO_FORM";
            optionsvalue[0][25] = "XUAN_IMAGES";
            optionsvalue[0][26] = "QQ";
            optionsvalue[0][27] = "INCLUDE_FILE";
            optionsvalue[0][28] = "SEECOOKIE";
            optionsvalue[0][29] = "TURNPIC";
            optionsvalue[0][30] = "LEAVEMESSAGE";
            optionsvalue[0][31] = "LEAVEMESSAGELIST";
            optionsvalue[0][32] = "USER_LOGIN_DISPLAY";
            optionsvalue[0][33] = "USED_MARK";

            optionsname[1] = new Array();
            optionsvalue[1] = new Array();

            <%for (int i=0; i<attrList.size(); i++){
              String temp = (String)attrList.get(i);
              String name = temp.substring(0, temp.indexOf(","));
              String value = temp.substring(temp.indexOf(",") + 1);
              out.println("optionsname[1]["+i+"] =\""+value+"\";\r\n");
              out.println("optionsvalue[1]["+i+"] =\""+name+"\";\r\n");
            }%>

            /*
             0--栏目模板，2--首页模板，3--专题模板 4--手机栏目模板   5--手机首页模板   6--手机专题模板
             1--文章模板
             */
            if (param == 0 || param == 2 || param == 3 || para == 4 || param == 5 || param == 6)
                param = 0;
            else
                param = 1;

            document.getElementById("MarkName").options.length = 0;
            for (var x = 0; x <= selectlens[param]; x++)
            {
                document.getElementById("MarkName").options.add(new Option(optionsname[param][x], optionsvalue[param][x]));
            }
        }

        /*function editMarkInfo() {
            var mySelection = CKEDITOR.instances.content.getSelection();
            if (mySelection.getType() == CKEDITOR.SELECTION_ELEMENT){
                var element = mySelection.getSelectedElement();
                alert(element.getOuterHtml());
                alert(element.getAttribute('name'));
            } else {
                mySelection.unlock(true);
                alert(mySelection.getNative());
                data = mySelection.getNative().createRange().text;
                alert(data);
            }
            //alert(CKEDITOR.env.ie);
            //var data = CKEDITOR.instances.content.getData();
        }*/
    </script>
</head>

<body>
<form action="createtemplate.jsp" method="post" name="createForm">
    <input type=hidden name="getreturnvalue" value="">
    <input type=hidden name="doCreate" value=true>
    <input type=hidden name="modelfilename">
    <input type=hidden name="column" value="<%=columnID%>">
    <input type=hidden name="modetype" value=<%=modelType%>>
    <input type=hidden name="rightid" value="<%=rightid%>">
    <input type=hidden name="tempURL" value="<%=request.getRequestURL().toString()+"-"+siteid%>">
    <input type=hidden name="modelSourceCodeFlag" value=0>
    <input type=hidden name="template_or_article_flag" value="0">
    <input type=hidden name="usermodelnewsadd" id="usermodelnewsadd" >
    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#003366>
            <td colspan=2 height=2></td>
        </tr>
        <tr height=30>
            <td width="70%"><%
                if (!success && errors) {
                    out.println("<span class=cur>创建模板失败，请重新再试。</span>");
                }
            %></td>
            <td width="30%" align=right>
                <input class=tine type=button value="  编辑  " name="editbutton" onclick="editMarkInfo();">&nbsp;&nbsp;
                <input class=tine type=submit value="  保存  " name="savebutton">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="window.close();">&nbsp;&nbsp;
            </td>
        </tr>
        <tr bgcolor=#003366>
            <td colspan=2 height=2></td>
        </tr>
        <tr>
            <td colspan=2 height=4></td>
        </tr>
    </table>

    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr>
            <td>
                <!--input type=radio name=insertTemplat value=0 onclick="javascript:Add_Template_onclick(<%//=columnID%>);">装入模板-->
                &nbsp;&nbsp;模板类型：<select name=modelType onchange="checkoption();">
                <%if (column.getParentID() == 0) {%>
                <option value=2 selected>首页模板</option>
                <option value=5>手机首页模板</option>
                <%} else {%>
                <option value=0 selected>栏目模板</option>
                <option value=4>手机栏目模板</option>
                <%}%>
                <option value=1>文章模板</option>
                <option value=3>专题模板</option>
                <option value=6>手机专题模板</option>
            </select>
                &nbsp;&nbsp;模板中文名称：<input name="cname" size=15>
                &nbsp;&nbsp;模板文件名：<input name="modelname" size=15 value="<%=modelname%>">
                &nbsp;&nbsp;选择标记：<select ID="MarkName" onchange="return MarkName_Add(<%=columnID%>,'<%=sitename%>')"></select>
                &nbsp;&nbsp;<input type=checkbox name="isIncluded" value=1>包含文件  &nbsp;&nbsp;&nbsp;模版号<input type=text name="tempnum" size="4">
            </td>
        </tr>
        <tr style="height: 100%">
            <td id="selhtmlcode" valign="top"><input type="hidden" name="hcode" id="htmlcodeid" value=""></td>
        </tr>
        <tr>
            <td ID=bottomofFld></td>
        </tr>
    </table>
    <table border="0" width="100%">
        <tr>
            <td>
                <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 1000px"><%=content%></textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content',{allowedContent: true});
                </script>
            </td>
        </tr>
    </table>
</form>
</body>
</html>