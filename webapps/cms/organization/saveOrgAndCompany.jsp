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
<%@ page import="java.net.URLDecoder" %>
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

    String jsondata = URLDecoder.decode(ParamUtil.getParameter(request,"compinfos"),"utf-8");
    System.out.println(jsondata);
    int pid = ParamUtil.getIntParameter(request,"pid",0);
    List<Companyinfo> comps = new ArrayList<Companyinfo>();
    String mphone = null;             //公司联系人手机号码
    String email = null;              //公司联系人电子邮件
    String compname = null;           //公司名称
    String shortcompname = null;      //公司简称
    String compcode = null;           //公司内部编码
    String legal = null;              //公司负责人姓名
    String tprovince = null;          //公司所在省
    String tcity = null;              //公司所在市
    String tcounty = null;            //公司所在县
    String address = null;            //公司详细地址
    String postcode = "";             //公司邮政编码
    String contactor = "";            //公司联系人
    String phone = "";                //公司联系电话号码
    String engcompname = "";          //公司英文名称
    String creditcode = "";           //公司统一社会信用代码
    String comptype = "";             //公司类型

    //保存创建组织架构信息返回的结果，成功创建返回创建的组织架构信息，未成功创建返回失败的原因，失败原因保存在ErrorMessage对象中
    Map result = null;

    if (jsondata!=null) {
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
            postcode = ob.getString("postcode");
            contactor = ob.getString("contactor");
            phone = ob.getString("phone");
            engcompname = ob.getString("engcompname");
            creditcode = ob.getString("creditcode");
            comptype = ob.getString("comptype");

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
            companyinfo.setPOSTCODE(postcode);
            companyinfo.setCONTACTOR(contactor);
            companyinfo.setCOMPANYPHONE(phone);
            companyinfo.setCOMPENGNAME(engcompname);
            companyinfo.setCOMPCREDITCODE(creditcode);
            companyinfo.setCOMPTYPE(comptype);

            comps.add(companyinfo);
        }
        ApplicationContext appContext = SpringInit.getApplicationContext();
        if (appContext!=null) {
            OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
            result = organizationService.saveOrganizationAndCompany(comps,authToken.getSiteID(),authToken.getUserID(),0,pid);
        }
    }

    Gson gson = new Gson();
    if(result !=null) {
        ErrorMessage errorMessage = (ErrorMessage)result.get("error");
        if (errorMessage.getErrcode()==0) {
            String json_result = gson.toJson(result);
            System.out.println(json_result);
            out.print(json_result);
        } else {
            String json_result = gson.toJson(errorMessage);
            System.out.println(json_result);
            out.print(json_result);
        }
    } else {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setErrcode(-4);
        errorMessage.setErrmsg("未知的错误，没有返回创建的组织架构结果数据");
        errorMessage.setModelname("创建组织架构模块");
        String json_result = gson.toJson(errorMessage);
        System.out.println(json_result);
        out.print(json_result);
    }
    out.flush();
%>