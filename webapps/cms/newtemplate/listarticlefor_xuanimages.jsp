<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
        %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="com.bizwink.cms.publish.IPublishManager" %>
<%@ page import="com.bizwink.cms.publish.PublishPeer" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="java.util.regex.Pattern" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=ϵͳ��ʱ�������µ�½!"));
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
    List selArticle = ParamUtil.getParameterValues(request, "selarticles");
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
    String cname = "ͼƬ��Ч";
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
        if (doaction.equals("����")) {
            System.out.println("ִ������");

        } else if (doaction.equals("ȷ��")){
            System.out.println("���ɱ��");
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
            markname = "ͼƬ��Ч";

            if (pic_texiao_type == 0) {
                System.out.println("�����Ϲ���" + pic_texiao_type);
                if (h==0) h = 180;
                if (w==0) w = 60;
                markname = "ͼƬ�Ϲ�";
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

                //�����������һ��ͼƬ��������FLASH�õƵ������ļ���FLASH�õƱ��
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrollup_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]100[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]");
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
                mark.setMarkType(100);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 1) {
                System.out.println("�����¹���" + pic_texiao_type);
                if (h==0) h = 180;
                if (w==0) w = 60;
                markname = "ͼƬ�¹�";
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

                //�����������һ��ͼƬ��������FLASH�õƵ������ļ���FLASH�õƱ��
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.append("[%%ARTICLE_LIST%%]</div>\r\n");
                sb.append("<div id=biz_scrolldown_pic[%%markid%%]_2></div>\r\n");
                sb.append("</div>\r\n");
                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]101[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]");
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
                mark.setMarkType(101);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 2) {
                System.out.println("�����ҹ���" + pic_texiao_type);
                if (h==0) h = 60;
                if (w==0) w = 560;
                markname = "ͼƬ�ҹ�";
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

                //�����������һ��ͼƬ��������FLASH�õƵ������ļ���FLASH�õƱ��
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]102[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]");
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
                mark.setMarkType(102);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else if (pic_texiao_type == 3) {
                System.out.println("���������" + pic_texiao_type);
                if (h==0) h = 60;
                if (w==0) w = 560;
                markname = "ͼƬ���";
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

                //�����������һ��ͼƬ��������FLASH�õƵ������ļ���FLASH�õƱ��
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                sb.insert(0,"[TAG][HTMLCODE][MARKTYPE]103[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]");
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
                mark.setMarkType(103);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

            } else {
                if (h==0) h = 200;
                if (w==0) w = 200;
                markname = "ͼƬ�õ�";
                /*sb.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n");
                sb.append("<slider>\r\n");
                for(int i=0; i<selArticle.size(); i++) {
                    String t = (String)selArticle.get(i);
                    int posi = t.lastIndexOf("-");
                    if (posi > -1) {
                        int articleid = Integer.parseInt(t.substring(posi+1));
                        artids = artids + articleid + ",";
                        Article article = articleMgr.getArticle(articleid);
                        String title = article.getMainTitle();
                        String article_url = article.getDirName() + article.getID() +  "." + extname;
                        String imageurl = article.getDirName() + "images/"  + article.getArticlepic();
                        sb.append("<item href=\"" + article_url + "\" title=\"" + title + "\" " + "imageurl=\"" + imageurl + "\" />\r\n");
                    }
                }
                sb.append("</slider>\r\n");

                //�����������һ��ͼƬ��������FLASH�õƵ������ļ���FLASH�õƱ��
                if (artids.length() > 0) {
                    artids = artids.substring(0,artids.length()-1);
                }

                /*content = "[TAG][HTMLCODE][MARKTYPE]104[/MARKTYPE][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<div align=\"center\">\r\n" +
                        "<object codebase=http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0\r\n" +
                        "height=" + h + "width=" + w + " align=middle classid=clsid:d27cdb6e-ae6d-11cf-96b8-444553540000>\r\n" +
                        "<param name=\"allowScriptAccess\" value=\"sameDomain\" />\r\n" +
                        "<param name=\"movie\" value=\"/js/picslider.swf?data=" + dirname + "[%%markid%%].xml\" />\r\n" +
                        "<param name=\"quality\" value=\"high\" />\r\n" +
                        "<param name=\"wmode\" value=\"transparent\" />\r\n" +
                        "<param name=\"bgcolor\" value=\"#ffffff\" />\r\n" +
                        "<embed src=\"/js/picslider.swf?data=" + dirname + "[%%markid%%].xml\" quality=\"high\" wmode=\"transparent\" bgcolor=\"#ffffff\" width=\""+ w + "\" height=\"" + h + "\"\r\n" +
                        "align=\"middle\" allowscriptaccess=\"sameDomain\" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" />\r\n" +
                        "</object></div>\r\n[/CONTENT][/HTMLCODE][/TAG]";
                */

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

                content = "[TAG][HTMLCODE][MARKTYPE]104[/MARKTYPE][SCROLLTITLE]" + scrolltitle + "[/SCROLLTITLE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][STYLES][/STYLES][ARTICLEIDS]" + artids +"[/ARTICLEIDS][CONTENT]<script type=\"text/javascript\">\r\n" +
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
                mark.setMarkType(104);
                if (orgmarkID > 0 && !saveas)
                    markMgr.Update(mark);
                else
                    markID = markMgr.Create(mark);

                //���������ļ������������ļ�������WEB������
                /*String appdir = request.getRealPath("/");
                String filename = appdir + "sites" + java.io.File.separator + sitename + java.io.File.separator + StringUtil.replace(dirname,"/",java.io.File.separator) + markID + ".xml";
                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(sb.toString());
                pw.close();
                int errcode = pubMgr.publish(username,filename,siteID,dirname,0);*/
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
        } else if (doaction.equals("e")) {    //�༭ĳ�����
            //System.out.println("markid=" + markID);
            mark1 = markMgr.getAMark(markID);
            content = mark1.getContent();
            content = StringUtil.replace(content,"<","&lt;");
            content = StringUtil.replace(content,">","&gt;");
            content = StringUtil.replace(content, "[", "<");
            content = StringUtil.replace(content, "]", ">");
            //ȥ��content�ֶε�����
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

            artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
            System.out.println("artids="+artids);
            if(artids!=null){
            if (artids.length() > 0) {
                if (artids.endsWith(",")) artids = artids.substring(0,artids.length() - 1);
                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                String[] arts = p.split(artids);
                for(int j=0; j<arts.length; j++) {
                    int articleid = Integer.parseInt(arts[j]);
                    Article article = articleMgr.getArticle(articleid);
                    if (article != null) {
                        String title = article.getMainTitle();
                        selArticle.add(title + "-" + article.getID());
                    }
                }
            }   }
            //System.out.println("attrName=" + attrName + "====artids=" + artids);
        }
    }

    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        if (column != null)
            CName = column.getCName();
        else
            CName = "��ҳ";
    } else {
        CName = "����ģ��";
    }

    total = articleMgr.getLinkForXuanImagesNum(columnID, keyword, modeltype);
    articleList = articleMgr.getLinkForXuanImages(columnID, start, range, keyword, modeltype);
    articleCount = articleList.size();

