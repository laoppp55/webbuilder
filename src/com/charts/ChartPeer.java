package com.charts;

import com.bizwink.cms.server.*;
import com.bizwink.cms.util.*;
import com.bizwink.stockinfo.SpiderException;
import com.bizwink.stockinfo.StockInfo;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.jfree.chart.*;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.*;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.experimental.chart.plot.CombinedXYPlot;
import org.jfree.ui.RectangleInsets;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.*;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartPeer implements IChartManager {
    PoolServer cpool;
    private Graphics2D graphics;

    public ChartPeer(PoolServer cpool)
    {
        this.cpool = cpool;
    }

    public static IChartManager getInstance()
    {
        return (IChartManager)CmsServer.getInstance().getFactory().getChartManager();
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
        PreparedStatement pstmt=null;
        ResultSet rs = null;

        String sqlstr = "SELECT PRE_CLOSE_PRICE,MAX_PRICE,MIN_PRICE,CLOSE_PRICE,EXCHANGES,THEDATE FROM STOCK_PRICE WHERE stockcode=? ORDER BY THEDATE DESC";
        String sql_to_count = "SELECT count(*) FROM STOCK_PRICE WHERE stockcode=?";

        try {
            Connection conn = cpool.getConnection();
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


    private JFreeChart createChart(XYDataset dataset,String type) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                //JFreeChart chart = ChartFactory.createXYLineChart(
                "股票价格走势图",                                        //报表标题
                "时间",                                                   //x轴是日期
                "价格",                                                   //y轴是价格
                dataset,
                //PlotOrientation.VERTICAL,
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.WHITE);
        /*----------设置消除字体的锯齿渲染（解决中文问题）--------------*/
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        /*----------设置标题字体--------------------------*/
        TextTitle textTitle = chart.getTitle();
        textTitle.setFont(new Font("黑体", Font.PLAIN, 10));
        chart.getLegend().setItemFont(new Font("宋体",Font.PLAIN,8));
        /*------这句代码解决了底部汉字乱码的问题-----------*/

        XYPlot plot = (XYPlot)chart.getPlot();
        if (type.equals("WTI"))
            plot.getRenderer().setSeriesPaint(0,Color.green);
        else
            plot.getRenderer().setSeriesPaint(0, Color.red);
        plot.setAxisOffset(new RectangleInsets(1.0,1.0,1.0,1.0));
        XYItemRenderer r = plot.getRenderer();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer)r;
        renderer.setBaseShapesVisible(false);

        DateAxis axis = (DateAxis)plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH时MM分"));
        axis.setVerticalTickLabels(true);
        /*------设置X轴坐标上的文字-----------*/
        axis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 8));
        /*------设置X轴的标题文字------------*/
        axis.setLabelFont(new Font("宋体", Font.PLAIN, 8));

        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setNumberFormatOverride(new DecimalFormat("0.00"));
        /*------设置Y轴坐标上的文字-----------*/
        rangeAxis.setTickLabelFont(new Font("sans-serif", Font.PLAIN, 8));
        /*------设置Y轴的标题文字------------*/
        rangeAxis.setLabelFont(new Font("黑体", Font.PLAIN, 8));

        return chart;
    }


    private XYDataset getStockDataset(String stockcode) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TimeSeries series1 = new TimeSeries(stockcode,Second.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        Timestamp thedate = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(System.currentTimeMillis()));

        try
        {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            DataSource connPool = (DataSource) env.lookup("jdbc/stock");
            conn = connPool.getConnection();

            //conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select trade_time,last_trade from tbl_stock where nameid=? and year(thedate)=? and month(thedate)=? and day(thedate)=? order by trade_time asc limit 1,2");
            pstmt.setString(1,stockcode);
            pstmt.setInt(2, cal.get(Calendar.YEAR));
            pstmt.setInt(3,(cal.get(Calendar.MONTH)+1));
            pstmt.setInt(4,cal.get(Calendar.DAY_OF_MONTH));
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                float price = rs.getFloat("last_trade");
                thedate = rs.getTimestamp("trade_time");
                series1.addOrUpdate(new Second(new Date(thedate.getTime())),price);
            }
            dataset.addSeries(series1);
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                    //cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }

        return dataset;
    }

    public String genStockLineImage(HttpSession session, PrintWriter pw,String stockcode,String cname) {
        String filename = null;
        XYDataset dataset = getStockDataset(stockcode);
        JFreeChart chart = createChart(dataset, cname);
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        try {
            filename = ServletUtilities.saveChartAsPNG(chart,338,250,info,session);
            ChartUtilities.writeImageMap(pw,filename,info,false);
        } catch(IOException e) {
            e.printStackTrace();
        }
        pw.flush();

        return filename;
    }

    public List getStockInfo(String stockcode) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        StockInfo stockInfo = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(System.currentTimeMillis()));

        try
        {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            DataSource connPool = (DataSource) env.lookup("jdbc/stock");
            conn = connPool.getConnection();

            //conn = cpool.getConnection();
            //布伦特
            pstmt = conn.prepareStatement("select trade_time,last_trade from tbl_stock where nameid=? and year(thedate)=? and month(thedate)=? and day(thedate)=? order by trade_time asc limit 1,100");
            pstmt.setString(1,stockcode);
            pstmt.setInt(2, cal.get(Calendar.YEAR));
            pstmt.setInt(3,(cal.get(Calendar.MONTH)+1));
            pstmt.setInt(4,cal.get(Calendar.DAY_OF_MONTH));
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                stockInfo =  new StockInfo();
                stockInfo.setTrade_time(rs.getTimestamp("trade_time"));
                stockInfo.setLast_trade(rs.getFloat("last_trade"));
                list.add(stockInfo);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    conn.close();
                    //cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }
        return list;
    }


    /**
     * Creates a sample dataset.  You wouldn't normally hard-code the
     * population of a dataset in this way (it would be better to read the
     * values from a file or a database query), but for a self-contained demo
     * this is the least complicated solution.
     *
     * @return The dataset.
     */
    private XYDataset createWTIDataset() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        TimeSeries series1 = new TimeSeries("WTI",Day.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        String thedate = null;
        Calendar cal = Calendar.getInstance();
        int year = 0;
        int month = 0;
        int day = 0;

        try
        {
            conn = cpool.getConnection();
            //WTI
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='WTI' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                crude data = new crude();
                data.setProduct_name(rs.getString("product_name"));
                float price = rs.getFloat("price_end");
                thedate = rs.getTimestamp("period").toString();
                int posi = thedate.indexOf(" ");
                thedate = thedate.substring(0,posi);
                posi = thedate.indexOf("-");
                year = Integer.parseInt(thedate.substring(0,posi));
                thedate = thedate.substring(posi+1);
                posi = thedate.indexOf("-");
                month = Integer.parseInt(thedate.substring(0,posi));
                day = Integer.parseInt(thedate.substring(posi+1));
                //System.out.println(year + "-" + month + "-" + day);

                series1.add(new Day(day,month,year), price);
            }
            dataset.addSeries(series1);
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }

        return dataset;
    }

    public List getCrudeForBlt() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            conn = cpool.getConnection();
            //布伦特
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='布伦特' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                crude data = new crude();
                data.setProduct_name(rs.getString("product_name"));
                System.out.println(data.getProduct_name());
                data.setPrice(rs.getFloat("price_end"));
                data.setPeriod(rs.getTimestamp("period"));
                list.add(data);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }
        return list;
    }

    public List getCrudeForWTI() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();

        try
        {
            conn = cpool.getConnection();
            //布伦特
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='WTI' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                crude data = new crude();
                data.setProduct_name(rs.getString("product_name"));
                System.out.println(data.getProduct_name());
                data.setPrice(rs.getFloat("price_end"));
                data.setPeriod(rs.getTimestamp("period"));
                list.add(data);
            }
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }
        return list;
    }

    private XYDataset createBOLENTEDataset() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List list = new ArrayList();
        TimeSeries series2 = new TimeSeries("布伦特",Day.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        String thedate = null;
        Calendar cal = Calendar.getInstance();
        int year = 0;
        int month = 0;
        int day = 0;

        try
        {
            conn = cpool.getConnection();
            //布伦特
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='布伦特' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            while(rs.next())
            {
                crude data = new crude();
                data.setProduct_name(rs.getString("product_name"));
                float price = rs.getFloat("price_end");
                thedate = rs.getTimestamp("period").toString();
                int posi = thedate.indexOf(" ");
                thedate = thedate.substring(0,posi);
                posi = thedate.indexOf("-");
                year = Integer.parseInt(thedate.substring(0,posi));
                thedate = thedate.substring(posi+1);
                posi = thedate.indexOf("-");
                month = Integer.parseInt(thedate.substring(0,posi));
                day = Integer.parseInt(thedate.substring(posi+1));
                //System.out.println(year + "-" + month + "-" + day);
                series2.add(new Day(day,month,year), price);
            }
            dataset.addSeries(series2);
            rs.close();
            pstmt.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }
        return dataset;
    }

    private XYDataset createPercentDataset() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TimeSeries series1 = new TimeSeries("价格%",Day.class);
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        String thedate = null;
        Calendar cal = Calendar.getInstance();
        crude[] wti = new crude[60];
        crude[] blt = new crude[60];
        int year = 0;
        int month = 0;
        int day = 0;


        try
        {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='布伦特' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            int i = 0;
            while(rs.next())
            {
                blt[i] = new crude();
                blt[i].setPrice(rs.getFloat("price_end"));
                blt[i].setProduct_name(rs.getString("product_name"));
                blt[i].setPeriod(rs.getTimestamp("period"));
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (SELECT * FROM market_crude where product_name='WTI' order by period desc) A WHERE ROWNUM < 60) WHERE RN >= 0");
            rs = pstmt.executeQuery();
            i = 0;
            while(rs.next())
            {
                wti[i] = new crude();
                wti[i].setPrice(rs.getFloat("price_end"));
                wti[i].setProduct_name(rs.getString("product_name"));
                wti[i].setPeriod(rs.getTimestamp("period"));
                i = i + 1;
            }
            rs.close();
            pstmt.close();

            float price = 0.00f;
            for(i=0; i<60; i++) {
                if (wti[i] != null && blt[i] != null) {
                    price = ((wti[i].getPrice() + blt[i].getPrice())/2.00f-80.00f)/80.00f;

                    thedate = wti[i].getPeriod().toString();
                    int posi = thedate.indexOf(" ");
                    thedate = thedate.substring(0,posi);
                    posi = thedate.indexOf("-");
                    year = Integer.parseInt(thedate.substring(0,posi));
                    thedate = thedate.substring(posi+1);
                    posi = thedate.indexOf("-");
                    month = Integer.parseInt(thedate.substring(0,posi));
                    day = Integer.parseInt(thedate.substring(posi+1));
                    series1.add(new Day(day,month,year), price);
                }
                //System.out.println(year + "-" + month + "-" + day);
            }
            dataset.addSeries(series1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(conn != null)
                try
                {
                    cpool.freeConnection(conn);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
        }

        return dataset;
    }

    public String generateWTI(HttpSession session, PrintWriter pw) {
        String filename = null;
        XYDataset dataset = createWTIDataset();
        JFreeChart chart = createChart(dataset,"WTI");
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        try {
            filename = ServletUtilities.saveChartAsPNG(chart,338,250,info,session);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ChartUtilities.writeImageMap(pw,filename,info,false);
        } catch(IOException e) {
            e.printStackTrace();
        }
        pw.flush();

        return filename;
    }

    public String generateBOLENTE(HttpSession session, PrintWriter pw) {
        String filename = null;
        XYDataset dataset = createBOLENTEDataset();
        JFreeChart chart = createChart(dataset,"BLT");
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        try {
            filename = ServletUtilities.saveChartAsPNG(chart,338,250,info,session);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ChartUtilities.writeImageMap(pw,filename,info,false);
        } catch(IOException e) {
            e.printStackTrace();
        }
        pw.flush();

        return filename;
    }

    public String generatePricePersent(HttpSession session, PrintWriter pw) {
        String filename = null;
        XYDataset dataset = createPercentDataset();
        JFreeChart chart = createChart(dataset,"价格%");
        ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
        try {
            filename = ServletUtilities.saveChartAsPNG(chart,338,250,info,session);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ChartUtilities.writeImageMap(pw,filename,info,false);
        } catch(IOException e) {
            e.printStackTrace();
        }
        pw.flush();

        return filename;
    }

    private StockInfo load(ResultSet rs) throws SpiderException {
        StockInfo stockindb = new StockInfo();
        try {
            stockindb.setName(rs.getString("name"));
            stockindb.setLast_trade(rs.getFloat("last_trade"));
            stockindb.setTrade_time(rs.getTimestamp("trade_time"));
            stockindb.setThechange(rs.getFloat("thechange"));
            stockindb.setPrev_close(rs.getFloat("prev_close"));
            stockindb.setOpen_money(rs.getFloat("open_money"));
            stockindb.setBid(rs.getFloat("bid"));
            stockindb.setAsk(rs.getFloat("ask"));
            stockindb.setTarget_est(rs.getFloat("target_est"));
            stockindb.setDay_range_low(rs.getFloat("day_range_low"));
            stockindb.setDay_range_high(rs.getFloat("day_range_high"));
            stockindb.setWk52_range_low(rs.getFloat("52wk_range_low"));
            stockindb.setWk52_range_high(rs.getFloat("52wk_range_high"));
            stockindb.setVolume(rs.getInt("volume"));
            stockindb.setM3_avg_vol(rs.getInt("3m_avg_vol"));
            stockindb.setMarket_cap(rs.getString("market_cap"));
            stockindb.setP_e(rs.getFloat("p_e"));
            stockindb.setEps(rs.getFloat("eps"));
            stockindb.setDiv_yield(rs.getString("div_yield"));
            stockindb.setThedate(rs.getTimestamp("thedate"));
            stockindb.setNameid(rs.getString("nameid"));
            stockindb.setDanwei(rs.getString("danwei"));
        } catch (Exception e) {
            System.out.println("Load failed");
            e.printStackTrace();
        }
        return stockindb;
    }
}