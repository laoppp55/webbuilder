<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="net.sf.json.JSONObject" %>
<%@ page import="net.sf.json.JSONArray" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.bizwink.error.ErrorMessage" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="java.math.BigDecimal" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 18-1-20
  Time: 下午6:02
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    request.setCharacterEncoding("utf-8");

    String jsondata = ParamUtil.getParameter(request,"compinfos");
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    List<Companyinfo> comps = new ArrayList<Companyinfo>();
    String mphone = null;
    String email = null;
    String compname = null;
    String shortcompname = null;
    String compcode = null;
    String legal = null;
    String tprovince = null;
    String tcity = null;
    String tcounty = null;
    String address = null;
    int compid = 0;

    //保存创建组织架构信息返回的结果，成功创建返回创建的组织架构信息，未成功创建返回失败的原因，失败原因保存在ErrorMessage对象中
    Map result = null;

    if (jsondata!=null) {
        System.out.println(jsondata);
        JSONObject jsStr = JSONObject.fromObject(jsondata);
        JSONArray jsonArray = jsStr.getJSONArray("rows");
        Iterator<Object> it = jsonArray.iterator();
        while (it.hasNext()) {
            JSONObject ob = (JSONObject) it.next();
            mphone = ob.getString("mphone");
            email = ob.getString("email");
            compname = ob.getString("compname");
            shortcompname = ob.getString("shortcompname");
            compcode = ob.getString("compcode");
            legal = ob.getString("legal");
            tprovince = ob.getString("tprovince");
            tcity = ob.getString("tcity");
            tcounty = ob.getString("tcounty");
            address = ob.getString("address");
            compid = ob.getInt("id");

            Companyinfo companyinfo = new Companyinfo();
            companyinfo.setCOMPANYNAME(compname);
            companyinfo.setMPHONE(mphone);
            companyinfo.setLEGAL(legal);
            companyinfo.setCOMPANYEMAIL(email);
            companyinfo.setCOMPANYADDRESS(address);
            companyinfo.setCOMPCODE(compcode);
            companyinfo.setCOMPSHORTNAME(shortcompname);
            companyinfo.setPROVINCE(tprovince);
            companyinfo.setCITY(tcity);
            companyinfo.setCOUNTRY(tcounty);
            companyinfo.setID(BigDecimal.valueOf(compid));
            comps.add(companyinfo);
        }
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
            result = organizationService.updateOrganizationAndCompany(comps,authToken.getSiteID(),authToken.getUserID(),0,orgid);
        }
    }

    Gson gson = new Gson();
    if(result !=null) {
        ErrorMessage errorMessage = (ErrorMessage)result.get("error");
        if (errorMessage.getErrcode()==0) {
            String json_result = gson.toJson(result);
            out.print(json_result);
        } else {
            String json_result = gson.toJson(errorMessage);
            out.print(json_result);
        }
    } else {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(-4);
        errorMessage.setErrmsg("未知的错误，没有返回修改的组织架构结果数据");
        errorMessage.setModelname("修改组织架构模块");
        String json_result = gson.toJson(errorMessage);
        out.print(json_result);
    }
    out.flush();
%>