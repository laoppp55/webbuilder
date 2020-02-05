//Compile by dzq.January 27,2003
// Source File Name:   Subscriber.java
package com.bizwink.webtrend;
import java.sql.Timestamp;

public class Webtrend
{
        private String urlname;
	  private String logSelect;
        private String ipaddress;
        private String ftpuser;
        private String ftppasswd;
        private String ipaddressSelect;
        private String filetypeselected;
        private String rejectedFileType;
        private String logdirname;

        private String ch_urlname;
        private String pageview;
        private String usersession;
        private Timestamp datetime;
        private int id;
        private String total_pageview;
        private String total_usersession;
        private String aver_user_time;
        private String unique_user;

        //tbl_subconclusion
        private String item_name;
        private String ch_name;
        private String usid;

        private String userid;
        private String domainname;
        private String logdate;

        //tbl_NetInfo
        private String uid;
        private String ndomainname;
        private String nipaddress;
        private String ftpname;
        private String ftppassword;
        private String org_log_path;
        private String log_type;
        private String req_type;
        private String rej_type;

        //tbl_ip
        private String pid;
        private String beginip;
        private String endip;
        private String continent;
        //private String country;
        //private String province;
        private String city;
        private int countryid;
        private int provinceid;

        //tbl_userip
        private String count;
        private int idcountry;

        //tbl_country
        private String cid;
        private String ctry;

        //tbl_city
        private String cityid;
        private String cityname;

        //for sql
        private int pv;
        private int us;

        //for tbl_refer
        private String upurlname;

        public Webtrend()
        {
        }

        //for pv
        public int getPV()
        {
            return pv;
        }

        public void setPV(int pv)
        {
            this.pv = pv;
        }

        //for us
        public int getUS()
        {
            return us;
        }

        public void setUS(int us)
        {
            this.us = us;
        }

        //for provinceid
        public int getProvinceID()
        {
            return provinceid;
        }

        public void setProvinceID(int provinceid)
        {
            this.provinceid = provinceid;
        }


        //for countryid
        public int getCountryID()
        {
            return countryid;
        }

        public void setCountryID(int countryid)
        {
            this.countryid = countryid;
        }

        //for cityname
        public String getCityName()
        {
            return cityname;
        }

        public void setCityName(String cityname)
        {
            this.cityname = cityname;
        }

        //for cityid
        public String getCityID()
        {
            return cityid;
        }

        public void setCityID(String cityid)
        {
            this.cityid = cityid;
        }

        //for cid
        public String getCID()
        {
            return cid;
        }

        public void setCID(String cid)
        {
            this.cid = cid;
        }

        //for ctry
        public String getCtry()
        {
            return ctry;
        }

        public void setCtry(String ctry)
        {
            this.ctry = ctry;
        }

        //for pid
        public String getPID()
        {
            return pid;
        }

        public void setPID(String pid)
        {
            this.pid = pid;
        }

        //for count
        public String getUCount()
        {
            return count;
        }

        public void setUCount(String count)
        {
            this.count = count;
        }

        //for idcountry
        public int getIDCountry()
        {
            return idcountry;
        }

        public void setIDCountry(int idcountry)
        {
            this.idcountry = idcountry;
        }

        //for city
        public String getCity()
        {
            return city;
        }

        public void setCity(String city)
        {
            this.city = city;
        }

        /*//for province
        public String getProvince()
        {
            return province;
        }

        public void setProvince(String province)
        {
            this.province = province;
        }

        //for country
        public String getCountry()
        {
            return country;
        }

        public void setCountry(String country)
        {
            this.country = country;
        }*/

        //for continent
        public String getContinent()
        {
            return continent;
        }

        public void setContinent(String continent)
        {
            this.continent = continent;
        }

        //for beginip
        public String getBeginIP()
        {
            return beginip;
        }

        public void setBeginIP(String beginip)
        {
            this.beginip = beginip;
        }

        //for endip
        public String getEndIP()
        {
            return endip;
        }

        public void setEndIP(String endip)
        {
            this.endip = endip;
        }

        //for usid
        public String getLogDate()
        {
            return logdate;
        }

        public void setLogDate(String logdate)
        {
            this.logdate = logdate;
        }

        //for DOMAINNAME
        public String getDomainName()
        {
            return domainname;
        }

