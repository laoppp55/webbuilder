package com.bizwink.cms.register;

import com.bizwink.cms.util.*;
import com.bizwink.cms.tree.Tree;
import com.bizwink.cms.tree.TreeManager;
import com.bizwink.cms.tree.node;
import com.bizwink.cms.news.IColumnManager;
import com.bizwink.cms.news.ColumnPeer;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.news.ColumnException;
import com.bizwink.cms.viewFileManager.ViewFile;
import com.bizwink.cms.markManager.mark;
import com.bizwink.cms.modelManager.Model;
import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.publish.PublishException;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-3-9
 * Time: 15:36:04
 * To change this template use File | Settings | File Templates.
 */
public class transferSite {
    public transferSite() {

    }

    //copySamSite(int sampleSiteID, int siteid,String userid,String sitename,String apppath)
    static public void main(String args[])
    {
        int sampleSiteID = 1;
        int siteid = 171;
        String sitename="www.bjgyzs.com";
        String userid="bjgyzs";
        String apppath="D:\\sites\\coosite\\webbuilder\\sites\\";
        try {
            copySamSite(sampleSiteID,siteid,userid,sitename,apppath);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private static Connection createConnection(String ip, String username, String password, String server,int flag) {
        Connection conn = null;
        String dbip = ip;
        String dbusername = username;
        String dbpassword = password;

        System.out.println("dbip=" + dbip);
        System.out.println("server=" + server);
        System.out.println("dbusername=" + dbusername);
        System.out.println("dbpassword=" + dbpassword);

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                java.util.Properties prop = new java.util.Properties();
                prop.put("user", dbusername);
                prop.put("password", dbpassword);
                //prop.put("weblogic.codeset", "GBK");
                System.out.println("jdbc:weblogic:mssqlserver4:" + dbip + ":2433");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":2433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:"+server, dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    private static final String SQL_GET_SampleSite_TemplateInfo =
            "SELECT id,columnid,isarticle,content,status,relatedcolumnid,modelversion,lockstatus,lockeditor," +
                    "defaulttemplate,chname,templatename,refermodelid,isIncluded FROM TBL_Template WHERE columnid in (select id from tbl_column where siteid=?)";

    private static final String SQL_Create_Template =
            "INSERT INTO TBL_Template(siteid,ColumnID,IsArticle,Createdate,Lastupdated,Editor,Creator,status,relatedcolumnid," +
                    "modelversion,LockStatus,lockeditor,ChName,defaulttemplate,TemplateName,refermodelid,isIncluded,ID,Content)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_GET_Mark_Info =
            "SELECT id,SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes,Lockflag,lockeditor," +
                    "Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid" +
                    " FROM TBL_Mark WHERE siteid = ?";

    private static final String SQL_CREATE_MARK =
            "INSERT INTO TBL_Mark (SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
                    "Lockflag,lockeditor,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid," +
                    "ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_COLUMN =
            "INSERT INTO TBL_Column (SiteID,ID,ParentID,Cname,Ename,Editor,Dirname,OrderID,isproduct,Extname,CreateDate,LastUpdated," +
                    "isdefineattr,hasarticlemodel,isaudited,ispublishmore,languagetype,columndesc,xmltemplate,contentshowtype,isrss," +
                    "getRssArticleTime,archivingrules,useArticleType,istype) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String getSampleSiteName="SELECT sitename from tbl_siteinfo where siteid=?";

    //获取新站点的rootid
    private static final String getSiteRootID="SELECT id from tbl_column where siteid=? and parentid=0";

    private static final String SEQ_NEXT_COLUMN_ID = "select tbl_column_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_VIEWFILE_ID = "select tbl_viewfile_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_MARK_ID = "select tbl_mark_id.NEXTVAL from dual";
    private static final String SEQ_NEXT_TEMPLATE_ID = "select tbl_template_id.NEXTVAL from dual";

    public static boolean copySamSite(int sampleSiteID, int siteid,String userid,String sitename,String apppath) throws CmsException {
        boolean succeed = false;
        Connection s_conn = null, t_conn=null;
        PreparedStatement pstmt = null, pstmt1 = null;
        ResultSet rs = null;
        String SQL_CREATE = null;
        String SQL_CREATESITE = null;
        int newColumnID = 0;
        int marknum = 0;
        String sampleSiteName = null;
        String dbtype = "oracle";
        String s_dbtype="mssql";
        sitename = StringUtil.replace(sitename,".","_");

        //ISequenceManager sequnceMgr = SequencePeer.getInstance();

        //if (cpool.getType().equalsIgnoreCase("oracle"))
        SQL_CREATESITE ="INSERT INTO tbl_siteipinfo (siteid,siteip,docpath,ftpuser," +
                "ftppasswd,publishway,Status,SiteName,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //else
        //    SQL_CREATESITE ="INSERT INTO tbl_siteipinfo (siteid,siteip,docpath,ftpuser," +
        //            "ftppasswd,publishway,Status,SiteName) VALUES (?, ?, ?, ?, ?, ?, ?, ?);select SCOPE_IDENTITY()";


        //if (cpool.getType().equalsIgnoreCase("oracle"))
        SQL_CREATE ="INSERT INTO TBL_SiteInfo (SiteName,ImagesDir,cssjsdir,tcflag,wapflag,PubFlag,BindFlag,CreateDate," +
                "berefered,copycolumn,becopycolumn,pusharticle,movearticle,config,SiteID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        //else
        //    SQL_CREATE ="INSERT INTO TBL_SiteInfo (SiteName,ImagesDir,cssjsdir,tcflag,wapflag,PubFlag,BindFlag,CreateDate," +
        //            "berefered,copycolumn,becopycolumn,pusharticle,movearticle,config) " +
        //            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);select SCOPE_IDENTITY()";
        try {
            int newsiteid = 0;
            try {
                /*String s_dbip = "211.154.43.212";
                String s_username = "coosite";
                String s_password = "coositedbpass";
                String s_server = "orcl9";
                s_conn = createConnection(s_dbip, s_username, s_password,s_server, 1);
                s_conn.setAutoCommit(false);
                */

                String s_dbip = "211.154.43.210";
                String s_username = "gangyuan";
                String s_password = "gangyuandbpass";
                String s_server = "";
                s_conn = createConnection(s_dbip, s_username, s_password,s_server, 0);
                s_conn.setAutoCommit(false);

                String t_dbip = "211.154.43.210";
                String t_username = "coositedb";
                String t_password = "coositedbpass2009";
                String t_server = "ORCL10G";
                t_conn = createConnection(t_dbip, t_username, t_password,t_server, 1);
                t_conn.setAutoCommit(false);


                //获取例子站点的域名
                pstmt = s_conn.prepareStatement(getSampleSiteName);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    sampleSiteName=rs.getString(1);
                    sampleSiteName = StringUtil.replace(sampleSiteName,".","_");
                }
                rs.close();
                pstmt.close();

                //拷贝所有的文件到CMS的sites目录和所有的web服务器
                //copyFilesToCMS_AND_WEB(siteid,sampleSiteID,userid,sampleSiteName,sitename,apppath);

                //获取新站点的rootid
                int siterootid = 0;
                pstmt = t_conn.prepareStatement(getSiteRootID);
                pstmt.setInt(1, siteid);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    siterootid=rs.getInt(1);
                    System.out.println("siterootid=" + siterootid);
                }
                rs.close();
                pstmt.close();

                // insert into tbl_column
                newColumnID = siterootid;
                pstmt = t_conn.prepareStatement(SQL_CREATE_COLUMN);
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
                    Tree colTree = TreeManager.getInstance().getSiteTree(sampleSiteID,s_conn);
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
                            Column column = columnMgr.getColumn(currentID,s_conn);
                            //System.out.println(column.getID());
                            if (column.getID() != 0) {
                                newnodenum = newnodenum - 1;
                                int parentID = newpid[newnodenum];       //新节点的父ID
                                for (i = colTree.getNodeNum() - 1; i >= 0; i--) {
                                    if (treeNodes[i].getLinkPointer() == currentID) {
                                        column = columnMgr.getColumn(treeNodes[i].getId(),s_conn);
                                        pstmt.setInt(1, siteid);

                                        //获取栏目ID
                                        pstmt1 = t_conn.prepareStatement(SEQ_NEXT_COLUMN_ID);
                                        rs = pstmt1.executeQuery();
                                        if (rs.next()) newColumnID = rs.getInt(1);
                                        rs.close();
                                        pstmt1.close();

                                        String columnname = null;
                                        String desc = null;
                                        String xmldesc=null;
                                        if (s_dbtype.equals("mssql"))
                                            columnname = StringUtil.iso2gbindb(column.getCName());
                                        else
                                            columnname = column.getCName();
                                        System.out.println("columnname=" + columnname);
                                        if (s_dbtype.equals("mssql"))
                                            desc = StringUtil.iso2gbindb(column.getDesc());
                                        else
                                            desc = column.getDesc();
                                        if (s_dbtype.equals("mssql"))
                                            xmldesc = StringUtil.iso2gbindb(column.getXMLTemplate());
                                        else
                                            xmldesc = column.getXMLTemplate();

                                        pstmt.setInt(2, newColumnID);
                                        pstmt.setInt(3, parentID);
                                        pstmt.setString(4, columnname);
                                        pstmt.setString(5, column.getEName());
                                        pstmt.setString(6, column.getEditor());
                                        pstmt.setString(7, column.getDirName());
                                        pstmt.setInt(8, column.getOrderID());
                                        pstmt.setInt(9, column.getIsProduct());
                                        pstmt.setString(10, column.getExtname());
                                        pstmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
                                        pstmt.setTimestamp(12, new Timestamp(System.currentTimeMillis()));
                                        pstmt.setInt(13, column.getDefineAttr());                           //是否定义了扩展属性
                                        pstmt.setInt(14, column.getHasArticleModel());                      //是否存在文章模板
                                        pstmt.setInt(15, column.getIsAudited());                            //是否需要审核
                                        pstmt.setInt(16, column.getIsPublishMoreArticleModel());            //是否存在多文章模板发布
                                        pstmt.setInt(17, column.getLanguageType());                         //定义了栏目的语言类型
                                        pstmt.setString(18, desc);                              //栏目说明
                                        pstmt.setString(19, xmldesc);                       //栏目扩展属性XML定义
                                        pstmt.setInt(20, column.getContentShowType());                      //内容显示类型
                                        pstmt.setInt(21, column.getRss());                                  //是否发布RSS
                                        pstmt.setInt(22, column.getGetRssArticleTime());                    //是否需要审核
                                        pstmt.setInt(23, column.getArchivingrules());                       //是否存在多文章模板发布
                                        pstmt.setInt(24, column.getUseArticleType());                       //定义了栏目的语言类型
                                        pstmt.setInt(25, column.getIsType());                               //定义了栏目的语言类型

                                        pstmt.executeUpdate();

                                        newpid[newnodenum] = newColumnID;
                                        newnodenum = newnodenum + 1;

                                        relNOCol = new relationBetweenOldColumnAndNewColumn();
                                        relNOCol.setNewColumnID(newColumnID);
                                        relNOCol.setOldColumnID(column.getID());
                                        NOColList.add(relNOCol);
                                    }
                                }
                            }
                        }
                        catch (ColumnException e) {
                            e.printStackTrace();
                        }
                        currentID = pid[nodenum];
                    } while (nodenum > 0);
                    pstmt.close();
                }

                //拷贝文章样式表------------------------------------tbl_viewfile
                List viewfilelist=new ArrayList();
                pstmt=s_conn.prepareStatement("select id,siteid,type,content,chinesename,editor,lockflag,notes,createdate,updatedate " +
                        "from tbl_viewfile t where siteid="+sampleSiteID);
                rs=pstmt.executeQuery();
                while(rs.next())
                {
                    ViewFile view= loadviewfile( rs) ;
                    viewfilelist.add(view);
                }
                rs.close();
                pstmt.close();

                //为新站点创建样式文件-------------------------------------------
                int viewid=0;
                String content = null;
                relationBetweenOldViewAndNewView relNOView = null;
                List NOViewList=new ArrayList();  //用来 存储 tbl_viewfile 插入的ID 和模版的ID
                for( i=0;i<viewfilelist.size();i++)
                {
                    pstmt=t_conn.prepareStatement("insert into tbl_viewfile(id,siteid,type,content,chinesename,editor,lockflag,notes,createdate,updatedate) values(?,?,?,?,?,?,?,?,?,?)");
                    ViewFile view=(ViewFile)viewfilelist.get(i);

                    //获取格式文件ID
                    pstmt1 = t_conn.prepareStatement(SEQ_NEXT_VIEWFILE_ID);
                    rs = pstmt1.executeQuery();
                    if (rs.next()) viewid = rs.getInt(1);
                    rs.close();
                    pstmt1.close();

                    pstmt.setInt(1, viewid);
                    pstmt.setInt(2,siteid);
                    pstmt.setInt(3,view.getType());
                    if (view.getContent() != null) {
                        content = view.getContent();
                        content = StringUtil.replace(content,sampleSiteName,sitename);
                        DBUtil.setBigString(dbtype, pstmt, 4, content);
                    } else {
                        pstmt.setNull(4, java.sql.Types.LONGVARCHAR);
                    }
                    pstmt.setString(5,view.getChineseName());
                    pstmt.setString(6,userid);
                    pstmt.setInt(7,view.getLockFlag());
                    pstmt.setString(8,view.getNotes());
                    pstmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
                    pstmt.setTimestamp(10, new Timestamp(System.currentTimeMillis()));
                    pstmt.executeUpdate();
                    pstmt.close();

                    relNOView = new relationBetweenOldViewAndNewView();
                    relNOView.setOldViewID(view.getID());
                    relNOView.setNewViewID(viewid);
                    NOViewList.add(relNOView);
                }

                //拷贝标记
                //"SELECT SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes,Lockflag,lockeditor," +
                //        "Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid,id" +
                //        " FROM TBL_Mark WHERE siteid = ?";

                List marklist = new ArrayList();
                mark mark = new mark();
                pstmt = s_conn.prepareStatement(SQL_GET_Mark_Info);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    mark = loadMark(rs,s_dbtype);
                    marklist.add(mark);
                }
                rs.close();
                pstmt.close();

