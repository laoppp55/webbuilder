package com.bizwink.cms.server;

import com.bizwink.cms.bjhqfw.jiben.IJiBenManager;
import com.bizwink.cms.bjhqfw.shetuan.ISheTuanManager;
import com.bizwink.cms.bjhqfw.yuding.IYuDingManager;
import com.bizwink.cms.business.Order.IOrderManager;
import com.bizwink.cms.multimedia.IMultimediaManager;
import com.bizwink.cms.security.*;
//import com.bizwink.cms.toolkit.gwcase.*;
import com.bizwink.cms.sjswsbs.IWsbsManager;
import com.bizwink.cms.toolkit.csinfo.ICsInfoManager;
import com.bizwink.cms.toolkit.websites.*;
import com.bizwink.cms.toolkit.workday.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.news.*;
import com.bizwink.cms.publish.*;
import com.bizwink.cms.modelManager.*;
import com.bizwink.cms.tree.*;
import com.bizwink.cms.processTag.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.audit.*;
import com.bizwink.cms.register.*;
import com.bizwink.net.ftp.*;
import com.bizwink.cms.webedit.*;
import com.bizwink.cms.extendAttr.*;
import com.bizwink.cms.orderArticleListManager.*;
import com.bizwink.cms.articleListmanager.*;
import com.bizwink.cms.markManager.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.webapps.address.IAddressManager;
//import com.bizwink.webapps.appointment.IAppointmentManager;
import com.bizwink.webapps.userfunction.IUserFunctionManager;
import com.bizwink.webtrend.*;
import com.bizwink.error.*;
import com.bizwink.log.*;
import com.bizwink.cms.excel.*;
import com.bizwink.move.*;
import com.bizwink.program.*;
import com.bizwink.publishQueue.*;
import com.bizwink.stockinfo.*;
import com.charts.*;

//for business
import com.bizwink.cms.business.Users.*;
import com.bizwink.cms.business.Product.*;
import com.bizwink.cms.business.Other.*;
import com.bizwink.cms.business.Message.*;
import com.bizwink.cms.business.deliver.*;

//for toolkit
import com.bizwink.cms.toolkit.addresslist.*;
import com.bizwink.cms.toolkit.task.*;
import com.bizwink.cms.toolkit.counter.*;
import com.sinopec.stock.IStockManager;
import com.bizwink.cms.toolkit.company.ICompanyManager;
import com.bizwink.cms.toolkit.companyinfo.ICompanyinfoManager;
import com.bizwink.cms.toolkit.subscribe.ISubscribeManager;
import com.bizwink.cms.pic.IPicManager;
import com.bizwink.cms.refers.IRefersManager;
import com.bizwink.cms.selfDefine.ISelfDefineManager;
import com.bizwink.event.*;
import com.bizwink.update.IUpdateManager;
import com.bizwink.picture.IPictureManager;
import com.bizwink.cms.toolkit.searchword.ISearchWordManager;

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
import com.bizwink.webapps.comment.IWebCommentManager;
import com.bizwink.webapps.leaveword.IWordManager;
import com.bizwink.webapps.security.IWebAuthManager;
import com.bizwink.webapps.feedback.IFeedbackManager;
import com.bizwink.webapps.register.*;
import com.bizwink.webapps.survey.answer.IAnswerManager;
import com.bizwink.webapps.survey.define.IDefineManager;
import com.bizwink.webapps.articleonclick.IArticleOnclickManager;
import com.bizwink.joincompany.IJoincompanyManager;
//import com.bizwink.bbs.bbs.*;
import com.bizwink.bbs.service.*;
import com.bizwink.wenba.*;
import com.bizwink.webapps.questions.*;
import com.bizwink.webapps.usercenter.*;
import com.chinabuyregister.IChinaBuyRegiterManager;
import com.xml.IFormManager;
import com.usermodelnews.IUserModelNewsManager;
import com.bizwink.collectionmgr.*;


