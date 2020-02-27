package com.bizwink.util;

import com.bizwink.cms.ExtendAttr;
import com.bizwink.cms.zTreeNodeObj;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdom.input.SAXBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Administrator on 18-4-6.
 */
public class createExtendattrInputHtml {
    public static String builderHtmlFromExtendattr(String extstr) {
        List textList = new ArrayList();
        List areaList = new ArrayList();
        Hashtable hash = new Hashtable();
        SAXBuilder builder = new SAXBuilder();
        StringBuffer content = new StringBuffer();
        StringBuffer scripts = new StringBuffer();

        try {
            Reader in = new StringReader(extstr);
            org.jdom.Document doc = builder.build(in);
            List nodeList = doc.getRootElement().getChildren();
            for (int i = 0; i < nodeList.size(); i++) {
                org.jdom.Element e = (org.jdom.Element) nodeList.get(i);
                int type = Integer.parseInt(e.getAttributeValue("type"));
                ExtendAttr extend = new ExtendAttr();
                extend.setCName(e.getText());
                extend.setEName(e.getName());
                extend.setControlType(type);
                extend.setDefaultValue(e.getAttributeValue("defaultval"));
                extend.setDataType(Integer.parseInt(e.getAttributeValue("datatype")));
                //1单行文本，2滚动多行文本，3上传文件，4富文本编辑器，5下拉列表，6日期与时间
                if (type == 1 || type == 3 ||type == 5 || type == 6)
                    textList.add(extend);
                else
                    areaList.add(extend);
            }

            int item_num = textList.size()  + areaList.size();

            if (item_num > 0) {
                content.append("<table border=0 width=\"100%\">\r\n");
                int count = 3 - textList.size() % 3;
                if (textList.size() % 3 == 0) count = 0;

                for (int i = 0; i < textList.size() + count; i++) {
                    if (i % 3 == 0) content.append("<tr height=24>\r\n");
                    if (i < textList.size()) {
                        ExtendAttr extend = (ExtendAttr) textList.get(i);
                        String type = "";
                        if (extend.getControlType() == 3)
                            type = "(<a href=javascript:upload_attrpic_onclick(\"" + extend.getEName() + "\")>图</a>)";
                        String cname = extend.getCName();

                        //读出当前属性的值
                        String value = "";
                        if (hash.get(extend.getEName()) != null)
                            value = hash.get(extend.getEName()).toString();

                        if (extend.getControlType() == 6)               //日期和时间输入框
                            content.append("<td width=\"33%\" align=\"left\">" + cname + type + "：<input class=tine name=" + extend.getEName() + " size=30 value=\"" + value + "\" onfocus=\"setday(this)\" readonly" +"></td>\r\n");
                        else if (extend.getControlType() == 5)  {       //下拉列表输入
                            String option_key_values = extend.getDefaultValue();
                            JSONObject jsStr = JSONObject.fromObject(option_key_values);
                            int listtype = jsStr.getInt("type");
                            JSONArray jsonArray = jsStr.getJSONArray("data");
                            List<zTreeNodeObj> zTreeNodeObjs = JSON_Str_To_ObjArray.Transfer_JsonStr_To_ObjArray(jsonArray);
                            List typeOptions = JSON_Str_To_ObjArray.genOptionsStr(zTreeNodeObjs);
                            if (listtype == 0) {        //单选列表
                                content.append("<td width=\"33%\" align=\"left\">" + cname + type + ":");
                                content.append("<select size=\"1\" id=\"selid" + i + "\" name=\"" + extend.getEName() + "\" class=\"tine\" style=\"width:200\">\n");
                                content.append("<option value=\"-1\">请选择</option>");
                                String tempbuf="";
                                for (int ii=0;ii<typeOptions.size();ii++) {
                                    tempbuf = tempbuf + typeOptions.get(ii);
                                }
                                String[] ss = tempbuf.split("\r\n");
                                for(int ii=1; ii<ss.length; ii++) {
                                    String tbuf = ss[ii];
                                    int posi = tbuf.indexOf("|");
                                    String text = tbuf.substring(0,posi);
                                    String keyval = tbuf.substring(posi+1);
                                    if (keyval.equalsIgnoreCase(value))
                                        content.append("<option value=\"" + keyval + "\"" + " selected>" + text + "</option>");
                                    else
                                        content.append("<option value=\"" + keyval + "\">" + text + "</option>");
                                }
                                content.append("</select>\r\n");
                                content.append("</td>\r\n");
                            } else {                   //多选列表
                                int itemnum = jsonArray.size();
                                String textValues = getTextValue(zTreeNodeObjs, value);
                                content.append("<td width=\"33%\" align=\"left\"><a href=\"#\" onclick=javascript:getMoreType("+ itemnum + ",\"" + extend.getEName() +"\");>" + cname + type + "</a>");
                                content.append("：<input type=\"text\" class=tine name=\"" + extend.getEName() + "txt\" size=30 value=\"" + textValues + "\" readonly=\"readonly\">");
                                content.append("：<input type=\"hidden\" class=tine name=\"" + extend.getEName() + "\" size=30 value=\"" + value + "\" readonly=\"readonly\">");
                                content.append("</td>\r\n");
                            }
                        } else                                           //单行文本输入，可以是字符型、数字和小数
                            content.append("<td width=\"33%\" align=\"left\">" + cname + type + "：<input class=tine name=" + extend.getEName() + " size=30 value=\"" + value + "\"></td>\r\n");

                        //组合JAVASCRIPT，只控制文本框的数据类型，整型及小数型
                        if (extend.getControlType() == 1 && (extend.getDataType() == 2 || extend.getDataType() == 4)) {
                            scripts.append("var ret = true;\r\n");
                            scripts.append("if (frm." + extend.getEName() + ".value != \"\")\r\n{\r\n");
                            if (extend.getDataType() == 1) {
                                scripts.append("var regstr = /[^0-9]/gi;\r\n");
                                scripts.append("if (regstr.exec(str) != null)\r\n{\r\n");
                                scripts.append("alert(" + cname + "请输入整型数据)\r\n");
                            } else {
                                scripts.append("var regstr = /[^0-9.]/gi;\r\n");
                                scripts.append("if (regstr.exec(frm." + extend.getEName() + ".value) != null)\r\n{\r\n");
                                scripts.append("alert(" + cname + "请输入小数型数据)\r\n");
                            }
                            scripts.append("frm." + extend.getEName() + ".focus();\r\n");
                            scripts.append("ret = false;\r\n");
                            scripts.append("}}\r\n");
                            scripts.append("return ret;\r\n");
                        } else {
                            scripts.append("return true;\r\n");
                        }
                    } else {
                        content.append("<td width=\"33%\"></td>");
                    }
                    if ((i + 1) % 3 == 0) content.append("</tr>\r\n");
                }

                count = 1;
                if (areaList.size() % 3 == 0) count = 0;
                //content.append("<table border=0 width=\"100%\">\r\n");
                for (int i = 0; i < areaList.size() + count; i++) {
                    if (i % 3 == 0) content.append("<tr>\r\n");
                    if (i < areaList.size()) {
                        ExtendAttr extend = (ExtendAttr) areaList.get(i);
                        String cname = extend.getCName();

                        //读出当前属性的值
                        String value = "";
                        if (hash.get(extend.getEName()) != null)
                            value = hash.get(extend.getEName()).toString();

                        if (extend.getControlType() == 4) {
                            content.append("<td width=\"33%\">" + cname + "：<input type=button value=编辑 onclick=Editor('" +extend.getEName() + "');>\r\n"+  "<br><textarea class=tine rows=10 cols=60 name=" + extend.getEName() + ">" + value + "</textarea>\r\n");
                        } else {
                            content.append("<td width=\"33%\">" + cname + "：<br><textarea class=tine rows=11 cols=60 name=" + extend.getEName() + ">" + value + "</textarea>\r\n");
                        }
                        content.append("</td>\r\n");
                    } else {
                        content.append("<td width=\"33%\"></td>");
                    }
                    if ((i + 1) % 3 == 0) content.append("</tr>\r\n");
                }
                content.append("</table>");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public static String getTextValue(List options,String strvalues) {
        String textval = "";
        String[] values = strvalues.split(",");
        for(int kk=0; kk<values.length; kk++) {
            for(int ii=0; ii<options.size();ii++) {
                zTreeNodeObj o = (zTreeNodeObj)options.get(ii);
                String tbuf = o.getName();
                int posi = tbuf.indexOf("|");
                String text = tbuf.substring(0,posi);
                String keyval = tbuf.substring(posi+1);
                if (keyval.trim().equals(values[kk].trim())) {
                    textval = textval + text + ",";
                    break;
                }
            }
        }
        if (textval.length()>0) textval = textval.substring(0,textval.length()-1);
        return textval;
    }
}
