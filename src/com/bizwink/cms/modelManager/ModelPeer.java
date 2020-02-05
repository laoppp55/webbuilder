package com.bizwink.cms.modelManager;

import java.io.*;
import java.util.*;
import java.sql.*;
import java.util.regex.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.markManager.*;
import com.bizwink.cms.modelManager.history.*;
import com.bizwink.cms.publishx.Publish;
import com.bizwink.cms.sitesetting.ISiteInfoManager;
import com.bizwink.cms.sitesetting.SiteInfoPeer;
import com.bizwink.cms.sitesetting.SiteInfo;

public class ModelPeer implements IModelManager {
    PoolServer cpool;

    public ModelPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IModelManager getInstance() {
        return CmsServer.getInstance().getFactory().getModelManager();
    }

    private static final String SQL_GETModel = "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum,tempnum from tbl_template where ID = ?";


    public Model getModel(int ID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModel);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }

    private static final String SQL_GETModelByTempnoInColumn =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum from " +
                    "tbl_template where siteid=? and columnid= ? and tempnum=?";


    public Model getModel(int siteid,int columnid,int tempno) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelByTempnoInColumn);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnid);
            pstmt.setInt(3, tempno);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }

    public Model getModel(int ID, String editor) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModel);
            pstmt.setInt(1, ID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);
            }
            rs.close();
            pstmt.close();

            //修改锁定标志
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("UPDATE tbl_template SET lockstatus = 1,lockeditor = ? WHERE ID = ?");
            pstmt.setString(1,editor);
            pstmt.setInt(2,ID);
            pstmt.execute();
            pstmt.close();
            conn.commit();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }

    private static final String SQL_GETDefaultModel =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum from " +
                    "TBL_Template where ColumnID = ? and  IsArticle = ? and defaultTemplate = 1";

    public Model getDefaultModel(int columnID, int isArticle) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;
        int refermodelID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETDefaultModel);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, isArticle);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);
            }
            rs.close();
            pstmt.close();

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }


    private static final String SQL_GETCurrentModel =
            "select ID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,siteid,tempnum from " +
                    "TBL_Template where ColumnID = ? and IsArticle = ? ORDER BY LastUpdated desc";

    public Model getCurrentModel(int columnID, int isArticle) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;
        int refermodelid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETCurrentModel);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, isArticle);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);       //默认模版不是引用模版
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }

    //根据是否是双引号或者是单引号确定正则表达式的形成规则不同
    public String findPicinString(String buf, String sitename, String dir, int imgflag) {
        String fileName;
        int posi;
        String tempStr = buf;
        StringBuffer imgbuf = new StringBuffer();
        Pattern p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|swf|css)\")|('[^',]*\\.(gif|jpg|jpeg|png|swf|css)')",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(buf);
        String matchStr=null;
        while (matcher.find()) {
            //matchStr = buf.substring(matcher.start(), matcher.end());
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

            //System.out.println("fileName = " + fileName);
            tempStr = StringUtil.replace(tempStr, matchStr, fileName);
        }
        return tempStr;
    }

    public String readModelFile(String filename, String appPath, String sitename, int siteid, String dir, int imgflag,
                                int languageType, int cssjsdir) throws IOException {

        //System.out.println("hello world");
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
        while ((ch = in.read()) > -1) {
            tempBuf.append((char) ch);
        }
        in.close();
        isr.close();
        fis.close();

        String str = tempBuf.toString();

        //去掉返回目录中的域名
        String sitenameBuf = StringUtil.replace(sitename, "_", ".");
        int posi = dir.indexOf(sitenameBuf);
        if (posi != -1) dir = dir.substring(posi + sitenameBuf.length());

        //str = StringUtil.iso2gb(str);     //for linux os
        str = findPicinString(str, sitename, dir, imgflag);
        str = src_Element(str, sitename, dir, imgflag, cssjsdir);
        str = backgroud_Element(str, sitename, dir, imgflag);
        str = css_Element(str, sitename, dir, imgflag, cssjsdir);
        str = swf_Element(str, sitename, dir, imgflag);
        str = iframe_Element(str, sitename, dir, imgflag, cssjsdir);
        str = div_backgroud_Element(str, sitename, dir, imgflag);
        //str = script_Element(str, sitename, dir, imgflag);

        if (cpool.getOStype().equalsIgnoreCase("unix")) {
            str = StringUtil.iso2gb(str);
        }

        return str;
    }

    public String readModelFile(int columnID,String path, String sitename, int siteid, int imgflag, int cssjsdir) throws IOException {
        String encoding = "GBK";
        boolean stopReadingFlag = false;
        String line=null;        // 用来保存每行读取的内容
        FileInputStream fis = new FileInputStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        line = reader.readLine();       // 读取第一行
        Pattern p = Pattern.compile("(<meta[^>]*charset=\"?(.+)\"?>|<meta[^>]*?charset=(\\w+)[\\W]*?>)",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = null;
        while (line != null && !stopReadingFlag) {          // 如果 line 为空说明读完了
            line = reader.readLine();                         // 读取下一行
            matcher = p.matcher(line);
            if (matcher.find()) {
                String matchStr = line.substring(matcher.start(), matcher.end());
                int posi = matchStr.toLowerCase().indexOf("charset");
                matchStr = matchStr.substring(posi + "charset".length());
                posi = matchStr.toLowerCase().indexOf("=");
                matchStr = matchStr.substring(posi + 1).trim();
                if (matchStr.startsWith("'")) {
                    matchStr = matchStr.substring(1).trim();
                    posi = matchStr.indexOf("'");
                    if (posi>-1)
                        matchStr = matchStr.substring(0,posi);
                    else {
                        posi = matchStr.indexOf(">");
                        if (posi>-1) matchStr = matchStr.substring(0,posi);
                    }
                } else if (matchStr.startsWith("\"")){
                    matchStr = matchStr.substring(1).trim();
                    posi = matchStr.indexOf("\"");
                    if (posi>-1)
                        matchStr = matchStr.substring(0,posi);
                    else {
                        posi = matchStr.indexOf(">");
                        if (posi>-1) matchStr = matchStr.substring(0,posi);
                    }
                } else {
                    posi = matchStr.indexOf(">");
                    if (posi>-1) {
                        matchStr = matchStr.substring(0,posi);
                        posi = matchStr.indexOf("\"");
                        if (posi>-1)
                            matchStr = matchStr.substring(0,posi);
                        else {
                            posi = matchStr.indexOf("'");
                            if (posi>-1) matchStr = matchStr.substring(0,posi);
                        }
                    }else {
                        posi = matchStr.indexOf("\"");
                        if (posi>-1)
                            matchStr = matchStr.substring(0,posi);
                        else {
                            posi = matchStr.indexOf("'");
                            if (posi>-1) matchStr = matchStr.substring(0,posi);
                        }
                    }
                }

                encoding = matchStr.trim();
                break;
            } else System.out.println("no found the charset");
        }
        fis.close();
        reader.close();

        StringBuffer tempBuf = new StringBuffer();
        fis = new FileInputStream(path);

        InputStreamReader isr = new InputStreamReader(fis, encoding);
        Reader in = new BufferedReader(isr);
        int ch;
        while ((ch = in.read()) > -1) {
            tempBuf.append((char) ch);
        }
        in.close();
        isr.close();
        fis.close();

        String str = tempBuf.toString();

        //去掉返回目录中的域名
        //String sitenameBuf = StringUtil.replace(sitename, "_", ".");
        int posi = path.indexOf(sitename + File.separator + "_templates");
        if (posi != -1) path = path.substring(posi + (sitename + File.separator + "_templates").length());
        posi = path.lastIndexOf(File.separator);
        if (posi != -1) path = path.substring(0,posi+1);
        path = StringUtil.replace(path,File.separator,"/");

        //System.out.println("path=" + path);
        //str = StringUtil.iso2gb(str);     //for linux os
        str = findPicinString(str, sitename, path, imgflag);
        str = src_Element(str, sitename, path, imgflag, cssjsdir);
        str = backgroud_Element(str, sitename, path, imgflag);
        str = css_Element(str, sitename, path, imgflag, cssjsdir);
        str = swf_Element(str, sitename, path, imgflag);
        str = iframe_Element(str, sitename, path, imgflag, cssjsdir);
        str = div_backgroud_Element(str, sitename, path, imgflag);
        //str = script_Element(str, sitename, dir, imgflag);

        if (cpool.getOStype().equalsIgnoreCase("unix")) {
            str = StringUtil.iso2gb(str);
        }

        return str;
    }

    public String iframe_Element(String str, String sitename, String dir, int imgflag, int cssjsdir) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<(\\s*)iframe[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSrc_Str(tag[i], sitename, dir, imgflag, cssjsdir);
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

    public String script_Element(String str, String sitename, String dir, int imgflag, int cssjsdir) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<(\\s*)script([^<>]*)src\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSrc_Str(tag[i], sitename, dir, imgflag, cssjsdir);
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

    public String src_Element(String str, String sitename, String dir, int imgflag, int cssjsdir) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            //Pattern p = Pattern.compile("<(\\s*)img[^>]*>", Pattern.CASE_INSENSITIVE);
            Pattern p = Pattern.compile("<[^<]*\\s+src(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                //System.out.println(tag[i]);
                tag[i] = formatSrc_Str(tag[i], sitename, dir, imgflag, cssjsdir);
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

    //<div style="background-image:url(images/090506title07bg.gif); height:217px; overflow:hidden;">
    //<div style="width:180px; height:262px; float:right; background:url(images/090506bg1.gif)">
    public String div_backgroud_Element(String str, String sitename, String dir, int imgflag) {
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
                    tag[i] = format_div_background_image(tag[i], sitename, dir, imgflag);
                }
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

    public String format_div_background_image(String str, String sitename, String dir, int imgflag) {
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

            posi = fileName.lastIndexOf("/");
            if (posi != -1) {
                fileName = fileName.substring(posi + 1);
            }

            if (imgflag == 0) {
                str = head_str + "url(/webbuilder/sites/" + sitename + "/images/" + fileName  + tail_str;
            } else {
                str = head_str + "url(/webbuilder/sites/" + sitename + dir + "images/" + fileName + tail_str;
            }
        }

        //System.out.println("str=" + str);

        return str;
    }


    public String backgroud_Element(String str, String sitename, String dir, int imgflag) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            //Pattern p = Pattern.compile("<[^<][^script]*background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            Pattern p = Pattern.compile("<[^<]*\\s+background(\\s*)=(\\s*)[^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];
            String temp;

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                temp = tag[i].toLowerCase();
                if (temp.indexOf("script") == -1)
                    tag[i] = formatBackground_Str(tag[i], sitename, dir, imgflag);
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


    public String css_Element(String str, String sitename, String dir, int imgflag, int cssjsdir) {
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
                tag[i] = formatCss_Str(tag[i], sitename, dir, imgflag, cssjsdir);
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

    public String swf_Element(String str, String sitename, String dir, int imgflag) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int posi;

        try {
            Pattern p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']movie[\"|'][^>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSwf_Str(tag[i], sitename, dir, imgflag, "value");
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
                tag1[i] = formatSwf_Str(tag1[i], sitename, dir, imgflag, "src");
            }

            buffer = new StringBuffer();
            for (int i = 0; i < tag1.length; i++) {
                buffer.append(buf1[i]);
                buffer.append(tag1[i]);
            }
            buffer.append(buf1[tag1.length]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String formatSrc_Str(String str, String sitename, String dir, int imgflag, int cssjsdir) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        if ((buf != null) && (buf.indexOf("src") != -1)) {
            posi = buf.indexOf("src");
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                fileName = buf.substring(0, posi);
                tail_str = buf.substring(posi);
            } else if (buf.endsWith("/>")) {
                fileName = buf.substring(0, buf.length() - 2);
                tail_str = "/>";
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

            //System.out.println("fileName=" + fileName);

            posi = fileName.lastIndexOf("/");
            if (posi != -1) {
                fileName = fileName.substring(posi + 1);
            }

            if (imgflag == 0) {
                if (fileName.toLowerCase().indexOf(".js") != -1)
                    if (cssjsdir == 1)
                        str = head_str + "src=\"/webbuilder/sites/" + sitename + "/js/" + fileName + "\"" + tail_str;
                    else
                        str = head_str + "src=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
                else
                    str = head_str + "src=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
            } else {
                if (fileName.toLowerCase().indexOf(".js") != -1)
                    if (cssjsdir == 1)
                        str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "js/" + fileName + "\"" + tail_str;
                    else
                        str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
                else
                    str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
            }
        }

        return str;
    }

    public String formatBackground_Str(String str, String sitename, String dir, int imgflag) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

        buf = str.toLowerCase();
        if ((buf != null) && (buf.indexOf("background") != -1)) {
            posi = buf.indexOf("background");
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
            if (posi != -1) {
                fileName = fileName.substring(posi + 1);
            }

            if (imgflag == 0) {
                str = head_str + "background=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
            } else {
                str = head_str + "background=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
            }
        }

        return str;
    }

    public String formatCss_Str(String str, String sitename, String dir, int imgflag, int cssjsdir) {
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
            if (buf.endsWith("/>")) {
                fileName = buf.substring(0, buf.length() - 2);
                tail_str = "/>";
            }else{
                fileName = buf.substring(0, buf.length() - 1);
                tail_str = ">";
            }
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
        if (posi != -1) {
            fileName = fileName.substring(posi + 1);
        }

        if (imgflag == 0) {
            if (cssjsdir == 1)
                str = head_str + "href=\"/webbuilder/sites/" + sitename + "/css/" + fileName + "\"" + tail_str;
            else
                str = head_str + "href=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        } else {
            if (cssjsdir == 1)
                str = head_str + "href=\"/webbuilder/sites/" + sitename + dir + "css/" + fileName + "\"" + tail_str;
            else
                str = head_str + "href=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String formatSwf_Str(String str, String sitename, String dir, int imgflag, String strflag) {
        String buf;
        String head_str;
        String tail_str;
        String fileName;
        int posi;

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

        posi = fileName.lastIndexOf("/");
        if (posi != -1) {
            fileName = fileName.substring(posi + 1);
        }

        if (strflag.equals("src"))
            if (imgflag == 0)
                str = head_str + "src=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
            else
                str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        else if (imgflag == 0)
            str = head_str + "value=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        else
            str = head_str + "value=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;

        return str;
    }

    private static final String SQL_CREATE_MODEL_FOR_ORACLE =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Content,Createdate,Lastupdated,Editor," +
                    "Creator,LockStatus,RelatedColumnID,Status,ModelVersion,TemplateName,ChName,ReferModelID," +
                    "defaultTemplate,isIncluded,tempnum,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String SQL_CREATE_MODEL_FOR_MSSQL =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Content,Createdate,Lastupdated,Editor," +
                    "Creator,LockStatus,RelatedColumnID,Status,ModelVersion,TemplateName,ChName,ReferModelID," +
                    "defaultTemplate,isIncluded,tempnum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);select SCOPE_IDENTITY()";

    private static final String SQL_CREATE_MODEL_FOR_MYSQL =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Content,Createdate,Lastupdated,Editor," +
                    "Creator,LockStatus,RelatedColumnID,Status,ModelVersion,TemplateName,ChName,ReferModelID," +
                    "defaultTemplate,isIncluded,tempnum) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String Update_Column_HasArticleModel =
            "update tbl_column set hasArticleModel = 1 where id = ?";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int Create(Model model, int siteID,int samsiteid,int sitetype) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int errcode = 1;

        int modelID=0;
        IMarkManager markMgr = markPeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Tree colTree = null;
        if (sitetype == 0)                                              //一般注册站点或者是拷贝其他网站创建站点
            colTree = TreeManager.getInstance().getSiteTree(siteID);
        else                                                            //使用共享模板网站创建网站 sitetype=1
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteID,samsiteid);
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                String relatedColumnIDs;
                relatedColumnIDs = markMgr.getRelatedColumnIDs(siteID,colTree,model.getColumnID(), model.getContent());

                //模板入库
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_CREATE_MODEL_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_CREATE_MODEL_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_CREATE_MODEL_FOR_MYSQL);
                pstmt.setInt(1, siteID);
                pstmt.setInt(2, model.getColumnID());
                pstmt.setInt(3, model.getIsArticle());
                if (model.getReferModelID() == 0)
                    DBUtil.setBigString(cpool.getType(), pstmt, 4, model.getContent());
                else
                    pstmt.setString(4, "");
                pstmt.setTimestamp(5, model.getCreatedate());
                pstmt.setTimestamp(6, model.getLastupdated());
                pstmt.setString(7, model.getEditor());
                pstmt.setString(8, model.getCreator());
                pstmt.setInt(9, model.getLockStatus());
                pstmt.setString(10, relatedColumnIDs);
                pstmt.setInt(11, 0);             //模板页面的状态设置为0,模板需要发布
                pstmt.setInt(12, 0);             //模板为第一个版本，版本号为零
                pstmt.setString(13, model.getTemplateName());
                pstmt.setString(14, model.getChineseName());
                pstmt.setInt(15, model.getReferModelID());
                pstmt.setInt(16, 0);
                pstmt.setInt(17, model.getIncluded());
                pstmt.setInt(18,model.getTempnum());
                if (cpool.getType().equals("oracle")) {
                    modelID = sequnceMgr.getSequenceNum("Template");
                    pstmt.setInt(19, modelID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        modelID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();
                } else {
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值markid
                    pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                    rs = pstmt.executeQuery();
                    if (rs.next()) modelID=rs.getInt(1);
                    rs.close();
                    pstmt.close();
                }

                //分析出该模板包含的新的标记ID，并将这些标记ID在tbl_templatemark中保存
                Pattern p = Pattern.compile("\\[TAG\\]\\[MARKID\\][0-9_]*\\[\\/MARKID\\]\\[\\/TAG\\]",Pattern.CASE_INSENSITIVE);
                String buf =model.getContent();
                java.util.regex.Matcher matcher = p.matcher(buf);
                String matchStr=null;
                while (matcher.find()) {
                    int colid = 0;
                    int markid = 0;
                    matchStr = buf.substring(matcher.start(), matcher.end());
                    int xposi = matchStr.indexOf("[TAG][MARKID]");
                    matchStr = matchStr.substring(xposi + "[TAG][MARKID]".length());
                    xposi = matchStr.indexOf("[/MARKID][/TAG]");
                    matchStr = matchStr.substring(0, xposi);
                    xposi = matchStr.indexOf("_");
                    markid = Integer.parseInt(matchStr.substring(0, xposi));
                    colid = Integer.parseInt(matchStr.substring(xposi + 1));

                    if (cpool.getType().equals("oracle")) {
                        pstmt = conn.prepareStatement("INSERT INTO tbl_templatemark (siteid,columnid,tid,mid,id) VALUES (?, ?, ?, ?, ?)");
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,colid);
                        pstmt.setInt(3,modelID);
                        pstmt.setInt(4,markid);
                        pstmt.setInt(5,sequnceMgr.getSequenceNum("SelfDefine"));
                        pstmt.executeUpdate();
                        pstmt.close();
                    }else if (cpool.getType().equals("mssql")||cpool.getType().equals("mysql")){
                        pstmt = conn.prepareStatement("INSERT INTO tbl_templatemark (siteid,columnid,tid,mid) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,colid);
                        pstmt.setInt(3,modelID);
                        pstmt.setInt(4,markid);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }

                List queueList = new ArrayList();
                Publish publish=null;
                Timestamp now = new Timestamp(System.currentTimeMillis());
                if (model.getIsArticle() == 1) {
                    //修改tbl_column，本栏目设置了文章模板
                    pstmt = conn.prepareStatement(Update_Column_HasArticleModel);
                    pstmt.setInt(1, model.getColumnID());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //修改所有受该模板影响的文章的发布状态位，由发布状态（pubflag=0）改为未发布状态（pubflag=1）
                    String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), model.getColumnID());
                    String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where columnid in " + cids +
                            " and pubflag = 0 and status = 1 and emptycontentflag = 0";
                    pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                    pstmt.executeUpdate();
                    pstmt.close();

                    if (cpool.getPublishWay() == 1) {
                        String SQL_Select_Article = "select id,columnid,maintitle,publishtime from tbl_article where columnid in " + cids + " and status = 1 and emptycontentflag = 0";
                        pstmt = conn.prepareStatement(SQL_Select_Article);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            publish = new Publish();
                            publish.setSiteID(siteID);
                            publish.setColumnID(rs.getInt("columnid"));
                            publish.setTargetID(rs.getInt("id"));
                            publish.setPublishDate(rs.getTimestamp("publishtime"));
                            publish.setPriority(1);
                            publish.setObjectType(1);
                            publish.setTitle(rs.getString("maintitle"));
                            queueList.add(publish);
                        }
                        rs.close();
                        pstmt.close();
                    }
                } else if (cpool.getPublishWay() == 1) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setColumnID(model.getColumnID());
                    publish.setTargetID(modelID);
                    publish.setPriority(2);                                         //栏目模板发布作业优先权设置为2
                    publish.setPublishDate(now);
                    if (model.getIsArticle() == 0 || model.getIsArticle() == 3)     //栏目模板和专题模板
                        publish.setObjectType(3);
                    else if (model.getIsArticle() == 2)                            //首页模板
                        publish.setObjectType(2);
                    else if (model.getIsArticle() >=10)
                        publish.setObjectType(model.getIsArticle());                //程序模板
                    publish.setTitle(model.getChineseName());
                    queueList.add(publish);
                }

                //"INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                //            "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                if (cpool.getPublishWay() == 1) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        pstmt.setInt(1, siteID);
                        pstmt.setInt(2, model.getColumnID());
                        pstmt.setInt(3, publish.getTargetID());
                        pstmt.setInt(4, publish.getObjectType());
                        pstmt.setInt(5, 1);
                        pstmt.setTimestamp(6, now);
                        pstmt.setTimestamp(7, publish.getPublishDate());
                        pstmt.setString(8, "");
                        pstmt.setString(9, publish.getTitle());
                        pstmt.setInt(10,publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            int id = sequnceMgr.getSequenceNum("PublishQueue");
                            pstmt.setLong(11, id);
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }
                conn.commit();
            } catch (Exception e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: create Template failed.");
            } finally {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        catch (SQLException e) {
            errcode = -1;
            throw new ModelException("Database exception: can't rollback?");
        }

        return errcode;
    }

    private static final String SQL_UPDATE_MODEL =
            "UPDATE TBL_Template SET ColumnID=?,IsArticle=?,Content=?,Lastupdated=?,Editor=?,LockStatus=?," +
                    "LockEditor=?,RelatedColumnid=?,Status=?,ModelVersion=?,ChName=?,TemplateName=?,isIncluded=? ,tempnum=? WHERE ID=?";

    private static final String SQL_UPDATE_MODEL_PUBFLAG =
            "UPDATE TBL_Template SET Status=0 WHERE refermodelid=? AND isarticle <> 1";

    private static final String SQL_GET_REFERRED_ARTICLE_MODEL =
            "SELECT ColumnID FROM TBL_Template WHERE refermodelid=? AND isarticle = 1";

    public void Update(Model model, int siteID,int samsiteid,int sitetype) throws ModelException {
        if (model.getReferModelID() == 0) {
            ModelHistory history = new ModelHistory(model.getRealPath());
            history.create(model);
        }

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IMarkManager markMgr = markPeer.getInstance();
        Tree colTree = null;
        if (sitetype == 0)                                              //一般注册站点或者是拷贝其他网站创建站点
            colTree = TreeManager.getInstance().getSiteTree(siteID);
        else                                                            //使用共享模板网站创建网站 sitetype=1
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteID,samsiteid);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                String relatedColumnIDs = model.getRelatedColumnIDs();
                if (model.getReferModelID() == 0) relatedColumnIDs = markMgr.getRelatedColumnIDs(siteID,colTree,model.getColumnID(), model.getContent());
                pstmt = conn.prepareStatement(SQL_UPDATE_MODEL);
                pstmt.setInt(1, model.getColumnID());
                pstmt.setInt(2, model.getIsArticle());
                DBUtil.setBigString(cpool.getType(), pstmt, 3, model.getContent());
                pstmt.setTimestamp(4, model.getLastupdated());
                pstmt.setString(5, model.getEditor());
                pstmt.setInt(6, model.getLockStatus());
                pstmt.setString(7, "");
                pstmt.setString(8, relatedColumnIDs);
                pstmt.setInt(9, 0);                 //模板页面的状态设置为0，模板需要发布
                pstmt.setInt(10, 0);                //模板为第一个版本，版本号为零
                pstmt.setString(11, model.getChineseName());
                pstmt.setString(12, model.getTemplateName());
                pstmt.setInt(13, model.getIncluded());
                pstmt.setInt(14,model.getTempnum());
                pstmt.setInt(15, model.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //修改引用当前模板的模板的发布状态
                if (model.getReferModelID() == 0) {
                    if (model.getIsArticle() != 1)   //针对栏目模板
                    {
                        pstmt = conn.prepareStatement(SQL_UPDATE_MODEL_PUBFLAG);
                        pstmt.setInt(1, model.getID());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else {                            //针对文章模板
                        pstmt = conn.prepareStatement(SQL_GET_REFERRED_ARTICLE_MODEL);
                        pstmt.setInt(1, model.getID());
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), rs.getInt(1));
                            String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where columnid in " + cids + " and (pubflag=0 or pubflag=2 and status=1)";
                            pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                        rs.close();
                        pstmt.close();
                    }
                }

                //删除这个模板包含的所有标记ID，从tbl_templatemark表中删除
                pstmt = conn.prepareStatement("delete from tbl_templatemark where siteid=? and tid=?");
                pstmt.setInt(1,siteID);
                pstmt.setInt(2,model.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //分析出该模板包含的新的标记ID，并将这些标记ID在tbl_templatemark中保存
                Pattern p = Pattern.compile("\\[TAG\\]\\[MARKID\\][0-9_]*\\[\\/MARKID\\]\\[\\/TAG\\]",Pattern.CASE_INSENSITIVE);
                String buf =model.getContent();
                java.util.regex.Matcher matcher = p.matcher(buf);
                String matchStr=null;
                while (matcher.find()) {
                    int colid = 0;
                    int markid = 0;
                    matchStr = buf.substring(matcher.start(), matcher.end());
                    int xposi = matchStr.indexOf("[TAG][MARKID]");
                    matchStr = matchStr.substring(xposi + "[TAG][MARKID]".length());
                    xposi = matchStr.indexOf("[/MARKID][/TAG]");
                    matchStr = matchStr.substring(0, xposi);
                    xposi = matchStr.indexOf("_");
                    markid = Integer.parseInt(matchStr.substring(0, xposi));
                    colid = Integer.parseInt(matchStr.substring(xposi + 1));
                    if (cpool.getType().equals("oracle")) {
                        pstmt = conn.prepareStatement("INSERT INTO tbl_templatemark (siteid,columnid,tid,mid,id) VALUES (?, ?, ?, ?, ?)");
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,colid);
                        pstmt.setInt(3,model.getID());
                        pstmt.setInt(4,markid);
                        pstmt.setInt(5,sequnceMgr.getSequenceNum("SelfDefine"));
                        pstmt.executeUpdate();
                        pstmt.close();
                    }else if (cpool.getType().equals("mssql")||cpool.getType().equals("mysql")){
                        pstmt = conn.prepareStatement("INSERT INTO tbl_templatemark (siteid,columnid,tid,mid) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,colid);
                        pstmt.setInt(3,model.getID());
                        pstmt.setInt(4,markid);
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }

                List queueList = new ArrayList();
                Publish publish=null;
                Timestamp now = new Timestamp(System.currentTimeMillis());

                //if (model.getIsArticle() == 1 && model.getPublishArticle()) {
                if (model.getIsArticle() == 1) {
                    //如果该模板被修改为文章模板，则修改TBL_COLUMN，设置本栏目存在文章模板标志
                    pstmt = conn.prepareStatement(Update_Column_HasArticleModel);
                    pstmt.setInt(1, model.getColumnID());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //如果该模板为文章模板，修改该栏目下引用的文章发布标志位
                    pstmt = conn.prepareStatement("update tbl_refers_article set pubflag=1 where columnid = ?");
                    pstmt.setInt(1, model.getColumnID());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //修改所有受该模板影响的文章的发布状态位，由发布状态（pubflag=0）改为未发布状态（pubflag=1）
                    String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), model.getColumnID());
                    String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where columnid in " + cids + " and (pubflag=0 or pubflag=2)";
                    pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                    pstmt.executeUpdate();
                    pstmt.close();

                    if (cpool.getPublishWay() == 1) {
                        String SQL_Select_Article = "select id,columnid,maintitle,publishtime from tbl_article where columnid in " + cids + " and status=1 and emptycontentflag=0";
                        pstmt = conn.prepareStatement(SQL_Select_Article);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            publish = new Publish();
                            publish.setSiteID(siteID);
                            publish.setColumnID(rs.getInt("columnid"));
                            publish.setTargetID(rs.getInt("id"));
                            publish.setPublishDate(rs.getTimestamp("publishtime"));
                            publish.setPriority(1);                                  //文章发布作业的优先级设置为1
                            publish.setObjectType(1);
                            publish.setTitle(rs.getString("maintitle"));
                            queueList.add(publish);
                        }
                        rs.close();
                        pstmt.close();
                    }
                }

                if (model.getIsArticle() != 1 && cpool.getPublishWay() == 1) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setTargetID(model.getID());
                    publish.setColumnID(model.getColumnID());
                    publish.setTitle(model.getChineseName());
                    publish.setPublishDate(now);
                    publish.setPriority(2);                                          //栏目模板发布作业优先级设置为2
                    if (model.getIsArticle() == 0 || model.getIsArticle() == 3)     //栏目模板和专题模板
                        publish.setObjectType(3);
                    else if (model.getIsArticle() == 2)                            //首页模板
                        publish.setObjectType(2);
                    else if (model.getIsArticle() >=10)
                        publish.setObjectType(model.getIsArticle());                //程序模板
                    queueList.add(publish);
                }

                //INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                //    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                if (cpool.getPublishWay() == 1) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        String title = publish.getTitle();
                        if (cpool.getType().equals("mssql")) title = StringUtil.gb2iso4View(title);
                        pstmt.setInt(1, siteID);
                        pstmt.setInt(2, model.getColumnID());
                        pstmt.setInt(3, publish.getTargetID());
                        pstmt.setInt(4, publish.getObjectType());
                        pstmt.setInt(5, 1);
                        pstmt.setTimestamp(6, now);
                        pstmt.setTimestamp(7, publish.getPublishDate());
                        pstmt.setString(8, "");
                        pstmt.setString(9, title);
                        pstmt.setInt(10,publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            int pqid = sequnceMgr.getSequenceNum("PublishQueue");
                            pstmt.setLong(11, pqid);
                        }
                        pstmt.executeUpdate();
                    }
                    pstmt.close();
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: update Template failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GETModelBySiteidAndColumnid =
            "select ID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,siteid,tempnum from " +
                    "TBL_Template where ColumnID = ? and siteid = ?";

    public Model getModelBySiteIDAndColumnid(int columnID, int siteID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Model model = null;
        int refermodelid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelBySiteidAndColumnid);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                model = load(rs);       //默认模版不是引用模版
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return model;
    }

    private static final String SQL_UPDATE_MODEL_CONTENT = "UPDATE TBL_Template SET Content=?,isincluded=1 WHERE siteid=? and columnid=?";

    public void Update(Model model, int siteID,int columnid,int samsiteid,int sitetype) throws ModelException {
        if (model.getReferModelID() == 0) {
            ModelHistory history = new ModelHistory(model.getRealPath());
            history.create(model);
        }

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        IMarkManager markMgr = markPeer.getInstance();
        Tree colTree = null;
        if (sitetype == 0)                                              //一般注册站点或者是拷贝其他网站创建站点
            colTree = TreeManager.getInstance().getSiteTree(siteID);
        else                                                            //使用共享模板网站创建网站 sitetype=1
            colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteID,samsiteid);

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                String relatedColumnIDs = model.getRelatedColumnIDs();
                if (model.getReferModelID() == 0) relatedColumnIDs = markMgr.getRelatedColumnIDs(siteID,colTree,model.getColumnID(), model.getContent());
                pstmt = conn.prepareStatement(SQL_UPDATE_MODEL_CONTENT);
                DBUtil.setBigString(cpool.getType(), pstmt, 1, model.getContent());
                pstmt.setInt(2,siteID);
                pstmt.setInt(3,columnid);
                pstmt.executeUpdate();
                pstmt.close();

                //修改引用当前模板的模板的发布状态
                if (model.getReferModelID() == 0) {
                    if (model.getIsArticle() != 1)   //针对栏目模板
                    {
                        pstmt = conn.prepareStatement(SQL_UPDATE_MODEL_PUBFLAG);
                        pstmt.setInt(1, model.getID());
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else {                            //针对文章模板
                        pstmt = conn.prepareStatement(SQL_GET_REFERRED_ARTICLE_MODEL);
                        pstmt.setInt(1, model.getID());
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), rs.getInt(1));
                            String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where columnid in " + cids + " and (pubflag=0 or pubflag=2 and status=1)";
                            pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                            pstmt.executeUpdate();
                            pstmt.close();
                        }
                        rs.close();
                        pstmt.close();
                    }
                }

                //删除这个模板包含的所有标记ID，从tbl_templatemark表中删除
                pstmt = conn.prepareStatement("delete from tbl_templatemark where siteid=? and tid=?");
                pstmt.setInt(1,siteID);
                pstmt.setInt(2,model.getID());
                pstmt.executeUpdate();
                pstmt.close();

                //分析出该模板包含的新的标记ID，并将这些标记ID在tbl_templatemark中保存
                Pattern p = Pattern.compile("\\[TAG\\]\\[MARKID\\][0-9_]*\\[\\/MARKID\\]\\[\\/TAG\\]",Pattern.CASE_INSENSITIVE);
                String buf =model.getContent();
                java.util.regex.Matcher matcher = p.matcher(buf);
                String matchStr=null;
                while (matcher.find()) {
                    int colid = 0;
                    int markid = 0;
                    matchStr = buf.substring(matcher.start(), matcher.end());
                    int xposi = matchStr.indexOf("[TAG][MARKID]");
                    matchStr = matchStr.substring(xposi + "[TAG][MARKID]".length());
                    xposi = matchStr.indexOf("[/MARKID][/TAG]");
                    matchStr = matchStr.substring(0, xposi);
                    xposi = matchStr.indexOf("_");
                    markid = Integer.parseInt(matchStr.substring(0, xposi));
                    colid = Integer.parseInt(matchStr.substring(xposi + 1));
                    pstmt = conn.prepareStatement("INSERT INTO tbl_templatemark (siteid,columnid,tid,mid,id) VALUES (?, ?, ?, ?, ?)");
                    pstmt.setInt(1,siteID);
                    pstmt.setInt(2,colid);
                    pstmt.setInt(3,model.getID());
                    pstmt.setInt(4,markid);
                    pstmt.setInt(5,sequnceMgr.getSequenceNum("SelfDefine"));
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                List queueList = new ArrayList();
                Publish publish=null;
                Timestamp now = new Timestamp(System.currentTimeMillis());

                //if (model.getIsArticle() == 1 && model.getPublishArticle()) {
                if (model.getIsArticle() == 1) {
                    //如果该模板被修改为文章模板，则修改TBL_COLUMN，设置本栏目存在文章模板标志
                    pstmt = conn.prepareStatement(Update_Column_HasArticleModel);
                    pstmt.setInt(1, model.getColumnID());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //如果该模板为文章模板，修改该栏目下引用的文章发布标志位
                    pstmt = conn.prepareStatement("update tbl_refers_article set pubflag=1 where columnid = ?");
                    pstmt.setInt(1, model.getColumnID());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //修改所有受该模板影响的文章的发布状态位，由发布状态（pubflag=0）改为未发布状态（pubflag=1）
                    String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), model.getColumnID());
                    String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where columnid in " + cids + " and (pubflag=0 or pubflag=2)";
                    pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                    pstmt.executeUpdate();
                    pstmt.close();

                    if (cpool.getPublishWay() == 1) {
                        String SQL_Select_Article = "select id,columnid,maintitle,publishtime from tbl_article where columnid in " + cids + " and status=1 and emptycontentflag=0";
                        pstmt = conn.prepareStatement(SQL_Select_Article);
                        rs = pstmt.executeQuery();
                        while (rs.next()) {
                            publish = new Publish();
                            publish.setSiteID(siteID);
                            publish.setColumnID(rs.getInt("columnid"));
                            publish.setTargetID(rs.getInt("id"));
                            publish.setPublishDate(rs.getTimestamp("publishtime"));
                            publish.setPriority(1);                                  //文章发布作业的优先级设置为1
                            publish.setObjectType(1);
                            publish.setTitle(rs.getString("maintitle"));
                            queueList.add(publish);
                        }
                        rs.close();
                        pstmt.close();
                    }
                }

                //获取网站根栏目ID
                int webroot_columnid = 0;
                pstmt = conn.prepareStatement("select t.id from tbl_column t where t.siteid=?");
                pstmt.setInt(1,siteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    webroot_columnid=rs.getInt("id");
                }
                rs.close();
                pstmt.close();

                //获取网站的首页模板，将首页模板送入到发布队列
                pstmt = conn.prepareStatement("select * from tbl_template t where t.isarticle=2 and t.siteid=2991 and t.tempnum in (select SHARETEMPLATENUM from tbl_siteinfo t1 where t1.siteid=?)");
                pstmt.setInt(1,siteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setTargetID(rs.getInt("id"));
                    publish.setColumnID(webroot_columnid);
                    publish.setTitle(rs.getString("chname"));
                    publish.setPublishDate(now);
                    publish.setPriority(2);                                          //栏目模板发布作业优先级设置为2
                    int isarticle = rs.getInt("ISARTICLE");
                    if (isarticle == 0 || isarticle == 3)     //栏目模板和专题模板
                        publish.setObjectType(3);
                    else if (isarticle == 2)                            //首页模板
                        publish.setObjectType(2);
                    else if (isarticle >=10)
                        publish.setObjectType(isarticle);                //程序模板
                    queueList.add(publish);
                }
                rs.close();
                pstmt.close();

                if (model.getIsArticle() != 1 && cpool.getPublishWay() == 1) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setTargetID(model.getID());
                    publish.setColumnID(model.getColumnID());
                    publish.setTitle(model.getChineseName());
                    publish.setPublishDate(now);
                    publish.setPriority(2);                                          //栏目模板发布作业优先级设置为2
                    if (model.getIsArticle() == 0 || model.getIsArticle() == 3)     //栏目模板和专题模板
                        publish.setObjectType(3);
                    else if (model.getIsArticle() == 2)                            //首页模板
                        publish.setObjectType(2);
                    else if (model.getIsArticle() >=10)
                        publish.setObjectType(model.getIsArticle());                //程序模板
                    queueList.add(publish);
                }

                //INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                //    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                if (cpool.getPublishWay() == 1) {
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        String title = publish.getTitle();
                        if (cpool.getType().equals("mssql")) title = StringUtil.gb2iso4View(title);

                        if (cpool.getType().equalsIgnoreCase("oracle"))
                            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                        else
                            pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                        pstmt.setInt(1, siteID);
                        pstmt.setInt(2, publish.getColumnID());
                        pstmt.setInt(3, publish.getTargetID());
                        pstmt.setInt(4, publish.getObjectType());
                        pstmt.setInt(5, 1);
                        pstmt.setTimestamp(6, now);
                        pstmt.setTimestamp(7, publish.getPublishDate());
                        pstmt.setString(8, "");
                        pstmt.setString(9, title);
                        pstmt.setInt(10,publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            int pqid = sequnceMgr.getSequenceNum("PublishQueue");
                            pstmt.setLong(11, pqid);
                        }
                        pstmt.executeUpdate();
                        pstmt.close();
                    }
                }
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: update Template failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    public void PublishHomePage(int siteID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List queueList = new ArrayList();
        Publish publish=null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            //获取网站根栏目ID
            int webroot_columnid = 0;
            pstmt = conn.prepareStatement("select t.id from tbl_column t where t.siteid=?");
            pstmt.setInt(1,siteID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                webroot_columnid=rs.getInt("id");
            }
            rs.close();
            pstmt.close();

            //获取网站的首页模板，将首页模板送入到发布队列
            pstmt = conn.prepareStatement("select * from tbl_template t where t.isarticle=2 and t.siteid=2991 and t.tempnum in (select SHARETEMPLATENUM from tbl_siteinfo t1 where t1.siteid=?)");
            pstmt.setInt(1,siteID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                publish = new Publish();
                publish.setSiteID(siteID);
                publish.setTargetID(rs.getInt("id"));
                publish.setColumnID(webroot_columnid);
                publish.setTitle(rs.getString("chname"));
                publish.setPublishDate(now);
                publish.setPriority(2);                                          //栏目模板发布作业优先级设置为2
                int isarticle = rs.getInt("ISARTICLE");
                if (isarticle == 0 || isarticle == 3)     //栏目模板和专题模板
                    publish.setObjectType(3);
                else if (isarticle == 2)                            //首页模板
                    publish.setObjectType(2);
                else if (isarticle >=10)
                    publish.setObjectType(isarticle);                //程序模板
                queueList.add(publish);
            }
            rs.close();
            pstmt.close();

            //INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            //    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            if (cpool.getPublishWay() == 1) {
                for (int i = 0; i < queueList.size(); i++) {
                    publish = (Publish) queueList.get(i);
                    String title = publish.getTitle();
                    if (cpool.getType().equals("mssql")) title = StringUtil.gb2iso4View(title);
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, publish.getColumnID());
                    pstmt.setInt(3, publish.getTargetID());
                    pstmt.setInt(4, publish.getObjectType());
                    pstmt.setInt(5, 1);
                    pstmt.setTimestamp(6, now);
                    pstmt.setTimestamp(7, publish.getPublishDate());
                    pstmt.setString(8, "");
                    pstmt.setString(9, title);
                    pstmt.setInt(10,publish.getPriority());
                    if (cpool.getType().equals("oracle")) {
                        int pqid = sequnceMgr.getSequenceNum("PublishQueue");
                        pstmt.setLong(11, pqid);
                    }
                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
            conn.commit();
        } catch (SQLException exp) {
            exp.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    //Add by EricDu 2003.8.27
    public void updatecancle(int lockstatus, int id) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement("update tbl_template set lockstatus = ?, lockeditor = ? where id = ?");
                pstmt.setInt(1, 0);      //修改锁定标志
                pstmt.setString(2, "");  //修改编制人
                pstmt.setInt(3, id);
                pstmt.executeUpdate();

                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: update lockstatus failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    public int checklock(Model model) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        boolean flg = false;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement("select count(ID) from tbl_template where id = ? and lockstatus = ?");
                pstmt.setInt(1, model.getID());
                pstmt.setInt(2, 1);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) flg = true;
                rs.close();
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
                conn.rollback();
            } finally {
                if (conn != null)
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
            }
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        if (flg)
            return 1;
        else
            return 0;
    }

    private static final String SQL_REMOVEModel ="DELETE FROM TBL_Template WHERE ID = ? and siteid=?";

    private static final String Update_Column_NoArticleModel ="update tbl_column set hasArticleModel = 0 WHERE ID = ? and siteid=?";

    private static final String Delete_Referred_Template ="DELETE FROM TBL_Template WHERE ReferModelID = ? and siteid=?";

    private static final String SQl_GetTheModel = "select ColumnID,IsArticle,ReferModelID from TBL_Template where ID = ? and siteid=?";

    public void Remove(int ID, int siteID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Tree colTree = TreeManager.getInstance().getSiteTree(siteID);
        int columnID = 0;
        int modelType = -1;
        int referModelID = 0;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                //获取被删除模板所在的栏目的ID，获取被删除模板的类型
                pstmt = conn.prepareStatement(SQl_GetTheModel);
                pstmt.setInt(1, ID);
                pstmt.setInt(2, siteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    columnID = rs.getInt("columnID");
                    modelType = rs.getInt("IsArticle");
                    referModelID = rs.getInt("referModelID");
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_REMOVEModel);
                pstmt.setInt(1, ID);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();

                if (referModelID == 0) {
                    pstmt = conn.prepareStatement(Delete_Referred_Template);
                    pstmt.setInt(1, ID);
                    pstmt.setInt(2, siteID);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                if (modelType == 1) {
                    //如果是文章模板被删除，则修改TBL_COLUMN，设置本栏目文章模板存在标志为0
                    pstmt = conn.prepareStatement(Update_Column_NoArticleModel);
                    pstmt.setInt(1, columnID);
                    pstmt.setInt(2, siteID);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //修改所有受该模板影响的文章的发布状态位，由发布状态（pubflag=0）改为未发布状态（pubflag=1）
                    String cids = colTree.getSubTree_NoArticleModelColumnIDList(colTree.getAllNodes(), columnID);
                    String SQL_Update_ArticleStatus = "update tbl_article set pubflag=1 where siteid=? and columnid in " + cids + " and pubflag=0";
                    pstmt = conn.prepareStatement(SQL_Update_ArticleStatus);
                    pstmt.setInt(1,siteID);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: delete Template failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    //删除某个栏目时要先删除该栏目下的所有文章（add by lxm 2003.5.15）
    private static final String SQL_REMOVEModelsInColumn = "DELETE FROM TBL_Template WHERE columnID=? ";

    public void removeModelsInColumn(int columnID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_REMOVEModelsInColumn);

                pstmt.setInt(1, columnID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: delete Template failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_setDefault = "update TBL_Template set defaultTemplate = 1 where id=?";
    private static final String SQL_oldDefault = "update TBL_Template set defaultTemplate = 0 where columnID=? and isArticle=?";

    public boolean setupDefault(int ID, int columnID, int modelType) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        boolean retval = false;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_oldDefault);
                pstmt.setInt(1, columnID);
                pstmt.setInt(2, modelType);
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_setDefault);
                pstmt.setInt(1, ID);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
                retval = true;
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: delete Template failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }

        return retval;
    }

    private static final String SQL_GETContentModelCOUNT =
            "SELECT count(ID) FROM TBL_Template where ColumnID = ? AND isArticle = ?";     //文章模板

    public int getContentModelCount(int columnID, int type) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETContentModelCOUNT);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, type);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelException("Database exception: get template count failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETIndexModelCOUNT =
            "SELECT count(ID) FROM TBL_Template where ColumnID = ? AND isArticle = ?";     //栏目模板

    public int getIndexModelCount(int columnID, int type) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETIndexModelCOUNT);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, type);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelException("Database exception: get template count failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETHomeModelCOUNT =
            "SELECT count(ID) FROM TBL_Template where ColumnID = ? AND isArticle = 2";     //首页模板

    public int getHomeModelCount(int webRootID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETHomeModelCOUNT);
            pstmt.setInt(1, webRootID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelException("Database exception: get template count failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GETTopicModelCOUNT =
            "SELECT count(ID) FROM TBL_Template where ColumnID = -1";     //专题模板

    public int getTopicModelCount() throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETTopicModelCOUNT);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelException("Database exception: get template count failed.");
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public int getModelCount(int siteid,int samsiteid,int sitetype,int columnID,int tempnum) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int count = 0;
        int samsite_root_columnid = 0;
        int site_root_columnid = 0;
        String SQL_GETModelCOUNT = "SELECT count(ID) FROM TBL_Template where (ColumnID = ? and siteid=?) OR (siteid=? and columnid=? and tempnum=?)";
        String SQL_Get_Samsite_Root_Column_ID = "SELECT min(id) FROM TBL_Column where siteid = ?";
        String SQL_Get_Site_Root_Column_ID = "SELECT min(id) FROM TBL_Column where siteid = ?";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_Get_Samsite_Root_Column_ID);
            pstmt.setInt(1, samsiteid);
            rs = pstmt.executeQuery();
            if (rs.next()) samsite_root_columnid = rs.getInt(1);
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_Get_Site_Root_Column_ID);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) site_root_columnid = rs.getInt(1);
            rs.close();
            pstmt.close();

            //System.out.println("SELECT count(ID) FROM TBL_Template where (ColumnID = " + columnID + " and siteid=" + siteid + ") OR (siteid=" + samsiteid + " and columnid=" + samsite_root_columnid +  " and tempnum=0)");

            //获取栏目模板的数量
            pstmt = conn.prepareStatement(SQL_GETModelCOUNT);
            pstmt.setInt(1, columnID);
            pstmt.setInt(2, siteid);
            pstmt.setInt(3, samsiteid);
            if (sitetype == 1  && site_root_columnid == columnID)           //处理共享站点的根节点
                pstmt.setInt(4, samsite_root_columnid);
            else                                                           //处理其他节点和一般站点节点
                pstmt.setInt(4, columnID);
            pstmt.setInt(5, tempnum);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    private static final String SQL_GET_NOT_REFERRED_MODEL_COUNT =
            "SELECT count(ID) FROM TBL_Template where ColumnID = ? AND ReferModelID = 0";

    public int getNotReferredModelCount(int columnID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_NOT_REFERRED_MODEL_COUNT);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return count;
    }

    public List getModels(int siteid,int samsiteid,int sitetype,int ColumnID,int tempnum,int startIndex, int numResults) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        Model model = null;
        String SQL_GETModelS = null;
        int samsite_root_columnid = 0;
        int site_root_columnid = 0;

        String SQL_Get_Site_Root_Column_ID = "SELECT min(id) FROM TBL_Column where siteid = ?";
        String SQL_Get_Samsite_Root_Column_ID = "SELECT min(id) FROM TBL_Column where siteid = ?";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_Get_Samsite_Root_Column_ID);
            pstmt.setInt(1, samsiteid);
            rs = pstmt.executeQuery();
            if (rs.next()) samsite_root_columnid = rs.getInt(1);
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_Get_Site_Root_Column_ID);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) site_root_columnid = rs.getInt(1);
            rs.close();
            pstmt.close();

            System.out.println("sitetype===" + sitetype);
            System.out.println("site_root_columnid===" + site_root_columnid);
            System.out.println("ColumnID===" + ColumnID);


            //获取栏目模板
            if (sitetype==1)
                if (site_root_columnid == ColumnID)
                    SQL_GETModelS ="select ID,siteid,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate,status,lockstatus,lockeditor," +
                            "ChName,TemplateName,ReferModelID from tbl_template where siteid=? and columnid=? and tempnum=? order by LastUpdated Desc";
                else
                    SQL_GETModelS ="select ID,siteid,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate,status,lockstatus,lockeditor," +
                            "ChName,TemplateName,ReferModelID from tbl_template where (siteid=? and columnid=? and tempnum=?) OR (siteid=? and columnid=?) order by LastUpdated Desc";
            else
                SQL_GETModelS ="select ID,siteid,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate,status,lockstatus,lockeditor," +
                        "ChName,TemplateName,ReferModelID from tbl_template where ColumnID = ? and siteid=?  order by LastUpdated Desc";

            pstmt = conn.prepareStatement(SQL_GETModelS);
            if (sitetype==1) {
                if (site_root_columnid == ColumnID) {       //访问网站的根目录，读取共享模板库里面的模板
                    pstmt.setInt(1, samsiteid);
                    pstmt.setInt(2, samsite_root_columnid);
                    pstmt.setInt(3, tempnum);
                } else {
                    pstmt.setInt(1, samsiteid);             //取共享网站的栏目模板和文章模板
                    pstmt.setInt(2, ColumnID);
                    pstmt.setInt(3, tempnum);
                    pstmt.setInt(4, siteid);                //取本网站的专题模板
                    pstmt.setInt(5, ColumnID);
                }
            } else {
                pstmt.setInt(1, ColumnID);
                pstmt.setInt(2, siteid);
            }
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    model = loadNoContent(rs);
                    list.add(model);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return list;
    }

    private static final String SQL_GETModelCOUNT_For_Program = "SELECT count(ID) FROM TBL_Template where siteid= ? and ColumnID = ?";

    public int getModelCountForPragram(int siteid,int columnID)
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelCOUNT_For_Program);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnID);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }
    //----------wangjian 2009
    public int getModelCountForPragram(int siteid,int samsiteid,int columnID)
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        int tempnum=0;
        String SQL_GETModelCOUNTs_For_Program = "SELECT count(ID) FROM TBL_Template where siteid= ? and ColumnID = ? and tempnum=?";
        ISiteInfoManager siteinfoMgr= SiteInfoPeer.getInstance();
        try{
            SiteInfo siteinfo=(SiteInfo)siteinfoMgr.getSiteInfo(siteid);
            tempnum=siteinfo.getTempnum();


        }catch(Exception e){

        }
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelCOUNTs_For_Program);
            pstmt.setInt(1, samsiteid);
            pstmt.setInt(2, columnID);
            pstmt.setInt(3, tempnum);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }


    //----------------

    private static final String SQL_GETModelS_FOR_Program =
            "select ID,SiteID,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate," +
                    "lockstatus,lockeditor,ChName,TemplateName,ReferModelID from " +
                    "tbl_template where siteid = ? and columnid = ? order by LastUpdated Desc";
    //"SELECT ID,SiteID,ColumnID,IsArticle,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
    //        "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded FROM " +
    //        "(SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_template where siteid=? and columnid=? order by LastUpdated Desc) " +
    //        "A WHERE ROWNUM <= ?) WHERE RN >= ?";


    public List getModelsForProgram(int siteID, int ColumnID, int startIndex, int numResults) throws ModelException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Model model = null;
        // System.out.println("siteid="+siteID);
        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelS_FOR_Program);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, ColumnID);
            //pstmt.setInt(3, startIndex + numResults-1);
            //pstmt.setInt(4, startIndex);
            rs = pstmt.executeQuery();
            //while (rs.next()) {
            //    model = loadNoContent(rs);
            //    list.add(model);
            //}
            for (int i = 0; i < startIndex; i++)
            {
                rs.next();
            }

            for (int i = 0; i < numResults; i++)
            {
                if (rs.next())
                {
                    model = loadNoContent(rs);
                    list.add(model);
                }
                else
                {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }
    //---------------wangjian 2009
    public List getModelsForProgram(int siteID,int samsiteid, int ColumnID, int startIndex, int numResults) throws ModelException
    {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Model model = null;
        int tempnum=0;
        String SQL_samGETModelS_FOR_Program =
                "select ID,SiteID,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate," +
                        "lockstatus,lockeditor,ChName,TemplateName,ReferModelID from " +
                        "tbl_template where siteid = ? and columnid = ? and tempnum=? order by LastUpdated Desc";
        ISiteInfoManager siteinfoMgr= SiteInfoPeer.getInstance();
        try{
            SiteInfo siteinfo=(SiteInfo)siteinfoMgr.getSiteInfo(siteID);
            tempnum=siteinfo.getTempnum();


        }catch(Exception e){

        }

        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_samGETModelS_FOR_Program);
            pstmt.setInt(1, samsiteid);
            pstmt.setInt(2, ColumnID);
            pstmt.setInt(3,tempnum);
            //pstmt.setInt(3, startIndex + numResults-1);
            //pstmt.setInt(4, startIndex);
            rs = pstmt.executeQuery();
            //while (rs.next()) {
            //    model = loadNoContent(rs);
            //    list.add(model);
            //}
            for (int i = 0; i < startIndex; i++)
            {
                rs.next();
            }

            for (int i = 0; i < numResults; i++)
            {
                if (rs.next())
                {
                    model = loadNoContent(rs);
                    list.add(model);
                }
                else
                {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        finally
        {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }
    //------

    private static final String SQL_GET_NOT_REFERRED_ModelS =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum from " +
                    "tbl_template where columnid = ? and ReferModelID = 0 order by LastUpdated Desc";

    public List getNotReferredModels(int ColumnID, int startIndex, int numResults) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Model model;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_NOT_REFERRED_ModelS);
            pstmt.setInt(1, ColumnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    model = load(rs);
                    list.add(model);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public List getModels(String cids, int startIndex, int numResults) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;

        String SQL_GETModelsInColumn = "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum from " +
                "tbl_template where columnid in " + cids + " and (isarticle=0 or isarticle=2)";
        ResultSet rs;

        List list = new ArrayList();
        Model model;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelsInColumn);
            rs = pstmt.executeQuery();

            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }

            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    model = load(rs);
                    list.add(model);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    public int getModelsNum(String cids) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        String SQL_GETModelsInColumn = "select count(id) from tbl_template where columnid in " + cids + " and (isarticle=0 or isarticle=2)";
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETModelsInColumn);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return count;
    }

    //获取某个站点需要发布的所有栏目页面
    //private static final String SQL_GET_MODELS_FORAUTOPUB =
    //        "select a.id,a.columnid,a.isarticle,a.chname from tbl_template a,tbl_column b " +
    //                "where a.columnid = b.id and a.isarticle != 1 and a.status = 0 and b.siteid=?";

    private static final String SQL_GET_MODELS_FORAUTOPUB =
            "select id,siteid,columnid,isarticle,chname from tbl_template where siteid=? and isarticle != 1 and status = 0";

    private static final String SQL_UPDATE_MODELS_FORAUTOPUB =
            "UPDATE TBL_Template SET Status = 1 WHERE ID = ?";

    private static final String SQL_INSERT_MODELS_FORAUTOPUB =
            "INSERT INTO TBL_Publish_Queue (ID, SiteID, Type) VALUES (?, ?, 0)";

    public List getModelsForAutoPub(int siteID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        int modelID;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_GET_MODELS_FORAUTOPUB);     //得到所有符合条件的需要发布的模板
                pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    list.add(loadForAutoPub(rs));
                }
                rs.close();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_MODELS_FORAUTOPUB);  //更新模板为发布成功
                for (int i = 0; i < list.size(); i++) {
                    modelID = ((Model) list.get(i)).getID();
                    pstmt.setInt(1, modelID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_INSERT_MODELS_FORAUTOPUB);  //模板进入发布队列
                for (int i = 0; i < list.size(); i++) {
                    modelID = ((Model) list.get(i)).getID();
                    pstmt.setInt(1, modelID);
                    pstmt.setInt(2, siteID);
                    pstmt.executeUpdate();
                }
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: update model failed.");
            } finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    }
                    catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        } catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
        return list;
    }

    private static final String SQL_GET_ARTICLE_MODELS =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum from " +
                    "tbl_template where columnID = ? and IsArticle = 1";

    public List getArticleModels(int ColumnID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Model model;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_MODELS);
            pstmt.setInt(1, ColumnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                model = load(rs);
                list.add(model);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GETColModels =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded from " +
                    "tbl_template where columnID = ?";

    public List getModels(int ColumnID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        List list = new ArrayList();
        Model model;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETColModels);
            pstmt.setInt(1, ColumnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                model = load(rs);
                list.add(model);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }

    private static final String SQL_GETArticleModels =
            "select count(id) from tbl_template where columnid = ? and isarticle=1";

    public boolean hasArticcleModel(int columnID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;
        boolean hasflag = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETArticleModels);
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

            if (count > 0)
                hasflag = true;
            else
                hasflag = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return hasflag;
    }

    private static final String UpdateModelPubFlag = "UPDATE tbl_template SET  status=? WHERE ID=?";

    public void updatePubFlag(Model model) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(UpdateModelPubFlag);

                //修改文章发布状态信息
                pstmt.setInt(1, model.getPubFlag());
                pstmt.setInt(2, model.getID());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new ModelException("Database exception: update article failed.");
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            throw new ModelException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_HAS_SAME_MODELNAME =
            "SELECT ID FROM TBL_Template WHERE siteid=? and ColumnID = ? AND TemplateName = ?";

    public boolean hasSameModelName(int siteid,int columnID, String templateName) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean isExit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_HAS_SAME_MODELNAME);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnID);
            pstmt.setString(3, templateName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isExit = true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return isExit;
    }

    private static final String SQL_HAS_SAME_MODELNAME_FOR_PROGRAM =
            "SELECT ID FROM TBL_Template WHERE siteid=? and ColumnID = ? and id!=? and TemplateName = ?";

    public boolean hasSameModelNameForProgram(int siteid,int columnID,int id, String templateName) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean isExit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_HAS_SAME_MODELNAME_FOR_PROGRAM);
            pstmt.setInt(1, siteid);
            pstmt.setInt(2, columnID);
            pstmt.setInt(3, id);
            pstmt.setString(4, templateName);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                isExit = true;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {

                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return isExit;
    }
    public List getSiteidModel(int siteid)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        Model model;
        try{
            con=cpool.getConnection();
            String sql="select *from tbl_template where siteid="+siteid+" and isarticle=2";
            //System.out.println("sql="+sql);
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                model = load(res);
                list.add(model);
            }
            res.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return list;
    }

    Model load(ResultSet rs) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("ID"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            model.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
            model.setCreatedate(rs.getTimestamp("Createdate"));
            model.setLastupdated(rs.getTimestamp("Lastupdated"));
            model.setEditor(rs.getString("Editor"));
            model.setDefaultTemplate(rs.getInt("defaultTemplate"));
            model.setPubFlag(rs.getInt("status"));
            model.setLockstatus(rs.getInt("lockstatus"));
            model.setLockEditor(rs.getString("lockeditor"));
            model.setChineseName(rs.getString("ChName"));
            model.setTemplateName(rs.getString("TemplateName"));
            model.setReferModelID(rs.getInt("ReferModelID"));
            model.setRelatedColumnIDs(rs.getString("RelatedColumnID"));
            model.setIncluded(rs.getInt("isIncluded"));
            model.setSiteID(rs.getInt("siteid"));
            model.setTempnum(rs.getInt("tempnum"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    Model loadNoContent(ResultSet rs) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("ID"));
            model.setSiteID(rs.getInt("siteid"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            model.setLastupdated(rs.getTimestamp("Lastupdated"));
            model.setEditor(rs.getString("Editor"));
            model.setDefaultTemplate(rs.getInt("defaultTemplate"));
            model.setLockstatus(rs.getInt("lockstatus"));
            model.setLockEditor(rs.getString("lockeditor"));
            model.setChineseName(rs.getString("ChName"));
            model.setTemplateName(rs.getString("TemplateName"));
            model.setReferModelID(rs.getInt("ReferModelID"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    Model loadForAutoPub(ResultSet rs) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("ID"));
            model.setSiteID(rs.getInt("siteid"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            model.setChineseName(rs.getString("ChName"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    //add by wangjian 2009-10-14
    public List getIncludeFileMaintitleString(String ids) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List articleList = new ArrayList();
        String SQL_GET_ARTICLE_MAINTITLE = "SELECT ChName FROM tbl_template WHERE id = ?";

        try {
            conn = cpool.getConnection();
            String aids[] = ids.split(",");
            pstmt = conn.prepareStatement(SQL_GET_ARTICLE_MAINTITLE);
            for (int i = 0; i < aids.length; i++) {
                pstmt.setInt(1, Integer.parseInt(aids[i]));
                rs = pstmt.executeQuery();
                if (rs.next()) articleList.add(aids[i] + "|" + rs.getString(1));
                rs.close();
            }
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return articleList;
    }
    //获得栏目下的专题模板 by wangjian 2009-10-14
    private static String SQL_INLUCDE_MODELS = "select ID,siteid,ColumnID,IsArticle,Lastupdated,Editor,defaultTemplate,status,lockstatus,lockeditor," +
            "ChName,TemplateName,ReferModelID from tbl_template where columnid = ? and IsArticle = 3 order by LastUpdated Desc";

    public List getIncludeModels(int ColumnID, int startIndex, int numResults) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        Model model;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_INLUCDE_MODELS);
            // System.out.println("SQL_INLUCDE_MODELS="+SQL_INLUCDE_MODELS);
            pstmt.setInt(1, ColumnID);
            rs = pstmt.executeQuery();
            for (int i = 0; i < startIndex; i++) {
                rs.next();
            }
            for (int i = 0; i < numResults; i++) {
                if (rs.next()) {
                    model = loadNoContent(rs);
                    list.add(model);
                } else {
                    break;
                }
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return list;
    }
    //add bye wangjian 2009-10-14
    public int getIncludeModelsNum(int ColumnID) throws ModelException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int num = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(id) from tbl_template where columnid = ? and IsArticle = 3");
            pstmt.setInt(1, ColumnID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);

                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return num;
    }
    //判断是否有文章摸版
    public Model getArticleModel(int columnid)
    {
        Model model=null;
        Connection  con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select * from tbl_template where columnid = ? and IsArticle = 1 order by DEFAULTTEMPLATE ,id desc";
            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,columnid);
            res=pstmt.executeQuery();

            if(res.next())
            {
                model=load(res);
            }
            res.close();
            pstmt.close();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return model;
    }

    public void updateModelContent(String content,int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_template set content=? where id=?");

            //修改文章发布状态信息
            DBUtil.setBigString(cpool.getType(), pstmt, 1, content);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }
}
