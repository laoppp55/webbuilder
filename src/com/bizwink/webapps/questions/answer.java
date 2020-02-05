package com.bizwink.webapps.questions;

import java.sql.Date;

public class answer {
	private int id;
	private int qid;
	private String anwser;
	private  int votenum;
	private String ipaddress;
	private String username;
	private int userid;
	private String cahkaoziliao;
	private Date  createdate;
	private int fenshu;
	private String picpath;
	private int batternas;
	private int anwStatus;
	
	public int getAnwStatus() {
		return anwStatus;
	}
	public void setAnwStatus(int anwStatus) {
		this.anwStatus = anwStatus;
	}
	public int getBatternas() {
		return batternas;
	}
	public void setBatternas(int batternas) {
		this.batternas = batternas;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
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
	public String getCahkaoziliao() {
		return cahkaoziliao;
	}
	public void setCahkaoziliao(String cahkaoziliao) {
		this.cahkaoziliao = cahkaoziliao;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public int getFenshu() {
		return fenshu;
	}
	public void setFenshu(int fenshu) {
		this.fenshu = fenshu;
	}
}
