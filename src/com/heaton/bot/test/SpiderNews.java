package com.heaton.bot.test;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
import com.bizwink.cms.articleListmanager.IArticleListManager;
import com.bizwink.cms.articleListmanager.articleListPeer;
import com.bizwink.cms.extendAttr.ExtendAttrException;
import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishException;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.util.ImageUtil;
import com.heaton.bot.*;
import magick.MagickException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.mail.internet.NewsAddress;
import java.io.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Pattern;

public class SpiderNews implements Runnable{
    private Map urls;
    private Thread runner;
    PoolServer cpool;

    public SpiderNews(PoolServer cpool) {
        this.cpool = cpool;
        urls = new HashMap();
        urls.put("1","http://www.sinopecnews.com.cn/wz/node_10995.htm");
        urls.put("2","http://www.sinopecnews.com.cn/wz/node_10989.htm");
        urls.put("3","http://www.sinopecnews.com.cn/wz/node_10981.htm");

        runner = new Thread(this);
        runner.start();
    }

    public void run() {
        //public static void main(String args[]) {
        String url = null;
        int i = 0;
        int posi = 0;
        String src_url = null;
        String result = null;
        HTTPSocket http = new HTTPSocket();
        ImageUtil imageUtil = null;
        Document document = null;
        SourceColumnMap sourceColumnMap = null;
        List sources = null;
        List sites_location = null;
        Element root = null;
        int siteid = 0;
        String cms_root = null;
        String sitename = null;
        String dirname = null;
        String pic = null;
        String smallpic = null;
        String specialpic = null;

        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        IFtpSetManager ftpMgr = FtpSetting.getInstance();
        IPublishManager  publishMgr = PublishPeer.getInstance();
        IExtendAttrManager articleMgr = ExtendAttrPeer.getInstance();
        IColumnManager columnMgr = ColumnPeer.getInstance();
        IArticleListManager articleListManager = articleListPeer.getInstance();

        while (true) {
            try {
                InputStream in  = SpiderNews.class.getResourceAsStream("sites.xml");
                SAXReader reader = new SAXReader();
                document = reader.read(in);
                root = document.getRootElement();
                sources = new ArrayList();
                for ( Iterator ii = root.elementIterator("info"); ii.hasNext(); ) {
                    Element info = (Element) ii.next();
                    Element site_name = (Element)info.elementIterator("site").next();
                    sitename = site_name.getStringValue();
                    Element cmsroot = (Element)info.elementIterator("cmsroot").next();
                    cms_root = cmsroot.getStringValue();
                    siteid = siteMgr.getSiteID(sitename);
                    sites_location = ftpMgr.getFtpInfoList(siteid);
                    Element columns = (Element)info.elementIterator("columns").next();
                    for ( Iterator jj = columns.elementIterator("column"); jj.hasNext(); ) {
                        Element column = (Element) jj.next();
                        Element sourceurl = (Element)column.elementIterator("sourceurl").next();
                        Element columnid = (Element)column.elementIterator("columnid").next();
                        Element columnname = (Element)column.elementIterator("columnname").next();
                        sourceColumnMap = new SourceColumnMap();
                        sourceColumnMap.setSiteid(siteid);
                        sourceColumnMap.setColumnid(Integer.parseInt(columnid.getStringValue()));
                        sourceColumnMap.setColumnname(columnname.getStringValue());
                        sourceColumnMap.setSourceurl(sourceurl.getStringValue());
                        sources.add(sourceColumnMap);
                    }
                }
            } catch (DocumentException exp) {
                exp.printStackTrace();
            } catch (SiteInfoException siteexp) {
                siteexp.printStackTrace();
            }

            for (int kk=0; kk<sources.size(); kk++) {
                sourceColumnMap = new SourceColumnMap();
                sourceColumnMap = (SourceColumnMap)sources.get(kk);
                int columnid = sourceColumnMap.getColumnid();
                siteid = sourceColumnMap.getSiteid();
                System.out.println("kk=" + kk + "==siteid=" + siteid + "==columnid===" + columnid + "==" + sourceColumnMap.getSourceurl());

                com.bizwink.cms.news.Column column = null;
                try {
                    column =columnMgr.getColumn(columnid);
                    dirname = column.getDirName();
                } catch (com.bizwink.cms.news.ColumnException exp) {
                    exp.printStackTrace();
                }
                int loopnum = 0;
                boolean getInfoSuccessFlag = false;
                while (loopnum<10 && !getInfoSuccessFlag) {
                    try{
                        http.setTimeout(30000);
                        http.send(sourceColumnMap.getSourceurl(),null);
                        result = http.getBody();
                        getInfoSuccessFlag = true;
                    }
                    catch (IOException ioexp) {
                        loopnum = loopnum + 1;
                    }
                }

                String nextpage = null;
                List url_arr = new ArrayList();
                do {
                    //�ҳ���ҳ����һҳ���ӵ�ַ
                    Pattern p = Pattern.compile("<a[^<>]*>��һҳ</a>", Pattern.CASE_INSENSITIVE);
                    java.util.regex.Matcher matcher = p.matcher(result);
                    if (matcher.find()) {
                        HTMLParser parse_href = new HTMLParser();
                        nextpage = result.substring(matcher.start(), matcher.end());
                        parse_href.source = new StringBuffer(nextpage);
                        while ( !parse_href.eof() ) {
                            char ch = parse_href.get();
                            if ( ch==0 ) {
                                HTMLTag tag = parse_href.getTag();
                                Attribute link = tag.get("HREF");
                                if ( link==null ) continue;
                                nextpage = "http://www.sinopecnews.com.cn/wz/" + link.getValue();
                            }
                        }
                    } else {
                        nextpage = null;
                    }

                    //��ȡ��ҳ������ҳ���ӵ�ַ
                    HTMLParser parse_href = new HTMLParser();
                    parse_href.source = new StringBuffer(result);
                    while ( !parse_href.eof() ) {
                        char ch = parse_href.get();
                        if ( ch==0 ) {
                            HTMLTag tag = parse_href.getTag();
                            Attribute link = tag.get("HREF");
                            if ( link==null ) continue;
                            url = link.getValue();
                            boolean exist_url = false;
                            String t_url = "";
                            for(int j=0; j<url_arr.size(); j++) {
                                t_url = (String)url_arr.get(j);
                                if (t_url.equalsIgnoreCase(url)) {
                                    exist_url = true;
                                    break;
                                }
                            }

                            if (exist_url == false && url.startsWith("content/")) {
                                url_arr.add(url);
                            }
                        }
                    }

                    //ȡ����һҳ������
                    System.out.println("nextpage=" + nextpage);
                    if (nextpage != null) {
                        loopnum = 0;
                        getInfoSuccessFlag = false;
                        while (loopnum<10 && !getInfoSuccessFlag) {
                            try{
                                http.setTimeout(30000);
                                http.send(nextpage, null);
                                result = http.getBody();
                                getInfoSuccessFlag = true;
                            } catch (IOException ioexp) {
                                loopnum = loopnum + 1;
                            }
                        }
                    }

                    //��ֻȡһҳ����ʱ����nextpage����Ϊ�գ������Ҫȡ����Ŀ������ҳ��ע�͵����м���
                    nextpage = null;
                } while (nextpage != null);

                //��ȡ��ҳ��ÿƪ�������µ�����
                /*System.out.println("url_arr.size()=" + url_arr.size());
                PrintWriter pw = null;
                try{
                    pw = new PrintWriter(new FileOutputStream("c:\\news\\tt.txt"),true);
                    for(int j=0; j<url_arr.size(); j++) {
                        url = (String)url_arr.get(j);
                        pw.write(url + "\r\n");
                    }
                    pw.close();
                } catch(IOException exp) {
                    exp.printStackTrace();
                }*/

                for(int j=0; j<url_arr.size(); j++) {
                    url = (String)url_arr.get(j);
                    String filename = "";

                    if (url.startsWith("content/")) {
                        i = i + 1;
                        posi = url.lastIndexOf("content/") + "content/".length();
                        if (posi>-1)
                            filename = url.substring(posi);
                        else {
                            posi = url.lastIndexOf("/");
                            filename = url.substring(posi+1);
                        }
                        filename = filename.replace("/","_");
                        String year_s = filename.substring(0,4);
                        String month_s = filename.substring(5,7);
                        String day_s = filename.substring(8,10);
                        int year = Integer.parseInt(year_s);
                        int month = Integer.parseInt(month_s);
                        int day = Integer.parseInt(day_s);

                        //��ȡ�ļ�URL�е��ļ�ID
                        String sarticleid = "";
                        posi = filename.lastIndexOf("_");
                        if (posi>-1) sarticleid = filename.substring(posi+1);
                        posi = sarticleid.lastIndexOf(".");
                        if (posi>-1) sarticleid = sarticleid.substring(0,posi);

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(System.currentTimeMillis());
                        if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) ==(month-1) && cal.get(Calendar.DAY_OF_MONTH)==day) {
                            //�ӷ������˻�ȡ���ݣ�����������ʧ�ܷ���ӷ������˻�ȡ���ݣ��������ִ��
                            url = "http://www.sinopecnews.com.cn/wz/" + url;
                            result = null;
                            loopnum = 0;
                            getInfoSuccessFlag = false;
                            while (loopnum<10 && !getInfoSuccessFlag) {
                                try {
                                    http.setTimeout(30000);
                                    http.send(url, null);
                                    result = http.getBody();
                                    getInfoSuccessFlag = true;
                                } catch (IOException ioexp) {
                                    loopnum = loopnum + 1;
                                }
                                //System.out.println("loopnum=" + loopnum + "===getInfoSuccessFlag===" + getInfoSuccessFlag);
                            }

                            if (result != null) {
                                Pattern p = Pattern.compile("<title>[^<>]*</title>", Pattern.CASE_INSENSITIVE);
                                java.util.regex.Matcher matcher = p.matcher(result);
                                String maintitle = null;
                                if (matcher.find()) {
                                    maintitle = result.substring(matcher.start(), matcher.end());
                                    posi = maintitle.lastIndexOf("-");
                                    maintitle = maintitle.substring(posi+1);
                                    maintitle = StringUtil.replace(maintitle,"</title>","");
                                }
                                posi = result.lastIndexOf("<!--enpcontent-->");
                                if (posi > -1) result = result.substring(posi + "<!--enpcontent-->".length());
                                posi = result.indexOf("<!--/enpcontent-->");
                                if (posi > -1) result = result.substring(0,posi);

                                //����������ͼƬ
                                String tbuf = result;
                                HTMLParser parse_src = new HTMLParser();
                                parse_src.source = new StringBuffer(tbuf);
                                while ( !parse_src.eof() ) {
                                    char ch = parse_src.get();
                                    if (ch == 0) {
                                        HTMLTag tag = parse_src.getTag();
                                        Attribute link = tag.get("src");
                                        if ( link==null ) continue;
                                        url = link.getValue();
                                        posi = url.lastIndexOf("/../");
                                        if (posi > -1) src_url = "http://www.sinopecnews.com.cn/wz/content/" + url.substring(posi + "/../".length());
                                        imageUtil = new ImageUtil();
                                        String cms_image_path = null;
                                        if (cms_root.endsWith(java.io.File.separator))
                                            cms_image_path = cms_root + "sites" + java.io.File.separator + StringUtil.replace(sitename,".","_") + StringUtil.replace(dirname,"/",java.io.File.separator) + "images/";
                                        else
                                            cms_image_path = cms_root + java.io.File.separator + "sites" + java.io.File.separator + StringUtil.replace(sitename,".","_") + StringUtil.replace(dirname,"/",java.io.File.separator) + "images/";

                                        //��CMS����ͼƬ�ļ�
                                        String imageurl = imageUtil.savepicFromWebsite(src_url,cms_image_path);

                                        //����վĿ��վ�㷢��ͼƬ�ļ� int siteid, String fileDir, int big5flag
                                        int pub_retcode = 0;
                                        try {
                                            pub_retcode = publishMgr.publish("edarongadmin",imageurl,siteid,dirname,0);
                                            if (pub_retcode == 0) pic = imageurl.substring(imageurl.lastIndexOf(java.io.File.separator) + 1);
                                        } catch (PublishException exp) {
                                            exp.printStackTrace();
                                        }

                                        //�������ͼ�ļ�
                                        String thumbnailImage = null;
                                        try {
                                            thumbnailImage = imageUtil.createThumbnailBy_jmagick(imageurl,"180X160");
                                        } catch (MagickException exp) {
                                            exp.printStackTrace();
                                        }

                                        //��������ͼ�ļ�
                                        if (thumbnailImage != null) {
                                            int pub_thumbnailImage_retcode = 0;
                                            try {
                                                pub_thumbnailImage_retcode = publishMgr.publish("edarongadmin",thumbnailImage,siteid,dirname,0);
                                                if (pub_thumbnailImage_retcode == 0) {
                                                    smallpic = thumbnailImage.substring(thumbnailImage.lastIndexOf(java.io.File.separator) + 1);
                                                    specialpic = smallpic;
                                                }
                                            } catch (PublishException exp) {
                                                exp.printStackTrace();
                                            }
                                        }

                                        result = StringUtil.replace(result,url,src_url);
                                        result = StringUtil.replace(result,"??????","");
                                        result = StringUtil.replace(result,"?????","");
                                        result = StringUtil.replace(result,"????","");
                                        result = StringUtil.replace(result,"???","");
                                        result = StringUtil.replace(result,"??","");
                                        result = StringUtil.replace(result,"?","");
                                    }
                                }

                                //�жϱ��ɼ�����������ݿ����Ƿ����
                                boolean exist_article_flag = articleListManager.existTheArticle(siteid,1,sarticleid);

                                //������Ϣ����ݿ�
                                if (maintitle!=null && column!=null && exist_article_flag ==false) {
                                    System.out.println("maintitle=" + maintitle + "===" + i);
                                    cal.set(Calendar.YEAR,year);
                                    cal.set(Calendar.MONTH,month-1);
                                    cal.set(Calendar.DAY_OF_MONTH,day);
                                    Article article = new Article();
                                    article.setMainTitle(maintitle);
                                    article.setSource("Sinopec news website");
                                    article.setColumnID(columnid);
                                    article.setSiteID(siteid);
                                    article.setCreateDate(new Timestamp(System.currentTimeMillis()));
                                    article.setLastUpdated(new Timestamp(System.currentTimeMillis()));
                                    article.setPublishTime(new Timestamp(cal.getTimeInMillis()));
                                    article.setEditor("edarongadmin");
                                    article.setDirName(column.getDirName());
                                    article.setMultimediatype(0);
                                    article.setPubFlag(1);
                                    article.setEditor("edarongadmin");
                                    article.setAuditFlag(0);
                                    article.setStatus(1);
                                    article.setNullContent(0);
                                    article.setSortID(i);
                                    article.setSarticleid(sarticleid);
                                    article.setFromsiteid(1);
                                    article.setContent(result);
                                    if (smallpic != null) {
                                        article.setProductPic(smallpic);
                                        article.setArticlepic(smallpic);
                                    } else {
                                        article.setProductPic("edarong.jpg");
                                        article.setArticlepic("edarong.jpg");
                                    }
                                    if (pic != null) {
                                        article.setProductBigPic(pic);
                                    }

                                    try {
                                        articleMgr.create(null,null,article,null,null,null,null,null);
                                    } catch (ExtendAttrException exp) {
                                        exp.printStackTrace();
                                    }
                                }
                            }
                        } //ʱ���ж��Ƿ��ǽ���
                    }
                }
            }

            //System.out.println("�ȴ�6��Сʱ���ٴ�������Ϣ�ɼ����򣡣���");
            try {
                Thread.sleep(1000*60*60*6);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}