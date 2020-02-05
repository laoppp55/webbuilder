package com.bizwink.cms.util;

import java.util.*;
import java.sql.*;

import com.bizwink.cms.server.*;

public class SequencePeer implements ISequenceManager {
    private static final String NEXT_SITE_ID = "SELECT max(SiteID) FROM TBL_SiteInfo";
    private static final String NEXT_SITEIPINFO_ID = "SELECT max(ID) FROM TBL_SiteIPInfo";
    private static final String NEXT_GROUP_ID = "SELECT max(GroupID) FROM TBL_Group";
    private static final String NEXT_COLUMN_RULE_ID = "SELECT max(ID) FROM TBL_COLUMN_AUDITING_RULES";
    private static final String NEXT_AUDIT_INFO_ID = "SELECT max(ID) FROM TBL_ARTICLE_AUDITING_INFO";
    private static final String NEXT_LOG_ID = "SELECT max(ID) FROM TBL_LOG";
    private static final String NEXT_VF_ID = "SELECT max(ID) FROM TBL_ViewFile";
    private static final String NEXT_ARTICLE_ID = "select max(ID) from TBL_Article";
    private static final String NEXT_ARTICLE_EXTENDATTR_ID = "select max(ID) from TBL_Article_ExtendAttr";
    private static final String NEXT_TEMPLATE_ID = "select max(ID) from TBL_Template";
    private static final String NEXT_MARK_ID = "select max(ID) from TBL_Mark";
    private static final String NEXT_COLUMN_ID = "select max(ID) from TBL_Column";
    private static final String NEXT_PUBLISH_QUEUE_ID = "select max(ID) from TBL_new_Publish_Queue";
    private static final String NEXT_ARTICLE_ATTECHMENT_ID = "select max(ID) from TBL_RelatedArtIDs";
    private static final String NEXT_PIC_ID = "select max(ID) from tbl_picture";
    private static final String NEXT_ARTICLE_KEYWORD_ID = "select max(ID) from tbl_article_keyword";
    private static final String NEXT_REFERS_ARTICLE_ID = "select max(ID) from tbl_refers_article";
    private static final String NEXT_REFERS_COLUMN_ID = "select max(ID) from tbl_refers_column";
    private static final String NEXT_TYPE_ID = "select max(ID) from tbl_type";
    private static final String NEXT_TYPE_ARTICLE_ID = "select max(ID) from tbl_type_article";
    private static final String NEXT_TBL_LEAVEWORD_ID = "select max(ID) from tbl_leaveword";
    private static final String NEXT_TURN_PIC_ID = "select max(ID) from tbl_turnpic";
    private static final String NEXT_TBL_COMMENT_ID = "select max(ID) from TBL_COMMENT";
    private static final String NEXT_TBL_USERREG_ID = "select max(ID) from TBL_USERINFO";
    private static final String NEXT_TBL_NAVIGATOR_ID = "select max(ID) from tbl_navigator";
    //for survey
    private static final String NEXT_SU_SURVEY_ID = "select max(ID) from SU_SURVEY";
    private static final String NEXT_SU_DQUESTION_ID = "select max(ID) from SU_DQUESTION";
    private static final String NEXT_SU_DANSWER_ID = "select max(ID) from SU_DANSWER";
    private static final String NEXT_SU_ANSWER_ID = "select max(ID) from SU_ANSWER";
    private static final String NEXT_SU_DEFINEUSERINFO_ID = "select max(ID) from SU_DEFINEUSERINFO";
    //for fee and sendway
    private static final String NEXT_FEE_ID = "select max(ID) from TBL_FEE";
    private static final String NEXT_SENDWAY_ID = "select max(ID) from TBL_SENDWAY";
    //for address info
    private static final String NEXT_TBL_ADDRESSINFO_ID = "select max(ID) from TBL_ADDRESSINFO";
    //for order_detail
    private static final String NEXT_TBL_ORDERS_DETAIL_ID = "select max(ID) from TBL_ORDERS_DETAIL";

