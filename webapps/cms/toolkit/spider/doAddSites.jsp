<%@ page import="com.bizwink.collectionmgr.*" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid= authToken.getSiteID();
    String siteName = ParamUtil.getParameter(request, "siteName");
    List getselectColumns = ParamUtil.getParameterValues(request,"selectcolumns");
    /*
    IDocument_TypeManager idtMgr = Document_TypePeer.getInstance();    
    String doc = ParamUtil.getParameter(request, "checkboxDoc");
    String pdf = ParamUtil.getParameter(request, "checkboxPdf");
    String xls = ParamUtil.getParameter(request, "checkboxXls");
    String ppt = ParamUtil.getParameter(request, "checkboxPpt");
    String rtf = ParamUtil.getParameter(request, "checkboxRtf");
    */
    String startUrl = ParamUtil.getParameter(request, "textfieldStarturl");
    int tbrelation = ParamUtil.getIntParameter(request, "tbrelation", -1);
    String tkeyword = ParamUtil.getParameter(request, "TRules");
    String bkeyword = ParamUtil.getParameter(request, "BRules");
    int classid = ParamUtil.getIntParameter(request, "columnid", -1);
    int loginflag = ParamUtil.getIntParameter(request, "loginflag", 0);
    String posturl = ParamUtil.getParameter(request, "posturl");
    String postdata = ParamUtil.getParameter(request, "postdata");
    int proxyflag = ParamUtil.getIntParameter(request, "proxy", 0);
    String proxyurl = ParamUtil.getParameter(request, "proxyurl");
    String proxyport = ParamUtil.getParameter(request, "proxyport");
    int keywordflag=ParamUtil.getIntParameter(request,"use",0);

    IBasic_AttributesManager baMgr = Basic_AttributesPeer.getInstance();
    Basic_Attributes basicAttr = new Basic_Attributes();

    basicAttr.setSiteName(siteName);
    basicAttr.setStartUrl(startUrl);
    basicAttr.setClassId(classid);//------------------classid
    basicAttr.setUrlNumber(3000);
    basicAttr.setPosturl(posturl);
    basicAttr.setLoginflag(loginflag);
    basicAttr.setPostdata(postdata);
    basicAttr.setProxyflag(proxyflag);
    basicAttr.setProxyurl(proxyurl);
    basicAttr.setProxyport(proxyport);
    basicAttr.setBKeyword(bkeyword);
    basicAttr.setTbRelation(tbrelation);
    basicAttr.setTKeyword(tkeyword);
    basicAttr.setKeywordflag(keywordflag);

    baMgr.addBasicAttr(siteid,basicAttr ,getselectColumns);
    
    response.sendRedirect("index.jsp");
%>