function goSearchPage(columnid,doSearchFlag,flag,ascdesc) {
    var currentPage = form.turnPage.value;
    articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc);
    form.turnPage.value = currentPage;
}

function Pagination(totalArticleNum,columnid,totalpages,curentpage,pageNumLength,doSearchFlag,flag,ascdesc) {
    var PaginationDiv = "";
    if (totalpages <= 1)
        PaginationDiv = "<div class=\"page\" style=\"display: none\">";
    else
        PaginationDiv = "<div class=\"page\" style=\"display: block\">";

    if (curentpage<=1)
        PaginationDiv = PaginationDiv + "<span class=\"txtl\">当前栏目的文章总数：" + totalArticleNum + "</span><span>第1页</span>";
    else {
        PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + ",1," + flag + "," + ascdesc + ");\">第1页</a>";
    }

    PaginationDiv = PaginationDiv + "<span>共" + totalpages + "页</span>";

    if (curentpage<=1)
        PaginationDiv = PaginationDiv + "<span>上一页</span>";
    else {
        PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage - 1) + ","+ doSearchFlag + "," + flag + "," + ascdesc + ");\">上一页</a>";
    }

    var half_pageNumLength = parseInt(pageNumLength/2);
    var startPageNum=0;
    var endPageNum = 0;
    if (curentpage<=half_pageNumLength)
        startPageNum=0;
    else {
        startPageNum = parseInt(curentpage) - parseInt(half_pageNumLength);
        if (startPageNum<=0) startPageNum = 0;
    }

    if (curentpage<half_pageNumLength) {
        if (totalpages <= pageNumLength)
            endPageNum = totalpages;
        else
            endPageNum = pageNumLength;
    } else {
        if (totalpages <= pageNumLength)
            endPageNum = totalpages;
        else {
            endPageNum = parseInt(curentpage) + parseInt(half_pageNumLength);
            if (endPageNum>totalpages) endPageNum = totalpages;
        }
    }

    for(var ii=startPageNum;ii<endPageNum;ii++) {
        if ((ii + 1) === curentpage)
            PaginationDiv = PaginationDiv + "<span class=\"cur\">" + curentpage + "</span>";
        else {
            PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (ii + 1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\">" + (ii + 1) + "</a>";
        }
    }

    if (curentpage<totalpages)
        PaginationDiv = PaginationDiv + "<a href=\"javascript:articlesInfoList(" + columnid + "," + (curentpage+1) + "," + doSearchFlag + "," + flag + "," + ascdesc + ");\">下一页</a>";
    else
        PaginationDiv = PaginationDiv + "<span>下一页</span>";

    PaginationDiv = PaginationDiv + "<span class=\"txtl\">转到第</span>";
    PaginationDiv = PaginationDiv + "<span class=\"select-pager\">";
    PaginationDiv = PaginationDiv + "<form name=\"form\">";

    PaginationDiv = PaginationDiv + "<select name=\"turnPage\" size=\"1\" onchange=\"javascript:goSearchPage(" + columnid + ",'" + doSearchFlag + "'," + flag + "," + ascdesc + ");\">";

    for(var ii=0;ii<totalpages;ii++) {
        if ((ii+1) === curentpage)
            PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\" selected>" + (ii+1) +  "</option>";
        else
            PaginationDiv = PaginationDiv + "<option value=\"" + (ii+1) + "\">" + (ii+1) + "</option>";
    }

    PaginationDiv = PaginationDiv + "</select>";
    PaginationDiv = PaginationDiv + "</form>";
    PaginationDiv = PaginationDiv + "</span>";
    PaginationDiv = PaginationDiv + "<span class=\"txtr\">页</span>";
    PaginationDiv = PaginationDiv + "</div>";

    return PaginationDiv;
}

