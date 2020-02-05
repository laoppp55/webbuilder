package com.heaton.bot;

import java.util.*;
import java.util.regex.*;

public class StringUtil
{
    public static String iso2gb(String str)
    {
        /*
        try
        {
          return new String(str.getBytes("iso-8859-1"), "GBK");
        }
        catch (Exception e)
        {
          return str;
        }
        */
        return str;
    }

    public static String iso2gbindb(String str)
    {
        try
        {
            return new String(str.getBytes("iso-8859-1"), "GBK");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String gb2isoindb(String str)
    {
        try
        {
            return new String(str.getBytes("GBK"), "iso-8859-1");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String gb2iso(String str)
    {
        try
        {
            return new String(str.getBytes("GBK"), "iso-8859-1");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String gb2iso4View(String str)
    {
        try
        {
            return new String(str.getBytes("iso-8859-1"), "GBK");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String gb2jis(String str)
    {
        try
        {
            return new String(str.getBytes(), "Shift_JIS");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String jis2gb(String str)
    {
        try
        {
            return new String(str.getBytes("Shift_JIS"), "GBK");
        }
        catch (Exception e)
        {
            return str;
        }
    }

    public static String toUtf8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static final String replace(String line, String oldString, String newString)
    {
        int i=0;
        if ( ( i=line.indexOf( oldString, i ) ) >= 0 )
        {
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while( ( i=line.indexOf( oldString, i ) ) > 0 )
            {
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            return buf.toString();
        }
        return line;
    }

    public static final String replace(String line, String oldString,String newString, int[] count)
    {
        if (line == null)
        {
            return null;
        }

        int i=0;
        if ((i=line.indexOf(oldString, i)) >= 0)
        {
            int counter = 0;
            counter++;
            char [] line2 = line.toCharArray();
            char [] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while( ( i=line.indexOf( oldString, i ) ) > 0 )
            {
                counter++;
                buf.append(line2, j, i-j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

    public static final String convJS(Object s)
    {
        if (s == null)
        {
            return "";
        }

        String t = (String)s;
        t = replaceJS(t,"\\","\\\\"); // replace backslash with \\
        t = replaceJS(t,"'","\\\'");  // replace an single quote with \'
        t = replaceJS(t,"\"","\\\""); // replace a double quote with \"
        t = replaceJS(t,"\r","\\r"); // replace CR with \r;
        t = replaceJS(t,"\n","\\n"); // replace LF with \n;
        t = gb2iso(t);
        return t;
    }

    public static final String convTemplate(Object s)
    {
        if (s == null)
        {
            return "";
        }

        String t = (String)s;
        t = replaceJS(t,"\\","\\\\"); // replace backslash with \\
        t = replaceJS(t,"'","\\\'");  // replace an single quote with \'
        t = replaceJS(t,"\"","\\\""); // replace a double quote with \"
        t = replaceJS(t,"\r","\\r"); // replace CR with \r;
        t = replaceJS(t,"\n","\\n"); // replace LF with \n;
        return t;
    }

    public static final String replaceJS(String s, String one, String another)
    {
        if (s.equals("")) return "";
        String res = "";
        int i = s.indexOf(one,0);
        int lastpos = 0;
        while (i != -1)
        {
            res += s.substring(lastpos,i) + another;
            lastpos = i + one.length();
            i = s.indexOf(one,lastpos);
        }
        res += s.substring(lastpos);  // the rest
        return res;
    }

    //获取标记之间的内容
    public static final List getContentByTag(String content,String startTag, String endTag)
    {
        List list = new ArrayList();
        String tempBuf = content.toLowerCase();
        String lowcaseStartTag = startTag.toLowerCase();
        String lowcaseEndTag = endTag.toLowerCase();
        String field = "";
        int num = 1;
        int posi = 0;
        int startTagPosi = 0;
        int endTagPosi = 0;
        int startTagLength = startTag.length();
        int endTagLength = endTag.length();

        startTagPosi = tempBuf.indexOf(lowcaseStartTag);
        while (startTagPosi != -1)
        {
            endTagPosi = tempBuf.indexOf(lowcaseEndTag);
            if (endTagPosi != -1) {
                field = content.substring(startTagPosi+startTag.length(),endTagPosi);
                list.add(field);
                tempBuf = tempBuf.substring(endTagPosi + endTagLength);
                content = content.substring(endTagPosi + endTagLength);
            }
            startTagPosi = tempBuf.indexOf(lowcaseStartTag);
        }

        return list;
    }

    public static final Hashtable getImageAttribute(String imgstr)
    {
        Hashtable hash = new Hashtable();

        if (imgstr != null && imgstr.trim().length() > 0)
        {
            //分析宽和高
            int width = 0;
            int height = 0;
            Pattern p = Pattern.compile("style(\\s*)=(\\s*)['\"]width(\\s*):(\\s*)[0-9]*px(\\s*);(\\s*)height(\\s*):(\\s*)[0-9]*px(\\s*)['\"]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(imgstr);
            if (m.find())
            {
                String style = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]*px", Pattern.CASE_INSENSITIVE);
                m = p.matcher(style);
                if (m.find())
                {
                    String widths = style.substring(m.start(), m.end());
                    width = Integer.parseInt(widths.substring(0,widths.indexOf("px")));
                }
                if (m.find())
                {
                    String heights = style.substring(m.start(), m.end());
                    height = Integer.parseInt(heights.substring(0,heights.indexOf("px")));
                }
            }
            else
            {
                String heights = "";
                p = Pattern.compile("height(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) heights = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
                m = p.matcher(heights);
                if (m.find()) height = Integer.parseInt(heights.substring(m.start()));

                String widths = "";
                p = Pattern.compile("width(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) widths = imgstr.substring(m.start(), m.end());
                p = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);
                m = p.matcher(widths);
                if (m.find()) width = Integer.parseInt(widths.substring(m.start()));
            }

            //分析src
            String src = "";
            p = Pattern.compile("src(\\s*)=(\\s*)['\"][^'\"]*(\\.gif|\\.jpg|\\.jpeg|\\.png|\\.bmp)['\"]", Pattern.CASE_INSENSITIVE);
            m = p.matcher(imgstr);
            if (m.find())
            {
                src = imgstr.substring(m.start(), m.end());
            }
            else
            {
                p = Pattern.compile("src(\\s*)=(\\s*)[^'\"]*(\\.gif|\\.jpg|\\.jpeg|\\.png|\\.bmp)", Pattern.CASE_INSENSITIVE);
                m = p.matcher(imgstr);
                if (m.find()) src = imgstr.substring(m.start(), m.end());
            }
            if (src != null && src.trim().length() > 0)
            {
                if (src.indexOf("\"") > -1)
                    src = src.substring(src.indexOf("\"") + 1, src.lastIndexOf("\""));
                else if (src.indexOf("'") > -1)
                    src = src.substring(src.indexOf("'") + 1, src.lastIndexOf("'"));
                else
                    src = src.substring(src.indexOf("=") + 1);
            }

            hash.put("width", String.valueOf(width));
            hash.put("height", String.valueOf(height));
            hash.put("alt", processStringAttr("alt", imgstr));                         //分析alt
            hash.put("src", processStringAttr("src", imgstr));                         //分析alt
            hash.put("align", processStringAttr("align", imgstr));                     //分析align
            hash.put("border", String.valueOf(processIntegerAttr("border", imgstr)));  //分析border
            hash.put("hspace", String.valueOf(processIntegerAttr("hspace", imgstr)));  //分析hspace
            hash.put("vspace", String.valueOf(processIntegerAttr("vspace", imgstr)));  //分析vspace
        }
        return hash;
    }

    private final static String processStringAttr(String attrName,String imgStr)
    {
        String attrValue = "";
        Pattern p = Pattern.compile(attrName + "(\\s*)=(\\s*)['\"][^'\"]*['\"]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(imgStr);
        if (m.find())
        {
            attrValue = imgStr.substring(m.start(), m.end());
        }
        else
        {
            p = Pattern.compile(attrName + "(\\s*)=(\\s*)[^>|\\s]*", Pattern.CASE_INSENSITIVE);
            m = p.matcher(imgStr);
            if (m.find()) attrValue = imgStr.substring(m.start(), m.end());
        }

        if (attrValue != null && attrValue.trim().length() > 0)
        {
            if (attrValue.indexOf("\"") > -1)
                attrValue = attrValue.substring(attrValue.indexOf("\"") + 1, attrValue.lastIndexOf("\""));
            else if (attrValue.indexOf("'") > -1)
                attrValue = attrValue.substring(attrValue.indexOf("'") + 1, attrValue.lastIndexOf("'"));
            else
                attrValue = attrValue.substring(attrValue.indexOf("=") + 1);
        }
        return attrValue;
    }

    private final static int processIntegerAttr(String attrName,String imgStr)
    {
        int attrValue = 0;
        Pattern p = Pattern.compile(attrName + "(\\s*)=(\\s*)[0-9]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(imgStr);
        if (m.find())
        {
            String temp = imgStr.substring(m.start(), m.end());
            if (temp.indexOf("\"") > -1)
                temp = temp.substring(temp.indexOf("\"") + 1, temp.lastIndexOf("\""));
            else if (temp.indexOf("'") > -1)
                temp = temp.substring(temp.indexOf("'") + 1, temp.lastIndexOf("'"));
            else
                temp = temp.substring(temp.indexOf("=") + 1);

            if (temp != null) attrValue = Integer.parseInt(temp);
        }
        return attrValue;
    }
}