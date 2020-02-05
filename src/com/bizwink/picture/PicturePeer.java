package com.bizwink.picture;

import java.util.*;
import java.sql.*;
import java.io.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.server.*;

public class PicturePeer implements IPictureManager{
    PoolServer cpool;

    public PicturePeer(PoolServer cpool){
        this.cpool=cpool;
    }

    public static IPictureManager getInstance()
    {
        return (IPictureManager) CmsServer.getInstance().getFactory().getPictureManager();
    }

    public String saveOnePicture(Picture picture) throws PictureException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        String newfilename = "";
        int picid = 0;
        String picfilename = picture.getPicname();
        ResultSet rs = null;

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle")) {
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, picture.getSiteID());
                pstmt.setInt(2, picture.getColumnID());
                pstmt.setInt(3, picture.getWidth());
                pstmt.setInt(4, picture.getHeight());
                pstmt.setInt(5, picture.getPicSize());
                pstmt.setString(6, picfilename);
                pstmt.setString(7, picture.getImgUrl());
                pstmt.setTimestamp(8, picture.getCreateDate());
                picid = sequnceMgr.getSequenceNum("Picture");
                pstmt.setInt(9, picid);
                pstmt.executeUpdate();
                pstmt.close();
            } else if (cpool.getType().equalsIgnoreCase("mssql")){
                String sqlstr = "INSERT INTO tbl_picture (siteid,columnid,width,height,picsize, picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?):select scope_identity()";
                pstmt = conn.prepareStatement(sqlstr);
                pstmt.setInt(1, picture.getSiteID());
                pstmt.setInt(2, picture.getColumnID());
                pstmt.setInt(3, picture.getWidth());
                pstmt.setInt(4, picture.getHeight());
                pstmt.setInt(5, picture.getPicSize());
                pstmt.setString(6, picfilename);
                pstmt.setString(7, picture.getImgUrl());
                pstmt.setTimestamp(8, picture.getCreateDate());
                rs = pstmt.executeQuery();
                if(rs.next()){
                    picid = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            }

            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }

        //int posi = picfilename.indexOf(".");
        //String extname = picfilename.substring(posi);
        //picfilename = picfilename.substring(0,posi);
        //newfilename = picfilename + "-" + picid + extname;
        //return newfilename;
        return picfilename;
    }

    public List saveMorePicture(List picture) throws PictureException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        Picture pic = new Picture();
        String newfilename = "";
        int picid = 0;
        String picfilename = "";
        ResultSet rs = null;
        List fileNameList = new ArrayList();

        try{
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            if (cpool.getType().equalsIgnoreCase("oracle")) {
                ISequenceManager sequnceMgr = SequencePeer.getInstance();
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

                for(int i=0;i<picture.size(); i++) {
                    pic = (Picture)picture.get(i);
                    picfilename = pic.getPicname();
                    pstmt.setInt(1, pic.getSiteID());
                    pstmt.setInt(2, pic.getColumnID());
                    pstmt.setInt(3, pic.getWidth());
                    pstmt.setInt(4, pic.getHeight());
                    pstmt.setInt(5, pic.getPicSize());
                    pstmt.setString(6, picfilename);
                    pstmt.setString(7, pic.getImgUrl());
                    pstmt.setTimestamp(8, pic.getCreateDate());
                    pstmt.setInt(9, sequnceMgr.getSequenceNum("Picture"));
                    pstmt.executeUpdate();
                    fileNameList.add(picfilename);
                }
                pstmt.close();
            } else if (cpool.getType().equalsIgnoreCase("mssql")) {
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize,picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?);select scope_identity()");
                for(int i=0;i<picture.size(); i++) {
                    pic = (Picture)picture.get(i);
                    pstmt.setInt(1, pic.getSiteID());
                    pstmt.setInt(2, pic.getColumnID());
                    pstmt.setInt(3, pic.getWidth());
                    pstmt.setInt(4, pic.getHeight());
                    pstmt.setInt(5, pic.getPicSize());
                    pstmt.setString(6, picfilename);
                    pstmt.setString(7, pic.getImgUrl());
                    pstmt.setTimestamp(8, pic.getCreateDate());
                    rs = pstmt.executeQuery();
                    if(rs.next()){
                        picid = rs.getInt(1);
                    }
                    rs.close();
                    fileNameList.add(picfilename);
                }
                pstmt.close();
            }

            conn.commit();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }

        return fileNameList;
    }

    private static final String SQL_GETPicture =
            "SELECT count(ID) FROM TBL_Picture where picname = ? and siteid = ? and columnid=?";

    public boolean existThePicture(String name,int siteid,int columnid) throws PictureException{
        boolean retcode = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        int count = 0;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETPicture);
            pstmt.setString(1, name);
            pstmt.setInt(2, siteid);
            pstmt.setInt(3, columnid);
            rs = pstmt.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
            rs.close();
            pstmt.close();
            if (count > 0) retcode = true;
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }

        return retcode;
    }
}
