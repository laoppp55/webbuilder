package com.bizwink.cms.modelManager;

import java.io.*;
import java.sql.*;
import java.util.regex.*;
import com.bizwink.upload.*;
import com.bizwink.cms.util.*;
import com.bizwink.cms.publish.*;

public class TemplatePeer
{
    private String forestr = null;

    //得到系统模板文件夹结构树
    public String getTemplatesTree(int columnID,String path) throws CmsException
    {
        //读取文件名配置表
        String arr[][] = null;

        try
        {
            String filePath = path + "inc" + File.separator + "dict.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));

            String temp = "";
            int i = 0;
            while (in.readLine() != null)
            {
                i++;
            }
            in.close();

            in = new BufferedReader(new FileReader(filePath));
            arr = new String[i][2];
            i = 0;

            while ((temp = in.readLine()) != null)
            {
                arr[i][0] = temp.substring(0, temp.indexOf("=="));
                arr[i][1] = temp.substring(temp.indexOf("==")+2, temp.length());
                i++;
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        StringBuffer content = new StringBuffer();
        path = path + "cmstemplates";

        content.append("<table border=0 cellPadding=0 cellSpacing=0 width=\"100%\">");
        content.append("<tr valign=\"top\">");
        content.append("<td nowrap><img src=\"../menu-images/menu_new_root.gif\" align=\"left\" border=\"0\" vspace=\"0\" hspace=\"0\"><span id=\"root\">&nbsp;<font class=line>系统模板</font></span>");
        content.append("</td>");
        content.append("</tr>");

        try
        {
            File file = new File(path);
            File[] list1 = file.listFiles();

            int k = 1;
            for (int i=0; i<list1.length; i++)
            {
                File files = new File(path + "\\" + list1[i].getName());
                File[] list2 = files.listFiles();

                String dirname = list1[i].getName();
                for (int j=0; j<arr.length; j++)
                {
                    if (list1[i].getName().compareToIgnoreCase(arr[j][0]) == 0)
                    {
                        dirname = arr[j][1];
                        break;
                    }
                }

                content.append("<tr valign=\"top\">");
                content.append("<td nowrap>");
                if (list2 != null && list2.length > 0)
                {
                    content.append("<a href=\"javascript:Expand(this.sub"+k+")\" onmouseover=\"window.status='点击加号展开/点击减号收缩';return true;\" onmouseout=\"window.status='';return true;\">");
                    if (i == list1.length-1)
                        content.append("<img src=\"../menu-images/menu_corner_plus.gif\" onclick=\"javascript:Switch(image"+i+")\" name=\"image"+i+"\" id=\"image"+i+"\" alt=\"展开/收缩\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_folder_closed.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                    else
                        content.append("<img src=\"../menu-images/menu_tee_plus.gif\" onclick=\"javascript:Switch(image"+i+")\" name=\"image"+i+"\" id=\"image"+i+"\" alt=\"展开/收缩\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_folder_closed.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                }
                else
                {
                    if (i == list1.length-1)
                        content.append("<img src=\"../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font>");
                    else
                        content.append("<img src=\"../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font>");
                }
                content.append("</td>");
                content.append("</tr>");

                for (int j=0; j<list2.length; j++)
                {
                    dirname = list2[j].getName();
                    for (int m=0; m<arr.length; m++)
                    {
                        if (list2[j].getName().compareToIgnoreCase(arr[m][0]) == 0)
                        {
                            dirname = arr[m][1];
                            break;
                        }
                    }

                    content.append("<tr valign=\"top\" style=\"display:none\" name=\"sub"+k+"\" id=\"sub"+k+"\">");
                    content.append("<td nowrap>");

                    if (i == list1.length-1)
                    {
                        if (j == list2.length-1)
                        {
                            content.append("<img src=\"../menu-images/menu_pixel.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                        else
                        {
                            content.append("<img src=\"../menu-images/menu_pixel.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                    }
                    else
                    {
                        if (j == list2.length-1)
                        {
                            content.append("<img src=\"../menu-images/menu_bar.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                        else
                        {
                            content.append("<img src=\"../menu-images/menu_bar.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?columnID="+columnID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                    }
                    content.append("</td>");
                    content.append("</tr>");
                }
                k++;
            }
            content.append("</table>");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return content.toString();
    }

    //得到某文件夹下的模板文件
    public String[][] getTemplates(String path) throws CmsException
    {
        String[][] arr = null;

        try
        {
            File file = new File(path);
            File[] list = file.listFiles();

            if (list.length > 0)
            {
                int j = 0;
                for (int i=0; i<list.length; i++)
                {
                    if (list[i].isFile() && QueryName(list[i].getName()))
                    {
                        j++;
                    }
                }

                if (j > 0)
                {
                    int k = 0;
                    arr = new String[j][2];

                    for (int i=0; i<list.length; i++)
                    {
                        if (list[i].isFile() && QueryName(list[i].getName()))
                        {
                            arr[k][0] = list[i].getName();
                            arr[k][1] = (new Timestamp(list[i].lastModified())).toString().substring(0,19);
                            k++;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return arr;
    }

    private boolean QueryName(String name) throws CmsException
    {
        boolean same = false;

        String extname = name.substring(name.lastIndexOf(".")+1,name.length());
        //可以随时扩展
        String[] exts = {"htm","html","shtm","shtml","asp","jsp","php"};

        for (int i=0; i<exts.length; i++)
        {
            if (extname.compareToIgnoreCase(exts[i]) == 0)
            {
                same = true;
                break;
            }
        }

        return same;
    }

    public void create(String username,String filename,String root,String siteName,
                       String dir,int imgFlag,int siteID) throws CmsException
    {
        try
        {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            String tempName = now.toString().substring(0,19);
            tempName = StringUtil.replace(tempName," ","_");
            tempName = StringUtil.replace(tempName,":","-");
            this.forestr = String.valueOf(tempName);

            String content = readModelFile(filename,siteName,dir,imgFlag);

            String tempPath = root + "sites" + File.separator + siteName +
                    File.separator + "_templates" + dir;
            String pathname = tempPath + forestr + ".html";

            File tempDir = new File(tempPath);
            if (!tempDir.exists())
            {
                tempDir.mkdirs();
            }

            //发布模板到cms/sites
            PrintWriter pw = new PrintWriter(new FileOutputStream(pathname));
            pw.write(content);
            pw.close();

            //发布图象
            String picpath = filename.substring(filename.lastIndexOf(".")-1,
                    filename.lastIndexOf("."));
            //源图片路径
            picpath = filename.substring(0,filename.lastIndexOf(File.separator)+1) +
                    "images" + picpath;

            //目的图片路径
            String topath = null;
            if (imgFlag == 0)
            {
                topath = root + "sites" + File.separator + siteName +
                        File.separator + "images";
            }
            else
            {
                topath = root + "sites" + File.separator + siteName +
                        dir + "images";
            }

            File file = new File(picpath);
            if (file.exists())
            {
                File pics[] = file.listFiles();
                if (pics.length > 0)
                {
                    for (int i=0; i<pics.length; i++)
                    {
                        if (pics[i].isFile())
                        {
                            //拷贝图象文件并改名到cms/sites
                            FileDeal.copy(picpath + File.separator + pics[i].getName(),
                                    topath + File.separator + forestr + "_" + pics[i].getName(), 0);

                            //拷贝图象文件并改名到WWW
                            String objPath = null;
                            if (imgFlag == 0)
                            {
                                objPath = File.separator + "images" + File.separator;
                            }
                            else
                            {
                                objPath = dir + "images" + File.separator;
                            }
                            //String rscPath = picpath + File.separator + pics[i].getName();
                            String rscPath = topath + File.separator + forestr + "_" + pics[i].getName();

                            IPublishManager publishMgr = PublishPeer.getInstance();
                            int retcode = publishMgr.publish(username,rscPath,siteID,objPath,0);
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String readModelFile(String filename,String sitename,String dir,int imgflag)
            throws IOException
    {
        File modelFile = new File(filename);
        int modelLength = (int)modelFile.length();
        char[] buf = new char[modelLength];
        StringBuffer tempBuf = new StringBuffer();
        String str = "";

        try
        {
            FileReader modelIn = new FileReader(modelFile);
            int retChar = modelIn.read(buf, 0,modelLength);
            modelIn.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        tempBuf.insert(0,buf);
        str = tempBuf.toString();
        str = src_Element(str,sitename,dir,imgflag);
        str = backgroud_Element(str,sitename,dir,imgflag);
        str = css_Element(str,sitename,dir,imgflag);
        str = swf_Element(str,sitename,dir,imgflag);

        return str;
    }

    public String src_Element(String str,String sitename,String dir,int imgflag)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)img[^>]*>",Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length-1];

            for(int i=0;i<buf.length-1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0,posi+1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSrc_Str(tag[i],sitename,dir,imgflag);
            }

            for(int i=0;i<tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String backgroud_Element(String str,String sitename,String dir,int imgflag)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<[^<]*background(\\s*)=(\\s*)[^>]*>",Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length - 1];

            for(int i=0;i<buf.length-1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0,posi+1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatBackground_Str(tag[i],sitename,dir,imgflag);
            }

            for(int i=0;i<tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String css_Element(String str,String sitename,String dir,int imgflag)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)link[^>]*>",Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length-1];

            for(int i=0;i<buf.length-1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0,posi+1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatCss_Str(tag[i],sitename,dir,imgflag);
            }

            for(int i=0;i<tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public String swf_Element(String str,String sitename,String dir,int imgflag)
    {
        StringBuffer buffer = new StringBuffer();
        String tempBuf = str;
        int num = 0;
        int posi = 0;

        try
        {
            Pattern p = Pattern.compile("<(\\s*)param(\\s*)[^<>]*name(\\s*)=(\\s*)[\"|']movie[\"|'][^>]*>",Pattern.CASE_INSENSITIVE);
            String buf[] = p.split(tempBuf);
            String tag[] = new String[buf.length-1];

            for(int i=0;i<buf.length-1; i++)
            {
                tempBuf = tempBuf.substring(buf[i].length());
                posi = tempBuf.indexOf(">");
                tag[i] = tempBuf.substring(0,posi+1);
                tempBuf = tempBuf.substring(tag[i].length());
                tag[i] = formatSwf_Str(tag[i],sitename,dir,imgflag,"value");
            }

            for(int i=0;i<tag.length; i++)
            {
                buffer.append(buf[i]);
                buffer.append(tag[i]);
            }
            buffer.append(buf[tag.length]);

            //处理embed中的swf文件名
            tempBuf = buffer.toString();
            p = Pattern.compile("<(\\s*)embed(\\s*)[^<>]*src(\\s*)=(\\s*)[^>]*>",Pattern.CASE_INSENSITIVE);
            String buf1[] = p.split(tempBuf);
            String tag1[] = new String[buf1.length - 1];

            for(int i=0;i<buf1.length-1; i++)
            {
                tempBuf = tempBuf.substring(buf1[i].length());
                posi = tempBuf.indexOf(">");
                tag1[i] = tempBuf.substring(0,posi+1);
                tempBuf = tempBuf.substring(tag1[i].length());
                tag1[i] = formatSwf_Str(tag1[i],sitename,dir,imgflag,"src");
            }

            buffer = new StringBuffer();
            for(int i=0;i<tag1.length; i++)
            {
                buffer.append(buf1[i]);
                buffer.append(tag1[i]);
            }
            buffer.append(buf1[tag1.length]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return buffer.toString();
    }


    public String formatSrc_Str(String str,String sitename,String dir,int imgflag)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        String dirName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("src");
        head_str = str.substring(0,posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi+1).trim();
        posi = buf.indexOf(" ");

        if(posi != -1)
        {
            fileName = buf.substring(0,posi);
            tail_str = buf.substring(posi);
        }
        else
        {
            fileName = buf.substring(0,buf.length() - 1);
            tail_str = ">";
        }

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            dirName = fileName.substring(0,posi);
            fileName = fileName.substring(posi + 1);
        }
        fileName = forestr + "_" + fileName;

        if (imgflag == 0)
        {
            str = head_str + "src=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        }
        else
        {
            str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\""+ tail_str;
        }

        return str;
    }

    public String formatBackground_Str(String str,String sitename,String dir,int imgflag)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("background");
        head_str = str.substring(0,posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi+1).trim();

        posi = buf.indexOf(" ");
        if(posi != -1)
        {
            fileName = buf.substring(0,posi);
            tail_str = buf.substring(posi);
        }
        else
        {
            fileName = buf.substring(0,buf.length() - 1);
            tail_str = ">";
        }

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }
        fileName = forestr + "_" + fileName;

        if (imgflag == 0)
        {
            str = head_str + "background=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        }
        else
        {
            str = head_str + "background=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String formatCss_Str(String str,String sitename,String dir,int imgflag)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("href");
        head_str = str.substring(0,posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi+1).trim();

        posi = buf.indexOf(" ");
        if(posi != -1)
        {
            fileName = buf.substring(0,posi);
            tail_str = buf.substring(posi);
        }
        else
        {
            fileName = buf.substring(0,buf.length()-1);
            tail_str = ">";
        }

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }
        fileName = forestr + "_" + fileName;

        if (imgflag == 0)
        {
            str = head_str + "href=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        }
        else
        {
            str = head_str + "href=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        }

        return str;
    }

    public String formatSwf_Str(String str,String sitename,String dir,int imgflag,String strflag)
    {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf(strflag);
        head_str = str.substring(0,posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi+1).trim();

        posi = buf.indexOf(" ");
        if(posi != -1)
        {
            fileName = buf.substring(0,posi);
            tail_str = buf.substring(posi);
        }
        else
        {
            fileName = buf.substring(0,buf.length() - 1);
            tail_str = ">";
        }

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1,fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length()-1) fileName = fileName.substring(0,fileName.length()-1);

        posi = fileName.lastIndexOf("/");
        if (posi != -1)
        {
            fileName = fileName.substring(posi + 1);
        }
        fileName = forestr + "_" + fileName;

        if (strflag.equals("src"))
            if (imgflag == 0)
                str = head_str + "src=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
            else
                str = head_str + "src=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;
        else
        if (imgflag == 0)
            str = head_str + "value=\"/webbuilder/sites/" + sitename + "/images/" + fileName + "\"" + tail_str;
        else
            str = head_str + "value=\"/webbuilder/sites/" + sitename + dir + "images/" + fileName + "\"" + tail_str;

        return str;
    }

    //////////////////////////////////////////////////////////////////////////////
    //for product add by Eric 2003-10-29
    //得到系统模板文件夹结构树
    public String getTypeTemplatesTree(int typeID,String path) throws CmsException
    {
        //读取文件名配置表
        String arr[][] = null;

        try
        {
            String filePath = path + "inc" + File.separator + "dict.txt";
            BufferedReader in = new BufferedReader(new FileReader(filePath));

            String temp = "";
            int i = 0;
            while (in.readLine() != null)
            {
                i++;
            }
            in.close();

            in = new BufferedReader(new FileReader(filePath));
            arr = new String[i][2];
            i = 0;

            while ((temp = in.readLine()) != null)
            {
                arr[i][0] = temp.substring(0, temp.indexOf("=="));
                arr[i][1] = temp.substring(temp.indexOf("==")+2, temp.length());
                i++;
            }
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        StringBuffer content = new StringBuffer();
        path = path + "cmstemplates";

        content.append("<table border=0 cellPadding=0 cellSpacing=0 width=\"100%\">");
        content.append("<tr valign=\"top\">");
        content.append("<td nowrap><img src=\"../../menu-images/menu_new_root.gif\" align=\"left\" border=\"0\" vspace=\"0\" hspace=\"0\"><span id=\"root\">&nbsp;<font class=line>系统模板</font></span>");
        content.append("</td>");
        content.append("</tr>");

        try
        {
            File file = new File(path);
            File[] list1 = file.listFiles();

            int k = 1;
            for (int i=0; i<list1.length; i++)
            {
                File files = new File(path + "\\" + list1[i].getName());
                File[] list2 = files.listFiles();

                String dirname = list1[i].getName();
                for (int j=0; j<arr.length; j++)
                {
                    if (list1[i].getName().compareToIgnoreCase(arr[j][0]) == 0)
                    {
                        dirname = arr[j][1];
                        break;
                    }
                }

                content.append("<tr valign=\"top\">");
                content.append("<td nowrap>");
                if (list2 != null && list2.length > 0)
                {
                    content.append("<a href=\"javascript:Expand(this.sub"+k+")\" onmouseover=\"window.status='点击加号展开/点击减号收缩';return true;\" onmouseout=\"window.status='';return true;\">");
                    if (i == list1.length-1)
                        content.append("<img src=\"../../menu-images/menu_corner_plus.gif\" onclick=\"javascript:Switch(image"+i+")\" name=\"image"+i+"\" id=\"image"+i+"\" alt=\"展开/收缩\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_folder_closed.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                    else
                        content.append("<img src=\"../../menu-images/menu_tee_plus.gif\" onclick=\"javascript:Switch(image"+i+")\" name=\"image"+i+"\" id=\"image"+i+"\" alt=\"展开/收缩\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_folder_closed.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                }
                else
                {
                    if (i == list1.length-1)
                        content.append("<img src=\"../../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font>");
                    else
                        content.append("<img src=\"../../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18></a><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font>");
                }
                content.append("</td>");
                content.append("</tr>");

                for (int j=0; j<list2.length; j++)
                {
                    dirname = list2[j].getName();
                    for (int m=0; m<arr.length; m++)
                    {
                        if (list2[j].getName().compareToIgnoreCase(arr[m][0]) == 0)
                        {
                            dirname = arr[m][1];
                            break;
                        }
                    }

                    content.append("<tr valign=\"top\" style=\"display:none\" name=\"sub"+k+"\" id=\"sub"+k+"\">");
                    content.append("<td nowrap>");

                    if (i == list1.length-1)
                    {
                        if (j == list2.length-1)
                        {
                            content.append("<img src=\"../../menu-images/menu_pixel.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                        else
                        {
                            content.append("<img src=\"../../menu-images/menu_pixel.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                    }
                    else
                    {
                        if (j == list2.length-1)
                        {
                            content.append("<img src=\"../../menu-images/menu_bar.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_corner.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                        else
                        {
                            content.append("<img src=\"../../menu-images/menu_bar.gif\" align=left border=0 vspace=0 hspace=0 width=18 height=18><img src=\"../menu-images/menu_tee.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18><a href=\"right.jsp?typeID="+typeID+"&dir1="+list1[i].getName()+"&dir2="+list2[j].getName()+"\" target=right><img src=\"../menu-images/menu_link_default.gif\" align=\"left\" border=0 vspace=0 hspace=0 width=18 height=18>&nbsp;<font class=line>"+dirname+"</font></a>");
                        }
                    }
                    content.append("</td>");
                    content.append("</tr>");
                }
                k++;
            }
            content.append("</table>");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return content.toString();
    }

}