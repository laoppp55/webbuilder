package com.bizwink.webaction;

import com.bizwink.error.ErrorMessage;
import com.bizwink.security.Auth;
import com.bizwink.persistence.CompanyinfoMapper;
import com.bizwink.persistence.DepartmentMapper;
import com.bizwink.persistence.OrganizationMapper;
import com.bizwink.persistence.UsersMapper;
import com.bizwink.po.Companyinfo;
import com.bizwink.po.Department;
import com.bizwink.po.Users;
import com.bizwink.vo.VoUser;
import com.bizwink.util.FileUtil;
import com.bizwink.util.JSON;
import com.bizwink.util.ParamUtil;
import com.bizwink.util.SessionUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-11-1.
 */
@Controller
public class UserAction {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private CompanyinfoMapper companyinfoMapper;
    @Autowired
    private DepartmentMapper departmentMapper;

    @RequestMapping("/UserAction!getUsersByCustomer")
    public void getUsersByCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        Auth authToken = SessionUtil.getUserAuthorization(request, response, request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());
        if (authCookies == null) { //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            response.sendRedirect("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return;
        }

        BigDecimal customerid = BigDecimal.valueOf(authCookies.getSiteid());
        BigDecimal orgid = BigDecimal.valueOf(authCookies.getOrgid());
        ErrorMessage errorMessage = new ErrorMessage();
        List<Users> usersList = usersMapper.getUsersByCustomer(customerid);

        List<VoUser> showusers = new ArrayList();
        if (usersList!=null) {
            Users users = null;
            Companyinfo companyinfo = null;
            Department department = null;
            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for(int ii=0;ii<usersList.size(); ii++) {
                users = usersList.get(ii);
                VoUser vouser = new VoUser();
                vouser.setID(users.getID());
                vouser.setUSERID(users.getUSERID());
                vouser.setSITEID(users.getSITEID());
                vouser.setNICKNAME(users.getNICKNAME());
                vouser.setEMAIL(users.getEMAIL());
                vouser.setMPHONE(users.getMPHONE());
                vouser.setUSERTYPE(users.getUSERTYPE());
                vouser.setCREATEDATE(users.getCREATEDATE());
                BigDecimal companyid = users.getCOMPANYID();
                String company_name = "";
                if (companyid!=null) {
                    companyinfo = companyinfoMapper.selectByPrimaryKey(users.getCOMPANYID());
                    if (companyinfo!=null) company_name = companyinfo.getCOMPANYNAME();
                    vouser.setCOMPANY(company_name);
                }
                vouser.setADDRESS(users.getADDRESS());
                BigDecimal deptid = users.getDEPTID();
                String deptname = "";
                if (deptid!=null) {
                    department = departmentMapper.selectByPrimaryKey(users.getDEPTID());
                    if (department!=null) deptname = department.getCNAME();
                    vouser.setDEPARTMENT(deptname);
                }
                Timestamp createdate = users.getCREATEDATE();

                vouser.setORGID(users.getORGID());
                vouser.setCOMPANYID(users.getCOMPANYID());
                vouser.setDEPTID(users.getDEPTID());
                vouser.setCREATERID(users.getCREATERID());
                showusers.add(vouser);
            }
            errorMessage.setErrcode(0);
            errorMessage.setErrmsg("成功读取用户列表");
            errorMessage.setModelname("用户管理");
        } else {
            errorMessage.setErrcode(-1);
            errorMessage.setErrmsg("未能获得用户列表信息");
            errorMessage.setModelname("用户管理");
        }

        Map result = new HashMap();
        result.put("usersdata",showusers);
        result.put("error",errorMessage);
        JSONObject json = JSONObject.fromObject(result);
        JSON.setPrintWriter(response, json.toString(),"utf-8");
    }

    @RequestMapping("/UserAction!saveUserInfo")
    public void saveUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        Auth authToken = SessionUtil.getUserAuthorization(request, response, request.getSession());
        Auth authCookies = SessionUtil.getUserAuthCookies(request, response,request.getSession());
        if (authCookies == null) { //COOKIE值是空，说明用户已经退出登录状态，检查SESSION的值是否存在，如果存在需要清除SESSION
            if (authToken != null) {
                SessionUtil.removeUserAuthorization(response,request.getSession());
            }
            response.sendRedirect("/members/login.jsp?tt=" + URLEncoder.encode("用户未登录，请先登录系统", "utf-8"));
            return;
        }

        BigDecimal customerid = BigDecimal.valueOf(authCookies.getSiteid());
        BigDecimal orgid = BigDecimal.valueOf(authCookies.getOrgid());
        ErrorMessage errorMessage = new ErrorMessage();

        String mphone = ParamUtil.getParameter(request, "mphone");
        String email = ParamUtil.getParameter(request,"email");
        String username = ParamUtil.getParameter(request,"username");
        String pwd = ParamUtil.getParameter(request,"pwd");
        String repwd = ParamUtil.getParameter(request, "repwd");
        String province = ParamUtil.getParameter(request,"tprovince");
        String city = ParamUtil.getParameter(request,"tcity");
        String county = ParamUtil.getParameter(request,"tcounty");
        String yzcode = ParamUtil.getParameter(request, "yzcode");
        int orgtype = ParamUtil.getIntParameter(request,"orgtype",0);

        BigDecimal companyid = getCompanyIDByOrgID(customerid, orgid);

        Users user = new Users();

    }

    private BigDecimal getCompanyIDByOrgID(BigDecimal customerid,BigDecimal orgid) {
        Map params = new HashMap();
        params.put("SITEID",customerid);
        params.put("ORGID",orgid);
        List<Companyinfo> companyinfoList = companyinfoMapper.getCompanyInfosByOrgid(params);

        return null;
    }

}
