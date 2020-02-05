package com.bizwink.images;
/*
 * StockGraphProducer
*/

import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.FontMetrics;

import java.sql.*;
import java.util.*;
import java.text.DecimalFormat;
import com.bizwink.calendar.*;
import java.text.DateFormat;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 *  Draw a simple stock price graph for a one week period
 *  The stock is Sun Microsystems for a week in March, 2000.
 *
 */
public class StockGraphProducer implements ImageProducer
{
    private static int ImageWidth = 582;
    private static int ImageHeight = 347;

    private static int VertInset = 25;
    private static int HorzInset = 25;
    private static int HatchLength = 10;
    private Graphics2D graphics;

    /**
     *  Request the producer create an image
     *
     *  @param stream stream to write image into
     *  @return image type
     */
    public String createImage(OutputStream stream) throws IOException
    {
        return "";
    }

    private static Connection getConnection(String driver,String url,String userid,String passwd) {
        Connection conn = null;
        try {

            Class.forName(driver);
            String dburl = url;
            String dbuser = userid;
            String dbpass = passwd;
            conn = DriverManager.getConnection(dburl, dbuser, dbpass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public String createDayK_LineImage(OutputStream stream,String stockcode) throws IOException
    {
        int number = 0;
        //大图
        int leng = 582;
        int width = 347;
        int left = 60;
        int v0=25;
        int v1=220;
        int v2=240;
        int v3=330;

        int ww=1;
        int pppp=5;
        int hang2 = (v3-v2)/5;
        int hang1 = (v1-v0)/8;

        v0=v1-hang1*8;
        v2=v3-hang2*5;
        int lie = (leng-left-15)/8;
        while(lie % 4 != 0) lie = lie-1;
        int right = lie*8 + left;
        int right1 = lie*8 + left + pppp;
        int count = lie*8/(ww*2 + 4);

        System.out.println("count=" + count);

        float[] open_ = null;
        float[] close_ = null;
        float[] max_ = null;
        float[] min_ = null;
        float[] trans_ = null;
        String[] trans_date = null;

        float[] ma5 = null;
        float[] ma10 = null;
        float[] ma30 = null;

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

        BufferedImage bi = new BufferedImage(ImageWidth,
                ImageHeight,
                BufferedImage.TYPE_BYTE_INDEXED);

        graphics = bi.createGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        graphics.setColor(Color.black);
        graphics.drawRect(left,v0,right1-60,v1-v0);
        graphics.drawRect(left,v2,right1-60,v3-v2);

        //画曲线图中的格子线
        for(int i=1;i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left,v0 + i*hang1,left+5,v0+i*hang1);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left,v0 + i*hang1,right1-5,v0+i*hang1);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v0 + i*hang1,right1,v0+i*hang1);
        }

        //画直方图中的格子线
        for(int i=1;i<5;i++) {
            graphics.drawLine(left,v2 + i*hang2,left+5,v2+i*hang2);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left+5,v2 + i*hang2,right1-5,v2+i*hang2);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v2 + i*hang2,right1,v2+i*hang2);
        }

        //画竖线，偶数线是实线，奇数线是虚线
        for(int i=1; i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
            graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
            graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
            graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
            if(i % 2 == 0) {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
                graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
            } else {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
                graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
            }
        }

        //画5日均线，10日均线，30日均线的图例
        graphics.setColor(Color.black);
        graphics.drawString("MA5:",left+20,v0-8);
        graphics.drawString("MA10:",left+100,v0-8);
        graphics.drawString("MA30:",left+180,v0-8);

        for(int j=-(ww+1); j<=ww+1; j++) {
            graphics.setColor(Color.red);
            graphics.drawLine(left+60,v0-12+j,left+70,v0-12+j);
            graphics.setColor(Color.green);
            graphics.drawLine(left+140,v0-12+j,left+150,v0-12+j);
            graphics.setColor(Color.blue);
            graphics.drawLine(left+220,v0-12+j,left+230,v0-12+j);
        }

        int rows = 0;
        float max_p = 0;
        float min_p = 0;
        float max_a = 0;
        float max_price = 0;
        float min_price = 0;
        float[] axis_value = new float[9];
        float[] axis_amount = new float[5];
        float max_amount = 0;

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String driver =props.getProperty("main.db.driver");
        String dburl =props.getProperty("main.db.url");
        String userid =props.getProperty("main.db.username");
        String passwd =props.getProperty("main.db.password");
        Connection conn = getConnection(driver,dburl,userid,passwd);
        PreparedStatement pstmt;
        ResultSet rs = null;

        String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE stockcode=? ORDER BY THEDATE DESC";
        String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE stockcode=?";

        try {
            pstmt = conn.prepareStatement(sql_to_count);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) rows = rs.getInt(1);
            rs.close();
            pstmt.close();

            open_ = new float[rows+2];
            close_ = new float[rows+2];
            max_ = new float[rows+2];
            min_ = new float[rows+2];
            trans_ = new float[rows+2];
            trans_date = new String[rows+2];

            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                float ooo = rs.getFloat(1);
                float hhh = rs.getFloat(2);
                float lll = rs.getFloat(3);
                float ccc = rs.getFloat(4);
                float uuu = rs.getFloat(5);
                String ddd = rs.getString(6);

                open_[number] = ooo;
                close_[number] = ccc;
                max_[number] = hhh;
                min_[number] = lll;
                trans_[number] = uuu;
                trans_date[number] = ddd;

                if (number == 0) {
                    max_p = max_[0];
                    min_p = min_[0];
                    max_a = trans_[0];
                }

                int abcd = 0;
                if (number > count)
                    abcd =count;
                else
                    abcd = number;

                ma5 = new float[abcd+1];
                ma10 = new float[abcd+1];
                ma30 = new float[abcd+1];
                boolean ma5flag = false;
                boolean ma10flag = false;
                boolean ma30flag = false;

                for (int i=0; i<=abcd; i++) {
                    if ((i + 4) <= number) {
                        ma5[i] = 0;
                        for(int j=5; j>0; j--) {
                            ma5[i] = ma5[i] + close_[i-j+5]*j;
                        }
                        ma5flag = true;
                        ma5[i] = ma5[i]/15;        //取5天的算术平均值
                    }

                    if ((i + 9) <= number) {     //取10天的算术平均值
                        ma10[i] = 0;
                        for(int j=10; j>0; j--) {
                            ma10[i] = ma10[i] + close_[i-j+10]*j;
                        }
                        ma10flag = true;
                        ma10[i] = ma10[i]/55;
                    }

                    if ((i + 29) <= number) {     //取30天的算术平均值
                        ma30[i] = 0;
                        for(int j=30; j>0; j--) {
                            ma30[i] = ma30[i] + close_[i-j+30]*j;
                        }
                        ma30flag = true;
                        ma30[i] = ma30[i]/465;
                    }
                }

                if (max_p < max_[number]) { max_p = max_[number]; }
                if (min_p > min_[number] && min_[number] !=0) { min_p = min_[number]; }
                if (max_a < trans_[number]) { max_a = trans_[number]; }

                max_price = (float)(max_p + (max_p - min_p)*0.1);
                min_price = (float)(min_p - (max_p - min_p)*0.1);

                for(int i=0; i<9; i++) {
                    axis_value[i] = min_price + i*(max_price-min_price)/8;
                }

                max_amount = max_a;
                for(int i=0; i<5; i++) {
                    //每手股票是100股
                    axis_amount[i] = i*max_amount/500;
                }

                if (number > 110)
                    break;
                else
                    number = number + 1;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        System.out.println("number=" + number);

        if (number > 30) {
            //画股票价格Y轴
            for(int i=0; i<9; i++) {
                int temp = v1 - i*hang1+4;
                String q = axis_value[i] + "";
                int posi = q.indexOf(".");
                int xiaoshu_length = q.substring(posi + 1).length();
                if (xiaoshu_length >= 2)
                    q = q.substring(0,q.indexOf(".")+3);
                else
                    q = q + "0";
                graphics.drawString(q,left-5-q.length()*6,temp);
            }

            //画股票交易量的Y轴
            DecimalFormat df = new DecimalFormat("0");
            for(int i=0; i<5; i++) {
                String q = df.format(axis_amount[i]/100);
                graphics.drawString(q,left-10-q.length()*6,v3-i*hang2+4);
            }

            int abcd = 0;
            //for(int k=0; k<1; k++) {
            //画K线图
            int old_x_ma5 = right - (ww*2+2);
            int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma10 = right - (ww*2+2);
            int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma30 = right - (ww*2+2);
            int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

            if (number > count)
                abcd = count;
            else
                abcd = number;

            for (int i=0; i<=abcd-1; i++) {
                int temp = i*(ww*2 + 4);
                float h1 = trans_[i]*(v3-v2)/max_amount;
                float y1 = 0;
                if (h1 < 0)
                    y1 = v3+h1;
                else
                    y1 = v3-h1;

/*        if (open_[i] > close_[i]) {
            graphics.setColor(Color.green);
            for(int j=-ww; j<=ww; j++) {
              graphics.drawLine(right-temp+j,v3-1,right-temp+j,(int)y1);
              graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
            }
        }
        else {
            graphics.setColor(Color.red);
            graphics.drawRect(right-temp-1,v3-(int)h1,3,(int)h1);
            for(int j=-ww; j<=ww; j++) {
              graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
            }
        }
*/
                if (open_[i] > close_[i])
                    graphics.setColor(Color.green);
                else
                    graphics.setColor(Color.red);

                for(int j=-ww; j<=ww; j++) {
                    //绘制成交量
                    graphics.drawLine(right-temp+j,v3-1,right-temp+j,(int)y1);
                    //绘制阳线和阴线的方块图
                    graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
                }

                //绘制阳线和阴线的上下的短线
                graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

                //画日期轴
                if (temp % lie ==0 && trans_date[i] != null) {
                    graphics.setColor(Color.black);
                    graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                }

                //画日期轴的第一项
                //if (i == 1 && trans_date[i] != null) {
                //  graphics.setColor(Color.black);
                //  graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                //}

                if(i<=ma5.length) {
                    graphics.setColor(Color.red);
                    graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma5 = right - temp;
                    old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma10.length) {
                    graphics.setColor(Color.green);
                    graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma10 = right - temp;
                    old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma30.length) {
                    graphics.setColor(Color.blue);
                    graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma30 = right - temp;
                    old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
                }
            }
            //}

            encoder.encode(bi);
        }
        return "image/jpg";
    }


    public String createSmallDayK_LineImage(OutputStream stream,String stockcode) throws IOException
    {
        int number = 0;
        //大图
        int leng = 200;
        int width = 188;

        int ww=1;
        int pppp=5;
        int hang_h = width/16;
        int v0 = hang_h;
        int v1 = hang_h * 9;
        int v2 = hang_h * 10;
        int v3 = hang_h * 15;

        int lie = leng/11;
        int left = 2*lie;
        int right = lie*8;
        int right1 = lie*10 + pppp;
        int count = lie*8/(ww*2 + 4);

        //System.out.println("count=" + count);

        float[] open_ = null;
        float[] close_ = null;
        float[] max_ = null;
        float[] min_ = null;
        float[] trans_ = null;
        String[] trans_date = null;

        float[] ma5 = null;
        float[] ma10 = null;
        float[] ma30 = null;

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);
        BufferedImage bi = new BufferedImage(leng,width, BufferedImage.TYPE_BYTE_INDEXED);
        graphics = bi.createGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        graphics.setColor(Color.black);
        graphics.drawRect(left,v2,right+5,v3-v2);
        graphics.drawRect(left,v0,right+5,v1-v0);

        //画曲线图中的格子线
        for(int i=1;i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left,v0 + i*hang_h,left+5,v0+i*hang_h);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left+5,v0 + i*hang_h,right1-5,v0+i*hang_h);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v0 + i*hang_h,right1,v0+i*hang_h);
        }

        //画直方图中的格子线
        for(int i=1;i<5;i++) {
            graphics.drawLine(left,v2 + i*hang_h,left+5,v2+i*hang_h);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left+5,v2 + i*hang_h,right1-5,v2+i*hang_h);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v2 + i*hang_h,right1,v2+i*hang_h);
        }

        //画竖线，偶数线是实线，奇数线是虚线
        for(int i=1; i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
            graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
            graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
            graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
            if(i % 2 == 0) {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
                graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
            } else {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
                graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
            }
        }

        //画5日均线，10日均线，30日均线的图例
        graphics.setColor(Color.black);
        graphics.drawString("MA5:",2*left,v0);
        graphics.drawString("MA10:",3*left,v0);
        graphics.drawString("MA30:",4*left,v0);

        for(int j=-(ww+1); j<=ww+1; j++) {
            graphics.setColor(Color.red);
            graphics.drawLine(2*left+30,v0-5+j,2*left+35,v0-5+j);
            graphics.setColor(Color.green);
            graphics.drawLine(3*left+30,v0-5+j,3*left+35,v0-5+j);
            graphics.setColor(Color.blue);
            graphics.drawLine(4*left+30,v0-5+j,4*left+35,v0-5+j);
        }

        int rows = 0;
        float max_p = 0;
        float min_p = 0;
        float max_a = 0;
        float max_price = 0;
        float min_price = 0;
        float[] axis_value = new float[9];
        float[] axis_amount = new float[5];
        float max_amount = 0;
        right = right1;
        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String driver =props.getProperty("main.db.driver");
        String dburl =props.getProperty("main.db.url");
        String userid =props.getProperty("main.db.username");
        String passwd =props.getProperty("main.db.password");
        Connection conn = getConnection(driver,dburl,userid,passwd);
        PreparedStatement pstmt;
        ResultSet rs = null;

        String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE stockcode=? ORDER BY THEDATE DESC";
        String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE stockcode=?";

        try {
            pstmt = conn.prepareStatement(sql_to_count);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) rows = rs.getInt(1);
            rs.close();
            pstmt.close();

            open_ = new float[rows+2];
            close_ = new float[rows+2];
            max_ = new float[rows+2];
            min_ = new float[rows+2];
            trans_ = new float[rows+2];
            trans_date = new String[rows+2];

            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                float ooo = rs.getFloat(1);
                float hhh = rs.getFloat(2);
                float lll = rs.getFloat(3);
                float ccc = rs.getFloat(4);
                float uuu = rs.getFloat(5);
                String ddd = rs.getString(6);

                open_[number] = ooo;
                close_[number] = ccc;
                max_[number] = hhh;
                min_[number] = lll;
                trans_[number] = uuu;
                trans_date[number] = ddd;

                if (number == 0) {
                    max_p = max_[0];
                    min_p = min_[0];
                    max_a = trans_[0];
                }

                int abcd = 0;
                if (number > count)
                    abcd =count;
                else
                    abcd = number;

                ma5 = new float[abcd+1];
                ma10 = new float[abcd+1];
                ma30 = new float[abcd+1];
                boolean ma5flag = false;
                boolean ma10flag = false;
                boolean ma30flag = false;

                for (int i=0; i<=abcd; i++) {
                    if ((i + 4) <= number) {
                        ma5[i] = 0;
                        for(int j=5; j>0; j--) {
                            ma5[i] = ma5[i] + close_[i-j+5]*j;
                        }
                        ma5flag = true;
                        ma5[i] = ma5[i]/15;        //取5天的算术平均值
                    }

                    if ((i + 9) <= number) {     //取10天的算术平均值
                        ma10[i] = 0;
                        for(int j=10; j>0; j--) {
                            ma10[i] = ma10[i] + close_[i-j+10]*j;
                        }
                        ma10flag = true;
                        ma10[i] = ma10[i]/55;
                    }

                    if ((i + 29) <= number) {     //取30天的算术平均值
                        ma30[i] = 0;
                        for(int j=30; j>0; j--) {
                            ma30[i] = ma30[i] + close_[i-j+30]*j;
                        }
                        ma30flag = true;
                        ma30[i] = ma30[i]/465;
                    }
                }

                if (max_p < max_[number]) { max_p = max_[number]; }
                if (min_p > min_[number] && min_[number] !=0) { min_p = min_[number]; }
                if (max_a < trans_[number]) { max_a = trans_[number]; }

                max_price = (float)(max_p + (max_p - min_p)*0.1);
                min_price = (float)(min_p - (max_p - min_p)*0.1);

                for(int i=0; i<9; i++) {
                    axis_value[i] = min_price + i*(max_price-min_price)/8;
                }

                max_amount = max_a;
                for(int i=0; i<5; i++) {
                    //每手股票是100股
                    axis_amount[i] = i*max_amount/500;
                }

                if (number > 110)
                    break;
                else
                    number = number + 1;
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        System.out.println("number=" + number);

        if (number > 30) {
            //画股票价格Y轴
            for(int i=0; i<9; i++) {
                int temp = v1 - i*hang_h+4;
                String q = axis_value[i] + "";
                int posi = q.indexOf(".");
                int xiaoshu_length = q.substring(posi + 1).length();
                if (xiaoshu_length >= 2)
                    q = q.substring(0,q.indexOf(".")+3);
                else
                    q = q + "0";
                graphics.drawString(q,left-5-q.length()*6,temp);
            }

            //画股票交易量的Y轴
            DecimalFormat df = new DecimalFormat("0");
            for(int i=0; i<5; i++) {
                String q = df.format(axis_amount[i]/100);
                graphics.drawString(q,left-10-q.length()*6,v3-i*hang_h+4);
            }

            int abcd = 0;
            //for(int k=0; k<1; k++) {
            //画K线图
            int old_x_ma5 = right - (ww*2+2);
            int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma10 = right - (ww*2+2);
            int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma30 = right - (ww*2+2);
            int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

            if (number > count)
                abcd = count;
            else
                abcd = number;

            for (int i=0; i<=abcd-1; i++) {
                int temp = i*(ww*2 + 4);
                float h1 = trans_[i]*(v3-v2)/max_amount;
                float y1 = 0;
                if (h1 < 0)
                    y1 = v3+h1;
                else
                    y1 = v3-h1;

                if (open_[i] > close_[i])
                    graphics.setColor(Color.green);
                else
                    graphics.setColor(Color.red);

                for(int j=-ww; j<=ww; j++) {
                    //绘制成交量
                    graphics.drawLine(right-temp+j,v3-1,right-temp+j,(int)y1);
                    //绘制阳线和阴线的方块图
                    graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
                }

                //绘制阳线和阴线的上下的短线
                graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

                //画日期轴
                if (temp % lie ==0 && trans_date[i] != null) {
                    graphics.setColor(Color.black);
                    graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+10);
                }

                //画日期轴的第一项
                //if (i == 1 && trans_date[i] != null) {
                //  graphics.setColor(Color.black);
                //  graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                //}

                if(i<=ma5.length) {
                    graphics.setColor(Color.red);
                    graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma5 = right - temp;
                    old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma10.length) {
                    graphics.setColor(Color.green);
                    graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma10 = right - temp;
                    old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma30.length) {
                    graphics.setColor(Color.blue);
                    graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma30 = right - temp;
                    old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
                }
            }
            //}

            encoder.encode(bi);
        }
        return "image/jpg";
    }

    public String createWeekK_LineImage(OutputStream stream,String stockcode) throws IOException
    {
        int number = 0;
        //大图
        int leng = 582;
        int width = 347;
        int left = 60;
        int v0=25;
        int v1=220;
        int v2=240;
        int v3=330;

        int ww=1;
        int pppp=5;
        int hang2 = (v3-v2)/5;
        int hang1 = (v1-v0)/8;

        v0=v1-hang1*8;
        v2=v3-hang2*5;
        int lie = (leng-left-15)/8;
        while(lie % 4 != 0) lie = lie-1;
        int right = lie*8 + left;
        int right1 = lie*8 + left + pppp;
        int count = lie*8/(ww*2 + 4);

        float[] open_ = null;
        float[] close_ = null;
        float[] max_ = null;
        float[] min_ = null;
        float[] trans_ = null;
        String[] trans_date = null;

        float[] ma5 = null;
        float[] ma10 = null;
        float[] ma30 = null;

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

        BufferedImage bi = new BufferedImage(ImageWidth,
                ImageHeight,
                BufferedImage.TYPE_BYTE_INDEXED);

        graphics = bi.createGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        graphics.setColor(Color.black);
        graphics.drawRect(left,v0,right1-60,v1-v0);
        graphics.drawRect(left,v2,right1-60,v3-v2);

        //画曲线图中的格子线
        for(int i=1;i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left,v0 + i*hang1,left+5,v0+i*hang1);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left,v0 + i*hang1,right1-5,v0+i*hang1);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v0 + i*hang1,right1,v0+i*hang1);
        }

        //画直方图中的格子线
        for(int i=1;i<5;i++) {
            graphics.drawLine(left,v2 + i*hang2,left+5,v2+i*hang2);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left+5,v2 + i*hang2,right1-5,v2+i*hang2);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v2 + i*hang2,right1,v2+i*hang2);
        }

        //画竖线，偶数线是实线，奇数线是虚线
        for(int i=1; i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
            graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
            graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
            graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
            if(i % 2 == 0) {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
                graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
            } else {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
                graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
            }
        }

        //画5日均线，10日均线，30日均线的图例
        graphics.setColor(Color.black);
        graphics.drawString("MA5:",left+20,v0-8);
        graphics.drawString("MA10:",left+100,v0-8);
        graphics.drawString("MA30:",left+180,v0-8);

        for(int j=-(ww+1); j<=ww+1; j++) {
            graphics.setColor(Color.red);
            graphics.drawLine(left+60,v0-12+j,left+70,v0-12+j);
            graphics.setColor(Color.green);
            graphics.drawLine(left+140,v0-12+j,left+150,v0-12+j);
            graphics.setColor(Color.blue);
            graphics.drawLine(left+220,v0-12+j,left+230,v0-12+j);
        }

        int rows = 0;
        float max_p = 0;
        float min_p = 0;
        float max_a = 0;
        float max_price = 0;
        float min_price = 0;
        float[] axis_value = new float[9];
        float[] axis_amount = new float[5];
        float max_amount = 0;

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.FRIDAY);
        int fridayCounter = 0;
        String[][] weeks90 = new String[2][257];


        while (fridayCounter <= 256) {
            // Go to the next Friday by adding 7 days.
            cal.add(GregorianCalendar.DAY_OF_MONTH,-7);
            String datestr = dateFormat.format(cal.getTime());
            int yposi =datestr.indexOf("年");
            int year = Integer.parseInt(datestr.substring(0,yposi));
            int mposi =datestr.indexOf("月");
            int month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            int dposi = datestr.indexOf("日");
            int day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            weeks90[0][fridayCounter] =year + "-" + month+ "-" +day;
            fridayCounter++;
        }

        cal.setTime(new java.util.Date());
        cal.set(GregorianCalendar.DAY_OF_WEEK, GregorianCalendar.MONDAY);
        int mondayCounter = 0;
        while (mondayCounter <= 256) {
            // Go to the next Friday by adding 7 days.
            cal.add(GregorianCalendar.DAY_OF_MONTH,-7);
            String datestr = dateFormat.format(cal.getTime());
            int yposi =datestr.indexOf("年");
            int year = Integer.parseInt(datestr.substring(0,yposi));
            int mposi =datestr.indexOf("月");
            int month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            int dposi = datestr.indexOf("日");
            int day = Integer.parseInt(datestr.substring(mposi+1,dposi));
            weeks90[1][mondayCounter] = year + "-" + month+ "-" +day;
            mondayCounter++;
        }

        String datewhere = "";
        for(int i=0; i<256; i++) {
            datewhere = datewhere + "to_date('" +weeks90[0][i] + "','yyyy-mm-dd'),";
        }
        datewhere = "(" + datewhere + "to_date('" + weeks90[0][256] +"','yyyy-mm-dd'))";

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String driver =props.getProperty("main.db.driver");
        String dburl =props.getProperty("main.db.url");
        String userid =props.getProperty("main.db.username");
        String passwd =props.getProperty("main.db.password");
        Connection conn = getConnection(driver,dburl,userid,passwd);
        PreparedStatement pstmt;
        ResultSet rs = null;
        /*try {
          Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
          conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:2433", "stockmaster", "1qaz2wsx");
        } catch (Exception exp) {
          exp.printStackTrace();
        }*/

        String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE thedate in " +
                datewhere + " and stockcode=? ORDER BY THEDATE DESC";

        //System.out.println("w_sqlstr=" + sqlstr);

        String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE thedate in " +datewhere + " and stockcode=?";
        try {
            pstmt = conn.prepareStatement(sql_to_count);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) rows = rs.getInt(1);
            rs.close();
            pstmt.close();

            open_ = new float[rows+2];
            close_ = new float[rows+2];
            max_ = new float[rows+2];
            min_ = new float[rows+2];
            trans_ = new float[rows+2];
            trans_date = new String[rows+2];

            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                float ooo = rs.getFloat(1);
                float hhh = rs.getFloat(2);
                float lll = rs.getFloat(3);
                float ccc = rs.getFloat(4);
                float uuu = rs.getFloat(5);
                String ddd = rs.getString(6);

                open_[number] = ooo;
                close_[number] = ccc;
                max_[number] = hhh;
                min_[number] = lll;
                trans_[number] = uuu;
                trans_date[number] = ddd;

                if (number == 0) {
                    max_p = max_[0];
                    min_p = min_[0];
                    max_a = trans_[0];
                }

                int abcd = 0;
                if (number > count)
                    abcd =count;
                else
                    abcd = number;

                ma5 = new float[abcd];
                ma10 = new float[abcd];
                ma30 = new float[abcd];
                boolean ma5flag = false;
                boolean ma10flag = false;
                boolean ma30flag = false;

                for (int i=0; i<abcd; i++) {
                    if ((i + 4) <= number) {
                        ma5[i] = 0;
                        for(int j=5; j>0; j--) {
                            ma5[i] = ma5[i] + close_[i-j+5]*j;
                        }
                        ma5flag = true;
                        ma5[i] = ma5[i]/15;
                    }

                    if ((i + 9) <= number) {
                        ma10[i] = 0;
                        for(int j=10; j>0; j--) {
                            ma10[i] = ma10[i] + close_[i-j+10]*j;
                        }
                        ma10flag = true;
                        ma10[i] = ma10[i]/55;
                    }

                    if ((i + 29) <= number) {
                        ma30[i] = 0;
                        for(int j=30; j>0; j--) {
                            ma30[i] = ma30[i] + close_[i-j+30]*j;
                        }
                        ma30flag = true;
                        ma30[i] = ma30[i]/465;
                    }
                }

                if (max_p < max_[number]) { max_p = max_[number]; }
                if (min_p > min_[number] && min_[number] !=0) { min_p = min_[number]; }
                if (max_a < trans_[number]) { max_a = trans_[number]; }

                max_price = (float)(max_p + (max_p - min_p)*0.1);
                min_price = (float)(min_p - (max_p - min_p)*0.1);

                for(int i=0; i<9; i++) {
                    axis_value[i] = min_price + i*(max_price-min_price)/8;
                }

                max_amount = max_a;
                for(int i=0; i<5; i++) {
                    //每手股票是100股
                    axis_amount[i] = i*max_amount/500;
                }

                if (number > 110)
                    break;
                else
                    number = number + 1;
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        //画股票价格Y轴
        if (number>30) {
            for(int i=0; i<9; i++) {
                int temp = v1 - i*hang1+4;
                String q = axis_value[i] + "";
                int posi = q.indexOf(".");
                int xiaoshu_length = q.substring(posi + 1).length();
                if (xiaoshu_length >= 2)
                    q = q.substring(0,q.indexOf(".")+3);
                else
                    q = q + "0";
                graphics.drawString(q,left-5-q.length()*6,temp);
            }

            //画股票交易量的Y轴
            DecimalFormat df = new DecimalFormat("0");
            for(int i=0; i<5; i++) {
                String q = df.format(axis_amount[i]/100);
                graphics.drawString(q,left-10-q.length()*6,v3-i*hang2+4);
            }

            int abcd = 0;
            //for(int k=0; k<rows; k++) {
            //画K线图
            int old_x_ma5 = right - (ww*2+2);
            int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma10 = right - (ww*2+2);
            int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma30 = right - (ww*2+2);
            int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

            if (number > count)
                abcd = count;
            else
                abcd = number;

            System.out.println("abcd=" + abcd);

            for (int i=0; i<abcd-1; i++) {
                int temp = i*(ww*2 + 4);
                if (open_[i] > close_[i])
                    graphics.setColor(Color.green);
                else
                    graphics.setColor(Color.red);
                for(int j=-ww; j<=ww; j++) {
                    float h1 = trans_[i]*(v3-v2)/max_amount;
                    float y1 = 0;
                    if (h1 < 0) y1 = v3+h1; else y1 = v3-h1;
                    graphics.drawLine(right-temp+j,v3-1,right-temp+j,(int)y1);
                    graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
                }

                graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

                //画日期轴的其他项
                if (temp % lie ==0 && trans_date[i] != null) {
                    graphics.setColor(Color.black);
                    graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                }

                //画日期轴的第一项
                //if (i == 1 && trans_date[i] != null) {
                //  graphics.setColor(Color.black);
                //  graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                //}

                if(i<=ma5.length) {
                    graphics.setColor(Color.red);
                    graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma5 = right - temp;
                    old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma10.length) {
                    graphics.setColor(Color.green);
                    graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma10 = right - temp;
                    old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<=ma30.length) {
                    graphics.setColor(Color.blue);
                    graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma30 = right - temp;
                    old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
                }
            }
            //}

            encoder.encode(bi);
        }
        return "image/jpg";
    }

