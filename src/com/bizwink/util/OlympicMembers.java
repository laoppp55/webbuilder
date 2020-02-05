package com.bizwink.util;

import java.sql.Timestamp;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2011-10-31
 * Time: 22:36:56
 * To change this template use File | Settings | File Templates.
 */
public class OlympicMembers {
    private String username;                 //varchar2(50),
    private String password;                 //varchar2(20),
    private String name_py;                  //varchar2(50),
    private String name_cn;                  //varchar2(50),
    private String name_cy;                  //varchar2(50),
    private String prename;                  //varchar2(50),
    private int sex;                         //smallint              default(0),
    private Timestamp birthdate;            //    date,
    private String country;                  //varchar2(50),
    private String nationality;
    private String area;                     //varchar2(100),
    private String officetelephone;        //  varchar2(13),
    private String hometelephone;           // varchar2(13),
    private String mobilephone;              //varchar2(11),
    private String email;                    //varchar2(200),
    private String ethnic;                   //varchar2(50),
    private String placeofbirth;             //varchar2(50),
    private Timestamp timeforjoincp;         //   date,
    private Timestamp timeofstartwork;       //   date,
    private int memberofcpc;                 //smallint               default(0),
    private String idfromcountry;            //varchar2(50),
    private String idnum;                     //varchar2(50),
    private String idtype;                      //smallint               default(0),
    private Timestamp idvaliddate;           //   date,
    private String contactpersonname;        //varchar2(50),
    private String contactpersonrelation;    //varchar2(50),
    private String contactpersonphone;       //varchar2(13),
    private String yjcountry;                //varchar2(50),
    private String yjprovince;               //varchar2(50),
    private String yjcity;                   //varchar2(5),
    private String yjaddress;                //varchar2(1000),
    private String cjcountry;                //varchar2(50),
    private String cjprovince;               //varchar2(50),
    private String cjcity;                   //varchar2(50),
    private String cjaddress;                //varchar2(1000),
    private String membertype;               //人员类型   新增加
    private String maincategory;             //主类别  新增加
    private String subcategory;              //子类别  新增加
    private String mainfunc;                 //主要职责  新增加
    private String regnum;                   //注册号  新增加


    private int age;
    private String company;
    private String title;
    private String address;
    private String postcode;
    private String deptOfOlympic;
    private String memberTypeOfOlympic;
    private int education;                   //最高学历   0-下学 1-中学 2-高中 3-职业学院 4-中专 5-大专 6-大学 7-研究生 8-博士
    private String eduinfo;                   //教育经历
    private String traninginfo;              //培训经理
    private String workinfo;                 //工作经历
    private Timestamp startdate;             //到奥组委开始时间
    private Timestamp enddate;               //奥委会工作结束时间
    private String titleInOlympic;          //在奥组委的职务
    private String skills;                   //职业技能
    private String photo;                    //人员照片
    private String location;                //奥运期间所在场馆或组织
    private int talentflag;                //重要人才标志  0-普通 1-重要
    private int authflag;                  //人员被认证标志   0-未认证  1-认证

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName_py() {
        return name_py;
    }

    public void setName_py(String name_py) {
        this.name_py = name_py;
    }

    public String getName_cn() {
        return name_cn;
    }

    public void setName_cn(String name_cn) {
        this.name_cn = name_cn;
    }

    public String getName_cy() {
        return name_cy;
    }

    public void setName_cy(String name_cy) {
        this.name_cy = name_cy;
    }

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOfficetelephone() {
        return officetelephone;
    }

    public void setOfficetelephone(String officetelephone) {
        this.officetelephone = officetelephone;
    }

    public String getHometelephone() {
        return hometelephone;
    }

