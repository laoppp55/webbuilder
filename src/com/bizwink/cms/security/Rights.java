package com.bizwink.cms.security;

public class Rights implements Comparable
{
	private int rightID;
	private int columnID;
	private String rightCName;
	private String rightEName;
	private String rightDesc;
	private String userID;

	public int getRightID()
	{
		return rightID;
	}

	public void setRightID(int rightID)
	{
		this.rightID = rightID;
	}

	public String getRightCName()
	{
		return rightCName;
	}

	public void setRightCName(String rightCName)
	{
		this.rightCName = rightCName;
	}

	public String getRightEName()
	{
		return rightEName;
	}

	public void setRightEName(String rightEName)
	{
		this.rightEName = rightEName;
	}

	public String getRightDesc()
	{
		return rightDesc;
	}

	public void setRightDesc(String rightDesc)
	{
		this.rightDesc = rightDesc;
	}

	public int compareTo(Object obj)
	{
		int rid1 = ((Right) obj).getRightID();
		int rid2 = this.getRightID();

		if (rid1 == rid2)
			return 0;
		else
			return 1;
	}

	public void setUserID(String userID)
	{
		this.userID = userID;
	}

	public String getUserID()
	{
		return userID;
	}

	public void setColumnID(int columnID)
	{
		this.columnID = columnID;
	}

	public int getColumnID()
	{
		return columnID;
	}
}