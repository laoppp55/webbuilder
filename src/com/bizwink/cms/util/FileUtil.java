package com.bizwink.cms.util;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Bizwink Software Inc</p>
 *
 * @author Peter Song
 * @version 1.0
 */

public class FileUtil {
    public static List getFileListInPath(String path) {
        List list = new ArrayList();

        File file = new File(path);
        String[] fileList = file.list();

        if (fileList != null) {
            for (int i = 0; i < fileList.length; i++) {
                File dir = new File(path + fileList[i]);
                if (!dir.isDirectory())
                    list.add(fileList[i]);
            }
        }

        return list;
    }

    public static String readTableHead(int listType, String appPath, String sitename) {
        String result = null;

        File listTypeFile = new File(appPath + "sites" + java.io.File.separator + sitename + java.io.File.separator + "_sys_ListStyle" + java.io.File.separator + "list" + listType + "_table_head.html");
        int listTypeLength = (int) listTypeFile.length();
        char[] buf = new char[listTypeLength];
        StringBuffer tempBuf = new StringBuffer();

        try {
            FileReader listTypeFileIn = new FileReader(listTypeFile);
            int retChar = listTypeFileIn.read(buf, 0, listTypeLength);
            listTypeFileIn.close();

            tempBuf.insert(0, buf);
            result = tempBuf.toString();

        } catch (IOException e) {
            e.printStackTrace();
            result = "error";
        }

        return result;
    }

    public static String readTableTail(int listType, String appPath, String sitename) {
        String result = null;

        File listTypeFile = new File(appPath + "sites" + java.io.File.separator + sitename + java.io.File.separator + "_sys_ListStyle" + java.io.File.separator + "list" + listType + "_table_tail.html");
        int listTypeLength = (int) listTypeFile.length();
        char[] buf = new char[listTypeLength];
        StringBuffer tempBuf = new StringBuffer();

        try {
            FileReader listTypeFileIn = new FileReader(listTypeFile);
            int retChar = listTypeFileIn.read(buf, 0, listTypeLength);
            listTypeFileIn.close();

            tempBuf.insert(0, buf);
            result = tempBuf.toString();

        } catch (IOException e) {
            e.printStackTrace();
            result = "error";
        }

        return result;
    }

    public static List<String> readFileByEncoding(String path, String encoding) throws IOException {
        List<String> contents = new ArrayList();
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
        String line = null;
        while ((line = reader.readLine()) != null) {
            contents.add(line + "\r\n");
        }
        reader.close();
        return contents;
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> contents = new ArrayList();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                contents.add(tempString);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }

        return contents;
    }

    public static void writeTextFile(String content,String filename) {
        File f=new File(filename);//新建一个文件对象，如果不存在则创建一个该文件
        try {
            FileWriter fw=new FileWriter(f);
            fw.write(content);//将字符串写入到指定的路径下的文件中
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(StringBuffer buf,String filename) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(filename));
            output.write(buf.toString());
            output.flush();
            output.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }

    public static void appendWriteFile(StringBuffer buf,String filename) {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(filename,true));       //true,则追加写入text文本
            output.write(buf.toString());
            output.flush();
            output.close();
        } catch (IOException exp) {
            exp.printStackTrace();
        }
    }


}