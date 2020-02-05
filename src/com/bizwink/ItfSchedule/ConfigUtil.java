package com.bizwink.ItfSchedule;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * 读取配置文件itftask.properties
 * 
 * @author huasen.xu 2013-11-11 create
 */
public class ConfigUtil {
	private static Map<String,String> configMap = new HashMap<String, String>();

	static {
		init();
	}

	public static void init(){
		InputStream in = null;
		try {
			Properties config = new Properties();
			in = ConfigUtil.class.getClassLoader().getResourceAsStream("itftask.properties");
			config.load(in);
			for(Iterator<Object> it = config.keySet().iterator();it.hasNext();){
				String key = (String)it.next();
				String value = config.getProperty(key);
				configMap.put(key, value);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取配置文件失败!!");
		}finally{
			try {
				if (in!=null){
					in.close();
					in = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map<String, String> getConfigMap() {
		return configMap;
	}

	public static void main(String[] args) {
		for(Iterator<String> it = configMap.keySet().iterator();it.hasNext();){
			String key = it.next();
			String value = configMap.get(key);
			System.out.println(key+"   "+value);
		}
	}

	public static String getProperty(String key){
		return configMap.get(key);
	}

	/**
	 * 按接口类型从配置文件获取运行频次设置，
	 * 如果没有单独配置，则返回统一频次配置
	 * 
	 * @param itfType
	 * @return long
	 */
	public static long getPeriod(String itfType){
		String val = configMap.get(itfType +".period");
		if(val == null || val.trim().length() == 0)
			val = configMap.get("period");

		return Long.parseLong(val);
	}

	/**
	 * 按接口类型从配置文件获取一次读取记录条数，
	 * 如果没有单独配置，则返回统一条数配置
	 * 
	 * @param itfType
	 * @return int
	 */
	public static int getReadCount(String itfType){
		String val = configMap.get(itfType +".readCount");
		if(val == null || val.trim().length() == 0)
			val = configMap.get("readCount");

		return Integer.parseInt(val);
	}

	/**
	 * 按接口类型从配置文件获取一次发送记录条数，
	 * 如果没有单独配置，则返回统一条数配置
	 * 
	 * @param itfType
	 * @return int
	 */
	public static int getSendCount(String itfType){
		String val = configMap.get(itfType +".sendCount");
		if(val == null || val.trim().length() == 0)
			val = configMap.get("sendCount");

		return Integer.parseInt(val);
	}

}
