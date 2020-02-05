package com.bizwink.cms.security;

public class Auth {
    private String userID;
    private String nickname;
    private String sitename;
    private int siteid;
    private int samsiteid;
    private int sitetype;
    private int sharetemplatenum;
    private int trypassnum = 0;
    private int errcode = 0;
    private PermissionSet permissionSet;
    private RolesSet roleSet;
    private int imgSaveFlag;
    private int cssjsDir;
    private int publishFlag;
    private int listShow;
    private int tagSite;
    private int copyColumn;
    private int beCopyColumn;
    private int pushArticle;
    private int moveArticle;
    private int orgid;
    private int companyid;
    private int deptid;
    private String company;
    private String department;
    private String emailaccount;
    private String emailpasswd;

    public Auth() {

    }

    public Auth(String company,String department,String emailaccount,String emailpasswd,String userID,String nickname, String sitename, int siteid, int samsiteid,int sitetype,int sharetemplatenum,int imgFlag, int cssjsDir, int publishFlag, int listShow,
                int errcode,int tagSite, int copyColumn, int beCopyColumn, int pushArticle, int moveArticle,int orgid,int companyid,int deptid,PermissionSet permissionSet,RolesSet roleSet) {
        this.company = company;
        this.userID = userID;
        this.nickname = nickname;
        this.siteid = siteid;
        this.sitename = sitename;
        this.imgSaveFlag = imgFlag;
        this.cssjsDir = cssjsDir;
        this.publishFlag = publishFlag;
        this.permissionSet = permissionSet;
        this.roleSet = roleSet;
        this.listShow = listShow;
        this.tagSite = tagSite;
        this.copyColumn = copyColumn;
        this.beCopyColumn = beCopyColumn;
        this.pushArticle = pushArticle;
        this.moveArticle = moveArticle;
        this.samsiteid = samsiteid;
        this.sitetype = sitetype;
        this.sharetemplatenum = sharetemplatenum;
        this.emailaccount = emailaccount;
        this.emailpasswd = emailpasswd;
        this.orgid = orgid;
        this.department = department;
        this.companyid = companyid;
        this.deptid = deptid;
        this.errcode = errcode;
    }

    public String getUserID() {
        return userID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSitename() {
        return sitename;
    }

    public String getEmailaccount() {
        return emailaccount;
    }

    public String getEmailpasswd() {
        return emailpasswd;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }

    public int getImgSaveFlag() {
        return imgSaveFlag;
    }

    public void setImgSaveFlag(int flag) {
        this.imgSaveFlag = flag;
    }

    public int getCssJsDir() {
        return cssjsDir;
    }

    public void setCssJsDir(int cssjsDir) {
        this.cssjsDir = cssjsDir;
    }

    public int getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(int publishFlag) {
        this.publishFlag = publishFlag;
    }

    public int getListShow() {
        return listShow;
    }

    public int getTagSite() {
        return tagSite;
    }

    public int getCopyColumn() {
        return copyColumn;
    }

    public int getBeCopyColumn() {
        return beCopyColumn;
    }

    public int getPushArtile() {
        return pushArticle;
    }

    public int getMoveArticle() {
        return moveArticle;
    }

    public PermissionSet getPermissionSet() {
        return permissionSet;
    }

    public RolesSet getRoleSet() {
        return roleSet;
    }

    public int getSamSiteid() {
        return samsiteid;
    }

    public int getSitetype() {
        return sitetype;
    }

    public int getShareTemplatenum() {
        return sharetemplatenum;
    }

    public int getTrypassnum() {
        return trypassnum;
    }

    public void setTrypassnum(int trypassnum) {
        this.trypassnum = trypassnum;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}