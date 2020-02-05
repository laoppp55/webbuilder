<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.webapps.register.*" %>
<%@ page import="java.util.*" %>
<%@page contentType="text/html;charset=gbk" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int id = ParamUtil.getIntParameter(request, "id", 0);
    Uregister reg = new Uregister();
    IUregisterManager regMgr = UregisterPeer.getInstance();
    reg = regMgr.getByIdrsbt(id);
    int siteid = reg.getSiteid();
    String password = reg.getPassword();
    String guid = "bcd";
    String userid = ParamUtil.getParameter(request,"userid");
    String org_gode = ParamUtil.getParameter(request,"org_gode");
    String org_name = ParamUtil.getParameter(request,"org_name");
    String org_area_code = ParamUtil.getParameter(request,"org_area_code");
    String org_sys_code = ParamUtil.getParameter(request,"org_sys_code");
    String org_type = ParamUtil.getParameter(request,"org_type");
    String org_link_person = ParamUtil.getParameter(request,"org_link_person");
    String org_person_id = ParamUtil.getParameter(request,"org_person_id");
    String org_sup_code = ParamUtil.getParameter(request,"org_sup_code");
    String org_addr = ParamUtil.getParameter(request,"org_addr");
    String org_post = ParamUtil.getParameter(request,"org_post");
    String org_phone = ParamUtil.getParameter(request,"org_phone");
    String org_mob_phone = ParamUtil.getParameter(request,"org_mob_phone");
    String org_fax = ParamUtil.getParameter(request,"org_fax");
    String org_bank = ParamUtil.getParameter(request,"org_bank");
    String org_account_name = ParamUtil.getParameter(request,"org_account_name");
    String org_account = ParamUtil.getParameter(request,"org_account");
    int org_hostility = ParamUtil.getIntParameter(request,"org_hostility",0);
    String org_web_site = ParamUtil.getParameter(request,"org_web_site");
    String org_mail = ParamUtil.getParameter(request,"org_mail"); 

    if(startflag == 1){
        reg.setSiteid(siteid);
        reg.setPassword(password);
        reg.setGuid(guid);
        reg.setMemberid(userid);
        reg.setOrggode(org_gode);
        reg.setOrgname(org_name);
        reg.setOrgareacode(org_area_code);
        reg.setOrgsyscode(org_sys_code);
        reg.setOrgtype(org_type);
        reg.setOrglinkperson(org_link_person);
        reg.setOrgpersonid(org_person_id);
        reg.setOrgsupcode(org_sup_code);
        reg.setOrgaddr(org_addr);
        reg.setOrgpost(org_post);
        reg.setOrgphone(org_phone);
        reg.setOrgmobphone(org_mob_phone);
        reg.setOrgfax(org_fax);
        reg.setOrgbank(org_bank);
        reg.setOrgaccountname(org_account_name);
        reg.setOrgaccount(org_account);
        reg.setOrghostility(org_hostility);
        reg.setOrgwebsite(org_web_site);
        reg.setOrgmail(org_mail);
        regMgr.updateRsbt(reg,id);
        response.sendRedirect("index.jsp");
    }

    List list1 = new ArrayList();
    List list2 = new ArrayList();
    List list3 = new ArrayList();
    list1 = regMgr.getAllOrg1();
    list2 = regMgr.getAllOrg2();
    list3 = regMgr.getAllOrg3();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>修改页面</title>
    <link href="style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" type="text/javascript">
       function check() {
            if (document.form1.org_gode.value == "") {
                alert('请填写组织机构代码！');
                return false;
            }
            if (document.form1.org_name.value == "") {
                alert('请填写组织机构名称！');
                return false;
            }
            if (document.form1.org_area_code.value == "") {
                alert('请填写地区代码！');
                return false;
            }
            if (document.form1.org_sys_code.value == "") {
                alert('请填写系统/行业代码！');
                return false;
            }
            if (document.form1.org_type.value == "") {
                alert('请填写单位类型！');
                return false;
            }
            if (document.form1.org_link_person.value == "") {
                alert('请填写单位联系人！');
                return false;
            }
            if (document.form1.org_person_id.value == "") {
                alert('请填写联系人身份证号码！');
                return false;
            }
            if (document.form1.org_addr.value == "") {
                alert('请填写组织机构地址！');
                return false;
            }
            if (document.form1.org_post.value == "") {
                alert('请填写组织机构邮编！');
                return false;
            }
            if (document.form1.org_phone.value == "") {
                alert('请填写联系电话！');
                return false;
            }
            if (document.form1.org_hostility.value == "") {
                alert('请填写设台单位性质！');
                return false;
            }
            if (form1.org_mail.value != "")
            {
                if (form1.org_mail.value.length > 100)
                {
                    window.alert("email地址长度不能超过100位!");
                    return false;
                }

                var regu = "^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$"
                var re = new RegExp(regu);
                if (form1.org_mail.value.search(re) != -1) {
                    return true;
                } else {
                    window.alert("请输入有效合法的电子邮件 ！")
                    return false;
                }
            }
            if (document.form1.org_mail.value == "") {
                alert('请填写电子邮箱！');
                return false;
            }
            return true;
        }
    </script>