function articlesInfoList(columnid,currentPage,doSearchFlag,flag,ascdesc) {
    var range = 20;
    var title = "";
    var articleid = "";
    var vicetitle = "";
    var author = "";
    var source = "";
    var spubdate = "";
    var epubdate = "";
    var status = "";

    if (doSearchFlag) {
        title = searchForm.title.value;
        articleid = searchForm.articleid.value;
        vicetitle = searchForm.vicetitle.value;
        author = searchForm.author.value;
        source = searchForm.source.value;
        spubdate = searchForm.spubdate.value;
        epubdate = searchForm.epubdate.value;
        status = searchForm.status.value;
    }

    htmlobj=$.ajax({
        url:"../article/articlesnew.jsp?page=" + currentPage,
        data:{
            doSearch:doSearchFlag,
            title:encodeURIComponent(title),
            articleid:articleid,
            vicetitle:encodeURIComponent(vicetitle),
            author:encodeURIComponent(author),
            source:encodeURIComponent(source),
            spubdate:encodeURIComponent(spubdate),
            epubdate:encodeURIComponent(epubdate),
            status:status,
            column:columnid,
            range:range,
            flag:flag,
            ascdesc:ascdesc
        },
        dataType:'json',
        async:false,
        success:function(jsondata){
            var writeRight = jsondata.WRITERIGHT;
            var publishRight = jsondata.PUBLISHRIGHT;
            var adminRight = jsondata.ADMINRIGHT;
            var searchflag = jsondata.DOSEARCH;
            var articleIds = "";
            range = jsondata.RANGE;
            var extra = jsondata.TOTALCOUNT % range;
            var totalPage = 0;
            if (extra>0)
                totalPage = parseInt(jsondata.TOTALCOUNT/range) + 1;
            else
                totalPage = jsondata.TOTALCOUNT/range;
            var ascDescFlag = 0;
            if (jsondata.ASCDESC === 0)
                ascDescFlag = 1;                    //升序排列
            else
                ascDescFlag = 0;                    //降序排列

            if (jsondata.ARTICLEID == 0)            //按文章ID排序
                articleid="";
            else
                articleid=jsondata.ARTICLEID;

            if (typeof(jsondata.TITLE) == "undefined")    //按文章标题排序
                title = "";
            else
                title = jsondata.TITLE;

            if (typeof(jsondata.VICETITLE) == "undefined")     //按文章副标题排序
                vicetitle = "";
            else
                vicetitle = jsondata.VICETITLE;

            if (typeof(jsondata.AUTHOR) == "undefined")      //按文章作者排序
                author = "";
            else
                author = jsondata.AUTHOR;

            if (typeof(jsondata.SOURCE) == "undefined")       //按文章来源排序
                source = "";
            else
                source = jsondata.SOURCE;

            status = parseInt(jsondata.STATUS);                  //按文章状态排序
            var option_str ="                      <option value=0>请选择</option>" +
                "                      <option value=1>新稿</option>" +
                "                      <option value=2>已发布</option>" +
                "                      <option value=3>未用</option>" +
                "                      <option value=4>在审</option>" +
                "                      <option value=5>退稿</option>";

            if (status==1)
                option_str ="                      <option value=0>请选择</option>" +
                    "                      <option value=1 selected>新稿</option>" +
                    "                      <option value=2>已发布</option>" +
                    "                      <option value=3>未用</option>" +
                    "                      <option value=4>在审</option>" +
                    "                      <option value=5>退稿</option>";
            else if (status==2)
                option_str ="                      <option value=0>请选择</option>" +
                    "                      <option value=1>新稿</option>" +
                    "                      <option value=2 selected>已发布</option>" +
                    "                      <option value=3>未用</option>" +
                    "                      <option value=4>在审</option>" +
                    "                      <option value=5>退稿</option>";
            else if (status==3)
                option_str ="                      <option value=0>请选择</option>" +
                    "                      <option value=1>新稿</option>" +
                    "                      <option value=2>已发布</option>" +
                    "                      <option value=3 selected>未用</option>" +
                    "                      <option value=4>在审</option>" +
                    "                      <option value=5>退稿</option>";
            else if (status==4)
                option_str ="                      <option value=0>请选择</option>" +
                    "                      <option value=1>新稿</option>" +
                    "                      <option value=2>已发布</option>" +
                    "                      <option value=3>未用</option>" +
                    "                      <option value=4 selected>在审</option>" +
                    "                      <option value=5>退稿</option>";
            else if (status==5)
                option_str ="                      <option value=0>请选择</option>" +
                    "                      <option value=1>新稿</option>" +
                    "                      <option value=2>已发布</option>" +
                    "                      <option value=3>未用</option>" +
                    "                      <option value=4>在审</option>" +
                    "                      <option value=5 selected>退稿</option>";

            var htmldata = "<table width=\"100%\" border=\"0\" cellspacing=\"10\" cellpadding=\"0\">\n";
            htmldata = htmldata + "    <tr>\n" +
                "        <td>\n" +
                "            <div class=\"location\" style='padding-bottom: 5px;'>\n" +
                "                <div>文章管理 >" + jsondata.COLUMNNAME + "</div>\n" +
                "                <div style='padding-top: 10px;padding-bottom: 10px;'>" +
                "              <form name=\"searchForm\">" +
                //"               <input type=\"hidden\" name=\"doSearch\" value=true>" +
                "              <input type=\"hidden\" name=\"column\" value=\"" + columnid + "\">" +
                "              文章ID：<input type=\"text\" name=\"articleid\" id=\"artid\" value=\"" + articleid + "\" class=\"sear_txt_1\"/>" +
                "              标题：<input type=\"text\" name=\"title\" id=\"titleid\" value=\"" + title + "\" class=\"sear_txt_1\"/>" +
                "              副标题：<input type=\"text\" name=\"vicetitle\" id=\"vicetitleid\" value=\"" + vicetitle + "\" class=\"sear_txt_1\"/>" +
                "              作者：<input type=\"text\" name=\"author\" id=\"authorid\" value=\"" + author + "\" class=\"sear_txt_1\"/>" +
                "              来源：<input type=\"text\" name=\"source\" id=\"sourceid\" value=\"" + source + "\" class=\"sear_txt_1\"/>" +
                "              发布日期：<input type=\"text\" name=\"spubdate\" id=\"spubdateid\" class=\"sear_txt_1\" />到" +
                "               <input type=\"text\" name=\"epubdate\" id=\"epubdateid\" class=\"sear_txt_1\" />" +
                "              状态：<select name=\"status\" id=\"statusid\" class=\"sear_txt_1\">" + option_str +
                "                   </select>" +
                "                 <input type=\"button\" name=\"search\" id=\"searchid\" value=\"搜索\" onclick=\"javascript:articlesInfoList("+columnid + "," + currentPage + ",true," + flag +"," + ascdesc + ");\" class=\"sear_btn\"/>" +
                "              </form>" +
                "            </div>"+
                "                <div class=\"button_top\" style='float: left;padding-right: 50px;'>\n" +
                "                    <input name=\"useArticleType\" type=\"radio\" value=\"0\" checked /><font style=\"font-size:9pt\">引用文章链接</font>&nbsp;\n" +
                "                    <input name=\"useArticleType\" type=\"radio\" value=\"1\"/><font style=\"font-size:9pt\">引用文章内容</font>\n" +
                "                </div>\n" +
                "                <div class=\"button_top\">\n" +
                "                    <input name=\"\" type=\"button\" value=\"引用\" class=\"button_bg_1\" onclick='javascript:doReferArticle(" + range + "," + currentPage + "," + doSearchFlag + "," + flag + "," + ascdesc + ")'/>\n" +
                "                    <input name=\"\" type=\"button\" value=\"取消\" class=\"button_bg_1\" onclick='javascript:window.close();'/>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </td>\n" +
                "    </tr>\n";
            htmldata = htmldata + "    <tr>\n" +
                "        <td align=\"left\" valign=\"top\">\n" +
                "          <form name=form1>" +
                "            <table id=\"articleDataGrid\" width=\"100%\" border=\"1\" cellspacing=\"1\" cellpadding=\"0\" bgcolor=\"#CCCCCC\" class=\"tab_list\">\n" +
                "                <tr>\n" +
                "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><input type=\"checkbox\" name=\"allbox\" id=\"allboxid\" onclick='javascript:CA();' />选择</td>\n" +
                "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">状态</td>\n" +
                "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">RSS</td>\n" +
                "                    <td width=\"300\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag +  ",1," + ascDescFlag + ");\">文章标题</a></td>\n" +
                "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",4," + ascDescFlag + ");\">发布时间</a></td>\n" +
                "                    <td width=\"120\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",5," + ascDescFlag + ");\">修改时间</a></td>\n" +
                "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",2," + ascDescFlag + ");\">主权重</a></td>\n" +
                "                    <td width=\"40\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\"><a href=\"javascript:articlesInfoList("+columnid + "," +currentPage + "," + searchflag + ",3," + ascDescFlag + ");\">次权重</a></td>\n" +
                "                    <td width=\"80\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">文章编辑</td>\n" +
                "                    <td width=\"30\" align=\"center\" valign=\"middle\" bgcolor=\"#f4f4f4\">预览</td>\n" +
                "                </tr>\n";
            for(var ii=0;ii<jsondata.ARTICLES.length;ii++) {
                var maintitle = jsondata.ARTICLES[ii].MAINTITLE;
                var articleColumnID = jsondata.ARTICLES[ii].COLUMNID;
                var editor = (typeof(jsondata.ARTICLES[ii].EDITOR)!="undefined")?jsondata.ARTICLES[ii].EDITOR:"";
                var createdate = (typeof(jsondata.ARTICLES[ii].CREATEDATE)!="undefined")?jsondata.ARTICLES[ii].CREATEDATE:"";
                var publishtime = (typeof(jsondata.ARTICLES[ii].PUBLISHTIME)!="undefined")?jsondata.ARTICLES[ii].PUBLISHTIME:"";
                var lastupdate = (typeof(jsondata.ARTICLES[ii].LASTUPDATED)!="undefined")?jsondata.ARTICLES[ii].LASTUPDATED:"";
                var main_weight = jsondata.ARTICLES[ii].DOCLEVEL;
                var vice_weight = jsondata.ARTICLES[ii].VICEDOCLEVEL;
                var emptyflag = jsondata.ARTICLES[ii].EMPTYCONTENTFLAG;
                var status = jsondata.ARTICLES[ii].STATUS;
                var lockflag = jsondata.ARTICLES[ii].LOCKSTATUS;
                var pubflag = jsondata.ARTICLES[ii].PUBFLAG;
                var auditflag = jsondata.ARTICLES[ii].AUDITFLAG;
                var mediatype = jsondata.ARTICLES[ii].MULTIMEDIATYPE;
                var isrssflag = jsondata.ARTICLES[ii].ISJOINRSS;
                var isown = true;
                var referID = jsondata.ARTICLES[ii].REFERID;
                var refersFrom = jsondata.ARTICLES[ii].RefersFrom;
                var refersTo = jsondata.ARTICLES[ii].RefersTo;
                var useArticleType = jsondata.ARTICLES[ii].UseArticleType
                var status_str = null;
                var rownum = ii;

                articleIds = articleIds + jsondata.ARTICLES[ii].ID + ",";

                var filename = jsondata.ARTICLES[ii].FILENAME;
                var ext = null;
                if (filename != null) {
                    var posi = filename.indexOf(".");
                    if(posi>-1) {
                        ext = filename.substring(posi+1);
                        ext = ext.toLowerCase();
                    }
                }
                if (ext != null) {
                    if (ext=="pdf")
                        ext = "pdf.gif";
                    else if (ext=="doc")
                        ext = "word.gif";
                    else if (ext=="xls")
                        ext = "excel.gif";
                    else if (ext=="zip" || ext=="rar" || ext=="tar")
                        ext = "zip.gif";
                    else if (ext=="gif")
                        ext = "gif.gif";
                    else if (ext=="jpg" || ext=="jpeg")
                        ext = "jpg.gif";
                    else if (ext=="html" || ext=="htm" || ext=="shtml" || ext=="jsp" || ext=="asp" || ext=="php" || ext=="aspx")
                        ext = "htm.gif";
                    else if (ext=="ppt")
                        ext = "ppt.gif";
                    else if (ext=="png" || ext=="bmp")
                        ext = "tu.gif";
                    else
                        ext = "wen.gif";
                } else {
                    ext = "wen.gif";
                }

                if (emptyflag == 0) {
                    if (status == 0) {
                        status_str = "<td align=center><img src=\"../images/button/wei.gif\" alt=\"未使用文章\"></td>";
                    } else if (status == 1 || status == 5 || status == 4 || status == 6) {
                        if (auditflag == 0)
                            switch (pubflag) {
                                case 0:
                                    status_str = "<td align=center><img src=\"../images/button/yi.gif\" border=0 alt=\"已发布\"></td>";
                                    break;
                                case 1:
                                    if(mediatype == 1){
                                        status_str = "<td align=center><img src=\"../images/button/dmt.gif\" border=0 alt=\"多媒体\"></td>";
                                    }else{
                                        status_str = "<td align=center><img src=\"../images/button/xin.gif\" border=0 alt=\"新稿\"></td>";
                                    }
                                    break;
                                case 2:
                                    status_str = "<td align=center><font color=red>发布中</font></td>";
                                    break;
                                default:
                                    break;
                            }
                        else if (auditflag == 1)
                            status_str = "<td align=center><img src=\"../images/button/shen.gif\" border=0 alt=\"在审\"></td>";
                        else
                            status_str = "<td align=center><img src=\"../images/button/tui.gif\" border=0 alt=\"退稿\"></td>";
                    } else if(status == 3){      //多媒体文件等待转换状态
                        status_str = "<td align=center><img src=\"../images/button/dai.gif\" border=0 alt=\"等待\"></td>";
                    }  else {                                  //2是归档状态
                        status_str = "<td align=center><img src=\"../images/button/dang.gif\" border=0 alt=\"存档\"></td>";
                    }
                } else {
                    status_str = "<td align=center><img src=\"../images/button/" + ext + "\" border=0 alt=\"上传文件\"></td>";
                }

                var rss_val = "";
                if (isrssflag===1) rss_val = "R";

                htmldata = htmldata + "                <tr bgcolor=\"#FFFFFF\" id=\"row" + jsondata.ARTICLES[ii].ID + "\">\n";
                htmldata = htmldata + "<td align=\"center\"><input type=\"checkbox\" name=\"article\" id=\"ckarticle\"" + jsondata.ARTICLES[ii].ID + " value=\"" + jsondata.ARTICLES[ii].ID  + "\"></td>";
                htmldata = htmldata + status_str;
                htmldata = htmldata + "<td>" + rss_val + "</td>";
                htmldata = htmldata + "<td>" + maintitle + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + publishtime + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + lastupdate + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + main_weight + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\">" + vice_weight + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\" >" + editor + "</td>";
                htmldata = htmldata + "<td align=\"center\" valign=\"middle\"><a href=\"javascript:Preview("+jsondata.ARTICLES[ii].ID + "," + columnid + ");\"><img src=\"../images/view.png\" width=\"23\" height=\"23\" /></a></td>";
                htmldata = htmldata + "</tr>";
            }
            if (articleIds.length>0) articleIds = articleIds.substring(0,articleIds.length-1);
            htmldata = htmldata + "            </table>\n";
            htmldata = htmldata + "           <input type=\"hidden\" name=\"allArticleIds\" value=\"" + articleIds + "\">";
            htmldata = htmldata + "         </form>";
            htmldata = htmldata + "        </td>\n" +
                "    </tr>\n" +
                "</table>";

            htmldata = htmldata + "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
            htmldata = htmldata + "    <tr>";
            htmldata = htmldata + "    <td align='right'>" + Pagination(jsondata.TOTALCOUNT,columnid,totalPage,currentPage,10,searchflag,jsondata.FLAG,jsondata.ASCDESC)+ "</td>";
            htmldata = htmldata + "    </tr>";
            htmldata = htmldata + "</table>";

            $("#contentid").html(htmldata);
            //$("#listtail").css('display','block');
            //$("#totalid").html(jsondata.TOTALCOUNT);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert(jqXHR.responseText);
            alert(jqXHR.status);
            alert(jqXHR.readyState);
            alert(jqXHR.statusText);
            alert(textStatus);
            alert(errorThrown);
        }
    });
}
