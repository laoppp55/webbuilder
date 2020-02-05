package com.bizwink.cms.sitesetting;

public class FtpInfo
{
    private int ID;
    private int siteid;          //站点的ID
    private String ip;           //站点的IP
    private String ftpuser;      //登陆该IP的ftp 用户名
    private String ftppwd;       //该ftp用户对应的密码
    private String docpath;      //在该IP上发布时该站点的根目录
    private int publishway;      //发布方式
    private int status;          //是否文章发布站点
    private String siteName;     //站点名称
    private int ftptype;         //FTP类型  0-普通FTP  1-SFTP

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public int getSiteid()
    {
        return siteid;
    }

    public void setSiteid(int siteid)
    {
        this.siteid = siteid;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public String getIp()
    {
        return ip;
    }

    public void setFtpuser(String ftpuser)
    {
        this.ftpuser = ftpuser;
    }

    public String getFtpuser()
    {
        return ftpuser;
    }

    public void setFtppwd(String ftppwd)
    {
        this.ftppwd = ftppwd;
    }

    public String getFtppwd()
    {
        return ftppwd;
    }

    public void setDocpath(String docpath)
    {
        this.docpath = docpath;
    }

    public String getDocpath()
    {
        return docpath;
    }

    public void setPublishway(int publishway)
    {
        this.publishway = publishway;
    }

    public int getPublishway()
    {
        return publishway;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }

    public void setSiteName(String siteName)
    {
        this.siteName = siteName;
    }

    public String getSiteName()
    {
        return siteName;
    }

    public int getFtptype() {
        return ftptype;
    }

    public void setFtptype(int ftptype) {
        this.ftptype = ftptype;
    }
}