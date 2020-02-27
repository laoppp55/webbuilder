package com.bizwink.util;

import java.io.*;
import java.util.*;

/**
 * <p>Title: cms</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2001</p>
 * <p>Company: bizwink</p>
 *
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
     *
     * @param filename 日志文件名
     */
    public FileProps(String filename) {
        this.filename = filename;
        props = new Properties();
        load();
    }

    /**
     * 打开日志文件。
     */
    public void load() {
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
            //FileInputStream in = new FileInputStream(filename);
            try {
                props.load(in);
            } finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得配置文件中的属性的值。
     *
     * @param key 属性
     * @return String 属性值
     */
    public String getProperty(String key) {
        return props.getProperty(key);
    }

    /**
     * 设置配置文件中的属性的值。
     *
     * @param key   属性
     * @param value 属性值
     */
    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    /**
     * 取得所有属性。
     *
     * @return Enumeration 属性列表。
     */
    public Enumeration keys() {
        return props.keys();
    }
}