    public void setHometelephone(String hometelephone) {
        this.hometelephone = hometelephone;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public Timestamp getTimeforjoincp() {
        return timeforjoincp;
    }

    public void setTimeforjoincp(Timestamp timeforjoincp) {
        this.timeforjoincp = timeforjoincp;
    }

    public Timestamp getTimeofstartwork() {
        return timeofstartwork;
    }

    public void setTimeofstartwork(Timestamp timeofstartwork) {
        this.timeofstartwork = timeofstartwork;
    }

    public int getMemberofcpc() {
        return memberofcpc;
    }

    public void setMemberofcpc(int memberofcpc) {
        this.memberofcpc = memberofcpc;
    }

    public String getIdfromcountry() {
        return idfromcountry;
    }

    public void setIdfromcountry(String idfromcountry) {
        this.idfromcountry = idfromcountry;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getIdtype() {
        return idtype;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public Timestamp getIdvaliddate() {
        return idvaliddate;
    }

    public void setIdvaliddate(Timestamp idvaliddate) {
        this.idvaliddate = idvaliddate;
    }

    public String getContactpersonname() {
        return contactpersonname;
    }

    public void setContactpersonname(String contactpersonname) {
        this.contactpersonname = contactpersonname;
    }

    public String getContactpersonrelation() {
        return contactpersonrelation;
    }

    public void setContactpersonrelation(String contactpersonrelation) {
        this.contactpersonrelation = contactpersonrelation;
    }

    public String getContactpersonphone() {
        return contactpersonphone;
    }

    public void setContactpersonphone(String contactpersonphone) {
        this.contactpersonphone = contactpersonphone;
    }

    public String getYjcountry() {
        return yjcountry;
    }

    public void setYjcountry(String yjcountry) {
        this.yjcountry = yjcountry;
    }

    public String getYjprovince() {
        return yjprovince;
    }

    public void setYjprovince(String yjprovince) {
        this.yjprovince = yjprovince;
    }

    public String getYjcity() {
        return yjcity;
    }

    public void setYjcity(String yjcity) {
        this.yjcity = yjcity;
    }

    public String getYjaddress() {
        return yjaddress;
    }

    public void setYjaddress(String yjaddress) {
        this.yjaddress = yjaddress;
    }

    public String getCjcountry() {
        return cjcountry;
    }

    public void setCjcountry(String cjcountry) {
        this.cjcountry = cjcountry;
    }

    public String getCjprovince() {
        return cjprovince;
    }

    public void setCjprovince(String cjprovince) {
        this.cjprovince = cjprovince;
    }

    public String getCjcity() {
        return cjcity;
    }

    public void setCjcity(String cjcity) {
        this.cjcity = cjcity;
    }

    public String getCjaddress() {
        return cjaddress;
    }

    public void setCjaddress(String cjaddress) {
        this.cjaddress = cjaddress;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDeptOfOlympic() {
        return deptOfOlympic;
    }

    public void setDeptOfOlympic(String deptOfOlympic) {
        this.deptOfOlympic = deptOfOlympic;
    }

    public String getMemberTypeOfOlympic() {
        return memberTypeOfOlympic;
    }

    public void setMemberTypeOfOlympic(String memberTypeOfOlympic) {
        this.memberTypeOfOlympic = memberTypeOfOlympic;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public String getEduinfo() {
        return eduinfo;
    }

    public void setEduinfo(String eduinfo) {
        this.eduinfo = eduinfo;
    }

    public String getTraninginfo() {
        return traninginfo;
    }

    public void setTraninginfo(String traninginfo) {
        this.traninginfo = traninginfo;
    }

    public String getWorkinfo() {
        return workinfo;
    }

    public void setWorkinfo(String workinfo) {
        this.workinfo = workinfo;
    }

    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public Timestamp getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        this.enddate = enddate;
    }

    public String getTitleInOlympic() {
        return titleInOlympic;
    }

    public void setTitleInOlympic(String titleInOlympic) {
        this.titleInOlympic = titleInOlympic;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getTalentflag() {
        return talentflag;
    }

    public void setTalentflag(int talentflag) {
        this.talentflag = talentflag;
    }

    public int getAuthflag() {
        return authflag;
    }

    public void setAuthflag(int authflag) {
        this.authflag = authflag;
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

    public String getMaincategory() {
        return maincategory;
    }

    public void setMaincategory(String maincategory) {
        this.maincategory = maincategory;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getMainfunc() {
        return mainfunc;
    }

    public void setMainfunc(String mainfunc) {
        this.mainfunc = mainfunc;
    }

    public String getRegnum() {
        return regnum;
    }

    public void setRegnum(String regnum) {
        this.regnum = regnum;
    }
}
