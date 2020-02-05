package com.bizwink.cms.news;

import com.bizwink.cms.util.XmlUtil;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.sitesetting.IFtpSetManager;
import com.bizwink.cms.sitesetting.FtpSetting;
import com.bizwink.cms.sitesetting.SiteInfoException;
import com.bizwink.cms.sitesetting.FtpInfo;
import com.bizwink.cms.server.FileProps;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;
import org.jdom.input.SAXBuilder;

public class RssMaker {
    String propname = "main";
    String rootPath;
    String companyName;
    String description;
    String url;
    String language;
    String livetime;
    String domain;
    String copyright;
    String imageTitle;
    String imageLink;
    String imageUrl;

    public RssMaker() {
        FileProps props = new FileProps("com/bizwink/cms/server/config.properties");

        companyName = props.getProperty(propname + ".rss.companyname");
        description = props.getProperty(propname + ".rss.description");
        url = props.getProperty(propname + ".rss.url");
        language = props.getProperty(propname + ".rss.language");
        livetime = props.getProperty(propname + ".rss.livetime");
        domain = props.getProperty(propname + ".rss.domain");
        copyright = props.getProperty(propname + ".rss.copyright");
        imageTitle = props.getProperty(propname + ".rss.image.title");
        imageLink = props.getProperty(propname + ".rss.image.link");
        imageUrl = props.getProperty(propname + ".rss.image.url");

        companyName = StringUtil.gb2iso4View(companyName);
        description = StringUtil.gb2iso4View(description);
        domain = StringUtil.gb2iso4View(domain);
        copyright = StringUtil.gb2iso4View(copyright);
        imageTitle = StringUtil.gb2iso4View(imageTitle);
        imageLink = StringUtil.gb2iso4View(imageLink);
        imageUrl = StringUtil.gb2iso4View(imageUrl);
        System.out.println("companyName=" + companyName);
    }

    public RssMaker(String rootPath) {
        this.rootPath = rootPath;
    }

    public boolean createRss(int siteid, int columnID, Column column, String appPath, String username, String sitename) {
        boolean success = false;
        FileOutputStream fo = null;
        SAXBuilder builder = new SAXBuilder();
        String dirname = column.getDirName();
        String _sitename = sitename.replaceAll("\\.", "_");

        String baseDir = appPath + "sites" + File.separator + _sitename;
        String rssInfoDataPath = baseDir + dirname + "rss.xml";
        File file = new File(rssInfoDataPath);
        if (!file.exists()) {
            XmlUtil.createXMLFile(rssInfoDataPath, "rss");
        } else {
            file.delete();
            XmlUtil.createXMLFile(rssInfoDataPath, "rss");
        }

        try {
            Document doc = builder.build(file);
            List nodeList = doc.getRootElement().getChildren();

            Element channelE = new Element("channel");
            Element itemE = new Element("title");
            itemE.setText(companyName);
            channelE.addContent(itemE);

            itemE = createImageNode();
            channelE.addContent(itemE);

            itemE = new Element("description");
            itemE.setText(description);
            channelE.addContent(itemE);

            itemE = new Element("link");
            itemE.setText(url);
            channelE.addContent(itemE);

            itemE = new Element("language");
            itemE.setText(language);
            channelE.addContent(itemE);

            itemE = new Element("generator");
            itemE.setText(sitename);
            channelE.addContent(itemE);

            itemE = new Element("ttl");
            itemE.setText(livetime);
            channelE.addContent(itemE);

            itemE = new Element("copyright");
            itemE.setText(copyright);
            channelE.addContent(itemE);

            itemE = new Element("pubDate");
            itemE.setText(String.valueOf(new Timestamp(System.currentTimeMillis())).substring(0, 16));
            channelE.addContent(itemE);

            //获得当前栏目下所有需要生成rss的文章，并添加到xml文件
            List articlelist = new ArrayList();
            IArticleManager articleMgr = ArticlePeer.getInstance();
            IColumnManager columnMgr = ColumnPeer.getInstance();
            articlelist = articleMgr.getPubRssArticles(columnID, column);
            for (int i = 0; i < articlelist.size(); i++) {
                Article article = (Article) articlelist.get(i);

                if(article.getNullContent() == 0){
                    String extName = columnMgr.getExtName(article.getColumnID());
                    itemE = createItemNode(article, extName, 0, sitename);
                    channelE.addContent(itemE);
                } else {
                    itemE = createItemNode(article, "", 1, sitename);
                    channelE.addContent(itemE);
                }
            }

            nodeList.add(channelE);

            XMLOutputter out = new XMLOutputter();
            fo = new FileOutputStream(rssInfoDataPath);
            out.output(doc, fo);

            rssInfoDataPath = StringUtil.replace(rssInfoDataPath, "/", File.separator);
            rssInfoDataPath = StringUtil.replace(rssInfoDataPath, "\\", File.separator);
            dirname = StringUtil.replace(dirname, "/", File.separator);
            dirname = StringUtil.replace(dirname, "\\", File.separator);
            IPublishManager pubMgr = PublishPeer.getInstance();
            pubMgr.publish(username, rssInfoDataPath, siteid, dirname, 0);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fo.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return success;
    }

    private Element createImageNode() {
        Element imageE = new Element("image");

        Element itemE = new Element("title");
        itemE.setText(imageTitle);
        imageE.addContent(itemE);

        itemE = new Element("link");
        itemE.setText(imageLink);
        imageE.addContent(itemE);

        itemE = new Element("url");
        itemE.setText(imageUrl);
        imageE.addContent(itemE);

        return imageE;
    }

    //flag=0 文章；flag=1 文件
    private Element createItemNode(Article article, String extName, int flag, String sitename) {
        String createdate_path = article.getCreateDate().toString().substring(0, 10);
        createdate_path = createdate_path.replaceAll("-", "") + "/";

        Element imageE = new Element("item");

        Element itemE = new Element("title");
        itemE.setText(StringUtil.gb2iso4View(article.getMainTitle()));
        imageE.addContent(itemE);

        itemE = new Element("link");
        String url = "";

        String dirname = "";
        try {
            IColumnManager colMgr = ColumnPeer.getInstance();
            Column acolumn = colMgr.getColumn(article.getColumnID());
            dirname = acolumn.getDirName();
        } catch (ColumnException e) {
            e.printStackTrace();
        }

        if(flag == 0) {
            url = "http://" + sitename.replaceAll("_", ".") + dirname + createdate_path + article.getID() + "." + extName;
        } else {
            url = "http://" + sitename.replaceAll("_", ".") + dirname + createdate_path + "download/" + article.getFileName();
        }
        itemE.setText(url);
        imageE.addContent(itemE);

        itemE = new Element("author");
        itemE.setText(article.getAuthor() == null ? domain : StringUtil.gb2iso4View(article.getAuthor()));
        imageE.addContent(itemE);

        itemE = new Element("guid");
        itemE.setText(url);
        imageE.addContent(itemE);

        itemE = new Element("pubDate");
        itemE.setText(String.valueOf(article.getPublishTime()).substring(0, 16));
        imageE.addContent(itemE);

        itemE = new Element("description");
        itemE.setText(article.getSummary() == null ? StringUtil.gb2iso4View(article.getMainTitle()) : StringUtil.gb2iso4View(article.getSummary()));
        imageE.addContent(itemE);

        return imageE;
    }
}