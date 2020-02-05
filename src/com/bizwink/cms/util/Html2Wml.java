package com.bizwink.cms.util;

import java.io.*;
import java.util.regex.*;
import java.util.*;

public class Html2Wml
{
    public static void main(String args[])
    {
        String content = "";
        String filename = "D:\\site_pub\\wap\\2.wml";
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = null;
            while ((line=br.readLine()) != null)
            {
                content += line + "\r\n";
            }
            br.close();

            content = convert(content,0);

            PrintWriter pw = new PrintWriter(new FileOutputStream(filename));
            pw.write(content);
            pw.close();

            content = "";
            br = new BufferedReader(new FileReader(filename));
            while ((line=br.readLine()) != null)
            {
                if (line.length() > 0)
                    content += line + "\r\n";
            }
            br.close();

            pw = new PrintWriter(new FileOutputStream(filename));
            pw.write(content);
            pw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //1--对栏目页进行转换
    //0--对内容页进行转换
    public static String convert(String content,int contentType)
    {
        content = content.toLowerCase();

        content = content.replaceAll("<html>","");
        content = content.replaceAll("</html>","");
        content = content.replaceAll("<body>","");
        content = content.replaceAll("</body>","");
        content = content.replaceAll("<head>","");
        content = content.replaceAll("</head>","");
        content = content.replaceAll("<title>","");
        content = content.replaceAll("</title>","");
        content = content.replaceAll("<tbody>","");
        content = content.replaceAll("</tbody>","");
        content = content.replaceAll("<meta\\s*[^<>]*>","");
        content = content.replaceAll("<!doctype\\s*[^<>]*>","");

        //全部改为双引号
        Pattern p = Pattern.compile("<[^<>]*=\\s*[^\"][^<>]*>");
        Matcher m = p.matcher(content);
        while (m.find())
        {
            int start = m.start();
            int end = m.end();
            String htmlstr = content.substring(start, end);
            if (htmlstr.startsWith("<a "))
            {
                //需继续分析title
            }
            else
            {
                Pattern p1 = Pattern.compile("=\\s*[^\"][^<>\\s]*[>\\s]");
                Matcher m1 = p1.matcher(htmlstr);
                while (m1.find())
                {
                    int start1 = m1.start();
                    int end1 = m1.end();
                    String htmlstr1 = htmlstr.substring(start1, end1);
                    if (htmlstr1.endsWith(" "))
                    {
                        htmlstr1 = htmlstr1.replaceAll(" ","");
                        htmlstr1 = "=\"" + htmlstr1.substring(1) + "\" ";
                    }
                    else
                    {
                        htmlstr1 = htmlstr1.replaceAll(" ","");
                        htmlstr1 = "=\"" + htmlstr1.substring(1,htmlstr1.length()-1) + "\">";
                    }

                    htmlstr = htmlstr.substring(0,start1) + htmlstr1 + htmlstr.substring(end1);
                    m1 = p1.matcher(htmlstr);
                }

                content = content.substring(0,start) + htmlstr + content.substring(end);
                m = p.matcher(content);
            }
        }

        //删除文章中的FLASH
        content = resultByFilter(content,"<object","</object>");

        //删除文章中的<FORM>
        content = resultByFilter(content,"<form","</form>");

        //删除文章中的<script>脚本内容
        content = resultByFilter(content,"<script","</script>");

        //删除<iframe>
        content = resultByFilter(content,"<iframe","</iframe>");

        //删除格式定义<style>
        content = resultByFilter(content,"<style","</style>");

        if (contentType == 0) {                              //发布文章内容
            String[] reserved = {"<a ","<img ","<br>"};        //被保留的元素
            Vector contentBuf = getContent(content,reserved);
            content = "";
            for (int i=0; i<contentBuf.size();i++) {
                content = content + (String)contentBuf.get(i);
            }

        } else {                                             //发布模板
            String[] reserved = {"<a ","<img ","<br>","<table","<tr","<td","</td>","</tr>","</table>"};   //被保留的元素
            Vector contentBuf = getTemplateContent(content,reserved);
            content = "";
            for (int i=0; i<contentBuf.size();i++) {
                content = content + (String)contentBuf.get(i);
            }
        }

        content = StringUtil.replace(content,"&nbsp;", " ");
        content = StringUtil.replace(content,"<br>", "<br/>");

        //处理img
        String buf = content;
        p = Pattern.compile("<\\s*img[^<>]*>",Pattern.CASE_INSENSITIVE);
        m = p.matcher(buf);
        String srcstr = "";
        String newstrsrc = "";
        while (m.find())
        {
            int start = m.start();
            int end = m.end();
            srcstr = buf.substring(start,end);
            newstrsrc = formatImgForWML(srcstr);
            content = StringUtil.replace(content,srcstr,newstrsrc);
            buf = buf.substring(end);
            m = p.matcher(buf);
        }

        //处理文本中的连接
        buf = content;
        p = Pattern.compile("<\\s*a[^<>]*>",Pattern.CASE_INSENSITIVE);
        m = p.matcher(buf);
        String href = "";
        String newhref = "";
        while (m.find())
        {
            int start = m.start();
            int end = m.end();
            href = buf.substring(start,end);
            newhref = formatHrefForWML(href);
            content = StringUtil.replace(content,href,newhref);
            buf = buf.substring(end);
            m = p.matcher(buf);
        }

        //汉字转换UTF-8的格式
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<content.length(); i++)
        {
            int hashCode = content.substring(i,i+1).hashCode();
            if (hashCode > 127)
                sb.append("&#" + hashCode + ";");
            else
                sb.append((char)hashCode);
        }

        content = sb.toString();

        return content;
    }

