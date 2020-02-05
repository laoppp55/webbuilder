<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@ page import="com.bizwink.cms.toolkit.company.*" %>
<%@ page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    
    int comtype = ParamUtil.getIntParameter(request, "companytype", -1);
    ICompanyManager comMgr = CompanyPeer.getInstance();
    int id = ParamUtil.getIntParameter(request, "id", -1);
    Company com = comMgr.getACompanyInfo(id);
    int startflag = ParamUtil.getIntParameter(request, "startflag", 0);
    int sti = ParamUtil.getIntParameter(request, "startIndex", -1);
    int cuPage = ParamUtil.getIntParameter(request, "currPage", -1);

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
        int companyid = ParamUtil.getIntParameter(request, "companyid", -1);
        int startIndex = ParamUtil.getIntParameter(request, "startIndex", -1);
        int currPage = ParamUtil.getIntParameter(request, "currPage", -1);

        Company company = new Company();
        company.setId(companyid);
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


        comMgr.modifyCompany(company);
        //response.sendRedirect("index.jsp?companytype=" + companytype + "&currPage=" + currPage + "&startIndex=" + startIndex);
        response.sendRedirect("index.jsp?companytype=" + companytype + "&startIndex=" + startIndex);
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
                    alert("公司电话有误，请重新输入！");
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
        <form name="modifyForm" action="modifyCompany.jsp" method="post" onsubmit="javascritp:return check();">
            <input type="hidden" name="startflag" value="1">
            <input type="hidden" name="companytype" value="<%=comtype%>">
            <input type="hidden" name="companyid" value="<%=id%>">
            <input type="hidden" name="startIndex" value="<%=sti%>">
            <input type="hidden" name="currPage" value="<%=cuPage%>">
            
            <tr>
                <td>公司名称:</td>
                <td><input type="text" name="companyname"
                           value="<%=com.getCompanyname()==null?"":com.getCompanyname()%>"></td>
            </tr>
            <tr>
                <td>地址:</td>
                <td><input type="text" name="companyaddress"
                           value="<%=com.getCompanyaddress()==null?"":com.getCompanyaddress()%>"></td>
            </tr>
            <tr>
                <td>电话:</td>
                <td><input type="text" name="companyphone"
                           value="<%=com.getCompanyphone()==null?"":com.getCompanyphone()%>"></td>
            </tr>
            <tr>
                <td>传真:</td>
                <td><input type="text" name="companyfax" value="<%=com.getCompanyfax()==null?"":com.getCompanyfax()%>">
                </td>
            </tr>
            <tr>
                <td>网址:</td>
                <td><input type="text" name="companywebsite"
                           value="<%=com.getCompanywebsite()==null?"":com.getCompanywebsite()%>"></td>
            </tr>
            <tr>
                <td>邮箱:</td>
                <td><input type="text" name="companyemail"
                           value="<%=com.getCompanyemail()==null?"":com.getCompanyemail()%>"></td>
            </tr>
            <tr>
                <td>邮编:</td>
                <td><input type="text" name="postcode" value="<%=com.getPostcode()==null?"":com.getPostcode()%>"
                           size="5"></td>
            </tr>
            <tr>
                <td>区号:</td>
                <td><input type="text" name="districtnumber"
                           value="<%=com.getDistrictnumber()==null?"":com.getDistrictnumber()%>"></td>
            </tr>
            <tr>
                <td>分类:</td>
                <td><input type="text" name="classification"
                           value="<%=com.getClsaaification()==null?"":com.getClsaaification()%>"></td>
            </tr>
            <tr>
                <td>地区:</td>
                <td><input type="text" name="area" value="<%=com.getArea()==null?"":com.getArea()%>"></td>
            </tr>
            <tr>
                <td>简介:</td>
                <td><textarea rows="5" cols="50" name="summary"><%=com.getSummary() == null ? "" : com.getSummary()%>
                </textarea></td>
            </tr>
            <tr>
                <td align="right" colspan="2"><input type="submit" value="提交"></td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>