<%--
  Created by IntelliJ IDEA.
  User: petersong
  Date: 16-6-19
  Time: 上午10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="com.bizwink.dao.TemplateManager" %>
<%@ page import="com.bizwink.po.Template" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.SecurityUtil" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.dubboservice.service.TemplateService" %>
<%@ page import="java.util.List" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String username = null;

    //入股SESSION失效，试试COOKIE是否可以读出来
    Cookie[] cookies = request.getCookies();
    String cookie_value = null;
    for(Cookie c :cookies ){
        if (c.getName().equalsIgnoreCase("AuthInfo_cookie")) {
            SecurityUtil securityUtil = new SecurityUtil();
            cookie_value = securityUtil.detrypt(c.getValue(),null);
            break;
        }
    }

    int retcode = 0;
    if (cookie_value != null) {
        int columnid = ParamUtil.getIntParameter(request, "columnid", 0);
        int siteid =  ParamUtil.getIntParameter(request, "siteid", 0);
        String content = filter.excludeHTMLCode(ParamUtil.getParameter(request, "content"));
        String topicname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "topicname"));
        String cname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "cname"));
        System.out.println("siteid=" + siteid);
        System.out.println("columnid=" + columnid);

        int posi = content.toLowerCase().indexOf("<body>");
        if (posi>-1) {
            content=content.substring(posi + "<body>".length());
            posi = content.indexOf("</body>");
            if (posi>-1) content = content.substring(0,posi);
        } else{
            posi = content.indexOf("</body>");
            if (posi>-1) content = content.substring(0,posi);
        }
        ApplicationContext appContext = SpringInit.getApplicationContext();
        TemplateService templateService = (TemplateService)appContext.getBean("TemplateService");
        List<Template> templates = templateService.getTemplateByColumnidAndSiteidAndTemplateType(BigDecimal.valueOf(siteid), BigDecimal.valueOf(columnid), BigDecimal.valueOf(3));
        posi = cookie_value.indexOf("-");
        username = cookie_value.substring(0, posi);
        if (templates.size() > 0) {
            Template t1 = templates.get(0);
            TemplateManager templateManager = new TemplateManager();
            Template template = new Template();
            template.setSITEID(BigDecimal.valueOf(siteid));
            template.setCOLUMNID(BigDecimal.valueOf(columnid));
            template.setCONTENT(content);
            template.setEDITOR(username);
            template.setID(t1.getID());
            template.setTEMPLATENAME(topicname);
            template.setCHNAME(cname);
            retcode = templateManager.UpdateTemplate(template);
        } else {
            TemplateManager templateManager = new TemplateManager();
            Template template = new Template();
            template.setSITEID(BigDecimal.valueOf(siteid));
            template.setCOLUMNID(BigDecimal.valueOf(columnid));
            template.setCONTENT(content);
            template.setEDITOR(username);
            template.setTEMPLATENAME(topicname);
            template.setCHNAME(cname);
            retcode = templateManager.CreateTemplate(template);
        }
    } else {
        retcode = -4;
    }

    ErrorMessage errorMessage = new ErrorMessage();
    if (retcode == -1) {
        errorMessage.setErrcode(retcode);
        errorMessage.setErrmsg("数据库操作出现错误");
        errorMessage.setModelname("采用DAO服务写入数据库程序出现错误");
    } else if (retcode == -2) {
        errorMessage.setErrcode(retcode);
        errorMessage.setErrmsg("获取模板表的主键出现错误");
        errorMessage.setModelname("采用DAO服务写入数据库程序出现错误");
    } else if (retcode == -3) {
        errorMessage.setErrcode(retcode);
        errorMessage.setErrmsg("COOKIE为空，没有登录系统");
        errorMessage.setModelname("写入模板表的农家乐介绍JSP页面出现错误");
    } else if (retcode == -4) {
        errorMessage.setErrcode(retcode);
        errorMessage.setErrmsg("获取发布队列表的主键出现错误");
        errorMessage.setModelname("采用DAO服务写入数据库程序出现错误");
    } else {
        errorMessage.setErrcode(retcode);
        errorMessage.setErrmsg("操作成功");
        errorMessage.setModelname("农家乐介绍页面");
    }

    Gson gson = new Gson();
    String jsondata = gson.toJson(errorMessage);
    out.print(jsondata);
    out.flush();
%>