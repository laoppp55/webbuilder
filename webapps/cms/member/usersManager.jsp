<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.service.UsersService" %>
<%@ page import="com.bizwink.po.Users" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.util.filter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.po.Organization" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.vo.VoUser" %>
<%@ page import="com.bizwink.service.DeptService" %>
<%@ page import="com.bizwink.service.CompanyService" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="com.bizwink.cms.security.IRightsManager" %>
<%@ page import="com.bizwink.cms.security.RightsPeer" %>
<%@ page import="com.bizwink.cms.security.Rights" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/26
  Time: 8:46
  To change this template use File | Settings | File Templates.
--%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("/webbuilder/index.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    int currentPage = ParamUtil.getIntParameter(request,"pageno",1);
    boolean doSearchFlag = ParamUtil.getBooleanParameter(request,"searchFlag");
    int flag = ParamUtil.getIntParameter(request,"flag",0);
    int ascdesc = ParamUtil.getIntParameter(request,"ascdesc",0);
    String keyword = ParamUtil.getParameter(request,"keyword");

    //System.out.println("orgid==" + orgid);
    //System.out.println("siteid==" + siteid);
    //System.out.println("currentPage==" + currentPage);
    //System.out.println("doSearchFlag==" + doSearchFlag);
    //System.out.println("flag==" + flag);
    //System.out.println("ascdesc==" + ascdesc);
    //System.out.println("keyword==" + keyword);

    int range = 20;
    int userCount = 0;
    List<Users> users = new ArrayList<Users>();
    DeptService deptService = null;
    CompanyService companyService = null;
    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (appContext!=null) {
        deptService = (DeptService)appContext.getBean("deptService");
        companyService = (CompanyService)appContext.getBean("companyService");
        UsersService usersService = (UsersService)appContext.getBean("usersService");
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        Organization organization = organizationService.getAOrganization(BigDecimal.valueOf(orgid));
        if (organization.getPARENT().intValue()==0) {
            if (!doSearchFlag) {
                userCount = usersService.getTotalUsersCount(BigDecimal.valueOf(siteid));
                users = usersService.getUsersByCustomer(BigDecimal.valueOf(siteid), BigDecimal.valueOf((currentPage - 1) * range + 1), BigDecimal.valueOf(currentPage * range));
            } else {
                //查询用户信息
                userCount = usersService.searchUsersCountByKeyword(keyword,BigDecimal.valueOf(siteid),null);
                users = usersService.searchUsersByKeyword(keyword,BigDecimal.valueOf(siteid), null,BigDecimal.valueOf((currentPage - 1) * range + 1), BigDecimal.valueOf(currentPage * range));
            }
        }else {
            List<Organization> organizations = organizationService.getSubOrgtreeByParant(BigDecimal.valueOf(siteid), BigDecimal.valueOf(orgid));
            List<Integer> orgids = new ArrayList<Integer>();
            for (int ii = 0; ii < organizations.size(); ii++) {
                orgids.add(organizations.get(ii).getID().intValue());
            }
            if (!doSearchFlag) {
                userCount = usersService.getUsersCountByParentOrgID(BigDecimal.valueOf(siteid), orgids);
                users = usersService.getUsersByParentOrgID(BigDecimal.valueOf(siteid), orgids, BigDecimal.valueOf((currentPage - 1) * range + 1), BigDecimal.valueOf(currentPage * range));
            } else {
                //查询用户信息
                userCount = usersService.searchUsersCountByKeyword(keyword,BigDecimal.valueOf(siteid),orgids);
                users = usersService.searchUsersByKeyword(keyword,BigDecimal.valueOf(siteid), orgids, BigDecimal.valueOf((currentPage - 1) * range + 1), BigDecimal.valueOf(currentPage * range));
            }
        }
    }

    IRightsManager rightsMgr = RightsPeer.getInstance();
    List<VoUser> voUsers = new ArrayList<VoUser>();
    for(int ii=0;ii<users.size();ii++) {
        Users user = users.get(ii);
        VoUser voUser = new VoUser();
        voUser.setID(user.getID());
        voUser.setORGID(user.getORGID());
        Companyinfo companyinfo = companyService.getCompanyinfoByCompid(user.getCOMPANYID());
        voUser.setCOMPANYID(user.getCOMPANYID());
        if (companyinfo!=null) voUser.setCOMPANY(companyinfo.getCOMPANYNAME());
        Department department = deptService.getDepartmentByDeptID(user.getDEPTID());
        voUser.setDEPTID(user.getDEPTID());
        if (department!=null) voUser.setDEPARTMENT(department.getCNAME());
        voUser.setNICKNAME(user.getNICKNAME());
        voUser.setUSERID(user.getUSERID());
        voUser.setCREATEDATE(user.getCREATEDATE());
        voUser.setEMAIL(user.getEMAIL());
        voUser.setADDRESS(user.getADDRESS());
        voUser.setMPHONE(user.getMPHONE());
        voUser.setSEX(user.getSEX());
        //判断是否是站点管理员账号，sitemanagerflag==1站点管理员 sitemanagerflag=0普通编辑
        int sitemanagerflag = 0;
        List<Rights> rightsList = rightsMgr.getRights(user.getUSERID());
        for(int jj=0; jj<rightsList.size(); jj++) {
            Rights rights = rightsList.get(jj);
            if (rights.getRightID()==54) {
                sitemanagerflag = 1;
                break;
            }
        }
        voUser.setSITEMANAGERFLAG(sitemanagerflag);
        voUser.setUNLOGIN(user.getUNLOGIN());
        voUsers.add(voUser);
    }

    Map data = new HashMap();
    data.put("USERS",voUsers);
    data.put("PAGENO",currentPage);
    data.put("USERCOUNT",userCount);
    data.put("RANGE",range);
    data.put("ORGID",orgid);
    data.put("SEARCHFLAG",doSearchFlag);
    data.put("FLAG",flag);
    data.put("ASCDESC",ascdesc);

    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    //System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>