    //用户自定义表单
    private static final String NEXT_SelfDefine_ID = "select max(ID) from TBL_ORDERS_DETAIL";

    //购物卷
    private static final String NEXT_TBL_SHOPPINGCARD_ID = "select max(ID) from TBL_SHOPPINGCARD";
    //用户角色
    private static final String NEXT_TBL_MEMBER_ROLES_ID = "select max(ID) from TBL_MEMBER_ROLES";

    private static final String NEXT_LOGIN_STATUS_ID = "select max(ID) from bbs_online";

    //业务预定
    //private static final String NEXT_TBL_APPOINTMENT_ID = "select max(ID) from tbl_Appointment";
    //private static final String NEXT_TBL_USERAPPOINTMENT_ID = "select max(ID) from tbl_userappointment";
    //对讲机
    //private static final String NEXT_TBL_ZILIAO_ID = "select max(ID) from tbl_diantaiziliao";
    //private static final String NEXT_TBL_DUIJIANGJI_ID = "select max(DJJID) from tbl_zhuduijiangji";

    //多媒体
    private static final String NEXT_TBL_MULTIMEDIA_ID = "select max(ID) from tbl_multimedia";
    //接口参数
    private static final String NEXT_PAYWAYINTERFACE_ID = "SELECT max(id) FROM TBL_PayWayInterface";
    //invoice info
    private static final String NEXT_INVOICEINFO_ID = "SELECT max(id) FROM tbl_invoiceinfo";
    private static final String NEXT_INVOICECONTENT_ID = "SELECT max(id) FROM tbl_invoicecontent";

    private static final String NEXT_PVDETAIL_ID = "SELECT max(urlid) FROM tlog_detail";

    private static final String NEXT_WENBA_COLUMN_ID = "SELECT max(id) FROM fawu_wenti_column";

    private HashMap uniqueIDCounters;
    PoolServer cpool;

    public static ISequenceManager getInstance() {
        return CmsServer.getInstance().getFactory().getSequenceManager();
    }

