<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.leaveword.Word" %>
<%@ page import="com.bizwink.webapps.leaveword.IWordManager" %>
<%@ page import="com.bizwink.webapps.leaveword.LeaveWordPeer" %>
<%@ page import="com.bizwink.cms.markManager.*" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page import="com.bizwink.cms.util.filter" %>
<%@ page import="com.bizwink.cms.xml.XMLProperties" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="java.sql.Timestamp" %>
<%@page contentType="text/html;charset=GBK" %>

<%
    String fromurl = request.getHeader("REFERER");

    IMarkManager markMgr = markPeer.getInstance();
    IWordManager wMgr =  LeaveWordPeer.getInstance();
    String title = ParamUtil.getParameter(request,"title");
    if (title != null && title !="") title = filter.excludeHTMLCode(title);
    String content = ParamUtil.getParameter(request,"content");
    if (content!=null && content!="") content = filter.excludeHTMLCode(content);
    String company = ParamUtil.getParameter(request,"company");
    if (company!=null && company!="") company = filter.excludeHTMLCode(company);
    String linkman = filter.excludeHTMLCode(ParamUtil.getParameter(request,"linkman"));
    if (linkman!=null && linkman!="") linkman = filter.excludeHTMLCode(linkman);
    String links = filter.excludeHTMLCode(ParamUtil.getParameter(request,"links"));
    if (links!=null && links!="") links = filter.excludeHTMLCode(links);
    String zip = filter.excludeHTMLCode(ParamUtil.getParameter(request,"zip"));
    if (zip!=null && zip!="") zip = filter.excludeHTMLCode(zip);
    String email = filter.excludeHTMLCode(ParamUtil.getParameter(request,"email"));
    if (email!=null && email!="") email = filter.excludeHTMLCode(email);
    String phone = filter.excludeHTMLCode(ParamUtil.getParameter(request,"phone"));
    if (phone!=null && phone!="") phone = filter.excludeHTMLCode(phone);
    String mphone = ParamUtil.getParameter(request,"mphone");
    if (mphone!=null && mphone!="") mphone = filter.excludeHTMLCode(mphone);
    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    int siteid = ParamUtil.getIntParameter(request,"siteid",0);
    int formid = ParamUtil.getIntParameter(request,"formid",0);
    int sex = ParamUtil.getIntParameter(request,"sex",0);
    String prefix = ParamUtil.getParameter(request,"prefix");
    if (prefix==null) prefix="A";
    int audit_n = 0;

    if (formid > 0) {
        String str = StringUtil.gb2iso4View(markMgr.getAMarkContent(formid));
        str = StringUtil.replace(str, "[", "<");
        str = StringUtil.replace(str, "]", ">");
        str = StringUtil.replace(str, "{^", "[");
        str = StringUtil.replace(str, "^}", "]");
        //System.out.println(str);

        //获取审核定义信息
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + str);
        String audit = properties.getProperty(properties.getName().concat(".AUDITFLAG"));                          //是否只显示审核后的信息
        if (audit != null) audit_n = Integer.parseInt(audit);
        String auditRule = properties.getProperty(properties.getName().concat(".AUDITRULE"));
        if (auditRule != null) {
            auditRule = auditRule.substring(0,auditRule.length() - 1);
            int posi = auditRule.indexOf("AND");
            if (posi>-1) auditRule = auditRule.substring(0,posi);
            //System.out.println(auditRule);
        }

        //获取用户定义信息
        String userid=null;
        Uregister ug = (Uregister)session.getAttribute("UserLogin");
        if (ug != null  && ug.getErrmsg().equals("ok"))
            userid=ug.getMemberid();
        else
            userid=request.getRemoteHost();

        if(startflag == 1){
            Word word_in_db = wMgr.getAWordByUserid(userid,siteid);
            int minus = 0;
            //设置可以写入数据库的条件
            boolean writeflag = false;
            if (word_in_db!=null) {
                String userid_in_db = word_in_db.getUserid();
                System.out.println(word_in_db.getUserid());
                System.out.println(word_in_db.getWritedate().toString());

                Timestamp writeOfword_in_db = word_in_db.getWritedate();
                Timestamp now = new Timestamp(System.currentTimeMillis());
                long timezone = now.getTime() - writeOfword_in_db.getTime();
                minus = (int)(timezone/600000l);
                if (minus>30) writeflag = true;
            } else {
                writeflag = true;
            }

            //必须相隔30分钟后才能发送第二个留言信息
            if (writeflag) {
                Word word = new Word();
                word.setCompany(company);
                word.setContent(content);
                word.setEmail(email);
                word.setLinkman(linkman);
                word.setLinks(links);
                word.setPhone(phone);
                word.setMphone(mphone);
                word.setSex(sex);
                word.setSiteid(siteid);
                word.setFormid(formid);
                word.setTitle(title);
                word.setUserid(userid);
                word.setZip(zip);
                word.setAuditflag(audit_n);
                word.setPrefix(prefix);
                if (audit_n==1) {
                    word.setAuditor(auditRule);
                }
                wMgr.insertWord(word);
                out.write("<script language=\"javascript\">alert(\"提交成功！\");window.location=\""+fromurl+"\";</script>");
            } else {
                out.write("<script language=\"javascript\">alert(\"如果您需要发送第二条留言信息，请在相隔半小时后发送！\");window.location=\""+fromurl+"\";</script>");
            }
        }else{
            response.sendRedirect(fromurl);
        }
    }
%>