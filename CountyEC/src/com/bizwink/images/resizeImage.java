package com.bizwink.images;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import magick.*;
import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

public class resizeImage
{
    String fromdir = "";
    String todir = "";
    String[] gg;
    String origin_file;

    static{
        //不能漏掉这个，不然jmagick.jar的路径找不到
        System.setProperty("jmagick.systemclassloader","no");
    }

    /**
     * 压缩图片
     * @param:imgfile 源文件路径
     * @param:targetImageFile  缩略图路径
     */
    public String createThumbnailBy_jmagick(int siteid,String username,String fileDir,String imgfile,String size,String type) throws MagickException
    {
        ImageInfo info = null;
        MagickImage image = null;
        Dimension imageDim = null;
        MagickImage scaled = null;
        String targetImageFile = imgfile.substring(0,imgfile.lastIndexOf(".")) + "_" + type + imgfile.substring(imgfile.lastIndexOf("."));
        double  Radio = 1.0;

        try{
            int xposi = -1;
            if (size != null) {
                xposi = size.indexOf("X");
                if (xposi == -1) xposi = size.indexOf("x");
            }

            double twidth = 0.0f;
            double theight = 0.0f;
            if (xposi > -1) {
                twidth = Double.parseDouble(size.substring(0,xposi));
                theight = Double.parseDouble(size.substring(xposi+1));
            } else {
                if (type.equals("tl")) {
                    twidth = 800.0f;
                    theight = 800.0f;
                } else if (type.equals("l")) {
                    twidth = 600.0f;
                    theight = 600.0f;
                } else if (type.equals("ml")) {
                    twidth = 400.0f;
                    theight = 400.0f;
                } else if (type.equals("m")) {
                    twidth = 300.0f;
                    theight = 300.0f;
                }  else if (type.equals("ms")) {
                    twidth = 200.0f;
                    theight = 200.0f;
                }  else if (type.equals("s")) {
                    twidth = 120.0f;
                    theight = 120.0f;
                }  else if (type.equals("ts")) {
                    twidth = 80.0f;
                    theight = 80.0f;
                }

            }
            info = new ImageInfo(imgfile);
            image = new MagickImage(info);
            imageDim = image.getDimension();
            int width = imageDim.width;
            int height = imageDim.height;

            if (height > theight || width > twidth) {
                if (height > width)
                    Radio = theight/height;
                else
                    Radio = twidth/width;
            }

            scaled = image.scaleImage((int)(width*Radio), (int)(height*Radio));//小图片文件的大小.
            scaled.setFileName(targetImageFile);
            scaled.writeImage(info);
        } catch (Exception exp) {
            exp.printStackTrace();
        }finally{
            if(scaled != null){
                scaled.destroyImages();
                image.destroyImages();
            }
        }

        return targetImageFile;
    }

    public String createThumbnailBy_jmagick(int siteid,String username,String fileDir,String imgfile,String size) throws MagickException
    {
        ImageInfo info = null;
        MagickImage image = null;
        Dimension imageDim = null;
        MagickImage scaled = null;
        String targetImageFile = imgfile.substring(0,imgfile.lastIndexOf(".")) + "_" + size +imgfile.substring(imgfile.lastIndexOf("."));
        double  Radio = 1.0;

        try{
            int xposi = size.indexOf("X");
            if (xposi == -1) xposi = size.indexOf("x");
            double twidth = 0.0f;
            double theight = 0.0f;
            if (xposi != -1) {
                twidth = Double.parseDouble(size.substring(0,xposi));
                theight = Double.parseDouble(size.substring(xposi+1));
            } else {
                twidth = 800;
                theight = 600;
            }
            info = new ImageInfo(imgfile);
            image = new MagickImage(info);
            imageDim = image.getDimension();
            int width = imageDim.width;
            int height = imageDim.height;

            if (height > theight || width > twidth) {
                if (height > width)
                    Radio = theight/height;
                else
                    Radio = twidth/width;
            }

            scaled = image.scaleImage((int)(width*Radio), (int)(height*Radio));   //小图片文件的大小.
            scaled.setFileName(targetImageFile);
            scaled.writeImage(info);
        } catch (Exception exp) {
            exp.printStackTrace();
        }finally{
            if(scaled != null){
                scaled.destroyImages();
                image.destroyImages();
            }
        }

        return targetImageFile;
    }

