package com.bizwink.cms.publish;

public class Publish {

	private int siteid;
	private String siteip;
	private String docpath;
	private String ftpuser;
	private String ftppasswd;
	private int publishway;

	public Publish() {
	}

	public int getSiteID() {
		return siteid;
	}

	public void setSiteID(int siteid) {
		this.siteid = siteid;
	}

	public String getSiteIP() {
		return siteip;
	}

	public void setSiteIP(String siteip) {
		this.siteip = siteip;
	}

	public String getDocPath() {
		return docpath;
	}

	public void setDocPath(String docpath) {
		this.docpath = docpath;
	}

	public String getFtpUser() {
		return ftpuser;
	}

	public void setFtpUser(String ftpuser) {
		this.ftpuser = ftpuser;
	}

	public String getFtpPasswd() {
		return ftppasswd;
	}

	public void setFtpPasswd(String ftppasswd) {
		this.ftppasswd = ftppasswd;
	}

	public int getPublishWay() {
		return publishway;
	}

	public void setPublishWay(int publishway) {
		this.publishway = publishway;
	}

}