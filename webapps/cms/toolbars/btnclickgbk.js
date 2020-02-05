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

function MarkName_Add(columnID,sitename)
{
    var returnvalue = "";
    var marksname = "";
    var isButton = "";

    var isMSIE = (navigator.appName == "Microsoft Internet Explorer");

    //判断浏览器
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

    if (mark_value == "ARTICLE_LIST") {
        marksname = "文章列表";
        winStr = "addArticleList.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "文章列表", "font-family:Verdana; font-size:12; dialogWidth:72em; dialogHeight:54em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "SUBARTICLE_LIST") {
        marksname = "子文章列表";
        winStr = "addArticleList.jsp?type=1&column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "子文章列表", "font-family:Verdana; font-size:12; dialogWidth:72em; dialogHeight:54em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子文章列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "BROTHER_LIST") {
        marksname = "兄弟文章列表";
        winStr = "addArticleList.jsp?type=2&column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "兄弟文章列表", "font-family:Verdana; font-size:12; dialogWidth:72em; dialogHeight:54em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "兄弟文章列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "RECOMMEND_LIST") {
        marksname = "推荐文章列表";
        winStr = "commendarticle.jsp?type=3&column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr,"推荐文章列表","font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:55em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "推荐文章列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "ARTICLE_COUNT") {
        marksname = "文章条数";
        winStr = "addArticleCountList.jsp";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "文章条数", "font-family:Verdana; font-size:12; dialogWidth:50em; dialogHeight:35em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章条数", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "SUBARTICLE_COUNT") {
        marksname = "子文章条数";
        winStr = "addSubArticleCountList.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "子文章条数", "font-family:Verdana; font-size:12; dialogWidth:50em; dialogHeight:35em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子文章条数", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "COLUMN_LIST") {
        marksname = "栏目列表";
        winStr = "addColumnList.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "栏目列表", "font-family:Verdana; font-size:12; dialogWidth:780px;dialogHeight:550px;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "栏目列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "SUBCOLUMN_LIST") {
        marksname = "子栏目列表";
        winStr = "addSubColumnList.jsp?type=1&column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "子栏目列表", "font-family:Verdana; font-size:12; dialogWidth:56em; dialogHeight:35em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "子栏目列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if(mark_value == "INCLUDE_FILE"){
        marksname = "包含文件";
        winStr = "add_include.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "包含文件", "font-family:Verdana; font-size:12; dialogWidth:56em; dialogHeight:35em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "包含文件", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "CHINESE_PATH") {
        marksname = "中文路径";
        winStr = "addPath.jsp?type=0";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "中文路径", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][CHINESE_PATH]" + returnvalue + "[/CHINESE_PATH][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "中文路径", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    //前台图片文字组合内容
    else if (mark_value == "IMGZUHECONTENT") {
        marksname = "前台图片组合内容";
        var oEditor1 = window.parent.FCKeditorAPI.GetInstance("content");
        var content= oEditor1.GetXHTML(true);
        var forms=content.split("[IMGZUHECONTENT]");

        winStr = "imgzuhecontent.jsp?order="+forms.length;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "前台图片组合内容", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18sem; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAGA][IMGZUHECONTENT]" + returnvalue + "[/IMGZUHECONTENT][/TAGA]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "前台图片组合内容", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "TEXTZUHECONTENT") {
        marksname = "前台文字组合内容";
        var oEditor1 = window.parent.FCKeditorAPI.GetInstance("content");
        var content= oEditor1.GetXHTML(true);
        var formsf=content.split("[TEXTZUHECONTENT]");
        winStr = "textzuhecontent.jsp?order="+formsf.length;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "前台文字组合内容", "font-family:Verdana; font-size:12; dialogWidth:56em; dialogHeight:35em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAGA][TEXTZUHECONTENT]" + returnvalue + "[/TEXTZUHECONTENT][/TAGA]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "前台文字组合内容", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "SEECOOKIE") {
        marksname = "最近浏览";
        winStr = "addSeeCookietag.jsp";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "最近浏览", "font-family:Verdana; font-size:12; dialogWidth:56em; dialogHeight:35em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue =returnvalue;
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "最近浏览", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    //---------------------
    else if (mark_value == "ENGLISH_PATH") {
        marksname = "英文路径";
        winStr = "addPath.jsp?type=1";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "英文路径", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][ENGLISH_PATH]" + returnvalue + "[/ENGLISH_PATH][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "英文路径", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "PREV_ARTICLE") {
        marksname = "上一篇";
        var winStr = "addNextArticle.jsp?type=0";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "上一篇", "font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][PREV_ARTICLE]" + returnvalue + "[/PREV_ARTICLE][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "上一篇", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "NEXT_ARTICLE") {
        marksname = "下一篇";
        var winStr = "addNextArticle.jsp?type=1";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "下一篇", "font-family:Verdana;font-size:12;dialogWidth:32em;dialogHeight:18em;status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][NEXT_ARTICLE]" + returnvalue + "[/NEXT_ARTICLE][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "下一篇", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "RELATED_ARTICLE") {
        marksname = "相关文章";
        winStr = "addRelateList.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "相关文章", "font-family:Verdana; font-size:12; dialogWidth:578px;dialogHeight:385px;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "相关文章", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "TOP_STORIES") {
        marksname = "热点文章";
        winStr = "../templatex/topStories.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "热点文章", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:55em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "热点文章", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "ARTICLE_MEDIA") {
        marksname = "视频播放";
        winStr = "../template/addPlayMediaFrame.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "视频播放", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:55em;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "视频播放", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "ARTICLE_CONTENT") {
        marksname = "文章内容";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_CONTENT][/ARTICLE_CONTENT][/TAG]";
    }
    else if (mark_value == "ARTICLE_MAINTITLE") {
        marksname = "文章标题";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_MAINTITLE][/ARTICLE_MAINTITLE][/TAG]";
    }
    else if (mark_value == "ARTICLE_VICETITLE") {
        marksname = "文章副标题";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_VICETITLE][/ARTICLE_VICETITLE][/TAG]";
    }
    else if (mark_value == "ARTICLE_KEYWORD") {
        marksname = "文章关键字";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_KEYWORD][/ARTICLE_KEYWORD][/TAG]";
    }
    else if (mark_value == "ARTICLE_PT") {
        marksname = "发布时间";
        winStr = "addArticlePt.jsp";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "发布时间", "font-family:Verdana; font-size:12; dialogWidth:300px;dialogHeight:120px;status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "发布时间", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "ARTICLE_AUTHOR") {
        marksname = "文章作者";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_AUTHOR][/ARTICLE_AUTHOR][/TAG]";
    }
    else if (mark_value == "ARTICLE_SUMMARY") {
        marksname = "文章概述";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_SUMMARY][/ARTICLE_SUMMARY][/TAG]";
    }
    else if (mark_value == "ARTICLE_SOURCE") {
        marksname = "文章来源";
        isButton = "1";
        returnvalue = "[TAG][ARTICLE_SOURCE][/ARTICLE_SOURCE][/TAG]";
    }
    else if (mark_value == "COLUMNNAME") {
        marksname = "栏目名称";
        isButton = "1";
        returnvalue = "[TAG][COLUMNNAME][/COLUMNNAME][/TAG]";
    }
    else if (mark_value == "PARENT_COLUMNNAME") {
        marksname = "父栏目名称";
        isButton = "1";
        returnvalue = "[TAG][PARENT_COLUMNNAME][/PARENT_COLUMNNAME][/TAG]";
    }
    else if (mark_value == "NAVBAR")
    {
        marksname = "导航条";
        winStr = "addNavBar.jsp?column=" + columnID + "&type=0";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "导航条", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][NAVBAR]" + returnvalue + "[/NAVBAR][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "导航条", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == "PAGINATION")
    {
        marksname = "分页标记";
        winStr = "addNavBar.jsp?type=1";
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "分页标记", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18em; status:no");
            if (returnvalue != "" && returnvalue != "0")
                returnvalue = "[TAG][PAGINATION]" + returnvalue + "[/PAGINATION][/TAG]";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "分页标记", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == 'ARTICLE_TYPE')
    {
        marksname = "文章分类标记";
        winStr = "addArticleType.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "文章分类标记", "font-family:Verdana; font-size:12; dialogWidth:32em; dialogHeight:18em; status:no");
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章分类标记", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
        isButton = "1";
    }  else if (mark_value == 'XUAN_IMAGES') {
        marksname = "图片特效";
        winStr = "addXuanImagesFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "图片特效", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "图片特效", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    } else if (mark_value == 'TURN_PIC') {
        marksname = "文章图片特效";
        winStr = "addArticleXuanImagesFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "文章图片特效", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "文章图片特效", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    } else if (mark_value == 'SEARCH_FORM') {
        marksname = "查询表单";
        returnvalue = "<table border=\"0\">" +
            "<form action=\"/" + sitename + "/_prog/search.jsp\" method=\"post\" name=\"searchForm\">" +
            "<tr>" +
            "<td><input name=\"searchcontent\" /></td>" +
            "<td><input type=\"image\" alt=\"\" src=\"/images/button-search.gif\" border=\"0\" /></td>" +
            "</tr>" +
            "</form>" +
            "</table>";
        isButton = "1";
    } else if (mark_value == 'LOGIN_FORM') {
        marksname = "登录表单";
        winStr = "addUserLoginFormFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "登录表单", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:60em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "登录表单", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }else if (mark_value == 'PROGRRAMLOGIN_FORM') {
        marksname = "程序模板登录表单";
        winStr = "addUserProgramLoginFormFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "程序模板登录表单", "font-family:Verdana; font-size:12; dialogWidth:60em; dialogHeight:40em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "程序模板登录表单", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == 'DEFINEINFO_FORM') {
        marksname = "调查表单";
        winStr = "addUserDefinenFormFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "调查表单", "font-family:Verdana; font-size:12; dialogWidth:40em; dialogHeight:40em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "调查表单", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }else if (mark_value == 'USER_LOGIN_DISPLAY') {
        marksname = "用户登录显示";
        winStr = "addUserLoginFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "用户登录显示", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:60em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户登录显示", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == 'LEAVEMESSAGE') {
        marksname = "用户留言表单";
        winStr = "addUserLeavemessageFormFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "用户留言表单", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:60em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户留言表单", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == 'LEAVEMESSAGELIST') {
        marksname = "用户留言列表";
        winStr = "addUserLeavemessageListFormFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "用户留言列表", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:60em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户留言列表", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    }
    else if (mark_value == 'ARTICLE_COMMENT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "文章评论";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addArticleCommentFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "文章评论", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "文章评论", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addFeedbackFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "信息反馈", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "信息反馈", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'LEAVE_MESSAGE') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户留言";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addLeaveMessageFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "用户留言", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "用户留言", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addLeaveMessageListFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "用户留言", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "用户留言", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'REGISTER_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户注册";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addUserRegisterFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "用户注册", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:60em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "用户注册", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'USER_LOGIN_DISPLAY') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "用户登录显示";
        /* var mname_in_thepage = includeTheProgramTag(marksname);
         if (mname_in_thepage == "[" + marksname + "]")
         alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
         else if (mname_in_thepage == ""){*/
        winStr = "addUserLoginFrame.jsp?column="+columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "用户登录显示", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "用户登录显示", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
        /*}
         else {
         alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
         }*/
    } else if (mark_value == 'UPDATEREG') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "修改注册";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addUpdateRegFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "修改注册", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "修改注册", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addMapmarkFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "地图标注", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "地图标注", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    } else if (mark_value == 'SEARCH_RESULT') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "信息检索";
        var mname_in_thepage = includeTheProgramTag(marksname);
        if (mname_in_thepage == "[" + marksname + "]")
            alert(mname_in_thepage + "标记已经存在，如果需要修改，请编辑该标记");
        else if (mname_in_thepage == ""){
            winStr = "addSearchFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "信息检索", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "信息检索", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addShoppingcarFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "购物车", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "购物车", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addOrderGenerateFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "订单生成", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:50em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "订单生成", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addOrderDisplayFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "订单回显", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "订单回显", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addOrderSearchFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "订单查询", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "订单查询", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
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
            winStr = "addOrderDetailSearchFrame.jsp?column="+columnID;
            if (isMSIE) {
                returnvalue = showModalDialog(winStr, "订单明细查询", "font-family:Verdana; font-size:12; dialogWidth:80em; dialogHeight:40em; status:no");
                if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            } else {
                var iWidth=1000;                                            //弹出窗口的宽度;
                var iHeight=800;                                            //弹出窗口的高度;
                var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
                var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
                win = window.open(winStr, "订单明细查询", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
                win.focus();
            }
        } else {
            alert("本页已经存在程序标记" + mname_in_thepage + "，请先删除已经存在的程序标记，然后在添加新的程序标记");
        }
    }  else if (mark_value == 'SELF_DEFINE_FORM') {
        //判断在页面上是否已经包括有程序标记，如果有用户是否要替换现有的程序标记，用户选择替换，则用新程序标记替换老的程序标记，否则退出
        marksname = "自定义表单";
        //var mname_in_thepage = includeTheProgramTag(marksname);
        winStr = "selfdefineform.jsp?column="+columnID;
        if (isMSIE) {
            isButton = "1";                          //不插入按钮格式，而是插入HTML源代码
            returnvalue = showModalDialog(winStr, "自定义表单", "font-family:Verdana; font-size:12; dialogWidth:70em; dialogHeight:40em; status:no");
            returnvalue = "<form name='myform' action='my.jsp' method='post'>" +
                "<input type='hidden' name='doCreate' value='true'>" +
                "</form>"
            document.createForm.textvalues.value = returnvalue;
        } else {
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "自定义表单", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    } else if (mark_value == 'USED_MARK') {
        winStr = "selectmark.jsp?column=" + columnID;
        if (isMSIE) {
            returnvalue = showModalDialog(winStr, "", "font-family:Verdana; font-size:12; dialogWidth:52em; dialogHeight:24em; status:no");
            if (returnvalue==undefined) document.createForm.MarkName.value = "NO_SELECT";
            if (returnvalue != undefined && returnvalue.indexOf('MARKID') != -1) {
                var retmarkid = returnvalue.substring(returnvalue.indexOf('[TAG]'), returnvalue.indexOf('[/TAG]') + 6);
                var marktype = returnvalue.substring(returnvalue.indexOf('[TYPE]') + 6, returnvalue.indexOf('[/TYPE]'));
                returnvalue = retmarkid;
                //alert(marktype);
                //alert(returnvalue);
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
            var iWidth=1000;                                            //弹出窗口的宽度;
            var iHeight=800;                                            //弹出窗口的高度;
            var iTop = (window.screen.availHeight-30-iHeight)/2;        //获得窗口的垂直位置;
            var iLeft = (window.screen.availWidth-10-iWidth)/2;         //获得窗口的水平位置;
            win = window.open(winStr, "", "top=" + iTop + ",left=" + iLeft + ",width=" + iWidth + ",height=" + iHeight);
            win.focus();
        }
    } else {
        returnvalue = "[TAG][" + mark_value + "]" + document.createForm.MarkName[document.createForm.MarkName.selectedIndex].text + "[/" + mark_value + "][/TAG]";
        isButton = "1";
    }

    if (returnvalue != "" && returnvalue != null && returnvalue != "0" && returnvalue != undefined) {
        if (isButton == "")
            returnvalue = "<INPUT name='" + returnvalue + "' type=button value='[" + marksname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>";
        var addMark = returnvalue;
        if ( mark_type == 11) {          //信息检索
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
        }

        //alert(addMark);
        document.createForm.MarkName.value = "NO_SELECT";
        InsertHTML('content', addMark);
    }
    document.createForm.MarkName.options[0].selected = true;
}

function includeTheProgramTag(tagname) {
    //文章评论  信息反馈  用户留言  用户注册  用户登录 修改注册 信息检索  购物车  订单生成 订单回显  订单查询 订单明细查询 自定义表单 网上调查
    //企业招聘  多媒体  全景展示  VOIP
    var oEditor = FCKeditorAPI.GetInstance("content");
    var buf = oEditor.GetXHTML(true);
    var regexp = /name=\"\[TAG\]\[MARKID\][0-9_\-]*\[\/MARKID\]\[\/TAG\]\" value=\"\[[^\[\]]*\]\"/g;
    var rtnAry = buf.match(regexp);
    if (rtnAry != null)
    {
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

function InsertHTML(e, inStr) {
    var oEditor = FCKeditorAPI.GetInstance(e);
    //var buf = oEditor.GetXHTML(true);
    //if (buf.indexOf("-100[/MARKID][/TAG]") == -1) {
    if (oEditor.EditMode == FCK_EDITMODE_WYSIWYG){
        oEditor.InsertHtml(inStr);
    } else {
        alert('You must be on WYSIWYG mode!');
    }
    //} else {
    //    alert('在本页面中已经存在程序标记，请删除原来的程序标记，在添加新的程序标记');
    //}
}

function UpdateHTML(e, inStr)
{
    var oEditor = FCKeditorAPI.GetInstance(e) ;
    var oActiveEl = oEditor.Selection.GetSelectedElement();
    oActiveEl.setAttribute('name', inStr);
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