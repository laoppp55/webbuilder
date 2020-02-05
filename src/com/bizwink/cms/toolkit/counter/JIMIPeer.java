package com.bizwink.cms.toolkit.counter;

import java.util.Random;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Frame;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;

import com.bizwink.cms.server.*;

import java.sql.*;

public class JIMIPeer implements IJIMIManager {
    PoolServer cpool;

    public JIMIPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IJIMIManager getInstance() {
        return CmsServer.getInstance().getFactory().getJIMIManager();
    }

    final static int ImageWidth = 120;   //图片宽度
    final static int ImageHeight = 20;   //图片高度
    final static int num = 40;           //图片点数
    final static Color bgColor = new Color(000, 000, 000);
    final static int len = 6;            //计数器长度

    Random rd = new Random();
    int rdGet;

    private Graphics graphics;
    private Image image;

    public void createImage(String code, OutputStream stream) throws IOException {
        try {
            Frame f = new Frame();
            f.setVisible(true);
            image = f.createImage(ImageWidth, ImageHeight);
            graphics = image.getGraphics();

            f.setVisible(false);
            drawStr(code);

            JimiWriter writer = Jimi.createJimiWriter("image/png", stream);
            writer.setSource(image);
            writer.putImage(stream);
        }
        catch (JimiException je) {
            throw new IOException(je.getMessage());
        }
    }

    public void drawStr(String code) {
        //图片背景
        graphics.setColor(bgColor);
        graphics.fillRect(0, 0, ImageWidth, ImageHeight);

        //生成字体形式
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < code.length(); i++) {
            Font font = new Font("宋体",
                    Font.BOLD,
                    15);

            graphics.setFont(font);

            //设置字体颜色
            Color color = new Color(255, 255, 255);
            graphics.setColor(color);
            //写字
            graphics.drawString(code.substring(i, i + 1), 5 + i * 20, 15);
        }
    }

    public int getRand(int max) {
        rdGet = Math.abs(rd.nextInt()) % max;

        return rdGet;
    }

    public long getCount(int siteid) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        long count = 0;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM tbl_counter where siteid = ?");
            pstmt.setInt(1, siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getLong("countnum");
            }
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
                    System.out.println("Close the connection failed.");
                }
            }
        }
        return count;
    }

    public void insertCount(int siteid, long num) {
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("update tbl_counter set countnum = ? where siteid = ?");
            pstmt.setLong(1, num);
            pstmt.setInt(2, siteid);
            pstmt.executeUpdate();

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
                    System.out.println("Close the connection failed.");
                }
            }
        }
    }

    public String getCode(String num) {
        String code = "";

        for (int i = 0; i < num.length(); i = i + 3) {
            code = code + (char) Integer.parseInt(num.substring(i, i + 3));
        }
        return code;
    }
}
