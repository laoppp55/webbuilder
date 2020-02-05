package com.bizwink.cms.util;

import com.bizwink.cms.pic.Pic;
import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 12-5-29
 * Time: 下午10:44
 * To change this template use File | Settings | File Templates.
 */
public class ImageUtil {
    public Pic getImageAttrValue(String filename,String dirname) {
        Pic pic = null;
        String o_filename = filename.substring(filename.lastIndexOf(java.io.File.separator) + 1);
        try {
            File F = new File(filename);
            BufferedImage Bi = ImageIO.read(F);
            if (Bi != null) {
                pic = new Pic();
                int width = Bi.getWidth();
                int height = Bi.getHeight();
                long size = F.length()/1024;
                pic.setWidth(width);
                pic.setHeight(height);
                pic.setPicsize((int)size);
                pic.setPicname(o_filename);
                pic.setFilename(o_filename);
                //System.out.println(dirname + "images/" + o_filename);
                pic.setImgurl(dirname + "images/" + o_filename);
                pic.setInfotype(0);
                pic.setCreatedate(new Timestamp(System.currentTimeMillis()));
            }
        } catch (IOException ioexp) {
            ioexp.printStackTrace();
        }

        return pic;
    }

    public  Document parse(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    public  String savepicFromWebsite(String imageurl,String cms_image_path) {
        String filepath = null;
        int looknum = 0;
        boolean successflag = false;

        File cms_dir = new File(cms_image_path);
        if (!cms_dir.exists()) {
            cms_dir.mkdirs();
        }

        while (successflag==false && looknum <3) {
            try {
                URL imgurl = new URL(imageurl);

                // 构造Image对象
                HttpURLConnection con = (HttpURLConnection)(imgurl.openConnection());
                con.setConnectTimeout(1000);
                con.setReadTimeout(1000);

                InputStream is = null;
                is = con.getInputStream();
                OutputStream os = null;

                //构造文件名.
                int startIndex = imageurl.lastIndexOf("/");
                String filename = imageurl.substring(startIndex+1);
                filename = StringUtil.replace(filename,"%","");
                filepath = cms_image_path + filename;
                //System.out.println("filepath=" + filepath);
                os = new FileOutputStream(filepath);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while((bytesRead = is.read(buffer,0,8192))!=-1){
                    os.write(buffer,0,bytesRead);
                }
                is.close();
                os.close();
                successflag=true;

                //向CMS中存入图片
                //System.out.println("cms_image_path=" + cms_image_path + imageurl.substring(startIndex+1));
                /*FileOutputStream cms_out = new FileOutputStream(cms_image_path + filename);
                FileInputStream is_file = new FileInputStream(filepath);
                while((bytesRead = is_file.read(buffer,0,8192))!=-1){
                    cms_out.write(buffer,0,bytesRead);
                }
                is_file.close();
                cms_out.close();
                successflag=true;
                 */

                // 得到源图宽
                /*Image src = javax.imageio.ImageIO.read(con.getInputStream());
                if (src!=null) {
                    int width = src.getWidth(null);
                    // 得到源图长
                    int height = src.getHeight(null);
                    Image scaledImage = src.getScaledInstance(width, height,Image.SCALE_DEFAULT);
                    BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
                    tag.getGraphics().drawImage(scaledImage, 0, 0, width, height, null);

                    //向WEB中存入图片
                    int startIndex = imageurl.lastIndexOf("/");
                    filepath = dirname + imageurl.substring(startIndex+1);
                    FileOutputStream out = new FileOutputStream(filepath);
                    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                    // 近JPEG编码
                    encoder.encode(tag);
                    out.close();

                    //向CMS中存入图片
                    //System.out.println("cms_image_path=" + cms_image_path + imageurl.substring(startIndex+1));
                    FileOutputStream cms_out = new FileOutputStream(cms_image_path + imageurl.substring(startIndex+1));
                    encoder = JPEGCodec.createJPEGEncoder(cms_out);
                    encoder.encode(tag);
                    cms_out.close();

                    successflag=true;
                }*/
            } catch (IOException ioexp) {
                looknum = looknum + 1;
                filepath = null;
            }
        }

        return filepath;
    }

    public String createThumbnailBy_jmagick(String imgfile,String size) throws MagickException
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

            //向CMS系统写入该图片
            /*File cms_dir = new File(cms_image_path);
            if (!cms_dir.exists()) {
                cms_dir.mkdirs();
            }
            int startIndex = targetImageFile.lastIndexOf(java.io.File.separator);
            scaled.setFileName(cms_image_path + targetImageFile.substring(startIndex));
            scaled.writeImage(info);
            */
        } catch (Exception exp) {
            targetImageFile = null;
            exp.printStackTrace();
        } finally{
            if(scaled != null){
                scaled.destroyImages();
            }
        }

        return targetImageFile;
    }


    public String createImageBy_jmagick(int siteid,String username,String fileDir,String imgfile,String cms_image_path) throws MagickException
    {
        ImageInfo info = null;
        MagickImage image = null;
        Dimension imageDim = null;
        MagickImage scaled = null;
        String targetImageFile = imgfile.substring(0,imgfile.lastIndexOf(".")) + "_200"  +imgfile.substring(imgfile.lastIndexOf("."));
        double  Radio = 1.0;

        try{
            info = new ImageInfo(imgfile);
            image = new MagickImage(info);
            imageDim = image.getDimension();
            int width = imageDim.width;
            int height = imageDim.height;
            //System.out.println("width=" + width);
            //System.out.println("height=" + height);

            if (height > width)
                Radio = 200.0f/height;
            else
                Radio = 200.0f/width;

            //System.out.println("Radio=" + Radio);

            scaled = image.scaleImage((int)(width*Radio), (int)(height*Radio));//小图片文件的大小.
            scaled.setFileName(targetImageFile);
            scaled.writeImage(info);

            //向CMS系统写入该图片
            File cms_dir = new File(cms_image_path);
            if (!cms_dir.exists()) {
                cms_dir.mkdirs();
            }
            int startIndex = targetImageFile.lastIndexOf(java.io.File.separator);
            scaled.setFileName(cms_image_path + targetImageFile.substring(startIndex));
            scaled.writeImage(info);
        } catch (Exception exp) {
            targetImageFile = null;
            exp.printStackTrace();
        } finally{
            if(scaled != null){
                scaled.destroyImages();
            }
        }

        return targetImageFile;
    }
}
