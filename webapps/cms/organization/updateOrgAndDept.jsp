<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="com.bizwink.util.filter" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-5-2
  Time: 下午9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    String username = authToken.getUserID();
    int usertype = 0;
    request.setCharacterEncoding("utf-8");

    String deptname = filter.excludeHTMLCode(ParamUtil.getParameter(request, "deptname"));
    String shortname = filter.excludeHTMLCode(ParamUtil.getParameter(request,"shortname"));
    String ename = filter.excludeHTMLCode(ParamUtil.getParameter(request,"ename"));
    String deptcode = filter.excludeHTMLCode(ParamUtil.getParameter(request,"deptcode"));
    String phone = filter.excludeHTMLCode(ParamUtil.getParameter(request,"phone"));
    String mphone = filter.excludeHTMLCode(ParamUtil.getParameter(request,"phone"));
    String email = filter.excludeHTMLCode(ParamUtil.getParameter(request,"email"));
    String contactor = filter.excludeHTMLCode(ParamUtil.getParameter(request,"contactor"));
    String indexrules = filter.excludeHTMLCode(ParamUtil.getParameter(request,"indrules"));
    int indexstartnum = ParamUtil.getIntParameter(request, "indstartnum", 0);
    int deptid = ParamUtil.getIntParameter(request, "id", 0);
    int orgid = ParamUtil.getIntParameter(request, "orgid", 0);

    Timestamp now = new Timestamp(System.currentTimeMillis());
    Department department = new Department();
    department.setCNAME(deptname);
    department.setSHORTNAME(shortname);
    department.setENAME(ename);
    department.setDepartcode(deptcode);
    department.setEMAIL(email);
    department.setTELEPHONE(phone);
    department.setMphone(mphone);
    department.setLEADER(contactor);
    department.setIndexrules(indexrules);
    department.setIndexnum(indexstartnum);
    department.setLASTUPDATE(now);
    department.setORGID(BigDecimal.valueOf(orgid));
    department.setID(BigDecimal.valueOf(deptid));
    Map result = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        result = organizationService.updateOrganizationAndDept(department,siteid,username,usertype,deptid);
    }

    Gson gson = new Gson();
    if (result!=null) {
        ErrorMessage errorMessage = (ErrorMessage)result.get("error");
        if (errorMessage.getErrcode()==0) {
            String json_result = gson.toJson(result);
            System.out.println(json_result);
            out.print(json_result);

        } else {
            String json_result = gson.toJson(errorMessage);
            out.print(json_result);
        }
    } else {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(-4);
        errorMessage.setErrmsg("修改组织架构记录部门信息出现错误");
        errorMessage.setModelname("修改组织架构");
        String jsondata = gson.toJson(errorMessage);
        out.print(jsondata);
    }

%>