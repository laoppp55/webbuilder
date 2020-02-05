package com.bizwink.util;

import com.bizwink.cms.extendAttr.ExtendAttr;
import com.bizwink.cms.multimedia.Attechment;
import com.bizwink.cms.news.Article;
import java.sql.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 17-6-2.
 */
public class SendDataToSJH {

    public String MakeTheXMLFromSection(List attrList,List<Attechment> attechments,Article article,int ColumnID) {
        String detailinfo = null;
        String projectno = null;
        StringBuffer jbinfo = new StringBuffer();
        StringBuffer zbrinfo = new StringBuffer();
        StringBuffer zbjginfo = new StringBuffer();
        StringBuffer timeinfo = new StringBuffer();
        StringBuffer wzinfo = new StringBuffer();
        StringBuffer tablesinfo = new StringBuffer();
        StringBuffer fujian = new StringBuffer();
        String type = "5";
        //System.out.println("栏目ID:"+article.getColumnID());
        /*  栏目ID 51357 中标公告  type=8
            栏目ID 51279 招标公告  type=5
            栏目ID 51358 询价书    type=4
            栏目ID 51576 资格预审    type=7
         */
        int columnid=article.getColumnID();
        if(columnid == 51358){  //4 公开询价
            type="4";
        }
        if(columnid == 51357){  //8 中标公告
            type="8";
        }
        if(columnid == 51576){  //7 资格预审
            type="7";
        }



        StringBuffer content = new StringBuffer();
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        content.append("<page>\r\n");

        ExtendAttr extend = null;
        //组装XML文件的头部信息
        StringBuffer field1 = new StringBuffer();
        field1.append("<corpname value=\"" + article.getSource() + "\"></corpname>\r\n");
        field1.append("<type value=\"" + type + "\"></type>\r\n");
        field1.append("<dataType value=\"1\"></dataType>\r\n");
        field1.append("<valid value=\"1\"></valid>\r\n");
        //field1.append("<senddate value=\"" + new Date(System.currentTimeMillis()) + "\"></senddate>\r\n");
        // field1.append("<senddate value=\"" + "2017-6-8 14:24:40" + "\"></senddate>\r\n");
        // field1.append("<enddate value=\"2017-6-9 14:30:00\"></enddate>\r\n");
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            String _ename = extend.getEName();
            if (_ename.equalsIgnoreCase("_plantform")){
                //content.append("<plantform value=\"" + extend.getStringValue() + "\"></plantform>\r\n");
                field1.append("<source value=\""+extend.getStringValue()+"\"></source>\r\n");
                break;
            }
        }
        // field1.append("<source value=\"99\"></source>\r\n");
        field1.append("<title value=\"" + article.getMainTitle() + "\"></title>\r\n");
        field1.append("<from value=\"" + article.getViceTitle() + "\"></from>\r\n");
        field1.append("<keyWord value=\"" + article.getKeyword() + "\"></keyWord>\r\n");
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            int datatype = extend.getDataType();
            String _ename = extend.getEName();
            if (_ename.equalsIgnoreCase("_id")) {
                content.append("<id value=\"" + extend.getStringValue() +"\"></id>\r\n");
                projectno = extend.getStringValue();
            }else if (_ename.equalsIgnoreCase("_plantformUrl"))
                content.append("<plantformUrl value=\"" + extend.getStringValue() +"\"></plantformUrl>\r\n");
            else if (_ename.equalsIgnoreCase("_plantform"))
                content.append("<plantform value=\"" + extend.getStringValue() + "\"></plantform>\r\n");
            else if (_ename.equalsIgnoreCase("_detailUrl"))
                content.append("<detailUrl value=\"" + extend.getStringValue() + "\"></detailUrl>\r\n");
            else if (_ename.equalsIgnoreCase("_detail"))
                detailinfo = extend.getTextValue();
            else if(_ename.equals("_senddate"))
                content.append("<senddate value=\""+extend.getStringValue()+"\"></senddate>\r\n");
            else if(_ename.equals("_enddate"))
                content.append("<enddate value=\""+extend.getStringValue()+"\"></enddate>\r\n");
        }
        content.append(field1);
        //组装招标项目基本信息、招标人信息、招标机构信息和招标时间安排等信息
        jbinfo.append("<pairedItem name=\"一、招标项目基本信息\">\r\n");
        zbrinfo.append("<pairedItem name=\"二、招标人信息\">\r\n");
        zbjginfo.append("<pairedItem name=\"三、招标代理机构信息\">\r\n");
        timeinfo.append("<pairedItem name=\"四、时间安排\">\r\n");
        if (projectno!=null) {
            jbinfo.append("<keyValuePair key=\"项目编号\">\r\n").append(projectno+"\r\n").append("</keyValuePair>\r\n");
            jbinfo.append("<keyValuePair key=\"项目名称\">\r\n").append(article.getMainTitle()+"\r\n").append("</keyValuePair>\r\n");
        }

        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            int datatype = extend.getDataType();
            String _ename = extend.getEName();
            if(_ename.startsWith("_jb")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    jbinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    jbinfo.append(value+"\r\n");
                    jbinfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_zbr")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    zbrinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    zbrinfo.append(value + "\r\n");
                    zbrinfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_dl")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    zbjginfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    zbjginfo.append(value + "\r\n");
                    zbjginfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_t")) {
                String data = extend.getStringValue();
                if (data!=null) {
                    data = data.replace("：",":");
                    int posi = data.indexOf(":");
                    if (posi > -1) {
                        String key = data.substring(0,posi);
                        String value = data.substring(posi+1);
                        timeinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                        timeinfo.append(value + "\r\n");
                        timeinfo.append("</keyValuePair>\r\n");
                    }
                }
            }
        }
        jbinfo.append("</pairedItem>\r\n");
        zbrinfo.append("</pairedItem>\r\n");
        zbjginfo.append("</pairedItem>\r\n");
        timeinfo.append("</pairedItem>\r\n");
        content.append(jbinfo);
        content.append(zbrinfo);
        content.append(zbjginfo);
        content.append(timeinfo);
        //组装招标项目基本信息、招标人信息、招标机构信息和招标时间安排等信息
        List<String> phase = new ArrayList<String>();
        List<Integer> xhs = new ArrayList<Integer>();
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            String text = "";
            if (extend.getDataType() == 3) {
                if (extend.getTextValue()!=null && extend.getTextValue()!=""){
                    text = extend.getTextValue().replace("：",":");
                    int posi = text.indexOf(":");
                    String sxh=text.substring(0,posi);
                    text = text.substring(posi+1);
                    int xh = Integer.parseInt(sxh);
                    xhs.add(xh);                           ///2
                    if (extend.getControlType() == 4) {
                        posi = text.toLowerCase().indexOf("<table");
                        String title = null;
                        if (posi > -1) {
                            title = text.substring(0,posi);
                            //组装成约定的表格的形式
                            if(title != null) {
                                //清除字符串中所有的HTML标签
                                Pattern clearHTMLTag = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
                                String[] vals = clearHTMLTag.split(title);
                                String value = "";
                                for(int kk=0;kk<vals.length;kk++) {
                                    value = value + vals[kk];
                                }
                                title = value;
                            }
                            phase.add(FormatTable(text));
/*
                            if (title.indexOf("物资")>-1){
                                wzinfo.append("<prods isTable=\"true\">\r\n");
                                wzinfo.append(FormatTable(text));
                                wzinfo.append("</prods>\r\n");
                            } else {
                                tablesinfo.append(FormatTable(text));
                            }
*/
                        }
                    } else {
                        text = text.replace(">","&gt;");
                        text = text.replace("<","&lt;");
                        phase.add(text);
                    }
                }
            }

            if (extend.getDataType() == 1) {
                if (extend.getStringValue() != null && extend.getStringValue()!="") {
                    text = extend.getTextValue();
                    if (text!=null) {
                        int posi = text.indexOf(":");
                        String sxh=text.substring(0,posi);
                        text = text.substring(posi+1);
                        phase.add(text);
                        int xh = Integer.parseInt(sxh);
                        xhs.add(xh);
                    }
                }
            }
        }

        //对于段落序号按照从低到高的顺序进行排列
        for(int ii=0;ii<xhs.size();ii++) {
            int val = xhs.get(ii);
            String text = phase.get(ii);
            for (int kk=ii+1;kk<xhs.size();kk++) {
                if (val > xhs.get(kk)) {
                    //交换排序的KEY值
                    val = xhs.get(kk);
                    xhs.set(kk,xhs.get(ii));
                    xhs.set(ii,val);
                    //交换段落的值
                    text = phase.get(kk);
                    phase.set(kk,phase.get(ii));
                    phase.set(ii,text);
                }
            }
        }

        for(int ii=0;ii<xhs.size();ii++) {
            // if (phase.get(ii).indexOf("<table") > -1)
            if (phase.get(ii).indexOf("isTable") > -1)
                content.append(phase.get(ii));
            else
                content.append("<stringItem name=\"招标公告详情\">\r\n" + phase.get(ii) + "\r\n</stringItem>\r\n");
        }



        //处理附件信息
        /*if (attechments!=null) {
            fujian.append("<attachments>\r\n");
            for (int ii=0; ii<attechments.size(); ii++) {
                Attechment attechment = (Attechment)attechments.get(ii);
                fujian.append("<ZSYFj name=\"" + attechment.getFilename() + "\">\r\n");
                fujian.append(attechment.getDirname() + "\r\n");
                fujian.append("</ZSYFj>\r\n");
            }
            fujian.append("</attachments>\r\n");
        }

        content.append(jbinfo).
                append(zbrinfo).
                append(zbjginfo).
                append(timeinfo).
                append(wzinfo).
                append(tablesinfo).
                append(fujian);

        if (article.getSummary()!=null)
            content.append("<stringItem name=\"招标公告详情\">\r\n" + article.getSummary()+"\r\n" + detailinfo + "\r\n" + "</stringItem>\r\n").
                    append("</page>");
        else
            content.append("<stringItem name=\"招标公告详情\">\r\n" + detailinfo + "\r\n" + "</stringItem>\r\n").
                    append("</page>");
*/
        content.append("</page>");
        return content.toString();
    }

    public String MakeTheXML(List attrList,List<Attechment> attechments,Article article,int ColumnID) {
        String detailinfo = null;
        String projectno = null;
        StringBuffer jbinfo = new StringBuffer();
        StringBuffer zbrinfo = new StringBuffer();
        StringBuffer zbjginfo = new StringBuffer();
        StringBuffer timeinfo = new StringBuffer();
        StringBuffer wzinfo = new StringBuffer();
        StringBuffer tablesinfo = new StringBuffer();
        StringBuffer fujian = new StringBuffer();

        StringBuffer content = new StringBuffer();
        content.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        content.append("<page>\r\n");

        ExtendAttr extend = null;
        //组装XML文件的头部信息
        StringBuffer field1 = new StringBuffer();
        field1.append("<corpname value=\"" + article.getSource() + "\"></corpname>\r\n");
        field1.append("<type value=\"" + "5" + "\"></type>\r\n");
        field1.append("<dataType value=\"1\"></dataType>\r\n");
        field1.append("<valid value=\"1\"></valid>\r\n");
        field1.append("<senddate value=\"" + new Date(System.currentTimeMillis()) + "\"></senddate>\r\n");
        field1.append("<enddate value=\"2017-05-20 23:59:59\"></enddate>\r\n");
        field1.append("<source value=\"99\"></source>\r\n");
        field1.append("<title value=\"" + article.getMainTitle() + "\"></title>\r\n");
        field1.append("<from value=\"" + article.getViceTitle() + "\"></from>\r\n");
        field1.append("<keyWord value=\"" + article.getKeyword() + "\"></keyWord>\r\n");
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            int datatype = extend.getDataType();
            String _ename = extend.getEName();
            if (_ename.equalsIgnoreCase("_id")) {
                content.append("<id value=\"" + extend.getStringValue() +"\"></id>\r\n");
                projectno = extend.getStringValue();
            }else if (_ename.equalsIgnoreCase("_plantformUrl"))
                content.append("<plantformUrl value=\"" + extend.getStringValue() +"\"></plantformUrl>\r\n");
            else if (_ename.equalsIgnoreCase("_plantform"))
                content.append("<plantform value=\"" + extend.getStringValue() + "\"></plantform>\r\n");
            else if (_ename.equalsIgnoreCase("_detailUrl"))
                content.append("<detailUrl value=\"" + extend.getStringValue() + "\"></detailUrl>\r\n");
            else if (_ename.equalsIgnoreCase("_detail"))
                detailinfo = extend.getTextValue();
        }
        content.append(field1);

        //组装招标项目基本信息、招标人信息、招标机构信息和招标时间安排等信息
        jbinfo.append("<pairedItem name=\"一、招标项目基本信息\">\r\n");
        zbrinfo.append("<pairedItem name=\"二、招标人信息\">\r\n");
        zbjginfo.append("<pairedItem name=\"三、招标代理机构信息\">\r\n");
        timeinfo.append("<pairedItem name=\"四、时间安排\">\r\n");
        if (projectno!=null) {
            jbinfo.append("<keyValuePair key=\"招标编号\">\r\n").append(projectno+"\r\n").append("</keyValuePair>\r\n");
        }
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            int datatype = extend.getDataType();
            String _ename = extend.getEName();
            if(_ename.startsWith("_jb")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    jbinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    jbinfo.append(value+"\r\n");
                    jbinfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_zbr")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    zbrinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    zbrinfo.append(value + "\r\n");
                    zbrinfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_dl")) {
                String data = extend.getStringValue();
                data = data.replace("：",":");
                int posi = data.indexOf(":");
                if (posi > -1) {
                    String key = data.substring(0,posi);
                    String value = data.substring(posi+1);
                    zbjginfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                    zbjginfo.append(value + "\r\n");
                    zbjginfo.append("</keyValuePair>\r\n");
                }
            } else if (_ename.startsWith("_t")) {
                String data = extend.getStringValue();
                if (data!=null) {
                    data = data.replace("：",":");
                    int posi = data.indexOf(":");
                    if (posi > -1) {
                        String key = data.substring(0,posi);
                        String value = data.substring(posi+1);
                        timeinfo.append("<keyValuePair key=\"" + key + "\">\r\n");
                        timeinfo.append(value + "\r\n");
                        timeinfo.append("</keyValuePair>\r\n");
                    }
                }
            }
        }
        jbinfo.append("</pairedItem>\r\n");
        zbrinfo.append("</pairedItem>\r\n");
        zbjginfo.append("</pairedItem>\r\n");
        timeinfo.append("</pairedItem>\r\n");

        //组合包括物资在内的各种表格信息
        for(int ii=0;ii<attrList.size(); ii++) {
            extend = (ExtendAttr)attrList.get(ii);
            int datatype = extend.getDataType();
            String _ename = extend.getEName();
            System.out.println("datatype==="+datatype);
            if (datatype == 3) {
                String data = extend.getTextValue();
                int posi = data.toLowerCase().indexOf("<table");
                String title = null;
                if (posi > -1) {
                    title = data.substring(0,posi);
                    //组装成约定的表格的形式
                    if(title != null) {
                        //清除字符串中所有的HTML标签
                        Pattern clearHTMLTag = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);
                        String[] vals = clearHTMLTag.split(title);
                        String value = "";
                        for(int kk=0;kk<vals.length;kk++) {
                            value = value + vals[kk];
                        }
                        title = value;
                    }

                    if (title.indexOf("物资")>-1){
                        wzinfo.append("<prods isTable=\"true\">\r\n");
                        wzinfo.append(FormatTable(data));
                        wzinfo.append("</prods>\r\n");
                    } else {
                        tablesinfo.append(FormatTable(data));
                    }
                }
            }
        }

        //处理附件信息
        if (attechments!=null) {
            fujian.append("<attachments>\r\n");
            for (int ii=0; ii<attechments.size(); ii++) {
                Attechment attechment = (Attechment)attechments.get(ii);
                fujian.append("<ZSYFj name=\"" + attechment.getFilename() + "\">\r\n");
                fujian.append(attechment.getDirname() + "\r\n");
                fujian.append("</ZSYFj>\r\n");
            }
            fujian.append("</attachments>\r\n");
        }

        content.append(jbinfo).
                append(zbrinfo).
                append(zbjginfo).
                append(timeinfo).
                append(wzinfo).
                append(tablesinfo).
                append(fujian);

        if (article.getSummary()!=null)
            content.append("<stringItem name=\"招标公告详情\">\r\n" + article.getSummary()+"\r\n" + detailinfo + "\r\n" + "</stringItem>\r\n").
                    append("</page>");
        else
            content.append("<stringItem name=\"招标公告详情\">\r\n" + detailinfo + "\r\n" + "</stringItem>\r\n").
                    append("</page>");

        return content.toString();
    }

    public String FormatTable(String buf) {
        buf = buf.replace("th","td");
        int posi = buf.toLowerCase().indexOf("<table");
        String title = null;
        if (posi > -1) {
            title = buf.substring(0,posi);
            buf = buf.substring(posi);
        }

        posi = buf.toLowerCase().indexOf("</table>");
        if (posi>-1) {
            buf = buf.substring(0,posi+"</table>".length());
        }

        //获取表格的行数
        Pattern prow = Pattern.compile("<tr([\\s\\S]*?)<\\/tr>", Pattern.CASE_INSENSITIVE);
        Matcher matcher = prow.matcher(buf);
        String matchStr=null;
        List<String> trs = new ArrayList<String>();
        while (matcher.find()) {
            matchStr = buf.substring(matcher.start(), matcher.end());
            trs.add(matchStr);
        }

        //清除字符串中所有的HTML标签
        Pattern clearHTMLTag = Pattern.compile("<[^<>]*>", Pattern.CASE_INSENSITIVE);

        //分解表格中某行列的正则表达式
        Pattern pcol = Pattern.compile("<td([\\s\\S]*?)<\\/td>", Pattern.CASE_INSENSITIVE);

        //获取表格每行的列数
        int[] columns = new int[trs.size()];
        for(int ii=0; ii<trs.size(); ii++) {
            matcher = pcol.matcher(trs.get(ii));
            int column = 0;
            while(matcher.find()) {
                column = column + 1;
            }
            columns[ii] = column;
        }

        //判断有几种不同列数的行
        Map dd = new HashMap<Integer,Integer>();
        for(int ii=0; ii<trs.size(); ii++) {
            int val = columns[ii];
            boolean compareFlag = true;
            if (ii>0) {
                if (val == columns[ii-1]) compareFlag = false;
            }
            if (compareFlag) {
                int count = 0;
                for(int kk=0; kk<trs.size();kk++) {
                    if (val==columns[kk]) {
                        count = count + 1;
                    }
                }
                dd.put(val,count);
            }
        }

        //找出最多的相同列数
        Iterator iter = dd.keySet().iterator();
        int targetVal = 0;
        int targetCol = 0;
        while (iter.hasNext()) {
            Integer key = (Integer)iter.next();
            Integer val = (Integer)dd.get(key);
            if ( val.intValue() > targetVal) {
                targetVal = val.intValue();                //具有最多相同列的行数
                targetCol = key.intValue();                //最多的相同列数
            }
        }

        //获取有效的表格行数据
        List<String> valid_trs = new ArrayList<String>();
        for(int ii=0; ii<trs.size(); ii++) {
            matcher = pcol.matcher(trs.get(ii));
            int column = 0;
            while(matcher.find()) {
                column = column + 1;
            }
            if(column == targetCol) valid_trs.add(trs.get(ii));
        }

        //获取表格的表头信息
        List<String> keys = new ArrayList<String>();
        matcher = pcol.matcher(valid_trs.get(0));
        while(matcher.find()) {
            matchStr = valid_trs.get(0).substring(matcher.start(), matcher.end());
            String[] vals = clearHTMLTag.split(matchStr);
            String value = "";
            for(int kk=0;kk<vals.length;kk++) {
                value = value + vals[kk];
            }
            keys.add(value);
        }

        //获取表格各列的数值信息
        String[][] values = new String[valid_trs.size()-1][targetCol];
        for(int ii=1; ii<valid_trs.size(); ii++) {
            matcher = pcol.matcher(valid_trs.get(ii));
            int col = 0;
            while(matcher.find()) {
                matchStr = valid_trs.get(ii).substring(matcher.start(), matcher.end());
                String[] vals = clearHTMLTag.split(matchStr);
                String value = "";
                for(int kk=0;kk<vals.length;kk++) {
                    value = value + vals[kk];
                }
                values[ii-1][col] = value.replace("\r\n","");
                col = col + 1;
            }
        }

        //丢掉某列数值全为空的列


        //组装成约定的表格的形式
        if(title != null) {
            String[] vals = clearHTMLTag.split(title);
            String value = "";
            for(int kk=0;kk<vals.length;kk++) {
                value = value + vals[kk];
            }
            title = value;
        }

        StringBuffer content = new StringBuffer();
        if (title.indexOf("物资")>-1) {
            content.append("<prods isTable=\"true\">\r\n");
        }
        content.append("<table name=\"" + title + "\">\r\n");
        for(int ii=0; ii<values.length;ii++) {
            content.append("<tr>\r\n");
            for(int kk=0; kk<targetCol; kk++) {
                //如果表头内容为空，抛弃掉该列内容
                StringBuffer therow = new StringBuffer();
                therow.append("<td key=\"" + keys.get(kk).replace("\r\n","") + "\">");
                therow.append(values[ii][kk]);
                therow.append("</td>\r\n");
                content.append(therow);
            }
            content.append("</tr>\r\n");
        }
        content.append("</table>\r\n");
        if (title.indexOf("物资")>-1) {
            content.append("</prods>\r\n");
        }

        return content.toString();
    }
}
