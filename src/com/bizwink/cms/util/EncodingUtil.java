package com.bizwink.cms.util;

/**
 * Title:        Software Engineer
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Bizwink Software Inc
 * @author       Peter Song
 * @version 1.0
 */

import java.lang.*;
import java.io.*;
import java.util.*;
import com.bizwink.cms.server.*;

public class EncodingUtil extends Encoding {

  // Simplfied/Traditional character equivalence hashes
  protected Hashtable s2thash, t2shash;

  public static EncodingUtil getInstance() {
	return (EncodingUtil)CmsServer.getInstance().getFactory().getEncodingUtil();
  }

  // Constructor
  public EncodingUtil() {
	super();
	String dataline;

	// Initialize and load in the simplified/traditional character hashses
	s2thash = new Hashtable();
	t2shash = new Hashtable();

	try {
      InputStream pydata = getClass().getResourceAsStream("hcutf8.txt");
	  BufferedReader in = new BufferedReader(new InputStreamReader(pydata, "UTF8"));
	  while ((dataline = in.readLine()) != null) {
		// Skip empty and commented lines
		if (dataline.length() == 0 || dataline.charAt(0) == '#') {
          continue;
		}

		// Simplified to Traditional, (one to many, but pick only one)
		s2thash.put(dataline.substring(0,1).intern(), dataline.substring(1,2));

		// Traditional to Simplified, (many to one)
		for (int i = 1; i < dataline.length(); i++) {
		  t2shash.put(dataline.substring(i,i+1).intern(), dataline.substring(0,1));
		}
      }
	}catch (Exception e) {
	  System.err.println(e);
	}
  }

  public String convertString(String dataline, int source_encoding, int target_encoding) {
	StringBuffer outline = new StringBuffer();
	int lineindex;

	if (source_encoding == HZ) {
      dataline = hz2gb(dataline);
	}
	for (lineindex = 0; lineindex < dataline.length(); lineindex++) {
      if ((source_encoding == GB2312 || source_encoding == UNICODES || source_encoding == ISO2022CN_GB ||
		 source_encoding == GBK || source_encoding == UNICODE || source_encoding == HZ) &&
		(target_encoding == BIG5 || target_encoding == CNS11643 || target_encoding == UNICODET ||
		 target_encoding == ISO2022CN_CNS)) {
        if (s2thash.containsKey(dataline.substring(lineindex, lineindex+1)) == true) {
    	  outline.append(s2thash.get(dataline.substring(lineindex, lineindex+1).intern()));
		}else {
		  outline.append(dataline.substring(lineindex, lineindex+1));
		}
      } else if ((source_encoding == BIG5 || source_encoding == CNS11643 || source_encoding == UNICODET ||
			source_encoding == ISO2022CN_CNS || source_encoding == GBK || source_encoding == UNICODE) &&
		       (target_encoding == GB2312 || target_encoding == UNICODES || target_encoding == ISO2022CN_GB ||
			target_encoding == HZ)) {
		if (t2shash.containsKey(dataline.substring(lineindex, lineindex+1)) == true) {
		  outline.append(t2shash.get(dataline.substring(lineindex, lineindex+1).intern()));
		} else {
		  outline.append(dataline.substring(lineindex, lineindex+1));
		}
      } else {
		outline.append(dataline.substring(lineindex, lineindex+1));
	  }
	}

	if (target_encoding == HZ) {
      // Convert to look like HZ
	  return gb2hz(outline.toString());
	}

	return outline.toString();
  }


  public String hz2gb(String hzstring) {
	byte[] hzbytes = new byte[2];
	byte[] gbchar = new byte[2];
	int byteindex = 0;
	StringBuffer gbstring = new StringBuffer("");

	try {
      hzbytes = hzstring.getBytes("8859_1");
	}catch (Exception usee) { System.err.println("Exception " + usee.toString()); return hzstring; }

	// Convert to look like equivalent Unicode of GB
	for (byteindex = 0; byteindex < hzbytes.length; byteindex++) {
      if (hzbytes[byteindex] == 0x7e) {
		if (hzbytes[byteindex+1] == 0x7b) {
          byteindex+=2;
		  while (byteindex < hzbytes.length) {
			if (hzbytes[byteindex] == 0x7e && hzbytes[byteindex+1] == 0x7d) {
              byteindex++;
			  break;
			} else if (hzbytes[byteindex] == 0x0a || hzbytes[byteindex] == 0x0d) {
			  gbstring.append((char)hzbytes[byteindex]);
			  break;
			}
			gbchar[0] = (byte)(hzbytes[byteindex] + 0x80);
			gbchar[1] = (byte)(hzbytes[byteindex+1] + 0x80);
			try {
			  gbstring.append(new String(gbchar, "GB2312"));
			}catch (Exception usee) { System.err.println("Exception " + usee.toString()); }
			byteindex+=2;
          }
		} else if (hzbytes[byteindex+1] == 0x7e) { // ~~ becomes ~
          gbstring.append('~');
		} else {  // false alarm
		  gbstring.append((char)hzbytes[byteindex]);
		}
      } else {
		gbstring.append((char)hzbytes[byteindex]);
	  }
	}
	return gbstring.toString();
  }

  public String gb2hz(String gbstring) {
	StringBuffer hzbuffer;
	byte[] gbbytes = new byte[2];
	int i;
	boolean terminated = false;

	hzbuffer = new StringBuffer("");
	try {
	  gbbytes = gbstring.getBytes("GB2312");
	}catch (Exception usee) { System.err.println(usee.toString()); return gbstring; }

	for (i = 0; i < gbbytes.length; i++) {
	  if (gbbytes[i] < 0) {
		hzbuffer.append("~{");
		terminated = false;
		while (i < gbbytes.length) {
		  if (gbbytes[i] == 0x0a || gbbytes[i] == 0x0d) {
			hzbuffer.append("~}" + (char)gbbytes[i]);
			terminated = true;
			break;
          } else if (gbbytes[i] >= 0) {
			hzbuffer.append("~}" + (char)gbbytes[i]);
			terminated = true;
			break;
		  }
		  hzbuffer.append((char)(gbbytes[i] + 256 - 0x80));
		  hzbuffer.append((char)(gbbytes[i+1] + 256 - 0x80));
		  i+=2;
		}
		if (terminated == false) {
		  hzbuffer.append("~}");
		}
      } else {
        if (gbbytes[i] == 0x7e) {
		  hzbuffer.append("~~");
		} else {
		  hzbuffer.append((char)gbbytes[i]);
		}
      }
	}
	return new String(hzbuffer);
  }


  public void convertFile(String sourcefile, String outfile, int source_encoding, int target_encoding) {
	BufferedReader srcbuffer;
	BufferedWriter outbuffer;
	String dataline;

	try {
	  srcbuffer = new BufferedReader(new InputStreamReader(new FileInputStream(sourcefile), javaname[source_encoding]));
	  outbuffer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile), javaname[target_encoding]));
	  while ((dataline = srcbuffer.readLine()) != null) {
		outbuffer.write(convertString(dataline, source_encoding, target_encoding));
		outbuffer.newLine();
	  }
	  srcbuffer.close();
	  outbuffer.close();
	}catch (Exception ex) {
	    System.err.println(ex);
	}
  }

  public static String transString(String words,  int source_encoding, int target_encoding) {
	String dataline, newWord = null;
    try {
      EncodingUtil encodingUtil = EncodingUtil.getInstance();
      newWord = encodingUtil.convertString(words,source_encoding,target_encoding);
    }catch (Exception ex) {
      System.err.println(ex);
	}
    return newWord;
  }
}