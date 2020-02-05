<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.toolkit.company.*" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int comtype = ParamUtil.getIntParameter(request, "companytype", -1);
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    if (startflag == 1) {
        int companytype = ParamUtil.getIntParameter(request, "companytype", -1);
        String companyname = ParamUtil.getParameter(request, "companyname");
        String companyaddress = ParamUtil.getParameter(request, "companyaddress");
        String companyphone = ParamUtil.getParameter(request, "companyphone");
        String companyfax = ParamUtil.getParameter(request, "companyfax");
        String companywebsite = ParamUtil.getParameter(request, "companywebsite");
        String companyemail = ParamUtil.getParameter(request, "companyemail");
        String postcode = ParamUtil.getParameter(request, "postcode");
        String districtnumber = ParamUtil.getParameter(request, "districtnumber");
        String classification = ParamUtil.getParameter(request, "classification");
        String area = ParamUtil.getParameter(request, "area");
        String summary = ParamUtil.getParameter(request, "summary");

        Company company = new Company();
        company.setCompanyname(companyname);
        company.setCompanyaddress(companyaddress);
        company.setCompanyphone(companyphone);
        company.setCompanyfax(companyfax);
        company.setCompanywebsite(companywebsite);
        company.setCompanyemail(companyemail);
        company.setPostcode(postcode);
        company.setDistrictnumber(districtnumber);
        company.setClsaaification(classification);
        company.setArea(area);
        company.setSummary(summary);
        company.setStation(companytype);

        ICompanyManager comMgr = CompanyPeer.getInstance();
        comMgr.addCompany(company);
        response.sendRedirect("index.jsp?companytype=" + companytype);
    }
%>
<html>
<head>
    <title>公司添加页面</title>
    <style type="text/css">
        td {
            font-size: 12px
        }
    </style>
    <script type="text/javascript">
        function check() {
            var companyname = document.getElementById("companyname").value;
            if (companyname == "") {
                alert("公司名称不能为空");
                document.getElementById("companyname").focus();
                return false;
            }

            var companyaddress = document.getElementById("companyaddress").value;
            if (companyaddress == "") {
                alert("公司地址不能为空");
                document.getElementById("companyaddress").focus();
                return false;
            }

            var companyphone = document.getElementById("companyphone").value;
            if (companyphone == "") {
                alert("公司电话不能为空");
                document.getElementById("companyphone").focus();
                return false;
            }


            if (companyphone != "") {
                var filter = /^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$/;
                flag = filter.test(companyphone);
                if (!flag) {
                    alert("电话号码有误，请重新输入！");
                    document.getElementById("companyphone").focus();
                    return false;
                }
            }

            var districtnumber = document.getElementById("districtnumber").value;
            if (districtnumber == "") {
                alert("区号不能为空");
                document.getElementById("districtnumber").focus();
                return false;
            }

            var area = document.getElementById("area").value;
            if (area == "") {
                alert("地区不能为空");
                document.getElementById("area").focus();
                return false;
            }

            var companyemail = document.getElementById("companyemail").value;
            if (companyemail != "") {
                var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                flag = reg.test(companyemail);
                if (!flag) {
                    alert("邮箱格式不正确！");
                    document.getElementById("companyemail").focus();
                    return false;
                }
            }

            var postcode = document.getElementById("postcode").value;
            if (postcode != "") {   //邮政编码判断
                var pattern = /^[0-9]{6}$/;
                flag = pattern.test(postcode);
                if (!flag) {
                    alert("非法的邮政编码！")
                    document.getElementById("postcode").focus();
                    return false;
                }
            }


        }
    </script>
</head>
<body>
<center>
<table>
<form name="addForm" action="addCompany.jsp" method="post" onsubmit="javascript:return check();">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="companytype" value="<%=comtype%>">
<tr>
    <td>公司名称:</td>
    <td><input type="text" name="companyname"></td>
</tr>
<tr>
    <td>地址:</td>
    <td><input type="text" name="companyaddress"></td>
</tr>
<tr>
    <td>电话:</td>
    <td><input type="text" name="companyphone"></td>
</tr>
<tr>
    <td>传真:</td>
    <td><input type="text" name="companyfax"></td>
</tr>
<tr>
    <td>网址:</td>
    <td><input type="text" name="companywebsite"></td>
</tr>
<tr>
    <td>邮箱:</td>
    <td><input type="text" name="companyemail"></td>
</tr>
<tr>
    <td>邮编:</td>
    <td><input type="text" name="postcode" size="5"></td>
</tr>
<tr>
    <td>区号:</td>
    <td><input type="text" name="districtnumber"></td>
</tr>
<tr>
    <td>分类:</td>
    <td><input type="text" name="classification"></td>
</tr>
<tr>
<td>地区:</td>

<td><select name="area">

<option value="北京">
    北京
</option>
<option value="上海">
    上海
</option>
<option value="天津">
    天津
</option>
<option value="重庆">
    重庆
</option>
<option value="四川">
    四川
</option>
<option value="贵州">
    贵州
</option>
<option value="广东">
    广东
</option>
<option value="浙江">
    浙江
</option>
<option value="福建">
    福建
</option>
<option value="湖南">
    湖南
</option>
<option value="湖北">
    湖北
</option>
<option value="山东">
    山东
</option>
<option value="山西">
    山西
</option>
<option value="河南">
    河南
</option>
<option value="河北">
    河北
</option>
<option value="吉林">
    吉林
</option>
<option value="辽宁">
    辽宁
</option>
<option value="黑龙江">
    黑龙江
</option>
<option value="安徽">
    安徽
</option>
<option value="江苏">
    江苏
</option>
<option value="江西">
    江西
</option>
<option value="海南">
    海南
</option>
<option value="陕西">
    陕西
</option>
<option value="云南">
    云南
</option>
<option value="青海">
    青海
</option>
<option value="宁夏">
    宁夏
</option>
<option value="甘肃">
    甘肃
</option>
<option value="新疆">
    新疆
</option>
<option value="西藏">
    西藏
</option>
<option value="广西">
    广西
</option>
<option value="内蒙古">
    内蒙古
</option>
<option value="深圳">
    深圳
</option>
<option value="香港">
    香港
</option>
<option value="澳门">
    澳门
</option>
<option value="台湾">
    台湾
</option>
</select></td>
</tr>
<tr>
    <td>简介:</td>
    <td><textarea rows="5" cols="50" name="summary"></textarea></td>
</tr>
<tr>
    <td align="right" colspan="2"><input type="submit" value="提交"></td>
</tr>
</form>
</table>
</center>
</body>
</html>