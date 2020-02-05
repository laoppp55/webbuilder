package com.bizwink.cms.publish;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.sql.*;
import java.util.regex.*;

import com.bizwink.cms.articleListmanager.IArticleListManager;
import com.bizwink.cms.articleListmanager.articleListPeer;
import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.markManager.markException;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.processTag.*;
import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.xml.*;
import com.bizwink.net.ftp.*;
import com.bizwink.net.sftp.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.cms.refers.IRefersManager;
import com.bizwink.cms.refers.RefersPeer;
import com.bizwink.cms.markManager.IMarkManager;
import com.bizwink.cms.markManager.markPeer;
import com.bizwink.cms.markManager.mark;
import com.bizwink.program.IProgramManager;
import com.bizwink.program.ProgramPeer;
import com.bizwink.program.Program;
import com.bizwink.program.pageOfProgram;
import info.monitorenter.cpdetector.io.*;

public class PublishPeer implements IPublishManager {
    PoolServer cpool;

    public PublishPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public PoolServer getPool() {
        return cpool;
    }

    public static IPublishManager getInstance() {
        return CmsServer.getInstance().getFactory().getPublishManager();
    }

    public int generatePage(String resultStr, String filename, String username, int siteid, String sitename, String fileDir, int big5flag, int languageType) {
        int errcode;
        String big5dir;
        String big5filename;

        try {
            int posi = filename.lastIndexOf(File.separator);
            String tempFilename = filename.substring(posi + 1);
            String dirname = filename.substring(0, posi);
            File dir = new File(dirname);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (cpool.getLanguage().equals("chinese")) {
                if (cpool.getType().equals("mssql")) {
                    resultStr = StringUtil.gb2iso4View(resultStr);
                    if (languageType == 2) resultStr = new String(resultStr.getBytes("Shift_JIS"), "GBK");
                } else if (cpool.getType().equals("oracle")) {
                    if (cpool.getOStype().equals("unix"))
                        resultStr = StringUtil.gb2isoindb(resultStr);     //for linux system
                }
            }

            //替换本地连接
            resultStr = StringUtil.replace(resultStr, "/webbuilder/sites/" + sitename + "/", "/");
            resultStr = StringUtil.replace(resultStr, "sites/" + sitename + "/", "/");

            //替换远程连接
            resultStr = StringUtil.replace(resultStr, "/webbuilder/sites/", "http://");
            resultStr = StringUtil.replace(resultStr, "sites/", "http://");

            //对于jsp文件，修改shtml的文件包含格式为jsp的文件包含格式
            if (filename.indexOf(".jsp") != -1) {
                resultStr = "<%@page contentType=\"text/html;charset=GBK\"%>\r\n" + resultStr;
                resultStr = convertShtmlIncludeFormat2JSPIncludeFormt(sitename, resultStr);
            }
            String diansitename = sitename;
            diansitename = diansitename.replaceAll("_", ".");
            resultStr = StringUtil.replace(resultStr, "[%%sitename%%]", diansitename);

            ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
            int encoding = siteMgr.getSiteEncoding(siteid);

            if (encoding == 1) {            //UTF-8页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 2) {     //GB2312页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "gb2312");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 3) {     //GBK页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "GBK");
                out.write(resultStr);
                out.flush();
                out.close();
            } else {                        //默认页面编码
                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(resultStr);
                pw.close();
            }

            errcode = publish(username, filename, siteid, fileDir, 0);

            if (big5flag == 1 && errcode == 0) {
                posi = dirname.indexOf(sitename);
                big5dir = dirname.substring(0, posi + sitename.length()) + File.separator + "big5" + fileDir;
                dir = new File(big5dir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                big5filename = big5dir + tempFilename;

                //转换为BIG5内容并向WWW服务器上发布该文件
                Convert convertor = new Convert();
                errcode = convertor.convertString(resultStr, big5filename, 0, 3, sitename);
                //convertor.convertFile(filename,big5filename,0,3,sitename);
                //big5ResultStr = convertor.convertString(buffer.toString(),0,3);
                //EncodingUtil encoding = new EncodingUtil().getInstance();
                //big5ResultStr = encoding.convertString(buffer.toString(),0,3);
                //pw.write(big5ResultStr);
                //pw.close();
                if (errcode == 0)
                    errcode = publish(username, big5filename, siteid, File.separator + "big5" + fileDir, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            errcode = -2;
        } catch (SiteInfoException siteexp) {
            siteexp.printStackTrace();
            errcode = -3;
        }

        return errcode;
    }

    //------wangjian 2009 判断共享
    public int generatePage(String resultStr, String filename, String username, int siteid, int samsiteid, String sitename, String fileDir, int big5flag, int languageType) {
        int errcode=0;
        String big5dir=null;
        String big5filename=null;

        try {
            int posi = filename.lastIndexOf(File.separator);
            String tempFilename = filename.substring(posi + 1);
            String dirname = filename.substring(0, posi);
            File dir = new File(dirname);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (cpool.getLanguage().equals("chinese")) {
                if (cpool.getType().equals("mssql")) {
                    resultStr = StringUtil.gb2iso4View(resultStr);
                    if (languageType == 2) resultStr = new String(resultStr.getBytes("Shift_JIS"), "GBK");
                } else if (cpool.getType().equals("oracle")) {
                    if (cpool.getOStype().equals("unix"))
                        resultStr = StringUtil.gb2isoindb(resultStr);     //for linux system
                }
            }

            //替换本地连接
            resultStr = StringUtil.replace(resultStr, "/webbuilder/sites/" + sitename + "/", "/");
            resultStr = StringUtil.replace(resultStr, "sites/" + sitename + "/", "/");

            //替换远程连接
            resultStr = StringUtil.replace(resultStr, "/webbuilder/sites/", "http://");
            resultStr = StringUtil.replace(resultStr, "sites/", "http://");

            String dotsitename = sitename;
            dotsitename = dotsitename.replaceAll("_", ".");
            resultStr = StringUtil.replace(resultStr, "[%%sitename%%]", dotsitename);

            //对于jsp文件，修改shtml的文件包含格式为jsp的文件包含格式
            if (filename.indexOf(".jsp") != -1) {
                resultStr = "<%@page contentType=\"text/html;charset=GBK\"%>\r\n" + resultStr;
                resultStr = convertShtmlIncludeFormat2JSPIncludeFormt(sitename, resultStr);
            }

            //如果该网站
            int encoding = 1;
            ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
            if (samsiteid > 0) {
                String samsitename = "";
                String dot_samsitename = "";
                try {
                    SiteInfo siteinfo = (SiteInfo) siteMgr.getSiteInfo(samsiteid);
                    dot_samsitename = siteinfo.getDomainName();
                    samsitename = StringUtil.replace(dot_samsitename, ".", "_");
                    resultStr = StringUtil.replace(resultStr, samsitename, dot_samsitename);
                } catch (Exception e) {
                    System.out.println("" + e.toString());
                }

                if (resultStr.indexOf(samsitename + "/_prog/") > -1) {
                    resultStr = resultStr.replaceAll(samsitename + "/_prog/", sitename + "/_prog/");
                }
                if (resultStr.indexOf("shoppingcart('" + samsitename + "');") > -1) {
                    resultStr = StringUtil.replace(resultStr, "shoppingcart('" + samsitename + "');", "shoppingcart('" + sitename + "');");
                }

                if (resultStr.indexOf("checkAddress('" + samsitename + "');") > -1) {
                    resultStr = StringUtil.replace(resultStr, "checkAddress('" + samsitename + "');", "checkAddress('" + sitename + "','shoppingcar.jsp');");
                }
                if (resultStr.indexOf("<%@ include file=\"/include") > -1) {
                    resultStr = StringUtil.replace(resultStr, "<%@ include file=\"/include", "<%@ include file=\"/" + sitename + "/include");
                }

                //处理CSS路径和js的路径
                //resultStr = format_CSS_and_JS(resultStr, dot_samsitename, dotsitename);
            }

            try{
                encoding = siteMgr.getSiteEncoding(siteid);
            }  catch (Exception e) {
                System.out.println("" + e.toString());
            }

            if (encoding == 1) {            //UTF-8页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 2) {     //GB2312页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "gb2312");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 3) {     //GBK页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "GBK");
                out.write(resultStr);
                out.flush();
                out.close();
            } else {                        //默认页面编码
                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(resultStr);
                pw.close();
            }

            errcode = publish(username, filename, siteid, fileDir, 0);

            if (big5flag == 1 && errcode == 0) {
                posi = dirname.indexOf(sitename);
                big5dir = dirname.substring(0, posi + sitename.length()) + File.separator + "big5" + fileDir;
                dir = new File(big5dir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                big5filename = big5dir + tempFilename;

                //转换为BIG5内容并向WWW服务器上发布该文件
                Convert convertor = new Convert();
                errcode = convertor.convertString(resultStr, big5filename, 0, 3, sitename);
                if (errcode == 0)
                    errcode = publish(username, big5filename, siteid, File.separator + "big5" + fileDir, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            errcode = -2;
        }

        return errcode;
    }

    //--------

    public int generatePage(String resultStr, String filename, String username, int siteid, String sitename, String fileDir, int big5flag, int languageType, boolean isown, int columnid, int articleid) {
        int errcode;
        String big5dir;
        String big5filename;

        try {
            int posi = filename.lastIndexOf(File.separator);
            String tempFilename = filename.substring(posi + 1);
            String dirname = filename.substring(0, posi);
            File dir = new File(dirname);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            if (cpool.getLanguage().equals("chinese")) {
                if (cpool.getType().equals("mssql")) {
                    resultStr = StringUtil.gb2iso4View(resultStr);
                    if (languageType == 2) resultStr = new String(resultStr.getBytes("Shift_JIS"), "GBK");
                } else if (cpool.getType().equals("oracle")) {
                    if (cpool.getOStype().equals("unix"))
                        resultStr = StringUtil.gb2isoindb(resultStr);     //for linux system
                }
            }

            IColumnManager columnMGr = ColumnPeer.getInstance();
            resultStr = StringUtil.replace(resultStr, "/webbuilder/sites/" + sitename + "/", "/");
            resultStr = StringUtil.replace(resultStr, "sites/" + sitename + "/", "/");

            //对于jsp文件，修改shtml的文件包含格式为jsp的文件包含格式
            if (filename.indexOf(".jsp") != -1) {
                resultStr = "<%@page contentType=\"text/html;charset=GBK\"%>\r\n" + resultStr;
                resultStr = convertShtmlIncludeFormat2JSPIncludeFormt(sitename, resultStr);
            }

            String diansitename = sitename;
            diansitename = diansitename.replaceAll("_", ".");
            resultStr = StringUtil.replace(resultStr, "[%%sitename%%]", diansitename);

            ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
            int encoding = siteMgr.getSiteEncoding(siteid);

            if (encoding == 1) {            //UTF-8页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "utf-8");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 2) {     //GB2312页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "gb2312");
                out.write(resultStr);
                out.flush();
                out.close();
            } else if (encoding == 3) {     //GBK页面编码
                OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filename), "GBK");
                out.write(resultStr);
                out.flush();
                out.close();
            } else {                        //默认页面编码
                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(resultStr);
                pw.close();
            }

            errcode = publish(username, filename, siteid, fileDir, 0);

            if (big5flag == 1 && errcode == 0) {
                posi = dirname.indexOf(sitename);
                big5dir = dirname.substring(0, posi + sitename.length()) + File.separator + "big5" + fileDir;
                dir = new File(big5dir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                big5filename = big5dir + tempFilename;

                //转换为BIG5内容并向WWW服务器上发布该文件
                Convert convertor = new Convert();
                errcode = convertor.convertString(resultStr, big5filename, 0, 3, sitename);
                if (errcode == 0)
                    errcode = publish(username, big5filename, siteid, File.separator + "big5" + fileDir, 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
            errcode = -2;
        } catch (SiteInfoException siteexp) {
            siteexp.printStackTrace();
            errcode = -3;
        }

        return errcode;
    }

    private String convertShtmlIncludeFormat2JSPIncludeFormt(String sitename, String content) {
        int posi = 0;
        StringBuffer buffer = new StringBuffer();

        try {
            Pattern p = Pattern.compile("<!--#include[^<>]*-->", Pattern.CASE_INSENSITIVE);
            String[] buf = p.split(content);
            String tag[] = new String[buf.length - 1];
            for (int i = 0; i < buf.length - 1; i++) {
                content = content.substring(buf[i].length());
                posi = content.indexOf(">");
                tag[i] = content.substring(0, posi + 1);
                content = content.substring(tag[i].length());
                tag[i] = formatFile_Str(tag[i], sitename);
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

    private String format_CSS_and_JS(String resultStr, String dot_samsitename, String dotsitename) {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = resultStr;
        int posi;

        try {
            //处理javascript文件路径
            Pattern p = Pattern.compile("<(\\s*)script([^<>]*)src\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = StringUtil.replace(tag[i], "http://" + dot_samsitename, "");
            }

            for (int i = 0; i < tag.length; i++) {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理CSS路径
            tempBuf = buffer.toString();
            buffer = new StringBuffer();
            p = Pattern.compile("<(\\s*)link([^<>]*)href\\s*=\\s*[^<>]*>", Pattern.CASE_INSENSITIVE);
            buf = p.split(tempBuf);
            tag = new String[buf.length - 1];

            for (int i = 0; i < buf.length - 1; i++) {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0, posi + 1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = StringUtil.replace(tag[i], "http://" + dot_samsitename, "");
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


    private String formatFile_Str(String str, String sitename) {
        String buf;
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("=");
        buf = buf.substring(posi + 1).trim();
        posi = buf.indexOf("-->");

        if (posi != -1)
            fileName = buf.substring(0, posi);
        else
            fileName = buf.substring(0, buf.length() - 3);

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        //用于coosite.com的格式
        if (fileName.startsWith("/"))
            return "<%@include file=\"/" + sitename + fileName + "\" %>";
        else
            return "<%@include file=\"/" + sitename + "/" + fileName + "\" %>";


        //用于独立网站的格式
        /*if (fileName.startsWith("/"))
            return "<%@include file=\""+fileName + "\" %>";
        else
            return "<%@include file=\"/" + fileName + "\" %>";
         */
    }

    private String filterHref(String content, String markname) {
        try {
            Pattern p = Pattern.compile("<a(\\s*)[^>]*" + markname + "[^>]*>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(content);
            if (m.find()) {
                int start = m.start();
                int end = m.end();
                content = content.substring(0, start) + content.substring(end);

                //去掉</a>
                String right = content.substring(start);
                int posi = right.toLowerCase().indexOf("</a>");
                right = right.substring(0, posi) + right.substring(posi + 4);
                content = content.substring(0, start) + right;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    //type=1 文章分页  2文章列表分页
    public String generateNavBarByDesc(int type, int navbarID, int pageNum, int pno, int num, Column column, String filename) {
        String str = "";
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        String headname = filename;
        if (type == 1) filename = filename + "-";

        try {
            str = viewfileMgr.getAViewFile(navbarID).getContent();
            if (str != null && str.trim().length() > 0) {
                if (pageNum > 1) {
                    getNavBarItemStyle itemStyle = new getNavBarItemStyle();

                    //分析当前页位置,去掉连接
                    if (pno == pageNum) {                                    //当前是第一页
                        str = itemStyle.formatTheStyle(str, 1);
                    } else if (pno == 1) {                                  //当前是最后一页
                        str = itemStyle.formatTheStyle(str, 2);
                    } else {                                                //当前页是其他页面
                        str = itemStyle.formatTheStyle(str, 3);
                    }

                    String ext = column.getExtname();
                    String dirName = column.getDirName();

                    if (type == 1) {
                        IArticleManager articleMgr = ArticlePeer.getInstance();
                        Article article = new Article();
                        try {
                            article = articleMgr.getArticle(Integer.parseInt(headname));
                        } catch (ArticleException e) {
                            e.printStackTrace();
                        }
                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                        createdate_path = createdate_path.replaceAll("-", "") + "/";
                        dirName = dirName + createdate_path;
                    }

                    str = StringUtil.replace(str, "<%%NUM%%>", Integer.toString(num));
                    str = StringUtil.replace(str, "<%%PAGENUM%%>", Integer.toString(pageNum));
                    str = StringUtil.replace(str, "<%%CURRENTPAGE%%>", String.valueOf(pno + 1));

                    if (pno == 0) {
                        str = StringUtil.replace(str, "<%%HEAD%%>", dirName + headname + "." + ext);
                        str = StringUtil.replace(str, "<%%PREVIOUS%%>", dirName + headname + "." + ext);
                        str = StringUtil.replace(str, "<%%NEXT%%>", dirName + filename + (pageNum - 1) + "." + ext);
                        str = StringUtil.replace(str, "<%%BOTTOM%%>", dirName + filename + "1." + ext);
                    } else if (pno > 0) {
                        str = StringUtil.replace(str, "<%%HEAD%%>", dirName + headname + "." + ext);
                        str = StringUtil.replace(str, "<%%PREVIOUS%%>", dirName + (((pno - pageNum) == 0) ? headname : filename + Integer.toString(pageNum - pno + 1)) + "." + ext);
                        str = StringUtil.replace(str, "<%%NEXT%%>", dirName + filename + (((pageNum - pno - 1) < pageNum) ? Integer.toString(pageNum - pno + 1) : Integer.toString(pageNum - 1)) + "." + ext);
                        str = StringUtil.replace(str, "<%%BOTTOM%%>", dirName + filename + "1." + ext);
                    }

                    if (str.indexOf("<%%SELECT%%>") > -1) {
                        StringBuffer buf = new StringBuffer();

                        buf.append("<form name=form>");
                        buf.append("<select name=turnPage size=1 onChange=\"window.location=this.form.turnPage.value;\">");

                        int i = 1;
                        while (i <= pageNum) {
                            buf.append("<option value=" + dirName);
                            buf.append((i - 1 == 0) ? headname : filename + Integer.toString(i - 1));
                            buf.append(".").append(ext);
                            buf.append((i - 1 == pno) ? " selected" : "");
                            buf.append(">");
                            //buf.append(StringUtil.gb2iso("第"));  //for sqlserver
                            buf.append(i);
                            //buf.append(StringUtil.gb2iso("页"));  //for sqlserver
                            buf.append("</option>");
                            i++;
                        }

                        buf.append("</select>");
                        buf.append("</form>");

                        str = StringUtil.replace(str, "<%%SELECT%%>", buf.toString());
                    }

                    if (str.indexOf("<%%NUMBER%%>") > -1) {
                        StringBuffer buf = new StringBuffer();

                        buf.append("[");

                        // Print out a "<<" if necessary
                        if (pno > 0) {
                            buf.append("<a href=\"" + dirName);
                            buf.append((pageNum - pno == 0) ? headname : filename + Integer.toString(pno - 1));
                            buf.append(".").append(ext);
                            buf.append("\">&lt;&lt;</a>&nbsp;");
                        }

                        int currentPage = pageNum - pno + 1;
                        int lo = currentPage - 3;
                        if (lo <= 0) {
                            lo = 1;
                        }
                        int hi = currentPage + 5;

                        // Add a link back to the first page
                        if (lo > 1) {
                            buf.append("<a href=\"" + dirName + headname);
                            buf.append(".").append(ext);
                            buf.append("\">1").append("</a> <b>...</b>");
                        }

                        // Print out low page numbers
                        while (lo < currentPage) {
                            buf.append("<a href=\"" + dirName).append((lo - 1 == 0) ? headname : filename + Integer.toString(pageNum - lo + 1));
                            buf.append(".").append(ext);
                            buf.append("\">");
                            buf.append(lo).append("</a>&nbsp;");
                            lo++;
                        }

                        // Current page
                        buf.append("<b>");
                        buf.append(currentPage);
                        buf.append("</b>&nbsp;");

                        // Print out high page numbers
                        while ((currentPage < hi) && (currentPage < pageNum)) {
                            buf.append("<a href=\"" + dirName + filename).append(pageNum - currentPage);
                            buf.append(".").append(ext);
                            buf.append("\">");
                            buf.append(currentPage + 1).append("</a>&nbsp;");
                            currentPage++;
                        }

                        // put ending page at the end, ie: " 2 3 4 ... 33"
                        if (pageNum > currentPage) {
                            buf.append("<b>...</b><a href=\"" + dirName);
                            buf.append((pageNum - 1 == 0) ? headname : filename + Integer.toString(1));
                            buf.append(".").append(ext);
                            buf.append("\">");
                            buf.append(pageNum).append("</a>&nbsp;");
                        }

                        if (pageNum > pno + 1) {
                            buf.append("<a href=\"" + dirName + filename).append(pno - 1);
                            buf.append(".").append(ext);
                            buf.append("\">&gt;&gt;</a>");
                        }
                        buf.append("] ");

                        str = StringUtil.replace(str, "<%%NUMBER%%>", buf.toString());
                    }

                    if (str.indexOf("<%%GOTO%%>") > -1) {
                        StringBuffer buf = new StringBuffer();
                        buf.append("<script language=javascript>");
                        buf.append("function keycheck(frm)");
                        buf.append("{");
                        buf.append("  if (window.event.keyCode == 13)");
                        buf.append("  {");
                        buf.append("    var page = frm.cmspage.value;");
                        buf.append("    if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > " + pageNum + ")))");
                        buf.append("    {");
                        buf.append("      alert('" + "请输入正确的页码！页码范围为：" + "1 - " + pageNum + "');");
                        buf.append("    }else{");
                        buf.append("      if (page == '1')");
                        buf.append("        document.location = '" + dirName + headname + "." + ext + "';");
                        buf.append("      else");
                        buf.append("        document.location = '" + dirName + filename + "'+parseInt(parseInt(page)-1)+'." + ext + "';");
                        buf.append("    }");
                        buf.append("  }");
                        buf.append("}\n\n");

                        buf.append("function cmscheck(frm)");
                        buf.append("{");
                        buf.append("var page = frm.cmspage.value;");
                        buf.append("if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > " + pageNum + ")))");
                        buf.append("{");
                        buf.append("  alert('" + "请输入正确的页码！页码范围为：" + "1 - " + pageNum + "');");
                        buf.append("}");
                        buf.append("else");
                        buf.append("{");
                        buf.append("  if (page == '1')");
                        buf.append("    document.location = '" + dirName + headname + "." + ext + "';");
                        buf.append("  else");
                        buf.append("    document.location = '" + dirName + filename + "'+parseInt(parseInt(page)-1)+'." + ext + "';");
                        buf.append("}");
                        buf.append("}");
                        buf.append("</script>");
                        str = str + buf.toString();

                        buf = new StringBuffer();
                        buf.append("cmscheck(this.form);");
                        str = StringUtil.replace(str, "<%%GOTO%%>", buf.toString());
                    }
                } else {
                    str = "";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    //type=1 文章分页  2文章列表分页
    //navbarID：导航棒样式文件ID
    //pageNum：分页页面的总数量
    //pno：当前生成的页
    //num：参与分页的文章总数量
    //column：当前栏目对象
    //filename：默认index，模板的英文名称
    //modeltype：模板类型
    public String generateNavBar(int type, int navbarID, int pageNum, int pno, int num, Column column, String filename,int modeltype) {
        String str = "";
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        String headname = filename;
        String[] navbar_fields = null;
        if (type == 1) filename = filename + "-";

        try {
            str = viewfileMgr.getAViewFile(navbarID).getContent();
            navbar_fields = str.split("\r");
            String current_page_style = null;
            String other_page_style = null;
            if (navbar_fields != null) {
                for(int ii=0; ii<navbar_fields.length; ii++) {
                    if (navbar_fields[ii].indexOf("<%%CURRENTPAGENOSTYLE%%>")>-1) {
                        current_page_style = navbar_fields[ii].replace("\r","");
                    }
                    if (navbar_fields[ii].indexOf("<%%OTHERPAGENOSTYLE%%>")>-1) {
                        other_page_style = navbar_fields[ii].replace("\r","");
                    }
                }
                if (current_page_style!=null) str = str.replace(current_page_style,"");   //剔除了当前页显示风格设置
                if (other_page_style!=null) str = str.replace(other_page_style,"");    //剔除了其他页显示风格设置
            }

            if (str != null && str.trim().length() > 0) {
                getNavBarItemStyle itemStyle = new getNavBarItemStyle();
                //分析当前页位置,去掉连接
                if (pno == 0) {                                           //当前是第一页
                    str = itemStyle.formatTheStyle(str, 1);
                } else if (pno == pageNum - 1) {                        //当前是最后一页
                    str = itemStyle.formatTheStyle(str, 2);
                } else {                                                //当前页是其他页面
                    str = itemStyle.formatTheStyle(str, 3);
                }

                String ext = column.getExtname();
                String dirName = column.getDirName();

                //如果是文章，在dirName中加入文章的创建时间路径 Modified by Eric 2007-9-6 14:47
                if (type == 1) {
                    IArticleManager articleMgr = ArticlePeer.getInstance();
                    Article article = new Article();
                    try {
                        article = articleMgr.getArticle(Integer.parseInt(headname));
                    } catch (ArticleException e) {
                        e.printStackTrace();
                    }
                    String createdate_path = article.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "") + "/";
                    dirName = dirName + createdate_path;
                }

                str = StringUtil.replace(str, "<%%NUM%%>", Integer.toString(num));
                str = StringUtil.replace(str, "<%%PAGENUM%%>", Integer.toString(pageNum));
                str = StringUtil.replace(str, "<%%CURRENTPAGENOSTYLE%%>", String.valueOf(pno + 1));

                if (pno == 0) {
                    str = StringUtil.replace(str, "<%%HEAD%%>", dirName + headname + "." + ext);
                    str = StringUtil.replace(str, "<%%PREVIOUS%%>", dirName + headname + "." + ext);
                    str = StringUtil.replace(str, "<%%NEXT%%>", dirName + filename + "1." + ext);
                    str = StringUtil.replace(str, "<%%BOTTOM%%>", dirName + filename + (pageNum - 1) + "." + ext);
                } else if (pno > 0) {
                    str = StringUtil.replace(str, "<%%HEAD%%>", dirName + headname + "." + ext);
                    str = StringUtil.replace(str, "<%%PREVIOUS%%>", dirName + (((pno - 1) == 0) ? headname : filename + Integer.toString(pno - 1)) + "." + ext);
                    str = StringUtil.replace(str, "<%%NEXT%%>", dirName + filename + (((pno + 1) < pageNum) ? Integer.toString(pno + 1) : Integer.toString(pageNum - 1)) + "." + ext);
                    str = StringUtil.replace(str, "<%%BOTTOM%%>", dirName + filename + (pageNum - 1) + "." + ext);
                }

                if (str.indexOf("<%%SELECT%%>") > -1) {
                    StringBuffer buf = new StringBuffer();

                    buf.append("<form name=form>");
                    buf.append("<select name=turnPage size=1 onChange=\"window.location=this.form.turnPage.value;\">");

                    int i = 1;
                    while (i <= pageNum) {
                        buf.append("<option value=" + dirName);
                        buf.append((i - 1 == 0) ? headname : filename + Integer.toString(i - 1));
                        buf.append(".").append(ext);
                        buf.append((i - 1 == pno) ? " selected" : "");
                        buf.append(">");
                        //buf.append(StringUtil.gb2iso("第"));  //for sqlserver
                        buf.append(i);
                        //buf.append(StringUtil.gb2iso("页"));  //for sqlserver
                        buf.append("</option>");
                        i++;
                    }

                    buf.append("</select>");
                    buf.append("</form>");

                    str = StringUtil.replace(str, "<%%SELECT%%>", buf.toString());
                }

                int seperate_number_style = str.indexOf(">||<");                  //样式文件中是否带有数字导航分隔符||
                int number_flag = str.indexOf("<%%NUMBER%%>");           //样式文件中是否有连续数字导航标志符
                int number3_flag = str.indexOf("<%%NUMBER3%%>");         //样式文件中是否有三段式数字导航标志符

                if (number3_flag > -1) {
                    StringBuffer buf = new StringBuffer();
                    buf.append("[");

                    // Print out a "<<" if necessary
                    if (pno > 0) {
                        buf.append("<a href=\"" + dirName);
                        buf.append((pno - 1 == 0) ? headname : filename + Integer.toString(pno - 1));
                        buf.append(".").append(ext);
                        buf.append("\">&lt;&lt;</a>&nbsp;");
                    }

                    int currentPage = pno + 1;
                    int lo = currentPage - 1;
                    if (lo <= 0) {
                        lo = 1;
                    }
                    int hi = currentPage + 1;

                    //增加返回到第一页的链接
                    if (lo > 1) {
                        buf.append("<a href=\"" + dirName + headname);
                        buf.append(".").append(ext);
                        buf.append("\">1").append("</a> <b>...</b>");
                    }

                    // Print out low page numbers
                    while (lo < currentPage) {
                        buf.append("<a href=\"" + dirName).append((lo - 1 == 0) ? headname : filename + Integer.toString(lo - 1));
                        buf.append(".").append(ext);
                        buf.append("\">");
                        buf.append(lo).append("</a>&nbsp;");
                        lo++;
                    }

                    // Current page
                    buf.append("<b>");
                    buf.append(currentPage);
                    buf.append("</b>&nbsp;");

                    // Print out high page numbers
                    while ((currentPage < hi) && (currentPage < pageNum)) {
                        buf.append("<a href=\"" + dirName + filename).append(currentPage);
                        buf.append(".").append(ext);
                        buf.append("\">");
                        buf.append(currentPage + 1).append("</a>&nbsp;");
                        currentPage++;
                    }

                    // put ending page at the end, ie: " 2 3 4 ... 33"
                    if (pageNum > currentPage) {
                        buf.append("<b>...</b><a href=\"" + dirName);
                        buf.append((pageNum - 1 == 0) ? headname : filename + Integer.toString(pageNum - 1));
                        buf.append(".").append(ext);
                        buf.append("\">");
                        buf.append(pageNum).append("</a>&nbsp;");
                    }

                    if (pageNum > pno + 1) {
                        buf.append("<a href=\"" + dirName + filename).append(pno + 1);
                        buf.append(".").append(ext);
                        buf.append("\">&gt;&gt;</a>");
                    }
                    buf.append("] ");
                    str = StringUtil.replace(str, "<%%NUMBER%%>", buf.toString());
                } else if (number_flag > -1){
                    StringBuffer buf = new StringBuffer();
                    //从PNO前推4页，向后推5页，每页显示10个分页
                    //从PNO前推4页获取开始页，保证开始页大于等于0，如果当前页前面只有3页，就只向前推3页，如果当前页前面有2页，就只向前推2页
                    int start_page_no = pno;
                    for(int ii=0;ii<2;ii++) {
                        start_page_no = start_page_no -1;
                        if (start_page_no>0)
                            continue;
                        else
                            break;
                    }
                    if (start_page_no<0) start_page_no=0;

                    //从PNO后推5页获取终止页，保证终止页小于等于pageNum，如果当前页后面只有3页，就只向后推3页，如果当前页后面有2页，就只向后推2页
                    int end_page_no = start_page_no;
                    for(int ii=start_page_no;ii<start_page_no+5;ii++) {
                        end_page_no = end_page_no + 1;
                        if (end_page_no<pageNum)
                            continue;
                        else
                            break;
                    }
                    if (end_page_no>pageNum) end_page_no=pageNum;

                    //创建本页的导航列表
                    String tbuf = null;
                    for(int ii=start_page_no;ii<end_page_no;ii++) {
                        if (ii == pno) {
                            //处理当前页的显示方式
                            tbuf = current_page_style.replace("<%%CURRENTPAGENOSTYLE%%>",String.valueOf(pno+1));
                        } else {
                            //处理其他页的显示方式
                            if (ii==0)
                                tbuf = "<a href=\"" + dirName + filename + "." + ext + "\">" + String.valueOf(ii+1) + "</a>";
                            else
                                tbuf = "<a href=\"" + dirName + filename + String.valueOf(ii) + "." + ext + "\">" + String.valueOf(ii+1) + "</a>";
                            tbuf = other_page_style.replace("<%%OTHERPAGENOSTYLE%%>",tbuf);
                        }
                        buf.append(tbuf);
                    }

                    str = StringUtil.replace(str, "<%%NUMBER%%>", buf.toString());
                } else if (number_flag > -1 && seperate_number_style > -1) {
                    Pattern p = Pattern.compile("\\|\\|", Pattern.CASE_INSENSITIVE);
                    String[] style = p.split(str);
                    StringBuffer buf = new StringBuffer();
                    if (style[0]!=null) {
                        if (pno > 0) {
                            String url = "";
                            if (pno - 1 == 0)
                                url = dirName + headname + "." + ext;
                            else
                                url = dirName + filename + Integer.toString(pno - 1) + "." + ext;
                            style[0] = StringUtil.replace(style[0], "<%%URL%%>", url);
                            style[0] = StringUtil.replace(style[0], "<%%NUMBER%%>", "");
                            buf.append(style[0] + "\r\n");
                        }
                    }

                    int currentPage = pno + 1;
                    int lo = currentPage - 3;
                    if (lo <= 0) {
                        lo = 1;
                    }
                    int hi = currentPage + 3;

                    //增加返回到第一页的链接
                    if (lo > 1) {
                        String url = null;
                        url = dirName + headname + "." + ext;
                        String style_for_no_current_page = style[1];
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%URL%%>", url);
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%NUMBER%%>", ">1");
                        buf.append(style_for_no_current_page + "\r\n");
                    }

                    // Print out low page numbers
                    while (lo < currentPage) {
                        String url = "";
                        if (lo - 1 == 0)
                            url = dirName + headname + "." + ext;
                        else
                            url = dirName + filename + Integer.toString(lo - 1) + "." + ext;
                        String style_for_no_current_page = style[1];
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%URL%%>", url);
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%NUMBER%%>", String.valueOf(lo));
                        buf.append(style_for_no_current_page + "\r\n");
                        lo++;
                    }

                    // Current page
                    String style_for_current_page = style[2];
                    style_for_current_page = StringUtil.replace(style_for_current_page, "<%%URL%%>", "#");
                    style_for_current_page = StringUtil.replace(style_for_current_page, "<%%NUMBER%%>", String.valueOf(currentPage));
                    buf.append(style_for_current_page + "\r\n");

                    // Print out high page numbers
                    while ((currentPage < hi) && (currentPage < pageNum)) {
                        String url = null;
                        url = dirName + filename + currentPage + "." + ext;
                        String style_for_no_current_page = style[1];
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%URL%%>", url);
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%NUMBER%%>", String.valueOf(currentPage + 1));
                        buf.append(style_for_no_current_page + "\r\n");
                        currentPage++;
                    }

                    // put ending page at the end, ie: " 2 3 4 ... 33"
                    if (pageNum > currentPage) {
                        String url = "";
                        if (pageNum - 1 == 0)
                            url = dirName + headname + "." + ext;
                        else
                            url = dirName + filename + Integer.toString(pageNum - 1) + "." + ext;
                        String style_for_no_current_page = style[1];
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%URL%%>", url);
                        style_for_no_current_page = StringUtil.replace(style_for_no_current_page, "<%%NUMBER%%>", String.valueOf(pageNum));
                        buf.append(style_for_no_current_page + "\r\n");
                    }

                    if (style[3]!=null) {
                        if (pageNum > pno + 1) {
                            String url = null;
                            url = dirName + filename + (pno + 1) + "." + ext;
                            style[3] = StringUtil.replace(style[3], "<%%URL%%>", url);
                            style[3] = StringUtil.replace(style[3], "<%%NUMBER%%>", "");
                            buf.append(style[3] + "\r\n");
                        }
                    }
                    str = buf.toString();
                }

                if (str.indexOf("<%%GOTO%%>") > -1) {
                    StringBuffer buf = new StringBuffer();
                    buf.append("<script language=javascript>");
                    buf.append("function keycheck(frm)");
                    buf.append("{");
                    buf.append("  if (window.event.keyCode == 13)");
                    buf.append("  {");
                    buf.append("    var page = frm.cmspage.value;");
                    buf.append("    if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > " + pageNum + ")))");
                    buf.append("    {");
                    buf.append("      alert('" + "请输入正确的页码！页码范围为：" + "1 - " + pageNum + "');");
                    buf.append("    }else{");
                    buf.append("      if (page == '1')");
                    buf.append("        document.location = '" + dirName + headname + "." + ext + "';");

                    buf.append("      else");
                    buf.append("        document.location = '" + dirName + filename + "'+parseInt(parseInt(page)-1)+'." + ext + "';");
                    buf.append("    }");
                    buf.append("  }");
                    buf.append("}\n\n");

                    buf.append("function cmscheck(frm)");
                    buf.append("{");
                    buf.append("var page = frm.cmspage.value;");
                    buf.append("if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > " + pageNum + ")))");
                    buf.append("{");
                    buf.append("  alert('" + "请输入正确的页码！页码范围为：" + "1 - " + pageNum + "');");
                    buf.append("}");
                    buf.append("else");
                    buf.append("{");
                    buf.append("  if (page == '1')");
                    buf.append("    document.location = '" + dirName + headname + "." + ext + "';");
                    buf.append("  else");
                    buf.append("    document.location = '" + dirName + filename + "'+parseInt(parseInt(page)-1)+'." + ext + "';");
                    buf.append("}");
                    buf.append("}");
                    buf.append("</script>");
                    str = str + buf.toString();

                    buf = new StringBuffer();
                    buf.append("cmscheck(this.form);");
                    str = StringUtil.replace(str, "<%%GOTO%%>", buf.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    //多文章模板发布
    private int generateMoreIndex(XMLProperties properties, String appPath, String sitename, String[] artList, String[] tagHTML,
                                  String[] buf, int altagIndex, int columnID, int listType, String username, int siteid, int option, String fname,int modeltype,Article article) {
        int errcode = 0;
        int artNum = 0;
        int navbar = 0;
        String tagName = properties.getName();
        ITagManager tagMgr = TagManager.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            artNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
            navbar = Integer.parseInt(properties.getProperty(tagName.concat(".NAVBAR")));
        } catch (NumberFormatException ignored) {
        }

        Column column = null;
        try {
            column = columnMgr.getColumn(columnID);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        //获得文章创建的时间，生成时间发布路径
        String createdate_path = "";
        if (article != null) {
            createdate_path = article.getCreateDate().toString().substring(0, 10);
            createdate_path = createdate_path.replaceAll("-", "");
        }

        String tempDir = column.getDirName();
        tempDir = StringUtil.replace(tempDir, "/", File.separator) + createdate_path + File.separator;
        String extname = column.getExtname();

        String baseDir = appPath + "sites" + File.separator + sitename;

        String headstr = "";
        String midstr = "";
        String tailstr = "";
        try {
            String datastr = viewfileMgr.getAViewFile(listType).getContent();
            headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
            midstr = datastr.substring(datastr.indexOf("<!--ROW-->") + 10, datastr.lastIndexOf("<!--ROW-->"));
            tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
        } catch (viewFileException e) {
            e.printStackTrace();
        }

        //获得文章列表栏数
        int columnNum = tagMgr.getColumnNum(midstr);                    //文章列数
        int articleCount = artList.length;                              //文章总条数
        int pageNum = (int) Math.ceil((double) articleCount / artNum);  //总行数
        int rowNum = artNum / columnNum;                                //整行数
        int extraNum = artNum % columnNum;                              //余下的文章数
        String arr[] = tagMgr.getColumnString(midstr);
        int lastPageArtNum = articleCount % artNum;
        for (int p = 0; p < pageNum; p++) {
            StringBuffer sb = new StringBuffer();

            if (p == pageNum - 1)        //如果是最后一页
            {
                if (lastPageArtNum > 0) {
                    rowNum = lastPageArtNum / columnNum;
                    extraNum = lastPageArtNum % columnNum;
                }
            }

            for (int i = 0; i < rowNum; i++)     //生成文章列表
            {
                int begin = p * artNum + i * columnNum;
                int end = p * artNum + (i + 1) * columnNum;

                for (int j = begin; j < end; j++)    //按行循环
                {
                    int k = (j - begin) * 2;
                    if (columnNum > 1)
                        sb.append(arr[k]);
                    sb.append(artList[j]);
                }

                if (columnNum > 1)
                    sb.append(arr[columnNum * 2]);
                sb.append("\r\n");
            }

            if (extraNum > 0) {
                int begin = p * artNum + rowNum * columnNum;
                for (int j = begin; j < begin + columnNum; j++) {
                    int k = (j - begin) * 2;
                    sb.append(arr[k]);
                    if (j < begin + extraNum)
                        sb.append(artList[j]);
                    else
                        sb.append("&nbsp;");
                }
                sb.append(arr[columnNum * 2]);
                sb.append("\r\n");
            }

            sb.insert(0, headstr);   //获取文章列表的表头
            sb.append(tailstr);      //为文章列表增加表尾

            //为文章列表增加尾部导航条
            if (navbar > 0)
                sb.append(generateNavBar(0, navbar, pageNum, p, articleCount, column, fname,modeltype));

            for (int j = 0; j < tagHTML.length; j++) {
                if (j == altagIndex)
                    buf[2 * j + 1] = sb.toString();
                else
                    buf[2 * j + 1] = tagHTML[j];
            }

            int languageType = column.getLanguageType();
            String filename;
            if (p == 0)
                filename = baseDir + tempDir + fname + "." + extname;
            else
                filename = baseDir + tempDir + fname + p + "." + extname;

            if (navbar > 0) {
                String resultStr = "";
                for (int i = 0; i < buf.length; i++) resultStr += buf[i];
                //增加文章的头信息，如文章的标题，文章概述，文章关键字、文章作者，发布时间等信息
                if (article != null)
                    resultStr = addArticleHeader(resultStr, article, siteid, 0, 0);
                else
                    resultStr = addColumnPageHeader(resultStr, columnID, siteid);
                errcode = generatePage(resultStr, filename, username, siteid, sitename, tempDir, option, languageType);
            } else if (navbar == 0)        //替换模板中所有NAVBAR标记
            {
                StringBuffer result = new StringBuffer();
                for (int i = 0; i < buf.length; i++) {
                    result.append(buf[i]);
                }

                String str = result.toString();

                int posi;
                if ((posi = str.indexOf("<TAG><NAVBAR>")) > -1 && str.indexOf("</NAVBAR></TAG>") > -1) {
                    String navStr = str.substring(posi, str.indexOf("</NAVBAR></TAG>") + 15);
                    XMLProperties prop = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + navStr);
                    int navType = Integer.parseInt(prop.getProperty(prop.getName()));
                    String nav = generateNavBar(0, navType, pageNum, p, articleCount, column, fname, modeltype);
                    str = StringUtil.replace(str, "<TAG><NAVBAR>" + navType + "</NAVBAR></TAG>", nav);
                }

                if (article != null)
                    str = addArticleHeader(str, article, siteid, 0, 0);
                else
                    str = addColumnPageHeader(str, columnID, siteid);
                errcode = generatePage(str, filename, username, siteid, sitename, tempDir, option, languageType);
            }
        }

        return errcode;
    }

    private int generateMoreColIndex(XMLProperties properties, String appPath, String sitename, String[] colList, String[] tagHTML,
                                     String[] buf, int altagIndex, int columnID, int listType, String username, int siteid, int samsiteid, int option, String fname,int modeltype) {
        int errcode = 0;
        int colNum = 0;
        int navbar = 0;
        String tagName = properties.getName();
        ITagManager tagMgr = TagManager.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            colNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
            navbar = Integer.parseInt(properties.getProperty(tagName.concat(".NAVBAR")));
        } catch (NumberFormatException ignored) {
        }

        Column column = null;
        try {
            column = columnMgr.getColumn(columnID);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        String tempDir = column.getDirName();
        tempDir = StringUtil.replace(tempDir, "/", File.separator);
        String extname = column.getExtname();

        String baseDir = appPath + "sites" + File.separator + sitename;

        String headstr = "";
        String midstr = "";
        String tailstr = "";
        try {
            String datastr = viewfileMgr.getAViewFile(listType).getContent();
            headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
            midstr = datastr.substring(datastr.indexOf("<!--ROW-->") + "<!--ROW-->".length(), datastr.lastIndexOf("<!--ROW-->"));
            tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + "<!--ROW-->".length());
        } catch (viewFileException e) {
            e.printStackTrace();
        }

        //获得栏目列表栏数
        int columnNum = tagMgr.getColumnNum(midstr);                         //文章列数
        int columnCount = colList.length;                                   //文章总条数
        int pageNum = (int) Math.ceil((double) columnCount / colNum);      //总页数
        int rowNum = colNum / columnNum;                                    //整行数
        int extraNum = colNum % columnNum;                                  //余下的栏目数
        String arr[] = tagMgr.getColumnString(midstr);
        if (tagName.equals("COLUMN_LIST")) {
            colNum = Integer.parseInt(properties.getProperty(tagName.concat(".COLUMNNUM")));
            pageNum = (int) Math.ceil((double) columnCount / colNum);
            rowNum = colNum / columnNum;                                //整行数
            extraNum = colNum % columnNum;
        }

        //页号倒排序，0--表示index.shtml页面
        for (int p = 0; p < pageNum; p++) {
            StringBuffer sb = new StringBuffer();
            if (p == pageNum - 1) {       //如果是最后一页
                int lastPageColNum = columnCount % colNum;                  //最后一页的栏目数
                if (lastPageColNum > 0) {
                    rowNum = lastPageColNum / columnNum;                      //最后一页的整行数
                    extraNum = lastPageColNum % columnNum;                    //最后一页剩余栏目数
                }
            }

            for (int i = 0; i < rowNum; i++) {    //生成栏目列表
                int begin = p * colNum + i * columnNum;
                int end = p * colNum + (i + 1) * columnNum;
                for (int j = begin; j < end; j++) {   //按行循环
                    int k = (j - begin) * 2;
                    if (columnNum > 1) {
                        sb.append(arr[k]);
                    }
                    sb.append(colList[j]);
                }

                if (columnNum > 1)
                    sb.append(arr[columnNum * 2]);
                sb.append("\r\n");
            }

            if (extraNum > 0) {
                int begin = p * colNum + rowNum * columnNum;
                for (int j = begin; j < begin + columnNum; j++) {
                    int k = (j - begin) * 2;
                    sb.append(arr[k]);
                    if (j < begin + extraNum)
                        sb.append(colList[j]);
                    else
                        sb.append("&nbsp;");
                }
                sb.append(arr[columnNum * 2]);
                sb.append("\r\n");
            }

            sb.insert(0, headstr);   //获取栏目列表的表头
            sb.append(tailstr);      //为栏目列表增加表尾

            //为栏目列表增加尾部导航条
            if (navbar > 0) sb.append(generateNavBar(0,navbar,pageNum,p,columnCount,column,fname,modeltype));

            for (int j = 0; j < tagHTML.length; j++) {
                if (j == altagIndex) {
                    buf[2 * j + 1] = sb.toString();
                } else {
                    buf[2 * j + 1] = tagHTML[j];
                }
            }

            int languageType = column.getLanguageType();
            String filename;
            if (p == 0)
                filename = baseDir + tempDir + fname + "." + extname;
            else
                filename = baseDir + tempDir + fname + p + "." + extname;

            if (navbar > 0) {
                String resultStr = "";
                for (int i = 0; i < buf.length; i++) {
                    resultStr += buf[i];
                }
                //增加栏目的头信息
                resultStr = addColumnPageHeader(resultStr, columnID, siteid);
                errcode = generatePage(resultStr, filename, username, siteid, samsiteid, sitename, tempDir, option, languageType);
            } else if (navbar == 0) {                            //替换模板中所有NAVBAR标记
                StringBuffer result = new StringBuffer();
                for (int i = 0; i < buf.length; i++) {
                    result.append(buf[i]);
                }

                String str = result.toString();
                int posi;
                if ((posi = str.indexOf("<TAG><NAVBAR>")) > -1 && str.indexOf("</NAVBAR></TAG>") > -1) {
                    String navStr = str.substring(posi, str.indexOf("</NAVBAR></TAG>") + 15);
                    XMLProperties prop = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + navStr);
                    String navType_And_Columnid = prop.getProperty(prop.getName());
                    posi = navType_And_Columnid.indexOf("-");
                    String navType_s = "";
                    if (posi > -1)
                        navType_s = navType_And_Columnid.substring(0, posi);
                    else
                        navType_s = navType_And_Columnid;
                    int navType = Integer.parseInt(navType_s);
                    String nav = generateNavBar(0,navType,pageNum,p,columnCount,column,fname,modeltype);
                    str = StringUtil.replace(str, "<TAG><NAVBAR>" + navType_And_Columnid + "</NAVBAR></TAG>", nav);
                }

                str = addColumnPageHeader(str, columnID, siteid);
                errcode = generatePage(str, filename, username, siteid, samsiteid, sitename, tempDir, option, languageType);
            }
        }

        return errcode;
    }

    public String PreviewArticlePage(int ID, int siteid, int samsiteid, String baseURL, String appPath, String sitename, int imgflag,
                                     int columnId) throws PublishException {
        String result = "";
        int modeltype = 0;
        IModelManager modelMgr = ModelPeer.getInstance();
        IArticleManager articleManager = ArticlePeer.getInstance();
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        try {
            Article article = articleManager.getArticle(ID);

            String templateBuf = "";
            int templateID;
            if (article.getModelID() > 0)
                templateID = article.getModelID();
            else if (samsiteid > 0) {

                templateID = pubTemplate.getDefaultTemplateID(siteid, columnId, 1, samsiteid);
            } else {
                templateID = pubTemplate.getDefaultTemplateID(siteid, columnId, 1);
            }
            //templateID = pubTemplate.getDefaultTemplateID(siteid, columnId, 1);     //1表示找文章页面模板
            if (templateID == 0)
                return "err1";
            else if (templateID == -1)
                return "err2";

            try {
                Model model = modelMgr.getModel(templateID);
                if (model.getReferModelID() > 0) {
                    templateID = model.getReferModelID();
                    model = modelMgr.getModel(templateID);
                }
                templateBuf = model.getContent();
                modeltype = model.getIsArticle();
            } catch (ModelException e) {
                e.printStackTrace();
            }

            String[] buf = pubTemplate.TemplatePaser(templateBuf);
            if (buf.length > 1) {
                String[] tag = new String[(buf.length - 1) / 2];
                for (int i = 0; i < tag.length; i++) {
                    tag[i] = buf[2 * i + 1];
                    tag[i] = StringUtil.replace(tag[i], "[", "<");
                    tag[i] = StringUtil.replace(tag[i], "]", ">");
                }

                //处理文章模板中所有的TAG,返回所有TAG的处理结果
                String[] tagHTML = pubCommon.ProcessTag(tag, ID, columnId, appPath, siteid, samsiteid, sitename, imgflag, templateID, modeltype, "", true);

                XMLProperties properties = null;
                String attrName = null;
                int html_code_mark_type = 0;
                for (int i = 0; i < tag.length; i++) {
                    String tbuf = tag[i].substring(6);
                    int posi = tbuf.indexOf(">");
                    attrName = tbuf.substring(0, posi);
                    if (attrName.equals("HTMLCODE")) {
                        String mybuf = tag[i];
                        int sposi = mybuf.indexOf("<MARKTYPE>");
                        int eposi = mybuf.indexOf("</MARKTYPE>");
                        html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                    }

                    if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") ||
                            attrName.equals("SUBARTICLE_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("BROTHER_LIST")
                            || attrName.equals("COMMEND_ARTICLE")) {
                        properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                        String listType_s = properties.getProperty(attrName.concat(".LISTTYPE"));
                        int listType = 0;
                        if (listType_s != null) listType = Integer.parseInt(listType_s);
                        try {
                            String datastr = viewfileMgr.getAViewFile(listType).getContent();
                            String tableHead = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                            String tableTail = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                            tagHTML[i] = tableHead + tagHTML[i] + tableTail;
                            buf[2 * i + 1] = tagHTML[i];
                        } catch (viewFileException e) {
                            System.out.println("Get the viewfile failed in PublishPeer line 1287");
                        }
                    } else if (attrName.equals("HTMLCODE")) {
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                        int markid = 0;
                        if (tagHTML[i].indexOf("<STYLES>") != -1) {
                            String style = "";
                            int sposi1 = tagHTML[i].indexOf("<STYLES>");
                            int eposi1 = tagHTML[i].indexOf("</STYLES>");
                            style = tagHTML[i].substring(sposi1 + 8, eposi1);
                            String a[] = new String[1];
                            a[0] = style;

                            if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                String aaa = buf[2 * i + 1];
                                aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                markid = Integer.parseInt(aaa);
                            }
                        }
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, ID);
                        int sposi = tagHTML[i].indexOf("<CONTENT>");
                        int eposi = tagHTML[i].indexOf("</CONTENT>");
                        tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                        buf[2 * i + 1] = tagHTML[i];
                        processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                    } else {
                        buf[2 * i + 1] = tagHTML[i];
                    }
                }
            }

            for (int i = 0; i < buf.length; i++) {
                result = result + buf[i];
            }

            if (result != null && result != "") {
                result = StringUtil.replace(result, "/webbuilder/sites/" + sitename, "http://" + StringUtil.replace(sitename, "_", "."));
            }

            //if (result.indexOf("<HEAD>") != -1)
            //    result = result.replaceFirst("<HEAD>", "<HEAD><base href=" + baseURL + ">");
            //if (result.indexOf("<head>") != -1)
            //    result = result.replaceFirst("<head>", "<head><base href=" + baseURL + ">");
        } catch (ArticleException e) {
            e.printStackTrace();
            return "articleerror";
        }

        return result;
    }

    public String PreviewTemplate(int columnID, int modelID, int isArticle, int siteid, int samsiteid, String baseURL, String appPath, String sitename, int imgflag) throws PublishException {
        String result = "";

        IArticleManager articleMgr = ArticlePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();

        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        try {
            Column column = columnMgr.getColumn(columnID);

            //获取模板内容
            Model model = modelMgr.getModel(modelID);
            int modeltype = model.getIsArticle();
            if (model.getReferModelID() > 0) {
                model = modelMgr.getModel(model.getReferModelID());
                if (model == null) return "err";
            }

            String templateBuf = model.getContent();
            if (templateBuf == null || templateBuf.trim().length() == 0) {
                return "err";
            }
            int i;
            int articleID = 0;
            int articleCount = 0;
            if (samsiteid == 0) {
                articleCount = articleMgr.getArticleCountIncludeSubColumn(columnID);
            } else {
                articleCount = articleMgr.getArticleCountIncludeSubColumnBySamsite(siteid, samsiteid, columnID);
            }
            if (isArticle == 1 && articleCount > 0)    //文章模板
            {
                List list = articleMgr.getArticles(columnID, 0, articleCount, 1, 1);
                articleID = ((Article) list.get(articleCount - 1)).getID();
            } else if (isArticle == 1) {
                return "articleerror";
            }

            int navbar = -1;
            int pageNum = 0;
            String[] buf = pubTemplate.TemplatePaser(templateBuf);
            if (buf.length > 1) {
                String[] tag = new String[(buf.length - 1) / 2];
                for (i = 0; i < tag.length; i++) {
                    tag[i] = buf[2 * i + 1];
                    tag[i] = StringUtil.replace(tag[i], "[", "<");
                    tag[i] = StringUtil.replace(tag[i], "]", ">");
                }

                //处理文章模板中所有的TAG,返回所有TAG的处理结果
                String[] tagHTML = pubCommon.ProcessTag(tag, articleID, columnID, appPath, siteid, samsiteid, sitename, imgflag, model.getID(), modeltype, "", true);
                //String[] tagHTML = pubCommon.ProcessTag(tag, articleID, columnID, appPath, siteid, samsiteid, sitename, imgflag, model.getID(), modeltype, "", true);

                XMLProperties properties = null;
                String attrName = null;
                int html_code_mark_type = 0;
                for (i = 0; i < tag.length; i++) {
                    if (tag[i].indexOf("<HTMLCODE>") == -1) {
                        properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                        attrName = properties.getName();
                    } else {
                        attrName = "HTMLCODE";
                        String mybuf = tag[i];
                        int sposi = mybuf.indexOf("<MARKTYPE>");
                        int eposi = mybuf.indexOf("</MARKTYPE>");
                        html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                    }

                    if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") ||
                            attrName.equals("SUBARTICLE_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("BROTHER_LIST")
                            || attrName.equals("COMMEND_ARTICLE")) {
                        int listType = Integer.parseInt(properties.getProperty(attrName.concat(".LISTTYPE")));
                        if (tagHTML[i].trim().length() > 0) {
                            String datastr = viewfileMgr.getAViewFile(listType).getContent();
                            String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                            String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                            tagHTML[i] = headstr + tagHTML[i] + tailstr;
                        }

                        if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST") || attrName.equals("BROTHER_LIST")) {
                            try {
                                int articleNum = 0;
                                int way = -1;
                                try {
                                    navbar = Integer.parseInt(properties.getProperty(attrName.concat(".NAVBAR")));
                                    articleNum = Integer.parseInt(properties.getProperty(attrName.concat(".ARTICLENUM")));
                                    way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));
                                } catch (NumberFormatException ignored) {
                                }

                                //如果是栏目模板并且分页,增加导航条部分
                                if (isArticle != 1 && way == 2 && articleNum > 0) {
                                    pageNum = articleCount / articleNum;
                                    if (articleCount % articleNum != 0)
                                        pageNum = pageNum + 1;

                                    String navStr = "";
                                    if (navbar > 0)
                                        navStr = generateNavBar(0, navbar, pageNum, 0, articleCount, column, "",isArticle);
                                    tagHTML[i] = tagHTML[i] + navStr;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        buf[2 * i + 1] = tagHTML[i];
                    } else if (attrName.equals("HTMLCODE")) {
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                        int markid = 0;
                        if (tagHTML[i].indexOf("<STYLES>") != -1) {
                            String style = "";
                            int sposi1 = tagHTML[i].indexOf("<STYLES>");
                            int eposi1 = tagHTML[i].indexOf("</STYLES>");
                            style = tagHTML[i].substring(sposi1 + 8, eposi1);
                            String a[] = new String[1];
                            a[0] = style;

                            if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                String aaa = buf[2 * i + 1];
                                aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                markid = Integer.parseInt(aaa);
                            }
                        }
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, articleID);
                        int sposi = tagHTML[i].indexOf("<CONTENT>");
                        int eposi = tagHTML[i].indexOf("</CONTENT>");
                        if (sposi + 9 < eposi) tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                        buf[2 * i + 1] = tagHTML[i];
                        processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                    } else {
                        buf[2 * i + 1] = tagHTML[i];
                    }
                }

                for (i = 0; i < buf.length; i++) {
                    result = result + buf[i];
                }

                //如果是自定义导航条位置
                if (navbar == 0) {
                    int posi;
                    if ((posi = result.indexOf("<TAG><NAVBAR>")) > -1 && result.indexOf("</NAVBAR></TAG>") > -1) {
                        String navStr = result.substring(posi, result.indexOf("</NAVBAR></TAG>") + 15);
                        XMLProperties prop = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + navStr);
                        String navType_s = prop.getProperty(prop.getName());
                        int dash_posi = navType_s.indexOf("-");
                        if (dash_posi > -1) navType_s = navType_s.substring(0, dash_posi);
                        int navType = Integer.parseInt(navType_s);
                        String nav = generateNavBar(0, navType, pageNum, 0, articleCount, column, "",isArticle);
                        result = StringUtil.replace(result, "<TAG><NAVBAR>" + navType + "</NAVBAR></TAG>", nav);
                    }
                }

                if (result != null && result != "") {
                    result = StringUtil.replace(result, "/webbuilder/sites/" + sitename, "http://" + StringUtil.replace(sitename, "_", "."));
                }

                //if (result.indexOf("<HEAD>") != -1)
                //    result = result.replaceFirst("<HEAD>", "<HEAD><base href=" + baseURL + ">");

                //if (result.indexOf("<head>") != -1)
                //    result = result.replaceFirst("<head>", "<head><base href=" + baseURL + ">");
            } else {
                result = templateBuf;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "articleerror";
        }

        return result;
    }

    public String PreviewTemplateForProgram(int columnID, int modelID, int isArticle, int siteid, int samsiteid, String baseURL, String appPath, String sitename, int imgflag) throws PublishException {
        String result = "";

        IArticleManager articleMgr = ArticlePeer.getInstance();
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        try {
            String tempDir = "";
            tempDir = StringUtil.replace(tempDir, "/", File.separator);

            //获取模板内容
            Model model = modelMgr.getModel(modelID);
            if (model.getReferModelID() > 0) {
                model = modelMgr.getModel(model.getReferModelID());
                if (model == null) return "err";
            }

            String templateBuf = model.getContent();
            if (templateBuf == null || templateBuf.trim().length() == 0) {
                return "err";
            }

            int i = 0;
            int articleID = 0;
            int navbar = -1;
            int pageNum = 0;
            String[] buf = pubTemplate.TemplatePaser(templateBuf);

            if (buf.length > 1) {
                //模板内包括TAG
                String[] tag = new String[(buf.length - 1) / 2];
                for (i = 0; i < tag.length; i++) {
                    tag[i] = buf[2 * i + 1];
                    tag[i] = StringUtil.replace(tag[i], "[", "<");
                    tag[i] = StringUtil.replace(tag[i], "]", ">");
                }

                //获取所有返回的HTML代码
                String[] tagHTML = pubCommon.ProcessTag(tag, 0, columnID, appPath, siteid, samsiteid, sitename, imgflag, model.getID(), model.getIsArticle(), "", true);

                int listType = 0;
                XMLProperties properties = null;
                String attrName = null;
                int html_code_mark_type = 0;
                for (i = 0; i < tag.length; i++) {
                    if (tag[i].indexOf("<HTMLCODE>") == -1) {
                        properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                        attrName = properties.getName();
                    } else {
                        attrName = "HTMLCODE";
                        String mybuf = tag[i];
                        int sposi = mybuf.indexOf("<MARKTYPE>");
                        int eposi = mybuf.indexOf("</MARKTYPE>");
                        html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                    }
                    if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") || attrName.equals("SUBARTICLE_LIST")) {
                        try {
                            listType = Integer.parseInt(properties.getProperty(attrName.concat(".LISTTYPE")));
                            int innerFlag = Integer.parseInt(properties.getProperty(attrName.concat(".INNERHTMLFLAG")));
                            int way = 0;
                            if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST"))
                                way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));

                            if (way == 0) {
                                if (innerFlag == 0 && tagHTML[i].trim().length() > 0) {
                                    String datastr = viewfileMgr.getAViewFile(listType).getContent();
                                    String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                                    String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                                    tagHTML[i] = headstr + "\r\n" + tagHTML[i] + tailstr;
                                }
                            }
                        } catch (Exception e) {
                        }
                        buf[2 * i + 1] = tagHTML[i];
                    } else if (attrName.equals("HTMLCODE")) {
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                        int markid = 0;
                        if (tagHTML[i].indexOf("<STYLES>") != -1) {
                            String style = "";
                            int sposi1 = tagHTML[i].indexOf("<STYLES>");
                            int eposi1 = tagHTML[i].indexOf("</STYLES>");
                            style = tagHTML[i].substring(sposi1 + 8, eposi1);
                            String a[] = new String[1];
                            a[0] = style;

                            if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                String aaa = buf[2 * i + 1];
                                aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                markid = Integer.parseInt(aaa);
                            }
                        }
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, articleID);
                        int sposi = tagHTML[i].indexOf("<CONTENT>");
                        int eposi = tagHTML[i].indexOf("</CONTENT>");
                        tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                        buf[2 * i + 1] = tagHTML[i];
                        processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                        buf[2 * i + 1] = StringUtil.replace(buf[2 * i + 1], "&123#;", "[");
                        buf[2 * i + 1] = StringUtil.replace(buf[2 * i + 1], "&321#;", "]");
                    } else {
                        buf[2 * i + 1] = tagHTML[i];
                    }
                }
            }

            for (i = 0; i < buf.length; i++) {
                result = result + buf[i];
            }

            if (result.indexOf("<HEAD>") != -1)
                result = result.replaceFirst("<HEAD>", "<HEAD><base href=" + baseURL + ">");

            if (result.indexOf("<head>") != -1)
                result = result.replaceFirst("<head>", "<head><base href=" + baseURL + ">");
        } catch (Exception e) {
            e.printStackTrace();
            return "articleerror";
        }


        return result;
    }

    //big5flag==0 生成简体  big5flag==1生成繁体  big5flag==2生成wap
    //isown=true是本栏目的文章，isown=false是引用其它栏目的文章
    //imgflag:未用变量
    //from:标识从不同的地方发起文章发布的请求，1表示从文章管理页面发起文章发布请求，0表示从文章发布页面发起文章发布请求
    public int CreateArticlePage(int ID,int columnId,int siteid,int sitetype,int samsiteid,String appPath,String sitename,String username,int imgflag,int big5flag, boolean isown,int from) throws PublishException {
        int errcode = 0;
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IArticleManager articleManager = ArticlePeer.getInstance();
        IExtendAttrManager extendAttrManager = ExtendAttrPeer.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            Article article=null;
            try {
                article = articleManager.getArticle(ID);
            } catch (ArticleException e) {
                articleManager.setPublishFailedStatus(ID);
                return -1;
            }

            int columnID = -1;
            if (!isown)
                columnID = columnId;
            else
                columnID = article.getColumnID();

            String content = article.getContent();

            Column column = null;
            try {
                column = columnMgr.getColumn(columnID);
            } catch (ColumnException e) {
                e.printStackTrace();
            }

            int languageType = column.getLanguageType();
            int templateID=0;
            if (article.getModelID() > 0)
                templateID = article.getModelID();
            else {
                if (samsiteid > 0) {
                    templateID = pubTemplate.getDefaultTemplateID(siteid, columnID, 1, samsiteid);   //获取被引用站点的文章模板
                } else {
                    templateID = pubTemplate.getDefaultTemplateID(siteid, columnID, 1);             //文章模板
                }
            }

            if (templateID < 1) return -2;

            List modelList = new ArrayList();
            try {
                if (column.getIsPublishMoreArticleModel() == 1)
                    modelList = modelMgr.getArticleModels(modelMgr.getModel(templateID).getColumnID());
                else
                    modelList.add(modelMgr.getModel(templateID));
            } catch (ModelException e) {
                e.printStackTrace();
            }

            //多文章模板发布
            for (int j = 0; j < modelList.size(); j++) {
                int morePage = -1;
                int moreList = -1;
                int contentPosi = -1;
                Model model = (Model) modelList.get(j);
                int modelID = model.getID();
                int modeltype = model.getIsArticle();
                String templateContent = model.getContent();

                //如果是引用的模板
                if (model.getReferModelID() > 0) {
                    modelID = model.getReferModelID();
                    try {
                        templateContent = modelMgr.getModel(modelID).getContent();
                    } catch (ModelException e) {
                        articleManager.setPublishFailedStatus(ID);
                        return -2;
                    }
                }

                String[] buf = pubTemplate.TemplatePaser(templateContent);      //处理文章模版中的标签
                if (buf.length > 1) {
                    String[] tag = new String[(buf.length - 1) / 2];
                    for (int i = 0; i < tag.length; i++) {
                        tag[i] = buf[2 * i + 1];
                        tag[i] = StringUtil.replace(tag[i], "[", "<");
                        tag[i] = StringUtil.replace(tag[i], "]", ">");
                    }

                    //处理文章模板中所有的TAG,返回所有TAG的处理结果
                    String[] tagHTML = pubCommon.ProcessTag(tag, ID, columnID, appPath, siteid, samsiteid, sitename, imgflag, modelID, modeltype, username, false);

                    int listType = 0;
                    XMLProperties properties = null;
                    String attrName = null;
                    int html_code_mark_type = 0;
                    for (int i = 0; i < tag.length; i++) {
                        String tbuf = tag[i].substring(6);
                        int posi = tbuf.indexOf(">");
                        attrName = tbuf.substring(0, posi);
                        if (attrName.equals("HTMLCODE")) {
                            String mybuf = tag[i];
                            int sposi = mybuf.indexOf("<MARKTYPE>");
                            int eposi = mybuf.indexOf("</MARKTYPE>");
                            html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                        } else {
                            properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                        }
                        if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") || attrName.equals("RECOMMEND_LIST")
                                || attrName.equals("SUBARTICLE_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("BROTHER_LIST")
                                || attrName.equals("COMMEND_ARTICLE")) {
                            String s_listType = properties.getProperty(attrName.concat(".LISTTYPE"));
                            listType = Integer.parseInt(s_listType);
                            int innerFlag = Integer.parseInt(properties.getProperty(attrName.concat(".INNERHTMLFLAG")));
                            int way = 0;
                            if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST") || attrName.equals("BROTHER_LIST") || attrName.equals("RECOMMEND_LIST")
                                    || attrName.equals("COLUMN_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("COMMEND_ARTICLE"))
                                way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));

                            if (way == 0) {
                                if (innerFlag == 0 && tagHTML[i].trim().length() > 0) {
                                    String datastr;
                                    try {
                                        datastr = viewfileMgr.getAViewFile(listType).getContent();
                                    } catch (viewFileException e) {
                                        articleManager.setPublishFailedStatus(ID);
                                        return -4;
                                    }
                                    String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                                    String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                                    tagHTML[i] = headstr + "\r\n" + tagHTML[i] + tailstr;
                                }
                                buf[2 * i + 1] = tagHTML[i];
                            } else {
                                moreList = i;
                            }
                        } else if (attrName.equals("HTMLCODE")) {
                            tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                            tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                            int markid = 0;
                            if (tagHTML[i].indexOf("<STYLES>") != -1) {
                                String style = "";
                                int sposi1 = tagHTML[i].indexOf("<STYLES>");
                                int eposi1 = tagHTML[i].indexOf("</STYLES>");
                                style = tagHTML[i].substring(sposi1 + 8, eposi1);
                                String a[] = new String[1];
                                a[0] = style;

                                if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                    String aaa = buf[2 * i + 1];
                                    aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                    markid = Integer.parseInt(aaa);
                                }
                            }
                            tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, ID);
                            int sposi = tagHTML[i].indexOf("<CONTENT>");
                            int eposi = tagHTML[i].indexOf("</CONTENT>");
                            tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                            buf[2 * i + 1] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, ID);
                            processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                        } else {
                            if ("ARTICLE_CONTENT".equals(attrName)) {
                                content = tagHTML[i];
                            }
                            if ("PAGINATION".equals(attrName)) {
                                if (content != null && content.indexOf("[TAG][PAGINATION][/PAGINATION][/TAG]") > -1)
                                    morePage = i;
                                else
                                    tagHTML[i] = "";
                            }
                            if ("ARTICLE_CONTENT".equals(attrName))
                                contentPosi = i;

                            buf[2 * i + 1] = tagHTML[i];
                        }
                    }

                    //在模板中有一个分页文章列表，并且只能有一个文章分页列表
                    if (moreList >= 0) {
                        String templateName = String.valueOf(ID);
                        if (model.getID() != templateID) templateName = ID + "-" + model.getTemplateName();

                        properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[moreList].trim()));
                        String[] articleList = tagHTML[moreList].trim().split("\\<\\-\\-B_I_Z_W_I_N_K\\-\\-\\>");
                        errcode = generateMoreIndex(properties, appPath, sitename, articleList, tagHTML, buf, moreList, columnID, listType, username, siteid,big5flag, templateName,1,article);
                    }
                }

                if (moreList == -1) {
                    String extName = column.getExtname();

                    //获得文章创建的时间，生成时间发布路径
                    String createdate_path = article.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "");

                    String tempDir = StringUtil.replace(column.getDirName(), "/", File.separator) + createdate_path + File.separator;
                    String rootPath = appPath + "sites" + File.separator + sitename + tempDir;

                    //contentPosi=-1表示文章模板中无内容标记
                    //morePage=-1表示文章内容中无分页
                    if (morePage == -1 || contentPosi == -1) {
                        String filename = rootPath + ID + "." + extName;
                        if (model.getID() != templateID)
                            filename = rootPath + ID + "-" + model.getTemplateName() + "." + extName;

                        if (morePage == -1 && contentPosi > -1) {
                            buf[2 * contentPosi + 1] = StringUtil.replace(buf[2 * contentPosi + 1], "[TAG][PAGINATION][/PAGINATION][/TAG]", "");
                        }
                        if (morePage > -1 && contentPosi == -1) {
                            buf[2 * morePage + 1] = "";
                        }

                        String resultStr = "";
                        for (int i = 0; i < buf.length; i++) resultStr += buf[i];

                        //如果文章内容不分页，就清除分页样式
                        if (resultStr.indexOf("[TAG][PAGESTYLE]") > -1) {
                            resultStr = resultStr.replaceAll("\\[TAG\\]\\[PAGESTYLE\\][0-9]*\\[/PAGESTYLE\\]\\[/TAG\\]", "");
                        }
                        if (resultStr.indexOf("[TAG][NEXTPAGE]") > -1) {
                            resultStr = resultStr.replaceAll("\\[TAG\\]\\[NEXTPAGE\\]\\[/NEXTPAGE\\]\\[/TAG\\]", "#");
                        }

                        //增加文章的头信息，如文章的标题，文章概述，文章关键字、文章作者，发布时间等信息
                        if (!extName.equals("wml")) {
                            resultStr = addArticleHeader(resultStr, article, siteid, 0, columnID);
                        }

                        //用文章ID替换[%%articleid%%]标记
                        String diansitename = sitename;
                        diansitename = diansitename.replaceAll("_", ".");
                        resultStr = StringUtil.replace(resultStr, "[%%sitename%%]", diansitename);
                        resultStr = StringUtil.replace(resultStr, "[%%articleid%%]", String.valueOf(ID));

                        //增加文章权限  文章需要有权限的用户浏览
                        if (column.getUserflag() == 1) {   //登录用户才可以浏览
                            String script_content = "\r\n<script language=\"javascript\">\r\n" +
                                    "var objXml = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n" +
                                    "objXml.open(\"POST\", \"/_commons/checkArticle.jsp?userflag=" + column.getUserflag() + "&userlevel="
                                    + column.getUserlevel() + "\", false);\r\n" +
                                    "objXml.Send();\r\n" +
                                    "var retstr = objXml.responseText;\r\n" +
                                    "if (retstr != null && retstr.length > 0 && retstr.indexOf(\"nologin\") > -1) {" +
                                    "    alert(\"请先登录系统!\");\r\n" +
                                    //"    window.location=\"" + "/_prog/login.jsp\";\r\n" +
                                    "    window.location=\"/" + sitename + "/_prog/login.jsp\";\r\n" +
                                    "    }\r\n" +
                                    "if (retstr != null && retstr.length > 0 && retstr.indexOf(\"nolevel\") > -1) {\r\n" +
                                    "   alert(\"只有一定级别的用户才能够浏览!\");\r\n" +
                                    "    window.close();    }\r\n" +
                                    "</script>\r\n";
                            if (cpool.getType().equalsIgnoreCase("mssql"))
                                script_content = StringUtil.gb2iso(script_content);
                            int end_head_posi = resultStr.toLowerCase().indexOf("</head>");
                            if (end_head_posi > -1) {
                                String before_content_of_end_head = resultStr.substring(0, end_head_posi);
                                String after_content_of_end_head = resultStr.substring(end_head_posi);
                                resultStr = before_content_of_end_head + script_content + after_content_of_end_head;
                            } else {
                                resultStr = script_content + resultStr;
                            }
                        }

                        //-----------------------------------------------------------------------
                        String modelcontent = model.getContent();
                        if (modelcontent.indexOf("IMGZUHECONTENT") != -1) {
                            Pattern p = Pattern.compile("\\[TAGA\\]\\[IMGZUHECONTENT\\]\\[CHINESENAME\\]\\S*\\[/IMGZUHECONTENT\\]\\[/TAGA\\]", Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(modelcontent);
                            String srcstr = "";
                            String dirname = article.getDirName();
                            while (m.find()) {
                                int start = m.start();
                                int end = m.end();
                                srcstr = modelcontent.substring(start, end);
                                srcstr = srcstr.substring(0, srcstr.indexOf("[/ORDER]") + 8);
                                if (resultStr.indexOf(srcstr) != -1) {
                                    String order = srcstr.substring(srcstr.indexOf("[ORDER]"), srcstr.indexOf("[/ORDER]") + 8);
                                    String width = srcstr.substring(srcstr.indexOf("[WIDTH]") + "[WIDTH]".length(), srcstr.indexOf("[/WIDTH]"));
                                    String height = srcstr.substring(srcstr.indexOf("[HEIGHT]") + "[HEIGHT]".length(), srcstr.indexOf("[/HEIGHT]"));
                                    String contentstr = "";
                                    if (content.indexOf(srcstr) != -1) {
                                        contentstr = content.substring(content.indexOf(srcstr) + srcstr.length(), content.lastIndexOf(order));
                                        contentstr = contentstr.substring(contentstr.indexOf("[SRC]") + "[SRC]".length(), contentstr.indexOf("[/SRC]"));
                                        contentstr = "<img src=" + dirname + "images/" + contentstr + "   widht=" + width + "  height=" + height + "  >";
                                    }
                                    resultStr = StringUtil.replace(resultStr, srcstr, "" + contentstr);
                                    resultStr = StringUtil.replace(resultStr, "[/IMGZUHECONTENT][/TAGA]\" value=\"[前台图片组合内容]\" />", "");
                                    resultStr = StringUtil.replace(resultStr, "<input type=\"button\" style=\"border-right: #808080 1px solid; border-top: #808080 1px solid; font-size: 12px; border-left: #808080 1px solid; border-bottom: #808080 1px solid; background-color: #d6d3ce\" name=\"", "");
                                    resultStr = StringUtil.replace(resultStr, "[/IMGZUHECONTENT][/TAGA]", "\" value=\"[前台图片组合内容]\" />");
                                }
                            }
                        }

                        //匹配文本
                        if (modelcontent.indexOf("TEXTZUHECONTENT") != -1) {
                            Pattern p = Pattern.compile("\\[TAGA\\]\\[TEXTZUHECONTENT\\]\\[CHINESENAME\\]\\S*\\[/TEXTZUHECONTENT\\]\\[/TAGA\\]", Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(modelcontent);
                            String srcstr = "";
                            String dirname = article.getDirName();
                            while (m.find()) {
                                int start = m.start();
                                int end = m.end();
                                srcstr = modelcontent.substring(start, end);
                                srcstr = srcstr.substring(0, srcstr.indexOf("[/ORDER]") + 8);
                                if (resultStr.indexOf(srcstr) != -1) {
                                    String order = srcstr.substring(srcstr.indexOf("[ORDER]"), srcstr.indexOf("[/ORDER]") + 8);
                                    String contentstr = "";
                                    if (content.indexOf(srcstr) != -1) {
                                        contentstr = content.substring(content.indexOf(srcstr) + srcstr.length(), content.lastIndexOf(order));
                                        contentstr = contentstr.substring(contentstr.indexOf("[AREA]") + "[AREA]".length(), contentstr.indexOf("[/AREA]"));
                                    }
                                    resultStr = StringUtil.replace(resultStr, srcstr, "" + contentstr);
                                    resultStr = StringUtil.replace(resultStr, "[/TEXTZUHECONTENT][/TAGA]\" value=\"[前台文字组合内容]\" />", "");
                                    resultStr = StringUtil.replace(resultStr, "<input type=\"button\" style=\"border-right: #808080 1px solid; border-top: #808080 1px solid; font-size: 12px; border-left: #808080 1px solid; border-bottom: #808080 1px solid; background-color: #d6d3ce\" name=\"", "");
                                    resultStr = StringUtil.replace(resultStr, "[/TEXTZUHECONTENT][/TAGA]", "\" value=\"[前台文字组合内容]\" />");
                                }
                            }
                        }

                        //-----------------------------------------------------------------------
                        errcode = generatePage(resultStr, filename, username, siteid, sitename, tempDir,big5flag, languageType, isown, columnID, article.getID());
                    } else {
                        String temp[] = content.split("\\[TAG\\]\\[PAGINATION\\]\\[/PAGINATION\\]\\[/TAG\\]");
                        String markstr = buf[2 * morePage + 1];
                        for (int i = 0; i < temp.length; i++) {
                            String filename = String.valueOf(ID);

                            //将内容分成若干段发布出去
                            buf[2 * contentPosi + 1] = temp[i];

                            //替换分页标记
                            XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + markstr);
                            int navbar = 0;
                            try {
                                navbar = Integer.parseInt(properties.getProperty(properties.getName()));
                            } catch (NumberFormatException ignored) {
                            }

                            buf[2 * morePage + 1] = generateNavBar(1, navbar, temp.length, i, temp.length, column, filename,1);

                            String resultStr = "";
                            for (int k = 0; k < buf.length; k++) resultStr += buf[k];

                            //处理文章内容中的分页样式
                            if (resultStr.indexOf("[TAG][PAGESTYLE]") > -1) {
                                Pattern p = Pattern.compile("\\[TAG\\]\\[PAGESTYLE\\][0-9]*\\[/PAGESTYLE\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
                                Matcher m = p.matcher(resultStr);
                                while (m.find()) {
                                    int start = m.start();
                                    int end = m.end();
                                    String tag = resultStr.substring(start, end);
                                    navbar = Integer.parseInt(tag.substring(tag.indexOf("[PAGESTYLE]") + 11, tag.indexOf("[/PAGESTYLE]")));

                                    resultStr = resultStr.substring(0, start) + generateNavBar(1, navbar, temp.length, i, temp.length, column, filename,1) + resultStr.substring(end);
                                    m = p.matcher(resultStr);
                                }
                            }

                            //处理文章内容中的图片连接形式 (目前主要为LINKTONE服务)
                            if (resultStr.indexOf("[TAG][NEXTPAGE][/NEXTPAGE][/TAG]") > -1) {
                                String nextFilename = ID + "-" + String.valueOf(i + 2) + "." + extName;
                                if (i == temp.length - 1)
                                    nextFilename = ID + "." + extName;
                                resultStr = resultStr.replaceAll("\\[TAG\\]\\[NEXTPAGE\\]\\[/NEXTPAGE\\]\\[/TAG\\]", nextFilename);
                            }

                            if (model.getID() != templateID) {
                                filename = ID + "-" + model.getTemplateName();
                            }
                            if (i > 0) {
                                int p = i;
                                if (cpool.getCustomer().equals("linktone")) p = i + 1;

                                filename = ID + "-" + String.valueOf(p);
                                if (model.getID() != templateID)
                                    filename = ID + "-" + model.getTemplateName() + "-" + String.valueOf(p);
                            }

                            filename = rootPath + filename + "." + extName;

                            //增加文章的头信息，如文章的标题，文章概述，文章关键字、文章作者，发布时间等信息
                            if (!extName.equals("wml")) {
                                resultStr = addArticleHeader(resultStr, article, siteid, i + 1, columnID);
                            }

                            //用文章ID替换[%%articleid%%]标记
                            String diansitename = sitename;
                            diansitename = diansitename.replaceAll("_", ".");
                            resultStr = StringUtil.replace(resultStr, "[%%sitename%%]", diansitename);
                            resultStr = StringUtil.replace(resultStr, "[%%articleid%%]", String.valueOf(ID));
                            //增加文章权限  文章需要有权限的用户浏览
                            if (column.getUserflag() == 1) {   //登录用户才可以浏览
                                String script_content = "\r\n<script language=\"javascript\">\r\n" +
                                        "var objXml = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n" +
                                        "objXml.open(\"POST\", \"/_commons/checkArticle.jsp?userflag=" + column.getUserflag() + "&userlevel="
                                        + column.getUserlevel() + "\", false);\r\n" +
                                        "objXml.Send();\r\n" +
                                        "var retstr = objXml.responseText;\r\n" +
                                        "if (retstr != null && retstr.length > 0 && retstr.indexOf(\"nologin\") > -1) {" +
                                        "    alert(\"请先登录系统\");\r\n" +
                                        //"    window.location=\"" + "/_prog/login.jsp\";\r\n" +
                                        "    window.location=\"/" + sitename + "/_prog/login.jsp\";\r\n" +
                                        "    }\r\n" +
                                        "if (retstr != null && retstr.length > 0 && retstr.indexOf(\"nolevel\") > -1) {\r\n" +
                                        "   alert(\"只有一定级别的用户才能浏览\");\r\n" +
                                        "    window.close();    }\r\n" +
                                        "</script>\r\n";
                                if (cpool.getType().equalsIgnoreCase("mssql"))
                                    script_content = StringUtil.gb2iso(script_content);
                                int end_head_posi = resultStr.toLowerCase().indexOf("</head>");
                                if (end_head_posi > -1) {
                                    String before_content_of_end_head = resultStr.substring(0, end_head_posi);
                                    String after_content_of_end_head = resultStr.substring(end_head_posi);
                                    resultStr = before_content_of_end_head + script_content + after_content_of_end_head;
                                } else {
                                    resultStr = script_content + resultStr;
                                }
                            }
                            errcode = generatePage(resultStr, filename, username, siteid, sitename, tempDir,big5flag, languageType);
                        }
                    }
                }
            }

            if (errcode == 0) {
                article.setPubFlag(0);
                article.setIsPublished(1);
            } else {
                article.setPubFlag(2);
                article.setIsPublished(article.getIsPublished());
            }

            //更新引用文章表中的文章发布标志位
            IRefersManager refersMgr = RefersPeer.getInstance();
            //System.out.println("isown===" + isown + "=" + article.getID() + "=" + article.getPubFlag() + "=" + article.getIsPublished());
            if (isown) {
                articleManager.updatePubFlag(article);                                                 //0--更新文章的发布标志位
                refersMgr.updateRefersArticlePubFlag(ID, columnID, 0, 0);           //0--地址引用
            } else {
                if (errcode == 0) {
                    refersMgr.updateRefersArticlePubFlag(ID, columnID, 0, 1);       //1--内容引用
                } else {
                    refersMgr.updateRefersArticlePubFlag(ID, columnID, 2, 1);       //1--内容引用
                }
            }

            //发布与该篇文章相关联的网站模板页面
            errcode = extendAttrManager.savePublishModelsIntoPublishQueue(siteid,columnID,ID,username);
            /*List pubModels = extendAttrManager.getNeedPublishModelsByArticleID(siteid,columnID,ID,username);
            if (from == 1) {
                for (int ii = 0; ii < pubModels.size(); ii++) {
                    Model model = (Model) pubModels.get(ii);
                    if (model.getIsArticle() == 2 || model.getIsArticle() == 4)            //发布PC首页模板和手机首页模板
                        createHomePage(siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, big5flag, model.getID());
                    else if (model.getIsArticle() == 0 || model.getIsArticle() == 4)      //发布PC栏目模板和手机栏目模板
                        CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, big5flag, model.getID());
                    else if (model.getIsArticle() == 3 || model.getIsArticle() == 6)      //发布PC专题模板和手机专题模板
                        CreateColPage(columnID, siteid, sitetype, samsiteid, appPath, sitename, username, imgflag, big5flag, model.getID());
                }
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            return -9;
        }

        return errcode;
    }

    //tcid 文章被推送到的栏目ID
    private String processArticleTitle(Article article, int siteID, int currentPage, String content, int tcid) {
        String mainTitle = article.getMainTitle();
        Tree colTree;
        if (tcid == 0)
            colTree = TreeManager.getInstance().getSiteTree(siteID);
        else {
            IColumnManager columnMgr = ColumnPeer.getInstance();
            try {
                siteID = columnMgr.getColumn(tcid).getSiteID();
            } catch (ColumnException e) {
                e.printStackTrace();
            }
            colTree = TreeManager.getInstance().getSiteTree(siteID);
        }

        try {
            int startP;
            int endP;
            while ((startP = mainTitle.indexOf("<")) > -1 && (endP = mainTitle.indexOf(">")) > -1) {
                mainTitle = mainTitle.substring(0, startP) + mainTitle.substring(endP + 1);
            }
            if (currentPage > 0) mainTitle = mainTitle + "(" + currentPage + ")";

            String title = "";
            int start = content.toLowerCase().indexOf("<title>");
            int end = content.toLowerCase().indexOf("</title>");
            if (start > 0 && end > 0 && start < end) title = content.substring(start + 7, end);

            start = title.toUpperCase().indexOf("[TITLESTYLE]");
            end = title.toUpperCase().indexOf("[/TITLESTYLE]");

            if (start > -1 && end > -1) {
                int listType = Integer.parseInt(title.substring(start + 12, end));
                IViewFileManager viewFileMgr = viewFilePeer.getInstance();
                String style = viewFileMgr.getAViewFile(listType).getContent();

                List columnList = colTree.getColumnNameList(colTree, article.getColumnID());
                int count = columnList.size();

                if (style.indexOf("<%%COLUMNNAME%%>") > -1) {
                    IColumnManager columnMgr = ColumnPeer.getInstance();
                    style = StringUtil.replace(style, "<%%COLUMNNAME%%>", columnMgr.getColumn(article.getColumnID()).getCName());
                }
                if (style.indexOf("<%%COLUMNNAME1%%>") > -1) {
                    if (count > 0)
                        style = StringUtil.replace(style, "<%%COLUMNNAME1%%>", (String) columnList.get(count - 1));
                    else
                        style = StringUtil.replace(style, "<%%COLUMNNAME1%%>", "");
                }
                if (style.indexOf("<%%COLUMNNAME2%%>") > -1) {
                    if (count > 1)
                        style = StringUtil.replace(style, "<%%COLUMNNAME2%%>", (String) columnList.get(count - 2));
                    else
                        style = StringUtil.replace(style, "<%%COLUMNNAME2%%>", "");
                }
                if (style.indexOf("<%%COLUMNNAME3%%>") > -1) {
                    if (count > 2)
                        style = StringUtil.replace(style, "<%%COLUMNNAME3%%>", (String) columnList.get(count - 3));
                    else
                        style = StringUtil.replace(style, "<%%COLUMNNAME3%%>", "");
                }
                if (style.indexOf("<%%DATA%%>") > -1) style = StringUtil.replace(style, "<%%DATA%%>", mainTitle);
                mainTitle = style;
            } else {
                if (tcid == 0)
                    mainTitle = mainTitle + colTree.getChinesePath(colTree, article);
                else {
                    //引用文章使用自己的文章路径
                    article.setColumnID(tcid);
                    mainTitle = mainTitle + colTree.getChinesePath(colTree, article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainTitle = StringUtil.replace(mainTitle, "—", "&mdash;");

        return mainTitle;
    }

    private String addArticleHeader(String content, Article article, int siteID, int currentPage, int tcid) {
        //增加文章标题信息
        String title = processArticleTitle(article, siteID, currentPage, content, tcid);

        int posi1;
        int posi2;
        int posi = content.toLowerCase().indexOf("</head>");

        String headstr = content.substring(0, posi + 7);
        content = content.substring(posi + 7);
        Pattern p = Pattern.compile("<title>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(headstr);
        if (matcher.find()) {
            posi1 = headstr.toLowerCase().indexOf("<title>");
            posi2 = headstr.toLowerCase().indexOf("</title>");
            if (posi1 > 0 && posi2 > 0)
                headstr = headstr.substring(0, posi1) + "<title>" + title + "</title>" + headstr.substring(posi2 + 8);
        } else {
            headstr = matcher.replaceFirst("<head><title>" + title + "</title>\r\n");
        }

        //增加文章描述信息
        String summary = "";
        if (article.getSummary() != null) {
            summary = article.getSummary();
        } else {
            String articleContent = article.getContent();
            if (articleContent != null) {
                articleContent = filterOutHTML(articleContent);
                posi1 = articleContent.indexOf(".");
                if (cpool.getType().equals("oracle"))
                    posi2 = articleContent.indexOf("。");
                else
                    posi2 = articleContent.indexOf(StringUtil.gb2iso("。"));
                if (posi2 > 1)
                    summary = articleContent.substring(0, posi2 + 1);
                else if (posi1 > 1)
                    summary = articleContent.substring(0, posi1 + 1);
                else if (articleContent.length() > 150)
                    summary = articleContent.substring(0, 150);
                else
                    summary = articleContent;
            }
        }

        if (summary != null && summary.trim().length() > 0) {
            p = Pattern.compile("<\\s*META\\s*NAME\\s*=\\s*\"description\"\\s*CONTENT\\s*=[^<>]*>", Pattern.CASE_INSENSITIVE);
            matcher = p.matcher(headstr);
            if (matcher.find()) {
                summary = StringUtil.replace(summary, "$", "\\$");
                summary = summary.replaceAll("\"", "'");
                summary = summary.replaceAll("\'", "");
                headstr = matcher.replaceFirst("<meta name=\"description\" content=\"" + summary + "\">");
            } else {
                summary = summary.replaceAll("\"", "'");
                summary = summary.replaceAll("\'", "");
                headstr = headstr.substring(0, headstr.length() - 7) + "<meta name=\"description\" content=\"" + summary + "\">\r\n</head>";
            }
        }

        //增加关键字信息
        //增加发布时间信息
        //增加作者信息

        //增加js最近浏览
        if (headstr.indexOf("</head>") != -1)
            headstr = headstr.substring(0, headstr.indexOf("</head>")) + "<script src=\"/_sys_js/commentarticle.js\" type=\"text/javascript\"></script></head>";

        return headstr + content;
    }

    private String addColumnPageHeader(String content, int columnID, int siteID) {
        Tree colTree = TreeManager.getInstance().getSiteTree(siteID);

        try {
            String title = "";
            int start = content.toLowerCase().indexOf("<title>");
            int end = content.toLowerCase().indexOf("</title>");
            if (start > 0 && end > 0 && start < end) title = content.substring(start + 7, end);

            start = title.toUpperCase().indexOf("[TITLESTYLE]");
            end = title.toUpperCase().indexOf("[/TITLESTYLE]");
            if (start > -1 && end > -1) {
                int listType = Integer.parseInt(title.substring(start + 12, end));
                IViewFileManager viewFileMgr = viewFilePeer.getInstance();
                String style = viewFileMgr.getAViewFile(listType).getContent();

                List columnList = colTree.getColumnNameList(colTree, columnID);
                int count = columnList.size();

                if (style != null) {
                    if (style.indexOf("<%%COLUMNNAME%%>") > -1) {
                        IColumnManager columnMgr = ColumnPeer.getInstance();
                        style = StringUtil.replace(style, "<%%COLUMNNAME%%>", columnMgr.getColumn(columnID).getCName());
                    }
                    if (style.indexOf("<%%COLUMNNAME1%%>") > -1) {
                        if (count > 0)
                            style = StringUtil.replace(style, "<%%COLUMNNAME1%%>", (String) columnList.get(count - 1));
                        else
                            style = StringUtil.replace(style, "<%%COLUMNNAME1%%>", "");
                    }
                    if (style.indexOf("<%%COLUMNNAME2%%>") > -1) {
                        if (count > 1)
                            style = StringUtil.replace(style, "<%%COLUMNNAME2%%>", (String) columnList.get(count - 2));
                        else
                            style = StringUtil.replace(style, "<%%COLUMNNAME2%%>", "");
                    }
                    if (style.indexOf("<%%COLUMNNAME3%%>") > -1) {
                        if (count > 2)
                            style = StringUtil.replace(style, "<%%COLUMNNAME3%%>", (String) columnList.get(count - 3));
                        else
                            style = StringUtil.replace(style, "<%%COLUMNNAME3%%>", "");
                    }
                    if (style.indexOf("<%%DATA%%>") > -1) style = StringUtil.replace(style, "<%%DATA%%>", "");

                    Pattern p = Pattern.compile("<title>", Pattern.CASE_INSENSITIVE);
                    Matcher matcher = p.matcher(content);
                    if (matcher.find()) {
                        int posi1 = content.toLowerCase().indexOf("<title>");
                        int posi2 = content.toLowerCase().indexOf("</title>");
                        if (posi1 > 0 && posi2 > 0)
                            content = content.substring(0, posi1) + "<title>" + style + "</title>" + content.substring(posi2 + 8);
                    } else {
                        //p = Pattern.compile("<head>", Pattern.CASE_INSENSITIVE);
                        content = matcher.replaceFirst("<head><title>" + style + "</title>\r\n");
                    }
                } else {
                    System.out.println("格式文件内容为空！！！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    private String filterOutHTML(String articleContent) {
        //剔除content中的[TAG]
        articleContent = articleContent.replaceAll("\\[TAG\\]\\[PAGINATION\\]\\[/PAGINATION\\]\\[/TAG\\]", "");
        articleContent = articleContent.replaceAll("\\[TAG\\]\\[AD_POSITION\\]\\[/AD_POSITION\\]\\[/TAG\\]", "");

        Pattern p = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
        String[] buf = p.split(articleContent);
        StringBuffer tempBuf = new StringBuffer();

        for (int i = 0; i < buf.length; i++) {
            tempBuf.append(buf[i]);
        }

        return tempBuf.toString().replaceAll("&nbsp;", "").trim();
    }

    public int CreateColPage(int columnID, int siteid,int sitetype, int samsiteid, String appPath, String sitename, String username, int imgflag, int option, int templateID) throws PublishException {
        int pubTemplateId = templateID;
        int i=0;
        int errcode = 0;
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        //获得模板文件名
        String templateName = "index";
        Model model = null;
        try {
            model = modelMgr.getModel(templateID);
            int modelType = model.getIsArticle();
            if (modelType == 3 || modelType == 6) {
                templateName = model.getTemplateName();
            } else {
                int defaultTemplateID = pubTemplate.getDefaultTemplateID(siteid, columnID, 0);
                //栏目的默认模板与当前发布模板不相同，并且网站是自己创建的网站或者完全拷贝的网站，使用模板自己的英文名字作为发布的文件名
                //对于被共享的网站仍然采用index作为发布的文件名
                if (templateID != defaultTemplateID){
                    if (sitetype == 0 || sitetype == 2) templateName = model.getTemplateName();
                }
            }
            if (templateName == null || templateName.trim().length() == 0) {
                return -4;    //当前模板没有文件名
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

        int languageType = 0;
        String tempDir = "";
        String filename = "";
        try {
            Column column = columnMgr.getColumn(columnID);
            tempDir = StringUtil.replace(column.getDirName(), "/", File.separator);
            filename = appPath + "sites" + File.separator + sitename + tempDir + templateName + "." + column.getExtname();
            languageType = column.getLanguageType();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        try {
            if (model.getReferModelID() > 0) {
                templateID = model.getReferModelID();
                model = modelMgr.getModel(templateID);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

        String content = model.getContent();
        int modeltype = model.getIsArticle();

        String[] buf = pubTemplate.TemplatePaser(content);

        int moreIndex = -1;
        if (buf.length >= 1) {
            //模板内包括TAG
            String[] tag = new String[(buf.length - 1) / 2];
            for (i = 0; i < tag.length; i++) {
                tag[i] = buf[2 * i + 1];
                tag[i] = StringUtil.replace(tag[i], "[", "<");
                tag[i] = StringUtil.replace(tag[i], "]", ">");
            }

            //获取所有返回的HTML代码
            String[] tagHTML = pubCommon.ProcessTag(tag, 0, columnID, appPath, siteid, samsiteid, sitename, imgflag, templateID, modeltype, username, false);

            int moreListType = 0;
            int listType = 0;
            XMLProperties properties = null;
            String attrName = null;
            int html_code_mark_type = 0;
            for (i = 0; i < tag.length; i++) {
                if (tag[i].indexOf("<HTMLCODE>") == -1) {
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                    attrName = properties.getName();
                } else {
                    attrName = "HTMLCODE";
                    String mybuf = tag[i];
                    int sposi = mybuf.indexOf("<MARKTYPE>");
                    int eposi = mybuf.indexOf("</MARKTYPE>");
                    html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                }

                if (attrName!=null) {
                    if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") || attrName.equals("SUBARTICLE_LIST")
                            || attrName.equals("BROTHER_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("RECOMMEND_LIST") || attrName.equals("COMMEND_ARTICLE")) {
                        try {
                            listType = Integer.parseInt(properties.getProperty(attrName.concat(".LISTTYPE")));
                            int innerFlag = Integer.parseInt(properties.getProperty(attrName.concat(".INNERHTMLFLAG")));
                            int way = 0;

                            if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST") || attrName.equals("BROTHER_LIST")|| attrName.equals("RECOMMEND_LIST")
                                    || attrName.equals("COLUMN_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("COMMEND_ARTICLE"))
                                way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));

                            if (way == 0){    //单页
                                if (innerFlag == 0 && tagHTML[i].trim().length() > 0) {
                                    String datastr = viewfileMgr.getAViewFile(listType).getContent();
                                    String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                                    String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                                    tagHTML[i] = headstr + "\r\n" + tagHTML[i] + tailstr;
                                }
                                buf[2 * i + 1] = tagHTML[i];
                            } else {   //分页
                                moreIndex = i;
                                moreListType = listType;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (attrName.equals("HTMLCODE")) {
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                        int markid = 0;
                        if (tagHTML[i].indexOf("<STYLES>") != -1) {
                            String style = "";
                            int sposi1 = tagHTML[i].indexOf("<STYLES>");
                            int eposi1 = tagHTML[i].indexOf("</STYLES>");
                            style = tagHTML[i].substring(sposi1 + 8, eposi1);
                            String a[] = new String[1];
                            a[0] = style;

                            if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                String aaa = buf[2 * i + 1];
                                aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                markid = Integer.parseInt(aaa);
                            }
                        }
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                        int sposi = tagHTML[i].indexOf("<CONTENT>");
                        int eposi = tagHTML[i].indexOf("</CONTENT>");
                        if (sposi != -1)
                            tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                        buf[2 * i + 1] = tagHTML[i];
                        processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                    } else {
                        buf[2 * i + 1] = tagHTML[i];
                    }
                } else {
                    System.out.println("标记名称为空");
                }
            }

            if (moreIndex >= 0) {            //在模板中有一个分页文章列表，并且只能有一个文章分页列表
                properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[moreIndex].trim()));
                String[] columnList = tagHTML[moreIndex].trim().split("\\<\\-\\-B_I_Z_W_I_N_K\\-\\-\\>");
                errcode = generateMoreColIndex(properties,appPath,sitename,columnList,tagHTML,buf,moreIndex,columnID,moreListType,username,siteid,samsiteid,option,templateName,modeltype);
            } else {                         //在模板中没有分页文章列表
                if (moreIndex == -1) {
                    String resultStr = "";
                    for (int j = 0; j < buf.length; j++) resultStr += buf[j];
                    if (model.getIncluded() == 1) {
                        int p = resultStr.toLowerCase().indexOf("<body");
                        if (p > -1) {
                            resultStr = resultStr.substring(p);
                            resultStr = resultStr.substring(resultStr.indexOf(">") + 1);
                        }
                        p = resultStr.toLowerCase().indexOf("</body>");
                        if (p > -1) resultStr = resultStr.substring(0, p);
                    } else {
                        resultStr = addColumnPageHeader(resultStr, columnID, siteid);
                    }

                    if (samsiteid > 0) {
                        errcode = generatePage(resultStr, filename, username, siteid, samsiteid, sitename, tempDir, option, languageType);
                    } else {
                        errcode = generatePage(resultStr, filename, username, siteid, sitename, tempDir, option, languageType);
                    }
                }
            }
        }

        //设置页面的发布标志位
        try {
            if (errcode == 0) {
                model.setPubFlag(1);
                model.setID(pubTemplateId);
            } else {
                model.setPubFlag(2);
                model.setID(pubTemplateId);
            }
            modelMgr.updatePubFlag(model);
        } catch (ModelException e) {
            e.printStackTrace();
        }

        return errcode;
    }

    public int CreateProgramPage(int columnID, int siteid,int sitetype, int samsiteid, String appPath, String sitename, String username, int imgflag, int option, int templateID) throws PublishException {
        int i = 0;
        int errcode = 0;
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        //IProgramManager programMgr = ProgramPeer.getInstance();

        //获得模板文件名
        String templateName = null;
        Model model = null;
        String filename = null;
        String tempDir = null;
        int modelType = 0;
        try {
            model = modelMgr.getModel(templateID);
            modelType = model.getIsArticle();
            templateName = model.getTemplateName();
            if (templateName == null || templateName.trim().length() == 0) {
                return -4;    //当前模板没有文件名
            }
        } catch (ModelException e) {
        }

        tempDir = File.separator + "_prog" + File.separator;
        filename = appPath + "sites" + File.separator + sitename + tempDir + templateName + ".jsp";

        String content = model.getContent();
        String[] buf = pubTemplate.TemplatePaser(content);

        if (buf.length > 1) {
            //模板内包括TAG
            String[] tag = new String[(buf.length - 1) / 2];
            for (i = 0; i < tag.length; i++) {
                tag[i] = buf[2 * i + 1];
                tag[i] = StringUtil.replace(tag[i], "[", "<");
                tag[i] = StringUtil.replace(tag[i], "]", ">");
            }

            //获取所有返回的HTML代码
            String[] tagHTML = pubCommon.ProcessTag(tag, 0, columnID, appPath, siteid, samsiteid, sitename, imgflag, model.getID(), model.getIsArticle(), "", true);

            int listType = 0;
            XMLProperties properties = null;
            String attrName = null;
            int html_code_mark_type = 0;
            for (i = 0; i < tag.length; i++) {
                if (tag[i].indexOf("<HTMLCODE>") == -1) {
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                    attrName = properties.getName();
                } else {
                    attrName = "HTMLCODE";
                    String mybuf = tag[i];
                    int sposi = mybuf.indexOf("<MARKTYPE>");
                    int eposi = mybuf.indexOf("</MARKTYPE>");
                    html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                }

                if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") || attrName.equals("SUBARTICLE_LIST")) {
                    try {
                        listType = Integer.parseInt(properties.getProperty(attrName.concat(".LISTTYPE")));
                        int innerFlag = Integer.parseInt(properties.getProperty(attrName.concat(".INNERHTMLFLAG")));
                        int way = 0;
                        if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST"))
                            way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));

                        if (way == 0) {
                            if (innerFlag == 0 && tagHTML[i].trim().length() > 0) {
                                String datastr = viewfileMgr.getAViewFile(listType).getContent();
                                String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                                String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                                tagHTML[i] = headstr + "\r\n" + tagHTML[i] + tailstr;
                            }
                            buf[2 * i + 1] = tagHTML[i];
                        }
                    } catch (Exception e) {
                    }
                } else if (attrName.equals("HTMLCODE")) {
                    tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                    tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");

                    if (tagHTML[i].indexOf("<STYLES>") != -1) {
                        String style = "";
                        int sposi1 = tagHTML[i].indexOf("<STYLES>");
                        int eposi1 = tagHTML[i].indexOf("</STYLES>");
                        style = tagHTML[i].substring(sposi1 + 8, eposi1);
                        String[] a = new String[1];
                        a[0] = style;
                    }

                    int markid = 0;
                    if (buf[(2 * i + 1)].indexOf("[MARKID]") != -1) {
                        String aaa = buf[(2 * i + 1)];
                        aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                        markid = Integer.parseInt(aaa);
                    }

                    if (html_code_mark_type == 18) {           //处理用户注册表单
                        userRegisterForm ugform = new userRegisterForm();
                        tagHTML[i] = ugform.createUserRegisterForm(sitename, cpool.getType(), tagHTML[i], markid, html_code_mark_type, 0);
                        ugform = null;
                    } else if (html_code_mark_type == 11) {     //处理信息检索显示页
                        tagHTML[i] = processTheTagSearch(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                    } else if (html_code_mark_type == 12) {     //购物车
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                    } else if (html_code_mark_type == 24) {      //处理地图标注信息
                        tagHTML[i] = processTheTagMapinfo(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                    } else {
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                    }
                    int sposi = tagHTML[i].indexOf("<CONTENT>");
                    int eposi = tagHTML[i].indexOf("</CONTENT>");
                    if ((sposi > -1) && (eposi > -1) && (eposi > sposi + 9))
                        tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                    buf[(2 * i + 1)] = tagHTML[i];
                    processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                    buf[(2 * i + 1)] = StringUtil.replace(buf[(2 * i + 1)], "&lsquare;", "[");
                    buf[(2 * i + 1)] = StringUtil.replace(buf[(2 * i + 1)], "&rsquare;", "]");
                } else if (attrName.equals("NAVBAR")) {
                    XMLProperties prop = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tagHTML[i]);
                    String navType_And_Columnid = prop.getProperty(prop.getName());
                    int posi = navType_And_Columnid.indexOf("-");
                    String navType_s = "";
                    if (posi > -1)
                        navType_s = navType_And_Columnid.substring(0, posi);
                    else
                        navType_s = navType_And_Columnid;
                    int navType = Integer.parseInt(navType_s);
                    if (html_code_mark_type == 11) {                            //检索导航棒
                        buf[2 * i + 1] = generateNavBarForProgram(navType, "");
                    }
                } else if (attrName.equals("LOGINDISPLAY")) {
                    int markid = 0;
                    if (buf[(2 * i + 1)].indexOf("[MARKID]") != -1) {
                        String aaa = buf[(2 * i + 1)];
                        aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                        markid = Integer.parseInt(aaa);
                    }
                    buf[2 * i + 1] = tagHTML[i];
                    if (cpool.getType().equalsIgnoreCase("mssql")) buf[2 * i + 1] = StringUtil.gb2iso(buf[2 * i + 1]);
                    processTagOfProgram(sitename, buf, markid, 19);
                } else {
                    buf[2 * i + 1] = tagHTML[i];
                }
            }
        }

        //发布页面并设置页面的发布标志位
        try {
            String resultStr = "";
            for (int j = 0; j < buf.length; j++) resultStr += buf[j];
            if (samsiteid > 0) {
                errcode = generatePage(resultStr, filename, username, siteid, samsiteid, sitename, "/_prog/", option, 3);
            } else {
                errcode = generatePage(resultStr, filename, username, siteid, sitename, "/_prog/", option, 3);
            }

            if (errcode == 0)
                model.setPubFlag(1);
            else
                model.setPubFlag(2);
            modelMgr.updatePubFlag(model);
        } catch (ModelException e) {
        }

        return errcode;
    }

    private String processTheTagSearch(String sitename, String buf, int markid, int marktype, int picarticleid) {
        String content = buf;
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
        String liststyle = properties.getProperty(properties.getName().concat(".LISTSTYLE"));
        int articlenum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLENUM")));
        int navbar = Integer.parseInt(properties.getProperty(properties.getName().concat(".NAVBAR")));
        String navbar_style = "";
        if (navbar > 0) navbar_style = generateNavBarForProgram(navbar, "");    //在列表默认位置设置导航条

        String prog = "    <%\r\n" +
                "        for (int i = 0; i < list.size(); i++) {\r\n" +
                "            Document doc = (Document) list.get(i);\r\n" +
                "            String maintitle = doc.get(\"maintitle\");\r\n" +
                "            maintitle = StringUtil.replace(maintitle,content,\"<font color=red>\"+content+\"</font>\");\r\n" +
                "            String vicetitle = doc.get(\"vicetitle\");\r\n" +
                "            vicetitle = StringUtil.replace(vicetitle,content,\"<font color=red>\"+content+\"</font>\");\r\n" +
                "            String id = doc.get(\"id\");\r\n" +
                "            String dirname = doc.get(\"dirname\");\r\n" +
                "            String summary = doc.get(\"summary\");\r\n" +
                "            summary = StringUtil.replace(summary,content,\"<font color=red>\"+content+\"</font>\");\r\n" +
                "            String saleprice = doc.get(\"saleprice\");\r\n" +
                "            String marketprice = doc.get(\"marketprice\");\r\n" +
                "            String vipprice = doc.get(\"vipprice\");\r\n" +
                "            String stocknum = doc.get(\"stock\");\r\n" +
                "            String smallpic = doc.get(\"smallpic\");\r\n" +
                "            String largepic = doc.get(\"largepic\");\r\n" +
                "            String specpic = doc.get(\"specpic\");\r\n" +
                "            String shoppingcarurl = doc.get(\"shoppingcarurl\");\r\n" +
                "            String createdate = doc.get(\"createdate\");\r\n" +
                "            SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd\");\r\n" +
                "            sitename=sitename.replaceAll(\"_\",\"\\\\.\");           \r\n" +
                "            String publishtime = null;\r\n" +
                "            try {\r\n" +
                "                publishtime = sdf.format(sdf.parse(doc.get(\"publishtime\")));\r\n" +
                "            } catch (ParseException e) {\r\n" +
                "            }\r\n" +
                "            String filename = doc.get(\"filename\");\r\n" +
                "            String url = \"\";\r\n" +
                "            if ((filename != null) && (!filename.equals(\"\")) && (filename.indexOf(\".html\") == -1) && (filename.indexOf(\".shtml\") == -1))\r\n" +
                "                url = \"http://\" + sitename + dirname + createdate + \"/download/\" + filename;\r\n" +
                "            else\r\n" +
                "                url = \"http://\" + sitename + dirname + createdate + \"/\" + id + \".shtml\";\r\n" +

                "    %>\r\n";

        liststyle = liststyle.substring(0, liststyle.length());
        String head = "";
        String tail = "";
        if (liststyle != null) {
            liststyle = liststyle.substring(0, liststyle.length() - 1);
            int posi = liststyle.indexOf("<" + "%%BEGIN%%" + ">");
            if (posi > -1) {
                head = liststyle.substring(0, posi);
                liststyle = liststyle.substring(posi + ("<" + "%%BEGIN%%" + ">").length());
            }
            posi = liststyle.indexOf("<" + "%%END%%" + ">");
            if (posi > -1) {
                tail = liststyle.substring(posi + ("<" + "%%END%%" + ">").length());
                liststyle = liststyle.substring(0, posi);
            }
            if (liststyle.indexOf("<%%URL%%>") > -1) liststyle = StringUtil.replace(liststyle, "<%%URL%%>", "<%=url%>");
            if (liststyle.indexOf("<%%DATA%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%DATA%%>", "<%=maintitle%>");
            if (liststyle.indexOf("<%%VICETITLE%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%VICETITLE%%>", "<%=(vicetitle!=null)?vicetitle:\"\"%>");
            if (liststyle.indexOf("<%%ASUMMARY%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%ASUMMARY%%>", "&nbsp;&nbsp;&nbsp;&nbsp;<%=(summary!=null)?summary:\"\"%>");
            if (liststyle.indexOf("<%%PT%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%PT%%>", "<%=publishtime%>");
            if (liststyle.indexOf("<%%SALEPRICE%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%SALEPRICE%%>", "<%=(saleprice!=null)?saleprice:\"\"%>");
            if (liststyle.indexOf("<%%MARKETPRICE%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%MARKETPRICE%%>", "<%=(marketprice!=null)?marketprice:\"\"%>");
            if (liststyle.indexOf("<%%VIPPRICE%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%VIPPRICE%%>", "<%=(vipprice!=null)?vipprice:\"\"%>");
            if (liststyle.indexOf("<%%STOCK%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%STOCK%%>", "<%=(stocknum!=null)?stocknum:\"\"%>");
            if (liststyle.indexOf("<%%SMALLPIC%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%SMALLPIC%%>", "<%=(smallpic!=null)?smallpic:\"\"%>");
            if (liststyle.indexOf("<%%LARGEPIC%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%LARGEPIC%%>", "<%=(largepic!=null)?largepic:\"\"%>");
            if (liststyle.indexOf("<%%SPECPIC%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%SPECPIC%%>", "<%=(specpic!=null)?specpic:\"\"%>");
            if (liststyle.indexOf("<%%SHOPPINGCARURL%%>") > -1)
                liststyle = StringUtil.replace(liststyle, "<%%SHOPPINGCARURL%%>", "abc");

            prog = prog + head + liststyle + tail + "\r\n";
        }

        prog = prog + "    <%}%>\r\n";
        prog = prog + navbar_style;
        return prog;
    }

    private String processTheTagMapinfo(String sitename, String buf, int markid, int marktype, int picarticleid) {
        String content = "";
        int width = 1200;
        int height = 800;
        double lat = 39.999373437683296d;
        double lng = 116.40782803259279d;
        int scale = 12;
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + buf);
        String width_s = properties.getProperty(properties.getName().concat(".WIDTH"));
        String height_s = properties.getProperty(properties.getName().concat(".HEIGHT"));
        String lat_s = properties.getProperty(properties.getName().concat(".CLAT"));
        String lng_s = properties.getProperty(properties.getName().concat(".CLNG"));
        String scale_s = properties.getProperty(properties.getName().concat(".SCALE"));

        if (width_s != null && width_s != "" && !width_s.equals("null")) width = Integer.parseInt(width_s);
        if (height_s != null && height_s != "" && !height_s.equals("null")) height = Integer.parseInt(height_s);
        if (lat_s != null && lat_s != "" && !lat_s.equals("null")) lat = Double.parseDouble(lat_s);
        if (lng_s != null && lng_s != "" && !lng_s.equals("null")) lng = Double.parseDouble(lng_s);
        if (scale_s != null && scale_s != "" && !scale_s.equals("null")) scale = Integer.parseInt(scale_s);

        content = "<div id=\"map_canvas\" style=\"width:" + width + "px;height:" + height + "px\"></div>\r\n<script type=\"text/javascript\">Demo.init(" + lat + "," + lng + "," + scale + ");</script>\r\n";

        return content;
    }

    public String generateNavBarForProgram(int navbarID, String filename) {
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        ViewFile viewfile = new ViewFile();
        String navbar_style = "";
        try {
            viewfile = viewfileMgr.getAViewFile(navbarID);
            navbar_style = viewfile.getContent();
            if (navbar_style != null) {
                if (navbar_style.indexOf("<%%NUMBER%%>") > -1) {
                    String normal_page_number_style = null;                      //一般页码格式
                    String current_page_number_style = null;                     //当前页码格式
                    String toleft_page_number_Style = null;                    //左翻页格式
                    String toright_page_number_style = null;                   //右翻页格式
                    String navbar_head = null;
                    String navbar_tail = null;

                    //处理<%%HEAD%%>

                    //处理<%%PREVIOUS%%>


                    if (toleft_page_number_Style != null)
                        toleft_page_number_Style = StringUtil.replace(toleft_page_number_Style, "<%%URL%%>", "search.jsp?currpage=<%=(current_yhds-1)*20+1%>&searchcontent=<%=content%>");
                    else
                        toleft_page_number_Style = "<a href=\"search.jsp?currpage=<%=(current_yhds-1)*20+1%>&searchcontent=<%=content%>\"><--</a>\r\n";

                    if (toright_page_number_style != null)
                        toright_page_number_style = StringUtil.replace(toright_page_number_style, "<%%URL%%>", "search.jsp?currpage=<%=(current_yhds+1)*20+1%>&searchcontent=<%=content%>");
                    else
                        toright_page_number_style = "<a href=\"search.jsp?currpage=<%=(current_yhds+1)*20+1%>&searchcontent=<%=content%>\">--></a>";

                    if (normal_page_number_style != null) {
                        normal_page_number_style = StringUtil.replace(normal_page_number_style, "<%%URL%%>", "search.jsp?currpage=<%=(current_yhds*20 + i+1)%>&searchcontent=<%=content%>");
                        normal_page_number_style = StringUtil.replace(normal_page_number_style, "<%%NUMBER%%>", "<%=(current_yhds*20 + i + 1)%>");
                    } else {
                        normal_page_number_style = "   <a href=\"search.jsp?currpage=<%=(current_yhds*20 + i+1)%>&searchcontent=<%=content%>\"><%=(current_yhds*20 + i + 1)%></a>";
                    }

                    if (current_page_number_style != null) {
                        current_page_number_style = StringUtil.replace(current_page_number_style, "<%%URL%%>", "#");
                        current_page_number_style = StringUtil.replace(current_page_number_style, "<%%CNUMBER%%>", "<%=(current_yhds*20 + i + 1)%>");
                    } else {
                        current_page_number_style = "   <%=(current_yhds*20 + i + 1)%>";
                    }

                    navbar_style = navbar_head + "\r\n<%\r\n" +
                            "int current_yhds = pages/20;\r\n" +
                            "if (current_yhds > 1) {%>\r\n" + toleft_page_number_Style + "\r\n" +
                            "<%} else {%>\r\n" +
                            "<%} if (extra==0) {\r\n" +
                            "for(int i=current_yhds*20; i<(current_yhds+1)*20; i++) {%>\r\n" +
                            "   <% if (pages ==i+1) {%>\r\n" + current_page_number_style + "\r\n" +
                            "   <%}else{%> \r\n" + normal_page_number_style +
                            "\r\n" +
                            "<%}}} else {" +
                            "     if (current_yhds<yhds-1) {\r\n" +
                            "         for(int i=0; i<20; i++) {%>\r\n" +
                            "           <% if (pages ==i+1) {%>\r\n" + current_page_number_style + "\r\n" +
                            "           <%}else{%> \r\n" + normal_page_number_style +
                            "     <%}}}else {\r\n" +
                            "     for(int i=(yhds-1)*20; i<(yhds-1)*20 + extra; i++) {%>\r\n" +
                            "           <% if (pages ==i+1) {%>\r\n" + current_page_number_style + "\r\n" +
                            "           <%} else {%> \r\n" + normal_page_number_style +
                            "<%}}}}\r\n" +
                            "if (current_yhds < yhds-1) {%>\r\n" + toright_page_number_style + "\r\n" +
                            "<%}%>\r\n" + navbar_tail + "\r\n";
                } else {
                    navbar_style = StringUtil.replace(navbar_style, "<%%HEAD%%>", "search.jsp?currpage=1&searchcontent=<%=content%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%PREVIOUS%%>", "search.jsp?currpage=<%=pages-1%>&searchcontent=<%=content%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%CURRENTPAGE%%>", "<%=pages%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%NEXT%%>", "search.jsp?currpage=<%=pages+1%>&searchcontent=<%=content%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%BOTTOM%%>", "search.jsp?currpage=<%=totalPages%>&searchcontent=<%=content%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%NUM%%>", "<%=num%>");
                    navbar_style = StringUtil.replace(navbar_style, "<%%PAGENUM%%>", "<%=totalPages%>");
                    if (navbar_style.indexOf("<%%GOTO%%>") > -1) {
                        StringBuffer tempbuf = new StringBuffer();
                        tempbuf.append("<script language=javascript>\r\n");
                        tempbuf.append("function keycheck(frm)\r\n");
                        tempbuf.append("{\r\n");
                        tempbuf.append("  if (window.event.keyCode == 13)\r\n");
                        tempbuf.append("  {\r\n");
                        tempbuf.append("    var page = frm.cmspage.value;\r\n");
                        tempbuf.append("    if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > <%=totalPages%>)))\r\n");
                        tempbuf.append("    {\r\n");
                        tempbuf.append("      alert('" + "请输入正确的页码！页码范围为：" + "1 - <%=totalPages%> + ');\r\n");
                        tempbuf.append("    }else{\r\n");
                        tempbuf.append("      if (page == '1')\r\n");
                        tempbuf.append("        document.location = 'search.jsp?currpage=1&searchcontent=<%=content%>';\r\n");
                        tempbuf.append("      else\r\n");
                        tempbuf.append("        document.location = 'search.jsp?currpage=' + page + '&searchcontent=<%=content%>';\r\n");
                        tempbuf.append("    }\r\n");
                        tempbuf.append("  }\r\n");
                        tempbuf.append("}\n\n");

                        tempbuf.append("function cmscheck(frm)\r\n");
                        tempbuf.append("{\r\n");
                        tempbuf.append("var page = frm.cmspage.value;\r\n");
                        tempbuf.append("if (page == '' || isNaN(page) || (!isNaN(page) && (parseInt(page) < 1 || parseInt(page) > <%=totalPages%>)))\r\n");
                        tempbuf.append("{\r\n");
                        tempbuf.append("  alert('" + "请输入正确的页码！页码范围为：" + "1 - <%=totalPages%>');\r\n");
                        tempbuf.append("}\r\n");
                        tempbuf.append("else\r\n");
                        tempbuf.append("{\r\n");
                        tempbuf.append("  if (page == '1')\r\n");
                        tempbuf.append("    document.location = 'search.jsp?currpage=1&searchcontent=<%=content%>';\r\n");
                        tempbuf.append("  else\r\n");
                        tempbuf.append("    document.location = 'search.jsp?currpage='+page +'&searchcontent=<%=content%>';\r\n");
                        tempbuf.append("}\r\n");
                        tempbuf.append("}\r\n");
                        tempbuf.append("</script>\r\n");
                        navbar_style = navbar_style + tempbuf.toString();

                        tempbuf = new StringBuffer();
                        tempbuf.append("cmscheck(this.form);");
                        navbar_style = StringUtil.replace(navbar_style, "<%%GOTO%%>", tempbuf.toString());
                    }

                    if (navbar_style.indexOf("<%%SELECT%%>") > -1) {
                        StringBuffer tempbuf = new StringBuffer();
                        tempbuf.append("<form name=\"form\">\r\n");
                        tempbuf.append("<select name=\"turnPage\" id=\"turnPageID\" size=\"1\" onchange=\"javascript:gotoThePage();\"></select>\r\n");
                        tempbuf.append("<script language=\"JavaScript\" type=\"text/JavaScript\">\r\n");
                        tempbuf.append("    document.getElementById(\"turnPageID\").options.length = 0;\r\n");
                        tempbuf.append("    for (var x = 0; x < <%=totalPages%>; x++)\r\n");
                        tempbuf.append("    {\r\n");
                        tempbuf.append("        document.getElementById(\"turnPageID\").options.add(new Option(x+1,x+1));\r\n");
                        tempbuf.append("        if (x+1 == <%=pages%>) {\r\n");
                        tempbuf.append("            this.form.turnPage[x].selected = \"true\";");
                        tempbuf.append("        }\r\n");
                        tempbuf.append("    }\r\n");
                        tempbuf.append("    function gotoThePage() {\r\n");
                        tempbuf.append("        window.location = \"search.jsp?currentPage=\" + this.form.turnPage.value + \"&searchcontent=<%=content%>\";\r\n");
                        tempbuf.append("    }\r\n");
                        tempbuf.append("</script>\r\n");
                        tempbuf.append("</form>\r\n");
                        navbar_style = StringUtil.replace(navbar_style, "<%%SELECT%%>", tempbuf.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return navbar_style;
    }

    private String processTheTagContent(String sitename, String buf, int markid, int marktype, int picarticleid) {
        String content = buf;
        String pics = "";
        String links = "";
        String texts = "";
        StringBuffer sb = new StringBuffer();

        IArticleManager articleMgr = ArticlePeer.getInstance();
        IArticleListManager articleListMgr = articleListPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();
        switch (marktype) {
            case 100:
                String str = "";//图片上滚
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容

                int sposi = content.indexOf("<CONTENT>");
                int eposi = content.indexOf("</CONTENT>");
                String ph = "";
                String pw = "";
                String tbuf = "";
                XMLProperties properties = null;
                String attrName = "";
                String artids = "";
                String s_scrolltitle = "";
                int scrolltitle = 1;
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    str = content;
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));

                    if (s_scrolltitle!=null) scrolltitle = Integer.parseInt(s_scrolltitle);

                    try {
                        if (artids!=null) {
                            if (artids.length() > 0) {
                                if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                                String[] arts = p.split(artids);
                                for (int j = 0; j < arts.length; j++) {
                                    int articleid = Integer.parseInt(arts[j]);
                                    Article article = articleMgr.getArticle(articleid);
                                    if (article != null) {
                                        Column column = columnManager.getColumn(article.getColumnID());
                                        String title = article.getMainTitle();
                                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + column.getExtname();
                                        String imageurl = null;
                                        if (article.getArticlepic() != null)
                                            imageurl = article.getDirName() + "images/" + article.getArticlepic();
                                        else {
                                            imageurl = "/_sys_images/scrollpic.jpg";
                                            title = "请在" + column.getCName() + "栏目下的文章\"" + title + "\"的文章图片区域上传图片";
                                        }
                                        if (ph != null && pw != null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else if (ph != null && pw == null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + 90 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else if (ph == null && pw != null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + 90 + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + 90 + "\" width=\"" + 90 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                    } else {
                                    }
                                }
                                str = StringUtil.replace(str, "<%%ARTICLE_LIST%%>", sb.toString());
                                str = StringUtil.replace(str, "<%%markid%%>", String.valueOf(markid));
                            }
                        }
                    } catch (ArticleException artexp) {
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        colexp.printStackTrace();
                    }
                }
                if (str.indexOf("<CONTENT>") != -1)
                    str = str.substring(str.indexOf("<CONTENT>") + 9, str.indexOf("</CONTENT>"));
                content = str;
                buf = str;
                break;
            case 101:                                                                                  //图片下滚
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
                    if (s_scrolltitle!=null) scrolltitle = Integer.parseInt(s_scrolltitle);
                    try {
                        if (artids!=null) {
                            if (artids.length() > 0) {
                                if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                                String[] arts = p.split(artids);
                                for (int j = 0; j < arts.length; j++) {
                                    int articleid = Integer.parseInt(arts[j]);
                                    Article article = articleMgr.getArticle(articleid);
                                    if (article != null) {
                                        Column column = columnManager.getColumn(article.getColumnID());
                                        String title = article.getMainTitle();
                                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + column.getExtname();
                                        String imageurl = null;
                                        if (article.getArticlepic() != null)
                                            imageurl = article.getDirName() + "images/" + article.getArticlepic();
                                        else {
                                            imageurl = "/_sys_images/scrollpic.jpg";
                                            title = "请在" + column.getCName() + "栏目下的文章\"" + title + "\"的文章图片区域上传图片";
                                        }
                                        if (ph != null && pw != null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else if (ph != null && pw == null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + 90 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else if (ph == null && pw != null)
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + 90 + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                        else
                                            sb.append("<a href=\"" + article_url + "\"><img height=\"" + 90 + "\" width=\"" + 90 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a>\r\n");
                                    } else {
                                    }
                                }
                                buf = StringUtil.replace(buf, "<%%ARTICLE_LIST%%>", sb.toString());
                                buf = StringUtil.replace(buf, "<%%markid%%>", String.valueOf(markid));
                            }
                        }
                    } catch (ArticleException artexp) {
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        colexp.printStackTrace();
                    }
                }
                break;
            case 102:                                                 //横向右滚动
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                StringBuffer st_fb = null;
                str = "";
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
                    if (s_scrolltitle!=null)
                        scrolltitle = Integer.parseInt(s_scrolltitle);
                    else
                        scrolltitle = 1;
                    st_fb = new StringBuffer();
                    st_fb.append("</tr><tr valign=\"top\">");
                    try {
                        if (artids!=null) {
                            if (artids.length() > 0) {
                                if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                                String[] arts = p.split(artids);
                                for (int j = 0; j < arts.length; j++) {
                                    int articleid = Integer.parseInt(arts[j]);
                                    Article article = articleMgr.getArticle(articleid);
                                    if (article != null) {
                                        Column column = columnManager.getColumn(article.getColumnID());
                                        String title = article.getMainTitle();
                                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + column.getExtname();
                                        String imageurl = null;
                                        if (article.getArticlepic() != null)
                                            imageurl = article.getDirName() + "images/" + article.getArticlepic();
                                        else {
                                            imageurl = "/_sys_images/scrollpic.jpg";
                                            title = "请在" + column.getCName() + "栏目下的文章\"" + title + "\"的文章图片区域上传图片";
                                        }
                                        if (ph != null && pw != null)
                                            sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                        else if (ph != null && pw == null)
                                            sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + 150 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                        else if (ph == null && pw != null)
                                            sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + 120 + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                        else
                                            sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + 120 + "\" width=\"" + 150 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                        if (scrolltitle == 1) {
                                            st_fb.append("<td><a href=\"" + article_url + "\">" + title + "</a></td>\r\n");
                                        }
                                    } else {
                                    }
                                }
                                if (st_fb.length() > 0) sb.append(st_fb);

                                buf = StringUtil.replace(buf, "<%%ARTICLE_LIST%%>", sb.toString());
                                buf = StringUtil.replace(buf, "<%%markid%%>", String.valueOf(markid));
                            }
                        }
                    } catch (ArticleException artexp) {
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        colexp.printStackTrace();
                    }
                }
            case 103:                                                        //图片左滚
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
                    if (s_scrolltitle != null && s_scrolltitle != "")
                        scrolltitle = Integer.parseInt(s_scrolltitle);
                    else
                        scrolltitle = 1;
                    st_fb = new StringBuffer();
                    st_fb.append("</tr><tr valign=\"top\">");
                    try {
                        if (artids != null) {
                            if (artids!=null) {
                                if (artids.length() > 0) {
                                    if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                    Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                                    String[] arts = p.split(artids);
                                    for (int j = 0; j < arts.length; j++) {
                                        int articleid = Integer.parseInt(arts[j]);
                                        Article article = articleMgr.getArticle(articleid);
                                        if (article != null) {
                                            Column column = columnManager.getColumn(article.getColumnID());
                                            String extname = "shtml";
                                            if (column != null) extname = column.getExtname();
                                            String title = article.getMainTitle();
                                            String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                            createdate_path = createdate_path.replaceAll("-", "");
                                            String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + extname;
                                            String imageurl = null;
                                            if (article.getArticlepic() != null)
                                                imageurl = article.getDirName() + "images/" + article.getArticlepic();
                                            else {
                                                imageurl = "/_sys_images/scrollpic.jpg";
                                                title = "请在" + column.getCName() + "栏目下的文章\"" + title + "\"的文章图片区域上传图片";
                                            }
                                            if (ph != null && pw != null)
                                                sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                            else if (ph != null && pw == null)
                                                sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + ph + "\" width=\"" + 150 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                            else if (ph == null && pw != null)
                                                sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + 120 + "\" width=\"" + pw + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                            else
                                                sb.append("<td><a href=\"" + article_url + "\"><img height=\"" + 120 + "\" width=\"" + 150 + "\" alt=\"" + title + "\" " + "src=\"" + imageurl + "\" /></a></td>\r\n");
                                            if (scrolltitle == 1) {
                                                st_fb.append("<td><a href=\"" + article_url + "\">" + title + "</a></td>\r\n");
                                            }
                                        } else {
                                        }
                                    }
                                    if (st_fb.length() > 0) sb.append(st_fb);

                                    buf = StringUtil.replace(buf, "<%%ARTICLE_LIST%%>", sb.toString());
                                    buf = StringUtil.replace(buf, "<%%markid%%>", String.valueOf(markid));
                                }
                            }
                        }
                        sposi = buf.indexOf("<CONTENT>");
                        eposi = buf.indexOf("</CONTENT>");
                        content = buf.substring(sposi + 9, eposi);
                    } catch (ArticleException artexp) {
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        colexp.printStackTrace();
                    }
                }
                buf = content;
                break;
            case 104:                                                 //图片幻灯
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                str = "";
                //去掉content字段的内容
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    str = content;
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);

                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\""+CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
                    if (s_scrolltitle != null && s_scrolltitle != "")
                        scrolltitle = Integer.parseInt(s_scrolltitle);
                    else
                        scrolltitle = 1;
                    try {
                        if (artids!=null) {
                            if (artids.length() > 0) {
                                List articles = new ArrayList();
                                if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                articles = articleListMgr.getArticleList(artids);

                                for (int j = 0; j < articles.size(); j++) {
                                    Article article = (Article) articles.get(j);
                                    if (article != null) {
                                        Column column = columnManager.getColumn(article.getColumnID());
                                        String title = article.getMainTitle();
                                        title = StringUtil.replace(title, "\"", "&quot;");
                                        title = StringUtil.replace(title, "'", "\'");
                                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + column.getExtname();
                                        String article_other_url = article.getOtherurl();
                                        if (article_other_url != null && article_other_url != "")
                                            article_url = article_other_url;
                                        String imageurl = null;
                                        if (article.getArticlepic() != null)
                                            imageurl = article.getDirName() + "images/" + article.getArticlepic();
                                        else {
                                            imageurl = "/_sys_images/scrollpic.jpg";
                                        }
                                        pics = pics + imageurl + "|";
                                        links = links + article_url + "|";
                                        texts = texts + title.replace("'","\'") + "|";
                                    }
                                }

                                //if (arts.length > 0) {
                                if (articles.size() > 0) {
                                    if (pics!=null)
                                        if (pics.length() > 1) pics = pics.substring(0, pics.length() - 1);
                                    if (links!=null)
                                        if (links.length() > 1) links = links.substring(0, links.length() - 1);
                                    if (texts!=null)
                                        if (texts.length() > 1) texts = texts.substring(0, texts.length() - 1);
                                    str = StringUtil.replace(str, "<%%pics%%>", pics);
                                    str = StringUtil.replace(str, "<%%links%%>", links);
                                    str = StringUtil.replace(str, "<%%texts%%>", texts);
                                    str = StringUtil.replace(str, "<%%markid%%>", String.valueOf(markid));
                                    if (scrolltitle == 0)
                                        str = StringUtil.replace(str, "var text_height=18", "var text_height=0");
                                }
                            }
                        }
                    } catch (ArticleException artexp) {
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        colexp.printStackTrace();
                    }
                }
                if (str.indexOf("<CONTENT>") != -1)
                    str = str.substring(str.indexOf("<CONTENT>") + 9, str.indexOf("</CONTENT>"));

                //---------------------------------------------zhongyan
                tbuf = str;
                content = str;
                buf = content;
                break;
            case 105:                                                 //小图引导大图，小图自动轮换
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                str = "";
                //去掉content字段的内容
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    str = content;
                    StringBuffer sbuf1 = new StringBuffer();
                    StringBuffer sbuf2 = new StringBuffer();
                    String smallimage = "";
                    String large_images = "window.ihead = [";
                    String largeimage = "";
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);

                    content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + content);
                    attrName = properties.getName();
                    artids = properties.getProperty(attrName.concat(".ARTICLEIDS"));
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    int linkflag = 0;
                    int thumbposi = 0;
                    String linkflag_s = properties.getProperty(attrName.concat(".LINK"));
                    if (linkflag_s != null) linkflag = Integer.parseInt(linkflag_s);
                    String thumbposi_s = properties.getProperty(attrName.concat(".THUMBPOSI"));
                    if (thumbposi_s != null) thumbposi = Integer.parseInt(thumbposi_s);
                    String xtqyh = properties.getProperty(attrName.concat(".XTQYH"));
                    String xtqyw = properties.getProperty(attrName.concat(".XTQYW"));
                    String dxjl = properties.getProperty(attrName.concat(".DXJL"));
                    String xth = properties.getProperty(attrName.concat(".XTH"));
                    String xtw = properties.getProperty(attrName.concat(".XTW"));
                    s_scrolltitle = properties.getProperty(attrName.concat(".SCROLLTITLE"));
                    if (s_scrolltitle != null && s_scrolltitle != "")
                        scrolltitle = Integer.parseInt(s_scrolltitle);
                    else
                        scrolltitle = 1;
                    try {
                        if (artids!=null) {
                            if (artids.length() > 0) {
                                sbuf1.append("<style type=\"text/css\">\r\n" +
                                        ".bizul\r\n" +
                                        "{\r\n" +
                                        "    PADDING-BOTTOM: 0px;\r\n" +
                                        "    LIST-STYLE-TYPE: none;\r\n" +
                                        "    MARGIN: 0px;\r\n" +
                                        "    PADDING-LEFT: 0px;\r\n" +
                                        "    PADDING-RIGHT: 0px;\r\n" +
                                        "    PADDING-TOP: 0px\r\n" +
                                        "}\r\n" +
                                        ".bizli\r\n" +
                                        "{\r\n" +
                                        "    PADDING-BOTTOM: 0px;\r\n" +
                                        "    LIST-STYLE-TYPE: none;\r\n" +
                                        "    MARGIN: 0px;\r\n" +
                                        "    PADDING-LEFT: 0px;\r\n" +
                                        "    PADDING-RIGHT: 0px;\r\n" +
                                        "    PADDING-TOP: 0px\r\n" +
                                        "}\r\n");
                                sbuf1.append("/*DIV区域的宽度和高度*/\r\n");
                                if (ph != null && pw != null) {
                                    sbuf1.append(".bizslider-con\r\n" +
                                            "{\r\n" +
                                            "    MARGIN-RIGHT: auto;\r\n" +
                                            "    MARGIN-LEFT: auto;\r\n" +
                                            "    WIDTH: ").append(pw).append("px;\r\n" +
                                            "    HEIGHT: ").append(ph).append("px\r\n" +
                                            "}\r\n");
                                } else if (ph == null && pw != null) {
                                    sbuf1.append(".bizslider-con\r\n" +
                                            "{\r\n" +
                                            "    MARGIN-RIGHT: auto;\r\n" +
                                            "    MARGIN-LEFT: auto;\r\n" +
                                            "    WIDTH: ").append(pw).append("px;\r\n" +
                                            "    HEIGHT: 350px\r\n" +
                                            "}\r\n");
                                } else if (ph != null && pw == null) {
                                    sbuf1.append(".bizslider-con\r\n" +
                                            "{\r\n" +
                                            "    MARGIN-RIGHT: auto;\r\n" +
                                            "    MARGIN-LEFT: auto;\r\n" +
                                            "    WIDTH: 600px;\r\n" +
                                            "    HEIGHT: ").append(ph).append("px\r\n" +
                                            "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider-con\r\n" +
                                            "{\r\n" +
                                            "    MARGIN-RIGHT: auto;\r\n" +
                                            "    MARGIN-LEFT: auto;\r\n" +
                                            "    WIDTH: 600px;\r\n" +
                                            "    HEIGHT: 350px\r\n" +
                                            "}\r\n");
                                }
                                sbuf1.append(".bizslider .bg\r\n" +
                                        "{\r\n" +
                                        "    POSITION: absolute;\r\n" +
                                        "    LINE-HEIGHT: 25px;\r\n" +
                                        "    BACKGROUND-COLOR: #ffffff;\r\n" +
                                        "    WIDTH: 100%;\r\n" +
                                        "    BOTTOM: 0px;\r\n" +
                                        "    HEIGHT: 25px;\r\n" +
                                        "    LEFT: 0px\r\n" +
                                        "}\r\n" +
                                        ".bizslider .desc\r\n" +
                                        "{\r\n" +
                                        "    POSITION: absolute;\r\n" +
                                        "    LINE-HEIGHT: 25px;\r\n" +
                                        "    BACKGROUND-COLOR: #ffffff;\r\n" +
                                        "    WIDTH: 100%;\r\n" +
                                        "    BOTTOM: 0px;\r\n" +
                                        "    HEIGHT: 25px;\r\n" +
                                        "    LEFT: 0px\r\n" +
                                        "}\r\n" +
                                        ".bizslider .bg\r\n" +
                                        "{\r\n" +
                                        "    FILTER: alpha(opacity=1);\r\n" +
                                        "    BACKGROUND-COLOR: #ffffff;\r\n" +
                                        "    opacity: 0.01\r\n" +
                                        "}\r\n" +
                                        ".bizslider .desc\r\n" +
                                        "{\r\n" +
                                        "    PADDING-LEFT: 6px;\r\n" +
                                        "    COLOR: #000;\r\n" +
                                        "    FONT-SIZE: 12px;\r\n" +
                                        "    TEXT-DECORATION: none\r\n" +
                                        "}\r\n" +
                                        ".bizslider .desc .sname\r\n" +
                                        "{\r\n" +
                                        "    FONT-WEIGHT: bold\r\n" +
                                        "}\r\n" +
                                        ".bizslider .desc:hover\r\n" +
                                        "{\r\n" +
                                        "    TEXT-DECORATION: none\r\n" +
                                        "}\r\n" +
                                        ".bizslider IMG\r\n" +
                                        "{\r\n" +
                                        "    DISPLAY: inline\r\n" +
                                        "}\r\n");
                                sbuf1.append("/*引导图片区域高度*/\r\n");
                                if (xtqyh != null) {
                                    sbuf1.append(".bizslider .slider-t\r\n" + "{\r\n");
                                    if (thumbposi == 0)
                                        sbuf1.append("    FLOAT: left;\r\n");
                                    else
                                        sbuf1.append("    FLOAT: right;\r\n");
                                    sbuf1.append("    HEIGHT: ").append(xtqyh).append("px;\r\n" + "    OVERFLOW: hidden\r\n" + "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider .slider-t\r\n" +
                                            "{\r\n");
                                    if (thumbposi == 0)
                                        sbuf1.append("    FLOAT: left;\r\n");
                                    else
                                        sbuf1.append("    FLOAT: right;\r\n");
                                    sbuf1.append("    HEIGHT: 350px;\r\n" + "    OVERFLOW: hidden\r\n" + "}\r\n");
                                }
                                sbuf1.append("/*滚动区高度*/\r\n");
                                if (ph != null) {
                                    sbuf1.append(".bizslider .slider-b\r\n" + "{\r\n");
                                    if (thumbposi == 0)
                                        sbuf1.append("    FLOAT: left;\r\n");
                                    else
                                        sbuf1.append("    FLOAT: right;\r\n");
                                    sbuf1.append("    HEIGHT: ").append(ph).append("px;\r\n" + "    OVERFLOW: hidden\r\n" + "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider .slider-b\r\n" + "{\r\n");
                                    if (thumbposi == 0)
                                        sbuf1.append("    FLOAT: left;\r\n");
                                    else
                                        sbuf1.append("    FLOAT: right;\r\n");
                                    sbuf1.append("    HEIGHT: 400px;\r\n" + "    OVERFLOW: hidden\r\n" + "}\r\n");
                                }
                                sbuf1.append("/*滚动区宽度*/\r\n");
                                if (pw != null) {
                                    sbuf1.append(".bizslider .slider-b\r\n" +
                                            "{\r\n" +
                                            "    POSITION: relative;\r\n" +
                                            "    WIDTH: ").append(pw).append("px;\r\n" +
                                            "    MARGIN-LEFT: 0px\r\n" +
                                            "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider .slider-b\r\n" +
                                            "{\r\n" +
                                            "    POSITION: relative;\r\n" +
                                            "    WIDTH: 600px;\r\n" +
                                            "    MARGIN-LEFT: 0px\r\n" +
                                            "}\r\n");
                                }

                                List articles = new ArrayList();
                                if (artids.endsWith(",")) artids = artids.substring(0, artids.length() - 1);
                                articles = articleListMgr.getArticleList(artids);
                                sbuf1.append("/*单个小图片LI的高度*/\r\n");
                                if (articles.size() > 0 && xtqyh != null) {
                                    sbuf1.append(".bizslider .slider-t LI\r\n" +
                                            "{\r\n" +
                                            "    POSITION: relative;\r\n" +
                                            "    PADDING-BOTTOM: 0px;\r\n" +
                                            "    HEIGHT: ").append(String.valueOf(Integer.parseInt(xtqyh) / articles.size())).append("px;\r\n" +
                                            "    MARGIN-LEFT: 0px\r\n" +
                                            "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider .slider-t LI\r\n" +
                                            "{\r\n" +
                                            "    POSITION: relative;\r\n" +
                                            "    PADDING-BOTTOM: 0px;\r\n" +
                                            "    HEIGHT: 72px;\r\n" +
                                            "    MARGIN-LEFT: 0px\r\n" +
                                            "}");
                                }
                                sbuf1.append("/*大图片宽度和高度*/\r\n");
                                if (w != null && h != null) {
                                    sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                            "{\r\n" +
                                            "    WIDTH: ").append(w).append("px;\r\n" +
                                            "    HEIGHT: ").append(h).append("px\r\n" +
                                            "}\r\n");
                                } else if (w == null && h != null) {
                                    if (pw != null) {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: ").append(pw).append("px;\r\n" +
                                                "    HEIGHT: ").append(h).append("px\r\n" +
                                                "}\r\n");
                                    } else {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: 600px;\r\n" +
                                                "    HEIGHT: ").append(h).append("px\r\n" +
                                                "}\r\n");
                                    }
                                } else if (w != null && h == null) {
                                    if (ph != null) {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: ").append(w).append("px;\r\n" +
                                                "    HEIGHT: ").append(ph).append("px\r\n" +
                                                "}\r\n");
                                    } else {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: ").append(w).append("px;\r\n" +
                                                "    HEIGHT: 350px\r\n" +
                                                "}\r\n");
                                    }
                                } else {
                                    if (pw != null && ph != null) {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: ").append(pw).append("px;\r\n" +
                                                "    HEIGHT: ").append(ph).append("px\r\n" +
                                                "}\r\n");
                                    } else if (pw == null && ph != null) {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: 600px;\r\n" +
                                                "    HEIGHT: ").append(ph).append("px\r\n" +
                                                "}\r\n");
                                    } else if (pw != null && ph == null) {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: ").append(pw).append("px;\r\n" +
                                                "    HEIGHT: 350px\r\n" +
                                                "}\r\n");
                                    } else {
                                        sbuf1.append(".bizslider .slider-b IMG\r\n" +
                                                "{\r\n" +
                                                "    WIDTH: 600px;\r\n" +
                                                "    HEIGHT: 350px\r\n" +
                                                "}\r\n");
                                    }
                                }
                                sbuf1.append("/*单个小图片的高度和宽度*/\r\n");
                                if (xth != null && xtw != null) {
                                    sbuf1.append(".bizslider .slider-t IMG\r\n" + "{\r\n" + "    FILTER: alpha(opacity=35);\r\n");
                                    if (thumbposi == 0) {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-LEFT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-LEFT: 5px;\r\n");
                                    } else {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-RIGHT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-RIGHT: 5px;\r\n");
                                    }
                                    sbuf1.append("    WIDTH: ").append(xtw).append("px;\r\n" + "    HEIGHT: ").append(xth).append("px;\r\n" + "    opacity: 0.35\r\n" + "}\r\n");
                                } else if (xth == null && xtw != null) {
                                    sbuf1.append(".bizslider .slider-t IMG\r\n" + "{\r\n" + "    FILTER: alpha(opacity=35);\r\n");
                                    if (thumbposi == 0) {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-LEFT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-LEFT: 5px;\r\n");
                                    } else {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-RIGHT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-RIGHT: 5px;\r\n");
                                    }
                                    sbuf1.append("    WIDTH: ").append(xtw).append("px;\r\n" + "    HEIGHT: 61px;\r\n" + "    opacity: 0.35\r\n" + "}\r\n");
                                } else if (xth != null && xtw == null) {
                                    sbuf1.append(".bizslider .slider-t IMG\r\n" + "{\r\n" + "    FILTER: alpha(opacity=35);\r\n");
                                    if (thumbposi == 0) {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-LEFT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-LEFT: 5px;\r\n");
                                    } else {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-RIGHT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-RIGHT: 5px;\r\n");
                                    }
                                    sbuf1.append("    WIDTH: 200px;\r\n" + "    HEIGHT: ").append(xth).append("px;\r\n" + "    opacity: 0.35\r\n" + "}\r\n");
                                } else {
                                    sbuf1.append(".bizslider .slider-t IMG\r\n" + "{\r\n" + "    FILTER: alpha(opacity=35);\r\n");
                                    if (thumbposi == 0) {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-LEFT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-LEFT: 5px;\r\n");
                                    } else {
                                        if (dxjl != null)
                                            sbuf1.append("    PADDING-RIGHT: ").append(dxjl).append("px;\r\n");
                                        else
                                            sbuf1.append("    PADDING-RIGHT: 5px;\r\n");
                                    }
                                    sbuf1.append("    WIDTH: 200px;\r\n" + "    HEIGHT: 61px;\r\n" + "    opacity: 0.35\r\n" + "}\r\n");
                                }

                                sbuf1.append(".bizslider .slider-t .on IMG\r\n" +
                                        "{\r\n" +
                                        "    FILTER: alpha(opacity=100);\r\n" +
                                        "    opacity: 1\r\n" +
                                        "}\r\n" +
                                        ".bizslider .slider-t .on SPAN\r\n" +
                                        "{\r\n" +
                                        "    BORDER-BOTTOM: #ff9900 2px solid;\r\n" +
                                        "    POSITION: absolute;\r\n" +
                                        "    BORDER-LEFT: #ff9900 2px solid;\r\n" +
                                        "    WIDTH: 101px;\r\n" +
                                        "    DISPLAY: block;\r\n" +
                                        "    MARGIN-LEFT: 5px;\r\n" +
                                        "    BORDER-TOP: #ff9900 2px solid;\r\n" +
                                        "    TOP: 0px;\r\n" +
                                        "    BORDER-RIGHT: #ff9900 2px solid;\r\n" +
                                        "    LEFT: 0px\r\n" +
                                        "}\r\n" +
                                        ".bizslider .slider-t .on B\r\n" +
                                        "{\r\n" +
                                        "    POSITION: absolute;\r\n" +
                                        "    WIDTH: 6px;\r\n" +
                                        "    HEIGHT: 6px;\r\n" +
                                        "    TOP: 26px;\r\n" +
                                        "    LEFT: 1px\r\n" +
                                        "}\r\n");
                                sbuf1.append("</style>");
                                sbuf1.append("<div id=\"biz_slider_con\" class=\"bizslider\">\r\n");
                                sbuf1.append("<ul class=\"bizul slider-b\">\r\n");
                                sbuf2.append("<ul class=\"bizul slider-t\">");
                                for (int j = 0; j < articles.size(); j++) {
                                    Article article = (Article) articles.get(j);
                                    if (article != null) {
                                        Column column = columnManager.getColumn(article.getColumnID());
                                        String title = article.getMainTitle();
                                        title = StringUtil.replace(title, "\"", "&quot;");
                                        title = StringUtil.replace(title, "'", "\'");
                                        String createdate_path = article.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        String article_url = article.getDirName() + createdate_path + "/" + article.getID() + "." + column.getExtname();
                                        String article_other_url = article.getOtherurl();
                                        if (article_other_url != null && article_other_url != "")
                                            article_url = article_other_url;

                                        if (article.getProductBigPic() != null) {
                                            largeimage = article.getDirName() + "images/" + article.getProductBigPic();
                                            large_images = large_images + "{src:'" + largeimage + "'},";
                                        } else {
                                            largeimage = "/_sys_images/scrollbigpic.jpg";
                                            large_images = large_images + "{src:'" + "/_sys_images/scrollbigpic.jpg" + "'},";
                                        }

                                        if (article.getProductPic() != null) {
                                            smallimage = article.getDirName() + "images/" + article.getProductPic();
                                        } else {
                                            smallimage = "/_sys_images/scrollpic.jpg";
                                        }

                                        //大图片循环显示
                                        if (j == 0) {
                                            sbuf1.append("<li class=\"bizli\"><a title=\"" + title + "\" target=\"_blank\" href=\"" + article_url + "\"><img alt=\"" + title + "\" src=\"" + largeimage + "\" border=\"0\" /></a> <a class=\"desc\" title=\"" + title + "\" target=\"_blank\" href=\"" + article_url + "\"\"><span class=\"sname\">" + title + "</span></a></li>\r\n");
                                        } else {
                                            sbuf1.append("<li class=\"bizli\" style=\"display: none\"><a title=\"" + title + "\" target=\"_blank\" href=\"" + article_url + "\"><img alt=\"" + title + "\" src=\"" + largeimage + "\"  border=\"0\" /></a> <a class=\"desc\" title=\"" + title + "\" target=\"_blank\" href=\"" + article_url + "\"\"><span class=\"sname\">" + title + "</span></a></li>\r\n");
                                        }

                                        //小图片循环显示
                                        sbuf2.append("<li class=\"bizli\"><a title=\"" + title + "\" target=\"_blank\" href=\"" + article_url + "\"><img alt=\"" + title + "\" src=\"" + smallimage + "\"  border=\"0\" /></a> </li>\r\n");

                                    }
                                }
                                //增加文章个数JS
                                String articleNum = "var articleNum = " + String.valueOf(articles.size()) + ";";
                                String speedtime = "var biztime = 1500;";
                                sbuf1.append("</ul>\r\n");
                                sbuf2.append("</ul>\r\n");
                                sbuf1.append(sbuf2.toString());
                                sbuf1.append("</div>\r\n");
                                //sbuf1.append(large_images + "];\r\n" + articleNum + "</script>\r\n");
                                sbuf1.append("<script type=\"text/javascript\">\r\n" + articleNum + "\r\n" + speedtime + "\r\n</script>\r\n");

                                if (str.indexOf("<CONTENT>") != -1) {
                                    str = str.substring(str.indexOf("<CONTENT>") + 9, str.indexOf("</CONTENT>"));
                                    try {
                                        sbuf1.append(new String(str.getBytes("GB2312"), "iso-8859-1"));
                                    } catch (UnsupportedEncodingException exp) {
                                        exp.printStackTrace();
                                    }
                                }
                            }
                            content = sbuf1.toString();
                        }
                    } catch (ArticleException artexp) {
                        try {
                            content = "生成特效结果图出现错误，您选择的文章可能没有大图片和小图片";
                            content = new String(content.getBytes("GB2312"), "iso-8859-1");
                        } catch (UnsupportedEncodingException exp) {
                            exp.printStackTrace();
                        }
                        artexp.printStackTrace();
                    } catch (ColumnException colexp) {
                        try {
                            content = "生成特效结果图出现错误，您选择的文章可能没有大图片和小图片";
                            content = new String(content.getBytes("GB2312"), "iso-8859-1");
                        } catch (UnsupportedEncodingException exp) {
                            exp.printStackTrace();
                        }
                        colexp.printStackTrace();
                    }
                } else {
                    try {
                        content = "请为生成特效结果图选择文章，并且文章必须包含大图片和小图片";
                        content = new String(content.getBytes("GB2312"), "iso-8859-1");
                    } catch (UnsupportedEncodingException exp) {
                        exp.printStackTrace();
                    }
                }
            case 120:                                                        //图片竖向上滚动
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    //去掉content字段的内容
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        if (content.indexOf("<%%ARTICLE_LIST%%>") != -1) {
                            List imglist = articleMgr.getArticleTurnPic(picarticleid);
                            StringBuffer imgsb = new StringBuffer();
                            int articleidtexiao = 0;
                            String imageurl = "";
                            String article_url = "";
                            if (imglist.size() == 0) {
                                content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                            } else {
                                for (int i = 0; i < imglist.size(); i++) {
                                    Turnpic tpic = (Turnpic) imglist.get(i);
                                    articleidtexiao = tpic.getArticleid();
                                    Article a = articleMgr.getArticle(articleidtexiao);
                                    if (a != null) {
                                        Column column = columnManager.getColumn(a.getColumnID());
                                        String title = a.getMainTitle();
                                        String createdate_path = a.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        article_url = a.getDirName() + createdate_path + "/" + a.getID() + "." + column.getExtname();
                                        imageurl = tpic.getPicname();
                                    }
                                    imgsb.append("<td><a href=\"" + imageurl + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" src=\"" + imageurl + "\" border=\"0\" /></a></td>\r\n");
                                }
                            }
                            content = StringUtil.replace(content, "<%%ARTICLE_LIST%%>", imgsb.toString());
                        }
                        if (content.indexOf("<%%markid%%>") != -1) {
                            content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;


            case 121:                                                        //图片竖向上滚动
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                //sposi = content.indexOf("<CONTENT>");
                //eposi = content.indexOf("</CONTENT>");
                //tbuf = content.substring(0,sposi+9);
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    //去掉content字段的内容
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        if (content.indexOf("<%%ARTICLE_LIST%%>") != -1) {
                            List imglist = articleMgr.getArticleTurnPic(picarticleid);
                            StringBuffer imgsb = new StringBuffer();
                            int articleidtexiao = 0;
                            String imageurl = "";
                            String article_url = "";
                            if (imglist.size() == 0) {
                                content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                            } else {
                                for (int i = 0; i < imglist.size(); i++) {
                                    Turnpic tpic = (Turnpic) imglist.get(i);
                                    articleidtexiao = tpic.getArticleid();
                                    Article a = articleMgr.getArticle(articleidtexiao);
                                    if (a != null) {
                                        Column column = columnManager.getColumn(a.getColumnID());
                                        String title = a.getMainTitle();
                                        String createdate_path = a.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        article_url = a.getDirName() + createdate_path + "/" + a.getID() + "." + column.getExtname();
                                        imageurl = tpic.getPicname();
                                    }
                                    imgsb.append("<td><a href=\"" + imageurl + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" src=\"" + imageurl + "\" border=\"0\" /></a></td>\r\n");
                                }
                            }
                            content = StringUtil.replace(content, "<%%ARTICLE_LIST%%>", imgsb.toString());
                        }
                        if (content.indexOf("<%%markid%%>") != -1) {
                            content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;
            case 122:                                                        //图片竖向上滚动
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                //sposi = content.indexOf("<CONTENT>");
                //eposi = content.indexOf("</CONTENT>");
                //tbuf = content.substring(0,sposi+9);
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    //去掉content字段的内容
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        if (content.indexOf("<%%ARTICLE_LIST%%>") != -1) {
                            List imglist = articleMgr.getArticleTurnPic(picarticleid);
                            StringBuffer imgsb = new StringBuffer();
                            int articleidtexiao = 0;
                            String imageurl = "";
                            String article_url = "";
                            if (imglist.size() == 0) {
                                content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                            } else {
                                for (int i = 0; i < imglist.size(); i++) {
                                    Turnpic tpic = (Turnpic) imglist.get(i);
                                    articleidtexiao = tpic.getArticleid();
                                    Article a = articleMgr.getArticle(articleidtexiao);
                                    if (a != null) {
                                        Column column = columnManager.getColumn(a.getColumnID());
                                        String title = a.getMainTitle();
                                        String createdate_path = a.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        article_url = a.getDirName() + createdate_path + "/" + a.getID() + "." + column.getExtname();
                                        imageurl = tpic.getPicname();
                                    }
                                    imgsb.append("<td><a href=\"" + imageurl + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" src=\"" + imageurl + "\" border=\"0\" /></a></td>\r\n");
                                }
                            }
                            content = StringUtil.replace(content, "<%%ARTICLE_LIST%%>", imgsb.toString());
                        }
                        if (content.indexOf("<%%markid%%>") != -1) {
                            content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;

            case 123:                                                        //图片横向左滚动
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                //去掉content字段的内容
                //sposi = content.indexOf("<CONTENT>");
                //eposi = content.indexOf("</CONTENT>");
                //tbuf = content.substring(0,sposi+9);
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    //去掉content字段的内容
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        if (content.indexOf("<%%ARTICLE_LIST%%>") != -1) {
                            List imglist = articleMgr.getArticleTurnPic(picarticleid);
                            StringBuffer imgsb = new StringBuffer();
                            int articleidtexiao = 0;
                            String imageurl = "";
                            String article_url = "";
                            if (imglist.size() == 0) {
                                content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                            } else {
                                for (int i = 0; i < imglist.size(); i++) {
                                    Turnpic tpic = (Turnpic) imglist.get(i);
                                    articleidtexiao = tpic.getArticleid();
                                    Article a = articleMgr.getArticle(articleidtexiao);
                                    if (a != null) {
                                        Column column = columnManager.getColumn(a.getColumnID());
                                        String title = a.getMainTitle();
                                        String createdate_path = a.getCreateDate().toString().substring(0, 10);
                                        createdate_path = createdate_path.replaceAll("-", "");
                                        article_url = a.getDirName() + createdate_path + "/" + a.getID() + "." + column.getExtname();
                                        imageurl = tpic.getPicname();
                                    }
                                    imgsb.append("<td><a href=\"" + imageurl + "\"><img height=\"" + ph + "\" width=\"" + pw + "\" src=\"" + imageurl + "\" border=\"0\" /></a></td>\r\n");
                                }
                            }
                            content = StringUtil.replace(content, "<%%ARTICLE_LIST%%>", imgsb.toString());
                        }
                        if (content.indexOf("<%%markid%%>") != -1) {
                            content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;
            case 124:                                                        //图片flash特效
                content = StringUtil.replace(content, "[", "<");
                content = StringUtil.replace(content, "]", ">");
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    //去掉content字段的内容
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;

                    try {
                        List imglist = articleMgr.getArticleTurnPic(picarticleid);
                        StringBuffer imgsb = new StringBuffer();
                        StringBuffer textsb = new StringBuffer();
                        if (picarticleid != 0) {
                            Article a = articleMgr.getArticle(picarticleid);
                            String title = a.getMainTitle();
                            if (content.indexOf("<%%pics%%>") != -1) {
                                //String imageurl = "";
                                if (imglist.size() == 0) {
                                    content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                                } else {
                                    for (int i = 0; i < imglist.size(); i++) {
                                        Turnpic tpic = (Turnpic) imglist.get(i);
                                        //imageurl = tpic.getPicname();
                                        if (imgsb.length() == 0) {
                                            imgsb = imgsb.append(tpic.getPicname());
                                            textsb = textsb.append(title);
                                        } else {
                                            imgsb = imgsb.append("|" + tpic.getPicname());
                                            textsb = textsb.append("|" + title);
                                        }
                                    }
                                }
                                content = StringUtil.replace(content, "<%%pics%%>", imgsb.toString());
                            }
                            if (content.indexOf("<%%links%%>") != -1) {
                                content = StringUtil.replace(content, "<%%links%%>", imgsb.toString());
                            }
                            if (content.indexOf("<%%texts%%>") != -1) {
                                content = StringUtil.replace(content, "<%%texts%%>", textsb.toString());
                            }
                            if (content.indexOf("<%%markid%%>") != -1) {
                                content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;

            case 125:                                                        //图片flash特效
                if (content.indexOf("[CONTENT]") != -1) {
                    content = StringUtil.replace(content, "[", "<");
                    content = StringUtil.replace(content, "]", ">");
                }
                ITagManager tagManager = TagManager.getInstance();
                String tihuancontent = "";
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        List imglist = articleMgr.getArticleTurnPic(picarticleid);
                        StringBuffer sb1 = new StringBuffer();
                        if (imglist.size() == 0) {
                            content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                        } else {
                            for (int i = 0; i < imglist.size(); i++) {
                                Turnpic tpic = (Turnpic) imglist.get(i);
                                sb1.append("bannerAD[" + i + "]='" + tpic.getPicname() + "'; \r\n");
                            }
                            if (content.indexOf("preloadedimages<i>") != -1) {
                                content = StringUtil.replace(content, "preloadedimages<i>", "preloadedimages[i]");
                            }
                            if (content.indexOf("bannerAD<i>") != -1) {
                                content = StringUtil.replace(content, "bannerAD<i>", "bannerAD[i]");
                            }
                            if (content.indexOf("bannerAD<adNum>") != -1) {
                                content = StringUtil.replace(content, "bannerAD<adNum>", "bannerAD[adNum]");
                            }
                            if (content.indexOf("<%%bannerAD%%>") != -1) {
                                content = StringUtil.replace(content, "<%%bannerAD%%>", sb1.toString());
                            }
                            if (content.indexOf("<%%markid%%>") != -1) {
                                content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;

            case 126:                                                        //图片flash特效
                if (content.indexOf("[CONTENT]") != -1) {
                    content = StringUtil.replace(content, "[", "<");
                    content = StringUtil.replace(content, "]", ">");
                }
                if (content.indexOf("<CONTENT>") != -1 || content.indexOf("<content>") != -1) {
                    sposi = content.indexOf("<CONTENT>");
                    eposi = content.indexOf("</CONTENT>");
                    tbuf = content.substring(0, sposi + 9);
                    tbuf = tbuf + content.substring(eposi);
                    //content = tbuf;
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tbuf);
                    attrName = properties.getName();
                    ph = properties.getProperty(attrName.concat(".PH"));
                    pw = properties.getProperty(attrName.concat(".PW"));
                    String h = properties.getProperty(attrName.concat(".H"));
                    String w = properties.getProperty(attrName.concat(".W"));
                    try {
                        List imglist = articleMgr.getArticleTurnPic(picarticleid);
                        StringBuffer sb1 = new StringBuffer();//firstimg
                        StringBuffer sb2 = new StringBuffer();//imglist
                        int imglistlength = 0;
                        imglistlength = imglist.size();
                        if (imglistlength == 0) {
                            content = content.substring(0, content.indexOf("<CONTENT>") + 9) + content.substring(content.indexOf("</CONTENT>"));
                        } else {
                            for (int i = 0; i < imglist.size(); i++) {
                                Turnpic tpic = (Turnpic) imglist.get(i);
                                if (i == 0) {
                                    sb1.append("<img src=\"" + tpic.getPicname() + "\" style=\"cursor: hand\" height=\"300\" width=\"200\" onclick=\"lastimg()\">");
                                }
                                if (i < 5) {
                                    sb2.append("<img id=\"img" + (i + 1) + "\" style=\"cursor: hand\" src=\"" + tpic.getPicname() + "\" onClick=\"selectimg('img" + (i + 1) + "')\">&nbsp;&nbsp;&nbsp;\r\n");
                                } else {
                                    sb2.append("<img id=\"img" + (i + 1) + "\" style=\"cursor: hand;display:none;\" src=\"" + tpic.getPicname() + "\" onclick=\"selectimg('img" + (i + 1) + "')\">&nbsp;&nbsp;&nbsp;\r\n");
                                }

                            }
                            if (content.indexOf("<%%firstimg%%>") != -1) {
                                content = StringUtil.replace(content, "<%%firstimg%%>", sb1.toString());
                            }
                            if (content.indexOf("<%%imglist%%>") != -1) {
                                content = StringUtil.replace(content, "<%%imglist%%>", sb2.toString());
                            }
                            if (content.indexOf("<%%markid%%>") != -1) {
                                content = StringUtil.replace(content, "<%%markid%%>", String.valueOf(markid));
                            }
                            if (content.indexOf("<%%imglistlength%%>") != -1) {
                                content = StringUtil.replace(content, "<%%imglistlength%%>", String.valueOf(imglistlength));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                buf = content;
                //-----------------------------------------------
        }

        return buf;
    }

    private void processTagOfProgram(String sitename, String[] buf, int markid, int tagid) {
        IProgramManager programMgr = ProgramPeer.getInstance();
        pageOfProgram pp = programMgr.getPageOfProgram(tagid);
        IMarkManager marks = markPeer.getInstance();
        mark m = null;

        if (pp.getHeader() != null) buf[0] = pp.getHeader() + buf[0];
        if (pp.getScript() != null) {                                //javascript语言
            int endheadposi = buf[0].toLowerCase().lastIndexOf("</head>");
            if (endheadposi > -1) {
                StringBuffer t1 = new StringBuffer();
                t1.append(buf[0].substring(0, endheadposi));
                t1.append(StringUtil.replace(pp.getScript(), "<%%sitename%%>", sitename));
                t1.append(buf[0].substring(endheadposi));
                buf[0] = t1.toString();
            }
        }

        //加入style文件
        if (markid > 0) {
            int endheadposi = buf[0].toLowerCase().lastIndexOf("</head>");
            if (endheadposi > -1) {
                try {
                    StringBuffer t1 = new StringBuffer();
                    t1.append(buf[0].substring(0, endheadposi));
                    m = marks.getAMark(markid);
                    String content = m.getContent();
                    if (content.indexOf("[STYLES]") != -1) {
                        String style = "";
                        int sposi1 = content.indexOf("[STYLES]");
                        int eposi1 = content.indexOf("[/STYLES]");
                        style = content.substring(sposi1 + 8, eposi1);
                        t1.append(style);
                    }
                    t1.append(buf[0].substring(endheadposi));
                    buf[0] = t1.toString();
                } catch (Exception e) {
                    System.out.println("" + e.toString());
                }
            }
        }
    }

    public int createHomePage(int siteid, int sitetype,int samsiteid, String appPath, String sitename, String username, int imgflag, int option, int templateID) throws PublishException {
        int errcode = 0;
        PublisTemplateFunc pubTemplate = PublisTemplateFunc.getInstance();
        PublishCommFunc pubCommon = PublishCommFunc.getInstance();
        IModelManager modelMgr = ModelPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();

        Tree columnTree = null;
        if (sitetype==1) {
            columnTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);
        } else {
            columnTree = TreeManager.getInstance().getSiteTree(siteid);
        }
        int webRootID = columnTree.getTreeRoot();

        Column column = null;
        try {
            column = columnManager.getColumn(webRootID);
        } catch (ColumnException uaee) {
            uaee.printStackTrace();
        }

        int i;
        int moreIndex = -1;
        int listType = 0;
        int languageType = column.getLanguageType();

        //获得模板文件名
        String templateName = "index";
        Model model = null;
        int modelType = 0;
        try {
            model = modelMgr.getModel(templateID);
            modelType = model.getIsArticle();
            if (modelType == 3) {
                templateName = model.getTemplateName();
            } else {
                if (samsiteid == 0) {               //非共享网站，获取默认的首页模板，比较获取的首页模板和默认首页模板是否一致
                    int defaultTemplateID = pubTemplate.getDefaultTemplateID(siteid, webRootID, 2);
                    if (templateID != defaultTemplateID) templateName = model.getTemplateName();
                }
            }
            if (templateName == null || templateName.trim().length() == 0) {
                return -4;    //当前模板没有文件名
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

        String localBaseDir = appPath + "sites" + File.separator + sitename;
        String filename = localBaseDir + File.separator + templateName + "." + column.getExtname();

        //获得模板内容
        try {
            if (model.getReferModelID() > 0) {
                templateID = model.getReferModelID();
                model = modelMgr.getModel(templateID);
            }
        } catch (ModelException e) {
            e.printStackTrace();
        }

        String content = model.getContent();
        int modeltype = model.getIsArticle();

        String[] buf = pubTemplate.TemplatePaser(content);
        if (buf.length > 1) {
            String[] tag = new String[(buf.length - 1) / 2];
            for (i = 0; i < tag.length; i++) {
                tag[i] = buf[2 * i + 1];
                tag[i] = StringUtil.replace(tag[i], "[", "<");
                tag[i] = StringUtil.replace(tag[i], "]", ">");
            }

            //处理文章模板中所有的TAG,返回所有TAG的处理结果
            String[] tagHTML = pubCommon.ProcessTag(tag, 0, webRootID, appPath, siteid, samsiteid, sitename, imgflag, templateID, modeltype, username, false);

            //处理返回的ARTICLE_LIST数据
            XMLProperties properties = null;
            String attrName = null;
            int html_code_mark_type = 0;
            for (i = 0; i < tag.length; i++) {
                if (tag[i].indexOf("<HTMLCODE>") == -1) {
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[i]));
                    attrName = properties.getName();
                } else {
                    attrName = "HTMLCODE";
                    String mybuf = tag[i];
                    int sposi = mybuf.indexOf("<MARKTYPE>");
                    int eposi = mybuf.indexOf("</MARKTYPE>");
                    html_code_mark_type = Integer.parseInt(mybuf.substring(sposi + 10, eposi));
                }
                if (attrName != null) {
                    if (attrName.equals("ARTICLE_LIST") || attrName.equals("COLUMN_LIST") || attrName.equals("TOP_STORIES") || attrName.equals("RECOMMEND_LIST")
                            || attrName.equals("SUBARTICLE_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("BROTHER_LIST")
                            || attrName.equals("COMMEND_ARTICLE")) {
                        try {
                            listType = Integer.parseInt(properties.getProperty(attrName.concat(".LISTTYPE")));
                            int innerFlag = Integer.parseInt(properties.getProperty(attrName.concat(".INNERHTMLFLAG")));
                            int way = 0;
                            if (attrName.equals("ARTICLE_LIST") || attrName.equals("SUBARTICLE_LIST") || attrName.equals("BROTHER_LIST") || attrName.equals("RECOMMEND_LIST")
                                    || attrName.equals("COLUMN_LIST") || attrName.equals("SUBCOLUMN_LIST") || attrName.equals("COMMEND_ARTICLE"))
                                way = Integer.parseInt(properties.getProperty(attrName.concat(".SELECTWAY")));

                            if (way == 0) {
                                if (innerFlag == 0 && tagHTML[i].trim().length() > 0) {
                                    String datastr = viewfileMgr.getAViewFile(listType).getContent();
                                    String headstr = datastr.substring(0, datastr.indexOf("<!--ROW-->"));
                                    String tailstr = datastr.substring(datastr.lastIndexOf("<!--ROW-->") + 10);
                                    tagHTML[i] = headstr + "\r\n" + tagHTML[i] + tailstr;
                                }
                                buf[2 * i + 1] = tagHTML[i];
                            } else {
                                moreIndex = i;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (attrName.equals("HTMLCODE")) {
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&lt;", "<");
                        tagHTML[i] = StringUtil.replace(tagHTML[i], "&gt;", ">");
                        int markid = 0;
                        if (tagHTML[i].indexOf("<STYLES>") != -1) {
                            String style = "";
                            int sposi1 = tagHTML[i].indexOf("<STYLES>");
                            int eposi1 = tagHTML[i].indexOf("</STYLES>");
                            style = tagHTML[i].substring(sposi1 + 8, eposi1);
                            String a[] = new String[1];
                            a[0] = style;
                            if (buf[2 * i + 1].indexOf("[MARKID]") != -1) {
                                String aaa = buf[2 * i + 1];
                                aaa = aaa.substring(aaa.indexOf("[MARKID]") + 8, aaa.lastIndexOf("_"));
                                markid = Integer.parseInt(aaa);
                            }
                        }
                        tagHTML[i] = processTheTagContent(sitename, tagHTML[i], markid, html_code_mark_type, 0);
                        int sposi = tagHTML[i].indexOf("<CONTENT>");
                        int eposi = tagHTML[i].indexOf("</CONTENT>");
                        if (sposi != -1)
                            tagHTML[i] = tagHTML[i].substring(sposi + 9, eposi);
                        buf[2 * i + 1] = tagHTML[i];

                        processTagOfProgram(sitename, buf, markid, html_code_mark_type);
                    } else {
                        buf[2 * i + 1] = tagHTML[i];
                    }
                }else {
                    System.out.println("TAG名字为空===" +tag[i]);
                }
            }

            //processTime = System.currentTimeMillis();

            //在模板中有一个分页文章列表，并且只能有一个文章分页列表
            if (moreIndex >= 0) {
                properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + StringUtil.iso2gb(tag[moreIndex]));
                String[] artList = tagHTML[moreIndex].split("\\<\\-\\-B_I_Z_W_I_N_K\\-\\-\\>");
                errcode = generateMoreIndex(properties,appPath,sitename,artList,tagHTML,buf,moreIndex,webRootID,listType,username,siteid,option,templateName,modelType,null);
            }
        }

        if (moreIndex == -1) {
            String resultStr = "";
            for (int j = 0; j < buf.length; j++) resultStr += buf[j];
            if (samsiteid > 0) {
                errcode = generatePage(resultStr, filename, username, siteid, samsiteid, sitename, File.separator, option, languageType);
            } else {
                errcode = generatePage(resultStr, filename, username, siteid, sitename, File.separator, option, languageType);
            }

        }

        //设置页面的发布标志位
        try {
            if (errcode == 0)
                model.setPubFlag(1);
            else
                model.setPubFlag(2);
            modelMgr.updatePubFlag(model);
        } catch (ModelException ex) {
            ex.printStackTrace();
        }

        return errcode;
    }

    public int CreateMenuTreePage(int columnID, int siteId, String appPath, String sitename, String username, int option, int templateID) throws PublishException {
        IColumnManager columnMgr = ColumnPeer.getInstance();

        //获得模板文件名
        String templateName = "menuTree";
        String menuBefore = "<link href=\"/images/sinopec.css\" rel=\"stylesheet\" type=\"text/css\" />" +
                "<script language=javascript>" +
                "function showM(i){" +
                "eval(\"m\"+i).style.display = \"\";" +
                "eval(\"icon\"+i).innerHTML=\"<a href='javascript:hideM(\"+i+\");'><img src='/images/icon-nav-hide.gif' border=0></a>\";" +
                "}" +
                "" +
                "function hideM(i){" +
                "eval(\"m\"+i).style.display = \"none\";" +
                "eval(\"icon\"+i).innerHTML=\"<a href='javascript:showM(\"+i+\");'><img src='/images/icon-nav-show.gif' border=0></a>\";" +
                "}" +
                "" +
                "function showArrow(i){" +
                "if(currentid != i)" +
                "eval(\"arr\"+i).style.display = \"\";" +
                "}" +
                "" +
                "function hideArrow(i){" +
                "if(currentid != i)" +
                "eval(\"arr\"+i).style.display = \"none\";" +
                "}" +
                "</script>" +
                "</head>" +
                "<body>" +
                "<table border=0 width=178 cellspacing=\"0\" cellpadding=\"0\"><tr>" +
                "<td width=\"178\" valign=\"top\">\r\n";

        String menuBody = "";
        String menuBodyBefore = "";
        String menuBodyBefore1 = "\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                "<tr>\r\n" +
                "<td width=\"15\" align=\"left\">\r\n";
        String menuBodyBefore2 = "";
        String menuBodyBefore2_1 = "<div id=\"icon";
        String menuBodyBefore2_2 = "\"><a href=\"javascript:showM(";
        String menuBodyBefore2_3 = ");\"><img src=\"/images/icon-nav-show.gif\" width=\"10\" height=\"10\" border=0 /></a></div>\r\n";
        String menuBodyBefore3 = "</td>\r\n" +
                "<td align=\"left\">\r\n" +
                "<a href=\"";
        String menuBodyBefore3_1 = "\" class=\"left-nav\">";
        String menuBodyBefore4 = "</a>" +
                "</td>\r\n" +
                "</tr>\r\n" +
                "</table>\r\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                "<tr>\r\n" +
                "<td><img src=\"/images/dotline-insite.gif\" width=\"178\" height=\"1\" /></td>\r\n" +
                "</tr>\r\n" +
                "</table>\r\n" +
                "<div id=\"m";
        String menuBodyBefore5 = "\" style=\"display:none\">";

        String menuBodyCenter = "";
        String menuBodyCenter1 = "\r\n<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                "<tr>\r\n" +
                "<td width=\"15\" align=\"left\">&nbsp;</td>\r\n" +
                "<td align=\"left\">\r\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                "<tr>\r\n" +
                "<td width=\"15\" align=\"left\">\r\n" +
                "<div id=\"arr";
        String menuBodyCenter2 = "\" style=\"display:none\"><img src=\"/images/arrow-gra-2.gif\" width=\"6\" height=\"5\" /></div>\r\n" +
                "</td>\r\n" +
                "<td align=\"left\">\r\n" +
                "<a href=\"";
        String menuBodyCenter2_1 = "\" class=\"left-nav\" onMouseOver=\"showArrow(";
        String menuBodyCenter3 = ");\"";
        String menuBodyCenter4 = "";
        String menuBodyCenter4_1 = " onMouseOut=\"hideArrow(";
        String menuBodyCenter4_2 = ");";
        String menuBodyCenter5 = "\">";
        String menuBodyCenter6 = "</a>\r\n" +
                "</td>\r\n" +
                "</tr>\r\n" +
                "</table>\r\n" +
                "</td>\r\n" +
                "</tr>\r\n" +
                "</table>\r\n" +
                "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\r\n" +
                "<tr>\r\n" +
                "<td><img src=\"/images/dotline-insite.gif\" width=\"178\" height=\"1\" /></td>\r\n" +
                "</tr>\r\n" +
                "</table>\r\n";
        String menuBodyEnd = "</div>\r\n";

        String menuEnd = "</td>" +
                "</tr>" +
                "</table>" +
                "<div id=\"m0\"></div>" +
                "<div id=\"arr0\"></div>" +
                "<div id=\"icon0\"></div>" +
                "<script language=javascript>" +
                "if(currentid != 0){if(currentid != currentpid){" +
                "eval(\"m\"+currentpid).style.display = \"\";" +
                "eval(\"icon\"+currentpid).innerHTML=\"<a href='javascript:hideM(\"+currentpid+\");'><img src='/images/icon-nav-hide.gif' border=0></a>\";" +
                "eval(\"arr\"+currentid).style.display = \"\";" +
                "}else{" +
                "eval(\"m\"+currentpid).style.display = \"\";" +
                "eval(\"icon\"+currentpid).innerHTML=\"<a href='javascript:hideM(\"+currentpid+\");'><img src='/images/icon-nav-hide.gif' border=0></a>\";" +
                "}}" +
                "</script>";

        int[] childs = TreeManager.getInstance().getChildsOfColumnId(columnID);
        if (childs != null) {
            for (int i = 0; i < childs.length; i++) {
                int currentCID = childs[i];
                String cname = "";
                String dirname = "";
                int columnid = 0;
                Column column = null;
                try {
                    column = columnMgr.getColumn(currentCID);
                    cname = StringUtil.gb2iso4View(column.getCName());
                    dirname = column.getDirName();
                    columnid = column.getID();
                } catch (ColumnException e) {
                    e.printStackTrace();
                }

                if ((cname != null) && (cname.toLowerCase().indexOf("include") == -1)) {
                    int[] subchilds = TreeManager.getInstance().getChildsOfColumnId(columnid);
                    if ((subchilds != null) && (subchilds.length > 0)) {
                        menuBodyBefore2 = menuBodyBefore2_1 + columnid + menuBodyBefore2_2 + columnid + menuBodyBefore2_3;
                    } else {
                        menuBodyBefore2 = "";
                    }
                    menuBodyBefore = menuBodyBefore1 + menuBodyBefore2 + menuBodyBefore3 + dirname + menuBodyBefore3_1 + cname + menuBodyBefore4 + columnid + menuBodyBefore5;

                    if ((subchilds != null) && (subchilds.length > 0)) {
                        menuBodyCenter = "";
                        for (int j = 0; j < subchilds.length; j++) {
                            int subCurrentCID = subchilds[j];
                            try {
                                column = columnMgr.getColumn(subCurrentCID);
                                cname = StringUtil.gb2iso4View(column.getCName());
                                dirname = column.getDirName();
                                columnid = column.getID();
                            } catch (ColumnException e) {
                                e.printStackTrace();
                            }
                            menuBodyCenter = menuBodyCenter + menuBodyCenter1 + columnid + menuBodyCenter2 + dirname + menuBodyCenter2_1 + columnid + menuBodyCenter3;
                            menuBodyCenter4 = menuBodyCenter4_1 + columnid + menuBodyCenter4_2;
                            menuBodyCenter = menuBodyCenter + menuBodyCenter4 + menuBodyCenter5 + cname + menuBodyCenter6;
                        }
                    } else {
                        menuBodyCenter = "";
                    }
                    menuBody = menuBody + menuBodyBefore + menuBodyCenter + menuBodyEnd;
                }
            }

            int languageType = 0;
            String tempDir = "";
            String filename = "";
            try {
                Column column = columnMgr.getColumn(columnID);
                tempDir = StringUtil.replace(column.getDirName(), "/", File.separator);
                filename = appPath + "sites" + File.separator + sitename + tempDir + templateName + "." + column.getExtname();
                languageType = column.getLanguageType();
            } catch (ColumnException e) {
                e.printStackTrace();
            }

            //在模板中没有分页文章列表
            String resultStr = menuBefore + menuBody + menuEnd;
            return generatePage(resultStr, filename, username, siteId, sitename, tempDir, option, languageType);
        } else {
            return -1;
        }
    }

    public int publish(String username, String localFileName, int siteid, String fileDir, int big5flag) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        File pubFile = new File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }
        int errcode = 0;

        List list=null;
        IFtpSetManager siteMgr = FtpSetting.getInstance();
        try {
            list = siteMgr.getFtpInfos(siteid);
        } catch (SiteInfoException e) {
            errcode = -31;
            e.printStackTrace();
            return errcode;
        }

        int len = list.size();
        for (int i = 0; i < len; i++) {
            FtpInfo ftpInfo = (FtpInfo) list.get(i);
            String siteIP = ftpInfo.getIp();
            String remoteDocRoot = ftpInfo.getDocpath();
            String ftpUser = ftpInfo.getFtpuser();
            String ftpPasswd = ftpInfo.getFtppwd();

            //ftpInfo.getPubway() ==0 表示是FTP多机发布。如果是1表示本机发布，如果是2表示其它方式发布
            if (ftpInfo.getPublishway() == 0) {
                if (ftpInfo.getFtptype() == 0) {
                    com.bizwink.net.ftp.FtpFileToDest ftpHandle = new com.bizwink.net.ftp.FtpFileToDest();
                    errcode = ftpHandle.transfer(siteIP, ftpUser, ftpPasswd, localFileName, fileDir, remoteDocRoot, big5flag);
                } else {
                    com.bizwink.net.sftp.FtpFileToDest ftpHandle = new com.bizwink.net.sftp.FtpFileToDest();
                    errcode = ftpHandle.transfer(siteIP, ftpUser, ftpPasswd, localFileName, fileDir, remoteDocRoot, big5flag);
                }
            }
            if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2) {
                Copy copyHandle = new Copy();
                errcode = copyHandle.copyFile(localFileName, remoteDocRoot, fileDir);
            }
            if (errcode < 0) {
                return errcode;
            }
        }
        return errcode;
    }

    //选择具体的主机发布
    public int publish(String username, String localFileName, int siteid, String fileDir, int big5flag, int hostID) {
        //初始化时，errcode是0，表示成功，如果出错，则将errcode置为负值
        File pubFile = new File(localFileName);
        if (!pubFile.exists()) {
            return -1;
        }
        int errcode = 0;

        IFtpSetManager siteMgr = FtpSetting.getInstance();
        FtpInfo ftpInfo;
        try {
            ftpInfo = siteMgr.getFtpInfo(hostID);
        } catch (SiteInfoException e) {
            errcode = -31;
            e.printStackTrace();
            return errcode;
        }

        String siteIP = ftpInfo.getIp();
        String remoteDocRoot = ftpInfo.getDocpath();
        String ftpUser = ftpInfo.getFtpuser();
        String ftpPasswd = ftpInfo.getFtppwd();

        //ftpInfo.getPubway() ==0 表示是FTP多机发布。如果是1表示本机发布，如果是2表示其它方式发布
        if (ftpInfo.getPublishway() == 0) {
            if (ftpInfo.getFtptype() == 0) {
                com.bizwink.net.ftp.FtpFileToDest ftpHandle = new com.bizwink.net.ftp.FtpFileToDest();
                errcode = ftpHandle.transfer(siteIP, ftpUser, ftpPasswd, localFileName, fileDir, remoteDocRoot, big5flag);
            } else {
                com.bizwink.net.sftp.FtpFileToDest ftpHandle = new com.bizwink.net.sftp.FtpFileToDest();
                errcode = ftpHandle.transfer(siteIP, ftpUser, ftpPasswd, localFileName, fileDir, remoteDocRoot, big5flag);
            }
            //errcode = ftpHandle.transfer(siteIP, ftpUser, ftpPasswd, localFileName, fileDir, remoteDocRoot, big5flag);
        }
        if (ftpInfo.getPublishway() == 1 || ftpInfo.getPublishway() == 2) {
            Copy copyHandle = new Copy();
            errcode = copyHandle.copyFile(localFileName, remoteDocRoot, fileDir);
        }
        if (errcode < 0) {
            return errcode;
        }
        return errcode;
    }

    //*****************************************************
    //from here add by Eric 2003.10.16
    //*****************************************************
    private static final String GET_SITE = "SELECT * FROM TBL_SiteIPinfo WHERE SiteID = ?";

    public Publish getSiteInfo(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Publish publish = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_SITE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                publish = load(rs);
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
        return publish;
    }

    public String getExtName(int columnID) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String extname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT extname FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, columnID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                extname = rs.getString(1);
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
        return extname;
    }

    private static final String GET_SITENAME = "SELECT siteid,SiteName FROM TBL_Siteinfo WHERE SiteID = ?";

    public String getSiteName(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String sitename = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_SITENAME);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                sitename = rs.getString("sitename");
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
        return sitename;
    }

    public String getCName(int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String cname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT cname FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                cname = rs.getString("cname");
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
        return cname;
    }

    public String getDirName(int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String dirname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT dirname FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                dirname = rs.getString(1);
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
        return dirname;
    }

    public int getPubFlag(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int pubflag = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT pubflag FROM tbl_siteinfo WHERE siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                pubflag = rs.getInt(1);
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
        return pubflag;
    }

    public String getUserName(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        String username = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT userid FROM tbl_members WHERE siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                username = rs.getString(1);
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
        return username;
    }

    public int getImageFlag(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int imgflag = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT imagesdir FROM tbl_siteinfo WHERE siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                imgflag = rs.getInt(1);
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
        return imgflag;
    }

    public int getSiteID(int articleid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int siteid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT tbl_article.id,tbl_article.columnid,tbl_column.id,tbl_column.siteid AS sid FROM tbl_article,tbl_column WHERE tbl_article.columnid=tbl_column.id AND tbl_article.id = ?");
            pstmt.setInt(1, articleid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                siteid = rs.getInt("sid");
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
        return siteid;
    }

    public int getSiteID(int parentid, int flag1, int flag2) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int siteid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT siteid FROM tbl_column WHERE parentid = ?");
            pstmt.setInt(1, parentid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                siteid = rs.getInt("siteid");
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
        return siteid;
    }

    public int getSiteID(int columnid, int flag) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int siteid = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT siteid FROM tbl_column WHERE id = ?");
            pstmt.setInt(1, columnid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                siteid = rs.getInt("siteid");
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
        return siteid;
    }

    public void writeLog(String sitename, String rootPath, String logLine) {
        String filename = (new Timestamp(System.currentTimeMillis())).toString().substring(0, 10);
        filename = rootPath + "log" + File.separator + sitename + File.separator + filename + ".log";
        try {
            File file = new File(rootPath + "log" + File.separator + sitename + File.separator);
            if (!file.exists()) {
                file.mkdirs();
            }

            File logFile = new File(filename);
            if (!logFile.exists()) {
                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(logLine + "");
                pw.close();
            } else {
                String content = "";
                String line;
                BufferedReader br = new BufferedReader(new FileReader(filename));
                while ((line = br.readLine()) != null) {
                    content += line + "";
                }
                br.close();

                PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
                pw.write(content + logLine + "");
                pw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断该文章内容是否为引用

    private static String SQL_CHECKARTICLECONTENT_REFERS = "SELECT id,usearticletype FROM tbl_refers_article t WHERE columnid = ? AND articleid = ?";

    public boolean checkArticleContentRefers(int articleid, int columnid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        boolean checkvalue = false;
        int getid = 0;
        int usearticletype = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_CHECKARTICLECONTENT_REFERS);
            pstmt.setInt(1, columnid);
            pstmt.setInt(2, articleid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                getid = rs.getInt(1);
                usearticletype = rs.getInt(2);
            }
            rs.close();
            pstmt.close();
            if (getid != 0 && usearticletype == 1)
                checkvalue = true;
        } catch (SQLException e) {
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
        return checkvalue;
    }

    Publish load(ResultSet rs) throws SQLException {
        Publish publish = new Publish();
        try {
            publish.setSiteID(rs.getInt("SiteID"));
            publish.setSiteIP(rs.getString("SiteIP"));
            publish.setDocPath(rs.getString("docpath"));
            publish.setFtpUser(rs.getString("ftpuser"));
            publish.setFtpPasswd(rs.getString("ftppasswd"));
            publish.setPublishWay(rs.getInt("publishway"));
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
        return publish;
    }
}
