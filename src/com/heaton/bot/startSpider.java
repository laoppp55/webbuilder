package com.heaton.bot;

import java.sql.*;
import java.io.*;
import java.net.*;
import java.util.List;

public class startSpider extends Thread implements ISpiderReportable {
    /**
     * The underlying spider object.
     */
    Spider _spider = null;

    static String rootpath;
    static String logpath;
    static String dbdriver;
    static String dburl;
    static String dbuser;
    static String dbpasswd;

    IWorkloadStorable wl = null;

    proxySetup proxy = null;

    public startSpider(String rootpath) {
        this.rootpath = rootpath;
    }

    public startSpider(String rootpath,String logpath,String dbdriver,String dburl,String dbuser,String dbpasswd) {
        this.rootpath = rootpath;
        this.logpath = logpath;
        this.dbdriver = dbdriver;
        this.dburl = dburl;
        this.dbuser = dbuser;
        this.dbpasswd = dbpasswd;
    }

    public static void main(String[] args) {
        dbdriver = Server.getParam("dbdriver");
        dburl = Server.getParam("dburl");
        dbuser = Server.getParam("dbuser");
        dbpasswd = Server.getParam("dbpass");
        rootpath = Server.getParam("saverootpath");
        logpath = Server.getParam("logpath");

        System.out.println("dbdriver=" + dbdriver);
        System.out.println("dburl=" + dburl);
        System.out.println("dbuser=" + dbuser);
        System.out.println("dbpasswd=" + dbpasswd);
        System.out.println("rootpath=" + rootpath);
        System.out.println("logpath=" + logpath);

        File file = new File(logpath);
        file.delete();
        Log.setLevel(Log.LOG_LEVEL_NORMAL);
        Log.setFile(true);
        Log.setConsole(false);
        Log.setPath(logpath);

        //启动这个Spider Server的主线程
        startSpider ss = new startSpider(rootpath);
        Thread runner = new Thread(ss);
        runner.start();
    }

