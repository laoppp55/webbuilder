package com.bizwink.processhtml;

import java.io.*;
import java.util.regex.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class formatTemplate {
    String htmlfilename;

    public formatTemplate(String htmlfilename){
        this.htmlfilename = htmlfilename;
    }

    //根据是否是双引号或者是单引号确定正则表达式的形成规则不同
    public String findPicinString(String buf,String sitename, String dir, int imgflag,String pubsite)
    {
        String fileName = "";
        int posi = 0;
        String tempStr = buf;
        StringBuffer imgbuf = new StringBuffer();
        Pattern p = null;

        p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|swf)\")|('[^',]*\\.(gif|jpg|jpeg|png|swf)')",
                Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(buf);
        String matchStr = "";
        while (matcher.find())
        {
            matchStr = buf.substring(matcher.start() + 1, matcher.end() - 1);
            if (imgbuf.indexOf("-" + matchStr + "#") != -1)
            {
                continue;
            }
            else
            {
                imgbuf.append("-");
                imgbuf.append(matchStr);
                imgbuf.append("#");
            }

            posi = matchStr.lastIndexOf("/");
            if (posi != -1)
                fileName = matchStr.substring(posi + 1);
            else
                fileName = matchStr;

            fileName = "sites/" + sitename + dir + "images/" + fileName;
            tempStr = StringUtil.replace(tempStr,matchStr, fileName);
            //tempStr.replaceAll(matchStr, fileName);
        }

        return tempStr;
    }

    public String readModelFile(String filename,String appPath,String sitename,int siteid,String dir,int imgflag,int languageType,String pubsite) throws IOException
    {
        String encoding = "GBK";
        if (languageType == 1)
            encoding = "BIG5";
        else if (languageType == 2)
            encoding = "SJIS";

        StringBuffer tempBuf = new StringBuffer();
        FileInputStream fis = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) > -1)
        {
            tempBuf.append((char)ch);
        }
        in.close();
        isr.close();
        fis.close();

        String str = tempBuf.toString();

        //去掉返回目录中的域名
        String sitenameBuf = StringUtil.replace(sitename, "_", ".");
        int posi = dir.indexOf(sitenameBuf);
        if (posi != -1) dir = dir.substring(posi + sitenameBuf.length());

        dir = StringUtil.replace(dir,File.separator,"/");

        //str = StringUtil.iso2gb(str);     //for linux os
        str = findPicinString(str,sitename, dir, imgflag,pubsite);
        str = src_Element(str,sitename,dir,imgflag,pubsite);
        str = backgroud_Element(str,sitename, dir,imgflag,pubsite);
        str = css_Element(str,sitename,dir,imgflag,pubsite);
        str = swf_Element(str,sitename, dir, imgflag,pubsite);
        str = iframe_Element(str,sitename, dir, imgflag,pubsite);
        str = script_Element(str, sitename, dir, imgflag,pubsite);

        if (CmsServer.getInstance().getOStype().equalsIgnoreCase("unix"))
        {
            str = StringUtil.iso2gb(str);
        }

        return str;
    }

    public int readModelFile(String appPath,String sitename,String dir,int imgflag,int languageType,String pubsite) throws IOException
    {
        int retcode = 0;
        String encoding = "GBK";
        if (languageType == 1)
            encoding = "BIG5";
        else if (languageType == 2)
            encoding = "SJIS";

        StringBuffer tempBuf = new StringBuffer();
        FileInputStream fis = new FileInputStream(this.htmlfilename);
        InputStreamReader isr = new InputStreamReader(fis, encoding);
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) > -1)
        {
            tempBuf.append((char)ch);
        }
        in.close();
        isr.close();
        fis.close();

        String str = tempBuf.toString();

        //去掉返回目录中的域名
        String sitenameBuf = StringUtil.replace(sitename, "_", ".");
        int posi = dir.indexOf(sitenameBuf);
        if (posi != -1) dir = dir.substring(posi + sitenameBuf.length());

        dir = StringUtil.replace(dir,File.separator,"/");

        str = findPicinString(str,sitename, dir, imgflag,pubsite);
        str = src_Element(str,sitename,dir,imgflag,pubsite);
        str = backgroud_Element(str,sitename, dir, imgflag,pubsite);
        str = css_Element(str,sitename,dir,imgflag,pubsite);
        str = swf_Element(str,sitename, dir, imgflag,pubsite);
        str = iframe_Element(str,sitename, dir, imgflag,pubsite);
        str = script_Element(str, sitename, dir, imgflag,pubsite);

        if (CmsServer.getInstance().getOStype().equalsIgnoreCase("unix"))
        {
            str = StringUtil.iso2gb(str);
        }

        //将格式化后的内容写回模板文件
        PrintWriter pw = new PrintWriter(new FileOutputStream(htmlfilename));
        pw.write(str);
        pw.close();

        return retcode;
    }

    public String iframe_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)iframe[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSrc_Str(tag[i],sitename, dir, imgflag,pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String script_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)script([^<>]*)src\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatScript_Str(tag[i],sitename, dir, imgflag,pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String src_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            //Pattern p = Pattern.compile("<(\\s*)img[^>]*>", Pattern.CASE_INSENSITIVE);
            Pattern p = Pattern.compile("<[^<]*\\s+src(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                //System.out.println(tempBuf);
                tag[i] = formatSrc_Str(tag[i],sitename, dir, imgflag,pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String backgroud_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            //Pattern p = Pattern.compile("<[^<][^script]*background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            Pattern p = Pattern.compile("<[^<]*\\s+background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];
            String temp="";

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                temp = tag[i].toLowerCase();
                if (temp.indexOf("script") == -1)
                    tag[i] = formatBackground_Str(tag[i],sitename, dir, imgflag,pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String css_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)link[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatCss_Str(tag[i],sitename, dir, imgflag,pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String swf_Element(String str,String sitename, String dir, int imgflag,String pubsite)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi = 0;

        try
        {
            //处理<PARAM NAME="Movie" VALUE="flash/banner.swf">
            Pattern p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']movie[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSwf_Str(tag[i],sitename, dir, imgflag, "value",pubsite);
            }

            for (int i = 0; i < tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理<PARAM NAME="Src" VALUE="flash/banner.swf">
            tempBuf = buffer.toString();
            p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']src[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            String buf2[] = p.split(tempBuf);
            String tag2[] = new String[buf2.length - 1];

            for (int i = 0; i < buf2.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf2[i].length());
                posi = tempBuf.indexOf(">");
                tag2[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag2[i].length());
                tag2[i] = formatSwf_Str(tag2[i],sitename, dir, imgflag, "value",pubsite);
            }

            buffer = new StringBuffer();
            for (int i = 0; i < tag2.length; i++)
            {
                buffer.append(buf2[i]);
                buffer.append(tag2[i]);
            }
            buffer.append(buf2[tag2.length]);

            //处理embed中的swf文件名
            tempBuf = buffer.toString();
            p = Pattern.compile("<(\\s*)embed(\\s*)[^<>]*src(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf1[] = p.split(tempBuf);
            String tag1[] = new String[buf1.length - 1];

            for (int i = 0; i < buf1.length - 1; i++)
            {
                tempBuf = tempBuf.substring(buf1[i].length());
                posi = tempBuf.indexOf(">");
                tag1[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag1[i].length());
                tag1[i] = formatSwf_Str(tag1[i],sitename, dir, imgflag, "src",pubsite);
            }

            buffer = new StringBuffer();
            for (int i = 0; i < tag1.length; i++)
            {
                buffer.append(buf1[i]);
                buffer.append(tag1[i]);
            }
            buffer.append(buf1[tag1.length]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String formatBackground_Str(String str, String sitename, String dir, int imgflag, String pubsite)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.toLowerCase().indexOf("background");
        if (posi > -1) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1)
            {
                fileName = buf.substring(0, posi);
                tail_str = buf.substring(posi);
            }
            else
            {
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

            posi = fileName.lastIndexOf("/");
            if (posi != -1)
            {
                fileName = fileName.substring(posi + 1);
            }

            if (fileName.toLowerCase().lastIndexOf(".jpg") > 0 || fileName.toLowerCase().lastIndexOf(".jpeg") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".gif") > 0 || fileName.toLowerCase().lastIndexOf(".png") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".bmp") > 0) {
                str = head_str + "background=\"sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
            }
        }

        return str;
    }

    public String formatScript_Str(String str, String sitename, String dir, int imgflag, String pubsite)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.toLowerCase().indexOf("src");
        head_str = str.substring(0, posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi + 1).trim();
        posi = buf.indexOf(" ");

        if (posi != -1)
        {
            fileName = buf.substring(0, posi);
            tail_str = buf.substring(posi);
        }
        else
        {
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

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }

        if (fileName.toLowerCase().lastIndexOf(".js") > 0 || fileName.toLowerCase().lastIndexOf(".bvs") > 0) {
            str = head_str + "src=\"sites/" + sitename + "/js/" + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String formatSrc_Str(String str, String sitename, String dir, int imgflag, String pubsite)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.toLowerCase().indexOf("src");

        //System.out.println("buf=" + buf);

        if (posi > -1) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();

            posi = buf.indexOf(" ");
            if (posi != -1)
            {
                fileName = buf.substring(0, posi);
                tail_str = buf.substring(posi);
            }
            else
            {
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

            posi = fileName.lastIndexOf("/");
            if (posi != -1)
            {
                fileName = fileName.substring(posi + 1);
            }

            if (fileName.toLowerCase().lastIndexOf(".jpg") > 0 || fileName.toLowerCase().lastIndexOf(".jpeg") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".gif") > 0 || fileName.toLowerCase().lastIndexOf(".png") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".bmp") > 0 || fileName.toLowerCase().lastIndexOf(".htm") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".html") > 0 || fileName.toLowerCase().lastIndexOf(".shtm") > 0 ||
                    fileName.toLowerCase().lastIndexOf(".shtml") > 0)
                str = head_str + "src=\"sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String formatCss_Str(String str, String sitename, String dir, int imgflag, String pubsite)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.toLowerCase().indexOf("href");
        head_str = str.substring(0, posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi + 1).trim();

        posi = buf.indexOf(" ");
        if (posi != -1)
        {
            fileName = buf.substring(0, posi);
            tail_str = buf.substring(posi);
        }
        else
        {
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

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }

        str = head_str + "href=\"sites/" + sitename + "/css/" + fileName + "\"" + tail_str;

        return str;
    }

    public String formatSwf_Str(String str, String sitename, String dir, int imgflag, String strflag, String pubsite)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.toLowerCase().indexOf(strflag);
        head_str = str.substring(0, posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi + 1).trim();

        posi = buf.indexOf(" ");
        if (posi != -1)
        {
            fileName = buf.substring(0, posi);
            tail_str = buf.substring(posi);
        }
        else
        {
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

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }

        if (fileName != null) {
            if (strflag.equals("value"))
                str = head_str + "value=\"sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
            else if (strflag.equals("src"))
                str = head_str + "src=\"sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        }

        // System.out.println(str);

        return str;
    }
}
