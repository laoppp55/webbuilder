package com.bizwink.cms.pic;

import com.bizwink.cms.news.Turnpic;
import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.server.FileProps;
import com.bizwink.cms.util.*;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;

import javax.swing.*;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.File;

public class PicPeer implements IPicManager {
    PoolServer cpool;

    private static String propname = "main";
    private static String marktype;
    private static String markImage;
    private static String markText;
    private static String textColor;
    private static String textFont;
    private static String textSize;

    public PicPeer() {
        FileProps props = new FileProps("com/bizwink/cms/server/config.properties");

        marktype = props.getProperty(propname + ".watermark.type");
        markImage = props.getProperty(propname + ".watermark.image");
        markText = props.getProperty(propname + ".watermark.text.content");
        textColor = props.getProperty(propname + ".watermark.text.color");
        textFont = props.getProperty(propname + ".watermark.text.font");
        textSize = props.getProperty(propname + ".watermark.text.size");

        if (markText != null) markText = StringUtil.iso2gbindb(markText);
        if (textFont != null) textFont = StringUtil.iso2gbindb(textFont);
    }

    public PicPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IPicManager getInstance() {
        return CmsServer.getInstance().getFactory().getPicManager();
    }

    private static String CREATE_PIC_INFO_FOR_ORACLE = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname," +
            "imgurl,notes,id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_PIC_INFO_FOR_MSSQL = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname," +
            "imgurl,notes) values(?, ?, ?, ?, ?, ?, ?, ?)";

    private static String CREATE_PIC_INFO_FOR_MYSQL = "insert into tbl_picture(siteid,columnid,width,height,picsize,picname," +
            "imgurl,notes) values(?, ?, ?, ?, ?, ?, ?, ?)";

