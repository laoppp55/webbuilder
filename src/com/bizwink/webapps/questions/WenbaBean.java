package com.bizwink.webapps.questions;

import java.sql.Date;

public class WenbaBean {
	private int id;
	private int columnid;
	private int parentID;
	private String cname;
	private String username;
	private int userid;
	private String title;
	private String content;
	private int fenshu;
	private Date createdate;
	private String cankaoziliao;
	private String picpath;
	private String ipaddress;
	private String province;
	private String city;
	private String area;
	private int emailnotify;
	private int xuanshang;
	private int answernum;
	private int usergrade;
	private int weekgrade;
	private String provinceid;
	private int status;
	private String email;
	
	public int getEmailnotify() {
		return emailnotify;
	}
	public void setEmailnotify(int emailnotify) {
		this.emailnotify = emailnotify;
	}
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
	public int getFenshu() {
		return fenshu;
	}
	public void setFenshu(int fenshu) {
		this.fenshu = fenshu;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getCankaoziliao() {
		return cankaoziliao;
	}
	public void setCankaoziliao(String cankaoziliao) {
		this.cankaoziliao = cankaoziliao;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
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
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public int getParentID() {
		return parentID;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public int getAnswernum() {
		return answernum;
	}
	public void setAnswernum(int answernum) {
		this.answernum = answernum;
	}
	public int getXuanshang() {
		return xuanshang;
	}
	public void setXuanshang(int xuanshang) {
		this.xuanshang = xuanshang;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getUsergrade() {
		return usergrade;
	}
	public void setUsergrade(int usergrade) {
		this.usergrade = usergrade;
	}
	public int getWeekgrade() {
		return weekgrade;
	}
	public void setWeekgrade(int weekgrade) {
		this.weekgrade = weekgrade;
	}
	
}
