<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%@ page import="com.bizwink.po.Companyinfo" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/createMember.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    boolean error = false;
    String errormsg = "";
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    int currentpage = ParamUtil.getIntParameter(request, "pageno", 0);
    int range = ParamUtil.getIntParameter(request, "range", 0);
    int doSearchFlag = ParamUtil.getIntParameter(request, "searchflag", 0);

    String userID = null;
    String password = null;
    String extname = null;
    String SiteName = null;
    String companyname = null;
    String contactor = null;
    String mphone = null;
    int pic = -1;
    int tcflag = -1;
    int wapflag = 0;
    int encoding = 1;         //默认页面编码格式为UTF-8
    int pubflag = -1;
    int cssjsdir= 0;

    String titlepic = "";
    String vtitlepic = "";
    String sourcepic = "";
    String authorpic = "";
    String contentpic = "";
    String specialpic = "";
    String productpic = "";
    String productsmallpic = "";
    String mediasize = "";
    String mediapicsize= "";

    if (doCreate) {
        companyname = ParamUtil.getParameter(request, "companyname");;
        contactor = ParamUtil.getParameter(request, "contactor");;
        mphone = ParamUtil.getParameter(request, "mphone");;
        userID = ParamUtil.getParameter(request, "userid");
        password = ParamUtil.getParameter(request, "password1");
        extname = ParamUtil.getParameter(request, "extname");
        SiteName = ParamUtil.getParameter(request, "SiteName");
        pic = ParamUtil.getIntParameter(request, "pic", -1);
        cssjsdir = ParamUtil.getIntParameter(request, "cssjsdir", 0);
        encoding = ParamUtil.getIntParameter(request, "encoding", 1);
        tcflag = ParamUtil.getIntParameter(request, "tcflag", -1);
        wapflag = ParamUtil.getIntParameter(request, "wapflag", 0);
        pubflag = ParamUtil.getIntParameter(request, "pubflag", -1);
        if(ParamUtil.getParameter(request,"titlepicheight")!=null && ParamUtil.getParameter(request,"titlepicwidth")!=null){
            titlepic = ParamUtil.getParameter(request,"titlepicheight") + "X" + ParamUtil.getParameter(request,"titlepicwidth");
        }
        if(ParamUtil.getParameter(request,"vtitlepicheight") != null &&  ParamUtil.getParameter(request,"vtitlepicwidth")!= null){
            vtitlepic = ParamUtil.getParameter(request,"vtitlepicheight") + "X" + ParamUtil.getParameter(request,"vtitlepicwidth");
        }
        if(ParamUtil.getParameter(request,"sourcepicheight") != null && ParamUtil.getParameter(request,"sourcepicwidth") != null){
            sourcepic = ParamUtil.getParameter(request,"sourcepicheight") + "X" + ParamUtil.getParameter(request,"sourcepicwidth");
        }
        if(ParamUtil.getParameter(request,"authorpicheight") != null && ParamUtil.getParameter(request,"authorpicwidth") != null){
            authorpic = ParamUtil.getParameter(request,"authorpicheight") + "X" + ParamUtil.getParameter(request,"authorpicwidth");
        }
        if(ParamUtil.getParameter(request,"contentpicheight")!=null && ParamUtil.getParameter(request,"contentpicwidth")!=null){
            contentpic = ParamUtil.getParameter(request,"contentpicheight") + "X" + ParamUtil.getParameter(request,"contentpicwidth");
        }
        if(ParamUtil.getParameter(request,"specialpicheight") != null && ParamUtil.getParameter(request,"specialpicwidth") != null){
            specialpic = ParamUtil.getParameter(request,"specialpicheight") + "X" + ParamUtil.getParameter(request,"specialpicwidth");
        }
        if(ParamUtil.getParameter(request,"productpicheight") != null && ParamUtil.getParameter(request,"productpicwidth") != null) {
            productpic = ParamUtil.getParameter(request,"productpicheight") + "X" + ParamUtil.getParameter(request,"productpicwidth");
        }
        if(ParamUtil.getParameter(request,"productsmallpicheight") != null && ParamUtil.getParameter(request,"productsmallpicwidth") !=null){
            productsmallpic = ParamUtil.getParameter(request,"productsmallpicheight") + "X" + ParamUtil.getParameter(request,"productsmallpicwidth");
        }

        if(ParamUtil.getParameter(request,"mediaheight") != null && ParamUtil.getParameter(request,"mediawidth") !=null){
            mediasize = ParamUtil.getParameter(request,"mediaheight") + "X" + ParamUtil.getParameter(request,"mediawidth");
        }

        if(ParamUtil.getParameter(request,"mediapicheight") != null && ParamUtil.getParameter(request,"mediapicwidth") !=null){
            mediapicsize = ParamUtil.getParameter(request,"mediapicheight") + "X" + ParamUtil.getParameter(request,"mediapicwidth");
        }

        if (userID == null || password == null || SiteName == null ||
                pic == -1 || tcflag == -1 || extname == null) {
            error = true;
            errormsg = "站点添加失败！";
        }
    }

    if (doCreate && !error) {
        try {
            IRegisterManager regMgr = RegisterPeer.getInstance();

            //先判断有无相同域名
            boolean isExit1 = regMgr.QuerySiteName(SiteName.trim());
            boolean isExit2 = regMgr.queryUsername(userID.trim());
            if (isExit1) {
                errormsg = "该域名已被注册，请换其它域名！";
                out.println("<script language=javascript>");
                out.println("alert('" + errormsg + "');");
                out.println("history.go(-1);");
                out.println("</script>");
                return;
            } else if (isExit2) {
                errormsg = "该用户名已被注册，请换其它用户名！";
                out.println("<script language=javascript>");
                out.println("alert('" + errormsg + "');");
                out.println("history.go(-1);");
                out.println("</script>");
                return;
            }

            Register register = new Register();
            register.setUserID(userID.trim());
            register.setUsername(userID.trim());
            register.setPassword(password);
            register.setExtName(extname);
            register.setSiteName(SiteName);
            register.setImagesDir(pic);
            register.setCssjsDir(cssjsdir);
            register.setTCFlag(tcflag);
            register.setWapFlag(wapflag);
            register.setEncoding(encoding);
            register.setPubFlag(pubflag);
            register.setBindFlag(1);
            register.setTitlepic(titlepic);
            register.setVtitlepic(vtitlepic);
            register.setSourcepic(sourcepic);
            register.setAuthorpic(authorpic);
            register.setContentpic(contentpic);
            register.setSpecialpic(specialpic);
            register.setProductpic(productpic);
            register.setProductsmallpic(productsmallpic);
            register.setMediasize(mediasize);
            register.setMediapicsize(mediapicsize);

            Companyinfo companyinfo = new Companyinfo();
            companyinfo.setCOMPANYNAME(companyname);
            companyinfo.setCONTACTOR(contactor);
            companyinfo.setMPHONE(mphone);

            regMgr.create(register,companyinfo);

            //创建文章列表图标
            //regMgr.copyIconToCMS(siteID, SiteName, request.getRealPath("/"));

            errormsg = "站点已成功添加！";
        }
        catch (Exception e) {
            e.printStackTrace();
            errormsg = "站点添加失败！";
        }
    }

    if (doCreate) {
%>
<script language=javascript>
    alert("<%=errormsg%>");
    window.opener.userInfoList(<%=range%>,<%=currentpage%>,<%=doSearchFlag%>);
    window.close();
</script>
<%
    }
%>
