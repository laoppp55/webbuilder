package com.bizwink.dubboservice.serviceimpl;

import com.bizwink.cms.publishx.Publish;
import com.bizwink.dubboservice.DBConnUtil;
import com.bizwink.dubboservice.service.TemplateService;
import com.bizwink.persistence.TemplateMapper;
import com.bizwink.po.Template;
import com.bizwink.util.DBUtil;
import org.apache.fop.util.DOMBuilderContentHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by petersong on 16-6-19.
 */
@Service
public class TemplateServiceImpl implements TemplateService{
    @Autowired
    private TemplateMapper templateMapper;

    public List<Template> getTemplateByColumnidAndSiteidAndTemplateType(BigDecimal siteid,BigDecimal columnid,BigDecimal isarticle) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("siteid",siteid);
        params.put("columnid",columnid);
        params.put("isarticle",isarticle);
        return templateMapper.getTemplateByColumnidAndSiteidAndTemplateType(params);
    }

    public List<Template> getTemplatesByRelatedColumnid(BigDecimal siteid,BigDecimal columnid) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("siteid",siteid);
        params.put("contion1","%," + columnid + ",%");
        params.put("contion2","%(" + columnid + ",%");
        params.put("contion3","%," + columnid + ")%");
        params.put("contion4","%(" + columnid + ")%");

        return templateMapper.getTemplatesByRelatedColumnid(params);
    }

    public List<Template> getTemplateByColumnidAndSiteidAndTemplateTypeAndTempno(BigDecimal siteid,BigDecimal columnid,BigDecimal isarticle,BigDecimal tempno) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put("siteid",siteid);
        params.put("columnid",columnid);
        params.put("isarticle",isarticle);
        params.put("tempno",tempno);

        return templateMapper.getTemplateByColumnidAndSiteidAndTemplateTypeAndTempno(params);
    }

    private static final String SQL_CREATE_MODEL_FOR_ORACLE =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Content,Createdate,Lastupdated,Editor," +
                    "Creator,LockStatus,RelatedColumnID,Status,ModelVersion,TemplateName,ChName,ReferModelID," +
                    "defaultTemplate,isIncluded,tempnum,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,Type,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /*
