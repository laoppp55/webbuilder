package com.bizwink.cms.util;

import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;

/* Copyright 2002 Erik Peterson
   Code and program free for non-commercial use.
   Contact erik@mandarintools.com for fees and
   licenses for commercial use.
*/

public class Convert extends Encoding {
    // Simplfied/Traditional character equivalence hashes
    protected Hashtable s2thash, t2shash;


    // Constructor
    public Convert() {
        super();
        String dataline;

        // Initialize and load in the simplified/traditional character hashses
        s2thash = new Hashtable();
        t2shash = new Hashtable();

        try {
            InputStream pydata = getClass().getResourceAsStream("hcutf8.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(pydata, "UTF8"));
            while ((dataline = in.readLine()) != null) {
                // Skip empty and commented lines
                if (dataline.length() == 0 || dataline.charAt(0) == '#') {
                    continue;
                }

                // Simplified to Traditional, (one to many, but pick only one)
                s2thash.put(dataline.substring(0, 1).intern(), dataline.substring(1, 2));

                // Traditional to Simplified, (many to one)
                for (int i = 1; i < dataline.length(); i++) {
                    t2shash.put(dataline.substring(i, i + 1).intern(), dataline.substring(0, 1));
                }
            }
        }
        catch (Exception e) {
            System.err.println(e);
        }

    }

    public String convertString(String dataline, int source_encoding, int target_encoding) {
        StringBuffer outline = new StringBuffer();
        int lineindex;

        if (source_encoding == HZ) {
            dataline = hz2gb(dataline);
        }
        for (lineindex = 0; lineindex < dataline.length(); lineindex++) {
            if ((source_encoding == GB2312 || source_encoding == GBK || source_encoding == ISO2022CN_GB ||
                    source_encoding == HZ ||
                    source_encoding == UNICODE || source_encoding == UNICODES || source_encoding == UTF8)
                    &&
                    (target_encoding == BIG5 || target_encoding == CNS11643 || target_encoding == UNICODET ||
                            target_encoding == ISO2022CN_CNS)) {
                if (s2thash.containsKey(dataline.substring(lineindex, lineindex + 1)) == true) {
                    outline.append(s2thash.get(dataline.substring(lineindex, lineindex + 1).intern()));
                } else {
                    outline.append(dataline.substring(lineindex, lineindex + 1));
                }
            } else if ((source_encoding == BIG5 || source_encoding == CNS11643 || source_encoding == UNICODET ||
                    source_encoding == UTF8 ||
                    source_encoding == ISO2022CN_CNS || source_encoding == GBK || source_encoding == UNICODE)
                    &&
                    (target_encoding == GB2312 || target_encoding == UNICODES || target_encoding == ISO2022CN_GB ||
                            target_encoding == HZ)) {
                if (t2shash.containsKey(dataline.substring(lineindex, lineindex + 1)) == true) {
                    outline.append(t2shash.get(dataline.substring(lineindex, lineindex + 1).intern()));
                } else {
                    outline.append(dataline.substring(lineindex, lineindex + 1));
                }
            } else {
                outline.append(dataline.substring(lineindex, lineindex + 1));
            }
        }

        if (target_encoding == HZ) {
            // Convert to look like HZ
            return gb2hz(outline.toString());
        }

        return outline.toString();
    }


    public String hz2gb(String hzstring) {
        byte[] hzbytes;
        byte[] gbchar = new byte[2];
        int byteindex;
        StringBuffer gbstring = new StringBuffer("");

        try {
            hzbytes = hzstring.getBytes("8859_1");
        }
        catch (Exception usee) {
            System.err.println("Exception " + usee.toString());
            return hzstring;
        }

        // Convert to look like equivalent Unicode of GB
        for (byteindex = 0; byteindex < hzbytes.length; byteindex++) {
            if (hzbytes[byteindex] == 0x7e) {
                if (hzbytes[byteindex + 1] == 0x7b) {
                    byteindex += 2;
                    while (byteindex < hzbytes.length) {
                        if (hzbytes[byteindex] == 0x7e && hzbytes[byteindex + 1] == 0x7d) {
                            byteindex++;
                            break;
                        } else if (hzbytes[byteindex] == 0x0a || hzbytes[byteindex] == 0x0d) {
                            gbstring.append((char) hzbytes[byteindex]);
                            break;
                        }
                        gbchar[0] = (byte) (hzbytes[byteindex] + 0x80);
                        gbchar[1] = (byte) (hzbytes[byteindex + 1] + 0x80);
                        try {
                            gbstring.append(new String(gbchar, "GB2312"));
                        } catch (Exception usee) {
                            System.err.println("Exception " + usee.toString());
                        }
                        byteindex += 2;
                    }
                } else if (hzbytes[byteindex + 1] == 0x7e) { // ~~ becomes ~
                    gbstring.append('~');
                } else {  // false alarm
                    gbstring.append((char) hzbytes[byteindex]);
                }
            } else {
                gbstring.append((char) hzbytes[byteindex]);
            }
        }
        return gbstring.toString();
    }

    public String gb2hz(String gbstring) {
        StringBuffer hzbuffer;
        byte[] gbbytes;
        int i;
        boolean terminated;

        hzbuffer = new StringBuffer("");
        try {
            gbbytes = gbstring.getBytes("GB2312");
        }
        catch (Exception usee) {
            System.err.println(usee.toString());
            return gbstring;
        }

        for (i = 0; i < gbbytes.length; i++) {
            if (gbbytes[i] < 0) {
                hzbuffer.append("~{");
                terminated = false;
                while (i < gbbytes.length) {
                    if (gbbytes[i] == 0x0a || gbbytes[i] == 0x0d) {
                        hzbuffer.append("~}" + (char) gbbytes[i]);
                        terminated = true;
                        break;
                    } else if (gbbytes[i] >= 0) {
                        hzbuffer.append("~}" + (char) gbbytes[i]);
                        terminated = true;
                        break;
                    }
                    hzbuffer.append((char) (gbbytes[i] + 256 - 0x80));
                    hzbuffer.append((char) (gbbytes[i + 1] + 256 - 0x80));
                    i += 2;
                }
                if (!terminated) {
                    hzbuffer.append("~}");
                }
            } else {
                if (gbbytes[i] == 0x7e) {
                    hzbuffer.append("~~");
                } else {
                    hzbuffer.append((char) gbbytes[i]);
                }
            }
        }
        return new String(hzbuffer);
    }

    //打开一个输入文件sourcefile，将其内容转换为BIG5码，将转换结果写到outfile输出文件中
    public void convertFile(String sourcefile, String outfile, int source_encoding, int target_encoding) {
        BufferedReader srcbuffer;
        BufferedWriter outbuffer;
        String dataline;

        try {
            srcbuffer = new BufferedReader(new InputStreamReader(new FileInputStream(sourcefile), javaname[source_encoding]));
            outbuffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), javaname[target_encoding]));
            while ((dataline = srcbuffer.readLine()) != null) {
                //System.out.println(dataline);
                outbuffer.write(convertString(dataline, source_encoding, target_encoding));
                outbuffer.newLine();
            }
            srcbuffer.close();
            outbuffer.close();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }

    //将一个字符串转换为BIG5码，转换结果写到输出文件outfile中
    public int convertString(String buf, String outfile, int source_encoding, int target_encoding, String sitename) {
        BufferedReader srcbuffer;
        BufferedWriter outbuffer;
        String dataline;
        String resultStr = StringUtil.gb2iso(buf);
        int retcode = 0;

        //处理页面上所有需要修改的内容，例如href,src等
        resultStr = processContent(resultStr, sitename);

        try {
            srcbuffer = new BufferedReader(new InputStreamReader(new StringBufferInputStream(resultStr), javaname[source_encoding]));
            outbuffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), javaname[target_encoding]));
            while ((dataline = srcbuffer.readLine()) != null) {
                outbuffer.write(convertString(dataline, source_encoding, target_encoding));
                outbuffer.newLine();
            }
            srcbuffer.close();
            outbuffer.close();
        }
        catch (Exception ex) {
            retcode = -1;
            System.err.println(ex);
        }

        return retcode;
    }

    //打开一个输入文件sourcefile，将其内容转换为BIG5码，将转换结果写到outfile输出文件中
    public void convertFile(String sourcefile, String outfile, int source_encoding, int target_encoding, String sitename) {
        BufferedReader srcbuffer;
        BufferedWriter outbuffer;
        String dataline, buf = "";

        try {
            srcbuffer = new BufferedReader(new InputStreamReader(new FileInputStream(sourcefile), javaname[source_encoding]));
            outbuffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), javaname[target_encoding]));
            while ((dataline = srcbuffer.readLine()) != null) {
                buf = buf + dataline + "\r\n";
            }

            //System.out.println(buf);

            //处理页面上所有需要修改的内容，例如href,src等
            buf = processContent(buf, sitename);

            outbuffer.write(convertString(buf, source_encoding, target_encoding));
            outbuffer.newLine();

            srcbuffer.close();
            outbuffer.close();
        }
        catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private String processContent(String big5ResultStr, String sitename) {
        //在所有的图象、FLASH和CSS文件前面增加路径"/big5"
        big5ResultStr = big5ResultStr.replaceAll("/webbuilder/sites/" + sitename + "/", "/big5/");
        if (big5ResultStr.indexOf("sites/") != -1)
            big5ResultStr = big5ResultStr.replaceAll("sites/" + sitename + "/", "/big5/");
        int i;

        //将gb2312字符集标志改为big5标志
        Pattern p;
        p = Pattern.compile("gb2312", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(big5ResultStr);
        big5ResultStr = matcher.replaceFirst("big5");

        p = Pattern.compile("gbk", Pattern.CASE_INSENSITIVE);
        matcher = p.matcher(big5ResultStr);
        big5ResultStr = matcher.replaceFirst("big5");

        //处理INCLUDE文件，将INCLUDE文件换为BIG5目录下的include文件
        big5ResultStr = changeBig5JspIncludePath(big5ResultStr);
        big5ResultStr = changeBig5ShtmlIncludePath(big5ResultStr);
        big5ResultStr = backgroud_Element(big5ResultStr);
        big5ResultStr = swf_Element(big5ResultStr);
        //big5ResultStr = changeBig5SelectHrefAndImg(big5ResultStr);

        //用href="/big5(或href='/big5或href=/big5)替换href="(或href='或href=)
        p = Pattern.compile("<[^<>]*href\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
        String tempbuf[] = p.split(big5ResultStr);
        StringBuffer buffer = new StringBuffer();
        matcher = p.matcher(big5ResultStr);
        String matchStr;

        for (i = 0; i < tempbuf.length - 1; i++) {
            matcher.find();
            matchStr = big5ResultStr.substring(matcher.start(), matcher.end());
            String temp = matchStr.substring(matchStr.toLowerCase().indexOf("href"));
            if (temp.toLowerCase().indexOf("http://") == -1 && temp.toLowerCase().indexOf("mailto") == -1 && temp.toLowerCase().indexOf("javascript") == -1)
                matchStr = format(matchStr, "href");
            buffer.append(tempbuf[i]);
            buffer.append(matchStr);
        }
        buffer.append(tempbuf[tempbuf.length - 1]);

        //用src="/big5(或src='/big5或src=/big5)替换src="(或src='或src=)
        big5ResultStr = buffer.toString();
        p = Pattern.compile("<[^<>]*src\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
        tempbuf = p.split(big5ResultStr);
        buffer = new StringBuffer();
        matcher = p.matcher(big5ResultStr);

        for (i = 0; i < tempbuf.length - 1; i++) {
            matcher.find();
            matchStr = big5ResultStr.substring(matcher.start(), matcher.end());
            String temp = matchStr.substring(matchStr.toLowerCase().indexOf("src"));
            if (temp.toLowerCase().indexOf("http://") == -1)
                matchStr = format(matchStr, "src");
            buffer.append(tempbuf[i]);
            buffer.append(matchStr);
        }
        buffer.append(tempbuf[tempbuf.length - 1]);

        return buffer.toString();
    }

    private String changeBig5JspIncludePath(String big5ResultStr) {
        Pattern p;
        p = Pattern.compile("<%@\\s*include\\s*file\\s*=[^<>]*%>", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(big5ResultStr);
        String[] tempBuf = p.split(big5ResultStr);
        String[] targetStr = new String[tempBuf.length - 1];
        String matchStr, tempStr;
        int i = 0, posi, posi1;
        StringBuffer buf = new StringBuffer();

        while (matcher.find()) {
            matchStr = big5ResultStr.substring(matcher.start(), matcher.end());
            posi = matchStr.indexOf("=");
            tempStr = matchStr.substring(0, posi + 1);
            matchStr = matchStr.substring(posi + 1);
            posi = matchStr.indexOf("\"");
            posi1 = matchStr.indexOf("'");
            if (posi != -1) {
                matchStr = matchStr.substring(posi + 1);
                matchStr = "\"/big5" + matchStr;
            } else if (posi1 != -1) {
                matchStr = matchStr.substring(posi + 1);
                matchStr = "'/big5" + matchStr;
            } else {
                matchStr = "/big5" + matchStr;
            }
            targetStr[i] = tempStr + matchStr;
            //System.out.println(targetStr[i]);
            i = i + 1;
        }

        for (i = 0; i < targetStr.length; i++) {
            buf.append(tempBuf[i]).append(targetStr[i]);
        }

        buf.append(tempBuf[i]);

        return buf.toString();
    }

    private String changeBig5ShtmlIncludePath(String big5ResultStr) {
        Pattern p;
        //<!--#include virtual="/include/top.html" -->
        p = Pattern.compile("<!--#include\\s*virtual\\s*=[^<>]*\\s*-->", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(big5ResultStr);
        String[] tempBuf = p.split(big5ResultStr);
        String[] targetStr = new String[tempBuf.length - 1];
        String matchStr, tempStr;
        int i = 0, posi, posi1;
        StringBuffer buf = new StringBuffer();

        while (matcher.find()) {
            matchStr = big5ResultStr.substring(matcher.start(), matcher.end());
            posi = matchStr.indexOf("=");
            tempStr = matchStr.substring(0, posi + 1);
            matchStr = matchStr.substring(posi + 1);
            posi = matchStr.indexOf("\"");
            posi1 = matchStr.indexOf("'");
            if (posi != -1) {
                matchStr = matchStr.substring(posi + 1);
                matchStr = "\"/big5" + matchStr;
            } else if (posi1 != -1) {
                matchStr = matchStr.substring(posi + 1);
                matchStr = "'/big5" + matchStr;
            } else {
                matchStr = "/big5" + matchStr;
            }
            targetStr[i] = tempStr + matchStr;
            //System.out.println(targetStr[i]);
            i = i + 1;
        }

        for (i = 0; i < targetStr.length; i++) {
            buf.append(tempBuf[i]).append(targetStr[i]);
        }

        buf.append(tempBuf[i]);

        return buf.toString();
    }

    private String changeBig5FormHrefAndImg(String str) {

        return "";
    }

    private String changeBig5ImgMap(String str) {

        return "";
    }

    private String changeBig5SelectHrefAndImg(String str) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<\\s*option\\s+value\\s*=[^<>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = format(tag[i], "value");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String backgroud_Element(String str) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<[^<]*background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = format(tag[i], "background");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public String swf_Element(String tempBuf) {
        StringBuffer buffer = new StringBuffer();

        try {
            //处理moive
            Pattern p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']movie[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                tag[i] = tempBuf.substring(0, tempBuf.indexOf(">") + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = format(tag[i], "value");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理src
            tempBuf = buffer.toString();
            buffer = new StringBuffer();
            p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']src[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            buf = p.split(tempBuf);
            tag = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                tag[i] = tempBuf.substring(0, tempBuf.indexOf(">") + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = format(tag[i], "value");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理<embed.....>
            tempBuf = buffer.toString();
            buffer = new StringBuffer();
            p = Pattern.compile("<(\\s*)embed(\\s*)[^<>]*src(\\s*)=[^<>]*>", Pattern.CASE_INSENSITIVE);
            buf = p.split(tempBuf);
            tag = new String[buf.length - 1];
            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                tag[i] = tempBuf.substring(0, tempBuf.indexOf(">") + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = format(tag[i], "src");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    //根据是否是双引号或者是单引号确定正则表达式的形成规则不同
    private String findPicinString(String buf, String sitename, String dir, int imgflag) {
        String fileName;
        int posi;
        String tempStr = buf;
        StringBuffer imgbuf = new StringBuffer();
        Pattern p;

        p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|swf|css)\")|('[^',]*\\.(gif|jpg|jpeg|png|swf|css)')",
                Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(buf);
        String matchStr;
        while (matcher.find()) {
            matchStr = buf.substring(matcher.start() + 1, matcher.end() - 1);
            if (imgbuf.indexOf("-" + matchStr + "#") != -1) {
                continue;
            } else {
                imgbuf.append("-");
                imgbuf.append(matchStr);
                imgbuf.append("#");
            }

            posi = matchStr.lastIndexOf("/");
            if (posi != -1)
                fileName = matchStr.substring(posi + 1);
            else
                fileName = matchStr;

            if (imgflag == 0) {
                fileName = "/webbuilder/sites/" + sitename + "/images/" + fileName;
            } else {
                fileName = "/webbuilder/sites/" + sitename + dir + "images/" + fileName;
            }
            tempStr = StringUtil.replace(tempStr, matchStr, fileName);
        }
        return tempStr;
    }

    private String format(String str, String token) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        posi = buf.indexOf(token);
        head_str = str.substring(0, posi);
        buf = str.substring(posi);
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

        posi = fileName.indexOf("\"");   //如果开始是双引号，去掉头部双引号
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");    //如果开始是单引号，去掉头部单引号
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");  //如果结尾是单引号，去掉尾部单引号
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");   //如果结尾是双引号，去掉尾部双引号
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        if ((str.indexOf("big5") == -1)&&(!fileName.equals("#")))
            str = head_str + " " + token + "=/big5" + fileName + tail_str;
        //str = head_str + " " + token + "=\"/big5" + fileName + "\"" + tail_str;

        return str;
    }
}
