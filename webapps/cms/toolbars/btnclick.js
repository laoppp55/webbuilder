function edit_src(formname)
{
    if (eval(formname).cname.value == "")
    {
        alert("请输入模板中文名称！");
        return;
    }

    if (eval(formname).modelname.value == "")
    {
        alert("请输入模板文件名！");
        return;
    }

    eval(formname).submit();
    return true;
}

function MarkName_Add(columnID,sitename) {
    var returnvalue = "";
    var marksname = "";
    var isButton = "";

    var buf = document.createForm.MarkName.value;
    var mark_type=0;
    var mark_value="";
    posi = buf.lastIndexOf("-");
    if (posi > -1)  {
        mark_value = buf.substring(0,posi);
        mark_type = buf.substring(posi+1);
    } else {
        mark_value = buf;
    }

    //alert("mark_value==" + mark_value);

    if (mark_value == "ARTICLE_LIST") {
        marksname = "文章列表";
        winStr = "addArticleList.jsp?column=" + columnID;
        var iWidth=window.screen.availWidth-500;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-200;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }
    else if (mark_value == "COMMEND_ARTICLE") {
        marksname = "推荐文章列表";
        winStr = "commendarticle.jsp?column="+columnID;
        var iWidth=window.screen.availWidth-500;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-200;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "推荐文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }
    else if (mark_value == "SUBARTICLE_LIST") {
        marksname = "子文章列表";
        winStr = "addArticleList.jsp?type=1&column=" + columnID;
        var iWidth=window.screen.availWidth-500;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-200;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "子文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }
    else if (mark_value == "BROTHER_LIST") {
        marksname = "兄弟文章列表";
        winStr = "addArticleList.jsp?type=2&column=" + columnID;
        var iWidth=window.screen.availWidth-500;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-200;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "兄弟文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "COLUMN_LIST") {
        marksname = "栏目列表";
        winStr = "addColumnList.jsp?column=" + columnID;
        var iWidth=window.screen.availWidth-500;                                                 //弹出窗口的宽度;
        var iHeight=window.screen.availHeight-200;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "栏目列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "SUBCOLUMN_LIST") {
        marksname = "子栏目列表";
        winStr = "addSubColumnList.jsp?type=1&column=" + columnID;
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "子栏目列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "RELATED_ARTICLE") {
        marksname = "相关文章";
        winStr = "addRelateList.jsp?column=" + columnID;
        var iWidth=600;                                                 //弹出窗口的宽度;
        var iHeight=400;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "相关文章", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "TOP_STORIES") {
        marksname = "热点文章";
        winStr = "../templatex/topStories.jsp?column=" + columnID;
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "热点文章", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "ARTICLE_COUNT") {
        marksname = "文章条数";
        winStr = "addArticleCountList.jsp";
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "文章条数", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "SUBARTICLE_COUNT") {
        marksname = "子文章条数";
        winStr = "addSubArticleCountList.jsp?column="+columnID;
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "子文章条数", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if(mark_value == "INCLUDE_FILE"){
        marksname = "包含文件";
        winStr = "add_include.jsp?column="+columnID;
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "包含文件", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "CHINESE_PATH") {
        marksname = "中文路径";
        winStr = "addPath.jsp?type=0";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "中文路径", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "ENGLISH_PATH") {
        marksname = "英文路径";
        winStr = "addPath.jsp?type=1";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "英文路径", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }  else if (mark_value == "SEECOOKIE") {
        marksname = "最近浏览";
        winStr = "addSeeCookietag.jsp";
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "最近浏览", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }/*else if (mark_value == "IMGZUHECONTENT") {
        marksname = "前台图片组合内容";
        var oEditor1 = CKEDITOR.instances.content;
        var content= oEditor1.getData();
        var forms=content.split("[IMGZUHECONTENT]");
        winStr = "imgzuhecontent.jsp?order="+forms.length;
        var iWidth=600;                                                 //弹出窗口的宽度;
        var iHeight=400;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "前台图片组合内容", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "TEXTZUHECONTENT") {
        marksname = "前台文字组合内容";
        var oEditor1 = CKEDITOR.instances.content;
        var content= oEditor1.getData();
        var formsf=content.split("[TEXTZUHECONTENT]");
        winStr = "textzuhecontent.jsp?order="+formsf.length;
        var iWidth=600;                                                 //弹出窗口的宽度;
        var iHeight=400;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "前台文字组合内容", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'ARTICLE_TYPE') {
        marksname = "文章分类标记";
        winStr = "addArticleType.jsp?column="+columnID;
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "文章分类标记", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }*/ else if (mark_value == "PREV_ARTICLE") {
        marksname = "上一篇";
        var winStr = "addNextArticle.jsp?type=0";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "上一篇", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "NEXT_ARTICLE") {
        marksname = "下一篇";
        var winStr = "addNextArticle.jsp?type=1";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "下一篇", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "ARTICLE_MEDIA") {
        marksname = "视频播放";
        winStr = "../template/addPlayMediaFrame.jsp?column=" + columnID;
        var iWidth=800;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "视频播放", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "ARTICLE_CONTENT") {
        marksname = "文章内容";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_CONTENT][/ARTICLE_CONTENT][/TAG]";
    } else if (mark_value == "ARTICLE_MAINTITLE") {
        marksname = "文章标题";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_MAINTITLE][/ARTICLE_MAINTITLE][/TAG]";
    } else if (mark_value == "ARTICLE_VICETITLE") {
        marksname = "文章副标题";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_VICETITLE][/ARTICLE_VICETITLE][/TAG]";
    } else if (mark_value == "ARTICLE_KEYWORD") {
        marksname = "文章关键字";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_KEYWORD][/ARTICLE_KEYWORD][/TAG]";
    } else if (mark_value == "ARTICLE_PT") {
        marksname = "发布时间";
        winStr = "addArticlePt.jsp";
        var iWidth=400;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "发布时间", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        //win = window.open(winStr, "发布时间", "font-family=Verdana,font-size=12,width=300px,height=120px,status=no");
        win.focus();
    } else if (mark_value == "ARTICLE_AUTHOR") {
        marksname = "文章作者";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_AUTHOR][/ARTICLE_AUTHOR][/TAG]";
    } else if (mark_value == "ARTICLE_SUMMARY") {
        marksname = "文章概述";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_SUMMARY][/ARTICLE_SUMMARY][/TAG]";
    } else if (mark_value == "ARTICLE_SOURCE") {
        marksname = "文章来源";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_SOURCE][/ARTICLE_SOURCE][/TAG]";
    } else if (mark_value == "COLUMNNAME") {
        marksname = "栏目名称";
        isButton = "1";
        returnvalue = "[TAG][COLUMNNAME][/COLUMNNAME][/TAG]";
    } else if (mark_value == "PARENT_COLUMNNAME") {
        marksname = "父栏目名称";
        isButton = "1";
        returnvalue = "[TAG][PARENT_COLUMNNAME][/PARENT_COLUMNNAME][/TAG]";
    } else if (mark_value == "NAVBAR") {
        marksname = "导航条";
        winStr = "addNavBar.jsp?column=" + columnID + "&type=0";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "导航条", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == "PAGINATION") {
        marksname = "分页标记";
        winStr = "addNavBar.jsp?type=1";
        var iWidth=500;                                                 //弹出窗口的宽度;
        var iHeight=300;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "分页标记", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }  else if (mark_value == 'XUAN_IMAGES') {
        marksname = "图片特效";
        winStr = "addXuanImagesFrame.jsp?column="+columnID;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "图片特效", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'TURN_PIC') {
        marksname = "文章图片特效";
        winStr = "addArticleXuanImagesFrame.jsp?column="+columnID;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "文章图片特效", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'SEARCH_FORM') {
        marksname = "查询表单";
        returnvalue =
            "<form action=\"/" + sitename + "/_prog/search.jsp\" method=\"post\" name=\"searchForm\">" +
            "<table border=\"0\">" +
            "<tr>" +
            "<td><input name=\"searchcontent\" /></td>" +
            "<td><input type=\"image\" alt=\"\" src=\"/images/button-search.gif\" border=\"0\" /></td>" +
            "</tr>" +
            "</table>" +
            "</form>";
        isButton = "1";
    } else if (mark_value == 'SEARCH_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "信息检索";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addSearchFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "信息检索", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'LOGIN_FORM') {
        marksname = "登录表单";
        winStr = "addUserLoginFormFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "登录表单", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }else if (mark_value == 'PROGRRAMLOGIN_FORM') {
        marksname = "登录页面";
        winStr = "addUserProgramLoginFormFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "登录页面", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'USER_LOGIN_DISPLAY') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "登录成功信息显示";
        winStr = "addUserLoginFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "登录成功信息显示", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'REGISTER_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户注册";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addUserRegisterFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户注册", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'UPDATEREG') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "修改注册";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addUpdateRegFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "修改注册", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    }else if (mark_value == 'DEFINEINFO_FORM') {
        marksname = "调查表单";
        winStr = "addUserDefinenFormFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "调查表单", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'LEAVEMESSAGE') {
        marksname = "用户留言表单";
        winStr = "addUserLeavemessageFormFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "用户留言表单", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    }
    else if (mark_value == 'LEAVEMESSAGELIST') {
        marksname = "用户留言列表";
        winStr = "addUserLeavemessageListFormFrame.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "用户留言表单", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'LEAVE_MESSAGE') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户留言";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addLeaveMessageFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户留言", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'LEAVE_MESSAGELIST') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户留言列表";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addLeaveMessageListFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户留言", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'ARTICLE_COMMENT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "文章评论";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addArticleCommentFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章评论", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'FEEDBACK') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "信息反馈";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addFeedbackFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "信息反馈", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'MAPMARK') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "地图标注";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addMapmarkFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "地图标注", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'SHOPPINGCAR_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "购物车";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addShoppingcarFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "购物车", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'ORDER_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "订单生成";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addOrderGenerateFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "订单生成", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'ORDER_REDISPLAY') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "订单回显";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addOrderDisplayFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "订单回显", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'ORDERSEARCH_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "订单查询";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addOrderSearchFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "订单查询", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'ORDERSEARCH_DETAIL') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "订单明细查询";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addOrderDetailSearchFrame.jsp?column="+columnID + "&marktype=" + mark_type;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "订单明细查询", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    }  else if (mark_value == 'SELF_DEFINE_FORM') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "自定义表单";
        winStr = "selfdefineform.jsp?column="+columnID + "&marktype=" + mark_type;
        var iWidth=1000;                                                 //弹出窗口的宽度;
        var iHeight=600;                                                //弹出窗口的高度;
        var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
        var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
        win = window.open(winStr, "自定义表单", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
        win.focus();
    } else if (mark_value == 'USED_MARK') {
        winStr = "selectmark.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:52em; dialogHeight:24em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            if (returnvalue != undefined && returnvalue.indexOf('MARKID') != -1) {
                var retmarkid = returnvalue.substring(returnvalue.indexOf('[TAG]'), returnvalue.indexOf('[/TAG]') + 6);
                var marktype = returnvalue.substring(returnvalue.indexOf('[TYPE]') + 6, returnvalue.indexOf('[/TYPE]'));
                returnvalue = retmarkid;
                if (marktype == '1') {
                    marksname = "文章列表";
                } else if (marktype == '2') {
                    marksname = "栏目列表";
                } else if (marktype == '3') {
                    marksname = "热点文章";
                } else if (marktype == '4') {
                    marksname = "相关文章";
                } else if (marktype == '5') {
                    marksname = "子文章列表";
                } else if (marktype == '6') {
                    marksname = "HTML碎片";
                } else if (marktype == '7') {
                    marksname = "兄弟文章列表";
                } else {
                    document.createForm.MarkName.value = "NO_SELECT";
                    return;
                }
            } else {
                document.createForm.MarkName.value = "NO_SELECT";
                return;
            }
        } else {
            win = window.open(winStr, "", "font-family=Verdana,font-size=12,width=600px,height=340px,status=no");
            win.focus();
        }
    } else {
        returnvalue = "[TAG][" + mark_value + "]" + document.createForm.MarkName[document.createForm.MarkName.selectedIndex].text + "[/" + mark_value + "][/TAG]";
        isButton = "1";
    }

    if (returnvalue != "" && returnvalue != null && returnvalue != "0" && returnvalue != undefined) {
        alert(returnvalue);
        InsertHTML(returnvalue);
        //if (isButton == "")
        //    returnvalue = "<INPUT name='" + returnvalue + "' type=button value='[" + marksname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
        //var addMark = returnvalue;
        /*if ( mark_type == 11) {          //信息检索
            document.createForm.cname.value="信息检索";
            document.createForm.modelname.value="search";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 12) {          //购物车
            document.createForm.cname.value="购物车";
            document.createForm.modelname.value="shoppingcar";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 13) {      //订单生成
            document.createForm.cname.value="订单生成";
            document.createForm.modelname.value="ordergenerate";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 14) {          //订单回显
            document.createForm.cname.value="订单回显";
            document.createForm.modelname.value="orderdisplay";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 15) {          //订单查询
            document.createForm.cname.value="订单查询";
            document.createForm.modelname.value="ordersearch";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 16) {          //信息反馈
            document.createForm.cname.value="信息反馈";
            document.createForm.modelname.value="feedback";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 17) {          //用户评论
            document.createForm.cname.value="用户评论";
            document.createForm.modelname.value="comment";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 18) {          //用户注册
            document.createForm.cname.value="用户注册";
            document.createForm.modelname.value="register";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 19) {          //用户登录
            document.createForm.cname.value="用户登录";
            document.createForm.modelname.value="login";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 20) {          //收货地址
            document.createForm.cname.value="订单明细查询";
            document.createForm.modelname.value="orderdetailsearch";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 21) {          //用户留言
            document.createForm.cname.value="用户留言";
            document.createForm.modelname.value="leavemessage";
            document.createForm.modelType.value=mark_type;
        } else if (mark_type == 22) {          //修改注册
            document.createForm.cname.value="修改注册";
            document.createForm.modelname.value="updatereg";
            document.createForm.modelType.value=mark_type;
        }*/

        document.createForm.MarkName.value = "NO_SELECT";
        InsertHTML(addMark);
    }
    document.createForm.MarkName.options[0].selected = true;
}

