package com.bizwink.cms.business.Order;

import java.sql.Date;
import java.sql.Timestamp;

public class Order {
    private long orderid;
    private int userid;
    private String name;
    private String address;
    private String postcode;
    private String phone;
    private int sex;
    private String province;
    private int country;
    private float payfee;
    private float totalfee;
    private float deliveryfee;
    private java.sql.Timestamp createdate;
    private int sendway;
    private int payway;
    private int payflag;
    private float insurance;
    private int status;
    private int siteid;
    private int orderflag;
    private String username;
    private String notes;
    private int billingway;
    private int nees_invoice;
    private String linktime;
    private String updateuser;
    private int userscores;
    private String email;
    private String telephone;
    private String r2TrxId;                  //支付中心返回流水号
    private String zfmemberid;              //支付中心会员ID
    private String r2type;                     //支付中心返回类型，1浏览器重定向 2服务器点对点
    private String payresult;               //支付结果，成功/失败/其他
    private Timestamp paydate;              //订单支付时间
    private Timestamp lastupdate;          //订单修改时间


    //for orders_detail
    private int productid;
    private int ordernum;
    private String city;
    private String zone;
    private int nouse;
    private float saleprice;
    private String type;
    private int cardid;
    private String productname;
    private String suppliername;
    private int supplierid;

    //for fee
    private int fid;
    private float sendfee;
    private String sendname;

    //for product
    private float psaleprice;

    //for zengpin
    private int presentid;
    //for jifen
    private int scores;
    //for maintitle
    private String maintitle;

    //for user phone
    private int id;
    //定单获得积分
    private int orderscores;
    //使用购物券面额
    private int usecard;

    //报社
    private String riqi;
    private String xiangxi;
    private int numbercopy;                   //订阅份数
    private float yprice;
    private float byprice;
    private float qprice;
    private float mprice;
    private float vipy;
    private float vipby;
    private float vipq;
    private float vipm;
    private int subscribe;                    //订阅方式，1年订 2半年订 3季度订 4月度订
    private int subscribenum;                //订阅季度数或者订阅月数
    private Date userinstarttime;            //用户键入的开始订阅时间
    private Date userinendtime;              //用户键入的结束订阅时间
    private Date servicestarttime;          //送报开始时间
    private Date serviceendtime;            //送报结束时间

    //城建党校
    private String projname;              //项目名称


    public float getByprice() {
        return byprice;
    }

    public void setByprice(float byprice) {
        this.byprice = byprice;
    }

    public float getVipby() {
        return vipby;
    }

    public void setVipby(float vipby) {
        this.vipby = vipby;
    }

    public float getYprice() {
        return yprice;
    }

    public void setYprice(float yprice) {
        this.yprice = yprice;
    }

    public float getQprice() {
        return qprice;
    }

    public void setQprice(float qprice) {
        this.qprice = qprice;
    }

    public float getMprice() {
        return mprice;
    }

    public void setMprice(float mprice) {
        this.mprice = mprice;
    }

    public float getVipy() {
        return vipy;
    }

    public void setVipy(float vipy) {
        this.vipy = vipy;
    }

    public float getVipq() {
        return vipq;
    }

    public void setVipq(float vipq) {
        this.vipq = vipq;
    }

    public float getVipm() {
        return vipm;
    }

    public void setVipm(float vipm) {
        this.vipm = vipm;
    }

    public Order() {
    }

    public String getMaintitle() {
        return maintitle;
    }