/*  public String createSmallWeekK_LineImage(OutputStream stream) throws IOException
  {
    int number = 0;
    int leng = 210;
    int width = 137;
    int left = 33;
    int v0=39;
    int v1=93;
    int v2=94;
    int v3=120;

    int ww=1;
    int pppp=5;
    int hang2 = (v3-v2)/5;
    int hang1 = (v1-v0)/8;

    v0=v1-hang1*8;
    v2=v3-hang2*5;
    int lie = (leng-left-15)/8;
    while(lie % 4 != 0) lie = lie-1;
    int right = lie*8 + left;
    int right1 = lie*8 + left + pppp;
    int count = lie*8/(ww*2 + 2)-1 + 29;

    float[] open_ = null;
    float[] close_ = null;
    float[] max_ = null;
    float[] min_ = null;
    int[] trans_ = null;
    String[] trans_date = null;

    float[] ma5 = null;
    float[] ma10 = null;
    float[] ma30 = null;

    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

    BufferedImage bi = new BufferedImage(ImageWidth,
                                         ImageHeight,
                                         BufferedImage.TYPE_BYTE_INDEXED);

    graphics = bi.createGraphics();

    graphics.setColor(Color.white);
    graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
    graphics.setColor(Color.black);
    graphics.drawRect(left,v0,right1-60,v1-v0);
    graphics.drawRect(left,v2,right1-60,v3-v2);

    //画曲线图中的格子线
    for(int i=1;i<8;i++) {
      graphics.setColor(Color.black);
      graphics.drawLine(left,v0 + i*hang1,left+5,v0+i*hang1);
      graphics.setColor(Color.lightGray);
      graphics.drawLine(left,v0 + i*hang1,right1-5,v0+i*hang1);
      graphics.setColor(Color.black);
      graphics.drawLine(right1-5,v0 + i*hang1,right1,v0+i*hang1);
    }

    //画直方图中的格子线
    for(int i=1;i<5;i++) {
      graphics.drawLine(left,v2 + i*hang2,left+5,v2+i*hang2);
      graphics.setColor(Color.lightGray);
      graphics.drawLine(left+5,v2 + i*hang2,right1-5,v2+i*hang2);
      graphics.setColor(Color.black);
      graphics.drawLine(right1-5,v2 + i*hang2,right1,v2+i*hang2);
    }

    //画竖线，偶数线是实线，奇数线是虚线
    for(int i=1; i<8;i++) {
      graphics.setColor(Color.black);
      graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
      graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
      graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
      graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
      if(i % 2 == 0) {
        graphics.setColor(Color.lightGray);
        graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
        graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
      } else {
        graphics.setColor(Color.lightGray);
        graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
        graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
      }
    }

    //画5日均线，10日均线，30日均线的图例
    graphics.setColor(Color.black);
    graphics.drawString("MA5:",left+20,v0-8);
    graphics.drawString("MA10:",left+100,v0-8);
    graphics.drawString("MA30:",left+180,v0-8);

    for(int j=-(ww+1); j<=ww+1; j++) {
      graphics.setColor(Color.red);
      graphics.drawLine(left+60,v0-12+j,left+70,v0-12+j);
      graphics.setColor(Color.green);
      graphics.drawLine(left+140,v0-12+j,left+150,v0-12+j);
      graphics.setColor(Color.blue);
      graphics.drawLine(left+220,v0-12+j,left+230,v0-12+j);
    }

    int rows = 0;
    float max_p = 0;
    float min_p = 0;
    int max_a = 0;
    float max_price = 0;
    float min_price = 0;
    float[] axis_value = new float[9];
    int[] axis_amount = new int[5];
    int max_amount = 0;

    com.bizwink.server.FileProps props = new com.bizwink.server.FileProps("com/bizwink/cms/server/config.properties");
    String driver =props.getProperty("main.db.driver");
    String dburl =props.getProperty("main.db.url");
    String userid =props.getProperty("main.db.username");
    String passwd =props.getProperty("main.db.password");
    Connection conn = getConnection(driver,dburl,userid,passwd);
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
      conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:2433", "stockmaster", "1qaz2wsx");
    } catch (Exception exp) {
      exp.printStackTrace();
    }

    String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE GID=0 ORDER BY THEDATE DESC";
    String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE GID=0";
    try {
      int num = 0;
      int dif = 0;
      pstmt = conn.prepareStatement(sql_to_count);
      rs = pstmt.executeQuery();
      if (rs.next()) rows = rs.getInt(1);
      rs.close();
      pstmt.close();

      open_ = new float[rows+2];
      close_ = new float[rows+2];
      max_ = new float[rows+2];
      min_ = new float[rows+2];
      trans_ = new int[rows+2];
      trans_date = new String[rows+2];

      pstmt = conn.prepareStatement(sqlstr);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        float ooo = rs.getFloat(1);
        float hhh = rs.getFloat(2);
        float lll = rs.getFloat(3);
        float ccc = rs.getFloat(4);
        int uuu = rs.getInt(5);
        String ddd = rs.getString(6);
        num = num + 1;
        //if ((ooo + hhh + lll + ccc) < 0.5 ) {
        //  dif = dif + 1;
        //} else {
          number = num -dif;
          open_[number] = ooo;
          close_[number] = ccc;
          max_[number] = hhh;
          min_[number] = lll;
          trans_[number] = uuu;
          trans_date[number] = ddd;
        //}

        max_p = max_[1];
        min_p = min_[1];
        max_a = trans_[1];

        int abcd = 0;
        if (number > count - 29)
          abcd =count - 29;
        else
          abcd = number;

        ma5 = new float[abcd];
        ma10 = new float[abcd];
        ma30 = new float[abcd];
        boolean ma5flag = false;
        boolean ma10flag = false;
        boolean ma30flag = false;

        for (int i=0; i<abcd; i++) {
          if ((i + 4) <= number) {
            ma5[i] = 0;
            for(int j=5; j>0; j--) {
              ma5[i] = ma5[i] + close_[i-j+5]*j;
            }
            ma5flag = true;
            ma5[i] = ma5[i]/15;
          }

          if ((i + 9) <= number) {
            ma10[i] = 0;
            for(int j=10; j>0; j--) {
              ma10[i] = ma10[i] + close_[i-j+10]*j;
            }
            ma10flag = true;
            ma10[i] = ma10[i]/55;
          }

          if ((i + 29) <= number) {
            ma30[i] = 0;
            for(int j=30; j>0; j--) {
              ma30[i] = ma30[i] + close_[i-j+30]*j;
            }
            ma30flag = true;
            ma30[i] = ma30[i]/465;
          }

          if (max_p < max_[i]) { max_p = max_[i]; }
          if (min_p > min_[i]) { min_p = min_[i]; }
          if (max_a < trans_[i]) { max_a = trans_[i]; }
        }

        max_price = (float)(max_p + (max_p - min_p)*0.1);
        min_price = (float)(min_p - (max_p - min_p)*0.1);

        for(int i=0; i<9; i++) {
          axis_value[i] = min_price + i*(max_price-min_price)/8;
        }

        max_amount = (int)(max_a*0.011+1)*100;
        for(int i=0; i<5; i++) {
          axis_amount[i] = i*max_amount/5;
        }
      }
      rs.close();
      pstmt.close();
    } catch (SQLException sqlexp) {
      sqlexp.printStackTrace();
    }

    //画股票价格Y轴
    for(int i=0; i<9; i++) {
      int temp = v1 - i*hang1+4;
      String q = axis_value[i] + "";
      if (q.length() >= 4)
        q = q.substring(0,q.indexOf(".")+3);
      else
        q = q + "0";
      graphics.drawString(q,left-5-q.length()*6,temp);
    }

    //画股票交易量的Y轴
    for(int i=0; i<5; i++) {
      String q = axis_amount[i] + "";
      graphics.drawString(q,left-10-q.length()*6,v3-i*hang2+4);
    }

    try {
      int abcd = 0;
      pstmt = conn.prepareStatement(sqlstr);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        //画K线图
        int old_x_ma5 = right - (ww*2+2);
        int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

        int old_x_ma10 = right - (ww*2+2);
        int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

        int old_x_ma30 = right - (ww*2+2);
        int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

        if (number > count - 29)
          abcd = count - 29;
        else
          abcd = number;

        for (int i=0; i<abcd; i++) {
          int temp = i*(ww*2 + 2);
          if (open_[i] > close_[i])
            graphics.setColor(Color.green);
          else
            graphics.setColor(Color.red);
          for(int j=-ww; j<=ww; j++) {
            graphics.drawLine(right-temp+j,v3-1,right-temp+j,v3-trans_[i]*(v3-v2)/max_amount);
            graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
          }

          graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

          //画日期轴的其他项
          if (temp % lie ==0 && trans_date[i] != null) {
            graphics.setColor(Color.black);
            graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
          }

          //画日期轴的第一项
          if (i == 1 && trans_date[i] != null) {
            graphics.setColor(Color.black);
            graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
          }

          if(i<=ma5.length) {
            graphics.setColor(Color.red);
            graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma5 = right - temp;
            old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
          }

          if(i<=ma10.length) {
            graphics.setColor(Color.green);
            graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma10 = right - temp;
            old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
          }

          if(i<=ma30.length) {
            graphics.setColor(Color.blue);
            graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma30 = right - temp;
            old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
          }
        }
      }
    } catch (SQLException exp) {

    }

    //createVerticalAxis();
    //createHorizontalAxis();

    //graphics.setColor(Color.green);

    //plotPrices(prices);

    encoder.encode(bi);

    return "image/jpg";
  }
*/

    public String createMonthK_LineImage(OutputStream stream,String stockcode) throws IOException
    {
        int number = 0;
        //大图
        int leng = 582;
        int width = 347;
        int left = 60;
        int v0=25;
        int v1=220;
        int v2=240;
        int v3=330;

        int ww=1;
        int pppp=5;
        int hang2 = (v3-v2)/5;
        int hang1 = (v1-v0)/8;

        v0=v1-hang1*8;
        v2=v3-hang2*5;
        int lie = (leng-left-15)/8;
        while(lie % 4 != 0) lie = lie-1;
        int right = lie*8 + left;
        int right1 = lie*8 + left + pppp;
        int count = lie*8/(ww*2 + 4);

        float[] open_ = null;
        float[] close_ = null;
        float[] max_ = null;
        float[] min_ = null;
        float[] trans_ = null;
        String[] trans_date = null;

        float[] ma5 = null;
        float[] ma10 = null;
        float[] ma30 = null;

        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

        BufferedImage bi = new BufferedImage(ImageWidth,
                ImageHeight,
                BufferedImage.TYPE_BYTE_INDEXED);

        graphics = bi.createGraphics();

        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
        graphics.setColor(Color.black);
        graphics.drawRect(left,v0,right1-60,v1-v0);
        graphics.drawRect(left,v2,right1-60,v3-v2);

        //画曲线图中的格子线
        for(int i=1;i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left,v0 + i*hang1,left+5,v0+i*hang1);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left,v0 + i*hang1,right1-5,v0+i*hang1);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v0 + i*hang1,right1,v0+i*hang1);
        }

        //画直方图中的格子线
        for(int i=1;i<5;i++) {
            graphics.drawLine(left,v2 + i*hang2,left+5,v2+i*hang2);
            graphics.setColor(Color.lightGray);
            graphics.drawLine(left+5,v2 + i*hang2,right1-5,v2+i*hang2);
            graphics.setColor(Color.black);
            graphics.drawLine(right1-5,v2 + i*hang2,right1,v2+i*hang2);
        }

        //画竖线，偶数线是实线，奇数线是虚线
        for(int i=1; i<8;i++) {
            graphics.setColor(Color.black);
            graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
            graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
            graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
            graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
            if(i % 2 == 0) {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
                graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
            } else {
                graphics.setColor(Color.lightGray);
                graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
                graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
            }
        }

        //画5日均线，10日均线，30日均线的图例
        graphics.setColor(Color.black);
        graphics.drawString("MA5:",left+20,v0-8);
        graphics.drawString("MA10:",left+100,v0-8);
        graphics.drawString("MA30:",left+180,v0-8);

        for(int j=-(ww+1); j<=ww+1; j++) {
            graphics.setColor(Color.red);
            graphics.drawLine(left+60,v0-12+j,left+70,v0-12+j);
            graphics.setColor(Color.green);
            graphics.drawLine(left+140,v0-12+j,left+150,v0-12+j);
            graphics.setColor(Color.blue);
            graphics.drawLine(left+220,v0-12+j,left+230,v0-12+j);
        }

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        int counter = 0;
        String[][] months60 = new String[2][91];

        while (counter < 80) {
            cal.add(GregorianCalendar.MONTH,-1);
            String datestr = dateFormat.format(cal.getTime());
            int yposi =datestr.indexOf("年");
            int year = Integer.parseInt(datestr.substring(0,yposi));
            int mposi =datestr.indexOf("月");
            int month = Integer.parseInt(datestr.substring(yposi+1,mposi));
            if (month == 2) {
                if (year % 4 == 0) {
                    months60[0][counter] = year+ "-" + month + "-" + 1;
                    months60[1][counter] = year+ "-" + month + "-" + 29;
                } else {
                    months60[0][counter] = year+ "-" + month + "-" + 1;
                    months60[1][counter] = year+ "-" + month + "-" + 28;
                }
            } else if (month ==1 || month ==3 || month ==5 || month ==7 || month ==8 || month ==10 || month ==12) {
                months60[0][counter] = year+ "-" + month + "-" + 1;
                months60[1][counter] = year+ "-" + month + "-" + 31;
            } else {
                months60[0][counter] = year+ "-" + month + "-" + 1;
                months60[1][counter] = year+ "-" + month + "-" + 30;
            }
            counter++;
        }

        String datewhere = "";
        for(int i=0; i<80; i++) {
            datewhere = datewhere + "to_date('" +months60[1][i] + "','yyyy-mm-dd'),";
        }
        datewhere = "(" + datewhere + "to_date('" + months60[1][90] +"','yyyy-mm-dd'))";

        int rows = 0;
        float max_p = 0;
        float min_p = 0;
        float max_a = 0;
        float max_price = 0;
        float min_price = 0;
        float[] axis_value = new float[9];
        float[] axis_amount = new float[5];
        float max_amount = 0;

        com.bizwink.cms.server.FileProps props = new com.bizwink.cms.server.FileProps("com/bizwink/cms/server/config.properties");
        String driver =props.getProperty("main.db.driver");
        String dburl =props.getProperty("main.db.url");
        String userid =props.getProperty("main.db.username");
        String passwd =props.getProperty("main.db.password");
        Connection conn = getConnection(driver,dburl,userid,passwd);
        PreparedStatement pstmt;
        ResultSet rs = null;
        /*try {
          Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
          conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:2433", "stockmaster", "1qaz2wsx");
        } catch (Exception exp) {
          exp.printStackTrace();
        }*/

        String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE thedate in " +
                datewhere + " and stockcode=? ORDER BY THEDATE DESC";

        System.out.println("m_sqlstr=" + sqlstr);

        String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE thedate in " +datewhere + " and stockcode=?";
        try {
            pstmt = conn.prepareStatement(sql_to_count);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            if (rs.next()) rows = rs.getInt(1);
            rs.close();
            pstmt.close();

            open_ = new float[rows+2];
            close_ = new float[rows+2];
            max_ = new float[rows+2];
            min_ = new float[rows+2];
            trans_ = new float[rows+2];
            trans_date = new String[rows+2];

            pstmt = conn.prepareStatement(sqlstr);
            pstmt.setString(1,stockcode);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                float ooo = rs.getFloat(1);
                float hhh = rs.getFloat(2);
                float lll = rs.getFloat(3);
                float ccc = rs.getFloat(4);
                float uuu = rs.getFloat(5);
                String ddd = rs.getString(6);

                open_[number] = ooo;
                close_[number] = ccc;
                max_[number] = hhh;
                min_[number] = lll;
                trans_[number] = uuu;
                trans_date[number] = ddd;

                if (number == 0) {
                    max_p = max_[0];
                    min_p = min_[0];
                    max_a = trans_[0];
                }

                int abcd = 0;
                if (number > count)
                    abcd =count;
                else
                    abcd = number;

                System.out.println("number=" + number);

                ma5 = new float[abcd];
                ma10 = new float[abcd];
                ma30 = new float[abcd];
                boolean ma5flag = false;
                boolean ma10flag = false;
                boolean ma30flag = false;

                for (int i=0; i<abcd; i++) {
                    if ((i + 4) <= number) {
                        ma5[i] = 0;
                        for(int j=5; j>0; j--) {
                            ma5[i] = ma5[i] + close_[i-j+5]*j;
                        }
                        ma5flag = true;
                        ma5[i] = ma5[i]/15;
                    }

                    if ((i + 9) <= number) {
                        ma10[i] = 0;
                        for(int j=10; j>0; j--) {
                            ma10[i] = ma10[i] + close_[i-j+10]*j;
                        }
                        ma10flag = true;
                        ma10[i] = ma10[i]/55;
                    }

                    if ((i + 29) <= number) {
                        ma30[i] = 0;
                        for(int j=30; j>0; j--) {
                            ma30[i] = ma30[i] + close_[i-j+30]*j;
                        }
                        ma30flag = true;
                        ma30[i] = ma30[i]/465;
                    }
                }

                if (max_p < max_[number]) { max_p = max_[number]; }
                if (min_p > min_[number] && min_[number] !=0) { min_p = min_[number]; }
                if (max_a < trans_[number]) { max_a = trans_[number]; }

                max_price = (float)(max_p + (max_p - min_p)*0.1);
                min_price = (float)(min_p - (max_p - min_p)*0.1);

                for(int i=0; i<9; i++) {
                    axis_value[i] = min_price + i*(max_price-min_price)/8;
                }

                max_amount = max_a;
                for(int i=0; i<5; i++) {
                    //每手股票是100股
                    axis_amount[i] = i*max_amount/500;
                }

                if (number > 110)
                    break;
                else
                    number = number + 1;
            }
            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException sqlexp) {
            sqlexp.printStackTrace();
        }

        //画股票价格Y轴
        if (number>0) {
            for(int i=0; i<9; i++) {
                int temp = v1 - i*hang1+4;
                String q = axis_value[i] + "";
                int posi = q.indexOf(".");
                int xiaoshu_length = q.substring(posi + 1).length();
                if (xiaoshu_length >= 2)
                    q = q.substring(0,q.indexOf(".")+3);
                else
                    q = q + "0";
                graphics.drawString(q,left-5-q.length()*6,temp);
            }

            //画股票交易量的Y轴
            DecimalFormat df = new DecimalFormat("0");
            for(int i=0; i<5; i++) {
                String q = df.format(axis_amount[i]/100);
                graphics.drawString(q,left-10-q.length()*6,v3-i*hang2+4);
            }

            if (rows < 80) {
                right = right - (80-rows)*6;
            }

            int abcd = 0;
            //for(int k=0; k<rows; k++) {
            //画K线图
            int old_x_ma5 = right - (ww*2+2);
            int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma10 = right - (ww*2+2);
            int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

            int old_x_ma30 = right - (ww*2+2);
            int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

            if (number > count)
                abcd = count;
            else
                abcd = number;

            for (int i=0; i<abcd; i++) {
                int temp = i*(ww*2 + 4);
                if (open_[i] > close_[i])
                    graphics.setColor(Color.green);
                else
                    graphics.setColor(Color.red);
                for(int j=-ww; j<=ww; j++) {
                    float h1 = trans_[i]*(v3-v2)/max_amount;
                    float y1 = 0;
                    if (h1 < 0) y1 = v3+h1; else y1 = v3-h1;
                    graphics.drawLine(right-temp+j,v3-1,right-temp+j,(int)y1);
                    graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
                }

                graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

                //画日期轴的其他项
                if (temp % lie ==0 && trans_date[i] != null) {
                    graphics.setColor(Color.black);
                    graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                }

                //画日期轴的第一项
                //if (i == 1 && trans_date[i] != null) {
                //  graphics.setColor(Color.black);
                //  graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
                //}

                //System.out.println("ma5=" + i + "==" + ma5[i]);
                //System.out.println("ma10=" + ma10.length);
                //System.out.println("ma30=" + ma30.length);

                if(i<ma5.length) {
                    graphics.setColor(Color.red);
                    graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma5 = right - temp;
                    old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<ma10.length) {
                    graphics.setColor(Color.green);
                    graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma10 = right - temp;
                    old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
                }

                if(i<ma30.length) {
                    graphics.setColor(Color.blue);
                    graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
                    old_x_ma30 = right - temp;
                    old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
                }
            }
            //}

            encoder.encode(bi);
        }

        return "image/jpg";
    }

