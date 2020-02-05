//Compile by dzq.June 25,2003
// Source File Name:   Error.java
package com.bizwink.error;
import java.sql.Timestamp;

public class Error
{
    //for tbl_bad
    private String userid;
    private String domainname;
    private String urlname;
    private String code;
    private long num;
    private String logdate;

    //for tbl_badname
    private String error_ename;
    private String error_chname;

    public Error()
    {
    }

    //for userid
    public String getUserID()
    {
        return userid;
    }

    public void setUserID(String userid)
    {
        this.userid = userid;
    }

    //for domainname
    public String getDomainName()
    {
        return domainname;
    }

    public void setDomainName()
    {
        this.domainname = domainname;
    }

    //for urlname
    public String getUrlName()
    {
        return urlname;
    }

    public void setUrlName(String urlname)
    {
        this.urlname = urlname;
    }

    //for code
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    //for num
    public long getNum()
    {
        return num;
    }

    public void setNum(long num)
    {
        this.num = num;
    }

    //for logdate
    public String getLogDate()
    {
        return logdate;
    }

    public void setLogDate(String logdate)
    {
        this.logdate = logdate;
    }

    //for error_ename
    public String getEName()
    {
        return error_ename;
    }

    public void setEName(String error_ename)
    {
        this.error_ename = error_ename;
    }

    //for error_chname
    public String getCHName()
    {
        return error_chname;
    }

    public void setCHName(String error_chname)
    {
        this.error_chname = error_chname;
    }
}


