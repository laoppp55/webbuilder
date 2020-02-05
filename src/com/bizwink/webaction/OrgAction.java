package com.bizwink.webaction;

import com.bizwink.security.Auth;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.Department;
import com.bizwink.po.Organization;
import com.bizwink.service.OrganizationService;
import com.bizwink.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-10-26.
 */
@Controller
public class OrgAction {
    @Autowired
    private OrganizationService orgService;

    @RequestMapping("/OrgAction!saveOrgnizationAndCompany")
    public ModelAndView saveOrgnizationAndCompany(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Auth authToken = SessionUtil.getUserAuthorization(request, response,request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());
        String s_type = request.getParameter("type");
        String username = null;

        if (authCookies == null) { //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            ModelAndView mv = new ModelAndView("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return mv;
        }

        List<Companyinfo> companyinfos = new ArrayList();
        Companyinfo companyinfo = new Companyinfo();

        String mphone = ParamUtil.getParameter(request,"mphone");
        String email = ParamUtil.getParameter(request,"email");
        String compname = ParamUtil.getParameter(request,"compname");
        String shortcompname = ParamUtil.getParameter(request,"shortcompname");
        String compcode = ParamUtil.getParameter(request,"compcode");
        String legal = ParamUtil.getParameter(request,"legal");
        String province = ParamUtil.getParameter(request,"tprovince");
        String city = ParamUtil.getParameter(request,"tcity");
        String county = ParamUtil.getParameter(request,"tcounty");
        String address = ParamUtil.getParameter(request,"address");
        int orgpid = ParamUtil.getIntParameter(request,"pid",0);
        String more_company_info = ParamUtil.getParameter(request, "compinfos");

        companyinfo.setMPHONE(mphone);                          //联系人手机
        companyinfo.setCOMPANYPHONE(mphone);
        companyinfo.setCOMPANYEMAIL(email);                     //联系人电子邮件
        companyinfo.setCOMPANYNAME(compname);                   //公司名称
        companyinfo.setCOMPSHORTNAME(shortcompname);            //公司短名称
        companyinfo.setCOMPCODE(compcode);                       //公司内部编码
        companyinfo.setLEGAL(legal);                             //法人
        companyinfo.setCOUNTRY("086");                          //国家
        companyinfo.setPROVINCE(province);                       //省
        companyinfo.setCITY(city);                               //市
        companyinfo.setZONE(county);                             //区县
        companyinfo.setCOMPANYADDRESS(address);                  //详细地址
        companyinfos.add(companyinfo);

        if (more_company_info!=null) {
            JSONObject jsonObject= JSONObject.fromObject(more_company_info);
            JSONArray theMessage = jsonObject.getJSONArray("rows");

            for(int ii=0;ii<theMessage.size();ii++) {
                JSONObject comp = theMessage.getJSONObject(ii);
                companyinfo = new Companyinfo();
                companyinfo.setMPHONE(mphone);                                             //联系人手机
                companyinfo.setCOMPANYPHONE(mphone);
                companyinfo.setCOMPANYEMAIL(email);                                        //联系人电子邮件
                companyinfo.setCOMPANYNAME(comp.getString("compname"));                   //公司名称
                companyinfo.setCOMPSHORTNAME(comp.getString("shortcompname"));            //公司短名称
                companyinfo.setCOMPCODE(comp.getString("compcode"));                       //公司内部编码
                companyinfo.setLEGAL(legal);                                                //法人
                companyinfo.setCOUNTRY("086");                                             //国家
                companyinfo.setPROVINCE(province);                                          //省
                companyinfo.setCITY(city);                                                  //市
                companyinfo.setZONE(county);                                                //区县
                companyinfo.setCOMPANYADDRESS(address);                                     //详细地址
                companyinfos.add(companyinfo);
            }
        }

        int usertype = authCookies.getUsertype();
        int siteid = authCookies.getSiteid();
        Map result = orgService.saveOrganizationAndCompany(companyinfos,siteid,username,usertype,orgpid);

        ModelAndView mv = new ModelAndView("/org/index");
        return mv;
    }

