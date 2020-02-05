<%@ page contentType="image/jpeg" import="javax.imageio.*" %>
<%@ page import="java.util.Random" %>
<%@ page import="java.awt.*" %>
<%@ page import="com.charts.*" %>
<%@ page import="java.awt.image.*" %>
<%
    out.clear();
    //设置页面不缓存
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);

// 在内存中创建图象
    int leng = 200;
    int length = 250;
    int width = 338;
    int hang_h = 20;      //行高
    int yw = 50;          //Y轴的坐标空间宽度
    int line_w = 1;       //线的宽度

    BufferedImage image = new BufferedImage(width, length, BufferedImage.TYPE_INT_RGB);

// 获取图形上下文
    Graphics graphics = image.getGraphics();
    graphics.setColor(Color.white);
    graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
    graphics.setColor(Color.black);
    graphics.drawRect(yw,0,width-yw-line_w,leng-line_w);

    //画中线
    float y_value = 0.00f;
    graphics.drawLine(yw,leng/2,width,leng/2);
    graphics.drawString("0.00", yw-30,leng/2+5);

    graphics.setColor(Color.blue);
    //从中线向上画4根分割线
    for(int i=4;i>0;i--) {
        y_value = (5-i)*0.02f;
        if (i == 3) {
            graphics.setColor(Color.red);
            graphics.drawString(String.valueOf(y_value), yw-30,i*hang_h+5);
            for(int j=0;j<150;j++) {
                if (j % 2 == 0) graphics.drawLine(yw+j*2,i*hang_h,yw+(j+1)*2,i*hang_h);
            }
            //graphics.drawLine(yw,i*hang_h,width,i*hang_h);
        } else {
            graphics.setColor(Color.blue);
            graphics.drawString(String.valueOf(y_value), yw-30,i*hang_h+5);
            graphics.setColor(Color.gray);
            for(int j=0;j<150;j++) {
                if (j % 2 == 0) graphics.drawLine(yw+j*2,i*hang_h,yw+(j+1)*2,i*hang_h);
            }
            //graphics.drawLine(yw,i*hang_h,width,i*hang_h);
        }
    }

    //从中线向下画4根分割线
    y_value = 0.00f;
    for(int i=0;i<4;i++) {
        y_value = (i+1)*(-0.02f);
        if (i==1) {
            graphics.setColor(Color.red);
            graphics.drawString(String.valueOf(y_value), yw-30,(i+6)*hang_h+5);
            for(int j=0;j<150;j++) {
                if (j % 2 == 0) graphics.drawLine(yw+j*2,(i+6)*hang_h,yw+(j+1)*2,(i+6)*hang_h);
            }
            //graphics.drawLine(yw,(i+6)*hang_h,width,(i+6)*hang_h);
        } else {
            graphics.setColor(Color.blue);
            graphics.drawString(String.valueOf(y_value), yw-30,(i+6)*hang_h+5);
            graphics.setColor(Color.gray);
            for(int j=0;j<150;j++) {
                if (j % 2 == 0) graphics.drawLine(yw+j*2,(i+6)*hang_h,yw+(j+1)*2,(i+6)*hang_h);
            }
            //graphics.drawLine(yw,(i+6)*hang_h,width,(i+6)*hang_h);
        }
    }

    //获取绘图的数据
    IChartManager chartMgr = ChartPeer.getInstance();
    java.util.List blt = chartMgr.getCrudeForBlt();
    java.util.List wti = chartMgr.getCrudeForWTI();

    String[] x_coordinate = new String[60];
    int[] x_values = new int[60];
    int[] y_values = new int[60];
    String thedate = null;
    for (int i=0; i<59; i++) {
        crude data_for_wti = (crude)wti.get(i);
        crude data_for_blt = (crude)blt.get(i);
        thedate = data_for_wti.getPeriod().toString();
        int posi = thedate.indexOf(" ");
        thedate = thedate.substring(0,posi);
        posi = thedate.indexOf("-");
        thedate = thedate.substring(posi+1);
        x_coordinate[i] = thedate;
        float price = ((data_for_wti.getPrice() + data_for_blt.getPrice())/2.00f-79.00f)/79.00f;
        if (price > 0)
            y_values[i] = (int)((price/0.02f)*20);
        else
            y_values[i] = 100 + (int)(((-price)/0.02f)*20);
        x_values[i] = yw + i*(width-yw)/59;
        //System.out.println("price=" + y_values[i]);
    }

    int lie_w = 20;
    //画竖格线
    for(int i=0;i<21;i++) {
        graphics.setColor(Color.black);
        if (i % 2 == 0) graphics.drawString(x_coordinate[i*2],(21-i)*lie_w+yw+3,leng+20);
        graphics.setColor(Color.gray);
        for(int j=0;j<100;j++) {
            if (j % 2 == 0 && i != 0) graphics.drawLine(i*lie_w+yw,j*2,i*lie_w+yw,(j+1)*2);
        }
        //graphics.drawLine(i*lie_w+yw,0,i*lie_w+yw,leng-1);
    }

    //画曲线图
    graphics.setColor(Color.black);
    graphics.drawPolyline(x_values,y_values,59);

    //画出每个竖线对应的X坐标
    //Graphics2D   g2   =   (Graphics2D)graphics;
    //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    //int i =0;
    //for(int i=0;i<21;i++) {
    //g2.rotate(90,i*lie_w+yw,leng);
    //g2.drawString(y_coordinate[i*2],i*lie_w+yw,leng+10);
    //}

    //i =1;
    //g2.rotate(90,i*lie_w+yw,leng);
    //g2.drawString(y_coordinate[i*2],i*lie_w+yw,leng+10);

    //画曲线图中的格子线
    /*for(int i=1;i<8;i++) {
        graphics.setColor(Color.black);
        graphics.drawLine(left,v0 + i*hang_h,left+5,v0+i*hang_h);
        graphics.setColor(Color.lightGray);
        graphics.drawLine(left+5,v0 + i*hang_h,right1-5,v0+i*hang_h);
        graphics.setColor(Color.black);
        graphics.drawLine(right1-5,v0 + i*hang_h,right1,v0+i*hang_h);
    }*/

//生成随机类
    //Random random = new Random();

// 设定背景色
    //graphics.setColor(getRandColor(200, 250));
    //graphics.fillRect(0, 0, width, leng);

//设定字体
    //graphics.setFont(new Font("Times New Roman", Font.PLAIN, 18));

//画边框
//g.setColor(new Color());
//g.drawRect(0,0,width-1,height-1);

// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
/*    graphics.setColor(getRandColor(160, 200));
    for (int i = 0; i < 155; i++) {
        int x = random.nextInt(width);
        int y = random.nextInt(leng);
        int xl = random.nextInt(12);
        int yl = random.nextInt(12);
        graphics.drawLine(x, y, x + xl, y + yl);
    }

// 取随机产生的认证码(4位数字)
    String sRand = "";
    for (int i = 0; i < 4; i++) {
        String rand = String.valueOf(random.nextInt(10));
        sRand += rand;
        // 将认证码显示到图象中
        graphics.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));//调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
        graphics.drawString(rand, 13 * i + 6, 16);
    }

// 将认证码存入SESSION
    session.setAttribute("randnum", sRand);
*/
// 图象生效
    graphics.dispose();

// 输出图象到页面
    ImageIO.write(image, "JPEG", response.getOutputStream());
%>
