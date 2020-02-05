<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.extendAttr.*,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.bizwink.cms.modelManager.history.ModelHistory" contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.cms.viewFileManager.IViewFileManager" %>
<%@ page import="com.bizwink.cms.viewFileManager.viewFilePeer" %>
<%@ page import="com.bizwink.cms.server.CmsServer" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errors = false;
    boolean success = false;
    String username = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String sitename = authToken.getSitename();
    String rootPath = application.getRealPath("/");

    int tempnum=ParamUtil.getIntParameter(request,"tempnum",0);
    long historyID = ParamUtil.getLongParameter(request, "historyID", 0);
    int ID = ParamUtil.getIntParameter(request, "template", 0);
    int rightid = ParamUtil.getIntParameter(request, "rightid", 0);
    int modelType = ParamUtil.getIntParameter(request, "modelType", 0);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int isIncluded = ParamUtil.getIntParameter(request, "isIncluded", 0);
    String content = ParamUtil.getParameter(request, "content");
    String cname = ParamUtil.getParameter(request, "cname");
    String modelname = ParamUtil.getParameter(request, "modelname");
    String modelnameBak = ParamUtil.getParameter(request, "modelnameBak");
    boolean publish = ParamUtil.getBooleanParameter(request, "publish");
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    session.setAttribute("createtemplate_columnid", String.valueOf(columnID));

    IModelManager modelManager = ModelPeer.getInstance();
    Model model;

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String columnName = StringUtil.gb2iso4View(column.getCName());
    System.out.println("siteid="+siteid);
    if (doUpdate) {
        try {
            if (modelname == null || modelname.length() < 1) {
                out.println("模板文件名不能为空！请<a href=javascript:history.go(-1);>返回</a>");
                return;
            } else if (!modelnameBak.equalsIgnoreCase(modelname)) {
                if (modelManager.hasSameModelName(siteid,columnID, modelname)) {
                    out.println("模板文件名称不能重复！请<a href=javascript:history.go(-1);>返回</a>");
                    return;
                }
            }

            int posi = -1;
            while ((posi = content.toLowerCase().indexOf("<cmstextarea")) > -1)
                content = content.substring(0, posi) + "<textarea" + content.substring(posi + 12);
            while ((posi = content.toLowerCase().indexOf("</cmstextarea>")) > -1)
                content = content.substring(0, posi) + "</textarea>" + content.substring(posi + 14);
            System.out.println("siteid="+siteid);
            model = new Model();

            model.setColumnID(columnID);
            model.setIsArticle(modelType);
            model.setContent(StringUtil.gb2isoindb(content));
            model.setCreatedate(new Timestamp(System.currentTimeMillis()));
            model.setLastupdated(new Timestamp(System.currentTimeMillis()));
            model.setEditor(username);
            model.setCreator(username);
            model.setLockstatus(0);
            model.setChineseName(StringUtil.gb2isoindb(cname));
            model.setTemplateName(modelname);
            model.setIncluded(isIncluded);
            model.setTempnum(tempnum);
            modelManager.Create(model, siteid,samsiteid,0);

            ///把对应的样式列表文件也拷贝 过去
            IViewFileManager fileMgr = viewFilePeer.getInstance();
            success = true;
        } catch (ModelException e) {
            e.printStackTrace();
            errors = true;
        }
    }

    if (success) {
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id=" + ID + "&column=" + columnID + "&rightid=" + rightid));
        return;
    }

    ModelHistory history = new ModelHistory(rootPath);
    List historylist = history.getModelList(sitename, ID);

    model = modelManager.getModel(ID, username);
    if (historyID > 0) {
        content = history.getModel(ID, historyID, sitename);
        model.setContent(content);
    }
    modelType = model.getIsArticle();
    content = model.getContent();
    int posi = -1;
    while ((posi = content.toLowerCase().indexOf("<textarea")) > -1)
        content = content.substring(0, posi) + "<cmstextarea" + content.substring(posi + 9);
    while ((posi = content.toLowerCase().indexOf("</textarea>")) > -1)
        content = content.substring(0, posi) + "</cmstextarea>" + content.substring(posi + 11);
    content = StringUtil.gb2iso4View(content);
    //System.out.println(content);
    cname = StringUtil.gb2iso4View(model.getChineseName());
    modelname = model.getTemplateName();
    isIncluded = model.getIncluded();

