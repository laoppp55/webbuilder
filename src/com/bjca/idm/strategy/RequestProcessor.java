package com.bjca.idm.strategy;

import javax.naming.AuthenticationException;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * Title:       RequestProcessor
 * Description:
 * Company:     BJCA
 * Author:      liwei
 * Date:        2014/4/18
 * Time:        9:31
 */
public class RequestProcessor {
    //是否失败标志
    private boolean failFlag;
    //返回对象
    private Map map;
    //请求URL
    private String requestUrl;
    //结果
    private String result;

    private HttpCookie cookie;

    private String requestBody;

    /**
     * http请求的方法类型 POST // GET
     */
    private String defaultMethodType="GET";



    public RequestProcessor(Map map, String requestUrl, HttpCookie cookie) {
        this.map = map;
        this.requestUrl = requestUrl;
        this.cookie = cookie;
    }

    public RequestProcessor(Map map, String requestUrl, HttpCookie cookie, String requestBody) {
        this.map = map;
        this.requestUrl = requestUrl;
        this.cookie = cookie;
        this.requestBody = requestBody;
    }

    public RequestProcessor(Map map, String requestUrl, HttpCookie cookie, String requestBody, String methodType) {
        this.map = map;
        this.requestUrl = requestUrl;
        this.cookie = cookie;
        this.requestBody = requestBody;
        this.defaultMethodType=methodType;
    }

    public boolean isFailed() {
        return failFlag;
    }

    public String getResult() {
        return result;
    }

    public RequestProcessor invoke() {
        result = null;
        try {
            result = processRequest(requestUrl, this.defaultMethodType, requestBody, cookie);
        } catch (IOException e) {
            map.put(SdkConsant.STATUS, "307");
            map.put(SdkConsant.MESSAGE, "服务连接错误！");
            failFlag = true;
            return this;
        } catch (AuthenticationException e) {
            if ("401".equals(e.getMessage().trim())) {
                map.put(SdkConsant.STATUS, "308");
                map.put(SdkConsant.MESSAGE, "用户认证失败！");
                failFlag = true;
                return this;
            } else if ("403".equals(e.getMessage().trim())) {
                map.put(SdkConsant.STATUS, "309");
                map.put(SdkConsant.MESSAGE, "用户状态异常！");
                failFlag = true;
                return this;
            } else if ("404".equals(e.getMessage().trim())) {
                map.put(SdkConsant.STATUS, "310");
                map.put(SdkConsant.MESSAGE, "请求地址不存在！");
                failFlag = true;
                return this;
            } else {
                map.put(SdkConsant.STATUS, "304");
                map.put(SdkConsant.MESSAGE, "系统内部错误！");
                failFlag = true;
                return this;
            }
        }
        failFlag = false;
        return this;
    }

    /**
     * process rest request
     *
     * @param requestUrl      rest service url
     * @param type            POST|PUT|DELETE|GET
     * @param postRequestBody request content
     * @param cookie
     * @return String
     */
    private String processRequest(String requestUrl, String type, String postRequestBody, HttpCookie cookie) throws IOException, AuthenticationException {
        HttpURLConnection con;
        String result;
        BufferedReader in;
        int responseCode;
        con = getHttpConnection(requestUrl, type);
        if (cookie != null) {
            con.setRequestProperty(SdkConsant.COOKIE, cookie.getName() + SdkConsant.EQUAL + cookie.getValue());
        }
        //you can add any request body here if you want to post
        if (postRequestBody != null && "POST".equals(type)) {
            con.setDoInput(true);
            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.write(postRequestBody.getBytes(SdkConsant.UTF_8));
            out.flush();
            out.close();
        }

        con.connect();
        responseCode = con.getResponseCode();
        if (responseCode == 200) {
            in = new BufferedReader(new InputStreamReader(con.getInputStream(), SdkConsant.UTF_8));
        } else {
            throw new AuthenticationException(String.valueOf(responseCode));
        }
        String temp;
        StringBuffer sb = new StringBuffer();
        while ((temp = in.readLine()) != null) {
            sb.append(temp).append(" ");
        }
        in.close();
        result = sb.toString().trim();
        con.disconnect();
        if ("401".equals(result.trim())) {
            throw new AuthenticationException("401");
        } else if ("403".equals(result.trim())) {
            throw new AuthenticationException("403");
        } else if ("404".equals(result.trim())) {
            throw new AuthenticationException("404");
        } else if ("500".equals(result.trim())) {
            throw new AuthenticationException("500");
        }
        return result;
    }

    /**
     * get HttpURLConnection with rest URL
     *
     * @param url  rest service url
     * @param type POST|PUT|DELETE|GET
     * @return HttpURLConnection
     */
    private HttpURLConnection getHttpConnection(String url, String type) throws IOException {
        //设置连接超时时间
        String defaultConnectTimeout = System.getProperty("sun.net.client.defaultConnectTimeout");
        //设置读取超时时间
        String defaultReadTimeout = System.getProperty("sun.net.client.defaultReadTimeout");
        if (defaultConnectTimeout == null || "".equals(defaultConnectTimeout.trim())) {
            //连接主机的超时时间（单位：毫秒）
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        }
        if (defaultReadTimeout == null || "".equals(defaultReadTimeout.trim())) {
            //从主机读取数据的超时时间（单位：毫秒）
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        }
        URL uri;
        HttpURLConnection httpURLConnection = null;
        uri = new URL(url);
        if("https".equals(uri.getProtocol())) {
            try {
                httpURLConnection = getHttpConnection(uri);
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        } else {
            httpURLConnection = (HttpURLConnection) uri.openConnection();
        }

        //type: POST, PUT, DELETE, GET
        httpURLConnection.setRequestMethod(type);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setUseCaches(false);
        httpURLConnection.setRequestProperty("Accept-Encoding", "application/json;charset=UTF-8");
        httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        return httpURLConnection;
    }



    private HttpURLConnection getHttpConnection( URL console) throws NoSuchProviderException, NoSuchAlgorithmException, IOException, KeyManagementException {
        TrustManager[] tm = { new TrustAnyTrustManager() };
        // SSLContext sc = SSLContext.getInstance("SSL");
        SSLContext sc = SSLContext.getInstance("SSL", "SunJSSE");
        sc.init(null, tm,new java.security.SecureRandom());

        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
        conn.setSSLSocketFactory(sc.getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        return  conn;
    }

    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
        }
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[] {};
        }
    }
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

}
