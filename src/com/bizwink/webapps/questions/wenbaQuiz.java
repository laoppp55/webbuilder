package com.bizwink.webapps.questions;

import java.sql.*;

public class wenbaQuiz {
	private int id;
	private int columnid;
	private String cname;
	private String question;
	private int status;
	private int voteflag;
	private int xuanshang;
	private int answernum;
	private String source;
	private Date  createdate;
	private String ipaddress;
	private String creater;
	private String province;
	private String city;
	private String area;
	private String picpath;
	private int emailnotify;
	private int parcolumid;
	public int getParcolumid() {
		return parcolumid;
	}
	public void setParcolumid(int parcolumid) {
		this.parcolumid = parcolumid;
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
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public int getEmailnotify() {
		return emailnotify;
	}
	public void setEmailnotify(int emailnotify) {
		this.emailnotify = emailnotify;
	}
}