//读出文章扩展属性，用于文章模板的标记
    IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
    List attrList = extendMgr.getAttrForTemplate(columnID);
    attrList.add(0, "NO_SELECT,选择标记");
    attrList.add("ARTICLEPIC,文章图片");
    attrList.add("ARTICLE_SUMMARY,文章概述");
    attrList.add("ARTICLE_PT,发布时间");
    attrList.add("ARTICLE_LIST,文章列表");
    attrList.add("RELATED_ARTICLE,相关文章");
    attrList.add("TOP_STORIES,热点文章");
    attrList.add("COLUMN_LIST,栏目列表");
    attrList.add("CHINESE_PATH,中文路径");
    attrList.add("ENGLISH_PATH,英文路径");
    attrList.add("NAVBAR,导航条");
    attrList.add("SUBARTICLE_LIST,子文章列表");
    attrList.add("BROTHER_LIST,兄弟文章列表");
    attrList.add("SUBCOLUMN_LIST,子栏目列表");
    attrList.add("ARTICLE_COUNT,文章条数");
    attrList.add("SUBARTICLE_COUNT,子文章条数");
    attrList.add("ARTICLEID,文章ID");
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
    attrList.add("ARTICLE_TYPE,文章分类");
    attrList.add("SITE_LOGO,网站LOGO");
    attrList.add("SITE_BANNER,网站形象图标");
    attrList.add("SITE_MAIN_NAVIGATOR,网站主导航");
    attrList.add("SITE_SIDE_NAVIGATOR,网站辅助导航");
    attrList.add("SEARCH,检索表单");
    attrList.add("ORDERSEARCH_RESULT,订单查询表单");
    attrList.add("USER_LOGIN,用户登录表单");
    attrList.add("ARTICLE_COMMENT,文章评论表单");
    attrList.add("TURN_PIC,文章轮换图片");
    attrList.add("USED_MARK,选择已有标记");
%>

<html>
<head>
    <title>修改模板</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/editor.css">
    <script type="text/javascript" src="../toolbars/btnclick.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>

    <script language="javascript">
        function checkoption()
        {
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
            /*else if (param == 4)
                document.createForm.cname.value = "WAP栏目模板";
            else if (param == 5)
                document.createForm.cname.value = "WAP文章模板";*/

            var selectlens = new Array;
            selectlens[0] = 24;
            selectlens[1] = <%=attrList.size() - 1%>;
            var optionsname = new Array();
            var optionsvalue = new Array();

            optionsname[0] = new Array();
            optionsname[0][0] = "选择标记";
            optionsname[0][1] = "文章列表";
            optionsname[0][2] = "栏目列表";
            optionsname[0][3] = "中文路径";
            optionsname[0][4] = "英文路径";
            optionsname[0][5] = "热点文章";
            optionsname[0][6] = "相关文章";
            optionsname[0][7] = "子文章列表";
            optionsname[0][8] = "兄弟文章列表";
            optionsname[0][9] = "子栏目列表";
            optionsname[0][10] = "文章条数";
            optionsname[0][11] = "子文章条数";
            optionsname[0][12] = "导航条";
            optionsname[0][13] = "栏目名称";
            optionsname[0][14] = "父栏目名称";
            optionsname[0][15] = "栏目URL";
            optionsname[0][16] = "网站LOGO";
            optionsname[0][17] = "网站形象图标";
            optionsname[0][18] = "网站主导航";
            optionsname[0][19] = "网站辅助导航";
            optionsname[0][20] ="检索表单";
            optionsname[0][21] ="订单查询表单";
            optionsname[0][22] ="用户登录表单";
            //optionsname[0][20] ="日历";
            optionsname[0][23] = "图片特效";
            optionsname[0][24] = "COPYRIGHT";
            optionsname[0][25] = "选择已有标记";
            //optionsname[0][24] = "选择已有标记";

            optionsvalue[0] = new Array();
            optionsvalue[0][0] = "NO_SELECT";
            optionsvalue[0][1] = "ARTICLE_LIST";
            optionsvalue[0][2] = "COLUMN_LIST";
            optionsvalue[0][3] = "CHINESE_PATH";
            optionsvalue[0][4] = "ENGLISH_PATH";
            optionsvalue[0][5] = "TOP_STORIES";
            optionsvalue[0][6] = "RELATED_ARTICLE";
            optionsvalue[0][7] = "SUBARTICLE_LIST";
            optionsvalue[0][8] = "BROTHER_LIST";
            optionsvalue[0][9] = "SUBCOLUMN_LIST";
            optionsvalue[0][10] = "ARTICLE_COUNT";
            optionsvalue[0][11] = "SUBARTICLE_COUNT";
            optionsvalue[0][12] = "NAVBAR";
            optionsvalue[0][13] = "COLUMNNAME";
            optionsvalue[0][14] = "PARENT_COLUMNNAME";
            optionsvalue[0][15] = "COLUMNURL";
            optionsvalue[0][16] = "SITE_LOGO";
            optionsvalue[0][17] = "SITE_BANNER";
            optionsvalue[0][18] = "SITE_MAIN_NAVIGATOR";
            optionsvalue[0][19] = "SITE_SIDE_NAVIGATOR";
            optionsvalue[0][20] = "SEARCH_FORM";
            optionsvalue[0][21] = "ORDERSEARCH_RESULT";
            optionsvalue[0][22] = "LOGIN_FORM";
            optionsvalue[0][23] = "XUAN_IMAGES";
            optionsvalue[0][24] = "COPY_RIGHT";
            optionsvalue[0][25] = "USED_MARK";
            // optionsvalue[0][24] = "USED_MARK";

            optionsname[1] = new Array();
            optionsvalue[1] = new Array();

            <%for (int i=0; i<attrList.size(); i++){
              String temp = (String)attrList.get(i);
              String name = temp.substring(0, temp.indexOf(","));
              String value = temp.substring(temp.indexOf(",") + 1);
              out.println("optionsname[1]["+i+"] =\""+value+"\";\r\n");
              out.println("optionsvalue[1]["+i+"] =\""+name+"\";\r\n");
            }%>

            if (param == 2 || param == 3 || para == 4) param = 0;
            if (param == 5) param = 1;
            document.getElementById("MarkName").options.length=0;
            for (x = 0; x <= selectlens[param]; x++)
            {
                document.getElementById("MarkName").options.add(new Option(optionsname[param][x],optionsvalue[param][x]));
            }
        }

        function historyback(historyID)
        {
            if (historyID > 0 && confirm("是否真的要恢复历史模板？"))
                window.location = "edittemplate.jsp?template=<%=ID%>&column=<%=columnID%>&isArticle=<%=modelType%>&rightid=2&historyID=" + historyID;
            else
                createForm.historyID[0].selected = true;
        }

        function saveModel()
        {
            if (document.createForm.modelType[1].selected)
            {
                var ret = confirm("要重新发布所有套用该文章模板的文章吗？");
                if (ret) {
                    document.createForm.publish.value = "true";
                }
            }
            edit_src(createForm);
        }
    </script>
