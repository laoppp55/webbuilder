package com.unittest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.model.PicturesTable;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFComment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class MSWordExtractor
{
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
                    OutputStream out = new FileOutputStream(new File(directory+ File.separator + fileName));
                    pic.writeImageContent(out);
                    characterRun.replaceText(characterRun.text(), fileName);
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
        String httpfile="http://bus.vodone.com:8080/ids/test.doc";
        MSWordExtractor mshttp=new MSWordExtractor();
        /*mshttp.initHttpExtractor(httpfile);
        mshttp.extractImages("C:\\");
        mshttp.destory();
        */
        try {
            XWPFDocument docx = new XWPFDocument(POIXMLDocument.openPackage("D:\\leads\\introduction.docx"));
            //org.apache.poi.xwpf.extractor.XWPFWordExtractor docx1 = new XWPFWordExtractor(POIXMLDocument.openPackage("D:\\leads\\introduction.docx"));
            org.apache.poi.xwpf.extractor.XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
            String text = extractor.getText();
            XWPFComment[] comments = docx.getComments();
            //org.apache.poi.xwpf.usermodel.XWPFComment[] comments;
        } catch (Exception exp) {

        }

        String localfile="D:\\leads\\WebBuilder.doc";
        MSWordExtractor lochttp=new MSWordExtractor();
        lochttp.initLocalExtractor(localfile);
        lochttp.extractImages("C:\\");
        mshttp.destory();
    }
}