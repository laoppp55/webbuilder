package com.heaton.bot;

import java.sql.*;
import java.io.*;

public class Server {

    private static String cfgpath = "/com/heaton/bot/config.cfg";

    //建立数据库的连接
    public static Connection createConnection() {
        Connection conn = null;
        try {
            String dbdriver = getParam("dbdriver");
            String dburl = getParam("dburl");
            String dbuser = getParam("dbuser");
            String dbpass = getParam("dbpass");

            //System.out.println("dbdriver=" + dbdriver);
            //System.out.println("dburl=" + dburl);
            //System.out.println("dbuser=" + dbuser);
            //System.out.println("dbpass=" + dbpass);

            Class.forName(dbdriver);
            conn = DriverManager.getConnection(dburl, dbuser, dbpass);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    //读取配置文件，获得参数值
    public static String getParam(String param) {

        String record;
        String paramval = "";
        String str;

        try {
            String a=Server.class.getClassLoader().getResource(".").getPath();
            FileReader fr = new FileReader(a + cfgpath);
            BufferedReader br = new BufferedReader(fr);
            while ((record = br.readLine()) != null) {
                str = record.substring(0, record.indexOf("="));
                if (str.equals(param)) {
                    paramval = record.substring(record.indexOf("=") + 1, record.length());
                    break;
                }
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramval;
    }

    public static String[] getParam() {

        String record;
        String paramval = "";
        String[] fieldarr = null;
        String str;

        try {
            FileReader fr = new FileReader(cfgpath);
            BufferedReader br = new BufferedReader(fr);
            while ((record = br.readLine()) != null) {
                str = record.substring(0, record.indexOf("="));
                if (str.equals("index_field")) {
                    paramval = record.substring(record.indexOf("=") + 1, record.length());
                    break;
                }
            }

            if (paramval != null) {
                fieldarr = paramval.split(",");
            } else {
                fieldarr = new String[1];
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fieldarr;
    }
}