</head>

<body>
<form name=form1 method="post" action="edit.jsp">
    <input type="hidden" name="nameflag" value="0"/>
    <input type="hidden" name="startflag" value="1"/>  
    <input type=hidden name="id" value="<%=id%>">
    <table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td align="center" valign="top">
                <table width="700" border="0" cellpadding="0" cellspacing="6" class="paddingtrlb">
                    <tr>
                        <td height="30" align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
                        <td align="left" valign="top" bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
                    </tr>
                    <tr>
                        <td height="30" colspan="3" align="center" valign="top" bgcolor="#FFFFFF" class="fonttitle">企业用户注册&nbsp; </td>
                    </tr>
                    <tr>
                        <td width="44%" align="right" valign="middle" bgcolor="#FFFFFF">用户ID：</td>
                        <td width="56%" height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="userid" type="text" class="textborder" value="<%=reg.getMemberid()%>" readonly/>
                        </label></td>
                        <td width="60%" align="left">
                            <div style="width:300px" id="usernameflag">请输入您要使用的用户名</div>
                        </td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_gode" type="text" class="textborder" value="<%=reg.getOrggode()==null?"":reg.getOrggode()%>"/>
                        </label></td>
                        <td align="left">请输入组织机构代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构名称：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_name" type="text" class="textborder" value="<%=reg.getOrgname()==null?"":reg.getOrgname()%>" />
                        </label></td>
                        <td align="left">请输入组织机构名称。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">地区代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_area_code" type="text" class="textborder" value="<%=reg.getOrgareacode()==null?"":reg.getOrgareacode()%>" readonly />
                        </label></td>
                        <td align="left">请输入地区代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">系统/行业代码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_sys_code">
                                <option value="-1">选择系统/行业代码
                                <%
                                        if(list1 != null){
                                            for (int i = 0; i < list1.size(); i++) {
                                                int value = 0;
                                                String name = "";
                                                Uregister re = (Uregister)list1.get(i);
                                                value = re.getValue();
                                                name = re.getName();
                                        %>
                                <OPTION VALUE="<%=value%>" <%if(reg.getOrgsyscode()!=null){if(Integer.parseInt(reg.getOrgsyscode()) == value ){%>selected<%}}%> ><%=name%>
                                </OPTION>
                                <%}}%>
                            </select>
                        </label></td>
                        <td align="left">请输入系统/行业代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">单位类型：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_type">
                                <option value="-1" selected>选择单位类型
                                <%
                                        if(list2 != null){
                                            for (int i = 0; i < list2.size(); i++) {
                                                int value = 0;
                                                String name = "";
                                                Uregister re = (Uregister)list2.get(i);
                                                value = re.getValue();
                                                name = re.getName();
                                        %>
                                <OPTION VALUE="<%=value%>" <%if(reg.getOrgtype()!=null){if(Integer.parseInt(reg.getOrgtype()) == value ){%>selected<%}}%> ><%=name%>
                                </OPTION>
                                <%}}%>
                            </select>
                        </label></td>
                        <td align="left">请输入单位类型。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">单位联系人：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_link_person" type="text" class="textborder" value="<%=reg.getOrglinkperson()==null?"":reg.getOrglinkperson()%>" />
                        </label></td>
                        <td align="left">请输入单位联系人。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">联系人身份证号码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_person_id" type="text" class="textborder" value="<%=reg.getOrgpersonid()==null?"":reg.getOrgpersonid()%>"/>
                        </label></td>
                        <td align="left">请输入联系人身份证号码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">上级组织机构：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_sup_code" type="text" class="textborder" value="<%=reg.getOrgsupcode()==null?"":reg.getOrgsupcode()%>"/>
                        </label></td>
                        <td align="left">        请输入地区代码。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构地址：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_addr" type="text" class="textborder" value="<%=reg.getOrgaddr()==null?"":reg.getOrgaddr()%>"/>
                        </label></td>
                        <td align="left">请输入组织机构地址。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构邮编：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_post" type="text" class="textborder" value="<%=reg.getOrgpost()==null?"":reg.getOrgpost()%>" />
                        </label></td>
                        <td align="left">请输入组织机构邮编。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">联系电话：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_phone" type="text" class="textborder" value="<%=reg.getOrgphone()==null?"":reg.getOrgphone()%>" />
                        </label></td>
                        <td align="left">请输入联系电话。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">手机号码：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_mob_phone" type="text" class="textborder" value="<%=reg.getOrgmobphone()==null?"":reg.getOrgmobphone()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">组织机构传真：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_fax" type="text" class="textborder" value="<%=reg.getOrgfax()==null?"":reg.getOrgfax()%>"/>
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">开户银行：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_bank" type="text" class="textborder" value="<%=reg.getOrgbank()==null?"":reg.getOrgbank()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">账户名称：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_account_name" type="text" class="textborder" value="<%=reg.getOrgaccountname()==null?"":reg.getOrgaccountname()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">银行账号：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_account" type="text" class="textborder" value="<%=reg.getOrgaccount()==null?"":reg.getOrgaccount()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">设台单位性质：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <select name="org_hostility">
                                <option value="-1" selected>选择设台单位性质
                                <%
                                        if(list3 != null){
                                            for (int i = 0; i < list3.size(); i++) {
                                                int value = 0;
                                                String name = "";
                                                Uregister re = (Uregister)list3.get(i);
                                                value = re.getValue();
                                                name = re.getName();
                                        %>
                                <OPTION VALUE="<%=value%>" <%if(reg.getOrghostility() == value ){%>selected<%}%> ><%=name%>
                                </OPTION>
                                <%}}%>
                            </select>
                        </label></td>
                        <td align="left">请输入设台单位性质。</td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">网址：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_web_site" type="text" class="textborder" value="<%=reg.getOrgwebsite()==null?"":reg.getOrgwebsite()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="middle" bgcolor="#FFFFFF">电子邮箱：</td>
                        <td height="35" align="left" valign="middle" bgcolor="#FFFFFF"><label>
                            <input name="org_mail" type="text" class="textborder" value="<%=reg.getOrgmail()==null?"":reg.getOrgmail()%>" />
                        </label></td>
                    </tr>
                    <tr>
                        <td align="right" valign="bottom" bgcolor="#FFFFFF">&nbsp;</td>
                        <td height="25" align="left" valign="bottom" bgcolor="#FFFFFF" colspan="2"><label>
                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="submit" name="Submit" value="修改" onclick="check()"/>
                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="button" name="goto" value="返回" onclick=javascript:history.go(-1);>
                        </label></td>
                    </tr>
                    <tr>
                        <td height="30" align="left" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
                        <td align="left" valign="top" bgcolor="#FFFFFF" colspan="2">&nbsp;</td>
                    </tr>
                </table>
            </td>
            <td>&nbsp;</td>
        </tr>
    </table>
</form>
</body>
</html>