    /**
     * The main loop of the spider. This can be called
     * directly, or the start method can be called to
     * run as a background thread. This method will not
     * return until there is no work remaining for the
     * spider.
     */
    public void run() {
        boolean startflag = false;

        while (true) {
            if (!startflag) {
                try {
                    wl = new SpiderSQLWorkload(dbdriver,dburl,dbuser,dbpasswd);
                    //wl.clearIndexURL();
                    //proxy = wl.getProxySetting();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                _spider = new Spider(this,
                        rootpath,
                        logpath,
                        new HTTPSocket(),
                        3,
                        wl,
                        proxy);
                _spider.setMaxBody(200000);
                _spider.start();
                startflag = true;
            }

            //停止10秒钟后，寻找新的作业
            if (_spider != null) {
                System.out.println("Spider进程还没有结束，继续等待！！！！！！！");
                try {
                    Thread.sleep(1000 * 60);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("再次启动信息抓取线程，从定义的网站群中获取信息");
                try {
                    Thread.sleep(1000 * 60);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startflag = false;
            }
        }
    }

    /**
     * This method is called once the spider
     * has no more work to do.
     */
    public void spiderComplete() {
        _spider = null;
    }

    /**
     * Called to add a workload to the workload manager.
     * This method will release a thread that was waiting
     * for a workload. This method will do nothing if the
     * spider has been halted.
     */
    public boolean batchAddWorkload(java.util.List urls) {
        return true;
    }

    /**
     * Not used. This must be implemented because
     * of the interface. Called when a page completes.
     *
     * @param page  The page that just completed.
     * @param error True if the completion of this page
     *              resulted in an error.
     */
    public void completePage(HTTP page,int urlid, boolean error, int siteid, int urltype) {

    }

    /**
     * Called by the spider when a page has been
     * loaded, and should be processed. For the
     * example, this method will save this file
     * to disk.
     *
     * @param page The HTTP object that corrispondeds to the
     *             page just visited.
     */
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
    public void processPage(HTTP page,List columns, List matchurls,List tags,int siteid) {
        //AttributeList list = page.getServerHeaders();
        //processContent(page,classid,matchurl,starttag,endtag);

        String[] parseResult;
        ParseHtml ph = new ParseHtml();
        int relation = 2;   //默认或关系
        String titleKey = "";
        String contentKey = "";

        String[] keywords = wl.getKeywords(siteid);
        if (keywords != null) {
            relation = Integer.parseInt(keywords[0]);
            titleKey = keywords[1];
            contentKey = keywords[2];
        }

        System.out.println("domatchurl(page.getURL(),matchurls)=" + domatchurl(page.getURL(),matchurls));

        if (domatchurl(page.getURL(),matchurls)) {
            System.out.println("pageurl=" + page.getURL());
            try {
                String buf = page.getBody();
                if (buf != null && buf !="" && tags.size()> 0) {
                    if (domatchtag(buf,tags)) {
                        //System.out.println("begin process content!!!!!!!!!!");
                        parseResult = ph.extractText(buf, titleKey, contentKey, relation, tags,page.getURL(), siteid,columns);
                        //System.out.println("end process content!!!!!!!!!!");
                        if (parseResult != null) saveContent(parseResult, page.getURL(),columns);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean domatchurl(String url,List ls) {
        boolean retcode = false;

        for(int i=0; i<ls.size(); i++) {
            String buf = (String)ls.get(i);
            if (url.indexOf(buf)>-1) {
                retcode = true;
                break;
            }
        }

        return retcode;
    }

    private boolean domatchtag(String body,List tags) {
        boolean retcode = false;

        for(int i=0; i<tags.size(); i++) {
            StartEndTag tag = (StartEndTag)tags.get(i);
            String starttag = tag.getStarttag();
            String endtag = tag.getEndtag();
            if (body.indexOf(starttag)>-1 && body.indexOf(endtag)>-1 ) {
                retcode = true;
                break;
            }
        }

        return retcode;
    }

    /**
     * As the files of the website are located,
     * this method is called to save them to disk.
     *
     * @param file The HTTP object corrisponding to the page
     *             just visited.
     */
    protected void processContent(HTTP file, List columns, List matchurls, List tags) {
        try {
            if (rootpath.length() > 0) {

                String buf = file.getBody();

                //去掉<html>前面的代码和</html>后面的代码
                int posi = buf.toLowerCase().indexOf("<html>");
                if (posi > -1) {
                    buf = buf.substring(posi);
                    posi = buf.toLowerCase().lastIndexOf("</html>");
                    if (posi > -1) buf = buf.substring(0, posi + 7);
                }

                String sbuf = buf;
                String title=null;
                int index_s_tag=-1, index_e_tag = -1;
                if (tags.size()>0) {
                    for(int i=0; i<tags.size(); i++)  {
                        StartEndTag tag = new StartEndTag();
                        tag = (StartEndTag)tags.get(i);
                        String starttag = tag.getStarttag();
                        String endtag = tag.getEndtag();

                        title = getTitle(buf);
                        index_s_tag = buf.indexOf(starttag);
                        if (index_s_tag != -1) {
                            buf = buf.substring(index_s_tag + starttag.length());
                            if (endtag != null && !endtag.equals(""))
                                index_e_tag = buf.indexOf(endtag);
                            if (index_e_tag != -1) {
                                buf = buf.substring(0, index_e_tag);
                            }
                            break;
                        }
                    }

                    //去掉无用的代码
                    htmlFilter filter = new htmlFilter(buf);
                    buf = filter.resultByFilter("<script", "</script>");
                    filter.setContent(buf);
                    buf = filter.resultByFilter("<style", "</style>");
                    filter.setContent(buf);
                    buf = filter.resultByFilter("<iframe", "</iframe>");
                    filter.setContent(buf);
                    buf = filter.resultByFilter("<head", "</head>");
                    filter.setContent(buf);
                    buf = filter.resultByFilter("<marquee", "</marquee>");

                    String result = buf;

                    //将分离出来的内容和标题写入文件
                    URL url = new URL(file.getURL());
                    String targetPath = url.toString();
                    if (domatchurl(targetPath,matchurls)) {
                        String source_file;
                        targetPath = URLUtility.convertFilename(rootpath, targetPath);
                        posi = targetPath.lastIndexOf(".");
                        if (posi != -1 && index_s_tag != -1) {
                            targetPath = targetPath.substring(0, posi) + ".html";
                            source_file = targetPath.substring(0, posi) + "_s.html";
                            FileOutputStream fso = new FileOutputStream(new File(targetPath));
                            fso.write(sbuf.getBytes());
                            fso.close();

                            fso = new FileOutputStream(new File(source_file));
                            fso.write(result.getBytes());
                            fso.close();

                            //保存到数据库中
                            SaveContent sc = new SaveContent();
                            sc.save(columns,title, result, new Timestamp(System.currentTimeMillis()), url.toString(), 1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.logException("Can't save output file: ", e);
        }
    }

    private void saveContent(String[] articleContent, String targetPath, List columns) {
        String url = targetPath;
        targetPath = URLUtility.convertFilename(rootpath, targetPath);

        int posi = 0;
        String source_file;

        try {
            targetPath = targetPath.substring(0, posi) + ".html";
            source_file = targetPath.substring(0, posi) + "_s.html";
            FileOutputStream fso = new FileOutputStream(new File(targetPath));
            fso.write(articleContent[1].getBytes());
            fso.close();

            fso = new FileOutputStream(new File(source_file));
            fso.write(articleContent[1].getBytes());
            fso.close();

            //保存到数据库中
            SaveContent sc = new SaveContent();
            sc.save(columns, articleContent[0], articleContent[1], new Timestamp(System.currentTimeMillis()), url, 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private String getTitle(String content) {
        String title = null;
        String tbuf = content.toLowerCase();
        int sposi, eposi;

        sposi = tbuf.indexOf("<title>");
        eposi = tbuf.indexOf("</title>");
        if (sposi > -1 && eposi > -1) {
            title = content.substring(sposi + "<title>".length(), eposi);
        }

        try {
            sposi = -1;
            sposi = title.lastIndexOf("-");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (sposi > -1) title = title.substring(0, sposi);

        return title;
    }

    /**
     * This method is called to determine if
     * query strings should be stripped.
     *
     * @return Returns true if query strings(the part of
     *         the URL after the ?) should be stripped.
     */
    public boolean getRemoveQuery() {
        return true;
    }

    /**
     * This method is called by the spider when an
     * internal link is found.
     *
     * @param url The URL of the link that was found. This
     *            link is passed in fully resolved.
     * @return True if the spider should add this link to
     *         its visitation list.
     */
    public boolean foundInternalLink(String url, int siteid) {
        return true;
    }

    /**
     * This method is called by the spider when an
     * external link is found. An external link is
     * one that points to a different host.
     *
     * @param url The URL of the link that was found. This
     *            link is passed in fully resolved.
     * @return True if the spider should add this link to
     *         its visitation list.
     */
    public boolean foundExternalLink(String url, int siteid) {
        return false;
    }

    /**
     * This method is called by the spider when an
     * other type link is found. Links such as email
     * addresses are sent to this method.
     *
     * @param url The URL of the link that was found. This
     *            link is passed in fully resolved.
     * @return True if the spider should add this link to
     *         its visitation list.
     */
    public boolean foundOtherLink(String url, int siteid) {
        return false;
    }
}