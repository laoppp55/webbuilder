package com.bizwink.util;


import java.util.Collection;
import java.util.Iterator;

public class StringUtil {

    public static boolean isNullOrEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	public static String toString(Object obj){
		if(obj == null) return "null";
		return obj.toString();
	}

	public static String join(Collection s, String delimiter) {
        StringBuffer buffer = new StringBuffer();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    public static final String replace(String line, String oldString, String newString) {
        int i = 0;
        if (line != null) {
            if ((i = line.indexOf(oldString, i)) >= 0) {
                char[] line2 = line.toCharArray();
                char[] newString2 = newString.toCharArray();
                int oLength = oldString.length();
                StringBuffer buf = new StringBuffer(line2.length);
                buf.append(line2, 0, i).append(newString2);
                i += oLength;
                int j = i;
                while ((i = line.indexOf(oldString, i)) > 0) {
                    buf.append(line2, j, i - j).append(newString2);
                    i += oLength;
                    j = i;
                }
                buf.append(line2, j, line2.length - j);
                return buf.toString();
            }
        }
        return line;
    }

    public static final String replace(String line, String oldString, String newString, int[] count) {
        if (line == null) {
            return null;
        }

        int i = 0;
        if ((i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0;
            counter++;
            char[] line2 = line.toCharArray();
            char[] newString2 = newString.toCharArray();
            int oLength = oldString.length();
            StringBuffer buf = new StringBuffer(line2.length);
            buf.append(line2, 0, i).append(newString2);
            i += oLength;
            int j = i;
            while ((i = line.indexOf(oldString, i)) > 0) {
                counter++;
                buf.append(line2, j, i - j).append(newString2);
                i += oLength;
                j = i;
            }
            buf.append(line2, j, line2.length - j);
            count[0] = counter;
            return buf.toString();
        }
        return line;
    }

}
