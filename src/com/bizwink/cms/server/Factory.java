package com.bizwink.cms.server;

import com.bizwink.cms.bjhqfw.jiben.IJiBenManager;
import com.bizwink.cms.bjhqfw.jiben.JiBenPeer;
import com.bizwink.cms.bjhqfw.shetuan.ISheTuanManager;
import com.bizwink.cms.bjhqfw.shetuan.SheTuanPeer;
import com.bizwink.cms.bjhqfw.yuding.IYuDingManager;
import com.bizwink.cms.bjhqfw.yuding.YuDingPeer;
import com.bizwink.cms.sjswsbs.IWsbsManager;
import com.bizwink.cms.sjswsbs.WsbsPeer;

import com.bizwink.cms.multimedia.*;
import com.bizwink.cms.toolkit.csinfo.CsInfoPeer;
import com.bizwink.cms.toolkit.csinfo.ICsInfoManager;
import com.bizwink.cms.toolkit.searchword.ISearchWordManager;
import com.bizwink.cms.toolkit.searchword.SearchWordPeer;
import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.security.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.processTag.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.audit.*;
import com.bizwink.cms.register.*;
import com.bizwink.net.ftp.*;
import com.bizwink.cms.webedit.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.log.*;
import com.bizwink.cms.orderArticleListManager.*;
import com.bizwink.cms.articleListmanager.*;
import com.bizwink.cms.markManager.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.stockinfo.*;
import com.bizwink.webapps.address.*;
import com.bizwink.webapps.userfunction.*;
import com.bizwink.webtrend.*;
import com.bizwink.error.*;
import com.bizwink.event.*;
import com.bizwink.move.*;
import com.bizwink.program.*;
import com.bizwink.cms.excel.*;
import com.bizwink.picture.*;
import com.bizwink.publishQueue.*;
import com.charts.*;

//for toolkit
//hello word
import com.bizwink.cms.toolkit.addresslist.*;
import com.bizwink.cms.toolkit.task.*;
import com.bizwink.cms.toolkit.counter.*;

//for sinopec
import com.sinopec.stock.*;
import com.bizwink.cms.toolkit.subscribe.*;
import com.bizwink.cms.toolkit.company.*;

//for business
import com.bizwink.cms.business.Users.*;
import com.bizwink.cms.business.Order.*;
import com.bizwink.cms.business.Product.*;
import com.bizwink.cms.business.Other.*;
import com.bizwink.cms.business.Message.*;
import com.bizwink.cms.business.deliver.*;
import com.bizwink.cms.pic.IPicManager;
import com.bizwink.cms.pic.PicPeer;
import com.bizwink.cms.refers.IRefersManager;
import com.bizwink.cms.refers.RefersPeer;
import com.bizwink.cms.selfDefine.ISelfDefineManager;
import com.bizwink.cms.selfDefine.SelfDefinePeer;
import com.bizwink.update.IUpdateManager;
import com.bizwink.update.UpdatePeer;

//进、销、存系统
//import com.bizwink.cms.kings.changedetail.*;
//import com.bizwink.cms.kings.changemaster.*;
//import com.bizwink.cms.kings.deliverydetail.*;
//import com.bizwink.cms.kings.deliverymaster.*;
//import com.bizwink.cms.kings.product.*;
//import com.bizwink.cms.kings.purchasedetail.*;
//import com.bizwink.cms.kings.purchasemaster.*;
//import com.bizwink.cms.kings.supplier.*;

//网站前台应用
import com.bizwink.webapps.comment.*;
import com.bizwink.webapps.leaveword.*;
import com.bizwink.webapps.security.*;
import com.bizwink.webapps.feedback.*;
import com.bizwink.webapps.survey.answer.*;
import com.bizwink.webapps.survey.define.*;
import com.bizwink.webapps.register.*;
import com.bizwink.webapps.articleonclick.*;
import com.bizwink.joincompany.*;
//import com.bizwink.bbs.bbs.*;
//import com.bizwink.bbs.service.*;
import com.bizwink.wenba.*;
import com.bizwink.webapps.questions.*;
import com.bizwink.webapps.usercenter.*;
import com.chinabuyregister.*;
import com.xml.IFormManager;
import com.xml.FormPeer;
import com.usermodelnews.*;
import com.bizwink.collectionmgr.*;