</head>

<body onload="return checkoption();">
<form action="copytemplate.jsp" method="post" name="createForm">
    <input type="hidden" name="backidx">
    <input type="hidden" name="textvalues" value="">   <!--表单定义的XML表示-->
    <input type="hidden" name="publish" value="false">
    <input type="hidden" name=doUpdate value=true>
    <input type=hidden name=column value="<%=columnID%>">
    <input type="hidden" name=modelnameBak value="<%=modelname%>">
    <input type="hidden" name=template value="<%=ID%>">
    <input type="hidden" name=columnCode value="<%=columnID%>">
    <input type="hidden" name=modelSourceCodeFlag value=0>
    <input type=hidden name=template_or_article_flag value="0">
    <input type="hidden" name=editor value="<%=username%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="tempURL" value="<%=request.getRequestURL().toString()+"-"+siteid%>">

    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr height=30>
            <td width="30%">当前栏目&nbsp;>&nbsp;<%=columnName%>
            </td>
            <td width="40%"><%
                if (!success && errors) {
                    out.println("<span class=cur>修改模板失败，请重新再试。</span>");
                }
            %></td>
            <td width="30%" align=right>
                <input class=tine type=button value="  保存  " name="savebutton" onclick="saveModel();">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="window.location='closewin.jsp?id=<%=ID%>&column=<%=columnID%>&rightid=<%=rightid%>';">&nbsp;&nbsp;
            </td>
        </tr>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr>
            <td colspan=3 height=4></td>
        </tr>
    </table>

    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr>
            <td>
                <!--input type=radio name=insertTemplat value=0 onclick="javascript:Add_Template_onclick(<%//=columnID%>);">装入模板-->
                &nbsp;模板类型：<select name=modelType onchange="checkoption();">
                <%if (column.getParentID() == 0) {%>
                <option value=2 <%if (modelType == 2) {%> selected<%}%>>首页模板</option>
                <%}else{%>
                <option value=0 <%if (modelType == 0) {%> selected<%}%>>栏目模板</option>
                <%}%>
                <option value=1 <%if (modelType == 1) {%> selected<%}%>>文章模板</option>
                <option value=3 <%if (modelType == 3) {%> selected<%}%>>专题模板</option>
            </select>
                &nbsp;模板中文名称：<input name="cname" size=15 value="<%=cname%>">
                &nbsp;模板文件名：<input name="modelname" size=15 value="<%=modelname%>">
                &nbsp;<select class=tine name="historyID" style="width:114px" onchange="historyback(this.value);">
                <option value="0">选择历史模板</option>
                <%for (int i = historylist.size() - 1; i > -1; i--) {%>
                <option value="<%=historylist.get(i)%>"><%=
                (new Timestamp(Long.parseLong((String) historylist.get(i)))).toString().substring(0, 19)%>
                </option>
                <%}%>
            </select>
                &nbsp;<select ID="MarkName" onchange="return MarkName_Add(<%=columnID%>,'<%=sitename%>')"></select>
                &nbsp;<input type=checkbox name="isIncluded" value=1 <%if(isIncluded==1){%> checked<%}%>>包含文件&nbsp;模版号<input type=text name="tempnum" size="3" value="<%=model.getTempnum()%>">
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
                <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"><%=content%></textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content');
                    //var oFCKeditor = new FCKeditor('content') ;
                    //oFCKeditor.BasePath = "../fckeditor/";
                    //oFCKeditor.Height = 530;
                    //oFCKeditor.ToolbarSet = "Default";
                    //oFCKeditor.ReplaceTextarea();
                </script>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
