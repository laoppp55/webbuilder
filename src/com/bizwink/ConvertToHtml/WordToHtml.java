package com.bizwink.ConvertToHtml;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 13-1-4
 * Time: 下午6:31
 * To change this template use File | Settings | File Templates.
 */

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.bizwink.cms.publish.IPublishManager;
import com.bizwink.cms.publish.PublishException;
import com.bizwink.cms.publish.PublishPeer;
import com.bizwink.cms.util.StringUtil;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.converter.WordToHtmlUtils;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.docx4j.convert.out.html.AbstractHtmlExporter;
import org.docx4j.convert.out.html.HtmlExporterNG2;
import org.docx4j.convert.out.html.SdtWriter;
import org.docx4j.convert.out.html.TagSingleBox;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WordToHtml {
    private HWPFDocument msWord;
    private HttpURLConnection connection;
    private InputStream inputStream;

    /*
   * 加载HTTP形式的Word文件
   *
   * */
    public void initHttpExtractor(String fileurl)
    {
        try
        {
            URL url= new URL(fileurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.connect();
            inputStream=connection.getInputStream();
            msWord = new HWPFDocument(inputStream);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*
   * 加载本地的Word文件
   *
   * */
    public void initLocalExtractor(String filepath)
    {
        try
        {
            inputStream = new FileInputStream(filepath);
            msWord = new HWPFDocument(inputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
   * 读取完word资源后，释放应该释放的对象
   *
   * */
    public void destory()
    {
        try
        {
            if(connection!=null)
            {
                connection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /*
   * 获取所有的段落文字
   *
   * */
    public String[] getParagraphTexts()
    {
        Range range = msWord.getRange();
        int numParagraph = range.numParagraphs();
        String[] paragraphs = new String[numParagraph];
        for (int i = 0; i < numParagraph; i++)
        {
            Paragraph p = range.getParagraph(i);
            paragraphs[i]= new String(p.text());
        }
        return paragraphs;
    }

    /*
   * 获取Word的所有文字
   *
   * */
    public String getMSWordText()
    {
        return msWord.getRange().text();
    }

    //将图片保存到指定的目录,并且将图片内容替换成图片的名字
    public void extractImages(String directory)
    {
        try
        {
            PicturesTable pTable = msWord.getPicturesTable();
            int numCharacterRuns = msWord.getRange().numCharacterRuns();
            for (int i = 0; i < numCharacterRuns; i++)
            {
                CharacterRun characterRun = msWord.getRange().getCharacterRun(i);
                if (pTable.hasPicture(characterRun))
                {
                    Picture pic = pTable.extractPicture(characterRun, false);
                    String fileName = pic.suggestFullFileName();
                    OutputStream out = new FileOutputStream(new File(directory+ File.separator + "images" + File.separator + fileName));
                    pic.writeImageContent(out);
                    characterRun.replaceText(characterRun.text(), "<img src=\"images/" + fileName + "\" />");
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String args[])
    {

        //出发啦旅行网.doc
        WordToHtml wordToHtml = new WordToHtml();
        String dirname = "D:\\sites\\bbn\\";
        String localfile="D:\\bizwink\\北京潮宏伟业科技有限公司需准备相关材料及盖章清单.doc";
        try {
            wordToHtml.convert2Html("petersong","www_test_com","d:\\sites\\test\\","/1/","北京潮宏伟业科技有限公司需准备相关材料及盖章清单.doc",7,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeFile(String content, String path) {
        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"GB2312"));
            bw.write(content);
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            } catch (IOException ie) {
            }
        }
    }

    //username：用户名
    //sitename：站点名称
    //baseDir：应用的基本路径
    //dirName：上传文件相对于基本路径的路径
    //filename：上传文件名称
    //siteID：站点ID
    //delfalg：是否删除上传文件
    public String convert2Html(String username,String sitename,String baseDir,String fileDir,String fileName,int siteid,int delflag) throws TransformerException, IOException, ParserConfigurationException {
        final String image_url = "/webbuilder/sites/" + sitename + fileDir + "images/";
        String local_filename = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator) + "download" + java.io.File.separator + fileName;
        String image_dir = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator) + "images" + java.io.File.separator;
        String html_dir = baseDir + "sites" + java.io.File.separator + sitename + StringUtil.replace(fileDir, "/", java.io.File.separator);

        File f = new File(image_dir);
        if (!f.exists()) f.mkdirs();
        f = new File(html_dir);
        if (!f.exists()) f.mkdirs();

        HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(local_filename));
        WordToHtmlUtils.loadDoc(new FileInputStream(local_filename));
        WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
        wordToHtmlConverter.setPicturesManager(new PicturesManager()
        {
            public String savePicture(byte[] content,PictureType pictureType, String suggestedName,float widthInches, float heightInches )
            {
                return image_url + suggestedName;
            }
        } );

        wordToHtmlConverter.processDocument(wordDocument);
        IPublishManager publishMgr = PublishPeer.getInstance();
        int errcode = 0;
        //save pictures
        List pics=wordDocument.getPicturesTable().getAllPictures();
        if(pics!=null){
            for(int i=0;i<pics.size();i++){
                Picture pic = (Picture)pics.get(i);
                try {
                    pic.writeImageContent(new FileOutputStream(image_dir + pic.suggestFullFileName()));
                    //int siteid, String fileDir, int big5flag
                    errcode = publishMgr.publish(username,image_dir + pic.suggestFullFileName(),siteid,fileDir+"images/",0);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (PublishException pe) {
                    pe.printStackTrace();
                }
            }
        }

        Document htmlDocument = wordToHtmlConverter.getDocument();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DOMSource domSource = new DOMSource(htmlDocument);
        StreamResult streamResult = new StreamResult(out);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer serializer = tf.newTransformer();
        serializer.setOutputProperty(OutputKeys.ENCODING, "gbk");
        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
        serializer.setOutputProperty(OutputKeys.METHOD, "html");
        serializer.transform(domSource, streamResult);
        String content_buf = new String(out.toByteArray());
        out.close();
        writeFile(content_buf,html_dir+fileName + ".html");
        System.out.println(html_dir+fileName + ".html");
        try {
            errcode = publishMgr.publish(username,html_dir+fileName + ".html",siteid,fileDir,0);
            errcode = publishMgr.publish(username,local_filename,siteid,fileDir+"download/",0);
        }catch(PublishException pubexp) {
            pubexp.printStackTrace();
        }

        return content_buf;
    }
}
