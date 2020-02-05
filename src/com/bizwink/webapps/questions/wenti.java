package com.bizwink.webapps.questions;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-11-27
 * Time: 14:03:34
 * To change this template use File | Settings | File Templates.
 */
public class wenti {
    private int id;                                      //主键
    private int columnid;                               //问题所属分类
    private String cname;                                //问题所属分类中文名称
    private String titles;                               // 问题名称
    private String question;                            //问题描述
    private int status;                                 //问题状态
    private int voteflag;                               //是否需要投票
    private int xuanshang;                              //悬赏分数
    private int anwsernum;                              //答案数目
    private String source;                               //问题来源
    private Timestamp createdate;                       //提问时间
    private String ipaddress;                           //提问者的IP地址
    private String creater;                             //提问者名字
    private String province;                            //提问者所在省
    private String city;                                 //提问者所在市
    private String area;                                 //提问者所在地区
    private String picpath;                              //提问者上传图片路径
    private String filepath;                             //上传文件保存路径
    private int emailnotify;                            //是否需要电子邮件提示
    private String email;                               //邮件地址
    private int userid;                                 //提问者用户ID
    private String username;                            //提问者用户名
    private int anwStatus;                              //是否过期  1——没有回答但问题已经过期不再接受回答 0--没有过期
    private String userid_hd;						    //指定专家的USERID


    public String getUserid_hd() {
        return userid_hd;
    }

    public void setUserid_hd(String userid_hd) {
        this.userid_hd = userid_hd;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColumnid() {
        return columnid;
    }

    public void setColumnid(int columnid) {
        this.columnid = columnid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getVoteflag() {
        return voteflag;
    }

    public void setVoteflag(int voteflag) {
        this.voteflag = voteflag;
    }

    public int getXuanshang() {
        return xuanshang;
    }

    public void setXuanshang(int xuanshang) {
        this.xuanshang = xuanshang;
    }

    public int getAnwsernum() {
        return anwsernum;
    }

    public void setAnwsernum(int anwsernum) {
        this.anwsernum = anwsernum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    public int getEmailnotify() {
        return emailnotify;
    }

    public void setEmailnotify(int emailnotify) {
        this.emailnotify = emailnotify;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public int getAnwStatus() {
        return anwStatus;
    }

    public void setAnwStatus(int anwStatus) {
        this.anwStatus = anwStatus;
    }

}
