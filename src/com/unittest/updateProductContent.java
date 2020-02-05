package com.unittest;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.IArticleManager;
import com.bizwink.cms.news.ArticlePeer;

import java.sql.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Created by IntelliJ IDEA.
 * User: Du Zhenqiang
 * Date: 2008-2-28
 * Time: 13:24:59
 * 过滤产品内容中的word格式
 */
public class updateProductContent {

    public updateProductContent(){

    }

    public void parseHtml(int articleId) {
        String parseResult;

        try {
            IArticleManager articleMgr = ArticlePeer.getInstance();
            Article article = articleMgr.getArticle(articleId);
            parseResult = parseTag(article.getContent());
            parseResult = replaceSymbol(parseResult);

            try {
                articleMgr.updateArticleContent(articleId, parseResult);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String replaceSymbol(String content) {
        content = content.replaceAll("～", "~");
        content = content.replaceAll("＜", "<");
        content = content.replaceAll("－", "-");
        content = content.replaceAll("＝", "=");
        content = content.replaceAll("Ⅰ", "I");
        content = content.replaceAll("Ⅱ", "II");
        content = content.replaceAll("Ⅲ", "III");
        content = content.replaceAll("Ⅳ", "IV");
        content = content.replaceAll("Ⅴ", "V");
        content = content.replaceAll("Ⅵ", "VI");
        content = content.replaceAll("℃", "&ordm;C");
        content = content.replaceAll("Ａ", "A");
        content = content.replaceAll("Ｂ", "B");
        content = content.replaceAll("Ｃ", "C");
        content = content.replaceAll("Ｄ", "D");
        content = content.replaceAll("Ｅ", "E");
        content = content.replaceAll("Ｆ", "F");
        content = content.replaceAll("Ｇ", "G");
        content = content.replaceAll("Ｈ", "H");
        content = content.replaceAll("Ｉ", "I");
        content = content.replaceAll("Ｊ", "J");
        content = content.replaceAll("Ｋ", "K");
        content = content.replaceAll("Ｌ", "L");
        content = content.replaceAll("Ｍ", "M");
        content = content.replaceAll("Ｎ", "N");
        content = content.replaceAll("Ｏ", "O");
        content = content.replaceAll("Ｐ", "P");
        content = content.replaceAll("Ｑ", "Q");
        content = content.replaceAll("Ｒ", "R");
        content = content.replaceAll("Ｓ", "S");
        content = content.replaceAll("Ｔ", "T");
        content = content.replaceAll("Ｕ", "U");
        content = content.replaceAll("Ｖ", "V");
        content = content.replaceAll("Ｗ", "W");
        content = content.replaceAll("Ｘ", "X");
        content = content.replaceAll("Ｙ", "Y");
        content = content.replaceAll("Ｚ", "Z");
        content = content.replaceAll("ａ", "a");
        content = content.replaceAll("ｂ", "b");
        content = content.replaceAll("ｃ", "c");
        content = content.replaceAll("ｄ", "d");
        content = content.replaceAll("ｅ", "e");
        content = content.replaceAll("ｆ", "f");
        content = content.replaceAll("ｇ", "g");
        content = content.replaceAll("ｈ", "h");
        content = content.replaceAll("ｉ", "i");
        content = content.replaceAll("ｊ", "j");
        content = content.replaceAll("ｋ", "k");
        content = content.replaceAll("ｌ", "l");
        content = content.replaceAll("ｍ", "m");
        content = content.replaceAll("ｎ", "n");
        content = content.replaceAll("ｏ", "o");
        content = content.replaceAll("ｐ", "p");
        content = content.replaceAll("ｑ", "q");
        content = content.replaceAll("ｒ", "r");
        content = content.replaceAll("ｓ", "s");
        content = content.replaceAll("ｔ", "t");
        content = content.replaceAll("ｕ", "u");
        content = content.replaceAll("ｖ", "v");
        content = content.replaceAll("ｗ", "w");
        content = content.replaceAll("ｘ", "x");
        content = content.replaceAll("ｙ", "y");
        content = content.replaceAll("ｚ", "z");
        content = content.replaceAll("１", "1");
        content = content.replaceAll("２", "2");
        content = content.replaceAll("３", "3");
        content = content.replaceAll("４", "4");
        content = content.replaceAll("５", "5");
        content = content.replaceAll("６", "6");
        content = content.replaceAll("７", "7");
        content = content.replaceAll("８", "8");
        content = content.replaceAll("９", "9");
        content = content.replaceAll("０", "0");
        content = content.replaceAll("。", ".");
        content = content.replaceAll("，", ",");
        content = content.replaceAll("“", "\"");
        content = content.replaceAll("”", "\"");
        content = content.replaceAll("“", "\"");
        content = content.replaceAll("”", "\"");
        content = content.replaceAll("：", ":");
        content = content.replaceAll("：", ":");
        content = content.replaceAll("（", "(");
        content = content.replaceAll("）", ")");
        content = content.replaceAll("（", "(");
        content = content.replaceAll("）", ")");
        content = content.replaceAll("＠", "@");
        content = content.replaceAll("＃", "#");
        content = content.replaceAll("￥", "$");
        content = content.replaceAll("％", "%");
        content = content.replaceAll("%", "%");
        content = content.replaceAll("＆", "&");
        content = content.replaceAll("×", "*");
        content = content.replaceAll("～", "~");
        content = content.replaceAll("＝", "=");
        content = content.replaceAll("？", "?");
        content = content.replaceAll("●", "&nbsp;&nbsp;&nbsp;&nbsp;&middot;&nbsp;&nbsp;");

        return content;
    }

    private static String parseTag(String inputHtml) {
        inputHtml = inputHtml.replaceAll("table", "TABLE");
        if (inputHtml.indexOf("<TABLE") != -1) {
            if (inputHtml != null) {
                String inputHtml_H = inputHtml.substring(0, inputHtml.indexOf("<TABLE"));
                String inputHtml_B = inputHtml.substring(inputHtml.indexOf("<TABLE"), inputHtml.lastIndexOf("</TABLE>") + 8);
                String inputHtml_T = inputHtml.substring(inputHtml.lastIndexOf("</TABLE>") + 8);

                Pattern p = Pattern.compile("<font(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(inputHtml_H);
                inputHtml_H = m.replaceAll("");
                m = p.matcher(inputHtml_T);
                inputHtml_T = m.replaceAll("");
                m = p.matcher(inputHtml_B);
                inputHtml_B = m.replaceAll("");

                p = Pattern.compile("</font>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(inputHtml_H);
                inputHtml_H = m.replaceAll("");
                m = p.matcher(inputHtml_T);
                inputHtml_T = m.replaceAll("");
                m = p.matcher(inputHtml_B);
                inputHtml_B = m.replaceAll("");

                p = Pattern.compile("<a href=(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(inputHtml_H);
                inputHtml_H = m.replaceAll("");
                m = p.matcher(inputHtml_T);
                inputHtml_T = m.replaceAll("");
                m = p.matcher(inputHtml_B);
                inputHtml_B = m.replaceAll("");

                p = Pattern.compile("</a>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(inputHtml_H);
                inputHtml_H = m.replaceAll("");
                m = p.matcher(inputHtml_T);
                inputHtml_T = m.replaceAll("");
                m = p.matcher(inputHtml_B);
                inputHtml_B = m.replaceAll("");

                /*inputHtml_H = inputHtml_H.replaceAll("&nbsp;", " ");
                while (inputHtml_H.indexOf("     ") != -1) {
                    inputHtml_H = inputHtml_H.replaceAll("     ", "    ");
                }
                inputHtml_H = inputHtml_H.replaceAll("    ", "　　");

                inputHtml_T = inputHtml_T.replaceAll("&nbsp;", " ");
                while (inputHtml_T.indexOf("     ") != -1) {
                    inputHtml_T = inputHtml_T.replaceAll("     ", "    ");
                }
                inputHtml_T = inputHtml_T.replaceAll("    ", "　　");*/

                inputHtml_B = inputHtml_B.replaceAll("<table", "<table class=product align=center");
                inputHtml_B = inputHtml_B.replaceAll("<td", "<td class=product");
                inputHtml_B = inputHtml_B.replaceAll("<TABLE", "<table class=product align=center");
                inputHtml_B = inputHtml_B.replaceAll("<TD", "<td class=product");
                inputHtml_B = inputHtml_B.replaceAll("<o:p>", "");
                inputHtml_B = inputHtml_B.replaceAll("</o:p>", "");

                inputHtml_H = inputHtml_H.replaceAll("<P", "<P class=product");
                inputHtml_H = inputHtml_H.replaceAll("<p", "<p class=product");
                inputHtml_H = inputHtml_H.replaceAll("<o:p>", "");
                inputHtml_H = inputHtml_H.replaceAll("</o:p>", "");

                inputHtml_T = inputHtml_T.replaceAll("<P", "<P class=product");
                inputHtml_T = inputHtml_T.replaceAll("<p", "<p class=product");
                inputHtml_T = inputHtml_T.replaceAll("<o:p>", "");
                inputHtml_T = inputHtml_T.replaceAll("</o:p>", "");

                inputHtml = inputHtml_H + inputHtml_B + inputHtml_T;
            }
        } else {
            Pattern p = Pattern.compile("<font(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(inputHtml);
            inputHtml = m.replaceAll("");

            p = Pattern.compile("</font>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(inputHtml);
            inputHtml = m.replaceAll("");

            p = Pattern.compile("<a href=(\\s*[^<>]*)>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(inputHtml);
            inputHtml = m.replaceAll("");

            p = Pattern.compile("</a>", Pattern.CASE_INSENSITIVE);
            m = p.matcher(inputHtml);
            inputHtml = m.replaceAll("");

            /*inputHtml = inputHtml.replaceAll("&nbsp;", " ");
            while (inputHtml.indexOf("     ") != -1) {
                inputHtml = inputHtml.replaceAll("     ", "    ");
            }
            inputHtml = inputHtml.replaceAll("    ", "　　");*/

            inputHtml = inputHtml.replaceAll("<table", "<table class=product align=center");
            inputHtml = inputHtml.replaceAll("<td", "<td class=product");
            inputHtml = inputHtml.replaceAll("<TABLE", "<table class=product align=center");
            inputHtml = inputHtml.replaceAll("<TD", "<td class=product");
            inputHtml = inputHtml.replaceAll("<P", "<P class=product");
            inputHtml = inputHtml.replaceAll("<p", "<p class=product");
            inputHtml = inputHtml.replaceAll("<o:p>", "");
            inputHtml = inputHtml.replaceAll("</o:p>", "");
        }
        return inputHtml;
    }
}
