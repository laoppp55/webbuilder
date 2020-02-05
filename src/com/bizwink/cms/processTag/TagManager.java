package com.bizwink.cms.processTag;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.*;
import java.text.*;
import java.util.regex.*;
import java.sql.Timestamp;

import com.bizwink.cms.orderArticleListManager.IOrderArticleListManager;
import com.bizwink.cms.orderArticleListManager.orderArticleException;
import com.bizwink.cms.orderArticleListManager.orderArticleListPeer;
import com.bizwink.cms.toolkit.companyinfo.CompanyinfoPeer;
import com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.register.*;
import com.bizwink.cms.xml.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.modelManager.IModelManager;
import com.bizwink.cms.modelManager.ModelPeer;
import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.modelManager.ModelException;
import com.bizwink.search.SearchFilesServlet;
import com.bizwink.program.Program;
import com.bizwink.program.IProgramManager;
import com.bizwink.program.ProgramPeer;
import com.bizwink.util.JSON_Str_To_ObjArray;
import com.bizwink.util.zTreeNodeObj;
import com.bizwink.webapps.articleonclick.IArticleOnclickManager;
import com.bizwink.webapps.articleonclick.ArticleOnclickPeer;
import com.bizwink.webapps.survey.define.Define;
import com.bizwink.webapps.survey.define.DefineException;
import com.bizwink.webapps.survey.define.DefinePeer;
import com.bizwink.webapps.survey.define.IDefineManager;
import com.bizwink.webapps.userfunction.IUserFunctionManager;
import com.bizwink.webapps.userfunction.UserFunctionPeer;
import info.monitorenter.cpdetector.io.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.jdom.input.SAXBuilder;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class TagManager implements ITagManager {
    PoolServer cpool;

    public TagManager(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static ITagManager getInstance() {
        return CmsServer.getInstance().getFactory().getTagManager();
    }

    public int getColumnNum(String content) {
        int columnNum = 0;

        Pattern p = Pattern.compile("<!\\-\\-BEGIN\\-\\->", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(content);
        while (m.find()) {
            columnNum++;
        }

        if (columnNum < 1) columnNum = 1;

        return columnNum;
    }

    public String[] getColumnString(String content) {
        String arr[];

        int columnNum = getColumnNum(content);
        content = "start" + content + "end";

        if (columnNum == 1) {
            content = StringUtil.replace(content, "<!--BEGIN-->", "");
            content = StringUtil.replace(content, "<!--END-->", "");
            arr = new String[columnNum];
            arr[0] = content;
        } else {
            arr = new String[2 * columnNum + 1];
        }

        int i = 0;
        int posi;
        while ((posi = content.indexOf("<!--BEGIN-->")) > -1) {
            int end = content.indexOf("<!--END-->");
            arr[i] = content.substring(0, posi);
            arr[i + 1] = content.substring(posi + 12, end);
            content = content.substring(end + 10);
            i = i + 2;
        }

        arr[0] = arr[0].substring(5);
        if (columnNum == 1)
            arr[0] = arr[0].substring(0, arr[0].length() - 3);
        else
            arr[2 * columnNum] = content.substring(0, content.length() - 3);

        return arr;
    }

    public String playMedia(int type, int articleID, XMLProperties properties, int columnID,int siteid) {
        String tagName = properties.getName();
        int mediatype = Integer.parseInt(properties.getProperty(tagName.concat(".MEDIATYPE")));
        String mediaposi = properties.getProperty(tagName.concat(".MEDIAPOSI"));
        String width_s = properties.getProperty(tagName.concat(".WIDTH"));
        String height_s = properties.getProperty(tagName.concat(".HEIGHT"));

        String mediaurl = "";
        StringBuffer buf = new StringBuffer();
        try {
            Article article = thearticle(articleID);
            String content = article.getContent();
            mediaurl = article.getMediafile();
            ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
            IColumnManager columnManager = ColumnPeer.getInstance();
            SiteInfo siteinfo = siteMgr.getSiteInfo(siteid);
            String sitename = siteinfo.getDomainName();

            //处理文章内容中的多媒体上传文件（新版）
            if (mediaurl==null) {
                Pattern p = Pattern.compile("\\[TAG\\]\\[MEDIA\\]\\[FILENAME\\][^\\[\\]]*\\[/FILENAME\\]\\[/MEDIA\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(content);
                while (m.find()) {
                    String tag = content.substring(m.start(), m.end());
                    tag = StringUtil.replace(tag, "[", "<");
                    tag = StringUtil.replace(tag, "]", ">");
                    tag = StringUtil.gb2iso4View(tag);
                    properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tag);
                    String filepath = properties.getProperty("MEDIA.FILENAME");
                    if (filepath != null) {
                        sitename = sitename.replace(".", "_");
                        int posi = filepath.indexOf(sitename);
                        if (posi > -1) {
                            filepath = filepath.substring(posi + sitename.length());
                            if (mediaurl == null) mediaurl = filepath;
                            //如果mediaurl不是以http://开头，是本站点URL，如果是以http://开头，则认为是跨站点的URL
                            if (mediaurl != null) {
                                if (!mediaurl.startsWith("http://") && !mediaurl.startsWith("mms://") && !mediaurl.startsWith(article.getDirName()))
                                    mediaurl = article.getDirName() + "images" + File.separator + mediaurl;

                                //根据多媒体文件的类型生成相应播放的HTML代码
                                if (mediatype == 1) {                 //flv
                                    buf.append("<object id=\"vcastr3\" type=\"application/x-shockwave-flash\" data=\"http://" + siteinfo.getDomainName() + "/_commons/vcastr3.swf\" width=\"" + width_s + "\" height=\"" + height_s + "\">\n" +
                                            "        <param name=\"movie\" value=\"http://" + siteinfo.getDomainName() + "/_commons/vcastr3.swf\" />\n" +
                                            "        <param name=\"allowFullScreen\" value=\"true\" />\n" +
                                            "        <param name=\"FlashVars\" value=\"xml=\n" +
                                            "        <vcastr>\n" +
                                            "        <channel>\n" +
                                            "        <item>\n" +
                                            "        <source>" + mediaurl + "</source>\n" +
                                            "        <duration></duration>\n" +
                                            "        <title></title>\n" +
                                            "        </item>\n" +
                                            "        </channel>\n" +
                                            "        <config>\n" +
                                            "        </config>\n" +
                                            "        <plugIns>\n" +
                                            "        <logoPlugIn>\n" +
                                            "        <url>http://" + siteinfo.getDomainName() + "/_commons/logoPlugIn.swf</url>\n" +
                                            "        <logoText></logoText>\n" +
                                            "        <logoTextAlpha>0.75</logoTextAlpha>\n" +
                                            "        <logoTextFontSize>30</logoTextFontSize>\n" +
                                            "        <logoTextLink></logoTextLink>\n" +
                                            "        <logoTextColor>0xffffff</logoTextColor>\n" +
                                            "        <textMargin>20 20 auto auto</textMargin>\n" +
                                            "        </logoPlugIn>\n" +
                                            "        </plugIns>\n" +
                                            "        </vcastr>\"\n" +
                                            "       />\n" +
                                            "      </object>\n");
                                } else if (mediatype == 3) {                                                      //wmv、mp4
                                    buf.append("<script language=javascript>\n");
                                    buf.append("     var Sys = {};\n");
                                    buf.append("     var ua = navigator.userAgent.toLowerCase();\n");
                                    buf.append("     var s;\n");
                                    buf.append("     (s = ua.match(/msie ([\\d.]+)/)) ? Sys.ie = s[1]:\n");
                                    buf.append("     (s = ua.match(/firefox\\/([\\d.]+)/)) ? Sys.firefox = s[1]:\n");
                                    buf.append("     (s = ua.match(/chrome\\/([\\d.]+)/)) ? Sys.chrome = s[1]:\n");
                                    buf.append("     (s = ua.match(/opera.([\\d.]+)/)) ? Sys.opera = s[1]:\n");
                                    buf.append("     (s = ua.match(/version\\/([\\d.]+).*safari/)) ? Sys.safari = s[1]:0;\n");
                                    buf.append("     if(Sys.ie){\n");
                                    //buf.append("         alert(\"IE\");\n");
                                    buf.append("document.write('<object width=\"" + width_s + "\" height=\"" + height_s + "\" classid=\"CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95\" codebase=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715\" standby=\"Loading Microsoft Windows Media Player components...\" type=\"application/x-oleobject\" hspace=\"5\"> ' + \n" +
                                            "'<param name=\"AutoRewind\" value=1>' +\n" +
                                            "'<param name=\"FileName\" value=\"" + mediaurl + "\"> ' +\n" +
                                            "'<param name=\"ShowControls\" value=\"1\"> ' +\n" +
                                            "'<param name=\"ShowPositionControls\" value=\"0\"> ' +\n" +
                                            "'<param name=\"ShowAudioControls\" value=\"1\"> ' +\n" +
                                            "'<param name=\"ShowTracker\" value=\"0\"> ' +\n" +
                                            "'<param name=\"ShowDisplay\" value=\"0\"> ' +\n" +
                                            "'<param name=\"ShowStatusBar\" value=\"0\"> ' +\n" +
                                            "'<param name=\"ShowGotoBar\" value=\"0\"> ' +\n" +
                                            "'<param name=\"ShowCaptioning\" value=\"0\"> ' +\n" +
                                            "'<param name=\"AutoStart\" value=1> '+\n" +
                                            "'<param name=\"Volume\" value=\"5000\"> '+\n" +
                                            "'<param name=\"AnimationAtStart\" value=\"0\"> '+\n" +
                                            "'<param name=\"TransparentAtStart\" value=\"0\"> '+ \n" +
                                            "'<param name=\"AllowChangeDisplaySize\" value=\"0\"> '+\n" +
                                            "'<param name=\"AllowScan\" value=\"0\"> '+\n" +
                                            "'<param name=\"EnableContextMenu\" value=\"0\"> '+\n" +
                                            "'<param name=\"ClickToPlay\" value=\"0\"> '+\n" +
                                            "'</object>');\n");
                                    buf.append("     } else {\n");
                                    buf.append("         document.write('FLV格式以外的其他视频格式文件需要特别多媒体播放插件进行播放！<br />');\n");
                                    buf.append("         document.write('<a href=\"" + mediaurl + "\">" + article.getMainTitle() + "</a>');\n");
                                    buf.append("     }\n");
                                    buf.append("     </script>\n");
                                } else if (mediatype == 2) {             //mp4
                                    buf.append("<video id=\"video_1\" class=\"video-js vjs-default-skin\" controls preload=\"none\" width=\"" + width_s + "\" height=\"" + height_s + "\" data-setup=\"{}\">");
                                    posi = mediaurl.indexOf(".");
                                    if (posi > -1) mediaurl = mediaurl.substring(0, posi) + ".mp4";
                                    buf.append("<source src=\"" + mediaurl + "\" type=\"video/mp4\">");
                                    buf.append("</video>");
                                } else {
                                    buf.append("不支持播放的视频文件类型");
                                }
                            } else {
                                buf.append("文章的视频文件内容为空，请上传视频文件或者录入视频文件所在的完整路径");
                            }
                        }
                    }
                }
            } else {
                Column column = columnManager.getColumn(columnID);
                mediaurl = "http://" + sitename + column.getDirName() + "images" + File.separator + mediaurl;
                //根据多媒体文件的类型生成相应播放的HTML代码
                if (mediatype == 1) {                 //flv
                    buf.append("<object id=\"vcastr3\" type=\"application/x-shockwave-flash\" data=\"http://" + siteinfo.getDomainName() + "/_commons/vcastr3.swf\" width=\"" + width_s + "\" height=\"" + height_s + "\">\n" +
                            "        <param name=\"movie\" value=\"http://" + siteinfo.getDomainName() + "/_commons/vcastr3.swf\" />\n" +
                            "        <param name=\"allowFullScreen\" value=\"true\" />\n" +
                            "        <param name=\"FlashVars\" value=\"xml=\n" +
                            "        <vcastr>\n" +
                            "        <channel>\n" +
                            "        <item>\n" +
                            "        <source>" + mediaurl + "</source>\n" +
                            "        <duration></duration>\n" +
                            "        <title></title>\n" +
                            "        </item>\n" +
                            "        </channel>\n" +
                            "        <config>\n" +
                            "        </config>\n" +
                            "        <plugIns>\n" +
                            "        <logoPlugIn>\n" +
                            "        <url>http://" + siteinfo.getDomainName() + "/_commons/logoPlugIn.swf</url>\n" +
                            "        <logoText></logoText>\n" +
                            "        <logoTextAlpha>0.75</logoTextAlpha>\n" +
                            "        <logoTextFontSize>30</logoTextFontSize>\n" +
                            "        <logoTextLink></logoTextLink>\n" +
                            "        <logoTextColor>0xffffff</logoTextColor>\n" +
                            "        <textMargin>20 20 auto auto</textMargin>\n" +
                            "        </logoPlugIn>\n" +
                            "        </plugIns>\n" +
                            "        </vcastr>\"\n" +
                            "       />\n" +
                            "      </object>\n");
                } else if (mediatype == 3) {                                                      //wmv、mp4
                    buf.append("<script language=javascript>\n");
                    buf.append("     var Sys = {};\n");
                    buf.append("     var ua = navigator.userAgent.toLowerCase();\n");
                    buf.append("     var s;\n");
                    buf.append("     (s = ua.match(/msie ([\\d.]+)/)) ? Sys.ie = s[1]:\n");
                    buf.append("     (s = ua.match(/firefox\\/([\\d.]+)/)) ? Sys.firefox = s[1]:\n");
                    buf.append("     (s = ua.match(/chrome\\/([\\d.]+)/)) ? Sys.chrome = s[1]:\n");
                    buf.append("     (s = ua.match(/opera.([\\d.]+)/)) ? Sys.opera = s[1]:\n");
                    buf.append("     (s = ua.match(/version\\/([\\d.]+).*safari/)) ? Sys.safari = s[1]:0;\n");
                    buf.append("     if(Sys.ie){\n");
                    //buf.append("         alert(\"IE\");\n");
                    buf.append("document.write('<object width=\"" + width_s + "\" height=\"" + height_s + "\" classid=\"CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95\" codebase=\"http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,5,715\" standby=\"Loading Microsoft Windows Media Player components...\" type=\"application/x-oleobject\" hspace=\"5\"> ' + \n" +
                            "'<param name=\"AutoRewind\" value=1>' +\n" +
                            "'<param name=\"FileName\" value=\"" + mediaurl + "\"> ' +\n" +
                            "'<param name=\"ShowControls\" value=\"1\"> ' +\n" +
                            "'<param name=\"ShowPositionControls\" value=\"0\"> ' +\n" +
                            "'<param name=\"ShowAudioControls\" value=\"1\"> ' +\n" +
                            "'<param name=\"ShowTracker\" value=\"0\"> ' +\n" +
                            "'<param name=\"ShowDisplay\" value=\"0\"> ' +\n" +
                            "'<param name=\"ShowStatusBar\" value=\"0\"> ' +\n" +
                            "'<param name=\"ShowGotoBar\" value=\"0\"> ' +\n" +
                            "'<param name=\"ShowCaptioning\" value=\"0\"> ' +\n" +
                            "'<param name=\"AutoStart\" value=1> '+\n" +
                            "'<param name=\"Volume\" value=\"5000\"> '+\n" +
                            "'<param name=\"AnimationAtStart\" value=\"0\"> '+\n" +
                            "'<param name=\"TransparentAtStart\" value=\"0\"> '+ \n" +
                            "'<param name=\"AllowChangeDisplaySize\" value=\"0\"> '+\n" +
                            "'<param name=\"AllowScan\" value=\"0\"> '+\n" +
                            "'<param name=\"EnableContextMenu\" value=\"0\"> '+\n" +
                            "'<param name=\"ClickToPlay\" value=\"0\"> '+\n" +
                            "'</object>');\n");
                    buf.append("     } else {\n");
                    buf.append("         document.write('FLV格式以外的其他视频格式文件需要特别多媒体播放插件进行播放！<br />');\n");
                    buf.append("         document.write('<a href=\"" + mediaurl + "\">" + article.getMainTitle() + "</a>');\n");
                    buf.append("     }\n");
                    buf.append("     </script>\n");
                } else if (mediatype == 2) {             //mp4
                    buf.append("<video id=\"video_1\" class=\"video-js vjs-default-skin\" controls preload=\"none\" width=\"" + width_s + "\" height=\"" + height_s + "\" data-setup=\"{}\">" + "\r\n");
                    int posi = mediaurl.indexOf(sitename);
                    if (posi > -1) {
                        mediaurl = mediaurl.substring(posi+sitename.length());
                        posi = mediaurl.indexOf(".");
                        if (posi > -1){
                            mediaurl = mediaurl.substring(0,posi) + ".mp4";
                            mediaurl = mediaurl.replace(File.separator,"/");
                            buf.append("<source src=\"" + mediaurl + "\" type=\"video/mp4\">" + "\r\n");
                        } else {
                            buf.append("你的浏览器不支持的视频格式：" + mediaurl + "\r\n");
                        }
                    } else {
                        buf.append("你的浏览器不支持的视频格式：" + mediaurl + "\r\n");
                    }
                    buf.append("</video>" + "\r\n");
                } else {
                    buf.append("不支持播放的视频文件类型：" + mediaurl + "\r\n");
                }
            }
        } catch (TagException exp) {
            exp.printStackTrace();
        } catch (SiteInfoException sexp) {
            sexp.printStackTrace();
        } catch (ColumnException exp) {

        }

        return buf.toString();
    }

    //处理推荐文章列表标记
    public String formatRecommendArticleList(int markType,int markID,XMLProperties properties, int articleID, int columnID, int siteid, String sitename, int imgflag, String username, int modeltype,String fragPath, boolean isPreview) throws TagException {
        String result;
        String tagName = properties.getName();
        int startnum = 0;
        int endnum = 0;

        try {
            startnum = Integer.parseInt(properties.getProperty(tagName.concat(".STARTARTNUM")));
            endnum = Integer.parseInt(properties.getProperty(tagName.concat(".ENDARTNUM")));
        }
        catch (NumberFormatException ignored) {
        }

        List articleList = recommendArticleListData(markID, properties, startnum, endnum, articleID, columnID, siteid);

        result = genArticleList(properties, articleList, articleID, columnID, siteid, sitename, username, modeltype,fragPath, isPreview);

        return result;
    }

    private List recommendArticleListData(int markid, XMLProperties properties, int startnum, int endnum, int currentArticleID, int defaultColumnID, int siteid) {
        List articleList = null;
        String selectColumns = ",";
        String tagName = properties.getName();
        int selectWay = 0;
        String selectway_str = properties.getProperty(tagName.concat(".SELECTWAY"));
        if (selectway_str!=null) selectWay = Integer.parseInt(selectway_str);
        int articleNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
        String columnIds = properties.getProperty(tagName.concat(".COLUMNIDS"));
        String columns = properties.getProperty(tagName.concat(".COLUMNS"));
        String orders = properties.getProperty(tagName.concat(".ORDER"));
        String time_range = properties.getProperty(tagName.concat(".ORDER_RANGE.TIME_RANGE"));
        String power_range = properties.getProperty(tagName.concat(".ORDER_RANGE.POWER_RANGE"));
        String vicepower_range = properties.getProperty(tagName.concat(".ORDER_RANGE.VICEPOWER_RANGE"));
        String number_range = properties.getProperty(tagName.concat(".ORDER_RANGE.NUMBER_RANGE"));

        IOrderArticleListManager orderArticleListManager = orderArticleListPeer.getInstance();
        try {
            articleList = orderArticleListManager.getRecommendArticleList(startnum,endnum,markid,siteid);
        } catch (orderArticleException exp) {
            exp.printStackTrace();
        }
        return articleList;
    }

    //处理ARTICLE_LIST标记
    public String formatArticleList(int markType,int markID,XMLProperties properties, int articleID, int columnID, int siteid, String sitename, int imgflag, String username, int modeltype,String fragPath, boolean isPreview) throws TagException {
        String result;
        String tagName = properties.getName();
        int startnum = 0;
        int endnum = 0;

        try {
            startnum = Integer.parseInt(properties.getProperty(tagName.concat(".STARTARTNUM")));
            endnum = Integer.parseInt(properties.getProperty(tagName.concat(".ENDARTNUM")));
        }
        catch (NumberFormatException ignored) {
        }

        List articleList = artListData(markType, properties, startnum, endnum, articleID, columnID, siteid);
        result = genArticleList(properties, articleList, articleID, columnID, siteid, sitename, username, modeltype,fragPath, isPreview);
        return result;
    }

    public String getHtmlMarkContent(int markid, int incflag, String content, String sitename, int siteID, int columnID, String username, String fragPath) throws TagException {
        int posi = content.indexOf("<body>");
        if (posi > -1) content = content.substring(posi + 6);
        posi = content.indexOf("</body>");
        if (posi > -1) content = content.substring(0, posi);
        content = content.replaceAll("/webbuilder/sites/" + sitename + "/", "/");
        if (content.indexOf("sites/") != -1) content = content.replaceAll("sites/" + sitename + "/", "/");
        String result = createIncludeFile(columnID, siteID, content, fragPath, username);

        return result;
    }

    private String genArticleList(XMLProperties properties, List articleList, int articleID, int columnID, int siteid, String sitename, String username,int modeltype, String fragPath, boolean isPreview) {
        String result;

        String tagName = properties.getName();
        int selectWay = 0;
        try {
            selectWay = Integer.parseInt(properties.getProperty(tagName.concat(".SELECTWAY")));
        }
        catch (NumberFormatException ignored) {
        }

        if (selectWay == 0 || isPreview)    //生成单页列表或预览
            result = getArticleListOnePage(properties, articleList, articleID, columnID, siteid, sitename,modeltype, username, fragPath, isPreview);
        else    //生成多页列表
            result = getArticleListPages(properties, articleList, siteid, sitename, modeltype,columnID);

        return result;
    }

    private String getArticleListOnePage(XMLProperties properties, List articleList, int articleID, int columnID, int siteID, String sitename, int modeltype,String username, String fragPath, boolean isPreview) {
        String result = "";
        String tagName = properties.getName();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        int articleNum = 0;
        int innerFlag = 0;
        int listType = 0;

        try {
            articleNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
            innerFlag = Integer.parseInt(properties.getProperty(tagName.concat(".INNERHTMLFLAG")));
            listType = Integer.parseInt(properties.getProperty(tagName.concat(".LISTTYPE")));
        }
        catch (NumberFormatException ignored) {
        }

        try {
            //获得样式列表的内容
            String content = viewfileMgr.getAViewFile(listType).getContent();
            int sposi = content.indexOf("<!--ROW-->");
            int eposi = content.lastIndexOf("<!--ROW-->");
            String temp = null;
            if (eposi > sposi + 10)
                temp = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));
            else
                temp = content;

            //获得文章列表栏数
            int columnNum = getColumnNum(temp);

            articleNum = (articleNum > articleList.size()) ? articleList.size() : articleNum;
            int rowNum = articleNum;
            if (columnNum > 1) {
                rowNum = (int) Math.ceil((double) articleNum / columnNum);
            }

            temp = StringUtil.replace(temp, "\r\n", "");
            temp = StringUtil.replace(temp, "<%%sitename%%>", sitename);

            for (int i = 0; i < rowNum; i++) {
                String arr[] = getColumnString(temp);
                for (int j = i * columnNum; j < (i + 1) * columnNum; j++) {
                    int p = 0;
                    if (columnNum > 1) p = (j - i * columnNum) * 2 + 1;

                    //删除多余的标记
                    if (j > articleNum - 1) {
                        arr[p] = "";
                        continue;
                    }

                    //替换碎片文件中的标记
                    Article article = (Article) articleList.get(j);
                    arr[p] = processMarknameInStyle(properties, article, articleID, arr[p], sitename, modeltype,siteID, 1, columnID, isPreview);
                }

                for (int j = 0; j < arr.length; j++) {
                    result = result + arr[j];
                }
            }
            result = result + "\r\n";

            if (!isPreview && innerFlag == 1) {
                if (result.trim().length() > 0)
                    result = content.substring(0, content.indexOf("<!--ROW-->")) + result + content.substring(content.lastIndexOf("<!--ROW-->") + 10);
                result = createIncludeFile(columnID, siteID, result, fragPath, username);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getArticleListPages(XMLProperties properties, List articleList, int siteID, String sitename,int modeltype, int columnID) {
        StringBuffer result = new StringBuffer();
        String tagName = properties.getName();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        int listType = 0;
        int articleNum = 0;
        try {
            listType = Integer.parseInt(properties.getProperty(tagName.concat(".LISTTYPE")));
            articleNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
        } catch (NumberFormatException ignored) {}

        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();
            String temp = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));

            temp = StringUtil.replace(temp, "<%%sitename%%>", sitename);

            int columnNum = getColumnNum(temp);                             //获得文章列表栏数
            int articleCount = articleList.size();                          //文章总数
            int pageNum = (int) Math.ceil((double) articleCount / articleNum);  //总页数
            String arr[] = getColumnString(temp);

            //if (pageNum > 50) pageNum = 50;                   //设置最多发布50个页面

            for (int i = 0; i < pageNum; i++) {
                int begin = i * articleNum;
                int end = (i + 1) * articleNum;
                for (int j = begin; j < end; j++) {
                    if (j > articleCount - 1) break;
                    Article article = (Article) articleList.get(j);
                    int p = 0;
                    if (columnNum > 1) p = ((j - begin) % columnNum) * 2 + 1;     //p表示该篇文章所在的列数
                    result.append(processMarknameInStyle(properties, article, 0, arr[p], sitename, modeltype, siteID, 2, columnID, false));
                    result.append("\r\n<--B_I_Z_W_I_N_K-->\r\n");
                }
                System.out.println("Total Pages：" + pageNum + "，" + articleNum + " lines in a page,current page is" + i + "!!!");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    private String getColumnListOnePage(List columnList, String sitename, String fragPath, String username, int columnID, int innerFlag, int siteID, boolean isPreview, int colNum, int listType) {
        String result = "";

        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        tagListElement element;

        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();
            String temp = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));

            //获得栏目列表栏数
            int columnNum = getColumnNum(temp);
            if (colNum != 0)      //colNum如果是分页列表，用户设定的栏目数;columnList是用户选定的参与列表的栏目
                colNum = (colNum > columnList.size()) ? columnList.size() : colNum;
            else
                colNum = columnList.size();

            int rowNum = colNum;
            if (columnNum > 1) {
                rowNum = (int) Math.ceil((double) colNum / columnNum);
            }

            temp = StringUtil.replace(temp, "\r\n", "");
            temp = StringUtil.replace(temp, "<%%sitename%%>", sitename);

            for (int i = 0; i < rowNum; i++) {
                String arr[] = getColumnString(temp);
                for (int j = i * columnNum; j < (i + 1) * columnNum; j++) {
                    int p = 0;
                    if (columnNum > 1) p = (j - i * columnNum) * 2 + 1;

                    //删除多余的标记
                    if (j > colNum - 1) {
                        arr[p] = "";
                        continue;
                    }

                    //替换碎片文件中的标记
                    element = (tagListElement) columnList.get(j);
                    arr[p] = processColMarknameInStyle(element, arr[p], 1);
                }

                for (int j = 0; j < arr.length; j++) {
                    result = result + arr[j];
                }
            }

            if (!isPreview && innerFlag == 1) {
                if (result.trim().length() > 0)
                    result = content.substring(0, content.indexOf("<!--ROW-->")) + result + content.substring(content.lastIndexOf("<!--ROW-->") + 10);
                result = createIncludeFile(columnID, siteID, result, fragPath, username);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getColumnListPages(List columnList, String sitename, int listType) {
        StringBuffer result = new StringBuffer();

        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        tagListElement element;

        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();
            String temp = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));

            temp = StringUtil.replace(temp, "\r\n", "");
            temp = StringUtil.replace(temp, "<%%sitename%%>", sitename);

            int columnNum = getColumnNum(temp);                                    //获得栏目列表栏数
            int columnCount = columnList.size();                                   //参与列表的栏目总数
            int pageNum = (int) Math.ceil((double) columnCount / columnNum);     //总页数
            String arr[] = getColumnString(temp);

            for (int i = 0; i < pageNum; i++) {
                int begin = i * columnNum;
                int end = (i + 1) * columnNum;

                for (int j = begin; j < end; j++) {
                    if (j > columnCount - 1) break;
                    element = (tagListElement) columnList.get(j);
                    int p = 0;
                    if (columnNum > 1) p = ((j - begin) % columnNum) * 2 + 1;     //p表示该篇文章所在的列数

                    result.append(processColMarknameInStyle(element, arr[p], 2));
                    result.append("\r\n<--B_I_Z_W_I_N_K-->\r\n");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    //生成碎片文件
    private String createIncludeFile(int columnID, int siteID, String content, String fragPath, String username) {
        IPublishManager publishMgr = PublishPeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        content = StringUtil.gb2iso4View(content);

        try {
            Column column = columnMgr.getColumn(columnID);
            String extName = column.getExtname().toLowerCase();
            String filename = fragPath.substring(fragPath.lastIndexOf(File.separator) + 1) + "." + extName;
            fragPath = fragPath.substring(0, fragPath.lastIndexOf(File.separator) + 1);

            File file = new File(fragPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            PrintWriter pw = new PrintWriter(new FileOutputStream(fragPath + filename));
            pw.write(content);
            pw.close();

            publishMgr.publish(username, fragPath + filename, siteID, "/includefile/", 1);

            if (extName.equals("jsp"))
                content = "<" + "%@include file=\"/includefile/" + filename + "\"%" + ">";
            else if (extName.equals("asp"))
                content = "<!--#include virtual=\"/includefile/" + filename + "\"-->";
            else if (extName.equals("shtml") || extName.equals("shtm"))
                content = "<!--#include virtual=\"/includefile/" + filename + "\"-->";
            else if (extName.equals("php"))
                content = "include('" + filename + "');";
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    //处理列表样式文件中的扩展属性，如<%%username%%>  type=1:单页  type=2:分页  type=3:相关文章  type=4:热点文章
    private String processMarknameInStyle(XMLProperties properties, Article article, int articleID, String style, String sitename, int modeltype,int siteID, int type, int pubCID, boolean isPreview) {
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        int useArticleType = -1;
        Column column = null;

        int columnID = article.getColumnID();
        try {
            if (pubCID > 0) useArticleType = columnMgr.getColumn(pubCID).getUseArticleType();
            column = columnMgr.getColumn(columnID);
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        int letterNum=0;
        int referID = article.getReferArticleID();
        String oldSitename = null;
        String oldDirName = null;

        try {
            if (referID > 0) {
                ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
                int oldColumnID = articleMgr.getArticle(referID).getColumnID();
                Column oldColumn = columnMgr.getColumn(oldColumnID);
                int oldSiteID = oldColumn.getSiteID();
                oldDirName = oldColumn.getDirName();
                oldSitename = siteMgr.getSiteInfo(oldSiteID).getDomainName();
            }

            //如果需要在文章列表中显示序号，处理样式字符串中的序号信息
            if (style.indexOf("<%%__num%%>") > -1) {
                style = StringUtil.replace(style, "<%%__num%%>", String.valueOf(article.getArticlenum()));
            }

            //处理主标题
            if (style.indexOf("<%%DATA%%>") > -1) {
                String maintitle = article.getMainTitle().trim();
                if (type == 1) {      //单页
                    int color = 0;
                    letterNum = 0;
                    int days = 0;
                    int daystyle = 0;

                    try {
                        color = Integer.parseInt(properties.getProperty(properties.getName().concat(".COLOR")));
                        letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".LETTERNUM")));
                        days = Integer.parseInt(properties.getProperty(properties.getName().concat(".NEW")));
                        if (days > 0) daystyle = Integer.parseInt(properties.getProperty(properties.getName().concat(".DAYSTYLE")));
                    } catch (NumberFormatException ignored) {}

                    //判断文章标题是否是图象
                    if (isImage(maintitle) && !isConvert2Image("<%%DATA%%>", style)) {
                        if (oldSitename != null)
                            maintitle = "<img src=http://" + oldSitename + oldDirName + "images/" + maintitle + " border=0>";
                        else {
                            if (!maintitle.startsWith("http://"))
                                maintitle = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + maintitle + " border=0>";
                        }
                    } else {
                        maintitle = processTitleSize(maintitle, letterNum);
                        if (article.getID() == articleID && color > 0) {
                            String content = viewfileMgr.getAViewFile(color).getContent();
                            style = StringUtil.replace(style, "<%%DATA%%>", content);
                        }

                        if (days > 0) {
                            long articleTime = article.getPublishTime().getTime();
                            long nowTime = System.currentTimeMillis();
                            if (nowTime - articleTime < days * 24 * 60 * 60 * 1000) {
                                String content = viewfileMgr.getAViewFile(daystyle).getContent();
                                content = content.replaceAll("/webbuilder/sites/", "");
                                if (content.indexOf("sites/") != -1) content = content.replaceAll("sites/", "");
                                style = StringUtil.replace(style, "<%%NEW%%>", content);
                            } else {
                                style = StringUtil.replace(style, "<%%NEW%%>", "");
                            }
                        }
                    }
                } else if (type == 2) {      //分页
                    letterNum = 0;
                    int days = 0;
                    int daystyle = 0;

                    try {
                        letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".LETTERNUM")));
                        days = Integer.parseInt(properties.getProperty(properties.getName().concat(".NEW")));
                        if (days > 0)
                            daystyle = Integer.parseInt(properties.getProperty(properties.getName().concat(".DAYSTYLE")));
                    } catch (NumberFormatException ignored) {
                    }

                    if (isImage(maintitle) && !isConvert2Image("<%%DATA%%>", style)) {
                        if (oldSitename != null)
                            maintitle = "<img src=http://" + oldSitename + oldDirName + "images/" + maintitle + " border=0>";
                        else {
                            if (!maintitle.startsWith("http://"))
                                maintitle = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + maintitle + " border=0>";
                        }
                    } else {
                        maintitle = processTitleSize(maintitle, letterNum);
                        if (days > 0) {
                            Calendar articlePubTime = Calendar.getInstance();
                            articlePubTime.setTimeInMillis(articlePubTime.getTimeInMillis());
                            articlePubTime.add(Calendar.DAY_OF_MONTH,days);
                            Calendar nowTime = Calendar.getInstance();
                            nowTime.setTimeInMillis(System.currentTimeMillis());
                            if (nowTime.before(articlePubTime)){
                                String content = viewfileMgr.getAViewFile(daystyle).getContent();
                                content = content.replaceAll("/webbuilder/sites/", "");
                                if (content.indexOf("sites/") != -1) content = content.replaceAll("sites/", "");
                                style = StringUtil.replace(style, "<%%NEW%%>", content);
                            } else {
                                style = StringUtil.replace(style, "<%%NEW%%>", "");
                            }
                        }
                    }
                } else if (type == 3){       //相关文章
                    if (isImage(maintitle) && !isConvert2Image("<%%DATA%%>", style)) {
                        if (oldSitename != null)
                            maintitle = "<img src=http://" + oldSitename + oldDirName + "images/" + maintitle + " border=0>";
                        else {
                            if (!maintitle.startsWith("http://"))
                                maintitle = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + maintitle + " border=0>";
                        }
                    } else {
                        letterNum = 0;
                        if (properties.getProperty(properties.getName().concat(".LETTERNUM")) != null)
                            letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".LETTERNUM")));

                        //相关文章改为全文检索后，处理相关文章的标题的时候需要转码 Modified by EricDu 2007-9-5 22:30
                        if (cpool.getType().equals("mssql") || cpool.getType().equals("sybase"))
                            maintitle = StringUtil.gb2iso(maintitle);
                        maintitle = processTitleSize(maintitle, letterNum);
                    }
                } else if (type == 4) {      //热点文章
                    if (isImage(maintitle) && !isConvert2Image("<%%DATA%%>", style)) {
                        if (oldSitename != null)
                            maintitle = "<img src=http://" + oldSitename + oldDirName + "images/" + maintitle + " border=0>";
                        else {
                            if (!maintitle.startsWith("http://"))
                                maintitle = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + maintitle + " border=0>";
                        }
                    } else {
                        letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_MAINTITLE")));
                        maintitle = processTitleSize(maintitle, letterNum);
                    }
                }
                style = StringUtil.replace(style, "<%%DATA%%>", maintitle);
            }

            //处理完整标题
            if (style.indexOf("<%%FULLTITLE%%>") > -1) {
                String maintitle = article.getMainTitle().trim();
                style = StringUtil.replace(style, "<%%FULLTITLE%%>", maintitle);
            }

            //处理文章状态
            if (style.indexOf("<%%ARTICLE_STATUS%%>") > -1) {
                int article_status = article.getStatus();
                style = StringUtil.replace(style, "<%%ARTICLE_STATUS%%>", String.valueOf(article_status));
            }

            //处理文章的相应下载
            if (style.indexOf("<%%DOWNFILE%%>") > -1) {
                String df = article.getDownfilename();
                DateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");
                String ymd = format.format(article.getCreateDate());
                if (df != null)
                    style = StringUtil.replace(style, "<%%DOWNFILE%%>", column.getDirName() + ymd + File.separator + "download" + File.separator + df);
                else
                    style = StringUtil.replace(style, "<%%DOWNFILE%%>", "#");
            }

            //处理副标题信息
            if (style.indexOf("<%%VICETITLE%%>") > -1) {
                letterNum = 0;
                if (properties.getProperty(properties.getName().concat(".ARTICLE_VICETITLE")) != null)
                    letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_VICETITLE")));

                String vicetitle = article.getViceTitle();
                if (vicetitle == null) vicetitle = "";
                if (!isImage(vicetitle))
                    vicetitle = processTitleSize(vicetitle, letterNum);
                else if (!isConvert2Image("<%%VICETITLE%%>", style)) {
                    if (oldSitename != null)
                        vicetitle = "<img src=http://" + oldSitename + oldDirName + "images/" + vicetitle + " border=0>";
                    else
                        vicetitle = toImage(vicetitle, sitename, article, siteID);
                }
                style = StringUtil.replace(style, "<%%VICETITLE%%>", vicetitle);
            }

            //处理文章来源
            if (style.indexOf("<%%ASOURCE%%>") > -1) {
                letterNum = 0;
                if (properties.getProperty(properties.getName().concat(".ARTICLE_SOURCE")) != null)
                    letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_SOURCE")));

                String source = article.getSource();
                if (source == null) source = "";
                if (!isImage(source))
                    source = processTitleSize(source, letterNum);
                else if (!isConvert2Image("<%%ASOURCE%%>", style)) {
                    if (oldSitename != null)
                        source = "<img src=http://" + oldSitename + oldDirName + "images/" + source + " border=0>";
                    else
                        source = toImage(source, sitename, article, siteID);
                }
                style = StringUtil.replace(style, "<%%ASOURCE%%>", source);
            }

            //处理作者信息
            if (style.indexOf("<%%AUTHOR%%>") > -1) {
                letterNum = 0;
                if (properties.getProperty(properties.getName().concat(".ARTICLE_AUTHOR")) != null)
                    letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_AUTHOR")));

                String author = article.getAuthor();
                if (author == null) author = "";
                if (!isImage(author))
                    author = processTitleSize(author, letterNum);
                else if (!isConvert2Image("<%%AUTHOR%%>", style)) {
                    if (oldSitename != null)
                        author = "<img src=http://" + oldSitename + oldDirName + "images/" + author + " border=0>";
                    else
                        author = toImage(author, sitename, article, siteID);
                }
                style = StringUtil.replace(style, "<%%AUTHOR%%>", author);
            }

            //处理文章摘要
            if (style.indexOf("<%%ASUMMARY%%>") > -1) {
                letterNum = 0;
                if (properties.getProperty(properties.getName().concat(".ARTICLE_SUMMARY")) != null)
                    letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_SUMMARY")));

                String summary = article.getSummary();
                if (summary == null) summary = "";
                summary = processTitleSize(summary, letterNum);
                style = StringUtil.replace(style, "<%%ASUMMARY%%>", summary);
            }

            //处理文章内容
            if (style.indexOf("<%%CONTENT%%>") > -1) {
                letterNum = 0;
                if (properties.getProperty(properties.getName().concat(".ARTICLE_CONTENT")) != null)
                    letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE_CONTENT")));

                String content = articleMgr.getArticle(article.getID()).getContent();
                if (content == null) content = "";
                content = processTitleSize(content, letterNum);
                style = StringUtil.replace(style, "<%%CONTENT%%>", content);
            }
            //-------------------------弹出图层
            if (style.indexOf("<%%POPLAYERS%%>") != -1) {
                Article articles = articleMgr.getArticle(article.getID());
                style = StringUtil.replace(style, "<%%POPLAYERS%%>", "onmouseover=\"divtanchu('img" + article.getID() + "'," + articles.getSalePrice() + "," + articles.getMarketPrice() + "," + articles.getMainTitle() + ",'" + articles.getDirName() + "/images/" + articles.getProductBigPic() + "')\" onmouseout=\"divquxiao()\" id=img" + article.getID());
            }
            //---------------------------------

            // 文章点机数
            if (style.indexOf("<%%CLICKNUM%%>") > -1) {
                String result = article.getClickNum() + "";
                style = style.replaceAll("<%%CLICKNUM%%>", result);

            }

            //处理文章URL
            if (style.indexOf("<%%URL%%>") > -1) {
                String articleURL=null;
                if (article.getColumnID() == pubCID) {
                    if (article.getUrltype() == 0) {
                        articleURL = getArticleURL(article,modeltype);
                    } else {
                        articleURL = article.getOtherurl();
                    }
                } else {    //当前发布的栏目与文章所在的栏目不同
                    if (columnMgr.getUseArticleTypeValue(pubCID, article.getReferedTargetId(), article.getID(), siteID)) {    //引用文章内容
                        articleURL = getArticleURL(article, article.getReferedTargetId(), 1, modeltype);
                    } else {
                        if (article.getReferedTargetId() != 0) {
                            articleURL = getArticleURL(article, pubCID, 0, siteID, modeltype);
                        } else {
                            articleURL = getArticleURL(article,modeltype);//引用文章链接
                        }
                    }
                }
                if (articleURL != "" && articleURL != null)
                    style = style.replaceAll("<%%URL%%>", articleURL);
                else
                    style = style.replaceAll("<%%URL%%>", "#");
            }

            //处理 加入购物车
            if (style.indexOf("<%%BUYCAR%%>") > -1) {
                style = style.replaceAll("<%%BUYCAR%%>", "/_commons/doshopping.jsp?num=1&pid=" + article.getID() + "&sitename=[%%sitename%%]");
            }

            //处理文章图片
            String articlepic = article.getArticlepic();
            if (style.indexOf("<%%ARTICLEPIC%%>") > -1) {
                if (articlepic != null) {
                    if (!isConvert2Image("<%%ARTICLEPIC%%>", style)) articlepic = toImage(articlepic, sitename, article, siteID);
                    style = StringUtil.replace(style, "<%%ARTICLEPIC%%>", articlepic);
                    //style = style.replaceAll("<%%ARTICLEPIC%%>", articlepic);
                } else {
                    style = StringUtil.replace(style, "<%%ARTICLEPIC%%>", "_zwtp.gif");
                    style = StringUtil.replace(style, "<%%ARTICLE_PATH%%>", "/");
                    //style = style.replaceAll("<%%ARTICLEPIC%%>", articlepic);
                }
            }

            String pt = article.getPublishTime().toString();
            if (style.indexOf("<%%PT%%>") > -1) {
                if (type == 4)      //热点文章
                {
                    String datestyle = properties.getProperty(properties.getName().concat(".ARTICLE_TIME"));
                    if (datestyle != null && datestyle.length() > 1) {
                        datestyle = StringUtil.replace(datestyle, ",", " ");
                        DateFormat format = new SimpleDateFormat(datestyle);
                        pt = StringUtil.gb2iso(format.format(article.getPublishTime()));
                    }
                } else {
                    pt = pt.substring(0, 10);
                }
                style = style.replaceAll("<%%PT%%>", pt);
            }

            pt = article.getPublishTime().toString();
            if (style.indexOf("<%%YEAR%%>") > -1)
                style = style.replaceAll("<%%YEAR%%>", pt.substring(0, 4));
            if (style.indexOf("<%%MONTH%%>") > -1)
                style = style.replaceAll("<%%MONTH%%>", pt.substring(5, 7));
            if (style.indexOf("<%%DAY%%>") > -1)
                style = style.replaceAll("<%%DAY%%>", pt.substring(8, 10));
            if (style.indexOf("<%%HOUR%%>") > -1)
                style = style.replaceAll("<%%HOUR%%>", pt.substring(11, 13));
            if (style.indexOf("<%%MINUTE%%>") > -1)
                style = style.replaceAll("<%%MINUTE%%>", pt.substring(14, 16));
            if (style.indexOf("<%%SECOND%%>") > -1)
                style = style.replaceAll("<%%SECOND%%>", pt.substring(17, 19));
            if (style.indexOf("<%%ENGLISH_MONTH%%>") > -1) {
                int month = Integer.parseInt(pt.substring(5, 7));
                String monthName = "";
                if (month == 1)
                    monthName = "January";
                else if (month == 2)
                    monthName = "February";
                else if (month == 3)
                    monthName = "March";
                else if (month == 4)
                    monthName = "April";
                else if (month == 5)
                    monthName = "May";
                else if (month == 6)
                    monthName = "June";
                else if (month == 7)
                    monthName = "July";
                else if (month == 8)
                    monthName = "August";
                else if (month == 9)
                    monthName = "September";
                else if (month == 10)
                    monthName = "October";
                else if (month == 11)
                    monthName = "November";
                else if (month == 12)
                    monthName = "December";

                style = style.replaceAll("<%%ENGLISH_MONTH%%>", monthName);
            }

            //取出文章所在栏目的中文名称，获取文章所在栏目的URL
            if (style.indexOf("<%%COLUMNURL%%>") > -1) {
                String columnUrl = getColumnURL(columnID,modeltype);
                style = style.replaceAll("<%%COLUMNURL%%>", columnUrl);
            }
            if (style.indexOf("<%%COLUMNNAME%%>") > -1) {
                String columnChName = getColumnChName(columnID);
                style = style.replaceAll("<%%COLUMNNAME%%>", columnChName);
            }

            //处理文章所在栏目的父栏目名称，父栏目URL
            Column pcolumn = columnMgr.getParentColumn(columnID);
            if (pcolumn != null) {
                if (style.indexOf("<%%PARENTCOLUMNURL%%>") > -1) {
                    style = style.replaceAll("<%%PARENTCOLUMNURL%%>", pcolumn.getDirName());
                }
                if (style.indexOf("<%%PARENTCOLUMNNAME%%>") > -1) {
                    style = style.replaceAll("<%%PARENTCOLUMNNAME%%>", pcolumn.getCName());
                }
                if (style.indexOf("<%%PARENTCOLUMNID%%>") > -1)
                    style = style.replaceAll("<%%PARENTCOLUMNID%%>", String.valueOf(pcolumn.getID()));
            }

            //处理文章所在栏目的一级栏目名称，一级栏目URL
            column = columnMgr.getFirstColumn(columnID);
            if (column != null) {
                if (style.indexOf("<%%FIRSTCOLUMNURL%%>") > -1) {
                    style = style.replaceAll("<%%FIRSTCOLUMNURL%%>", column.getDirName());
                }
                if (style.indexOf("<%%FIRSTCOLUMNNAME%%>") > -1) {
                    style = style.replaceAll("<%%FIRSTCOLUMNNAME%%>", column.getCName());
                }
                if (style.indexOf("<%%FIRSTCOLUMNID%%>") > -1)
                    style = style.replaceAll("<%%FIRSTCOLUMNID%%>", String.valueOf(pcolumn.getID()));
            }

            if (style.indexOf("<%%ARTICLEID%%>") > -1)
                style = style.replaceAll("<%%ARTICLEID%%>", String.valueOf(article.getID()));
            if (style.indexOf("<%%COLUMNID%%>") > -1)
                style = style.replaceAll("<%%COLUMNID%%>", String.valueOf(columnID));
            if (style.indexOf("<%%ARTICLE_PATH%%>") > -1) {
                if (article.getSiteID() != siteID) {
                    ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
                    String art_sitename = siteMgr.getSiteInfo(article.getSiteID()).getDomainName();
                    style = style.replaceAll("<%%ARTICLE_PATH%%>", "/webbuilder/sites/" + art_sitename + article.getDirName());
                } else {
                    style = style.replaceAll("<%%ARTICLE_PATH%%>", "/webbuilder/sites/" + sitename + article.getDirName());
                }
            }
            if (style.indexOf("<%%DOCLEVEL%%>") > -1)
                style = style.replaceAll("<%%DOCLEVEL%%>", String.valueOf(article.getDocLevel()));
            if (style.indexOf("<%%ARTICLE_VERSION%%>") > -1) {
                String articleversion = articleMgr.getArticleVersion(article.getID());
                style = StringUtil.replace(style, "<%%ARTICLE_VERSION%%>", articleversion.trim());
            }

            //替换商品属性
            if (style.indexOf("<%%PRODUCT_") > -1) {
                article = articleMgr.getArticle(article.getID());

                if (style.indexOf("<%%PRODUCT_SALEPRICE%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_SALEPRICE%%>", String.valueOf(article.getSalePrice()));
                if (style.indexOf("<%%PRODUCT_VIPPRICE%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_VIPPRICE%%>", String.valueOf(article.getVIPPrice()));
                if (style.indexOf("<%%PRODUCT_INPRICE%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_INPRICE%%>", String.valueOf(article.getInPrice()));
                if (style.indexOf("<%%PRODUCT_MARKETPRICE%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_MARKETPRICE%%>", String.valueOf(article.getMarketPrice()));
                if (style.indexOf("<%%PRODUCT_WEIGHT%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_WEIGHT%%>", String.valueOf(article.getProductWeight()));
                if (style.indexOf("<%%PRODUCT_STOCK%%>") > -1)
                    style = StringUtil.replace(style, "<%%PRODUCT_STOCK%%>", String.valueOf(article.getStockNum()));
                if (style.indexOf("<%%PRODUCT_BRAND%%>") > -1) {
                    if (article.getBrand() != null)
                        style = StringUtil.replace(style, "<%%PRODUCT_BRAND%%>", article.getBrand());
                    else
                        style = StringUtil.replace(style, "<%%PRODUCT_BRAND%%>", "");
                }
                String picname = article.getProductPic();
                if (style.indexOf("<%%PRODUCT_PIC%%>") > -1 && picname != null) {
                    if (!isConvert2Image("<%%PRODUCT_PIC%%>", style)) picname = toImage(picname, sitename, article, siteID);
                    style = StringUtil.replace(style, "<%%PRODUCT_PIC%%>", picname);
                }
                picname = article.getProductBigPic();
                if (style.indexOf("<%%PRODUCT_BIGPIC%%>") > -1 && picname != null) {
                    if (!isConvert2Image("<%%PRODUCT_BIGPIC%%>", style)) picname = toImage(picname, sitename, article, siteID);
                    style = StringUtil.replace(style, "<%%PRODUCT_BIGPIC%%>", picname);
                }
            }

            //替换相关性文章属性
            if (style.indexOf("<%%RELATED_") > -1 && article.getRelatedArtID() != null && article.getRelatedArtID().trim().length() > 0) {
                try {
                    int relatedID = Integer.parseInt(article.getRelatedArtID());
                    Article relatedArticle = articleMgr.getArticle(relatedID);
                    if (relatedArticle != null) {
                        int relatedColumnID = relatedArticle.getColumnID();

                        if (style.indexOf("<%%RELATED_URL%%>") > -1) {
                            column = columnMgr.getColumn(relatedColumnID);
                            String relatedURL = column.getDirName() + relatedID + "." + column.getExtname();
                            style = style.replaceAll("<%%RELATED_URL%%>", relatedURL);
                        }

                        if (style.indexOf("<%%RELATED_ID%%>") > -1)
                            style = style.replaceAll("<%%RELATED_ID%%>", String.valueOf(relatedID));

                        if (style.indexOf("<%%RELATED_DATA%%>") > -1)
                            style = StringUtil.replace(style, "<%%RELATED_DATA%%>", relatedArticle.getMainTitle().trim());

                        if (relatedArticle.getViceTitle() != null && style.indexOf("<%%RELATED_VICETITLE%%>") > -1)
                            style = StringUtil.replace(style, "<%%RELATED_VICETITLE%%>", relatedArticle.getViceTitle().trim());

                        if (relatedArticle.getSource() != null && style.indexOf("<%%RELATED_SOURCE%%>") > -1)
                            style = StringUtil.replace(style, "<%%RELATED_SOURCE%%>", relatedArticle.getSource().trim());

                        if (relatedArticle.getSummary() != null && style.indexOf("<%%RELATED_SUMMARY%%>") > -1)
                            style = StringUtil.replace(style, "<%%RELATED_SUMMARY%%>", relatedArticle.getSummary().trim());

                        if (relatedArticle.getAuthor() != null && style.indexOf("<%%RELATED_AUTHOR%%>") > -1)
                            style = StringUtil.replace(style, "<%%RELATED_AUTHOR%%>", relatedArticle.getAuthor().trim());

                        //替换相关文章的扩展属性
                        int p;
                        String temp = style;
                        List extendList = new ArrayList();
                        while ((p = temp.indexOf("<%%RELATED_")) > -1) {
                            String mark = temp.substring(p + 10, temp.indexOf("%%>"));
                            temp = temp.substring(temp.indexOf("%%>") + 3);
                            extendList.add(mark);
                        }

                        extendList = extendMgr.getArticleExtendValue(relatedID, extendList);
                        for (int i = 0; i < extendList.size(); i++) {
                            temp = (String) extendList.get(i);
                            String ename = temp.substring(0, temp.indexOf("="));
                            String value = temp.substring(temp.indexOf("=") + 1);
                            style = StringUtil.replace(style, "<%%RELATED" + ename + "%%>", value);
                        }
                    }
                }
                catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            //替换扩展属性
            if (style.indexOf("<%%") > -1 && style.indexOf("%%>") > -1) {
                List matchList = new ArrayList();
                Pattern p = Pattern.compile("\\<%%[^\\<\\>%]*%%\\>", Pattern.CASE_INSENSITIVE);
                Matcher matcher = p.matcher(style);
                while (matcher.find()) {
                    String matchStr = style.substring(matcher.start() + 3, matcher.end() - 3);
                    matchList.add(matchStr);
                }

                matchList = extendMgr.getArticleExtendValue(article.getID(), matchList);
                String xmlTemplate = extendMgr.getXMLTemplate(article.getColumnID());
                SAXBuilder builder = new SAXBuilder();
                try {
                    if (xmlTemplate!= null && xmlTemplate!="") {
                        Reader in = new StringReader(xmlTemplate);
                        org.jdom.Document doc = builder.build(in);

                        List nodeList = doc.getRootElement().getChildren();
                        ExtendAttr extend = null;
                        for (int i = 0; i < matchList.size(); i++) {
                            String temp = (String) matchList.get(i);
                            String ename = temp.substring(0, temp.indexOf("="));
                            extend = null;
                            for (int j = 0; j < nodeList.size(); j++) {
                                org.jdom.Element e = (org.jdom.Element) nodeList.get(j);
                                int attrtype = Integer.parseInt(e.getAttributeValue("type"));
                                if (attrtype == 5 && ename.equals(e.getName())) {
                                    extend = new ExtendAttr();
                                    extend.setCName(e.getText());
                                    extend.setEName(e.getName());
                                    extend.setControlType(type);
                                    extend.setDefaultValue(e.getAttributeValue("defaultval"));
                                    extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));
                                    break;
                                }
                            }
                            String value = temp.substring(temp.indexOf("=") + 1);
                            letterNum = 0;
                            if (properties.getProperty(properties.getName().concat("." + ename)) != null)
                                letterNum = Integer.parseInt(properties.getProperty(properties.getName().concat("." + ename)));
                            if (extend == null)                               //处理不是分类列表的扩展属性
                                value = processTitleSize(value, letterNum);
                            else {                                              //处理分类列表扩展属性
                                String listDefineValue = extend.getDefaultValue();
                                JSONObject jsStr = JSONObject.fromObject(listDefineValue);
                                JSONArray jsonArray = jsStr.getJSONArray("data");
                                List<zTreeNodeObj> zTreeNodeObjs = JSON_Str_To_ObjArray.Transfer_JsonStr_To_ObjArray(jsonArray);
                                for(int ii=0; ii<zTreeNodeObjs.size(); ii++) {
                                    zTreeNodeObj zTreeNodeObj = zTreeNodeObjs.get(ii);
                                    //System.out.println(zTreeNodeObj.getName());
                                }
                            }
                            style = StringUtil.replace(style, "<%%" + ename + "%%>", value);
                        }
                    }
                } catch (IOException exp) {
                    System.out.println("TagManager模块分析扩展属性出错");
                    //exp.printStackTrace();
                }

                //去掉多余的扩展属性标记
                matcher = p.matcher(style);
                if (matcher.find()) {
                    style = matcher.replaceAll("");
                }
            }
        } catch (Exception e) {
            System.out.println("TagManager模块处理标记内容出错");
            e.printStackTrace();
        }

        return style;
    }

    //处理列表样式文件中的扩展属性，如<%%username%%>  type=1:单页  type=2:分页
    private String processColMarknameInStyle(tagListElement element, String style, int type) {
        String str = style;
        try {
            str = str.replaceAll("<%%COLUMNNAME%%>", element.cnname);
            str = str.replaceAll("<%%COLUMNURL%%>", element.url);
            str = str.replaceAll("<%%COLUMNDESC%%>", element.desc);
            str = str.replaceAll("<%%COLUMNID%%>", String.valueOf(element.columnID));
            str = str.replaceAll("<%%PARENTCOLUMNNAME%%>", element.pcnname);
            str = str.replaceAll("<%%PARENTCOLUMNURL%%>", element.purl);
            str = str.replaceAll("<%%PARENTCOLUMNDESC%%>", element.pdesc);
            str = str.replaceAll("<%%PARENTCOLUMNID%%>", String.valueOf(element.pcolumnID));
            str = str.replaceAll("<%%FIRSTCOLUMNNAME%%>", element.fcnname);
            str = str.replaceAll("<%%FIRSTCOLUMNURL%%>", element.furl);
            str = str.replaceAll("<%%FIRSTCOLUMNDESC%%>", element.fdesc);
            str = str.replaceAll("<%%FIRSTCOLUMNID%%>", String.valueOf(element.fcolumnID));
            str = str.replaceAll("<%%PT%%>", element.pt.toString().substring(0, 10));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private String processTitleSize(String title, int size) {
        if (size > 0 && title != null) {
            if (cpool.getType().equals("mssql") || cpool.getType().equals("sybase"))
                title = StringUtil.iso2gbindb(title);
            String oldTitle = title;

            StringBuffer head = new StringBuffer();
            StringBuffer end = new StringBuffer();
            while (title.startsWith("<")) {
                head.append(title.substring(0, title.indexOf(">") + 1));
                title = title.substring(title.indexOf(">") + 1);
            }
            while (title.endsWith(">") && title.indexOf("<") > -1) {
                end.insert(0, title.substring(title.lastIndexOf("<")));
                title = title.substring(0, title.lastIndexOf("<"));
            }

            int titleSize = 0;
            char[] c = title.toCharArray();
            for (int i = 0; i < c.length; i++) {
                if (String.valueOf(c[i]).hashCode() > 255)
                    titleSize = titleSize + 2;
                else
                    titleSize++;
            }

            String t = "";
            if (size * 2 < titleSize) {
                int len = 0;
                for (int i = 0; i < c.length; i++) {
                    if (String.valueOf(c[i]).hashCode() > 255)
                        len = len + 2;
                    else
                        len++;
                    if (len > size * 2)
                        break;
                    t += String.valueOf(c[i]);
                }
            } else {
                t = title;
            }

            if (oldTitle.length() == titleSize) {
                if (t.length() < oldTitle.length()) {
                    if (!String.valueOf(c[t.length() - 1]).equals(" ") && !String.valueOf(c[t.length()]).equals(" ")) {
                        if (t.lastIndexOf(" ") > -1)
                            t = t.substring(0, t.lastIndexOf(" "));
                    }
                }
            }
            title = head.toString() + t + end.toString();

            if (cpool.getType().equals("mssql") || cpool.getType().equals("sybase"))
                title = StringUtil.gb2iso(title);
        }
        return title;
    }

    private boolean isConvert2Image(String mark, String content) {
        boolean is = false;
        content = StringUtil.replace(content, "<%%ARTICLE_PATH%%>", "");
        Pattern p = Pattern.compile("\\<img[^<>]*src\\s*=\\s*[^<>]*" + mark + "[^<>]*\\>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(content);
        if (matcher.find()) is = true;

        return is;
    }

    private String getColumnChName(int columnID) {
        IColumnManager colMgr = ColumnPeer.getInstance();
        Column column = null;

        try {
            column = colMgr.getColumn(columnID);
        }
        catch (ColumnException ex) {
            ex.printStackTrace();
        }
        if (column != null)
            return column.getCName();
        else
            return "";
    }

    private String getColumnURL(int columnID,int modeltype) {
        IColumnManager colMgr = ColumnPeer.getInstance();
        String urlName = "";
        Column column;

        try {
            column = colMgr.getColumn(columnID);
            String extname = column.getExtname();
            urlName = column.getDirName();
            if (modeltype == 4 || modeltype == 5 || modeltype == 6)
                urlName = urlName + "index-m." + extname;
            else
                urlName = urlName + "index." + extname;
        }
        catch (ColumnException ex) {
            ex.printStackTrace();
        }
        return urlName;
    }

    private String getColumnURL(XMLProperties properties, Column column,int modeltype) {
        int columnID = column.getID();
        String extname = column.getExtname();
        String urlName = column.getDirName();

        String tagName = properties.getName();
        String definedUrl = properties.getProperty(tagName.concat(".LINK").concat(".URL"));
        String param = properties.getProperty(tagName.concat(".LINK").concat(".PARAM"));
        String cid = properties.getProperty(tagName.concat(".LINK").concat(".CID"));
        String aid = properties.getProperty(tagName.concat(".LINK").concat(".AID"));
        int artNum = 1;
        int linkType = 0;

        try {
            linkType = Integer.parseInt(properties.getProperty(tagName.concat(".LINK").concat(".WAY")));
            artNum = Integer.parseInt(properties.getProperty(tagName.concat(".LINK").concat(".LINKARTICLE")));
        }
        catch (NumberFormatException ignored) {
        }

        switch (linkType) {
            case 0:
                if (modeltype == 4 || modeltype == 5 || modeltype == 6)
                    urlName = urlName + "index-m." + extname;
                else
                    urlName = urlName + "index." + extname;
                break;
            case 1:
                if (definedUrl != null) urlName = definedUrl;

                if (param != null || cid != null) urlName = urlName + "?";

                if (param != null) urlName = urlName + param;

                if (cid != null) {
                    if (param != null)
                        urlName = urlName + "&" + "cid=" + columnID;
                    else
                        urlName = urlName + "cid=" + columnID;
                }
                break;
            case 2:
                IArticleManager articleManager = ArticlePeer.getInstance();
                try {
                    Article article = articleManager.getArticleInNum(columnID, artNum);

                    if (article != null) {
                        if (article.getNullContent() == 0) {
                            if (modeltype == 4 || modeltype == 5 || modeltype == 6)
                                urlName = urlName + article.getID() + "-m." + extname;
                            else
                                urlName = urlName + article.getID() + "." + extname;
                        } else
                            urlName = urlName + "download/" + article.getFileName();

                        if (param != null || cid != null || aid != null) urlName = urlName + "?";
                        if (param != null) urlName = urlName + param;
                        if (cid != null) {
                            if (param != null)
                                urlName = urlName + "&" + "cid=" + columnID;
                            else
                                urlName = urlName + "cid=" + columnID;
                        }
                        if (aid != null) urlName = urlName + "&" + "aid=" + article.getID();
                    } else {
                        urlName = urlName + "index." + extname;
                    }
                }
                catch (ArticleException ex) {
                    ex.printStackTrace();
                }
                break;
        }
        return urlName;
    }

    private String getArticleURL(Article article,int modeltype) {
        int columnID = article.getColumnID();
        IColumnManager colMgr = ColumnPeer.getInstance();
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        int emptycontentflag = article.getNullContent();
        String urlName = article.getDirName();

        try {
            Column column = colMgr.getColumn(columnID);
            SiteInfo siteinfo = siteMgr.getSiteInfo(article.getSiteID());
            //String sitename = siteinfo.getDomainName();

            if (column != null) {
                String extname = column.getExtname();
                if (extname == null) extname = "html";
                if (urlName == null) urlName = column.getDirName();

                //获得文章创建的时间，生成时间发布路径
                String createdate_path=null;
                if (article.getCreateDate() != null) {
                    Timestamp createdate = article.getCreateDate();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    createdate_path = formatter.format(createdate);
                } else {
                    IArticleManager articleMgr = ArticlePeer.getInstance();
                    Article getArticle = new Article();
                    try {
                        getArticle = articleMgr.getArticle(article.getID());
                    } catch (ArticleException e) {
                        e.printStackTrace();
                    }
                    createdate_path = getArticle.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "");
                }

                urlName = urlName + createdate_path + "/";

                if (emptycontentflag == 0) {
                    if (modeltype == 4 || modeltype == 5 || modeltype == 6){
                        urlName = urlName + article.getID() + "-m." + extname;
                    }else {
                        urlName = urlName + article.getID() + "." + extname;
                    }
                }else
                    urlName = urlName + "download/" + article.getFileName();
            }
        } catch (ColumnException ex) {
            ex.printStackTrace();
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        }

        return urlName;
    }

    private String getArticleURL(Article article, int pubColumnID, int usearticleflag, int psiteid,int modeltype) {
        IColumnManager colMgr = ColumnPeer.getInstance();
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        int emptycontentflag = article.getNullContent();
        String urlName = "";
        int columnID = 0;
        try {
            if (usearticleflag == 0) columnID = article.getColumnID();
            Column column = colMgr.getColumn(columnID);
            if (column!=null) {
                int siteid = column.getSiteID();
                SiteInfo siteinfo = siteMgr.getSiteInfo(article.getSiteID());
                String sitename = siteinfo.getDomainName();
                String extname = column.getExtname();
                urlName = column.getDirName();
                if (extname == null) extname = "html";

                //获得文章创建的时间，生成时间发布路径
                String createdate_path;
                if (article.getCreateDate() != null) {
                    createdate_path = article.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "");
                } else {
                    IArticleManager articleMgr = ArticlePeer.getInstance();
                    Article getArticle = new Article();
                    try {
                        getArticle = articleMgr.getArticle(article.getID());
                    } catch (ArticleException e) {
                        e.printStackTrace();
                    }
                    createdate_path = getArticle.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "");
                }

                urlName = urlName + createdate_path + "/";

                if (emptycontentflag == 0) {
                    if (article.getUrltype() == 1)
                        urlName = article.getOtherurl();
                    else {
                        if (modeltype == 4 || modeltype == 5 || modeltype == 6) {
                            urlName = urlName + article.getID() + "-m." + extname;
                        } else {
                            urlName = urlName + article.getID() + "." + extname;
                        }
                    }
                } else
                    urlName = urlName + "download/" + article.getFileName();
            } else {
                System.out.println("文章所在的栏目不存在");
                return null;
            }
        }
        catch (ColumnException ex) {
            ex.printStackTrace();
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        }


        return urlName;
    }

    private String getArticleURL(Article article, int pubColumnID, int usearticleflag,int modeltype) {
        IColumnManager colMgr = ColumnPeer.getInstance();
        int emptycontentflag = article.getNullContent();
        String urlName = "";
        int columnID = 0;
        try {
            if (usearticleflag == 0) columnID = article.getColumnID();
            Column column = colMgr.getColumn(pubColumnID);
            String extname = column.getExtname();
            urlName = column.getDirName();
            if (extname == null) extname = "html";

            //获得文章创建的时间，生成时间发布路径
            String createdate_path;
            if (article.getCreateDate() != null) {
                createdate_path = article.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "");
            } else {
                IArticleManager articleMgr = ArticlePeer.getInstance();
                Article getArticle = new Article();
                try {
                    getArticle = articleMgr.getArticle(article.getID());
                } catch (ArticleException e) {
                    e.printStackTrace();
                }
                createdate_path = getArticle.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "");
            }

            urlName = urlName + createdate_path + "/";

            if (emptycontentflag == 0)
                if (modeltype == 4 || modeltype == 5 || modeltype == 6)
                    urlName = urlName + article.getID() + "-m." + extname;
                else
                    urlName = urlName + article.getID() + "." + extname;
            else
                urlName = urlName + "download/" + article.getFileName();
        }
        catch (ColumnException ex) {
            ex.printStackTrace();
        }

        return urlName;
    }


    private String getArticleURL(XMLProperties properties, Article article, int siteID) {
        String tagName = properties.getName();
        String definedUrl = properties.getProperty(tagName.concat(".LINK").concat(".URL"));
        String param = properties.getProperty(tagName.concat(".LINK").concat(".PARAM"));
        String cid = properties.getProperty(tagName.concat(".LINK").concat(".CID"));
        String aid = properties.getProperty(tagName.concat(".LINK").concat(".AID"));

        int columnID = article.getColumnID();
        IColumnManager colMgr = ColumnPeer.getInstance();
        int emptycontentflag = article.getNullContent();
        String urlName = "";

        try {
            Column column = colMgr.getColumn(columnID);
            String extname = column.getExtname();
            urlName = column.getDirName();
            if (extname == null) extname = "html";
            int column_siteID = column.getSiteID();

            //获得文章创建的时间，生成时间发布路径
            String createdate_path;
            if (article.getCreateDate() != null) {
                createdate_path = article.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "");
            } else {
                IArticleManager articleMgr = ArticlePeer.getInstance();
                Article getArticle = new Article();
                try {
                    getArticle = articleMgr.getArticle(article.getID());
                } catch (ArticleException e) {
                    e.printStackTrace();
                }
                createdate_path = getArticle.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "");
            }
            urlName = urlName + createdate_path + "/";

            if (emptycontentflag == 0)
                urlName = urlName + article.getID() + "." + extname;
            else
                urlName = urlName + "download/" + article.getFileName();

            if (definedUrl != null) urlName = definedUrl;

            if (param != null || (cid != null && !cid.equals("0")) || (aid != null && !cid.equals("0")))
                urlName = urlName + "?";

            if (param != null) urlName = urlName + param;

            if (cid != null && !cid.equals("0")) {
                if (param != null)
                    urlName = urlName + "&" + "cid=" + columnID;
                else
                    urlName = urlName + "cid=" + columnID;
            }

            int articleID = article.getID();
            if (aid != null && !aid.equals("0")) urlName = urlName + "&" + "aid=" + articleID;

            //替换URL中的标记<%%ARTICLE_MAINTITLE%%>
            if (urlName.indexOf("(ARTICLE_MAINTITLE)") > -1)
                urlName = StringUtil.replace(urlName, "(ARTICLE_MAINTITLE)", article.getMainTitle());

            //替换URL中的标记<%%ARTICLE_PATH%%>
            if (urlName.indexOf("(ARTICLE_PATH)") > -1)
                urlName = StringUtil.replace(urlName, "(ARTICLE_PATH)", article.getDirName());

            //替换URL中的标记<%%ARTICLE_VERSION%%>
            if (urlName.indexOf("(ARTICLE_VERSION)") > -1) {
                IArticleManager articleMgr = ArticlePeer.getInstance();
                String articleVersion = articleMgr.getArticleVersion(articleID);
                urlName = StringUtil.replace(urlName, "(ARTICLE_VERSION)", String.valueOf(articleVersion));
            }

            //替换URL中的标记<%%ARTICLE_VICETITLE%%>
            if (urlName.indexOf("(ARTICLE_VICETITLE)") > -1) {
                if (article.getViceTitle() != null)
                    urlName = StringUtil.replace(urlName, "(ARTICLE_VICETITLE)", article.getViceTitle());
                else
                    urlName = StringUtil.replace(urlName, "(ARTICLE_VICETITLE)", "");
            }

            //替换URL中的标记<%%ARTICLE_AUTHOR%%>
            if (urlName.indexOf("(ARTICLE_AUTHOR)") > -1) {
                if (article.getAuthor() != null)
                    urlName = StringUtil.replace(urlName, "(ARTICLE_AUTHOR)", article.getAuthor());
                else
                    urlName = StringUtil.replace(urlName, "(ARTICLE_AUTHOR)", "");
            }

            //如果当前栏目非本站栏目，则在URL前加上域名
            if (column_siteID != siteID) {
                IRegisterManager registerMgr = RegisterPeer.getInstance();
                String sitename = registerMgr.getSite(column_siteID).getSiteName();
                urlName = "http://" + sitename + urlName;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return urlName;
    }

    public String getSubTreeColumnIDs(int siteid,String columnIDs) {
        ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();
        SiteInfo siteinfo = null;
        try {
            siteinfo = siteInfoMgr.getSiteInfo(siteid);
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        }
        if (siteinfo != null) {
            int samsiteid = siteinfo.getSamsiteid();
            Tree colTree = null;
            if (samsiteid > 0)
                colTree = TreeManager.getInstance().getSiteTree(samsiteid);
            else
                colTree = TreeManager.getInstance().getSiteTree(siteid);
            node[] treenodes = colTree.getAllNodes();

            String arrID[] = columnIDs.split(",");
            columnIDs = "";
            for (int k = 0; k < arrID.length; k++) {
                int p;
                if ((p = arrID[k].indexOf("-getAllSubArticle")) > -1) {
                    int columnID = Integer.parseInt(arrID[k].substring(0, p));
                    int[] cid = new int[treenodes.length + 1];
                    int[] pid = new int[treenodes.length];
                    int nodeNum = 1;
                    int i;
                    int j = 1;
                    pid[1] = columnID;

                    do {
                        columnID = pid[nodeNum];
                        cid[j] = columnID;
                        j = j + 1;
                        nodeNum = nodeNum - 1;
                        for (i = 0; i < treenodes.length; i++) {
                            if (treenodes[i] != null) {
                                if (treenodes[i].getLinkPointer() == columnID) {
                                    nodeNum = nodeNum + 1;
                                    pid[nodeNum] = treenodes[i].getId();
                                }
                            }
                        }
                    } while (nodeNum >= 1);
                    cid[0] = j - 1;

                    for (int m = 0; m < cid[0]; m++) {
                        columnIDs = columnIDs + "," + cid[m + 1];
                    }
                } else {
                    columnIDs = columnIDs + "," + arrID[k];
                }
            }
        }

        return columnIDs.substring(1);
    }

    private List artListData(int markType, XMLProperties properties, int startnum, int endnum, int currentArticleID, int defaultColumnID, int siteid) {
        List articleList = new ArrayList();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        //ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();

        String selectColumns = ",";
        String tagName = properties.getName();
        int selectWay = 0;
        String selectway_str = properties.getProperty(tagName.concat(".SELECTWAY"));
        if (selectway_str!=null) selectWay = Integer.parseInt(selectway_str);
        int articleNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
        String columnIds = properties.getProperty(tagName.concat(".COLUMNIDS"));
        String columns = properties.getProperty(tagName.concat(".COLUMNS"));
        String orders = properties.getProperty(tagName.concat(".ORDER"));
        String time_range = properties.getProperty(tagName.concat(".ORDER_RANGE.TIME_RANGE"));
        String power_range = properties.getProperty(tagName.concat(".ORDER_RANGE.POWER_RANGE"));
        String vicepower_range = properties.getProperty(tagName.concat(".ORDER_RANGE.VICEPOWER_RANGE"));
        String number_range = properties.getProperty(tagName.concat(".ORDER_RANGE.NUMBER_RANGE"));

        //文章分类
        String article_first_type = properties.getProperty(tagName.concat(".ARTICLEFIRSTTYPE"));
        String article_second_type = properties.getProperty(tagName.concat(".ARTICLESECONDTYPE"));

        int exclude = 0;
        boolean hasReferedArticle = false;
        if (properties.getProperty(tagName.concat(".EXCLUDE")) != null)
            exclude = Integer.parseInt(properties.getProperty(tagName.concat(".EXCLUDE")));
        int archive = 0;
        if (properties.getProperty(tagName.concat(".ARCHIVE")) != null)
            archive = Integer.parseInt(properties.getProperty(tagName.concat(".ARCHIVE")));

        if ((columns != null) && (columns.trim().length() > 0)) {
            if (columns.equals("*")) {
                columnIds = "(" + defaultColumnID + ")";
            }
        }

        if (columnIds.indexOf("-getAllSubArticle") > -1)    //允许显示子栏目
        {
            columnIds = columnIds.substring(1, columnIds.length() - 1);
            columnIds = getSubTreeColumnIDs(siteid,columnIds);
        } else {
            columnIds = columnIds.substring(1, columnIds.length() - 1);
        }

        columnIds = "(" + columnIds + ")";

        String orderby = "";
        String[] order = orders.split(",");
        for (int i = 0; i < order.length; i++) {
            int orderType = Integer.parseInt(order[i]);
            if (orderType == 1)
                orderby = orderby + "publishtime desc,";
            else if (orderType == 2)
                orderby = orderby + "publishtime asc,";
            else if (orderType == 3)
                orderby = orderby + "doclevel desc,";
            else if (orderType == 4)
                orderby = orderby + "doclevel asc,";
            else if (orderType == 5)
                orderby = orderby + "sortid desc,";
            else if (orderType == 6)
                orderby = orderby + "sortid asc,";
            else if (orderType == 7)
                orderby = orderby + "vicedoclevel desc,";
            else if (orderType == 8)
                orderby = orderby + "vicedoclevel asc,";
            else if (orderType == 9)
                orderby = orderby + "maintitle desc,";
            else if (orderType == 10)
                orderby = orderby + "maintitle asc,";
            else if (orderType == 11)
                orderby = orderby + "beidate desc,";
            else if (orderType == 12)
                orderby = orderby + "beidate asc,";
            else if (orderType == 13)
                orderby = orderby + "salesnum desc,";
            else if (orderType == 14)
                orderby = orderby + "salesnum asc,";
        }

        if (orderby.length() > 0) orderby = " order by " + orderby.substring(0, orderby.length() - 1);

        String where = "siteid=" + siteid + " and ";

        if (time_range != null && time_range.trim().length() > 0) {
            String time0 = time_range.substring(0, time_range.indexOf(","));
            String time1 = time_range.substring(time_range.indexOf(",") + 1);
            if (time0.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + "publishtime >= TO_DATE('" + time0 + "','YYYY-MM-DD') and ";
                else
                    where = where + "publishtime >= '" + time0 + "' and ";
            }
            if (time1.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + "publishtime <= TO_DATE('" + time1 + "','YYYY-MM-DD') and ";
                else
                    where = where + "publishtime <= '" + time1 + "' and ";
            }
        }
        if (power_range != null && power_range.trim().length() > 0) {
            String where1 = "";
            String power[] = power_range.split(",");
            for (int i = 0; i < power.length; i++) {
                String temp;
                int posi = power[i].indexOf("-");
                if (posi > 0)
                    temp = "(DocLevel >= " + power[i].substring(0, posi) + " AND DocLevel <= " + power[i].substring(posi + 1) + ")";
                else
                    temp = "(DocLevel = " + power[i] + ")";
                where1 = where1 + temp + " OR ";
            }
            if (where1.length() > 0) {
                where1 = where1.substring(0, where1.length() - 3);
                where = where + "(" + where1 + ") AND ";
            }
        }
        if (vicepower_range != null && vicepower_range.trim().length() > 0) {
            String where1 = "";
            String power[] = vicepower_range.split(",");
            for (int i = 0; i < power.length; i++) {
                String temp;
                int posi = power[i].indexOf("-");
                if (posi > 0)
                    temp = "(ViceDocLevel >= " + power[i].substring(0, posi) + " AND ViceDocLevel <= " + power[i].substring(posi + 1) + ")";
                else
                    temp = "(ViceDocLevel = " + power[i] + ")";
                where1 = where1 + temp + " OR ";
            }
            if (where1.length() > 0) {
                where1 = where1.substring(0, where1.length() - 3);
                where = where + "(" + where1 + ") AND ";
            }
        }
        if (number_range != null && number_range.trim().length() > 0) {
            String number0 = number_range.substring(0, number_range.indexOf(","));
            String number1 = number_range.substring(number_range.indexOf(",") + 1);
            if (number0.length() > 0)
                where = where + "sortid >= " + number0 + " and ";
            if (number1.length() > 0)
                where = where + "sortid <= " + number1 + " and ";
        }

        if (markType == 2 || markType == 3) {
            int articleID = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLEID")));
            if (articleID == 0 && currentArticleID == 0) return articleList;
            if (articleID == 0) articleID = currentArticleID;

            if (markType == 2)   //子文章列表
            {
                where = where + "RelatedArtID = '" + articleID + "' and ";
            } else {            //兄弟文章列表
                try {
                    String parentID = articleMgr.getArticle(articleID).getRelatedArtID();
                    if (parentID != null && parentID.trim().length() > 0)
                        where = where + "RelatedArtID = '" + parentID + "' and ";
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if (where.trim().length() > 0) {
            where = where.trim();
            where = " and " + where.substring(0, where.length() - 3);
        }

        if (exclude == 1 && currentArticleID > 0) {
            where = where.trim() + " and ID <> " + currentArticleID;
        }

        //分类文章
        String articleids = "";
        if (article_second_type != null && !article_second_type.equals("") && !article_second_type.equals("null")) {
            IColumnManager cMgr = ColumnPeer.getInstance();
            try {
                List selectColumnsList = cMgr.getRefersColumnIds(columnIds);
                for (int i = 0; i < selectColumnsList.size(); i++) {
                    Column scolumn = (Column) selectColumnsList.get(i);
                    selectColumns = selectColumns + scolumn.getScid() + ",";
                }

                if (selectColumns != null) {
                    if (selectColumns.lastIndexOf(",") > 0) {
                        selectColumns = selectColumns.substring(0, selectColumns.length() - 1);
                        hasReferedArticle = true;
                    } else
                        selectColumns = "";
                } else {
                    selectColumns = "";
                }
            } catch (ColumnException e) {
                e.printStackTrace();
            }

            columnIds = "(" + columnIds.substring(1, columnIds.length() - 1) + selectColumns + ")";
            articleids = cMgr.getArticleIDForType(columnIds, article_first_type, article_second_type);

            if (articleids != null && !articleids.equals("") && !articleids.equals("null")) {
                articleids = articleids.substring(0, articleids.length() - 1);
                where = where.trim() + " and id in(" + articleids + ")";
            } else {
                where = where.trim() + " and id = 0";
            }
        }

        try {
            if (selectWay != 0) articleNum = 0;
            if (article_second_type == null)
                articleList = articleMgr.getOrderArticles(startnum, endnum, columnIds, where, orderby, archive, articleNum, defaultColumnID, hasReferedArticle);
            else
                articleList = articleMgr.getOrderArticles(startnum, endnum, columnIds, where, orderby, archive, articleNum, defaultColumnID, articleids);
        } catch (ArticleException e) {
            e.printStackTrace();
        }

        return articleList;
    }
    //ARTICLE_LIST标记处理完毕

    public String formatSitelogo(int siteid) throws TagException {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        String buf = "";
        try {
            String logo = siteMgr.getSiteStringAttribute(siteid, "sitelogo");
            buf = "<img src='/images/" + logo + "' border='0' />";
        } catch (SiteInfoException exp) {

        }

        return buf;
    }

    public String formatSitebanner(int siteid) throws TagException {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        String buf = "";
        try {
            String banner = siteMgr.getSiteStringAttribute(siteid, "banner");
            buf = "<img src='/images/" + banner + "' border='0' />";
        } catch (SiteInfoException exp) {

        }

        return buf;
    }

    public String formatMainNavigator(int siteid) throws TagException {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IViewFileManager vfManager = viewFilePeer.getInstance();
        String mainmenu = "";
        try {
            int mnv = siteMgr.getSiteIntAttribute(siteid, "navigator");
            navigator nv = new navigator();
            nv = vfManager.getNavigator(mnv);
            mainmenu = nv.getContent();
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        } catch (viewFileException exp) {
            exp.printStackTrace();
        }

        return mainmenu;
    }

    public String formatSideNavigator(int siteid) throws TagException {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IViewFileManager vfManager = viewFilePeer.getInstance();
        String sidemenu = "";
        try {
            int mnv = siteMgr.getSiteIntAttribute(siteid, "navigator");
            navigator nv = new navigator();
            nv = vfManager.getNavigator(mnv);
            sidemenu = nv.getfContent();
        } catch (SiteInfoException exp) {
            exp.printStackTrace();
        } catch (viewFileException exp) {
            exp.printStackTrace();
        }

        return sidemenu;
    }

    //处理copyright标记
    public String formatCopyright(int siteid) throws TagException {
        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IViewFileManager vfManager = viewFilePeer.getInstance();
        String copyright = "";
        try {

            SiteInfo siteinfo = (SiteInfo) siteMgr.getSiteInfo(siteid);
            copyright = siteinfo.getCopyright();
            if (copyright == null) {
                copyright = "";
            }
        } catch (Exception e) {
            e.toString();
        }

        return copyright;
    }

    //处理ARTICLE_CONTENT标记
    public Article thearticle(int articleID) throws TagException {
        Article article;
        IArticleManager articleManager = ArticlePeer.getInstance();
        try {
            article = articleManager.getArticle(articleID);
        }
        catch (ArticleException e) {
            throw new TagException("获取文章是出错" + e.getMessage());
        }

        return article;
    }

    public String formatArticleMainTitle(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String maintitle;

        Article article = thearticle(articleID);
        if (article != null) {
            maintitle = article.getMainTitle();
        } else {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        }

        //检查标题中是否有图片
        if (properties.getProperty(properties.getName()) == null) maintitle = toImage(maintitle, sitename, article, siteID);

        maintitle=StringUtil.replace(maintitle,"—","&mdash;");

        return maintitle;
    }

    public String formatArticlePic(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String articlepic;

        Article article = thearticle(articleID);
        if (article != null) {
            articlepic = article.getArticlepic();
        } else {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        }

        //检查是否有图片
        if (properties.getProperty(properties.getName()) != null)
            articlepic = toImage(articlepic, sitename, article, siteID);

        return articlepic;
    }

    public String formatRelateArticleAttribute(int articleID, String attrName) throws TagException {
        String temp = "";

        String relateID;
        Article article = thearticle(articleID);
        if (article != null) {
            relateID = article.getRelatedArtID();
            if (relateID == null || relateID.trim().length() == 0)
                return "";
            else if (attrName.equals("RELATEID"))
                return relateID;
        } else {
            return "";
        }

        article = thearticle(Integer.parseInt(relateID));
        if (article != null) {
            if (attrName.equals("RELATED_DATA")) {
                temp = article.getMainTitle();
            } else if (attrName.equals("RELATED_VICETITLE")) {
                temp = article.getViceTitle();
            } else if (attrName.equals("RELATED_SOURCE")) {
                temp = article.getSource();
            } else if (attrName.equals("RELATED_SUMMARY")) {
                temp = article.getSummary();
            } else if (attrName.equals("RELATED_AUTHOR")) {
                temp = article.getAuthor();
            } else if (attrName.equals("RELATED_URL") || attrName.equals("PARENT_PATH")) {
                IColumnManager columnMgr = ColumnPeer.getInstance();
                Column column = null;
                try {
                    column = columnMgr.getColumn(article.getColumnID());
                }
                catch (ColumnException e) {
                    e.printStackTrace();
                }

                String dirName;
                if (column != null)
                    dirName = column.getDirName();
                else
                    dirName = "";
                if (attrName.equals("PARENT_PATH")) {
                    //获得父文章创建的时间，生成时间发布路径
                    String createdate_path = article.getCreateDate().toString().substring(0, 10);
                    createdate_path = createdate_path.replaceAll("-", "") + "/";

                    return dirName + createdate_path;
                }
                String extName = column.getExtname();
                temp = dirName + article.getID() + "." + extName;
            }
            if (temp == null) temp = "";
        } else {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        }
        return temp;
    }

    public String formatArticleViceTitle(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String vicetitle;
        Article article = thearticle(articleID);

        if (article != null)
            vicetitle = article.getViceTitle();
        else if (articleID <= 0)
            return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
        else
            return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");

        //检查副标题中是否有图片
        if (properties.getProperty(properties.getName()) == null)
            vicetitle = toImage(vicetitle, sitename, article, siteID);

        return vicetitle;
    }

    public String formatArticleAuthor(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String author;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            author = article.getAuthor();
        }

        if (properties.getProperty(properties.getName()) == null)
            author = toImage(author, sitename, article, siteID);

        return author;
    }

    public String formatArticlePULISHDATE(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        Timestamp publishtime = null;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            publishtime = article.getPublishTime();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        if (publishtime!=null)
            return dateFormat.format(publishtime);
        else
            return "";
    }

    public String formatArticleURL(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String article_url = null;
        Article article = thearticle(articleID);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            sitename = StringUtil.replace(sitename,"_",".");
            String createDate = dateFormat.format(article.getCreateDate());
            if (article.getFileName()==null)
                article_url = "http://" +sitename + article.getDirName() + createDate + "/" + articleID + ".shtml";
            else
                article_url = "http://" +sitename + article.getDirName() + "download/" + article.getFileName();
        }

        if (article_url!=null)
            return article_url;
        else
            return "";
    }

    public String formatColumnName(XMLProperties properties, int columnID) throws TagException {
        String CName=null;
        IColumnManager columnMgr = ColumnPeer.getInstance();

        try {
            if (columnID > 0) {
                Column column = columnMgr.getColumn(columnID);
                if (properties.getName().equals("COLUMNNAME"))
                    CName = column.getCName();
                else
                    CName = columnMgr.getColumn(column.getParentID()).getCName();
            } else {
                CName = "";
            }
        }
        catch (ColumnException e) {
            return "";
        }

        return CName;
    }

    public String formatArticleCount(int type, int articleID, XMLProperties properties, int columnID, int siteid) throws TagException {
        int count = 0;

        IArticleManager articleMgr = ArticlePeer.getInstance();
        String columnIds = properties.getProperty(properties.getName().concat(".COLUMNIDS"));
        String time_range = properties.getProperty(properties.getName().concat(".TIME_RANGE"));
        String power_range = properties.getProperty(properties.getName().concat(".POWER_RANGE"));
        String order_range = properties.getProperty(properties.getName().concat(".ORDER_RANGE"));
        if (type == 1)   //子文章列表情况
        {
            int ID = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLEID")));
            if (ID > 0) articleID = ID;
        }

        if (columnIds.equals("(0)")) {
            columnIds = "(" + String.valueOf(columnID) + ")";
        }

        if (columnIds.indexOf("-getAllSubArticle") > -1)    //允许显示子栏目
        {
            columnIds = columnIds.substring(1, columnIds.length() - 1);
            columnIds = getSubTreeColumnIDs(siteid,columnIds);
            columnIds = "(" + columnIds + ")";
        }

        String where = "";
        if (time_range != null && time_range.trim().length() > 0) {
            String time0 = time_range.substring(0, time_range.indexOf(","));
            String time1 = time_range.substring(time_range.indexOf(",") + 1);
            if (time0.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + "publishtime >= TO_DATE('" + time0 + "','YYYY-MM-DD') and ";
                else
                    where = where + "publishtime >= '" + time0 + "' and ";
            }
            if (time1.length() > 0) {
                if (cpool.getType().equals("oracle"))
                    where = where + "publishtime <= TO_DATE('" + time1 + "','YYYY-MM-DD') and ";
                else
                    where = where + "publishtime <= '" + time1 + "' and ";
            }
        }
        if (power_range != null && power_range.trim().length() > 0) {
            String power0 = power_range.substring(0, power_range.indexOf(","));
            String power1 = power_range.substring(power_range.indexOf(",") + 1);
            if (power0.length() > 0)
                where = where + "doclevel >= " + power0 + " and ";
            if (power1.length() > 0)
                where = where + "doclevel <= " + power1 + " and ";
        }
        if (order_range != null && order_range.trim().length() > 0) {
            String number0 = order_range.substring(0, order_range.indexOf(","));
            String number1 = order_range.substring(order_range.indexOf(",") + 1);
            if (number0.length() > 0)
                where = where + "sortid >= " + number0 + " and ";
            if (number1.length() > 0)
                where = where + "sortid <= " + number1 + " and ";
        }

        if (where.trim().length() > 0) {
            where = where.trim();
            where = " and " + where.substring(0, where.length() - 3);
        }

        if (type == 1)           //子文章列表情况
        {
            if (where.trim().length() > 0)
                where = where + " and RelatedArtID = '" + articleID + "' ";
            else
                where = " and RelatedArtID = '" + articleID + "' ";
        }

        try {
            count = articleMgr.getOrderArticlesCount(columnIds, where, siteid);
        }
        catch (ArticleException e) {
            e.printStackTrace();
        }

        return String.valueOf(count);
    }

    public String formatArticlePT(XMLProperties properties, int articleID) throws TagException {
        Article article = thearticle(articleID);
        String pt;

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            String datestyle = properties.getProperty(properties.getName());
            if (datestyle != null && datestyle.length() > 1) {
                datestyle = StringUtil.replace(datestyle, "|", " ");
                DateFormat format = new SimpleDateFormat(datestyle);
                pt = format.format(article.getPublishTime());
            } else {
                pt = article.getPublishTime().toString().substring(0, 10);
            }
        }

        return pt;
    }

    public String formatArticleSummary(int articleID) throws TagException {
        String summary;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            summary = article.getSummary();
            if (summary == null) summary = "";
        }

        return summary;
    }

    public String formatArticleIndexNum (int articleID) throws TagException {
        String indexNum = null;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            indexNum = article.getIndexnum();
            if (indexNum == null) indexNum = "";
        }

        return indexNum;
    }

    public String formatArticleKeyword(int articleID) throws TagException {
        String keyword;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            keyword = article.getKeyword();
            if (keyword == null) keyword = "";
        }

        return keyword;
    }

    public String formatArticleStatus(int articleID) throws TagException {
        int status=0;
        Article article = thearticle(articleID);

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            status = article.getStatus();
        }

        return String.valueOf(status);
    }


    public String formatArticleSource(XMLProperties properties, int articleID, String sitename, int siteID) throws TagException {
        String source;
        Article article = thearticle(articleID);

        if (article != null) {
            source = article.getSource();
        } else {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        }

        if (properties.getProperty(properties.getName()) == null)
            source = toImage(source, sitename, article, siteID);

        return source;
    }

    public String formatArticleContent(XMLProperties properties, int articleID, int columnID, int modelID) throws TagException {
        String content = "";
        Article article = thearticle(articleID);
        IPublishManager publishMgr = PublishPeer.getInstance();
        IColumnManager columnMGr = ColumnPeer.getInstance();

        if (article == null) {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        } else {
            content = article.getContent();
            if (content == null) content = "";
            if (content.toLowerCase().indexOf("<body") > -1) {
                content = content.substring(content.toLowerCase().indexOf("<body"));
                content = content.substring(content.indexOf(">") + 1);
            }
            if (content.toLowerCase().indexOf("</body>") > -1) {
                content = content.substring(0, content.toLowerCase().indexOf("</body>"));
            }

            //清楚文章内容里面的多媒体文件标识符
            Pattern p = Pattern.compile("\\[TAG\\]\\[MEDIA\\]\\[FILENAME\\][^\\[\\]]*\\[/FILENAME\\]\\[/MEDIA\\]\\[/TAG\\]", Pattern.CASE_INSENSITIVE);
            String t_content = content;
            Matcher m = p.matcher(content);
            while (m.find()) {
                String tag = content.substring(m.start(), m.end());
                t_content = t_content.replace(tag,"");
            }
            content  = t_content;

            //edit by xuzheming  域名
            boolean usearticletype = publishMgr.checkArticleContentRefers(articleID, columnID);
            if (usearticletype) {
                SiteInfo siteinfo = columnMGr.getSiteName(articleID);
                String getDomain = siteinfo.getDomainName();
                getDomain = "http://" + getDomain.replaceAll("_", ".");
                content = content.replaceAll("/webbuilder/sites/" + siteinfo.getDomainName(), getDomain);
            }

            //处理FLASH
            if (content.toLowerCase().indexOf("</object>") > -1) {
                p = Pattern.compile("<object\\s*[^<>]*D27CDB6E\\-AE6D\\-11cf\\-96B8\\-444553540000[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(content);
                int count = 0;
                while (m.find())
                    count++;
                String arr[] = new String[count * 2 + 1];

                int i = 0;
                m = p.matcher(content);
                while (m.find()) {
                    int start = m.start();
                    arr[i] = content.substring(0, start);
                    content = content.substring(start);
                    arr[i + 1] = content.substring(0, content.toLowerCase().indexOf("</object>") + 9);
                    i++;
                    i++;
                    content = content.substring(content.toLowerCase().indexOf("</object>") + 9);
                    m = p.matcher(content);
                }
                arr[i] = content;

                if (arr.length > 1) {
                    Pattern p1 = Pattern.compile("width=[0-9]*", Pattern.CASE_INSENSITIVE);
                    Pattern p2 = Pattern.compile("height=[0-9]*", Pattern.CASE_INSENSITIVE);

                    for (int j = 0; j < (arr.length - 1) / 2; j++) {
                        String flash = arr[j * 2 + 1];
                        String src = null;
                        if (flash.indexOf("/webbuilder/sites/") > -1) {
                            src = flash.substring(flash.indexOf("/webbuilder/sites/"), flash.indexOf(".swf") + 4);
                            flash = StringUtil.replace(flash, " ", "");
                            flash = StringUtil.replace(flash, "\"", "");
                            flash = StringUtil.replace(flash, "'", "");

                            String width = "";
                            m = p1.matcher(flash);
                            if (m.find()) {
                                width = flash.substring(m.start(), m.end());
                                width = width.substring(width.indexOf("=") + 1);
                            }

                            String height = "";
                            m = p2.matcher(flash);
                            if (m.find()) {
                                height = flash.substring(m.start(), m.end());
                                height = height.substring(height.indexOf("=") + 1);
                            }

                            StringBuffer sb = new StringBuffer();
                            sb.append("<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000' codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0' height=" + height + " width=" + width + ">");
                            sb.append("<PARAM NAME=movie VALUE='" + src + "'>");
                            sb.append("<PARAM NAME=quality VALUE=autohigh>");
                            sb.append("<EMBED src='" + src + "' quality=autohigh TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash' height=" + height + " width=" + width + "></EMBED>");
                            sb.append("</OBJECT>");
                            arr[j * 2 + 1] = sb.toString();
                        }
                    }
                }
                content = "";
                for (int j = 0; j < arr.length; j++)
                    content += arr[j];
            }

            //处理文章内容中的关键字链接  add by EricDu for include key.js
            //content = "<div id=biz_cms_content>" + content + "</div>";
            //content = content + "<script type=\"text/javascript\" src=\"/js/key.js\"></script>";
        }

        return content;
    }

    //获得栏目列表标记的数据
    private List colListData(int flag, XMLProperties properties, int columnID, String sitename,int modeltype) {
        List list = new ArrayList();
        IColumnManager columnManager = ColumnPeer.getInstance();
        String cids = null;
        cids = properties.getProperty(properties.getName().concat(".COLUMNIDS"));
        int lids = cids.length();
        cids = cids.substring(1, lids - 1);
        String[] arrColumnID = cids.split(",");
        Column column;

        try {
            if (flag == 1) {
                if (arrColumnID != null) {
                    for (int i = 0; i < arrColumnID.length; i++) {
                        tagListElement element = new tagListElement();
                        if (Integer.parseInt(arrColumnID[i]) > 0)
                            columnID = Integer.parseInt(arrColumnID[i]);

                        column = columnManager.getColumn(columnID);
                        element.cnname = column.getCName();
                        element.url = getColumnURL(properties, column,modeltype);
                        element.pt = column.getLastUpdated();
                        element.columnID = columnID;

                        String desc = column.getDesc();
                        if (desc == null) desc = "";
                        if (isImage(desc)) {
                            desc = "<img src=/webbuilder/sites/" + sitename + "/images/" + desc.trim() + " border=0>";
                        }
                        element.desc = desc;
                        list.add(element);
                    }
                }
            } else if (flag == 2) {
                if (arrColumnID.length > 0) {
                    if (Integer.parseInt(arrColumnID[0]) > 0) {
                        List columnlist = columnManager.getSubColumns(Integer.parseInt(arrColumnID[0]));
                        for (int i = 0; i < columnlist.size(); i++) {
                            tagListElement element = new tagListElement();

                            column = (Column) columnlist.get(i);
                            element.cnname = column.getCName();
                            element.url = getColumnURL(properties, column,modeltype);
                            element.pt = column.getLastUpdated();
                            element.columnID = columnID;

                            String desc = column.getDesc();
                            if (desc == null) desc = "";
                            if (isImage(desc)) {
                                desc = "<img src=/webbuilder/sites/" + sitename + "/images/" + desc.trim() + " border=0>";
                            }
                            element.desc = desc;
                            list.add(element);
                        }
                    }
                }
            }
        }
        catch (ColumnException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    //显示栏目列表标记的数据
    //flag=1 栏目列表；flag=2 子栏目列表
    public String formatColumnList(int flag, XMLProperties properties, String sitename, int siteID, int columnID, String username, int modeltype,String fragPath, boolean isPreview) throws TagException {
        String result;
        String tagName = properties.getName();
        int innerFlag = 0;
        int selectWay = 0;
        int colNum = 0;
        int listType = 0;

        List columnList = colListData(flag, properties, columnID, sitename,modeltype);

        try {
            selectWay = Integer.parseInt(properties.getProperty(properties.getName().concat(".SELECTWAY")));
            if (selectWay != 0)
                colNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".COLUMNNUM")));   //如果是分页列表，获得用户设定的每页显示的栏目数
            innerFlag = Integer.parseInt(properties.getProperty(tagName.concat(".INNERHTMLFLAG")));
            listType = Integer.parseInt(properties.getProperty(tagName.concat(".LISTTYPE")));
        }
        catch (NumberFormatException ignored) {
        }

        if (selectWay == 0) {
            result = getColumnListOnePage(columnList, sitename, fragPath, username, columnID, innerFlag, siteID, isPreview, colNum, listType);
        } else {
            result = getColumnListPages(columnList, sitename, listType);
        }
        return result;
    }

    public String formatChinesePath(XMLProperties properties, int columnID, int siteid, int samsiteid, int modeltype) throws TagException {
        int styleID = Integer.parseInt(properties.getProperty(properties.getName()));

        if (columnID > 0) {
            Tree colTree = null;
            if (samsiteid == 0)
                colTree = TreeManager.getInstance().getSiteTree(siteid);
            else
                colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);
            return colTree.getChineseNavBar(colTree, columnID, styleID, modeltype);
        } else {
            //SEARCH_RESULT-11
            //SHOPPINGCAR_RESULT-12
            //ORDER_RESULT-13
            //ORDER_REDISPLAY-14
            //ORDERSEARCH_RESULT-15
            //FEEDBACK-16
            //ARTICLE_COMMENT-17
            //REGISTER_RESULT-18
            //USER_LOGIN_DISPLAY-19
            //ORDERSEARCH_DETAIL-20
            //LEAVE_MESSAGE-21
            //UPDATEREG-22
            if (modeltype == 11) {
                return "信息检索";
            } else if (modeltype == 12) {
                return "购物车";
            } else if (modeltype == 13) {
                return "订单生成";
            } else if (modeltype == 14) {
                return "订单显示";
            } else if (modeltype == 15) {
                return "订单查询";
            } else if (modeltype == 16) {
                return "信息反馈";
            } else if (modeltype == 17) {
                return "文章评论";
            } else if (modeltype == 18) {
                return "用户注册";
            } else if (modeltype == 19) {
                return "用户登录";
            } else if (modeltype == 20) {
                return "订单查询明细";
            } else if (modeltype == 21) {
                return "用户留言";
            } else if (modeltype == 22) {
                return "修改用户注册信息";
            } else {
                return "";
            }
        }
    }

    public String formatEnglishPath(XMLProperties properties, int columnID, int siteid, int samsiteid, int modeltype) throws TagException {
        int styleID = Integer.parseInt(properties.getProperty(properties.getName()));
        if (columnID > 0) {
            Tree colTree = null;
            if (samsiteid == 0) {
                colTree = TreeManager.getInstance().getSiteTree(siteid);
                return colTree.getEnglishNavBar(colTree, columnID, styleID, modeltype);
            } else {
                colTree = TreeManager.getInstance().getSiteTreeIncludeSampleSite(siteid, samsiteid);
                return colTree.getChineseNavBar(colTree, columnID, styleID, modeltype);
            }
        } else {
            //SEARCH_RESULT-11
            //SHOPPINGCAR_RESULT-12
            //ORDER_RESULT-13
            //ORDER_REDISPLAY-14
            //ORDERSEARCH_RESULT-15
            //FEEDBACK-16
            //ARTICLE_COMMENT-17
            //REGISTER_RESULT-18
            //USER_LOGIN_DISPLAY-19
            //ORDERSEARCH_DETAIL-20
            //LEAVE_MESSAGE-21
            //UPDATEREG-22
            if (modeltype == 11) {
                return "Infomation Search";
            } else if (modeltype == 12) {
                return "Shopping Car";
            } else if (modeltype == 13) {
                return "Genarate Order";
            } else if (modeltype == 14) {
                return "Display Order";
            } else if (modeltype == 15) {
                return "Search Order";
            } else if (modeltype == 16) {
                return "Feedback";
            } else if (modeltype == 17) {
                return "Comment The Article";
            } else if (modeltype == 18) {
                return "User Register";
            } else if (modeltype == 19) {
                return "User Login";
            } else if (modeltype == 20) {
                return "Order Detail";
            } else if (modeltype == 21) {
                return "Leave Message";
            } else if (modeltype == 22) {
                return "Update Register Infomation";
            } else {
                return "";
            }
        }
    }

    public String formatHTMLCODE(String content, int markID) throws TagException {
        return content;
    }

    public String formatArticleType(XMLProperties properties, int articleId, int siteID) throws TagException {
        String selectTStr = properties.getProperty(properties.getName());
        int columnId = Integer.parseInt(selectTStr.substring(selectTStr.indexOf("_") + 1, selectTStr.lastIndexOf("_")));
        int addlink = Integer.parseInt(selectTStr.substring(selectTStr.lastIndexOf("_") + 1));
        selectTStr = selectTStr.substring(0, selectTStr.indexOf("_"));

        IColumnManager columnMgr = ColumnPeer.getInstance();
        return columnMgr.getArticlesType(columnId, addlink, selectTStr, articleId, siteID);
    }

    public String processNextArticleLink(XMLProperties properties, int articleID, int columnID, int siteID, int type) throws TagException {
        StringBuffer buf = new StringBuffer();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();
        Article article = null;
        String content = "";

        try {
            int listType = Integer.parseInt(properties.getProperty(properties.getName()));
            content = viewfileMgr.getAViewFile(listType).getContent();
            article = articleMgr.getNextArticle(articleID, columnID, siteID, type);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (article != null) {
            int nextID = article.getID();
            String dirName = article.getDirName();
            String title = article.getMainTitle();

            //获得文章创建的时间，生成时间发布路径
            String createdate_path;
            if (article.getCreateDate() != null) {
                createdate_path = article.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "") + "/";
            } else {
                Article getArticle = new Article();
                try {
                    getArticle = articleMgr.getArticle(article.getID());
                } catch (ArticleException e) {
                    e.printStackTrace();
                }
                createdate_path = getArticle.getCreateDate().toString().substring(0, 10);
                createdate_path = createdate_path.replaceAll("-", "") + "/";
            }

            String extName = "html";
            IColumnManager columnMgr = ColumnPeer.getInstance();
            try {
                extName = columnMgr.getColumn(columnID).getExtname();
            }
            catch (ColumnException e) {
                e.printStackTrace();
            }

            String links = dirName + createdate_path + nextID + "." + extName;

            content = StringUtil.replace(content, "<%%URL%%>", links);
            if (title != null)
                content = StringUtil.replace(content, "<%%DATA%%>", title);
            else
                content = StringUtil.replace(content, "<%%DATA%%>", "");
        } else {
            content = StringUtil.replace(content, "<%%URL%%>", "/");
            content = StringUtil.replace(content, "<%%DATA%%>", "");
        }
        buf.append(content);

        return buf.toString();
    }

    //处理文章模板中的自定义属性，如[TAG][username]用户姓名[/username][/TAG]
    public String processExtendAttribute(XMLProperties properties,String xmlTemplate, int articleID, int columnID, String sitename, String xml) throws TagException {
        StringBuffer buf = new StringBuffer();
        String attrName = properties.getName();
        IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();

        try {
            if (attrName.toUpperCase().indexOf("RELATED_") == 0) {
                String relatedID = articleMgr.getArticle(articleID).getRelatedArtID();
                if (relatedID != null && relatedID.trim().length() > 0) {
                    articleID = Integer.parseInt(relatedID);
                    attrName = attrName.substring(8);
                }
            }

            List attrList = new ArrayList();
            attrList.add(attrName);
            attrList = extendMgr.getArticleExtendValue(articleID, attrList);
            String temp = String.valueOf(attrList.get(0));
            String ename = temp.substring(0,temp.indexOf("="));
            String result = temp.substring(temp.indexOf("=") + 1);

            SAXBuilder builder = new SAXBuilder();
            try {
                Reader in = new StringReader(xmlTemplate);
                org.jdom.Document doc = builder.build(in);
                List nodeList = doc.getRootElement().getChildren();
                ExtendAttr extend = null;
                for (int j = 0; j < nodeList.size(); j++) {
                    org.jdom.Element e = (org.jdom.Element) nodeList.get(j);
                    int attrtype = Integer.parseInt(e.getAttributeValue("type"));
                    if (attrtype == 5 && ename.equals(e.getName())) {
                        extend = new ExtendAttr();
                        extend.setCName(e.getText());
                        extend.setEName(e.getName());
                        extend.setControlType(5);
                        extend.setDefaultValue(e.getAttributeValue("defaultval"));
                        extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));
                        break;
                    }
                }

                if (extend!=null) {
                    JSONObject jsStr = JSONObject.fromObject(extend.getDefaultValue());
                    JSONArray jsonArray = jsStr.getJSONArray("data");
                    List<zTreeNodeObj> zTreeNodeObjs = JSON_Str_To_ObjArray.Transfer_JsonStr_To_ObjArray(jsonArray);
                    String tbuf = "";
                    String[] t_values = result.split(",");
                    for(int jj=0; jj<t_values.length; jj++) {
                        for(int ii=0;ii<zTreeNodeObjs.size();ii++) {
                            zTreeNodeObj zTreeNodeObj = zTreeNodeObjs.get(ii);
                            String tt = zTreeNodeObj.getName();
                            int posi = tt.indexOf("|");
                            String text = tt.substring(0,posi);
                            String keyval = tt.substring(posi+1);
                            if (t_values[jj].trim().equals(keyval.trim())) {
                                tbuf = tbuf + text + ",";
                                break;
                            }
                        }
                    }
                    if (tbuf.length()>0) tbuf = tbuf.substring(0,tbuf.length()-1);
                    buf.append(tbuf);
                } else {
                    buf.append(result);
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return buf.toString();
    }

    public String formatCompanyAttrbuite(int articleID, String attrName, String sitename,int siteID)  throws TagException {
        String content = "";
        ICompanyinfoManager companyinfoManager = CompanyinfoPeer.getInstance();

        com.bizwink.cms.toolkit.companyinfo.Companyinfo companyinfo = companyinfoManager.getACompanyInfoBySiteid(siteID);

        if (companyinfo != null) {
            if ("COMPANYNAME".equals(attrName))
                if (companyinfo.getCompanyname()!=null)
                    content = companyinfo.getCompanyname();
            if ("TELEPHONE".equals(attrName))
                if (companyinfo.getCompanyphone()!=null)
                    content = companyinfo.getCompanyphone();
            if ("EMAIL".equals(attrName))
                if (companyinfo.getCompanyemail()!=null)
                    content = companyinfo.getCompanyemail();
            if ("WEIBO".equals(attrName))
                if (companyinfo.getWEIBO()!=null)
                    content = companyinfo.getWEIBO();
            if ("QQ".equals(attrName))
                if (companyinfo.getQQ() !=null)
                    content = companyinfo.getQQ();
        }

        return content;
    }

    public String formatProductAttrbuite(int articleID, String attrName, String siteName) throws TagException {
        String content = "";

        Article article = thearticle(articleID);
        if (article != null) {
            if ("PRODUCT_SALEPRICE".equals(attrName))
                content = String.valueOf(article.getSalePrice());
            if ("PRODUCT_VIPPRICE".equals(attrName))
                content = String.valueOf(article.getVIPPrice());
            if ("PRODUCT_VIPPRICE".equals(attrName))
                content = String.valueOf(article.getVIPPrice());
            if ("PRODUCT_INPRICE".equals(attrName))
                content = String.valueOf(article.getInPrice());
            if ("PRODUCT_MARKETPRICE".equals(attrName))
                content = String.valueOf(article.getMarketPrice());
            if ("PRODUCT_SCORE".equals(attrName))
                content = String.valueOf(article.getScore());
            if ("PRODUCT_VOUCHER".equals(attrName))
                content = String.valueOf(article.getVoucher());
            if ("PRODUCT_WEIGHT".equals(attrName))
                content = String.valueOf(article.getProductWeight());
            if ("PRODUCT_STOCK".equals(attrName))
                content = String.valueOf(article.getStockNum());
            if ("PRODUCT_BRAND".equals(attrName))
                content = article.getBrand();
            if ("ARTICLE_PATH".equals(attrName))
                content = article.getDirName();

            if ("PRODUCT_PIC".equals(attrName) || "PRODUCT_BIGPIC".equals(attrName)) {
                if ("PRODUCT_PIC".equals(attrName))
                    content = article.getProductPic();
                if ("PRODUCT_BIGPIC".equals(attrName))
                    content = article.getProductBigPic();
            }
            if (content == null || content.equals("0") || content.equals("0.0")) content = "";
        } else {
            if (articleID <= 0)
                return StringUtil.gb2iso("500-错误原因：可能是在栏目模板或首页模板中使用了“文章属性标记”");
            else
                return StringUtil.gb2iso("501-错误原因：获取文章时出现错误");
        }

        return content;
    }

    /*
    private String processAttrValue(ExtendAttr extend,int columnID,String attrName,String xml,String sitename)
    {
      StringBuffer buf = new StringBuffer();
      IColumnManager columnMgr = ColumnPeer.getInstance();
      IExtendAttrManager extendMgr = ExtendAttrPeer.getInstance();

      try
      {
        String xmlTemplate = extendMgr.getXMLTemplate(columnID);
        if (xmlTemplate == null || xmlTemplate.trim().length() == 0)
        {
          return "";
        }

        SAXBuilder builder = new SAXBuilder();
        Reader in = new StringReader(xmlTemplate);
        Document doc = builder.build(in);

        Element e = doc.getRootElement().getChild(attrName);
        if (e == null)  return "";
        int type = Integer.parseInt(e.getAttributeValue("type"));

        String value = "";
        if (extend.getDataType() == 1)
          value = extend.getStringValue();
        else if (extend.getDataType() == 2)
          value = String.valueOf(extend.getNumericValue());
        else if (extend.getDataType() == 3)
          value = extend.getTextValue();
        else if (extend.getDataType() == 4)
          value = String.valueOf(extend.getFloatValue());
        if (value == null || value.trim().length() == 0)  return "";

        if (type == 3)       //如果为文件上传
        {
          Column column = columnMgr.getColumn(columnID);
          String dirName = column.getDirName();

          int width = Integer.parseInt(e.getAttributeValue("width"));
          int height = Integer.parseInt(e.getAttributeValue("height"));
          String path = "sites/" + sitename + dirName + "images/" + value;

          //如果为图片
          if (value.toLowerCase().indexOf(".gif") > 0 || value.toLowerCase().indexOf(".jpg") > 0 ||
                  value.toLowerCase().indexOf(".jpeg") > 0 || value.toLowerCase().indexOf(".bmp") > 0 ||
                  value.toLowerCase().indexOf(".png") > 0)
          {
            String cname = "";
            if (xml.length() > 0)
            {
              Reader r = new StringReader(xml);
              Document d = builder.build(r);
              cname = d.getRootElement().getChildText(attrName);
              r.close();
            }

            if (xml.length() > 0 && cname.equals(""))
            {
              buf.append(value);
            }
            else
            {
              if (width > 0)
              {
                if (height > 0)
                  buf.append("<img src=" + path + " border=0 width=" + width + " height=" + height + ">");
                else
                  buf.append("<img src=" + path + " border=0 width=" + width + ">");
              }
              else
              {
                buf.append("<img src=" + path + " border=0>");
              }
            }
          }
          else if (value.toLowerCase().indexOf(".swf") > 0)     //如果为FLASH
          {
            buf.append("<OBJECT classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'");
            buf.append("        codebase='http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=5,0,0,0'");
            buf.append("        height=" + height + " width=" + width + ">");
            buf.append("<PARAM NAME=movie VALUE='" + path + "'>");
            buf.append("<PARAM NAME=quality VALUE=autohigh>");
            buf.append("<EMBED src='" + path + "' quality=autohigh TYPE='application/x-shockwave-flash' PLUGINSPAGE='http://www.macromedia.com/shockwave/download/index.cgi?P1_Prod_Version=ShockwaveFlash'");
            buf.append("       height=" + height + " width=" + width + ">");
            buf.append("</EMBED>");
            buf.append("</OBJECT>");
          }
          else                                                  //如果为其他文件，则加上连接
          {
            if (xml == null || xml.trim().length() == 0)
            {
              return "";
            }
            Reader r = new StringReader(xml);
            Document d = builder.build(r);
            String cname = d.getRootElement().getChildText(attrName);
            if (cname != null && cname.trim().length() > 0)           //文章模板里的标记
              buf.append("<a href=" + path + " target=_blank>" + cname + "</a>");
            else                         //栏目模板里的标记
              buf.append(path);
            r.close();
          }
        }
        else
        {
          if (value.equals("0") || value.equals("0.0"))  value = "";
          buf.append(value);
        }
        in.close();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }

      return buf.toString();
    }
    */

    public String relatedArticleList(XMLProperties properties, int articleID, int siteid, String sitename, int modeltype,int columnID) throws TagException {
        String tagName = properties.getName();
        IArticleManager articleManager = ArticlePeer.getInstance();

        int selectNum = 0;
        int listType = 0;
        int area = 0;
        String idNum = "";
        String keyword = "";
        try {
            keyword = properties.getProperty(tagName.concat(".KEYWORD"));
        }
        catch (Exception e) {
        }

        if (articleID == 0 && (keyword == null || keyword.trim().length() == 0))
            return "mark error";

        try {
            selectNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
            listType = Integer.parseInt(properties.getProperty(tagName.concat(".LISTTYPE")));
            area = Integer.parseInt(properties.getProperty(tagName.concat(".AREA")));
            if (area == 1)
                idNum = properties.getProperty(tagName.concat(".COLUMNIDS"));
        }
        catch (NumberFormatException ignored) {
        }

        if (keyword == null || keyword.trim().length() == 0) {
            try {
                keyword = articleManager.getArticle(articleID).getKeyword();
                if (cpool.getType().equals("mssql"))   //如果是sybase不应转码
                    keyword = StringUtil.iso2gbindb(keyword);
            }
            catch (ArticleException e) {
                e.printStackTrace();
            }
        }

        if (keyword == null || keyword.trim().length() == 0) return "";

        List artList = new ArrayList();
        StringBuffer keyWordsSql = new StringBuffer();
        int maxKeywordNum = 0;

        keyword = keyword.replaceAll("；", ";");
        keyword = keyword.replaceAll(",", ";");
        String keywords[] = keyword.split(";");

        if (keywords != null) {
            if (keywords.length > 10)
                maxKeywordNum = 10;
            else
                maxKeywordNum = keywords.length;
        }

        int match = 0;
        if (properties.getProperty(tagName.concat(".MATCHTYPE")) != null) {
            match = Integer.parseInt(properties.getProperty(tagName.concat(".MATCHTYPE")));
        }

        if (match == 0) {
            for (int i = 0; i < maxKeywordNum; i++) {
                keyWordsSql.append("(maintitle like '%" + keywords[i] + "%') or ");
                keyWordsSql.append("(keyword like '%" + keywords[i] + "%') or ");
            }
        } else {
            for (int i = 0; i < maxKeywordNum; i++) {
                keyWordsSql.append("(maintitle = '" + keywords[i] + "') or ");
                keyWordsSql.append("(keyword = '" + keywords[i] + "') or ");
            }
        }

        String sqlStr = keyWordsSql.toString().trim();
        sqlStr = sqlStr.substring(0, sqlStr.length() - 2).trim();

        //剔除当前文章
        if (articleID > 0) sqlStr = "(" + sqlStr + ") and ID <> " + articleID;

        try {
            if (area == 0) {
                idNum = "(select ID from tbl_column where siteid=" + siteid + ")";
            } else if (idNum.indexOf("-getAllSubArticle") > -1) {
                idNum = idNum.substring(1, idNum.length() - 1);
                idNum = "(" + getSubTreeColumnIDs(siteid,idNum) + ")";
            }

            artList = articleManager.getArticles(idNum, sqlStr, selectNum);
        }
        catch (ArticleException e) {
            e.printStackTrace();
        }

        return genRelateList(properties, listType, artList, siteid, sitename,modeltype,columnID);
    }

    //通过全文索引获取相关文章
    //add the program by peter song on 04/18/2008
    public String relatedArticleList(XMLProperties properties, int articleID, int siteid, String sitename, int modeltype,int columnID, int sflag) throws TagException {
        String tagName = properties.getName();
        IArticleManager articleManager = ArticlePeer.getInstance();

        int selectNum = 0;
        int listType = 0;
        int area = 0;
        String idNum = "";
        String keyword = "";
        //Article article = null;
        String title=null;
        try {
            keyword = properties.getProperty(tagName.concat(".KEYWORD"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (articleID == 0 && (keyword == null || keyword.trim().length() == 0))
            return "mark error";

        try {
            selectNum = Integer.parseInt(properties.getProperty(tagName.concat(".ARTICLENUM")));
            listType = Integer.parseInt(properties.getProperty(tagName.concat(".LISTTYPE")));
            area = Integer.parseInt(properties.getProperty(tagName.concat(".AREA")));
            if (area == 1) idNum = properties.getProperty(tagName.concat(".COLUMNIDS"));
        }
        catch (NumberFormatException ignored) {
        }

        if (keyword == null || keyword.trim().length() == 0) {
            try {
                String maintitle = articleManager.getArticle(articleID).getMainTitle();
                if (cpool.getType().equals("mssql")) maintitle = StringUtil.gb2iso4View(maintitle);
                title = maintitle;
                keyword = "";
                Reader r = new StringReader(maintitle);
                Analyzer analyzer = new IKAnalyzer();
                TokenStream ts = analyzer.tokenStream("field",r);
                CharTermAttribute ch = ts.addAttribute(CharTermAttribute.class);
                Token t=null;
                int count = 0;
                while (ts.incrementToken()) {
                    if (ch.toString().length()>1) keyword = keyword + ch.toString() + ";";
                    count = count + 1;
                }
                int posi = keyword.lastIndexOf(";");
                if (posi > -1) keyword = keyword.substring(0, posi);
                ts.close();
                r.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            keyword = keyword.replaceAll("；", ";");
            keyword = keyword.replaceAll("，", ";");
            keyword = keyword.replaceAll(",", ";");
            keyword = keyword.replaceAll("#", ";");
            int posi = keyword.lastIndexOf(";");
            if (posi > -1) keyword = keyword.substring(0, posi);
        }

        List artList = new ArrayList();
        try {
            SearchFilesServlet searchMgr = new SearchFilesServlet();
            artList = searchMgr.getRelatedArticles(keyword,title, selectNum, idNum, articleID, sitename);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return genRelateList(properties, listType, artList, siteid, sitename,modeltype,columnID);
    }

    private String genRelateList(XMLProperties properties, int listType, List artList, int siteid, String sitename,int modeltype, int columnID) {
        String result = "";
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();

        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();
            String head = content.substring(0, content.indexOf("<!--ROW-->"));
            String temp = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));
            String tail = content.substring(content.lastIndexOf("<!--ROW-->") + 10);

            //获得文章列表栏数
            int columnNum = getColumnNum(temp);
            int articleNum = artList.size();
            int rowNum = articleNum;
            if (columnNum > 1) {
                rowNum = (int) Math.ceil((double) articleNum / columnNum);
            }

            Article article=null;
            temp = temp.replaceAll("<%%sitename%%>", sitename);

            for (int i = 0; i < rowNum; i++) {
                String arr[] = getColumnString(temp);
                for (int j = i * columnNum; j < (i + 1) * columnNum; j++) {
                    int p = 0;
                    if (columnNum > 1) p = (j - i * columnNum) * 2 + 1;
                    if (j > articleNum - 1) {
                        arr[p] = "";        //删除多余的标记
                        continue;
                    }
                    //替换碎片文件中的标记
                    article = (Article) artList.get(j);
                    arr[p] = processMarknameInStyle(properties, article, 0, arr[p], sitename,modeltype, siteid, 3, columnID, false);
                }

                for (int j = 0; j < arr.length; j++) {
                    result = result + arr[j];
                }
            }

            result = head + "\r\n" + result + "\r\n" + tail;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String processLink(XMLProperties properties, int articleID, String sitename,int modeltype) throws TagException {
        String tagName = properties.getName();
        int columnID;
        String weight;
        Article article = null;
        IArticleManager articleManager = ArticlePeer.getInstance();

        try {
            weight = properties.getProperty(tagName.concat(".WEIGHT"));
            columnID = Integer.parseInt(properties.getProperty(tagName.concat(".COLUMNIDS")));
            if (weight.equalsIgnoreCase("articleid")) {
                article = articleManager.getArticleByWeight(columnID, articleID, 0);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (article != null)
            return getArticleURL(article,modeltype);
        else
            return "#";
    }

    public String processTopStories(XMLProperties properties, int siteID, int columnID, String username, String sitename, int modeltype,String fragPath, boolean isPreview) throws TagException {
        String result = "";
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();

        int listType = 0;
        int lastest = 0;
        int articleNum = 0;
        int beginNum = 0;
        int innerFlag = 0;
        int order = 0;
        String ids = "";
        String power = "";
        String headstr = "";
        String midstr = "";
        String tailstr = "";
        boolean selectArticle = false;

        try {
            listType = Integer.parseInt(properties.getProperty(properties.getName().concat(".LISTTYPE")));
            innerFlag = Integer.parseInt(properties.getProperty(properties.getName().concat(".INNERHTMLFLAG")));
            if (properties.getProperty(properties.getName().concat(".ARTICLE.ID")) != null) {
                selectArticle = true;
                ids = properties.getProperty(properties.getName().concat(".ARTICLE.ID"));
                if (properties.getProperty(properties.getName().concat(".ORDER")) != null)
                    order = Integer.parseInt(properties.getProperty(properties.getName().concat(".ORDER")));
            } else    //栏目
            {
                ids = properties.getProperty(properties.getName().concat(".COLUMN.ID"));
                articleNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE.ARTICLENUM")));
                lastest = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE.LASTEST")));
                beginNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLE.BEGINNUM")));
                power = properties.getProperty(properties.getName().concat(".ARTICLE.POWER"));
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //组合查询条件，得到ArticleList
        List articleList = new ArrayList();
        try {
            if (selectArticle) {
                if (order == 1 || cpool.getCustomer().equals("linktone")) {
                    articleList = articleMgr.getArticles4PublishTopStories(ids);
                } else {
                    String[] tempID = ids.split(",");
                    for (int i = 0; i < tempID.length; i++) {
                        Article article = articleMgr.getArticle(Integer.parseInt(tempID[i]));
                        if (article != null) articleList.add(article);
                    }
                }
            } else {
                if (ids.equals("0")) ids = String.valueOf(columnID);
                String where = "WHERE ColumnID IN (" + ids + ") AND ";

                String beginPower = power.substring(0, power.indexOf("-"));
                if (beginPower.trim().length() > 0)
                    where = where + "docLevel >= " + beginPower + " AND ";

                String endPower = power.substring(power.indexOf("-") + 1);
                if (endPower.trim().length() > 0)
                    where = where + "docLevel <= " + endPower + " AND ";

                where = where.substring(0, where.length() - 4);
                if (lastest == 1) where = where + "ORDER BY PublishTime DESC";

                articleList = articleMgr.getArticlesforPublishTopstories(beginNum, articleNum, where);
            }
        }
        catch (ArticleException e) {
            e.printStackTrace();
        }

        //得到样式文件内容
        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();

            headstr = content.substring(0, content.indexOf("<!--ROW-->"));
            midstr = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));
            tailstr = content.substring(content.lastIndexOf("<!--ROW-->") + 10);
        }
        catch (viewFileException e) {
            e.printStackTrace();
        }

        //支持分列
        int columnNum = getColumnNum(midstr);
        articleNum = articleList.size();
        int rowNum = articleNum;
        if (columnNum > 1) {
            rowNum = (int) Math.ceil((double) articleNum / columnNum);
        }

        midstr = StringUtil.replace(midstr, "\r\n", "");
        midstr = StringUtil.replace(midstr, "<%%sitename%%>", sitename);

        for (int i = 0; i < rowNum; i++) {
            String arr[] = getColumnString(midstr);
            for (int j = i * columnNum; j < (i + 1) * columnNum; j++) {
                int p = 0;
                if (columnNum > 1) p = (j - i * columnNum) * 2 + 1;

                //删除多余的标记
                if (j > articleNum - 1) {
                    arr[p] = "";
                    continue;
                }
                Article article = (Article) articleList.get(j);
                arr[p] = processMarknameInStyle(properties, article, 0, arr[p], sitename,modeltype, siteID, 4, columnID, false);
            }

            for (int j = 0; j < arr.length; j++) {
                result = result + arr[j];
            }
        }

        if (!isPreview && innerFlag == 1) {
            String content = result.trim();
            if (content.length() > 0)
                content = headstr + content + tailstr;
            result += createIncludeFile(columnID, siteID, content, fragPath, username);
        }

        return result;
    }

    //this method add by Eric on 2004-10-26 for ad
    public String formatADVPosition(XMLProperties properties, int columnID, int siteid) throws TagException {
        String result;
        String sizeid = properties.getProperty(properties.getName().concat(".AD_SIZE"));
        String positionid = properties.getProperty(properties.getName().concat(".AD_POS"));
        String typeid = properties.getProperty(properties.getName().concat(".AD_TYPE"));

        int width = 0;
        int height = 0;
        if (sizeid.toLowerCase().indexOf("x") != -1) {
            width = Integer.parseInt(sizeid.substring(0, sizeid.toLowerCase().indexOf("x")).trim());
            height = Integer.parseInt(sizeid.substring(sizeid.toLowerCase().indexOf("x") + 1, sizeid.length()).trim());
        }

        IColumnManager columnMgr = ColumnPeer.getInstance();
        Column column = new Column();
        try {
            column = columnMgr.getColumn(columnID);
        } catch (Exception e) {
            System.out.println("Get the column failed in formatADVPosition method");
        }
        String dirname = column.getDirName();

        ISiteInfoManager siteinfoMgr = SiteInfoPeer.getInstance();
        SiteInfo siteinfo = new SiteInfo();
        try {
            siteinfo = siteinfoMgr.getSiteInfo(siteid);
        } catch (Exception e) {
            System.out.println("Get the siteinfo failed in formatADVPosition method");
        }
        String sitename = siteinfo.getDomainName();

        //在此判断该column在ad系统中是否已定义，如果没有定义，result返回空
        if ((!typeid.equalsIgnoreCase("text")) && (!typeid.equalsIgnoreCase("javascript")) && (!typeid.equalsIgnoreCase("FloatPauseScript"))) {
            result = "<iframe border=0 FRAMEBORDER=0 scrolling=0 HSPACE=0 VSPACE=0 MARGINWIDTH=0 MARGINHEIGHT=0 width=\"" + width + "\" height=\"" + height + "\"" +
                    " src=http://demo.shop8888.com:8078/adserver/getAd?siteId=" + sitename + "&channelId=" + dirname.replaceAll("/", ".") + "&posId=" + positionid + "&sizeId=" + sizeid + "&typeId=" + typeid + ">" +
                    "</iframe>";
        } else {
            result = "<SCRIPT LANGUAGE=\"JavaScript1.1\" SRC=\"http://demo.shop8888.com:8078/adserver/getAd?siteId=" + sitename + "&channelId=" + dirname.replaceAll("/", ".") + "&posId=" + positionid + "&sizeId=" + sizeid + "&typeId=" + typeid + "\" > </SCRIPT>";
        }

        return result;
    }

    //处理文章推荐标记开始
    public String processCommendArticle(XMLProperties properties, int siteID, int columnID, String username, String sitename,int modeltype, String fragPath, boolean isPreview) throws TagException {
        StringBuffer result = new StringBuffer();
        IViewFileManager viewfileMgr = viewFilePeer.getInstance();
        IArticleManager articleMgr = ArticlePeer.getInstance();

        int listType = 0;
        int articleNum = 0;
        int innerFlag = 0;
        String headstr = "";
        String midstr = "";
        String tailstr = "";
        String order = "";
        int columnid = 0;
        int selectway = 0;
        String ids = "";

        try {
            columnid = Integer.parseInt(properties.getProperty(properties.getName().concat(".COLUMNID")));
            listType = Integer.parseInt(properties.getProperty(properties.getName().concat(".LISTTYPE")));
            innerFlag = Integer.parseInt(properties.getProperty(properties.getName().concat(".INNERHTMLFLAG")));
            selectway = Integer.parseInt(properties.getProperty(properties.getName().concat(".SELECTWAY")));
            if (selectway == 0) {
                ids = properties.getProperty(properties.getName().concat(".ARTICLE.ID"));
            } else {
                articleNum = Integer.parseInt(properties.getProperty(properties.getName().concat(".ARTICLENUM")));
                order = properties.getProperty(properties.getName().concat(".ORDER"));
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //组合查询条件，得到ArticleList
        List articleList;
        articleList = null;
        try {

            String where;
            if (selectway == 0) {
                where = "where a.id in(" + ids + ") and a.id = b.articleid and b.columnid = " + columnid;
                articleList = articleMgr.getArticlesforPublishCommendArticle(where);
            } else {
                String orderby = "";
                String[] orders = order.split(",");
                if (order != null) {
                    int orderType = Integer.parseInt(orders[0]);
                    int orderType1 = Integer.parseInt(orders[1]);
                    if (orderType == 0) {
                        switch (orderType1) {
                            case 0:
                            case 3:
                                orderby = "order by b.createdate desc";
                                break;
                            case 1:
                                orderby = "and b.orders != 0 order by b.orders desc";
                                break;
                            case 2:
                                orderby = "and b.orders !=0 order by b.orders asc";
                                break;
                            case 4:
                                orderby = "order by b.createdate asc";
                                break;
                        }
                    } else if (orderType == 1) {
                        switch (orderType1) {
                            case 0:
                            case 3:
                            case 4:
                                orderby = "order by b.createdate desc";
                                break;
                            case 1:
                                orderby = "and b.orders !=0 order by b.createdate desc,b.orders desc";
                                break;
                            case 2:
                                orderby = "and b.orders !=0 order by b.createdate desc,b.orders asc";
                                break;
                        }
                    } else if (orderType == 2) {
                        switch (orderType1) {
                            case 0:
                            case 3:
                            case 4:
                                orderby = "order by b.createdate asc";
                                break;
                            case 1:
                                orderby = "and b.orders !=0 order by b.createdate asc,b.orders desc";
                                break;
                            case 2:
                                orderby = "and b.orders !=0 order by b.createdate asc,b.orders asc";
                                break;
                        }
                    } else if (orderType == 3) {
                        switch (orderType1) {
                            case 0:
                            case 1:
                            case 2:
                                orderby = "and b.orders !=0 order by b.orders desc";
                                break;
                            case 3:
                                orderby = "and b.orders !=0 order by b.orders desc,b.createdate desc";
                                break;
                            case 4:
                                orderby = "and b.orders !=0 order by b.orders desc,b.createdate asc";
                                break;
                        }
                    } else if (orderType == 4) {
                        switch (orderType1) {
                            case 0:
                            case 1:
                            case 2:
                                orderby = "and b.orders !=0 order by b.orders asc";
                                break;
                            case 3:
                                orderby = "and b.orders !=0 order by b.orders asc,b.createdate desc";
                                break;
                            case 4:
                                orderby = "and b.orders !=0 order by b.orders asc,b.createdate asc";
                                break;
                        }
                    }
                }
                where = "WHERE a.id = b.articleid and b.columnid = " + columnid + " " + orderby;
                articleList = articleMgr.getArticlesforPublishCommendArticle(articleNum, where);
            }
        }
        catch (ArticleException e) {
            e.printStackTrace();
        }

        //得到样式文件内容
        try {
            String content = viewfileMgr.getAViewFile(listType).getContent();

            headstr = content.substring(0, content.indexOf("<!--ROW-->"));
            midstr = content.substring(content.indexOf("<!--ROW-->") + 10, content.lastIndexOf("<!--ROW-->"));
            tailstr = content.substring(content.lastIndexOf("<!--ROW-->") + 10);
        }
        catch (viewFileException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < articleList.size(); i++) {

            Article article = (Article) articleList.get(i);
            result.append(processMarknameInStyle(properties, article, 0, midstr, sitename,modeltype, siteID, 5, columnID, false));
        }

        if (!isPreview && innerFlag == 1) {
            String content = result.toString().trim();
            if (content.length() > 0)
                content = headstr + content + tailstr;
            result = new StringBuffer();
            result.append(createIncludeFile(columnID, siteID, content, fragPath, username));
        }

        return result.toString();
    }
    //处理文章推荐标记结束

    private String toImage(String filename, String sitename, Article article, int siteID) {
        if (filename != null) {
            if (!filename.startsWith("http://")) {
                if (filename.toLowerCase().indexOf(".gif") != -1 || filename.toLowerCase().indexOf(".jpg") != -1 ||
                        filename.toLowerCase().indexOf(".jpeg") != -1 || filename.toLowerCase().indexOf(".png") != -1 ||
                        filename.toLowerCase().indexOf(".bmp") != -1) {
                    //IArticleManager articleMgr = ArticlePeer.getInstance();
                    try {
                        int otherSiteID = article.getSiteID();
                        if (siteID == otherSiteID) {
                            if (filename.trim().length() == 12)
                                filename = "<img src=" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                            else if (filename.trim().length() == 15)
                                filename = "<img src=/webbuilder/sites/" + sitename + article.getDirName() + "images/" + filename.trim() + " border=0>";
                            else
                                filename = "<img src=" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        } else {
                            IRegisterManager registerMgr = RegisterPeer.getInstance();
                            sitename = registerMgr.getSite(otherSiteID).getSiteName();
                            sitename = StringUtil.replace(sitename, "_", ".");
                            if (filename.trim().length() == 12)
                                filename = "<img src=" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                            else if ((filename.trim().length() == 15))
                                filename = "<img src=http://" + sitename + article.getDirName() + "images/" + filename.trim() + " border=0>";
                            else
                                filename = "<img src=http://" + sitename + "/" + article.getDirName() + "images/" + filename.trim() + " border=0>";
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            filename = "";
        }

        return filename;
    }

    private boolean isImage(String filename) {
        if (filename != null) {
            if (filename.toLowerCase().indexOf(".gif") != -1 || filename.toLowerCase().indexOf(".jpg") != -1 ||
                    filename.toLowerCase().indexOf(".jpeg") != -1 || filename.toLowerCase().indexOf(".png") != -1 ||
                    filename.toLowerCase().indexOf(".bmp") != -1)
                return true;
        }
        return false;
    }

    //this method add by Eric on 2005-9-9 for survey
    public String formatSurvey(XMLProperties properties, String extpath) throws TagException {
        int surveyID = Integer.parseInt(properties.getProperty(properties.getName()));
        String result = "<!--#include virtual=\"/biz_survey/survey_" + surveyID + ".html\"-->";

        return result;
    }

    //this method add by Eric on 2005-9-9 for counter
    public String formatCounter(XMLProperties properties, int siteid) throws TagException {
        int typeID = Integer.parseInt(properties.getProperty(properties.getName()));
        String result = "<iframe src=\"/webapp/counter/counter.jsp?siteid=" + siteid + "&imgtype=" + typeID + "\" width=\"100%\" marginwidth=\"0\" height=\"90\" marginheight=\"0\" scrolling=\"no\" frameborder=\"0\"></iframe>";

        return result;
    }

    //this method add by Eric on 2005-9-12 for user login
    public String formatUserLogin(XMLProperties properties, int siteid, String tag_content) throws TagException {
        String buf = StringUtil.replace(tag_content, "[", "<");
        buf = StringUtil.replace(buf, "]", ">");
        properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + buf);
        String result = properties.getProperty(properties.getName().concat(".CONTENT"));
        return result;
    }

    //this method add by Eric on 2005-9-14 for calendar
    public String formatCalendar(XMLProperties properties, int siteid, String extpath) throws TagException {
        String result = "<!--#include virtual=\"/php/calendar/calendar.shtml\"-->";
        return result;
    }

    //处理文章轮换图片 by feixiang  2008-11-25
    //pictype==1   使用小图片
    //pictype==2   使用中图片
    //pictype==3   使用大图片
    //pictype==0   使用原图片
    public String formatProductTurnPic(int articleID, int pictype) throws TagException {
        String temp = "";
        String htmlstr = "";
        IArticleManager aMgr = ArticlePeer.getInstance();
        List list = aMgr.getArticleTurnPic(articleID);
        if (list != null && list.size() > 0) {
            htmlstr = htmlstr + "<div class=\"play\">\n\r" +
                    "        <div id=\"picmenu\"></div>\n\r<div class=\"c\" id=\"times\">自动播放：<input type=\"radio\" name=\"times\" value=\"3000\" onclick=\"javascript:selectTimes(this.value);\">3秒<input type=\"radio\" name=\"times\" value=\"6000\" onclick=\"javascript:selectTimes(this.value);\">6秒<input type=\"radio\" name=\"times\" value=\"9000\" onclick=\"javascript:selectTimes(this.value);\">9秒<input type=\"radio\" name=\"times\" value=\"-1\" onclick=\"javascript:selectTimes(this.value);\">停止</div></div>\n\r"
                    + "<div class=\"pic\" id=\"pic\">\r\n";
            for (int i = 0; i < 1; i++) {
                Turnpic tpic = (Turnpic) list.get(i);
                String picname = tpic.getPicname();
                if (tpic != null) {
                    switch (pictype) {
                        case 0:
                            int dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ts." + picname.substring(dotposi + 1);
                            break;
                        case 1:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_s." + picname.substring(dotposi + 1);
                            break;
                        case 2:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ms." + picname.substring(dotposi + 1);
                            break;
                        case 3:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_m." + picname.substring(dotposi + 1);
                            break;
                        case 4:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ml." + picname.substring(dotposi + 1);
                            break;
                        case 5:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_l." + picname.substring(dotposi + 1);
                            break;
                        case 6:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_tl." + picname.substring(dotposi + 1);
                            break;
                        default:
                            break;
                    }
                    htmlstr = htmlstr + "<a onclick=\"javascript:doClick();\" href=\"#\"><img src=" + picname + " id=\"turn\" border=0></a><p id=\"picinfo\">" + StringUtil.gb2iso4View(tpic.getNotes()) + "</p></div>\r\n";
                }
            }
            htmlstr = htmlstr + "<div class=\"play\">\n\r" +
                    "        <div id=\"picmenu1\"></div>\n\r<div class=\"c\" id=\"times1\">自动播放：<input type=\"radio\" name=\"times\" value=\"3000\" onclick=\"javascript:selectTimes(this.value);\">3秒<input type=\"radio\" name=\"times\" value=\"6000\" onclick=\"javascript:selectTimes(this.value);\">6秒<input type=\"radio\" name=\"times\" value=\"9000\" onclick=\"javascript:selectTimes(this.value);\">9秒<input type=\"radio\" name=\"times\" value=\"-1\" onclick=\"javascript:selectTimes(this.value);\">停止</div></div>\n\r";
            temp = temp + "<script language=javascript>var obj,first,total,cn;var pics = new Array();\r\n";

            for (int i = 0; i < list.size(); i++) {  //初始化图片
                Turnpic tpic = (Turnpic) list.get(i);
                String picname = tpic.getPicname();
                if (tpic != null) {
                    switch (pictype) {
                        //case 0:
                        //    temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                        //    break;
                        case 0:
                            int dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ts." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 1:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_s." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 2:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ms." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 3:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_m." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 4:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_ml." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 5:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_l." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        case 6:
                            dotposi = picname.lastIndexOf(".");
                            picname = picname.substring(0, dotposi) + "_tl." + picname.substring(dotposi + 1);
                            temp = temp + "pics[" + i + "]=\"" + picname + "\";";
                            break;
                        default:
                            break;
                    }
                }
            }
            temp = temp + "var picinfo = new Array();\r\n";
            for (int i = 0; i < list.size(); i++) {
                //初始化图片说明信息
                Turnpic tpic = (Turnpic) list.get(i);
                if (tpic != null) {
                    String notes = tpic.getNotes();
                    if (notes != null && !notes.equals("") && !notes.equals("null")) {
                        notes = tpic.getNotes().replaceAll("\"", "\\\""); //考虑图片说明中的双引号
                        //temp = temp + "picinfo[" + i + "]=\"" + tpic.getNotes() + "\";";
                        temp = temp + "picinfo[" + i + "]=\"" + notes + "\";";
                    } else {
                        temp = temp + "picinfo[" + i + "]=\"" + "\";";
                    }
                }
            }
            //写入导航
            temp = temp + "function loadpic(times,flag){var str =\"<div class=fleft><img src='http://images.fantong.com/newt/pic_ts_left.gif' border=0/></div><div class=fleft>&nbsp;\";\r\n";//上一页
            //页数
            temp = temp + "for(var a=0;a<pics.length;a++){\r\n";
            temp = temp + "if(a == 0){\r\n";
            temp = temp + "str = str +\"<span class=number>1</span>&nbsp;\";\r\n";
            temp = temp + "}else{if(a<6){\r\n";
            temp = temp + "str = str +\"<a href='javascript:showPic(\"+a+\");' class=numbero>\"+(a+1)+\"</a>&nbsp;\";\r\n";
            temp = temp + "}}}";
            temp = temp + "str = str +\"<b>...</b><a href='javascript:showPic(\"+(pics.length-1)+\");' class=numbero>\"+(pics.length)+\"</a>&nbsp;</div><div class=fleft><a href='javascript:showPic(1);'><img src=http://images.fantong.com/newt/pic_ts_right.gif border=0/></a></div>\";\r\n"; //下一页
            temp = temp + "if(str.length > 0){\r\n";
            temp = temp + "document.getElementById(\"picmenu\").innerHTML = str;\r\n";
            temp = temp + "document.getElementById(\"picmenu1\").innerHTML = str;\r\n";
            temp = temp + "}\r\n";
            //temp = temp + "//if(flag){";
            temp = temp + "obj=document.getElementById(\"turn\");\r\n";
            temp = temp + "first=pics[0];\r\n";
            temp = temp + "total=pics[pics.length-1];\r\n";
            temp = temp + "cn=pics[0];\r\n";
            // temp = temp + "//setTimeout(\"change(\"+times+\")\",times/2);";
            //temp = temp + "//}";
            temp = temp + "}\r\n";
            temp = temp + "function change(times){\r\n";
            temp = temp + "url=\"\";\r\n";
            temp = temp + "var picinfos=\"\";\r\n";
            temp = temp + "if(cn!=total){\r\n";
            temp = temp + "for(var b=0;b<pics.length;b++){\r\n";
            temp = temp + "if(cn==pics[b]){\r\n";
            temp = temp + "url=pics[b+1];\r\n";
            temp = temp + "cn=pics[b+1];picinfos=picinfo[b];\r\n";
            temp = temp + "break;\r\n";
            temp = temp + "}\r\n";
            temp = temp + "}\r\n";
            temp = temp + "}\r\n";
            temp = temp + "else if(cn==total){\r\n";
            temp = temp + "url=pics[0];\r\n";
            temp = temp + "cn=pics[0];picinfos=picinfo[0];\r\n";
            temp = temp + "}\r\n";
            temp = temp + "obj.src=url;\r\n";
            temp = temp + "document.getElementById(\"picinfo\").innerHTML = picinfos;\r\n";
            temp = temp + "setTimeout(\"change(\"+times+\")\",times);\r\n";
            temp = temp + "}\r\n";
            temp = temp + "function showPic(i){\r\n";
            temp = temp + "obj=document.getElementById(\"turn\");\r\n";
            temp = temp + "obj.src=pics[i];\r\n";
            temp = temp + "document.getElementById(\"picinfo\").innerHTML = picinfo[i];\r\n";
            temp = temp + "var str =\"<div class=fleft><img src='http://images.fantong.com/newt/pic_ts_left.gif' border=0/></div><div class=fleft>&nbsp;\";\r\n";//上一页
            temp = temp + "if(i != 0){\r\n";
            temp = temp + "str =\"<div class=fleft><a href='javascript:showPic(\"+((i-1))+\");'><img src='http://images.fantong.com/newt/pic_ts_left.gif' border=0/></a></div><div class=fleft>&nbsp;\";\r\n";//上一页
            temp = temp + "}if((i-3)>0){str=str+\"<a href='javascript:showPic(0);' class=numbero>1</a>&nbsp;<b>...</b>\";}\r\n";
            temp = temp + "for(var a=0;a<pics.length;a++)\r\n";
            temp = temp + "{\r\n";
            temp = temp + "if(a == i){\r\n";
            temp = temp + "str = str + \"<span class=number>\"+(a+1)+\"</span>&nbsp;\";\r\n";
            temp = temp + "}else{if(i-1==a||i-2==a||i-3==a||i+1==a||i+2==a||i+3==a){\r\n";
            temp = temp + "str = str +\"<a href='javascript:showPic(\"+a+\");' class=numbero>\"+(a+1)+\"</a>&nbsp;\";\r\n";
            temp = temp + "}}}if((i + 4)<pics.length){str=str+\"<b>...</b><a href='javascript:showPic(\"+(pics.length-1)+\");' class=numbero>\"+(pics.length)+\"</a>&nbsp;\";}\r\n";
            temp = temp + "if(i == pics.length-1){\r\n";
            temp = temp + "str = str +\"</div><div class=fleft><img src=http://images.fantong.com/newt/pic_ts_right.gif border=0/></div>\";\r\n"; //下一页
            temp = temp + "}else{\r\n";
            temp = temp + "str = str +\"</div><div class=fleft><a href='javascript:showPic(\"+(i+1)+\");'><img src=http://images.fantong.com/newt/pic_ts_right.gif border=0/></a></div>\";\r\n"; //下一页
            temp = temp + "}\r\n";
            temp = temp + "if(str.length > 0){\r\n";
            temp = temp + "document.getElementById(\"picmenu\").innerHTML = str;\r\n";
            temp = temp + "document.getElementById(\"picmenu1\").innerHTML = str;\r\n";
            temp = temp + "}\r\n";
            temp = temp + "}\r\n";
            temp = temp + "function doClick(){\r\n";
            temp = temp + "var   currentNode   =   document.getElementById(\"turn\"); \r\n";
            temp = temp + "var urlstr = currentNode.src;\r\n";
            temp = temp + "window.open(urlstr);\r\n";
            temp = temp + "}\r\n";
            temp = temp + "function selectTimes(times){\r\n";
            temp = temp + " if(times>0){\r\n";
            temp = temp + "clearTimeout(setTimeout(\"0\")-1);\r\n";
            temp = temp + " setTimeout(\"change(\"+times+\")\",times);\r\n";
            temp = temp + "}else{\r\n";
            temp = temp + "clearTimeout(setTimeout(\"0\")-1);\r\n";
            temp = temp + "}\r\n";
            temp = temp + "}\r\n";
            temp = temp + "</script>" + htmlstr;
            temp = temp + "<script type=\"text/javascript\" xml:space=\"preserve\">\n" +
                    "loadpic(4000,false);</script>";
        }
        return temp;
    }

    //by Vincent 2010-07-14
    //组合图片路径函数
/*    public String getPicpath(String str){
   //  String tempstr = "/cizhuandiban/images/pic07l2x1t3.jpg";
        String[] strs = str.split("/");
        StringBuilder newStr = new StringBuilder();
        newStr.append("../");
        for(int i = 2;i < strs.length;i++){
            newStr.append(strs[i]);
            if(i < strs.length -1){
                newStr.append("/");
            }
        }
        return newStr.toString();
    }*/


    public String formatProductTurnPicOne(String tagcontent, int markId, int articleID, String sitename, boolean isPreview) throws TagException {

        String temp = "";
        IArticleManager aMgr = ArticlePeer.getInstance();
        List list = aMgr.getArticleTurnPic(articleID);   //取出该文章图片列表
        // String temppath = sitename.replace("\\","/").substring(0,(sitename.length()-1));   //获得图片所在目录

        int content_posi = 0;
        int slash_content_posi = 0;
        content_posi = tagcontent.indexOf("<CONTENT>");
        slash_content_posi = tagcontent.indexOf("</CONTENT>");
        String content = tagcontent.substring(content_posi + 9, slash_content_posi);
        tagcontent = StringUtil.replace(tagcontent, content, "");
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>" + tagcontent);
        String pictype = properties.getProperty(properties.getName().concat(".PICTYPE"));
        String disppictype = properties.getProperty(properties.getName().concat(".DISPPICTYPE"));
        int texiao_type = Integer.parseInt(properties.getProperty(properties.getName().concat(".TEXIAOTYPE")));

        int h = Integer.parseInt(properties.getProperty(properties.getName().concat(".H")));
        int w = Integer.parseInt(properties.getProperty(properties.getName().concat(".W")));
        int px = Integer.parseInt(properties.getProperty(properties.getName().concat(".PX")));

        int texiao_type_from_article = aMgr.getchangepic(articleID);      //图片轮换效果标志位
        if (texiao_type_from_article != 0) texiao_type = texiao_type_from_article;

        if (list != null && list.size() > 0) {
            if (texiao_type == 100) {
                temp = GetTurnPicJSOne(articleID, list, sitename, pictype, isPreview);
            } else if (texiao_type == 24) {
                int npictype = 0;
                if (pictype.equalsIgnoreCase("ts"))
                    npictype = 0;
                else if (pictype.equalsIgnoreCase("s"))
                    npictype = 1;
                else if (pictype.equalsIgnoreCase("ms"))
                    npictype = 2;
                else if (pictype.equalsIgnoreCase("m"))
                    npictype = 3;
                else if (pictype.equalsIgnoreCase("ml"))
                    npictype = 4;
                else if (pictype.equalsIgnoreCase("l"))
                    npictype = 5;
                else if (pictype.equalsIgnoreCase("tl"))
                    npictype = 6;
                temp = formatProductTurnPic(articleID, npictype);
            } else if (texiao_type == 25) {
                temp = GetTurnPicJSThree(articleID, list, sitename, pictype, isPreview);
            } else if (texiao_type >= 0 && texiao_type <= 23) {
                temp = GetTurnPicJSFour(articleID, list, sitename, pictype, disppictype, isPreview, texiao_type, h, w, px);
            }
        }
        return temp;
    }

    //组建轮换效果JS
    //随机变换效果
    private String GetTurnPicJSOne(int articleID, List list, String sitename, String ptype, boolean isPreview) {
        String temp = "";
        temp = temp + "<script language=JavaScript>\r\n";
        temp = temp + "var imgUrl=new Array();\r\n ";
        temp = temp + "var adNum=0;\r\n";
        temp = temp + "var j=0;\r\n";

        for (int i = 0; i < list.size(); i++) {
            Turnpic tpic = (Turnpic) list.get(i);
            String picname = tpic.getPicname();
            int posi = picname.indexOf(".");
            String extname = picname.substring(posi);
            picname = picname.substring(0, posi) + "_" + ptype + extname;
            //判断是否为预览
            if (isPreview) {
                temp = temp + "imgUrl[" + i + "]= \"sites/" + sitename + picname + "\";\r\n";    //图片路径
            }

            //发布路径
            else if (!isPreview) {
                String temppicname = picname.substring(picname.indexOf("/images/"));//图片路径
                temp = temp + "imgUrl[" + i + "]= \".." + temppicname + "\";\r\n";
            }

        }
        temp = temp + "for (i=0;i< " + list.size() + ";i++) {\r\n";
        temp = temp + "if (imgUrl[i]!=\"\"){\r\n";
        temp = temp + "j++;\r\n";
        temp = temp + "}\r\n";
        temp = temp + "else { break;} }\r\n";
        temp = temp + "function playTran(){\r\n" +
                " if (document.all)\r\n" +
                " imgInit.filters.revealTrans.play();\r\n" +
                "}\r\n";
        temp = temp + "var key = 0;\r\n";
        temp = temp + "function nextAd(){\r\n" +
                " if(adNum<j){adNum++ ;}\r\n" +
                " else adNum=1;\r\n" +
                "\r\n" +
                " if( key==0 ){\r\n" +
                " key=1;\r\n" +
                " } else if (document.all){\r\n" +
                " imgInit.filters.revealTrans.Transition=100;\r\n" +
                " imgInit.filters.revealTrans.apply();\r\n" +
                " playTran();\r\n" +
                "\r\n" +
                " }\r\n" +
                " document.images.imgInit.src=imgUrl[adNum-1];\r\n" +
                " theTimer=setTimeout(\"nextAd()\", 3000);\r\n" +
                "}\r\n";
        temp = temp + "</script>\r\n" +
                "<img style=\"FILTER: revealTrans(duration=2,transition=20);border-color:#000000;color:#000000\" src=\"javascript:nextAd()\"  border=1 class=img01 name=imgInit>";
        return temp;
    }

    //渐变轮换效果
    private String GetTurnPicJSTwo(int articleID, List list, String sitename, String ptype, boolean isPreview) throws TagException {
        String temp = "";
        temp = temp + "<SCRIPT>\r\n";
        temp = temp + "var NowFrame = 1;\r\n";
        temp = temp + "var MaxFrame = " + list.size() + ";\r\n";
        temp = temp + "var bStart = 0;\r\n";
        temp = temp + "function fnToggle() {\r\n";
        temp = temp + "var next = NowFrame + 1;\r\n";
        temp = temp + " if(next == MaxFrame+1)\r\n";
        temp = temp + "{\r\n";
        temp = temp + "NowFrame = MaxFrame;\r\n";
        temp = temp + "next = 1;\r\n";
        temp = temp + "}\r\n";
        temp = temp + " if(bStart == 0)\r\n";
        temp = temp + "{\r\n";
        temp = temp + "  bStart = 1;\r\n";
        temp = temp + " setTimeout('fnToggle()', 3500);\r\n";
        temp = temp + " return;\r\n";
        temp = temp + "}\r\n";
        temp = temp + "else {\r\n";
        temp = temp + "oTransContainer.filters[0].Apply();\r\n";
        temp = temp + "document.images['oDIV'+next].style.display = \"\";\r\n";
        temp = temp + "document.images['oDIV'+NowFrame].style.display = \"none\";\r\n";
        temp = temp + "oTransContainer.filters[0].Play(duration=2);\r\n";
        temp = temp + "if(NowFrame == MaxFrame){\r\n";
        temp = temp + " NowFrame = 1;\r\n";
        temp = temp + "}\r\n";
        temp = temp + "else {";
        temp = temp + " NowFrame++;\r\n }\r\n";
        temp = temp + " setTimeout('fnToggle()', 3500);\r\n";
        temp = temp + "} \r\n}\r\n";
        temp = temp + "fnToggle();\r\n";
        temp = temp + "</SCRIPT>\r\n";
        temp = temp + "<div align=\"center\">\r\n";
        temp = temp + "<DIV id=oTransContainer style=\"FILTER: progid:DXImageTransform.Microsoft.Wipe(GradientSize=1.0,wipeStyle=0, motion='forward'); WIDTH: 500px; HEIGHT: 352px\">\r\n";
        for (int i = 0; i < list.size(); i++) {
            Turnpic tpic = (Turnpic) list.get(i);
            String picname = tpic.getPicname();
            int posi = picname.indexOf(".");
            String extname = picname.substring(posi);
            picname = picname.substring(0, posi) + "_" + ptype + extname;

            if (i == 0) {
                temp = temp + "<IMG id=\"oDIV" + (i + 1) + "\" style=\"BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; BORDER-LEFT: black 1px solid; BORDER-BOTTOM: black 1px solid\" src=\"";
            } else {
                temp = temp + "<IMG id=\"oDIV" + (i + 1) + "\" style=\"BORDER-RIGHT: black 1px solid; BORDER-TOP: black 1px solid; BORDER-LEFT: black 1px solid; DISPLAY: none; BORDER-BOTTOM: black 1px solid\" src=\"";
            }
            //预览路径
            if (isPreview) {
                temp = temp + "sites/" + sitename + picname;
            }
            //发布路径
            else if (!isPreview) {
                String temppicname = picname.substring(picname.indexOf("/images/"));//图片路径
                temp = temp + ".." + temppicname;
            }
            temp = temp + "\" border=0 >\r\n";

        }
        temp = temp + "</DIV></div>\r\n";

        return temp;
    }
//生成flash效果

    private String GetTurnPicJSThree(int articleID, List list, String sitename, String ptype, boolean isPreview) {
        String temp = "";
        temp = temp + "<script language=\"Javascript\" type=\"text/javascript\">\r\n";
        temp = temp + "function ImgArray(len){\r\n";
        temp = temp + "this.length=len;\r\n";
        temp = temp + "}\r\n";
        temp = temp + "ImgName = new ImgArray(" + list.size() + ");\r\n";
        for (int i = 0; i < list.size(); i++) {
            Turnpic tpic = (Turnpic) list.get(i);
            String picname = tpic.getPicname();
            int posi = picname.indexOf(".");
            String extname = picname.substring(posi);
            picname = picname.substring(0, posi) + "_" + ptype + extname;

            //预览路径完成
            if (isPreview) {
                temp = temp + "ImgName[" + i + "]=\"sites/" + sitename + picname + "\"\r\n";
            }
            //发布路径
            else if (!isPreview) {
                String temppicname = picname.substring(picname.indexOf("/images/"));//图片路径
                temp = temp + "ImgName[" + i + "]=\".." + temppicname + "\"\r\n";
            }

        }
        temp = temp + "var t=0;\r\n";
        temp = temp + "function playImg(){\r\n";
        temp = temp + " if (t==" + (list.size() - 1) + ")";
        temp = temp + " {\r\n t=0;\r\n } \r\n";
        temp = temp + "else{\r\n t++;\r\n }\r\n";
        temp = temp + "img.style.filter=\"blendTrans(Duration=3)\";\r\n";
        temp = temp + "img.filters[0].apply();\r\n";
        temp = temp + "img.src=ImgName[t];\r\n";
        temp = temp + "img.filters[0].play();\r\n";
        temp = temp + "mytimeout=setTimeout(\"playImg()\",5000);\r\n" +
                "}\r\n" +
                "</script>\r\n";
        temp = temp + "<img src=\"http://www.cnknow.com/images/Main/1.gif\" name=\"img\"/>\r\n" +
                "<script language=\"Javascript\">playImg();</script>\r\n";
        return temp;
    }

    //模板轮换效果
    private String GetTurnPicJSFour(int articleID, List list, String sitename, String ptype, String dptype, boolean isPreview, int texiao_type, int h, int w, int px) {
        StringBuffer temp = new StringBuffer();
        String temponepic = "";
        String spicname = "";
        temp.append("<script language=javascript>\r\n");
        temp.append("function loadpic(){\r\n");
        temp.append("var pics = new Array();\r\n");
        temp.append("var spics = new Array();\r\n");
        for (int i = 0; i < list.size(); i++) {
            Turnpic tpic = (Turnpic) list.get(i);
            String picname = tpic.getPicname();
            int posi = picname.indexOf(".");
            String extname = picname.substring(posi);
            picname = picname.substring(0, posi) + "_" + dptype + extname;
            spicname = picname.substring(0, posi) + "_" + ptype + extname;  //缩率图路径


            if (isPreview) {//预览图片路径
                if (i == 0) {
                    temponepic = temponepic + "sites/" + sitename + picname; //首张图片路径
                }
                temp.append("pics[").append(i).append("]=\"sites/").append(sitename).append(picname).append("\";\r\n");
                temp.append("spics[").append(i).append("]=\"sites/").append(sitename).append(spicname).append("\";\r\n");
            } else {
                String temppicname = picname.substring(picname.indexOf("/images/")); //首张图片路径
                if (i == 0) {
                    temponepic = temponepic + ".." + temppicname;
                }
                String tempspic = spicname.substring(spicname.indexOf("/images/"));
                temp.append("pics[").append(i).append("]=\"..").append(temppicname).append("\";\r\n");
                temp.append("spics[").append(i).append("]=\"..").append(tempspic).append("\";\r\n");
            }
        }
        temp.append("var browser=navigator.appName;\r\n");
        temp.append("var b_version=navigator.appVersion;\r\n");
        temp.append("var version=parseFloat(b_version);\r\n");
        temp.append("var flag;\r\n");
        temp.append("if ((browser==\"Microsoft Internet Explorer\")&& (version>=4)){\r\n");
        temp.append("flag = 0;}\r\n");

        temp.append("var str = \"<table border=0 cellpadding=0 cellspacing=0><tr>\";\r\n");
        temp.append("for(var a=0;a<pics.length;a++)\r\n");
        temp.append("{\r\n");
        temp.append("str = str + \"<td valign=middle align=center style='padding:").append(px / 2).append("px;' ><a href=\\\"javascript:showPic('\"+pics[a]+\"'\" +\",\"+ flag + \" );\\\"><img src= '\"\r\n");
        temp.append("str = str + spics[a] + \"'\";\r\n");
        temp.append("str = str + \" style='border: 1px solid #D4D4D4; padding:3px 3px 3px 3px;' /></a></td>\";\r\n");
        temp.append("}\r\n");
        temp.append("str = str + \"</tr></table>\";\r\n");
        temp.append("if(str.length > 0){\r\n");
        temp.append("document.getElementById(\"picmenu\").innerHTML = str;\r\n");
        temp.append("}\r\n}\r\n");
        temp.append("function showPic(i,j){\r\n");
        temp.append("obj=document.getElementById(\"turn\");\r\n");
        temp.append("if( j == 0 ){");
        temp.append("with(obj.filters.revealTrans){\r\n");
        temp.append("apply();\r\n");
        temp.append("transition=").append(texiao_type).append(";\r\n");
        temp.append("obj.src=i;\r\n");
        temp.append("play();\r\n");
        temp.append("}\r\n}\r\n");
        temp.append("else{\r\n");
        temp.append("document.getElementById(\"pic\").innerHTML = \"<img src='\"+i+\"'>\";");
        temp.append("}\r\n}\r\n");
        temp.append("function doClick(){\r\n");
        temp.append("var   currentNode   =   document.getElementById(\"turn\");\r\n");
        temp.append("var urlstr = currentNode.src;\r\n");
        temp.append("window.open(urlstr); \r\n");
        temp.append("}\r\n</script>\r\n<center>\r\n");
        temp.append("<div id=\"pic\"><a onclick=\"javascript:doClick();\" href=\"#\"><img src=\" ").append(temponepic).append(" \" id=\"turn\" style='border: 1px solid #D4D4D4; padding:5px 5px 5px 5px;filter:revealTrans(duration=1)' /></a></div>\r\n");
        temp.append("<div id=\"picmenu\"></div>\r\n");
        temp.append("<script type=\"text/javascript\" xml:space=\"preserve\">\r\n");
        temp.append("loadpic();</script>\r\n");
        temp.append("</center>\r\n");


        return temp.toString();
    }

    //format include file by wangjian 2009-10-14
    public String formatInclude(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath,String appPath, boolean isPreview) throws TagException {
        String result = "";


        int order = 0;
        String ids = "";
        IModelManager modelMgr = ModelPeer.getInstance();
        IColumnManager columnManager = ColumnPeer.getInstance();

        if (properties.getProperty(properties.getName().concat(".INLUCDE.ID")) != null) {
            ids = properties.getProperty(properties.getName().concat(".INLUCDE.ID"));
            if (properties.getProperty(properties.getName().concat(".ORDER")) != null)
                order = Integer.parseInt(properties.getProperty(properties.getName().concat(".ORDER")));
        }
        if (ids != null && !ids.equals("")) {
            //获得所有的模板
            List includelist = modelMgr.getIncludeFileMaintitleString(ids);
            for (int i = 0; i < includelist.size(); i++) {
                String temp = (String) includelist.get(i);
                String filepath = "";
                try {
                    Model model = modelMgr.getModel(Integer.parseInt(temp.substring(0, temp.indexOf("|"))));
                    Column column = columnManager.getColumn(model.getColumnID());

                    //用于coosite模式
                    /*if (columnID > 0) {      //用于静态页面的包含文件路径生成
                        filepath = column.getDirName() + model.getTemplateName() + "." + column.getExtname();
                    } else {                 //用于程序模板包含文件的路径生成
                        filepath = "/" + sitename + column.getDirName() + model.getTemplateName() + "." + column.getExtname();
                    }*/

                    //用于软件发放模式
                    filepath = column.getDirName() + model.getTemplateName() + "." + column.getExtname();
                } catch (ModelException e) {
                    e.printStackTrace();
                } catch (ColumnException e) {
                    e.printStackTrace();
                }

                if (!isPreview) {
                    if (order == 0) {
                        result = result + "<%@ include file=\"" + filepath + "\" %>";
                    }
                    if (order == 1) {
                        result = result + "<!--#include virtual=\"" + filepath + "\"-->";
                    }
                    if (order == 2) {
                        result = result + "<object type=\"text/x-scriptlet\" data=\"" + filepath + "\" width=100% height=100%></object>";
                    }
                    if (order == 3) {
                        result = result + "<!--#include file =\"" + filepath + "\"-->";
                    }
                    if (order == 4) {
                        result = result + "<?php include (\"" + filepath + "\");?> ";
                    }
                } else {
                    String t_filePath = StringUtil.replace(filepath,"/",java.io.File.separator);
                    String filename = appPath + "sites" + java.io.File.separator + sitename + t_filePath;
                    try {
                        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
                        detector.add(new ParsingDetector(false));
                        detector.add(JChardetFacade.getInstance());
                        // UnicodeDetector用于Unicode家族编码的测定
                        detector.add(UnicodeDetector.getInstance());
                        // ASCIIDetector用于ASCII编码测定,测定字符串是否包含ASSCII
                        detector.add(ASCIIDetector.getInstance());
                        Charset charset = null;
                        File f = new File(filename);
                        charset = detector.detectCodepage(f.toURL());
                        InputStreamReader reader = null;
                        FileInputStream fis=new FileInputStream(filename);
                        if (charset.name().equalsIgnoreCase("utf-8"))
                            reader = new InputStreamReader(fis, "utf-8");
                        else
                            reader = new InputStreamReader(fis, "gbk");
                        BufferedReader br = new BufferedReader(reader);
                        String s1 = null;
                        while((s1 = br.readLine()) != null) {
                            result = result + s1 + "\r\n";
                        }
                        br.close();
                        reader.close();
                        fis.close();
                    } catch (FileNotFoundException exp) {
                        exp.printStackTrace();
                    } catch (IOException exp) {
                        exp.printStackTrace();
                    }
                }
            }
        }
        return result;
    }

    public String formatArticleClickNum(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, int articleID) throws TagException {
        String result = "";
        IArticleOnclickManager aopeer = ArticleOnclickPeer.getInstance();
        result = "<script type=\"text/javascript\" src=\"/_sys_js/clicknum.js\"></script><div id=onclicknum></div><script>updateonclicknum(" + articleID + ")</script>";
        return result;
    }

    //处理程序模板--订单回显
    public String formatRedisplayForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(14);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //购物车
    public String formatShoppingcarForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(12);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }
        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //信息检索页面
    public String formatSearchForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(11);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //订单生成页面
    public String formatGenerateOrderForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(13);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //订单查询页面
    public String formatOrderSearchForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(15);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //用户注册页面
    public String formatUserRegisterForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(16);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //用户登录页面
    public String formatUserLoginForProgram(XMLProperties properties, int siteID, String extpath) throws TagException {
        Program program = null;
        IProgramManager programMgr = ProgramPeer.getInstance();
        String result = "";

        List plist = programMgr.getProgramByType(16);

        for (int i = 0; i < plist.size(); i++) {
            program = (Program) plist.get(i);
            result = result + program.getProgram() + "-" + program.getL_type() + "B_I_Z_W_I_N_K";
        }

        if (result != "") result = result.substring(0, result.length() - 13);

        return result;
    }

    //程序模板处理结束
    //处理最近浏览过的页面
    public String getSeeCookie(XMLProperties properties, int siteID, String extpath, int articleid, int columnid) {
        String seecookie = "";
        String tagName = properties.getName();
        String viewfilestr = properties.getProperty(tagName.concat(".MARKID"));
        String str = properties.getProperty(tagName.concat(".MARKID"));
        viewfilestr = viewfilestr.substring(0, viewfilestr.indexOf("_"));
        int getnum = Integer.parseInt(str.substring(str.indexOf("_") + 1, str.length()));
        int viewid = Integer.parseInt(viewfilestr);
        seecookie = "<script src=\"/_sys_js/commentarticle.js\" type=\"text/javascript\"></script><div id=\"getcookie\"></div><script>getCookie(" + viewid + "," + getnum + ")</script>";
        return seecookie;


    }

    //处理调查信息
    public String formatDefineInfo(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath) throws TagException {
        String result = "";

        int order = 0;
        String ids = "";
        String wstytles = "";
        String astytles = "";
        int yangshi = 0;
        int userinfo = 0;
        String submitinfo = "";
        String submitpic = "";
        String resultinfo = "";
        String resultpic = "";
        if (properties.getProperty(properties.getName().concat(".DEFINE.ID")) != null) {
            ids = properties.getProperty(properties.getName().concat(".DEFINE.ID"));
            if (properties.getProperty(properties.getName().concat(".WSTYTLE")) != null)
                wstytles = properties.getProperty(properties.getName().concat(".WSTYTLE"));
            if (properties.getProperty(properties.getName().concat(".ASTYTLE")) != null)
                astytles = properties.getProperty(properties.getName().concat(".ASTYTLE"));
            if (properties.getProperty(properties.getName().concat(".YANGSHI")) != null)
                yangshi = Integer.parseInt(properties.getProperty(properties.getName().concat(".YANGSHI")));
            if (properties.getProperty(properties.getName().concat(".USERINFO")) != null)
                userinfo = Integer.parseInt(properties.getProperty(properties.getName().concat(".USERINFO")));
            if (properties.getProperty(properties.getName().concat(".SUBMITINFO")) != null)
                submitinfo = properties.getProperty(properties.getName().concat(".SUBMITINFO"));
            if (properties.getProperty(properties.getName().concat(".SUBMITPICINFO")) != null)
                submitpic = properties.getProperty(properties.getName().concat(".SUBMITPICINFO"));
            if (properties.getProperty(properties.getName().concat(".RESULTINFO")) != null)
                resultinfo = properties.getProperty(properties.getName().concat(".RESULTINFO"));
            if (properties.getProperty(properties.getName().concat(".RESULTPICINFO")) != null)
                resultpic = properties.getProperty(properties.getName().concat(".RESULTPICINFO"));
        }
        if (wstytles.indexOf(".") > -1) {
            wstytles = wstytles.substring(wstytles.indexOf(".") + 1);
        }
        if (astytles.indexOf(".") > -1) {
            astytles = astytles.substring(astytles.indexOf(".") + 1);
        }
        if (wstytles.indexOf("#") > -1) {
            wstytles = wstytles.substring(wstytles.indexOf("#") + 1);
        }
        if (astytles.indexOf("#") > -1) {
            astytles = astytles.substring(astytles.indexOf("#") + 1);
        }
        if (wstytles.equals("-1")) {
            wstytles = "";
        }
        if (astytles.equals("-1")) {
            astytles = "";
        }
        if (ids != null && !ids.equals("")) {
            //获得调查信息
            IDefineManager defineMgr = DefinePeer.getInstance();
            List questionlist = new ArrayList();
            List answerlist = new ArrayList();
            int sid = Integer.parseInt(ids);
            if (sid != -1) {
                try {
                    questionlist = defineMgr.getAllDefineQuestionsBySID(sid);
                } catch (DefineException e) {
                    e.printStackTrace();
                }
            }
            result += "<script type=\"text/javascript\">" +
                    "function checkNum(listsize, nother, qid, which, qtype) {\n" +
                    "      var otherBtnName = \"answerForm.other\" + qid;\n" +
                    "      var o = eval(otherBtnName);\n" +
                    "      if (nother == 1) {\n" +
                    "        if (listsize == which) {\n" +
                    "          if (qtype == 1) {\n" +
                    "            o.disabled = 0;\n" +
                    "          } else {\n" +
                    "            if (eval(\"answerForm.answer\" + qid + \"[\" + listsize + \"]\").checked) {\n" +
                    "              o.disabled = 0;\n" +
                    "            } else {\n" +
                    "              o.disabled = 1;\n" +
                    "            }\n" +
                    "          }\n" +
                    "        } else {\n" +
                    "          o.disabled = 1;\n" +
                    "        }\n" +
                    "      }" +
                    "}" +
                    "function viewResult() {\n" +
                    "      window.open(\"/_commons/viewResult.jsp?sid=" + sid + "\");\n" +
                    "    }" +
                    "</script>";
            result += "<form action=\"/_commons/answer.jsp\" method=\"post\" name=\"answerForm\" target=\"_blank\"><input type=\"hidden\" name=\"sid\" value=\"" + sid + "\">";
            if (yangshi == 0) {
                //横向
                result += "<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                if (questionlist.size() > 0) {
                    result += "<tr>\n" +
                            "  <td>\n" +
                            "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">";
                    for (int i = 0; i < questionlist.size(); i++) {
                        Define qdefine = (Define) questionlist.get(i);
                        int qid = qdefine.getQid();
                        String qname = qdefine.getQname();
                        int qtype = qdefine.getQtype();
                        int nother = qdefine.getNother();
                        int atype = qdefine.getAtype();
                        result += "<tr>\n" +
                                "        <td height=\"1\" ></td>\n" +
                                "      </tr>\n" +
                                "      <tr>\n" +
                                "        <td class=\"" + wstytles +
                                "\">&nbsp;&nbsp;" + StringUtil.iso2gb(qname) +
                                "</td>\n" +
                                "      </tr>\n" +
                                "      <tr>\n" +
                                "        <td height=\"1\"></td>\n" +
                                "      </tr>";
                        try {
                            answerlist = defineMgr.getAllDefineAnswersByQID(qid);
                        } catch (DefineException e) {
                            e.printStackTrace();
                        }
                        result += "<tr>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\">";
                        if (answerlist != null) {
                            for (int j = 0; j < answerlist.size(); j++) {
                                Define adefine = (Define) answerlist.get(j);
                                String qanswer = adefine.getQanswer();
                                String picurl = adefine.getPicurl();

                                if (atype != 1) {
                                    qanswer = picurl;
                                }
                                String checkstr = "";
                                if (j == 0) {
                                    checkstr = " checked ";
                                }
                                if (qtype == 1) {
                                    result += "&nbsp;&nbsp;<input type=\"radio\" name=\"answer" + qid +
                                            "\" value=\"" + StringUtil.iso2gb(qanswer) +
                                            "\" onClick=\"checkNum(" + (answerlist.size() - 1) + "," + nother + "," + qid + "," + j + "," + qtype +
                                            ");\" " + checkstr + " >";
                                    if (atype == 1) {
                                        result += StringUtil.iso2gb(qanswer);
                                    } else {
                                        result += "<a href=\"/answerspic/" + picurl + "\" target=_blank><img src=\"/answerspic/" + picurl + "\" alt=\"\" border=0></a>";
                                    }
                                    result += "&nbsp;&nbsp;";
                                } else {
                                    result += "<input type=\"checkbox\" name=\"answer" + qid + "\" value=\"" + StringUtil.iso2gb(qanswer) + "\" onClick=\"checkNum(" +
                                            (answerlist.size() - 1) + "," + nother + "," + qid + "," + j + "," + qtype + ");\" " + checkstr + " >";
                                    if (atype == 1) {
                                        result += StringUtil.iso2gb(qanswer);
                                    } else {
                                        result += "<a href=\"/answerspic/" + picurl + "\" target=_blank><img src=\"/answerspic/" + picurl + "\" alt=\"\" border=0></a>";
                                    }
                                    result += "&nbsp;&nbsp;";
                                }
                            }
                            if (nother == 1) {
                                result += "<input type=\"text\" name=\"other" + qid + "\"><script type=\"text/javascript\">answerForm.other" + qid + ".disabled = 1;</script>";
                            }
                        }
                        result += "</td>\n" +
                                "      </tr>";
                    }
                    //收集用户信息
                    if (userinfo == 1) {
                        result += "<tr>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\">";
                        result += "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                                "      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">用户名：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"username\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +
                                "      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">联系电话：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"phone\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +
                                /*"      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">年龄：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"age\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +*/
                                "      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">email：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"email\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +
                                "</table>";
                        result += "</td></tr>";
                    }

                    result += "</table>\n" +
                            "  </td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "  <td>\n" +
                            "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                            "      <tr>\n" +
                            "        <td></td>\n" +
                            "        <td align=\"center\">";
                    if (submitinfo != null && !submitinfo.equals("")) {
                        if (submitinfo.equals("submit")) {
                            //按钮
                            result += "<input type=\"submit\" name=\"subbutton\" value=\" 提交 \">\n";
                        } else if (submitinfo.equals("image")) {
                            //图片
                            result += "<input type=\"image\" src=\"/images/" + submitpic + "\" name=\"subbutton\" value=\" 提交 \" border=\"0\">\n";
                        }
                    }

                    result += "          &nbsp;&nbsp;\n";
                    if (resultinfo != null && !resultinfo.equals("")) {
                        if (resultinfo.equals("submit")) {
                            //按钮
                            result += "<input type=\"button\" name=\"surveybut\" value=\"查看结果\"  onclick=\"viewResult();\">&nbsp;&nbsp;";
                        } else if (resultinfo.equals("image")) {
                            if (resultpic != null && !resultpic.equals("") && !resultpic.equals("null")) {
                                //图片
                                result += "<a href=\"#\" onclick=\"viewResult();\"><img src=\"/images/" + resultpic + "\" border=\"0\"></a>\n";
                            } else {
                                result += "<input type=\"button\" name=\"surveybut\" value=\"查看结果\"  onclick=\"viewResult();\">&nbsp;&nbsp;";
                            }
                        }
                    }
                    result += "\n" +
                            "        </td>\n" +
                            "        <td align=300></td>\n" +
                            "      </tr>\n" +
                            "    </table>\n" +
                            "  </td>\n" +
                            "</tr></table>";

                }
            } else {
                //竖向
                result += "<table border=\"0\" width=\"100%\" cellspacing=\"1\" cellpadding=3>";
                if (questionlist.size() > 0) {
                    for (int i = 0; i < questionlist.size(); i++) {
                        Define qdefine = (Define) questionlist.get(i);
                        int qid = qdefine.getQid();
                        String qname = qdefine.getQname();
                        int qtype = qdefine.getQtype();
                        int nother = qdefine.getNother();
                        int atype = qdefine.getAtype();
                        result += "<tr>\n" +
                                "  <td width=\"5%\" align=left class=\"" + wstytles + "\">" + StringUtil.iso2gb(qname) + "</td>\n" +
                                "</tr>";
                        try {
                            answerlist = defineMgr.getAllDefineAnswersByQID(qid);
                        } catch (DefineException e) {
                            e.printStackTrace();
                        }
                        result += "<tr>\n" +
                                "  <td width=\"5%\" align=left>\n" +
                                "    <table border=0>";
                        if (answerlist != null) {
                            for (int j = 0; j < answerlist.size(); j++) {
                                Define adefine = (Define) answerlist.get(j);
                                String qanswer = adefine.getQanswer();
                                String picurl = adefine.getPicurl();

                                if (atype != 1) {
                                    qanswer = picurl;
                                }
                                result += " <tr>\n" +
                                        "        <td class=\"" + astytles + "\">";
                                String checkstr = "";
                                if (j == 0) {
                                    checkstr = " checked ";
                                }
                                if (qtype == 1) {
                                    result += "<input type=\"radio\" name=\"answer" + qid + "\" value=\"" + StringUtil.iso2gb(qanswer) + "\" onClick=\"checkNum(" + (answerlist.size() - 1) + "," + nother + "," + qid + "," + j + "," + qtype + ");\" " + checkstr + " >";
                                    if (atype == 1) {
                                        result += StringUtil.iso2gb(qanswer);
                                    } else {
                                        result += "<a href=\"/answerspic/" + picurl + "\" target=_blank><img src=\"/answerspic/" + picurl + "\" alt=\"\" border=0></a>";
                                    }
                                    result += "&nbsp;&nbsp;";
                                } else {
                                    result += "<input type=\"checkbox\" name=\"answer" + qid + "\" value=\"" + StringUtil.iso2gb(qanswer) + "\" onClick=\"checkNum(" +
                                            (answerlist.size() - 1) + "," + nother + "," + qid + "," + j + "," + qtype + ");\" " + checkstr + " >";
                                    if (atype == 1) {
                                        result += StringUtil.iso2gb(qanswer);
                                    } else {
                                        result += "<a href=\"/answerspic/" + picurl + "\" target=_blank><img src=\"/answerspic/" + picurl + "\" alt=\"\" border=0></a>";
                                    }
                                    result += "&nbsp;&nbsp;";
                                }
                            }

                            if (nother == 1) {
                                result += "<input type=\"text\" name=\"other" + qid + "\"><script type=\"text/javascript\">answerForm.other" + qid + ".disabled = 1;</script>";

                            }
                            result += "</td>\n" +
                                    "      </tr>";
                        }
                        result += "</table>\n" +
                                "  </td>\n" +
                                "</tr>";
                    }
                    //收集用户信息
                    if (userinfo == 1) {
                        result += "<tr>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\">";
                        result += "    <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">\n" +
                                "      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">用户名：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"username\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +
                                "      <tr>\n" +
                                "        <td align=\"right\" class=\"" + astytles + "\">联系电话：</td>\n" +
                                "        <td align=\"left\" class=\"" + astytles + "\"><input type=\"text\" name=\"phone\" value=\"\">\n" +
                                "        </td>\n" +
                                "       </tr>\n" +
                                "</table>";
                        result += "</td></tr>";
                    }
                    result += "<tr>\n" +
                            "  <td align=center>\n";
                    if (submitinfo != null && !submitinfo.equals("")) {
                        if (submitinfo.equals("submit")) {
                            //按钮
                            result += "<input type=\"submit\" name=\"subbutton\" value=\" 提交 \">\n";
                        } else if (submitinfo.equals("image")) {
                            //图片
                            result += "<input type=\"image\" src=\"/images/" + submitpic + "\" name=\"subbutton\" value=\" 提交 \" border=\"0\">\n";
                        }
                    }
                    if (resultinfo != null && !resultinfo.equals("") && resultpic != null && !resultpic.equals("") && !resultpic.equals("null")) {
                        if (resultinfo.equals("submit")) {
                            //按钮
                            result += "      <input type=\"button\" name=\"surveybut\" value=\"查看结果\"  onclick=\"viewResult();\">&nbsp;&nbsp;";
                        } else if (resultinfo.equals("image")) {
                            //图片
                            result += "      <a href=\"#\" onclick=\"viewResult();\"><img src=\"/images/" + resultpic + "\" border=\"0\"></a>\n";
                        }
                    }
                    result += "  </td>\n" +
                            "</tr></table>";
                }
            }
            result += "</form>";

        }
        return result;
    }

    //处理留言表单 add by feixiang 2010-08-30
    public String formatLeavemessageInfo(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markid) throws TagException {
        String result = "";
        String content = properties.getProperty(properties.getName().concat(".CONTENT"));
        String siteid = properties.getProperty(properties.getName().concat(".SITEID"));
        if (siteid != null) {
            siteID = Integer.parseInt(siteid);
        }

        String global_desc_style = null;
        String global_content_style = null;
        String global_desc_align = null;
        String global_content_align = null;
        XMLProperties contentProperties = null;
        contentProperties = new XMLProperties(content.substring(0, content.length() - 1));
        global_desc_style = contentProperties.getProperty("globalsetting.gdescstyle");
        global_content_style = contentProperties.getProperty("globalsetting.gcontentstyle");
        String b[] = contentProperties.getChildrenProperties("fields");
        String tbuf[] = new String[b.length];
        int fieldorder[] = new int[b.length];
        global_desc_align = contentProperties.getProperty("globalsetting.globaldescalign");
        global_content_align = contentProperties.getProperty("globalsetting.globalcontentalign");

        String submit = "";
        String reset = "";
        String scriptstr = "<script language=JavaScript1.2 src=\"/_sys_js/check.js\"></script>\r\n<script language=\"javascript\">\r\n" +
                "function check(form){\r\n";
        String formstr = "<form id=\"ldformid\" method=\"post\" action=\"/_commons/leavemessage.jsp\" onSubmit=\"return check(ldform);\" name=\"ldform\">\r\n" +
                "<input type=\"hidden\" name=\"startflag\" value=\"1\" />&nbsp;\r\n" +
                "<input type=\"hidden\" name=\"doCreate\" value=\"1\" />\r\n" +
                "<input type=\"hidden\" name=\"formid\" value=\"" + markid + "\" />\r\n" +
                "<input type=\"hidden\" name=\"siteid\" value=\"" + siteID + "\" />\r\n" +
                "<table border=\"0\" width=\"100%\">\r\n";

        if (global_desc_style != null) {
            if (global_desc_style.indexOf(".") > -1) {
                global_desc_style = global_desc_style.substring(global_desc_style.indexOf(".") + 1);
            }
            if (global_desc_style.indexOf("#") > -1) {
                global_desc_style = global_desc_style.substring(global_desc_style.indexOf("#") + 1);
            }
        }
        if (global_content_style != null) {
            if (global_content_style.indexOf(".") > -1) {
                global_content_style = global_content_style.substring(global_content_style.indexOf(".") + 1);
            }
            if (global_content_style.indexOf("#") > -1) {
                global_content_style = global_content_style.substring(global_content_style.indexOf("#") + 1);
            }
        }

        for (int j = 0; j < b.length; j++) {
            tbuf[j] = "";
            fieldorder[j] = 0;
        }

        for (int j = 0; j < b.length; j++) {
            String desc_style = contentProperties.getProperty("fields." + b[j] + ".descstyle");
            String content_style = contentProperties.getProperty("fields." + b[j] + ".contentstyle");
            String chinesename = contentProperties.getProperty("fields." + b[j] + ".chinesename");
            String name = contentProperties.getProperty("fields." + b[j] + ".name");
            String fieldtype = contentProperties.getProperty("fields." + b[j] + ".type.name");
            String isnull = contentProperties.getProperty("fields." + b[j] + ".isnull");
            String fieldlen = contentProperties.getProperty("fields." + b[j] + ".flen");
            int ordernum = j;
            String ordernum_s = contentProperties.getProperty("fields." + b[j] + ".order");
            if (ordernum_s != null && ordernum_s != "" && !ordernum_s.equalsIgnoreCase("null"))
                if (ordernum_s!=null)ordernum = Integer.parseInt(ordernum_s);


            if (desc_style != null && !chinesename.equals("重置") && !chinesename.equals("提交")) {
                if (desc_style.indexOf(".") > -1) {
                    desc_style = desc_style.substring(desc_style.indexOf(".") + 1);
                }
                if (desc_style.indexOf("#") > -1) {
                    desc_style = desc_style.substring(desc_style.indexOf("#") + 1);
                }
            }
            if (content_style != null && !chinesename.equals("重置") && !chinesename.equals("提交")) {
                if (content_style.indexOf(".") > -1) {
                    content_style = content_style.substring(content_style.indexOf(".") + 1);
                }
                if (content_style.indexOf("#") > -1) {
                    content_style = content_style.substring(content_style.indexOf("#") + 1);
                }
            }

            if (chinesename.equals("信件标题")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">信件标题：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">信件标题：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">信件标题：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"title\" name=\"title\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"100\" type=\"text\" class=\"" + global_content_style + "\" />(<font color=red>*</font>)</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"title\" name=\"title\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + global_content_style + "\" />(<font color=red>*</font>)</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"title\" name=\"title\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"100\" type=\"text\" class=\"" + content_style + "\" />(<font color=red>*</font>)</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"title\" name=\"title\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + content_style + "\" />(<font color=red>*</font>)</td>\r\n";
                } else {
                    tbuf[j] = tbuf[j] + "<input id=\"title\" name=\"title\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"100\" type=\"text\" />(<font color=red>*</font>)</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
                if (fieldlen != null)
                    scriptstr += "    if (!InputValid(form.title, 1, \"string\", 1, 1," + fieldlen + ",\"请输入留言标题\"))\r\n" +
                            "        return false;\r\n";
                else
                    scriptstr += "    if (!InputValid(form.title, 1, \"string\",0,0,0,\"请输入留言标题\"))\r\n" +
                            "        return false;\r\n";
            }

            if (chinesename.equals("信件内容")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">信件内容：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">信件内容：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">信件内容：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"" + fieldlen + "\" class=\"" + global_content_style + "\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"20\" class=\"" + global_content_style + "\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"" + fieldlen + "\" class=\"" + content_style + "\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"20\" class=\"" + content_style + "\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"" + fieldlen + "\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<textarea id=\"content\" name=\"content\" value=\"\" rows=\"10\" cols=\"20\" /></textarea>(<font color=red>*</font>)</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
                if (fieldlen != null)
                    scriptstr += "    if (!InputValid(form.content, 1, \"string\", 1, 1," + fieldlen + ",\"请输入留言内容\"))\r\n" +
                            "        return false;\r\n";
                else
                    scriptstr += "    if (!InputValid(form.content, 1, \"string\",0,0,0,\"请输入留言内容\"))\r\n" +
                            "        return false;\r\n";
            }

            if (chinesename.equals("是否对外公开联系信息")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">是否对外公开联系信息：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">是否对外公开联系信息：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">是否对外公开联系信息：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"radio\" class=\"" + global_content_style + "\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"radio\" class=\"" + global_content_style + "\" checked />不公开" +
                                "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" type=\"radio\" maxlength=\"200\" class=\"" + global_content_style + "\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" type=\"radio\" maxlength=\"200\" class=\"" + global_content_style + "\" checked />不公开" +
                                "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" size=\"" + fieldlen + "\" type=\"radio\" maxlength=\"200\" class=\"" + content_style + "\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" size=\"" + fieldlen + "\" type=\"radio\" maxlength=\"200\" class=\"" + content_style + "\" checked />不公开" +
                                "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" type=\"radio\" maxlength=\"200\" class=\"" + content_style + "\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" type=\"radio\" maxlength=\"200\" class=\"" + content_style + "\" checked />不公开" +
                                "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"radio\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"radio\" checked />不公开" +
                                "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"0\" maxlength=\"200\" type=\"radio\" />公开" +
                                "<input id=\"ocontactorid\" name=\"ocontactor\" value=\"1\" maxlength=\"200\" type=\"radio\" checked />不公开" +
                                "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("校验码")) {
                fieldorder[j] = ordernum;
                //加入验证码字段
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">验证码：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"left\"><input id=\"txtVerify\" name=\"txtVerify\" type=\"text\" /> <img id=\"safecode\" border=\"0\" name=\"cod\" alt=\"\" onclick=\"javascript:shuaxin()\" src=\"/_commons/drawImage.jsp\" /><a href=\"javascript:shuaxin()\" class=\"" + "\">看不清楚?换下一张</a></td>\r\n";
                tbuf[j] = tbuf[j] + "<div id=\"v_mag\"></div>\r\n";
                tbuf[j] = tbuf[j] + "</tr>\r\n";
                scriptstr += "    if (!InputValid(form.txtVerify, 1, \"string\",0,0,0,\"请输入正确的验证码\"))\r\n" +
                        "        return false;\r\n";
            }

            if (chinesename.equals("发信公司")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.company, 1, \"string\", 1, 1," + fieldlen + ",\"请输入留言人所在公司\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.company, 1, \"string\",0,0,0,\"请输入留言人所在公司\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">发信公司：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">发信公司：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">发信公司：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"text\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" type=\"text\" maxlength=\"200\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"200\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" type=\"text\" maxlength=\"200\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"200\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"company\" name=\"company\" value=\"\" maxlength=\"200\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("姓    名")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.linkman, 1, \"string\", 1, 1," + fieldlen + ",\"请输入联系人信息\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.linkman, 1, \"string\",0,0,0,\"请输入联系人信息\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">姓    名：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">姓    名：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">姓    名：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"100\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"100\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"100\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"linkman\" name=\"linkman\" value=\"\" maxlength=\"100\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("联系地址")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.links, 1, \"string\", 1, 1," + fieldlen + ",\"请输入联系地址\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.links, 1, \"string\",0,0,0,\"请输入联系地址\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">联系地址：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">联系地址：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">联系地址：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"500\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" type=\"text\" maxlength=\"500\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"500\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" type=\"text\" maxlength=\"500\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"500\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"links\" name=\"links\" value=\"\" maxlength=\"500\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("邮政编码")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.zip, 1, \"zip\", 1, 1," + fieldlen + ",\"请输入邮政编码\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.zip, 1, \"zip\",0,0,0,\"请输入邮政编码\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">邮政编码：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">邮政编码：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">邮政编码：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"6\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" type=\"text\" maxlength=\"6\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"6\" type=\"text\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" type=\"text\" maxlength=\"6\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"6\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"zip\" name=\"zip\" value=\"\" maxlength=\"6\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("电子邮件")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.email, 1, \"email\", 1, 1," + fieldlen + ",\"请输入正确的电子邮箱地址\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.email, 1, \"email\",0,0,0,\"请输入正确的电子邮箱地址\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">电子邮件：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">电子邮件：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">电子邮件：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"100\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" size=\"" + fieldlen + "\" type=\"text\" maxlength=\"100\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" type=\"text\" maxlength=\"100\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"100\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"email\" name=\"email\" value=\"\" maxlength=\"100\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("联系电话")) {
                fieldorder[j] = ordernum;
                tbuf[j] = tbuf[j] + "<tr>\r\n";
                String isnulls = "";
                if (isnull.equals("1")) {
                    //必填
                    isnulls = "(<font color=red>*</font>)";
                    if (fieldlen != null)
                        scriptstr += "    if (!InputValid(form.phone, 1, \"fax\", 1, 1," + fieldlen + ",\"请输入正确的联系电话\"))\r\n" +
                                "        return false;\r\n";
                    else
                        scriptstr += "    if (!InputValid(form.phone, 1, \"fax\",0,0,0,\"请输入正确的联系电话\"))\r\n" +
                                "        return false;\r\n";
                }
                if (desc_style != null) {
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">联系电话：</td>\r\n";
                } else if (global_desc_style != null)
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">联系电话：</td>\r\n";
                else
                    tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">联系电话：</td>\r\n";
                tbuf[j] = tbuf[j] + "<td width=\"70%\" align=\"" + global_content_align + "\">\r\n";
                if (global_content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"15\" type=\"text\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" maxlength=\"15\" type=\"text\" class=\"" + global_content_style + "\" />" + isnulls + "</td>\r\n";
                } else if (content_style != null) {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"15\" type=\"text\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" maxlength=\"15\" type=\"text\" class=\"" + content_style + "\" />" + isnulls + "</td>\r\n";
                } else {
                    if (fieldlen != null)
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" size=\"" + fieldlen + "\" maxlength=\"15\" type=\"text\" />" + isnulls + "</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<input id=\"phone\" name=\"phone\" value=\"\" maxlength=\"15\" type=\"text\" />" + isnulls + "</td>\r\n";
                }
                tbuf[j] = tbuf[j] + "</tr>\r\n";
            }

            if (chinesename.equals("提交")) {
                //提交按钮
                if (desc_style != null && !desc_style.equals("")) {
                    if (desc_style.equals("submit")) {
                        submit = "<input type=\"submit\" name=\"button\" value=\" 提交 \">";
                    } else if (desc_style.equals("image")) {
                        submit = "<input type=\"image\" src=\"/images/" + content_style + "\" name=\"button\" border=\"0\">";
                    }
                }
            }
            if (chinesename.equals("重置")) {
                //提交按钮
                if (desc_style != null && !desc_style.equals("")) {
                    if (desc_style.equals("submit")) {
                        reset = "<input type=\"button\" name=\"button1\" value=\" 重置 \" onclick=\"javascript:window.form.reset;\">";
                    } else if (desc_style.equals("image")) {
                        reset = "<a href=\"#\" onclick=\"javascript:window.form.reset();\"><img src=\"/images/" + content_style + "\" border=\"0\"></a>";
                    }
                }
            }
        }

        //比较确定每个注册项的顺序，数组排序
        int i = 0;   //当前项
        int k = 0;   //存放比当前数更小的项
        while (i < b.length) {
            k = 0;
            int num = fieldorder[i];
            //找到数组中剩余项中最小的数
            for (int j = i + 1; j < b.length; j++) {
                if (num > fieldorder[j]) {
                    k = j;
                    num = fieldorder[j];
                }
            }

            //k>0存在比当前项更小的数，将当前项与第K项进行交换
            if (k > 0) {
                String s = tbuf[i];
                tbuf[i] = tbuf[k];
                tbuf[k] = s;

                int tnum = fieldorder[i];
                fieldorder[i] = fieldorder[k];
                fieldorder[k] = tnum;
            }
            i = i + 1;
        }

        //按fieldorder数组中的顺序合并tbuf数组中各个项形成表单
        for (int j = 0; j < b.length; j++) {
            if (tbuf[j] != null) formstr = formstr + tbuf[j];
        }

        formstr += "<tr><td colspan=\"2\" align=\"center\">";
        formstr += submit + "&nbsp;&nbsp;" + reset;
        formstr += "</td></tr>";
        scriptstr += "    return true;\r\n" +
                "}\r\n" +
                "function shuaxin(){\r\n" +
                "    var verify=document.getElementById('safecode');\r\n" +
                "    verify.setAttribute('src','/_commons/drawImage.jsp?dumy='+Math.random());\r\n" +
                "}\r\n" +
                "</script>";
        formstr += "</table></form>";
        formstr = scriptstr + formstr;
        result = formstr;
        if (cpool.getType().equalsIgnoreCase("mssql")) result = StringUtil.gb2iso(result);
        return result;
    }

    //处理留言列表 add by feixiang 2010-08-31
    public String formatLeavemessageList(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "<div id=\"biz_message_list\"></div>\r\n";
        result += "<SCRIPT language=javascript>\r\n" +
                "    function getleavemessagelist(markid,startrows){\r\n" +
                "        var objXml;\r\n" +
                "        if (window.ActiveXObject) {\r\n" +
                "            objXml = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n" +
                "        } else if (window.XMLHttpRequest) {\r\n" +
                "            objXml = new XMLHttpRequest(\"\");\r\n" +
                "        }\r\n" +
                "        objXml.open(\"POST\", \"/_commons/leavemessagelist.jsp?markid=\"+markid+\"&startrow=\"+startrows, false);\r\n" +
                "        objXml.send(null);\r\n" +
                "        var retstr = objXml.responseText;\r\n" +
                "        if (retstr != null && retstr.length > 0) {\r\n" +
                "            document.getElementById(\"biz_message_list\").innerHTML = retstr;\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "</SCRIPT>\r\n" +
                "<script language=\"javascript\">getleavemessagelist(" + markID + ",0);</script>\r\n";
        return result;
    }

    //处理购物车 add by feixiang 2010-10-13
    public String formatShoppongCarList(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "<SCRIPT language=javascript src=\"/_commons/js/shoppingcart.js\"></script><div id=\"biz_shoppingcar_list\"></div>\r\n";
        result += "<SCRIPT language=javascript>\r\n" +
                "    function getshoppingcarlist(markid){\r\n" +
                "        var objXml;\r\n" +
                "        if (window.ActiveXObject) {\r\n" +
                "            objXml = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n" +
                "        } else if (window.XMLHttpRequest) {\r\n" +
                "            objXml = new XMLHttpRequest(\"\");\r\n" +
                "        }\r\n" +
                "        objXml.open(\"POST\", \"/_commons/getshoppingcarlist.jsp?markid=\"+markid, false);\r\n" +
                "        objXml.send(null);\r\n" +
                "        var retstr = objXml.responseText;\r\n" +
                "        if (retstr != null && retstr.length > 0) {\r\n" +
                "            document.getElementById(\"biz_shoppingcar_list\").innerHTML = retstr;\r\n" +
                "        }\r\n" +
                "    }\r\n" +
                "</SCRIPT>\r\n" +
                "<script language=\"javascript\">getshoppingcarlist(" + markID + ");</script>\r\n";
        return result;
    }

    //订单生成 add by feixiang 2010-10-20
    public String formatOrderResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String siteid = properties.getProperty(properties.getName().concat(".SITEID"));

        String result = "<SCRIPT language=javascript src=\"/_commons/js/shoppingcart.js\"></script>\r\n";
        result += "<form name=\"confirmform\" action=\"\" method=\"post\"><input type=\"hidden\" name=\"markid\" value=\"" + markID + "\"><div id =\"biz_address\"></div>";
        result += "<div id =\"biz_sendway\"></div>";
        result += "<div id =\"biz_payway\"></div>";
        result += "<div id =\"biz_invoiceinfo_list\"></div>";
        result += "<div id =\"biz_product\"></div></form>";
        result += "<script language=\"javascript\">getProductAddress('" + sitename + "','" + "/" + sitename + "/_prog/ordergenerate.jsp'," + markID + ");</script>";//送货地址信息
        result += "<script language=\"javascript\">getSendWay(" + markID + "," + siteid + ");</script>";
        result += "<script language=\"javascript\">getPayWay(" + markID + "," + siteid + ");</script>";
        result += "<script language=\"javascript\">getInvoiceInfo(" + markID + ");</script>";
        result += "<script language=\"javascript\">getProductInfo(" + markID + "," + siteid + ");</script>";
        return result;
    }

    //用户登录表单 add by feixiang 2010-12-10
    public String formatLoginForm(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "<script type=\"text/javascript\" src=\"/_sys_js/check.js\"></script>\r\n" +
                "       <script type=\"text/JavaScript\">\r\n" +
                "        function check(){\r\n" +
                "            var userName = document.loginForm.username.value;\r\n" +
                "            var passWord = document.loginForm.password.value;\r\n" +
                "            if(userName==\"\"){\r\n" +
                "                alert(\"请输入用户名！\");\r\n" +
                "                return false;\r\n" +
                "            }\r\n" +
                "            if(passWord == \"\"){\r\n" +
                "                alert(\"请输入密码！\");\r\n" +
                "                return false;\r\n" +
                "            }\r\n" +
                "            return true;\r\n" +
                "        }\r\n" +
                "    </script>\r\n" +
                "<div id=\"biz_user_login\" style=\"display:none\"></div>\r\n" +
                "<div id=\"biz_user_login_form\">\r\n"+ "    <form name=\"loginForm\" method=\"post\" action=\"/_commons/dologin.jsp\" onsubmit=\"return check();\">\r\n";
        String siteid = properties.getProperty(properties.getName().concat(".SITEID"));
        String loginform = properties.getProperty(properties.getName().concat(".LOGININFO"));
        String textsize = properties.getProperty(properties.getName().concat(".TEXTSIZE"));
        String submits = properties.getProperty(properties.getName().concat(".SUBMITS"));
        String submitsimages = properties.getProperty(properties.getName().concat(".SUBMITISMAGE"));
        String register = properties.getProperty(properties.getName().concat(".REGISGTER"));
        String regimages = properties.getProperty(properties.getName().concat(".REGIMAGE"));
        String findpwd = properties.getProperty(properties.getName().concat(".findpwd"));
        String findpwdimages = properties.getProperty(properties.getName().concat(".FINDPWDIMAGE"));

        //用户名
        String usernamestr = "<input type=\"text\" id=\"username\" name=\"username\" value=\"\" size=\"\">\r\n";
        //处理密码
        String passwd = "<input type=\"password\" id=\"password\" name=\"password\" value=\"\" size=\"\">\r\n";
        //处理文本框大小
        String size_val = "";
        if (textsize != null && !textsize.equals("") && !textsize.equalsIgnoreCase("null")) {
            int size = Integer.parseInt(textsize);
            if (size > 0) {
                size_val = "size=\"" + size + "\"";
                usernamestr = StringUtil.replace(usernamestr,"size=\"\"",size_val);
                passwd = StringUtil.replace(passwd,"size=\"\"",size_val);
                if (loginform != null) loginform = StringUtil.replace(loginform,"size=\"\"",size_val);
            }
        }

        //处理提交按钮
        String submit = "";
        if (submits.equals("submit")) {
            //提交
            submit = "<input type=\"submit\" name=\"submit\" value=\"登录\">\r\n";
        } else if (submits.equals("images")) {
            //图片
            submit = "<input type=\"image\" src=\"/_sys_images/buttons/" + submitsimages + "\">\r\n";
        } else if (submits.equals("links")) {
            submit = "<a href=\"#\" onclick=\"javascript:document.loginForm.submit();\">登录</a>\r\n";
        }
        if (loginform != null) {
            loginform = loginform.substring(0, loginform.length() - 1);
            loginform = StringUtil.replace(loginform, "<" + "%%username%%" + ">", usernamestr);
            loginform = StringUtil.replace(loginform, "<" + "%%passwd%%" + ">", passwd);
            loginform = StringUtil.replace(loginform, "<" + "%%submits%%" + ">", submit);
        }
        result += "<input type=\"hidden\" name=\"siteid\" value=\"" + siteid + "\">\r\n";
        result += "<input type=\"hidden\" name=\"doLogin\" value=\"true\">\r\n";
        result += loginform;
        result += "</form>\r\n";
        result += "<script language=\"javascript\">checklogin();</script>\r\n";
        result += "</div>\r\n";
        return result;
    }

    //用户登录信息显示 add by feixiang 2010-12-10
    public String formatLoginDisplay(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        //String result = "<script language=javascript>checklogin(" + markID + ");</script>";
        //return result;
        String result = "<div id=\"errmsgid\"><%=(ug!=null)?ug.getErrmsg():\"\"%></div>\r\n" +
                "         <div id=\"biz_user_login_form\">\r\n"+
                //(对于coosite模式)
                //"         <form name=\"loginForm\" method=\"post\" action=\"/" + sitename + "/_prog/login.jsp\" onsubmit=\"return check();\">\r\n";
                "         <form name=\"loginForm\" method=\"post\" action=\"" + "/_prog/login.jsp\" onsubmit=\"return check();\">\r\n";

        String siteid = properties.getProperty(properties.getName().concat(".SITEID"));
        String loginform = properties.getProperty(properties.getName().concat(".LOGININFO"));
        String textsize = properties.getProperty(properties.getName().concat(".TEXTSIZE"));
        String submits = properties.getProperty(properties.getName().concat(".SUBMITS"));
        String submitsimages = properties.getProperty(properties.getName().concat(".SUBMITISMAGE"));

        //处理提交按钮
        String submit = "";
        if (submits.equals("submit")) {
            //提交
            submit = "<input type=\"submit\" name=\"submit\" value=\"登录\">\r\n";
        } else if (submits.equals("images")) {
            //图片
            submit = "<input type=\"image\" src=\"/_sys_images/buttons/" + submitsimages + "\">\r\n";
        } else if (submits.equals("links")) {
            submit = "<a href=\"#\" onclick=\"javascript:document.loginForm.submit();\">登录</a>\r\n";
        }
        if (loginform != null) {
            loginform = loginform.substring(0, loginform.length() - 1);
            loginform = StringUtil.replace(loginform, "<" + "%%submit%%" + ">", submit);
        }
        result += "<input type=\"hidden\" name=\"siteid\" value=\"" + siteid + "\">\r\n";
        result += "<input type=\"hidden\" name=\"doLogin\" value=\"true\">\r\n";
        result += loginform;
        result += "</form>\r\n";
        result += "</div>\r\n";
        return result;
    }

    //处理订单查询 add by feixiang 2010-12-17
    public String formatOrderSearchResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "<div id=\"biz_ordersearch_list\"></div>\r\n";
        result += "<SCRIPT language=javascript src=\"/_commons/js/shoppingcart.js\"></SCRIPT>\r\n" +
                "<script language=\"javascript\">getordersearchresult(" + markID + ",0);</script>\r\n";
        return result;
    }

    //处理订单详细 add by feixiang 2010-12-17
    public String formatOrderDeatilResult(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "";
        result += "<SCRIPT language=javascript src=\"/_commons/js/shoppingcart.js\"></SCRIPT>\r\n" +
                "<script language=\"javascript\">getorderdetailresult(" + markID + ");</script>\r\n";
        return result;
    }

    public String formatLoginFormForProgram(XMLProperties properties, int siteID, int columnID, String username, String sitename, String fragPath, boolean isPreview, String appPath, int markID) throws TagException {
        String result = "<script type=\"text/JavaScript\">\n" +
                "        function checkPLogin(){\n" +
                "            var userName = document.ploginForm.username.value;\n" +
                "            var passWord = document.ploginForm.password.value;\n" +
                "            if(userName==\"\"){\n" +
                "                alert(\"请输入用户名！\");\n" +
                "                return false;\n" +
                "            }\n" +
                "            if(passWord == \"\"){\n" +
                "                alert(\"请输入密码！\");\n" +
                "                return false;\n" +
                "            }\n" +
                "            return true;\n" +
                "        }\n" +
                "    </script>" +
                "<div id=\"biz_user_login\" style=\"display:none\"></div>\n" +
                "<div id=\"biz_user_login_form\"><form name=\"ploginForm\" method=\"post\" action=\"/_commons/dologin.jsp\" onsubmit=\"return checkPLogin();\">";
        String siteid = properties.getProperty(properties.getName().concat(".SITEID"));
        String loginform = properties.getProperty(properties.getName().concat(".LOGININFO"));
        String textsize = properties.getProperty(properties.getName().concat(".TEXTSIZE"));
        String submits = properties.getProperty(properties.getName().concat(".SUBMITS"));
        String submitsimages = properties.getProperty(properties.getName().concat(".SUBMITISMAGE"));
        //用户名
        String usernamestr = "<input type=\"text\" id=\"username\" name=\"username\" value=\"\" ";
        //处理密码
        String passwd = "<input type=\"password\" id=\"password\" name=\"password\" value=\"\" ";
        //处理文本框大小
        if (textsize != null && !textsize.equals("") && !textsize.equalsIgnoreCase("null")) {
            int size = Integer.parseInt(textsize);
            if (size > 0) {
                usernamestr += "size=\"" + size + "\"";
                passwd += "size=\"" + size + "\"";
            }

        }

        usernamestr += ">";
        passwd += ">";
        //处理提交按钮
        String submit = "";
        if (submits.equals("submit")) {
            //提交
            submit = "<input type=\"submit\" name=\"submit\" value=\"登录\">";
        } else if (submits.equals("images")) {
            //图片
            submit = "<input type=\"image\" src=\"/_sys_images/buttons/" + submitsimages + "\">";
        } else if (submits.equals("links")) {
            submit = "<a href=\"#\" onclick=\"javascript:document.ploginForm.submit();\">登录</a>";
        }
        if (loginform != null) {
            loginform = loginform.substring(0, loginform.length() - 1);
            loginform = StringUtil.replace(loginform, "<" + "%%username%%" + ">", usernamestr);
            loginform = StringUtil.replace(loginform, "<" + "%%passwd%%" + ">", passwd);
            loginform = StringUtil.replace(loginform, "<" + "%%submits%%" + ">", submit);
        }
        result += "<input type=\"hidden\" name=\"siteid\" value=\"" + siteid + "\">";
        result += "<input type=\"hidden\" name=\"doLogin\" value=\"true\">";
        result += loginform;
        result += "</form></div>";
        return result;
    }
}