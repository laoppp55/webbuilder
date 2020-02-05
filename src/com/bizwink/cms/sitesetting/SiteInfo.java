package com.bizwink.cms.sitesetting;

public class SiteInfo {
    private String domainName;        //站点的域名
    private int siteid;               //该站点的编号
    private int imgeDir;              //图片保存位置
    private int cssjsDir;             //css文件和js文件存放的位置
    private int tcFlag;               //是否发布繁体
    private int wapflag;              //是否支持WAP1.0
    private int encoding;             //网站页面编码类型
    private int rssflag;              //是否支持RSS
    private int xhtmlflag;            //是否支持WAP2.0
    private int bindFlag;
    private int pubFlag;
    private int validflag;            //站点是否可以作为网站模板使用
    private int sitetype;             //站点类型 0--样例网站 1-普通网站 2-共享网站
    private int samsiteid;            //共享站点的id，对于一般注册的站点和拷贝的站点samsiteid=0
    private String sitepic;
    private String createdate;
    private String copyright;
    private int tempnum;
    private String titlepic;                     //标题图片大小
    private String vtitlepic;                    //副标题图片大小
    private String sourcepic;                    //来源图片大小
    private String authorpic;                    //作者图片大小
    private String contentpic;                   //内容图片大小
    private String specialpic;                   //文章特效图片大小
    private String productpic;                   //商品大图片大小
    private String productsmallpic;              //商品小图片大小
    private String mediasize;                     //视频文件大小
    private String mediapicsize;                  //视频文件缩略图的大小
    private String ts_pic;
    private String s_pic;
    private String ms_pic;
    private String m_pic;
    private String ml_pic;
    private String l_pic;
    private String tl_pic;

    public void setDomainName(String pdomainName) {
        this.domainName = pdomainName;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setSiteid(int siteid) {
        this.siteid = siteid;
    }

    public int getSiteid() {
        return siteid;
    }

    public void setImgeDir(int imgeDir) {
        this.imgeDir = imgeDir;
    }

    public int getImgeDir() {
        return imgeDir;
    }

    public void setTcFlag(int tcFlag) {
        this.tcFlag = tcFlag;
    }

    public int getTcFlag() {
        return tcFlag;
    }

    public void setWapFlag(int wapFlag) {
        this.wapflag = wapFlag;
    }

    public int getWapFlag() {
        return wapflag;
    }

    public int getEncoding() { return encoding;}

    public void setEncoding(int encoding) { this.encoding = encoding;}

    public void setRSSFlag(int rssFlag) {
        this.rssflag = rssFlag;
    }

    public int getRSSFlag() {
        return rssflag;
    }

    public void setXHTMLFlag(int xhtmlFlag) {
        this.xhtmlflag = xhtmlFlag;
    }

    public int getXHTMLFlag() {
        return xhtmlflag;
    }

    public void setBindFlag(int bindFlag) {
        this.bindFlag = bindFlag;
    }

    public int getBindFlag() {
        return bindFlag;
    }

    public int getPubFlag() {
        return pubFlag;
    }

    public void setPubFlag(int pubFlag) {
        this.pubFlag = pubFlag;
    }

    public int getValidFlag() {
        return validflag;
    }

    public void setValidFlag(int validFlag) {
        this.validflag = validFlag;
    }

    public int getSitetype() {
        return sitetype;
    }

    public void setSitetype(int type) {
        this.sitetype = type;
    }

    public int getCssjsDir() {
        return cssjsDir;
    }

    public void setCssjsDir(int cssjsDir) {
        this.cssjsDir = cssjsDir;
    }
    public void setDomainPic(String pic) {
        this.sitepic = pic;
    }

    public String getDomainPic() {
        return sitepic;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public void setSamsiteid(int siteid) {
        this.samsiteid = siteid;
    }

    public int getSamsiteid() {
        return samsiteid;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getTempnum() {
        return tempnum;
    }

    public void setTempnum(int tempnum) {
        this.tempnum = tempnum;
    }

    public int getWapflag() {
        return wapflag;
    }

    public void setWapflag(int wapflag) {
        this.wapflag = wapflag;
    }

    public int getRssflag() {
        return rssflag;
    }

    public void setRssflag(int rssflag) {
        this.rssflag = rssflag;
    }

    public int getXhtmlflag() {
        return xhtmlflag;
    }

    public void setXhtmlflag(int xhtmlflag) {
        this.xhtmlflag = xhtmlflag;
    }

    public int getValidflag() {
        return validflag;
    }

    public void setValidflag(int validflag) {
        this.validflag = validflag;
    }

    public String getSitepic() {
        return sitepic;
    }

    public void setSitepic(String sitepic) {
        this.sitepic = sitepic;
    }

    public String getTitlepic() {
        return titlepic;
    }

    public void setTitlepic(String titlepic) {
        this.titlepic = titlepic;
    }

    public String getVtitlepic() {
        return vtitlepic;
    }

    public void setVtitlepic(String vtitlepic) {
        this.vtitlepic = vtitlepic;
    }

    public String getSourcepic() {
        return sourcepic;
    }

    public void setSourcepic(String sourcepic) {
        this.sourcepic = sourcepic;
    }

    public String getAuthorpic() {
        return authorpic;
    }

    public void setAuthorpic(String authorpic) {
        this.authorpic = authorpic;
    }

    public String getContentpic() {
        return contentpic;
    }

    public void setContentpic(String contentpic) {
        this.contentpic = contentpic;
    }

    public String getSpecialpic() {
        return specialpic;
    }

    public void setSpecialpic(String specialpic) {
        this.specialpic = specialpic;
    }

    public String getProductpic() {
        return productpic;
    }

    public void setProductpic(String productpic) {
        this.productpic = productpic;
    }

    public String getProductsmallpic() {
        return productsmallpic;
    }

    public void setProductsmallpic(String productsmallpic) {
        this.productsmallpic = productsmallpic;
    }

    public String getTs_pic() {
        return ts_pic;
    }

    public void setTs_pic(String ts_pic) {
        this.ts_pic = ts_pic;
    }

    public String getS_pic() {
        return s_pic;
    }

    public void setS_pic(String s_pic) {
        this.s_pic = s_pic;
    }

    public String getMs_pic() {
        return ms_pic;
    }

    public void setMs_pic(String ms_pic) {
        this.ms_pic = ms_pic;
    }

    public String getM_pic() {
        return m_pic;
    }

    public void setM_pic(String m_pic) {
        this.m_pic = m_pic;
    }

    public String getMl_pic() {
        return ml_pic;
    }

    public void setMl_pic(String ml_pic) {
        this.ml_pic = ml_pic;
    }

    public String getL_pic() {
        return l_pic;
    }

    public void setL_pic(String l_pic) {
        this.l_pic = l_pic;
    }

    public String getTl_pic() {
        return tl_pic;
    }

    public void setTl_pic(String tl_pic) {
        this.tl_pic = tl_pic;
    }

    public String getMediasize() {
        return mediasize;
    }

    public void setMediasize(String mediasize) {
        this.mediasize = mediasize;
    }

    public String getMediapicsize() {
        return mediapicsize;
    }

    public void setMediapicsize(String mediapicsize) {
        this.mediapicsize = mediapicsize;
    }
}