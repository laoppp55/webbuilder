package com.bizwink.cms.news;

import java.sql.*;

public class Article {
    private int ID = 0;                //编号
    private int articlenum;          //文章序号，在文章列表中如果需要显示顺序流水号，则在生成文章列表的时候动态生成。
    private int siteid;                //文章所属的站点
    private String deptid;             //发布文章的部门编号
    private int columnID;              //栏目编号
    private String articleclass;      //从根目录到文章所在目录的id的联合号
    private int sortID;                //排序号
    private String maintitle;          //主标题
    private String vicetitle;          //副标题
    private String summary;            //摘要
    private String keyword;            //关键词
    private String source;             //来源
    private String content;            //内容
    private int nullcontent;           //内容是否为空，0--不为空   1--为空
    private String author;             //作者
    private Timestamp publishTime;     //页面生成日期
    private Timestamp createDate;      //入库日期
    private Timestamp lastUpdated;     //修改日期
    private String dirName;            //发布后的目录名
    private String fileName;           //发布后的文件名
    private String editor;             //编辑
    private String creator;            //文章原始创建人
    private int status;                //是否可用              0--不使用   1--使用
    private int topflag;               //文章标题在列表中是否置顶， 0-正常顺序显示 1-置顶
    private int redflag;               //文章标题在列表中是否飘红，0-正常 1-飘红
    private int boldflag;              //文章标题在列表中是否加黑，0-正常 1-加黑
    private int docLevel;              //重要性,               0--不同文章 1--重要文章
    private int pubFlag;               //发布标识              0--被发布   1--需要发布   2--发布失败，需要重新发布
    private int auditFlag;             //审核状态              0--审核完毕 1--在审核中   2--被退稿，需要重新审核
    private int subscriber;            //是否发送给订阅者       0--不发送   1--需要发送   2--发送完毕
    private int lockstatus;            //文件使用锁定标志       0--开锁     1--锁定
    private String auditor;            //是否在审核中
    private String lockeditor;         //文章编辑者
    private boolean isTemplate;        //当前记录是文章还是模板
    private int isArticleTemplate;     //1=文章模板  0=栏目模板
    private int isPublished;            //文章是否被发布过
    private String articleversion;      //文章版本号
    private String indexnum;            //文章索引号
    private float salePrice;            //商品销售价格
    private float inPrice;              //商品进价
    private float marketPrice;         //商品市场价格
    private float vipprice;            //商品VIP价格
    private int score;                     //商品积分
    private int voucher;                  //商品购物券
    private String productPic;            //商品小图片
    private String productBigPic;        //商品大图片
    private String brand;                 //商品品牌
    private int stockNum;                //商品库存
    private float productWeight;       //商品重量
    private int notes;                   //商品说明字段
    private String mediafile;            //视频文件名称
    private String relatedArtID;
    private int viceDocLevel;          //文章副权重
    private int isJoinRSS;             //是否发布到RSS页面
    private int clickNum;              //文章点击数
    private int referArticleID;
    private int modelID;                //文章使用的发布模板
    private String siteName;            //站点名称
    private String articlepic;          //文章图片
    private int orders;                 //推荐文章排序
    private String otherurl;            //文章自定义链接的URL
    private int urltype;                //文章链接类型 0-默认链接 1-自定义链接
    private int indexflag;              //文章是否被加入到全文索引库中
    private int referedTargetId;       //文章是否是引用的
    private boolean isown;              //文章是来自引用表中的文章
    private String downfilename;        //下载文件
    private int t1;                      //按条件1分类
    private int t2;                      //按条件2分类
    private int t3;                      //按条件3分类
    private int t4;                      //按条件4分类
    private int t5;                      //按条件5分类
    private Date bbdate;
    int salesnum;                        //销售数量
    String markid;                        //
    private int userflag;
    private int processofaudit;        //被审核的文章所处的审核步骤
    //column
    private String ename;
    private int changepic;              //图片特效类型
    private int multimediatype;         //多媒体文章标志0-不是多媒体文章 1-是多媒体文章
    private int fromsiteid;             //采集信息来源网站ID
    private String sarticleid;           //采集信息在原来网站的文章ID

