package com.bizwink.cms.publish;

import com.bizwink.cms.util.StringUtil;
import com.bizwink.cms.xml.XMLProperties;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-8-25
 * Time: 19:08:07
 * To change this template use File | Settings | File Templates.
 */
public class userRegisterForm {
    protected String createUserRegisterForm(String sitename,String dbtype, String buf, int markid, int marktype, int picarticleid) {
        boolean exist_birthdate = false;
        boolean exist_idno = false;
        String country_province_city_combine = "";
        String content=buf;
        int sposi = content.indexOf("<CONTENT>");
        int eposi = content.indexOf("</CONTENT>");
        String formstr = "";
        String buttonstr = "";
        String mustcheckfield_program = "";
        int formtype = 0;         //0--表示普通FORM表单，不包括文件上传字段   1--表示有文件上传字段的表单
        int resetbuttonflag = 0;   //0--表示表单不设置重置按钮        1--表示表单设置重置按钮
        String global_desc_style = null;
        String global_desc_align = null;
        String global_content_style = null;
        String global_prompt_style = null;
        String global_prompt_align = null;
        int global_field_len = 0;
        String okimage = null;
        String cancelimage = null;
        String resetimage = null;
        XMLProperties properties = null;
        if (sposi != -1 || eposi != -1) {
            content = content.substring(sposi + 9,eposi);
            if (content.indexOf("<chinesename>重置</chinesename>") > -1) resetbuttonflag=1;
            properties = new XMLProperties(content);
            global_desc_style = properties.getProperty("globalsetting.globaldescstyle");
            global_desc_align = properties.getProperty("globalsetting.globaldescalign");
            global_content_style = properties.getProperty("globalsetting.globalcontentstyle");
            global_prompt_style = properties.getProperty("globalsetting.globalpromptstyle");
            global_prompt_align = properties.getProperty("globalsetting.globalpromptalign");
            global_field_len = Integer.parseInt(properties.getProperty("globalsetting.globalfieldlen"));
            okimage = properties.getProperty("globalsetting.okimage");
            cancelimage = properties.getProperty("globalsetting.cancelimage");
            resetimage = properties.getProperty("globalsetting.resetimage");

            String b[] = properties.getChildrenProperties("fields");
            String not_null_field_name[] = new String[b.length];
            for(int i=0; i<b.length; i++) not_null_field_name[i] = null;
            String tbuf[] = new String[b.length];
            int fieldorder[] = new int[b.length];
            for (int j=0; j<b.length; j++) {
                String desc_style = properties.getProperty("fields." + b[j] + ".descstyle");
                String content_style = properties.getProperty("fields." + b[j] + ".contentstyle");
                String chinesename = properties.getProperty("fields." + b[j] + ".chinesename");
                String name = properties.getProperty("fields." + b[j] + ".name");
                int ordernum=0;
                int maxlen = 50;
                String ordernum_s = properties.getProperty("fields." + b[j] + ".order");
                if (ordernum_s != null) {
                    ordernum=Integer.parseInt(ordernum_s);
                }
                String fieldtype = properties.getProperty("fields." + b[j] + ".type.name");
                String isnull = properties.getProperty("fields." + b[j] + ".isnull");
                int fieldlen = Integer.parseInt(properties.getProperty("fields." + b[j] + ".flen"));
                if (chinesename.equals("用户名称") && ordernum > 0) {
                    maxlen = 50;
                    tbuf[j] = generateCheckInputTR("register_user",global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                }

                if (chinesename.equals("用户口令") && ordernum > 0) {
                    maxlen = 20;
                    fieldorder[j] = ordernum;
                    tbuf[j] = "<tr>\r\n";
                    if (desc_style != null)
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">" + chinesename + "：</td>\r\n";
                    else if (global_desc_style != null)
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">" + chinesename + "：</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">" + chinesename + "：</td>\r\n";

                    if (content_style != null){
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + fieldlen + " \"maxlength=\"" + maxlen +"\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" maxlength=\"" + maxlen +"\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";

                    } else if (global_content_style != null) {
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                    } else {
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord1\" type=\"" + name + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword1()\" name=\"passWord1\" />(<font color=\"red\">*</font>)</td>\r\n";
                    }
                    if (global_prompt_style != null)
                        tbuf[j] = tbuf[j] + "<td width=\"20%\" align=\"" + global_prompt_align + "\"><div id=\"p_mag1\" class=\""+ global_prompt_style + "\"></div></td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<td width=\"20%\" align=\"" + global_prompt_align + "\"><div id=\"p_mag1\"></div></td>\r\n";
                    tbuf[j] = tbuf[j] + "</tr>\r\n";

                    //确认密码
                    tbuf[j] = tbuf[j] + "<tr>\r\n";
                    if (desc_style != null)
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + desc_style + "\">确认密码：</td>\r\n";
                    else if (global_desc_style != null)
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\" class=\"" + global_desc_style + "\">确认密码：</td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<td align=\"" + global_desc_align + "\">确认密码：</td>\r\n";

                    if (content_style != null){
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";

                    } else if (global_content_style != null) {
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" maxlength=\"" + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" class=\"" + global_content_style + "\" />(<font color=\"red\">*</font>)</td>\r\n";
                    } else {
                        if (fieldlen != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else if (global_field_len != 0)
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" size=\"" + global_field_len + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" />(<font color=\"red\">*</font>)</td>\r\n";
                        else
                            tbuf[j] = tbuf[j] + "<td width=\"50%\" align=\"left\"><input id=\"passWord2\" type=\"" + name + "\" maxlength=\"" + maxlen + "\" onBlur=\"javascript:checkPassword2()\" name=\"passWord2\" />(<font color=\"red\">*</font>)</td>\r\n";
                    }
                    if (global_prompt_style != null)
                        tbuf[j] = tbuf[j] + "<td width=\"20%\" align=\"" + global_prompt_align + "\"><div id=\"p_mag2\" class=\""+ global_prompt_style + "\"></div></td>\r\n";
                    else
                        tbuf[j] = tbuf[j] + "<td width=\"20%\" align=\"" + global_prompt_align + "\"><div id=\"p_mag2\"></div></td>\r\n";
                    tbuf[j] = tbuf[j] + "</tr>\r\n";
                }

                if (chinesename.equals("用户真实姓名") && ordernum > 0) {
                    maxlen = 30;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //电子邮件
                if (chinesename.equals("电子邮件") && ordernum > 0) {
                    maxlen = 100;
                    tbuf[j] = generateCheckInputTR("email",global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //国家
                if (chinesename.equals("国家") && ordernum > 0) {
                    maxlen = 50;
                    country_province_city_combine = country_province_city_combine + name;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //省份
                if (chinesename.equals("省份") && ordernum > 0) {
                    maxlen = 30;
                    country_province_city_combine = country_province_city_combine + name;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //城市
                if (chinesename.equals("城市") && ordernum > 0) {
                    maxlen = 20;
                    country_province_city_combine = country_province_city_combine + name;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //城市
                if (chinesename.equals("所在街道") && ordernum > 0) {
                    maxlen = 100;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //nation               varchar2(30),                       --民族
                if (chinesename.equals("民族") && ordernum > 0) {
                    maxlen = 30;
                    if (fieldtype.equals("text"))
                        tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,maxlen,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    else
                        tbuf[j] = generateSelectTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style,"汉族:回族:畲族:塔塔尔族:阿昌族:哈萨克族:土家族:景颇族:哈尼族:土族:白族:维吾尔族:保安族:赫哲族:乌孜别克族:基诺族:布依族:拉祜族:锡伯族:黎族:东乡族:蒙古族:仫佬族:达斡尔族:藏族:毛南族:裕固族:俄罗斯族:德昂族:僳僳族:瑶族:朝鲜族:布朗族:满族:彝族:门巴族:侗族:苗族:佤族:羌族:独龙族:怒族:珞巴族:普米族:傣族:纳西族:高山族:壮族:额伦春族:塔吉克族:京族:仡佬族:鄂温克族:撒拉族:柯尔克孜族:水族");
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //degree               varchar2(30),                       --教育程度
                if (chinesename.equals("教育程度") && ordernum > 0) {
                    maxlen = 30;
                    if (fieldtype.equals("text"))
                        tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    else
                        tbuf[j] = generateSelectTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style,"博士:硕士:研究生:大学本科:大学专科:高中:中等专业学校:初中");
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //idno                 varchar2(18),                       --身份证号
                if (chinesename.equals("身份证号") && ordernum > 0) {
                    maxlen = 19;
                    exist_idno = true;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", int, 0, 19, 19,请输入正确的身份证号码))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //联系人
                if (chinesename.equals("联系人") && ordernum > 0) {
                    maxlen = 30;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //详细地址
                if (chinesename.equals("具体地址") && ordernum > 0) {
                    maxlen = 200;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //邮政编码
                if (chinesename.equals("邮政编码") && ordernum > 0) {
                    maxlen = 6;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"zip\", 0, 0, 6,\"请输入正确的邮政编码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //电话
                if (chinesename.equals("电话") && ordernum > 0) {
                    maxlen = 12;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"fax\", 0, 0, 12,\"请输入正确的电话号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //传真
                if (chinesename.equals("传真") && ordernum > 0) {
                    maxlen = 12;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"fax\", 0, 0, 12,\"请输入正确的传真号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //个人主页
                if (chinesename.equals("个人主页") && ordernum > 0) {
                    maxlen = 100;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //个人留言
                if (chinesename.equals("个人留言") && ordernum > 0) {
                    maxlen = 500;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //性别
                if (chinesename.equals("用户性别") && ordernum > 0) {
                    tbuf[j] = generateSexTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style,"男:女");
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }
                //用户QQ号码
                if (chinesename.equals("用户QQ号码") && ordernum > 0) {
                    maxlen = 30;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"int\", 0, 0, 12,\"请输入正确的QQ号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //MSN号码
                if (chinesename.equals("MSN号码") && ordernum > 0) {
                    maxlen = 30;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + "\"int\", 0, 0, 12,\"请输入正确的QQ号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //出生日期
                if (chinesename.equals("出生日期") && ordernum > 0) {
                    exist_birthdate=true;
                    tbuf[j] = generateDateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform.year" + ", " + isnull + ",\"int\", 0, 1, 4,\"请输入正确的年份信息\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform.month" + ", " + isnull + ",\"int\", 0, 1, 2,\"请输入正确的月份信息\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform.day" + ", " + isnull + ",\"int\", 0, 1, 2,\"请输入正确的日期信息\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";

                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //用户头像
                if (chinesename.equals("用户头像") && ordernum > 0) {
                    maxlen = 50;
                    formtype = 1;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //移动电话
                if (chinesename.equals("移动电话") && ordernum > 0) {
                    maxlen=12;
                    tbuf[j] = generateCheckInputTR("int",global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"int\", 0, 0, 11,\"请输入正确的手机号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //工作单位
                if (chinesename.equals("工作单位") && ordernum > 0) {
                    maxlen = 200;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //unitpcode            varchar2(10),                       --单位邮政编码
                if (chinesename.equals("单位邮政编码") && ordernum > 0) {
                    maxlen = 6;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"zip\", 0, 0, 6,\"请输入正确的邮政编码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //unitphone            varchar2(20),                       --单位电话
                if (chinesename.equals("单位电话") && ordernum > 0) {
                    maxlen = 6;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    mustcheckfield_program = mustcheckfield_program  + "        if (!InputValid(Regform." + name + ", " + isnull + ", \"fax\", 0, 0, 12,\"请输入正确的电话号码\"))\r\n";
                    mustcheckfield_program = mustcheckfield_program  + "            return (false);\r\n";
                }

                //stationtype          varchar2(8),                        --站台类别
                if (chinesename.equals("站台类别") && ordernum > 0) {
                    tbuf[j] = generateSelectTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style,"基地台:车载台:手持台:其它");
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //entitytype           varchar2(1),                        --申请者类别--集体/个人
                if (chinesename.equals("申请者类别") && ordernum > 0) {
                    tbuf[j] = generateSexTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style,"集体:个人");
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name + ";" + chinesename;
                }

                //stationaddr          varchar2(80),                       --站台地址
                if (chinesename.equals("站台地址") && ordernum > 0) {
                    maxlen = 80;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //opedegree            varchar2(20),                       --操作证等级
                if (chinesename.equals("操作证等级") && ordernum > 0) {
                    maxlen = 20;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //opecode              varchar2(20),                       --操作证书编号
                if (chinesename.equals("操作证书编号") && ordernum > 0) {
                    maxlen = 20;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //callsign             varchar2(20),                       --呼号
                if (chinesename.equals("呼号") && ordernum > 0) {
                    maxlen = 20;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //memo                 varchar2(512),                      --备注
                if (chinesename.equals("备注") && ordernum > 0) {
                    maxlen = 512;
                    tbuf[j] = generateTR(global_desc_align,global_prompt_align,global_prompt_style,global_field_len,fieldlen,maxlen,chinesename,name,fieldtype,isnull,global_desc_style,global_content_style,desc_style,content_style);
                    fieldorder[j] = ordernum;
                    if (isnull.equals("1")) not_null_field_name[j] = name;
                }

                //兴趣爱好

                //收入水平

                //确认
                if (chinesename.equals("确认")) {
                    //加入验证码字段
                    buttonstr = buttonstr + "<tr>\r\n";
                    buttonstr = buttonstr + "<td align=\"right\">验证码：</td>\r\n";
                    buttonstr = buttonstr + "<td width=\"50%\" align=\"left\"><input id=\"txtVerify\" name=\"txtVerify\" type=\"text\" /> <img id=\"safecode\" border=\"0\" name=\"cod\" alt=\"\" onclick=\"javascript:shuaxin()\" src=\"/_commons/drawImage.jsp\" /><a href=\"javascript:shuaxin()\" class=\"" + global_prompt_style + "\">看不清楚?换下一张</a></td>\r\n";
                    buttonstr = buttonstr + "<td><div id=\"v_mag\"></div></td>\r\n";
                    buttonstr = buttonstr + "</tr>\r\n";
                    buttonstr = buttonstr + generateSubmitButton(resetbuttonflag,okimage,cancelimage,resetimage,global_prompt_style,global_field_len,fieldlen,chinesename,name,fieldtype,global_content_style,content_style);
                }
            }

            //比较确定每个注册项的顺序，数组排序
            int i=0;   //当前项
            int k=0;   //存放比当前数更小的项
            while (i<b.length) {
                k = 0;
                int num = fieldorder[i];
                //System.out.println(num);
                //找到数组中剩余项中最小的数
                for (int j=i+1; j<b.length; j++) {
                    if (num>fieldorder[j]) {
                        k = j;
                        num = fieldorder[j];
                    }
                }

                //k>0存在比当前项更小的数，将当前项与第K项进行交换
                if (k>0) {
                    String s = tbuf[i];
                    tbuf[i] = tbuf[k];
                    tbuf[k] = s;

                    int tnum = fieldorder[i];
                    fieldorder[i] = fieldorder[k];
                    fieldorder[k] = tnum;
                }
                i = i +1;
            }

            //按fieldorder数组中的顺序合并tbuf数组中各个项形成表单
            for (int j=0; j<b.length; j++){
                if (tbuf[j] != null) formstr = formstr + tbuf[j];
            }

            //合并提交按钮项
            formstr = formstr + buttonstr;

            //加入表单头部信息
            if (formtype == 0) {
                formstr = "<form id=\"Regformid\" method=\"post\" action=\"register.jsp?formtype=0\" onSubmit=\"return check_register();\" name=\"Regform\">\r\n" +
                        "<input type=\"hidden\" name=\"startflag\" value=\"1\" />&nbsp;\r\n" +
                        "<input type=\"hidden\" name=\"doCreate\" value=\"1\" />\r\n" +
                        "<table border=\"0\" width=\"100%\">\r\n" + formstr;
            } else {
                formstr = "<form id=\"Regformid\" method=\"post\" action=\"register.jsp?formtype=1\" enctype=\"multipart/form-data\" onSubmit=\"return check_register();\" name=\"Regform\">\r\n" +
                        "<input type=\"hidden\" name=\"startflag\" value=\"1\" />&nbsp;\r\n" +
                        "<input type=\"hidden\" name=\"doCreate\" value=\"1\" />\r\n" +
                        "<table border=\"0\" width=\"100%\">\r\n" + formstr;
            }

            //验证表单字段是否为空的验证javascript程序
            String checkprogram = "";
            checkprogram = "<script type=\"text/JavaScript\">\r\n";
            checkprogram = checkprogram  + "    function check_register(){\r\n";
            checkprogram = checkprogram  + "        var name = Regform.username.value;\r\n";
            checkprogram = checkprogram  + "        var pass = document.getElementById(\"passWord1\").value;\r\n";
            checkprogram = checkprogram  + "        if(name == \"\" || name == null){\r\n";
            checkprogram = checkprogram  + "            alert('用户名不能为空');\r\n";
            checkprogram = checkprogram  + "            return false;\r\n";
            checkprogram = checkprogram  + "        }\r\n";

            checkprogram = checkprogram  + "        if(pass ==\"\"){\r\n";
            checkprogram = checkprogram  + "            alert('密码不能为空');\r\n";
            checkprogram = checkprogram  + "            return false;\r\n";
            checkprogram = checkprogram  + "        }\r\n";

            checkprogram = checkprogram  + mustcheckfield_program;

            //用户身份证号和生日字段都被选中，联合检查身份证号和生日字段内容是否匹配
            if (exist_birthdate == true && exist_idno==true) {

            }

            for(i=0; i<b.length; i++) {
                if (not_null_field_name[i] != null) {
                    int posi = not_null_field_name[i].lastIndexOf(";");
                    String ename = not_null_field_name[i].substring(0,posi);
                    String cname = not_null_field_name[i].substring(posi+1);

                    checkprogram = checkprogram  + "        var " + ename + "= Regform." + ename + ".value;\r\n";
                    checkprogram = checkprogram  + "        if (" + ename + "==\"\" || " + ename + "==null) {\r\n";
                    checkprogram = checkprogram  + "            alert('" + cname + "字段不能为空！！！');\r\n";
                    checkprogram = checkprogram  + "            return false;\r\n";
                    checkprogram = checkprogram  + "        }\r\n";

                }
            }

            checkprogram = checkprogram  + "        var yanzheng = document.getElementById(\"txtVerify\").value;\r\n";
            checkprogram = checkprogram  + "        var verify = document.getElementById(\"txtVerify\").value;\r\n";
            checkprogram = checkprogram  + "        if(yanzheng ==\"\"){\r\n";
            checkprogram = checkprogram  + "            alert('验证码不能为空');\r\n";
            checkprogram = checkprogram  + "            return false;\r\n";
            checkprogram = checkprogram  + "        }else{\r\n";
            checkprogram = checkprogram  + "            var objXmlc;\r\n";
            checkprogram = checkprogram  + "            if (window.ActiveXObject){\r\n";
            checkprogram = checkprogram  + "                objXmlc = new ActiveXObject(\"Microsoft.XMLHTTP\");\r\n";
            checkprogram = checkprogram  + "            }else if (window.XMLHttpRequest){\r\n";
            checkprogram = checkprogram  + "                objXmlc = new XMLHttpRequest();\r\n";
            checkprogram = checkprogram  + "            }\r\n";
            checkprogram = checkprogram  + "            objXmlc.open(\"POST\", \"/_commons/CheceVerify.jsp?Verify=\"+verify, false);\r\n";
            checkprogram = checkprogram  + "            objXmlc.send(null);\r\n";
            checkprogram = checkprogram  + "            var res = objXmlc.responseText;\r\n";
            checkprogram = checkprogram  + "            var re = res.split('-');\r\n";
            checkprogram = checkprogram  + "            var retstrs = re[0];\r\n";
            checkprogram = checkprogram  + "            if(retstrs==0){\r\n";
            checkprogram = checkprogram  + "                var message = \"<font color=red>验证码错误！！<font>\";\r\n";
            checkprogram = checkprogram  + "                document.getElementById(\"v_mag\").innerHTML = message;\r\n";
            checkprogram = checkprogram  + "                document.all.txtVerify.value =\"\";\r\n";
            checkprogram = checkprogram  + "                shuaxin();\r\n";
            checkprogram = checkprogram  + "                return false;\r\n";
            checkprogram = checkprogram  + "            }\r\n";
            checkprogram = checkprogram  + "        }\r\n";
            checkprogram = checkprogram  + "        return true;\r\n";
            checkprogram = checkprogram  + "    }\r\n";
            checkprogram = checkprogram  + "</script>\r\n";

            formstr = checkprogram + formstr  + "</table>\n\r</form>\r\n";
            if (dbtype.equalsIgnoreCase("mssql")) formstr = StringUtil.gb2iso(formstr);

        }

        return formstr;
    }

    private String generateTR(String globaldescalign,String globalpromptalign,String globalpromptstyle,int globallen,int fieldlen,int maxlen,String chinesename,String name,String fieldtype,String isnull,String gdescstyle,String gcontentstyle,String descstyle,String contentstyle) {
        String Buf = "";
        Buf = Buf + "<tr>\r\n";
        if (descstyle != null){
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + descstyle + "\">" + chinesename + "：</td>\r\n";
        }else if (gdescstyle != null)
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + gdescstyle + "\">"+chinesename + "：</td>\r\n";
        else
            Buf = Buf + "<td align=\"" + globaldescalign + "\">" + chinesename + "：</td>\r\n";

        if (gcontentstyle != null) {
            if (fieldlen !=0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" class=\"" + gcontentstyle + "\" />\r\n";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" maxlength=\"" + maxlen + "\" class=\"" + gcontentstyle + "\" />\r\n";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" maxlength=\"" + maxlen + "\" class=\"" + gcontentstyle + "\" />\r\n";
        } else if (contentstyle != null) {
            if (fieldlen !=0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" class=\"" + contentstyle + "\" />\r\n";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" maxlength=\"" + maxlen + "\" class=\"" + contentstyle + "\" />\r\n";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" maxlength=\"" + maxlen + "\" class=\"" + contentstyle + "\" />\r\n";
        } else {
            if (fieldlen !=0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" />\r\n";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" maxlength=\"" + maxlen +"\" />\r\n";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" maxlength=\"" + maxlen + "\" />\r\n";
        }

        if (isnull.equals("1"))
            Buf = Buf + "(<font color=\"red\">*</font>)</td>\r\n";
        else
            Buf = Buf + "</td>\r\n";
        Buf = Buf + "<td></td>\r\n";
        Buf = Buf + "</tr>\r\n";
        return Buf;
    }

    private String generateSelectTR(String globaldescalign,String globalpromptalign,String globalpromptstyle,int globallen,int fieldlen,String chinesename,String name,String fieldtype,String isnull,String gdescstyle,String gcontentstyle,String descstyle,String contentstyle,String values) {
        Pattern p = Pattern.compile(":", Pattern.CASE_INSENSITIVE);
        String tbuf[] = p.split(values);
        int fl = 0;
        //设置字段的长度
        if (fieldlen != 0)
            fl = fieldlen/3;
        else if (globallen != 0)
            fl = globallen/3;

        String Buf = "";
        Buf = Buf + "<tr>\r\n";
        if (descstyle != null){
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + descstyle + "\">" + chinesename + "：</td>\r\n";
        }else if (gdescstyle != null)
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + gdescstyle + "\">"+chinesename + "：</td>\r\n";
        else
            Buf = Buf + "<td align=\"" + globaldescalign + "\">" + chinesename + "：</td>\r\n";

        if (gcontentstyle != null) {
            if (fieldlen !=0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" style=\"width:" + fl*10 + "px\" class=\"" + gcontentstyle + "\">\r\n";
            } else if (globallen != 0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" style=\"width:" + fl*10 + "px\" class=\"" + gcontentstyle + "\">\r\n";
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\"" + " class=\"" + fl*10 + "\">\r\n";
            }
        } else if (contentstyle != null) {
            if (fieldlen !=0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" + style=\"width:" + fl*10 + "\" class=\"" + contentstyle + "\">\r\n";
            } else if (globallen != 0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" + style=\"width:" + fl*10 + "\" class=\"" + contentstyle + "\">\r\n";
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\"" + " class=\"" + contentstyle + "\">\r\n";
            }
        } else {
            if (fieldlen !=0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" + style=\"width:" + fl*10 + "\">\r\n";
            } else if (globallen != 0) {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\" + style=\"width:" + fl*10 + "\" />\r\n";
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"" + name + "\" size=\"1\">\r\n";
            }
        }

        Buf = Buf +"<option value=\"\">请选择</option>";
        for(int i =0; i<tbuf.length; i++) {
            Buf = Buf +"<option value=\"" + tbuf[i] + "\">" + tbuf[i] + "</option>";
        }
        Buf = Buf + "</select>";

        if (isnull.equals("1"))
            Buf = Buf + "(<font color=\"red\">*</font>)</td>\r\n";
        else
            Buf = Buf + "</td>\r\n";
        Buf = Buf + "<td></td>\r\n";
        Buf = Buf + "</tr>\r\n";
        return Buf;
    }

    private String generateSubmitButton(int rest_exist_flag,String okimage,String cancelimage,String resetimage,String globalpromptstyle,int globallen,int fieldlen,String chinesename,String name,String fieldtype,String gcontentstyle,String contentstyle) {
        String Buf = "";
        Buf = Buf + "<tr><tr><td height=\"10\" colspan=\"3\"></td></tr><tr>\r\n";

        if (fieldtype.equals("image")) {
            Buf = Buf + "<td align=\"center\" colspan=\"3\">\r\n";
            if (okimage != null)
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" src=\"/_sys_images/buttons/" + okimage + "\" value=\"" + chinesename + "\" />\r\n";
            else
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"" + chinesename + "\" />\r\n";
            if (cancelimage != null)
                Buf = Buf + "<img src=\"/_sys_images/buttons/" + cancelimage + "\" value=\"返回\" onclick=\"javascript:window.close()\" />\r\n";
            else
                Buf = Buf + "<input name=\"cancel\" type=\"" + fieldtype + "\" value=\"返回\" onclick=\"javascript:window.close()\" />\r\n";
            if (rest_exist_flag == 1) {
                if (resetimage != null)
                    Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" src=\"/_sys_images/buttons/" + resetimage + "\" value=\"" + chinesename + "\" />\r\n";
                else
                    Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"重置\" onclick=\"javascript:window.close()\" />\r\n";
            }
            Buf = Buf + "</td>\r\n";
        } else {
            Buf = Buf + "<td align=\"center\" colspan=\"3\">\r\n";
            Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"" + chinesename + "\" />\r\n";
            Buf = Buf + "<input name=\"cancel\" type=\"" + fieldtype + "\" value=\"返回\" onclick=\"javascript:window.close()\" />\r\n";
            if (rest_exist_flag == 1)
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"重置\" onclick=\"javascript:window.close()\" />\r\n";
            Buf = Buf + "</td>\r\n";
        }

        Buf = Buf + "</tr>\r\n";
        return Buf;
    }

    private String generateCheckInputTR(String checktype,String globaldescalign,String globalpromptalign,String globalpromptstyle,int globallen,int fieldlen,int maxlen,String chinesename,String name,String fieldtype,String isnull,String gdescstyle,String gcontentstyle,String descstyle,String contentstyle) {
        String Buf = "";
        Buf = Buf + "<tr>\r\n";

        if (descstyle != null){
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + descstyle + "\">" + chinesename + "：</td>\r\n";
        }else if (gdescstyle != null)
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + gdescstyle + "\">" + chinesename + "：</td>\r\n";
        else
            Buf = Buf + "<td align=\"" + globaldescalign + "\">" + chinesename + "：</td>\r\n";

        if (gcontentstyle != null) {
            if (fieldlen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + gcontentstyle + "\" />";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + globallen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + gcontentstyle + "\" />";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + gcontentstyle + "\" />";
        } else if (contentstyle != null) {
            if (fieldlen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + contentstyle + "\" />";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + globallen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + contentstyle + "\" />";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + contentstyle + "\" />";
        } else {
            if (fieldlen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + fieldlen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" />";
            else if (globallen != 0)
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" size=\"" + globallen + "\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" />";
            else
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input id=\"" + name + "id\" onblur=\"javascript:check_" + checktype + "()\" maxlength=\"" + maxlen + "\" name=\"" + name + "\" type=\"" + fieldtype + "\" />";
        }

        if (isnull.equals("1"))
            Buf = Buf + "(<font color=\"red\">*</font>)</td>\r\n";
        else
            Buf = Buf + "</td>\r\n";
        if (globalpromptstyle != null)
            Buf = Buf + "<td width=\"20%\" align=\"" + globalpromptalign + "\"><div id=\"p_" + checktype + "\" class=\""+ globalpromptstyle + "\"></div></td>\r\n";
        else
            Buf = Buf + "<td width=\"20%\" align=\"" + globalpromptalign + "\"><div id=\"p_" + checktype + "\"></div></td>\r\n";
        Buf = Buf + "</tr>\r\n";

        return Buf;
    }

    private String generateSexTR(String globaldescalign,String globalpromptalign,String globalpromptstyle,int globallen,int fieldlen,String chinesename,String name,String fieldtype,String isnull,String gdescstyle,String gcontentstyle,String descstyle,String contentstyle,String pairvalue) {
        int posi = pairvalue.indexOf(":");
        String val1 = pairvalue.substring(0,posi);
        String val2 = pairvalue.substring(posi+1);
        int fl = 0;
        //设置字段的长度
        if (fieldlen != 0)
            fl = fieldlen/3;
        else if (globallen != 0)
            fl = globallen/3;

        String Buf = "";
        Buf = Buf + "<tr>\r\n";
        if (descstyle != null){
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + descstyle + "\">" + chinesename + "：</td>\r\n";
        }else if (gdescstyle != null)
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + gdescstyle + "\">"+chinesename + "：</td>\r\n";
        else
            Buf = Buf + "<td align=\"" + globaldescalign + "\">" + chinesename + "：</td>\r\n";

        if (gcontentstyle != null) {
            if (fieldtype.equals("text") )  {
                if (fieldlen !=0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" class=\"" + gcontentstyle + "\" />\r\n";
                else if (globallen != 0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" class=\"" + gcontentstyle + "\" />\r\n";
                else
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + gcontentstyle + "\" />\r\n";
            } else if (fieldtype.equals("select")) {
                if (fieldlen !=0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\" class=\"" + gcontentstyle + "\" />\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                } else if (globallen != 0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\" class=\"" + gcontentstyle + "\" />\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }else {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" />\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"0\" class=\"" + gcontentstyle + "\" />" + val1 + "&nbsp;&nbsp;\r\n";
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"1\" class=\"" + gcontentstyle + "\" />" + val2 + "&nbsp;&nbsp;\r\n";
            }
        } else if (contentstyle != null) {
            if (fieldtype.equals("text") )  {
                if (fieldlen !=0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" class=\"" + contentstyle + "\" />\r\n";
                else if (globallen != 0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" class=\"" + contentstyle + "\" />\r\n";
                else
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" class=\"" + contentstyle + "\" />\r\n";
            } else if (fieldtype.equals("select")) {
                if (fieldlen !=0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\" class=\"" + contentstyle + "\">\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                } else if (globallen != 0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\" class=\"" + contentstyle + "\">\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }else {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" class=\"" + contentstyle + "\">\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"0\" class=\"" + contentstyle + "\" />" + val1 + "&nbsp;&nbsp;\r\n";
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"1\" class=\"" + contentstyle + "\" />" + val2 + "&nbsp;&nbsp;\r\n";
            }
        } else {
            if (fieldtype.equals("text") )  {
                if (fieldlen !=0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + fieldlen + "\" />\r\n";
                else if (globallen != 0)
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" size=\"" + globallen + "\" />\r\n";
                else
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" />\r\n";
            } else if (fieldtype.equals("select")) {
                if (fieldlen !=0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\">\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                } else if (globallen != 0) {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" style=\"width: " + fl*10 + "px\">\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }else {
                    Buf = Buf + "<td width=\"50%\" align=\"left\"><select name=\"sex\" size=\"1\" />\r\n";
                    Buf = Buf + "<option value=\"0\">" + val1 + "</option>\r\n";
                    Buf = Buf + "<option value=\"1\">" + val2 + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }
            } else {
                Buf = Buf + "<td width=\"50%\" align=\"left\"><input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"0\" />" + val1 + "&nbsp;&nbsp;\r\n";
                Buf = Buf + "<input name=\"" + name + "\" type=\"" + fieldtype + "\" value=\"1\" />" + val2 + "&nbsp;&nbsp;\r\n";
            }
        }

        if (isnull.equals("1"))
            Buf = Buf + "(<font color=\"red\">*</font>)</td>\r\n";
        else
            Buf = Buf + "</td>\r\n";
        Buf = Buf + "<td></td>\r\n";
        Buf = Buf + "</tr>\r\n";
        return Buf;
    }

    private String generateDateTR(String globaldescalign,String globalpromptalign,String globalpromptstyle,int globallen,int fieldlen,String chinesename,String name,String fieldtype,String isnull,String gdescstyle,String gcontentstyle,String descstyle,String contentstyle) {
        String Buf = "";
        int fl = 0;

        //设置字段的长度
        if (fieldlen != 0)
            fl = fieldlen/3;
        else if (globallen != 0)
            fl = globallen/3;

        Buf = Buf + "<tr>\r\n";
        if (descstyle != null){
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + descstyle + "\">" + chinesename + "：</td>\r\n";
        }else if (gdescstyle != null)
            Buf = Buf + "<td align=\"" + globaldescalign + "\" class=\"" + gdescstyle + "\">"+chinesename + "：</td>\r\n";
        else
            Buf = Buf + "<td align=\"" + globaldescalign + "\">" + chinesename + "：</td>\r\n";

        if (gcontentstyle != null) {
            Buf = Buf + "<td width=\"50%\" align=\"left\">\r\n";
            if (fieldtype.equals("text") ){
                if (fl != 0) {                                  //gconetntstyle不为空 字段类型为TEXT类型
                    Buf = Buf + "<input name=\"year\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"2\" class=\"" + gcontentstyle + "\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"2\" class=\"" + gcontentstyle + "\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" type=\"" + fieldtype + "\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" type=\"" + fieldtype + "\" maxlength=\"2\" class=\"" + gcontentstyle + "\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" type=\"" + fieldtype + "\" maxlength=\"2\" class=\"" + gcontentstyle + "\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                }
            } else if (fieldtype.equals("select") ) {              //gconetntstyle不为空 字段类型为SELECT类型
                if (fl == 0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" type=\"text\" size=\"" + fl + "\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                }
            } else {                 //gconetntstyle不为空 字段类型为DATE类型
                if (fl == 0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" size=\"" + fl + "\" type=\"text\" maxlength=\"4\" class=\"" + gcontentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + gcontentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                }
            }
            if (isnull.equals("1"))  Buf = Buf + "(<font color=\"red\">*</font>)\r\n";
            if (fieldtype.equals("date") ) {
                Buf = Buf + "<input type=\"hidden\" name=\"sd\" value=\"\">";
                Buf = Buf + "<a href=\"JavaScript:opencalendar('/_commons/calendar.jsp?form=form&ip=sd&d=<%=d%>')\"";
                Buf = Buf + " onclick=\"setLastMousePosition(event)\" tabindex=\"3\"><img src=\"/_sys_images/date_picker.gif\" border=\"0\" width=\"34\" height=\"21\" align=\"absmiddle\" border=\"0\"></a>";
            }
            Buf = Buf + "</td>\r\n";
        } else if (contentstyle != null) {          //该字段的STYLE设置非空
            Buf = Buf + "<td width=\"50%\" align=\"left\">\r\n";
            if (fieldtype.equals("text") ){
                if (fl==0) {
                    Buf = Buf + "<input name=\"year\" type=\"" + fieldtype + "\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" type=\"" + fieldtype + "\" maxlength=\"2\" class=\"" + contentstyle + "\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" type=\"" + fieldtype + "\" maxlength=\"2\" class=\"" + contentstyle + "\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"2\" class=\"" + contentstyle + "\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" type=\"" + fieldtype + "\" size=\"" + fl + "\" maxlength=\"2\" class=\"" + contentstyle + "\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                }
            } else if (fieldtype.equals("select") ) {
                if (fl ==0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" type=\"text\" size=\"" + fl + "\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                }
            } else {
                if (fl == 0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" type=\"text\" size=\"" + fl + "\" maxlength=\"4\" class=\"" + contentstyle + "\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" style=\"width: " + fl*5 + "px\" class=\"" + contentstyle + "\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                }
            }
            if (isnull.equals("1"))  Buf = Buf + "(<font color=\"red\">*</font>)\r\n";
            if (fieldtype.equals("date") ) {
                Buf = Buf + "<input type=\"hidden\" name=\"sd\" value=\"\">";
                Buf = Buf + "<a href=\"JavaScript:opencalendar('/_commons/calendar.jsp?form=form&ip=sd&d=<%=d%>')\"";
                Buf = Buf + " onclick=\"setLastMousePosition(event)\" tabindex=\"3\"><img src=\"/_sys_images/date_picker.gif\" border=\"0\" width=\"34\" height=\"21\" align=\"absmiddle\" border=\"0\"></a>";
            }
            Buf = Buf + "</td>\r\n";
        } else {                                       //字段的STYLE设置和表单的STYLE设置都为空
            Buf = Buf + "<td width=\"50%\" align=\"left\">\r\n";
            if (fieldtype.equals("text") ){
                if (fl == 0) {
                    Buf = Buf + "<input name=\"year\" type=\"" + fieldtype + "\" maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" type=\"" + fieldtype + "\" maxlength=\"2\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" type=\"" + fieldtype + "\" maxlength=\"2\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" size=\"" + fl + "\" type=\"" + fieldtype + "\" maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"month\" size=\"" + fl + "\" type=\"" + fieldtype + "\" maxlength=\"2\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<input name=\"day\" size=\"" + fl + "\" type=\"" + fieldtype + "\" maxlength=\"2\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                }
            } else if (fieldtype.equals("select") ) {
                if (fl ==0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\"" + " maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\"" + " />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\"" + " />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" size=\"" + fl + "\" type=\"text\"" + " maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\"" + " style=\"width: " + fl*5 + "px\" />月&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +">" + i + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\"" + " style=\"width: " + fl*5 + "px\" />日&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>\r\n";
                }
            } else {
                if (fl ==0) {
                    Buf = Buf + "<input name=\"year\" type=\"text\" maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                } else {
                    Buf = Buf + "<input name=\"year\" size=\"" + fl + "\" type=\"text\" maxlength=\"4\" />年&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    Buf = Buf + "<select name=\"month\" size=\"1\" style=\"width: " + fl*5 + "px\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 13; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>月\r\n";
                    Buf = Buf + "<select name=\"day\" size=\"1\" style=\"width: " + fl*5 + "px\" />&nbsp;&nbsp;&nbsp;&nbsp;\r\n";
                    for (int i = 1; i < 32; i++)
                        Buf = Buf + "<option value=\"" + i +"\">" + i + "</option>\r\n";
                    Buf = Buf + "</select>日\r\n";
                }
            }

            if (isnull.equals("1"))  Buf = Buf + "(<font color=\"red\">*</font>)\r\n";
            if (fieldtype.equals("date") ) {
                Buf = Buf + "<input type=\"hidden\" name=\"sd\" value=\"\">";
                Buf = Buf + "<a href=\"JavaScript:opencalendar('/_commons/calendar.jsp?form=form&ip=sd&d=<%=d%>')\"";
                Buf = Buf + " onclick=\"setLastMousePosition(event)\" tabindex=\"3\"><img src=\"/_sys_images/date_picker.gif\" border=\"0\" width=\"34\" height=\"21\" align=\"absmiddle\" border=\"0\"></a>";
            }
            Buf = Buf + "</td>\r\n";
        }
        Buf = Buf + "<td></td>\r\n";
        Buf = Buf + "</tr>\r\n";
        return Buf;
    }
}