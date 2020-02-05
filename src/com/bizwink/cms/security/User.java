package com.bizwink.cms.security;

import java.sql.Timestamp;
import java.util.*;

public class User
{
    private int siteid;                      //站点ID
    private String userID;                   //用户登陆ID
    private String password;                 //该用户的登陆密码
    private String nickName;                 //呢称
    private String email;                    //邮件
    private String qq;
    private String weixin;
    private String weibo;
    private String website;
    private String phone;                    //电话号码
    private String mobilePhone;
    private String fax;
    private String myimage;
    private int usertype;
    private int sex;
    private String postcode;
    private int createarticles;                       //创建文章数
    private int editarticles;                         //修改文章数
    private int deletearticles;                       //删除文章数
    private List rightList = new ArrayList();          //用户可以管理的栏目id
    private String address;                             //公司所在地址
    private String company;                             //用户所在公司
    private String department;                         //用户所在部门
    private List rolesList = new ArrayList();          //用户角色
    private String lw;                                   //用户作为留言表管理员可以管理的留言表的ID列表
    private String deptlw;                              //用户作为留言表的部门管理员可以管理的留言表的ID列表
    private String emailaccount;
    private String emailpasswd;
    private int departmentarticlestype;
    private String departmentarticlesids;
    private int trypassnum;
    private Timestamp trypasstime;
    private int orgid;                                  //用户所属组织架构ID
    private int companyid;                             //用户所属公司ID
    private int deptid;                                //用户所属部门ID
    private int uid;
    private String userIdCode;
    private String departCode;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getID()
    {
        return userID;
    }

    public void setID(String userID)
    {
        this.userID = userID;
    }

    public int getSiteID()
    {
        return siteid;
    }

    public void setSiteID(int siteid)
    {
        this.siteid = siteid;
    }

    public List getRightList()
    {
        return rightList;
    }

    public void setRightList(List rightList)
    {
        this.rightList = rightList;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getMobilePhone()
    {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone)
    {
        this.mobilePhone = mobilePhone;
    }

    public String getMyimage() {
        return myimage;
    }

    public void setMyimage(String myimage) {
        this.myimage = myimage;
    }

    public void setSiteid(int siteid)
    {
        this.siteid = siteid;
    }

    public int getSiteid()
    {
        return siteid;
    }

    public int getCreatearticles() {
        return createarticles;
    }

    public void setCreatearticles(int createarticles) {
        this.createarticles = createarticles;
    }

    public int getEditarticles() {
        return editarticles;
    }

    public void setEditarticles(int editarticles) {
        this.editarticles = editarticles;
    }

    public int getDeletearticles() {
        return deletearticles;
    }

    public void setDeletearticles(int deletearticles) {
        this.deletearticles = deletearticles;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List getRolesList() {
        return rolesList;
    }

    public void setRolesList(List rolesList) {
        this.rolesList = rolesList;
    }

    public String getEmailaccount() {
        return emailaccount;
    }

    public void setEmailaccount(String emailaccount) {
        this.emailaccount = emailaccount;
    }

    public String getEmailpasswd() {
        return emailpasswd;
    }

    public void setEmailpasswd(String emailpasswd) {
        this.emailpasswd = emailpasswd;
    }

    public int getDepartmentarticlestype() {
        return departmentarticlestype;
    }

    public void setDepartmentarticlestype(int departmentarticlestype) {
        this.departmentarticlestype = departmentarticlestype;
    }

    public String getDepartmentarticlesids() {
        return departmentarticlesids;
    }

    public void setDepartmentarticlesids(String departmentarticlesids) {
        this.departmentarticlesids = departmentarticlesids;
    }

    public String getLw() {
        return lw;
    }

    public void setLw(String lw) {
        this.lw = lw;
    }

    public String getDeptlw() {
        return deptlw;
    }

    public void setDeptlw(String deptlw) {
        this.deptlw = deptlw;
    }

    public int getTrypassnum() {
        return trypassnum;
    }

    public void setTrypassnum(int trypassnum) {
        this.trypassnum = trypassnum;
    }

    public Timestamp getTrypasstime() {
        return trypasstime;
    }

    public void setTrypasstime(Timestamp trypasstime) {
        this.trypasstime = trypasstime;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getOrgid() {
        return orgid;
    }

    public void setOrgid(int orgid) {
        this.orgid = orgid;
    }

    public int getCompanyid() {
        return companyid;
    }

    public void setCompanyid(int companyid) {
        this.companyid = companyid;
    }

    public int getDeptid() {
        return deptid;
    }

    public void setDeptid(int deptid) {
        this.deptid = deptid;
    }

    public String getUserIdCode() {
        return userIdCode;
    }

    public void setUserIdCode(String userIdCode) {
        this.userIdCode = userIdCode;
    }

    public String getDepartCode() {
        return departCode;
    }

    public void setDepartCode(String departCode) {
        this.departCode = departCode;
    }
}