    public SequencePeer(PoolServer cpool) {
        this.cpool = cpool;
        uniqueIDCounters = new HashMap();
        uniqueIDCounters.put("Site", new Counter(getNextDbID(NEXT_SITE_ID)));
        uniqueIDCounters.put("SiteIPInfo", new Counter(getNextDbID(NEXT_SITEIPINFO_ID)));
        uniqueIDCounters.put("Group", new Counter(getNextDbID(NEXT_GROUP_ID)));
        uniqueIDCounters.put("ColumnAuditRule", new Counter(getNextDbID(NEXT_COLUMN_RULE_ID)));
        uniqueIDCounters.put("ArticleAuditInfo", new Counter(getNextDbID(NEXT_AUDIT_INFO_ID)));
        uniqueIDCounters.put("Log", new Counter(getNextDbID(NEXT_LOG_ID)));
        uniqueIDCounters.put("ViewFile", new Counter(getNextDbID(NEXT_VF_ID)));
        uniqueIDCounters.put("Article", new Counter(getNextDbID(NEXT_ARTICLE_ID)));
        uniqueIDCounters.put("ArticleExtendAttr", new Counter(getNextDbID(NEXT_ARTICLE_EXTENDATTR_ID)));
        uniqueIDCounters.put("Template", new Counter(getNextDbID(NEXT_TEMPLATE_ID)));
        uniqueIDCounters.put("Mark", new Counter(getNextDbID(NEXT_MARK_ID)));
        uniqueIDCounters.put("Column", new Counter(getNextDbID(NEXT_COLUMN_ID)));
        uniqueIDCounters.put("Pic", new Counter(getNextDbID(NEXT_PIC_ID)));
        uniqueIDCounters.put("Keyword", new Counter(getNextDbID(NEXT_ARTICLE_KEYWORD_ID)));
        uniqueIDCounters.put("Attechment", new Counter(getNextDbID(NEXT_ARTICLE_ATTECHMENT_ID)));
        if (cpool.getPublishWay() == 1)
            uniqueIDCounters.put("PublishQueue", new Counter(getNextDbID(NEXT_PUBLISH_QUEUE_ID)));
        uniqueIDCounters.put("RefersArticle", new Counter(getNextDbID(NEXT_REFERS_ARTICLE_ID)));
        uniqueIDCounters.put("RefersColumn", new Counter(getNextDbID(NEXT_REFERS_COLUMN_ID)));
        //uniqueIDCounters.put("Type", new Counter(getNextDbID(NEXT_TYPE_ID)));
        //uniqueIDCounters.put("TypeArticle", new Counter(getNextDbID(NEXT_TYPE_ARTICLE_ID)));
        uniqueIDCounters.put("LeaveWord", new Counter(getNextDbID(NEXT_TBL_LEAVEWORD_ID)));
        uniqueIDCounters.put("TurnPic", new Counter(getNextDbID(NEXT_TURN_PIC_ID)));
        uniqueIDCounters.put("Comment", new Counter(getNextDbID(NEXT_TBL_COMMENT_ID)));
        //uniqueIDCounters.put("Userreg", new Counter(getNextDbID(NEXT_TBL_USERREG_ID)));
        //uniqueIDCounters.put("Navigator", new Counter(getNextDbID(NEXT_TBL_NAVIGATOR_ID)));
        //for survey
        uniqueIDCounters.put("Survey", new Counter(getNextDbID(NEXT_SU_SURVEY_ID)));
        uniqueIDCounters.put("Dquestion", new Counter(getNextDbID(NEXT_SU_DQUESTION_ID)));
        uniqueIDCounters.put("Danswer", new Counter(getNextDbID(NEXT_SU_DANSWER_ID)));
        uniqueIDCounters.put("Answer", new Counter(getNextDbID(NEXT_SU_ANSWER_ID)));
        uniqueIDCounters.put("Defineuserinfo", new Counter(getNextDbID(NEXT_SU_DEFINEUSERINFO_ID)));

        //for fee and sendway
        //uniqueIDCounters.put("Fee", new Counter(getNextDbID(NEXT_FEE_ID)));
        //uniqueIDCounters.put("SendWay", new Counter(getNextDbID(NEXT_SENDWAY_ID)));
        //for addressinfo
        uniqueIDCounters.put("AddressInfo", new Counter(getNextDbID(NEXT_TBL_ADDRESSINFO_ID)));
        //uniqueIDCounters.put("OrderDetail", new Counter(getNextDbID(NEXT_TBL_ORDERS_DETAIL_ID)));
        //购物卷
        //uniqueIDCounters.put("ShoppingCard", new Counter(getNextDbID(NEXT_TBL_SHOPPINGCARD_ID)));
        //用户角色
        uniqueIDCounters.put("Roles", new Counter(getNextDbID(NEXT_TBL_MEMBER_ROLES_ID)));

        uniqueIDCounters.put("Userreg", new Counter(getNextDbID(NEXT_LOGIN_STATUS_ID)));

        //多媒体
        uniqueIDCounters.put("Multimedia", new Counter(getNextDbID(NEXT_TBL_MULTIMEDIA_ID)));
        //接口参数
        uniqueIDCounters.put("PayWayInterface", new Counter(getNextDbID(NEXT_PAYWAYINTERFACE_ID)));
        //invoice info
        //uniqueIDCounters.put("InvoiceInfo", new Counter(getNextDbID(NEXT_INVOICEINFO_ID)));
        //uniqueIDCounters.put("InoviceContent", new Counter(getNextDbID(NEXT_INVOICECONTENT_ID)));

        //访问量统计分析
        uniqueIDCounters.put("PVDetail", new Counter(getNextDbID(NEXT_PVDETAIL_ID)));

        //问吧
        uniqueIDCounters.put("wenba", new Counter(getNextDbID(NEXT_WENBA_COLUMN_ID)));
    }

