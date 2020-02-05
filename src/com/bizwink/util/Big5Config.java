package com.bizwink.util;

import java.io.BufferedReader;
import java.io.FileReader;

public class Big5Config
{
    private String cfgpath;

    public Big5Config()
    {
        this.cfgpath = "e:\\sites\\masterkong2007\\WEB-INF\\classes\\com\\bizwink\\util\\big5.properties";
    }

    public String getParam(String param)
    {
        String record = null;
        String paramval = "";
        String str = "";
        try
        {
            FileReader fr = new FileReader(this.cfgpath);
            BufferedReader br = new BufferedReader(fr);
            record = new String();
            do {
                if ((record = br.readLine()) == null)
                    break;
                str = record.substring(0, record.indexOf("="));
            } while (!str.equals(param));
            paramval = record.substring(record.indexOf("=") + 1, record.length());

            br.close();
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramval;
    }
}