retcode:-1数据库连接出现错误，-2获取模板表的主键出现错误，-3获取发布队列表的主键出现错误
 */
    public int CreateTemplate(Template template){
        int retcode = 0;
        DBConnUtil dbConnUtil = new DBConnUtil();
        PreparedStatement pstmt = null;
        Connection conn = dbConnUtil.getConn();
        Timestamp thetime = new Timestamp(System.currentTimeMillis());

        if (conn!=null) {
            try {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_CREATE_MODEL_FOR_ORACLE);
                pstmt.setInt(1, template.getSITEID().intValue());
                pstmt.setInt(2, template.getCOLUMNID().intValue());
                pstmt.setInt(3, 3);        //包含文件类型
                DBConnUtil.setBigString("oracle", pstmt, 4, template.getCONTENT());
                pstmt.setString(4, "");
                pstmt.setTimestamp(5, thetime);
                pstmt.setTimestamp(6, thetime);
                pstmt.setString(7, template.getEDITOR());
                pstmt.setString(8, template.getEDITOR());
                pstmt.setInt(9, 0);
                pstmt.setString(10, "");
                pstmt.setInt(11, 0);             //模板页面的状态设置为0,模板需要发布
                pstmt.setInt(12, 0);             //模板为第一个版本，版本号为零
                pstmt.setString(13, template.getTEMPLATENAME());
                pstmt.setString(14, template.getCHNAME());
                pstmt.setInt(15, 0);
                pstmt.setInt(16, 0);
                pstmt.setInt(17, 0);
                pstmt.setInt(18,0);
                long modelID = dbConnUtil.getMainKeyID("Template");
                if (modelID > 0l) {
                    pstmt.setLong(19, modelID);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else {
                    retcode = -2;
                }

                if (retcode == 0) {
                    List queueList = new ArrayList();
                    Publish publish = new Publish();
                    publish.setSiteID(template.getSITEID().intValue());
                    publish.setColumnID(template.getCOLUMNID().intValue());
                    publish.setTargetID((int)modelID);
                    publish.setPriority(2);                                         //栏目模板发布作业优先权设置为2
                    publish.setPublishDate(thetime);
                    publish.setObjectType(3);
                    publish.setTitle(template.getCHNAME());

                    queueList.add(publish);
                    pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        pstmt.setInt(1, publish.getSiteID());
                        pstmt.setInt(2, publish.getColumnID());
                        pstmt.setInt(3, publish.getTargetID());
                        pstmt.setInt(4, publish.getObjectType());
                        pstmt.setInt(5, 1);
                        pstmt.setTimestamp(6, publish.getPublishDate());
                        pstmt.setTimestamp(7, publish.getPublishDate());
                        pstmt.setString(8, "");
                        pstmt.setString(9, publish.getTitle());
                        pstmt.setInt(10,publish.getPriority());
                        long id = dbConnUtil.getMainKeyID("PublishQueue");
                        if (id>0l) {
                            pstmt.setLong(11, id);
                            pstmt.executeUpdate();
                        } else {
                            retcode=-3;
                        }
                    }
                    pstmt.close();
                    conn.commit();
                }
            } catch (SQLException exp) {
                retcode = -1;
                exp.printStackTrace();
            } finally {
                try{
                    conn.close();
                }catch(SQLException sexp) {
                    sexp.printStackTrace();
                }
            }
        } else {
            System.out.println("不能获得数据库的链接");
        }

        return retcode;
    }

    private static final String SQL_UPDATE_MODEL =
            "UPDATE TBL_Template SET ColumnID=?,IsArticle=?,Content=?,Lastupdated=?,Editor=?,LockStatus=?," +
                    "LockEditor=?,RelatedColumnid=?,Status=?,ModelVersion=?,ChName=?,TemplateName=?,isIncluded=? ,tempnum=? WHERE ID=?";

    public int UpdateTemplate(Template template){
        int retcode = 0;
        DBConnUtil dbConnUtil = new DBConnUtil();
        PreparedStatement pstmt = null;
        Connection conn = dbConnUtil.getConn();
        Timestamp thetime = new Timestamp(System.currentTimeMillis());

        if (conn!=null) {
            try {
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_MODEL);
                pstmt.setInt(1, template.getCOLUMNID().intValue());
                pstmt.setInt(2, 3);
                System.out.println(template.getCONTENT());
                DBConnUtil.setBigString("oracle", pstmt, 3, template.getCONTENT());
                pstmt.setTimestamp(4, thetime);
                pstmt.setString(5, template.getEDITOR());
                pstmt.setInt(6, 0);
                pstmt.setString(7, "");
                pstmt.setString(8, "");
                pstmt.setInt(9, 0);                 //模板页面的状态设置为0，模板需要发布
                pstmt.setInt(10, 0);                //模板为第一个版本，版本号为零
                pstmt.setString(11, template.getCHNAME());
                pstmt.setString(12, template.getTEMPLATENAME());
                pstmt.setInt(13, 1);
                pstmt.setInt(14,0);
                pstmt.setInt(15, template.getID().intValue());
                pstmt.executeUpdate();
                pstmt.close();

                List queueList = new ArrayList();
                Publish publish = null;
                publish = new Publish();
                publish.setSiteID(template.getSITEID().intValue());
                publish.setTargetID(template.getID().intValue());
                publish.setColumnID(template.getCOLUMNID().intValue());
                publish.setTitle(template.getCHNAME());
                publish.setPublishDate(thetime);
                publish.setPriority(2);                                          //栏目模板发布作业优先级设置为2
                publish.setObjectType(3);
                queueList.add(publish);
                pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                for (int i = 0; i < queueList.size(); i++) {
                    publish = (Publish) queueList.get(i);
                    pstmt.setInt(1, publish.getSiteID());
                    pstmt.setInt(2, publish.getColumnID());
                    pstmt.setInt(3, publish.getTargetID());
                    pstmt.setInt(4, publish.getObjectType());
                    pstmt.setInt(5, 1);
                    pstmt.setTimestamp(6, publish.getPublishDate());
                    pstmt.setTimestamp(7, publish.getPublishDate());
                    pstmt.setString(8, "");
                    pstmt.setString(9, publish.getTitle());
                    pstmt.setInt(10,publish.getPriority());
                    long id = dbConnUtil.getMainKeyID("PublishQueue");
                    if (id>0l) {
                        pstmt.setLong(11, id);
                        pstmt.executeUpdate();
                    } else {
                        retcode=-3;
                    }
                }
                pstmt.close();
                conn.commit();
            } catch(SQLException exp) {
                retcode = -1;
                exp.printStackTrace();
            } finally {
                try{
                    conn.close();
                }catch(SQLException sexp) {
                    sexp.printStackTrace();
                }
            }
        } else {
            System.out.println("不能获得数据库的链接");
        }

        return retcode;
    }

    private static final String SQL_GETModel =
            "select ID,SiteID,ColumnID,IsArticle,content,Createdate,Lastupdated,Editor,creator,status,relatedcolumnid," +
                    "modelversion,lockstatus,lockeditor,ChName,defaultTemplate,TemplateName,ReferModelID,isincluded,tempnum,tempnum from " +
                    "tbl_template where siteid = ? and columnid=? and isarticle=?";


    public Template getTemplate(int siteid,int columnid,int isarticle){
        DBConnUtil dbConnUtil = new DBConnUtil();
        PreparedStatement pstmt = null;
        Connection conn = dbConnUtil.getConn();
        ResultSet rs = null;
        Template model = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(SQL_GETModel);
                pstmt.setInt(1,siteid);
                pstmt.setInt(2,columnid);
                pstmt.setInt(3,isarticle);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    model = load(rs);
                }
                rs.close();
                pstmt.close();
            } catch (SQLException exp) {
                exp.printStackTrace();
            } finally {
                try{
                    conn.close();
                }catch(SQLException sexp) {
                    sexp.printStackTrace();
                }
            }
        }

        return model;
    }

    Template load(ResultSet rs) throws SQLException {
        Template model = new Template();
        try {
            model.setID(BigDecimal.valueOf(rs.getInt("ID")));
            model.setCOLUMNID(BigDecimal.valueOf(rs.getInt("ColumnID")));
            model.setISARTICLE((short)rs.getInt("IsArticle"));
            model.setCONTENT(DBConnUtil.getBigString("oracle", rs, "content"));
            model.setCREATEDATE(rs.getTimestamp("Createdate"));
            model.setLASTUPDATED(rs.getTimestamp("Lastupdated"));
            model.setEDITOR(rs.getString("Editor"));
            model.setDEFAULTTEMPLATE((short)rs.getInt("defaultTemplate"));
            model.setSTATUS((short)rs.getInt("status"));
            model.setLOCKSTATUS((short)rs.getInt("lockstatus"));
            model.setLOCKEDITOR(rs.getString("lockeditor"));
            model.setCHNAME(rs.getString("ChName"));
            model.setTEMPLATENAME(rs.getString("TemplateName"));
            model.setREFERMODELID(BigDecimal.valueOf(rs.getInt("ReferModelID")));
            model.setRELATEDCOLUMNID(rs.getString("RelatedColumnID"));
            model.setISINCLUDED((short)rs.getInt("isIncluded"));
            model.setSITEID(BigDecimal.valueOf(rs.getInt("siteid")));
            model.setTEMPNUM(BigDecimal.valueOf(rs.getInt("tempnum")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public int DeleteTemplate(int templateid){

        return 0;
    }
}
