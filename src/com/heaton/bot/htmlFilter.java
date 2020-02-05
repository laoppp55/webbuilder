package com.heaton.bot;

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

public class htmlFilter {
  String content;

  public htmlFilter(String htmlContent) {
    this.content = htmlContent;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String resultByFilter(String startStr,String endStr) {
    String result = this.content.toLowerCase();
    String lowcase_startStr = startStr.toLowerCase();
    String lowcase_endStr = endStr.toLowerCase();
    int num = 0;
    int posi = 0;

    while (result.indexOf(lowcase_endStr) != -1)
    {
      num = num +1;
      posi = result.indexOf(lowcase_endStr);
      result = result.substring(posi + lowcase_endStr.length());
    }

    //System.out.println("num=" + num);

    String[] buf = new String[num + 1];
    int startPosi = 0;
    int endPosi = 0;
    int i = 0;

    if (num > 0)
    {
      String tempBuf = this.content;                           //���ڽ�ȡ��Ϣ���ַ�
      String lowcase_content = this.content.toLowerCase();     //���ڻ�ȡλ����Ϣ���ַ�
      for (i=0; i<num; i++)
      {
        startPosi = lowcase_content.indexOf(lowcase_startStr);
        endPosi = lowcase_content.indexOf(lowcase_endStr);
        if (startPosi > -1)
          buf[i] = tempBuf.substring(0, startPosi).trim();
        tempBuf = tempBuf.substring(endPosi + lowcase_endStr.length()).trim();
        lowcase_content = lowcase_content.substring(endPosi + lowcase_endStr.length()).trim();
      }

      buf[i] = tempBuf;
      result = "";

      for (i=0; i<num; i++) {
        result = result + buf[i];
      }
      result = result + buf[i];
    }

    buf = null;

    return result;
  }
}