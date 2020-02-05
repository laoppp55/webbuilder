package com.bizwink.cms.multimedia;

import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 10:44:47
 * To change this template use File | Settings | File Templates.
 */
public class MultimediaPeer implements IMultimediaManager{

    PoolServer cpool;
    public MultimediaPeer(PoolServer cpool){
        this.cpool = cpool;
    }
    public static IMultimediaManager getInstance(){
        return CmsServer.getInstance().getFactory().getMultimediaManager();
    }

    private static String CREATE_MULT_INFO_FOR_ORACLE = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename," +
            "encodeflag,createdate,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_MULT_INFO_FOR_MSSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename," +
            "encodeflag,createdate) values(?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_MULT_INFO_FOR_MYSQL = "insert into tbl_multimedia(siteid,articleid,dirname,filepath,oldfilename,newfilename," +
            "encodeflag,createdate) values(?, ?, ?, ?, ?, ?, ?, ?)";

    public int insertMult(Multimedia mult){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt=null;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int id=0;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_MULT_INFO_FOR_MYSQL);
            pstmt.setInt(1,mult.getSiteid());
            pstmt.setInt(2,mult.getArticleid());
            pstmt.setString(3,mult.getDirname());
            pstmt.setString(4,mult.getFilepath());
            pstmt.setString(5,mult.getOldfilename());
            pstmt.setString(6,mult.getNewfilename());
            pstmt.setInt(7,mult.getEncodeflag());
            pstmt.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(9, sequnceMgr.getSequenceNum("Multimedia"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
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
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return code;
    }

    public boolean encode(String fileIn, String fileOut, String fileName) {
        String fullPath = fileOut + "\\" + fileName; //完整文件路径
        String path = fileOut + "\\" + "abc.flv";
        //String cmd = "D:\\salawed_new\\webapps\\encoder.bat " + fileIn + " " + fullPath; //命令字串
        String cmd = "D:\\sala\\encoder.bat " + fullPath + " " + path; //命令字串
        //System.out.println("*************"+cmd);
        //String cmd = "ffmpeg -i \"E:/input/CIMG1052.AVI\" -y -ab 32 -ar 22050 -b 800000 -s 640*480 E:/output/3.flv";
        try {
//如果目录路径不存在则创建
            File file = new File(fileOut);
            if (!file.exists()) {
                file.mkdirs();
            }
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();//java程序等待执行过程完毕
            File filePath = new File(fullPath);

//如果文件存在,并且长度不为0,则表示转换成功.
            boolean success = filePath.exists() && filePath.length() > 0;
            if (success) {
//创建缩略图
                //Runtime.getRuntime().exec("D:\\salawed_new\\webapps\\makeimg.bat " + fullPath + " " + fileOut + "\\" + fileName + ".jpg");
                Runtime.getRuntime().exec("D:\\sala\\makeimg.bat " + fullPath + " " + fileOut + "\\" + fileName + ".jpg");
            }
            return success;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    //获取文章的附件信息
    private static String SQL_GET_ATTECHMENTS = "select id,cname,pageid,pagetype,contenttype,filename,summary,editor," +
            "createdate,lastupdate,dirname,content from tbl_relatedartids where pageid=?";

    public List<Attechment> getAttechments(int articleid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        Attechment attechment = null;
        List<Attechment> attechments = new ArrayList<Attechment>();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GET_ATTECHMENTS);
            pstmt.setInt(1,articleid);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                attechment = load(rs);
                attechments.add(attechment);
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

        return attechments;
    }

    //id,cname,pageid,pagetype,contenttype,filename,summary,editor," +
    //        "createdate,lastupdate,dirname,content
    private Attechment load(ResultSet rs){
        Attechment attechment = new Attechment();
        SimpleDateFormat myFmt2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            attechment.setId(rs.getInt("id"));
            attechment.setCname(rs.getString("cname"));
            attechment.setPageid(rs.getInt("pageid"));
            attechment.setPagetype(rs.getInt("pagetype"));
            attechment.setContenttype(rs.getInt("contenttype"));
            attechment.setFilename(rs.getString("filename"));
            attechment.setSummary(rs.getString("summary"));
            attechment.setEditor(rs.getString("editor"));
            attechment.setCreatedate(myFmt2.format(rs.getTimestamp("createdate")));
            attechment.setLastupdate(myFmt2.format(rs.getTimestamp("lastupdate")));
            attechment.setDirname(rs.getString("dirname"));
            attechment.setContent(rs.getString("content"));
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        return  attechment;
    }
}
