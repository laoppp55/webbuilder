package com.bizwink.publishQueue;

import java.sql.*;
import java.util.*;

import com.bizwink.cms.news.*;
import com.bizwink.cms.publishx.Publish;
import com.bizwink.cms.server.*;
import com.bizwink.cms.sitesetting.*;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;

public class PublishQueuePeer implements IPublishQueueManager {
    PoolServer cpool;

    public PublishQueuePeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IPublishQueueManager getInstance() {
        return CmsServer.getInstance().getFactory().getPublishQueueManager();
    }

    //从发布队列中获取当前需要发布的作业,按先进先出的顺序获取发布作业
    synchronized public Jobinfo getOneJob() throws PublishQueueException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int jobid = 0;
        Jobinfo onejob = null;
        boolean exist_onejob = false;
        String dbtype=cpool.getType();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            String SQL_GetOneJob = null;

            if (cpool.getType().equals("oracle"))
                SQL_GetOneJob = "select * from tbl_new_publish_queue where status = 1 and siteid=40 and publishdate < sysdate order by priority asc";
            else if (cpool.getType().equals("mssql"))
                SQL_GetOneJob = "select * from tbl_new_publish_queue where status = 1 and siteid=40 and publishdate < getdate() order by priority asc";
            else
                SQL_GetOneJob = "select * from tbl_new_publish_queue where status = 1 and siteid=40 and publishdate < current_date() order by priority asc";

            String SQL_UpdateJobStatus = "update tbl_new_publish_queue set status = 0 where id=?";

