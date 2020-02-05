package com.bizwink.cms.register;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.net.URL;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.publish.*;
import com.bizwink.po.Companyinfo;
import com.bizwink.upload.*;
import com.bizwink.cms.viewFileManager.*;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.news.ColumnException;
import com.bizwink.cms.markManager.mark;
import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.publishx.*;
import com.bizwink.cms.publishx.Publish;
import com.bizwink.webapps.register.UregisterException;
import org.jdom.input.SAXBuilder;
import org.jdom.Document;
import org.jdom.Element;

public class RegisterPeer implements IRegisterManager {
    PoolServer cpool;

    public RegisterPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IRegisterManager getInstance() {
        return CmsServer.getInstance().getFactory().getRegisterManager();
    }

    private static final String SQL_CREATE_SITE_FOR_ORACLE = "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,encodeflag,PubFlag,BindFlag," +
            "CreateDate,Berefered,SiteID,sitetype,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize) VALUES ( ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_CREATE_SITE_FOR_MSSQL = "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,encodeflag,PubFlag,BindFlag," +
            "CreateDate,Berefered,sitetype,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize) VALUES ( ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);SELECT scope_identity();";

    private static final String SQL_CREATE_SITE_FOR_MYSQL = "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,encodeflag,PubFlag,BindFlag," +
            "CreateDate,Berefered,sitetype,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic,mediasize,mediapicsize) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


    private static final String SQL_CREATE_SITE_COLUMN_FOR_ORACLE = "INSERT INTO TBL_Column (ID,SiteID,ParentID,OrderID,Cname,Ename,Dirname,Editor,Extname," +
            "CreateDate,LastUpdated,isDefineAttr,hasArticleModel,isAudited,isProduct,isPublishMore,LanguageType,contentshowtype,isrss,archivingrules,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0, 0, 0, 0, 0, 0, 0,?,?,?,?,?,?,?,?)";

    private static final String SQL_CREATE_SITE_COLUMN_FOR_MSSQL = "INSERT INTO TBL_Column (SiteID,ParentID,OrderID,Cname,Ename,Dirname,Editor,Extname,CreateDate,LastUpdated,isDefineAttr,hasArticleModel,isAudited,isProduct,isPublishMore," +
            "LanguageType,contentshowtype,isrss,archivingrules,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0, 0, 0, 0, 0, 0, 0,?,?,?,?,?,?,?,?);SELECT scope_identity();";

    private static final String SQL_CREATE_SITE_COLUMN_FOR_MYSQL = "INSERT INTO TBL_Column (SiteID,ParentID,OrderID,Cname,Ename,Dirname,Editor,Extname,CreateDate,LastUpdated,isDefineAttr,hasArticleModel,isAudited,isProduct,isPublishMore," +
            "LanguageType,contentshowtype,isrss,archivingrules,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0, 0, 0, 0, 0, 0, 0,?,?,?,?,?,?,?,?)";

    private static final String SQL_CREATE_SITE_MASTER = "INSERT INTO TBL_Members (id,SiteID,UserID,Userpwd,textpwd,NickName) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_SITE_MASTER_FOR_MSSQL_AND_MYSQL = "INSERT INTO TBL_Members (SiteID,UserID,Userpwd,textpwd,NickName) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_SITE_MASTER_RIGHT = "INSERT INTO TBL_Members_Rights (UserID,ColumnID,RightID) VALUES (?, ?, ?)";