//for companyinfo
import com.bizwink.cms.toolkit.companyinfo.*;
import com.bizwink.cms.toolkit.websites.*;

//for 2010-09-13
//import com.bizwink.cms.toolkit.gwcase.*;
import com.bizwink.cms.toolkit.workday.*;
//import com.bizwink.webapps.appointment.*;

public class Factory implements IFactory {
    PoolServer cpool;

    IUserManager             userPeer;
    IGroupManager            groupPeer;
    IRightManager            rightPeer;

    IAuthManager             authPeer;
    ISequenceManager         sequencePeer;
    IColumnManager           columnPeer;
    IColumnTree              columnTree;
    ITree                    treePeer;
    IArticleManager          articlePeer;
    IColumnUserManager       columnUserPeer;
    IPublishManager          publishPeer;
    IModelManager            modelPeer;
    ITagManager              tagPeer;
    IFtpSetManager           ftpInfoPeer;
    EncodingUtil             encodingUtil;
    ILogManager              logPeer;

    IRightsManager           rightsPeer;
    IAuditManager            auditPeer;
    ISiteInfoManager         siteInfoPeer;
    IRegisterManager         registerPeer;
    IPullContentManager      pullContentPeer;
    IWebEditManager          webEditPeer;
    IWordManager             wordPeer;
    IJIMIManager             jimiPeer;
    IExtendAttrManager       extendAttrPeer;
    IWebtrendManager         webtrendPeer;
    IErrorManager            errorPeer;
    IMoveManager             movePeer;
    IOrderArticleListManager orderArtListPeer;
    IArticleListManager      articleListPeer;
    IViewFileManager         viewFilePeer;
    IMarkManager             markPeer;
    //ISystemTemplateManager   systemTemplatePeer;
    IExcelManager            excelPeer;
    ActivityManager          activityManagerImpl;
    EventManager             eventManagerImpl;
    IPicManager              picPeer;
    IProgramManager          programPeer;
    IPictureManager          picturePeer;
    IPublishQueueManager     publishQueuePeer;
    ISpiderManager           spiderPeer;

    //for business
    IBUserManager            buserPeer;
    IOrderManager            orderPeer;
    IProductManager          productPeer;
    IOtherManager            otherPeer;
    IMessageManager          messagePeer;
    IDeliverManager          deliverPeer;

    //for toolkit
    //IFeedBackManager        feedbackPeer;
    IAddressListManager      addresslistPeer;
    ITaskManager             taskPeer;
    IChartManager            chartPeer;
    ISearchWordManager       searchWordManager;

    //for sinopec
    IStockManager            stockPeer;
    ICompanyManager          companyPeer;
    ISubscribeManager        subscribePeer;

    IArticleKeywordManager   articlekeywordPeer;
    IUpdateManager           updatePeer;
    IRefersManager           refersPeer;

    //进、销、存系统
    //IProductSuManager productSuPeer;
    //IPurchaseDetailManager purchaseDetailManager;
    //IPurchaseMasterManager purchaseMasterManager;
    //ISupplierSuManager supplierSuManager;
    //IChangeMasterManager changeMasterManager;
    //IChangeDetailManager changeDetailManager;
    //IDeliveryMasterManager deliveryMasterManager;
    //IDeliveryDetailManager deliveryDetailManager;

    //网站前台应用
    IWebCommentManager     webCommentPeer;        //文章评论
    IWordManager           webWordPeer;           //网站留言
    IWebAuthManager        webAuthPeer;           //用户登录和权限管理
    IFeedbackManager       feedbackPeer;
    IUregisterManager      uregisterPeer;
    //IBBSManager            bbsPeer;
    //IServiceManager        servicePeer;
    IWenbaManager          wenbaPeer;
    IQuestionManager       questionPeer;
    IUsercenterManager     usercenterPeer;
    IArticleOnclickManager aopeer;
    //for survey
    IAnswerManager         answerPeer;
    IDefineManager         definePeer;
    //信息采集系统配置程序
    IBasic_AttributesManager basic_AttributesPeer;
    IMatchUrl_SpecialCodeManager matchUrl_SpecialCodePeer;


