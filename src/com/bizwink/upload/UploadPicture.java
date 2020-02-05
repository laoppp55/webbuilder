package com.bizwink.upload;
//未处理图片重复时的判断

import java.io.*;
import java.util.Enumeration;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.StringUtil;
import org.apache.tools.zip.*;

public class UploadPicture {
    ZipFile zf = null;
    String allowed = ".zip,.jpg,.gif,.bmp,";

    String dbtype = CmsServer.getInstance().getProperty("main.db.type");

    public boolean doUploadPics(String sourcefile, String realpath, int columnid, int siteid, String editor) {
        boolean succeed = false;
        Enumeration fileenum = null;
        //获得文章创建的时间，生成时间发布路径
        Timestamp nowtime = new Timestamp(System.currentTimeMillis());
        String createdate_path = nowtime.toString().substring(0, 10);
        createdate_path = createdate_path.replaceAll("-", "");
        String savepath = realpath + createdate_path + File.separator + "download";
        System.out.println(savepath);
        try {
            zf = new ZipFile(sourcefile);
            fileenum = zf.getEntries();
            while (fileenum.hasMoreElements()) {
                try {
                    ZipEntry target = (ZipEntry) fileenum.nextElement();
                    saveEntry(savepath, target, siteid, columnid, editor);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("zipfile not found");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("IO error...");
                }
            }
            zf.close();
            succeed = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("zipfile not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO error...");
        } finally {
            File sFile = new File(sourcefile);
            sFile.delete();
        }

        return succeed;
    }

    private static final String SQL_CREATE_ARTICLE_ORACLE =
            "INSERT INTO TBL_Article (ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,DirName,Publishtime,SalePrice,InPrice,MarketPrice,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,siteid,ID) VALUES (?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_CREATE_ARTICLE_MSSQL =
            "INSERT INTO TBL_Article (ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,DirName,Publishtime,SalePrice,InPrice,MarketPrice,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,siteid) VALUES (?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);select SCOPE_IDENTITY();";

    private static final String SQL_CREATE_ARTICLE_MYSQL =
            "INSERT INTO TBL_Article (ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,DirName,Publishtime,SalePrice,InPrice,MarketPrice,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,siteid) VALUES (?, ?, ?, ?, " +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    private static final String SQL_GET_MAX_SORTID = "select max(sortid) from tbl_article where columnid = ?";

    private static final String SQL_GET_MAX_DIRNAME = "select dirname from tbl_column where id = ?";

