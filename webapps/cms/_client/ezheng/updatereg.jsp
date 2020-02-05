<%@page contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.webapps.register.UregisterPeer" %>
<%@ page import="com.bizwink.webapps.register.Uregister" %>
<%@ page import="com.bizwink.webapps.register.IUregisterManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="com.bizwink.webapps.feedback.FeedbackPeer" %>
<%@ page import="com.bizwink.webapps.feedback.IFeedbackManager" %>


<%
    Uregister username=(Uregister)session.getAttribute("UserLogin");
    if (username == null) {
        
            out.write("<script  lanugage=\"javascript\">alert(\"对不起，您没登陆\");window.location=\"/\";</script>");  
return;  
}
    String memberid=username.getMemberid();
    
    String pass0 = ParamUtil.getParameter(request,"pass0");
    String pass1 = ParamUtil.getParameter(request,"pass1");
    String UserName = ParamUtil.getParameter(request,"Rname");
    String lianxi = ParamUtil.getParameter(request,"lianxi");
    String con = ParamUtil.getParameter(request,"con");
    String city = ParamUtil.getParameter(request,"city");
    String post = ParamUtil.getParameter(request,"post");
    String phone = ParamUtil.getParameter(request,"phone");
    String yphone = ParamUtil.getParameter(request,"yphone");
    String chuanzhen = ParamUtil.getParameter(request,"chuanzhen");
    String birth = ParamUtil.getParameter(request,"birth");
    int doupdate = ParamUtil.getIntParameter(request,"doUpdate",0);

    Uregister ure = null;
    String sitename = request.getServerName();
    IUregisterManager regMgr = UregisterPeer.getInstance();
     IFeedbackManager feedMgr = FeedbackPeer.getInstance();
   int siteid = feedMgr.getSiteID(sitename);
     int Siteid = siteid ;
    
       if (doupdate == 1) {
        ure=new Uregister();
        ure.setMemberid(memberid);
        ure.setPassword(pass1);
        ure.setName(UserName);
        ure.setLinkman(lianxi);
        ure.setCountru(con);
        ure.setCity(city);
        ure.setPostalcode(post);
        ure.setPhoen(phone);
        ure.setMobilephone(yphone);
        ure.setFax(chuanzhen);
        ure.setBirthday(birth);

        int code = regMgr.Update_userinfo(ure,Siteid);
        if(code ==0){
            //response.sendRedirect("su.jsp");
        }else{
            out.println("<script  lanugage=\"javascript\">alert(\"修改失败！请重新修改！\");window.location=\"make.jsp\";</script>");
        }
    }
ure = regMgr.getUserInfo(memberid,Siteid);
%>
<html>
    <head>
        <title>我的电子商务网站</title>
        <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
        <meta content="bzwink" name="author" />
        <meta content="商品销售" name="description" />
        <meta content="化妆品、十自绣、首饰、工艺品" name="keywords" />
        <link href="/css/link.css" rel="stylesheet" />
        <script type="text/JavaScript">
        function update_do(){
            var pass0 = document.all.pass0.value;
            var pass1 = document.all.pass1.value;
            var pass2 = document.all.pass1.value;
            if(pass0==""){
                alert("请输入原密码！");
                return false;
            }else{
                var objXmlc;
                var ref = window.location.href;
                if (window.ActiveXObject){
                    objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
                }
                else if (window.XMLHttpRequest){
                    objXmlc = new XMLHttpRequest();
                }
                objXmlc.open("POST", "/_commons/checkpassword.jsp?uid=<%= memberid%>&pass="+pass0 + "&siteid=<%=Siteid%>", false);
                objXmlc.send(null);
                var res = objXmlc.responseText;
                alert(res);
                var re = res.split('-');
                var retstrs = re[0];
                alert(retstrs);
                if(retstrs==1){
                    document.getElementById("tishi").innerHTML = "密码填写正确！";
                }if(retstrs==0){
                document.getElementById("tishi").innerHTML = "密码填写错误！";
                return false;
            }
            }
            if(pass1==""){
                alert("请输入新密码！");
                return false;
            }
            if(pass1!=pass2){
                alert("两次输入的新密码不一致！");
                return false;
            }
            document.all.form1.action = "updatereg.jsp";
            document.all.form1.submit();
        }
    </script>
<style type="text/css">
<!--.biz_table{ border:1 dashed null;
 } 
