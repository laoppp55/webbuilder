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
    int customer = authToken.getSiteID();
    int pid = ParamUtil.getIntParameter(request,"pid",0);
    String verifycode = ParamUtil.getParameter(request,"verifycode");
%>
<!DOCTYPE html>
<html>
<head>
    <title>增加单位信息</title>
    <meta charset="UTF-8">
    <meta http-equiv="pragma" content="no-cache" />
    <link rel="stylesheet" type="text/css" href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../design/layui.js"></script>
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
                    var selObj = $("#provinceid");
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
                    var selObj = $("#cityid");
                    var jj = 0;

                    //清空原来的搜有值
                    var selOpt = $("#cityid option");
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
            $("#zone option").remove();
            //$("#zoneid").append("<option value=\"01\">请选择区县</option>");
        }

        function selzones(val) {
            htmlobj=$.ajax({
                url:"getZones.jsp?cityid=" + val,
                dataType:'json',
                async:false,
                success:function(data){
                    //清空原来的搜有值
                    var selOpt = $("#zoneid option");
                    selOpt.remove();

                    //增加新的OPTION的值
                    var selObj = $("#zoneid");
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

            var tprovince = "";            //公司所在省
            var tcity = "";                //公司所在市
            var tcounty = "";              //公司所在县
            var address = "";              //公司详细地址
            var postcode = "";             //公司邮政编码
            var contactor = "";            //公司联系人
            var mphone = "";               //公司联系人手机号码
            var phone = "";                //公司联系电话号码
            var email = "";                //公司联系人电子邮件
            var legal = "";                //公司负责人姓名

            var compname = "";             //公司名称
            var shortcompname = "";        //公司简称
            var engcompname = "";          //公司英文名称
            var creditcode = "";           //公司统一社会信用代码
            var compcode = "";             //公司内部编码
            var comptype = "";             //公司类型

            if (parseInt(compnum) > 0) {
                tprovince = compinfoform.province.value;
                tcity = compinfoform.city.value;
                tcounty = compinfoform.zone.value;
                address = compinfoform.address.value;
                postcode = compinfoform.postcode.value;
                contactor = compinfoform.contactor.value;
                mphone = compinfoform.mphone.value;
                phone = compinfoform.areacode.value + "-" + compinfoform.phone.value + "-" + compinfoform.extnumber.value;
                email = compinfoform.email.value;
                legal = compinfoform.manager.value;
                jsoninfo = "{\"total\":" + compnum + ",\"rows\":[\r\n";
                for(var ii=0; ii<compnum; ii++) {
                    var lineno = parseInt(ii) + parseInt(1);
                    try {
                        if (parseInt(lineno)==parseInt(1)) {
                            compname = compinfoform.compname.value;
                            shortcompname = compinfoform.shortcompname.value;
                            engcompname = compinfoform.engcompname.value;
                            creditcode = compinfoform.creditcode.value;
                            compcode = compinfoform.compcode.value;
                            comptype = compinfoform.relatetype.value;
                            if (parseInt(compnum)>parseInt(1)) {
                                jsoninfo = jsoninfo + "{\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                    "\",\"shortcompname\":\"" + shortcompname + "\",\"engcompname\":\"" + engcompname + "\",\"compcode\":\"" + compcode +
                                    "\",\"creditcode\":\"" + creditcode + "\",\"comptype\":\"" + comptype +"\",\"legal\":\"" + legal +
                                    "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                    "\",\"address\":\"" + address + "\",\"postcode\":\"" + postcode + "\",\"contactor\":\"" + contactor +
                                    "\",\"phone\":\"" + phone + "\"},\r\n";
                            } else {
                                jsoninfo = jsoninfo + "{\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                    "\",\"shortcompname\":\"" + shortcompname + "\",\"engcompname\":\"" + engcompname + "\",\"compcode\":\"" + compcode +
                                    "\",\"creditcode\":\"" + creditcode + "\",\"comptype\":\"" + comptype +"\",\"legal\":\"" + legal +
                                    "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                    "\",\"address\":\"" + address + "\",\"postcode\":\"" + postcode + "\",\"contactor\":\"" + contactor +
                                    "\",\"phone\":\"" + phone + "\"}\r\n";
                            }
                        } else if (parseInt(lineno)>parseInt(1)) {
                            compname = eval("compinfoform.compname"  + lineno +".value");
                            shortcompname = eval("compinfoform.shortcompname"  + lineno +".value");
                            engcompname = eval("compinfoform.engcompname"  + lineno + ".value");
                            creditcode = eval("compinfoform.creditcode" + lineno + ".value");
                            compcode = eval("compinfoform.compcode"  + lineno +".value");
                            comptype = eval("compinfoform.relatetype" + lineno + ".value");
                            if (ii<parseInt(compnum)-parseInt(1)) {
                                jsoninfo = jsoninfo + "{\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                    "\",\"shortcompname\":\"" + shortcompname + "\",\"engcompname\":\"" + engcompname + "\",\"compcode\":\"" + compcode +
                                    "\",\"creditcode\":\"" + creditcode + "\",\"comptype\":\"" + comptype +"\",\"legal\":\"" + legal +
                                    "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                    "\",\"address\":\"" + address + "\",\"postcode\":\"" + postcode + "\",\"contactor\":\"" + contactor +
                                    "\",\"phone\":\"" + phone + "\"},\r\n";
                            } else {
                                jsoninfo = jsoninfo + "{\"mphone\":\"" + mphone + "\",\"email\":\"" + email + "\",\"compname\":\"" + compname +
                                    "\",\"shortcompname\":\"" + shortcompname + "\",\"engcompname\":\"" + engcompname + "\",\"compcode\":\"" + compcode +
                                    "\",\"creditcode\":\"" + creditcode + "\",\"comptype\":\"" + comptype +"\",\"legal\":\"" + legal +
                                    "\",\"tprovince\":\"" + tprovince + "\",\"tcity\":\"" + tcity + "\",\"tcounty\":\"" + tcounty +
                                    "\",\"address\":\"" + address + "\",\"postcode\":\"" + postcode + "\",\"contactor\":\"" + contactor +
                                    "\",\"phone\":\"" + phone + "\"}\r\n";
                            }
                        }
                    } catch(exception) {
                        alert(exception.message);
                    }
                }
                jsoninfo = jsoninfo + "]}";
                compinfoform.compinfos.value = jsoninfo;
            }

            alert(compinfoform.compinfos.value);

            htmlobj=$.ajax({
                url:"saveOrgAndCompany.jsp",
                data:{
                    compinfos:  encodeURI(jsoninfo),
                    pid:  compinfoform.pid.value
                },
                dataType:'json',
                async:false,
                success:function(data){
                    alert(data.error.errcode);
                    window.top.close();
                    window.top.opener.location.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                    alert(jqXHR.status);
                    alert(jqXHR.readyState);
                    alert(jqXHR.statusText);
                    alert(textStatus);
                    alert(errorThrown);
                }
            });
        }

        function closewin(){
            window.top.close();
            window.top.opener.location.reload();
        }

        function addCompany() {
            var rowcount = parseInt(compinfoform.newrownum.value) + parseInt(1);
            var newRow = "<tr id=\"trname" + rowcount.toString(10) + "\"><td align=\"right\">单位名称:</td><td align=\"left\">" +
                "<input class=\"easyui-textbox\" type=\"text\" id=\"name" + rowcount.toString(10) + "\" name=\"compname" + rowcount.toString(10) + "\" data-options=\"required:true\" style=\"width:280px\" /></td>" +
                "</tr>";
            newRow = newRow + "<tr id=\"trsname" + rowcount.toString(10) + "\"><td align=\"right\">单位简称:</td><td align=\"left\">"+
                "<input class=\"easyui-textbox\" type=\"text\" id=\"sname" + rowcount.toString(10) + "\" name=\"shortcompname" + rowcount.toString(10) + "\" data-options=\"required:true\" style=\"width:280px\" /></td>" +
                "</tr>";
            newRow = newRow + "<tr id=\"trename" + rowcount.toString(10) + "\"><td align=\"right\">单位英文名称:</td><td align=\"left\">"+
                "<input class=\"easyui-textbox\" type=\"text\" id=\"ename" + rowcount.toString(10) + "\" name=\"engcompname" + rowcount.toString(10) + "\" data-options=\"required:true\" style=\"width:280px\" /></td>" +
                "</tr>";
            newRow = newRow + "<tr id=\"trxycode" + rowcount.toString(10) + "\"><td align=\"right\">社会统一信用代码:</td><td align=\"left\">" +
                "<input class=\"easyui-textbox\" type=\"text\" id=\"xycode" + rowcount.toString(10) + "\" name=\"creditcode" + rowcount.toString(10) + "\" data-options=\"required:true\" style=\"width:280px\" /></td>" +
                "</tr>";
            newRow = newRow + "<tr id=\"trnbcode" + rowcount.toString(10) + "\"><td align=\"right\">内部编码:</td><td align=\"left\">" +
                "<input class=\"easyui-textbox\" type=\"text\" id=\"nbcode" + rowcount.toString(10) + "\" name=\"compcode" + rowcount.toString(10) + "\" data-options=\"required:true\" style=\"width:280px\" /></td>" +
                "</tr>";
            newRow = newRow + "<tr id=\"trraltype" + rowcount.toString(10) + "\"><td align=\"right\">关系类型:</td><td align=\"left\">" +
                "<select  name=\"relatetype" +  + rowcount.toString(10) +"\" id=\"raltype" + rowcount.toString(10) + "\" " + "style=\"width:160px\">" +
                "    <option value=\"0\">请选择</option>" +
                "    <option value=\"1\">分公司</option>" +
                "    <option value=\"2\">子公司</option>" +
                "    <option value=\"3\">控股公司</option>" +
                "    <option value=\"4\">参股公司</option>" +
                "    <option value=\"5\">派驻机构</option>" +
                "   <option value=\"6\">其它</option>" +
                "</select>"+
                "</td>" +
                "</tr>";
            newRow = newRow + "<tr><td colspan=\"2\"><hr size=\"1\" color=\"red\" /></td></tr>";
            compinfoform.newrownum.value = rowcount;
            $('#compContainer').append(newRow);
            //$.parser.parse();
        }
    </script>
</head>
<body style="text-align: center">
<div title="创建组织架构" style="width:900px;">
    <div id="thecomp" style="width:800px;">
        <form name="compinfoform">
            <input type="hidden" name="newrownum" value="1">
            <input type="hidden" name="compinfos" value="">
            <input type="hidden" name="pid" value="<%=pid%>">

            <table id="compContainer" cellpadding="5" align="center">
                <tr>
                    <td align="right">单位全称:</td>
                    <td align="left"><input type="text" name="compname" style="width:280px" class="layui-input" /><a href="#" onclick="javascript:addCompany();">增加单位别名</a></td>
                </tr>
                <tr>
                    <td align="right">单位简称:</td>
                    <td align="left"><input type="text" name="shortcompname" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">单位英文名称:</td>
                    <td align="left"><input type="text" name="engcompname" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">社会统一信用代码:</td>
                    <td align="left"><input type="text" name="creditcode" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">单位内部编码:</td>
                    <td align="left"><input type="text" name="compcode" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">负责人姓名:</td>
                    <td align="left"><input type="text" name="manager" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">关系类型:</td>
                    <td align="left">
                        <select  name="relatetype" style="width:160px" lay-verify="required">
                            <option value="0">请选择</option>
                            <option value="1">分公司</option>
                            <option value="2">子公司</option>
                            <option value="3">控股公司</option>
                            <option value="4">参股公司</option>
                            <option value="5">派驻机构</option>
                            <option value="6">其它</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">所在地址:</td>
                    <td align="left">
                        <select style="width: auto;" name="province" id="provinceid" lay-verify="required" onchange="javascript:selcitys(this.value)">
                            <option value="0">选择省/市</option>
                        </select>
                        <select style="width: auto;" name="city" id="cityid" lay-verify="required" onchange="javascript:selzones(this.value)">
                            <option value="0">选择市/区</option>
                        </select>
                        <select style="width: auto;" name="zone" id="zoneid" lay-verify="required">
                            <option value="0">选择区/县</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td align="right">详细地址:</td>
                    <td align="left"><input name="address" style="width:280px" class="layui-input"  /></td>
                </tr>
                <tr>
                    <td align="right">邮政编码:</td>
                    <td align="left"><input type="text" name="postcode" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">联系人姓名:</td>
                    <td align="left"><input type="text" name="contactor" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">联系人手机号:</td>
                    <td align="left"><input type="text" name="mphone" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">联系电话:</td>
                    <td align="left"><input type="text" name="areacode" style="width:40px" class="layui-input" />-<input type="text" name="phone" style="width:160px" class="layui-input" />-<input type="text" name="extnumber" style="width:40px" class="layui-input" /></td>
                </tr>
                <tr>
                    <td align="right">电子邮箱:</td>
                    <td align="left"><input type="text" name="email" style="width:280px" class="layui-input" /></td>
                </tr>
                <tr><td colspan="2"><hr size="1" color="red" /></td></tr>
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