    //用户自定义表单
    ISelfDefineManager     selfDefinePeer;
    IJoincompanyManager    joinpeer;
    IChinaBuyRegiterManager chinabuypeer;
    //自定义表单
    IFormManager formpeer;
    //根绝后台摸版生成前台录入信息
    IUserModelNewsManager  umn;
    IUserFunctionManager   userFunctionPeer;

    //添加企业管理
    ICompanyinfoManager companyinfoPeer;
    IWebsiteManager     websiteinfoPeer;

    //无线电
    //IGWcaseManager GwcasePeer;

    //工作日管理
    IWorkdayManager workPeer;
    // 业务预约
    //IAppointmentManager appointmentPeer;
    //多媒体
    IMultimediaManager multimediaPeer;
    //送货地址
    IAddressManager addressPeer;

    //网上会议室预订功能
    IYuDingManager yuDingManager;
    IJiBenManager jiBenManager;
    ISheTuanManager sheTuanManager;

    //石景山网上办事
    IWsbsManager wsbsManager;
    //房型信息管理
    ICsInfoManager csInfoManager;

    public Factory(PoolServer cpool) {
        this.cpool = cpool;

        userPeer = new UserPeer(cpool);
        groupPeer = new GroupPeer(cpool);
        rightPeer = new RightPeer(cpool);
        columnUserPeer = new ColumnUserPeer(cpool);
        authPeer = new AuthPeer(cpool);
        sequencePeer = new SequencePeer(cpool);
        columnPeer = new ColumnPeer(cpool);
        articlePeer = new ArticlePeer(cpool);
        columnTree = new ColumnTree(cpool);
        publishPeer = new PublishPeer(cpool);
        modelPeer = new ModelPeer(cpool);
        treePeer = new TreeManager(cpool);
        tagPeer = new TagManager(cpool);
        ftpInfoPeer = new FtpSetting(cpool);
        encodingUtil = new EncodingUtil();
        rightsPeer = new RightsPeer(cpool);
        auditPeer = new AuditPeer(cpool);
        siteInfoPeer = new SiteInfoPeer(cpool);
        registerPeer = new RegisterPeer(cpool);
        pullContentPeer = new PullContentPeer(cpool);
        webEditPeer = new WebEditPeer(cpool);
        logPeer = new LogPeer(cpool);
        wordPeer = new LeaveWordPeer(cpool);
        jimiPeer = new JIMIPeer(cpool);
        extendAttrPeer = new ExtendAttrPeer(cpool);
        webtrendPeer = new WebtrendPeer(cpool);
        errorPeer = new ErrorPeer(cpool);
        movePeer = new MovePeer(cpool);

        orderArtListPeer = new orderArticleListPeer(cpool);
        articleListPeer = new articleListPeer(cpool);
        markPeer = new markPeer(cpool);
        viewFilePeer = new viewFilePeer(cpool);
        buserPeer = new buserPeer(cpool);
        orderPeer = new orderPeer(cpool);
        productPeer = new productPeer(cpool);
        otherPeer = new otherPeer(cpool);
        messagePeer = new messagePeer(cpool);
        deliverPeer = new DeliverPeer(cpool);
        excelPeer = new ExcelPeer(cpool);
        activityManagerImpl = new ActivityManagerImpl(cpool);
        eventManagerImpl = new EventManagerImpl(cpool);
        addresslistPeer = new AddressListPeer(cpool);
        picPeer = new PicPeer(cpool);
        picturePeer  = new PicturePeer(cpool);
        programPeer         = new ProgramPeer(cpool);
        publishQueuePeer   = new PublishQueuePeer(cpool);
        spiderPeer = new SpiderPeer(cpool);
        chartPeer  = new ChartPeer(cpool);
        userFunctionPeer = new UserFunctionPeer(cpool);
        //taskPeer            = new TaskPeer(cpool);

        //for business
        buserPeer = new buserPeer(cpool);
        orderPeer = new orderPeer(cpool);
        productPeer = new productPeer(cpool);
        otherPeer = new otherPeer(cpool);
        messagePeer = new messagePeer(cpool);
        deliverPeer = new DeliverPeer(cpool);

        //for toolkit
        //feedbackPeer = new FeedBackPeer(cpool);
        articlekeywordPeer = new ArticleKeywordPeer(cpool);
        updatePeer = new UpdatePeer(cpool);
        refersPeer = new RefersPeer(cpool);
        searchWordManager = new SearchWordPeer(cpool);

        //for sinopec
        stockPeer = new StockPeer(cpool);
        companyPeer = new CompanyPeer(cpool);
        subscribePeer = new SubscribePeer(cpool);

        //进、销、存系统
        //productSuPeer = new ProductSuPeer(cpool);
        //purchaseDetailManager = new PurchaseDetailPeer(cpool);
        //purchaseMasterManager = new PurchaseMasterPeer(cpool);
        //supplierSuManager = new SupplierSuPeer(cpool);
        //changeMasterManager = new ChangeMasterPeer(cpool);
        //changeDetailManager = new ChangeDetailPeer(cpool);
        //deliveryMasterManager = new DeliveryMasterPeer(cpool);
        //deliveryDetailManager = new DeliveryDetailPeer(cpool);

        //网站前台应用
        webCommentPeer = new webCommentPeer(cpool);
        webWordPeer = new LeaveWordPeer(cpool);
        webAuthPeer = new webAuthPeer(cpool);
        feedbackPeer = new FeedbackPeer(cpool);
        uregisterPeer = new UregisterPeer(cpool);
        //bbsPeer = new BBSPeer(cpool);
        //servicePeer = new ServicePeer(cpool);
        wenbaPeer = new wenbaManagerImpl(cpool);
        questionPeer = new questionManagerImpl(cpool);
        usercenterPeer = new UsercenterPeer(cpool);
        aopeer=new ArticleOnclickPeer(cpool);
        //for survey
        answerPeer = new AnswerPeer(cpool);
        definePeer = new DefinePeer(cpool);
        //信息采集系统配置程序
        basic_AttributesPeer = new Basic_AttributesPeer(cpool);
        matchUrl_SpecialCodePeer = new MatchUrl_SpecialCodePeer(cpool);

        selfDefinePeer = new SelfDefinePeer(cpool);
        joinpeer=new JoincompanyPeer(cpool);

        chinabuypeer=new ChinaBuyRegiterPeer(cpool);
        formpeer=new FormPeer(cpool);

        umn=new UserModelNewsPeer(cpool);
        companyinfoPeer = new CompanyinfoPeer(cpool);
        websiteinfoPeer = new WebsiteInfoPeer(cpool);

        //GwcasePeer = new GwcasePeer(cpool);

        //工作日管理
        workPeer = new WorkdayPeer(cpool);

        //业务预约
        //appointmentPeer = new AppointmentPeer(cpool);

        //多媒体
        multimediaPeer = new MultimediaPeer(cpool);
        //送货地址
        addressPeer = new AddressPeer(cpool);

        //会议室预订功能
        yuDingManager = new YuDingPeer(cpool);
        jiBenManager = new JiBenPeer(cpool);
        sheTuanManager = new SheTuanPeer(cpool);

        //石景山网上办事
        wsbsManager = new WsbsPeer(cpool);
        //房型信息管理
        csInfoManager = new CsInfoPeer(cpool);

    }

