package com.ucap.repository.utils;

import okhttp3.*;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HttpUtil {

    /**
     * 请求编码
     */
    public static String requestEncoding = "UTF-8";
    /**
     * 连接超时
     */
    private static int connectTimeOut = 20000;
    /**
     * 读取数据超时
     */
    private static int readTimeOut = 40000;
    //	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HttpUtil.class);
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    static  OkHttpClient client = initOkHttpClient();

    /**
     * 将网络文件下载保存到本地
     *
     * @param urlPath     网络url地址
     * @param downloadDir 下载存放目录
     * @return 返回下载文件
     */
    public static File downloadFile(String urlPath, String downloadDir) {
        File file = null;
        try {
            // 统一资源
            URL url = new URL(urlPath);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
            httpURLConnection.connect();

            // 文件大小
            // int fileLength = httpURLConnection.getContentLength();

            // 文件名
            String filePathUrl = httpURLConnection.getURL().getFile();
            String fileFullName = filePathUrl.substring(filePathUrl.lastIndexOf(File.separatorChar) + 1);

            // log.info("file length---->" + fileLength);

            URLConnection con = url.openConnection();

            BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

            String path = downloadDir + File.separatorChar + fileFullName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            int len = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                len += size;
                out.write(buf, 0, size);
                // 打印下载百分比
                // System.out.println("下载了-------> " + len * 100 / fileLength +
                // "%\n");
            }
            bin.close();
            out.close();
        } catch (Exception e) {
            logger.error("下载出错,下载地址:" + urlPath + ",保存地址:" + downloadDir + ",异常信息:", e);
        } finally {
            return file;
        }

    }

    /**
     * 作用：实现网络访问文件，将获取到的数据存在字节数组中
     *
     * @param url ：访问网络的url地址
     * @return byte[]
     */
    public static byte[] loadByteFromURL(String url) {
        HttpURLConnection httpConn = null;
        BufferedInputStream bis = null;
        try {
            URL urlObj = new URL(url);
            httpConn = (HttpURLConnection) urlObj.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setDoInput(true);
            httpConn.setConnectTimeout(10 * 1000);
            httpConn.connect();

            if (httpConn.getResponseCode() == 200) {
                bis = new BufferedInputStream(httpConn.getInputStream());
                return streamToByte(bis);
            }
            bis = new BufferedInputStream(httpConn.getInputStream());
            byte[] b = streamToByte(bis);
            logger.error("将网络URL转成字节数组出错,url地址：" + url + ",CODE:" + httpConn.getResponseCode() + "b:" + (b == null));
            if (b != null) {
                logger.error("将网络URL转成字节数组出错,url地址：" + url + ",CODE:" + httpConn.getResponseCode() + "b:" + b.length);
            }
        } catch (Exception e) {
            logger.error("将网络URL转成字节数组出错,url地址：" + url + ",错误信息:", e);
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (httpConn != null) {
                	httpConn.disconnect();
                }
            } catch (IOException e) {
                logger.error("将网络URL转成字节数组关闭输出流出错,url地址：" + url + ",错误信息:", e);
            }
        }
        return null;
    }

    public static byte[] streamToByte(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int c = 0;
        byte[] buffer = new byte[8 * 1024];
        try {
            while ((c = is.read(buffer)) != -1) {
                baos.write(buffer, 0, c);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            logger.error("文件流转换字节出现异常：", e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                logger.error("关闭文件流出现异常：", e);
            }
        }
        return null;
    }

    /**
     * 从http url 提取参数
     * 如链接地址为http://192.168.1.7:8888/website-webapp/common/preview_resource.action?id=7c7d2c83a97346858a4fd45ee692f28e&type=doc&jcrVer=1.0
     * 返回结果为Map {jcrVer=1.0, type=doc, id=7c7d2c83a97346858a4fd45ee692f28e}
     *
     * @param httpUrl
     * @return
     */
    public static Map<String, String> parseUrlParamer(String httpUrl) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            URL u = new URL(httpUrl);
            // 假如http 后面不带参数 返回map 对象，防止解析报错
            if (u.getQuery() == null) {
                return map;
            }
            String[] urlParamer = u.getQuery().split("&");
            for (int i = 0; i < urlParamer.length; i++) {
                String temp[] = urlParamer[i].split("=");
                String value = "";
                // 防止value 为空
                if (temp.length > 1 || temp[1] != null) {
                    value = temp[1];
                }
                map.put(temp[0], value);
            }

        } catch (Exception e) {
            logger.error("parseUrlParamer:httpUrl:" + httpUrl + ",提取参数出错:", e);
        }
        return map;
    }

    /**
     * <pre>
     * 发送带参数的GET的HTTP请求
     * </pre>
     *
     * @param reqUrl     HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    public static String doGet(String reqUrl, Map parameters, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            url_con = doHttpGetConnection(reqUrl, parameters);
            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }

        return responseContent;
    }

    private static HttpURLConnection doHttpGetConnection(String reqUrl, Map parameters) throws IOException {
        HttpURLConnection url_con = null;
        String params = getMapParamsToStr(parameters, HttpUtil.requestEncoding);

        URL url = new URL(reqUrl);
        url_con = (HttpURLConnection) url.openConnection();
        url_con.setRequestMethod("GET");
        url_con.setRequestProperty("User-agent","Mozilla/4.0");
        System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpUtil.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
        System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpUtil.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时

        url_con.setDoOutput(true);
        byte[] b = params.toString().getBytes();
        url_con.getOutputStream().write(b, 0, b.length);
        url_con.getOutputStream().flush();
        url_con.getOutputStream().close();

        return url_con;
    }

    /**
     * 大汉接口调用专用
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String getNormalHttpResult(String url, String param) throws  IOException{
        org.apache.http.client.HttpClient httpClient = new org.apache.http.impl.client.DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity postingString = new StringEntity(param);// json传递
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        return result;
    }
    /**
     * <pre>
     * 发送不带参数的GET的HTTP请求
     * </pre>
     *
     * @param reqUrl HTTP请求URL
     * @return HTTP响应的字符串
     */
    public static String doGet(String reqUrl, String recvEncoding) {
        String responseContent = null;
        try {
            StringBuffer params = new StringBuffer();
            String queryUrl = reqUrl;
            int paramIndex = reqUrl.indexOf("?");

            if (paramIndex > 0) {
                queryUrl = reqUrl.substring(0, paramIndex);
                String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
                String[] paramArray = parameters.split("&");
                for (int i = 0; i < paramArray.length; i++) {
                    String string = paramArray[i];
                    int index = string.indexOf("=");
                    if (index > 0) {
                        String parameter = string.substring(0, index);
                        String value = string.substring(index + 1, string.length());
                        params.append(parameter);
                        params.append("=");
                        params.append(URLEncoder.encode(value, HttpUtil.requestEncoding));
                        params.append("&");
                    }
                }

                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(reqUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestProperty("User-agent","Mozilla/4.0");
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            InputStream in = httpUrlConn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
        }

        return responseContent;
    }

    /**
     * <pre>
     * 发送不带参数的POST的HTTP请求
     * </pre>
     *
     * @param reqUrl HTTP请求URL
     * @return HTTP响应的字符串
     */
    public static String doPost(String reqUrl, String recvEncoding) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            StringBuffer params = new StringBuffer();
            String queryUrl = reqUrl;
            int paramIndex = reqUrl.indexOf("?");

            if (paramIndex > 0) {
                queryUrl = reqUrl.substring(0, paramIndex);
                String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
                String[] paramArray = parameters.split("&");
                for (int i = 0; i < paramArray.length; i++) {
                    String string = paramArray[i];
                    int index = string.indexOf("=");
                    if (index > 0) {
                        String parameter = string.substring(0, index);
                        String value = string.substring(index + 1, string.length());
                        params.append(parameter);
                        params.append("=");
                        params.append(URLEncoder.encode(value, HttpUtil.requestEncoding));
                        params.append("&");
                    }
                }

                params = params.deleteCharAt(params.length() - 1);
            }

            URL url = new URL(queryUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpUtil.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpUtil.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
            url_con.setRequestProperty("Content-Length", params.toString().length() + "");
            url_con.setRequestProperty("User-agent","Mozilla/4.0");
            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer temp = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                temp.append(tempLine);
                temp.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = temp.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }

        return responseContent;
    }

    /**
     * <pre>
     * 发送带参数的POST的HTTP请求
     * </pre>
     *
     * @param reqUrl     HTTP请求URL
     * @param parameters 参数映射表
     * @return HTTP响应的字符串
     */
    public static String doPost(String reqUrl, Map parameters) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            String params = getMapParamsToStr(parameters, HttpUtil.requestEncoding);
            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            url_con.setRequestProperty("User-agent","Mozilla/4.0");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpUtil.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpUtil.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时

            url_con.setDoOutput(true);
            byte[] b = params.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();
            int responseCode = url_con.getResponseCode();  
            InputStream in=null;
	    	if (responseCode == 200) {  
	    		in = new BufferedInputStream(url_con.getInputStream());  
	   	    } else {
	    		in = new BufferedInputStream(url_con.getErrorStream());  
	    	} 
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, HttpUtil.requestEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * <pre>
     * 发送带参数的POST的HTTP请求
     * </pre>
     *
     * @param reqUrl   HTTP请求URL
     * @param jsonData 发送数据，raw
     * @return HTTP响应的字符串
     */
    public static String doJsonPost(String reqUrl, String jsonData) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpUtil.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpUtil.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
            url_con.setDoOutput(true);
            url_con.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            url_con.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式

            byte[] b = jsonData.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, HttpUtil.requestEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    public static String doXmlPost(String reqUrl, String xmlData) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            URL url = new URL(reqUrl);
            url_con = (HttpURLConnection) url.openConnection();
            url_con.setRequestMethod("POST");
            System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpUtil.connectTimeOut));// （单位：毫秒）jdk1.4换成这个,连接超时
            System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpUtil.readTimeOut)); // （单位：毫秒）jdk1.4换成这个,读操作超时
            url_con.setDoOutput(true);
            url_con.setRequestProperty("Accept", "application/xml"); // 设置接收数据的格式
            url_con.setRequestProperty("Content-Type", "application/xml"); // 设置发送数据的格式

            byte[] b = xmlData.toString().getBytes();
            url_con.getOutputStream().write(b, 0, b.length);
            url_con.getOutputStream().flush();
            url_con.getOutputStream().close();

            InputStream in = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, HttpUtil.requestEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
            in.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * @param reqUrl
     * @param parameters
     * @param recvEncoding
     * @param fileIn       文件流
     * @return
     */
    public static String uploadMedia(String reqUrl, Map parameters, String recvEncoding, InputStream fileIn, String fileName, String contentType) {
        HttpURLConnection url_con = null;
        String responseContent = null;
        try {
            // 设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            // String params =
            // getMapParamsToStr(parameters,HttpRequestProxy.requestEncoding);

            URL urlObj = new URL(reqUrl);// + "&" + params.toString());
            // 连接
            url_con = (HttpURLConnection) urlObj.openConnection();
            /**
             * 设置关键值
             */
            url_con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
            url_con.setDoInput(true);
            url_con.setDoOutput(true);
            url_con.setUseCaches(false); // post方式不能使用缓存

            // 设置请求头信息
            url_con.setRequestProperty("Connection", "Keep-Alive");
            url_con.setRequestProperty("Charset", recvEncoding);

            // 设置边界
            url_con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            // 请求正文信息

            // 第一部分：
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + fileName + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes(recvEncoding);

            // 获得输出流
            OutputStream out = new DataOutputStream(url_con.getOutputStream());
            // 输出表头
            out.write(head);
            // 文件正文部分
            // 把文件以流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(fileIn);
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();
            if (parameters.get("type") != null && parameters.get("type").toString().equals("video")) {
                out.write(("--" + BOUNDARY + "\r\n").getBytes());
                out.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes());
                out.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}", parameters.get("title").toString(), parameters.get("introduction").toString()).getBytes());
            }
            out.write(("\r\n--" + BOUNDARY + "--\r\n\r\n").getBytes());
            out.flush();
            out.close();
            in.close();

            InputStream iddn = url_con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(iddn, recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf = System.getProperty("line.separator");
            while (tempLine != null) {
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            responseContent = tempStr.toString();
            rd.close();
        } catch (IOException e) {
            logger.error("网络故障", e);
        } finally {
            if (url_con != null) {
                url_con.disconnect();
            }
        }
        return responseContent;
    }

    /**
     * @param actionUrl url 可带参数 ?data=111&data1=222  拼接
     * @param fStream   上传文件流
     * @param filename  文件名称
     * @return
     * @Description TODO(描述)  上传文件方法
     * @author xiaoG 2018年7月23日下午3:34:34
     */
    public static String uploadFile(String actionUrl, FileInputStream fStream, String filename) {
        String end = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        HttpURLConnection httpURLConnection = null;
        DataOutputStream ds = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader reader = null;
        StringBuffer resultBuffer = new StringBuffer();
        String tempLine = null;

        try {
            // 统一资源
            URL url = new URL(actionUrl);
            // 连接类的父类，抽象类
            URLConnection urlConnection = url.openConnection();
            // http的连接类
            httpURLConnection = (HttpURLConnection) urlConnection;

            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpURLConnection.setDoInput(true);
            // 设置是否向httpUrlConnection输出
            httpURLConnection.setDoOutput(true);
            // Post 请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            // 设定请求的方法，默认是GET
            httpURLConnection.setRequestMethod("POST");
            // 设置字符编码连接参数
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            // 设置字符编码
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            // 设置请求内容类型
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            // 设置DataOutputStream
            ds = new DataOutputStream(httpURLConnection.getOutputStream());
            ds.writeBytes(twoHyphens + boundary + end);

            ds.writeBytes("Content-Disposition: form-data; " + "name=\"file" + "\";filename=\"" + new String(filename.getBytes("utf-8"),"iso-8859-1") + "\"" + end);
            ds.writeBytes(end);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = fStream.read(buffer)) != -1) {
                ds.write(buffer, 0, length);
            }
            ds.writeBytes(end);
            /* close streams */
            fStream.close();
            ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
            ds.flush();
            if (httpURLConnection.getResponseCode() >= 300) {
                throw new Exception("HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());
            }

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                reader = new BufferedReader(inputStreamReader);
                tempLine = null;
                resultBuffer = new StringBuffer();
                while ((tempLine = reader.readLine()) != null) {
                    resultBuffer.append(tempLine);
                    resultBuffer.append("\n");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return resultBuffer.toString();
    }

    /**
     * OkHttp方式上传文件
     * @param actionUrl 请求的url
     * @param fileInputStream 文件流
     * @param filename 文件名
     * @return
     * @throws IOException
     */
    public static String uploadFileByOkHttp(String actionUrl, FileInputStream fileInputStream, String filename) throws IOException {

        try {
            byte[] byt = new byte[fileInputStream.available()];
            fileInputStream.read(byt);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", filename,
                            RequestBody.create(MediaType.parse("multipart/form-data"),byt))
                    .build();
            okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0");
            headers.put("Connection", "Keep-Alive");
            headers.put("Content-Type","application/json;charset=UTF-8");
            headers.put("Authorization", "Client-ID " + UUID.randomUUID());
            okhttp3.Headers okHeaders = okhttp3.Headers.of(headers);
            builder.headers(okHeaders);
            // 创建请求
            Request request = builder.url(actionUrl).post(requestBody).build();
            Response response = client.newCall(request).execute();
            return new String(response.body().bytes(),"utf-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将参数转换成string
     *
     * @param paramMap
     * @param requestEncoding
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String getMapParamsToStr(Map paramMap, String requestEncoding) throws IOException {
        StringBuffer params = new StringBuffer();
        // 设置边界
        for (Iterator iter = paramMap.entrySet().iterator(); iter.hasNext(); ) {
            Map.Entry element = (Map.Entry) iter.next();
            params.append(element.getKey().toString());
            params.append("=");
            params.append(URLEncoder.encode(element.getValue().toString(), requestEncoding));
            params.append("&");
        }

        if (params.length() > 0) {
            params = params.deleteCharAt(params.length() - 1);
        }

        return params.toString();
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     * <p>
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     * <p>
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     * <p>
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * @return 请求编码
     */
    public static String getRequestEncoding() {
        return requestEncoding;
    }

    /**
     * @param requestEncoding 请求编码
     */
    public static void setRequestEncoding(String requestEncoding) {
        HttpUtil.requestEncoding = requestEncoding;
    }

    /**
     * @param connectTimeOut 连接超时(毫秒)
     */
    public static void setConnectTimeOut(int connectTimeOut) {
        HttpUtil.connectTimeOut = connectTimeOut;
    }

    /**
     * @param readTimeOut 读取数据超时(毫秒)
     */
    public static void setReadTimeOut(int readTimeOut) {
        HttpUtil.readTimeOut = readTimeOut;
    }

    public static String doPost(String url, String appName, String data) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setDoOutput(true);
        urlConnection.setDoInput(true);
        urlConnection.setUseCaches(false);
        urlConnection.setRequestProperty("contentType", "UTF-8");
        // 构造提交的参数
        urlConnection.connect();
        StringBuffer param = new StringBuffer();
        param.append("appName=" + appName + "&data=" + data);
        urlConnection.getOutputStream().write(param.toString().getBytes());
        urlConnection.getOutputStream().flush();
        urlConnection.getOutputStream().close();
        String ecod = urlConnection.getContentEncoding();
        if (ecod == null)
            ecod = "UTF-8";
        InputStream in = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, ecod));
        StringBuffer temp = new StringBuffer();
        String line = bufferedReader.readLine();
        while (line != null) {
            temp.append(line);
            line = bufferedReader.readLine();
        }
        in.close();
        bufferedReader.close();
        String content = temp.toString();
        return content;
    }

    /**
     * 初始化OkHttpClient
     *
     * @return
     * @throws Exception 
     */
    public static OkHttpClient initOkHttpClient() {
    	 final X509TrustManager trustManager = new X509TrustManager() {
             @Override
             public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

             }

             @Override
             public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

             }

             @Override
             public X509Certificate[] getAcceptedIssuers() {
                 return new X509Certificate[0];
             }
         };
        SSLContext sslContext=null;
		try {
			sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        SSLSocketFactory sslSocketFactory=null;
        if(!Validator.isEmpty(sslContext)){
        //if (sslContext!=null) {
        	 sslSocketFactory = sslContext.getSocketFactory();
        }
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        OkHttpClient client = new OkHttpClient().newBuilder().sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(DO_NOT_VERIFY).connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();
        return client;
    }

    public static void main(String[] args) {
        byte[] bytes = downloadFile("http://ysp.www.gov.cn/013582404bd78ad3c016b8fffefe6a9a/allmobilize.min.js");
        String str = new String(bytes);
        System.out.println(str);
    }

    /**
     * 根据网络地址下载文件返回二进制流
     *
     * @param filePath
     * @return
     */
    public static byte[] downloadFile(String filePath) {
        //接收响应
        Response response = null;
        try {
            //创建请求
            Request.Builder builder = new Request.Builder().url(filePath);
            builder.addHeader("refer", filePath);
            builder.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            Request request = builder.build();
            response = client.newCall(request).execute();
            return response.body().bytes();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return null;
    }
    /*public static String getNormalHttpResult(String url, String param) {
        String contentType = "application/json";
        String charset = "UTF-8";

        HttpSender httpSender = HttpSender.getInstancePost(url);
        httpSender.setRequestCharset(charset);
        httpSender.setResultCharset(charset);
        RequestEntity requestEntity;
        try {
            requestEntity = new StringRequestEntity(param, contentType, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "Entity ERROR";
        }
        httpSender.setRequestBody(requestEntity);
        if (httpSender.execute()) {
            return httpSender.getResult();

        } else {
            return "HTTP ERROR!";
        }
    }*/


}
