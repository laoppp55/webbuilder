package com.bizwink.cms.toolkit.company;

import java.sql.Timestamp;

/**
 * Created by petersong on 15-10-20.
 */
public class Meetting_sign_part {
    private int id;                       //number,              --主键ID
    private int signid;                  //number,              --注册培训会主表ID
    private String name;                  //varchar2(50),        --培训人姓名
    private String depttitle;            //varchar2(50),        --培训人所在部门和职务
    private String mobilephone;          //varchar2(11),        --培训人手机号码
    private String fax;                   //varchar2(15),        --培训人传真号码
    private String email;                 //varchar2(50),        --培训人电子邮件地址
    private Timestamp meettingtime;      //date,                --培训会时间
    private String meetingaddress;      //varchar2(200),       --培训会地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSignid() {
        return signid;
    }

    public void setSignid(int signid) {
        this.signid = signid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepttitle() {
        return depttitle;
    }

    public void setDepttitle(String depttitle) {
        this.depttitle = depttitle;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getMeettingtime() {
        return meettingtime;
    }

    public void setMeettingtime(Timestamp meettingtime) {
        this.meettingtime = meettingtime;
    }

    public String getMeetingaddress() {
        return meetingaddress;
    }

    public void setMeetingaddress(String meetingaddress) {
        this.meetingaddress = meetingaddress;
    }
}