    public PoolServer getConnectionPool() {
        return cpool;
    }

    public IAuthManager getAuthManager() {
        return authPeer;
    }

    //石景山网上办事
    public IWsbsManager getWsbsManager(){
        return wsbsManager;
    }
    public IColumnUserManager getColumnUserManager() {
        return columnUserPeer;
    }

    public IUserManager getUserManager() {
        return userPeer;
    }

    public IGroupManager getGroupManager() {
        return groupPeer;
    }

    public IRightManager getRightManager() {
        return rightPeer;
    }

    public ISequenceManager getSequenceManager() {
        return sequencePeer;
    }

    public IColumnManager getColumnManager() {
        return columnPeer;
    }

    public IArticleManager getArticleManager() {
        return articlePeer;
    }

    public IColumnTree getColumnTree() {
        return columnTree;
    }

    public IPublishManager getPublishManager() {
        return publishPeer;
    }

    public IModelManager getModelManager() {
        return modelPeer;
    }

    public ITree getTreeManager() {
        return treePeer;
    }

    public ITagManager getTagManager() {
        return tagPeer;
    }

    public IFtpSetManager getFtpSetManager() {
        return ftpInfoPeer;
    }

    public ISiteInfoManager getSiteInfoManager() {
        return siteInfoPeer;
    }

