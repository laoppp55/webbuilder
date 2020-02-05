package com.bizwink.cms.register;

import com.bizwink.cms.util.StringUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-6-9
 * Time: 15:56:30
 * To change this template use File | Settings | File Templates.
 */
public class formatPictureURL {
    /**
     * 测试函数*
     */
    public static void main(String[] args) {
        String dbip = "114.113.159.160";
        String dbusername = "coositedb";
        String dbpassword = "coositedbpass2009";
        String content = null;
        String sam_sitename="cy040_coosite_com";
        String sitename="123.coosite.com";
        formatPictureURL fpurl = new formatPictureURL();
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl10g", dbusername, dbpassword);
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            //siteid=675的模板
            pstmt = conn.prepareStatement("SELECT t.content,t.siteid FROM tbl_template t WHERE t.siteid=675");
            rs = pstmt.executeQuery();
            if (rs.next()) {
                content = rs.getString("content");
                System.out.println(fpurl.replaceSitenameInTheHTML(content,sam_sitename,sitename));
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e2) {
            e2.printStackTrace();
        }

    }

    public String replaceSitenameInTheHTML(String buf, String sam_sitename, String sitename) {
        String dash_sitename = StringUtil.replace(sitename,".","_");
        return StringUtil.replace(buf,sam_sitename,dash_sitename);
    }

    public String formatHTML(String buf, String sam_sitename, String sitename) {
        String str = buf;
        str = script_Element(str, sam_sitename, sitename);
        str = css_Element(str, sam_sitename, sitename);
        str = form_Element(str, sam_sitename, sitename);
        str = src_Element(str, sam_sitename, sitename);
        str = backgroud_Element(str, sam_sitename, sitename);
        str = div_backgroud_Element(str, sam_sitename, sitename);
        str = swf_Element(str, sam_sitename, sitename);
        return str;
    }

    public String src_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try {
            Pattern p = Pattern.compile("<(\\s*)img[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                //System.out.println(tag[i]);
                tag[i] = formatSrc_Str(tag[i], sam_sitename, sitename);
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatSrc_Str(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        String dirName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("src");
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

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        posi = fileName.indexOf("/webbuilder/sites/" + sam_sitename);
        if (posi != -1) fileName = fileName.substring(("/webbuilder/sites/" + sam_sitename).length());
        str = head_str + "src=\"http://" + StringUtil.replace(sam_sitename, "_", ".") + fileName + "\"" + tail_str;

        return str;
    }

    public String backgroud_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try {
            Pattern p = Pattern.compile("<[^<]*background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatBackground_Str(tag[i], sam_sitename, sitename);
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatBackground_Str(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("background");
        if (posi != -1) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            if (posi != -1) buf = buf.substring(posi + 1).trim();

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

            posi = fileName.indexOf("/webbuilder/sites/" + sam_sitename);
            if (posi != -1) fileName = fileName.substring(("/webbuilder/sites/" + sam_sitename).length());
            str = head_str + "background=\"http://" + StringUtil.replace(sam_sitename, "_", ".") + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String swf_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try {
            Pattern p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']movie[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSwf_Str(tag[i], sam_sitename, sitename, "value");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理embed中的swf文件名
            tempBuf = buffer.toString();
            p = Pattern.compile("<(\\s*)embed(\\s*)[^<>]*src(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf1[] = p.split(tempBuf);
            String tag1[] = new String[buf1.length - 1];

            for (int i = 0; i < buf1.length - 1; i++) {
                tempBuf = tempBuf.substring(buf1[i].length());
                posi = tempBuf.indexOf(">");
                tag1[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag1[i].length());
                tag1[i] = formatSwf_Str(tag1[i], sam_sitename, sitename, "src");
            }

            buffer = new StringBuffer();
            for (int i = 0; i < tag1.length; i++) {
                buffer.append(buf1[i]);
                buffer.append(tag1[i]);
            }
            buffer.append(buf1[tag1.length]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatSwf_Str(String str, String sam_sitename, String sitename, String strflag) {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf(strflag);
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

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        posi = fileName.indexOf("/webbuilder/sites/" + sam_sitename);
        if (posi != -1) fileName = fileName.substring(("/webbuilder/sites/" + sam_sitename).length());

        /*if (strflag.equals("src"))
            str = head_str + "src=\"http://" + StringUtil.replace(sam_sitename,"_",".") + fileName + "\"" + tail_str;
        else
            str = head_str + "value=\"http://" + StringUtil.replace(sam_sitename,"_",".") + fileName + "\"" + tail_str;
        */

        if (strflag.equals("src"))
            str = head_str + "src=\"/webbuilder/sites/" + sitename + fileName + "\"" + tail_str;
        else
            str = head_str + "value=\"/webbuilder/sites/" + sitename + fileName + "\"" + tail_str;
        return str;
    }

    //<div style="background-image:url(images/090506title07bg.gif); height:217px; overflow:hidden;">
    //<div style="width:180px; height:262px; float:right; background:url(images/090506bg1.gif)">
    public String div_backgroud_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<div [^<]*background[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];
            String temp;

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                temp = tag[i].toLowerCase();

                if (temp.indexOf("url") != -1) {
                    tag[i] = format_div_background_image(tag[i], sam_sitename, sitename);
                }
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String format_div_background_image(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        if ((buf != null) && (buf.indexOf("url") != -1)) {
            posi = buf.indexOf("url");
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("(");
            buf = buf.substring(posi + 1).trim();

            posi = buf.indexOf(")");
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

            posi = fileName.indexOf("/webbuilder/sites/" + sam_sitename);
            if (posi != -1) fileName = fileName.substring(("/webbuilder/sites/" + sam_sitename).length());

            str = head_str + "url(/http://" + StringUtil.replace(sam_sitename, "_", ".") + fileName + tail_str;
        }
        return str;
    }

    public String css_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<(\\s*)link[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatCss_Str(tag[i], sam_sitename, sitename);
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatCss_Str(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        posi = buf.indexOf("href");
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

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        posi = fileName.lastIndexOf("/");
        if (posi != -1) fileName = fileName.substring(posi + 1);

        str = head_str + "href=\"/webbuilder/sites/" + sitename + "/css/" + fileName + "\"" + tail_str;

        return str;
    }

    public String script_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<script[^>]*src=[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                //System.out.println(tag[i]);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatScript_Str(tag[i], sam_sitename, sitename);
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatScript_Str(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        posi = buf.indexOf("src");
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

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        //posi = fileName.indexOf("/webbuilder/sites/"+sam_sitename);
        posi = fileName.lastIndexOf("/");
        if (posi != -1) fileName = fileName.substring(posi + 1);

        str = head_str + "src=\"/webbuilder/sites/" + sitename + "/js/" + fileName + "\"" + tail_str;

        return str;
    }

    public String form_Element(String str, String sam_sitename, String sitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<form[^>]*action=[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                //System.out.println(tag[i]);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatForm_Str(tag[i], sam_sitename, sitename);
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String formatForm_Str(String str, String sam_sitename, String sitename) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        posi = buf.indexOf("action");
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

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        //posi = fileName.indexOf("/webbuilder/sites/"+sam_sitename);
        posi = fileName.lastIndexOf(sam_sitename);
        if (posi != -1) fileName = fileName.substring(posi + sam_sitename.length());

        str = head_str + "action=\"/" + sitename + fileName + "\"" + tail_str;

        return str;
    }
}
