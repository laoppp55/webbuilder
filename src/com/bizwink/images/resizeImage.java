package com.bizwink.images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.AffineTransformOp;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import com.bizwink.cms.pic.Pic;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifInteropDirectory;
import com.drew.metadata.exif.GpsDirectory;
import com.drew.metadata.exif.NikonType1MakernoteDirectory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.NikonType2MakernoteDirectory;
import com.drew.metadata.Tag;
import magick.CompositeOperator;
import magick.CompressionType;
import magick.DrawInfo;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import magick.PixelPacket;
import magick.PreviewType;
import com.bizwink.cms.publish.*;

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
                IPublishManager publishManager = PublishPeer.getInstance();
                fileDir = fileDir + "images" + File.separator;
                try {
                    int errcode = publishManager.publish(username, targetImageFile, siteid, fileDir, 0);
                } catch (PublishException exp) {
                    exp.printStackTrace();
                }
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

        try {
            if (size != null) {
                int xposi = size.indexOf("X");
                if (xposi == -1) xposi = size.indexOf("x");
                double twidth = 0.0f;
                double theight = 0.0f;
                if (xposi != -1) {   //对原图进行缩放，发布缩放后的图片
                    theight = Double.parseDouble(size.substring(0, xposi));
                    twidth = Double.parseDouble(size.substring(xposi + 1));
                    info = new ImageInfo(imgfile);
                    image = new MagickImage(info);
                    imageDim = image.getDimension();
                    int width = imageDim.width;
                    int height = imageDim.height;

                    if (height > theight || width > twidth) {
                        if (height > width)
                            Radio = theight / height;
                        else
                            Radio = twidth / width;
                    }

                    scaled = image.scaleImage((int) (width * Radio), (int) (height * Radio));//小图片文件的大小.
                    scaled.setFileName(targetImageFile);
                    scaled.writeImage(info);
                } else {
                    targetImageFile = imgfile;
                }
            } else {     //未设置图片缩放后的大小则发布原图
                targetImageFile = imgfile;
            }
        } catch(Exception exp){
            exp.printStackTrace();
        }finally{
            if (scaled != null) {
                scaled.destroyImages();
                image.destroyImages();
                IPublishManager publishManager = PublishPeer.getInstance();
                fileDir = fileDir + "images" + File.separator;
                try {
                    int errcode = publishManager.publish(username, targetImageFile, siteid, fileDir, 0);
                } catch (PublishException exp) {
                    exp.printStackTrace();
                }
            }
        }


        return targetImageFile;
    }

    public String createSmallPictureBy_jmagick(String targetFileRoot,String sourceFileRoot,String imgfile,String size) throws MagickException
    {
        ImageInfo info = null;
        MagickImage image = null;
        Dimension imageDim = null;
        MagickImage scaled = null;
        String targetImageFile = null;
        double  Radio = 1.0;

        try{
            if (targetFileRoot.endsWith(File.separator)) targetFileRoot=targetFileRoot.substring(0,targetFileRoot.length()-1);
            if (sourceFileRoot.endsWith(File.separator)) sourceFileRoot=sourceFileRoot.substring(0,sourceFileRoot.length()-1);
            //构建目标文件名称，包括全部的路径
            int posi = imgfile.indexOf(sourceFileRoot);
            if (size!=null && size!="")
                targetImageFile = targetFileRoot + File.separator + size  + "_" + imgfile.substring(posi + sourceFileRoot.length()+1);
            else
                targetImageFile = targetFileRoot + File.separator + "s_" + imgfile.substring(posi + sourceFileRoot.length()+1);

            //判断目标文件所在的路径是否存在，如果不存在创建目标目录
            String targetPath = targetImageFile.substring(0,targetImageFile.lastIndexOf(File.separator));
            File tf = new File(targetPath);
            if (!tf.exists()) tf.mkdirs();

            //开始缩放大的图片文件
            int xposi = size.indexOf("X");
            if (xposi == -1) xposi = size.indexOf("x");
            double twidth = 0.0f;
            double theight = 0.0f;
            if (xposi != -1) {   //对原图进行缩放，发布缩放后的图片
                twidth = Double.parseDouble(size.substring(0,xposi));
                theight = Double.parseDouble(size.substring(xposi+1));
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
            } else {  //发布原图
                targetImageFile = imgfile;
            }
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

    public Pic getImageAttributesBy_jmagick(String imgfile) throws MagickException
    {
        ImageInfo info = null;
        MagickImage image = null;
        Dimension imageDim = null;
        Pic picture = null;

        //System.out.println("java library path=" + System.getProperty("java.library.path"));

        picture = new Pic();
        info = new ImageInfo(imgfile);
        image = new MagickImage(info);
        imageDim = image.getDimension();
        int width = imageDim.width;
        int height = imageDim.height;
        picture.setHeight(height);
        picture.setWidth(width);

        System.out.println("width==" + width);

        /*if (imgfile.toLowerCase().endsWith(".jpg") || imgfile.toLowerCase().endsWith(".jpeg")) {
            Metadata metadata = null;

            try {
                metadata = JpegMetadataReader.readMetadata(new File(imgfile));
            } catch (IOException ioexp) {
            } catch (JpegProcessingException exp) {
            }

            if (metadata != null) {
                Directory exif = metadata.getDirectory(ExifIFD0Directory.class);
                Collection<Tag> tags = null;
                Iterator<Tag> iter = null;
                if (exif!=null) {
                    tags = exif.getTags();
                    iter = tags.iterator();
                    //逐个遍历每个Tag
                    while (iter.hasNext()) {
                        Tag tag = (Tag) iter.next();
                        if (tag.getTagName().equalsIgnoreCase("Make")) picture.setCammer(tag.getDescription());
                        if (tag.getTagName().equalsIgnoreCase("Model")) picture.setCammertype(tag.getDescription());
                    }
                }

                exif = metadata.getDirectory(GpsDirectory.class);
                if (exif != null) {
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
                            picture.setLatf(degree + minute/60 + second/3600);
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
                            picture.setLngf(degree + minute/60 + second/3600);
                            System.out.println(degree + minute/60 + second/3600);
                        }
                    }
                }
            }
        }*/


        return picture;
    }

    /**
     * 水印(图片logo)
     * @param filePath  源文件路径
     * @param toImg     修改图路径
     * @param logoPath  logo图路径
     * @throws MagickException
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
     * @throws MagickException
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
     * @throws MagickException
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


    public resizeImage(String fromdir,String todir)
    {
        this.fromdir = fromdir;
        this.todir  = todir;
    }

    public resizeImage(String sfilename,String[] pics)
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
        if(!F.isFile()) throw new IOException(F + " is not image file error in CreateThumbnail!");

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

    public static void main(String[] args) throws Exception {
        resizeImage resizeImage = new resizeImage();
        resizeImage.getImageAttributesBy_jmagick("C:\\Document\\photo\\mmexport1474190294272.jpg");

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