    public EncodingUtil getEncodingUtil() {
        return encodingUtil;
    }

    public IRightsManager getRightsManager() {
        return rightsPeer;
    }

    public IAuditManager getAuditManager() {
        return auditPeer;
    }

    public IRegisterManager getRegisterManager() {
        return registerPeer;
    }

    public IPullContentManager getPullContentManager() {
        return pullContentPeer;
    }

    public IWebEditManager getWebEditManager() {
        return webEditPeer;
    }

    public IWordManager getWordManager() {
        return wordPeer;
    }

    public IJIMIManager getJIMIManager() {
        return jimiPeer;
    }

    public IExtendAttrManager getExtendAttrManager() {
        return extendAttrPeer;
    }

    public IWebtrendManager getWebtrendManager() {
        return webtrendPeer;
    }

    public IErrorManager getErrorManager() {
        return errorPeer;
    }

    public ILogManager getLogManager() {
        return logPeer;
    }

    public IMoveManager getMoveManager() {
        return movePeer;
    }

    public IOrderArticleListManager getOrderArticleListManager() {
        return orderArtListPeer;
    }

    public IArticleListManager getArticleListManager() {
        return articleListPeer;
    }

    public IMarkManager getMarkManager() {
        return markPeer;
    }

    public IViewFileManager getViewFileManager() {
        return viewFilePeer;
    }

    public IBUserManager getBUserManager() {
        return buserPeer;
    }

    public IOrderManager getOrderManager() {
        return orderPeer;
    }

    public IProductManager getProductManager() {
        return productPeer;
    }

    public IOtherManager getOtherManager() {
        return otherPeer;
    }

    public IMessageManager getMessageManager() {
        return messagePeer;
    }

    public IDeliverManager getDeliverManager() {
        return deliverPeer;
    }

    public IExcelManager getExcelManager() {
        return excelPeer;
    }

    public ActivityManager getActivityManager() {
        return activityManagerImpl;
    }

    public EventManager getEventManager() {
        return eventManagerImpl;
    }

    public IAddressListManager getAddressListManager() {
        return addresslistPeer;
    }

    public ITaskManager getTaskManager() {
        return taskPeer;
    }

    public IPublishQueueManager getPublishQueueManager() {
        return publishQueuePeer;
    }

    public ISpiderManager getSpiderManager() {
        return spiderPeer;
    }

    public IPicManager getPicManager() {
        return picPeer;
    }

    public IArticleKeywordManager getArticleKeywordManager() {
        return articlekeywordPeer;
    }

    public IUpdateManager getUpdateManager() {
        return updatePeer;
    }

    public IRefersManager getRefersManager() {
        return refersPeer;
    }

    public IStockManager getStockManager() {
        return stockPeer;
    }
    public ICompanyManager getCompanyManager() {
        return companyPeer;
    }
    public ISubscribeManager getSubscribeManager() {
        return subscribePeer;
    }

    public IProgramManager getProgramManager(){
        return programPeer;
    }

    public IPictureManager getPictureManager(){
        return picturePeer;
    }

