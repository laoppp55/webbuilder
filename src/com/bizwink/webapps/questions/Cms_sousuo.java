package com.bizwink.webapps.questions;

import java.sql.Timestamp;

public class Cms_sousuo {
	private String MainTitle;
	private Timestamp CreateDate; 
	private String Driname;
	public String getMainTitle() {
		return MainTitle;
	}
	public void setMainTitle(String mainTitle) {
		MainTitle = mainTitle;
	}
	public Timestamp getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Timestamp createDate) {
		CreateDate = createDate;
	}
	public String getDriname() {
		return Driname;
	}
	public void setDriname(String driname) {
		Driname = driname;
	}
	
}