/*  public String createSmallMonthK_LineImage(OutputStream stream) throws IOException
  {
    int number = 0;
    int leng = 210;
    int width = 137;
    int left = 33;
    int v0=39;
    int v1=93;
    int v2=94;
    int v3=120;

    int ww=1;
    int pppp=5;
    int hang2 = (v3-v2)/5;
    int hang1 = (v1-v0)/8;

    v0=v1-hang1*8;
    v2=v3-hang2*5;
    int lie = (leng-left-15)/8;
    while(lie % 4 != 0) lie = lie-1;
    int right = lie*8 + left;
    int right1 = lie*8 + left + pppp;
    int count = lie*8/(ww*2 + 2)-1 + 29;

    float[] open_ = null;
    float[] close_ = null;
    float[] max_ = null;
    float[] min_ = null;
    int[] trans_ = null;
    String[] trans_date = null;

    float[] ma5 = null;
    float[] ma10 = null;
    float[] ma30 = null;

    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(stream);

    BufferedImage bi = new BufferedImage(ImageWidth,
                                         ImageHeight,
                                         BufferedImage.TYPE_BYTE_INDEXED);

    graphics = bi.createGraphics();

    graphics.setColor(Color.white);
    graphics.fillRect(0, 0, bi.getWidth(), bi.getHeight());
    graphics.setColor(Color.black);
    graphics.drawRect(left,v0,right1-60,v1-v0);
    graphics.drawRect(left,v2,right1-60,v3-v2);

    //画曲线图中的格子线
    for(int i=1;i<8;i++) {
      graphics.setColor(Color.black);
      graphics.drawLine(left,v0 + i*hang1,left+5,v0+i*hang1);
      graphics.setColor(Color.lightGray);
      graphics.drawLine(left,v0 + i*hang1,right1-5,v0+i*hang1);
      graphics.setColor(Color.black);
      graphics.drawLine(right1-5,v0 + i*hang1,right1,v0+i*hang1);
    }

    //画直方图中的格子线
    for(int i=1;i<5;i++) {
      graphics.drawLine(left,v2 + i*hang2,left+5,v2+i*hang2);
      graphics.setColor(Color.lightGray);
      graphics.drawLine(left+5,v2 + i*hang2,right1-5,v2+i*hang2);
      graphics.setColor(Color.black);
      graphics.drawLine(right1-5,v2 + i*hang2,right1,v2+i*hang2);
    }

    //画竖线，偶数线是实线，奇数线是虚线
    for(int i=1; i<8;i++) {
      graphics.setColor(Color.black);
      graphics.drawLine(left+lie*i,v0,left+lie*i,v0+5);
      graphics.drawLine(left+lie*i,v1-5,left+lie*i,v1);
      graphics.drawLine(left+lie*i,v2,left+lie*i,v2+5);
      graphics.drawLine(left+lie*i,v3-5,left+lie*i,v3);
      if(i % 2 == 0) {
        graphics.setColor(Color.lightGray);
        graphics.drawLine(left+lie*i,v0+5,left+lie*i,v1-5);
        graphics.drawLine(left+lie*i,v2+5,left+lie*i,v3-5);
      } else {
        graphics.setColor(Color.lightGray);
        graphics.drawLine(left+lie*i,v1-5,left+lie*i,v0+5);
        graphics.drawLine(left+lie*i,v3-5,left+lie*i,v2+5);
      }
    }

    //画5日均线，10日均线，30日均线的图例
    graphics.setColor(Color.black);
    graphics.drawString("MA5:",left+20,v0-8);
    graphics.drawString("MA10:",left+100,v0-8);
    graphics.drawString("MA30:",left+180,v0-8);

    for(int j=-(ww+1); j<=ww+1; j++) {
      graphics.setColor(Color.red);
      graphics.drawLine(left+60,v0-12+j,left+70,v0-12+j);
      graphics.setColor(Color.green);
      graphics.drawLine(left+140,v0-12+j,left+150,v0-12+j);
      graphics.setColor(Color.blue);
      graphics.drawLine(left+220,v0-12+j,left+230,v0-12+j);
    }

    int rows = 0;
    float max_p = 0;
    float min_p = 0;
    int max_a = 0;
    float max_price = 0;
    float min_price = 0;
    float[] axis_value = new float[9];
    int[] axis_amount = new int[5];
    int max_amount = 0;

    com.bizwink.server.FileProps props = new com.bizwink.server.FileProps("com/bizwink/cms/server/config.properties");
    String driver =props.getProperty("main.db.driver");
    String dburl =props.getProperty("main.db.url");
    String userid =props.getProperty("main.db.username");
    String passwd =props.getProperty("main.db.password");
    Connection conn = getConnection(driver,dburl,userid,passwd);
    PreparedStatement pstmt;
    ResultSet rs = null;
    try {
      Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
      conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://127.0.0.1:2433", "stockmaster", "1qaz2wsx");
    } catch (Exception exp) {
      exp.printStackTrace();
    }

    String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE GID=0 ORDER BY THEDATE DESC";
    String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE GID=0";
    try {
      int num = 0;
      int dif = 0;
      pstmt = conn.prepareStatement(sql_to_count);
      rs = pstmt.executeQuery();
      if (rs.next()) rows = rs.getInt(1);
      rs.close();
      pstmt.close();

      open_ = new float[rows+2];
      close_ = new float[rows+2];
      max_ = new float[rows+2];
      min_ = new float[rows+2];
      trans_ = new int[rows+2];
      trans_date = new String[rows+2];

      pstmt = conn.prepareStatement(sqlstr);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        float ooo = rs.getFloat(1);
        float hhh = rs.getFloat(2);
        float lll = rs.getFloat(3);
        float ccc = rs.getFloat(4);
        int uuu = rs.getInt(5);
        String ddd = rs.getString(6);
        num = num + 1;
        //if ((ooo + hhh + lll + ccc) < 0.5 ) {
        //  dif = dif + 1;
        //} else {
          number = num -dif;
          open_[number] = ooo;
          close_[number] = ccc;
          max_[number] = hhh;
          min_[number] = lll;
          trans_[number] = uuu;
          trans_date[number] = ddd;
        //}

        max_p = max_[1];
        min_p = min_[1];
        max_a = trans_[1];

        int abcd = 0;
        if (number > count - 29)
          abcd =count - 29;
        else
          abcd = number;

        ma5 = new float[abcd];
        ma10 = new float[abcd];
        ma30 = new float[abcd];
        boolean ma5flag = false;
        boolean ma10flag = false;
        boolean ma30flag = false;

        for (int i=0; i<abcd; i++) {
          if ((i + 4) <= number) {
            ma5[i] = 0;
            for(int j=5; j>0; j--) {
              ma5[i] = ma5[i] + close_[i-j+5]*j;
            }
            ma5flag = true;
            ma5[i] = ma5[i]/15;
          }

          if ((i + 9) <= number) {
            ma10[i] = 0;
            for(int j=10; j>0; j--) {
              ma10[i] = ma10[i] + close_[i-j+10]*j;
            }
            ma10flag = true;
            ma10[i] = ma10[i]/55;
          }

          if ((i + 29) <= number) {
            ma30[i] = 0;
            for(int j=30; j>0; j--) {
              ma30[i] = ma30[i] + close_[i-j+30]*j;
            }
            ma30flag = true;
            ma30[i] = ma30[i]/465;
          }

          if (max_p < max_[i]) { max_p = max_[i]; }
          if (min_p > min_[i]) { min_p = min_[i]; }
          if (max_a < trans_[i]) { max_a = trans_[i]; }
        }

        max_price = (float)(max_p + (max_p - min_p)*0.1);
        min_price = (float)(min_p - (max_p - min_p)*0.1);

        for(int i=0; i<9; i++) {
          axis_value[i] = min_price + i*(max_price-min_price)/8;
        }

        max_amount = (int)(max_a*0.011+1)*100;
        for(int i=0; i<5; i++) {
          axis_amount[i] = i*max_amount/5;
        }
      }
      rs.close();
      pstmt.close();
    } catch (SQLException sqlexp) {
      sqlexp.printStackTrace();
    }

    //画股票价格Y轴
    for(int i=0; i<9; i++) {
      int temp = v1 - i*hang1+4;
      String q = axis_value[i] + "";
      if (q.length() >= 4)
        q = q.substring(0,q.indexOf(".")+3);
      else
        q = q + "0";
      graphics.drawString(q,left-5-q.length()*6,temp);
    }

    //画股票交易量的Y轴
    for(int i=0; i<5; i++) {
      String q = axis_amount[i] + "";
      graphics.drawString(q,left-10-q.length()*6,v3-i*hang2+4);
    }

    try {
      int abcd = 0;
      pstmt = conn.prepareStatement(sqlstr);
      rs = pstmt.executeQuery();
      while(rs.next()) {
        //画K线图
        int old_x_ma5 = right - (ww*2+2);
        int old_y_ma5 = (int)(v1 - (ma5[1] - min_price)*(v1 - v0)/(max_price - min_price));

        int old_x_ma10 = right - (ww*2+2);
        int old_y_ma10 = (int)(v1 - (ma10[1] - min_price)*(v1 - v0)/(max_price - min_price));

        int old_x_ma30 = right - (ww*2+2);
        int old_y_ma30 = (int)(v1 - (ma30[1] - min_price)*(v1 - v0)/(max_price - min_price));

        if (number > count - 29)
          abcd = count - 29;
        else
          abcd = number;

        for (int i=0; i<abcd; i++) {
          int temp = i*(ww*2 + 2);
          if (open_[i] > close_[i])
            graphics.setColor(Color.green);
          else
            graphics.setColor(Color.red);
          for(int j=-ww; j<=ww; j++) {
            graphics.drawLine(right-temp+j,v3-1,right-temp+j,v3-trans_[i]*(v3-v2)/max_amount);
            graphics.drawLine(right-temp+j,(int)(v1-(open_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp+j,(int)(v1-(close_[i]-min_price)*(v1-v0)/(max_price-min_price)));
          }

          graphics.drawLine(right-temp,(int)(v1-(max_[i]-min_price)*(v1-v0)/(max_price-min_price)),right-temp,(int)(v1-(min_[i]-min_price)*(v1-v0)/(max_price-min_price)));

          //画日期轴的其他项
          if (temp % lie ==0 && trans_date[i] != null) {
            graphics.setColor(Color.black);
            graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
          }

          //画日期轴的第一项
          if (i == 1 && trans_date[i] != null) {
            graphics.setColor(Color.black);
            graphics.drawString(trans_date[i].substring(2,trans_date[i].indexOf(" ")),right-temp-20,v1+15);
          }

          if(i<=ma5.length) {
            graphics.setColor(Color.red);
            graphics.drawLine(old_x_ma5,old_y_ma5,right-temp,(int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma5 = right - temp;
            old_y_ma5 = (int)(v1-(ma5[i]-min_price)*(v1-v0)/(max_price-min_price));
          }

          if(i<=ma10.length) {
            graphics.setColor(Color.green);
            graphics.drawLine(old_x_ma10,old_y_ma10,right-temp,(int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma10 = right - temp;
            old_y_ma10 = (int)(v1-(ma10[i]-min_price)*(v1-v0)/(max_price-min_price));
          }

          if(i<=ma30.length) {
            graphics.setColor(Color.blue);
            graphics.drawLine(old_x_ma30,old_y_ma30,right-temp,(int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price)));
            old_x_ma30 = right - temp;
            old_y_ma30 = (int)(v1-(ma30[i]-min_price)*(v1-v0)/(max_price-min_price));
          }
        }
      }
    } catch (SQLException exp) {

    }

    encoder.encode(bi);

    return "image/jpg";
  }
*/

    /**
     *  Test Entrypoint
     *
     */
    public static void main(String[] args)
    {
        try
        {
            FileOutputStream day_k_line = new FileOutputStream("d_stockgraph.jpg");
            //FileOutputStream smallday_k_line = new FileOutputStream("small_d_stockgraph.jpg");
            FileOutputStream week_k_line = new FileOutputStream("w_stockgraph.jpg");
            FileOutputStream month_k_line = new FileOutputStream("m_stockgraph.jpg");
            StockGraphProducer producer = new StockGraphProducer();
            producer.createDayK_LineImage(day_k_line,"600028.SS");
            //producer.createSmallDayK_LineImage(smallday_k_line,"600028.SS");
            producer.createWeekK_LineImage(week_k_line,"600028.SS");
            producer.createMonthK_LineImage(month_k_line,"600028.SS");
            day_k_line.close();
            //smallday_k_line.close();
            week_k_line.close();
            month_k_line.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
