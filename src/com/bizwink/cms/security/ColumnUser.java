package com.bizwink.cms.security;

public class ColumnUser
{
	private String columnEname;
	private String userID;
	private int columnID;
	private String columnCname;

	public String getUserID()
	{
		return userID;
	}

	public void setUserID(String uid)
	{
		this.userID = uid;
	}

	public String getColumnEname()
	{
		return columnEname;
	}

	public void setColumnEname(String ename)
	{
		this.columnEname = ename;
	}

	public int getColumnID()
	{
		return columnID;
	}

	public void setColumnID(int columnID)
	{
		this.columnID = columnID;
	}

	public void setColumnCname(String columnCname)
	{
		this.columnCname = columnCname;
	}

	public String getColumnCname()
	{
		return columnCname;
	}
}