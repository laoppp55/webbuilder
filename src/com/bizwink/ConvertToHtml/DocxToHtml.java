package com.bizwink.ConvertToHtml;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bizwink.cms.util.StringUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.samples.AbstractSample;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.core.FileURIResolver;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;

public class DocxToHtml extends AbstractSample {
     /* @param fileName
     *            docx文件路径
     *            html输出文件路径
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public String docx2Html(String username,String sitename,int siteid,String baseDir,String fileDir,String fileName,int delflag) throws TransformerException, IOException, ParserConfigurationException {
        final String image_url = "/webbuilder/sites/" + sitename + fileDir + "images/";
        String local_filename = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator) + "download" + java.io.File.separator + fileName;
        String image_dir = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator) + "images" + java.io.File.separator;
        String html_dir = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator);

        //long startTime = System.currentTimeMillis();
        XWPFDocument document = new XWPFDocument(new FileInputStream(local_filename));
        XHTMLOptions options = XHTMLOptions.create().indent(4);

        // 导出图片
        File imageFolder = new File(image_dir);
        options.setExtractor(new FileImageExtractor(imageFolder));

        // URI resolver
        options.URIResolver(new FileURIResolver(imageFolder));
        int posi = fileName.lastIndexOf(".");
        String filename_no_extname = fileName.substring(0,posi);
        File outFile = new File(html_dir+filename_no_extname + ".html");
        outFile.getParentFile().mkdirs();
        OutputStream out = new FileOutputStream(outFile);
        XHTMLConverter.getInstance().convert(document, out, options);
        out.close();

        String buf = readFileByLines(html_dir+filename_no_extname + ".html","utf-8");
        buf = StringEscapeUtils.unescapeHtml(buf);

        //处理HTML字符串中的图片路径
        buf = ChangeImageLinkInContent(buf, baseDir);

        return buf;
        //System.out.println("Generate " + fileOutName + " with " + (System.currentTimeMillis() - startTime) + " ms.");
    }

    public String readFileByLines(String fileName,String charset) {
        FileInputStream file = null;
        BufferedReader reader = null;
        InputStreamReader inputFileReader = null;
        String content = "";
        String tempString = null;
        try {
            file = new FileInputStream(fileName);
            inputFileReader = new InputStreamReader(file, charset);
            reader = new BufferedReader(inputFileReader);
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                content += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return content;
    }

    //处理文章内容中的图片，图片不抓取到本地，将图片的链接替换成原来网站的绝对链接地址，原来的网站如果不能访问，则此链接地址也就不能访问
    private String ChangeImageLinkInContent(String content, String baseDir) {
        String buf = content;
        Pattern p = Pattern.compile("<img[^<>]*\\s+src(\\s*)=(\\s*)[^<>]*(.gif|.jpg|.jpeg|.bmp|.png)[^<>]*>", Pattern.CASE_INSENSITIVE);

        if (content!=null) {
            Matcher matcher = p.matcher(content);
            while (matcher.find()) {
                String src_url = content.substring(matcher.start(),matcher.end());
                int posi = src_url.toLowerCase().indexOf("src");
                if (posi > -1) {
                    src_url = src_url.substring(posi + "src".length());
                    posi = src_url.indexOf("=");
                    if (posi >-1) src_url = src_url.substring(posi+1);
                    posi = src_url.indexOf(" ");
                    if (posi > -1)
                        src_url = src_url.substring(0,posi);
                    else {
                        posi = src_url.indexOf(">");
                        if (posi>-1) src_url = src_url.substring(0,posi);
                    }
                }

                if (src_url.startsWith("\"") || src_url.startsWith("'")) src_url = src_url.substring(1);
                if (src_url.endsWith("\"") || src_url.endsWith("'")) src_url = src_url.substring(0,src_url.length()-1);

                String pic_url = src_url.replace(baseDir,"/webbuilder/");
                pic_url = pic_url.replace(File.separator,"/");
                buf = buf.replace(src_url,pic_url);
            }
        }

        return buf;
    }
}