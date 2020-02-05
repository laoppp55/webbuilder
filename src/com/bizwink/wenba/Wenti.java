package com.bizwink.wenba;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-10-14
 * Time: 14:11:54
 * To change this template use File | Settings | File Templates.
 */
public class Wenti {
    private int id;
    private int columnid;
    private String cname;
    private String question;
    private String username;
    private int status;public int getIstop() {
    return istop;
}

    public void setIstop(int istop) {
        this.istop = istop;
    }

    private int voteflag;
    private int xuanshang;
    private int answernum;
    private String source;
    private String createdate;
    private int istop;
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

    public int getAnswernum() {
        return answernum;
    }

    public void setAnswernum(int answernum) {
        this.answernum = answernum;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
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

    public int getEmailnotify() {
        return emailnotify;
    }

    public void setEmailnotify(int emailnotify) {
        this.emailnotify = emailnotify;
    }

    private String ipaddress;
    private String create;
    private String province;
    private String city;
    private String area;
    private int emailnotify;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