    public void setMaintitlee(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSendnamne() {
        return sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
    }

    public int getPresentid() {
        return presentid;
    }

    public void setPresentid(int presentid) {
        this.presentid = presentid;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public float getPsaleprice() {
        return psaleprice;
    }

    public void setpPsaleprice(float psaleprice) {
        this.psaleprice = psaleprice;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public float getSendfee() {
        return sendfee;
    }

    public void setSdenfee(float sendfee) {
        this.sendfee = sendfee;
    }

    public int getNouse() {
        return nouse;
    }

    public void setNouse(int nouse) {
        this.nouse = nouse;
    }

    public int getNees_invoice() {
        return nees_invoice;
    }

    public void setNees_invoicee(int nees_invoice) {
        this.nees_invoice = nees_invoice;
    }

    //for orderid
    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }
    //for userid

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    //for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //for address
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    //for postcode
    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    //for phone
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //for sex
    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    //for province
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    //for country
    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    //for totalfee
    public float getTotalfee() {
        return totalfee;
    }

    public void setTotalfee(float totalfee) {
        this.totalfee = totalfee;
    }

    //for deliveryfee
    public float getDeliveryfee() {
        return deliveryfee;
    }

    public void setDeliveryfee(float deliveryfee) {
        this.deliveryfee = deliveryfee;
    }

    //for payfee
    public float getPayfee() {
        return payfee;
    }

    public void setPayfee(float payfee) {
        this.payfee = payfee;
    }

    //for createdate
    public java.sql.Timestamp getCreateDate() {
        return createdate;
    }

    public void setCreateDate(java.sql.Timestamp createdate) {
        this.createdate = createdate;
    }

    //for sendway
    public int getSendWay() {
        return sendway;
    }

    public void setSendWay(int sendway) {
        this.sendway = sendway;
    }

    //for payway
    public int getPayWay() {
        return payway;
    }

    public void setPayWay(int payway) {
        this.payway = payway;
    }

    //for insurance
    public float getInsurance() {
        return sendway;
    }

    public void setInsurance(float insurance) {
        this.insurance = insurance;
    }

    //for status
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    //for siteid
    public int getSiteid() {
        return siteid;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getOrderFlag() {
        return orderflag;
    }

    public void setOrderFlag(int orderflag) {
        this.orderflag = orderflag;
    }

    //for username
    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    //for notes
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public int getOrderNum() {
        return ordernum;
    }

    public void setOrderNum(int ordernum) {
        this.ordernum = ordernum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getBillingway() {
        return billingway;
    }

    public void setBillingway(int billingway) {
        this.billingway = billingway;
    }

    public int getCardid() {
        return cardid;
    }

    public void setCardid(int cardid) {
        this.cardid = cardid;
    }

    public int getUserscores() {
        return userscores;
    }

    public void setUserscores(int userscores) {
        this.userscores = userscores;
    }

    public String getLinktime() {
        return linktime;
    }

    public void setLinktime(String linktime) {
        this.linktime = linktime;
    }

    public int getPayflag() {
        return payflag;
    }

    public void setPayflag(int payflag) {
        this.payflag = payflag;
    }

    public String getR2TrxId() {
        return r2TrxId;
    }

    public void setR2TrxId(String r2TrxId) {
        this.r2TrxId = r2TrxId;
    }

    public String getZfmemberid() {
        return zfmemberid;
    }

    public void setZfmemberid(String zfmemberid) {
        this.zfmemberid = zfmemberid;
    }

    public String getR2type() {
        return r2type;
    }

    public void setR2type(String r2type) {
        this.r2type = r2type;
    }

    public String getPayresult() {
        return payresult;
    }

    public void setPayresult(String payresult) {
        this.payresult = payresult;
    }

    public Timestamp getPaydate() {
        return paydate;
    }

    public void setPaydate(Timestamp paydate) {
        this.paydate = paydate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdateuser() {
        return updateuser;
    }

    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String name) {
        this.suppliername = name;
    }

    public int getSupplierid() {
        return supplierid;
    }

    public void setSupplierid(int supplierid) {
        this.supplierid = supplierid;
    }

    public int getOrderscores() {
        return orderscores;
    }

    public void setOrderscores(int orderscores) {
        this.orderscores = orderscores;
    }

    public int getUsecard() {
        return usecard;
    }

    public void setUsecard(int usecard) {
        this.usecard = usecard;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }

    public String getXiangxi() {
        return xiangxi;
    }

    public void setXiangxi(String xiangxi) {
        this.xiangxi = xiangxi;
    }

    public int getNumbercopy() {
        return numbercopy;
    }

    public void setNumbercopy(int numbercopy) {
        this.numbercopy = numbercopy;
    }

    public int getSubscribenum() {
        return subscribenum;
    }

    public void setSubscribenum(int subscribenum) {
        this.subscribenum = subscribenum;
    }

    public Date getUserinstarttime() {
        return userinstarttime;
    }

    public void setUserinstarttime(Date userinstarttime) {
        this.userinstarttime = userinstarttime;
    }

    public Date getUserinendtime() {
        return userinendtime;
    }

    public void setUserinendtime(Date userinendtime) {
        this.userinendtime = userinendtime;
    }

    public Date getServicestarttime() {
        return servicestarttime;
    }

    public void setServicestarttime(Date servicestarttime) {
        this.servicestarttime = servicestarttime;
    }

    public Date getServiceendtime() {
        return serviceendtime;
    }

    public void setServiceendtime(Date serviceendtime) {
        this.serviceendtime = serviceendtime;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public int getSendway() {
        return sendway;
    }

    public void setSendway(int sendway) {
        this.sendway = sendway;
    }

    public int getPayway() {
        return payway;
    }

    public void setPayway(int payway) {
        this.payway = payway;
    }

    public int getOrderflag() {
        return orderflag;
    }

    public void setOrderflag(int orderflag) {
        this.orderflag = orderflag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNees_invoice(int nees_invoice) {
        this.nees_invoice = nees_invoice;
    }

    public Timestamp getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(Timestamp lastupdate) {
        this.lastupdate = lastupdate;
    }

    public int getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(int ordernum) {
        this.ordernum = ordernum;
    }

    public void setSendfee(float sendfee) {
        this.sendfee = sendfee;
    }

    public String getSendname() {
        return sendname;
    }

    public void setPsaleprice(float psaleprice) {
        this.psaleprice = psaleprice;
    }

    public void setMaintitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getProjname() {
        return projname;
    }

    public void setProjname(String projname) {
        this.projname = projname;
    }
}
