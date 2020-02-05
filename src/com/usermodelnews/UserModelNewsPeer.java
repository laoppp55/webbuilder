package com.usermodelnews;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.extendAttr.ExtendAttr;
import com.bizwink.cms.util.StringUtil;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import org.w3c.dom.Document;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.jdom.input.SAXBuilder;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-26
 * Time: 15:07:09
 * To change this template use File | Settings | File Templates.
 */
public class UserModelNewsPeer implements IUserModelNewsManager{
    PoolServer cpool;

    public UserModelNewsPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IUserModelNewsManager getInstance() {
        return CmsServer.getInstance().getFactory().getUserModelNewsManager();
    }
    public List getColumntemplatexml(String  xmltemplate)
    {
        List list=new ArrayList();
        SAXBuilder builder = new SAXBuilder();

        try {
            Reader in = new StringReader(xmltemplate);
            org.jdom.Document doc = builder.build(in);
            //System.out.println("gggggggggggggggg");
            List nodeList = doc.getRootElement().getChildren();
            for (int i = 0; i < nodeList.size(); i++) {
                org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                int type = Integer.parseInt(e.getAttributeValue("type"));
                // System.out.println(" type="+type);
                ExtendAttr extend = new ExtendAttr();
                extend.setCName(e.getText());
                extend.setEName(e.getName());
                extend.setControlType(type);
                extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));
                list.add(extend) ;
            }
        }catch(Exception e){
            System.out.println(""+e.toString());
        }
        return list;
    }
    //匹配摸版上的所有图片标记
    public List getImgAllContent(String content)
    {
        List list=new ArrayList();
        String str=content;
        str= StringUtil.replace(str,"[","<");
        str=StringUtil.replace(str,"]",">");
        if(content.indexOf("[TAGA][IMGZUHECONTENT]")!=-1){
            Pattern p = Pattern.compile("<TAGA><IMGZUHECONTENT>\\S*</IMGZUHECONTENT></TAGA>", Pattern.CASE_INSENSITIVE);
            // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            String srcstr = "";
            // System.out.println("content="+str);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                srcstr = content.substring(start, end);
                //          System.out.println("name="+srcstr);
                list.add(srcstr);
                // list.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                //    System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
            }
        }
        return list;
    }
    //匹配text文本字段
    public List getTextAllContent(String content)
    {
        List list=new ArrayList();
        String str=content;
        str= StringUtil.replace(str,"[","<");
        str=StringUtil.replace(str,"]",">");
        if(content.indexOf("[TAGA][TEXTZUHECONTENT]")!=-1){
            Pattern p = Pattern.compile("<TAGA><TEXTZUHECONTENT>\\S*</TEXTZUHECONTENT></TAGA>", Pattern.CASE_INSENSITIVE);
            // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(str);
            String srcstr = "";
            System.out.println("content="+str);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                srcstr = content.substring(start, end);
                System.out.println("name="+srcstr);
                list.add(srcstr);
                // list.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                //    System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
            }
        }
        return list;
    }
}
