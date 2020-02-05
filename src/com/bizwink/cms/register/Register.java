package com.bizwink.cms.register;

import java.sql.Timestamp;

public class Register {
    private int siteID;
    private String extname;
    private String siteName;
    private String userID;
    private String password;
    private String username;
    private int imagesDir;
    private int cssjsDir;
    private int tcFlag;
    private int wapflag;              //是否支持WAP1.0
    private int rssflag;              //是否支持RSS
    private int xhtmlflag;            //是否支持WAP2.0
    private int encoding;             //网站页面编码格式
    private int pubFlag;
    private int bindFlag;
    private int beRefered;
    private int copyColumn;
    private int beCopyColumn;
    private int pushArticle;
    private int moveArticle;
    private String configInfo;
    private Timestamp createDate;
    private String email;
    private String mphone;
    private int joincompanyid;
    private String joinid;
    private int dflag;
    private String logo;
    private String banner;
    private int nav;
    private int samsite;
    private int sharetemplatenum;
    private String address;
    private String company;
    private String copyright;
    private String phone;                                 //联系电话
    private int sex;                                      //0表示男 1表示女
    private String country;                               //国家
    private String province;                              //省
    private String city;                                  //市
    private String area;                                  //区县
    private String jiedao;                                //街道
    private String shequ;                                 //社区
    private String postcode;                               //邮政编码
    private Timestamp birthdate;                          //出生日期
    private String myimage;                                //头像
    private int usertype;                                //用户类型    0--个人用户    1-企业用户

    private int id;
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
    private String name;


    //add by kang 2010-09
    private String titlepic;                     //标题图片大小
    private String vtitlepic;                    //副标题图片大小
    private String sourcepic;                    //来源图片大小
    private String authorpic;                    //作者图片大小
    private String contentpic;                   //内容图片大小
    private String specialpic;                   //文章特效图片大小
    private String productpic;                   //商品大图片大小
    private String productsmallpic;              //商品小图片大小
    private String mediasize;                    //视频文件大小
    private String mediapicsize;                 //视频文件图片大小
    private String ts_pic;
    private String s_pic;
    private String ms_pic;
    private String m_pic;
    private String ml_pic;
    private String l_pic;
    private String tl_pic;
    //end add

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public int getTcFlag() {
        return tcFlag;
    }

    public void setTcFlag(int tcFlag) {
        this.tcFlag = tcFlag;
    }

    public int getWapflag() {
        return wapflag;
    }

    public void setWapflag(int wapflag) {
        this.wapflag = wapflag;
    }

    public int getEncoding() {
        return encoding;
    }

    public void setEncoding(int encoding) {
        this.encoding = encoding;
    }

    public int getRssflag() {
        return rssflag;
    }

    public void setRssflag(int rssflag) {
        this.rssflag = rssflag;
    }

    public int getXhtmlflag() {
        return xhtmlflag;
    }

    public void setXhtmlflag(int xhtmlflag) {
        this.xhtmlflag = xhtmlflag;
    }

    public int getNav() {
        return nav;
    }

