package com.bizwink.cms.server;

import com.bizwink.cms.util.filter;

import java.io.*;
import java.util.*;
/**
 * <p>Title: cms</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: bizwink</p>
 * @author peter
 * @version 1.0
 */

public class FileProps {

    String filename;    //日志文件名
    Properties props;

    /**
     * 空构造方法。建立一个新的Properties.
     */
    public FileProps() {
        props = new Properties();
    }

    /**
     * 构造方法。打开日志文件。
     * @param filename 日志文件名
     */
    public FileProps(String filename) {
        this.filename = filename;
        props = new Properties();
        load();
    }

    /**
     * 装入属性文件。
     */
    public void load() {
        InputStream in = null;
        try {
            /*适用于java1.6以上版本*/
            in = this.getClass().getClassLoader().getResourceAsStream(filename);
            props.load(new InputStreamReader(in,"gbk"));
            //props.load(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("config.properties"), "UTF-8"));


            /*适用于java1.5版本*/
            //String filepath = this.getClass().getResource("").toString();
            //int posi = filepath.indexOf("file:/");
            //filepath = filepath.substring(posi + "file:/".length());
            //props.load(new FileInputStream( filepath + File.separator + "config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException ioexp) {
                ioexp.printStackTrace();
            }
        }

    }

    /**
     * 取得配置文件中的属性的值。
     * @param key 属性
     * @return String 属性值
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 设置配置文件中的属性的值。
     * @param key 属性
     * @param value 属性值
     */
    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    /**
     * 取得所有属性。
     * @return Enumeration 属性列表。
     */
    public Enumeration keys() {
        return props.keys();
    }
}