    @RequestMapping("/OrgAction!saveOrgnizationAndDept")
    public ModelAndView saveOrgnizationAndDept(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Auth authToken = SessionUtil.getUserAuthorization(request, response,request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());
        String s_type = request.getParameter("type");
        String username = null;
        int usertype = 0;

        if (authCookies == null){                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            ModelAndView mv = new ModelAndView("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return mv;
        }

        String phone = ParamUtil.getParameter(request,"phone");
        String email = ParamUtil.getParameter(request,"email");
        String deptname = ParamUtil.getParameter(request,"deptname");
        String shortname = ParamUtil.getParameter(request,"shortname");
        String deptcode = ParamUtil.getParameter(request, "deptcode");
        int orgpid = ParamUtil.getIntParameter(request, "pid", 0);

        Department department = new Department();
        department.setCNAME(deptname);
        department.setTELEPHONE(phone);
        department.setENAME(deptcode);
        department.setSHORTNAME(shortname);
        department.setEMAIL(email);

        int siteid = authCookies.getSiteid();
        Map result = orgService.saveOrganizationAndDept(department, siteid, username, usertype, orgpid);

        ModelAndView mv = new ModelAndView("/org/index");
        return mv;
    }

    @RequestMapping("/OrgAction!getFirstLevelOrgnizations")
    public void getFirstLevelOrgnizations(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Auth authToken = SessionUtil.getUserAuthorization(request, response,request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());
        String s_type = request.getParameter("type");
        if (authCookies == null) { //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            response.sendRedirect("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return;
        }

        int siteid = authCookies.getSiteid();
        StringBuffer orgTreeData = new StringBuffer();
        Organization rootOrganization = orgService.getRootOrganization(BigDecimal.valueOf(siteid));
        //获取组织架构第一级的记录信息
        BigDecimal orgRootID = rootOrganization.getID();
        //获取组织架构的根记录信息
        List<Organization> organizationList = orgService.getFirstLevelOrg(BigDecimal.valueOf(siteid),orgRootID);
        List<Organization> firstLevelOrg = orgService.getOrgByParant(BigDecimal.valueOf(siteid),orgRootID);
        for(int ii=0;ii<firstLevelOrg.size();ii++) {
            organizationList.add(firstLevelOrg.get(ii));
        }
        Organization organization = null;
        if (s_type.equalsIgnoreCase("1")) {                       //1表示生成树表
            orgTreeData.append("{\"total\":" + organizationList.size() + ",\"rows\":[\r\n");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1)) {
                    if (orgRootID==organization.getID())
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                    else {
                        if (organization.getISLEAF().intValue()==1)
                            orgTreeData.append("{\"id\":\""+organization.getID() + "\","  + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"},\r\n");
                        else
                            orgTreeData.append("{\"id\":\""+organization.getID() + "\","  + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                    }
                }else {
                    if (orgRootID==organization.getID())
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
                    else {
                        if (organization.getISLEAF().intValue()==1)
                            orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," +  "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"}\r\n");
                        else
                            orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," +  "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
                    }
                }
            }
//            treedata.append("],\"footer\":[\r\n");
//            treedata.append("{\"name\":\"Total Persons:\",\"persons\":" + (nodes.size()-1) + ",\"iconCls\":\"icon-sum\"}");
            orgTreeData.append("]}");
        } else {                                              //其他值表示生成树
            orgTreeData.append("[");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1))
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                else
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
            }
            orgTreeData.append("]");
        }

        FileUtil.writeTxtFile(orgTreeData.toString(), new File("/usr/local/log/uc16.txt"));
        JSON.setPrintWriter(response, orgTreeData.toString(),"utf-8");
    }

    @RequestMapping("/OrgAction!getOrgByParant")
    public void getOrgByParant(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String s_pcode = request.getParameter("pid");
        String s_type = request.getParameter("type");
        String vcode = request.getParameter("verifycode");

        Auth authToken = SessionUtil.getUserAuthorization(request, response,request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());

        if (authCookies == null) {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            response.sendRedirect("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return;
        }

        StringBuffer orgTreeData = new StringBuffer();
        int siteid = authCookies.getSiteid();
        int orgid = authCookies.getOrgid();
        List<Organization> organizationList = orgService.getOrgByParant(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
        Organization organization = null;
        if (s_type.equalsIgnoreCase("1")) {                       //1表示生成树表
            orgTreeData.append("{\"total\":" + organizationList.size() + ",\"rows\":[\r\n");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1)){
                    if (organization.getISLEAF().intValue()==1)
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"},\r\n");
                    else
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                }else{
                    if (organization.getISLEAF().intValue()==1)
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"}\r\n");
                    else
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
                }
            }