                int newMarkID = 0;
                int newcid = 0;
                pstmt = t_conn.prepareStatement(SQL_CREATE_MARK);
                for (i = 0; i < marklist.size(); i++) {
                    //if (i != 88) {
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
                    content = StringUtil.replace(content,sampleSiteName,sitename);
                    //content = StringUtil.replace(content,"WAY]" ,"");
                    DBUtil.setBigString(dbtype, pstmt, 3, replaceColumnIDAndViewID_Of_MarkContent(content, NOColList,NOViewList));
                    pstmt.setInt(4, mark.getMarkType());
                    System.out.println("mark-name=" + mark.getChineseName()+  "===" + mark.getID());
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
                    pstmt.setString(15, replaceColumnID_IN_Mark_Of_RelatedColumnIDs(mark.getRelatedColumnID(),NOColList));
                    //获取标记ID
                    pstmt1 = t_conn.prepareStatement(SEQ_NEXT_MARK_ID);
                    rs = pstmt1.executeQuery();
                    if (rs.next()) newMarkID = rs.getInt(1);
                    rs.close();
                    pstmt1.close();

                    pstmt.setInt(16, newMarkID);
                    pstmt.executeUpdate();

                    relNOMark = new relationBetweenOldMarkAndNewMark();
                    relNOMark.setNewMarkID(newMarkID);
                    relNOMark.setOldMarkID(mark.getID());
                    NOMarkList.add(relNOMark);
                }
                //}
                pstmt.close();