public interface IFactory {
    IUserManager                 getUserManager();
    IGroupManager                getGroupManager();
    IRightManager                getRightManager();
    IColumnUserManager           getColumnUserManager();
    IAuthManager                 getAuthManager();
    ISequenceManager             getSequenceManager();
    IColumnManager               getColumnManager();
    IArticleManager              getArticleManager();
    IColumnTree                  getColumnTree();
    IPublishManager              getPublishManager();
    IModelManager                getModelManager();
    ITree                        getTreeManager();
    ITagManager                  getTagManager();
    IFtpSetManager               getFtpSetManager();
    EncodingUtil                 getEncodingUtil();
    IRightsManager               getRightsManager();
    IAuditManager                getAuditManager();
    ISiteInfoManager             getSiteInfoManager();
    IRegisterManager             getRegisterManager();
    IPullContentManager          getPullContentManager();
    IWebEditManager              getWebEditManager();
    IWordManager                 getWordManager();
    IJIMIManager                 getJIMIManager();
    ILogManager                  getLogManager();
    IWebtrendManager             getWebtrendManager();
    IErrorManager                getErrorManager();
    IMoveManager                 getMoveManager();
    IExcelManager                getExcelManager();
    IArticleListManager          getArticleListManager();
    IMarkManager                 getMarkManager();
    IViewFileManager             getViewFileManager();
    IExtendAttrManager           getExtendAttrManager();
    IOrderArticleListManager     getOrderArticleListManager();
    ActivityManager              getActivityManager();
    EventManager                 getEventManager();
    ISpiderManager               getSpiderManager();

    //for toolkit
    ITaskManager                 getTaskManager();
    IAddressListManager          getAddressListManager();
    IChartManager                getChartManager();
    ICsInfoManager               getCsInfoManager();
    ISearchWordManager           getSearchWordManager();

    //石景山网上办事
    IWsbsManager getWsbsManager();


    //for business
    IBUserManager                getBUserManager();
    IOrderManager                getOrderManager();
    IProductManager              getProductManager();
    IOtherManager                getOtherManager();
    IMessageManager              getMessageManager();
    IDeliverManager              getDeliverManager();
    IPicManager                  getPicManager();
    //Add by Eric 2007-8-12 for define articles' keywords replace
    IArticleKeywordManager       getArticleKeywordManager();

    //Add by Eric 2007-8-28 for Update old version cms data
    IUpdateManager               getUpdateManager();

    //Add by Eric 2008-2-19 for Refers article
    IRefersManager               getRefersManager();
    IPublishQueueManager         getPublishQueueManager();

    //for sinopec
    IStockManager                getStockManager();
    ICompanyManager              getCompanyManager();
    ISubscribeManager            getSubscribeManager();
    IProgramManager              getProgramManager();
    IPictureManager              getPictureManager();

    //进、销、存系统
    //IProductSuManager            getProductSuManager();
    //IPurchaseDetailManager       getPurchaseDetailManager();
    //IPurchaseMasterManager       getPurchaseMasterManager();
    //ISupplierSuManager           getSupplierSuManager();
    //IChangeMasterManager         getChangeMasterManager();
    //IChangeDetailManager         getChangeDetailManager();
    //IDeliveryMasterManager       getDeliveryMasterManager();
    //IDeliveryDetailManager       getDeliveryDetailManager();


    //网站前台程序模块
    IWebCommentManager               getWebCommentManager();        //文章评论
    IWordManager                     getWebWordManager();           //网站留言
    IWebAuthManager                  getWebAuthManager();           //用户登录和权限管理
    IFeedbackManager                 getFeedbackManager();
    IUregisterManager                getUregisterManager();
    //IBBSManager                      getBBSManager();
    IWenbaManager                    getWenbaManager();
    IQuestionManager                 getQuestionManager();
    //IServiceManager                  getServiceManager();
    IArticleOnclickManager           getArticleOnclickManager();
    //for survey
    IAnswerManager                   getAnswerManager();
    IDefineManager                   getDefineManager();

    //信息采集系统配置程序
    IBasic_AttributesManager         getBasic_AttributesManager();
    IMatchUrl_SpecialCodeManager     getMatchUrl_SpecialCodeManager();

    //用户自定义表单
    ISelfDefineManager               getSelfDefineManager();
    IJoincompanyManager              getJoincompanyManager();
    IChinaBuyRegiterManager          getChinaBuyRegiterManager();
    IFormManager                     getFormManager();
    IUserModelNewsManager            getUserModelNewsManager();
    IUserFunctionManager             getUserFunctionManager();
    IUsercenterManager               getUsercenterManager();

    ICompanyinfoManager              getCompanyinfoPeer();
    IWebsiteManager                  getWebsiteInfoPeer();
    //IGWcaseManager                   getGwcasePeer();
    //工作日管理
    IWorkdayManager                  getWorkdayPeer();
    //业务预约
    //IAppointmentManager              getAppointmentManager();
    //多媒体
    IMultimediaManager               getMultimediaManager();
    //送货地址
    IAddressManager                  getAddressManager();

    //华侨服务
    IYuDingManager getYuDingManager();
    IJiBenManager getJiBenManager();
    ISheTuanManager getSheTuanManager();
}
