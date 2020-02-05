package com.bizwink.cms.toolkit.companyinfo;

import com.bizwink.cms.security.User;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.util.StringUtil;
import com.bizwink.upload.RandomStrg;
import com.sun.org.apache.xerces.internal.dom.DocumentImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FilenameUtils;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CompanyinfoPeer implements ICompanyinfoManager {
    PoolServer cpool;

    public CompanyinfoPeer(PoolServer cpool) {
        this.cpool = cpool;
    }
    public static ICompanyinfoManager getInstance() {
        return CmsServer.getInstance().getFactory().getCompanyinfoPeer();
    }

    //增加地址信息
    private static String SQL_INSERTSINOCOMPANY_FOR_ORACLE = "insert into tbl_companyinfo(siteid,companyclassid,companyname,companyaddress,companyphone,companyfax,companywebsite,companyemail,postcode,classification,summary,companylatitude,companylongitude,publishflag,createdate,updatedate,id) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static String SQL_INSERTSINOCOMPANY_FOR_MSSQL = "insert into tbl_companyinfo(siteid,companyclassid,companyname,companyaddress,companyphone,companyfax,companywebsite,companyemail,postcode,classification,summary,companylatitude,companylongitude,publishflag,createdate,updatedate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);select SCOPE_IDENTITY();";

    private static String SQL_INSERTSINOCOMPANY_FOR_MYSQL = "insert into tbl_companyinfo(siteid,companyclassid,companyname,companyaddress,companyphone,companyfax,companywebsite,companyemail,postcode,classification,summary,companylatitude,companylongitude,publishflag,createdate,updatedate) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    private static String CREATE_PIC_INFO_FOR_ORACLE = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname,imgurl,infotype,notes,articleid,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_PIC_INFO_FOR_MSSQL = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname,imgurl,infotype,notes,,articleid) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_PIC_INFO_FOR_MYSQL = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname,imgurl,infotype,notes,articleid) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_MULT_INFO_FOR_ORACLE = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename,infotype,encodeflag,createdate,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_MULT_INFO_FOR_MSSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename,infotype,encodeflag,createdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_MULT_INFO_FOR_MYSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename,infotype,encodeflag,createdate) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public retCompany addCompanyInfo(Companyinfo companyinfo,String dir){
        Connection conn = null;
        PreparedStatement pstmt;
        retCompany retc = new retCompany();
        int companyid = 0;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        String companyname = companyinfo.getCompanyname();
        String companyaddress = companyinfo.getCompanyaddress();
        String companyclassname = companyinfo.getClassification();
        String companysummary = companyinfo.getSummary();

        try{
            if (companyname != null) companyname = StringUtil.gb2isoindb(companyname);
            if (companyaddress != null) companyaddress = StringUtil.gb2isoindb(companyaddress);
            if (companyclassname != null) companyclassname = StringUtil.gb2isoindb(companyclassname);
            if (companysummary != null) companysummary = StringUtil.gb2isoindb(companysummary);
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANY_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANY_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANY_FOR_MYSQL);
            pstmt.setInt(1,companyinfo.getSiteid());
            pstmt.setInt(2,companyinfo.getCompanyclassid());
            pstmt.setString(3,companyname);
            pstmt.setString(4,companyaddress);
            pstmt.setString(5,companyinfo.getCompanyphone());
            pstmt.setString(6,companyinfo.getCompanyfax());
            pstmt.setString(7,companyinfo.getCompanywebsite());
            pstmt.setString(8,companyinfo.getCompanyemail());
            pstmt.setString(9,companyinfo.getPostcode());
            pstmt.setString(10,companyclassname);
            pstmt.setString(11,companysummary);
            pstmt.setDouble(12,companyinfo.getCompanylatitude());
            pstmt.setDouble(13,companyinfo.getCompanylongitude());
            pstmt.setInt(14,0);
            pstmt.setTimestamp(15,new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(16,new Timestamp(System.currentTimeMillis())) ;
            if (cpool.getType().equals("oracle")) {
                companyid = sequnceMgr.getSequenceNum("CompanyInfo");
                pstmt.setInt(17, companyid);
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equals("mssql")) {
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    companyid = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();

                //获取MYSQL的数据库ID
                pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) companyid=rs.getInt(1);
                rs.close();
                pstmt.close();
            }

            List list = companyinfo.getCompanypic();
            if (list != null) {
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MYSQL);
                for (int i = 0; i < list.size(); i++) {
                    FileItem  item=(FileItem)list.get(i);
                    String filename = item.getName();
                    filename= FilenameUtils.getName(filename);
                    pstmt.setInt(1, companyinfo.getSiteid());
                    pstmt.setInt(2, companyinfo.getCompanyclassid());
                    pstmt.setInt(3, 0);
                    pstmt.setInt(4, 0);
                    pstmt.setInt(5, (int)item.getSize());
                    pstmt.setString(6, filename);
                    pstmt.setString(7, "/_company/"+companyid + "/images/" + filename);
                    pstmt.setInt(8, 1);                           //1标识上传的为图片文件
                    pstmt.setString(9, "");
                    pstmt.setInt(10, companyid);
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(11, sequnceMgr.getSequenceNum("Pic"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();
            }

            list = companyinfo.getCompanyMedia();
            if (list != null) {
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
                retc.setCompanyid(companyid);
                List lname = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    FileItem  item=(FileItem)list.get(i);
                    String filename = item.getName();
                    String ext = ".mpeg";
                    int posi = filename.lastIndexOf(".");
                    if (posi > -1) ext = filename.substring(posi);
                    posi = filename.lastIndexOf(File.separator);
                    String sfilename = filename.substring(posi+1);
                    String filepath = dir + companyid + File.separator + "images" + File.separator;
                    //根据随机数生成文件名
                    RandomStrg rstr = new RandomStrg();
                    rstr.setCharset("a-z0-9");
                    rstr.setLength(8);
                    rstr.generateRandomObject();
                    String tofilename = String.valueOf(rstr.getRandom() + System.currentTimeMillis() + ext);
                    lname.add(tofilename);

                    pstmt.setInt(1,companyinfo.getSiteid());
                    pstmt.setInt(2,companyid);
                    pstmt.setString(3,"/_company/");
                    pstmt.setString(4,filepath);
                    pstmt.setString(5,sfilename);
                    pstmt.setString(6,tofilename);
                    pstmt.setInt(7,1);
                    pstmt.setInt(8,0);
                    pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(10, sequnceMgr.getSequenceNum("Multimedia"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();
                retc.setList(lname);
            }
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return retc;
    }

    public retCompany savePicAndMedias(int siteid,int columnid,int companyid,List pictures,List medias,String dir){
        Connection conn = null;
        PreparedStatement pstmt;
        retCompany retc = new retCompany();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MYSQL);
            for (int i = 0; i < pictures.size(); i++) {
                FileItem  item=(FileItem)pictures.get(i);
                String filename = item.getName();
                filename= FilenameUtils.getName(filename);
                pstmt.setInt(1, siteid);
                pstmt.setInt(2, columnid);
                pstmt.setInt(3, 0);
                pstmt.setInt(4, 0);
                pstmt.setInt(5, (int)item.getSize());
                pstmt.setString(6, filename);
                pstmt.setString(7, "/_company/"+companyid + "/images/" + filename);
                pstmt.setInt(8, 1);                           //1表示企业分类信息中的图片
                pstmt.setString(9, "图片注释");
                pstmt.setInt(10, companyid);
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(11, sequnceMgr.getSequenceNum("Pic"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
            retc.setCompanyid(companyid);
            List lname = new ArrayList();
            for (int i = 0; i < medias.size(); i++) {
                FileItem  item=(FileItem)medias.get(i);
                String filename = item.getName();
                String ext = ".mpeg";
                int posi = filename.lastIndexOf(".");
                if (posi > -1) ext = filename.substring(posi);
                posi = filename.lastIndexOf(File.separator);
                String sfilename = filename.substring(posi+1);
                String filepath = dir + companyid + File.separator + "images" + File.separator;
                //根据随机数生成目标文件名
                RandomStrg rstr = new RandomStrg();
                rstr.setCharset("a-z0-9");
                rstr.setLength(8);
                rstr.generateRandomObject();
                String tofilename = String.valueOf(rstr.getRandom() + System.currentTimeMillis() + ext);
                lname.add(tofilename);

                pstmt.setInt(1,siteid);
                pstmt.setInt(2,companyid);
                pstmt.setString(3,"/_company/");
                pstmt.setString(4,filepath);
                pstmt.setString(5,sfilename);
                pstmt.setString(6,tofilename);
                pstmt.setInt(7,1);
                pstmt.setInt(8,0);
                pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(10, sequnceMgr.getSequenceNum("Multimedia"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
            retc.setList(lname);
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return retc;
    }

    public int getCompanyNum(String sql){
        int num = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!sql.equals("")) {
            sql = sql.replaceAll("@", "%");
        }
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                num = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return num;
    }

    private static String SQL_GET_COMPANYS = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_companyinfo where siteid=? and companyclassid=? order by createdate desc) A WHERE ROWNUM <= ?) WHERE RN >= ?";

    private static String SQL_GET_PICS_FOR_A_COMPANY = "select id,siteid,imgurl,infotype from tbl_picture where siteid=? and articleid=? and infotype=1";

    private static String SQL_GET_MEDIAS_FOR_A_COMPANY = "select id,siteid,dirname,newfilename,infotype from tbl_multimedia where siteid=? and articleid=? and infotype=1";

    public List getCompanyInfos(int siteid,int columnid,int start, int range,String username,int flag){
        List list = new ArrayList();
        Companyinfo companyinfo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COMPANYS);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,columnid);
            pstmt.setInt(3,start + range);
            pstmt.setInt(4,start);
            rs = pstmt.executeQuery();

            while(rs.next()){
                companyinfo = load(rs);
                list.add(companyinfo);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public List getAllCompanyInfos(int siteid,int start,int range,String username,int flag){
        List list = new ArrayList();
        Companyinfo companyinfo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String SQL_GET_ALLCOMPANYINFO = "SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_companyinfo where siteid=28 order by createdate desc) A WHERE ROWNUM <=50) WHERE RN>=0";

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ALLCOMPANYINFO);
            rs = pstmt.executeQuery();

            while(rs.next()){
                companyinfo = load(rs);
                System.out.println("name=" + companyinfo.getCompanyname());
                list.add(companyinfo);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public List getCompanyPicsInfos(int siteid,int articleid) {
        List pics = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt1=null;
        ResultSet rs1=null;
        try{
            conn = cpool.getConnection();
            //获得图片信息
            pstmt1 = conn.prepareStatement(SQL_GET_PICS_FOR_A_COMPANY);
            pstmt1.setInt(1,siteid);
            pstmt1.setInt(2,articleid);
            rs1 = pstmt1.executeQuery();
            while(rs1.next()) {
                String imgurl = rs1.getString("imgurl");
                pics.add(imgurl);
            }
            rs1.close();
            pstmt1.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs1!=null) rs1.close();
                    if (pstmt1!=null) pstmt1.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return pics;
    }

    public List getCompanyMediasInfos(int siteid,int articleid) {
        List medias = new ArrayList();
        Connection conn = null;
        PreparedStatement pstmt1=null;
        ResultSet rs1=null;
        try{
            conn = cpool.getConnection();
            //获得多媒体信息
            pstmt1 = conn.prepareStatement(SQL_GET_MEDIAS_FOR_A_COMPANY);
            pstmt1.setInt(1,siteid);
            pstmt1.setInt(2,articleid);
            rs1 = pstmt1.executeQuery();
            while(rs1.next()) {
                String mediafilename = rs1.getString("newfilename");
                int posi = mediafilename.lastIndexOf(".");
                mediafilename = mediafilename.substring(0,posi) + ".flv";
                String mediaurl = rs1.getString("dirname") + articleid + "/images/" + mediafilename;
                medias.add(mediaurl);
            }
            rs1.close();
            pstmt1.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs1!=null) rs1.close();
                    if (pstmt1!=null) pstmt1.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return medias;
    }

    private static String SQL_GET_COMPANYS_NUM = "SELECT companynum FROM tbl_companyclass where siteid=? and id=?";

    public int getCompanyInfos_num(int siteid,int columnid,String username,int flag){
        int num = 0;
        Companyinfo companyinfo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_COMPANYS_NUM);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) num = rs.getInt("companynum");
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return num;
    }

    private Companyinfo load(ResultSet rs) throws Exception{
        Companyinfo companyinfo = new Companyinfo();
        try{
            companyinfo.setId(rs.getInt("id"));
            companyinfo.setSiteid(rs.getInt("siteid"));
            companyinfo.setCompanyname(rs.getString("companyname"));
            companyinfo.setCompanyaddress(rs.getString("companyaddress"));
            companyinfo.setCompanyphone(rs.getString("companyphone"));
            companyinfo.setCompanyfax(rs.getString("companyfax"));
            companyinfo.setCompanywebsite(rs.getString("companywebsite"));
            companyinfo.setCompanyemail(rs.getString("companyemail"));
            companyinfo.setPostcode(rs.getString("postcode"));
            companyinfo.setClassification(rs.getString("classification"));
            companyinfo.setCompanyclassid(rs.getInt("companyclassid"));
            companyinfo.setSummary(rs.getString("summary"));
            companyinfo.setCompanylatitude(rs.getFloat("companylatitude"));
            companyinfo.setCompanylongitude(rs.getFloat("companylongitude"));
            companyinfo.setPublishflag(rs.getInt("publishflag"));
            companyinfo.setCreatedate(rs.getTimestamp("createdate"));
            companyinfo.setLastupdated(rs.getTimestamp("updatedate")) ;
            companyinfo.setPROVINCE(rs.getString("PROVINCE"));             //VARCHAR2(12)	Y
            companyinfo.setCITY(rs.getString("CITY"));	                 //VARCHAR2(12)	Y
            companyinfo.setZONE(rs.getString("ZONE"));	                 //VARCHAR2(12)	Y
            companyinfo.setTOWN(rs.getString("TOWN"));	                 //VARCHAR2(12)	Y
            companyinfo.setVILLAGE(rs.getString("VILLAGE"));	             //VARCHAR2(12)	Y
            companyinfo.setCOUNTRY(rs.getString("COUNTRY"));	             //VARCHAR2(12)	Y
            companyinfo.setMPHONE(rs.getString("MPHONE"));	             //VARCHAR2(50)	Y
            companyinfo.setBIZPHONE(rs.getString("BIZPHONE"));	             //VARCHAR2(50)	Y
            companyinfo.setCONTACTOR(rs.getString("CONTACTOR"));	             //VARCHAR2(50)	Y
            companyinfo.setSAMSITEID(rs.getInt("SAMSITEID"));	             //NUMBER	Y
            companyinfo.setCOMPANYLEVEL(rs.getInt("COMPANYLEVEL"));	         //NUMBER	Y
            companyinfo.setQQ(rs.getString("QQ"));
            companyinfo.setWEIXIN(rs.getString("WEIXIN"));
            companyinfo.setWEIBO(rs.getString("WEIBO"));

        } catch(Exception e){
            e.printStackTrace();
        }
        return companyinfo;
    }

    //删除公司
    private static String SQL_DELETECOMPANY = "delete from tbl_companyinfo where id=?";
    public void delCompany(int id,int siteid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_DELETECOMPANY);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(DELETE_PICS_SQL);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(DELETE_MEDIA_SQL);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();

            conn.commit();
        } catch(Exception e) {
            try{
                conn.rollback();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public Companyinfo getACompanyInfo(int id,int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Companyinfo company = new Companyinfo();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyinfo where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                company = new Companyinfo();
                company = load(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return company;
    }

    public Companyinfo getACompanyInfoBySiteid(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Companyinfo company = new Companyinfo();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyinfo where siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                company = new Companyinfo();
                company = load(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return company;
    }

    public Companyinfo searchCompanyInfo(String keyword) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        Companyinfo company = new Companyinfo();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyinfo where companyname like '%" + keyword + "%'");
            rs = pstmt.executeQuery();

            if (rs.next()) {
                company = new Companyinfo();
                company = load(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    if (rs != null) rs.close();
                    if(pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return company;
    }

    private static String SQL_UPDATECOMPANY = "update tbl_companyinfo set companyname=?,companyaddress=?,companyphone=?,companyfax=?,companywebsite=?,companyemail=?,postcode=?,classification=?,companylatitude=?,companylongitude=?,summary=?,updatedate=? where id=? and siteid =?";

    private static String DELETE_PICS_SQL = "delete from tbl_picture where infotype=1 and siteid=? and articleid=?";

    private static String DELETE_MEDIA_SQL = "delete from tbl_multimedia where infotype=1 and siteid=? and articleid=?";

    public retCompany modifyCompany(Companyinfo company,String dir) {
        Connection conn =null;
        PreparedStatement pstmt = null;
        retCompany retc = new retCompany();
        retc.setCompanyid(company.getId());
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(SQL_UPDATECOMPANY);
            pstmt.setString(1,company.getCompanyname());
            pstmt.setString(2,company.getCompanyaddress());
            pstmt.setString(3,company.getCompanyphone());
            pstmt.setString(4,company.getCompanyfax());
            pstmt.setString(5,company.getCompanywebsite());
            pstmt.setString(6,company.getCompanyemail());
            pstmt.setString(7,company.getPostcode());
            pstmt.setString(8,company.getClassification());
            pstmt.setDouble(9,company.getCompanylatitude());
            pstmt.setDouble(10,company.getCompanylongitude());
            pstmt.setString(11,company.getSummary());
            pstmt.setTimestamp(12,new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(13,company.getId());
            pstmt.setInt(14,company.getSiteid());
            pstmt.executeUpdate();
            pstmt.close();

            List list = company.getCompanypic();
            if (list.size() > 0) {
                pstmt = conn.prepareStatement(DELETE_PICS_SQL);
                pstmt.setInt(1,company.getSiteid());
                pstmt.setInt(2,company.getId());
                pstmt.executeUpdate();
                pstmt.close();
                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MYSQL);
                for (int i = 0; i < list.size(); i++) {
                    FileItem  item=(FileItem)list.get(i);
                    String filename = item.getName();
                    filename= FilenameUtils.getName(filename);
                    pstmt.setInt(1, company.getSiteid());
                    pstmt.setInt(2, company.getCompanyclassid());
                    pstmt.setInt(3, 0);
                    pstmt.setInt(4, 0);
                    pstmt.setInt(5, (int)item.getSize());
                    pstmt.setString(6, filename);
                    pstmt.setString(7, "/_company/"+company.getId() + "/images/" + filename);
                    pstmt.setInt(8, 1);                           //1表示企业分类信息中的图片
                    pstmt.setString(9, "图片注释");
                    pstmt.setInt(10, company.getId());
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(11, sequnceMgr.getSequenceNum("Pic"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();
            }

            list = company.getCompanyMedia();
            if (list.size() > 0) {
                pstmt = conn.prepareStatement(DELETE_MEDIA_SQL);
                pstmt.setInt(1,company.getSiteid());
                pstmt.setInt(2,company.getId());
                pstmt.executeUpdate();
                pstmt.close();

                if (cpool.getType().equalsIgnoreCase("oracle"))
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
                else  if (cpool.getType().equalsIgnoreCase("mssql"))
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
                else
                    pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
                List lname = new ArrayList();
                for (int i = 0; i < list.size(); i++) {
                    FileItem  item=(FileItem)list.get(i);
                    String filename = item.getName();
                    String ext = ".mpeg";
                    int posi = filename.lastIndexOf(".");
                    if (posi > -1) ext = filename.substring(posi);
                    posi = filename.lastIndexOf(File.separator);
                    String sfilename = filename.substring(posi+1);
                    String filepath = dir + company.getId() + File.separator + "images" + File.separator;

                    //获得随机字符串作为文件名称的一部分
                    RandomStrg rstr = new RandomStrg();
                    rstr.setCharset("a-z0-9");
                    rstr.setLength(8);
                    rstr.generateRandomObject();
                    String tofilename = String.valueOf(rstr.getRandom() + System.currentTimeMillis() + ext);
                    lname.add(tofilename);

                    pstmt.setInt(1,company.getSiteid());
                    pstmt.setInt(2,company.getId());
                    pstmt.setString(3,"/_company/");
                    pstmt.setString(4,filepath);
                    pstmt.setString(5,sfilename);
                    pstmt.setString(6,tofilename);
                    pstmt.setInt(7,1);
                    pstmt.setInt(8,0);
                    pstmt.setTimestamp(9,new Timestamp(System.currentTimeMillis()));
                    if (cpool.getType().equals("oracle")) {
                        pstmt.setInt(10, sequnceMgr.getSequenceNum("Multimedia"));
                        pstmt.executeUpdate();
                    } else if (cpool.getType().equals("mssql")) {
                        pstmt.executeUpdate();
                    } else {
                        pstmt.executeUpdate();
                    }
                }
                pstmt.close();
            }
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return retc;
    }

    public List getCompanyList(int siteid){
        List list = new ArrayList();
        Companyinfo companyinfo = new Companyinfo();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyinfo where siteid  = ? order by createdate desc");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()){
                companyinfo = load(rs);
                list.add(companyinfo);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    public List getCompanyClassList(int siteid){
        List list = new ArrayList();
        companyClass companyclass = new companyClass();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyclass where siteid  = ? and parentid>0 order by lastupdated desc");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()){
                companyclass = loadCompanyClass(rs);
                list.add(companyclass);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_ORACLE = "insert into tbl_companyclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated,id) values (?,?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_MSSQL = "insert into tbl_companyclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated) values (?,?,?,?,?,?,?,?,?,?,?)";
    private static String SQL_INSERTSINOCOMPANYCLASS_FOR_MYSQL = "insert into tbl_companyclass (siteid,parentid,orderid,cname,ename,summary,dirname,editor,extname,createdate,lastupdated) values (?,?,?,?,?,?,?,?,?,?,?)";

    public void create(companyClass companyclass){
        Connection conn = null;
        PreparedStatement pstmt=null;
        int orderid = 0;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        String ctname = companyclass.getCname();
        String desc = companyclass.getDesc();

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select max(orderid) from tbl_companyclass where siteid=? and parentid=?");
            pstmt.setInt(1,companyclass.getSiteid());
            pstmt.setInt(2,companyclass.getParentid());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) orderid = rs.getInt(1);
            rs.close();
            pstmt.close();

            conn.setAutoCommit(false);
            if (ctname != null) ctname = StringUtil.gb2isoindb(ctname);
            if (desc != null) desc = StringUtil.gb2isoindb(desc);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_INSERTSINOCOMPANYCLASS_FOR_MYSQL);
            pstmt.setInt(1,companyclass.getSiteid());
            pstmt.setInt(2,companyclass.getParentid());
            pstmt.setInt(3,orderid+1);
            pstmt.setString(4,ctname);
            pstmt.setString(5,companyclass.getEname());
            pstmt.setString(6,companyclass.getDesc());
            pstmt.setString(7,companyclass.getDirname());
            pstmt.setString(8,companyclass.getEditor());
            pstmt.setString(9,companyclass.getExtname());
            pstmt.setTimestamp(10,companyclass.getCreatedate());
            pstmt.setTimestamp(11,companyclass.getLastupdated());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(12, sequnceMgr.getSequenceNum("CompanyInfo"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_DELETE_COMPANYCLASS = "delete FROM tbl_companyclass WHERE siteid = ? AND id = ?";

    public void remove(int id,int siteID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_DELETE_COMPANYCLASS);
            pstmt.setInt(1, siteID);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            pstmt.close();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    private static final String SQL_UPDATECOLUMN = "UPDATE tbl_companyclass SET cname = ?,OrderID = ?,summary=?,Extname = ?,editor=?,LastUpdated = ? WHERE siteid=? and ID = ?";

    public void update(companyClass companyclass,int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);
                pstmt = conn.prepareStatement(SQL_UPDATECOLUMN);

                pstmt.setString(1, StringUtil.iso2gb (companyclass.getCname()));
                pstmt.setInt(2,companyclass.getOrderid());
                pstmt.setString(3, companyclass.getDesc());
                pstmt.setString(4, companyclass.getExtname());
                pstmt.setString(5, companyclass.getEditor());
                pstmt.setTimestamp(6,companyclass.getLastupdated());
                pstmt.setInt(7, siteid);
                pstmt.setInt(8, companyclass.getId());
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            } catch (Exception e) {
                e.printStackTrace();
                conn.rollback();
            } finally {
                if (conn != null) {
                    try {
                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getClassidByName(String colname,int siteid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        int classid = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select id from tbl_companyclass where siteid = ? and cname=?");
            pstmt.setInt(1, siteid);
            pstmt.setString(2,colname);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                classid = rs.getInt("id");
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return classid;
    }

    public companyClass getCompanyClass(int id) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        companyClass companyclass = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_companyclass where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                companyclass = new companyClass();
                companyclass = loadCompanyClass(rs);
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return companyclass;
    }

    private static final String SQL_GET_INDEX_EXTNAME = "SELECT extname FROM tbl_companyclass WHERE siteid = ? AND parentid = 0";

    public String getIndexExtName(int siteID) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        String extname = "";

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_INDEX_EXTNAME);
            pstmt.setInt(1, siteID);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                extname = rs.getString("ExtName");
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
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return extname;
    }

    private static final String SQL_checkDualEnName = "SELECT id FROM tbl_companyclass WHERE parentid=? and ename=?";

    public boolean duplicateEnName(int parentColumnID, String enName) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        boolean existflag = false;
        ResultSet rs=null;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_checkDualEnName);
            pstmt.setInt(1, parentColumnID);
            pstmt.setString(2, enName);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                existflag = true;
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
                } catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
        return existflag;
    }

    private companyClass loadCompanyClass(ResultSet rs) throws Exception{
        companyClass companyclass = new companyClass();
        try{
            companyclass.setId(rs.getInt("id"));
            companyclass.setSiteid(rs.getInt("siteid"));
            companyclass.setParentid(rs.getInt("parentid"));
            companyclass.setOrderid(rs.getInt("orderid"));
            companyclass.setCname(rs.getString("cname"));
            companyclass.setEname(rs.getString("ename"));
            companyclass.setDirname(rs.getString("dirname"));
            companyclass.setEditor(rs.getString("editor"));
            companyclass.setExtname(rs.getString("extname"));
            companyclass.setDesc(rs.getString("summary"));
            companyclass.setCreatedate(rs.getTimestamp("createdate"));
            companyclass.setLastupdated(rs.getTimestamp("lastupdated"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return companyclass;
    }

    public int  createSpotInfo(ScenicSpot spot){
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int spotid = 0;

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement("insert into tbl_mytrips (siteid,jingqu_name,jingqu_address,jingqu_summary,jingqu_lat,jingqu_lon,createdate,updatedate,id) values (?,?,?,?,?,?,?,?,?)");
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement("insert into tbl_mytrips (siteid,jingqu_name,jingqu_address,jingqu_summary,jingqu_lat,jingqu_lon,createdate,updatedate) values (?,?,?,?,?,?,?,?);select SCOPE_IDENTITY();");
            else
                pstmt = conn.prepareStatement("insert into tbl_mytrips (siteid,jingqu_name,jingqu_address,jingqu_summary,jingqu_lat,jingqu_lon,createdate,updatedate) values (?,?,?,?,?,?,?,?)");
            pstmt.setInt(1,spot.getSiteid());
            pstmt.setString(2,spot.getSpotname());
            pstmt.setString(3,spot.getSummary());
            pstmt.setString(4,spot.getSpotaddress());
            pstmt.setFloat(5,spot.getLatitude());
            pstmt.setFloat(6,spot.getLongitude());
            pstmt.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
            if (cpool.getType().equals("oracle")) {
                spotid = sequnceMgr.getSequenceNum("CompanyInfo");
                pstmt.setInt(9, spotid);
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equals("mssql")) {
                ResultSet rs = pstmt.executeQuery();
                if(rs.next()){
                    spotid = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();

                //��ȡMysql�����е�ֵid
                pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) spotid=rs.getInt(1);
                rs.close();
                pstmt.close();
            }

            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    if (pstmt != null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        return spotid;
    }

    public List getSpotListByPerson(int siteid){
        List list = new ArrayList();
        ScenicSpot spot = new ScenicSpot();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_mytrips where siteid  = ? order by updatedate desc");
            pstmt.setInt(1,siteid);
            rs = pstmt.executeQuery();
            while (rs.next()){
                spot = loadForSpot(rs);
                list.add(spot);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private ScenicSpot loadForSpot(ResultSet rs) throws Exception{
        ScenicSpot spot = new ScenicSpot();
        try{
            spot.setId(rs.getInt("id"));
            spot.setSiteid(rs.getInt("siteid"));
            spot.setSpotid(rs.getInt("spotid"));
            spot.setSpotname(rs.getString("jingqu_name"));
            spot.setSpotaddress(rs.getString("jingqu_address"));
            spot.setSummary(rs.getString("jingqu_summary"));
            spot.setLatitude(rs.getFloat("jingqu_lat"));
            spot.setLongitude(rs.getFloat("jingqu_lon"));
            spot.setCreatedate(rs.getTimestamp("createdate"));
            spot.setLastupdated(rs.getTimestamp("updatedate"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return spot;
    }

    public List getMemberList(){
        List list = new ArrayList();
        User user = new User();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select userid,siteid,nickname,email,myimage from tbl_members where userid<>'admin' and userid<>'sq015admin' order by createdate desc");

            rs = pstmt.executeQuery();
            while (rs.next()){
                user = loadForUser(rs);
                list.add(user);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    if (rs != null) rs.close();
                    if (pstmt != null) pstmt.close();
                    cpool.freeConnection(conn);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private User loadForUser(ResultSet rs) throws Exception{
        User user = new User();
        try{
            user.setUserID(rs.getString("userid"));
            user.setSiteid(rs.getInt("siteid"));
            user.setNickName(rs.getString("nickname"));
            user.setEmail(rs.getString("email"));
            user.setMyimage(rs.getString("myimage"));
        } catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }


    public int CreateXML(int siteid,String path){
        int code = 0;
        List list = new ArrayList();
        list = getCompanyList(siteid);

        DocumentImpl doc = new DocumentImpl();
        doc.setEncoding("GBK");
        Element eData = doc.createElement("Companys");

        for(int i = 0; i < list.size(); i++){
            Companyinfo companyinfo =  (Companyinfo)list.get(i);

            Element eInfo = doc.createElement("CompanyInfo");
            Element eID = doc.createElement("ID");
            eID.appendChild(doc.createTextNode(String.valueOf(companyinfo.getId())));
            eInfo.appendChild(eID);
            Element eSiteId = doc.createElement("SiteId");
            eSiteId.appendChild(doc.createTextNode(String.valueOf(companyinfo.getSiteid())));
            eInfo.appendChild(eSiteId);
            Element eCompanyClassId = doc.createElement("ClassId");
            eCompanyClassId.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyclassid())));
            eInfo.appendChild(eCompanyClassId);
            Element eName = doc.createElement("Name");
            eName.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyname())));
            eInfo.appendChild(eName);
            Element eAdd = doc.createElement("Addresss");
            eAdd.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyaddress())));
            eInfo.appendChild(eAdd);
            Element ePhone = doc.createElement("Phone");
            ePhone.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyphone())));
            eInfo.appendChild(ePhone);
            Element eFax = doc.createElement("Fax");
            eFax.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyfax())));
            eInfo.appendChild(eFax);
            Element eWebSite = doc.createElement("WebSite");
            eWebSite.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanywebsite())));
            eInfo.appendChild(eWebSite);
            Element eEmail = doc.createElement("Email");
            eEmail.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanyemail())));
            eInfo.appendChild(eEmail);
            Element ePostcode = doc.createElement("Postcode");
            ePostcode.appendChild(doc.createTextNode(String.valueOf(companyinfo.getPostcode())));
            eInfo.appendChild(ePostcode);
            Element eClassification = doc.createElement("Classification");
            eClassification.appendChild(doc.createTextNode(String.valueOf(companyinfo.getClassification())));
            eInfo.appendChild(eClassification);
            Element eSummary = doc.createElement("Summary");
            eSummary.appendChild(doc.createTextNode(String.valueOf(companyinfo.getSummary())));
            eInfo.appendChild(eSummary);
            Element eLatitude = doc.createElement("Latitude");
            eLatitude.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanylatitude())));
            eInfo.appendChild(eLatitude);
            Element eLongitude = doc.createElement("Longitude");
            eLongitude.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanylongitude())));
            eInfo.appendChild(eLongitude);
            Element eGooglecode = doc.createElement("Googlecode");
            eGooglecode.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanygooglecode())));
            eInfo.appendChild(eGooglecode);
            Element eCompanypic = doc.createElement("CompanyPic");
            eCompanypic.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCompanypic())));
            eInfo.appendChild(eCompanypic);
            Element ePublistflag = doc.createElement("PublishFlag");
            ePublistflag.appendChild(doc.createTextNode(String.valueOf(companyinfo.getPublishflag())));
            eInfo.appendChild(ePublistflag);
            Element eCreatedate = doc.createElement("Createdate");
            eCreatedate.appendChild(doc.createTextNode(String.valueOf(companyinfo.getCreatedate())));
            eInfo.appendChild(eCreatedate);
            Element eLastupdated = doc.createElement("Lastupdated");
            eLastupdated.appendChild(doc.createTextNode(String.valueOf(companyinfo.getLastupdated())));
            eInfo.appendChild(eLastupdated);
            eData.appendChild(eInfo);
        }
        doc.appendChild(eData);

        String ret = "";
        try {
            OutputFormat format = new OutputFormat(doc);   //Serialize DOM
            format.setEncoding("GBK");
            StringWriter stringOut = new StringWriter();        //Writer will be a String
            XMLSerializer serial = new XMLSerializer(stringOut, format);
            serial.asDOMSerializer();                            // As a DOM Serializer
            serial.serialize(doc.getDocumentElement());
            ret = stringOut.toString();
        } catch (IOException e) {
            code = 1;
            e.printStackTrace();
        }

        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String pathtemp = path + "_companys.xml";

        try {
            FileWriter writer = new FileWriter(pathtemp);
            writer.write(ret);
            writer.close();
        } catch (IOException e) {
            code = 1;
            e.printStackTrace();
        }
        return code;
    }

    public void addMeetings(Meettings meettings) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("insert into tbl_meetings (id,meetingname,meetingdatetime,address,createdate,editor)values(?,?,?,?,?,?)");
            pstmt.setInt(1, sequnceMgr.getSequenceNum("CompanyInfo"));
            pstmt.setString(2, meettings.getMeetingname());
            pstmt.setTimestamp(3, meettings.getMeetingdatetime());
            pstmt.setString(4, meettings.getAddress());
            pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(6,meettings.getEditor());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public List getAllmeetings(int start, int range) throws Exception{
        List list = new ArrayList();
        Meettings meetting = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            //System.out.println("siteid=" + siteid);
            //System.out.println("columnid=" + columnid);
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_meetings  order by createdate desc) A WHERE ROWNUM <= ?) WHERE RN >= ?");
            pstmt.setInt(1,start + range);
            pstmt.setInt(2,start);
            rs = pstmt.executeQuery();

            while(rs.next()){
                meetting = new Meettings();
                meetting.setID(rs.getInt("id"));
                meetting.setMeetingname(rs.getString("meetingname"));
                meetting.setMeetingdatetime(rs.getTimestamp("meetingdatetime"));
                meetting.setAddress(rs.getString("address"));
                meetting.setCreatedate(rs.getTimestamp("createdate"));
                meetting.setEditor(rs.getString("editor"));
                list.add(meetting);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public int getAllmeetingsNum() throws Exception{
        Connection conn = null;
        int count = 0;
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(id) from tbl_meetings");
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

    public void deleteMeetings(int id) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
           /* conn = cpool.getConnection();
            pstmt = conn.prepareStatement();
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(DELETE_PICS_SQL);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(DELETE_MEDIA_SQL);
            pstmt.setInt(1,siteid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            pstmt.close();*/

            conn.commit();
        } catch(Exception e) {
            try{
                conn.rollback();
            }catch(SQLException e1){
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public Meettings getMeeting(int id) throws Exception{
        Meettings meettings = new Meettings();;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_meetings where id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                meettings.setMeetingname(rs.getString("meetingname"));
                meettings.setMeetingdatetime(rs.getTimestamp("meetingdatetime"));
                meettings.setAddress(rs.getString("address"));
            }
            rs.close();
            pstmt.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    cpool.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return meettings;
    }

    public void updateMeeting(int id,Meettings meettings) throws Exception{
        Connection conn = null;
        PreparedStatement pstmt=null;
        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_meetings set meetingname=?,meetingdatetime=?,address=?,editor=? where id=?");
            pstmt.setString(1, meettings.getMeetingname());
            pstmt.setTimestamp(2, meettings.getMeetingdatetime());
            pstmt.setString(3, meettings.getAddress());
            pstmt.setString(4,meettings.getEditor());
            pstmt.setInt(5,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch (Exception e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }
    }

    public List getMeetingTime() throws Exception{
        List list = new ArrayList();
        Meettings meetting = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select m.id,m.meetingdatetime  from tbl_meetings m");
            rs = pstmt.executeQuery();

            while(rs.next()){
                meetting = new Meettings();
                meetting.setID(rs.getInt("id"));
                meetting.setMeetingdatetime(rs.getTimestamp("meetingdatetime"));
                list.add(meetting);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;

    }

    public String getMeetingaddress(int id) throws Exception{
        String str="";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select m.address  from tbl_meetings m where m.id=?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
              str = rs.getString("address");
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return str;
    }

    public int sub_baoming(Meetting_sign meetting_sign,List list) throws  Exception{
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        ResultSet rs = null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        Meetting_sign_part meetting_sign_part;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("insert into tbl_meetting_sign(id,orderid,meetingid,comapnyname,invoicetitle,address,postcode,fee,payway,paytime,createdate)" +
                    "values (?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setInt(1,sequnceMgr.getSequenceNum("CompanyInfo"));
            pstmt.setLong(2, meetting_sign.getOrderid());
            pstmt.setInt(3, meetting_sign.getMeetingid());
            pstmt.setString(4, meetting_sign.getComapnyname());
            pstmt.setString(5,meetting_sign.getInvoicetitle());
            pstmt.setString(6,meetting_sign.getAddress());
            pstmt.setString(7,meetting_sign.getPostcode());
            pstmt.setFloat(8, meetting_sign.getFee());
            pstmt.setInt(9, meetting_sign.getPayway());
            pstmt.setTimestamp(10, meetting_sign.getPaytime());
            pstmt.setTimestamp(11,new Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement("insert into tbl_meetting_sign_part(id,orderid,signid,name,depttitle,mobilephone,fax,email,meettingtime,meetingaddress)" +
                    "values (?,?,?,?,?,?,?,?,?,?)");
            for(int i=0;i<list.size();i++){
                meetting_sign_part = (Meetting_sign_part)list.get(i);
                pstmt.setInt(1,sequnceMgr.getSequenceNum("CompanyInfo"));
                pstmt.setLong(2, meetting_sign.getOrderid());
                pstmt.setInt(3, meetting_sign_part.getSignid());
                pstmt.setString(4, meetting_sign_part.getName());
                pstmt.setString(5, meetting_sign_part.getDepttitle());
                pstmt.setString(6,meetting_sign_part.getMobilephone());
                pstmt.setString(7,meetting_sign_part.getFax());
                pstmt.setString(8,meetting_sign_part.getEmail());
                pstmt1 = conn.prepareStatement("select m.meetingdatetime,m.address from tbl_meetings m where m.id=?");
                pstmt1.setInt(1,meetting_sign_part.getSignid());
                rs = pstmt1.executeQuery();
                Timestamp meetingdatetime = new Timestamp(System.currentTimeMillis());
                String address="";
                while(rs.next()){
                    meetingdatetime = rs.getTimestamp("meetingdatetime");
                    address = rs.getString("address");
                }
                rs.close();
                pstmt1.close();

                pstmt.setTimestamp(9, meetingdatetime);
                pstmt.setString(10,address);
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
        } catch(Exception e){
            code =-1;
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    code =-1;
                    e.printStackTrace();
                }
            }
        }
        return code;
    }

    public List getAllmeeting_sign(int start, int range) throws Exception{
        List list = new ArrayList();
        Meetting_sign meetting_sign = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_meetting_sign  order by createdate desc) A WHERE ROWNUM <= ?) WHERE RN >= ?");
            pstmt.setInt(1,start + range);
            pstmt.setInt(2,start);
            rs = pstmt.executeQuery();

            while(rs.next()){
                meetting_sign = new Meetting_sign();
                meetting_sign.setId(rs.getInt("id"));
                meetting_sign.setOrderid(rs.getLong("orderid"));
                meetting_sign.setMeetingid(rs.getInt("meetingid"));
                meetting_sign.setComapnyname(rs.getString("comapnyname"));
                meetting_sign.setInvoicetitle(rs.getString("invoicetitle"));
                meetting_sign.setAddress(rs.getString("address"));
                meetting_sign.setPostcode(rs.getString("postcode"));
                meetting_sign.setFee(rs.getFloat("fee"));
                meetting_sign.setPayway(rs.getInt("payway"));
                meetting_sign.setPaytime(rs.getTimestamp("paytime"));
                meetting_sign.setCreatedate(rs.getTimestamp("createdate"));
                list.add(meetting_sign);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public int getAllmeetingSignNum() throws Exception{
        Connection conn = null;
        int count = 0;
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(id) from tbl_meetting_sign");
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

    public List getmeeting_sign_part(int start, int range,Long orderid) throws Exception{
        List list = new ArrayList();
        Meetting_sign_part meetting_sign_part = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM tbl_meetting_sign_part where orderid=?) A WHERE ROWNUM <= ?) WHERE RN >= ?");
            pstmt.setLong(1,orderid);
            pstmt.setInt(2,start + range);
            pstmt.setInt(3,start);
            rs = pstmt.executeQuery();

            while(rs.next()){
                meetting_sign_part = new Meetting_sign_part();
                meetting_sign_part.setId(rs.getInt("id"));
                meetting_sign_part.setOrderid(rs.getLong("orderid"));
                meetting_sign_part.setSignid(rs.getInt("signid"));
                meetting_sign_part.setName(rs.getString("name"));
                meetting_sign_part.setDepttitle(rs.getString("depttitle"));
                meetting_sign_part.setMobilephone(rs.getString("mobilephone"));
                meetting_sign_part.setFax(rs.getString("fax"));
                meetting_sign_part.setEmail(rs.getString("email"));
                meetting_sign_part.setMeettingtime(rs.getTimestamp("meettingtime"));
                meetting_sign_part.setMeetingaddress(rs.getString("meetingaddress"));
                list.add(meetting_sign_part);
            }
            rs.close();
            pstmt.close();
        } catch(Exception e){
            e.printStackTrace();
        }   finally{
            if(conn != null){
                try{
                    if (rs!=null) rs.close();
                    if (pstmt!=null) pstmt.close();
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    public int getmeetingSignpartNum(Long orderid) throws Exception{
        Connection conn = null;
        int count = 0;
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(id) from tbl_meetting_sign_part where orderid=?");
            pstmt.setLong(1,orderid);
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
}
