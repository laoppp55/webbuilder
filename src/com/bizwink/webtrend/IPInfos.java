package com.bizwink.webtrend;

import java.util.List;
import java.util.Map;

public class IPInfos {
    private String ipaddress;
    private int accessnum;
    private Map<String,List> urls;
    private Map<String,List> accesstime;

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public Map<String,List> getUrls() {
        return urls;
    }

    public int getAccessnum() {
        return accessnum;
    }

    public void setAccessnum(int accessnum) {
        this.accessnum = accessnum;
    }

    public void setUrls(Map<String,List> urls) {
        this.urls = urls;
    }

    public Map<String,List> getAccesstime() {
        return accesstime;
    }

    public void setAccesstime(Map<String,List> accesstime) {
        this.accesstime = accesstime;
    }
}