    private int CreateImageToArticle(Picture pic) {
        int newid = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        System.out.println("hello word");
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        if (dbtype.equals("oracle"))  newid = sequenceMgr.getSequenceNum("Article");
        System.out.println("hello word!!!");
        Timestamp nowtime = new Timestamp(System.currentTimeMillis());
        ResultSet rs = null;
        int sortid = 0;
        String dirname = "";

        try {
            conn = CmsServer.getInstance().getConnection();

            pstmt = conn.prepareStatement(SQL_GET_MAX_SORTID);
            pstmt.setInt(1, pic.getColumnID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sortid = rs.getInt(1) + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement(SQL_GET_MAX_DIRNAME);
            pstmt.setInt(1, pic.getColumnID());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dirname = rs.getString(1);
            }
            rs.close();
            pstmt.close();

            System.out.println(dirname);

            conn.setAutoCommit(false);
            if (dbtype.equals("oracle"))
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_ORACLE);
            else  if (dbtype.equals("mssql"))
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_MSSQL);
            else
                pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_MYSQL);
            pstmt.setInt(1, pic.getColumnID());
            pstmt.setInt(2, sortid);
            pstmt.setString(3, pic.getImgfile());
            pstmt.setString(4, "");
            pstmt.setString(5, "");
            pstmt.setString(6, "");
            pstmt.setString(7, "");
            pstmt.setString(8, "<html><head></head><body></body></html>");
            pstmt.setInt(9, 1);
            pstmt.setString(10, "");
            pstmt.setTimestamp(11, nowtime);
            pstmt.setTimestamp(12, nowtime);
            pstmt.setString(13, pic.getImgfile());
            pstmt.setInt(14, 0);
            pstmt.setInt(15, 0);
            pstmt.setInt(16, 1);
            pstmt.setInt(17, 0);
            pstmt.setInt(18, 0);
            pstmt.setString(19, pic.getEditor());
            pstmt.setString(20, dirname);
            pstmt.setTimestamp(21, nowtime);
            pstmt.setFloat(22, 0);
            pstmt.setFloat(23, 0);
            pstmt.setFloat(24, 0);
            pstmt.setInt(25, 0);
            pstmt.setInt(26, 0);
            pstmt.setString(27, "");
            pstmt.setString(28, "");
            pstmt.setString(29, "");
            pstmt.setString(30, "");
            pstmt.setInt(31, 0);
            pstmt.setInt(32, 0);
            pstmt.setInt(33, 0);
            pstmt.setInt(34, 0);
            pstmt.setInt(35, 0);
            pstmt.setInt(36, 0);
            pstmt.setInt(37, 0);
            pstmt.setInt(38, 0);
            pstmt.setInt(39, pic.getSiteID());
            if (dbtype.equals("oracle")) {
                pstmt.setInt(40, newid);
                pstmt.executeUpdate();
                pstmt.close();
            } else if (dbtype.equals("mssql")) {
                rs = pstmt.executeQuery();
                if(rs.next()){
                    newid = rs.getInt(1);
                }
                rs.close();
                pstmt.close();
            } else {
                pstmt.executeUpdate();
                pstmt.close();

                //获取Mysql自增列的值id
                pstmt = conn.prepareStatement("select LAST_INSERT_ID()");
                rs = pstmt.executeQuery();
                if (rs.next()) newid = rs.getInt(1);
                rs.close();
                pstmt.close();
            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            CmsServer.getInstance().freeConnection(conn);
        }

        System.out.println("newid=" + newid);
        return newid;
    }

    public void saveEntry(String s, ZipEntry target, int siteid, int columnid, String editor) throws IOException {
        try {
            if (!s.endsWith(File.separator)) s = s + File.separator;

            String filename = s + target.getName();
            System.out.println("filename=" + filename);
            if (!checkFile(filename)) return;
            File file = new File(filename);
            if (target.isDirectory()) {
                file.mkdirs();
            } else {
                InputStream is = zf.getInputStream(target);
                BufferedInputStream bis = new BufferedInputStream(is);
                File dir = new File(file.getParent());
                dir.mkdirs();

                FileOutputStream fos = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                int c;
                while ((c = bis.read()) != -1) {
                    bos.write((byte) c);
                }
                bos.close();
                fos.close();

                int width = 0, height = 0;
                BufferedImage bi = ImageIO.read(file);
                width = bi.getWidth();
                height = bi.getHeight();
                Picture pic = new Picture();
                pic.setColumnID(columnid);
                pic.setSiteID(siteid);
                pic.setCreateDate(new Timestamp(System.currentTimeMillis()));
                pic.setHeight(height);
                pic.setWidth(width);
                pic.setImgfile(file.getName());
                pic.setSize(target.getSize());
                pic.setEditor(editor);
                int picid = CreateImageToArticle(pic);
                if (picid > 0) {
                    String newname = String.valueOf(picid) + file.getName().substring(file.getName().indexOf("."));
                    File newfile = new File(file.getParent() + File.separator + newname);
                } else {
                    file.delete();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkFile(String filename) {
        int posi = filename.lastIndexOf(".");
        String extname = "";
        if (posi == -1)
            return false;
        else {
            extname = filename.substring(posi);
            System.out.println("extname=" + extname);
            if (extname.toLowerCase().equals(".jpg") || extname.toLowerCase().equals(".jpeg") || extname.toLowerCase().equals(".png") || extname.toLowerCase().equals(".gif") || extname.toLowerCase().equals(".bmp")) {
                return true;
            } else {
                return false;
            }
        }
    }
}
