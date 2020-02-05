package com.alipay.util;

import java.net.*;
import java.io.*;


public class CheckURL {
  /**
   * 对字符串进行MD5加密
   *
   * @return 获取url内容
   */
  public static String check(String urlvalue) {


    String inputLine = "";
    StringBuffer buf = new StringBuffer();

    try {
      URL url = new URL(urlvalue);
      HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

      while ((inputLine = in.readLine()) != null) {
        buf.append(inputLine);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    inputLine = buf.toString();
    inputLine = inputLine == null?"":inputLine.trim();
    System.out.println(inputLine);

    return inputLine;
  }


  }