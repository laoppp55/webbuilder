package com.heaton.bot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseHtml {

    //服务器端返回的头信息
    //Date = Mon, 21 Nov 2005 01:34:25 GMT
    //Server = Apache/1.3.31 (Unix) mod_gzip/1.3.19.1a
    //Cache-Control = max-age=5184000
    //Expires = Fri, 20 Jan 2006 01:34:25 GMT
    //Last-Modified = Sun, 20 Nov 2005 07:51:51 GMT
    //ETag = "ef50f0-113-43802b17"
    //Accept-Ranges = bytes
    //Content-Length = 275
    //Content-Type = image/gif
    //Age = 43924
    //X-Cache = HIT from squid.sohu.com
    //X-Cache-Lookup = HIT from squid.sohu.com:80
    //Connection = close
    public void parseHtml(String url, String titlekey, String contentkey, int relation,List tags, proxySetup proxy) {
        try {
            String sCurrentLine;
            String sTotalString = "";
            String[] parseResult;

            if ((proxy.getProxyUser() != null) && (proxy.getProxyPWD() != null)) {
                System.getProperties().put("proxySet", "true");
                System.getProperties().put("proxyHost", proxy.getProxyHost());
                System.getProperties().put("proxyPort", proxy.getProxyPort());
            }

            InputStream l_urlStream;
            URL l_url = new URL(url);
            HttpURLConnection l_connection = (HttpURLConnection) l_url.openConnection();

            if ((proxy.getProxyUser() != null) && (proxy.getProxyPWD() != null)) {
                String proxyauth = proxy.getProxyUser() + ":" + proxy.getProxyPWD();
                String encodedProxyAuth = new sun.misc.BASE64Encoder().encode(proxyauth.getBytes());
                l_connection.setRequestProperty("Proxy-Authorization", encodedProxyAuth);
            }

            l_connection.connect();
            l_urlStream = l_connection.getInputStream();

            //获取页面编码方式
            String encodestr = getEncode(url);
            if (encodestr != null)
                encodestr = encodestr.toLowerCase();
            else
                encodestr = "";

            //根据不同的编码方式抓取页面
            BufferedReader l_reader;
            if (encodestr.equals("utf-8")) {
                InputStreamReader read = new InputStreamReader(l_urlStream, "UTF-8");
                l_reader = new java.io.BufferedReader(read);
            } else {
                l_reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(l_urlStream));
            }

            while ((sCurrentLine = l_reader.readLine()) != null) {
                sTotalString += sCurrentLine;
            }

            parseResult = extractText(sTotalString, titlekey, contentkey, relation, tags, url, 1,null);
            if (parseResult != null) {
                System.out.println("Title is : " + parseResult[0]);
                System.out.println("Content is : " + parseResult[1]);
            } else {
                System.out.println("没有匹配文章");
            }
        } catch (Exception e) {
        }
    }

    /**
     * 抽取文章内容
     *
     * @param inputHtml      抓取到的页面代码
     * @param titleKey       标题关键字
     * @param contentKey     内容关键字
     * @param relation       标题与内容关键字的关系
     * @param tags           特征码
     * @return 提取后的内容
     * @throws Exception 异常
     */
    public String[] extractText(String inputHtml, String titleKey, String contentKey, int relation,
                                List tags,String url, int siteid,List columns) throws Exception {

        String[] result = new String[2];
        int doflag = 0;
        boolean gettedArticle = false;
        boolean havemarkcode = false;
        boolean titleOk = false;
        boolean contentOk = false;

        //从数据库中获得标题，并对标题进行关键字匹配
        result[0] = getTitle(url, siteid);


        //如果有特征码，截取特征码中间的部分
        if (tags.size() > 0) {
            for(int i=0; i<tags.size(); i++)  {
                StartEndTag tag = new StartEndTag();
                tag = (StartEndTag)tags.get(i);
                String begin_markcode = tag.getStarttag();
                String end_markcode = tag.getEndtag();
                begin_markcode = new String(begin_markcode.getBytes("iso8859_1"), "GBK");
                end_markcode = new String(end_markcode.getBytes("iso8859_1"), "GBK");

                if ((inputHtml.indexOf(begin_markcode) + begin_markcode.length()) < inputHtml.indexOf(end_markcode)) {
                    inputHtml = inputHtml.substring(inputHtml.indexOf(begin_markcode) + begin_markcode.length(), inputHtml.indexOf(end_markcode));
                    havemarkcode = true;
                    break;
                }
            }
        }

        //剔除javascript、css、object和iframe
        htmlFilter filter = new htmlFilter(inputHtml);
        inputHtml = filter.resultByFilter("<script", "</script>");
        filter.setContent(inputHtml);
        inputHtml = filter.resultByFilter("<style", "</style>");
        filter.setContent(inputHtml);
        inputHtml = filter.resultByFilter("<iframe", "</iframe>");
        filter.setContent(inputHtml);
        inputHtml = filter.resultByFilter("<object", "</object>");
        filter.setContent(inputHtml);
        inputHtml = filter.resultByFilter("<head", "</head>");
        filter.setContent(inputHtml);
        inputHtml = filter.resultByFilter("<marquee", "</marquee>");

        //取出<body></body>之间的部分
        if ((inputHtml.indexOf("<body") != -1) && (inputHtml.indexOf("</body>") != -1)) {
            inputHtml = inputHtml.substring(inputHtml.indexOf("<body"), inputHtml.indexOf("</body>"));
        }

        if ((!havemarkcode) && (inputHtml != null) && (inputHtml.indexOf("<") != -1)) {
            inputHtml = inputHtml.substring(inputHtml.indexOf("<"));
            result[1] = parseContent(inputHtml);
        } else {
            result[1] = inputHtml;
        }
        if (result[1].indexOf("<img") != -1)
            result[1] = processContentImage(result[1], url);

        SaveContent sv = new SaveContent();
        sv.save(columns, result[0], result[1], new Timestamp(System.currentTimeMillis()), url, 0);
        return null;
    }

    /**
     * 分析内容
     *
     * @param inputHtml 需要分析的Html代码
     * @return 返回分析后的内容
     */
    private static String parseContent(String inputHtml) {
        String parseResult = "";

        inputHtml = parseTag(inputHtml);

        Pattern p = Pattern.compile("<*\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(inputHtml);
        if (m.find()) {
            String[] splittag = p.split(inputHtml);

            if ((splittag != null) && (splittag.length > 0))
                for (int i = 0; i < splittag.length; i++) {
                    if ((splittag[i] != null) && (splittag[i].length() > 0) && (splittag[i].length() > 50)) {
                        if (((splittag[i].getBytes().length - splittag[i].length()) > 5) &&
                                (splittag[i].toLowerCase().indexOf("copyright &copy;") == -1))     //至少含有5个汉字并且不含有copyright
                            parseResult += splittag[i];
                    }
                }

            parseResult = parseResult.replaceAll("&lt;", "<");
            parseResult = parseResult.replaceAll("&gt;", ">");
            if (parseResult.startsWith("null"))
                parseResult = parseResult.substring(parseResult.indexOf("null") + 4);
            parseResult = parseResult.trim();
            if (parseResult.startsWith("</"))
                parseResult = parseResult.substring(parseResult.indexOf(">") + 1);
        }

        return parseResult;
    }

    private static String parseTag(String inputHtml) {
        //替换有用的标记开始
        Pattern p = Pattern.compile("<(p|font|br|span|div|strong|u|center|img|image|li)(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;$1$2&gt;");
        p = Pattern.compile("<\\s*/\\s*(p|font|br|span|div|strong|u|center|img|image|li)\\s*>", Pattern.CASE_INSENSITIVE);
        m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;/$1&gt;");

        p = Pattern.compile("<(b|i)>", Pattern.CASE_INSENSITIVE);
        m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;$1&gt;");
        p = Pattern.compile("<\\s*/\\s*(b|i)>", Pattern.CASE_INSENSITIVE);
        m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;/$1&gt;");

        p = Pattern.compile("<h([1-6]{1})(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
        m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;h$1$2&gt;");
        p = Pattern.compile("</h([1-6]{1})>", Pattern.CASE_INSENSITIVE);
        m = p.matcher(inputHtml);
        inputHtml = m.replaceAll("&lt;/h$1&gt;");
        //替换有用的标记结束

        return inputHtml;
    }

    private static String getEncode(String geturl) throws Exception {
        URL url = new URL(geturl);
        String charset;
        Pattern pattern = Pattern.compile("charset.*=.*>?", Pattern.CASE_INSENSITIVE);
        URLConnection con = url.openConnection();
        String contentType = con.getContentType(); //先尝试从http响应头获取字符编码
        charset = doGetEncode(pattern, contentType);
        if (charset == null) {  //如果得不到，尝试从页面的元数据信息上获取
            InputStream is = url.openStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int count;
            byte[] bytes = new byte[1024];
            while ((count = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, count);
                bos.flush();
                charset = doGetEncode(pattern, bos.toString());
                if (charset != null) {  //找到编码
                    break;
                }
                bos.reset();
            }
        }
        return charset;
    }

    /**
     * 读取页面数据匹配模式
     *
     * @param pattern 正则表达式
     * @param str     带有编码的字符串
     * @return 页面编码
     * @throws Exception 异常
     */
    private static String doGetEncode(Pattern pattern, String str) throws Exception {
        Matcher matcher;
        String matchStr;
        String charset = null;
        matcher = pattern.matcher(str);
        if (matcher.find()) {  //找到第一个符合要求的
            matchStr = matcher.group();
            //截取希望处理的字符串,替换可能的特殊符号
            charset = matchStr.substring(matchStr.indexOf("=") + 1).replaceAll("[\"|\\|/|\\s].*[/>|>]", "");
        }
        return charset;
    }

    private String getTitle(String url, int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String title = "";

        String GET_TITLE_BY_URL = "select urltitle from tbl_workload_" + siteid + " where url = ?";

        try {
            conn = Server.createConnection();
            pstmt = conn.prepareStatement(GET_TITLE_BY_URL);
            pstmt.setString(1, url);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                title = rs.getString(1);
                //title = title == null ? "" : new String(title.getBytes("iso8859_1"), "GBK");
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return title;
    }

    //处理文章内容中的图片，如果是相对路径替换为绝对路径
    private String processContentImage(String content, String myurl) {

        Pattern p = Pattern.compile("<img[^<>]*\\s+src(\\s*)=(\\s*)[^<>]*>", Pattern.CASE_INSENSITIVE);
        String buf[] = p.split(content);
        String tag[] = new String[buf.length - 1];

        for (int i = 0; i < buf.length - 1; i++) {
            content = content.substring(buf[i].length());
            int posi = content.indexOf(">");
            tag[i] = content.substring(0, posi + 1);
            content = content.substring(tag[i].length());
            tag[i] = formatSrc_Str(tag[i], myurl);
            //System.out.println("format content image src: " + tag[i]);
        }

        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < tag.length; i++) {
            buffer.append(buf[i]);
            buffer.append(tag[i]);
        }
        buffer.append(buf[tag.length]);

        return buffer.toString();
    }


    private String formatSrc_Str(String imagesrc, String myurl) {

        String url = myurl;
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        if (url.indexOf("http://") != -1) {
            imagesrc = imagesrc.trim();

            buf = imagesrc.toLowerCase();
            posi = buf.indexOf("src");
            head_str = imagesrc.substring(0, posi);
            buf = imagesrc.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                fileName = buf.substring(0, posi);
                tail_str = buf.substring(posi);
            } else {
                fileName = buf.substring(0, buf.length() - 1);
                tail_str = ">";
            }

            posi = fileName.indexOf("\"");
            if (posi == 0) fileName = fileName.substring(1, fileName.length());
            posi = fileName.indexOf("'");
            if (posi == 0) fileName = fileName.substring(1, fileName.length());

            posi = fileName.lastIndexOf("'");
            if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
            posi = fileName.lastIndexOf("\"");
            if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

            if (fileName.startsWith("./")) {
                url = url.substring(0, url.lastIndexOf("/"));
                fileName = url + fileName.substring(1);
            } else if (fileName.startsWith("../")) {
                int times = (fileName.length() - fileName.replaceAll("../", "").length()) / 3;
                url = url.substring(0, url.lastIndexOf("/"));
                for (int i = 0; i < times; i++) {
                    url = url.substring(0, url.lastIndexOf("/"));
                }
                url = url + "/";
                fileName = fileName.replaceAll("\\.\\.\\/", "");
                fileName = url + fileName;
            } else if (fileName.startsWith("/")) {
                String[] suburl = url.substring(url.indexOf("http://") + 7).split("/");
                fileName = "http://" + suburl[0] + fileName;
            } else
            if ((!fileName.startsWith("/")) && (!fileName.startsWith(".")) && (!fileName.startsWith("http://"))) {
                fileName = url.substring(0, url.lastIndexOf("/") + 1) + fileName;
            }
            return head_str + fileName + tail_str;
        } else {
            return imagesrc;
        }
    }
}
