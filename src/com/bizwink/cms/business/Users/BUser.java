package com.bizwink.cms.business.Users;

import java.sql.*;


public class BUser
{
    private int id;
    private String userid;
    private String realname;
    private String password;
    private String username;
    private int sex;
    private String address;
    private String postcode;
    private int province;
    private int city;
    private int zone;
    private int country;
    private String email;
    private String phone;
    private String nightphone;
    private String mobilephone;
    private String fax;
    private String shenfenzheng;
    private int level;
    private int score;
    private int lockflag;
    private int revenue;
    private int birthday_year;
    private int birthday_month;
    private int birthday_day;
    private String occupation;
    private Timestamp createdate;
    private float prepay;
    private int education;
    private String forsave;
    private String forread;
    private String ip;
    private int loginnum;
    private String introduce;
    private String linkman;
    private int siteid;
    private int subscribe;
    private int valid;

    public BUser(){
    }

    public int getID(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    //for userid
    public String getUserID(){
        return userid;
    }

    public void setUserID(String userid){
        this.userid = userid;
    }

    public int getValid(){
        return valid;
    }

    public void setValid(int valid){
        this.valid=valid;
    }

    /*  public int getReceiveFlag(){
        return receiveflag;
      }

      public void setReceiveFlag(int receiveflag){
        this.receiveflag=receiveflag;
      }
    */
    public int getSubScribe(){
        return subscribe;
    }

    public void setSubScribe(int subscribe){
        this.subscribe=subscribe;
    }

    //for realname
    public String getRealName(){
        return realname;
    }

    public void setRealName(String realname){
        this.realname = realname;
    }

    //for linkman
    public String getLinkMan(){
        return linkman;
    }

    public void setLinkMan(String linkman){
        this.linkman=linkman;
    }


    //for password
    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    //for username
    public String getUserName(){
        return username;
    }

    public void setUserName(String username){
        this.username = username;
    }

    //for sex
    public int getSex(){
        return sex;
    }

    public void setSex(int sex){
        this.sex = sex;
    }

    //for address
    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    //for postcode
    public String getPostCode(){
        return postcode;
    }

    public void setPostCode(String postcode){
        this.postcode = postcode;
    }

    //for province
    public int getProvince(){
        return province;
    }

    public void setProvince(int province){
        this.province = province;
    }

    //for city
    public int getCity(){
        return city;
    }

    public void setCity(int city){
        this.city = city;
    }

    //for zone
    public int getZone(){
        return zone;
    }
    public void setZone(int zone){
        this.zone=zone;
    }

    //for country
    public int getCountry(){
        return country;
    }

    public void setCountry(int country){
        this.country = country;
    }

    //for email
    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    //for phone
    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    //for nightphone
    public String getNightPhone(){
        return nightphone;
    }

    public void setNightPhone(String nightphone){
        this.nightphone = nightphone;
    }

    //for mobilephone
    public String getMobilePhone(){
        return mobilephone;
    }

    public void setMobilePhone(String mobilephone){
        this.mobilephone = mobilephone;
    }

    public String getFax(){
        return fax;
    }

    public void setFax(String fax){
        this.fax=fax;
    }

    //for shenfenzheng
    public String getShenFenZheng(){
        return shenfenzheng;
    }

    public void setShenFenZheng(String shenfenzheng){
        this.shenfenzheng = shenfenzheng;
    }

    //for level
    public int getLevel(){
        return level;
    }

    public void setLevel(int level){
        this.level = level;
    }

    //for score
    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    //for lockflag
    public int getLockFlag(){
        return lockflag;
    }

    public void setLockFlag(int lockflag){
        this.lockflag = lockflag;
    }

    //for revenue
    public int getRevenue(){
        return revenue;
    }

    public void setRevenue(int revenue){
        this.revenue = revenue;
    }

    //for birthday_year
    public int getBirthday_year(){
        return birthday_year;
    }

    public void setBirthday_year(int birthday_year){
        this.birthday_year = birthday_year;
    }

    //for birthday_month
    public int getBirthday_month(){
        return birthday_month;
    }

    public void setBirthday_month(int birthday_month){
        this.birthday_month = birthday_month;
    }

    //for birthday_day
    public int getBirthday_day(){
        return birthday_day;
    }

    public void setBirthday_day(int birthday_day){
        this.birthday_day = birthday_day;
    }

    //for occupation
    public String getOccupation(){
        return occupation;
    }

    public void setOccupation(String occupation){
        this.occupation = occupation;
    }

    //for createdate
    public Timestamp getCreateDate(){
        return createdate;
    }

    public void setCreateDate(Timestamp createdate){
        this.createdate = createdate;
    }

    //for prepay
    public float getPrepay(){
        return prepay;
    }

    public void setPrepay(float prepay){
        this.prepay = prepay;
    }

    //for education
    public int getEducation(){
        return education;
    }

    public void setEducation(int education){
        this.education = education;
    }

    //for forsave
    public String getForSave(){
        return forsave;
    }

    public void setForSave(String forsave){
        this.forsave = forsave;
    }

    //for forread
    public String getForRead(){
        return forread;
    }

    public void setForRead(String forread){
        this.forread = forread;
    }

    //for ip
    public String getIP(){
        return ip;
    }

    public void setIP(String ip){
        this.ip = ip;
    }

    //for loginnum
    public void setLoginNum(int loginnum){
        this.loginnum = loginnum;
    }

    public int getLoginNum() {
        return loginnum;
    }

    public String getIntroduce(){
        return introduce;
    }

    //for introduce
    public void setIntroduce(String introduce){
        this.introduce=introduce;
    }

    public int getSiteid()
    {
        return siteid;
    }

    public void setSiteid(int siteid)
    {
        this.siteid = siteid;
    }
}
