<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.cms.publish.*,
                 java.util.regex.Pattern,
                 com.bizwink.cms.xml.XMLProperties,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%
    request.setCharacterEncoding("utf-8");
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
    int range = ParamUtil.getIntParameter(request, "range", 8);
    int h = ParamUtil.getIntParameter(request, "height", 0);
    int w = ParamUtil.getIntParameter(request, "width", 0);
    int ph = ParamUtil.getIntParameter(request, "picheight", 0);
    int pw = ParamUtil.getIntParameter(request, "picwidth", 0);
    String msg = ParamUtil.getParameter(request, "msg");
    String doaction = ParamUtil.getParameter(request, "doaction");
    String keyword = ParamUtil.getParameter(request, "keyword");
    List selArticle = ParamUtil.getParameterValues(request, "selarticles");
    String s_h = null;
    String s_w = null;
    String s_ph = null;
    String s_pw = null;
    int linkflag = 0;
    int thumbposi = 0;
    int xth = 0;
    int xtw = 0;
    int dxjl = 0;
    int xtqyh = 0;
    int xtqyw = 0;
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
    String cname = "图片特效";
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

    System.out.println("doaction=" + doaction);
    if (doaction != null) {
        if (doaction.equals("搜索")) {
            System.out.println("执行搜索");
        } else if (doaction.equals("确定")){
            markID = ParamUtil.getIntParameter(request, "mark", 0);
            boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
            pic_texiao_type = ParamUtil.getIntParameter(request,"pictxtype",2);
            linkflag = ParamUtil.getIntParameter(request,"hrefflag",0);
            thumbposi = ParamUtil.getIntParameter(request,"thumbposi",0);
            xtqyw = ParamUtil.getIntParameter(request,"xtqyw",0);
            xtqyh = ParamUtil.getIntParameter(request,"xtqyh",0);
            dxjl = ParamUtil.getIntParameter(request,"dxjl",0);
            xth = ParamUtil.getIntParameter(request,"xth",0);
            xtw = ParamUtil.getIntParameter(request,"xtw",0);
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
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrollup_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]100[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<![CDATA[");
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
                sb.append("</script>\r\n]]>[/CONTENT][/HTMLCODE][/TAG]");

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(100);
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
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                //如果包含至少一张图片，则生成FLASH幻灯的数据文件和FLASH幻灯标记
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrolldown_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]101[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<![CDATA[");
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
                sb.append("</script>\r\n]]>[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(101);
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
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]102[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<![CDATA[");
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
                sb.append("</script>\r\n]]>[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(102);
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
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                //如果包含至少一张图片，则生成FLASH幻灯的数据文件和FLASH幻灯标记
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]103[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<![CDATA[");
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
                sb.append("</script>\r\n]]>[/CONTENT][/HTMLCODE][/TAG]");
                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(sb.toString());
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(103);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 4) {
                if (h==0) h = 200;
                if (w==0) w = 200;
                markname = "图片幻灯";
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                content = "[TAG][HTMLCODE][MARKTYPE]104[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<![CDATA[<script type=\"text/javascript\">\r\n" +
                        "var focus_width=" + w + ";\r\n" +
                        "var focus_height=" + h + ";\r\n" +
                        "var text_height=18;\r\n" +
                        "var swf_height = focus_height+text_height;\r\n" +
                        "var pics='[%%pics%%]" + "';\r\n" +
                        "var links='[%%links%%]"  + "';\r\n" +
                        "var texts='[%%texts%%]" + "';\r\n" +
                        "var ieflag = 0;"+
                        "var objXml;\n" +
                        "    if (window.ActiveXObject) {\n" +
                        "        ieflag = 0;\n" +
                        "    } else if (window.XMLHttpRequest) {\n" +
                        "        ieflag = 1;\n" +
                        "    }" +
                        "if(ieflag == 0){" +
                        "document.write('<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"'+ focus_width +'\" height=\"'+ swf_height +'\">');\n" +
                        "document.write('<param name=\"movie\" value=\"/_sys_js/focus1.swf\" />');\n" +
                        "document.write('<param name=\"quality\" value=\"high\" />');\n" +
                        "document.write('<param name=\"flashvars\" value=\"pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'\">');\n" +
                        "document.write('<param name=\"menu\" value=\"false\" /><param name=wmode value=\"opaque\">');\n" +
                        "document.write('<embed src=\"/_sys_js/focus1.swf\"');\n" +
                        "document.write('quality=\"high\"');\n" +
                        "document.write('pluginspage=\"http://www.macromedia.com/go/getflashplayer\"');\n" +
                        "document.write('type=\"application/x-shockwave-flash\"');\n" +
                        "document.write('width=\"'+focus_width+'\"');\n" +
                        "document.write('height=\"'+swf_height+'\" />');" +
                        "document.write('</object>');" +
                        "}else if(ieflag == 1){" +
                        "document.write('<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" width=\"'+ focus_width +'\" height=\"'+ swf_height +'\">');\n" +
                        "document.write('<param name=\"movie\" value=\"/_sys_js/focus1.swf\" />');\n" +
                        "document.write('<param name=\"quality\" value=\"high\" />');\n" +
                        "document.write('<param name=\"menu\" value=\"false\" /><param name=wmode value=\"opaque\">');\n" +
                        "document.write('<embed src=\"/_sys_js/focus1.swf\"');\n" +
                        "document.write('flashvars=\"pics='+pics+'&links='+links+'&texts='+texts+'&borderwidth='+focus_width+'&borderheight='+focus_height+'&textheight='+text_height+'\"');\n" +
                        "document.write('quality=\"high\"');\n" +
                        "document.write('pluginspage=\"http://www.macromedia.com/go/getflashplayer\"');\n" +
                        "document.write('type=\"application/x-shockwave-flash\"');\n" +
                        "document.write('width=\"'+focus_width+'\"');\n" +
                        "document.write('height=\"'+swf_height+'\" />');" +
                        "document.write('</object>');" +
                        "}" +
                        "</script>\r\n]]>[/CONTENT][/HTMLCODE][/TAG]";

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(content);
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(104);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);
            } else if (pic_texiao_type == 5){
                if (h==0) h = 200;
                if (w==0) w = 200;
                markname = "小图引导大图";
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                    }
                }

                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                content = "[TAG][HTMLCODE][MARKTYPE]105[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][LINK]" + linkflag + "[/LINK]" +
                        "[PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][THUMBPOSI]" + thumbposi + "[/THUMBPOSI]" +
                        "[XTQYH]" + xtqyh + "[/XTQYH][XTQYW]" + xtqyw + "[/XTQYW][DXJL]" + dxjl + "[/DXJL][XTH]" + xth + "[/XTH][XTW]" + xtw + "[/XTW][CONTENT]<![CDATA[" +
                        "<script type=\"text/javascript\" src=\"/_sys_js/base.js?v=20000000003\"></script><script type=\"text/javascript\"  src=\"/_sys_js/pic.js\"></script>\r\n"+
                        "]]>[/CONTENT][/HTMLCODE][/TAG]";

                mark mark = new mark();
                mark.setID(markID);
                mark.setColumnID(columnID);
                mark.setSiteID(siteID);
                mark.setContent(content);
                mark.setChinesename(cname);
                mark.setNotes(notes);
                mark.setInnerHTMLFlag(innerFlag);
                mark.setFormatFileNum(listType);
                mark.setMarkType(105);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);
            }

            if (orgmarkID > 0 && !saveas) {
                out.println("<script>top.close();</script>");
            } else {
                String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
                out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                        "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
            }
            return;
        } else if (doaction.equals("e")) {    //编辑某个标记
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
            pic_texiao_type = Integer.parseInt(properties.getProperty(attrName.concat(".MARKTYPE")))-100;

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

            String linkflag_s = properties.getProperty(attrName.concat(".LINK"));
            if (linkflag_s != null)
                linkflag = Integer.parseInt(linkflag_s);
            else
                linkflag = 0;
            String thumbposi_s = properties.getProperty(attrName.concat(".THUMBPOSI"));
            if (thumbposi_s != null)
                thumbposi = Integer.parseInt(thumbposi_s);
            else
                thumbposi = 0;
            String xtqyh_s = properties.getProperty(attrName.concat(".XTQYH"));
            if (xtqyh_s != null)
                xtqyh = Integer.parseInt(xtqyh_s);
            else
                xtqyh = 0;
            String xtqyw_s = properties.getProperty(attrName.concat(".XTQYW"));
            if (xtqyw_s != null)
                xtqyw = Integer.parseInt(xtqyw_s);
            else
                xtqyw = 0;
            String dxjl_s = properties.getProperty(attrName.concat(".DXJL"));
            if (dxjl_s != null)
                dxjl = Integer.parseInt(dxjl_s);
            else
                dxjl = 0;
            String xth_s = properties.getProperty(attrName.concat(".XTH"));
            if (xth_s != null)
                xth = Integer.parseInt(xth_s);
            else
                xth = 0;
            String xtw_s = properties.getProperty(attrName.concat(".XTW"));
            if (xtw_s != null)
                xtw = Integer.parseInt(xtw_s);
            else
                xtw = 0;

            artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
            if(artids!=null){
                if (artids.length() > 0) {
                    if (artids.endsWith(",")) artids = artids.substring(0,artids.length() - 1);
                    Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                    String[] arts = p.split(artids);
                    for(int j=0; j<arts.length; j++) {
                        int articleid = Integer.parseInt(arts[j]);
                        Article article = articleMgr.getArticle(articleid);
                        if (article != null) {
                            String title = StringUtil.gb2iso4View(article.getMainTitle());
                            selArticle.add(title + "-" + article.getID());
                        }
                    }
                }
            }
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

    total = articleMgr.getLinkForXuanImagesNum(columnID, keyword, modeltype);
    articleList = articleMgr.getLinkForXuanImages(columnID, start, range, keyword, modeltype);
    articleCount = articleList.size();
%>

<html>
<head>
    <title>图片特效</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="JavaScript">
    function initsel()
    {
        articles = document.getElementById("selarticlesID");
        //alert(articles);
        articles.length = 0;
        <%
        for (int i=0; i<selArticle.size(); i++) {
            String val = (String)selArticle.get(i);
            int posi = val.lastIndexOf("-");
            String text = StringUtil.gb2iso4View(val.substring(0,posi));
        %>
        oOption = document.createElement("OPTION");
        oOption.text = "<%=text%>";
        oOption.value = "<%=val%>";
        articles.add(oOption);
        <%}%>
    }


    function PreviewArticle(articleID)
    {
        window.open("../article/preview.jsp?article=" + articleID, "Preview", "width=800,height=600,left=0,top=0,scrollbars");
    }

    function selectthis(maintitle,articleid)
    {
        //alert(maintitle);
        maintitle=document.getElementById(articleid).value;
        articles = document.getElementById("selarticlesID");
        oOption = document.createElement("OPTION");
        oOption.text = maintitle;
        oOption.value = maintitle + "-" + articleid;
        articles.add(oOption);
    }

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

        //选择所有选中的文章
        for (var i = 0; i < form.selarticles.length; i++) {
            form.selarticles[i].selected = true;
        }

        form.method="post";
        form.action="listarticlefor_xuanimages.jsp?start=" + start + "&pictxtype=" + ptxvalue;
        form.submit();
        //return true;
    }

    function delarticle(form)
    {
        var select = false;
        for (var i = 0; i < form.selarticles.length; i++)
        {
            if (form.selarticles[i].selected)
            {
                select = true;
                break;
            }
        }

        if (select)
        {
            for (var i = 0; i < form.selarticles.length; i++)
                if (form.selarticles[i].selected)
                    form.selarticles[i] = null;
        }
        else
        {
            alert("请选择栏目！");
        }
    }

    function show_xtattr(me) {
        me.style.display="block";
    }

    function hide_xtattr(me) {
        me.style.display="none";
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8 onload="javascript:initsel()">
<form name="selarticleform" onsubmit="javascript:onclickthehref(selarticleform,<%=start%>)">
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
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
        <tr>
            <td colspan=5>当前所在栏目-->><font color=red><%=CName%>
            </font></td>
        </tr>
        <tr class=itm bgcolor="#dddddd">
            <td align=center width="15%">选中该链接</td>
            <td align=center width="50%">标题</td>
            <td align=center width="15%">修改时间</td>
            <td align=center width="10%">编辑</td>
            <td align=center width="10%">预览</td>
        </tr>
        <%
            for (int i = 0; i < articleCount; i++) {
                Article article = (Article) articleList.get(i);

                int articleID = article.getID();       //文章ID或模板ID
                String editor = article.getEditor();   //文章或模板的编辑者
                String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
                String lastUpdated = article.getLastUpdated().toString().substring(0, 10); //文章或模板的最后修改时间

                columnID = article.getColumnID();      //文章或模板所属栏目ID

                //获得文章创建时间，生成发布路径中的一部分
                String createdate_path = "";
                if(article.getCreateDate() != null){
                    createdate_path = article.getCreateDate().toString();
                    createdate_path = createdate_path.substring(0, 10).replaceAll("-","") + "/";
                }
                String maintitle = StringUtil.gb2iso4View(article.getMainTitle());

                out.println("<tr bgcolor=" + bgcolor + "class=itm>");
                out.println("<td align=center><input type=checkbox name=selectedLink onclick=selectthis('sss'," + articleID + ") value='" +maintitle+ "' id="+articleID+"></td>");
                out.println("<td>" + maintitle + "</td>");
                out.println("<td>" + lastUpdated + "</td>");
                out.println("<td>" + editor + "</td>");
                out.println("<td align=center>");
                out.println("<a href=javascript:PreviewArticle(" + articleID + ");><img src=../images/button/view.gif border=0></a>");
                out.println("</td></tr>");
            }
        %>
    </table>

    <table cellpadding="1" cellspacing="1" border="0">
        <tr >
            <td valign="top">被选文章:
                <select size=4 name="selarticles" id="selarticlesID" style="width:200" multiple>
                    <%
                        if (selArticle != null) {
                            for(int i=0; i<selArticle.size(); i++) {
                                String value = (String)selArticle.get(i);
                                String text = value.substring(0,value.lastIndexOf("-"));
                                out.println("<option value=\"" + value + "\">" + text + "</option>");
                            }
                        }
                    %>
                </select>
                <input type=button value="删除" onclick="delarticle(selarticleform);"> <p>
            </td>
            <td colspan=2 valign="top">
                输入标题:<input name=keyword size=35>&nbsp;
                <input type=submit name="doaction" value="搜索"><p>
                <input type=checkbox name="scrolltitle" value="1" <%=(scrolltitle==0)?"":"checked"%>>是否滚动文章标题
                <input type=checkbox name="hrefflag" value="1" <%=(linkflag==0)?"":"checked"%>>图片是否有连接<p>
            </td>
        </tr>
    </table>

    <table border=0 cellpadding=1 cellspacing=1 width="100%">
        <tr>
            <td>
                <input type=radio name="pictxtype" value="0" <%=(pic_texiao_type==0)?"checked='true'":""%> onclick="javascript:hide_xtattr(thumb);"> 上滚动
                <input type=radio name="pictxtype" value="1" <%=(pic_texiao_type==1)?"checked='true'":""%> onclick="javascript:hide_xtattr(thumb);"> 下滚动
                <input type=radio name="pictxtype" value="2" <%=(pic_texiao_type==2)?"checked='true'":""%> onclick="javascript:hide_xtattr(thumb);"> 右滚动
                <input type=radio name="pictxtype" value="3" <%=(pic_texiao_type==3)?"checked='true'":""%> onclick="javascript:hide_xtattr(thumb);"> 左滚动
                <input type=radio name="pictxtype" value="4" <%=(pic_texiao_type==4)?"checked='true'":""%> onclick="javascript:hide_xtattr(thumb);"> 幻灯片
                <input type=radio name="pictxtype" value="5" <%=(pic_texiao_type==5)?"checked='true'":""%> onclick="javascript:show_xtattr(thumb);"> 小图引导大图<br />
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                特效区高度：<input type=text name="height" <%=(h!=0)?"value="+h:"value=\"\""%> size="10">
                特效区宽度：<input type=text name="width"  <%=(w!=0)?"value="+w:"value=\"\""%> size="10">
                图片高度： <input type=text name="picheight"  <%=(ph!=0)?"value="+ph:"value=\"\""%> size="10">
                图片宽度： <input type=text name="picwidth"  <%=(pw!=0)?"value="+pw:"value=\"\""%> size="10"> <br>
                <div id="thumb" style="display:none;">
                    小图片区的位置：
                    <input type=radio name="thumbposi" value="0" <%=(thumbposi==0)?"checked='true'":""%>> 大图左侧
                    <input type=radio name="thumbposi" value="1" <%=(thumbposi==1)?"checked='true'":""%>> 大图右侧
                    <input type=radio name="thumbposi" value="2" <%=(thumbposi==2)?"checked='true'":""%>> 大图下方
                    <input type=radio name="thumbposi" value="3" <%=(thumbposi==3)?"checked='true'":""%>> 大图上方
                    <br>
                    单张小图片区域高度：<input type=text name="xtqyh" <%=(xtqyh!=0)?"value="+xtqyh:"value=\"\""%> size="10">
                    单张小图片区域宽度：<input type=text name="xtqyw" <%=(xtqyw!=0)?"value="+xtqyw:"value=\"\""%> size="10">
                    小图片与大图片距离：<input type=text name="dxjl" <%=(dxjl!=0)?"value="+dxjl:"value=\"\""%> size="10">
                    <br>
                    单张小图片高度：<input type=text name="xth" <%=(xth!=0)?"value="+xth:"value=\"\""%> size="10">
                    单张小图片宽度：<input type=text name="xtw" <%=(xtw!=0)?"value="+xtw:"value=\"\""%> size="10">
                </div>
                <script language="JavaScript">
                    var txtype = <%=pic_texiao_type%>;
                    if (txtype == 5) show_xtattr(thumb);
                </script>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                标记是否要生成包含文件：
                <input type=radio name=innerFlag value=0 <%if(innerFlag==0){%>checked<%}%>>否
                <input type=radio name=innerFlag value=1 <%if(innerFlag==1){%>checked<%}%>>是
                标记中文名称：
                <input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr>
            <td>标记描述：<br><textarea rows="3" name="notes" id="notesid" cols="50"><%=notes%>
            </textarea></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
        </tr>
        <tr>
            <td>
                <input type="submit"  name="doaction" value="确定">&nbsp;&nbsp;
                <input type="button"  value="取消" onclick="javascript:top.window.close();">
            </td>
        </tr>
    </table>
</form>
</BODY>
</html>