package com.heaton.bot;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 *
 * @author unascribed
 * @version 1.0
 */

public class proxySetup {

    int id;
    String proxyHost;
    String proxyPort;
    String proxyUserID;
    String proxyPWD;
    int proxyFlag;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String host) {
        this.proxyHost = host;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String port) {
        this.proxyPort = port;
    }

    public String getProxyUser() {
        return proxyUserID;
    }

    public void setProxyUserID(String userid) {
        this.proxyUserID = userid;
    }

    public String getProxyPWD() {
        return proxyPWD;
    }

    public void setProxyPWD(String pwd) {
        this.proxyPWD = pwd;
    }

    public int getProxyFlag() {
        return proxyFlag;
    }

    public void setProxyFlag(int proxyFlag) {
        this.proxyFlag = proxyFlag;
    }
}