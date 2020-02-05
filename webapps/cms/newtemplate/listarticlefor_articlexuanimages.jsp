<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="java.util.regex.Pattern" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    int modeltype = ParamUtil.getIntParameter(request, "modeltype", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int h = ParamUtil.getIntParameter(request, "height", 0);
    int w = ParamUtil.getIntParameter(request, "width", 0);
    int ph = ParamUtil.getIntParameter(request, "picheight", 0);
    int pw = ParamUtil.getIntParameter(request, "picwidth", 0);
    String msg = ParamUtil.getParameter(request, "msg");
    String doaction = ParamUtil.getParameter(request, "doaction");
    String keyword = ParamUtil.getParameter(request, "keyword");
    String s_h = null;
    String s_w = null;
    String s_ph = null;
    String s_pw = null;
    int total = 0;
    int articleCount = 0;
    List articleList = null;
    String CName = null;
    Column column = null;
    String dirname = null;
    String content = null;
    int innerFlag = 0;
    int pic_texiao_type = ParamUtil.getIntParameter(request,"pictxtype",2);
    int scrolltitle = ParamUtil.getIntParameter(request,"scrolltitle",0);
    System.out.println("scrolltitle=" + scrolltitle);

    String notes = "";
    String cname = "文章图片特效";
    String extname = "";
    mark mark1 = null;
    StringBuffer sb = new StringBuffer();
    String artids = "";

    IColumnManager columnManager = ColumnPeer.getInstance();
    IMarkManager markMgr = markPeer.getInstance();
    IArticleManager articleMgr = ArticlePeer.getInstance();
    IPublishManager pubMgr = PublishPeer.getInstance();

    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        if (column != null) {
            dirname = column.getDirName();
            extname = column.getExtname();
        }
    } else {
        dirname = "/_prog/";
    }

    if (doaction != null) {
        if (doaction.equals("搜索")) {
            System.out.println("执行搜索");
        } else if (doaction.equals("确定")){
            markID = ParamUtil.getIntParameter(request, "mark", 0);
            boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
            pic_texiao_type = ParamUtil.getIntParameter(request,"pictxtype",2);
            cname = ParamUtil.getParameter(request, "chineseName");
            notes = ParamUtil.getParameter(request, "notes");
            innerFlag = ParamUtil.getIntParameter(request, "innerFlag", 0);
            int listType = ParamUtil.getIntParameter(request, "listType", 0);
            int orgmarkID = markID;

            String viewer = request.getHeader("user-agent");
            String markname = "";
            markname = "图片特效";

            if (pic_texiao_type == 0) {
                System.out.println("竖向上滚动" + pic_texiao_type);
                if (h==0) h = 180;
                if (w==0) w = 60;
                markname = "图片上滚";
                sb.append("<div id=biz_scrollup_pic[%%markid%%]_0 style=\"overflow:hidden;height:" + h + "px ;width:" + w + "px ;background:#ffffff;color:#ffffff\">\r\n");
                sb.append("<div id=biz_scrollup_pic[%%markid%%]_1>\r\n");
                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrollup_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]120[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][CONTENT]");
                sb.append("<SCRIPT language=javascript>\r\n");
                sb.append("var speed=30;\r\n");
                sb.append("biz_scrollup_pic[%%markid%%]_2.innerHTML=biz_scrollup_pic[%%markid%%]_1.innerHTML;\r\n");
                sb.append("function Marquee(){\r\n");
                sb.append("if(biz_scrollup_pic[%%markid%%]_2.offsetTop-biz_scrollup_pic[%%markid%%]_0.scrollTop<=0)\r\n");
                sb.append("biz_scrollup_pic[%%markid%%]_0.scrollTop-=biz_scrollup_pic[%%markid%%]_1.offsetHeight;\r\n");
                sb.append("else{\r\n");
                sb.append("biz_scrollup_pic[%%markid%%]_0.scrollTop++;\r\n");
                sb.append("}");
                sb.append("}");
                sb.append("var MyMar=setInterval(Marquee,speed);\r\n");
                sb.append("biz_scrollup_pic[%%markid%%]_0.onmouseover=function() {clearInterval(MyMar)}\r\n");
                sb.append("biz_scrollup_pic[%%markid%%]_0.onmouseout=function() {MyMar=setInterval(Marquee,speed)}\r\n");
                sb.append("</script>\r\n[/CONTENT][/HTMLCODE][/TAG]");

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(120);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 1) {
                System.out.println("竖向下滚动" + pic_texiao_type);
                if (h==0) h = 180;
                if (w==0) w = 60;
                markname = "图片下滚";
                sb.append("<div id=biz_scrolldown_pic[%%markid%%]_0 style=\"overflow:hidden;height:" + h + "px ;width:" + w + "px ;background:#214984;color:#ffffff\">\r\n");
                sb.append("<div id=biz_scrolldown_pic[%%markid%%]_1>\r\n");
                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrolldown_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]121[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][CONTENT]");
                sb.append("<SCRIPT language=javascript>\r\n");
                sb.append("var speed=30;\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_2.innerHTML=biz_scrolldown_pic[%%markid%%]_1.innerHTML;\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_0.scrollTop=biz_scrolldown_pic[%%markid%%]_0.scrollHeight;\r\n");
                sb.append("function Marquee(){\r\n");
                sb.append("if(biz_scrolldown_pic[%%markid%%]_1.offsetTop-biz_scrolldown_pic[%%markid%%]_0.scrollTop>=0)\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_0.scrollTop+=biz_scrolldown_pic[%%markid%%]_2.offsetHeight;\r\n");
                sb.append("else{\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_0.scrollTop--;\r\n");
                sb.append("}\r\n");
                sb.append("}\r\n");
                sb.append("var MyMar=setInterval(Marquee,speed);\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_0.onmouseover=function() {clearInterval(MyMar)}\r\n");
                sb.append("biz_scrolldown_pic[%%markid%%]_0.onmouseout=function() {MyMar=setInterval(Marquee,speed)}\r\n");
                sb.append("</script>\r\n[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(121);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 2) {
                System.out.println("横向右滚动" + pic_texiao_type);
                if (h==0) h = 60;
                if (w==0) w = 560;
                markname = "图片右滚";
                sb.append("<div id=\"biz_scrollleft_pic[%%markid%%]_0\" style=\"OVERFLOW:hidden; WIDTH:" + w + "px; " + " height:" + h +"px; COLOR:#ffffff\" align=\"center\">\r\n");
                sb.append("<table cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\">\r\n");
                sb.append("<tr><td id=\"biz_scrollleft_pic[%%markid%%]_1\"><table><tr>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]122[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][CONTENT]");
                sb.append("[%%ARTICLE_LIST%%]</tr></table></td><td id=\"biz_scrollleft_pic[%%markid%%]_2\">&nbsp;</td></tr></table></div>\r\n");
                sb.append("<SCRIPT language=javascript>\r\n");
                sb.append("var speed=30;\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_2.innerHTML=biz_scrollleft_pic[%%markid%%]_1.innerHTML;\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_0.scrollLeft=biz_scrollleft_pic[%%markid%%]_0.scrollWidth;\r\n");
                sb.append("function Marquee(){\r\n");
                sb.append("if(biz_scrollleft_pic[%%markid%%]_0.scrollLeft<=0)\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_0.scrollLeft+=biz_scrollleft_pic[%%markid%%]_2.offsetWidth;\r\n");
                sb.append("else{\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_0.scrollLeft--;\r\n");
                sb.append("}\r\n");
                sb.append("}\r\n");
                sb.append("var MyMar=setInterval(Marquee,speed);\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_0.onmouseover=function() {clearInterval(MyMar)}\r\n");
                sb.append("biz_scrollleft_pic[%%markid%%]_0.onmouseout=function() {MyMar=setInterval(Marquee,speed)}\r\n");
                sb.append("</script>\r\n[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(122);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 3) {
                System.out.println("横向左滚动" + pic_texiao_type);
                if (h==0) h = 60;
                if (w==0) w = 560;
                markname = "图片左滚";
                sb.append("<div id=\"biz_scrollright_pic[%%markid%%]_0\" style=\"OVERFLOW:hidden; WIDTH:" + w + "px; " + " height:" + h +"px; COLOR:#ffffff\" align=\"center\">\r\n");
                sb.append("<table cellspacing=\"0\" cellpadding=\"0\" align=\"center\" border=\"0\">\r\n");
                sb.append("<tr><td id=\"biz_scrollright_pic[%%markid%%]_1\"><table><tr>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]123[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][CONTENT]");
                sb.append("[%%ARTICLE_LIST%%]</tr></table></td><td id=\"biz_scrollright_pic[%%markid%%]_2\">&nbsp;</td></tr></table></div>\r\n");
                sb.append("<SCRIPT language=javascript>\r\n");
                sb.append("var speed=30;\r\n");
                sb.append("biz_scrollright_pic[%%markid%%]_2.innerHTML=biz_scrollright_pic[%%markid%%]_1.innerHTML;\r\n");
                sb.append("function Marquee(){\r\n");
                sb.append("if(biz_scrollright_pic[%%markid%%]_2.offsetWidth-biz_scrollright_pic[%%markid%%]_0.scrollLeft<=0)\r\n");
                sb.append("  biz_scrollright_pic[%%markid%%]_0.scrollLeft-=biz_scrollright_pic[%%markid%%]_1.offsetWidth;\r\n");
                sb.append("else{\r\n");
                sb.append("  biz_scrollright_pic[%%markid%%]_0.scrollLeft++;\r\n");
                sb.append("}\r\n");
                sb.append("}\r\n");
                sb.append("var MyMar=setInterval(Marquee,speed);\r\n");
                sb.append("biz_scrollright_pic[%%markid%%]_0.onmouseover=function() {clearInterval(MyMar)}\r\n");
                sb.append("biz_scrollright_pic[%%markid%%]_0.onmouseout=function() {MyMar=setInterval(Marquee,speed)}\r\n");
                sb.append("</script>\r\n[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(123);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if(pic_texiao_type == 4){
                if (h==0) h = 200;
                if (w==0) w = 200;
                markname = "图片幻灯";
                content = "[TAG][HTMLCODE][MARKTYPE]124[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][CONTENT]<script type=\"text/javascript\">\r\n" +
                        "var focus_width=" + w + ";\r\n" +
                        "var focus_height=" + h + ";\r\n" +
                        "var text_height=18;\r\n" +
                        "var swf_height = focus_height+text_height;\r\n" +
                        "var pics='[%%pics%%]" + "';\r\n" +
                        "var links='[%%links%%]"  + "';\r\n" +
                        "var texts='[%%texts%%]" + "';\r\n" +
                        "document.write('<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"'+ focus_width +'\" height=\"'+ swf_height +'\">');\r\n" +
                        "document.write('<param name=\"allowScriptAccess\" value=\"sameDomain\"><param name=\"movie\" value=\"/_sys_js/focus1.swf\">'\r\n);" +
                        "document.write('<param name=\"quality\" value=\"high\"><param name=\"bgcolor\" value=\"#F0F0F0\">');\r\n" +
                        "document.write('<param name=\"menu\" value=\"false\"><param name=wmode value=\"opaque\">');\r\n" +
                        "document.write('<param name=\"FlashVars\" value=\"pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'\">');\r\n" +
                        "document.write('</object>');\r\n" +
                        "</script>\r\n[/CONTENT][/HTMLCODE][/TAG]";

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(content);
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(124);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);
            }else if(pic_texiao_type == 5){
                if (h==0) h = 180;
                if (w==0) w = 120;
                markname = "自动轮换显示";
                content = "[TAG][HTMLCODE][MARKTYPE]125[/MARKTYPE][CONTENT]\r\n" +
                          "<div id=\"biz_auto_pic[%%markid%%]_0\" style=\"OVERFLOW:hidden; WIDTH:" + w + "px; " + " height:" + h +"px; COLOR:#ffffff\" align=\"center\">\r\n"+
                          "<table><tr> \r\n" +
                          "<td width=\"159\" valign=\"middle\"> \r\n" +
                          "<img src=\"\" name=\"bannerADrotator\" width=\""+ pw+"\" height=\""+ph+"\" style=\"FILTER: revealTrans(duration=2,transition=20);\"> \n" +
                          "<script language=\"JavaScript\" type=\"text/javascript\"> \r\n" +
                          "var bannerAD=new Array(); \r\n" +
                          "var adNum=0; \r\n" +
                          "\r\n" +
                          "[%%bannerAD%%] \r\n" +
                          "\r\n" +
                          "var preloadedimages=new Array(); \r\n" +
                          "for (i=1;i<bannerAD.length;i++) { \r\n" +
                          "\tpreloadedimages[i]=new Image(); \r\n" +
                          "\tpreloadedimages[i].src=bannerAD[i]; \r\n" +
                          "}\r\n" +
                          "function  nextAd(){\r\n" +
                          "if (adNum<bannerAD.length-1) { \r\n" +
                          "\tadNum++; \r\n" +
                          "} else { \r\n" +
                          "\tadNum=0; \r\n" +
                          "} \r\n" +
                          "if (document.all) { \r\n" +
                          "\tbannerADrotator.filters.revealTrans.Transition=Math.floor(Math.random()*23); \r\n" +
                          "\tbannerADrotator.filters.revealTrans.apply(); \r\n" +
                          "} \r\n" +
                          "\r\n" +
                          "document.images.bannerADrotator.src=bannerAD[adNum]; \r\n" +
                          "if (document.all) { \r\n" +
                          "bannerADrotator.filters.revealTrans.play(); \r\n" +
                          "} \r\n" +
                          "\r\n" +
                          "theTimer=setTimeout(\"nextAd()\", 5000); \r\n" +
                          "}\r\n" +
                          "nextAd();\r\n" +
                          "</script></td></tr></table></div>[/CONTENT][/HTMLCODE][/TAG]";

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(content);
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(125);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);
            }else if(pic_texiao_type == 6){
                if (h==0) h = 500;
                if (w==0) w = 1000;
                markname = "图片相册";

                content = "[TAG][HTMLCODE][MARKTYPE]126[/MARKTYPE][CONTENT]\r\n" +
                          "<div id=\"biz_photo_pic[%%markid%%]_0\" style=\"OVERFLOW:hidden; WIDTH:" + w + "px; " + " height:" + h +"px; COLOR:#ffffff\" align=\"center\">\r\n"+
                          "<script type=\"text/javascript\">\n" +
"   \tfunction selectimg(imgid){\n" +
"   \t\tvar imgflag = imgid.substring(3);\n" +
"   \t\tvar imgid = \"img\"+imgflag;\n" +
"   \t\t\n" +
"   \t\tdocument.getElementById(\"imgflag\").value=imgflag;\n" +
"   \t\tvar lastdisplayflag = lastimgexist();\n" +
"   \t\tvar nextdisplayflag = nextimgexist();\n" +
"   \t\t\n" +
"   \t\t\n" +
"   \t\tvar srcpath = document.getElementById(imgid).src;\n" +
"   \t\tvar t = \"<table align=\\\"center\\\" height=\\\"90%\\\" width=\\\"90%\\\">\"+\n" +
"\t\t\t\t\"\t<tr>\"+\n" +
"\t\t\t\t\"\t\t<td align=\\\"center\\\">\"+\n" +
"\t\t\t\t\"\t\t<table width=\\\"90%\\\"><tr>\"+\n" +
"\t\t\t\t\"       <td width=\\\"25%\\\" align=\\\"right\\\"><div id=\\\"lastimghidden\\\" style=\\\"cursor: hand;display:\"+lastdisplayflag+\"\\\"><img src=\\\"/_sys_images/daleft.gif\\\" onclick=\\\"lastimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"40%\\\" align=\\\"center\\\"><img id=\\\"\"+imgid+\"\\\" style=\\\"cursor: hand\\\" src=\\\"\"+srcpath+\"\\\" height=\\\"300\\\" width=\\\"200\\\"></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"25%\\\" align=\\\"left\\\"><div id=\\\"nextimghidden\\\" style=\\\"cursor: hand;display:\"+nextdisplayflag+\"\\\"><img src=\\\"/_sys_images/daright.gif\\\" onclick=\\\"nextimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t</tr></table>\"+\n" +
"\t\t\t\t\"\t\t</td>\"+\n" +
"\t\t\t\t\"\t</tr>\"+\n" +
"\t\t\t\t\"</table>\";\n" +
"   \t\tdocument.getElementById(\"bigimgplay\").innerHTML = t;\n" +
"   \t\t\n" +
"   \t}\n" +
"   \tfunction lastimg(){\n" +
"   \t\tvar imgflag = document.getElementById(\"imgflag\").value;\n" +
"   \t\timgflag--;\n" +
"   \t\tvar imgid = \"img\"+imgflag;\n" +
"   \t\t\n" +
"   \t\tdocument.getElementById(\"imgflag\").value=imgflag;\n" +
"   \t\t\n" +
"   \t\tvar obj = document.getElementById(imgid);\n" +
"   \t\tvar srcpath = document.getElementById(imgid).src;\n" +
"   \t\tvar displayflag = lastimgexist();\n" +
"   \t\tvar t = \"<table align=\\\"center\\\" height=\\\"90%\\\" width=\\\"90%\\\">\"+\n" +
"\t\t\t\t\"\t<tr>\"+\n" +
"\t\t\t\t\"\t\t<td align=\\\"center\\\">\"+\n" +
"\t\t\t\t\"\t\t<table width=\\\"90%\\\"><tr>\"+\n" +
"\t\t\t\t\"       <td width=\\\"25%\\\" align=\\\"right\\\"><div id=\\\"lastimghidden\\\" style=\\\"cursor: hand;display:\"+displayflag+\"\\\"><img src=\\\"/_sys_images/daleft.gif\\\" onclick=\\\"lastimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"40%\\\" align=\\\"center\\\"><img id=\\\"\"+imgid+\"\\\" style=\\\"cursor: hand\\\" src=\\\"\"+srcpath+\"\\\" height=\\\"300\\\" width=\\\"200\\\"></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"25%\\\" align=\\\"left\\\"><div id=\\\"nextimghidden\\\"><img src=\\\"/images/daright.gif\\\" style=\\\"cursor: hand\\\" onclick=\\\"nextimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t</tr></table>\"+\n" +
"\t\t\t\t\"\t\t</td>\"+\n" +
"\t\t\t\t\"\t</tr>\"+\n" +
"\t\t\t\t\"</table>\";\n" +
"   \t\tdocument.getElementById(\"bigimgplay\").innerHTML = t;\n" +
"   \t\t\n" +
"   \t}\n" +
"   \t\n" +
"   \tfunction nextimg(){\n" +
"   \t\tvar imgflag = document.getElementById(\"imgflag\").value;\n" +
"   \t\timgflag++;\n" +
"   \t\tvar imgid = \"img\"+imgflag;\n" +
"   \t\tdocument.getElementById(\"imgflag\").value=imgflag;\n" +
"   \t\t\n" +
"   \t\tvar obj = document.getElementById(imgid);\n" +
"   \t\t\n" +
"   \t\tvar srcpath = document.getElementById(imgid).src;\n" +
"   \t\tvar displayflag = nextimgexist();\n" +
"   \t\tvar t = \"<table align=\\\"center\\\" height=\\\"90%\\\" width=\\\"90%\\\">\"+\n" +
"\t\t\t\t\"\t<tr>\"+\n" +
"\t\t\t\t\"\t\t<td align=\\\"center\\\">\"+\n" +
"\t\t\t\t\"\t\t<table width=\\\"90%\\\"><tr>\"+\n" +
"\t\t\t\t\"       <td width=\\\"25%\\\" align=\\\"right\\\"><div id=\\\"lastimghidden\\\"><img src=\\\"/images/daleft.gif\\\" style=\\\"cursor: hand\\\" onclick=\\\"lastimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"40%\\\" align=\\\"center\\\"><img id=\\\"\"+imgid+\"\\\" style=\\\"cursor: hand\\\" src=\\\"\"+srcpath+\"\\\" height=\\\"300\\\" width=\\\"200\\\"></td>\"+\n" +
"\t\t\t\t\"\t\t<td width=\\\"25%\\\" align=\\\"left\\\"><div id=\\\"nextimghidden\\\" style=\\\"cursor: hand;display:\"+displayflag+\"\\\"><img src=\\\"/images/daright.gif\\\" onclick=\\\"nextimg()\\\"></div></td>\"+\n" +
"\t\t\t\t\"\t\t</tr></table>\"+\n" +
"\t\t\t\t\"\t\t</td>\"+\n" +
"\t\t\t\t\"\t</tr>\"+\n" +
"\t\t\t\t\"</table>\";\n" +
"   \t\tdocument.getElementById(\"bigimgplay\").innerHTML = t;\n" +
"   \t}\n" +
"   \t\n" +
"   \tfunction nextimgexist(){\n" +
"   \t\tvar imgflag = document.getElementById(\"imgflag\").value;\n" +
"   \t\timgflag++;\n" +
"   \t\tvar imgid = \"img\"+imgflag;\n" +
"   \t\tvar obj = document.getElementById(imgid);\n" +
"   \t\tif(obj==null){\n" +
"   \t\t\treturn \"none\";\n" +
"   \t\t}else{\n" +
"   \t\t\treturn \"\";\n" +
"   \t\t}\n" +
"   \t}\n" +
"   \tfunction lastimgexist(){\n" +
"   \t\tvar imgflag = document.getElementById(\"imgflag\").value;\n" +
"   \t\timgflag--;\n" +
"   \t\tvar imgid = \"img\"+imgflag;\n" +
"   \t\tvar obj = document.getElementById(imgid);\n" +
"   \t\tif(obj==null){\n" +
"   \t\t\treturn \"none\";\n" +
"   \t\t}else{\n" +
"   \t\t\treturn \"\";\n" +
"   \t\t}\n" +
"   \t}\n" +
"   \t\n" +
"   \tfunction smallimgplay(){\n" +
"   \t\tvar max = parseInt(document.getElementById(\"smallimgmaxflag\").value);\n" +
"   \t\tvar min = parseInt(document.getElementById(\"smallimgminflag\").value);\n" +
"   \t\tvar length = parseInt(document.getElementById(\"imglength\").value);\n" +
"   \t\tvar srcpath = \"\";\n" +
"   \t\tvar t = \"\";\n" +
"   \t\tfor(var i=min;i<=max;i++){\n" +
"   \t\t\tsrcpath = document.getElementById(\"img\"+i).src;\n" +
"   \t\t\tt = t + \"<img id=\\\"img\"+i+\"\\\" style=\\\"cursor: hand\\\" src=\\\"\"+srcpath+\"\\\" onclick=\\\"selectimg('img\"+i+\"')\\\">&nbsp;&nbsp;&nbsp;\\r\\n\";\n" +
"   \t\t}\n" +
"   \t\tfor(var i=1;i<=length;i++){\n" +
"   \t\t\tsrcpath = document.getElementById(\"img\"+i).src;\n" +
"   \t\t\tif(i<min||i>max){\n" +
"   \t\t\t\tt = t + \"<img id=\\\"img\"+i+\"\\\" style=\\\"cursor: hand\\\" style=\\\"display:none\\\" src=\\\"\"+srcpath+\"\\\" onclick=\\\"selectimg('img\"+i+\"')\\\">&nbsp;&nbsp;&nbsp;\\r\\n\";\n" +
"   \t\t\t}\n" +
"   \t\t}\n" +
"   \t\tdocument.getElementById(\"showsmall\").innerHTML = t;\n" +
"   \t\t\n" +
"   \t\tif(min==1){\n" +
"   \t\t\tdocument.getElementById(\"lastsmall\").style.display = \"none\";\n" +
"   \t\t}else{\n" +
"   \t\t\tdocument.getElementById(\"lastsmall\").style.display = \"\";\n" +
"   \t\t}\n" +
"   \t\tif(max==length){\n" +
"   \t\t\tdocument.getElementById(\"nextsmall\").style.display = \"none\";\n" +
"   \t\t}else{\n" +
"   \t\t\tdocument.getElementById(\"nextsmall\").style.display = \"\";\n" +
"   \t\t}\n" +
"   \t}\n" +
"   \t\n" +
"   \tfunction lastsmallplay(){\n" +
"   \t\tvar max = parseInt(document.getElementById(\"smallimgmaxflag\").value);\n" +
"   \t\tvar min = parseInt(document.getElementById(\"smallimgminflag\").value);\n" +
"   \t\tvar length = parseInt(document.getElementById(\"imglength\").value);\n" +
"   \t\tvar scrolllength = parseInt(document.getElementById(\"imgscrolllength\").value);\n" +
"   \t\tif(min-scrolllength<0){\n" +
"   \t\t\tmin = 1;\n" +
"   \t\t\tmax = 4;\n" +
"   \t\t}else{\n" +
"   \t\t\tmin = min - scrolllength;\n" +
"   \t\t\tif(max==length){\n" +
"   \t\t\t\tmax = min + (scrolllength-1);\n" +
"   \t\t\t}else{\n" +
"   \t\t\t\tmax = max - scrolllength;\n" +
"   \t\t\t}\n" +
"   \t\t}\n" +
"   \t\tdocument.getElementById(\"smallimgminflag\").value = min;\n" +
"   \t\tdocument.getElementById(\"smallimgmaxflag\").value = max;\n" +
"   \t\tsmallimgplay();\n" +
"   \t}\n" +
"   \t\n" +
"   \tfunction nextsmallplay(){\n" +
"   \t\tvar max = parseInt(document.getElementById(\"smallimgmaxflag\").value);\n" +
"   \t\tvar min = parseInt(document.getElementById(\"smallimgminflag\").value);\n" +
"   \t\tvar length = parseInt(document.getElementById(\"imglength\").value);\n" +
"   \t\tvar scrolllength = parseInt(document.getElementById(\"imgscrolllength\").value);\n" +
"   \t\tif(max<length&&max+scrolllength<length){\n" +
"   \t\t\tmin = min + scrolllength;\n" +
"   \t\t\tmax = max + scrolllength;\n" +
"   \t\t}else if(max<length&&max+scrolllength>length){\n" +
"   \t\t\tmin = min + scrolllength;\n" +
"   \t\t\tmax = length;\n" +
"   \t\t}else if(max<length&&max+scrolllength==length){\n" +
"   \t\t\tmin = min + scrolllength;\n" +
"   \t\t\tmax = length;\n" +
"   \t\t}\n" +
"   \t\tdocument.getElementById(\"smallimgminflag\").value = min;\n" +
"   \t\tdocument.getElementById(\"smallimgmaxflag\").value = max;\n" +
"   \t\tsmallimgplay();\n" +
"   \t}\n" +
"    </script>"+
"<input type=\"hidden\" id=\"imgflag\" name=\"imgflag\" value=\"1\">\n" +
"  <input type=\"hidden\" id=\"smallimgmaxflag\" name=\"smallimgflag\" value=\"5\">\n" +
"  <input type=\"hidden\" id=\"smallimgminflag\" name=\"smallimgflag\" value=\"1\">\n" +
"  <input type=\"hidden\" id=\"imglength\" name=\"imglength\" value=\"[%%imglistlength%%]\">\n" +
"  <input type=\"hidden\" id=\"imgscrolllength\" name=\"imgscrolllength\" value=\"5\">\n" +
"  <table height=\"90%\" width=\"90%\" align=\"center\">\n" +
"\t  <tr>\n" +
"\t  \t<td>\n" +
"\t\t\t<div id=\"bigimgplay\">\n" +
"\t\t\t\t<table align=\"center\" height=\"90%\" width=\"90%\">\n" +
"\t\t\t\t\t<tr>\n" +
"\t\t\t\t\t\t<td align=\"center\">\n" +
"\t\t\t\t\t\t<table width=\"90%\"><tr>\n" +
"\t\t\t\t\t\t<td width=\"25%\" align=\"right\"><div id=\"lastimghidden\" style=\"display:none\"><img src=\"/_sys_images/daleft.gif\" style=\"cursor: hand\" onclick=\"lastimg()\"></div>&nbsp;</td>\n" +
"\t\t\t\t\t\t<td width=\"40%\" align=\"center\">[%%firstimg%%]&nbsp;</td>\n" +
"\t\t\t\t\t\t<td width=\"25%\" align=\"left\"><div id=\"nextimghidden\"><img src=\"/_sys_images/daright.gif\" style=\"cursor: hand\" onclick=\"nextimg()\"></div></td>\n" +
"\t\t\t\t\t\t</tr>\n" +
"\t\t\t\t\t\t</table>\n" +
"\t\t\t\t\t\t</td>\n" +
"\t\t\t\t\t\t\n" +
"\t\t\t\t\t</tr>\n" +
"\t\t\t\t</table>\n" +
"\t\t\t</div>\n" +
"\t  \t</td>\n" +
"\t  </tr>"+
"<tr>\n" +
"\t  \t<td align=\"center\">\n" +
"\t  \t<table width=\"90%\"><tr>\n" +
"\t  \t\t<td align=\"left\" width=\"10%\">&nbsp;<div id=\"lastsmall\"><img src=\"/_sys_images/xiaoleft.gif\" style=\"cursor: hand\" onclick=\"lastsmallplay()\"></div></td>\n" +
"\t  \t\t<td align=\"center\" width=\"80%\">&nbsp;<div id=\"showsmall\">"+
"[%%imglist%%]"+
"</div></td>\n" +
"\t  \t\t<td align=\"right\" width=\"10%\">&nbsp;<div id=\"nextsmall\"><img src=\"/_sys_images/xiaoright.gif\" style=\"cursor: hand\" onclick=\"nextsmallplay()\"></div></td>\n" +
"\t  \t</tr></table>\n" +
"\t  \t</td>\n" +
"\t  </tr>\n" +
"  </table>"+
                          "[/CONTENT][/HTMLCODE][/TAG]";

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(content);
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(126);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);
            }

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
        } else if (doaction.equals("e")) {    //编辑某个标记
            //System.out.println("markid=" + markID);
            mark1 = markMgr.getAMark(markID);
            content = mark1.getContent();
            content = StringUtil.replace(content,"<","&lt;");
            content = StringUtil.replace(content,">","&gt;");
            content = StringUtil.replace(content, "[", "<");
            content = StringUtil.replace(content, "]", ">");
            //去掉content字段的内容
            int sposi = content.indexOf("<CONTENT>");
            int eposi = content.indexOf("</CONTENT>");
            String tbuf = content.substring(0,sposi+9);
            tbuf = tbuf + content.substring(eposi);
            content = tbuf;
            //System.out.println(content);
            XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + content);
            String attrName = properties.getName();
            pic_texiao_type = Integer.parseInt(properties.getProperty(attrName.concat(".MARKTYPE")))-120;
            String s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
            if (s_scrolltitle != null)
                scrolltitle = Integer.parseInt(s_scrolltitle);
            else
                scrolltitle = 0;
            s_h = properties.getProperty(attrName.concat(".H"));
            s_w = properties.getProperty(attrName.concat(".W"));
            if (s_h != null)
                h = Integer.parseInt(s_h);
            else
                h = 0;
            if (s_w != null)
                w = Integer.parseInt(s_w);
            else
                w = 0;

            s_ph = properties.getProperty(attrName.concat(".PH"));
            s_pw = properties.getProperty(attrName.concat(".PW"));
            if (s_ph != null)
                ph = Integer.parseInt(s_ph);
            else
                ph = 0;
            if (s_pw != null)
                pw = Integer.parseInt(s_pw);
            else
                pw = 0;
        }
    }

    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        if (column != null)
            CName = column.getCName();
        else
            CName = "首页";
    } else {
        CName = "程序模板";
    }