    /**
     * 水印(图片logo)
     * @param filePath  源文件路径
     * @param toImg     修改图路径
     * @param logoPath  logo图路径
     * @throws magick.MagickException
     */
    public static void initLogoImg(String filePath, String toImg, String logoPath) throws MagickException {
        ImageInfo info = new ImageInfo();
        MagickImage fImage = null;
        MagickImage sImage = null;
        MagickImage fLogo = null;
        MagickImage sLogo = null;
        Dimension imageDim = null;
        Dimension logoDim = null;
        try {
            fImage = new MagickImage(new ImageInfo(filePath));
            imageDim = fImage.getDimension();
            int width = imageDim.width;
            int height = imageDim.height;
            if (width > 660) {
                height = 660 * height / width;
                width = 660;
            }
            sImage = fImage.scaleImage(width, height);

            fLogo = new MagickImage(new ImageInfo(logoPath));
            logoDim = fLogo.getDimension();
            int lw = width / 8;
            int lh = logoDim.height * lw / logoDim.width;
            sLogo = fLogo.scaleImage(lw, lh);

            sImage.compositeImage(CompositeOperator.AtopCompositeOp, sLogo,  width-(lw + lh/10), height-(lh + lh/10));
            sImage.setFileName(toImg);
            sImage.writeImage(info);
        } finally {
            if(sImage != null){
                sImage.destroyImages();
            }
        }
    }

    /**
     * 水印(文字)
     * @param filePath 源文件路径
     * @param toImg    修改图路径
     * @param text     名字(文字内容自己随意)
     * @throws magick.MagickException
     */
    public static void initTextToImg(String filePath, String toImg,  String text) throws MagickException{
        ImageInfo info = new ImageInfo(filePath);
        if (filePath.toUpperCase().endsWith("JPG") || filePath.toUpperCase().endsWith("JPEG")) {
            info.setCompression(CompressionType.JPEGCompression); //压缩类别为JPEG格式
            info.setPreviewType(PreviewType.JPEGPreview); //预览格式为JPEG格式
            info.setQuality(95);
        }
        MagickImage aImage = new MagickImage(info);
        Dimension imageDim = aImage.getDimension();
        int wideth = imageDim.width;
        int height = imageDim.height;
        if (wideth > 660) {
            height = 660 * height / wideth;
            wideth = 660;
        }
        int a = 0;
        int b = 0;
        String[] as = text.split("");
        for (String string : as) {
            if(string.matches("[\u4E00-\u9FA5]")){
                a++;
            }
            if(string.matches("[a-zA-Z0-9]")){
                b++;
            }
        }
        int tl = a*12 + b*6 + 300;
        MagickImage scaled = aImage.scaleImage(wideth, height);
        if(wideth > tl) { //&#038;& height > 5){
            DrawInfo aInfo = new DrawInfo(info);
            aInfo.setFill(PixelPacket.queryColorDatabase("white"));
            aInfo.setUnderColor(new PixelPacket(0,0,0,100));
            aInfo.setPointsize(12);
            //解决中文乱码问题,自己可以去随意定义个自己喜欢字体，我在这用的微软雅黑
            String fontPath = "C:/WINDOWS/Fonts/MSYH.TTF";
//              String fontPath = "/usr/maindata/MSYH.TTF";
            aInfo.setFont(fontPath);
            aInfo.setTextAntialias(true);
            aInfo.setOpacity(0);
            aInfo.setText("　" + text + "于　" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "　上传于XXXX网，版权归作者所有！");
            aInfo.setGeometry("+" + (wideth-tl) + "+" + (height-5));
            scaled.annotateImage(aInfo);
        }
        scaled.setFileName(toImg);
        scaled.writeImage(info);
        scaled.destroyImages();
    }