    public static String resultByFilter(String content,String startStr,String endStr) {
        String result = content.toLowerCase();
        String lowcase_startStr = startStr.toLowerCase();
        String lowcase_endStr = endStr.toLowerCase();
        int num = 0;
        int posi = 0;

        while (result.indexOf(lowcase_endStr) != -1)
        {
            num = num +1;
            posi = result.indexOf(lowcase_endStr);
            result = result.substring(posi + lowcase_endStr.length());
        }

        String[] buf = new String[num + 1];
        int startPosi = 0;
        int endPosi = 0;
        int i = 0;

        if (num > 0)
        {
            String tempBuf = content;                           //用于截取信息的字符串
            String lowcase_content = content.toLowerCase();     //用于获取位置信息的字符串
            for (i=0; i<num; i++)
            {
                startPosi = lowcase_content.indexOf(lowcase_startStr);
                endPosi = lowcase_content.indexOf(lowcase_endStr);
                buf[i] = tempBuf.substring(0, startPosi).trim();
                tempBuf = tempBuf.substring(endPosi + lowcase_endStr.length()).trim();
                lowcase_content = lowcase_content.substring(endPosi + lowcase_endStr.length()).trim();
            }
            buf[i] = tempBuf;
            result = "";
            for (i=0; i<num; i++) {
                result = result + buf[i];
            }
            result = result + buf[i];
        }

        return result;
    }

    public static Vector getContent(String body,String[] reserved){
        Vector contents = new Vector();
        byte[] buf = ("<html>" + body + "</html>").getBytes();
        int i = 0;
        String tempbuf = "";

        //去掉字符<前面的所有字符
        while(buf[i] != 0) {
            if (buf[i] != '<')
                i = i + 1;
            else
                break;
        }

        int cardid = 1;
        int pageContentLength = 0;
        contents.add("<bizwink-page>");
        while(buf[i] != 0) {
            if (buf[i] == '<') {
                tempbuf = "";
                while (buf[i] != '>' && buf[i] != 0) {
                    tempbuf = tempbuf + new String(buf,i,1);
                    i = i + 1;
                }
            }

            if (tempbuf.indexOf("<a ") >=0 ) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<img ") >= 0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("</a") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<br") >=0) {
                contents.add(tempbuf + ">");
            }

            if (buf[i] == '>') {
                String content = "";
                while (buf[i] != '<' && buf[i] != 0) {
                    if (buf[i] < 0) {
                        content = content + new String(buf,i,2);
                        i = i + 2;
                    } else {
                        content = content + new String(buf,i,1);
                        i = i + 1;
                    }
                    if (i >= buf.length) break;
                }

                contents.add(content.substring(1,content.length()));
                pageContentLength = pageContentLength + content.length();
                if (pageContentLength >= 2048)  {
                    pageContentLength = 0;
                    cardid = cardid + 1;
                    contents.add("</bizwink-page>");
                    contents.add("<bizwink-page>");
                }
            }