function editMarkInfo() {
    var mySelection = CKEDITOR.instances.content.getSelection();
    if (mySelection.getType() == CKEDITOR.SELECTION_ELEMENT){
        var element = mySelection.getSelectedElement();
        //alert(element.getOuterHtml());
        var bname = element.getAttribute('name');
        var bval = element.getAttribute('value');
        var markID = -1;
        var columnID = -1;
        if (bname.indexOf("[MARKID]") > -1 && bname.indexOf("[SEECOOKIE]") == -1) {
            markID = bname.substring(bname.indexOf("[MARKID]") + "[MARKID]".length, bname.indexOf("_"));
            columnID = bname.substring(bname.indexOf("_") + 1, bname.indexOf("[/MARKID]"));
        }

        if(bname.indexOf("[NAVBAR]") > -1){
            bname = element.getAttribute('data-name');
        }

        if(bname.indexOf("[PAGINATION]") > -1){
            bname = element.getAttribute('data-name');
        }

        if (bname.indexOf("[CHINESE_PATH]") > -1) {
            bname = element.getAttribute('data-name');
            markID = bname.substring(bname.indexOf("[CHINESE_PATH]") + "[CHINESE_PATH]".length, bname.indexOf("[/CHINESE_PATH]"));
        }

        if (bname.indexOf("[ENGLISH_PATH]") > -1) {
            bname = element.getAttribute('data-name');
            markID = bname.substring(bname.indexOf("[ENGLISH_PATH]") + "[ENGLISH_PATH]".length, bname.indexOf("[/ENGLISH_PATH]"));
        }

        if (bname.indexOf("[SEECOOKIE][MARKID]") > -1) {
            bname = element.getAttribute('data-name');
        }

        if (bname.indexOf("[PREV_ARTICLE]") > -1) {
            bname = element.getAttribute('data-name');
        }

        if (bname.indexOf("[NEXT_ARTICLE]") > -1) {
            bname = element.getAttribute('data-name');
        }

        var markcode = "";
        htmlobj=$.ajax({
            url:"../template/getMarkCode.jsp",
            type:'post',
            dataType:'json',
            data:{
                markname:encodeURI(bval)
            },
            async:false,
            cache:false,
            success:function(data){
                markcode = data.result;
            },
            error: function (jqXHR, textStatus, errorThrown) {
                /*弹出jqXHR对象的信息*/
                alert(jqXHR.responseText);
                alert(jqXHR.status);
                alert(jqXHR.readyState);
                alert(jqXHR.statusText);
                /*弹出其他两个参数的信息*/
                alert(textStatus);
                alert(errorThrown);
            }
        });

        if (markcode === "1001"){     //[文章列表]
            var winStr = "addArticleList.jsp?column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1002") {      //[推荐文章列表]
            var winStr = "commendarticle.jsp?type=3&column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "推荐文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1003") {    //[子文章列表]
            var winStr = "addArticleList.jsp?type=1&column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1004") {           //[兄弟文章列表]
            var winStr = "addArticleList.jsp?type=2&column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "兄弟文章列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1005") {         //"[栏目列表]"
            var winStr = "addColumnList.jsp?column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "栏目列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1006") {        //[子栏目列表]
            var winStr = "addSubColumnList.jsp?column="+columnID+"&mark="+markID;
            var iWidth=window.screen.availWidth-200;                                                 //弹出窗口的宽度;
            var iHeight=window.screen.availHeight-100;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子栏目列表", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1007") {       //[热点文章]
            var winStr = "../templatex/topStories.jsp?column="+columnID+"&mark="+markID;
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "热点文章", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1008") {      //[相关文章]
            var winStr = "addRelateList.jsp?column="+columnID+"&mark="+markID;
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "相关文章", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1009") {      //[导航条]
            var winStr = "addNavBar.jsp?column="+columnID+"&str="+bname + "&type=0";
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "导航条", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1010") {      //[分页标记]
            var winStr = "addNavBar.jsp?column="+columnID+"&str="+bname + "&type=1";
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "分页标记", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1011") {     //[中文路径]
            var winStr = "addPath.jsp?type=0&column="+columnID + "&str="+ bname;
            var iWidth=500;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "中文路径", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1012") {     //[英文路径]
            var winStr = "addPath.jsp?type=0&column="+columnID + "&str="+ bname;
            var iWidth=500;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "英文路径", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1013") {     //[文章条数]
            var winStr = "addArticleCountList.jsp?str="+bname;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=450;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章条数", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1014") {      //[子文章条数]
            var winStr = "addSubArticleCountList.jsp?str="+bname;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=450;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子文章条数", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1015") {     //[包含文件]
            var winStr = "add_include.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "包含文件", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1016") {     //[最近浏览]
            var winStr = "addSeeCookietag.jsp?column="+columnID+"&mark="+markID + "&doaction=e&str=" + bname;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "最近浏览", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1017") {         //[上一篇]
            var winStr = "addNextArticle.jsp?str=" + bname;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "最近浏览", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1018") {       //[下一篇]
            var winStr = "addNextArticle.jsp?str=" + bname;
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "下一篇", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1019") {     //[视频播放]
            var winStr = "addPlayMediaFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            var iWidth=800;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "视频播放", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1020") {       //[发布时间]
            //alert(bname);
            var winStr = "addArticlePt.jsp?column="+columnID+"&str="+bname + "&doaction=e";
            var iWidth=400;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "发布时间", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }


        /*if (bval == "[前台图片组合内容]") {
            marksname = "前台图片组合内容";
            var oEditor1 = CKEDITOR.instances.content;
            var content= oEditor1.getData();
            var forms=content.split("[IMGZUHECONTENT]");
            winStr = "imgzuhecontent.jsp?order="+forms.length;
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "前台图片组合内容", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (bval == "[前台文字组合内容]") {
            marksname = "前台文字组合内容";
            var oEditor1 = CKEDITOR.instances.content;
            var content= oEditor1.getData();
            var formsf=content.split("[TEXTZUHECONTENT]");
            winStr = "textzuhecontent.jsp?order="+formsf.length;
            var iWidth=600;                                                 //弹出窗口的宽度;
            var iHeight=400;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "前台文字组合内容", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (bval == "[文章分类标记]") {
            marksname = "文章分类标记";
            winStr = "addArticleType.jsp?column="+columnID;
            var iWidth=500;                                                 //弹出窗口的宽度;
            var iHeight=300;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章分类标记", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }*/
        //[图片上滚]      [图片下滚]     [图片右滚]     [图片左滚]     [图片幻灯]    [小图引导大图]
        if (markcode === "1021" || markcode === "1022" || markcode === "1023" || markcode === "1024" || markcode === "1025" || markcode === "1026") {
            marksname = "图片特效";
            winStr = "addXuanImagesFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "图片特效", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1027") {      //[文章图片特效]
            marksname = "文章图片特效";
            winStr = "addArticleXuanImagesFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章图片特效", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
        if (markcode === "1028") {     //[信息检索]
            marksname = "信息检索";
            winStr = "addSearchFrame.jsp?column="+columnID+"&mark="+markID + "&doaction=e";
            var iWidth=1000;                                                 //弹出窗口的宽度;
            var iHeight=600;                                                //弹出窗口的高度;
            var iTop = (window.screen.availHeight-100-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-100-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "信息检索", "width="+ iWidth + ",height=" + iHeight + ",left=" + iLeft + ",top=" + iTop + ",status=yes,resizable=no,scrollbars=yes");
            win.focus();
        }
    } else {
        mySelection.unlock(true);
        //alert(mySelection.getNative());
        data = mySelection.getNative().createRange().text;
        //alert(data);
    }
    //alert(CKEDITOR.env.ie);
    //var data = CKEDITOR.instances.content.getData();
}

function InsertHTML(inStr) {
    var editor = CKEDITOR.instances.content;
    if (editor.mode == 'wysiwyg') {
        editor.insertHtml(inStr);
    } else
        alert('你必须处于可视化模式才能进行内容编辑!');
}

function SetHTMLToEditor(inStr) {
    var editor = CKEDITOR.instances.content;
    if (editor.mode == 'wysiwyg') {
        editor.setData(inStr);
    } else
        alert('你必须处于可视化模式才能进行内容编辑!');
}

function UpdateHTML(inStr) {
    var editor = CKEDITOR.instances.content;
    var selection = editor.getSelection();
    if(selection.getType()==3) {
        var element = editor.getSelection().getSelectedElement();
        element.setAttributes({
            'name':inStr
        });
        element.data('name',inStr);
    }
}

function includeTheProgramTag(tagname) {
    //文章评论  信息反馈  用户留言  用户注册  用户登录 修改注册 信息检索  购物车  订单生成 订单回显  订单查询 订单明细查询 自定义表单 网上调查
    //企业招聘  多媒体  全景展示  VOIP
    //var oEditor = FCKeditorAPI.GetInstance("content");
    //var buf = oEditor.GetXHTML(true);

    var buf = CKEDITOR.instances.content.getData();
    var regexp = /name=\"\[TAG\]\[MARKID\][0-9_\-]*\[\/MARKID\]\[\/TAG\]\" value=\"\[[^\[\]]*\]\"/g;
    var rtnAry = buf.match(regexp);
    if (rtnAry != null) {
        var findflag = false;
        var len = rtnAry.length;
        var i = 0;
        var name = "";
        while (i < len)
        {
            var tempbuf = rtnAry[i];
            posi = tempbuf.indexOf("value=\"");
            if (posi>-1) name = tempbuf.substring(posi+7);
            posi = name.indexOf("\"");
            if (posi > -1) name=name.substring(0,posi);
            if (name=="[文章评论]" || name=="[信息反馈]" || name=="[用户留言]" || name=="[用户注册]" || name=="[用户登录]" ||
                name=="[修改注册]" || name=="[信息检索]" || name=="[购物车]" || name=="[订单生成]" || name=="[订单回显]" ||
                name=="[订单查询]" || name=="[订单明细查询]") {
                findflag = true;
                break;
            }
            i++;
        }
    }

    //如果找到程序标记，则返回程序标记名称，否则返回空
    if (findflag == true)
        return name;
    else
        return "";
}

function CreateElementForEditor(elementName,sitename,dirname,filename) {
    //CKEDITOR.instances.editor1.setData( '<p>This is the editor data.</p>' );
    var editor = CKEDITOR.instances.content;
    // Check the active editing mode.
    if (editor.mode == 'wysiwyg') {
        //alert(elementName + "==" + dirname + "==" + filename.trim() + "==" + sitename);
        if (elementName == "object") {
            var element = CKEDITOR.dom.element.createFromHtml('<object id="vcastr3" type="application/x-shockwave-flash" data="http://www.bucg.com/_commons/vcastr3.swf" width="600" height="400">' +
                '<param name="movie" value="http://www.bucg.com/_commons/vcastr3.swf" />'+
                '<param name="allowFullScreen" value="true" />'+
                '<param name="FlashVars" value="xml='+
                '     <vcastr>'+
                '     <channel>'+
                '        <item>'+
                '            <source>' + dirname + 'images/' + filename.trim() + '</source>'+
                '            <duration></duration>'+
                '            <title></title>'+
                '        </item>'+
                '     </channel>'+
                '     <config></config>'+
                '     <plugIns>'+
                '         <logoPlugIn>'+
                '             <url>http://www.bucg.com/_commons/logoPlugIn.swf</url>'+
                '             <logoText></logoText>'+
                '             <logoTextAlpha>0.75</logoTextAlpha>'+
                '             <logoTextFontSize>30</logoTextFontSize>'+
                '             <logoTextLink></logoTextLink>'+
                '             <logoTextColor>0xffffff</logoTextColor>'+
                '             <textMargin>20 20 auto auto</textMargin>'+
                '         </logoPlugIn>'+
                '     </plugIns>'+
                '     </vcastr>"'+
                '/>'+
                '</object>');
            editor.insertElement(element);
        }
    } else
        alert('你必须处于可视化模式才能进行内容编辑!');
}

function Add_Template_onclick(par) {
    returnvalue = showModalDialog("../template/selectModel.jsp?column=" + par, "","font-family=Verdana,font-size=12,width=600,height=300,status=no");

    if (returnvalue != undefined && returnvalue != "")
    {
        createForm.modelfilename.value = returnvalue;
        createForm.doCreate.value = false;
        createForm.submit();
    }
}