    private static final String SEQ_NEXT_ARTICLE_ID = "select tbl_article_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_ARTICLE_EXTENDATTR_ID = "select tbl_article_extendattr_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_ARTICLE_ATTECHMENT_ID = "select tbl_article_attechment_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_SITE_ID = "select tbl_site_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_SITEIP_ID = "select tbl_siteip_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TEMPLATE_ID = "select tbl_template_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_MARK_ID = "select tbl_mark_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_COLUMN_ID = "select tbl_column_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_GROUP_ID = "select tbl_group_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_PUBLISH_QUEUE_ID = "select tbl_new_publish_queue_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_LOG_ID = "select tbl_log_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_ARTICLE_KEYWORD_ID = "select tbl_article_keyword_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_PIC_ID = "select tbl_pic_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_VIEWFILE_ID = "select tbl_viewfile_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TYPE_ID = "select tbl_type_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TYPE_ARTICLE_ID = "select tbl_type_article_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_REFERS_ARTICLE_ID = "select tbl_refers_article_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_REFERS_COLUMN_ID = "select tbl_refers_column_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_LEAVEWORD_ID = "select tbl_leaveword_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_MESSAGE_ID = "select message_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_PROGRAM_ID = "select tbl_program_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TURN_PIC_ID = "select tbl_turnpic_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_COMMENT_ID = "select tbl_COMMENT_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_USERROLE_ID = "select tbl_userrole_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_USERREG_ID = "select tbl_userreg_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_NAVIGATOR_ID = "select navigator_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_ACTIVITY_ID = "select tbl_activity_id.NEXTVAL from dual";

    private static final String SEQ_NEXT_MEMBERS_ID="select tbl_members_id.NEXTVAL from dual";
    //进、销、存系统
    //private static final String SEQ_NEXT_SUPPLIER_ID = "select tbl_supplier_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_PRODUCT_ID = "select tbl_product_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_PURCHASE_ID = "select tbl_purchase_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_PURCHASEDETAIL_ID = "select tbl_purchasedetail_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_DELIVERYDETAIL_ID = "select tbl_deliverydetail_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_DELIVERYMASTER_ID = "select tbl_deliverymaster_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_CHANGEDETAIL_ID = "select tbl_changedetail_id.NEXTVAL from dual";
    //private static final String SEQ_NEXT_CHANGEMASTER_ID = "select tbl_changemaster_id.NEXTVAL from dual";
    //for survey
    private static final String SEQ_SU_SURVEY_ID = "select SU_SURVEY_id.NEXTVAL from dual";
    private static final String SEQ_SU_DQUESTION_ID = "select SU_DQUESTION_id.NEXTVAL from dual";
    private static final String SEQ_SU_DANSWER_ID = "select SU_DANSWER_id.NEXTVAL from dual";
    private static final String SEQ_SU_ANSWER_ID = "select SU_ANSWER_id.NEXTVAL from dual";
    private static final String SEQ_SU_DEFINEUSERINFO_ID = "select SU_DEFINEUSERINFO_ID.NEXTVAL from dual";
    //feo fee and send way
    private static final String SEQ_TBL_FEE_ID = "select TBL_FEE_ID.NEXTVAL from dual";
    private static final String SEQ_TBL_SENDWAY_ID = "select TBL_SENDWAY_ID.NEXTVAL from dual";
    //for address
    private static final String SEQ_TBL_ADDRESSINFO_ID = "select TBL_ADDRESSINFO_ID.NEXTVAL from dual";
    //for order detail
    private static final String SEQ_TBL_ORDERS_DETAIL_ID = "select TBL_ORDERS_DETAIL_ID.NEXTVAL from dual";
    private static final String SEQ_SELFDEFINE_ID = "select self_define_ID.NEXTVAL from dual";
    //组织架构序列号
    private static final String SEQ_Joincomapy_ID = "select JOINCOMPANY_ID.NEXTVAL from dual";
    //公司寻列号
    private static final String SEQ_COMPANYINFO_ID = "select COMPANYINFO_ID.NEXTVAL from dual";
    //部门寻列号
    private static final String SEQ_DEPARTMENT_ID = "select DEPARTMENT_ID.NEXTVAL from dual";
    //购物券
    private static final String SEQ_TBL_SHOPPINGCARD_ID = "select TBL_SHOPPINGCARD_ID.NEXTVAL from dual";
    private static final String SEQ_TBL_ARTICLE_ARTICLELIST = "select TBL_ARTICLE_ARTICLELIST_ID.NEXTVAL from dual";
    //用户角色
    private static final String SEQ_TBL_MEMBER_ROLES_ID = "select TBL_MEMBER_ROLES_ID.NEXTVAL from dual";
    //多媒体
    private static final String SEQ_TBL_MULTIMEDIA_ID = "select tbl_multimedia_id.NEXTVAL from dual";
    //接口参数
    private static final String SEQ_TBL_PAYWAYINTERFACE_ID = "select tbl_paywayinterface_id.NEXTVAL from dual";
    //invoice info
    private static final String SEQ_TBL_INVOICEINFO_ID = "select tbl_invoiceinfo_id.NEXTVAL from dual";
    private static final String SEQ_TBL_INVOICECONTENT_ID = "select tbl_invoicecontent_id.NEXTVAL from dual";
    //授权管理的资源，目前主要是指管理的留言板
    private static final String SEQ_AUTHRIZED_RESOUCE_ID = "select tbl_resouce_id.NEXTVAL from dual";
    private static final String SEQ_MEETROOM_ID = "select tbl_jbxinxi_id.NEXTVAL from dual";
    private static final String SEQ_YUDING_MEETROOM_ID = "select tbl_ydxinxi_id.NEXTVAL from dual";

