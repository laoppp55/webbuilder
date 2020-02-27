package com.bizwink.util;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-12-5
 * Time: 19:57:04
 * To change this template use File | Settings | File Templates.
 */
public class filter {
    public static String excludeHTMLCode(String param ) {
        String content = param;

        if (content != null && content!="") {
            Pattern p = Pattern.compile("<script[^>]*>[\\d\\D]*?</script>", Pattern.CASE_INSENSITIVE);
            java.util.regex.Matcher matcher = p.matcher(content);
            String matchStr=null;
            String tbuf = content;
            while (matcher.find()) {
                matchStr = tbuf.substring(matcher.start(), matcher.end());
                content = content.replace(matchStr,"");
            }

            Pattern p1 = Pattern.compile("^((([hH][tT][tT][pP][sS]?|[fF][tT][pP])\\:\\/\\/)?([\\w\\.\\-]+(\\:[\\w\\.\\&%\\$\\-]+)*@)?((([^\\s\\(\\)\\<\\>\\\\\\\"\\.\\[\\]\\,@;:]+)(\\.[^\\s\\(\\)\\<\\>\\\\\\\"\\.\\[\\]\\,@;:]+)*(\\.[a-zA-Z]{2,4}))|((([01]?\\d{1,2}|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d{1,2}|2[0-4]\\d|25[0-5])))(\\b\\:(6553[0-5]|655[0-2]\\d|65[0-4]\\d{2}|6[0-4]\\d{3}|[1-5]\\d{4}|[1-9]\\d{0,3}|0)\\b)?((\\/[^\\/][\\w\\.\\,\\?\\'\\\\\\/\\+&%\\$#\\=~_\\-@]*)*[^\\.\\,\\?\\\"\\'\\(\\)\\[\\]!;<>{}\\s\\x7F-\\xFF])?)$", Pattern.CASE_INSENSITIVE);
            java.util.regex.Matcher matcher1 = p1.matcher(content);
            String matchStr1=null;
            String tbuf1 = content;
            while (matcher1.find()) {
                matchStr1 = tbuf1.substring(matcher1.start(), matcher1.end());
                if (!isEmail(matchStr1))
                    content = content.replace(matchStr1,"");
            }
        }

        return content;
    }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