%>

<html>
<head>
    <title>图片特效</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="JavaScript">
    function onclickthehref(form,start)
    {
        var obj;
        obj=document.getElementsByName("pictxtype");
        if(obj!=null){
            var i,ptxvalue;
            for(i=0;i<obj.length;i++){
                if(obj[i].checked){
                    ptxvalue = obj[i].value;
                    break;
                }
            }
        }

        var imgW = document.selarticleform.picwidth.value;
        var W = document.selarticleform.width.value;
        if(ptxvalue<=1){
            if(W>imgW*2){
                alert('滚动区宽度不能大于图片宽度的2倍');
                return false;
            }
        }
        form.method="post";
        form.action="listarticlefor_articlexuanimages.jsp?start=" + start + "&pictxtype=" + ptxvalue;
        form.submit();
        //return true;
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<form name="selarticleform" onsubmit="javascript:return onclickthehref(selarticleform,<%=start%>)">
<input type=hidden name=doSearch value=true>
<input type=hidden name=saveas value=false>
<input type=hidden name=mark value="<%=markID%>">
<input type=hidden name=column value=<%=columnID%>>
<input type=hidden name=start value=<%=start%>>
<input type=hidden name=range value=<%=range%>>
<input type=hidden name=modeltype value=<%=modeltype%>>
<%
    if (msg != null) out.println("<span class=cur>" + msg + "</span>");
    if (articleCount > 0) {
        if (keyword == null) keyword = "";
        out.println("<table cellpadding=1 cellspacing=1 width=100% border=0>");
        out.println("<tr><td width=50% align=left class=line>");
        if (start - range >= 0)
            out.println("<a href=\"javascript:onclickthehref(selarticleform," + (start-range) + ")\"><img src=../images/btn_previous.gif align=bottom border=0></a>" + start);
        out.println("</td><td width=50% align=right class=line>");
        if (start + range < total) {
            int remain = ((start + range - total) < range) ? (total - start - range) : range;
            out.println(remain + "<a href=\"javascript:onclickthehref(selarticleform," + (start+range) + ")\"><img src=../images/btn_next.gif align=bottom border=0></a>");
        }
        out.println("</td></tr></table>");
    }
%>
<table cellpadding="1" cellspacing="1" border="0">
    <tr >
        <td colspan=2 valign="top">
            <input type=checkbox name="scrolltitle" value="1" <%=(scrolltitle==0)?"":"checked"%>>是否滚动文章标题<p>
            <input type=radio name="pictxtype" value="0" <%=(pic_texiao_type==0)?"checked='true'":""%>> 竖向上滚动
            <input type=radio name="pictxtype" value="1" <%=(pic_texiao_type==1)?"checked='true'":""%>> 竖向下滚动
            <input type=radio name="pictxtype" value="2" <%=(pic_texiao_type==2)?"checked='true'":""%>> 横向右滚动
            <input type=radio name="pictxtype" value="3" <%=(pic_texiao_type==3)?"checked='true'":""%>> 横向左滚动
            <input type=radio name="pictxtype" value="4" <%=(pic_texiao_type==4)?"checked='true'":""%>> FLASH幻灯片
            <input type=radio name="pictxtype" value="5" <%=(pic_texiao_type==5)?"checked='true'":""%>> 自动轮换显示
            <input type=radio name="pictxtype" value="6" <%=(pic_texiao_type==6)?"checked='true'":""%>> 图片相册<br>
            滚动区高度：<input type=text name="height" <%=(h!=0)?"value="+h:"value=\"\""%> size="10">
            滚动区宽度：<input type=text name="width"  <%=(w!=0)?"value="+w:"value=\"\""%> size="10"><br>
            图片高度： <input type=text name="picheight"  <%=(ph!=0)?"value="+ph:"value=\"\""%> size="10">
            图片宽度： <input type=text name="picwidth"  <%=(pw!=0)?"value="+pw:"value=\"\""%> size="10">
        </p>
            <table border=0 cellpadding=0 cellspacing=0 width="100%">
                <tr height=24>
                    <td>
                        标记是否要生成包含文件：
                        <input type=radio name=innerFlag value=0 <%if(innerFlag==0){%>checked<%}%>>否
                        <input type=radio name=innerFlag value=1 <%if(innerFlag==1){%>checked<%}%>>是
                    </td>
                </tr>
                <tr height=24>
                    <td>标记中文名称：<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
                </tr>
                <tr height=80>
                    <td>标记描述：<br><textarea rows="3" name="notes" id="notesid" cols="38" class=tine><%=notes%>
                    </textarea></td>
                </tr>
            </table>
        </td>
    </tr>
    <tr><td>
        <input type="submit"  name="doaction" value="确定">&nbsp;&nbsp;
        <input type="button"  value="取消" onclick="javascript:top.window.close();">
    </td></tr>
</table>
</form>
</BODY>
</html>