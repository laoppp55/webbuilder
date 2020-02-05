package com.bizwink.cms.sjswsbs;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by kang on 2018/8/15.
 */
public class ReportGangdom implements Serializable {

    private int id;
    private String jbrname;  //举报人姓名
    private int sex;          //举报人性别 1男 0 女
    private String idcardno;  //举报人身份证号
    private String telphone;  //举报人电话号码
    private String address;   //举报人联系地址
    private String postcode;  //举报人邮政编码

    //被举报人
    private String reportedname; //姓名   必填
    private String epithet;      //绰号
    private String rpaddress;    //联系地址
    private String rpidcardno;   //身份证号
    private String province;     //默认 北京市  必填
    private String city;         //默认 石景山区  必填
    private String county;       //街道或区域    必填
    private String reportedcontent;  //举报内容  必填

    //涉及公职人员
    private String gzname;      //姓名
    private String unittitle;   //所在单位职务
    private String unlevel;     //级别
    private String gzreportedcontent; //涉及内容

    private Timestamp createdate;  //创建时间
    private String ipadress;    //举报人IP地址
    private String yanzhengmsg;  //前面所有信息MD5编码


    private String jbrlink; //举报人联系方式
    private String jbrpolitical; //举报人政治面貌
    private String jbrlevel; //举报人级别
    private String department; //被举报人单位
    private String rpJob; //被举报人职务
    private String rplevel;//被举报人级别

    private String repmaintitle;//被举报标题
    private String repclass; //问题类别
    private String repclasses;//问题细类

    private String searchmsg;  //查询码
    private int auditflag;  //受理 --1，受理  0，未受理
    private String filename; //附件



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJbrname() {
        return jbrname;
    }

    public void setJbrname(String jbrname) {
        this.jbrname = jbrname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
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

    public String getReportedname() {
        return reportedname;
    }

    public void setReportedname(String reportedname) {
        this.reportedname = reportedname;
    }

    public String getEpithet() {
        return epithet;
    }

    public void setEpithet(String epithet) {
        this.epithet = epithet;
    }

    public String getRpaddress() {
        return rpaddress;
    }

    public void setRpaddress(String rpaddress) {
        this.rpaddress = rpaddress;
    }

    public String getRpidcardno() {
        return rpidcardno;
    }

    public void setRpidcardno(String rpidcardno) {
        this.rpidcardno = rpidcardno;
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

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getReportedcontent() {
        return reportedcontent;
    }

    public void setReportedcontent(String reportedcontent) {
        this.reportedcontent = reportedcontent;
    }

    public String getGzname() {
        return gzname;
    }

    public void setGzname(String gzname) {
        this.gzname = gzname;
    }

    public String getUnittitle() {
        return unittitle;
    }

    public void setUnittitle(String unittitle) {
        this.unittitle = unittitle;
    }

    public String getUnlevel() {
        return unlevel;
    }

    public void setUnlevel(String unlevel) {
        this.unlevel = unlevel;
    }

    public String getGzreportedcontent() {
        return gzreportedcontent;
    }

    public void setGzreportedcontent(String gzreportedcontent) {
        this.gzreportedcontent = gzreportedcontent;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getIpadress() {
        return ipadress;
    }

    public void setIpadress(String ipadress) {
        this.ipadress = ipadress;
    }

    public String getYanzhengmsg() {
        return yanzhengmsg;
    }

    public void setYanzhengmsg(String yanzhengmsg) {
        this.yanzhengmsg = yanzhengmsg;
    }

    public String getJbrlink() {
        return jbrlink;
    }

    public void setJbrlink(String jbrlink) {
        this.jbrlink = jbrlink;
    }

    public String getJbrpolitical() {
        return jbrpolitical;
    }

    public void setJbrpolitical(String jbrpolitical) {
        this.jbrpolitical = jbrpolitical;
    }

    public String getJbrlevel() {
        return jbrlevel;
    }

    public void setJbrlevel(String jbrlevel) {
        this.jbrlevel = jbrlevel;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRpJob() {
        return rpJob;
    }

    public void setRpJob(String rpJob) {
        this.rpJob = rpJob;
    }

    public String getRplevel() {
        return rplevel;
    }

    public void setRplevel(String rplevel) {
        this.rplevel = rplevel;
    }

    public String getRepmaintitle() {
        return repmaintitle;
    }

    public void setRepmaintitle(String repmaintitle) {
        this.repmaintitle = repmaintitle;
    }

    public String getRepclass() {
        return repclass;
    }

    public void setRepclass(String repclass) {
        this.repclass = repclass;
    }

    public String getRepclasses() {
        return repclasses;
    }

    public void setRepclasses(String repclasses) {
        this.repclasses = repclasses;
    }

    public String getSearchmsg() {
        return searchmsg;
    }

    public void setSearchmsg(String searchmsg) {
        this.searchmsg = searchmsg;
    }

    public int getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(int auditflag) {
        this.auditflag = auditflag;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
