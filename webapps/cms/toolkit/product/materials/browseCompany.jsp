<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.cms.toolkit.company.*" %>
<%@ page import="com.bizwink.cms.security.*" %>
<%@page language="java" contentType="text/html;charset=GBK" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int id= ParamUtil.getIntParameter(request,"id",-1);
    int companytype=ParamUtil.getIntParameter(request,"companytype",-1);
    int startIndex = ParamUtil.getIntParameter(request, "startIndex", -1);
    ICompanyManager comMgr= CompanyPeer.getInstance();
    Company company=comMgr.getACompanyInfo(id);
%>
<html>
<head>
    <title>公司管理</title>
</head>
<body>
<center>
    <table>
        <form name="browseForm" action="" method="post">
            <tr>
                <td>编号</td>
                <td><input type="text" value="<%=id%>"></td>
            </tr>
            <tr>
                <td>公司名称</td>
                <td><input type="text" value="<%=company.getCompanyname()==null?"":company.getCompanyname()%>" size="50"></td>
            </tr>
            <tr>
                <td>地址</td>
                <td><input type="text" value="<%=company.getCompanyaddress()==null?"":company.getCompanyaddress()%>" size="50"></td>
            </tr>
            <tr>
                <td>电话</td>
                <td><input type="text" value="<%=company.getCompanyphone()==null?"":company.getCompanyphone()%>"></td>
            </tr>
            <tr>
                <td>传真</td>
                <td><input type="text" value="<%=company.getCompanyfax()==null?"":company.getCompanyfax()%>"></td>
            </tr>
            <tr>
                <td>网址</td>
                <td><input type="text" value="<%=company.getCompanywebsite()==null?"":company.getCompanywebsite()%>"></td>
            </tr>
            <tr>
                <td>邮箱</td>
                <td><input type="text" value="<%=company.getCompanyemail()==null?"":company.getCompanyemail()%>"></td>
            </tr>
            <tr>
                <td>邮编</td>
                <td><input type="text" value="<%=company.getPostcode()==null?"":company.getPostcode()%>"></td>
            </tr>
            <tr>
                <td>区号</td>
                <td><%=company.getDistrictnumber()==null?"":company.getDistrictnumber()%></td>
            </tr>
            <tr>
                <td>分类</td>
                <td><input type="text" value="<%=company.getClsaaification()==null?"":company.getClsaaification()%>"></td>
            </tr>
            <tr>
                <td>地区</td>
                <td><input type="text" value="<%=company.getArea()==null?"":company.getArea()%>"></td>
            </tr>
            <tr>
                <td>简介</td>
                <td><textarea rows="5" cols="50"><%=company.getSummary()==null?"":company.getSummary()%></textarea></td>
            </tr>
            <tr>
                <td align="center" colspan="2"><a href="index.jsp?companytype=<%=companytype%>&startIndex=<%=startIndex%>">返回</a></td>
            </tr>
        </form>
    </table>
</center>
</body>
</html>