            if (i >= buf.length) break;
        }

        contents.add("</bizwink-page>");

        //增加导航条信息
        //if (cardid > 1) {
        //  contents.add("<a href=\"#card2\">2</a>");
        //}

        return contents;
    }

    public static Vector getTemplateContent(String body,String[] reserved){
        Vector contents = new Vector();
        byte[] buf = body.getBytes();
        int i = 0;
        String tempbuf = "";

        //去掉字符<前面的所有字符
        while(buf[i] != 0) {
            if (buf[i] != '<')
                i = i + 1;
            else
                break;
        }

        while(buf[i] != 0) {
            if (buf[i] == '<') {
                tempbuf = "";
                while (buf[i] != '>' && buf[i] != 0) {
                    tempbuf = tempbuf + new String(buf,i,1);
                    i = i + 1;
                }
            }

            if (tempbuf.indexOf("<a ") >=0 ) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<img ") >= 0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("</a") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<table") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<tr") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("<td") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("</td") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("</tr") >=0) {
                contents.add(tempbuf + ">");
            } else if(tempbuf.indexOf("</table") >=0) {
                contents.add(tempbuf + ">");
            }  else if(tempbuf.indexOf("<br") >=0) {
                contents.add(tempbuf + ">");
            }

            if (buf[i] == '>') {
                String content = "";
                while (buf[i] != '<' && buf[i] != 0) {
                    if (buf[i] < 0) {
                        content = content + new String(buf,i,2);
                        i = i + 2;
                    } else {
                        content = content + new String(buf,i,1);
                        i = i + 1;
                    }
                    if (i >= buf.length) break;
                }

                contents.add(content.substring(1,content.length()));
            }

            if (i >= buf.length) break;
        }

        return contents;
    }

    public static String formatHrefForWML(String str) {
        String buf;
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        int posi = 0;

        buf = str.toLowerCase();
        posi = buf.indexOf("href");
        if (posi>=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                fileName = buf.substring(0, posi);
            }
            else {
                fileName = buf.substring(0, buf.length() - 1);
            }

            posi = fileName.indexOf("\"");
            if (posi == 0) fileName = fileName.substring(1, fileName.length());
            posi = fileName.indexOf("'");
            if (posi == 0) fileName = fileName.substring(1, fileName.length());

            posi = fileName.lastIndexOf("'");
            if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
            posi = fileName.lastIndexOf("\"");
            if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

            str = "<a href=\"" + fileName + "\">";
        }
        return str;
    }

    public static String formatImgForWML(String str) {
        String buf;
        String content = "";
        String head_str = "";
        String tail_str = "";
        String fileName = "";
        String alignstr = "";
        String heightstr = "";
        String widthstr = "";
        String vspacestr = "";
        String hspacestr = "";
        String altstr = "";
        int posi = 0;

        //获取SRC的内容
        buf = str.toLowerCase();
        posi = buf.indexOf("src");
        head_str = str.substring(0, posi);
        buf = str.substring(posi);
        posi = buf.indexOf("=");
        buf = buf.substring(posi + 1).trim();
        posi = buf.indexOf(" ");

        if (posi != -1) {
            fileName = buf.substring(0, posi);
        }
        else {
            fileName = buf.substring(0, buf.length() - 1);
        }

        posi = fileName.indexOf("\"");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());
        posi = fileName.indexOf("'");
        if (posi == 0) fileName = fileName.substring(1, fileName.length());

        posi = fileName.lastIndexOf("'");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);
        posi = fileName.lastIndexOf("\"");
        if (posi == fileName.length() - 1) fileName = fileName.substring(0, fileName.length() - 1);

        content = content + "<img src=\"" + fileName + "\" ";

        //获取align元素的内容
        buf = str.toLowerCase();
        posi = buf.indexOf("alt");
        if (posi >=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                altstr = buf.substring(0, posi);
            }
            else {
                altstr = buf.substring(0, buf.length() - 1);
            }

            posi = altstr.indexOf("\"");
            if (posi == 0) altstr = altstr.substring(1, altstr.length());
            posi = altstr.indexOf("'");
            if (posi == 0) altstr = altstr.substring(1, altstr.length());

            posi = altstr.lastIndexOf("'");
            if (posi == altstr.length() - 1) altstr = altstr.substring(0, altstr.length() - 1);
            posi = altstr.lastIndexOf("\"");
            if (posi == altstr.length() - 1) altstr = altstr.substring(0, altstr.length() - 1);

            content = content + "alt=\"" + altstr + "\" ";
        } else {
            content = content + "alt=\"image\" ";
        }

        //获取align元素的内容
        buf = str.toLowerCase();
        posi = buf.indexOf("align");
        if (posi >=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                alignstr = buf.substring(0, posi);
            }
            else {
                alignstr = buf.substring(0, buf.length() - 1);
            }

            posi = alignstr.indexOf("\"");
            if (posi == 0) alignstr = alignstr.substring(1, alignstr.length());
            posi = alignstr.indexOf("'");
            if (posi == 0) alignstr = alignstr.substring(1, alignstr.length());

            posi = alignstr.lastIndexOf("'");
            if (posi == alignstr.length() - 1) alignstr = alignstr.substring(0, alignstr.length() - 1);
            posi = alignstr.lastIndexOf("\"");
            if (posi == alignstr.length() - 1) alignstr = alignstr.substring(0, alignstr.length() - 1);

            content = content + "align=\"" + alignstr + "\" ";
        }

        //获取height元素的内容
        buf = str.toLowerCase();
        posi = buf.indexOf("height");
        if (posi >=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                heightstr = buf.substring(0, posi);
            }
            else {
                heightstr = buf.substring(0, buf.length() - 1);
            }

            posi = heightstr.indexOf("\"");
            if (posi == 0) heightstr = heightstr.substring(1, heightstr.length());
            posi = heightstr.indexOf("'");
            if (posi == 0) heightstr = heightstr.substring(1, heightstr.length());

            posi = heightstr.lastIndexOf("'");
            if (posi == heightstr.length() - 1) heightstr = heightstr.substring(0, heightstr.length() - 1);
            posi = heightstr.lastIndexOf("\"");
            if (posi == heightstr.length() - 1) heightstr = heightstr.substring(0, heightstr.length() - 1);

            content = content + "height=\"" + heightstr + "\" ";
        }

        //获取width元素的内容
        buf = str.toLowerCase();
        posi = buf.indexOf("width");
        if (posi>=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                widthstr = buf.substring(0, posi);
            }
            else {
                widthstr = buf.substring(0, buf.length() - 1);
            }

            posi = widthstr.indexOf("\"");
            if (posi == 0) widthstr = widthstr.substring(1, widthstr.length());
            posi = widthstr.indexOf("'");
            if (posi == 0) widthstr = widthstr.substring(1, widthstr.length());

            posi = widthstr.lastIndexOf("'");
            if (posi == widthstr.length() - 1) widthstr = widthstr.substring(0, widthstr.length() - 1);
            posi = widthstr.lastIndexOf("\"");
            if (posi == widthstr.length() - 1) widthstr = widthstr.substring(0, widthstr.length() - 1);

            content = content + "width=\"" + widthstr + "\" ";
        }

        //获取vspace元素内容
        buf = str.toLowerCase();
        posi = buf.indexOf("vspace");
        if (posi>=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                vspacestr = buf.substring(0, posi);
            }
            else {
                vspacestr = buf.substring(0, buf.length() - 1);
            }

            posi = vspacestr.indexOf("\"");
            if (posi == 0) vspacestr = vspacestr.substring(1, vspacestr.length());
            posi = vspacestr.indexOf("'");
            if (posi == 0) vspacestr = vspacestr.substring(1, vspacestr.length());

            posi = vspacestr.lastIndexOf("'");
            if (posi == vspacestr.length() - 1) vspacestr = vspacestr.substring(0, vspacestr.length() - 1);
            posi = vspacestr.lastIndexOf("\"");
            if (posi == vspacestr.length() - 1) vspacestr = vspacestr.substring(0, vspacestr.length() - 1);

            content = content + "vspace=\"" + vspacestr + "\" ";
        }

        //获取hspace元素内容
        buf = str.toLowerCase();
        posi = buf.indexOf("hspace");
        if (posi>=0) {
            head_str = str.substring(0, posi);
            buf = str.substring(posi);
            posi = buf.indexOf("=");
            buf = buf.substring(posi + 1).trim();
            posi = buf.indexOf(" ");

            if (posi != -1) {
                hspacestr = buf.substring(0, posi);
            }
            else {
                hspacestr = buf.substring(0, buf.length() - 1);
            }

            posi = hspacestr.indexOf("\"");
            if (posi == 0) hspacestr = hspacestr.substring(1, hspacestr.length());
            posi = hspacestr.indexOf("'");
            if (posi == 0) hspacestr = hspacestr.substring(1, hspacestr.length());

            posi = hspacestr.lastIndexOf("'");
            if (posi == hspacestr.length() - 1) hspacestr = hspacestr.substring(0, hspacestr.length() - 1);
            posi = hspacestr.lastIndexOf("\"");
            if (posi == hspacestr.length() - 1) hspacestr = hspacestr.substring(0, hspacestr.length() - 1);

            content = content + "hspace=\"" + hspacestr + "\" ";
        }

        content = content +  "/>";

        return content;
    }
}