    public int getMultimediatype() {
        return multimediatype;
    }

    public void setMultimediatype(int multimediatype) {
        this.multimediatype = multimediatype;
    }

    public String getMediafile() {
        return mediafile;
    }

    public void setMediafile(String mediafile) {
        this.mediafile = mediafile;
    }

    public int getChangepic() {
        return changepic;
    }

    public void setChangepic(int changepic) {
        this.changepic = changepic;
    }

    public int getProcessofaudit() {
        return processofaudit;
    }

    public void setProcessofaudit(int processofaudit) {
        this.processofaudit = processofaudit;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getArticlenum() {
        return articlenum;
    }

    public void setArticlenum(int articlenum) {
        this.articlenum = articlenum;
    }

    public int getSiteID() {
        return siteid;

    }

    public void setSiteID(int siteid) {
        this.siteid = siteid;
    }

    public String getDeptid() { return deptid;}

    public void setDeptid(String deptid) {this.deptid = deptid;}

    public int getColumnID() {
        return columnID;
    }

    public void setColumnID(int columnID) {
        this.columnID = columnID;
    }

    public int getSortID() {
        return sortID;
    }

    public void setSortID(int sortID) {
        this.sortID = sortID;
    }

    public String getMainTitle() {
        return maintitle;
    }

    public void setMainTitle(String maintitle) {
        this.maintitle = maintitle;
    }

    public String getViceTitle() {
        return vicetitle;
    }

    public void setViceTitle(String vicetitle) {
        this.vicetitle = vicetitle;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNullContent() {
        return nullcontent;
    }

    public void setNullContent(int nullcontent) {
        this.nullcontent = nullcontent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
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

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTopflag() {
        return topflag;
    }

    public void setTopflag(int topflag) {
        this.topflag = topflag;
    }

    public int getRedflag() {
        return redflag;
    }

    public void setRedflag(int redflag) {
        this.redflag = redflag;
    }

    public int getBoldflag() {
        return boldflag;
    }

    public void setBoldflag(int boldflag) {
        this.boldflag = boldflag;
    }

    public int getDocLevel() {
        return docLevel;
    }

    public void setDocLevel(int docLevel) {
        this.docLevel = docLevel;
    }

    public int getAuditFlag() {
        return auditFlag;
    }

    public void setAuditFlag(int auditFlag) {
        this.auditFlag = auditFlag;
    }

    public int getPubFlag() {
        return pubFlag;
    }

    public void setPubFlag(int pubFlag) {
        this.pubFlag = pubFlag;
    }

    public int getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    public int getLockStatus() {
        return lockstatus;
    }

    public void setLockStatus(int lockstatus) {
        this.lockstatus = lockstatus;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setLockEditor(String lockeditor) {
        this.lockeditor = lockeditor;
    }

    public String getLockEditor() {
        return lockeditor;
    }

    public boolean getIsTemplate() {
        return isTemplate;
    }

    public void setIsTemplate(boolean isTemplate) {
        this.isTemplate = isTemplate;
    }

    public int getIsArticleTemplate() {
        return isArticleTemplate;
    }

    public void setIsArticleTemplate(int isArticleTemplate) {
        this.isArticleTemplate = isArticleTemplate;
    }

    public void setIsPublished(int isPublished) {
        this.isPublished = isPublished;
    }

    public int getIsPublished() {
        return isPublished;
    }

    public void setArticleVersion(String articleversion) {
        this.articleversion = articleversion;
    }

    public String getArticleVersion() {
        return articleversion;
    }

    public String getIndexnum() {
        return indexnum;
    }

    public void setIndexnum(String indexnum) {
        this.indexnum = indexnum;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setInPrice(float inPrice) {
        this.inPrice = inPrice;
    }

    public float getInPrice() {
        return inPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setVIPPrice(float vPrice) {
        this.vipprice = vPrice;
    }

    public float getVIPPrice() {
        return vipprice;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public int getVoucher() {
        return voucher;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductBigPic(String productBigPic) {
        this.productBigPic = productBigPic;
    }

    public String getProductBigPic() {
        return productBigPic;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setStockNum(int stockNum) {
        this.stockNum = stockNum;
    }

    public int getStockNum() {
        return stockNum;
    }

    public void setProductWeight(float productWeight) {
        this.productWeight = productWeight;
    }

    public float getProductWeight() {
        return productWeight;
    }

    public int getNotes() {
        return notes;
    }

    public void setNotes(int notes) {
        this.notes = notes;
    }

    public void setRelatedArtID(String relatedArtID) {
        this.relatedArtID = relatedArtID;
    }

    public String getRelatedArtID() {
        return relatedArtID;
    }

    public int getViceDocLevel() {
        return viceDocLevel;
    }

    public void setViceDocLevel(int viceDocLevel) {
        this.viceDocLevel = viceDocLevel;
    }

    public int getJoinRSS() {
        return isJoinRSS;
    }

    public void setJoinRSS(int joinRSS) {
        isJoinRSS = joinRSS;
    }

    public int getClickNum() {
        return clickNum;
    }

    public void setClickNum(int clickNum) {
        this.clickNum = clickNum;
    }

    public int getReferArticleID() {
        return referArticleID;
    }

    public void setReferArticleID(int referArticleID) {
        this.referArticleID = referArticleID;
    }

    public int getModelID() {
        return modelID;
    }

    public void setModelID(int modelID) {
        this.modelID = modelID;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getArticlepic() {
        return articlepic;
    }

    public void setArticlepic(String articlepic) {
        this.articlepic = articlepic;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getUrltype() {
        return urltype;
    }

    public void setUrltype(int urltype) {
        this.urltype = urltype;
    }

    public String getOtherurl() {
        return otherurl;
    }

    public void setOtherurl(String otherurl) {
        this.otherurl = otherurl;
    }

    public int getIndexflag() {
        return indexflag;
    }

    public void setIndexflag(int indexflag) {
        this.indexflag = indexflag;
    }

    public int getReferedTargetId() {
        return referedTargetId;
    }

    public void setReferedTargetId(int referedTargetId) {
        this.referedTargetId = referedTargetId;
    }

    public String getDownfilename() {
        return downfilename;
    }

    public void setDownfilename(String downfilename) {
        this.downfilename = downfilename;
    }

    public boolean isIsown() {
        return isown;
    }

    public void setIsown(boolean isown) {
        this.isown = isown;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public int getT1() {
        return t1;
    }

    public void setT1(int val) {
        this.t1 = val;
    }

    public int getT2() {
        return t2;
    }

    public void setT2(int val) {
        this.t2 = val;
    }

    public int getT3() {
        return t3;
    }

    public void setT3(int val) {
        this.t3 = val;
    }

    public int getT4() {
        return t4;
    }

    public void setT4(int val) {
        this.t4 = val;
    }

    public int getT5() {
        return t5;
    }

    public void setT5(int val) {
        this.t5 = val;
    }

    public Date getBbdate() {
        return bbdate;
    }

    public void setBbdate(Date bbdate) {
        this.bbdate = bbdate;
    }

    public int getSalesnum() {
        return salesnum;
    }

    public void setSalesnum(int salesnum) {
        this.salesnum = salesnum;
    }

    public String getMarkid() {
        return markid;
    }

    public void setMarkid(String markid) {
        this.markid = markid;
    }

    public String getArticleclass() {
        return articleclass;
    }

    public void setArticleclass(String ac) {
        this.articleclass = ac;
    }

    public int getUserflag() {
        return userflag;
    }

    public void setUserflag(int userflag) {
        this.userflag = userflag;
    }

    public int getFromsiteid() {
        return fromsiteid;
    }

    public void setFromsiteid(int fromsiteid) {
        this.fromsiteid = fromsiteid;
    }

    public String getSarticleid() {
        return sarticleid;
    }

    public void setSarticleid(String sarticleid) {
        this.sarticleid = sarticleid;
    }
}