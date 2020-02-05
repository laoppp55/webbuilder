package com.bjca.idm.datasynchronize;

import com.bizwink.cms.security.Department;
import com.bizwink.cms.security.IUserManager;
import com.bizwink.cms.security.User;
import com.bizwink.cms.security.UserPeer;
import com.bjca.idm.strategy.RequestProcessor;
import com.bjca.idm.strategy.TransJsonStringToMap;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Title:       SynchronizeDeptInfo
 * Description: 机构同步
 * Company:     BJCA
 * Author:      zangyan
 * Date:        15-10-14
 * Time:        上午09:57
 */
public class SynchronizeInfo extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private  final Logger logger = Logger.getLogger(getClass());
    private int operateID;
    private String queryOrg="/QueryOrganizationVO";
    private String queryUser="/QueryUserInfoDetail";

    /**
     *  synchronizeDeptInfo
     *  operateID    操作码
     *  orgNumber   待同步的机构信息
     *  userType  备用
     *  @return 根据业务实际处理情况返回 true 或 false
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String synType=request.getParameter("synType");
        System.out.println("同步信息--------------------------------------"+synType);
        if(null!=request.getParameter("operateID")&&null!=synType) {
            int synFlag= Integer.parseInt(synType);
            this.operateID = Integer.parseInt(request.getParameter("operateID"));
            switch (synFlag){
                case 0:
                    synchronizeUser(request,response);
                    break;
                case 1:
                    synchronizeDept(request,response);
                    break;
                case 2:
                    synchronizeUser(request,response);
                    synchronizeDept(request,response);
                    break;
                default:
                    break;
            }
        }else {
            response.getOutputStream().write("false".getBytes());
        }


      /*  OrganizationVO vo = new OrganizationVO();
        vo.setOrgNumber(orgNumber);
        Holder<OrganizationVO> organizationVO = new Holder<OrganizationVO>(vo);
        Holder<Response> res = new Holder<Response>();
        int result = service.queryOrganizationVO(operatorId, organizationVO, res);*/

    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    private String getRequestUrl(){
        ResourceBundle bundle = ResourceBundle.getBundle("deploy");
        return bundle.getString("idm.service");
    }
    private String getAuthId(){
        ResourceBundle bundle = ResourceBundle.getBundle("deploy");
        return bundle.getString("idm.userId");
    }

    private Map sendRequest(Map map,String requestUrl) {
        RequestProcessor requestProcessor = new RequestProcessor(map, requestUrl, null).invoke();
        if (requestProcessor.isFailed()) {
            System.out.println("请求失败！");
            this.logger.info("请求失败！");
            return null;
        }
        String result = requestProcessor.getResult();
        System.out.println("result="+result);
        Map requestMap = null;
        try {
            requestMap = new TransJsonStringToMap().readJson2Map(result);
            System.out.println("requestMap="+requestMap.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requestMap;
    }
    private void synchronizeDept(HttpServletRequest request, HttpServletResponse response){
        try {
            IUserManager uMgr = UserPeer.getInstance();
            Department dept;
            Map map = new HashMap();
            String orgNumber = request.getParameter("orgNumber");
            //String requestUrl="http://60.247.77.105:8080/idm/rest/QueryOrganizationVO?orgNumber="+orgNumber+"&authId=93ce5274575740e994a0c458488ce9db";
            //System.out.println(getRequestUrl());
            String requestUrl = getRequestUrl() + queryOrg + "?orgNumber=" + orgNumber +"&authId="+getAuthId();
            //System.out.println(requestUrl);
            switch (operateID) {
                case 41: {
                    //增加机构
                    Map requestMap = sendRequest(map, requestUrl);
                    int status = Integer.parseInt(requestMap.get("status").toString());
                    Map info = (Map) requestMap.get("info");
                    if (status == 0) {
                        //2)更新应用系统中的机构信息
                        //应用系统自己处理
                        //3）返回结果 ，true 或者 false
                        Department department = uMgr.getOneDepartmentInfoByCode(orgNumber);
                        if(department !=null){
                            this.logger.info("operateID:" + operateID + " 机构已存在：" + " 机构编码orgNumber:" + info.get("orgNumber"));
                            response.getOutputStream().write("true".getBytes());
                            break;
                        }else {
                            String orgName = info.get("orgName").toString();
                            String upcode = info.get("orgUpCode").toString();
                            dept = new Department();
                            if(upcode !=null && upcode.length()>0){
                                dept.setUnit(uMgr.getOneDepartmentInfoByCode(upcode).getUnit());
                            }
                            dept.setCname(orgName);
                            dept.setEname(orgNumber);
                            dept.setSiteid(40);
                            uMgr.create(dept);
                            this.logger.info("operateID:" + operateID + " 增加机构成功：" + " 机构编码orgNumber:" + info.get("orgNumber"));
                            response.getOutputStream().write("true".getBytes());
                            break;
                        }
                    } else {
                        //从统一认证查询机构信息失败
                        this.logger.info("增加机构失败!"+"错误信息：" + info.toString());
                        response.getOutputStream().write("false".getBytes());
                        break;
                    }
                }
                case 42: {
                    //修改机构
                    Map requestMap = sendRequest(map, requestUrl);
                    int status = Integer.parseInt(requestMap.get("status").toString());
                    Map info = (Map) requestMap.get("info");
                    if (status == 0) {
                        dept = new Department();
                        dept.setEname(info.get("orgNumber").toString());
                        dept.setCname(info.get("orgName").toString());
                        uMgr.update_departbyUK(dept);
                        this.logger.info("operateID:" + operateID + " 修改机构成功：" + " 机构编码orgNumber:" + info.get("orgNumber"));
                        response.getOutputStream().write("true".getBytes());
                        break;
                    }else{
                        //从统一认证查询机构信息失败
                        this.logger.info("修改机构失败!"+"错误信息：" + info.toString());
                        response.getOutputStream().write("false".getBytes());
                        break;
                    }

                }
                case 43: {
                    //删除机构
                    //根据机构唯一标识删除应用系统中 response.getOutputStream().write("true".getBytes());的机构，然后返回结果 true 或者 false
                    uMgr.deleteDepartment(orgNumber);
                    this.logger.info("operateID:" + operateID + " 删除机构成功：" + " 机构编码orgNumber:" + orgNumber);
                    response.getOutputStream().write("true".getBytes());
                    break;
                }
                default: {
                    response.getOutputStream().write("false".getBytes());
                    break;
                }
            }
        }catch (IOException e){
            this.logger.info(e.getMessage());
        }catch (Exception ex){
            this.logger.info(ex.getMessage());
        }
    }

    private void synchronizeUser(HttpServletRequest request, HttpServletResponse response){
        try {
            IUserManager uMgr = UserPeer.getInstance();
            String userIdCode = request.getParameter("userIdCode");
            //String userType = request.getParameter("userType");
            //System.out.println("userType="+userType);
            System.out.println("operateID="+operateID);
            switch (operateID) {
                case 11: {  //增加、修改、授权用户
                    Map map = new HashMap();
                   // String requestUrl="http://60.247.77.105:8080/idm/rest/QueryUserInfoDetail?userIdCode=a3bb70b89a1e4dbb87ed8d69b1f0100b&authId=93ce5274575740e994a0c458488ce9db";
                   // String url="http://60.247.77.105:8080/idm/rest";//测试环境
                   // String requestUrl = url + queryUser + "?userIdCode=" + userIdCode + "&authId=93ce5274575740e994a0c458488ce9db";
                    String requestUrl = getRequestUrl() + queryUser + "?userIdCode=" + userIdCode + "&authId="+getAuthId();
                    System.out.println(requestUrl);
                    Map requestMap = sendRequest(map, requestUrl);
                    System.out.println(requestMap.toString());
                    int status = Integer.parseInt(requestMap.get("status").toString());
                    Map info = (Map) requestMap.get("info");
                    System.out.println("status="+status);
                    if (status == 0) {
                        //用户类型
                        String userType = info.get("userType").toString();

                        //用户名
                        String userName = info.get("userName").toString();
                        if(userType.equals("2")) {  //单位用户  取unitName
                            userName = info.get("unitName").toString();
                        }
                        //System.out.println("userIdCode："+userIdCode);
                        //所属机构code
                       String orgNumber = ((Map)((List)info.get("orglist")).get(0)).get("orgNumber").toString();
                       System.out.println("orgNumber="+orgNumber);

                       Department dept = uMgr.getOneDepartmentInfoByCode(orgNumber);
                       if(dept !=null) {
                           String department = String.valueOf(dept.getId());
                           User user = uMgr.getUserByIdCode(userIdCode);
                           if (user != null) {
                               user = new User();
                               user.setID(userName);
                               user.setUserIdCode(userIdCode);
                               user.setDepartCode(orgNumber);
                               user.setDepartment(department);
                               user.setNickName(userName);
                               uMgr.updateUserInfoByca(user);
                               System.out.println("用户已存在，userIdCode:" + userIdCode);
                               this.logger.info("用户已存在，更新用户信息:" + userName);
                               response.getOutputStream().write("true".getBytes());
                           } else {
                               user = new User();
                               user.setID(userName);
                               user.setUserIdCode(userIdCode);
                               user.setDepartCode(orgNumber);
                               user.setDepartment(department);
                               uMgr.createByca(user);
                               System.out.println("增加用户信息:" + userIdCode);
                               this.logger.info("增加用户信息：" + userName);
                           }

                           //this.logger.info("用户信息:" + info.toString());
                           response.getOutputStream().write("true".getBytes());
                       }else{
                           System.out.println("机构编码为："+orgNumber+"的机构不存在，需先增加该机构");
                           this.logger.info("机构编码为："+orgNumber+"的机构不存在，需先增加该机构");
                       }
                    }else {
                        System.out.println("错误信息:" + info.toString());
                        //System.out.println("失败，错误码："+responseValue.getErrorCode()+",错误信息："+responseValue.getErrorMsg());
                        this.logger.info("错误信息:" + info.toString());
                        response.getOutputStream().write("false".getBytes());
                    }
                    break;
                }
                case 13: {  // 删除用户  ，然后返回结果 true 或 false
                    uMgr.deleteUser(userIdCode);
                    this.logger.info("operateID:" + operateID + " 删除用户：" + userIdCode);
                    response.getOutputStream().write("true".getBytes());
                    break;
                }

                case 51: {  // 解除用户权限  ，然后返回结果 true 或 false
                    this.logger.info("operateID:" + operateID + " 清除(删除或冻结)用户在应用系统中的权限：" + userIdCode);
                    response.getOutputStream().write("true".getBytes());
                    break;
                }

                default: {
                    response.getOutputStream().write("false".getBytes());
                    break;
                }
            }
        }catch (IOException e){
            this.logger.info(e.getMessage());
        }catch (Exception ex){
            this.logger.info(ex.getMessage());
        }
    }
}
