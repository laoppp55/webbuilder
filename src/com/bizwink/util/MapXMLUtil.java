package com.bizwink.util;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.*;

/**
 * Created by Administrator on 18-9-30.
 */
public class MapXMLUtil {
    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key: data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
            writer.close();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Document onedoc = null;
        Map<String, String> data = new HashMap<String, String>();

        try {
            // 读取并解析XML文档
            // SAXReader就是一个管道，用一个流的方式，把xml文件读出来
            //
            // SAXReader reader = new SAXReader(); //User.hbm.xml表示你要解析的xml文档
            // Document document = reader.read(new File("User.hbm.xml"));
            // 下面的是通过解析xml字符串的
            onedoc = DocumentHelper.parseText(strXML); // 将字符串转为XML
            Element rootElt = onedoc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
            List<Element> elements = new ArrayList();
            elements.add(rootElt);
            int itemnum = 1;
            while(elements.size()>0) {
                //取得需要处理的节点，节点数量减少1项，列表中移走被处理的节点
                itemnum = itemnum - 1;
                Element element = elements.get(0);
                elements.remove(0);

                String name = element.getName();
                String value = element.getText();
                //System.out.println("name:" + name + "==" + value);
                data.put(name, value);

                //获取某个节点下面的所有子节点
                Iterator iter = element.elementIterator();

                //遍历根节点下面的所有子节点
                while (iter.hasNext()) {
                    Element recordEle = (Element) iter.next();
                    elements.add(0,recordEle);
                    itemnum = itemnum + 1;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }

        return data;
    }

    public static void main(String[] args) throws Exception {
        String xmlStr="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<HotelGeoList>根节点" +
                "<HotelGeo Country=\"中国\" ProvinceName=\"重庆\" ProvinceId=\"0400\" CityName=\"开县（重庆）\" CityCode=\"0424\">希尔顿酒店" +
                "</HotelGeo>"+
                "<HotelGeo Country=\"中国\" ProvinceName=\"重庆\" ProvinceId=\"0400\" CityName=\"梁平（重庆）\" CityCode=\"0430\">喜来登酒店" +
                "<Districts>pppp" +
                "<Location Id=\"0001\" Name=\"梁平\">梁平001</Location>" +
                "</Districts>"+
                "</HotelGeo>"+
                "<HotelGeo Country=\"中国\" ProvinceName=\"重庆\" ProvinceId=\"0400\" CityName=\"南川（重庆）\" CityCode=\"0411\">洲际酒店"+
                "<Districts>nnnn"+
                "<Location Id=\"0001\" Name=\"南川区\">南川区0001</Location>"+
                "</Districts>"+
                "<CommericalLocations>mmmm"+
                "<Location Id=\"041102\" Name=\"金佛山\">金佛山041102</Location>"+
                "<Location Id=\"041101\" Name=\"南川城区\">南川城区041101</Location>"+
                "</CommericalLocations>"+
                "</HotelGeo>"+
                "</HotelGeoList>";


        xmlToMap(xmlStr);
    }
}
