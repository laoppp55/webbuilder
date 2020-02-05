package com.bizwink.wenba;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2008-10-15
 * Time: 10:06:15
 * To change this template use File | Settings | File Templates.
 */
public class Answer {
    private int id;
    private int qid;
    private String anwser;
    private int votenum;
    private String ipaddress;
    private String username;
    private int userid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getAnwser() {
        return anwser;
    }

    public void setAnwser(String anwser) {
        this.anwser = anwser;
    }

    public int getVotenum() {
        return votenum;
    }

    public void setVotenum(int votenum) {
        this.votenum = votenum;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public String getCankaoziliao() {
        return cankaoziliao;
    }

    public void setCankaoziliao(String cankaoziliao) {
        this.cankaoziliao = cankaoziliao;
    }

    public int getFenshu() {
        return fenshu;
    }

    public void setFenshu(int fenshu) {
        this.fenshu = fenshu;
    }

    private String createdate;
    private String cankaoziliao;
    private int fenshu;
}
