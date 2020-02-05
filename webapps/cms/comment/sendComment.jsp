<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.webapps.comment.webCommentPeer" %>
<%@ page import="com.bizwink.webapps.comment.IWebCommentManager" %>
<%@ page import="com.bizwink.webapps.comment.webComment" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    int startrow = ParamUtil.getIntParameter(request, "startrow", 0);
    int range = ParamUtil.getIntParameter(request, "range", 10);
    int siteid = 13;

    IFeedbackManager feedMgr = FeedbackPeer.getInstance();
    IWebCommentManager cMgr = webCommentPeer.getInstance();
    String sitename = request.getServerName();  //site name
    siteid = feedMgr.getSiteID(sitename);     //get siteid
    int articleid = ParamUtil.getIntParameter(request, "id", 0);
    String content = ParamUtil.getParameter(request, "content");
    String links = ParamUtil.getParameter(request, "link");
    String name = ParamUtil.getParameter(request, "name");
    String ip = request.getRemoteHost();

    int errcode = ParamUtil.getIntParameter(request, "errcode", -1);

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    if (startflag == 1) {


        webComment comment = new webComment();
        comment.setAbout(articleid);
        comment.setSiteid(siteid);
        comment.setContent(content);
        comment.setLink(links);
        comment.setName(name);
        comment.setIP(ip);
        comment.setUsrid(ip.substring(0,ip.lastIndexOf("."))+".*");
        errcode = cMgr.createComment(comment);
        /*if (errcode == 0) {
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("err.jsp");
        }
        return;*/
    }

    List list = new ArrayList();
    int rows = 0;

    String sqlstr = "select * from tbl_comment where siteid = " + siteid + " and about = " + articleid + " order by createdate desc";
    rows = cMgr.getAllCommentNum("select count(*) from tbl_comment where siteid = "+siteid+" and about = "+articleid);
    list = cMgr.getAllcommentInfo(sqlstr, startrow, range);



    int totalpages = 0;
    int currentpage = 0;
    if (rows < range) {
        totalpages = 1;
        currentpage = 1;
    } else {
        if (rows % range == 0)
            totalpages = rows / range;
        else
            totalpages = rows / range + 1;
        currentpage = startrow / range + 1;
    }

    String outstr = "";
    if (errcode == 0) {
        outstr = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\">";
        for (int i = 0; i < list.size(); i++) {
            webComment comment = (webComment) list.get(i);
            String userids = comment.getUsrid() == null ? "" : StringUtil.gb2iso4View(comment.getUsrid());
            String contents = comment.getContent() == null ? "" : StringUtil.gb2iso4View(comment.getContent());
            String ips = comment.getIP() == null ? "" : comment.getIP().substring(0, comment.getIP().lastIndexOf(".")) + ".*";
            String createdate = comment.getCreateDate().toString().substring(0, 19);
            outstr = outstr + "<tr>\n" +
                    "            <td valign=\"top\">\n" +
                    "                评论人：\n" +
                    "            </td>\n" +
                    "            <td valign=\"top\">" + userids + "&nbsp;&nbsp;</td>\n" +
                    "            <td valign=\"top\">\n" +
                    "                网友ip：\n" +
                    "            </td>\n" +
                    "            <td valign=\"top\">" + ips + "&nbsp;&nbsp;</td>\n" +
                    "            <td valign=\"top\">\n" +
                    "                评论时间：\n" +
                    "            </td>\n" +
                    "            <td valign=\"top\">" + createdate + "</td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td valign=\"top\">\n" +
                    "                内容：\n" +
                    "            </td>\n" +
                    "            <td valign=\"top\" colspan=\"5\">" + contents + "</td>\n" +
                    "        </tr><tr height=\"10\"><td colspan=6></td></tr>";
        }
        outstr = outstr + "</table>\n" +
                "    <table width=\"70%\" class=\"css_002\">\n" +
                "        <tr valign=\"bottom\" width=\"100%\">\n" +
                "            <td>总" + totalpages + "页&nbsp; 第" + currentpage + "页" + "</td>\n" +
                "            <td>\n" +
                "            </td>\n" +
                "            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                "            <td class=\"css_002\">";
        if ((startrow - range) >= 0) {
            outstr = outstr + "[<a href=\"javascript:pages(" + (startrow - range) + ")\" class=\"css_002\">上一页</a>]";
        }
        if ((startrow + range) < rows) {
            outstr = outstr + " [<a href=\"javascript:pages(" + (startrow + range) + ")\" class=\"css_002\">下一页</a>]";
        }
        if (totalpages > 1) {
            outstr = outstr + "&nbsp;&nbsp;第<input type=\"text\" name=\"jump\" value=\"" + currentpage + "\" size=\"3\">页&nbsp;\n" +
                    "                <a href=\"#\" class=\"css_002\" onclick=\"pages((document.all('jump').value-1) *" + range + ");\">GO</a>";
        }
        outstr = outstr + "</td>\n" +
                "<td align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                "<td align=\"right\">&nbsp;&nbsp;&nbsp;&nbsp;</td>\n" +
                "</tr>\n" +
                "</table>";
        out.print(outstr);
    } else {
        out.print("false");
    }
%>