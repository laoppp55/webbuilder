<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-11-2
  Time: 下午10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.service.OrganizationService" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="com.bizwink.po.Department" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.po.Companyinfo" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int errcode = 0;
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    int siteid = authToken.getSiteID();
    int orgid = ParamUtil.getIntParameter(request,"orgid",0);
    String verifycode = ParamUtil.getParameter(request,"verifycode");

    ApplicationContext appContext = SpringInit.getApplicationContext();
    List<Companyinfo> alias_companyinfos = new ArrayList<Companyinfo>();
    List<Integer> alias_company_ids = new ArrayList<Integer>();
    List<Companyinfo> main_companyinfos = new ArrayList<Companyinfo>();
    Companyinfo companyinfo = null;
    if (appContext!=null) {
        OrganizationService organizationService = (OrganizationService)appContext.getBean("organizationService");
        alias_companyinfos = organizationService.getAliasCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
        main_companyinfos = organizationService.getMainCompaniesByOrgid(BigDecimal.valueOf(siteid),BigDecimal.valueOf(orgid));
    }

    if (alias_companyinfos.size()>0) {
        for(int ii=0;ii<alias_companyinfos.size();ii++) {
            alias_company_ids.add(alias_companyinfos.get(ii).getID().intValue());
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>修改组织架构信息--公司</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript">
        var errcode = <%=errcode%>;
        $(document).ready(function(){
            if (errcode == -1) {
                $("#yzmmsg").html("验证码输入错误，请重新输入验证码");
                $("#yzmmsg").css({color:"red"});
            } else if (errcode == -2) {
                $("#yzmmsg").html("运行环境初始化错误，请联系客服人员");
                $("#yzmmsg").css({color:"red"});
            } else if (errcode > 0) {
                alert("用户注册成功，系统跳转登陆页面");
                window.location.href="../index.jsp";
            }

            htmlobj=$.ajax({
                url:"getProvinces.jsp",
                dataType:'json',
                async:false,
                success:function(data){
                    //alert(data.length);
                    //alert(data[0].ID + ":" + data[0].PROVNAME + ":" + data[0].SELFCODE);
                    var selObj = $("#province");
                    var jj = 0;
                    for(jj=0; jj < data.length; jj++) {
                        var value=data[jj].ID;
                        var text=data[jj].PROVNAME;
                        selObj.append("<option value=\""+value+"\">"+text+"</option>");
                    }
                }
            });
        });

        function selcitys(val) {
            htmlobj=$.ajax({
                url:"getCitys.jsp?provinceid=" + val,
                dataType:'json',
                async:false,
                success:function(data){
                    var selObj = $("#city");
                    var jj = 0;

                    //清空原来的搜有值
                    var selOpt = $("#city option");
                    selOpt.remove();

                    //增加新的OPTION的值
                    selObj.append("<option value=\"01\">请选择市</option>");
                    for(jj=0; jj < data.length; jj++) {
                        var value=data[jj].ID;
                        var text=data[jj].CITYNAME;
                        selObj.append("<option value=\""+value+"\">"+text+"</option>");
                    }
                }
            });

            //清空区县选择
            $("#county option").remove();
            $("#county").append("<option value=\"01\">请选择区县</option>");
        }

        function selzones(val) {
            htmlobj=$.ajax({
                url:"getZones.jsp?cityid=" + val,
                dataType:'json',
                async:false,
                success:function(data){
                    //清空原来的搜有值
                    var selOpt = $("#zone option");
                    selOpt.remove();

                    //增加新的OPTION的值
                    var selObj = $("#county");
                    var jj = 0;
                    selObj.append("<option value=\"01\">请选择区县</option>");
                    for(jj=0; jj < data.length; jj++) {
                        var value=data[jj].ID;
                        var text=data[jj].ZONENAME;
                        selObj.append("<option value=\""+value+"\">"+text+"</option>");
                    }
                }
            });
        }

        function doSave(){
            var  compnum = compinfoform.newrownum.value;
            var jsoninfo = "";

            var compid = "";
            var mphone = "";
            var email = "";
            var compname = "";
            var shortcompname = "";
            var compcode = "";
            var legal = "";
            var tprovince = "";
            var tcity = "";
            var tcounty = "";
            var address = "";

            if (parseInt(compnum) > 0) {
                compid = compinfoform.maincompanyid.value;
                mphone = compinfoform.mphone.value;
                email = compinfoform.email.value;
                legal = compinfoform.legal.value;
                tprovince = compinfoform.tprovince.value;
                tcity = compinfoform.tcity.value;
                tcounty = compinfoform.tcounty.value;
                address = compinfoform.address.value;
                compname = compinfoform.compname.value;
                shortcompname = compinfoform.shortcompname.value;
                compcode = compinfoform.compcode.value;
                jsoninfo = "{\"total\":" + compnum + ",\"rows\":[\r\n";
                if (parseInt(compnum)>parseInt(1)) {
                    jsoninfo = jsoninfo + "{\"id\":" + compid + ",\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                            "\",\"shortcompname\":\"" + shortcompname + "\",\"compcode\":\"" + compcode + "\",\"legal\":\"" + legal +
                            "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                            "\",\"address\":\"" + address + "\"},\r\n";
                } else {
                    jsoninfo = jsoninfo + "{\"id\":" + compid + ",\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                            "\",\"shortcompname\":\"" + shortcompname + "\",\"compcode\":\"" + compcode + "\",\"legal\":\"" + legal +
                            "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                            "\",\"address\":\"" + address + "\"}\r\n";
                }

                <%
                int count = 0;
                for(int ii=0; ii<alias_company_ids.size(); ii++) {
                     int companyid = alias_company_ids.get(ii);
                     count = count + 1;
                %>
                var thecount = <%=count%>;
                var compid =<%=companyid%>;
                try {
                    compname = eval("compinfoform.compname"  + compid +".value");
                    shortcompname = eval("compinfoform.shortcompname"  + compid +".value");
                    compcode = eval("compinfoform.compcode"  + compid +".value");
                    if (thecount<<%=alias_company_ids.size()-1%>) {
                        jsoninfo = jsoninfo + "{\"id\":" + compid + ",\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                "\",\"shortcompname\":\"" + shortcompname + "\",\"compcode\":\"" + compcode + "\",\"legal\":\"" + legal +
                                "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                "\",\"address\":\"" + address + "\"},\r\n";
                    } else {
                        jsoninfo = jsoninfo + "{\"id\":" + compid + ",\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                "\",\"shortcompname\":\"" + shortcompname + "\",\"compcode\":\"" + compcode + "\",\"legal\":\"" + legal +
                                "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                "\",\"address\":\"" + address + "\"}\r\n";
                    }
                } catch(exception) {
                    alert(exception.message);
                }
                <%}%>
                jsoninfo = jsoninfo + "]}";
                compinfoform.compinfos.value = jsoninfo;
            }

            htmlobj=$.ajax({
                url:"updateOrgAndCompany.jsp",
                data:{
                    compinfos:  jsoninfo,
                    orgid:  compinfoform.orgid.value
                },
                dataType:'json',
                async:false,
                success:function(data){
                    //alert(data);
                    //alert(data[0].ID + ":" + data[0].PROVNAME + ":" + data[0].SELFCODE);
                    window.top.close();
                }
            });
        }

        function closewin(){
            window.top.close();
            window.top.opener.location.reload();
        }

        function addCompany() {
            var rowcount = parseInt(compinfoform.newrownum.value) + parseInt(1);
            var newRow = "<tr><td align=\"left\">公司名称:</td><td align=\"left\">" +
                    "<input class=\"easyui-textbox\" type=\"text\" id=\"name" + rowcount.toString(10) + "\" name=\"compname" + rowcount.toString(10) + "\" data-options=\"required:true\" /></td>" +
                    "</tr>";
            newRow = newRow + "<tr><td align=\"left\">公司简称:</td><td align=\"left\">"+
                    "<input class=\"easyui-textbox\" type=\"text\" id=\"sname" + rowcount.toString(10) + "\" name=\"shortcompname" + rowcount.toString(10) + "\" data-options=\"required:true\" /></td>" +
                    "</tr>";
            newRow = newRow + "<tr><td align=\"left\">社会统一信用代码:</td><td align=\"left\">" +
                    "<input class=\"easyui-textbox\" type=\"text\" id=\"code" + rowcount.toString(10) + "\" name=\"compcode" + rowcount.toString(10) + "\" data-options=\"required:true\" /></td>" +
                    "</tr>";
            newRow = newRow + "<tr><td colspan=\"2\"><hr size=\"1\" color=\"red\" /></td></tr>";
            compinfoform.newrownum.value = rowcount;
            //alert(newRow);
            $('#compContainer').append(newRow);
            //$.parser.parse();
        }
    </script>
</head>
<body style="text-align: center">
<div title="创建组织架构" style="width:900px;">
    <div id="thecomp" style="width:800px;">
        <form name="compinfoform">
            <input type="hidden" name="newrownum" value="<%=(alias_companyinfos.size() + 1)%>">
            <input type="hidden" name="compinfos" value="">
            <input type="hidden" name="orgid" value="<%=orgid%>">
            <input type="hidden" name="maincompanyid" value="<%=main_companyinfos.get(0).getID().intValue()%>">

            <table id="compContainer" cellpadding="5" align="center">
                <tr>
                    <td align="left">公司名称:</td>
                    <td align="left"><input type="text" name="compname" value="<%=main_companyinfos.get(0).getCOMPANYNAME()%>"/><!--a href="#" onclick="javascript:addCompany();">增加公司别名</a--></td>
                </tr>
                <tr>
                    <td align="left">公司简称:</td>
                    <td align="left"><input type="text" name="shortcompname" value="<%=main_companyinfos.get(0).getCOMPSHORTNAME()%>"/></td>
                </tr>
                <tr>
                    <td align="left">社会统一信用代码:</td>
                    <td align="left"><input type="text" name="compcode" value="<%=main_companyinfos.get(0).getCOMPCODE()%>"/></td>
                </tr>
                <tr>
                    <td align="left">联系人手机:</td>
                    <td align="left"><input type="text" name="mphone" value="<%=main_companyinfos.get(0).getMPHONE()%>"/></td>
                </tr>
                <tr>
                    <td align="left">邮件地址:</td>
                    <td align="left"><input type="text" name="email" value="<%=main_companyinfos.get(0).getCOMPANYEMAIL()%>"/></td>
                </tr>
                <tr>
                    <td align="left">法人姓名:</td>
                    <td align="left"><input type="text" name="legal" value="<%=main_companyinfos.get(0).getLEGAL()%>"/></td>
                </tr>
                <tr>
                    <td align="left" colspan="2">
                        <select id="province" name="tprovince" style="margin-right:6px;" onchange="javascript:selcitys(this.value)">
                            <option value="01">请选择省</option>
                        </select>
                        <select id="city" name="tcity" style="margin-right:6px;" onchange="javascript:selzones(this.value)">
                            <option value="01">请选择市</option>
                        </select>
                        <select id="county" name="tcounty" style="margin-right:6px;">
                            <option value="01">请选择县</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="left">详细地址:</td>
                    <td align="left"><input name="address" style="width:280px" value="<%=(main_companyinfos.get(0).getCOMPANYADDRESS()==null)?"":main_companyinfos.get(0).getCOMPANYADDRESS()%>"/></td>
                </tr>
                <tr><td colspan="2"><hr size="1" color="red" /></td></tr>
                <%
                    for(int ii=0;ii<alias_companyinfos.size(); ii++) {
                        companyinfo = alias_companyinfos.get(ii);
                %>
                <tr>
                    <td align="left">公司名称:</td>
                    <td align="left"><input type="text" name="compname<%=companyinfo.getID().intValue()%>" value="<%=companyinfo.getCOMPANYNAME()%>"/><!--a href="#" onclick="javascript:addCompany();">增加公司别名</a--></td>
                </tr>
                <tr>
                    <td align="left">公司简称:</td>
                    <td align="left"><input type="text" name="shortcompname<%=companyinfo.getID().intValue()%>" value="<%=companyinfo.getCOMPSHORTNAME()%>"/></td>
                </tr>
                <tr>
                    <td align="left">社会统一信用代码:</td>
                    <td align="left"><input type="text" name="compcode<%=companyinfo.getID().intValue()%>" value="<%=companyinfo.getCOMPCODE()%>"/></td>
                </tr>
                <%}%>
            </table>
            <div style="text-align:center;padding:5px">
                <input type="button" name="save" value="保存" onclick="javascript:doSave();">
                <input type="button" name="cancel" value="返回" onclick="javascript:closewin();">
            </div>
        </form>
    </div>
</div>
</body>
</html>