    public void createPic(List list) {
        Connection conn = null;
        PreparedStatement pstmt;
        ISequenceManager sequnceMgr = SequencePeer.getInstance();
        int id;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_ORACLE);
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MSSQL);
            else
                pstmt = conn.prepareStatement(CREATE_PIC_INFO_FOR_MYSQL);
            for (int i = 0; i < list.size(); i++) {
                Pic pic = (Pic) list.get(i);
                pstmt.setInt(1, pic.getSiteid());
                pstmt.setInt(2, pic.getColumnid());
                pstmt.setInt(3, pic.getWidth());
                pstmt.setInt(4, pic.getHeight());
                pstmt.setInt(5, pic.getPicsize());
                pstmt.setString(6, pic.getPicname());
                pstmt.setString(7, pic.getImgurl());
                pstmt.setString(8, pic.getNotes());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(9, sequnceMgr.getSequenceNum("Pic"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
            }
            pstmt.close();
            conn.commit();
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
    }

    //搜索图片
    public int getPicInfoNum(String picname, int siteid, int type) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int num = 0;
        String sql;
        if (picname == null || picname.equals("") || picname.equals("null")) {
            if (type == 0) {
                sql = "select count(id) from tbl_picture where siteid = ?";
            } else {
                sql = "select count(id) from tbl_picture";
            }
        } else {
            if (type == 0) {
                sql = "select count(id) from tbl_picture where siteid = ? and notes like '%" + picname + "%'";
            } else {
                sql = "select count(id) from tbl_picture where notes like '%" + picname + "%'";
            }
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (type == 0) {
                pstmt.setInt(1, siteid);
            }
            rs = pstmt.executeQuery();
            if (rs.next()) {
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

    public List getPicInfo(String picname, int siteid, int type, int start, int range) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        String sql;
        if (picname == null || picname.equals("") || picname.equals("null")) {
            if (type == 0) {
                sql = "select * from tbl_picture where siteid = ? order by createdate desc";
            } else {
                sql = "select * from tbl_picture order by createdate desc";
            }
        } else {
            if (type == 0) {
                sql = "select * from tbl_picture where siteid = ? and notes like '%" + picname + "%' order by createdate desc";
            } else {
                sql = "select * from tbl_picture where notes like '%" + picname + "%' order by createdate desc";
            }
        }

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (type == 0) {
                pstmt.setInt(1, siteid);
            }
            rs = pstmt.executeQuery();
            for (int i = 0; i < start; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Pic pic = new Pic();
                pic.setId(rs.getInt("id"));
                pic.setColumnid(rs.getInt("columnid"));
                pic.setHeight(rs.getInt("height"));
                pic.setWidth(rs.getInt("width"));
                pic.setImgurl(rs.getString("imgurl"));
                pic.setPicname(rs.getString("picname"));
                pic.setPicsize(rs.getInt("picsize"));
                pic.setCreatedate(rs.getTimestamp("createdate"));
                list.add(pic);
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
        return list;
    }

    public List<Turnpic> getTurpics(int articleid) {
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<Turnpic> list = new ArrayList();
        String sql= "select id,articleid,picname,mediaurl,sortid,createdate,notes,lib1,lib2,lib3,lib4,lib5,lib6,lib7,lib8 from tbl_turnpic where articleid order by sortid asc";
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                Turnpic pic = new Turnpic();
                pic.setId(rs.getInt("id"));
                pic.setArticleid(rs.getInt("articleid"));
                pic.setPicname(rs.getString("picname"));
                pic.setMediaurl(rs.getString("mediaurl"));
                pic.setSortid(rs.getInt("sortid"));
                pic.setCreatedate(rs.getTimestamp("createdate"));
                pic.setNotes(rs.getString("notes"));
                pic.setTab1(rs.getString("lib1"));
                pic.setTab1(rs.getString("lib2"));
                pic.setTab1(rs.getString("lib3"));
                pic.setTab1(rs.getString("lib4"));
                pic.setTab1(rs.getString("lib5"));
                pic.setTab1(rs.getString("lib6"));
                pic.setTab1(rs.getString("lib7"));
                pic.setTab1(rs.getString("lib8"));
                list.add(pic);
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

        return list;
    }
    /**
     * 给图片添加水印
     *
     * @param orgfilepath 原始图片的路径
     * @return 返回提示信息
     */
    public String AddWaterMarkToImg(String orgfilepath,String notes) {
        String returnmsg = "";

        if ((marktype != null) && (ColorUtil.isNumber(marktype))) {
            ImageIcon imgIcon = new ImageIcon(orgfilepath);
            Image theImg = imgIcon.getImage();
            int width = theImg.getWidth(null);
            int height = theImg.getHeight(null);

            BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bimage.createGraphics();
            g.setColor(Color.red);
            g.setBackground(Color.white);
            g.drawImage(theImg, 0, 0, null);

            if (Integer.parseInt(marktype) == 1) {
                if (markImage != null) {
                    File file = new File(markImage);
                    if (file.exists()) {
                        ImageIcon waterIcon = new ImageIcon(markImage);
                        Image waterImg = waterIcon.getImage();
                        int wwidth = waterImg.getWidth(null);
                        int wheight = waterImg.getHeight(null);

                        if ((width > (wwidth + 10)) && (height > (wheight + 10)))
                            g.drawImage(waterImg, width - wwidth - 10, height - wheight - 10, null);  //添加图片水印
                        else
                            returnmsg = "水印图标超出原始图片范围，无法添加水印";
                    } else {
                        returnmsg = "水印图标不存在";
                    }
                } else {
                    returnmsg = "没有设置水印图标";
                }
            } else if (Integer.parseInt(marktype) == 2) {
                if (markText != null) {
                    if (width > markText.length() && height > 40) {
                        g.setColor(ColorUtil.parseToColor(textColor));
                        Font f = new Font(textFont, Font.BOLD, Integer.parseInt(textSize));
                        g.setFont(f);
                        g.drawString(markText, markText.length(), 40);  // 添加文字水印
                    } else
                        returnmsg = "水印文字超出原始图片范围，无法添加水印";
                } else {
                    returnmsg = "水印文字不存在";
                }
            } else if (Integer.parseInt(marktype) == 3) {
                if (markImage != null) {
                    File file = new File(markImage);
                    if (file.exists()) {
                        ImageIcon waterIcon = new ImageIcon(markImage);
                        Image waterImg = waterIcon.getImage();
                        int wwidth = waterImg.getWidth(null);
                        int wheight = waterImg.getHeight(null);

                        if ((width > (wwidth + 10)) && (height > (wheight + 10)))
                            g.drawImage(waterImg, width - wwidth - 10, height - wheight - 10, null);  //添加图片水印
                        else
                            returnmsg = returnmsg + "水印图标超出原始图片范围，无法添加水印<br>";
                    } else {
                        returnmsg = returnmsg + "水印图标不存在<br>";
                    }
                } else {
                    returnmsg = returnmsg + "没有设置水印图标<br>";
                }

                if (notes!=null) {
                    if (width > notes.length() && height > 40) {
                        FontMetrics fm = g.getFontMetrics ();
                        g.setColor(ColorUtil.parseToColor(textColor));
                        Font f = new Font(textFont, Font.BOLD, Integer.parseInt(textSize));
                        g.setFont(f);
                        int strWidth = fm.stringWidth (notes);
                        //System.out.println(width/2-notes.length());
                        //g.drawString(notes,width/2-notes.length(), height-40);  // 添加文字水印
                        g.drawString(notes,width-strWidth*3-50, height-40);  // 添加文字水印
                    } else
                        returnmsg = returnmsg + "水印文字超出原始图片范围，无法添加水印<br>";
                } else {
                    if (markText != null) {
                        if (width > markText.length() && height > 40) {
                            g.setColor(ColorUtil.parseToColor(textColor));
                            Font f = new Font(textFont, Font.BOLD, Integer.parseInt(textSize));
                            g.setFont(f);
                            g.drawString(markText, markText.length(), 40);  // 添加文字水印
                        } else
                            returnmsg = returnmsg + "水印文字超出原始图片范围，无法添加水印<br>";
                    } else {
                        returnmsg = returnmsg + "水印文字不存在<br>";
                    }
                }
            } else {
                returnmsg = "水印类别设置错误";
            }
            g.dispose();

            if (Integer.parseInt(marktype) != 3) {
                if (returnmsg.length() == 0) {
                    try {
                        FileOutputStream out = new FileOutputStream(orgfilepath);
                        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
                        param.setQuality(1, true);
                        encoder.encode(bimage, param);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else
                try {
                    FileOutputStream out = new FileOutputStream(orgfilepath);
                    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                    JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
                    param.setQuality(1, true);
                    encoder.encode(bimage, param);
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } else {
            returnmsg = "没有设置水印类别";
        }
        return returnmsg;
    }

    public String saveOnePicture(Pic picture) {
        Connection conn = null;
        PreparedStatement pstmt;
        int picid = 0;
        String picfilename = picture.getPicname();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            else
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            pstmt.setInt(1, picture.getSiteid());
            pstmt.setInt(2, picture.getColumnid());
            pstmt.setInt(3, picture.getWidth());
            pstmt.setInt(4, picture.getHeight());
            pstmt.setInt(5, picture.getPicsize());
            pstmt.setString(6, picfilename);
            pstmt.setString(7, picture.getImgurl());
            pstmt.setTimestamp(8, picture.getCreatedate());
            if (cpool.getType().equals("oracle")) {
                pstmt.setInt(9, sequnceMgr.getSequenceNum("Pic"));
                pstmt.executeUpdate();
            } else if (cpool.getType().equals("mssql")) {
                pstmt.executeUpdate();
            } else {
                pstmt.executeUpdate();
            }
            pstmt.close();
            conn.commit();
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
        return picfilename;
    }

    public List saveMorePicture(List picture) {
        Connection conn = null;
        PreparedStatement pstmt;
        String picfilename;
        int picid = 0;
        List fileNameList = new ArrayList();
        ISequenceManager sequnceMgr = SequencePeer.getInstance();

        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);

            if (cpool.getType().equalsIgnoreCase("oracle"))
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate,id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            else  if (cpool.getType().equalsIgnoreCase("mssql"))
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            else
                pstmt = conn.prepareStatement("INSERT INTO tbl_picture (siteid,columnid,width,height,picsize," +
                        "picname,imgurl,createdate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

            for (int i = 0; i < picture.size(); i++) {
                Pic pic = (Pic) picture.get(i);
                picfilename = pic.getPicname();
                pstmt.setInt(1, pic.getSiteid());
                pstmt.setInt(2, pic.getColumnid());
                pstmt.setInt(3, pic.getWidth());
                pstmt.setInt(4, pic.getHeight());
                pstmt.setInt(5, pic.getPicsize());
                pstmt.setString(6, picfilename);
                pstmt.setString(7, pic.getImgurl());
                pstmt.setTimestamp(8, pic.getCreatedate());
                if (cpool.getType().equals("oracle")) {
                    pstmt.setInt(9, sequnceMgr.getSequenceNum("Pic"));
                    pstmt.executeUpdate();
                } else if (cpool.getType().equals("mssql")) {
                    pstmt.executeUpdate();
                } else {
                    pstmt.executeUpdate();
                }
                fileNameList.add(picfilename);
            }
            pstmt.close();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return fileNameList;
    }

    private static final String SQL_GETPicture =
            "SELECT count(ID) FROM TBL_Picture where picname = ? and siteid = ?";

    public boolean existThePicture(String name, int siteid) {
        boolean retcode = false;
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        int count = 0;

        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(SQL_GETPicture);
            pstmt.setString(1, name);
            pstmt.setInt(2, siteid);
            rs = pstmt.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
            rs.close();
            pstmt.close();
            if (count > 0) retcode = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }

        return retcode;
    }
}