            pstmt=conn.prepareStatement(SQL_GetOneJob);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                onejob = load(rs,dbtype);
                jobid = onejob.getID();
                exist_onejob = true;
            }
            rs.close();
            pstmt.close();

            if (exist_onejob) {
                pstmt=conn.prepareStatement(SQL_UpdateJobStatus);
                pstmt.setInt(1,jobid);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }



        return onejob;
    }

    public void updateJobStatus(int jobid,int status) throws PublishQueueException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        Jobinfo onejob = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            String SQL_UpdateJobStatus = "update tbl_new_publish_queue set status = ? where id=?";
            pstmt=conn.prepareStatement(SQL_UpdateJobStatus);
            pstmt.setInt(1,status);
            pstmt.setInt(2,jobid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public void removeJob(int jobid,int jobtype,int taskid) throws PublishQueueException {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            String SQL_Remove_JOB = "delete from tbl_new_publish_queue where id=?";
            pstmt=conn.prepareStatement(SQL_Remove_JOB);
            pstmt.setInt(1,jobid);
            pstmt.executeUpdate();
            pstmt.close();

            switch (jobtype) {
                case 1:    //发布文章作业
                    String Update_Article_Pubflag = "update tbl_article set pubflag=? where id=?";
                    pstmt=conn.prepareStatement(Update_Article_Pubflag);
                    pstmt.setInt(1,0);
                    pstmt.setInt(2,taskid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    break;
                case 0:    //发布栏目作业
                case 3:    //发布首页栏目作业
                    String Update_Template_Status = "update tbl_template set status=? where id=?";
                    pstmt=conn.prepareStatement(Update_Template_Status);
                    pstmt.setInt(1,0);
                    pstmt.setInt(2,taskid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    break;
                case 2:    //发布标记作业
                    String Update_Mark_Status = "update tbl_mark set pubflag=? where id=?";
                    pstmt=conn.prepareStatement(Update_Mark_Status);
                    pstmt.setInt(1,0);
                    pstmt.setInt(2,taskid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    break;
            }

            conn.commit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public boolean existTheJobInQueue(int siteid,int columnid,int targetid,int type)  throws PublishQueueException {
        boolean existFlag = false;
        String SQL_GetOneJob = "select * from tbl_new_publish_queue where siteid=? and columnid=? and targetid=? and type=?";
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();

            pstmt=conn.prepareStatement(SQL_GetOneJob);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,columnid);
            pstmt.setInt(3,targetid);
            pstmt.setInt(4,type);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                existFlag = true;
            }
            rs.close();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return existFlag;
    }

    public void setupJobErrorMsg(int jobid,int jobtype,int errcode) throws PublishQueueException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        String errmsg = null;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            String SQL_UpdateJobStatus = "update tbl_new_publish_queue set errcode = ?,errmsg=? where id=?";
            pstmt=conn.prepareStatement(SQL_UpdateJobStatus);
            pstmt.setInt(1,errcode);
            if (errcode == -2) {
                errmsg = "没有设置文章模板";
            } else if (errcode == -3){
                errmsg = "栏目被删除";
            } else {
                errmsg = "信息发布错";
            }
            pstmt.setString(2,errmsg);
            pstmt.setInt(3,jobid);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL = "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
            "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public void insertJobs(Article article) throws PublishQueueException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        SiteInfo siteinfo = null;
        ResultSet rs = null;
        Publish publish = null;
        String ids = "";
        List queueList = new ArrayList();
        int siteID = article.getSiteID();
        int columnID = article.getColumnID();

        ISiteInfoManager siteMgr = SiteInfoPeer.getInstance();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            //文章不需要审核，将需要发布的信息加入到信息发布队列
            if (article.getAuditFlag() == 0) {
                siteinfo = siteMgr.getSiteInfo(siteID);
                //修改与该篇文章相关联的 栏目模板 的发布标志位
                String SQL_Select_Model = "select id,columnid,chname,isarticle from tbl_template where isarticle <> 1 and siteid=? and (relatedcolumnid like '%," + columnID + ",%' or relatedcolumnid like '%(" + columnID + ",%' or relatedcolumnid like '%," + columnID + ")%' or relatedcolumnid like '%(" + columnID + ")%')";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                if (siteinfo.getSamsiteid() != 0)
                    pstmt.setInt(1, siteinfo.getSamsiteid());
                else
                    pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    if (cpool.getPublishWay() == 1) {
                        publish = new Publish();
                        publish.setSiteID(siteID);
                        publish.setTargetID(rs.getInt("id"));
                        publish.setColumnID(rs.getInt("columnid"));
                        publish.setTitle(rs.getString("chname"));
                        publish.setPriority(2);                      //栏目模板发布作业的优先级设置为2
                        publish.setPublishDate(article.getPublishTime());
                        if (rs.getInt(("isarticle")) == 0 || rs.getInt(("isarticle"))==3)   //栏目模板和专题模板
                            publish.setObjectType(3);
                        else if (rs.getInt(("isarticle")) == 1)                              //首页模板
                            publish.setObjectType(2);
                        queueList.add(publish);
                    }
                    ids += rs.getString(1) + ",";
                }
                rs.close();
                pstmt.close();

                if (ids.length() > 0) {
                    ids = ids.substring(0, ids.length() - 1);
                    String SQL_Update_ModelPubflag = "update tbl_template set status = 0 where id in (" + ids + ")";
                    pstmt = conn.prepareStatement(SQL_Update_ModelPubflag);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                //将需要发布的文章和模板加入到发布队列
                if (cpool.getPublishWay() == 1 && article.getStatus() == 1 && article.getAuditFlag() == 0) {
                    //加入当前文章
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setColumnID(columnID);
                    publish.setTargetID(article.getID());
                    publish.setTitle(article.getMainTitle());
                    publish.setPriority(1);                      //文章发布作业的优先级设置为1
                    publish.setPublishDate(article.getPublishTime());
                    publish.setObjectType(1);
                    queueList.add(publish);

                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        pstmt.setInt(1, siteID);
                        pstmt.setInt(2, columnID);
                        pstmt.setInt(3, publish.getTargetID());
                        pstmt.setInt(4, publish.getObjectType());
                        pstmt.setInt(5, 1);
                        pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
                        pstmt.setTimestamp(7, publish.getPublishDate());
                        pstmt.setString(8, "");
                        pstmt.setString(9, publish.getTitle());
                        pstmt.setInt(10,publish.getPriority());
                        if (cpool.getType().equals("oracle")) {
                            pstmt.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                            pstmt.executeUpdate();
                        } else if (cpool.getType().equals("mssql")) {
                            pstmt.executeUpdate();
                        } else {
                            pstmt.executeUpdate();
                        }
                    }
                    pstmt.close();
                }
            }

            conn.commit();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                }
                catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private Jobinfo load(ResultSet rs,String dbtype) {
        Jobinfo onejob = new Jobinfo();
        try {
            onejob.setID(rs.getInt("id"));
            onejob.setSiteID(rs.getInt("siteid"));
            onejob.setColumnID(rs.getInt("columnid"));
            onejob.setTargetID(rs.getInt("targetid"));
            onejob.setType(rs.getInt("type"));
            onejob.setStatus(rs.getInt("status"));
            onejob.setCreatedate(rs.getTimestamp("createdate"));
            onejob.setPublishdate(rs.getTimestamp("publishdate"));
            onejob.setUniqueid(rs.getString("uniqueid"));
            if (dbtype.equalsIgnoreCase("mssql"))
                onejob.setTitle(StringUtil.gb2iso4View(rs.getString("title")));
            else
                onejob.setTitle(rs.getString("title"));
        }catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        return onejob;
    }
}