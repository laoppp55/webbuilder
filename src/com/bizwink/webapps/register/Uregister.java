package com.bizwink.webapps.register;

import java.sql.Timestamp;

public class Uregister {
    private int id;
    private String memberid;         //   用户ID
    private String password;         //   密码
    private String email;            //    邮箱
    private String name;             //    用户名
    private int Siteid;              //    站点ID
    private String linkman;
    private String postalcode;
    private String countru;
    private String phoen;
    private String fax;
    private String birthday;
    private String mobilephone;
    private String grade;
    private String title;            //评论标题
    private String content;          //评论内容
    private String memo;             //用户注释
    private String ip;
    private String company;          //用户所在公司
    private String department;      //用户所在部门
    private String address;
    private String homepage;
    private String message;
    private String sex;
    private String qq;
    private Timestamp birthdate;
    private String unit;
    private int salary;
    private String hoppy;
    private String province;
    private String zone;
    private String city;
    private String image;
    private String sign;
    private String idno;
    private String degree;
    private String nation;
    private String unitpostcode;
    private String unitphone;
    private String stationtype;
    private String entitytype;
    private String callsign;
    private String stationaddress;
    private String opedegree;
    private String beizhu;
    private String opecode;
    private int usertype;
    private int scores;                  //目前总积分
    private int score;                   //单次获取的积分
    private int meilizhi;
    private int lockflag;
    private int valid;
    private Timestamp createdate;
    private Timestamp lastlogindate;
    private String errmsg;

    //企业用户注册信息
    private String guid;
    private String orggode;
    private String orgname;
    private String orgareacode;
    private String orgsyscode;
    private String orgtype;
    private String orglinkperson;
    private String orgpersonid;
    private String orgsupcode;
    private String orgaddr;
    private String orgpost;
    private String orgphone;
    private String orgmobphone;
    private String orgfax;
    private String orgbank;
    private String orgaccountname;
    private String orgaccount;
    private int orghostility;
    private String orgwebsite;
    private String orgmail;
    private int value;
    private int company_or_personal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSiteid() {
        return Siteid;
    }

    public void setSiteid(int siteid) {
        Siteid = siteid;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountru() {
        return countru;
    }

    public void setCountru(String countru) {
        this.countru = countru;
    }

    public String getPhoen() {
        return phoen;
    }

    public void setPhoen(String phoen) {
        this.phoen = phoen;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getHoppy() {
        return hoppy;
    }

    public void setHoppy(String hoppy) {
        this.hoppy = hoppy;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getUnitpostcode() {
        return unitpostcode;
    }

    public void setUnitpostcode(String unitpostcode) {
        this.unitpostcode = unitpostcode;
    }

    public String getUnitphone() {
        return unitphone;
    }

    public void setUnitphone(String unitphone) {
        this.unitphone = unitphone;
    }

    public String getStationtype() {
        return stationtype;
    }

    public void setStationtype(String stationtype) {
        this.stationtype = stationtype;
    }

    public String getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(String entitytype) {
        this.entitytype = entitytype;
    }

    public String getCallsign() {
        return callsign;
    }

    public void setCallsign(String callsign) {
        this.callsign = callsign;
    }

    public String getStationaddress() {
        return stationaddress;
    }

    public void setStationaddress(String stationaddress) {
        this.stationaddress = stationaddress;
    }

    public String getOpedegree() {
        return opedegree;
    }

    public void setOpedegree(String opedegree) {
        this.opedegree = opedegree;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getOpecode() {
        return opecode;
    }

    public void setOpecode(String opecode) {
        this.opecode = opecode;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getMeilizhi() {
        return meilizhi;
    }

    public void setMeilizhi(int meilizhi) {
        this.meilizhi = meilizhi;
    }

    public int getLockflag() {
        return lockflag;
    }

    public void setLockflag(int lockflag) {
        this.lockflag = lockflag;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getLastlogindate() {
        return lastlogindate;
    }

    public void setLastlogindate(Timestamp lastlogindate) {
        this.lastlogindate = lastlogindate;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getOrggode() {
        return orggode;
    }

    public void setOrggode(String orggode) {
        this.orggode = orggode;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }

    public String getOrgareacode() {
        return orgareacode;
    }

    public void setOrgareacode(String orgareacode) {
        this.orgareacode = orgareacode;
    }

    public String getOrgsyscode() {
        return orgsyscode;
    }

    public void setOrgsyscode(String orgsyscode) {
        this.orgsyscode = orgsyscode;
    }

    public String getOrgtype() {
        return orgtype;
    }

    public void setOrgtype(String orgtype) {
        this.orgtype = orgtype;
    }

    public String getOrglinkperson() {
        return orglinkperson;
    }

    public void setOrglinkperson(String orglinkperson) {
        this.orglinkperson = orglinkperson;
    }

    public String getOrgpersonid() {
        return orgpersonid;
    }

    public void setOrgpersonid(String orgpersonid) {
        this.orgpersonid = orgpersonid;
    }

    public String getOrgsupcode() {
        return orgsupcode;
    }

    public void setOrgsupcode(String orgsupcode) {
        this.orgsupcode = orgsupcode;
    }

    public String getOrgaddr() {
        return orgaddr;
    }

    public void setOrgaddr(String orgaddr) {
        this.orgaddr = orgaddr;
    }

    public String getOrgpost() {
        return orgpost;
    }

    public void setOrgpost(String orgpost) {
        this.orgpost = orgpost;
    }

    public String getOrgphone() {
        return orgphone;
    }

    public void setOrgphone(String orgphone) {
        this.orgphone = orgphone;
    }

    public String getOrgmobphone() {
        return orgmobphone;
    }

    public void setOrgmobphone(String orgmobphone) {
        this.orgmobphone = orgmobphone;
    }

    public String getOrgfax() {
        return orgfax;
    }

    public void setOrgfax(String orgfax) {
        this.orgfax = orgfax;
    }

    public String getOrgbank() {
        return orgbank;
    }

    public void setOrgbank(String orgbank) {
        this.orgbank = orgbank;
    }

    public String getOrgaccountname() {
        return orgaccountname;
    }

    public void setOrgaccountname(String orgaccountname) {
        this.orgaccountname = orgaccountname;
    }

    public String getOrgaccount() {
        return orgaccount;
    }

    public void setOrgaccount(String orgaccount) {
        this.orgaccount = orgaccount;
    }

    public int getOrghostility() {
        return orghostility;
    }

    public void setOrghostility(int orghostility) {
        this.orghostility = orghostility;
    }

    public String getOrgwebsite() {
        return orgwebsite;
    }

    public void setOrgwebsite(String orgwebsite) {
        this.orgwebsite = orgwebsite;
    }

    public String getOrgmail() {
        return orgmail;
    }

    public void setOrgmail(String orgmail) {
        this.orgmail = orgmail;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCompany_or_personal() {
        return company_or_personal;
    }

    public void setCompany_or_personal(int company_or_personal) {
        this.company_or_personal = company_or_personal;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}