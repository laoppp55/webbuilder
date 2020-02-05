package com.extractText;

import com.bizwink.cms.util.SendMail;
import com.heaton.bot.HTTP;
import com.heaton.bot.HTTPSocket;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.*;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.*;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-4
 * Time: 20:21:15
 * To change this template use File | Settings | File Templates.
 */

public class extractTextFromHTML {
    protected static final String lineSign = System.getProperty("line.separator");
    protected static final int lineSign_size = lineSign.length();

    //public static final ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
    //     "newwatch/persistence.xml","newwatch/biz-util.xml", "newwatch/biz-dao.xml"
    //});

    public static String downloadPage(String path) throws IOException {
        String result = null;
        try{
            HTTP http=new HTTPSocket();
            http.send(path,null);
            result = http.getBody();
        }
        catch (IOException ioexp) {
            ioexp.printStackTrace();
        }

        return result;
    }

    public static void main(String args[])
    {
        extractTextFromHTML console = new extractTextFromHTML();
        ChannelLinkDO c = new ChannelLinkDO();
        c.setEncode("gb2312");
        c.setLink("http://news.sohu.com/20101206/n278125446.shtml");
        c.setLinktext("test");
        //HttpClient httpclient = new HttpClient();
        //GetMethod getMethod = new GetMethod("http://www.qiche.com.cn/files/200712/12016.shtml");
        try {
            String buf = extractTextFromHTML.downloadPage(c.getLink());
            //System.out.println(buf);
            PrintWriter pw = new PrintWriter(new FileOutputStream("c:\\test.html"));
            pw.write(buf);
            pw.close();
            console.makeContext(c,buf);
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    public void makeContext(ChannelLinkDO c,String buf) {
        String metakeywords = "<META content = {0} name=keywords>";
        String metatitle = "<TITLE>{0}</TITLE>";
        String metadesc = "<META content = {0} name=description>";
        String netshap = "<p> 正文快照：时间{0}</p>";
        String tempLeate = "<LI class=active><A href=\"{0}\" target=_blank>{1}</A></LI>";
        String crop = "<P><A href=\"{0}\" target=_blank>{1}</A></p>";

        try {
            String siteUrl = getLinkUrl(c.getLink());
            System.out.println(siteUrl);
            Parser parser = Parser.createParser(buf,"gb2312");
            //HtmlPage visitor = new HtmlPage(parser);
            //parser.visitAllNodesWith(visitor);

            //String textInPage = visitor.getTitle();
            //System.out.println(textInPage);

            for(NodeIterator e = parser.elements(); e.hasMoreNodes();) {
                Node node = (Node)e.nextNode();
                if (node instanceof Html) {
                    PageContext context = new PageContext();
                    context.setNumber(0);
                    context.setTextBuffer(new StringBuffer());
                    extractHtml(node,context,siteUrl);
                    System.out.println("test");
                }
            }

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    private String getLinkUrl(String link) {
        String urlDomainPattern = "(http://[^/]*?" + "/)(.*?)";
        Pattern pattern = Pattern.compile(urlDomainPattern,Pattern.CASE_INSENSITIVE + Pattern.DOTALL);
        Matcher matcher = pattern.matcher(link);
        String url = "";
        while(matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            url = link.substring(start,end-1).trim();
        }
        return url;
    }

    protected List extractHtml(Node nodep,PageContext context,String siteUrl) throws Exception{
        NodeList nodeList = nodep.getChildren();
        boolean bl = false;
        if (nodeList == null || nodeList.size() ==0) {
            if (nodep instanceof ParagraphTag) {
                ArrayList tableList = new ArrayList();
                StringBuffer temp = new StringBuffer();
                temp.append("<p style=\"TEXT-INDENT:2em\">");
                tableList.add(temp);
                temp = new StringBuffer();
                temp.append("</p>").append(lineSign);
                tableList.add(temp);
                return tableList;
            }
            return null;
        }

        if ((nodep instanceof TableTag) || (nodep instanceof Div)) {
            bl = true;
        }

        if (nodep instanceof ParagraphTag) {
            ArrayList tableList = new ArrayList();
            StringBuffer temp = new StringBuffer();
            temp.append("<p style=\"TEXT-INDENT:2em\">");
            tableList.add(temp);
            extractParagraph(nodep,siteUrl,tableList);
            temp = new StringBuffer();
            temp.append("</p>").append(lineSign);
            tableList.add(temp);
            return tableList;
        }

        ArrayList tableList = new ArrayList();
        try {
            for(NodeIterator e = nodeList.elements(); e.hasMoreNodes();) {
                Node node = (Node)e.nextNode();
                //System.out.println(node.toHtml());
                if (node instanceof LinkTag)  {
                    LinkTag link = (LinkTag)node;
                    System.out.println(link.getLinkText());
                    tableList.add(node);
                    setLinkImg(node,siteUrl);
                } else if (node instanceof ImageTag) {
                    ImageTag img = (ImageTag)node;
                    if (img.getImageURL().toLowerCase().indexOf("http://")<0) {
                        img.setImageURL(siteUrl + img.getImageURL());
                    } else {
                        img.setImageURL(img.getImageURL());
                    }
                    tableList.add(node);
                } else if (node instanceof StyleTag || node instanceof ScriptTag || node instanceof SelectTag) {

                } else if (node instanceof TextNode) {

                } else {
                    if (node instanceof TableTag || node instanceof Div) {

                    }

                    List tempList = extractHtml(node,context,siteUrl);
                    if ((tempList != null) && (tempList.size()>0 )) {
                        Iterator ti = tempList.iterator();
                        while(ti.hasNext()) {
                            tableList.add(ti.next());
                        }
                    }
                }
            }
        }catch(Exception e) {
            return null;
        }
        return null;
    }

    private void setLinkImg(Node nodep,String siteUrl) {

    }

    private List extractParagraph(Node nodep,String siteUrl,List tableList){
        NodeList nodeList = nodep.getChildren();
        if (nodeList == null || nodeList.size() ==0) {
            if (nodep instanceof ParagraphTag) {
                StringBuffer temp = new StringBuffer();
                temp.append("<p style=\"TEXT-INDENT:2em\">");
                tableList.add(temp);
                temp = new StringBuffer();
                temp.append("</p>").append(lineSign);
                tableList.add(temp);
                return tableList;
            }
            return null;
        }

        return null;
    }
}