    //LOG分析系统
    private static final String SEQ_PVDETAIL_ID = "select pv_detail_id.NEXTVAL from dual";

    public final synchronized int getSequenceNum(String seqFlag) {
        int nextID = 1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            if (seqFlag.equals("Article"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ARTICLE_ID);
            else if (seqFlag.equals("ArticleExtendAttr"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ARTICLE_EXTENDATTR_ID);
            else if (seqFlag.equals("Attechment"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ARTICLE_ATTECHMENT_ID);
            else if (seqFlag.equals("Site"))
                pstmt = conn.prepareStatement(SEQ_NEXT_SITE_ID);
            else if (seqFlag.equals("SiteIPInfo"))
                pstmt = conn.prepareStatement(SEQ_NEXT_SITEIP_ID);
            else if (seqFlag.equals("Template"))
                pstmt = conn.prepareStatement(SEQ_NEXT_TEMPLATE_ID);
            else if (seqFlag.equals("Mark"))
                pstmt = conn.prepareStatement(SEQ_NEXT_MARK_ID);
            else if (seqFlag.equals("Column"))
                pstmt = conn.prepareStatement(SEQ_NEXT_COLUMN_ID);
            else if (seqFlag.equals("PublishQueue") && cpool.getPublishWay() == 1)
                pstmt = conn.prepareStatement(SEQ_NEXT_PUBLISH_QUEUE_ID);
            else if (seqFlag.equals("Group"))
                pstmt = conn.prepareStatement(SEQ_NEXT_GROUP_ID);
            else if (seqFlag.equals("Log"))
                pstmt = conn.prepareStatement(SEQ_NEXT_LOG_ID);
            else if (seqFlag.equals("Activity"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ACTIVITY_ID);
            else if (seqFlag.equals("Keyword"))
                pstmt = conn.prepareStatement(SEQ_NEXT_ARTICLE_KEYWORD_ID);
            else if (seqFlag.equals("Pic"))
                pstmt = conn.prepareStatement(SEQ_NEXT_PIC_ID);
            else if (seqFlag.equals("Type"))
                pstmt = conn.prepareStatement(SEQ_NEXT_TYPE_ID);
            else if (seqFlag.equals("TypeArticle"))
                pstmt = conn.prepareStatement(SEQ_NEXT_TYPE_ARTICLE_ID);
            else if (seqFlag.equals("ViewFile"))
                pstmt = conn.prepareStatement(SEQ_NEXT_VIEWFILE_ID);
            else if (seqFlag.equals("RefersArticle"))
                pstmt = conn.prepareStatement(SEQ_NEXT_REFERS_ARTICLE_ID);
            else if (seqFlag.equals("RefersColumn"))
                pstmt = conn.prepareStatement(SEQ_NEXT_REFERS_COLUMN_ID);
            else if (seqFlag.equals("LeaveWord"))
                pstmt = conn.prepareStatement(SEQ_NEXT_LEAVEWORD_ID);
            else if (seqFlag.equals("Message"))
                pstmt = conn.prepareStatement(SEQ_NEXT_MESSAGE_ID);
            else if (seqFlag.equalsIgnoreCase("ProgramLibrary"))
                pstmt = conn.prepareStatement(SEQ_NEXT_PROGRAM_ID);
            else if (seqFlag.equals("TurnPic"))
                pstmt = conn.prepareStatement(SEQ_NEXT_TURN_PIC_ID);
            else if (seqFlag.equals("Comment"))
                pstmt = conn.prepareStatement(SEQ_NEXT_COMMENT_ID);
            else if (seqFlag.equals("Userreg"))
                pstmt = conn.prepareStatement(SEQ_NEXT_USERREG_ID);
            else if (seqFlag.equals("Role"))
                pstmt = conn.prepareStatement(SEQ_NEXT_USERROLE_ID);
            else if (seqFlag.equals("Navigator"))
                pstmt = conn.prepareStatement(SEQ_NEXT_NAVIGATOR_ID);
            else if (seqFlag.equals("Survey"))
                pstmt = conn.prepareStatement(SEQ_SU_SURVEY_ID);
            else if (seqFlag.equals("Defineuserinfo"))
                pstmt = conn.prepareStatement(SEQ_SU_DEFINEUSERINFO_ID);
            else if (seqFlag.equals("Dquestion"))
                pstmt = conn.prepareStatement(SEQ_SU_DQUESTION_ID);
            else if (seqFlag.equals("Danswer"))
                pstmt = conn.prepareStatement(SEQ_SU_DANSWER_ID);
            else if (seqFlag.equals("Answer"))
                pstmt = conn.prepareStatement(SEQ_SU_ANSWER_ID);
            else if (seqFlag.equals("Fee"))
                pstmt = conn.prepareStatement(SEQ_TBL_FEE_ID);
            else if (seqFlag.equals("SendWay"))
                pstmt = conn.prepareStatement(SEQ_TBL_SENDWAY_ID);
            else if (seqFlag.equals("AddressInfo"))
                pstmt = conn.prepareStatement(SEQ_TBL_ADDRESSINFO_ID);
            else if (seqFlag.equals("OrderDetail"))
                pstmt = conn.prepareStatement(SEQ_TBL_ORDERS_DETAIL_ID);
            else if (seqFlag.equals("SelfDefine"))
                pstmt = conn.prepareStatement(SEQ_SELFDEFINE_ID);
                /*else if (seqFlag.equals("PurchaseDetail"))
              pstmt = conn.prepareStatement(SEQ_NEXT_PURCHASEDETAIL_ID);
          else if (seqFlag.equals("DeliveryDetail"))
              pstmt = conn.prepareStatement(SEQ_NEXT_DELIVERYDETAIL_ID);
          else if (seqFlag.equals("DeliveryMaster"))
              pstmt = conn.prepareStatement(SEQ_NEXT_DELIVERYMASTER_ID);
          else if (seqFlag.equals("ChangeDetail"))
              pstmt = conn.prepareStatement(SEQ_NEXT_CHANGEDETAIL_ID);
          else if (seqFlag.equals("PurchaseMaster"))
              pstmt = conn.prepareStatement(SEQ_NEXT_PURCHASE_ID);
          else if (seqFlag.equals("ProductSu"))
              pstmt = conn.prepareStatement(SEQ_NEXT_PRODUCT_ID);
          else if (seqFlag.equals("ChangeMaster"))
              pstmt = conn.prepareStatement(SEQ_NEXT_CHANGEMASTER_ID);
          else if (seqFlag.equals("SupplierSu"))
              pstmt = conn.prepareStatement(SEQ_NEXT_SUPPLIER_ID);*/
            else if (seqFlag.equals("joincompany"))
                pstmt = conn.prepareStatement(SEQ_Joincomapy_ID);
            else if (seqFlag.equals("CompanyInfo"))
                pstmt = conn.prepareStatement(SEQ_COMPANYINFO_ID);
            else if (seqFlag.equals("Department"))
                pstmt = conn.prepareStatement(SEQ_DEPARTMENT_ID);
                //购物券
            else if (seqFlag.equals("ShoppingCard"))
                pstmt = conn.prepareStatement(SEQ_TBL_SHOPPINGCARD_ID);
            else if (seqFlag.equals("tblarticlelist"))
                pstmt = conn.prepareStatement(SEQ_TBL_ARTICLE_ARTICLELIST);
            else if (seqFlag.equals("Roles"))
                pstmt = conn.prepareStatement(SEQ_TBL_MEMBER_ROLES_ID);
                /*else if(seqFlag.equals("DianTaiZiLiao"))
              pstmt = conn.prepareStatement(SEQ_NEXT_ZILIAO_ID);
          else if(seqFlag.equals("DuiJiangJi"))
              pstmt = conn.prepareStatement(SEQ_NEXT_DUIJIANGJI_ID);
          else if (seqFlag.equals("Appointment"))
              pstmt = conn.prepareStatement(SEQ_TBL_APPOINTMENT_ID);
          else if (seqFlag.equals("UserAppointment"))
              pstmt = conn.prepareStatement(SEQ_TBL_USERAPPOINTMENT_ID);*/
            else if (seqFlag.equals("Multimedia"))
                pstmt = conn.prepareStatement(SEQ_TBL_MULTIMEDIA_ID);
            else if (seqFlag.equals("PayWayInterface"))
                pstmt = conn.prepareStatement(SEQ_TBL_PAYWAYINTERFACE_ID);
            else if (seqFlag.equals("InvoiceInfo"))
                pstmt = conn.prepareStatement(SEQ_TBL_INVOICEINFO_ID);
            else if (seqFlag.equals("InvoiceContent"))
                pstmt = conn.prepareStatement(SEQ_TBL_INVOICECONTENT_ID);
            else if (seqFlag.equals("AuthrizedResouce"))
                pstmt = conn.prepareStatement(SEQ_AUTHRIZED_RESOUCE_ID);
            else if (seqFlag.equals("Meetroom"))
                pstmt = conn.prepareStatement(SEQ_MEETROOM_ID);
            else if (seqFlag.equals("Yuding"))
                pstmt = conn.prepareStatement(SEQ_YUDING_MEETROOM_ID);
            else if (seqFlag.equals("PVDetail"))
                pstmt = conn.prepareStatement(SEQ_PVDETAIL_ID);
            else if (seqFlag.equals("SJSWSBSSEQ")) {
                pstmt = conn.prepareStatement("select SJS_WSBS_SEQ.NEXTVAL from dual");
            }
            else if(seqFlag.equals("MEMBERSID")){
                pstmt = conn.prepareStatement(SEQ_NEXT_MEMBERS_ID);
            }


            rs = pstmt.executeQuery();
            if (rs.next()) nextID = rs.getInt(1);
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return nextID;
    }

    //从流水号表中获取当前的流水号
    public final synchronized int getLSH_Num() {
        int maxID = 0,nextID = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String s_ct = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            Timestamp c = new Timestamp(System.currentTimeMillis());
            s_ct = c.toString();
            int posi = s_ct.indexOf(" ");
            s_ct = s_ct.substring(0,posi).trim();
            if (cpool.getType().equals("oracle"))
                pstmt = conn.prepareStatement("select max(lsh) from tbl_lshbydate where cdate=to_date(?,'yyyy-MM-dd')");
            else if (cpool.getType().equals("mssql"))
                pstmt = conn.prepareStatement("select max(lsh) from tbl_lshbydate where cdate=?");
            else
                pstmt = conn.prepareStatement("select max(lsh) from tbl_lshbydate where cdate=?");
            pstmt.setString(1,s_ct);
            rs = pstmt.executeQuery();
            if (rs.next()) maxID = rs.getInt(1);
            rs.close();
            pstmt.close();

            nextID = maxID + 1;
            if (maxID == 0) {
                if (cpool.getType().equals("oracle"))
                    pstmt = conn.prepareStatement("insert into tbl_lshbydate(cdate,lsh) values(to_date(?,'yyyy-MM-dd'),?)");
                else if (cpool.getType().equals("mssql"))
                    pstmt = conn.prepareStatement("insert into tbl_lshbydate(cdate,lsh) values(?,?)");
                else
                    pstmt = conn.prepareStatement("insert into tbl_lshbydate(cdate,lsh) values(?,?)");
                pstmt.setString(1,s_ct);
                pstmt.setInt(2,nextID);
                pstmt.executeUpdate();
                pstmt.close();
            } else {
                if (cpool.getType().equals("oracle"))
                    pstmt = conn.prepareStatement("update tbl_lshbydate set lsh=? where cdate=to_date('" + s_ct + "','yyyy-MM-dd')");
                else if (cpool.getType().equals("mssql"))
                    pstmt = conn.prepareStatement("update tbl_lshbydate set lsh=? where cdate=" + s_ct);
                else
                    pstmt = conn.prepareStatement("update tbl_lshbydate set lsh=? where cdate=" + s_ct);
                pstmt.setInt(1,nextID);
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return nextID;
    }


    //生成定单号
    public final synchronized long generateOrderID() {
        long oredertime = System.currentTimeMillis();
        String str = String.valueOf(oredertime);
        str = str.substring(6, 13);
        if (str.length() < 7) {
            for (int i = 0; i < (7 - str.length()); i++) {
                str = "1" + str;
            }
        }

        String random = String.valueOf(Math.random());
        random = random.substring(random.indexOf(".") + 1, random.indexOf(".") + 4);
        str = str + random;
        if (str != null) {
            if (str.length() < 10) {
                for (int i = 0; i < (10 - str.length()); i++) {
                    str = "1" + str;
                }
            }
        }
        long orderid = Long.parseLong(str);
        str = String.valueOf(orderid);
        if (str.length() < 10) {
            for (int i = 0; i < (10 - str.length()); i++) {
                str = "1" + str;
            }
        }

        orderid = Long.parseLong(str);
        return orderid;
    }


    public final synchronized int nextID(String counterName) {
        Counter counter = (Counter) uniqueIDCounters.get(counterName);
        return counter.next();
    }

    private int getNextDbID(String query) {
        int currentID = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            if (rs.next()) currentID = rs.getInt(1);
            rs.close();
            pstmt.close();
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
        finally {
            try {
                cpool.freeConnection(conn);
            }
            catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (currentID < 0) currentID = 0;
        return currentID;
    }

    private final class Counter {
        private int count;

        public Counter(int currentCount) {
            count = currentCount;
        }

        public final synchronized int next() {
            return (++count);
        }
    }
}
