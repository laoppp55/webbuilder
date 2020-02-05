package com.bizwink.cms.toolkit.companyinfo;

import java.sql.Timestamp;

/**
 * Created by petersong on 15-10-20.
 */
public class Meettings {
    private int    ID;                     //number,              --培训会主键ID
    private int    siteid;                //number,              --培训会站点id
    private String  meetingname;          //varchar2(200),       --培训会名称
    private Timestamp meetingdatetime;    //date,                --培训会的日期和时间
    private String address;                //varchar2(10),        --培训会地址
    private Timestamp createdate;         //date,                --创建日期和时间
    private String editor;                 //varchar2(50),        --创建人

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMeetingname() {
        return meetingname;
    }

    public void setMeetingname(String meetingname) {
        this.meetingname = meetingname;
    }

    public Timestamp getMeetingdatetime() {
        return meetingdatetime;
    }

    public void setMeetingdatetime(Timestamp meetingdatetime) {
        this.meetingdatetime = meetingdatetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }
}
