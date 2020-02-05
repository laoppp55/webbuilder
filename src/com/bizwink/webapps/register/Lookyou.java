package com.bizwink.webapps.register;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-8-9
 * Time: 下午10:43
 * To change this template use File | Settings | File Templates.
 */
public class Lookyou {
    private long userid;               //用户ID，主键
    private String username;           //用户真实姓名
    private String email;              //用户电子邮件地址，是用户的登录名
    private String password;           //用户口令，md5加密保存
    private String photo;              //头像
    private String summary;            //个人介绍
    private String phone;              //联系电话
    private String mphone;             //手机号码
    private String im;                  //及时通讯地址
    private Timestamp birthdate;       //生日
    private String address;             //联系地址
    private int maritalstatus;        //婚姻状态
    private String nursery;            //幼儿园名称
    private String prischool;          //小学名称
    private String middleschool;      //中学名称
    private String highschool;         //高中名称
    private String university;         //大学名称
    private String privince;           //省名称
    private String city;                //城市名称
    private Timestamp createdate;      //创建日期和时间


    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaritalstatus() {
        return maritalstatus;
    }

    public void setMaritalstatus(int maritalstatus) {
        this.maritalstatus = maritalstatus;
    }

    public String getNursery() {
        return nursery;
    }

    public void setNursery(String nursery) {
        this.nursery = nursery;
    }

    public String getPrischool() {
        return prischool;
    }

    public void setPrischool(String prischool) {
        this.prischool = prischool;
    }

    public String getMiddleschool() {
        return middleschool;
    }

    public void setMiddleschool(String middleschool) {
        this.middleschool = middleschool;
    }

    public String getHighschool() {
        return highschool;
    }

    public void setHighschool(String highschool) {
        this.highschool = highschool;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPrivince() {
        return privince;
    }

    public void setPrivince(String privince) {
        this.privince = privince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
