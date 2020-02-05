package com.heaton.bot;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 * The SpiderWorker class performs the actual work of
 * spidering pages.  It is implemented as a thread
 * that is created by the spider class.
 * <p/>
 * Copyright 2001-2003 by Jeff Heaton (http://www.jeffheaton.com)
 *
 * @author Jeff Heaton
 * @version 1.2
 */
public class SpiderWorker extends Thread {

    /**
     * The URL that this spider worker
     * should be downloading.
     */
    protected jobinfo target;

    protected proxySetup proxy;

    /**
     * 当前处于运行状态的站点ID
     */
    protected List sites;

    protected siteinfo sf;

    /**
     * The owner of this spider worker class,
     * should always be a Spider object.
     * This is the class that this spider
     * worker will send its data to.
     */
    protected Spider owner;

    /**
     * Indicates if the spider is busy or not.
     * true = busy
     * false = idle
     */
    protected boolean busy;

    /**
     * A descendant of the HTTP object that
     * this class should be using for HTTP
     * communication. This is usually the
     * HTTPSocket class.
     */
    protected HTTP http;

    /**
     * Constructs a spider worker object.
     *
     * @param owner The owner of this object, usually
     *              a Spider object.
     * @param http  http
     * @param proxy proxy
     */
    public SpiderWorker(Spider owner, HTTP http, proxySetup proxy) {
        this.http = http;
        this.owner = owner;
        this.proxy = proxy;
    }

    /**
     * Returns true of false to indicate if
     * the spider is busy or idle.
     *
     * @return true = busy
     *         false = idle
     */
    public boolean isBusy() {
        return this.busy;
    }

    /**
     * The run method causes this thread to go idle
     * and wait for a workload. Once a workload is
     * received, the processWorkload method is called
     * to handle the workload.
     */
    public void run() {
        //System.out.println("线程 " + this.getName() + "被启动");

        //File file = new File(owner.logpath + File.separator + this.getName() + ".log");
        //file.delete();
        //Log.setLevel(Log.LOG_LEVEL_NORMAL);
        //Log.setFile(true);
        //Log.setConsole(false);
        //Log.setPath(owner.logpath + File.separator + this.getName() + ".log");

        //设置Proxy服务器
        if ((proxy != null) && (proxy.getProxyFlag() == 1)) {
            http.setProxyHost(proxy.getProxyHost());
            http.setProxyPort(proxy.getProxyPort());
        }

        owner.getSpiderDone().workerBegin();
        while (true) {
            sf = this.owner.getSite();
            if (sf != null) {
                System.out.println("线程 " + this.getName() + "抓取站点" + sf.getSitename() + "=" + sf.getStarturl());
                if (sf.getLoginflag() != 0) {
                    http.setUseCookies(true, true);
                    http.setAgent("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
                    http.SetAutoRedirect(true);
                    HTMLPage page = new HTMLPage(http);

                    //登录到网站系统
                    try {
                        page.post(sf.getPostURL(), sf.getPostData());
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                //处理每个站点初始化的URL
                target = new jobinfo();
                target.setJoburl(sf.getStarturl());
                target.setID(0);

                processWorkload(sf.getID(), sf.getColumns(),sf.getMatchurls(),sf.getTags());

                //处理站点其他的URL
                while (target != null) {
                    target = this.owner.getWorkload(sf.getID());
                    if (target != null) {
                        processWorkload(sf.getID(), sf.getColumns(),sf.getMatchurls(),sf.getTags());
                    }
                }

                //修改这个站点的状态为完成状态
                this.owner.updateSiteStatus(sf.getID(), "C");
            } else {
                break;
            }
        }

        //System.out.println("线程 " + this.getName() + "结束运行");
        owner.getSpiderDone().workerEnd();
    }

    /**
     * The run method actually performs the
     * the workload assigned to this object.
     */
    public void processWorkload(int siteid, List columns, List matchurls,List tags) {
        List urllist = new ArrayList();
        Date c_date = new Date(System.currentTimeMillis());
        int s_day = 0;
        int s_mon = 0;
        int s_year = 0;
        int urlid = 0;

        try {
            busy = true;
            urlid = target.getID();
            boolean saveflag = false;
            String timebuf;
            Log.log(Log.LOG_LEVEL_NORMAL, "Spidering " + target.getJoburl());

            http.setTimeout(30000);
            http.send(target.getJoburl(), null);

            Attribute typeAttribute = http.getServerHeaders().get("Content-Type");
            Attribute modified_timeAttribute = http.getServerHeaders().get("Last-Modified");

            if (modified_timeAttribute == null)
                modified_timeAttribute = http.getServerHeaders().get("Date");

            if (modified_timeAttribute != null) {
                timebuf = modified_timeAttribute.getValue();
                //Tue, 04 Sep 2007 12:43:06 GMT
                timebuf = timebuf.substring(5, timebuf.length() - 4).trim();
                s_day = Integer.parseInt(timebuf.substring(0, 2));
                s_mon = convertMonthToNumberFormat(timebuf.substring(3, 6));
                s_year = Integer.parseInt(timebuf.substring(7, 11));

                if ((c_date.getYear() + 1900) == s_year && (c_date.getMonth() + 1) == s_mon && c_date.getDate() == s_day) {
                    saveflag = true;
                    owner.setURL_LastUpdate(target.getID(), s_year, s_mon, s_day, siteid);
                }
            }

            // if no content-type at all, its PROBABLY not HTML
            if (typeAttribute == null) {
                owner.completePage(http,urlid,false, siteid, 100);
                return;
            }

            // now check to see if is HTML, ONLY PARSE text type files(namely HTML)
            owner.processPage(http,columns,matchurls,tags,siteid);

            if (!typeAttribute.getValue().startsWith("text/")) {
                owner.completePage(http,urlid, false, siteid, 200);
                return;
            }

            String buf = http.getBody();
            ParsePage parse1 = new ParsePage(buf);
            System.out.println("saveflag=" + saveflag);
            urllist = parse1.resultByFilter(target.getJoburl(),matchurls,"<a","</a>");


            /*HTMLParser parse = new HTMLParser();

            parse.source = new StringBuffer(http.getBody());

            //System.out.println("target=" + target.getJoburl());

            // find all the links
            while (!parse.eof()) {
                char ch = parse.get();
                if (ch == 0) {
                    HTMLTag tag = parse.getTag();
                    Attribute link = tag.get("HREF");

                    if (link == null) continue;
                    URL mytarget;
                    try {
                        mytarget = new URL(new URL(target.getJoburl()), link.getValue());
                    } catch (MalformedURLException e) {
                        owner.foundOtherLink(link.getValue(), siteid);
                        continue;
                    }

                    if (link.getValue().toLowerCase().indexOf(".css") == -1) {

                        String s_url = mytarget.toString().toLowerCase();
                        String filename = mytarget.getFile();
                        filename = filename.substring(filename.lastIndexOf("/") + 1);

                        if (s_url.indexOf(matchurl) != -1 && saveflag && s_url.indexOf("pdf") == -1 &&
                                s_url.indexOf("doc") == -1 && s_url.indexOf("ppt") == -1 && s_url.indexOf("rar") == -1 &&
                                s_url.indexOf("zip") == -1 && s_url.indexOf("exe") == -1 && s_url.indexOf("xls") == -1 &&
                                s_url.indexOf("??") == -1) {

                            URLInfo urlinfo = new URLInfo();

                            //获取URL对应的标题
                            String title = buf;
                            int posi = title.indexOf(filename);
                            if (posi > -1) {
                                title = title.substring(posi);
                                posi = title.indexOf("</a>");
                                if (posi > -1) title = title.substring(0, posi);
                                //System.out.println(title);
                                posi = title.lastIndexOf(">");
                                if (posi > -1) title = title.substring(posi + 1);
                            } else {
                                title = "文章标题";
                            }
                            urlinfo.setURLInfo(mytarget);
                            urlinfo.setTitle(title);
                            urllist.add(urlinfo);
                        } else {
                            //Log.log(Log.LOG_LEVEL_NORMAL,"Spider found external link: " + mytarget.toString() );
                            owner.foundExternalLink(mytarget.toString(), siteid);
                        }
                    }
                }
            }*/

            //如果是新网页,保存该网页上的连接
            owner.batchAddWorkload(urllist, siteid);
            //if (saveflag==true)  owner.batchAddWorkload(urllist, siteid);

            //判断被抓取网页的类型
            //int s_index = buf.indexOf(starttag);
            //int e_index = buf.indexOf(endtag);
            int urltype = 0;
            //if (s_index > -1 && e_index > -1) {
            //    urltype = 1;
            //}

            System.out.println("抓取完网页"+ "(" + this.getName() + ")" + this.target.getJoburl() + "(" + s_year + "-" + s_mon + "-" + s_day + ")" + "-(" + this.target.getTitle() + ")");
            owner.completePage(http,urlid, false, siteid, urltype);
        } catch (java.io.IOException e) {
            Log.log(Log.LOG_LEVEL_ERROR, "Error loading file(" + target.getJoburl() + "): " + e);
            owner.completePage(http,urlid, true, siteid, 100);
        } catch (Exception e) {
            Log.logException("Exception while processing file(" + target.getJoburl() + "): ", e);
            owner.completePage(http,urlid, true, siteid, 100);
        } finally {
            busy = false;
            urllist = null;
        }
    }

    private int convertMonthToNumberFormat(String smonth) {
        int n_month = 0;

        if (smonth.equalsIgnoreCase("Jan"))
            n_month = 1;
        if (smonth.equalsIgnoreCase("Feb"))
            n_month = 2;
        if (smonth.equalsIgnoreCase("Mar"))
            n_month = 3;
        if (smonth.equalsIgnoreCase("Apr"))
            n_month = 4;
        if (smonth.equalsIgnoreCase("May"))
            n_month = 5;
        if (smonth.equalsIgnoreCase("Jun"))
            n_month = 6;
        if (smonth.equalsIgnoreCase("Jul"))
            n_month = 7;
        if (smonth.equalsIgnoreCase("Aut"))
            n_month = 8;
        if (smonth.equalsIgnoreCase("Sep"))
            n_month = 9;
        if (smonth.equalsIgnoreCase("Oct"))
            n_month = 10;
        if (smonth.equalsIgnoreCase("Nov"))
            n_month = 11;
        if (smonth.equalsIgnoreCase("Dec"))
            n_month = 12;

        return n_month;
    }

    /**
     * Returns the HTTP descendant that this
     * object should use for all HTTP communication.
     *
     * @return An HTTP descendant object.
     */
    public HTTP getHTTP() {
        return http;
    }
}

