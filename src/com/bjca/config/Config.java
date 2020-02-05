package com.bjca.config;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author sunhongbin
 * @version 1.0
 */

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static Properties prop;

    public Config() {
    }

    public static String getProperty(String name){
	if(prop == null)
	    load();
	return prop.getProperty(name);
    }

    private static void load() {
	prop = new Properties();
	try {
	    prop.load( (Config.class).
		getResourceAsStream("config.properties"));
	}
	catch (IOException ex) {
			ex.printStackTrace();
	}
    }

    public static void reload() {
	load();
    }

    public static void main (String args[]) {
	String jndi = getProperty("uamsdbsource_jndi");
	System.out.println("jndi:" + jndi);
    }



}
