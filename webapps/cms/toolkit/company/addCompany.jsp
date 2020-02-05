<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.companyinfo.*" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.StringUtil" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    int siteid = authToken.getSiteID();
    int comtype = ParamUtil.getIntParameter(request, "companytype", -1);
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int comcolumnid = ParamUtil.getIntParameter(request,"comcolumnid",0);

    if (startflag == 1) {
        String companyname = ParamUtil.getParameter(request, "companyname");
        String companyaddress = ParamUtil.getParameter(request, "companyaddress");
        String companyphone = ParamUtil.getParameter(request, "companyphone");
        String companyfax = ParamUtil.getParameter(request, "companyfax");
        String companywebsite = ParamUtil.getParameter(request, "companywebsite");
        String companyemail = ParamUtil.getParameter(request, "companyemail");
        String postcode = ParamUtil.getParameter(request, "postcode");
        String classification = ParamUtil.getParameter(request, "classification");
        float companylatitude = ParamUtil.getFloatParameter(request,"companylatitude",0f);
        float companylongitude = ParamUtil.getFloatParameter(request,"companylongitude",0f) ;
        String companygooglecode = ParamUtil.getParameter(request,"companygooglecode");
        String companypic = ParamUtil.getParameter(request,"companypic");
        String summary = ParamUtil.getParameter(request, "summary");

        Companyinfo company = new Companyinfo();
        company.setSiteid(siteid);
        company.setCompanyname(companyname);
        company.setCompanyaddress(companyaddress);
        company.setCompanyphone(companyphone);
        company.setCompanyfax(companyfax);
        company.setCompanywebsite(companywebsite);
        company.setCompanyemail(companyemail);
        company.setPostcode(postcode);
        company.setClassification(classification);
        company.setCompanylatitude(companylatitude);
        company.setCompanylongitude(companylongitude);
        company.setCompanygooglecode(companygooglecode);
        company.setCompanypic(companypic);
        company.setSummary(summary);

        ICompanyinfoManager comMgr = CompanyinfoPeer.getInstance();
        comMgr.addCompanyInfo(company);
        response.sendRedirect("index.jsp?siteid=" + siteid);
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
            <input type="hidden" name="siteid" value="<%=siteid%>">
            <tr>
                <td>企业名称:</td>
                <td><input type="text" name="companyname" size="30" ></td>
            </tr>
            <tr>
                <td>地址:</td>
                <td><input type="text" name="companyaddress" size="30"></td>
            </tr>
            <tr>
                <td>电话:</td>
                <td><input type="text" name="companyphone" size="30"></td>
            </tr>
            <tr>
                <td>传真:</td>
                <td><input type="text" name="companyfax" size="30"></td>
            </tr>
            <tr>
                <td>网址:</td>
                <td><input type="text" name="companywebsite" size="30"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="companyemail" size="30"></td>
            </tr>
            <tr>
                <td>邮编:</td>
                <td><input type="text" name="postcode"size="30" ></td>
            </tr>
            <tr>
                <td>分类:</td>
                <td><input type="text" name="classification" size="30"></td>
            </tr>
            <tr>
                <td>北纬:</td>
                <td><input type="text" name="companylatitude" size="30"></td>
            </tr>
            <tr>
                <td>东经:</td>
                <td><input type="text" name="companylongitude" size="30"></td>
            </tr>
            <tr>
                <td>图片:</td>
                <td><input type="file" name="companypic" size="30"></td>
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