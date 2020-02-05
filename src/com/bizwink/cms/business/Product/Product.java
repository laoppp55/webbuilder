package com.bizwink.cms.business.Product;

import java.sql.*;

public class Product
{
    private int ID = 0;                //编号
    private int siteid;                //文章所属的站点
    private int columnID;              //栏目编号
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
    private int status;                //是否可用              0--不使用   1--使用
    private int docLevel;              //重要性,               0--不同文章 1--重要文章
    private int pubFlag;               //发布标识              0--被发布   1--需要发布   2--发布失败，需要重新发布
    private int auditFlag;             //审核状态              0--审核完毕 1--在审核中   2--被退稿，需要重新审核
    private int subscriber;            //是否发送给订阅者      0--不发送   1--需要发送   2--发送完毕
    private int lockstatus;            //文件使用锁定标志      0--开锁     1--锁定
    private String auditor;            //是否在审核中
    private String lockeditor;         //文章编辑者
    private boolean isTemplate;        //当前记录是文章还是模板
    private int isArticleTemplate;     //1=文章模板  0=栏目模板
    private int isPublished;
    private String articleversion;
    private float salePrice;
    private float inPrice;
    private float marketPrice;
    private String productPic;
    private String productBigPic;
    private String brand;
    private int stockNum;
    private int productWeight;
    private String relatedArtID;
    private int scores;          //商品积分
    private int voucher;         //商品允许使用购物券的金额
    private String suppliername;

    public Product(){
    }

    // Returns the id of the article.
    public int getID() { return ID; }

    // set the id of the article.
    public void setID(int ID) { this.ID = ID; }

    // Returns the id of the article.
    public int getSiteID() { return siteid; }

    // set the id of the article.
    public void setSiteID(int siteid) { this.siteid  = siteid; }

    // Returns the columnID of the article.
    public int getColumnID() {
        return columnID;
    }

    // set the columnID of the article.
    public void setColumnID(int columnID) {
        this.columnID = columnID;
    }

    // Returns the sortID of the article.
    public int getSortID() {
        return sortID;
    }

    // set the sortID of the article.
    public void setSortID(int sortID) {
        this.sortID = sortID;
    }

    // Returns the Maintitle of the article.
    public String getMainTitle() {
        return maintitle;
    }

    // set the Maintitle of the article.
    public void setMainTitle(String maintitle) {
        this.maintitle = maintitle;
    }

    // Returns the Vicetitle of the article.
    public String getViceTitle() {
        return vicetitle;
    }

    // set the Vicetitle of the article.
    public void setViceTitle(String vicetitle) {
        this.vicetitle = vicetitle;
    }

    // Returns the summary of the article.
    public String getSummary() {
        return summary;
    }

    // Sets the summary of the article.
    public void setSummary(String summary) {
        this.summary = summary;
    }

    // Returns the keywords of the article.
    public String getKeyword() {
        return keyword;
    }

    // Sets the keywords of the article.
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    // * Returns the Source of the article.
    public String getSource() {
        return source;
    }

    //* Sets the Source of the article.
    public void setSource(String source) {
        this.source = source;
    }

    // * Returns the content of the article.
    public String getContent() {
        return content;
    }

    // * set the content of the article.
    public void setContent(String content) {
        this.content = content;
    }

    // * Returns the content of the article.
    public int getNullContent() {
        return nullcontent;
    }

    // * set the content of the article.
    public void setNullContent(int nullcontent) {
        this.nullcontent = nullcontent;
    }
    // * Returns the content of the article.
    public String getAuthor() {
        return author;
    }

    // * set the content of the article.
    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    // Returns the Date that the article was created.
    public Timestamp getCreateDate() {
        return createDate;
    }

    //* Sets the creation date of the article.
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    // * Returns the Date that the article was modified.
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    // * Sets the modified date of the article.
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    // * Returns the dirname of the article.
    public String getDirName() {
        return dirName;
    }

    // * Sets the dirname of the article.
    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    //* Returns the filename of the article.
    public String getFileName() {
        return fileName;
    }

