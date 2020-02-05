package com.bizwink.cms.security;

import java.util.*;
import com.bizwink.cms.util.*;
import com.bizwink.webapps.leaveword.WordException;

public interface IUserManager
{
    //创建新用户
    int create(User user,String opuser) throws CmsException;

    //更新用户属性，其中,userID用于在数据库中定位纪录，故不能更新userID
    int update(User user,String opuser) throws CmsException;

    //根据userID和siteid更新用户密码
    void updatePassword(String userID, int siteid, String password,String opuser) throws CmsException;

    //修改用户头像信息
    int updateUserHeadImg(int siteid,String imgfile,String userid,String opuser) throws CmsException;

    //修改用户类型
    int updateUserType(int siteid,int usertype,String userid,String opuser) throws CmsException;

    void update_departbyUK(Department dept)  throws CmsException;

    void update_membersDepart(String ename, String useridcode)  throws CmsException;

    //删除用户
    int remove(User user,String opuser) throws CmsException;

    //批量删除用户
    int batchDelUsers(int siteid,String uids,String opuser) throws CmsException;

    //批量禁止或者许可用户登录
    int unloginUser(int siteid,String userid,String opuser,int loginval) throws CmsException;

    //批量禁止或者许可用户登录
    int batchUnloginUsers(int siteid,String uids,String opuser,String loginflag) throws CmsException;

    //获取某个站点下的用户总数，其中，siteID=-1表示获取所有站点下的用户总数
    int getUserCount(int siteid) throws CmsException;

    //获取某个站点下特定用户的信息,其中userid,siteid 不能为空
    User getUser(String userID, int siteid) throws CmsException;

    User getUser(String userID) throws CmsException;

    User getUserByUID(int uid, int siteid) throws CmsException;

    //获取某个站点下的从特定记录数间的用户列表,if siteid=-1 表示获取所有站点下的用户
    List getUsers(int startIndex, int numResults, int siteid) throws CmsException;

    //获取某个站点下的用户列表,if siteid=-1 表示获取所有站点下的用户
    List getUsers(int siteid) throws CmsException;

    //获取某个组织的用户列表
    List getUsersByOrgid(int siteid,int orgid) throws CmsException;

    //获取某个公司的用户列表
    List<User> getUsersByCompanyid(int siteid,int companyid) throws CmsException;

    //获取某个部门的用户列表
    List<User> getUsersByDeptid(int siteid,int deptid) throws CmsException;

    //获取特定用户所在的所有用户组
    GroupSet getUserGroups(String userID) throws CmsException;

    //更新特定用户的用户组别
    void updateUserGroups(String userID, GroupSet groupSet) throws CmsException;

    //如果存在该用户，则返回true，该用户不存在，则返回false
    boolean existUser(String userID, int siteid) throws CmsException;

    //获取特定用户的密码，其中userid,siteid 不能为空
    String getPasswd(String userID, int siteid) throws CmsException;

    GroupSet getAllGroups(int siteID) throws CmsException;

    void updateUserInfo(User user) throws CmsException;

    void create(Department dept) throws CmsException;

    void update(Department dept) throws CmsException;

    List getDepartments(int siteid) throws CmsException;

    List getMemberRolesList(String userid);

    int deleteDepartment(int id);

    int deleteDepartment(String departCode);

    Department getOneDepartmentInfoByCode(String departCode);

    User getUserByIdCode(String userIDcode) throws CmsException;

    void updateUserInfoByca(User user) throws CmsException;

    int deleteUser(String userIdcode);

    void createByca(User user) throws CmsException;

    Department getOneDepartmentInfoById(int id);

    List getDepartments(String ids);

    String getUserDepartmentName(String userid);

    List getRemainDepartments(String ids,int siteID);

    List getUserInfoByDepartmentIds(String ids);

    String getUserByRole(String rolename,int deptid,int siteid) throws CmsException;

    String getRoleByUser(String userid,int siteid) throws CmsException;

    int getDeptidByUser(String userid,int siteid) throws CmsException;

    List getUsersBYDepartment(String deptid,int siteID,int lwid,String rolename);

    List getEmployeesBYDepartmentID(int deptid,int siteID);

    List getWebUsers(int siteid) throws CmsException;

    List getWebRoles() throws CmsException;

    User getByIduser(String id);

    Role getByIdrole(String id);
}