    private static final String SQL_CREATE_ORG_FOR_ORACLE = "INSERT INTO tbl_organization (siteid,parent,cotype,name,lastupdate,createdate,createuser,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ORG_FOR_MSSQL = "INSERT INTO tbl_organization (siteid,parent,cotype,name,lastupdate,createdate,createuser) VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_COMPANY_FOR_ORACLE = "INSERT INTO tbl_companyinfo (siteid,companyname,mphone,contactor,createdate,updatedate,createuser,orgid,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_COMPANY_FOR_MSSQL = "INSERT INTO tbl_companyinfo (siteid,companyname,mphone,contactor,createdate,updatedate,createuser,orgid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public void create(Register register,Companyinfo companyinfo) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                int siteID = 0;
                if (cpool.getType().equals("oracle")) {
                    siteID = sequnceMgr.getSequenceNum("Site");
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_FOR_ORACLE);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, "127.0.0.1");
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7,register.getEncoding());
                    pstmt.setInt(8, register.getPubFlag());
                    pstmt.setInt(9, register.getBindFlag());
                    pstmt.setTimestamp(10, now);
                    pstmt.setInt(11, 0);
                    pstmt.setInt(12, siteID);
                    pstmt.setInt(13,0);                        //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setString(14, register.getTitlepic());
                    pstmt.setString(15, register.getVtitlepic());
                    pstmt.setString(16, register.getSourcepic());
                    pstmt.setString(17, register.getAuthorpic());
                    pstmt.setString(18, register.getContentpic());
                    pstmt.setString(19, register.getSpecialpic());
                    pstmt.setString(20, register.getProductpic());
                    pstmt.setString(21, register.getProductsmallpic());
                    pstmt.setString(22,register.getMediasize());
                    pstmt.setString(23,register.getMediapicsize());
                    pstmt.executeUpdate();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_ORACLE);
                    pstmt.setInt(1, sequnceMgr.getSequenceNum("Column"));
                    pstmt.setInt(2, siteID);
                    pstmt.setInt(3, 0);
                    pstmt.setInt(4, 1);
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "/");
                    pstmt.setString(8, "admin");
                    pstmt.setString(9, register.getExtName());
                    pstmt.setTimestamp(10, now);
                    pstmt.setTimestamp(11, now);
                    pstmt.setString(12, register.getTitlepic());
                    pstmt.setString(13, register.getVtitlepic());
                    pstmt.setString(14, register.getSourcepic());
                    pstmt.setString(15, register.getAuthorpic());
                    pstmt.setString(16, register.getContentpic());
                    pstmt.setString(17, register.getSpecialpic());
                    pstmt.setString(18, register.getProductpic());
                    pstmt.setString(19, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加组织架构信息
                    int orgid = sequnceMgr.getSequenceNum("joincompany");
                    pstmt = conn.prepareStatement(SQL_CREATE_ORG_FOR_ORACLE);
                    pstmt.setInt(1,siteID);
                    pstmt.setInt(2,0);                    //组织架构根节点的父ID为0
                    pstmt.setInt(3,1);                    //节点类型为公司
                    pstmt.setString(4,companyinfo.getCOMPANYNAME());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.setInt(8,orgid);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加公司信息
                    pstmt = conn.prepareStatement(SQL_CREATE_COMPANY_FOR_ORACLE);
                    pstmt.setInt(1,siteID);
                    pstmt.setString(2,companyinfo.getCOMPANYNAME());
                    pstmt.setString(3,companyinfo.getMPHONE());
                    pstmt.setString(4,companyinfo.getCONTACTOR());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.setInt(8,orgid);
                    pstmt.setInt(9,sequnceMgr.getSequenceNum("joincompany"));
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加企业分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_COMPANYCLASS_FOR_ORACLE);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "公司分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setInt(11, sequnceMgr.getSequenceNum("joincompany"));
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加站点分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_WEBSITECLASS_FOR_ORACLE);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "站点分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setInt(11, sequnceMgr.getSequenceNum("joincompany"));
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_FOR_MSSQL);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, "127.0.0.1");
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7,register.getEncoding());
                    pstmt.setInt(8, register.getPubFlag());
                    pstmt.setInt(9, register.getBindFlag());
                    pstmt.setTimestamp(10, now);
                    pstmt.setInt(11,0);
                    pstmt.setInt(12,0);  //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setString(13, register.getTitlepic());
                    pstmt.setString(14, register.getVtitlepic());
                    pstmt.setString(15, register.getSourcepic());
                    pstmt.setString(16, register.getAuthorpic());
                    pstmt.setString(17, register.getContentpic());
                    pstmt.setString(18, register.getSpecialpic());
                    pstmt.setString(19, register.getProductpic());
                    pstmt.setString(20, register.getProductsmallpic());
                    pstmt.setString(21,register.getMediasize());
                    pstmt.setString(22,register.getMediapicsize());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        siteID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_MSSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 1);
                    pstmt.setString(4, register.getSiteName());
                    pstmt.setString(5, "/");
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setString(11, register.getTitlepic());
                    pstmt.setString(12, register.getVtitlepic());
                    pstmt.setString(13, register.getSourcepic());
                    pstmt.setString(14, register.getAuthorpic());
                    pstmt.setString(15, register.getContentpic());
                    pstmt.setString(16, register.getSpecialpic());
                    pstmt.setString(17, register.getProductpic());
                    pstmt.setString(18, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加组织架构信息
                    pstmt = conn.prepareStatement(SQL_CREATE_ORG_FOR_MSSQL);
                    pstmt.setInt(1,siteID);
                    pstmt.setInt(2,0);                    //组织架构根节点的父ID为0
                    pstmt.setInt(3,1);                    //节点类型为公司
                    pstmt.setString(4,companyinfo.getCOMPANYNAME());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.executeUpdate();
                    rs = pstmt.executeQuery();
                    int orgid = 0;
                    if (rs.next()) {
                        orgid = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    //增加公司信息
                    pstmt = conn.prepareStatement(SQL_CREATE_COMPANY_FOR_MSSQL);
                    pstmt.setInt(1,siteID);
                    pstmt.setString(2,companyinfo.getCOMPANYNAME());
                    pstmt.setString(3,companyinfo.getMPHONE());
                    pstmt.setString(4,companyinfo.getCONTACTOR());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.setInt(8,orgid);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加企业分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_COMPANYCLASS_FOR_MSSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "公司分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加站点分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_WEBSITECLASS_FOR_MSSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "站点分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.executeUpdate();
                    pstmt.close();
                } else if (cpool.getType().equals("mysql")) {
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_FOR_MYSQL);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, "127.0.0.1");
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7,register.getEncoding());
                    pstmt.setInt(8, register.getPubFlag());
                    pstmt.setInt(9, register.getBindFlag());
                    pstmt.setTimestamp(10, now);
                    pstmt.setInt(11, 0);
                    pstmt.setInt(12,0);        //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setString(13, register.getTitlepic());
                    pstmt.setString(14, register.getVtitlepic());
                    pstmt.setString(15, register.getSourcepic());
                    pstmt.setString(16, register.getAuthorpic());
                    pstmt.setString(17, register.getContentpic());
                    pstmt.setString(18, register.getSpecialpic());
                    pstmt.setString(19, register.getProductpic());
                    pstmt.setString(20, register.getProductsmallpic());
                    pstmt.setString(21,register.getMediasize());
                    pstmt.setString(22,register.getMediapicsize());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值siteid
                    pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                    rs = pstmt.executeQuery();
                    if (rs.next()) siteID = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 1);
                    pstmt.setString(4, register.getSiteName());
                    pstmt.setString(5, "/");
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setString(11, register.getTitlepic());
                    pstmt.setString(12, register.getVtitlepic());
                    pstmt.setString(13, register.getSourcepic());
                    pstmt.setString(14, register.getAuthorpic());
                    pstmt.setString(15, register.getContentpic());
                    pstmt.setString(16, register.getSpecialpic());
                    pstmt.setString(17, register.getProductpic());
                    pstmt.setString(18, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加组织架构信息
                    pstmt = conn.prepareStatement(SQL_CREATE_ORG_FOR_MSSQL);
                    pstmt.setInt(1,siteID);
                    pstmt.setInt(2,0);                    //组织架构根节点的父ID为0
                    pstmt.setInt(3,1);                    //节点类型为公司
                    pstmt.setString(4,companyinfo.getCOMPANYNAME());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.executeUpdate();
                    rs.close();
                    pstmt.close();

                    //获取Mysql自增列的值orgid
                    int orgid = 0;
                    pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                    rs = pstmt.executeQuery();
                    if (rs.next()) orgid = rs.getInt(1);
                    rs.close();
                    pstmt.close();


                    //增加公司信息
                    pstmt = conn.prepareStatement(SQL_CREATE_COMPANY_FOR_MSSQL);
                    pstmt.setInt(1,siteID);
                    pstmt.setString(2,companyinfo.getCOMPANYNAME());
                    pstmt.setString(3,companyinfo.getMPHONE());
                    pstmt.setString(4,companyinfo.getCONTACTOR());
                    pstmt.setTimestamp(5,now);
                    pstmt.setTimestamp(6,now);
                    pstmt.setString(7,"admin");
                    pstmt.setInt(8,orgid);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加企业分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_COMPANYCLASS_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "公司分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加站点分类信息根目录
                    pstmt = conn.prepareStatement(INSERT_WEBSITECLASS_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 0);
                    pstmt.setString(4, "站点分类");
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                System.out.println("db type==" + cpool.getType());

                if (cpool.getType().equals("oracle")) {
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_MASTER);
                    pstmt.setInt(1,sequnceMgr.getSequenceNum("Userreg"));
                    pstmt.setInt(2, siteID);
                    pstmt.setString(3, register.getUserID());
                    pstmt.setString(4, Encrypt.md5(register.getPassword().getBytes()));
                    pstmt.setString(5, register.getPassword());
                    pstmt.setString(6, register.getUsername());
                    pstmt.executeUpdate();
                    pstmt.close();
                }else if (cpool.getType().equals("mssql")||cpool.getType().equals("mysql")){
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_MASTER_FOR_MSSQL_AND_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setString(2, register.getUserID());
                    pstmt.setString(3, Encrypt.md5(register.getPassword().getBytes()));
                    pstmt.setString(4, register.getPassword());
                    pstmt.setString(5, register.getUsername());
                    pstmt.executeUpdate();
                    pstmt.close();
                }

                pstmt = conn.prepareStatement(SQL_CREATE_SITE_MASTER_RIGHT);
                pstmt.setString(1, register.getUserID());
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 54);
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create site failed.");
            } finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_GET_SampleSite_TemplateInfo =
            "SELECT id,columnid,isarticle,relatedcolumnid,lockstatus,lockeditor,defaulttemplate,chname,templatename," +
                    "refermodelid,isIncluded,content FROM TBL_Template WHERE siteid=?";

    private static final String SQL_Create_Template_FOR_ORACLE =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Createdate,Lastupdated,Editor,Creator,status,relatedcolumnid," +
                    "modelversion,LockStatus,lockeditor,ChName,defaulttemplate,TemplateName,refermodelid,isIncluded,Content,ID)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_Create_Template_FOR_MSSQL =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Createdate,Lastupdated,Editor,Creator,status,relatedcolumnid," +
                    "modelversion,LockStatus,lockeditor,ChName,defaulttemplate,TemplateName,refermodelid,isIncluded,Content)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);SELECT SCOPE_IDENTITY();";

    private static final String SQL_Create_Template_FOR_MYSQL =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Createdate,Lastupdated,Editor,Creator,status,relatedcolumnid," +
                    "modelversion,LockStatus,lockeditor,ChName,defaulttemplate,TemplateName,refermodelid,isIncluded,Content)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String SQL_GET_Mark_Info =
            "SELECT SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes,Lockflag,lockeditor," +
                    "Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid,id" +
                    " FROM TBL_Mark WHERE siteid = ?";

    private static final String SQL_CREATE_MARK_FOR_ORACLE =
            "INSERT INTO TBL_Mark (SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
                    "Lockflag,lockeditor,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid," +
                    "ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_MARK_FOR_MSSQL =
            "INSERT INTO TBL_Mark (SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
                    "Lockflag,lockeditor,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);SELECT SCOPE_IDENTITY();";

    private static final String SQL_CREATE_MARK_FOR_MYSQL =
            "INSERT INTO TBL_Mark (SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
                    "Lockflag,lockeditor,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String SQL_CREATE_COLUMN_FOR_ORACLE =
            "INSERT INTO TBL_Column (SiteID,ParentID,Cname,Ename,Editor,Dirname,OrderID,isproduct,Extname,CreateDate,LastUpdated," +
                    "isdefineattr,hasarticlemodel,isaudited,ispublishmore,languagetype,columndesc,xmltemplate,contentshowtype,isrss," +
                    "getRssArticleTime,archivingrules,useArticleType,istype,ID) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_COLUMN_FOR_MSSQL =
            "INSERT INTO TBL_Column (SiteID,ParentID,Cname,Ename,Editor,Dirname,OrderID,isproduct,Extname,CreateDate,LastUpdated," +
                    "isdefineattr,hasarticlemodel,isaudited,ispublishmore,languagetype,columndesc,xmltemplate,contentshowtype,isrss," +
                    "getRssArticleTime,archivingrules,useArticleType,istype) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);SELECT SCOPE_IDENTITY();";

    private static final String SQL_CREATE_COLUMN_FOR_MYSQL =
            "INSERT INTO TBL_Column (SiteID,ParentID,Cname,Ename,Editor,Dirname,OrderID,isproduct,Extname,CreateDate,LastUpdated," +
                    "isdefineattr,hasarticlemodel,isaudited,ispublishmore,languagetype,columndesc,xmltemplate,contentshowtype,isrss," +
                    "getRssArticleTime,archivingrules,useArticleType,istype) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String getSampleSiteName = "SELECT sitename,sitevalid FROM tbl_siteinfo WHERE siteid=?";

    //获取新站点的rootid
    private static final String getSiteRootID = "SELECT id FROM tbl_column WHERE siteid=? AND parentid=0";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE =
            "INSERT INTO tbl_new_publish_queue (siteid,columnid,targetid,TYPE,Status,CreateDate,PublishDate,UniqueID,title,PRIORITY,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,TYPE,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL =
            "INSERT INTO tbl_new_publish_queue (SiteID,columnid,TargetID,TYPE,Status,CreateDate," +
                    "PublishDate,UniqueID,title,PRIORITY) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public boolean copySamSite(int sampleSiteID, int siteid, String userid, String sitename, String apppath) throws CmsException {
        boolean succeed = false;
        Connection conn = null;
        PreparedStatement pstmt = null, pstmt1 = null;
        ResultSet rs = null;
        String SQL_CREATE = null;
        String SQL_CREATESITE = null;
        int newColumnID = 0;
        int marknum = 0;
        String sampleSiteName = null;
        sitename = StringUtil.replace(sitename, ".", "_");

        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        try {
            int newsiteid = 0;
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                //获取例子站点的域名
                pstmt = conn.prepareStatement(getSampleSiteName);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    sampleSiteName = rs.getString(1);
                    sampleSiteName = StringUtil.replace(sampleSiteName, ".", "_");
                }
                rs.close();
                pstmt.close();

                //拷贝所有的文件到CMS的sites目录和所有的web服务器
                copyFilesToCMS_AND_WEB(siteid,sampleSiteID,userid,sampleSiteName,sitename,apppath);
                //copy_CSS_AND_SCRIPT_ToCMS_AND_WEB(sampleSiteName, sitename, apppath);

                System.out.println("开始创建栏目" + "===" + System.currentTimeMillis());
                //获取新站点的rootid
                int siterootid = 0;
                pstmt = conn.prepareStatement(getSiteRootID);
                pstmt.setInt(1, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    siterootid = rs.getInt(1);
                }
                rs.close();
                pstmt.close();

                // insert into tbl_column
                newColumnID = siterootid;
                int currentID = 0;                                            //当前正在处理的节点
                int i = 0;
                int orderNumber = 0;                                          //当前节点在同级节点的顺序号
                int nodenum = 1;                                              //当前被处理节点的初始值
                int newnodenum = 1;
                int subflag = 1;
                List NOColList = new ArrayList();
                relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();
                List NOMarkList = new ArrayList();
                relationBetweenOldMarkAndNewMark relNOMark = new relationBetweenOldMarkAndNewMark();

                //从样本站点中拷贝栏目、模版、标记、格式文件到新的站点
                if (sampleSiteID > 0) {
                    Tree colTree = TreeManager.getInstance().getSiteTree(sampleSiteID);
                    node[] treeNodes = colTree.getAllNodes();
                    int pid[] = new int[colTree.getNodeNum()];                        //遍历树所需要的节点数组，存储当前未处理的节点
                    int newpid[] = new int[colTree.getNodeNum()];                     //遍历树所需要的节点数组，存储当前未处理的节点
                    //循环变量
                    int[] ordernum = new int[colTree.getNodeNum()];                   //当前节点所有子节点的顺序号
                    //判断当前节点是否有子节点
                    StringBuffer buf = new StringBuffer();                            //存储生成的菜单树
                    IColumnManager columnMgr = ColumnPeer.getInstance();
                    newpid[0] = newColumnID;
                    currentID = colTree.getTreeRoot();

                    //定义样本网站和新定义的网站之间的栏目对应关系
                    relNOCol.setNewColumnID(newColumnID);
                    relNOCol.setOldColumnID(currentID);
                    NOColList.add(relNOCol);

                    do {
                        //从处理的节点数组中取出当前正在处理的元素，查找该元素下的子元素
                        nodenum = nodenum - 1;
                        for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
                            if (treeNodes[i].getLinkPointer() == currentID) {
                                nodenum = nodenum + 1;
                                pid[nodenum] = treeNodes[i].getId();
                            }
                        }

                        try {
                            Column column = columnMgr.getColumn(currentID);
                            if (column.getID() != 0) {
                                newnodenum = newnodenum - 1;
                                int parentID = newpid[newnodenum];       //新节点的父ID
                                for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
                                    if (treeNodes[i].getLinkPointer() == currentID) {
                                        if (cpool.getType().equalsIgnoreCase("oracle"))
                                            pstmt = conn.prepareStatement(SQL_CREATE_COLUMN_FOR_ORACLE);
                                        else if (cpool.getType().equalsIgnoreCase("mssql"))
                                            pstmt = conn.prepareStatement(SQL_CREATE_COLUMN_FOR_MSSQL);
                                        else
                                            pstmt = conn.prepareStatement(SQL_CREATE_COLUMN_FOR_MYSQL);
                                        column = columnMgr.getColumn(treeNodes[i].getId());
                                        pstmt.setInt(1, siteid);
                                        pstmt.setInt(2, parentID);
                                        pstmt.setString(3, column.getCName());
                                        pstmt.setString(4, column.getEName());
                                        pstmt.setString(5, column.getEditor());
                                        pstmt.setString(6, column.getDirName());
                                        pstmt.setInt(7, column.getOrderID());
                                        pstmt.setInt(8, column.getIsProduct());
                                        pstmt.setString(9, column.getExtname());
                                        pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                                        pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                                        pstmt.setInt(12, column.getDefineAttr());                           //是否定义了扩展属性
                                        pstmt.setInt(13, column.getHasArticleModel());                      //是否存在文章模板
                                        pstmt.setInt(14, column.getIsAudited());                            //是否需要审核
                                        pstmt.setInt(15, column.getIsPublishMoreArticleModel());            //是否存在多文章模板发布
                                        pstmt.setInt(16, column.getLanguageType());                         //定义了栏目的语言类型
                                        pstmt.setString(17, column.getDesc());                              //栏目说明
                                        pstmt.setString(18, column.getXMLTemplate());                       //栏目扩展属性XML定义
                                        pstmt.setInt(19, column.getContentShowType());                      //内容显示类型
                                        pstmt.setInt(20, column.getRss());                                  //是否发布RSS
                                        pstmt.setInt(21, column.getGetRssArticleTime());                    //是否需要审核
                                        pstmt.setInt(22, column.getArchivingrules());                       //是否存在多文章模板发布
                                        pstmt.setInt(23, column.getUseArticleType());                       //定义了栏目的语言类型
                                        pstmt.setInt(24, column.getIsType());                               //定义了栏目的语言类型
                                        if (cpool.getType().equalsIgnoreCase("oracle")) {
                                            newColumnID = sequnceMgr.getSequenceNum("Column");
                                            pstmt.setInt(25, newColumnID);
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                        } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                                            rs = pstmt.executeQuery();
                                            if (rs.next()) {
                                                newColumnID = rs.getInt(1);
                                            }
                                            rs.close();
                                            pstmt.close();
                                        } else {
                                            pstmt.executeUpdate();
                                            pstmt.close();

                                            //获取Mysql自增列的值siteid
                                            pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                                            rs = pstmt.executeQuery();
                                            if (rs.next()) newColumnID = rs.getInt(1);
                                            rs.close();
                                            pstmt.close();
                                        }

                                        newpid[newnodenum] = newColumnID;
                                        newnodenum = newnodenum + 1;

                                        relNOCol = new relationBetweenOldColumnAndNewColumn();
                                        relNOCol.setNewColumnID(newColumnID);
                                        relNOCol.setOldColumnID(column.getID());
                                        NOColList.add(relNOCol);
                                    }
                                }
                            }
                        } catch (ColumnException e) {
                            e.printStackTrace();
                        }
                        currentID = pid[nodenum];
                    } while (nodenum > 0);
                }

                //拷贝文章样式表------------------------------------tbl_viewfile
                System.out.println("开始拷贝样式文件" + "===" + System.currentTimeMillis());
                List viewfilelist = new ArrayList();
                pstmt = conn.prepareStatement("SELECT id,siteid,TYPE,content,chinesename,editor,lockflag,notes,createdate,updatedate " +
                        "FROM tbl_viewfile t WHERE siteid=" + sampleSiteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ViewFile view = loadviewfile(rs);
                    viewfilelist.add(view);
                }
                rs.close();
                pstmt.close();

                //为新站点创建样式文件-------------------------------------------
                int viewid = 0;
                String content = null;
                relationBetweenOldViewAndNewView relNOView = null;
                List NOViewList = new ArrayList();  //用来 存储 tbl_viewfile 插入的ID 和模版的ID
                String viewfile_SQL = "";
                if (cpool.getType().equals("oracle"))
                    viewfile_SQL = "INSERT INTO tbl_viewfile(siteid,TYPE,content,chinesename,editor,lockflag,notes,createdate,updatedate,id) VALUES(?,?,?,?,?,?,?,?,?,?)";
                else if (cpool.getType().equals("mssql"))
                    viewfile_SQL = "INSERT INTO tbl_viewfile(siteid,TYPE,content,chinesename,editor,lockflag,notes,createdate,updatedate) VALUES(?,?,?,?,?,?,?,?,?);SELECT SCOPE_IDENTITY();";
                else
                    viewfile_SQL = "INSERT INTO tbl_viewfile(siteid,TYPE,content,chinesename,editor,lockflag,notes,createdate,updatedate) VALUES(?,?,?,?,?,?,?,?,?)";

                for (i = 0; i < viewfilelist.size(); i++) {
                    pstmt = conn.prepareStatement(viewfile_SQL);
                    ViewFile view = (ViewFile) viewfilelist.get(i);
                    pstmt.setInt(1, siteid);
                    pstmt.setInt(2, view.getType());
                    if (view.getContent() != null) {
                        content = view.getContent();
                        content = StringUtil.replace(content, sampleSiteName, sitename);
                        DBUtil.setBigString(cpool.getType(), pstmt, 3, content);
                    } else {
                        pstmt.setNull(3, java.sql.Types.LONGVARCHAR);
                    }
                    pstmt.setString(4, view.getChineseName());
                    pstmt.setString(5, userid);
                    pstmt.setInt(6, view.getLockFlag());
                    pstmt.setString(7, view.getNotes());
                    pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                    if (cpool.getType().equals("oracle")) {
                        viewid = sequnceMgr.getSequenceNum("ViewFile");
                        pstmt.setInt(10, viewid);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            viewid = rs.getInt(1);
                        }
                        rs.close();
                        pstmt.close();
                    } else {
                        pstmt.executeUpdate();
                        pstmt.close();

                        //获取Mysql自增列的值siteid
                        pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                        rs = pstmt.executeQuery();
                        if (rs.next()) viewid = rs.getInt(1);
                        rs.close();
                        pstmt.close();
                    }

                    relNOView = new relationBetweenOldViewAndNewView();
                    relNOView.setOldViewID(view.getID());
                    relNOView.setNewViewID(viewid);
                    NOViewList.add(relNOView);
                }

                //拷贝标记
                System.out.println("开始拷标记信息" + "===" + System.currentTimeMillis());
                List marklist = new ArrayList();
                mark mark = new mark();
                pstmt = conn.prepareStatement(SQL_GET_Mark_Info);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    mark = loadMark(rs);
                    marklist.add(mark);
                }
                rs.close();
                pstmt.close();

                int newMarkID = 0;
                int newcid = 0;
                for (i = 0; i < marklist.size(); i++) {
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_MSSQL);
                    else
                        pstmt = conn.prepareStatement(SQL_CREATE_MARK_FOR_MYSQL);
                    mark = (mark) marklist.get(i);
                    pstmt.setInt(1, siteid);
                    //获取新站点对应的栏目ID
                    for (int k = 0; k < NOColList.size(); k++) {
                        relNOCol = (relationBetweenOldColumnAndNewColumn) NOColList.get(k);
                        if (relNOCol.getOldColumnID() == mark.getColumnID()) {
                            newcid = relNOCol.getNewColumnID();
                            break;
                        }
                    }
                    pstmt.setInt(2, newcid);
                    content = mark.getContent();
                    content = StringUtil.replace(content, sampleSiteName, sitename);
                    DBUtil.setBigString(cpool.getType(), pstmt, 3, replaceColumnIDAndViewID_Of_MarkContent(content, NOColList, NOViewList));
                    //pstmt.setString(3, replaceColumnID_Of_MarkContent(content, NOColList));
                    pstmt.setInt(4, mark.getMarkType());
                    pstmt.setString(5, mark.getChineseName());
                    pstmt.setInt(6, mark.getFormatFileNum());
                    pstmt.setString(7, mark.getNotes());
                    pstmt.setInt(8, mark.getLockFlag());           //没有被锁定
                    pstmt.setString(9, mark.getLockEditor());
                    pstmt.setInt(10, mark.getPubFlag());           //需要发布
                    pstmt.setInt(11, mark.getInnerHTMLFlag());
                    pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(13, new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(14, new Timestamp(System.currentTimeMillis()));
                    pstmt.setString(15, replaceColumnID_IN_Mark_Of_RelatedColumnIDs(mark.getRelatedColumnID(), NOColList));
                    if (cpool.getType().equals("oracle")) {
                        newMarkID = sequnceMgr.getSequenceNum("Mark");
                        pstmt.setInt(16, newMarkID);
                        pstmt.executeUpdate();
                        pstmt.close();
                    } else if (cpool.getType().equals("oracle")) {
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            newMarkID = rs.getInt(1);
                        }
                        rs.close();
                        pstmt.close();
                    } else {
                        pstmt.executeUpdate();
                        pstmt.close();

                        //获取Mysql自增列的值siteid
                        pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                        rs = pstmt.executeQuery();
                        if (rs.next()) newMarkID = rs.getInt(1);
                        rs.close();
                        pstmt.close();
                    }
                    relNOMark = new relationBetweenOldMarkAndNewMark();
                    relNOMark.setNewMarkID(newMarkID);
                    relNOMark.setOldMarkID(mark.getID());
                    NOMarkList.add(relNOMark);
                }

                //拷贝模板
                System.out.println("开始拷贝网站模板" + "===" + System.currentTimeMillis());
                Model model = new Model();
                pstmt = conn.prepareStatement(SQL_GET_SampleSite_TemplateInfo);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                List queueList = new ArrayList();
                com.bizwink.cms.publishx.Publish publish;

                while (rs.next()) {
                    model = loadTemplate(rs, userid);
                    //获取新站点对应的栏目ID
                    int old_tid = model.getID();
                    //System.out.println("old_tid=" + old_tid + "==(" + model.getChineseName() + ")");
                    for (int k = 0; k < NOColList.size(); k++) {
                        relNOCol = (relationBetweenOldColumnAndNewColumn) NOColList.get(k);
                        if (relNOCol.getOldColumnID() == model.getColumnID()) {
                            newcid = relNOCol.getNewColumnID();
                            break;
                        }
                    }

                    int new_tid = 0;
                    //修改模板上标记的ID为新的标记ID，并重写模板内容
                    content = model.getContent();
                    try {
                        String tbuf = content;
                        Pattern p = Pattern.compile("\\[MARKID\\][0-9,_,-]*\\[/MARKID\\]", Pattern.CASE_INSENSITIVE);
                        Matcher m = p.matcher(tbuf);
                        while (m.find()) {
                            int start = m.start();
                            int end = m.end();
                            String thetag = tbuf.substring(start, end);
                            String newtag = replaceMarkid(thetag, NOMarkList, NOColList);
                            content = StringUtil.replace(content, thetag, newtag);
                            tbuf = tbuf.substring(end);
                            m = p.matcher(tbuf);
                        }

                        //修改模板的格式文件ID，例如中文路径的格式文件，英文路径的格式文件和导航条的格式文件
                        content = replaceViewID_IN_Template(content, NOViewList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //为新的站点加入该模板
                    formatPictureURL fpurl = new formatPictureURL();
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt1 = conn.prepareStatement(SQL_Create_Template_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt1 = conn.prepareStatement(SQL_Create_Template_FOR_MSSQL);
                    else
                        pstmt1 = conn.prepareStatement(SQL_Create_Template_FOR_MYSQL);

                    pstmt1.setInt(1, siteid);
                    if (model.getColumnID() == -100)     //动态程序模板
                        pstmt1.setInt(2, -100);
                    else
                        pstmt1.setInt(2, newcid);
                    pstmt1.setInt(3, model.getIsArticle());
                    pstmt1.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
                    pstmt1.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                    pstmt1.setString(6, userid);
                    pstmt1.setString(7, userid);
                    pstmt1.setInt(8, 0);                                       //status
                    if (model.getRelatedColumnIDs() != null) {
                        if (!model.getRelatedColumnIDs().equalsIgnoreCase("()"))
                            pstmt1.setString(9, replaceColumnID_Of_RelatedColumnIDs(model.getRelatedColumnIDs(), NOColList));
                        else
                            pstmt1.setString(9, model.getRelatedColumnIDs());
                    } else {
                        pstmt1.setString(9, model.getRelatedColumnIDs());
                    }
                    pstmt1.setInt(10, 0);                                      //modelversion
                    pstmt1.setInt(11, model.getLockStatus());
                    pstmt1.setString(12, model.getLockEditor());
                    pstmt1.setString(13, model.getChineseName());
                    pstmt1.setInt(14, model.getDefaultTemplate());
                    pstmt1.setString(15, model.getTemplateName());
                    pstmt1.setInt(16, model.getReferModelID());
                    pstmt1.setInt(17, model.getIncluded());         //模板需要发布
                    //content = fpurl.formatHTML(content, sampleSiteName, sitename);
                    content = fpurl.replaceSitenameInTheHTML(content, sampleSiteName, sitename);
                    content = StringUtil.replace(content, sampleSiteName, sitename);
                    DBUtil.setBigString(cpool.getType(), pstmt1, 18, content);
                    //获取模板新的ID
                    if (cpool.getType().equals("oracle")) {
                        new_tid = sequnceMgr.getSequenceNum("Template");
                        pstmt1.setInt(19, new_tid);
                        pstmt1.executeUpdate();
                        pstmt1.close();
                    } else if (cpool.getType().equals("oracle")) {
                        ResultSet rs1 = pstmt1.executeQuery();
                        if (rs1.next()) {
                            new_tid = rs1.getInt(1);
                        }
                        rs1.close();
                        pstmt1.close();

                    } else {
                        pstmt1.executeUpdate();
                        pstmt1.close();

                        //获取Mysql自增列的值siteid
                        pstmt1 = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                        ResultSet rs1 = pstmt1.executeQuery();
                        if (rs1.next()) new_tid = rs1.getInt(1);
                        rs1.close();
                        pstmt1.close();
                    }


                    if (model.getIsArticle() != 1) {                                        //非文章模板
                        publish = new Publish();
                        publish.setSiteID(siteid);
                        if (model.getColumnID() == -100)                                     //动态程序模板
                            publish.setColumnID(-100);
                        else
                            publish.setColumnID(newcid);
                        publish.setTargetID(new_tid);
                        publish.setPublishDate(new Timestamp(System.currentTimeMillis()));
                        publish.setPriority(1);
                        if (model.getIsArticle() == 2)                                       //首页作业
                            publish.setObjectType(0);
                        else if (model.getIsArticle() == 0 || model.getIsArticle() == 3)    //栏目模板或专题模板作业
                            publish.setObjectType(3);
                        else if (model.getIsArticle() > 10)                                 //程序模板
                            publish.setObjectType(model.getIsArticle());
                        publish.setTitle(model.getChineseName());
                        queueList.add(publish);
                    }
                }
                rs.close();
                pstmt.close();

                //加入作业队列
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                else if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                for (i = 0; i < queueList.size(); i++) {
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
                    pstmt.setInt(10, publish.getPriority());
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setLong(11, sequnceMgr.getSequenceNum("PublishQueue"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("oracle")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();

                //清除模版列表
                NOColList = null;
                NOViewList = null;
                NOMarkList = null;

                conn.commit();
            } catch (Exception e) {
                succeed = true;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: create user failed.");
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
        } catch (SQLException e) {
            succeed = true;
            e.printStackTrace();
            throw new CmsException("Database exception: can't rollback?");
        }


        System.out.println("网站生成结束" + "===" + System.currentTimeMillis());
        return succeed;
    }

    //拷贝CSS文件和脚本文件到WEB
    public void copy_CSS_AND_SCRIPT_ToCMS_AND_WEB(String userid,String sampleSiteName,int siteid, String sitename, String appPath) throws CmsException {
        //源文件路径
        String srcPathIcon = appPath + "sites" + File.separator + sampleSiteName + File.separator;
        //目标文件路径
        String objPathIcon = appPath + "sites" + File.separator + sitename + File.separator;

        String srcfile = null;
        FileInputStream source = null;
        FileOutputStream destination = null;

        //拷贝文章列表图标
        File file = new File(srcPathIcon);
        int index = 0;
        List dirs = new ArrayList();
        dirs.add(file);

        try {
            do {
                file = (File) dirs.get(index);
                File[] fs = file.listFiles();
                dirs.remove(index);
                index = index - 1;
                if (fs != null) {
                    for (int i = 0; i < fs.length; i++) {
                        if (fs[i].isDirectory()) {
                            dirs.add(fs[i]);
                            index = index + 1;
                        } else {
                            srcfile = fs[i].getPath();
                            String extname = srcfile.substring(srcfile.lastIndexOf(".") + 1);
                            //if (extname.equalsIgnoreCase("gif") || extname.equalsIgnoreCase("jpg") || extname.equalsIgnoreCase("jpeg") ||
                            //        extname.equalsIgnoreCase("swf") || extname.equalsIgnoreCase("css") || extname.equalsIgnoreCase("js") ||
                            //        extname.equalsIgnoreCase("vbs")) {
                            if (extname.equalsIgnoreCase("css") || extname.equalsIgnoreCase("js") || extname.equalsIgnoreCase("vbs")) {
                                int posi = srcfile.indexOf(srcPathIcon);
                                String objfile = objPathIcon + srcfile.substring(posi + srcPathIcon.length());
                                String objpath = objfile.substring(0, objfile.lastIndexOf(File.separator));
                                File dir = new File(objpath);
                                if (!dir.exists()) dir.mkdirs();
                                source = new FileInputStream(srcfile);
                                destination = new FileOutputStream(objfile);
                                byte[] buffer = new byte[1024];
                                int bytes_read;

                                while (true) {
                                    bytes_read = source.read(buffer);
                                    if (bytes_read == -1) break;
                                    destination.write(buffer, 0, bytes_read);
                                }

                                source.close();
                                destination.close();

                                //发布文件到所有的WEB服务器
                                try {
                                    IPublishManager publishMgr = PublishPeer.getInstance();
                                    String localFileName = srcfile;
                                    String dirName = srcfile.substring(posi + srcPathIcon.length()-1);
                                    posi = dirName.lastIndexOf(File.separator);
                                    dirName = dirName.substring(0,posi+1);
                                    int errcode = publishMgr.publish(userid, localFileName, siteid, dirName, 0);
                                } catch (PublishException pex) {
                                    pex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } while (dirs.size() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            srcPathIcon = null;
            objPathIcon = null;
            file = null;
        }
    }

    //拷贝文章列表图标及HTML碎片文件到WEB
    private void copyFilesToCMS_AND_WEB(int siteid, int sampleSiteID, String userid, String sampleSiteName, String sitename, String appPath) throws CmsException {
        //源文件路径
        String srcPathIcon = appPath + "sites" + File.separator + sampleSiteName + File.separator;
        //目标文件路径
        String objPathIcon = appPath + "sites" + File.separator + sitename + File.separator;

        String srcfile = null;
        FileInputStream source = null;
        FileOutputStream destination = null;

        //拷贝文章列表图标
        File file = new File(srcPathIcon);
        int index = 0;
        List dirs = new ArrayList();
        dirs.add(file);

        try {
            do {
                file = (File) dirs.get(index);
                File[] fs = file.listFiles();
                dirs.remove(index);
                index = index - 1;
                if (fs != null) {
                    for (int i = 0; i < fs.length; i++) {
                        if (fs[i].isDirectory()) {
                            dirs.add(fs[i]);
                            index = index + 1;
                        } else {
                            srcfile = fs[i].getPath();
                            String extname = srcfile.substring(srcfile.lastIndexOf(".") + 1);
                            System.out.println("srcfile=" + srcfile + "====" + extname);
                            if (extname.equalsIgnoreCase("gif") || extname.equalsIgnoreCase("jpg") || extname.equalsIgnoreCase("jpeg") ||
                                    extname.equalsIgnoreCase("swf") || extname.equalsIgnoreCase("css") || extname.equalsIgnoreCase("js") ||
                                    extname.equalsIgnoreCase("vbs")) {
                                int posi = srcfile.indexOf(srcPathIcon);
                                String objfile = objPathIcon + srcfile.substring(posi + srcPathIcon.length());
                                String objpath = objfile.substring(0, objfile.lastIndexOf(File.separator));
                                File dir = new File(objpath);
                                if (!dir.exists()) dir.mkdirs();
                                source = new FileInputStream(srcfile);
                                destination = new FileOutputStream(objfile);
                                byte[] buffer = new byte[1024];
                                int bytes_read;

                                while (true) {
                                    bytes_read = source.read(buffer);
                                    if (bytes_read == -1) break;
                                    destination.write(buffer, 0, bytes_read);
                                }

                                source.close();
                                destination.close();

                                //发布文件到所有的WEB服务器
                                try {
                                    IPublishManager publishMgr = PublishPeer.getInstance();
                                    String localFileName = srcfile;
                                    //System.out.println("localFileName=" + localFileName);
                                    String dirName = srcfile.substring(posi + srcPathIcon.length()-1);
                                    posi = dirName.lastIndexOf(File.separator);
                                    dirName = dirName.substring(0,posi+1);
                                    //System.out.println("dirName=" + dirName);
                                    int errcode = publishMgr.publish(userid, localFileName, siteid, dirName, 0);
                                } catch (PublishException pex) {
                                    pex.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } while (dirs.size() != 0);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            srcPathIcon = null;
            objPathIcon = null;
            file = null;
        }
    }

//    "SELECT SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
//            "editor,Lockflag,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid" +
//            " FROM TBL_Mark WHERE siteid = ?";

    mark loadMark(ResultSet rs) throws SQLException {
        mark mark = new mark();
        try {
            mark.setSiteID(rs.getInt("SiteID"));
            mark.setColumnID(rs.getInt("ColumnID"));
            //mark.setContent(rs.getString("Content"));
            mark.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
            mark.setMarkType(rs.getInt("MarkType"));
            mark.setNotes(rs.getString("Notes"));
            mark.setLockFlag(rs.getInt("LockFlag"));
            mark.setLockEditor(rs.getString("lockeditor"));
            mark.setPubFlag(rs.getInt("PubFlag"));
            mark.setInnerHTMLFlag(rs.getInt("InnerHTMLFlag"));
            mark.setFormatFileNum(rs.getInt("formatfilenum"));
            mark.setChinesename(rs.getString("ChineseName"));
            mark.setRelatedColumnID(rs.getString("relatedcolumnid"));
            mark.setID(rs.getInt("ID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mark;
    }

//    "SELECT id,columnid,isarticle,relatedcolumnid,lockstatus,lockeditor," +
//            "defaulttemplate,chname,templatename,refermodelid,isIncluded,content FROM TBL_Template WHERE SiteID = ?";

    Model loadTemplate(ResultSet rs, String userid) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("id"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            model.setRelatedColumnIDs(rs.getString("relatedcolumnid"));
            model.setLockstatus(rs.getInt("lockstatus"));
            model.setLockEditor(rs.getString("lockeditor"));
            model.setDefaultTemplate(rs.getInt("defaulttemplate"));
            model.setChineseName(rs.getString("ChName"));
            model.setTemplateName(rs.getString("TemplateName"));
            model.setReferModelID(rs.getInt("refermodelid"));
            model.setIncluded(rs.getInt("isIncluded"));
            model.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
            model.setEditor(userid);
            model.setCreator(userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    Model loadTemplateNoContent(ResultSet rs, String userid) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("id"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            model.setRelatedColumnIDs(rs.getString("relatedcolumnid"));
            model.setLockstatus(rs.getInt("lockstatus"));
            model.setLockEditor(rs.getString("lockeditor"));
            model.setDefaultTemplate(rs.getInt("defaulttemplate"));
            model.setChineseName(rs.getString("ChName"));
            model.setTemplateName(rs.getString("TemplateName"));
            model.setReferModelID(rs.getInt("refermodelid"));
            model.setIncluded(rs.getInt("isIncluded"));
            //model.setContent(DBUtil.getBigString(cpool.getType(), rs, "content"));
            model.setEditor(userid);
            model.setCreator(userid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    public ViewFile loadviewfile(ResultSet rs) {
        ViewFile view = new ViewFile();
        try {
            view.setID(rs.getInt("id"));
            view.setSiteID(rs.getInt("siteid"));
            view.setType(rs.getInt("type"));
            view.setContent(StringUtil.gb2iso4View(rs.getString("content")));
            view.setChineseName(rs.getString("chinesename"));
            view.setEditor(rs.getString("editor"));
            view.setLockFlag(rs.getInt("lockflag"));
            view.setNotes(rs.getString("notes"));
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return view;
    }

    private String replaceColumnIDAndViewID_Of_MarkContent(String buf, List list, List vlist) {
        int startposi = 0;
        int endposi = 0;
        String tempbuf = "";
        String colids = "";
        String startbuf = "";
        String endbuf = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();
        relationBetweenOldViewAndNewView relNOView = new relationBetweenOldViewAndNewView();

        startposi = buf.indexOf("[COLUMNIDS]");
        endposi = buf.indexOf("[/COLUMNIDS]");

        if (startposi > -1 && endposi > -1) {
            startbuf = buf.substring(0, startposi + 12);
            tempbuf = buf.substring(startposi + 12, endposi - 1);
            endbuf = buf.substring(endposi - 1);

            //用新的ID替换例子网站的栏目ID
            //7874-getAllSubArticle
            Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
            String columnids[] = p.split(tempbuf);
            int colid = 0;
            for (int i = 0; i < columnids.length; i++) {
                startposi = columnids[i].indexOf("-getAllSubArticle");
                if (startposi == -1)
                    colid = Integer.parseInt(columnids[i]);
                else
                    colid = Integer.parseInt(columnids[i].substring(0, startposi));
                for (int j = 0; j < list.size(); j++) {
                    relNOCol = (relationBetweenOldColumnAndNewColumn) list.get(j);
                    if (relNOCol.getOldColumnID() == colid) {
                        if (startposi == -1)
                            colids = colids + relNOCol.getNewColumnID() + ",";
                        else
                            colids = colids + relNOCol.getNewColumnID() + "-getAllSubArticle,";
                        break;
                    }
                }
            }

            if (colids.length() > 0) {
                colids = colids.substring(0, colids.length() - 1);
                buf = startbuf + colids + endbuf;
            }

            //替换格式文件的ID
            startposi = buf.indexOf("[LISTTYPE]");
            endposi = buf.indexOf("[/LISTTYPE]");
            if (startposi > -1 && endposi > -1) {
                startbuf = buf.substring(0, startposi + 10);
                tempbuf = buf.substring(startposi + 10, endposi);
                endbuf = buf.substring(endposi);
                if (tempbuf != null && tempbuf != "") {
                    int viewid = Integer.parseInt(tempbuf);
                    for (int j = 0; j < vlist.size(); j++) {
                        relNOView = (relationBetweenOldViewAndNewView) vlist.get(j);
                        if (relNOView.getOldViewID() == viewid) {
                            viewid = relNOView.getNewViewID();
                            break;
                        }
                    }
                    buf = startbuf + viewid + endbuf;
                }
            }

            //System.out.println("buf=" + buf);
        }

        return buf;
    }

    private String replaceViewID_IN_Template(String buf, List vlist) {
        String tbuf = buf;

        //替换中文路径的格式文件ID
        Pattern p = Pattern.compile("\\[CHINESE_PATH\\][0-9]*\\[/CHINESE_PATH\\]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tbuf);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String thetag = tbuf.substring(start, end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf, thetag, newtag);
            tbuf = tbuf.substring(end);
            m = p.matcher(tbuf);
        }

        //替换英文路径的格式文件ID
        tbuf = buf;
        p = Pattern.compile("\\[ENGLISH_PATH\\][0-9]*\\[/ENGLISH_PATH\\]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(tbuf);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String thetag = tbuf.substring(start, end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf, thetag, newtag);
            tbuf = tbuf.substring(end);
            m = p.matcher(tbuf);
        }

        //替换导航条的格式文件ID
        tbuf = buf;
        p = Pattern.compile("\\[NAVBAR\\][0-9]*\\[/NAVBAR\\]", Pattern.CASE_INSENSITIVE);
        m = p.matcher(tbuf);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String thetag = tbuf.substring(start, end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf, thetag, newtag);
            tbuf = tbuf.substring(end);
            m = p.matcher(tbuf);
        }

        return buf;
    }

    private String replaceViewID(String tag_content, List vlist) {
        String tbuf = tag_content;
        int posi = tbuf.indexOf("]");
        String start_tag = tbuf.substring(0, posi + 1);
        tbuf = tbuf.substring(posi + 1);
        posi = tbuf.indexOf("[");
        int viewid = Integer.parseInt(tbuf.substring(0, posi));
        String end_tag = tbuf.substring(posi);

        relationBetweenOldViewAndNewView relNOView = new relationBetweenOldViewAndNewView();

        for (int j = 0; j < vlist.size(); j++) {
            relNOView = (relationBetweenOldViewAndNewView) vlist.get(j);
            if (relNOView.getOldViewID() == viewid) {
                viewid = relNOView.getNewViewID();
                break;
            }
        }

        tbuf = start_tag + viewid + end_tag;
        //System.out.println("tbuf=" + tbuf);

        return tbuf;
    }

    //替换tbl_template表中的relatedcolumnid字段的内容
    private String replaceColumnID_Of_RelatedColumnIDs(String buf, List list) {
        int startposi = 0;
        int endposi = 0;
        String tempbuf = "";
        String colids = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();

        startposi = buf.indexOf("(");
        endposi = buf.indexOf(")");

        if (startposi > -1 && endposi > -1) {
            tempbuf = buf.substring(startposi + 1);
            endposi = tempbuf.indexOf(")");
            tempbuf = tempbuf.substring(0, endposi);

            //用新的ID替换例子网站的栏目ID
            Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
            String columnids[] = p.split(tempbuf);
            int colid = 0;
            for (int i = 0; i < columnids.length; i++) {
                colid = Integer.parseInt(columnids[i]);
                for (int j = 0; j < list.size(); j++) {
                    relNOCol = (relationBetweenOldColumnAndNewColumn) list.get(j);
                    if (relNOCol.getOldColumnID() == colid) {
                        colids = colids + relNOCol.getNewColumnID() + ",";
                        break;
                    }
                }
            }

            if (colids.length() > 0) {
                colids = colids.substring(0, colids.length() - 1);
                buf = "(" + colids + ")";
            }
        }

        return buf;
    }

    //替换tbl_mark表中的relatedcolumnid字段的内容
    private String replaceColumnID_IN_Mark_Of_RelatedColumnIDs(String buf, List list) {
        int startposi = 0;
        String tempbuf = "";
        String colids = "";
        String startbuf = "";
        String endbuf = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();

        if (buf != null) {
            if (buf.indexOf("(") > -1) tempbuf = buf.substring(buf.indexOf("(") + 1);
            if (tempbuf.indexOf(")") > -1) tempbuf = tempbuf.substring(0, tempbuf.indexOf(")"));

            //用新的ID替换例子网站的栏目ID
            if (tempbuf!=null) {
                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                String columnids[] = p.split(tempbuf);
                int colid = 0;
                for (int i = 0; i < columnids.length; i++) {
                    if (!columnids[i].isEmpty()) {
                        startposi = columnids[i].indexOf("-getAllSubArticle");
                        if (startposi == -1)
                            colid = Integer.parseInt(columnids[i].trim());
                        else
                            colid = Integer.parseInt(columnids[i].trim().substring(0, startposi));
                        for (int j = 0; j < list.size(); j++) {
                            relNOCol = (relationBetweenOldColumnAndNewColumn) list.get(j);
                            if (relNOCol.getOldColumnID() == colid) {
                                if (startposi == -1)
                                    colids = colids + relNOCol.getNewColumnID() + ",";
                                else
                                    colids = colids + relNOCol.getNewColumnID() + "-getAllSubArticle,";
                                break;
                            }
                        }
                    }
                }

                if (colids.length() > 0) {
                    colids = colids.substring(0, colids.length() - 1);
                    buf = startbuf + colids + endbuf;
                }
            }
        }

        return buf;
    }

    private String replaceMarkid(String thetag, List NOMarkList, List NOColList) {
        String tbuf = thetag.substring(8, thetag.length() - 9);
        int posi = tbuf.indexOf("_");
        int markid = 0;
        int columnid = 0;
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();
        relationBetweenOldMarkAndNewMark relNOMark = new relationBetweenOldMarkAndNewMark();

        if (posi > -1) {
            markid = Integer.parseInt(tbuf.substring(0, posi));
            for (int j = 0; j < NOMarkList.size(); j++) {
                relNOMark = (relationBetweenOldMarkAndNewMark) NOMarkList.get(j);
                if (relNOMark.getOldMarkID() == markid) {
                    markid = relNOMark.getNewMarkID();
                    break;
                }
            }

            columnid = Integer.parseInt(tbuf.substring(posi + 1));
            for (int j = 0; j < NOColList.size(); j++) {
                relNOCol = (relationBetweenOldColumnAndNewColumn) NOColList.get(j);
                if (relNOCol.getOldColumnID() == columnid) {
                    columnid = relNOCol.getNewColumnID();
                    break;
                }
            }
            tbuf = markid + "_" + columnid;
        } else {
            markid = Integer.parseInt(tbuf);
            for (int j = 0; j < NOMarkList.size(); j++) {
                relNOMark = (relationBetweenOldMarkAndNewMark) NOMarkList.get(j);
                if (relNOMark.getOldMarkID() == markid) {
                    markid = relNOMark.getNewMarkID();
                    break;
                }
            }
            tbuf = String.valueOf(markid);
        }

        return "[MARKID]" + tbuf + "[/MARKID]";
    }

    private static final String SQL_GET_SITE =
            "SELECT * FROM TBL_SiteInfo WHERE SiteID = ?";

    public Register getSite(int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Register register = null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SITE);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();
            if (rs.next())
                register = load(rs);
            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return register;
    }

    private static final String SQL_UPDATE_SITE_INFO =
            "UPDATE TBL_SiteInfo SET BindFlag=?,ImagesDir=?,cssjsdir=?,tcFlag=?,wapflag=?,encodeflag=?,PubFlag=?,BeRefered=?,Config=?,copyColumn=?," +
                    "beCopyColumn=?,pushArticle=?,moveArticle=?, titlepic=?,vtitlepic=?,sourcepic=?,authorpic=?,contentpic=?," +
                    "specialpic=?,productpic=?,productsmallpic=?,mediasize=?,mediapicsize=? WHERE SiteID=?";

    private static final String SQL_UPDATE_COLUMN =
            "UPDATE TBL_Column SET Extname = ?,  titlepic=?,vtitlepic=?,sourcepic=?,authorpic=?,contentpic=?," +
                    "specialpic=?,productpic=?,productsmallpic=?,mediasize=?,mediapicsize=? WHERE SiteID = ? AND ParentID = 0";

    public int update(Register register) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt=null;
        int errcode = 1;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(SQL_UPDATE_SITE_INFO);
                pstmt.setInt(1, register.getBindFlag());
                pstmt.setInt(2, register.getImagesDir());
                pstmt.setInt(3, register.getCssjsDir());
                pstmt.setInt(4, register.getTCFlag());
                pstmt.setInt(5, register.getWapFlag());
                pstmt.setInt(6,register.getEncoding());
                pstmt.setInt(7, register.getPubFlag());
                pstmt.setInt(8, register.getBeRefered());
                pstmt.setString(9, register.getConfigInfo());
                pstmt.setInt(10, register.getCopyColumn());
                pstmt.setInt(11, register.getBeCopyColumn());
                pstmt.setInt(12, register.getPushArticle());
                pstmt.setInt(13, register.getMoveArticle());
                pstmt.setString(14, register.getTitlepic());
                pstmt.setString(15, register.getVtitlepic());
                pstmt.setString(16, register.getSourcepic());
                pstmt.setString(17, register.getAuthorpic());
                pstmt.setString(18, register.getContentpic());
                pstmt.setString(19, register.getSpecialpic());
                pstmt.setString(20, register.getProductpic());
                pstmt.setString(21, register.getProductsmallpic());
                pstmt.setString(22,register.getMediasize());
                pstmt.setString(23,register.getMediapicsize());
                pstmt.setInt(24, register.getSiteID());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_UPDATE_COLUMN);
                pstmt.setString(1, register.getExtName());
                pstmt.setString(2, register.getTitlepic());
                pstmt.setString(3, register.getVtitlepic());
                pstmt.setString(4, register.getSourcepic());
                pstmt.setString(5, register.getAuthorpic());
                pstmt.setString(6, register.getContentpic());
                pstmt.setString(7, register.getSpecialpic());
                pstmt.setString(8, register.getProductpic());
                pstmt.setString(9, register.getProductsmallpic());
                pstmt.setString(10,register.getMediasize());
                pstmt.setString(11,register.getMediapicsize());
                pstmt.setInt(12, register.getSiteID());
                pstmt.executeUpdate();
                pstmt.close();

                conn.commit();
            } catch (Exception e) {
                errcode = -1;
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update SiteInfo failed.");
            } finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            errcode = -1;
            throw new CmsException("Database exception: can't rollback?");
        }

        return errcode;
    }

    private static final String SQL_UPDATE_PUBFLAG = "UPDATE TBL_SiteInfo SET pubflag = ? WHERE siteid = ?";

    //更新某站点文章发布方式
    public void update_pubflag(int siteID, int pubflag) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_PUBFLAG);
                pstmt.setInt(1, pubflag);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update SiteInfo failed.");
            } finally {
                try {
                    if (conn != null)

                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_UPDATE_SITEPIC =
            "UPDATE TBL_SiteInfo SET sitepic = ? WHERE siteid = ?";

    //更新某站点文章发布方式
    public void update_sitepic(int siteID, String sitepic) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;

        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATE_SITEPIC);
                pstmt.setString(1, sitepic);
                pstmt.setInt(2, siteID);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update SiteInfo failed.");
            } finally {
                try {
                    if (conn != null)
                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
    }

    private static final String SQL_QUERY_PUBFLAG =
            "SELECT pubflag FROM TBL_SiteInfo WHERE siteid = ?";

    //查询某站点文章发布方式
    public int query_pubflag(int siteID) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int pubflag = -1;

        try {
            try {
                conn = cpool.getConnection();
                pstmt = conn.prepareStatement(SQL_QUERY_PUBFLAG);
                pstmt.setInt(1, siteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    pubflag = rs.getInt("pubflag");
                }

                rs.close();
                pstmt.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
                conn.rollback();
                throw new CmsException("Database exception: update SiteInfo failed.");
            } finally {
                try {
                    if (conn != null)

                        cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new CmsException("Database exception: can't rollback?");
        }
        return pubflag;
    }

    private static final String SQL_GET_SITEID =
            "SELECT MAX(SiteID) AS ID FROM TBL_SiteInfo";

    public int getSiteID() throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        int ID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SITEID);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ID = rs.getInt("ID");
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ++ID;
    }

    private static final String SQL_GET_SITE_ID =
            "SELECT SiteID FROM TBL_SiteInfo WHERE SiteName = ? ORDER BY CreateDate DESC";

    public int getSite_ID(String siteName) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        int siteID = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_SITE_ID);
            pstmt.setString(1, siteName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                siteID = rs.getInt("SiteID");
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return siteID;
    }

    private static final String SQL_QUERY_SITENAME =
            "SELECT * FROM TBL_SiteInfo WHERE SiteName = ?";

    public boolean QuerySiteName(String siteName) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        boolean isExit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_SITENAME);
            pstmt.setString(1, siteName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                isExit = true;
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isExit;
    }

    private static final String SQL_QUERY_USERNAME =
            "SELECT * FROM TBL_Members WHERE UserID = ?";

    //判断有无相同的用户名
    public boolean queryUsername(String username) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        boolean isExit = false;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_QUERY_USERNAME);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                isExit = true;
            }

            rs.close();
            pstmt.close();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    cpool.freeConnection(conn);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return isExit;
    }

    private boolean SendPass(String to, String subject, String body) {
        boolean success = false;

        try {
            String from = "bet@163.net";

            SendMail mail = new SendMail("smtp.163.net");

            mail.setFrom(from);
            mail.setTo(to);
            mail.setSubject(subject);
            mail.setBody(body);
            mail.setNeedAuth(true);

            success = mail.sendout();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }


    private static final String SQL_GET_PASSWORD =
            "SELECT a.Userpwd,b.Email FROM TBL_Members a,TBL_SiteInfo b WHERE " +
                    "a.UserID = ? AND a.SiteID = b.SiteID AND b.SiteName = ? AND b.BindFlag = 1";

    private static final String SQL_UPDATE_PASSWORD =
            "UPDATE TBL_Members SET Userpwd = ? WHERE UserID = ?";

    public void getPassword(String userID, String siteName) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            boolean success = true;
            conn = cpool.getConnection();

            pstmt = conn.prepareStatement(SQL_GET_PASSWORD);
            pstmt.setString(1, userID);
            pstmt.setString(2, siteName);
            rs = pstmt.executeQuery();

            String email = null;
            while (rs.next()) {
                email = rs.getString("Email");
            }

            rs.close();
            pstmt.close();

            if (email != null) {
                //生成新密码
                String password;
                RandomStrg rstr = new RandomStrg();
                try {
                    rstr.setCharset("a-zA-Z0-9");
                    rstr.setLength(5);
                    rstr.generateRandomObject();
                    password = rstr.getRandom();
                } catch (Exception e) {
                    password = userID;
                    success = false;
                }

                //密码加密
                String md5_pass = Encrypt.md5(password.getBytes());

                //先发送密码
                try {
                    if (email.length() > 0) {
                        String subject = "您取回的北京盈商动力公司在线CMS服务的密码！";
                        String body = "<meta http-equiv=Content-Type " +
                                "content=text/html; charset=gb2312>" +
                                "<p>您好！欢迎您使用北京盈商动力公司的在线CMS服务！</p>" +
                                "<p>您的域名是：" + siteName + "</p>" +
                                "<p>您的用户名是：" + userID + "</p>" +
                                "<p>您的新密码是：" + password + "</p>" +
                                "<p><a href=http://www.bizwink.com.cn target=_blank>" +
                                "北京盈商动力软件有限公司</a></p>";

                        success = SendPass(email, subject, body);
                    }
                } catch (Exception m) {
                    m.printStackTrace();
                    success = false;
                }

                //再更新数据库
                if (success) {
                    pstmt = conn.prepareStatement(SQL_UPDATE_PASSWORD);
                    pstmt.setString(1, md5_pass);
                    pstmt.setString(2, userID);

                    pstmt.executeUpdate();
                    pstmt.close();
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                if (conn != null)

                    cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //拷贝文章列表图标及HTML碎片文件到CMS
    public void copyIconToCMS(int siteID, String dname, String rootPath) throws CmsException {
        //源文件路径
        String srcPath = rootPath + "template" + File.separator + "ListStyle" + File.separator + "listimages" + File.separator;

        //目标文件路径
        dname = StringUtil.replace(dname, ".", "_");
        String objPath = rootPath + "sites" + File.separator + dname + File.separator + "_sys_ListImages" + File.separator;

        //拷贝图标
        File file = new File(srcPath);
        try {
            if (file.exists()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isFile()) {
                        String name = files[i].getName();
                        FileDeal.copy(srcPath + name, objPath + name, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //拷贝文章列表图标及HTML碎片文件到WEB
    public void copyIconToWEB(String username, int siteID, String rootPath) throws CmsException {
        //源文件路径
        String srcPathIcon = rootPath + "template" + File.separator + "ListStyle" + File.separator + "listimages" + File.separator;
        //目标文件路径
        String objPathIcon = File.separator + "_sys_ListImages" + File.separator;

        //拷贝文章列表图标
        File file = new File(srcPathIcon);

        try {
            if (file.exists()) {
                File[] files = file.listFiles();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        if (files[i].canRead() && files[i].isFile()) {
                            String fname = files[i].getName();
                            IPublishManager publishMgr = PublishPeer.getInstance();
                            publishMgr.publish(username, srcPathIcon + fname, siteID, objPathIcon, 0);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ViewFile loadvf(ResultSet rs) {
        ViewFile vf = new ViewFile();
        try {
            vf.setID(rs.getInt("id"));
            vf.setSiteID(rs.getInt("siteid"));
            vf.setType(rs.getInt("type"));
            vf.setChineseName(rs.getString("chinesename"));
            vf.setContent(rs.getString("content"));
            vf.setEditor(rs.getString("editor"));
            vf.setNotes(rs.getString("notes"));
            vf.setLockFlag(rs.getInt("lockflag"));
            vf.setLastUpdated(rs.getTimestamp("updatedate"));
            vf.setCreateDate(rs.getTimestamp("createdate"));
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return vf;
    }

    Register load(ResultSet rs) throws SQLException {
        Register register = new Register();
        register.setSiteID(rs.getInt("SiteID"));
        register.setSiteName(rs.getString("SiteName"));
        register.setImagesDir(rs.getInt("ImagesDir"));
        register.setCssjsDir(rs.getInt("cssjsdir"));
        register.setTCFlag(rs.getInt("tcFlag"));
        register.setWapFlag(rs.getInt("wapflag"));
        register.setPubFlag(rs.getInt("PubFlag"));
        register.setBindFlag(rs.getInt("BindFlag"));
        register.setBeRefered(rs.getInt("BeRefered"));
        register.setCopyColumn(rs.getInt("copyColumn"));
        register.setBeCopyColumn(rs.getInt("beCopyColumn"));
        register.setPushArticle(rs.getInt("pushArticle"));
        register.setMoveArticle(rs.getInt("moveArticle"));
        register.setConfigInfo(rs.getString("Config"));
        register.setCreateDate(rs.getTimestamp("CreateDate"));
        register.setTitlepic(rs.getString("titlepic"));
        register.setVtitlepic(rs.getString("vtitlepic"));
        register.setSourcepic(rs.getString("sourcepic"));
        register.setAuthorpic(rs.getString("authorpic"));
        register.setContentpic(rs.getString("contentpic"));
        register.setSpecialpic(rs.getString("specialpic"));
        register.setProductpic(rs.getString("productpic"));
        register.setProductsmallpic(rs.getString("productsmallpic"));
        register.setMediasize(rs.getString("mediasize"));
        register.setMediapicsize(rs.getString("mediapicsize"));
        register.setTs_pic(rs.getString("ts_pic"));
        register.setS_pic(rs.getString("s_pic"));
        register.setMs_pic(rs.getString("ms_pic"));
        register.setM_pic(rs.getString("m_pic"));
        register.setMl_pic(rs.getString("ml_pic"));
        register.setL_pic(rs.getString("l_pic"));
        register.setTl_pic(rs.getString("tl_pic"));
        return register;
    }

    private static final String SQL_CREATE_COOSITE_SITE_FOR_ORACLE =
            "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,PubFlag,BindFlag," +
                    "CreateDate,Berefered,sitelogo,banner,navigator,sitevalid,samsiteid,SiteID,sitetype,sharetemplatenum," +
                    "copyright,titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_COOSITE_SITE_FOR_MSSQL =
            "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,PubFlag,BindFlag," +
                    "CreateDate,Berefered,sitelogo,banner,navigator,sitevalid,samsiteid,sitetype,sharetemplatenum,copyright,titlepic," +
                    "vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);SELECT scope_identity();";

    private static final String SQL_CREATE_COOSITE_SITE_FOR_MYSQL =
            "INSERT INTO TBL_SiteInfo (SiteName,siteip,ImagesDir,cssjsDir,tcFlag,wapflag,PubFlag,BindFlag," +
                    "CreateDate,Berefered,sitelogo,banner,navigator,sitevalid,samsiteid,sitetype,sharetemplatenum,copyright," +
                    "titlepic,vtitlepic,sourcepic,authorpic,contentpic,specialpic,productpic,productsmallpic)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_SITE_HOST_FOR_ORACLE = "INSERT INTO TBL_SiteIPInfo (siteid,siteip,sitename,docpath,ftpuser,ftppasswd,ftptype,publishway,status,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_SITE_HOST_FOR_MSSQL = "INSERT INTO TBL_SiteIPInfo (siteid,siteip,sitename,docpath,ftpuser,ftppasswd,ftptype,publishway,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);SELECT scope_identity();";

    private static final String SQL_CREATE_SITE_HOST_FOR_MYSQL = "INSERT INTO TBL_SiteIPInfo (siteid,siteip,sitename,docpath,ftpuser,ftppasswd,ftptype,publishway,status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_COMPANYCLASS_FOR_ORACLE = "INSERT INTO tbl_companyclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_COMPANYCLASS_FOR_MSSQL = "INSERT INTO tbl_companyclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_COMPANYCLASS_FOR_MYSQL = "INSERT INTO tbl_companyclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_WEBSITECLASS_FOR_ORACLE = "INSERT INTO tbl_websiteclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_WEBSITECLASS_FOR_MSSQL = "INSERT INTO tbl_websiteclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String INSERT_WEBSITECLASS_FOR_MYSQL = "INSERT INTO tbl_websiteclass (siteid,parentid,orderid,cname,ename,dirname,editor,extname,createdate," +
            "lastupdated) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_INSERT_DNS_RECORD = "INSERT INTO records(zone,host,type,data,ttl,mx_priority,refresh,retry," +
            "expire,minimum,serial,resp_contact,primary_ns) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public int insertcreate(Register register, String appPath) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        Connection mysql_conn = null;
        PreparedStatement mysql_pstmt =  null;
        String sitename = register.getSiteName();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int errcode = 0;
        int rootColumnID = 0;
        int reg_dns_flag = 0;
        String www_ip = null;
        String ftp_ip = null;
        String docroot = null;
        String ftpuser = null;
        String ftppasswd = null;
        int flag = 0;
        int siteID = 0;

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select ipaddress,wwwip,docroot,ftpuser,ftppasswd,flag from tbl_sites_number t where t.sitesnum = (select min(sitesnum) from tbl_sites_number)");
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                www_ip = res.getString("wwwip");
                ftp_ip = res.getString("ipaddress");
                docroot = res.getString("docroot");
                ftpuser = res.getString("ftpuser");
                ftppasswd = res.getString("ftppasswd");
                flag = res.getInt("flag");
            }
            res.close();
            pstmt.close();
        } catch (SQLException exp) {
            System.out.println("从数据库获取WWW服务器信息出现错误");
            errcode = -10;
        }


        //在DNS服务器的数据库表中加入域名解析记录
        if (sitename.endsWith("coosite.com") && errcode == 0) {
            try {
                //'coosite.com', 'petersong', 'A', '211.154.43.210', '800', '28800' , '14400' , '10', NULL ,NULL , NULL , NULL , NULL;
                Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                //jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=utf8
                mysql_conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dns?characterEncoding=gbk","dns","1qaz2wsx");
                mysql_conn.setAutoCommit(false);
                mysql_pstmt = mysql_conn.prepareStatement(SQL_INSERT_DNS_RECORD);
                mysql_pstmt.setString(1, "coosite.com");
                mysql_pstmt.setString(2, register.getUserID());
                mysql_pstmt.setString(3, "A");
                mysql_pstmt.setString(4, www_ip);
                mysql_pstmt.setInt(5, 86400);
                mysql_pstmt.setNull(6, java.sql.Types.VARCHAR);
                mysql_pstmt.setInt(7, 14400);
                mysql_pstmt.setInt(8, 10);
                mysql_pstmt.setInt(9, 604800);
                mysql_pstmt.setInt(10, 800);
                mysql_pstmt.setNull(11, java.sql.Types.BIGINT);
                mysql_pstmt.setNull(12, java.sql.Types.VARCHAR);
                mysql_pstmt.setNull(13, java.sql.Types.VARCHAR);
                mysql_pstmt.executeUpdate();
                mysql_pstmt.close();
                mysql_conn.commit();
            } catch (Exception sqlexp) {
                sqlexp.printStackTrace();
                reg_dns_flag = -1;
                errcode = -11;
                System.out.println("增加DNS记录出现错误");
            }
        }

        try {
            if (reg_dns_flag == 0 && errcode == 0) {
                Timestamp now = new Timestamp(System.currentTimeMillis());
                conn.setAutoCommit(false);
                if (cpool.getType().equals("oracle")) {
                    siteID = sequnceMgr.getSequenceNum("Site");
                    pstmt = conn.prepareStatement(SQL_CREATE_COOSITE_SITE_FOR_ORACLE);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, www_ip);
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7, register.getPubFlag());
                    pstmt.setInt(8, register.getBindFlag());
                    pstmt.setTimestamp(9, now);
                    pstmt.setInt(10, 0);
                    pstmt.setString(11, register.getLogo());
                    pstmt.setString(12, register.getBanner());
                    pstmt.setInt(13, register.getNavigator());
                    pstmt.setInt(14, 1);                                       //1--表示站点有效，0-表示站点暂时停止对外服务
                    pstmt.setInt(15, register.getSamsite());
                    pstmt.setInt(16, siteID);
                    pstmt.setInt(17, 2);                                       //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setInt(18, register.getSharetemplatenum());
                    pstmt.setString(19, register.getCopyright());
                    pstmt.setString(20, register.getTitlepic());
                    pstmt.setString(21, register.getVtitlepic());
                    pstmt.setString(22, register.getSourcepic());
                    pstmt.setString(23, register.getAuthorpic());
                    pstmt.setString(24, register.getContentpic());
                    pstmt.setString(25, register.getSpecialpic());
                    pstmt.setString(26, register.getProductpic());
                    pstmt.setString(27, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_ORACLE);
                    rootColumnID = sequnceMgr.getSequenceNum("Column");
                    pstmt.setInt(1, rootColumnID);
                    pstmt.setInt(2, siteID);
                    pstmt.setInt(3, 0);
                    pstmt.setInt(4, 1);
                    pstmt.setString(5, register.getSiteName());
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "/");
                    pstmt.setString(8, "admin");
                    pstmt.setString(9, register.getExtName());
                    pstmt.setTimestamp(10, now);
                    pstmt.setTimestamp(11, now);
                    pstmt.setString(12, register.getTitlepic());
                    pstmt.setString(13, register.getVtitlepic());
                    pstmt.setString(14, register.getSourcepic());
                    pstmt.setString(15, register.getAuthorpic());
                    pstmt.setString(16, register.getContentpic());
                    pstmt.setString(17, register.getSpecialpic());
                    pstmt.setString(18, register.getProductpic());
                    pstmt.setString(19, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //增加主机信息
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_HOST_FOR_ORACLE);
                    pstmt.setInt(1,siteID);
                    pstmt.setString(2, ftp_ip);
                    pstmt.setString(3, sitename);
                    pstmt.setString(4, docroot + "/" + register.getUserID());
                    pstmt.setString(5,ftpuser);
                    pstmt.setString(6,ftppasswd);
                    pstmt.setInt(7, flag);                //ftp发布=0 sftp发布=1
                    pstmt.setInt(8, 0);                   //发布方式是FTP发布
                    pstmt.setInt(9, 1);                   //status
                    pstmt.setInt(10,sequnceMgr.getSequenceNum("SiteIPInfo"));
                    pstmt.executeUpdate();
                    pstmt.close();

                } else if (cpool.getType().equals("mssql")) {
                    pstmt = conn.prepareStatement(SQL_CREATE_COOSITE_SITE_FOR_MSSQL);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, "127.0.0.1");
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7, register.getPubFlag());
                    pstmt.setInt(8, register.getBindFlag());
                    pstmt.setTimestamp(9, now);
                    pstmt.setInt(10, 0);
                    pstmt.setString(11, register.getLogo());
                    pstmt.setString(12, register.getBanner());
                    pstmt.setInt(13, register.getNavigator());
                    pstmt.setInt(14, 1);                                       //一般注册的站点设置为1，表示为普通站点
                    pstmt.setInt(15, register.getSamsite());
                    pstmt.setInt(16,2);                                        //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setInt(17, register.getSharetemplatenum());
                    pstmt.setString(18, register.getCopyright());
                    pstmt.setString(19, register.getTitlepic());
                    pstmt.setString(20, register.getVtitlepic());
                    pstmt.setString(21, register.getSourcepic());
                    pstmt.setString(22, register.getAuthorpic());
                    pstmt.setString(23, register.getContentpic());
                    pstmt.setString(24, register.getSpecialpic());
                    pstmt.setString(25, register.getProductpic());
                    pstmt.setString(26, register.getProductsmallpic());
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        siteID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_MSSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 1);
                    pstmt.setString(4, register.getSiteName());
                    pstmt.setString(5, "/");
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setString(11, register.getTitlepic());
                    pstmt.setString(12, register.getVtitlepic());
                    pstmt.setString(13, register.getSourcepic());
                    pstmt.setString(14, register.getAuthorpic());
                    pstmt.setString(15, register.getContentpic());
                    pstmt.setString(16, register.getSpecialpic());
                    pstmt.setString(17, register.getProductpic());
                    pstmt.setString(18, register.getProductsmallpic());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        rootColumnID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    //增加主机信息
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_HOST_FOR_MSSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setString(2, ftp_ip);
                    pstmt.setString(3, sitename);
                    pstmt.setString(4, docroot + "/" + register.getUserID());
                    pstmt.setString(5,ftpuser);
                    pstmt.setString(6, ftppasswd);
                    pstmt.setInt(7, flag);                //ftp发布=0 sftp发布=1
                    pstmt.setInt(8, 0);                   //发布方式是FTP发布
                    pstmt.setInt(9, 1);                   //status
                    pstmt.executeUpdate();
                    pstmt.close();

                } else if (cpool.getType().equals("mysql")) {
                    pstmt = conn.prepareStatement(SQL_CREATE_COOSITE_SITE_FOR_MYSQL);
                    pstmt.setString(1, register.getSiteName());
                    pstmt.setString(2, "127.0.0.1");
                    pstmt.setInt(3, register.getImagesDir());
                    pstmt.setInt(4, register.getCssjsDir());
                    pstmt.setInt(5, register.getTCFlag());
                    pstmt.setInt(6, register.getWapFlag());
                    pstmt.setInt(7, register.getPubFlag());
                    pstmt.setInt(8, register.getBindFlag());
                    pstmt.setTimestamp(9, now);
                    pstmt.setInt(10, 0);
                    pstmt.setString(11, register.getLogo());
                    pstmt.setString(12, register.getBanner());
                    pstmt.setInt(13, register.getNavigator());
                    pstmt.setInt(14, 1);                                       //一般注册的站点设置为1，表示为普通站点
                    pstmt.setInt(15, register.getSamsite());
                    pstmt.setInt(16,2);                                        //0--表示自定义站点，1-表示共享栏目和模板的站点，2--表示拷贝模板站点的栏目结构和模板
                    pstmt.setInt(17, register.getSharetemplatenum());
                    pstmt.setString(18, register.getCopyright());
                    pstmt.setString(19, register.getTitlepic());
                    pstmt.setString(20, register.getVtitlepic());
                    pstmt.setString(21, register.getSourcepic());
                    pstmt.setString(22, register.getAuthorpic());
                    pstmt.setString(23, register.getContentpic());
                    pstmt.setString(24, register.getSpecialpic());
                    pstmt.setString(25, register.getProductpic());
                    pstmt.setString(26, register.getProductsmallpic());
                    pstmt.executeUpdate();
                    pstmt.close();

                    //获取Mysql自增列的值siteid
                    pstmt = conn.prepareStatement("SELECT LAST_INSERT_ID()");
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) siteID = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_COLUMN_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setInt(2, 0);
                    pstmt.setInt(3, 1);
                    pstmt.setString(4, register.getSiteName());
                    pstmt.setString(5, "/");
                    pstmt.setString(6, "/");
                    pstmt.setString(7, "admin");
                    pstmt.setString(8, register.getExtName());
                    pstmt.setTimestamp(9, now);
                    pstmt.setTimestamp(10, now);
                    pstmt.setString(11, register.getTitlepic());
                    pstmt.setString(12, register.getVtitlepic());
                    pstmt.setString(13, register.getSourcepic());
                    pstmt.setString(14, register.getAuthorpic());
                    pstmt.setString(15, register.getContentpic());
                    pstmt.setString(16, register.getSpecialpic());
                    pstmt.setString(17, register.getProductpic());
                    pstmt.setString(18, register.getProductsmallpic());
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        rootColumnID = rs.getInt(1);
                    }
                    rs.close();
                    pstmt.close();

                    //增加主机信息
                    pstmt = conn.prepareStatement(SQL_CREATE_SITE_HOST_FOR_MYSQL);
                    pstmt.setInt(1, siteID);
                    pstmt.setString(2, ftp_ip);
                    pstmt.setString(3, sitename);
                    pstmt.setString(4, docroot + "/" + register.getUserID());
                    pstmt.setString(5,ftpuser);
                    pstmt.setString(6, ftppasswd);
                    pstmt.setInt(7, flag);                //ftp发布=0 sftp发布=1
                    pstmt.setInt(8, 0);                   //发布方式是FTP发布
                    pstmt.setInt(9, 1);                   //status
                    pstmt.executeUpdate();
                    pstmt.close();

                }

                String SQL_CREATE_SITE_MEMBER = "INSERT INTO TBL_Members (id,SiteID,UserID,Userpwd,NickName,EMAIL,mphone,company,address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(SQL_CREATE_SITE_MEMBER);
                pstmt.setInt(1, sequnceMgr.getSequenceNum("MEMBERSID"));
                pstmt.setInt(2, siteID);
                pstmt.setString(3, register.getUserID());
                pstmt.setString(4, Encrypt.md5(register.getPassword().getBytes()));
                pstmt.setString(5, register.getUsername());
                pstmt.setString(6, register.getEmail());
                pstmt.setString(7, register.getMphone());
                pstmt.setString(8, register.getCompany());
                pstmt.setString(9, register.getAddress());
                pstmt.executeUpdate();
                pstmt.close();

                pstmt = conn.prepareStatement(SQL_CREATE_SITE_MASTER_RIGHT);
                pstmt.setString(1, register.getUserID());
                pstmt.setInt(2, 0);
                pstmt.setInt(3, 54);
                pstmt.executeUpdate();
                pstmt.close();

                //获取需要发布的作业
                List queueList = new ArrayList();
                Publish publish = null;
                String SQL_Select_Model = "select id,columnid,chname from tbl_template where isarticle = 2 and tempnum=? and siteid=?";
                pstmt = conn.prepareStatement(SQL_Select_Model);
                pstmt.setInt(1, register.getSharetemplatenum());
                pstmt.setInt(2,register.getSamsite());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && cpool.getPublishWay() == 1) {
                    publish = new Publish();
                    publish.setSiteID(siteID);
                    publish.setTargetID(rs.getInt("id"));
                    publish.setColumnID(rs.getInt("columnid"));
                    publish.setTitle(rs.getString("chname"));
                    publish.setObjectType(2);
                    queueList.add(publish);
                }

                //将需要发布的文章和模板加入到发布队列
                if (cpool.getPublishWay() == 1) {
                    PreparedStatement pstmt1 = null;
                    if (cpool.getType().equalsIgnoreCase("oracle"))
                        pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_ORACLE);
                    else if (cpool.getType().equalsIgnoreCase("mssql"))
                        pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MSSQL);
                    else
                        pstmt1 = conn.prepareStatement(SQL_INSERT_PUBLISH_QUEUE_FOR_MYSQL);
                    for (int i = 0; i < queueList.size(); i++) {
                        publish = (Publish) queueList.get(i);
                        boolean exist_onejob = false;
                        String SQL_GetOneJob = "select * from tbl_new_publish_queue where siteid=? and columnid=? and targetid=? and type=?";
                        pstmt=conn.prepareStatement(SQL_GetOneJob);
                        pstmt.setInt(1,siteID);
                        pstmt.setInt(2,rootColumnID);
                        pstmt.setInt(3,publish.getTargetID());
                        pstmt.setInt(4,publish.getObjectType());
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            exist_onejob = true;
                        }
                        rs.close();
                        pstmt.close();

                        if (exist_onejob == false) {
                            pstmt1.setInt(1, siteID);
                            pstmt1.setInt(2, rootColumnID);
                            pstmt1.setInt(3, publish.getTargetID());
                            pstmt1.setInt(4, publish.getObjectType());
                            pstmt1.setInt(5, 1);
                            pstmt1.setTimestamp(6, now);
                            pstmt1.setTimestamp(7, now);
                            pstmt1.setString(8, "");
                            pstmt1.setString(9, StringUtil.iso2gbindb(publish.getTitle()));
                            pstmt1.setInt(10,0);
                            if (cpool.getType().equals("oracle")) {
                                pstmt1.setInt(11, sequnceMgr.getSequenceNum("PublishQueue"));
                                pstmt1.executeUpdate();
                            } else if (cpool.getType().equals("mssql")) {
                                pstmt1.executeUpdate();
                            } else {
                                pstmt1.executeUpdate();
                            }
                        }
                    }
                    pstmt1.close();
                }

                //修改HASH表内站点数量的记录,并提交对数据库内容的修改
                pstmt = conn.prepareStatement("UPDATE tbl_sites_number SET sitesnum=sitesnum+1 WHERE ipaddress=?");
                pstmt.setString(1, ftp_ip);
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
                errcode = siteID;
            }
        } catch (Exception e) {
            errcode = -12;
            e.printStackTrace();
            System.out.println("生成ORACLE数据库的各项数据出现错误");
        } finally {
            try {
                if (conn != null) {
                    conn.rollback();
                    cpool.freeConnection(conn);
                }
            } catch (Exception e) {
                System.out.println("释放数据库连接或者是回退ORACLE连接出现错误 ");
            }
        }

        try {
            //ORACLE数据库注册用户信息失败，回退DNS的注册信息，否则就关闭MYSQL的数据库链接
            if (reg_dns_flag == 0 && errcode < 0) {
                mysql_conn.setAutoCommit(false);
                mysql_pstmt = mysql_conn.prepareStatement("delete from records where host=?");
                mysql_pstmt.setString(1, register.getUserID());
                mysql_pstmt.executeUpdate();
                mysql_pstmt.close();
                mysql_conn.commit();
                mysql_conn.close();
            } else {
                if (mysql_conn!=null) {
                    mysql_conn.close();
                }
            }
        } catch (Exception sqlexp) {
            sqlexp.printStackTrace();
            reg_dns_flag = -2;
            System.out.println("DNS记录回退出现问题");
        }

        return errcode;
    }

    public String getIPByCode() throws CmsException {
        String ip = null;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet res = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select ipaddress from tbl_sites_number t where t.sitesnum = (select min(sitesnum) from tbl_sites_number)");
            //pstmt = conn.prepareStatement("SELECT ipaddress FROM tbl_sites_number WHERE hashcode=?");
            //pstmt.setInt(1, code);
            res = pstmt.executeQuery();
            if (res.next()) {
                ip = res.getString("ipaddress");
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {

                }
            }
        }

        return ip;
    }


    public int getCode() throws CmsException {
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet res = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT hashcode FROM tbl_sites_number ORDER BY sitesnum ASC");
            res = pstmt.executeQuery();
            if (res.next()) {
                code = res.getInt("hashcode");
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {

                }
            }
        }

        return code;
    }


    public resinConfig getResinConfig() {
        resinConfig config = new resinConfig();
        config.setErrorCode(0);

        try {
            SAXBuilder builder = new SAXBuilder();
            URL xmlfileurl = this.getClass().getResource("/com/bizwink/cms/register/COO_SETTING.xml");
            Document doc = builder.build(xmlfileurl);
            Element ele = doc.getRootElement().getChild("FTP_INFO");
            config.setdocpath(ele.getAttributeValue("pubbasedir"));
            config.setlogoheight(Integer.parseInt(ele.getAttributeValue("logoheight")));
            config.setlogowidth(Integer.parseInt(ele.getAttributeValue("logowidth")));
            //ele = doc.getRootElement().getChild("RESIN_CONFIG");
            //config.setResinConfigPath(ele.getChildText("path"));
            //config.setAppPath(ele.getChildText("webapp"));
            //config.setConfigContent(ele.getChildText("content"));
        } catch (Exception e) {
            e.printStackTrace();
            config.setErrorCode(-1);
        }

        return config;
    }

    public String checkUser(String name) {
        String str = "true";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT userid FROM tbl_members WHERE userid='" + name + "'");
            res = pstmt.executeQuery();
            if (res.next()) {
                str = "false";
            }

            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {

                }
            }
        }
        return str;
    }

    //  (新用户注册时检查该用户名是否存在)
    public int checkSystemUserExist(String str) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int flag = 0;
        try {
            conn = cpool.getConnection();
            //检查个人注册表中是否存在该用户名
            pstmt = conn.prepareStatement("SELECT userid FROM tbl_members WHERE userid = ?");
            pstmt.setString(1, str);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;
            }
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) cpool.freeConnection(conn);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    //（新用户注册时验证该邮箱是否已经存在）
    public int checkSystemUserEmailExist(String str) throws CmsException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int flag = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT email FROM tbl_members WHERE email = ?");
            pstmt.setString(1, str);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                flag = 1;
            } else {
                flag = 0;
            }
            // System.out.print(flag+"=============="+"检查邮箱");
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    public String checkHost(String hostname) {
        String str = "true";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT siteid FROM tbl_siteinfo WHERE sitename='" + hostname + "'");
            res = pstmt.executeQuery();
            if (res.next()) {
                str = "false";
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {

                }
            }
        }
        return str;
    }

    public String checkEmail(String email) {
        String str = "true";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet res = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT email FROM tbl_members WHERE email='" + email + "'");
            res = pstmt.executeQuery();
            if (res.next()) {
                str = "false";
            }
            res.close();
            pstmt.close();
        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {

                }
            }
        }
        return str;
    }

    //10.9.8

    //判断用户是否存在
    public boolean userExist(String userid) {
        int result;
        result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(userid) FROM tbl_members WHERE userid=?";
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userid.trim());
            rs = pstmt.executeQuery();
            if (rs.next()) result = rs.getInt(1);
            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null)
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            else
                System.out.println("数据库连接失败!");
        }

        return result > 0;
    }

    private static String GET_INSERT_RSBT = "INSERT INTO tbl_rsbt_org(id,guid,userid,siteid,password,org_gode,org_name,org_area_code," +
            "org_sys_code,org_type,org_link_person,org_person_id,org_sup_code,org_addr,org_post,org_phone,org_mob_phone,org_fax," +
            "org_bank,org_account_name,org_account,org_hostility,org_web_site,org_mail,createdate) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    public int insertRegister(Register reg) {
        int code = 0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("SELECT MAX(id) FROM tbl_rsbt_org");
            ResultSet res = pstmt.executeQuery();
            if (res.next()) {
                id = res.getInt(1);
            }
            id++;
            res.close();
            pstmt.close();
            pstmt = conn.prepareStatement(GET_INSERT_RSBT);
            pstmt.setInt(1, id);
            pstmt.setString(2, reg.getGuid());
            pstmt.setString(3, reg.getUserID());
            pstmt.setInt(4, reg.getSiteID());
            pstmt.setString(5, reg.getPassword());
            pstmt.setString(6, reg.getOrggode());
            pstmt.setString(7, reg.getOrgname());
            pstmt.setString(8, reg.getOrgareacode());
            pstmt.setString(9, reg.getOrgsyscode());
            pstmt.setString(10, reg.getOrgtype());
            pstmt.setString(11, reg.getOrglinkperson());
            pstmt.setString(12, reg.getOrgpersonid());
            pstmt.setString(13, reg.getOrgsupcode());
            pstmt.setString(14, reg.getOrgaddr());
            pstmt.setString(15, reg.getOrgpost());
            pstmt.setString(16, reg.getOrgphone());
            pstmt.setString(17, reg.getOrgmobphone());
            pstmt.setString(18, reg.getOrgfax());
            pstmt.setString(19, reg.getOrgbank());
            pstmt.setString(20, reg.getOrgaccountname());
            pstmt.setString(21, reg.getOrgaccount());
            pstmt.setInt(22, reg.getOrghostility());
            pstmt.setString(23, reg.getOrgwebsite());
            pstmt.setString(24, reg.getOrgmail());
            pstmt.setTimestamp(25, new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return code;
    }

    private static String GET_UPDATE_RSBT = "UPDATE tbl_rsbt_org SET guid=?,userid=?,siteid=?,password=?,org_gode=?,org_name=?,org_area_code=?," +
            "org_sys_code=?,org_type=?,org_link_person=?,org_person_id=?,org_sup_code=?,org_addr=?,org_post=?,org_phone=?,org_mob_phone=?,org_fax=?," +
            "org_bank=?,org_account_name=?,org_account=?,org_hostility=?,org_web_site=?,org_mail=?,createdate=? WHERE id = ?";

    public void updateRsbt(Register reg, int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_RSBT);
            pstmt.setString(1, reg.getGuid());
            pstmt.setString(2, reg.getUserID());
            pstmt.setInt(3, reg.getSiteID());
            pstmt.setString(4, reg.getPassword());
            pstmt.setString(5, reg.getOrggode());
            pstmt.setString(6, reg.getOrgname());
            pstmt.setString(7, reg.getOrgareacode());
            pstmt.setString(8, reg.getOrgsyscode());
            pstmt.setString(9, reg.getOrgtype());
            pstmt.setString(10, reg.getOrglinkperson());
            pstmt.setString(11, reg.getOrgpersonid());
            pstmt.setString(12, reg.getOrgsupcode());
            pstmt.setString(13, reg.getOrgaddr());
            pstmt.setString(14, reg.getOrgpost());
            pstmt.setString(15, reg.getOrgphone());
            pstmt.setString(16, reg.getOrgmobphone());
            pstmt.setString(17, reg.getOrgfax());
            pstmt.setString(18, reg.getOrgbank());
            pstmt.setString(19, reg.getOrgaccountname());
            pstmt.setString(20, reg.getOrgaccount());
            pstmt.setInt(21, reg.getOrghostility());
            pstmt.setString(22, reg.getOrgwebsite());
            pstmt.setString(23, reg.getOrgmail());
            pstmt.setTimestamp(24, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(25, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteRsbtList(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("DELETE FROM tbl_rsbt_org WHERE id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

            pstmt.close();
            cpool.freeConnection(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String GET_ALL_RSBL = "SELECT * FROM tbl_rsbt_org ORDER BY id DESC";

    public List getAllRsbt() {
        List list = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_RSBL);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setUserID(rs.getString("userid"));
                reg.setSiteID(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreateDate(rs.getTimestamp("createdate"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_ALL_RSBT_SITE = "SELECT * FROM TBL_RSBT_ORG WHERE siteid=? ORDER BY id DESC";

    public List getAllRsbt(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_ALL_RSBT_SITE);
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setUserID(rs.getString("userid"));
                reg.setSiteID(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreateDate(rs.getTimestamp("createdate"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String GET_ALL_SUPPLIER_NUM = "SELECT COUNT(id) FROM TBL_RSBT_ORG WHERE siteid = ?";

    public int getAllRsbtNum(int siteid) {
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_SUPPLIER_NUM);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    public List getCurrentQueryRsbtList(int id, String sqlstr, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");
        String sql = "AND siteid=?";
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr + sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Register reg = loadsurvey(rs);
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_CURRENT_RSBT_LIST = "SELECT * FROM TBL_RSBT_ORG WHERE siteid = ? ORDER BY id DESC";

    public List getCurrentRsbtList(int siteid, int startrow, int range) {
        Connection conn = null;
        List list = new ArrayList();

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(GET_CURRENT_RSBT_LIST);
            pstmt.setInt(1, siteid);
            ResultSet rs = pstmt.executeQuery();

            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Register reg = loadsurvey(rs);
                list.add(reg);
            }//System.out.println("aaaaaaaaaa"+list);
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    private static String GET_BYID_RSBT = "SELECT * FROM tbl_rsbt_org WHERE id = ?";

    public Register getByIdrsbt(int id) {
        Register reg = new Register();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_RSBT);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                reg.setId(rs.getInt("id"));
                reg.setGuid(rs.getString("guid"));
                reg.setUserID(rs.getString("userid"));
                reg.setSiteID(rs.getInt("siteid"));
                reg.setPassword(rs.getString("password"));
                reg.setOrggode(rs.getString("org_gode"));
                reg.setOrgname(rs.getString("org_name"));
                reg.setOrgareacode(rs.getString("org_area_code"));
                reg.setOrgsyscode(rs.getString("org_sys_code"));
                reg.setOrgtype(rs.getString("org_type"));
                reg.setOrglinkperson(rs.getString("org_link_person"));
                reg.setOrgpersonid(rs.getString("org_person_id"));
                reg.setOrgsupcode(rs.getString("org_sup_code"));
                reg.setOrgaddr(rs.getString("org_addr"));
                reg.setOrgpost(rs.getString("org_post"));
                reg.setOrgphone(rs.getString("org_phone"));
                reg.setOrgmobphone(rs.getString("org_mob_phone"));
                reg.setOrgfax(rs.getString("org_fax"));
                reg.setOrgbank(rs.getString("org_bank"));
                reg.setOrgaccountname(rs.getString("org_account_name"));
                reg.setOrgaccount(rs.getString("org_account"));
                reg.setOrghostility(rs.getInt("org_hostility"));
                reg.setOrgwebsite(rs.getString("org_web_site"));
                reg.setOrgmail(rs.getString("org_mail"));
                reg.setCreateDate(rs.getTimestamp("createdate"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return reg;
    }

    public Register loadsurvey(ResultSet rs) {
        Register reg = new Register();
        try {
            reg.setId(rs.getInt("id"));
            reg.setGuid(rs.getString("guid"));
            reg.setUserID(rs.getString("userid"));
            reg.setSiteID(rs.getInt("siteid"));
            reg.setPassword(rs.getString("password"));
            reg.setOrggode(rs.getString("org_gode"));
            reg.setOrgname(rs.getString("org_name"));
            reg.setOrgareacode(rs.getString("org_area_code"));
            reg.setOrgsyscode(rs.getString("org_sys_code"));
            reg.setOrgtype(rs.getString("org_type"));
            reg.setOrglinkperson(rs.getString("org_link_person"));
            reg.setOrgpersonid(rs.getString("org_person_id"));
            reg.setOrgsupcode(rs.getString("org_sup_code"));
            reg.setOrgaddr(rs.getString("org_addr"));
            reg.setOrgpost(rs.getString("org_post"));
            reg.setOrgphone(rs.getString("org_phone"));
            reg.setOrgmobphone(rs.getString("org_mob_phone"));
            reg.setOrgfax(rs.getString("org_fax"));
            reg.setOrgbank(rs.getString("org_bank"));
            reg.setOrgaccountname(rs.getString("org_account_name"));
            reg.setOrgaccount(rs.getString("org_account"));
            reg.setOrghostility(rs.getInt("org_hostility"));
            reg.setOrgwebsite(rs.getString("org_web_site"));
            reg.setOrgmail(rs.getString("org_mail"));
            reg.setCreateDate(rs.getTimestamp("createdate"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reg;
    }

    public List getAllOrg1() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_rsbt_org1");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getAllOrg2() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_rsbt_org2");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getAllOrg3() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_rsbt_org3");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Register reg = new Register();
                reg.setId(rs.getInt("id"));
                reg.setValue(rs.getInt("value"));
                reg.setName(rs.getString("name"));
                list.add(reg);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


}