    public void setNav(int nav) {
        this.nav = nav;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setSiteID(int siteID) {
        this.siteID = siteID;
    }

    public int getSiteID() {
        return siteID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setExtName(String extName) {
        this.extname = extName;
    }

    public String getExtName() {
        return extname;
    }

    public void setImagesDir(int imagesDir) {
        this.imagesDir = imagesDir;
    }

    public int getImagesDir() {
        return imagesDir;
    }

    public void setTCFlag(int tcFlag) {
        this.tcFlag = tcFlag;
    }

    public int getTCFlag() {
        return tcFlag;
    }

    public void setWapFlag(int wapFlag) {
        this.wapflag = wapFlag;
    }

    public int getWapFlag() {
        return wapflag;
    }

    public void setRSSFlag(int rssFlag) {
        this.rssflag = rssFlag;
    }

    public int getRSSFlag() {
        return rssflag;
    }

    public void setXHTMLFlag(int xhtmlFlag) {
        this.xhtmlflag = xhtmlFlag;
    }

    public int getXHTMLFlag() {
        return xhtmlflag;
    }

    public void setPubFlag(int pubFlag) {
        this.pubFlag = pubFlag;
    }

    public int getPubFlag() {
        return pubFlag;
    }

    //设置是否允许用户使用网站
    public void setBindFlag(int bindFlag) {
        this.bindFlag = bindFlag;
    }

    public int getBindFlag() {
        return bindFlag;
    }

    //设置站点的名称
    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public int getBeRefered() {
        return beRefered;
    }

    public void setBeRefered(int beRefered) {
        this.beRefered = beRefered;
    }

    public int getCopyColumn() {
        return copyColumn;
    }

    public void setCopyColumn(int copyColumn) {
        this.copyColumn = copyColumn;
    }

    public int getBeCopyColumn() {
        return beCopyColumn;
    }

    public void setBeCopyColumn(int beCopyColumn) {
        this.beCopyColumn = beCopyColumn;
    }

    public int getPushArticle() {
        return pushArticle;
    }

    public void setPushArticle(int pushArticle) {
        this.pushArticle = pushArticle;
    }

    public int getMoveArticle() {
        return moveArticle;
    }

    public void setMoveArticle(int moveArticle) {
        this.moveArticle = moveArticle;
    }

    public int getCssjsDir() {
        return cssjsDir;
    }

    public void setCssjsDir(int cssjsDir) {
        this.cssjsDir = cssjsDir;
    }

    public String getMphone() {
        return mphone;
    }

    public void setMphone(String mphone) {
        this.mphone = mphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJoincompanyid() {
        return joincompanyid;
    }

    public void setJoincompanyid(int joincompanyid) {
        this.joincompanyid = joincompanyid;
    }

    public String getJoinid() {
        return joinid;
    }

    public void setJoinid(String joinid) {
        this.joinid = joinid;
    }

    public int getDflag() {
        return dflag;
    }

    public void setDflag(int dflag) {
        this.dflag = dflag;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getNavigator() {
        return nav;
    }

    public void setNavigator(int nav) {
        this.nav = nav;
    }

    public int getSamsite() {
        return samsite;
    }

    public void setSamsite(int samsite) {
        this.samsite = samsite;
    }

    public int getSharetemplatenum() {
        return sharetemplatenum;
    }

    public void setSharetemplatenum(int sharetemplatenum) {
        this.sharetemplatenum = sharetemplatenum;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getVtitlepic() {
        return vtitlepic;
    }

    public void setVtitlepic(String vtitlepic) {
        this.vtitlepic = vtitlepic;
    }

    public String getSourcepic() {
        return sourcepic;
    }

    public void setSourcepic(String sourcepic) {
        this.sourcepic = sourcepic;
    }

    public String getAuthorpic() {
        return authorpic;
    }

    public void setAuthorpic(String authorpic) {
        this.authorpic = authorpic;
    }

    public String getContentpic() {
        return contentpic;
    }

    public void setContentpic(String contentpic) {
        this.contentpic = contentpic;
    }

    public String getSpecialpic() {
        return specialpic;
    }

    public void setSpecialpic(String specialpic) {
        this.specialpic = specialpic;
    }

    public String getProductpic() {
        return productpic;
    }

    public void setProductpic(String productpic) {
        this.productpic = productpic;
    }

    public String getProductsmallpic() {
        return productsmallpic;
    }

    public void setProductsmallpic(String productsmallpic) {
        this.productsmallpic = productsmallpic;
    }

    public String getTs_pic() {
        return ts_pic;
    }

    public void setTs_pic(String ts_pic) {
        this.ts_pic = ts_pic;
    }

    public String getS_pic() {
        return s_pic;
    }

    public void setS_pic(String s_pic) {
        this.s_pic = s_pic;
    }

    public String getMs_pic() {
        return ms_pic;
    }

    public void setMs_pic(String ms_pic) {
        this.ms_pic = ms_pic;
    }

    public String getM_pic() {
        return m_pic;
    }

    public void setM_pic(String m_pic) {
        this.m_pic = m_pic;
    }

    public String getMl_pic() {
        return ml_pic;
    }

    public void setMl_pic(String ml_pic) {
        this.ml_pic = ml_pic;
    }

    public String getL_pic() {
        return l_pic;
    }

    public void setL_pic(String l_pic) {
        this.l_pic = l_pic;
    }

    public String getTl_pic() {
        return tl_pic;
    }

    public void setTl_pic(String tl_pic) {
        this.tl_pic = tl_pic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getJiedao() {
        return jiedao;
    }

    public void setJiedao(String jiedao) {
        this.jiedao = jiedao;
    }

    public String getShequ() {
        return shequ;
    }

    public void setShequ(String shequ) {
        this.shequ = shequ;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public Timestamp getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Timestamp birthdate) {
        this.birthdate = birthdate;
    }

    public String getMyimage() {
        return myimage;
    }

    public void setMyimage(String myimage) {
        this.myimage = myimage;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getMediasize() {
        return mediasize;
    }

    public void setMediasize(String mediasize) {
        this.mediasize = mediasize;
    }

    public String getMediapicsize() {
        return mediapicsize;
    }

    public void setMediapicsize(String mediapicsize) {
        this.mediapicsize = mediapicsize;
    }
}