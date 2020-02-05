package com.bizwink.cms.toolkit.survey;

import java.util.*;
import java.io.*;

import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//import org.apache.xerces.parsers.DOMParser;

public class SurveyPeer implements ISurveyManager
{
    //配置文件
    private static String filename = "com/bizwink/survey/config.properties";

    //输出XML
    private StringBuffer content = new StringBuffer();


    public static void SurverPeer() {

    }

    //创建XML文件type=1:创建调查2:创建调查的问题
    public void Create_XML(int type, Survey survey) throws SurveyException
    {
        try
        {
            Document doc =
                    (Document)Class.forName("org.apache.xerces.dom.DocumentImpl").newInstance();

            //读取原有XML文件
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            //创建调查
            if (type == 1)
            {
                //获得顶层元素
                Element root = doc.getDocumentElement();

                //顶层
                Element new_survey = doc.createElement("new-survey");
                //将新XML与旧XML连接起来
                root.appendChild(new_survey);

                //第一层
                Element ID = doc.createElement("ID");
                ID.appendChild(doc.createTextNode(Get_ID()));   //自增长ID
                new_survey.appendChild(ID);

                Element title = doc.createElement("title");
                String temp = new String(survey.getTitle().getBytes("iso-8859-1"),"GBK");
                title.appendChild(doc.createTextNode(temp));

                new_survey.appendChild(title);

                Element cate = doc.createElement("cate");
                cate.appendChild(doc.createTextNode(survey.getCate()));
                //cate.setAttribute("type", "单选");
                new_survey.appendChild(cate);

                Element desc = doc.createElement("desc");
                temp = new String(survey.getDesc().getBytes("iso-8859-1"),"GBK");
                desc.appendChild(doc.createTextNode(temp));
                new_survey.appendChild(desc);

                Element status = doc.createElement("status");
                status.appendChild(doc.createTextNode(survey.getStatus()));
                new_survey.appendChild(status);

                Element datetime = doc.createElement("datetime");
                datetime.appendChild(doc.createTextNode(survey.getDateTime()));
                new_survey.appendChild(datetime);

                Element item = doc.createElement("item");
                new_survey.appendChild(item);
            }
            //创建问题
            else if (type == 2)
            {
                //获得顶层元素
                NodeList nodeList = doc.getElementsByTagName("ID");
                NodeList itemList = doc.getElementsByTagName("item");

                int len = nodeList.getLength();
                for (int i=0; i<len; i++)
                {
                    String str = nodeList.item(i).getFirstChild().getNodeValue().trim();
                    if (str.compareTo(survey.getID().trim()) == 0)
                    {
                        Element answer = doc.createElement("answer");
                        String temp = new String(survey.getAnswer().getBytes("iso-8859-1"),"GBK");
                        answer.appendChild(doc.createTextNode(temp));
                        //将新XML与旧XML连接起来
                        itemList.item(i).appendChild(answer);

                        Element vote = doc.createElement("vote");
                        vote.appendChild(doc.createTextNode(survey.getVote()));
                        //将新XML与旧XML连接起来
                        itemList.item(i).appendChild(vote);

                        break;
                    }
                }
            }

            //调用Out_XML()方法得到XML源文件
            String content = Out_XML(doc);

            //将新生成的XML保存到硬盘
            PrintWriter pw = new PrintWriter(new FileOutputStream(Get_File()));
            pw.write(content);
            pw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //输出XML文件
    public String Out_XML(Node node) throws SurveyException
    {
        int type = node.getNodeType();

        switch (type)
        {
            // print the document element
            case Node.DOCUMENT_NODE:
            {
                content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                Out_XML(((Document)node).getDocumentElement());
                break;
            }

            // print element with attributes
            case Node.ELEMENT_NODE:
            {
                content.append("<");
                content.append(node.getNodeName());
                NamedNodeMap attrs = node.getAttributes();
                for (int i = 0; i < attrs.getLength(); i++)
                {
                    Node attr = attrs.item(i);
                    content.append(" " + attr.getNodeName() +
                            "=\"" + attr.getNodeValue() +
                            "\"");
                }
                content.append(">");

                NodeList children = node.getChildNodes();
                if (children != null)
                {
                    int len = children.getLength();
                    for (int i = 0; i < len; i++)
                        Out_XML(children.item(i));
                }

                break;
            }

            // handle entity reference nodes
            case Node.ENTITY_REFERENCE_NODE:
            {
                content.append("&");
                content.append(node.getNodeName());
                content.append(";");
                break;
            }

            // print cdata sections
            case Node.CDATA_SECTION_NODE:
            {
                content.append("<![CDATA[");
                content.append(node.getNodeValue());
                content.append("]]>");
                break;
            }

            // print text
            case Node.TEXT_NODE:
            {
                content.append(node.getNodeValue());
                break;
            }

            // print processing instruction
            case Node.PROCESSING_INSTRUCTION_NODE:
            {
                content.append("<?");
                content.append(node.getNodeName());
                String data = node.getNodeValue();
                {
                    content.append(" ");
                    content.append(data);
                }
                content.append("?>");
                break;
            }
        }

        if (type == Node.ELEMENT_NODE)
        {
            //content.append("\n");
            content.append("</");
            content.append(node.getNodeName());
            content.append('>');
        }

        return content.toString();
    }


    //读取XML文件
    public List Get_XML(int type, String ID) throws SurveyException
    {
        List list = new ArrayList();

        try
        {
            //读取原有XML文件
            Document doc = null;
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            NodeList nodeList = doc.getElementsByTagName("new-survey");

            for (int i=0; i<nodeList.getLength(); i++)
            {
                if (type == 1)
                {
                    NodeList temp = nodeList.item(i).getChildNodes();
                    Survey survey = new Survey();

                    survey.setID(temp.item(0).getFirstChild().getNodeValue().trim());
                    survey.setTitle(temp.item(1).getFirstChild().getNodeValue().trim());
                    survey.setCate(temp.item(2).getFirstChild().getNodeValue().trim());
                    survey.setDesc(temp.item(3).getFirstChild().getNodeValue().trim());
                    survey.setStatus(temp.item(4).getFirstChild().getNodeValue().trim());
                    survey.setDateTime(temp.item(5).getFirstChild().getNodeValue().trim());

                    list.add(survey);
                }
                else if (type == 2)
                {
                    String tempStr = nodeList.item(i).getChildNodes().
                            item(0).getFirstChild().getNodeValue().trim();

                    if (tempStr.compareTo(ID) == 0)
                    {
                        NodeList tlist =
                                nodeList.item(i).getChildNodes().item(6).getChildNodes();

                        for (int j=0; j<tlist.getLength(); j++)
                        {
                            if (tlist.item(j).hasChildNodes())
                            {
                                Survey survey = new Survey();
                                survey.setAnswer(tlist.item(j).getFirstChild().getNodeValue().trim());
                                list.add(survey);
                            }
                        }

                        break;
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }


    //读取指定的XML文件
    public List Get_ITEM_XML(String ID) throws SurveyException
    {
        List list = new ArrayList();

        try
        {
            //读取原有XML文件
            Document doc = null;
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();
            NodeList nodeList = doc.getElementsByTagName("new-survey");

            for (int i=0; i<nodeList.getLength(); i++)
            {
                String tempStr = nodeList.item(i).getChildNodes().
                        item(0).getFirstChild().getNodeValue().trim();
                if (tempStr.compareTo(ID) == 0)
                {
                    NodeList temp = nodeList.item(i).getChildNodes();
                    Survey survey = new Survey();

                    survey.setID(temp.item(0).getFirstChild().getNodeValue().trim());
                    survey.setTitle(temp.item(1).getFirstChild().getNodeValue().trim());
                    survey.setCate(temp.item(2).getFirstChild().getNodeValue().trim());
                    survey.setDesc(temp.item(3).getFirstChild().getNodeValue().trim());
                    survey.setStatus(temp.item(4).getFirstChild().getNodeValue().trim());
                    survey.setDateTime(temp.item(5).getFirstChild().getNodeValue().trim());

                    list.add(survey);
                    break;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return list;
    }


    //修改XML文件
    public void Update_XML(int type,Survey survey) throws SurveyException
    {
        try
        {
            Document doc = null;

            //读取原有XML文件
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            NodeList nodeList = doc.getElementsByTagName("new-survey");

            for (int i=0; i<nodeList.getLength(); i++)
            {
                if (type == 1)
                {
                    //修改survey
                    String tempStr = nodeList.item(i).getChildNodes().
                            item(0).getFirstChild().getNodeValue().trim();
                    if (tempStr.compareTo(survey.getID()) == 0)
                    {
                        NodeList temp = nodeList.item(i).getChildNodes();

                        temp.item(1).getFirstChild().setNodeValue
                                (new String(survey.getTitle().getBytes("iso-8859-1"),"GBK"));
                        temp.item(2).getFirstChild().setNodeValue
                                (new String(survey.getCate().getBytes("iso-8859-1"),"GBK"));
                        temp.item(3).getFirstChild().setNodeValue
                                (new String(survey.getDesc().getBytes("iso-8859-1"),"GBK"));
                        temp.item(4).getFirstChild().setNodeValue
                                (new String(survey.getStatus().getBytes("iso-8859-1"),"GBK"));

                        //调用Out_XML()方法得到XML源文件
                        String content = Out_XML(doc);

                        //将新生成的XML保存到硬盘
                        PrintWriter pw = new PrintWriter(new FileOutputStream(Get_File()));
                        pw.write(content);
                        pw.close();

                        break;
                    }
                }
                else if (type == 2)
                {
                    //修改answer
                    String tempStr = nodeList.item(i).getChildNodes().
                            item(0).getFirstChild().getNodeValue().trim();

                    if (tempStr.compareTo(survey.getID()) == 0)
                    {
                        NodeList temp = nodeList.item(i).getChildNodes().
                                item(6).getChildNodes();

                        for (int j=0; j<temp.getLength(); j++)
                        {
                            if (temp.item(j).hasChildNodes())
                            {
                                if (temp.item(j).getFirstChild().getNodeValue().trim().compareTo(survey.getTitle()) == 0)
                                {
                                    temp.item(j).getFirstChild().setNodeValue(survey.getAnswer().trim());

                                    //调用Out_XML()方法得到XML源文件
                                    String content = Out_XML(doc);

                                    //将新生成的XML保存到硬盘
                                    PrintWriter pw = new PrintWriter(new FileOutputStream(Get_File()));
                                    pw.write(content);
                                    pw.close();

                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //更新票数
    public void Update_Vote(String ID,String item[]) throws SurveyException
    {
        try
        {
            Document doc = null;

            //读取原有XML文件
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            //得到ID
            NodeList nodeList = doc.getElementsByTagName("ID");
            int i = 0;

            for (i=0; i<nodeList.getLength(); i++)
            {
                String tempStr = nodeList.item(i).getFirstChild().getNodeValue().trim();
                if (tempStr.compareTo(ID.trim()) == 0)   //判断ID是否相同
                {
                    break;
                }
            }

            //通过ID和Answer找到Vote
            nodeList = nodeList.item(i).getParentNode().getChildNodes().item(6).getChildNodes();

            for (int j=0; j<item.length; j++)
            {
                for (int k=0; k<nodeList.getLength(); k++)
                {
                    Node node = nodeList.item(k);
                    if (node.getNodeName().trim().compareTo("answer") == 0)
                    {
                        if (node.getFirstChild().getNodeValue().trim().compareTo(item[j].trim()) == 0)
                        {
                            int count = Integer.parseInt(nodeList.item(k+1).getFirstChild().getNodeValue().trim());
                            nodeList.item(k+1).getFirstChild().setNodeValue(String.valueOf(count + 1));
                        }
                    }
                }
            }

            //调用Out_XML()方法得到XML源文件
            String content = Out_XML(doc);

            //将新生成的XML保存到硬盘
            PrintWriter pw = new PrintWriter(new FileOutputStream(Get_File()));
            pw.write(content);
            pw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    //删除XML文件
    public void Delete_XML(int type,String ID,String answer) throws SurveyException
    {
        try
        {
            answer  = new String(answer.getBytes("iso-8859-1"), "GBK");

            //读取原有XML文件
            Document doc = null;
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            if (type == 1)      //删除调查
            {
                NodeList nodeList = doc.getElementsByTagName("new-survey");

                for (int i=0; i<nodeList.getLength(); i++)
                {
                    if (nodeList.item(i).getChildNodes().item(0).getFirstChild().getNodeValue().trim().compareTo(ID.trim()) == 0)
                    {
                        doc.getDocumentElement().removeChild(nodeList.item(i));
                        break;
                    }
                }
            }
            else if (type ==2)  //删除调查的问题
            {
                NodeList nodeList = doc.getElementsByTagName("new-survey");

                for (int i=0; i<nodeList.getLength(); i++)
                {
                    if (nodeList.item(i).getChildNodes().item(0).getFirstChild().getNodeValue().trim().compareTo(ID.trim()) == 0)
                    {
                        NodeList temp = nodeList.item(i).getChildNodes().item(6).getChildNodes();
                        //System.out.println(temp.getLength());

                        for (int j=0; j<temp.getLength(); j++)
                        {
                            if (temp.item(j).hasChildNodes())
                            {
                                String tempStr = temp.item(j).getFirstChild().getNodeValue().trim();

                                if (tempStr.trim().compareTo(answer.trim()) == 0)
                                {
                                    nodeList.item(i).getChildNodes().item(6).removeChild(temp.item(j));
                                    nodeList.item(i).getChildNodes().item(6).removeChild(temp.item(j));

                                    break;
                                }
                            }
                        }
                    }
                }
            }

            //调用Out_XML()方法得到XML源文件
            String content = Out_XML(doc);

            //将新生成的XML保存到硬盘
            PrintWriter pw = new PrintWriter(new FileOutputStream(Get_File()));
            pw.write(content);
            pw.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("delete survey is failed!");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("delete survey is failed!");
        }
    }


    //查询XML文件是否存在,如不存在,则创建初始文件
    public static boolean Query_File() throws SurveyException
    {
        boolean success = true;

        try
        {
            SurveyPeer sv = new SurveyPeer();
            File file = new File(sv.Get_File());

            if (!file.exists())
            {
                StringBuffer content = new StringBuffer();
                content.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
                content.append("<survey>\n\n").append("</survey>");

                PrintWriter pw = new PrintWriter(new FileOutputStream(sv.Get_File()));
                pw.write(content.toString());
                pw.close();

                success = true;
            }
        }
        catch (IOException e)
        {
            System.out.println("Generating XML File is failed!");
            e.printStackTrace();
            success = false;
        }
        catch (Exception e)
        {
            System.out.println("Generating XML File is failed!");
            e.printStackTrace();
            success = false;
        }

        if (success == true)
            return true;
        else
            return false;
    }

    //从配置文件config.properties中取得data.xml文件
    public String Get_File() throws SurveyException
    {
        String name = null;

        try
        {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename);
            Properties prop = new Properties();

            prop.load(is);
            name = prop.getProperty("FilePath");
        }
        catch (IOException e)
        {
            System.out.println("There is not config.properties or config is null!");
            e.printStackTrace();
        }
        catch (Exception e)
        {
            System.out.println("There is not config.properties or config is null!");
            e.printStackTrace();
        }
        return name;
    }

    //得到递增ID
    public String Get_ID() throws SurveyException
    {
        int count = 0;

        try
        {
            //读取原有XML文件
            Document doc = null;
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            NodeList nodeList = doc.getElementsByTagName("ID");
            int ID = nodeList.getLength() - 1;
            count = Integer.parseInt(nodeList.item(ID).getFirstChild().getNodeValue().trim());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return String.valueOf(count + 1);
    }


    //查询是否有指定的Title或Answer
    public boolean Query_Title(int type,String name,String ID) throws SurveyException
    {
        boolean isExit = false;

        try
        {
            name = new String(name.getBytes("iso-8859-1"), "GBK");

            //读取原有XML文件
            Document doc = null;
            DOMParser parser = new DOMParser();

            parser.parse(Get_File());
            doc = parser.getDocument();

            NodeList nodeList = null;
            if (type == 1)
            {
                nodeList = doc.getElementsByTagName("title");
            }
            else if (type == 2)
            {
                nodeList = doc.getElementsByTagName("answer");
            }

            int len = nodeList.getLength();

            for (int i=0; i<len; i++)
            {
                String str = nodeList.item(i).getFirstChild().getNodeValue().trim();
                if (type == 1)
                {
                    if (str.compareTo(name.trim()) == 0)
                    {
                        isExit = true;
                        break;
                    }
                }
                else if (type == 2)
                {
                    if (str.compareTo(name.trim()) == 0)
                    {
                        Node node = null;
                        NodeList temp = doc.getElementsByTagName("ID");

                        for (int j=0; j<temp.getLength(); j++)
                        {
                            if (temp.item(j).getFirstChild().getNodeValue().trim().compareTo(ID.trim()) == 0)
                                node = temp.item(j);
                        }

                        if (node.getParentNode().equals(nodeList.item(i).getParentNode().getParentNode()))
                        {
                            isExit = true;
                            break;
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return isExit;
    }

}
