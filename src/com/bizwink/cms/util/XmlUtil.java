package com.bizwink.cms.util;

import java.io.*;

public class XmlUtil {
  public static void createXMLFile(String dataPath, String nodename) {
    String docPath = dataPath.substring(0, dataPath.lastIndexOf(File.separator) + 1);
    File file = new File(docPath);
    if (!file.exists()) file.mkdirs();

    file = new File(dataPath);
    if (!file.exists()) {
      try {
        file.createNewFile();
        FileWriter fw = new FileWriter(dataPath);
        fw.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        if (nodename != null)
          fw.write("<" + nodename + " version=\"2.0\"/>");
        fw.close();
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
