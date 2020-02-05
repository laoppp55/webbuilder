<%@ page import="com.bizwink.cms.util.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.xml.XMLProperties" contentType="text/html;charset=GBK"%>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect("../login.jsp?url=member/editGroup.jsp");
        return;
    }
    if (authToken.getUserID().compareToIgnoreCase("admin") != 0) {
        request.setAttribute("message", "无系统管理员的权限");
        response.sendRedirect("../index.jsp");
        return;
    }

    IRegisterManager regMgr = RegisterPeer.getInstance();
    IColumnManager columnManager = ColumnPeer.getInstance();

    boolean error = false;
    String errormsg = "";
    String extname;
    int pic;
    int tcflag;
    int wapflag;
    int pubflag;
    int bindflag;
    int tagSite = 0;
    int listShow = 0;
    int beRefered;
    int copyColumn;
    int beCopyColumn;
    int pushArticle;
    int moveArticle;

    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    int siteID = ParamUtil.getIntParameter(request, "siteid", 0);

    if (doUpdate) {
        extname = ParamUtil.getParameter(request, "extname");
        pic = ParamUtil.getIntParameter(request, "pic", 0);
        tcflag = ParamUtil.getIntParameter(request, "tcflag", 0);
        wapflag = ParamUtil.getIntParameter(request, "wapflag", 0);
        pubflag = ParamUtil.getIntParameter(request, "pubflag", 0);
        bindflag = ParamUtil.getIntParameter(request, "bindflag", 0);
        tagSite = ParamUtil.getIntParameter(request, "tagSite", 0);
        listShow = ParamUtil.getIntParameter(request, "listShow", 0);
        beRefered = ParamUtil.getIntParameter(request, "beRefered", 0);
        copyColumn = ParamUtil.getIntParameter(request, "copyColumn", 0);
        beCopyColumn = ParamUtil.getIntParameter(request, "beCopyColumn", 0);
        pushArticle = ParamUtil.getIntParameter(request, "pushArticle", 0);
        moveArticle = ParamUtil.getIntParameter(request, "moveArticle", 0);

        StringBuffer configInfo = new StringBuffer();
        configInfo.append("<Config>");
        configInfo.append("<GlobalTag>" + tagSite + "</GlobalTag>");
        configInfo.append("<PublishListShow>" + listShow + "</PublishListShow>");
        configInfo.append("</Config>");

        try {
            Register register = new Register();
            register.setSiteID(siteID);
            register.setImagesDir(pic);
            register.setTCFlag(tcflag);
            register.setWapFlag(wapflag);
            register.setPubFlag(pubflag);
            register.setBindFlag(bindflag);
            register.setExtName(extname);
            register.setBeRefered(beRefered);
            register.setCopyColumn(copyColumn);
            register.setBeCopyColumn(beCopyColumn);
            register.setPushArticle(pushArticle);
            register.setMoveArticle(moveArticle);
            register.setConfigInfo(configInfo.toString());

            regMgr.update(register);
            errormsg = "站点修改成功！";
        }
        catch (Exception e) {
            e.printStackTrace();
            errormsg = "站点修改失败！";
            error = true;
        }
    }

    if (doUpdate) {
        out.println("<script language=javascript>");
        if (error) out.println("alert('" + errormsg + "');");
        out.println("opener.history.go(0);");
        out.println("window.close();");
        out.println("</script>");
        return;
    }

    //读出该站点信息
    Register register = regMgr.getSite(siteID);
    extname = columnManager.getIndexExtName(siteID);
    String configInfo = register.getConfigInfo();
    if (configInfo != null && configInfo.length() > 0) {
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gb2312\"?>" + configInfo);
        tagSite = Integer.parseInt(properties.getProperty("GlobalTag"));
        listShow = Integer.parseInt(properties.getProperty("PublishListShow"));       
    }
    copyColumn = register.getCopyColumn();
    beCopyColumn = register.getBeCopyColumn();
    pushArticle = register.getPushArticle();
    moveArticle = register.getMoveArticle();
%>

<html>
<head>
    <meta http-equiv=Content-Type content="text/html; charset=gb2312">
    <link rel=stylesheet type=text/css href=../style/global.css>
    <title>站点修改</title>
</head>