.biz_table td{ font-size:12px; color:#000000; font-family:宋体 ; text-align:left;
}
.biz_table input{ font-size:18px;  size:12px;

}
biz_table img{ border:0;
}
-->
</style></head>
    <body leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" width="100%">
        <%@include file="/www_chinabuy360_cn/include/top.shtml" %>
        <table cellspacing="0" cellpadding="0" width="1006" border="0">
            <tbody>
                <tr>
                    <td width="6" bgcolor="#ececec">&nbsp;</td>
                    <td width="32"><img alt="" src="/images/logo_leftgap02.gif" /></td>
                    <td align="center" width="220"><img height="23" alt="" width="210" border="0" src="/images/category_view.gif" /></td>
                    <td width="740" bgcolor="#ececec">
                    <table cellspacing="0" cellpadding="0" width="730" bgcolor="#ececec" border="0">
                        <tbody>
                            <tr>
                                <td width="10"><img height="30" alt="" width="10" border="0" src="/images/keyword_leftgap.gif" /></td>
                                <td width="730"><img height="10" alt="" width="2" align="absMiddle" border="0" src="/images/notice_bullet.gif" />&nbsp;<font style="FONT-SIZE: 12px"><strong>修改用户注册信息</strong></font></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                    <td width="8" bgcolor="#ececec"></td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="10">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <a href="/www_chinabuy360_cn/_prog/ordersearch.jsp">定单查询</a>
        <table cellspacing="0" cellpadding="0" width="960" align="center" border="0">
            <tbody>
                <tr>
                    <td valign="top" width="230"></td>
                    <td valign="top" align="center" width="730">
                    <table cellspacing="1" cellpadding="1" width="500" summary="" border="1">
                        <tbody>
                            <tr>
                                <td><form name="form1" id="form1" method="POST">
    <input type="hidden" name="memberid" value=""<%= memberid%>"">
    <input type="hidden" name="doUpdate" value="1">
    <table width="100%" border="0" align="center"    class="biz_table">
        <tr>
            <td>用户名：</td>
            <td>"<%= memberid%>"</td>
            <td></td>
        </tr>
        <tr>
            <td>真实姓名</td>
            <td><input type="text" id="Rname" name="Rname" value="<%=(ure.getName()!=null)?ure.getName():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>旧密码</td>
            <td><input type="password" id="pass0" name="pass0"></td>
            <td>
                <div id="tishi"></div>
            </td>
        </tr>
        <tr>
            <td>新密码</td>
            <td><input type="password" id="pass" name="pass"></td>
            <td></td>
        </tr>
        <tr>
            <td>确认新密码</td>
            <td><input type="password" id="pass1" name="pass1"></td>
            <td></td>
        </tr>
        <tr>
            <td>联系人</td>
            <td><input type="text" id="lianxi" name="lianxi" value="<%=(ure.getLinkman()!=null)?ure.getLinkman():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>国家</td>
            <td><input type="text" id="con" name="con" value="<%=(ure.getCountru()!=null)?ure.getCountru():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>所在城市</td>
            <td><input type="text" id="city" name="city" value="<%=(ure.getCity()!=null)?ure.getCity():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>邮编</td>
            <td><input type="text" id="post" name="post" value="<%=(ure.getPostalcode()!=null)?ure.getPostalcode():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>电话号码</td>
            <td><input type="text" id="phone" name="phone" value="<%=(ure.getPhoen()!=null)?ure.getPhoen():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>移动电话</td>
            <td><input type="yphone" name="yphone" value="<%=(ure.getMobilephone()!=null)?ure.getMobilephone():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>传真</td>
            <td><input type="text" id="chuanzhen" name="chuanzhen" value="<%=(ure.getFax()!=null)?ure.getFax():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td>出生日期</td>
            <td><input type="text" id="birth" name="birth" value="<%=(ure.getBirthday()!=null)?ure.getBirthday():""%>"></td>
            <td></td>
        </tr>
        <tr>
            <td><input type="button" value="提交" onclick="javascript:update_do()"></td>
            <td>&nbsp;</td>
            <td></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>&nbsp;</td>
            <td></td>
        </tr>
    </table>
</form></td>
                            </tr>
                        </tbody>
                    </table>
                    </td>
                </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0">
            <tbody>
                <tr height="30">
                    <td></td>
                </tr>
            </tbody>
        </table>
        <%@include file="/www_chinabuy360_cn/include/low.shtml" %>
    </body>
</html>