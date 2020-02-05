<%@ page import="java.util.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=gbk"
%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.util.JSON" %>
<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%@ include file="../include/auth.jsp"%>
<%
    if (!SecurityCheck.hasPermission(authToken, 54)) {
        response.sendRedirect("../error.jsp?message=无管理用户的权限");
        return;
    }

    boolean error = false;
    int errcode = 0;
    int siteID = authToken.getSiteID();
    String username = authToken.getUserID();
    String userid    = ParamUtil.getParameter(request,"userid");
    String password  = ParamUtil.getParameter(request,"passwd");
    String realname  = ParamUtil.getParameter(request,"realname");
    String mphone = ParamUtil.getParameter(request,"mphone");
    String phone = ParamUtil.getParameter(request,"phone");
    String email = ParamUtil.getParameter(request,"email");
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    String rightids  = ParamUtil.getParameter(request,"rightids");
    boolean doCreate = ParamUtil.getBooleanParameter(request,"doCreate");

    ApplicationContext appContext = SpringInit.getApplicationContext();
    if (doCreate) {
        if (userid == null || password == null) error = true;
    }

    if (!error && doCreate) {
        IUserManager userMgr = UserPeer.getInstance();
        try {
            List<Companyinfo> companyinfos = new ArrayList<Companyinfo>();
            List<Department> departments = new ArrayList<Department>();
            if (appContext!=null) {
                OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
                departments = organizationService.getADepartmentByOrgid(BigDecimal.valueOf(siteID),BigDecimal.valueOf(orgid));
                companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteID),BigDecimal.valueOf(orgid));
            }
            String[] right_ids = rightids.split(",");
            List<String> rightList = new ArrayList<>();
            for(int ii=0; ii<right_ids.length; ii++) {
                rightList.add(right_ids[ii]);
            }
            boolean IsExit = userMgr.existUser(userid,siteID);
            if (!IsExit) {
                User newUser = new User();
                newUser.setID(userid);
                newUser.setSiteid(siteID);
                newUser.setPassword(password);
                newUser.setNickName(realname);
                newUser.setMobilePhone(mphone);
                newUser.setPhone(phone);
                newUser.setEmail(email);
                newUser.setRightList(rightList);
                newUser.setOrgid(orgid);
                if (departments.size()>=1) {
                    newUser.setDepartment(departments.get(0).getCNAME());
                    newUser.setDeptid(departments.get(0).getID().intValue());
                }else {
                    newUser.setDepartment("");
                    newUser.setDeptid(0);
                }
                if (companyinfos.size()>=1) {
                    newUser.setCompany(companyinfos.get(0).getCOMPANYNAME());
                    newUser.setCompanyid(companyinfos.get(0).getID().intValue());
                }else {
                    newUser.setCompany("");
                    newUser.setCompanyid(0);
                }
                errcode = userMgr.create(newUser,username);
            }
        } catch (Exception e) {
            errcode = -2;
            e.printStackTrace();
        }
    }

    Map data = new HashMap();
    data.put("ERRCODE",errcode);
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    String jsondata = gson.toJson(data);
    System.out.println(jsondata);
    JSON.setPrintWriter(response,jsondata,"utf-8");
%>