//            treedata.append("],\"footer\":[\r\n");
//            treedata.append("{\"name\":\"Total Persons:\",\"persons\":" + (nodes.size()-1) + ",\"iconCls\":\"icon-sum\"}");
            orgTreeData.append("]}");
        } else {                                              //其他值表示生成树
            orgTreeData.append("[");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1))
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                else
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
            }
            orgTreeData.append("]");
        }

        JSON.setPrintWriter(response, orgTreeData.toString(),"utf-8");
    }

    @RequestMapping("/OrgAction!getSubNodes")
    public void getSubNodes(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String s_pcode = request.getParameter("pid");
        String s_type = request.getParameter("type");
        String vcode = request.getParameter("verifycode");
        Auth authToken = SessionUtil.getUserAuthorization(request, response,request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());

        if (authCookies == null) {                        //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            response.sendRedirect("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return;
        }

        System.out.println("type==" + s_type);

        int siteid = authCookies.getSiteid();
        int orgid = authCookies.getOrgid();
        StringBuffer orgTreeData = new StringBuffer();
        Organization organization = null;
        if (s_type.equalsIgnoreCase("1")) {                       //1表示生成树表
            BigDecimal pcode = BigDecimal.valueOf(Integer.parseInt(s_pcode));
            List<Organization> organizationList = orgService.getOrgByParant(BigDecimal.valueOf(siteid),pcode);
            //{parent: node.id,data: [{id: '073',name: 'name73'}]}
            orgTreeData.append("{\"parent\":\"" + s_pcode + "\",\"data\": [\r\n");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1)){
                    if (organization.getISLEAF().intValue()==1)
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"},\r\n");
                    else
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                }else{
                    if (organization.getISLEAF().intValue()==1)
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\"," + "\"state\":\"closed\"," + "\"verifycode\":\"" + verifycode + "\"}\r\n");
                    else
                        orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"_parentId\":" + "\""+organization.getPARENT().intValue() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"orgtype\":"+"\""+(organization.getCOTYPE().intValue()==1?"公司":"部门") +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
                }
            }
            orgTreeData.append("]}");
        } else {                                              //其他值表示生成树
            List<Organization> organizationList = orgService.getSubOrgtreeByParant(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
            System.out.println("size===" + organizationList.size());
            orgTreeData.append("[");
            for(int ii=0; ii<organizationList.size(); ii++) {
                organization = organizationList.get(ii);
                String verifycode = SecurityUtil.Encrypto("customer=" + siteid + "&pid=" + organization.getID() + "&type=1");
                if (ii<(organizationList.size()-1))
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"pId\":"+"\""+organization.getPARENT().intValue() +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"},\r\n");
                else
                    orgTreeData.append("{\"id\":\""+organization.getID() + "\"," + "\"name\":" +"\""+organization.getNAME()+"\"," +"\"pId\":"+"\""+organization.getPARENT().intValue() +"\","  + "\"orderid\":" + "\"" + organization.getORDERID().intValue() + "\",\"verifycode\":\"" + verifycode + "\"}\r\n");
            }
            orgTreeData.append("]");
        }

        FileUtil.writeTxtFile(orgTreeData.toString(), new File("/usr/local/log/uc18.txt"));
        JSON.setPrintWriter(response, orgTreeData.toString(),"utf-8");
    }
}