                //拷贝模板
                Model model = new Model();
                List templateList = new ArrayList();
                pstmt = s_conn.prepareStatement(SQL_GET_SampleSite_TemplateInfo);
                pstmt.setInt(1, sampleSiteID);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    model = loadTemplate(rs, userid,s_dbtype);
                    templateList.add(model);
                }
                rs.close();
                pstmt.close();

                for (i = 0; i < templateList.size(); i++) {
                    model = (Model) templateList.get(i);
                    //获取新站点对应的栏目ID
                    int old_tid = model.getID();
                    System.out.println("old_tid=" + old_tid);
                    for (int k = 0; k < NOColList.size(); k++) {
                        relNOCol = (relationBetweenOldColumnAndNewColumn) NOColList.get(k);
                        if (relNOCol.getOldColumnID() == model.getColumnID()) {
                            newcid = relNOCol.getNewColumnID();
                            break;
                        }
                    }

                    int new_tid = 0;
                    //获取模板新的ID
                    pstmt1 = t_conn.prepareStatement(SEQ_NEXT_TEMPLATE_ID);
                    rs = pstmt1.executeQuery();
                    if (rs.next()) new_tid = rs.getInt(1);
                    rs.close();
                    pstmt1.close();

                    //修改模板上标记的ID为新的标记ID，并重写模板内容
                    content = model.getContent();
                    if (content != null) {
                        content = StringUtil.replace(content,"sites/" + sampleSiteName,"/webbuilder/sites/" + sitename);
                        try {
                            String tbuf = content;
                            Pattern p = Pattern.compile("\\[MARKID\\][0-9,_]*\\[/MARKID\\]", Pattern.CASE_INSENSITIVE);
                            Matcher m = p.matcher(tbuf);
                            while (m.find()) {
                                int start = m.start();
                                int end = m.end();
                                String thetag = tbuf.substring(start,end);
                                String newtag = replaceMarkid(thetag,NOMarkList,NOColList,newcid);
                                content = StringUtil.replace(content, thetag, newtag);
                                tbuf = tbuf.substring(end);
                                m = p.matcher(tbuf);
                            }

                            //修改模板的格式文件ID，例如中文路径的格式文件，英文路径的格式文件和导航条的格式文件
                            content = replaceViewID_IN_Template(content,NOViewList);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    //为新的站点加入该模板
                    pstmt1 = t_conn.prepareStatement(SQL_Create_Template);
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
                    if (model.getRelatedColumnIDs() != null)  {
                        if (!model.getRelatedColumnIDs().equalsIgnoreCase("()") && !model.getRelatedColumnIDs().equalsIgnoreCase("(; c)"))
                            pstmt1.setString(9, replaceColumnID_Of_RelatedColumnIDs(model.getRelatedColumnIDs(),NOColList));
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
                    if (model.getTemplateName() != null)
                        pstmt1.setString(15, model.getTemplateName());
                    else
                        pstmt1.setString(15, "template" + new_tid);
                    pstmt1.setInt(16, model.getReferModelID());
                    pstmt1.setInt(17, model.getIncluded());         //模板需要发布
                    pstmt1.setInt(18, new_tid);
                    if (content != null)
                        DBUtil.setBigString(dbtype, pstmt1, 19, content);
                    else
                        pstmt1.setNull(19, java.sql.Types.LONGVARCHAR);
                    pstmt1.executeUpdate();
                    pstmt1.close();
                }

                //清除模版列表
                templateList = null;
                NOColList = null;
                NOViewList = null;
                NOMarkList = null;

                t_conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                t_conn.rollback();
                s_conn.rollback();
                throw new CmsException("Database exception: create user failed.");
            } finally {
                if (s_conn != null) {
                    s_conn.close();
                }
                if (t_conn != null) {
                    t_conn.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CmsException("Database exception: can't rollback?");
        }

        System.out.println("网站拷贝完毕!!!!!!");

        return succeed;
    }

    //拷贝文章列表图标及HTML碎片文件到WEB
    private static void copyFilesToCMS_AND_WEB(int siteid,int sampleSiteID,String userid,String sampleSiteName,String sitename,String appPath) throws CmsException {
        //源文件路径
        String srcPathIcon = appPath + "sites" + File.separator + sampleSiteName + File.separator;
        //目标文件路径
        String objPathIcon = appPath + "sites" + File.separator + sitename + File.separator;

        String srcfile = null;
        FileInputStream source = null;
        FileOutputStream destination = null;

        //拷贝文章列表图标
        File file = new File(srcPathIcon);
        int index=0;
        List dirs = new ArrayList();
        dirs.add(file);

        try {
            do {
                file = (File)dirs.get(index);
                File[] fs = file.listFiles();
                dirs.remove(index);
                index = index - 1;
                for(int i=0; i<fs.length; i++) {
                    if (fs[i].isDirectory()) {
                        dirs.add(fs[i]);
                        index = index + 1;
                    } else {
                        srcfile = fs[i].getPath();
                        int posi = srcfile.indexOf(srcPathIcon);
                        String objfile = objPathIcon + srcfile.substring(posi + srcPathIcon.length());
                        String objpath = objfile.substring(0,objfile.lastIndexOf(File.separator));
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
            } while (dirs.size() != 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            srcPathIcon = null;
            objPathIcon = null;
            file = null;
        }
    }

//    "SELECT SiteID,columnid,Content,Marktype,Chinesename,formatfilenum,Notes," +
//            "editor,Lockflag,Pubflag,Innerhtmlflag,Createdate,updatedate,publishtime,relatedcolumnid" +
//            " FROM TBL_Mark WHERE siteid = ?";

    static mark loadMark(ResultSet rs,String dbtype) throws SQLException {
        mark mark = new mark();
        try {
            mark.setID(rs.getInt("ID"));
            mark.setSiteID(rs.getInt("SiteID"));
            mark.setColumnID(rs.getInt("ColumnID"));
            //mark.setContent(rs.getString("Content"));
            String content = DBUtil.getBigString(dbtype, rs, "content");
            if (dbtype.equals("mssql"))
                mark.setContent(StringUtil.iso2gbindb(content));
            else
                mark.setContent(content);
            mark.setMarkType(rs.getInt("MarkType"));
            mark.setNotes(rs.getString("Notes"));
            mark.setLockFlag(rs.getInt("LockFlag"));
            mark.setLockEditor(rs.getString("lockeditor"));
            mark.setPubFlag(rs.getInt("PubFlag"));
            mark.setInnerHTMLFlag(rs.getInt("InnerHTMLFlag"));
            mark.setFormatFileNum(rs.getInt("formatfilenum"));
            String chname= rs.getString("ChineseName");
            if (chname != null) {
                if (dbtype.equals("mssql"))
                    mark.setChinesename(StringUtil.iso2gbindb(chname));
                else
                    mark.setChinesename(chname);
            } else {
                mark.setChinesename("文章列表样式");
            }
            mark.setRelatedColumnID(rs.getString("relatedcolumnid"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return mark;
    }

//    "SELECT id,columnid,isarticle,content,status,relatedcolumnid,modelversion,lockstatus,lockeditor," +
//            "defaulttemplate,chname,templatename,refermodelid,isIncluded FROM TBL_Template WHERE SiteID = ?";

    static Model loadTemplate(ResultSet rs, String userid,String dbtype) throws SQLException {
        Model model = new Model();
        try {
            model.setID(rs.getInt("id"));
            model.setColumnID(rs.getInt("ColumnID"));
            model.setIsArticle(rs.getInt("IsArticle"));
            String content= DBUtil.getBigString(dbtype, rs, "content");
            if (dbtype.equals("mssql"))
                model.setContent(StringUtil.iso2gbindb(content));
            else
                model.setContent(content);
            model.setRelatedColumnIDs(rs.getString("relatedcolumnid"));
            model.setEditor(userid);
            model.setCreator(userid);
            model.setLockstatus(rs.getInt("lockstatus"));
            model.setLockEditor(rs.getString("lockeditor"));
            model.setDefaultTemplate(rs.getInt("defaulttemplate"));
            String cname = rs.getString("ChName");
            if (dbtype.equals("mssql"))
                model.setChineseName(StringUtil.iso2gbindb(cname));
            else
                model.setChineseName(cname);
            model.setTemplateName(rs.getString("TemplateName"));
            model.setReferModelID(rs.getInt("refermodelid"));
            model.setIncluded(rs.getInt("isIncluded"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    static public ViewFile loadviewfile(ResultSet rs)
    {
        ViewFile view=new ViewFile();
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

    static private String replaceColumnIDAndViewID_Of_MarkContent(String buf, List list,List vlist) {
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
            tempbuf = buf.substring(startposi + 12, endposi-1);
            endbuf = buf.substring(endposi-1);
            //System.out.println(tempbuf);

            //用新的ID替换例子网站的栏目ID
            //7874-getAllSubArticle
            Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
            String columnids[] = p.split(tempbuf);
            int colid = 0;
            for (int i = 0; i < columnids.length; i++) {
                startposi = columnids[i].indexOf("-getAllSubArticle");
                if (startposi == -1) {
                    if (columnids[i]!=null)
                        colid = Integer.parseInt(columnids[i]);
                } else
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

            //System.out.println(buf);


            //替换格式文件的ID
            startposi = buf.indexOf("[LISTTYPE]");
            endposi = buf.indexOf("[/LISTTYPE]");
            if (startposi > -1 && endposi > -1) {
                startbuf = buf.substring(0, startposi + 10);
                tempbuf = buf.substring(startposi + 10, endposi);
                endbuf = buf.substring(endposi);
                if (tempbuf != null && tempbuf !="") {
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

    static private String replaceViewID_IN_Template(String buf,List vlist) {
        String tbuf = buf;

        //替换中文路径的格式文件ID
        Pattern p = Pattern.compile("\\[CHINESE_PATH\\][0-9]*\\[/CHINESE_PATH\\]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(tbuf);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String thetag = tbuf.substring(start,end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf,thetag,newtag);
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
            String thetag = tbuf.substring(start,end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf,thetag,newtag);
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
            String thetag = tbuf.substring(start,end);
            //System.out.println("thetag=" + thetag);
            String newtag = replaceViewID(thetag, vlist);
            buf = StringUtil.replace(buf,thetag,newtag);
            tbuf = tbuf.substring(end);
            m = p.matcher(tbuf);
        }

        return buf;
    }

    static private String replaceViewID(String tag_content,List vlist) {
        String tbuf = tag_content;
        int posi = tbuf.indexOf("]");
        String start_tag = tbuf.substring(0,posi+1);
        tbuf = tbuf.substring(posi+1);
        posi = tbuf.indexOf("[");
        int viewid = Integer.parseInt(tbuf.substring(0,posi));
        String end_tag=tbuf.substring(posi);

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
    private static String replaceColumnID_Of_RelatedColumnIDs(String buf,List list)  {
        int startposi = 0;
        int endposi = 0;
        String tempbuf = "";
        String colids = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();

        //System.out.println("buf=" + buf);
        startposi = buf.indexOf("(");
        endposi = buf.indexOf(")");

        if (startposi > -1 && endposi > -1) {
            tempbuf = buf.substring(startposi + 1);
            endposi = tempbuf.indexOf(")");
            tempbuf = tempbuf.substring(0,endposi);

            //System.out.println("tempbuf=" + tempbuf);

            //用新的ID替换例子网站的栏目ID
            Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);

            String columnids[] = p.split(tempbuf);
            int colid = 0;
            for (int i = 0; i < columnids.length; i++) {
                //System.out.println("columnids[i]=" + columnids[i]);
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

        //System.out.println("buf=" + buf);
        return buf;
    }

    //替换tbl_template表中的relatedcolumnid字段的内容
/*    static private String replaceColumnID_Of_RelatedColumnIDs(String buf,List list)  {
        int startposi = 0;
        int endposi = 0;
        String tempbuf = "";
        String colids = "";
        String startbuf = "";
        String endbuf = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();

        startposi = buf.indexOf("(");
        endposi = buf.indexOf(")");

        if (startposi > -1 && endposi > -1) {
            startbuf = buf.substring(0, startposi + 1);
            tempbuf = buf.substring(startposi + 1, endposi - 1);
            endbuf = buf.substring(endposi - 1);

            //用新的ID替换例子网站的栏目ID
            Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
            String columnids[] = p.split(tempbuf);
            int colid = 0;
            for (int i = 0; i < columnids.length; i++) {
                if (!columnids[i].isEmpty()) {
                    colid = Integer.parseInt(columnids[i]);
                    for (int j = 0; j < list.size(); j++) {
                        relNOCol = (relationBetweenOldColumnAndNewColumn) list.get(j);
                        if (relNOCol.getOldColumnID() == colid) {
                            colids = colids + relNOCol.getNewColumnID() + ",";
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

        //System.out.println("buf=" + buf);
        return buf;
    }
*/

    //替换tbl_mark表中的relatedcolumnid字段的内容
    static private String replaceColumnID_IN_Mark_Of_RelatedColumnIDs(String buf,List list)  {
        int startposi = 0;
        int endposi = 0;
        String tempbuf = "";
        String colids = "";
        String startbuf = "";
        String endbuf = "";
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();

        System.out.println("buf=" + buf);

        if (buf!=null) {
            tempbuf = buf.substring(buf.indexOf("(") + 1);
            tempbuf = tempbuf.substring(0,tempbuf.indexOf(")"));

            //用新的ID替换例子网站的栏目ID
            if (tempbuf!=null) {
                Pattern p = Pattern.compile(",", Pattern.CASE_INSENSITIVE);
                String columnids[] = p.split(tempbuf);
                int colid = 0;
                for (int i = 0; i < columnids.length; i++) {
                    startposi = columnids[i].indexOf("-getAllSubArticle");
                    int fang_posi = columnids[i].lastIndexOf("]");
                    if (fang_posi == -1) {
                        if (startposi == -1)  {
                            colid = Integer.parseInt(columnids[i].trim());
                        }else
                            colid = Integer.parseInt(columnids[i].substring(0, startposi));
                    }
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
            }
        }

        //System.out.println("buf=" + buf);
        return buf;
    }

    static private String replaceMarkid(String thetag,List NOMarkList,List NOColList,int newcid) {
        String tbuf = thetag.substring(8,thetag.length() - 9);
        int posi = tbuf.indexOf("_");
        int markid = 0;
        int columnid = 0;
        relationBetweenOldColumnAndNewColumn relNOCol = new relationBetweenOldColumnAndNewColumn();
        relationBetweenOldMarkAndNewMark relNOMark = new relationBetweenOldMarkAndNewMark();

        //System.out.println("tbuf=" + tbuf);
        if (posi>-1) {
            markid = Integer.parseInt(tbuf.substring(0,posi));
            // System.out.println("markid=" + markid);
            for (int j = 0; j < NOMarkList.size(); j++) {
                relNOMark = (relationBetweenOldMarkAndNewMark)NOMarkList.get(j);
                if (relNOMark.getOldMarkID() == markid) {
                    markid = relNOMark.getNewMarkID();
                    break;
                }
            }

            columnid = Integer.parseInt(tbuf.substring(posi+1));
            //System.out.println("columnid=" + columnid);
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
                relNOMark = (relationBetweenOldMarkAndNewMark)NOMarkList.get(j);
                if (relNOMark.getOldMarkID() == markid) {
                    markid = relNOMark.getNewMarkID();
                    break;
                }
            }
            tbuf = String.valueOf(markid) + "_" + newcid;
        }

        return "[MARKID]" + tbuf + "[/MARKID]";
    }
}
