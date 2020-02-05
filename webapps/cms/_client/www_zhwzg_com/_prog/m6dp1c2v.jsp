<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.bizwink.cms.util.*"%>
<%@ page import="com.xml.*"%>
<%@page contentType="text/html;charset=GBK"%>
<%
    int startflag = ParamUtil.getIntParameter(request, "startflag", -1);
    int bookid = ParamUtil.getIntParameter(request, "shu", -1);
    IFormManager formMgr = FormPeer.getInstance();
    //System.out.println("bookid=" + bookid);
    //System.out.println("startflag=" + startflag);
    if (startflag == 1) {
        String fromurl = request.getHeader("REFERER");
        String bookname = ParamUtil.getParameter(request, "bookname");
        String username = ParamUtil.getParameter(request, "username");
        String telephone = ParamUtil.getParameter(request, "telephone");
        String postcode = ParamUtil.getParameter(request, "postcode");
        String address = ParamUtil.getParameter(request, "address");

        formfields ff = new formfields();
        ff.setBookname(bookname);
        ff.setUsername(username);
        ff.setTelephone(telephone);
        ff.setPostcode(postcode);
        ff.setAddress(address);
        boolean retcode = formMgr.insertZhengshu(ff);
        if (retcode == false) {
            response.sendRedirect("/mfzs/success.shtml");
            return;
        } else {
            response.sendRedirect("/mfzs/error.shtml");
            return;
        }

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head xmlns="">
    <title>珠海振国肿瘤康复医院</title>
    <script src="/_sys_js/tanchuceng.js" type="text/javascript"></script>
    <meta content="text/html; charset=gb2312" http-equiv="Content-Type" />
    <link href="/css/wzgstyle.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript">
        function check(){
            if(myform.username.value == "" || myform.username.value == null){
                alert("请输入申请人姓名！");
                myform.username.focus();
                return false;
            }
            if(myform.address.value == "" || myform.address.value == null){
                alert("请输入寄送地址！");
                myform.address.focus();
                return false;
            }
            if(myform.telephone.value == "" || myform.telephone.value == null){
                alert("请输入申请人联系电话！");
                myform.telephone.focus();
                return false;
            }
            if(myform.postcode.value == "" || myform.postcode.value == null){
                alert("请输入申请人邮政编码！");
                myform.postcode.focus();
                return false;
            }

            return true;
        }
    </script>
</head>
<body xmlns="">
<table cellspacing="0" cellpadding="0" width="1000" align="center"
       background="/images/2010527wzg-bg.gif" border="0"
       style="background-repeat: repeat-y">
    <tbody>
    <tr>
        <td valign="top" align="left" width="233">
            <table cellspacing="0" cellpadding="0" width="233" border="0">
                <tbody>
                <tr>
                    <td valign="top" align="left"><img height="433" alt=""
                                                       width="128" src="/images/2010527wag-logo2.jpg" /></td>
                    <td width="2"><img height="434" alt="" width="2"
                                       src="/images/2010527wzg-line2.gif" /></td>
                    <td valign="top" align="left" width="103"><%@include
                            file="/www_shwzg_com/inc/menu.shtml"%></td>
                </tr>
                </tbody>
            </table><%@include file="/www_shwzg_com/inc/expert.shtml"%></td>
        <td valign="top" align="left" width="767"><%@include
                file="/www_shwzg_com/inc/weather.shtml"%><table
                cellspacing="0" cellpadding="0" width="767" border="0">
            <tbody>
            <tr>
                <td valign="top" align="left"><img height="218" alt=""
                                                   width="767" src="/images/2010527wag-gsjj8.jpg" /></td>
            </tr>
            <tr>
                <td height="30">&nbsp;</td>
            </tr>
            </tbody>
        </table>
            <table cellspacing="0" cellpadding="0" width="767" border="0">
                <tbody>
                <tr>
                    <td><img height="1" alt="" width="13" src="/images/space.gif" /></td>
                    <td valign="bottom" align="left" height="18"><span
                            class="titlered">免费赠书</span> <span class="entitlered">Free
						Book Donation</span></td>
                </tr>
                <tr>
                    <td width="13"><img height="1" alt="" width="13"
                                        src="/images/space.gif" /></td>
                    <td><img height="8" alt="" width="740"
                             src="/images/2010527wag-tbg.jpg" /></td>
                </tr>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" width="767"
                   background="/images/2010527wag-tbg2.jpg" border="0"
                   style="background-repeat: no-repeat">
                <tbody>
                <tr>
                    <td width="13"><img height="1" alt="" width="13"
                                        src="/images/space.gif" /></td>
                    <td valign="top" align="left" width="754" height="368">
                        <table cellspacing="0" cellpadding="0" width="734" border="0">
                            <tbody>
                            <tr>
                                <td><img height="12" alt="" src="/images/space.gif" /></td>
                            </tr>
                            <tr>
                                <td>
                                    <div
                                            style="scrollbar-face-color: #98642b; scrollbar-highlight-color: #98642b; overflow: auto; scrollbar-shadow-color: #98642b; scrollbar-arrow-color: #daae7f; scrollbar-base-color: #98642b; height: 336px; scrollbar-dark-shadow-color: #98642B">
                                        <form action="m6dp1c2v.jsp" method="post" name="myform" onsubmit='return check();'>
                                            <input type='hidden' name='startflag' value='1'>
                                            <%
                                                if (bookid==1) {
                                                    out.println("<input type='hidden' name='bookname' value='《生命之歌--战胜癌症实录》'>");
                                                } else if (bookid==2) {
                                                    out.println("<input type='hidden' name='bookname' value='《肿瘤防治与康复》（上、下册）'>");
                                                } else if (bookid==3) {
                                                    out.println("<input type='hidden' name='bookname' value='《肿瘤防治与康复》（上、下册）'>");
                                                } else if (bookid==4) {
                                                    out.println("<input type='hidden' name='bookname' value='《肿瘤防治与康复》（上、下册）'>");
                                                } else if (bookid==5) {
                                                    out.println("<input type='hidden' name='bookname' value='《肿瘤防治与康复》（上、下册）'>");
                                                } else {
                                                    out.println("<input type='hidden' name='bookname' value='《肿瘤防治与康复》（上、下册）'>");
                                                }
                                            %>
                                            <table height="72" cellspacing="1" cellpadding="1" width="700"
                                                   border="0">
                                                <tbody>
                                                <tr>
                                                    <td colspan="2"><span style="color: #800000">请您仔细填写个人信息，方便我们把书籍寄给您</span>&nbsp;&nbsp;</td>
                                                </tr>
                                                <tr>
                                                    <td align="right">用户名：</td>
                                                    <td><input class="textbg" alt="用户名"
                                                               style="width: 200px" name="username" type="text" /></td>
                                                </tr>
                                                <tr>
                                                    <td align="right">电话：</td>
                                                    <td><input class="textbg" alt="电话" style="width: 120px"
                                                               name="telephone" type="text" /></td>
                                                </tr>
                                                <tr>
                                                    <td align="right">邮编：</td>
                                                    <td><input class="textbg" alt="邮编" style="width: 120px"
                                                               name="postcode" type="text" /></td>
                                                </tr>
                                                <tr>
                                                    <td align="right">地址：</td>
                                                    <td><input class="textbg" alt="地址"
                                                               style="width: 230px; height: 80px" name="address"
                                                               type="text" /></td>
                                                </tr>
                                                <tr>
                                                    <td></td>
                                                    <td><input type="submit" name="提交" value="提交" /></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </form>
                                    </div>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table><%@include file="/www_shwzg_com/inc/low.shtml"%></td>
    </tr>
    </tbody>
</table>
</body>
</html>