package com.wxpay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Administrator on 18-9-23.
 */
public class WXPayServiceConfig extends WXPayConfig {
    private byte[] certData;
    private static WXPayServiceConfig INSTANCE;

    public static WXPayServiceConfig getInstance() throws Exception {
        if (INSTANCE == null) {
            synchronized (WXPayServiceConfig.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayServiceConfig();
                }
            }
        }
        return INSTANCE;
    }

    public WXPayServiceConfig(String certPath) throws Exception {
        String theCertPath = certPath;
        File file = new File(theCertPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public WXPayServiceConfig() throws Exception {

    }

    public String getAppID() {
        return "wx535f9f1638e08a6f";
    }

    public String getMchID() {
        return "1515884491";
    }

    public String getKey() {
        //return "999bjrbfxgs96060BJRBFXGS52175678";
        return "12345678900987654321234321234543";
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public String getSpbillCreateIp() {
        return "218.249.193.150";
    }

    public String getNotifUrl() {
        return "http://cs.jbfx.com.cn:8090/ec/m/wxcallback.jsp";
    }

    public String getRefundNotifyUrl() {
        return "http://cs.jbfx.com.cn:8090/ec/wxrefundcallback.jsp";
    }

    public String getTradeType() {
        return "NATIVE";
    }

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