        public void setDomainName(String domainname)
        {
            this.domainname = domainname;
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

        //for usid
        public String getUsID()
        {
            return usid;
        }

        public void setUsID(String usid)
        {
            this.usid = usid;
        }

        //for ch_name
        public String getCh_Name()
        {
            return ch_name;
        }

        public void setCh_Name(String ch_name)
        {
            this.ch_name = ch_name;
        }

        //for item_name
        public String getItem_Name()
        {
            return item_name;
        }

        public void setItem_Name(String item_name)
        {
            this.item_name = item_name;
        }


        //for id
        public int getID()
        {
            return id;
        }

        public void setID(int id)
        {
            this.id = id;
        }

        //for total_pageview
        public String getTotalPageView()
        {
            return total_pageview;
        }

        public void setTotalPageView(String total_pageview)
        {
            this.total_pageview = total_pageview;
        }

        //for total_usersession
        public String getTotalUsersession()
        {
            return total_usersession;
        }

        public void setTotalUsersession(String total_usersession)
        {
            this.total_usersession = total_usersession;
        }

        //for aver_user_time
        public String getAverUserTime()
        {
            return aver_user_time;
        }

        public void setAverUserTime(String aver_user_time)
        {
            this.aver_user_time = aver_user_time;
        }

        //for unique_user
        public String getUniqueUser()
        {
            return unique_user;
        }

        public void setUniqueUser(String unique_user)
        {
            this.unique_user = unique_user;
        }

        //for datetime
        public Timestamp getDateTime()
        {
            return datetime;
        }

        public void setDateTime(Timestamp datetime)
        {
            this.datetime = datetime;
        }

        //for usersession
        public String getUsersession()
        {
            return usersession;
        }

        public void setUsersession(String usersession)
        {
            this.usersession = usersession;
        }

        //for pageview
        public String getPageView()
        {
            return pageview;
        }

        public void setPageView(String pageview)
        {
            this.pageview = pageview;
        }

        //for ch_urlname
        public String getCh_Urlname()
        {
            return ch_urlname;
        }

        public void setCh_Urlname(String ch_urlname)
        {
            this.ch_urlname = ch_urlname;
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

        //for logSelect
        public String getLogSelect()
        {
            return logSelect;
        }

        public void setLogSelect(String logSelect)
        {
            this.logSelect = logSelect;
        }

        //for ipaddress
        public String getIpAddress()
        {
            return ipaddress;
        }

        public void setIpAddress(String ipaddress)
        {
            this.ipaddress = ipaddress;
        }

        //for ftpuser
        public String getFtpUser()
        {
            return ftpuser;
        }

        public void setFtpUser(String ftpuser)
        {
            this.ftpuser = ftpuser;
        }

        //for ftppasswd
        public String getFtpPasswd()
        {
            return ftppasswd;
        }

        public void setFtpPasswd(String ftppasswd)
        {
            this.ftppasswd = ftppasswd;
        }

        //for ipaddressSelect
        public String getIpAddressSelect()
        {
            return ipaddressSelect;
        }

        public void IpAddressSelect(String ipaddressSelect)
        {
            this.ipaddressSelect = ipaddressSelect;
        }

        //for fileTypeSelected
        public String getFileTypeSelected()
        {
            return filetypeselected;
        }

        public void setFileTypeSelected(String filetypeselected)
        {
            this.filetypeselected = filetypeselected;
        }

        //for rejectedFileType
        public String getRejectedFileType()
        {
            return rejectedFileType;
        }

        public void setRejectedFileType(String rejectedFileType)
        {
            this.rejectedFileType = rejectedFileType;
        }

        //for logdirname
        public String getLogDirName()
        {
            return logdirname;
        }

        public void setLogDirName(String logdirname)
        {
            this.logdirname = logdirname;
        }

        //for uid
        public String getUID()
        {
            return uid;
        }

        public void setUID(String uid)
        {
            this.uid = uid;
        }

        //for ndomainname
        public String getNDomainName()
        {
            return ndomainname;
        }

        public void setNDomainName(String ndomainname)
        {
            this.ndomainname = ndomainname;
        }

        //for nipaddress
        public String getNIpAddress()
        {
            return nipaddress;
        }

        public void setNIpAddress(String nipaddress)
        {
            this.nipaddress = nipaddress;
        }

        //for ftpname
        public String getFtpName()
        {
            return ftpname;
        }

        public void setFtpName(String ftpname)
        {
            this.ftpname = ftpname;
        }

        //for password
        public String getFtpPassword()
        {
            return ftppassword;
        }

        public void setFtpPassword(String ftppassword)
        {
            this.ftppassword = ftppassword;
        }

        //for org_log_path
        public String getOrg_Log_Path()
        {
            return org_log_path;
        }

        public void setOrg_Log_Path(String org_log_path)
        {
            this.org_log_path = org_log_path;
        }

        //for log_type
        public String getLog_Type()
        {
            return log_type;
        }

        public void setLog_Type(String log_type)
        {
            this.log_type = log_type;
        }

        //for req_type
        public String getReq_Type()
        {
            return req_type;
        }

        public void setReq_Type(String req_type)
        {
            this.req_type = req_type;
        }

        //for getRej_Type
        public String getRej_Type()
        {
            return rej_type;
        }

        public void setRej_Type(String rej_type)
        {
            this.rej_type = rej_type;
        }

        //for upurlname
        public String getUpUrlName()
        {
            return upurlname;
        }

        public void setUpUrlName(String upurlname)
        {
            this.upurlname = upurlname;
        }
}


