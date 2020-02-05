package com.bizwink.cms.news;

import java.sql.*;
import java.util.List;

public class Column {
    private int ID;
    private int siteid;                            //站点编号
    private String dirName;                        //发布后的目录名
    private int orderID;                           //显示次序
    private int parentID;                          //default 0,父编号
    private int defineAttr;                        //栏目是否有自定义属性 0--没有  1--有
    private int hasArticleModel;                   //栏目是否有文章模板  0--没有   1--有
    private String CName;                          //chinese name
    private String EName;                          //english name
    private String extname;                        //生成文件的扩展名
    private Timestamp createDate;                  //创建时间(sysdate)
    private Timestamp lastUpdated;                 //最后修改时间(sysdate)
    private String editor;                         //最后修改者，FK
    private String xmlTemplate;                    //扩展属性模板
    private int extattrscope;                     //扩展属性的适用范围  0-本栏目 1-本栏目和它下面的所有子栏目
    private int isAudited;                         //是否需要审核
    private String desc;                           //栏目描述
    private int isProduct;                         //栏目属性
    private int isPosition;                       //是否需要录入地理位置信息
    private int isPublishMore;                     //是否发布多个文章模板
    private int languageType;                      //栏目语言类型 0-简体中文 1-繁体中文 2-日文
    private int contentShowType;                   //内容展示的类型 0--HTML  1--HTML+WAP 2-HTML+XHTML+WAP 3-HTML+XHTML+WAP+RSS
    private int isRss;                             //是否发布RSS
    private int getRssArticleTime;                 //要发布RSS的文章的天数
    private int archivingrules;                    //定义归档规则
    private List selectColumns;                    //定义引用被引用文章的栏目
    private List selectTypes;                      //定义引用分类的栏目
    private int useArticleType;                    //引用文章的类型 0-引用链接地址 1-引用文章内容
    private int isType;                            //是否需要文章分类 0-否 1-继承父栏目分类 2-继承父栏目并自定义分类 3-自定义分类
    private int userflag;                          //栏目文章是否只有注册用户才能浏览
    private int userlevel;                         //用户级别控制
    private int publicflag;                        //是否允许注册用户向本栏目发布信息
    private int ssiteid;
    private int scid;
    private int artfrom;                           //0-栏目定义时设置的引用，1-文章录入时设置的引用
    private int articlecount;                     //栏目中文章的总数量，包括本栏目的文章数量和从其他栏目引用的文章数量


    //add by kang 2010-09
    private String titlepic;                     //标题图片大小
    private String vtitlepic;                    //副标题图片大小
    private String sourcepic;                    //来源图片大小
    private String authorpic;                    //作者图片大小
    private String contentpic;                   //内容图片大小
    private String specialpic;                   //文章特效图片大小
    private String productpic;                   //商品大图片大小
    private String productsmallpic;              //商品小图片大小
    private String mediasize;                    //视频文件的大小
    private String mediapicsize;                 //视频缩略图大小
    private String ts_pic;
    private String s_pic;
    private String ms_pic;
    private String m_pic;
    private String ml_pic;
    private String l_pic;
    private String tl_pic;

    //end add

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSiteID() {
        return siteid;
    }

    public void setSiteID(int siteID) {
        this.siteid = siteID;
    }

    public void setDefineAttr(int defineAttr) {
        this.defineAttr = defineAttr;
    }

    public int getDefineAttr() {
        return defineAttr;
    }

    public void setHasArticleModel(int hasArticleModel) {
        this.hasArticleModel = hasArticleModel;
    }

    public int getHasArticleModel() {
        return hasArticleModel;
    }

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getCName() {
        return CName;
    }

    public void setCName(String CName) {
        this.CName = CName;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    public String getExtname() {
        return extname;
    }

    public void setExtname(String extname) {
        this.extname = extname;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setXMLTemplate(String xmlTemplate) {
        this.xmlTemplate = xmlTemplate;
    }

    public String getXMLTemplate() {
        return xmlTemplate;
    }

    public int getExtattrscope() {
        return extattrscope;
    }

    public void setExtattrscope(int scope) {
        this.extattrscope = scope;
    }

    public int getIsAudited() {
        return isAudited;
    }

    public void setIsAudited(int isAudited) {
        this.isAudited = isAudited;
    }

    public int getPublicflag() {
        return publicflag;
    }

    public void setPublicflag(int flag) {
        this.publicflag = flag;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public int getIsPosition() {
        return isPosition;
    }

    public void setIsPosition(int isPosition) {
        this.isPosition = isPosition;
    }

    public int getIsPublishMoreArticleModel() {
        return isPublishMore;
    }

    public void setIsPublishMoreArticleModel(int isPublishMore) {
        this.isPublishMore = isPublishMore;
    }

    public int getLanguageType() {
        return languageType;
    }

    public void setLanguageType(int languageType) {
        this.languageType = languageType;
    }

    public int getContentShowType() {
        return contentShowType;
    }

    public void setContentShowType(int showtype) {
        this.contentShowType = showtype;
    }

    public int getGetRssArticleTime() {
        return getRssArticleTime;
    }

    public void setGetRssArticleTime(int getRssArticleTime) {
        this.getRssArticleTime = getRssArticleTime;
    }

    public int getRss() {
        return isRss;
    }

    public void setRss(int rss) {
        isRss = rss;
    }

    public int getArchivingrules() {
        return archivingrules;
    }

    public void setArchivingrules(int archivingrules) {
        this.archivingrules = archivingrules;
    }

    public List getSelectColumns() {
        return selectColumns;
    }

    public void setSelectColumns(List selectColumns) {
        this.selectColumns = selectColumns;
    }

    public int getUseArticleType() {
        return useArticleType;
    }

    public void setUseArticleType(int useArticleType) {
        this.useArticleType = useArticleType;
    }

    public int getIsType() {
        return isType;
    }

    public void setIsType(int istype) {
        isType = istype;
    }

    public int getSsiteid() {
        return ssiteid;
    }

    public void setSsiteid(int ssiteid) {
        this.ssiteid = ssiteid;
    }

    public int getScid() {
        return scid;
    }

    public void setScid(int scid) {
        this.scid = scid;
    }

    public List getSelectTypes() {
        return selectTypes;
    }

    public void setSelectTypes(List selectTypes) {
        this.selectTypes = selectTypes;
    }

    public int getUserflag() {
        return userflag;
    }

    public void setUserflag(int userflag) {
        this.userflag = userflag;
    }

    public int getUserlevel() {
        return userlevel;
    }

    public void setUserlevel(int userlevel) {
        this.userlevel = userlevel;
    }

    public int getArtfrom() {
        return artfrom;
    }

    public void setArtfrom(int from) {
        this.artfrom = from;
    }
    public int getArticleCount() {
        return articlecount;
    }

    public void setArticlecount(int count) {
        this.articlecount = count;
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