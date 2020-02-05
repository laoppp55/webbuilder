package com.bizwink.cms.xml;

import java.io.*;
import java.util.*;

import com.bizwink.cms.server.CmsServer;
import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import com.bizwink.cms.util.StringUtil;

public class XMLProperties {
    private File file;
    private Document doc;
    private Map propertyCache = new HashMap();

    public XMLProperties(String XMLString) {
        String tempBuf = StringUtil.replace(XMLString, "&nbsp;", " ");
        byte[] bytes = null;
        try {
            tempBuf = tempBuf.replace("<?xml version=\"1.0\"?>","<?xml version=\"1.0\" encoding=\"" + CmsServer.lang + "\"?>");
            bytes = tempBuf.getBytes();
            SAXBuilder builder = new SAXBuilder();
            DataUnformatFilter format = new DataUnformatFilter();
            builder.setXMLFilter(format);
            doc = builder.build(new ByteArrayInputStream(bytes));
            String name = doc.getRootElement().getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XMLProperties(String XMLString,int flag) {
        String tempBuf = StringUtil.replace(XMLString, "&nbsp;", " ");
        try {
            SAXBuilder builder = new SAXBuilder();
            Reader in= new StringReader(tempBuf);
            try {
                doc = builder.build(in);
                Element root = doc.getRootElement();
                //注意此处取出的是root节点下面的一层的Element集合
                List ls = root.getChildren();
                for (Iterator iter = ls.iterator(); iter.hasNext(); ) {
                    Element el = (Element) iter.next();
                }
            } catch (JDOMException ex) {
                ex.printStackTrace();
            }
        }
        catch (Exception e) {
            System.err.println("Error creating XML parser in " + "PropertyManager.java");
            e.printStackTrace();
        }
    }

    //注意此处取出的是root节点下面的一层的Element集合
    public List getFirstLevelChildrens() {
        Element root = doc.getRootElement();
        List ls = root.getChildren();
        return ls;
    }

    public String getProperty(String name) {
        if (propertyCache.containsKey(name)) {
            return (String) propertyCache.get(name);
        }

        String[] propName = parsePropertyName(name);
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            //System.out.println(propName[i]);
            element = element.getChild(propName[i]);
            if (element == null) {
                return null;
            }
        }
        String value = element.getText();
        if ("".equals(value)) {
            return null;
        } else {
            value = value.trim();
            propertyCache.put(name, value);
            return value;
        }
    }

    public String[] getChildrenProperties(String parent) {
        String[] propName = parsePropertyName(parent);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            element = element.getChild(propName[i]);
            if (element == null) {
                // This node doesn't match this part of the property name which
                // indicates this property doesn't exist so return empty array.
                return new String[]{};
            }
        }
        // We found matching property, return names of children.
        List children = element.getChildren();
        int childCount = children.size();
        String[] childrenNames = new String[childCount];
        for (int i = 0; i < childCount; i++) {
            childrenNames[i] = ((Element) children.get(i)).getName();
        }
        return childrenNames;
    }

    public void setProperty(String name, String value) {
        // Set cache correctly with prop name and value.
        propertyCache.put(name, value);

        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length; i++) {
            // If we don't find this part of the property in the XML heirarchy
            // we add it as a new node
            if (element.getChild(propName[i]) == null) {
                element.addContent(new Element(propName[i]));
            }
            element = element.getChild(propName[i]);
        }
        // Set the value of the property in this node.
        element.setText(value);
        // write the XML properties to disk
        saveProperties();
    }

    public void deleteProperty(String name) {
        String[] propName = parsePropertyName(name);
        // Search for this property by traversing down the XML heirarchy.
        Element element = doc.getRootElement();
        for (int i = 0; i < propName.length - 1; i++) {
            element = element.getChild(propName[i]);
            // Can't find the property so return.
            if (element == null) {
                return;
            }
        }
        // Found the correct element to remove, so remove it...
        element.removeChild(propName[propName.length - 1]);
        // .. then write to disk.
        saveProperties();
    }

    private synchronized void saveProperties() {
        OutputStream out = null;
        boolean error = false;
        // Write data out to a temporary file first.
        File tempFile = null;
        try {
            tempFile = new File(file.getParentFile(), file.getName() + ".tmp");
            // Use JDOM's XMLOutputter to do the writing and formatting. The
            // file should always come out pretty-printed.
            XMLOutputter outputter = new XMLOutputter();
            out = new BufferedOutputStream(new FileOutputStream(tempFile));
            outputter.output(doc, out);
        }
        catch (Exception e) {
            e.printStackTrace();
            // There were errors so abort replacing the old property file.
            error = true;
        }
        finally {
            try {
                out.close();
            }
            catch (Exception e) {
                e.printStackTrace();
                error = true;
            }
        }
        // No errors occured, so we should be safe in replacing the old
        if (!error) {
            // Delete the old file so we can replace it.
            file.delete();
            // Rename the temp file. The delete and rename won't be an
            // automic operation, but we should be pretty safe in general.
            // At the very least, the temp file should remain in some form.
            tempFile.renameTo(file);
        }
    }

    private String[] parsePropertyName(String name) {
        // Figure out the number of parts of the name (this becomes the size
        // of the resulting array).
        int size = 1;
        for (int i = 0; i < name.length(); i++) {
            if (name.charAt(i) == '.') {
                size++;
            }
        }
        String[] propName = new String[size];
        // Use a StringTokenizer to tokenize the property name.
        StringTokenizer tokenizer = new StringTokenizer(name, ".");
        int i = 0;
        while (tokenizer.hasMoreTokens()) {
            propName[i] = tokenizer.nextToken();
            i++;
        }
        return propName;
    }

    public String getName() {
        // Search for this property by traversing down the XML heirarchy
        if (doc !=null) {
            Element element = doc.getRootElement();
            return ((Element) element.getChildren().get(0)).getName();
        } else {
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        XMLProperties properties = new XMLProperties("<?xml version=\"1.0\" encoding=\"gbk\"?><TAG><ARTICLE_LIST><STARTARTNUM>0</STARTARTNUM><ENDARTNUM>0</ENDARTNUM><COLUMNS>一级注册建造师,二级注册建造师,一级注册造价师,二级注册造价师,其它,安全三类人员培训,现场管理人员培训,特种作业操作人员</COLUMNS><COLUMNIDS>(78,79,80,81,82,75,76,77)</COLUMNIDS><SELECTWAY>0</SELECTWAY><ARTICLENUM>20</ARTICLENUM><LISTTYPE>6</LISTTYPE><LETTERNUM>0</LETTERNUM><ORDER>1,0,0</ORDER><ORDER_RANGE><TIME_RANGE>,</TIME_RANGE><POWER_RANGE></POWER_RANGE><VICEPOWER_RANGE></VICEPOWER_RANGE><NUMBER_RANGE>,</NUMBER_RANGE></ORDER_RANGE><COLOR>0</COLOR><EXCLUDE>0</EXCLUDE><NEW>0</NEW><LINK><WAY>0</WAY></LINK><INNERHTMLFLAG>0</INNERHTMLFLAG><CHINESENAME>文章列表样式</CHINESENAME><NOTES></NOTES></ARTICLE_LIST></TAG>\n");
        System.out.println(properties.getName());
    }
}