    public IWebCommentManager getWebCommentManager(){
        return webCommentPeer;
    }

    public IWordManager getWebWordManager(){
        return webWordPeer;
    }

    public IWebAuthManager getWebAuthManager(){
        return webAuthPeer;
    }
    public IUregisterManager getUregisterManager(){
        return uregisterPeer;
    }
    public IFeedbackManager getFeedbackManager(){
        return feedbackPeer;
    }

    /*public IBBSManager getBBSManager()
    {
        return bbsPeer;
    }

    public IServiceManager getServiceManager()
    {
        return servicePeer;
    }*/

    public IWenbaManager getWenbaManager()
    {
        return wenbaPeer;
    }

    public IQuestionManager getQuestionManager()
    {
        return questionPeer;
    }

    public IUsercenterManager getUsercenterManager()
    {
        return usercenterPeer;
    }

    public IChartManager getChartManager()
    {
        return chartPeer;
    }

    //for survye
    public IAnswerManager getAnswerManager()
    {
        return answerPeer;
    }

    public IDefineManager getDefineManager(){
        return definePeer;
    }

    //进、销、存系统
    /*public IProductSuManager getProductSuManager(){
        return productSuPeer;
    }

    public IPurchaseDetailManager getPurchaseDetailManager(){
        return purchaseDetailManager;
    }

    public IPurchaseMasterManager getPurchaseMasterManager(){
        return purchaseMasterManager;
    }

    public ISupplierSuManager getSupplierSuManager(){
        return supplierSuManager;
    }

    public IChangeMasterManager getChangeMasterManager(){
        return changeMasterManager;
    }

    public IChangeDetailManager getChangeDetailManager() {
        return changeDetailManager;
    }

    public IDeliveryMasterManager getDeliveryMasterManager(){
        return deliveryMasterManager;
    }

    public IDeliveryDetailManager getDeliveryDetailManager(){
        return deliveryDetailManager;
    }*/

    //信息采集系统配置程序
    public IBasic_AttributesManager getBasic_AttributesManager() {
        return basic_AttributesPeer;
    }

    public IMatchUrl_SpecialCodeManager getMatchUrl_SpecialCodeManager() {
        return matchUrl_SpecialCodePeer;
    }

    //用户自定义表单
    public ISelfDefineManager getSelfDefineManager(){
        return selfDefinePeer;
    }

    public IUserFunctionManager getUserFunctionManager(){
        return userFunctionPeer;
    }

    public IJoincompanyManager getJoincompanyManager(){
        return joinpeer;
    }

    public IChinaBuyRegiterManager getChinaBuyRegiterManager(){
        return chinabuypeer;
    }
    public IArticleOnclickManager getArticleOnclickManager(){
        return aopeer;
    }
    public IFormManager getFormManager()
    {
        return formpeer;
    }

    public IUserModelNewsManager getUserModelNewsManager()
    {
        return umn;
    }

    public ICompanyinfoManager getCompanyinfoPeer() {
        return companyinfoPeer;
    }

    public IWebsiteManager getWebsiteInfoPeer() {
        return websiteinfoPeer;
    }

    //public IGWcaseManager getGwcasePeer(){
    //    return GwcasePeer;
    //}

    //工作日管理
    public IWorkdayManager getWorkdayPeer(){
        return workPeer;
    }

    //public IAppointmentManager getAppointmentManager()
    //{
    //    return appointmentPeer;
    //}

    public IMultimediaManager getMultimediaManager(){
        return multimediaPeer;
    }

    public IAddressManager getAddressManager(){
        return addressPeer;
    }

    //会议室预订系统
    public IYuDingManager getYuDingManager(){
        return yuDingManager;
    }
    public IJiBenManager getJiBenManager(){
        return jiBenManager;
    }
    public ISheTuanManager getSheTuanManager(){
        return sheTuanManager;
    }

    public ICsInfoManager getCsInfoManager(){
        return  csInfoManager;
    }

    public ISearchWordManager getSearchWordManager(){
        return  searchWordManager;
    }
}