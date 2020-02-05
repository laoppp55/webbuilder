package com.xml;


import java.io.*; //Java基础包，包含各种IO操作
import java.util.*; //Java基础包，包含各种标准数据结构操作
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.xml.parsers.*; //XML解析器接口

import org.w3c.dom.*; //XML的DOM实现
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-8
 * Time: 16:00:00
 * To change this template use File | Settings | File Templates.
 */
public class TreatmentXML {

    public boolean readXMLFile(String forms[], int siteid, String sitename, int templateid,String basedir) throws Exception {
        // IFormManager formpeer=FormPeer.getInstance();
        //   Form form=new Form();
        // int k=formpeer.createTable(form);
        //   System.out.println("cccccccccccccccc");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        boolean flag = false;
        List list = new ArrayList();
        List xmlpathlist = new ArrayList();
        try {
            String strxml = "";
            List namelist = new ArrayList();
            List valuelist = new ArrayList();
            List typelist = new ArrayList();
            List chinesenamelist=new ArrayList();
            List oldnamelist=new ArrayList();
            for (int i = 0; i < forms.length; i++) {
                //if(i==0){

                Pattern p = Pattern.compile("name=\"\\S*", Pattern.CASE_INSENSITIVE);
                // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(forms[i]);
                String srcstr = "";
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr = forms[i].substring(start, end);

                    namelist.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                    // System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }

                //如果没有value值 插入空
                p = Pattern.compile("<\\s*input[^<>]*>|<\\s*select[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(forms[i]);
                String srcstr1 = "";
                String srcstr2 = "";
                String srcstr3="";
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr1 = forms[i].substring(start, end);
                    if (srcstr1.indexOf("value") == -1) {
                        valuelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("value=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr1);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr1 = srcstr1.substring(start1, end1);
                            valuelist.add(srcstr1.substring(srcstr1.indexOf("\"") + 1, srcstr1.lastIndexOf("\"")));
                        }
                    }

                    if (srcstr3.indexOf("old") == -1) {
                        oldnamelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("old=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr1);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr3 = srcstr3.substring(start1, end1);
                            oldnamelist.add(srcstr3.substring(srcstr3.indexOf("\"") + 1, srcstr3.lastIndexOf("\"")));
                        }
                    }


                    if (srcstr2.indexOf("alt") == -1) {
                        chinesenamelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("alt=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr2);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr2 = srcstr2.substring(start1, end1);
                            chinesenamelist.add(srcstr2.substring(srcstr2.indexOf("\"") + 1, srcstr2.lastIndexOf("\"")));
                        }
                    }
                    //  System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }

                p = Pattern.compile("type=\"\\S*|<\\s*select[^<>]*>", Pattern.CASE_INSENSITIVE);
                // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(forms[i]);

                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr = forms[i].substring(start, end);

                    typelist.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                    //  System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }


                namelist.add("[form]");
                valuelist.add("[form]");
                typelist.add("[form]");
                chinesenamelist.add("[form]");
                oldnamelist.add("[form]");
                // System.out.println("forms="+forms[0]);
                // }
            }
            //html文件里面name属性
            /*FileOutputStream in=new FileOutputStream("");
           byte a[]= strxml.getBytes();
           in.write(a);

            in.close();  */
            //  System.out.println("namelistsize="+namelist.size());
            String xmlname = "";
            for (int i = 0; i < namelist.size(); i++) {
                String name = (String) namelist.get(i);
                String type = (String) typelist.get(i);
                String value = (String) valuelist.get(i);
                String chinesename=(String)chinesenamelist.get(i);
                if (!name.equals("[form]")) {
                    // System.out.println("name="+name);
                    if (name.equals("createform") && type.equals("hidden")) {
                        xmlname = "tbl_" + sitename + "_" + value;
                        /*  strxml="<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
                       strxml=strxml+"<data>\n";
                       strxml=strxml+"<table>tbl_"+sitename+"_"+value+"</table>\n";
                       strxml=strxml+"<classname>"+sitename+"_"+value+"</classname>\n";
                       strxml=strxml+"<fields>\n"; */
                    } else {
                        /* strxml=strxml+"<field>\n";
                       strxml=strxml+"<classproperty>"+name+"</classproperty>\n";
                       strxml=strxml+"<columnname>"+name+"</columnname>\n";
                       strxml=strxml+"<inputname>"+name+"</inputname>\n";
                       strxml=strxml+"<inputtype>"+type+"</inputtype>\n";
                       strxml=strxml+"<inputvalue>"+value+"</inputvalue>\n";
                       strxml=strxml+"<siteid>"+siteid+"</siteid>\n";
                       strxml=strxml+"<templateid>"+templateid+"</templateid>\n";
                       strxml=strxml+"</field>\n";
                     // System.out.println("ssssssssssssssssssssss");  */
                    }
                } else {
                    xmlpathlist.add(basedir+"\\sites\\"+sitename+"\\_prog\\" + xmlname + ".xml");
                }
            }
        } catch (Exception e) {

        }
        //循环路径查找xml文件
        for (int j = 0; j < xmlpathlist.size(); j++) {
            Form formtable = new Form();
            formtable.setCreateformname("[table]");
            list.add(formtable);
            String xmlpath = (String) xmlpathlist.get(j);
            //System.out.println("xmlpaht="+xmlpath);
            try {
                db = dbf.newDocumentBuilder();
            } catch (ParserConfigurationException pce) {
                System.err.println(pce);
                System.exit(1);
            }
            Document doc = null;
            try {
                doc = db.parse(xmlpath);
            }
            catch (DOMException dom) {
                System.err.println(dom.getMessage());
                System.exit(1);
            }
            catch (IOException ioe) {
                System.err.println(ioe);
                System.exit(1);
            }
            Element root = doc.getDocumentElement(); //root为"学生印花名"节点
            //读取表名
            NodeList table_names = root.getElementsByTagName("table");
            String table_name = "";

            for (int i = 0; i < table_names.getLength(); i++) {
                Element student = (Element) table_names.item(i);
                table_name = student.getChildNodes().item(0).getTextContent();
                //System.out.println("1="+student.getChildNodes().item(0).getTextContent());
            }
            //读取,fields下的属性
            NodeList students = root.getElementsByTagName("field"); //返回“学生”节点集合
            for (int i = 0; i < students.getLength(); i++) {
                // System.out.println("i="+i);
                Element student = (Element) students.item(i);
                Form form = new Form();
                form.setCreateformname(table_name);
                form.setColumnname(student.getChildNodes().item(1).getTextContent());
                form.setInputtype(student.getChildNodes().item(7).getTextContent());
                form.setInputvalue(student.getChildNodes().item(9).getTextContent());
                form.setSiteid(Integer.parseInt(student.getChildNodes().item(11).getTextContent()));
                form.setTemplateid(student.getChildNodes().item(13).getTextContent());
                form.setOldname(student.getChildNodes().item(17).getTextContent());
                //  System.out.println("000000000000"+student.getChildNodes().item(17).getTextContent());
                list.add(form);
                /* System.out.println("column="+student.getChildNodes().item(1).getTextContent());
                System.out.println("inputtype="+student.getChildNodes().item(7).getTextContent());
                System.out.println("inputvalue="+student.getChildNodes().item(9).getTextContent());
                System.out.println("siteid="+student.getChildNodes().item(11).getTextContent());
                System.out.println("templateid="+student.getChildNodes().item(13).getTextContent());*/

                // Process prcs=Runtime.getRuntime().exec("cmd.exe  /c   D:\\javac a.java");
                //System.out.println("==="+prcs.getInputStream());
                /*String in = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(prcs.getInputStream()));
                String temps = "";
                StringBuffer sb = new StringBuffer();
                while ( (temps = br.readLine()) != null) {
                    System.out.println("temp="+temps);
                    //sb.append(temp);
                }
                in = sb.toString();
                System.out.println(in);*/
                // student_vector.add(studentBean);
            }
        }
        IFormManager formpeer = FormPeer.getInstance();
        //System.out.println("bbbbbbbbbbbbb");


        Form form=new Form();
        form.setCreateformname("[table]");
        list.add(form);

        int k = formpeer.createTable(list);
        if (xmlpathlist.isEmpty()) {
            flag = true;
        } else {
            flag = false;
        }
        return flag;
    }

    public void createXML(String forms[], int siteid, String sitename, int templateid,String basedir) {
        try {
            String strxml = "";
            List namelist = new ArrayList();
            List valuelist = new ArrayList();
            List typelist = new ArrayList();
            List chinesenamelist=new ArrayList();
            List oldnamelist=new ArrayList();
            for (int i = 0; i < forms.length; i++) {
                Pattern p = Pattern.compile("name=\"\\S*", Pattern.CASE_INSENSITIVE);
                // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(forms[i]);
                String srcstr = "";
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr = forms[i].substring(start, end);
                    System.out.println("name="+srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                    namelist.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                    //    System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }

                //如果没有value值 插入空
                p = Pattern.compile("<\\s*input[^<>]*>|<\\s*select[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(forms[i]);
                String srcstr1 = "";
                String srcstr2 = "";
                String srcstr3="";
                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr1 = forms[i].substring(start, end);
                    srcstr2 = forms[i].substring(start, end);
                    srcstr3 = forms[i].substring(start, end);
                    if (srcstr1.indexOf("value") == -1) {
                        valuelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("value=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr1);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr1 = srcstr1.substring(start1, end1);
                            valuelist.add(srcstr1.substring(srcstr1.indexOf("\"") + 1, srcstr1.lastIndexOf("\"")));
                        }
                    }

                    if (srcstr3.indexOf("old") == -1) {
                        oldnamelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("old=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr1);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr3 = srcstr3.substring(start1, end1);
                            oldnamelist.add(srcstr3.substring(srcstr3.indexOf("\"") + 1, srcstr3.lastIndexOf("\"")));
                        }
                    }

                    if (srcstr2.indexOf("alt") == -1) {
                        chinesenamelist.add("");
                    } else {
                        //取 value值
                        Pattern p1 = Pattern.compile("alt=\"\\S*", Pattern.CASE_INSENSITIVE);
                        Matcher m1 = p1.matcher(srcstr2);
                        while (m1.find()) {
                            int start1 = m1.start();
                            int end1 = m1.end();
                            srcstr2 = srcstr2.substring(start1, end1);
                            chinesenamelist.add(srcstr2.substring(srcstr2.indexOf("\"") + 1, srcstr2.lastIndexOf("\"")));
                        }
                    }
                    //  System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }


                p = Pattern.compile("type=\"\\S*|<\\s*select[^<>]*>", Pattern.CASE_INSENSITIVE);
                // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(forms[i]);

                //Pattern   p2 = Pattern.compile("", Pattern.CASE_INSENSITIVE);
                // Pattern p = Pattern.compile("<\\s*input[^<>]*>", Pattern.CASE_INSENSITIVE);
                // Matcher   m2 = p2.matcher(forms[i]);

                /*    while (m2.find()) {
                 int start = m2.start();
                 int end = m2.end();
                 srcstr = forms[i].substring(start, end);
                    System.out.println("gggggggggggggggggggggggggggggggggg");
                    typelist.add("select");
                    valuelist.add("");
               //  System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
               } */

                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr = forms[i].substring(start, end);
                    typelist.add(srcstr.substring(srcstr.indexOf("\"") + 1, srcstr.lastIndexOf("\"")));
                    //  System.out.println("srcstr="+srcstr.substring(srcstr.indexOf("\"")+1,srcstr.lastIndexOf("\"")));
                }


                namelist.add("[form]");
                valuelist.add("[form]");
                typelist.add("[form]");
                chinesenamelist.add("[form]");
                oldnamelist.add("[form]");
                // System.out.println("forms="+forms[0]);
                // }
            }

            System.out.println("valuelist="+valuelist.size()+"   typelist="+typelist.size()+"   namelist="+namelist.size()+" chinesenamelist="+chinesenamelist.size()+"  oldnamelist="+oldnamelist.size());  //html文件里面name属性
            /*FileOutputStream in=new FileOutputStream("");
           byte a[]= strxml.getBytes();
           in.write(a);

            in.close();  */
            //  System.out.println("namelistsize="+namelist.size());
            String xmlname = "";
            for (int i = 0; i < namelist.size(); i++) {
                String name = (String) namelist.get(i);
                String type = (String) typelist.get(i);
                String value = (String) valuelist.get(i);
                String oldname= (String) oldnamelist.get(i);
                String chinesename=(String)chinesenamelist.get(i);
                System.out.println("name="+name);
                if (!name.equals("[form]")) {
                    if (name.equals("createform") && type.equals("hidden")) {
                        xmlname = "tbl_" + sitename + "_" + value;
                        strxml = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n";
                        strxml = strxml + "<data>\n";
                        strxml = strxml + "<table>tbl_" + sitename + "_" + value + "</table>\n";
                        strxml = strxml + "<classname>" + sitename + "_" + value + "</classname>\n";
                        strxml = strxml + "<fields>\n";
                    } else {
                        if (!name.equals("")) {
                            //System.out.println("name="+name+"=========");
                            strxml = strxml + "<field>\n";
                            strxml = strxml + "<classproperty>" + name + "</classproperty>\n";
                            strxml = strxml + "<columnname>" + name + "</columnname>\n";
                            strxml = strxml + "<inputname>" + name + "</inputname>\n";
                            strxml = strxml + "<inputtype>" + type + "</inputtype>\n";
                            strxml = strxml + "<inputvalue>" + value + "</inputvalue>\n";
                            strxml = strxml + "<siteid>" + siteid + "</siteid>\n";
                            strxml = strxml + "<templateid>" + templateid + "</templateid>\n";
                            strxml = strxml + "<chinesename>" +chinesename  + "</chinesename>\n";
                            strxml = strxml + "<oldcolumnname>"+oldname+"</oldcolumnname>";
                            strxml = strxml + "</field>\n";
                        }
                        // System.out.println("ssssssssssssssssssssss");
                    }
                } else {
                    strxml = strxml + "</fields></data>";
                    //System.out.println("xmlanme="+xmlname);
                    FileOutputStream in = new FileOutputStream(basedir+"\\sites\\"+sitename+"\\_prog\\" + xmlname + ".xml");
                    byte a[] = strxml.getBytes();
                    in.write(a);

                    in.close();
                    //System.out.println("strxml="+strxml);
                }

                // System.out.println("name="+name+"  type="+type+"    value="+value);

            }

            readXMLFile(forms, siteid, sitename, templateid,basedir);


        } catch (Exception e) {
            System.out.println("" + e.toString());
        }
    }

    /* public void writeXMLFile(String outFile)//throws Exception
   {
    DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
    DocumentBuilder db=null;
    try{
     db=dbf.newDocumentBuilder();
     }catch(Exception pce){
      System.err.println(pce);
      System.exit(1);
      }
    Document doc=db.newDocument();
    //在doc中创建"学生花名册"tag作为根节点
    Element root=doc.createElement("data");
    doc.appendChild(root);
    for(int i=0;i<student_vector.size();i++)
    {
     Form student=(Form)student_vector.get(i);
     Element studentNode=doc.createElement("fields");
     root.appendChild(studentNode);
     //studentNode.setAttribute("姓名", student.getName());
     //studentNode.setAttribute("性别", student.getSex());

     Element stuNameNode=doc.createElement("columnname");
     stuNameNode.appendChild(doc.createTextNode(student.getColumnname()));
     studentNode.appendChild(stuNameNode);

     Element stuSexNode=doc.createElement("inputname");
     stuSexNode.appendChild(doc.createTextNode(student.getInputname()));
     studentNode.appendChild(stuSexNode);
    }
    //用xmlserializer把document的内容进行串化
    FileOutputStream os=null;
    try{
     OutputFormat outformat=new OutputFormat(doc);
     os=new FileOutputStream("E:\\output.xml");
     XMLSerializer xmlSerilizer=new XMLSerializer(os,outformat);
     xmlSerilizer.serialize(doc);
     }catch(Exception e)
     {
      System.out.println("create xml failed...\n");
     }
    //功能同xmlSerializer，最后运行效果相同
    /*
    FileOutputStream outStream=null;
    try{
    outStream=new FileOutputStream(outFile);
    }catch(Exception e)
    {
     System.err.println(e);
     System.exit(1);
     }
    OutputStreamWriter outWriter=new OutputStreamWriter(outStream);
    //doc.write(outWriter,"GB2312");
    callWriteXMLFile(doc,outWriter,"GB2312");
    try{
    outWriter.close();
    outStream.close();
   }catch(Exception e)
   {
    System.err.println(e);
    System.exit(1);
    }
   }
   private void callWriteXMLFile(Document doc,OutputStreamWriter w,String encoding)
   {
   try{
      Source source=new DOMSource(doc);
      Result ret=new StreamResult(w);
      Transformer xformer=TransformerFactory.newInstance().newTransformer();
      xformer.setOutputProperty(OutputKeys.ENCODING,encoding);
      xformer.transform(source,ret);
      }catch(TransformerConfigurationException e)
      {
       e.printStackTrace();
       }
      catch(TransformerException e)
      {
       e.printStackTrace();
       }
   }
   } */
    public List getForm(String path) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        List list = new ArrayList();
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException pce) {
            System.err.println(pce);
            System.exit(1);
        }
        Document doc = null;
        try {
            doc = db.parse(path);
        }
        catch (DOMException dom) {
            System.err.println(dom.getMessage());
            System.exit(1);
        }
        catch (IOException ioe) {
            System.err.println(ioe);
            System.exit(1);
        }
        Element root = doc.getDocumentElement(); //root为"学生印花名"节点
        //读取表名
        NodeList table_names = root.getElementsByTagName("table");
        String table_name = "";

        for (int i = 0; i < table_names.getLength(); i++) {
            Element student = (Element) table_names.item(i);
            table_name = student.getChildNodes().item(0).getTextContent();
            //System.out.println("1="+student.getChildNodes().item(0).getTextContent());
        }
        //读取,fields下的属性
        NodeList students = root.getElementsByTagName("field"); //返回“学生”节点集合
        for (int i = 0; i < students.getLength(); i++) {
            // System.out.println("i="+i);
            Element student = (Element) students.item(i);
            Form form = new Form();
            form.setCreateformname(table_name);
            form.setColumnname(student.getChildNodes().item(1).getTextContent());
            form.setInputname(student.getChildNodes().item(1).getTextContent());
            form.setInputtype(student.getChildNodes().item(7).getTextContent());
            form.setInputvalue(student.getChildNodes().item(9).getTextContent());
            form.setSiteid(Integer.parseInt(student.getChildNodes().item(11).getTextContent()));
            form.setTemplateid(student.getChildNodes().item(13).getTextContent());
            form.setChinesename(student.getChildNodes().item(15).getTextContent());
            list.add(form);
            /* System.out.println("column="+student.getChildNodes().item(1).getTextContent());
            System.out.println("inputtype="+student.getChildNodes().item(7).getTextContent());
            System.out.println("inputvalue="+student.getChildNodes().item(9).getTextContent());
            System.out.println("siteid="+student.getChildNodes().item(11).getTextContent());
            System.out.println("templateid="+student.getChildNodes().item(13).getTextContent());*/

            // Process prcs=Runtime.getRuntime().exec("cmd.exe  /c   D:\\javac a.java");
            //System.out.println("==="+prcs.getInputStream());
            /*String in = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(prcs.getInputStream()));
            String temps = "";
            StringBuffer sb = new StringBuffer();
            while ( (temps = br.readLine()) != null) {
                System.out.println("temp="+temps);
                //sb.append(temp);
            }
            in = sb.toString();
            System.out.println(in);*/
            // student_vector.add(studentBean);
        }


        return list;
    }

}
