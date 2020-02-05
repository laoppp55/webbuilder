package com.bizwink.cms.modelManager.history;

import java.io.*;
import java.util.*;
import java.text.DateFormat;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import com.bizwink.cms.modelManager.*;

public class ModelHistory {
    private String rootPath = "";

    public ModelHistory(String rootPath) {
        this.rootPath = rootPath;
    }

    public void create(Model model) {
        String fulldataPath = rootPath + "sites" + File.separator + model.getSiteName() + File.separator + "_model" + File.separator + model.getID() + File.separator;
        File file = new File(fulldataPath);
        if (!file.exists()) file.mkdirs();

        FileOutputStream fo = null;
        String dataPath = rootPath + "sites" + File.separator + model.getSiteName() + File.separator + "_model" + File.separator;

        IModelManager modelMgr = ModelPeer.getInstance();
        file = new File(dataPath);
        if (!file.exists()) file.mkdirs();

        dataPath += model.getID() + ".xml";
        file = new File(dataPath);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(dataPath);
                fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
                fw.write("<models/>");
                fw.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        SAXBuilder builder = new SAXBuilder();
        try {
            Document doc = builder.build(file);
            List nodeList = doc.getRootElement().getChildren();
            if (nodeList.size() > 30) nodeList.remove(0);

            String content = modelMgr.getModel(model.getID()).getContent();
            Element e = new Element("model");
            e.setAttribute("time", String.valueOf(System.currentTimeMillis()));
            e.setText(content);
            nodeList.add(e);

            XMLOutputter out = new XMLOutputter();
            fo = new FileOutputStream(dataPath);
            out.output(doc, fo);

            //写模板的全备份
            java.util.Date current=new java.util.Date();
            java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
            String c=sdf.format(current);
            //System.out.println(c);
            PrintWriter pw = new PrintWriter(new FileOutputStream(fulldataPath + c + ".html"));
            pw.write(content);
            pw.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                fo.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List getModelList(String sitename, int modelID) {
        List list = new ArrayList();
        SAXBuilder builder = new SAXBuilder();
        Element e = null;

        String dataPath = rootPath + "sites" + File.separator + sitename +
                File.separator + "_model" + File.separator;

        File file = new File(dataPath);
        if (!file.exists()) {
            file.mkdirs();
            return list;
        }

        dataPath += modelID + ".xml";
        file = new File(dataPath);
        if (!file.exists()) return list;

        try {
            Document doc = builder.build(file);
            List nodeList = doc.getRootElement().getChildren();
            for (int i = 0; i < nodeList.size(); i++) {
                e = (Element) nodeList.get(i);
                list.add(e.getAttributeValue("time"));
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public String getModel(int modelID, long time, String sitename) {
        String content = null;
        SAXBuilder builder = new SAXBuilder();
        Element e = null;
        String dataPath = rootPath + "sites" + File.separator + sitename +
                File.separator + "_model" + File.separator;

        File file = new File(dataPath);
        if (!file.exists()) {
            file.mkdirs();
            return content;
        }

        dataPath += modelID + ".xml";
        file = new File(dataPath);
        if (!file.exists()) return content;

        try {
            Document doc = builder.build(file);
            List nodeList = doc.getRootElement().getChildren();
            for (int i = 0; i < nodeList.size(); i++) {
                e = (Element) nodeList.get(i);
                if (Long.parseLong(e.getAttributeValue("time")) == time) {
                    content = e.getText();
                    break;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return content;
    }
}