    // * Sets the filename of the article.
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    //* Returns the editor of the article.
    public String getEditor() {
        return editor;
    }

    // * Sets the editor of the article.
    public void setEditor(String editor) {
        this.editor = editor;
    }

    // * Returns the status of the article.
    public int getStatus() {
        return status;
    }

    //* set the status of the article.
    public void setStatus(int status) {
        this.status = status;
    }

    //* Returns the level of the article.
    public int getDocLevel() {
        return docLevel;
    }

    // * set the doclevel of the article.
    public void setDocLevel(int docLevel) {
        this.docLevel = docLevel;
    }

    // Returns the current audit level of the article.
    public int getAuditFlag() {
        return auditFlag;
    }

    //set the new audit level of the article.
    public void setAuditFlag (int auditFlag) {
        this.auditFlag = auditFlag;
    }

    // * Returns the publishFlag of the article.
    public int getPubFlag() {
        return pubFlag;
    }

    // * set the publishFlag of the article.
    public void setPubFlag(int pubFlag) {
        this.pubFlag = pubFlag;
    }

    // * Returns the publishFlag of the article.
    public int getSubscriber() {
        return subscriber;
    }

    // * set the publishFlag of the article.
    public void setSubscriber(int subscriber) {
        this.subscriber = subscriber;
    }

    // Returns the current audit level of the article.
    public int getLockStatus() {
        return lockstatus;
    }

    //set the new audit level of the article.
    public void setLockStatus (int lockstatus) {
        this.lockstatus = lockstatus;
    }

    public void setAuditor(String auditor)
    {
        this.auditor = auditor;
    }

    public String getAuditor()
    {
        return auditor;
    }

    public void setLockEditor(String lockeditor)
    {
        this.lockeditor = lockeditor;
    }

    public String getLockEditor()
    {
        return lockeditor;
    }

    public boolean getIsTemplate()
    {
        return isTemplate;
    }

    public void setIsTemplate(boolean isTemplate)
    {
        this.isTemplate = isTemplate;
    }

    public int getIsArticleTemplate()
    {
        return isArticleTemplate;
    }

    public void setIsArticleTemplate(int isArticleTemplate)
    {
        this.isArticleTemplate = isArticleTemplate;
    }

    public void setIsPublished(int isPublished)
    {
        this.isPublished = isPublished;
    }

    public int getIsPublished()
    {
        return isPublished;
    }

    public void setArticleVersion(String articleversion)
    {
        this.articleversion = articleversion;
    }

    public String getArticleVersion()
    {
        return articleversion;
    }

    public void setSalePrice(float salePrice)
    {
        this.salePrice = salePrice;
    }

    public float getSalePrice()
    {
        return salePrice;
    }

    public void setInPrice(float inPrice)
    {
        this.inPrice = inPrice;
    }

    public float getInPrice()
    {
        return inPrice;
    }

    public void setMarketPrice(float marketPrice)
    {
        this.marketPrice = marketPrice;
    }

    public float getMarketPrice()
    {
        return marketPrice;
    }

    public void setProductPic(String productPic)
    {
        this.productPic = productPic;
    }

    public String getProductPic()
    {
        return productPic;
    }

    public void setProductBigPic(String productBigPic)
    {
        this.productBigPic = productBigPic;
    }

    public String getProductBigPic()
    {
        return productBigPic;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setStockNum(int stockNum)
    {
        this.stockNum = stockNum;
    }

    public int getStockNum()
    {
        return stockNum;
    }

    public void setProductWeight(int productWeight)
    {
        this.productWeight = productWeight;
    }

    public int getProductWeight()
    {
        return productWeight;
    }

    public void setRelatedArtID(String relatedArtID)
    {
        this.relatedArtID = relatedArtID;
    }

    public String getRelatedArtID()
    {
        return relatedArtID;
    }

    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

    public int getVoucher() {
        return voucher;
    }

    public void setVoucher(int voucher) {
        this.voucher = voucher;
    }

    public void setSuppliername(String suppliername)
    {
        this.suppliername = suppliername;
    }

    public String getSuppliername()
    {
        return suppliername;
    }

}