    /**
     * 切图
     * @param imgPath 源图路径
     * @param toPath  修改图路径
     * @param w
     * @param h
     * @param x
     * @param y
     * @throws magick.MagickException
     */
    public static void cutImg(String imgPath, String toPath, int w, int h, int x, int y) throws MagickException {
        ImageInfo infoS = null;
        MagickImage image = null;
        MagickImage cropped = null;
        Rectangle rect = null;
        try {
            infoS = new ImageInfo(imgPath);
            image = new MagickImage(infoS);
            rect = new Rectangle(x, y, w, h);
            cropped = image.cropImage(rect);
            cropped.setFileName(toPath);
            cropped.writeImage(infoS);

        } finally {
            if (cropped != null) {
                cropped.destroyImages();
            }
        }
    }


    public resizeImage(String fromdir, String todir)
    {
        this.fromdir = fromdir;
        this.todir  = todir;
    }

    public resizeImage(String sfilename, String[] pics)
    {
        this.gg = pics;
        this.origin_file = sfilename;
    }

    public resizeImage()
    {
    }

    public String createThumbnail(String imgfile) throws Exception
    {
        String ext = "";
        double Radio = 0.0;
        String targetImageFile = "";

        File F = new File(fromdir,imgfile);
        if(!F.isFile())
            throw new Exception(F + " is not image file error in CreateThumbnail!");

        File thF = new File(todir,targetImageFile);
        BufferedImage Bi = ImageIO.read(F);
        Image Itemp = Bi.getScaledInstance(120,120,Bi.SCALE_SMOOTH);

        if (isJpg(imgfile)) {
            ext = "jpg";
        } else {
            ext = "png";
        }

        if (Bi.getHeight() > 120 || Bi.getWidth() > 120) {
            if (Bi.getHeight() > Bi.getWidth())
                Radio = 120.0/Bi.getHeight();
            else
                Radio = 120.0/Bi.getWidth();
        }

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Radio,Radio),null);
        Itemp = op.filter(Bi, null);
        try {
            ImageIO.write((BufferedImage)Itemp,ext,thF);
        } catch (Exception ex) {
            throw new Exception(" ImageIO.write error in CreateThumbnail" + ex.getMessage());
        }

        return ("image/png");
    }

    //type==1  小图片
    //type==2  中图片
    //type==3  大图片
    //按图片类型命名
    public String createThumbnail(String imgfile,String size,String type) throws IOException
    {
        String ext = "";
        double Radio = 0.0;
        String targetImageFile = "";

        File F = new File(imgfile);
        if(!F.isFile())
            throw new IOException(F + " is not image file error in CreateThumbnail!");

        targetImageFile = imgfile.substring(0,imgfile.lastIndexOf(".")) + "_" + type +imgfile.substring(imgfile.lastIndexOf("."));
        File thF = new File(targetImageFile);

        int xposi = size.indexOf("X");
        double twidth = Double.parseDouble(size.substring(0,xposi));
        double theight = Double.parseDouble(size.substring(xposi+1));
        BufferedImage Bi = ImageIO.read(F);
        Image Itemp = Bi.getScaledInstance((int)twidth,(int)theight,Bi.SCALE_SMOOTH);

        if (isJpg(imgfile)) {
            ext = "jpg";
        } else {
            ext = "png";
        }

        if (Bi.getHeight() > theight || Bi.getWidth() > twidth) {
            if (Bi.getHeight() > Bi.getWidth())
                Radio = theight/Bi.getHeight();
            else
                Radio = twidth/Bi.getWidth();
        }

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Radio,Radio),null);
        Itemp = op.filter(Bi, null);

        try {
            ImageIO.write((BufferedImage)Itemp,ext,thF);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return targetImageFile;
    }

    //按图片大小命名
    public String createThumbnail(String imgfile,String size) throws IOException
    {
        String ext = "";
        double Radio = 0.0;
        String targetImageFile = "";

        File F = new File(imgfile);
        if(!F.isFile())
            throw new IOException(F + " is not image file error in CreateThumbnail!");

        targetImageFile = imgfile.substring(0,imgfile.lastIndexOf(".")) + "_" + size +imgfile.substring(imgfile.lastIndexOf("."));
        File thF = new File(targetImageFile);

        int xposi = size.indexOf("X");
        double twidth = Double.parseDouble(size.substring(0,xposi));
        double theight = Double.parseDouble(size.substring(xposi+1));
        BufferedImage Bi = ImageIO.read(F);
        Image Itemp = Bi.getScaledInstance((int)twidth,(int)theight,Bi.SCALE_SMOOTH);

        if (isJpg(imgfile)) {
            ext = "jpg";
        } else {
            ext = "png";
        }

        if (Bi.getHeight() > theight || Bi.getWidth() > twidth) {
            if (Bi.getHeight() > Bi.getWidth())
                Radio = theight/Bi.getHeight();
            else
                Radio = twidth/Bi.getWidth();
        }

        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(Radio,Radio),null);
        Itemp = op.filter(Bi, null);

        try {
            ImageIO.write((BufferedImage)Itemp,ext,thF);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return targetImageFile;
    }

    public boolean isJpg(String filename) {
        boolean retcode=false;

        if ((filename.toLowerCase().lastIndexOf(".jpg") > -1) || (filename.toLowerCase().lastIndexOf(".jpeg") > -1) || (filename.toLowerCase().lastIndexOf(".png") > -1)  || (filename.toLowerCase().lastIndexOf(".gif") > -1))
            retcode = true;

        return retcode;
    }

    /**
     * 是否使用 GraphicsMagick
     */
    private static final boolean IS_USE_GRAPHICS_MAGICK = true;
    /**
     * ImageMagick安装路径，windows下使用
     */
    private static final String IMAGE_MAGICK_PATH = "C:\\Program Files (x86)\\ImageMagick-6.3.9-Q16";

    /**
     *  gm 命令所在目录
     */
    private static final String GRAPHICS_MAGICK_PATH = "/usr/bin";

    /**
     * 命令类型
     *
     */
    private enum CommandType {
        convert("转换处理"), identify("图片信息"), compositecmd("图片合成");
        private String name;

        CommandType(String name) {
            this.name = name;
        }
    }

    /**
     * 获取 ImageCommand
     *
     * @param command 命令类型
     * @return
     */
    private static ImageCommand getImageCommand(CommandType command) {
        ImageCommand cmd = null;
        switch (command) {
            case convert:
                cmd = new ConvertCmd(IS_USE_GRAPHICS_MAGICK);
                break;
            case identify:
                cmd = new IdentifyCmd(IS_USE_GRAPHICS_MAGICK);
                break;
            case compositecmd:
                cmd = new CompositeCmd(IS_USE_GRAPHICS_MAGICK);
                break;
        }
        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
            cmd.setSearchPath(IS_USE_GRAPHICS_MAGICK ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }

    /**
     * 根据坐标裁剪图片
     *
     * @param srcPath   要裁剪图片的路径
     * @param newPath   裁剪图片后的路径
     * @param x   起始横坐标
     * @param y   起始纵坐标
     * @param x1  结束横坐标
     * @param y1  结束纵坐标
     */
    public int cutImage(String os,String srcPath, String newPath, int x, int y, int x1,int y1)  throws Exception {
        int errcode = 0;
        int width = x1 - x;
        int height = y1 - y;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        /**  width：裁剪的宽度    * height：裁剪的高度 * x：裁剪的横坐标 * y：裁剪纵坐标  */
        op.crop(width, height, x, y);
        op.addImage(newPath);
        ConvertCmd convert = new ConvertCmd();
        convert.setSearchPath(GRAPHICS_MAGICK_PATH);
        //ImageCommand convert = getImageCommand(CommandType.convert);
        try {
            convert.run(op);
            op.closeOperation();
        } catch (Exception exp) {
            errcode = -1;
            exp.printStackTrace();
        }

        return errcode;
    }

    /**
     * 根据尺寸缩放图片
     * @param width  缩放后的图片宽度
     * @param height  缩放后的图片高度
     * @param srcPath   源图片路径
     * @param newPath   缩放后图片的路径
     */
    public int zoomImage(Integer width, Integer height, String srcPath, String newPath) throws Exception {
        int errcode = 0;
        IMOperation op = new IMOperation();
        op.addImage(srcPath);
        if(width != null && height!=null){       //高度和宽度都不为空，根据高度和宽度缩放图片
            op.resize(width, height);
        }else if(height == null && width!=null){                //高度为空，根据宽度缩放图片
            op.resize(width, null);
        }else if(height != null && width==null){                                  //宽度为空，根据高度缩放图片
            op.resize(null, height);
        } else {
            errcode = -1;
        }

        if (errcode != -1) {
            op.addImage(newPath);
            ImageCommand cmd = getImageCommand(CommandType.convert);
            //ImageCommand cmd = new ConvertCmd(true);
            //cmd.setSearchPath(GRAPHICS_MAGICK_PATH);
            try {
                System.out.println("src===" + srcPath);
                System.out.println("dest===" + newPath);
                cmd.run(op);
                op.closeOperation();
            } catch (Exception exp) {
                exp.printStackTrace();
                errcode = -1;
            }
        }

        return errcode;
    }

    /**
     * 图片信息
     *
     * @param imagePath
     * @return
     */
    public String showImageInfo(String imagePath) {
        String line = null;
        try {
            IMOperation op = new IMOperation();
            op.format("width:%w,height:%h,path:%d%f,size:%b%[EXIF:DateTimeOriginal]");
            op.addImage(1);
            ImageCommand identifyCmd = getImageCommand(CommandType.identify);
            ArrayListOutputConsumer output = new ArrayListOutputConsumer();
            identifyCmd.setOutputConsumer(output);
            identifyCmd.run(op, imagePath);
            ArrayList<String> cmdOutput = output.getOutput();
            assert cmdOutput.size() == 1;
            line = cmdOutput.get(0);
            op.closeOperation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(line);
        return line;
    }

    /**
     * 给图片加水印
     * @param srcPath   源图片路径
     */
    public int addImgText(String os,String srcPath,String content) throws Exception {
        int errcode = 0;
        IMOperation op = new IMOperation();
        op.font("微软雅黑");
        op.gravity("southeast");
        op.pointsize(18).fill("#BCBFC8").draw("text 0,0 "+content);   //("x1 x2 x3 x4") x1 格式，x2 x轴距离 x3 y轴距离  x4名称
        op.addImage();
        op.addImage();
        //ImageCommand convert = getImageCommand(CommandType.convert);
        ConvertCmd convert = new ConvertCmd();
        convert.setSearchPath(GRAPHICS_MAGICK_PATH);
        try {
            convert.run(op,srcPath,srcPath);
            op.closeOperation();
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
        }

        return errcode;
    }
    /**
     * 图片水印
     *
     * @param srcImagePath   源图片
     * @param waterImagePath 水印
     * @param destImagePath  生成图片
     * @param gravity  图片位置
     * @param dissolve 水印透明度
     */
    public int waterMark(String os,String waterImagePath, String srcImagePath, String destImagePath, String gravity, int dissolve) {
        int errcode = 0;
        IMOperation op = new IMOperation();
        op.gravity(gravity);
        op.dissolve(dissolve);
        op.addImage(waterImagePath);
        op.addImage(srcImagePath);
        op.addImage(destImagePath);
        //ImageCommand convert = getImageCommand(CommandType.convert);
        ConvertCmd convert = new ConvertCmd();
        convert.setSearchPath(GRAPHICS_MAGICK_PATH);
        try {
            convert.run(op);
            op.closeOperation();
        } catch (IOException e) {
            errcode = -1;
            e.printStackTrace();
        } catch (InterruptedException e) {
            errcode = -2;
            e.printStackTrace();
        } catch (IM4JavaException e) {
            errcode = -3;
            e.printStackTrace();
        }

        return errcode;
    }
    /**
     * 图片旋转
     *
     * @param srcImagePath
     * @param destImagePath
     * @param angle
     */
    public int rotate(String os,String srcImagePath, String destImagePath, double angle) {
        int errcode = 0;
        try {
            IMOperation op = new IMOperation();
            op.rotate(angle);
            op.addImage(srcImagePath);
            op.addImage(destImagePath);
            //ImageCommand convert = getImageCommand(CommandType.convert);
            ConvertCmd convert = new ConvertCmd();
            convert.setSearchPath(GRAPHICS_MAGICK_PATH);
            convert.run(op);
            op.closeOperation();
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
        }

        return errcode;
    }

    /**
     * 图片合成
     * @param args
     * @param maxWidth
     * @param maxHeight
     * @param newpath
     * @param mrg
     * @param type 1:横,2:竖
     */
    public int montage(String os,String[] args,Integer maxWidth,Integer maxHeight,String newpath,Integer mrg,String type) {
        int errcode = 0;
        IMOperation op = new IMOperation();
        //ImageCommand cmd = getImageCommand(CommandType.convert);
        ConvertCmd convert = new ConvertCmd();
        convert.setSearchPath(GRAPHICS_MAGICK_PATH);
        String thumb_size = maxWidth+"x"+maxHeight+"^";
        String extent = maxWidth+"x"+maxHeight;
        if("1".equals(type)){
            op.addRawArgs("+append");
        }else if("2".equals(type)){
            op.addRawArgs("-append");
        }

        op.addRawArgs("-thumbnail",thumb_size);
        op.addRawArgs("-gravity","center");
        op.addRawArgs("-extent",extent);

        Integer border_w = maxWidth / 40;
        op.addRawArgs("-border",border_w+"x"+border_w);
        op.addRawArgs("-bordercolor","#ccc");

        op.addRawArgs("-border",1+"x"+1);
        op.addRawArgs("-bordercolor","#fff");

        for(String img : args){
            op.addImage(img);
        }
        if("1".equals(type)){
            Integer whole_width = ((mrg / 2) +1 + border_w + maxWidth + border_w + (mrg / 2) +1)*args.length - mrg;
            Integer whole_height = maxHeight + border_w + 1;
            op.addRawArgs("-extent",whole_width + "x" +whole_height);
        }else if("2".equals(type)){
            Integer whole_width = maxWidth + border_w + 1;
            Integer whole_height = ((mrg / 2) +1 + border_w + maxHeight + border_w + (mrg / 2) +1)*args.length - mrg;
            op.addRawArgs("-extent",whole_width + "x" +whole_height);
        }
        op.addImage(newpath);
        try {
            convert.run(op);
            op.closeOperation();
        } catch (Exception e) {
            errcode = -1;
            e.printStackTrace();
        }

        return errcode;
    }

    public static void main(String[] args) throws Exception {
        resizeImage resizeImage = new resizeImage();
        String[] pics = new String[2];

        //for windows
        pics[0] = "C:\\Document\\phone\\IMG_20171003_082640.jpg";
        pics[1] = "C:\\Document\\phone\\IMG_20171003_082702.jpg";
        //resizeImage.montage("linux",pics,400,400,"C:\\Document\\phone\\t11.jpg",10,"1");
        resizeImage.showImageInfo(pics[1]);
        //resizeImage.rotate("linux",pics[1],"C:\\Document\\phone\\t12.JPG" ,45);
        resizeImage.zoomImage(67, 67, pics[1],"C:\\Document\\phone\\t13.JPG");

        //pics[0] = "/data/gugulx/zhucaifeng/images/11.jpg";
        //pics[1] = "/data/gugulx/zhucaifeng/images/12.jpg";
        //resizeImage.montage("linux",pics,400,400,"/data/gugulx/zhucaifeng/images/t11.jpg",10,"1");
        //resizeImage.showImageInfo("linux", pics[1]);
        //resizeImage.rotate("linux",pics[1],"/data/gugulx/zhucaifeng/images/t12.JPG" ,45);
        //resizeImage.zoomImage("linux",67, 67, pics[1],"/data/gugulx/zhucaifeng/images/t13.JPG");

        /*File jpegFile = new File("D:\\disneyapp\\webbuilder\\webapps\\cms\\sitespic\\product_331.jpg");
        Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);
        Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
        Collection<Tag> tags = null;
        Iterator<Tag> iter = null;
        //if (exif!=null) {
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            System.out.println(tag.getTagName() + "--" + tag.getDescription());
        }
        //}

        exif = metadata.getDirectory(GpsDirectory.class);
        //if (exif != null) {
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            if (tag.getTagName().equalsIgnoreCase("GPS Latitude")) {
                String latf_s = tag.getDescription();
                String[] items = latf_s.split(" ");
                items[0] = items[0].substring(0,items[0].length()-1);
                items[1] = items[1].substring(0,items[1].length()-1);
                items[2] = items[2].substring(0,items[2].length()-1);

                double degree = Double.parseDouble(items[0]);
                double minute = Double.parseDouble(items[1]);
                double second = Double.parseDouble(items[2]);
                System.out.println(degree);
                System.out.println(minute);
                System.out.println(second);
                System.out.println(degree + minute/60 + second/3600);
            }

            if (tag.getTagName().equalsIgnoreCase("GPS Longitude")) {
                String lonf_s = tag.getDescription();
                String[] items = lonf_s.split(" ");
                items[0] = items[0].substring(0,items[0].length()-1);
                items[1] = items[1].substring(0,items[1].length()-1);
                items[2] = items[2].substring(0,items[2].length()-1);

                double degree = Double.parseDouble(items[0]);
                double minute = Double.parseDouble(items[1]);
                double second = Double.parseDouble(items[2]);
                System.out.println(degree);
                System.out.println(minute);
                System.out.println(second);
                System.out.println(degree + minute/60 + second/3600);
            }
        }
        //}


        exif = metadata.getDirectory(ExifInteropDirectory.class);
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            System.out.println(tag);
        }

        exif = metadata.getDirectory(NikonType1MakernoteDirectory.class);
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            System.out.println(tag);
        }

        exif = metadata.getDirectory(ExifSubIFDDirectory.class);
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            System.out.println(tag);
        }

        exif = metadata.getDirectory(NikonType2MakernoteDirectory.class);
        tags = exif.getTags();
        iter = tags.iterator();
        //逐个遍历每个Tag
        while (iter.hasNext()) {
            Tag tag = (Tag) iter.next();
            System.out.println(tag);
        }

        //检查是否Tag中包含了图片属性-摘要中的作者 (xp)
        if (exif.containsTag(ExifIFD0Directory.TAG_WIN_AUTHOR)) {
            System.out.println("->Pic author is "
                    + exif.getDescription(ExifIFD0Directory.TAG_WIN_AUTHOR));
        }
        // 检查是否Tag中包含了图片属性-摘要中的标题 (xp)
        if (exif.containsTag(ExifIFD0Directory.TAG_WIN_TITLE)) {
            System.out.println("->Pic title is "
                    + exif.getDescription(ExifIFD0Directory.TAG_WIN_TITLE));
        }
        // 检查是否Tag中包含了图片属性-摘要中的主题 (xp)
        if (exif.containsTag(ExifIFD0Directory.TAG_WIN_SUBJECT)) {
            System.out.println("->Pic subject is "
                    + exif.getDescription(ExifIFD0Directory.TAG_WIN_SUBJECT));
        }
        */
    }
}