<body bgcolor=#cccccc>
<div align="center">
    <form method="POST" action="editSite.jsp" name="RegForm">
        <input type=hidden name="doUpdate" value="true">
        <input type=hidden name="siteid" value="<%=siteID%>">
        <table border="1" width="95%" cellpadding="0" cellspacing="0" borderColorDark=#ffffec borderColorLight=#5e5e00
               align=center>
            <tr>
                <td colspan=2 width="100%" bgcolor="#006699" align="center" height="16"><font color="#FFFFFF"><b>所有选项必须正确填写</b></font>
                </td>
            </tr>
            <tr height="32">
                <td width="35%" align="right">图像存储方式：</td>
                <td width="65%">
                    <input type="radio" value="0" name="pic" <%if(register.getImagesDir()==0){%>checked<%}%>>根目录images下
                    <input type="radio" value="1" name="pic" <%if(register.getImagesDir()==1){%>checked<%}%>>各栏目images下
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持繁体：</td>
                <td>
                    <input type="radio" value="0" name="tcflag" <%if(register.getTCFlag()==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="tcflag" <%if(register.getTCFlag()==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持WAP：</td>
                <td>
                    <input type="radio" value="0" name="wapflag" <%if(register.getWapFlag()==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="wapflag" <%if(register.getWapFlag()==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">文章发布方式：</td>
                <td><input type="radio" value="0" name="pubflag" <%if(register.getPubFlag()==0){%>checked<%}%>>手动&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="pubflag" <%if(register.getPubFlag()==1){%>checked<%}%>>自动
                </td>
            </tr>
            <tr height="32">
                <td align="right">发布列表显示规则：</td>
                <td><input type="radio" value="0" name="listShow" <%if(listShow==0){%>checked<%}%>>文章在前
                    <input type="radio" value="1" name="listShow" <%if(listShow==1){%>checked<%}%>>模板在前
                </td>
            </tr>
            <tr height="32">
                <td align="right">文章是否被其它站点引用：</td>
                <td><input type="radio" value="0" name="beRefered" <%if(register.getBeRefered()==0){%>checked<%}%>>禁止引用
                    <input type="radio" value="1" name="beRefered" <%if(register.getBeRefered()==1){%>checked<%}%>>支持引用
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章引用：</td>
                <td><input type="radio" value="0" name="tagSite" <%if(tagSite==0){%>checked<%}%>>不支持&nbsp;&nbsp;
                    <input type="radio" value="1" name="tagSite" <%if(tagSite==1){%>checked<%}%>>支持
                </td>
            </tr>
            <tr height="32">
                <td align="right">栏目是否被其它站点复制：</td>
                <td><input type="radio" value="0" name="beCopyColumn" <%if(beCopyColumn==0){%>checked<%}%>>禁止复制
                    <input type="radio" value="1" name="beCopyColumn" <%if(beCopyColumn==1){%>checked<%}%>>支持复制
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间栏目复制：</td>
                <td><input type="radio" value="0" name="copyColumn" <%if(copyColumn==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="copyColumn" <%if(copyColumn==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章推送：</td>
                <td><input type="radio" value="0" name="pushArticle" <%if(pushArticle==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="pushArticle" <%if(pushArticle==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">支持站间文章移动：</td>
                <td><input type="radio" value="0" name="moveArticle" <%if(moveArticle==0){%>checked<%}%>>否&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="1" name="moveArticle" <%if(moveArticle==1){%>checked<%}%>>是
                </td>
            </tr>
            <tr height="32">
                <td align="right">站点状态：</td>
                <td><input type="radio" value="1" name="bindflag" <%if(register.getBindFlag()==1){%>checked<%}%>>激活&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="radio" value="0" name="bindflag" <%if(register.getBindFlag()==0){%>checked<%}%>>暂停
                </td>
            </tr>
            <tr height="32">
                <td align="right">
                    首页扩展名：
                </td>
                <td>&nbsp;<select name=extname size=1 class=tine>
                    <option value="html"  <%=(extname.compareTo("html") == 0) ? "selected" : ""%>>html</option>
                    <option value="htm"   <%=(extname.compareTo("htm") == 0) ? "selected" : ""%>>htm</option>
                    <option value="jsp"   <%=(extname.compareTo("jsp") == 0) ? "selected" : ""%>>jsp</option>
                    <option value="asp"   <%=(extname.compareTo("asp") == 0) ? "selected" : ""%>>asp</option>
                    <option value="shtm"  <%=(extname.compareTo("shtm") == 0) ? "selected" : ""%>>shtm</option>
                    <option value="shtml" <%=(extname.compareTo("shtml") == 0) ? "selected" : ""%>>shtml</option>
                    <option value="php"   <%=(extname.compareTo("php") == 0) ? "selected" : ""%>>php</option>
                    <option value="wml"   <%=(extname.compareTo("wml") == 0) ? "selected" : ""%>>wml</option>
                </select>
                </td>
            </tr>
        </table>
        <p><input type="submit" value=" 保存 ">&nbsp;&nbsp;&nbsp;
            <input type="button" value=" 取消 " onclick="window.close();"></p>
    </form>
</div>

</body>
</html>
