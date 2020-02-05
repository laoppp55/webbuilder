package com.bizwink.cms.audit;

import java.util.*;

import com.bizwink.cms.util.*;
import com.bizwink.cms.security.User;

public interface IAuditManager {
    //读取在某栏目上有文章审核权限的所有用户
    List getUsers_hasAuditRight(int columnID, int siteID) throws CmsException;

    //创建栏目审核规则
    void create(Audit audit) throws CmsException;

    //查询某栏目是否有审核规则
    boolean queryAuditRules(int columnID) throws CmsException;

    //读取某栏目的所有审核规则
    Audit getAuditRules(int columnID) throws CmsException;

    List getAuditSubRules(int columnID) throws CmsException;
    //修改某栏目的审核规则
    void update(Audit audit) throws CmsException;

    //删除某栏目的某条审核规则
    void delete(int ID) throws CmsException;

    //查询该篇文章在数据库中的ID
    int getArticleID(int columnID, String maintitle, String editor) throws CmsException;

    //boolean Query_User_Article(String userID,int articleID) throws CmsException;

    int Query_User_Article_Audit(String userID, int articleID) throws CmsException;

    int Query_Article_Column(int articleID) throws CmsException;

    String queryPrev_Auditor(String userID, int articleID,int siteid) throws CmsException;

    void upateAuditFlag(int auditFlag, String auditor, int articleID, int siteId, int columnId) throws CmsException;

    void Create_Article_Audit_Info(Audit audit,int processofaudit,int backtowho) throws CmsException;

    boolean queryArticle_isAllAudited(String userID, int articleID) throws CmsException;

    boolean queryLeaveword_isAllAudited(String userID, String auditRule,int lwID) throws CmsException;

    List getBack_Articles(String userID) throws CmsException;

    List getArticles_NeedAudit(String userID, int siteID) throws CmsException;

    String getColumn_Cname(int columnID) throws CmsException;

    //判断某栏目默认的规则是否在审核之中
    boolean queryColumn_isAudited(int columnID) throws CmsException;

    //查询所有站点的webmaster用户及admin
    List getUsers() throws CmsException;

    //修改用户密码
    int updatePassword(String userID, String password) throws CmsException;

    String[] getArticleInfo(int articleID, String userID, int type) throws CmsException;

    void updateAudit_Info(String userID, int articleID, String comments, int flag,int messagetype) throws CmsException;

    String getComments(String userID, int articleID) throws CmsException;

    boolean query_User_isAuditing(String userID, int articleID);

    void Auditing(Audit audit, String act, int isAudit, int siteId, int columnId,int processofaudit);

    void AuditingLeaveword(Audit audit,String auditRule, String act, int isAudit, int siteId, int formId);

    int getUsersCount(String searchstr) throws CmsException;

    List getUsers(int result, int startrow, String searchstr) throws CmsException;

    int getSitenameCount(String searchstr) throws CmsException;

    List getSitename(int result, int startrow, String searchstr) throws CmsException;

    int getNicknameCount(String searchstr) throws CmsException;

    List getNickname(int result, int startrow, String searchstr) throws CmsException;

    int getEmailCount(String searchstr) throws CmsException;

    List getEmail(int result, int startrow, String searchstr) throws CmsException;

    User getUserOne(int siteid);
}