%>

<html>
<head>
    <title>ͼƬ��Ч</title>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
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
        String text = val.substring(0,posi);
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

        //ѡ������ѡ�е�����
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
            alert("��ѡ����Ŀ��");
        }
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
        <td colspan=5>��ǰ������Ŀ-->><font color=red><%=CName%>
        </font></td>
    </tr>
    <tr class=itm bgcolor="#dddddd">
        <td align=center width="15%">ѡ�и�����</td>
        <td align=center width="50%">����</td>
        <td align=center width="15%">�޸�ʱ��</td>
        <td align=center width="10%">�༭</td>
        <td align=center width="10%">Ԥ��</td>
    </tr>
    <%
        for (int i = 0; i < articleCount; i++) {
            Article article = (Article) articleList.get(i);

            int articleID = article.getID();       //����ID��ģ��ID
            String editor = article.getEditor();   //���»�ģ��ı༭��
            String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
            String lastUpdated = article.getLastUpdated().toString().substring(0, 10); //���»�ģ�������޸�ʱ��

            columnID = article.getColumnID();      //���»�ģ��������ĿID

            //������´���ʱ�䣬���ɷ���·���е�һ����
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
        <td valign="top">��ѡ����:
            <select size=5 name="selarticles" id="selarticlesID" style="width:200" multiple>
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
            <input type=button value="ɾ��" onclick="delarticle(selarticleform);"> <p>
            <input type=checkbox name="scrolltitle" value="1" <%=(scrolltitle==0)?"":"checked"%>>�Ƿ�������±���<p>
        </td>
        <td colspan=2 valign="top">
            �������:<input name=keyword size=35>&nbsp;
            <input type=submit name="doaction" value="����"><p>
            <input type=radio name="pictxtype" value="0" <%=(pic_texiao_type==0)?"checked='true'":""%>> �����Ϲ���
            <input type=radio name="pictxtype" value="1" <%=(pic_texiao_type==1)?"checked='true'":""%>> �����¹���
            <input type=radio name="pictxtype" value="2" <%=(pic_texiao_type==2)?"checked='true'":""%>> �����ҹ���
            <input type=radio name="pictxtype" value="3" <%=(pic_texiao_type==3)?"checked='true'":""%>> ���������
            <input type=radio name="pictxtype" value="4" <%=(pic_texiao_type==4)?"checked='true'":""%>> FLASH�õ�Ƭ<br>
            �������߶ȣ�<input type=text name="height" <%=(h!=0)?"value="+h:"value=\"\""%> size="10">
            ���������ȣ�<input type=text name="width"  <%=(w!=0)?"value="+w:"value=\"\""%> size="10"><br>
            ͼƬ�߶ȣ� <input type=text name="picheight"  <%=(ph!=0)?"value="+ph:"value=\"\""%> size="10">
            ͼƬ���ȣ� <input type=text name="picwidth"  <%=(pw!=0)?"value="+pw:"value=\"\""%> size="10">
        </p>
            <table border=0 cellpadding=0 cellspacing=0 width="100%">
                <tr height=24>
                    <td>
                        ����Ƿ�Ҫ���ɰ����ļ���
                        <input type=radio name=innerFlag value=0 <%if(innerFlag==0){%>checked<%}%>>��
                        <input type=radio name=innerFlag value=1 <%if(innerFlag==1){%>checked<%}%>>��
                    </td>
                </tr>
                <tr height=24>
                    <td>����������ƣ�<input name=chineseName size=20 value="<%=cname%>" class=tine></td>
                </tr>
                <tr height=80>
                    <td>���������<br><textarea rows="3" name="notes" id="notesid" cols="38" class=tine><%=notes%>
                    </textarea></td>
                </tr>
            </table>

        </td>
    </tr>
    <tr><td>
        <input type="submit"  name="doaction" value="ȷ��">&nbsp;&nbsp;
        <input type="button"  value="ȡ��" onclick="javascript:top.window.close();">
    </td></tr>
</table>
</form>
</BODY>
</html>