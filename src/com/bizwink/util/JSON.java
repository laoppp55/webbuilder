package com.bizwink.util;

import com.esotericsoftware.reflectasm.MethodAccess;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Map.Entry;

public class JSON {
    public static String Encode(Object obj) {
        if(obj == null || obj.toString().equals("null")) return null;
        if(obj != null && obj.getClass() == String.class){
            return obj.toString();
        }
        JSONSerializer serializer = new JSONSerializer();
        serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Date.class);
        serializer.transform(new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"), Timestamp.class);
        return serializer.deepSerialize(obj);
    }

    public static Object Decode(String json) {
        if (StringUtil.isNullOrEmpty(json)) return "";
        JSONDeserializer deserializer = new JSONDeserializer();
        deserializer.use(String.class, new DateTransformer("yyyy-MM-dd'T'HH:mm:ss"));
        Object obj = deserializer.deserialize(json);
        if(obj != null && obj.getClass() == String.class){
            return Decode(obj.toString());
        }
        return obj;
    }

    public static void setPrintWriter(HttpServletResponse response,String json,String encoding) throws Exception
    {
        response.setContentType("text/html;charset="+encoding);
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }

    //生成JSON字符串
    public static String objects2ComboBox(Object[] objects,String methodGetId,String methodGetName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(objects.length<=0)
            return "";
        ArrayList data = new ArrayList();
        for (int i = 0; i < objects.length; i++)
        {
            if(objects[i]==null)
                continue;
//        	Method getId = objects[i].getClass().getMethod(methodGetId,new Class[0]);
//        	Method getName = objects[i].getClass().getMethod(methodGetName,new Class[0]);
//        	if(!getId.isAccessible())
//        		getId.setAccessible(true);
//        	if(!getName.isAccessible())
//        		getName.setAccessible(true);
//        	String id=String.valueOf(getId.invoke(objects[i],new Object[0]));
            //yaots 20140621up 采用reflectasm反射性能更高
            MethodAccess access  = MethodAccess.get(objects[i].getClass());
            String id=String.valueOf(access.invoke(objects[i], methodGetId));
            if(id.equals("0")||StringUtils.isEmpty(id))
                continue;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);
            map.put("text",String.valueOf(access.invoke(objects[i], methodGetName)));

            data.add(map);
        }
        return JSON.Encode(data);
    }

    public static String objects2ComboBox(List objects,String methodGetId,String methodGetName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(objects.size()<=0)
            return "";
        ArrayList data = new ArrayList();
        for (int i = 0; i < objects.size(); i++)
        {
            if(objects.get(i)==null)
                continue;
            MethodAccess access  = MethodAccess.get(objects.get(i).getClass());
            String id=String.valueOf(access.invoke(objects.get(i), methodGetId));
            if(id.equals("0")||StringUtils.isEmpty(id))
                continue;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);
            map.put("text",String.valueOf(access.invoke(objects.get(i), methodGetName)));
            data.add(map);
        }
        return JSON.Encode(data);
    }

    public static String objects2ComboBoxForRate(Object[] objects,String methodGetId,String methodGetName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(objects.length<=0)
            return "";
        ArrayList data = new ArrayList();
        for (int i = 0; i < objects.length; i++)
        {
            if(objects[i]==null)
                continue;
            MethodAccess access  = MethodAccess.get(objects[i].getClass());
            String id=String.valueOf(access.invoke(objects[i], methodGetId));
            if(id.equals("0")||StringUtils.isEmpty(id))
                continue;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);
            map.put("text",String.valueOf(access.invoke(objects[i], methodGetName))+"%");
            data.add(map);
        }
        return JSON.Encode(data);
    }

    //生成JSON字符串
    public static String objects2ComboBox(Object[] objects,String methodGetId,String methodGetName,boolean flag ) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(objects.length<=0)
            return "";
        ArrayList data = new ArrayList();
        for (int i = 0; i < objects.length; i++)
        {
            if(objects[i]==null)
                continue;
            MethodAccess access  = MethodAccess.get(objects[i].getClass());
            String id=String.valueOf(access.invoke(objects[i], methodGetId));
            if(id.equals("0")||StringUtils.isEmpty(id))
                continue;
            String name=String.valueOf(access.invoke(objects[i], methodGetName));
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",id);
            if(flag)
                map.put("text",id+'/'+name);
            else
                map.put("text",name);
            data.add(map);
        }
        return JSON.Encode(data);
    }

    //生成JSON字符串
    public static String objects2ComboBox(Map inputMap) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(inputMap==null)
            return "";
        ArrayList data = new ArrayList();
        Set set =inputMap.entrySet();
        Iterator it=set.iterator();
        while(it.hasNext()){
            Entry<String, String>  entry=(Entry<String, String>) it.next();
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id",String.valueOf(entry.getKey()));
            map.put("text",(String)entry.getValue());
            data.add(map);
        }
        return JSON.Encode(data);
    }

    //生成JSON字符串，采用自定义key
    public static String objects2ComboBox(Object[] objects,String methodGetId,String methodGetName,String key1, String key2) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException
    {
        if(objects.length<=0)
            return "";
        ArrayList data = new ArrayList();
        for (int i = 0; i < objects.length; i++)
        {
            if(objects[i]==null)
                continue;
            MethodAccess access  = MethodAccess.get(objects[i].getClass());
            String id=String.valueOf(access.invoke(objects[i], methodGetId));
            if(id.equals("0")||StringUtils.isEmpty(id))
                continue;
            HashMap<String, String> map = new HashMap<String, String>();
            map.put(key1, id);
            map.put(key2, String.valueOf(access.invoke(objects[i], methodGetName)));
            data.add(map);
        }
        return JSON.Encode(data);
    }
}

