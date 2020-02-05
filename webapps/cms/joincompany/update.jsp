<%@ page contentType="text/html;charset=gbk" import="com.bizwink.joincompany.Joincompany" %>
<%@ page import="com.bizwink.joincompany.JoincompanyPeer" %>
<%@ page import="com.bizwink.joincompany.IJoincompanyManager" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %><%
    IJoincompanyManager jpeer= JoincompanyPeer.getInstance();
    Joincompany join=(Joincompany)session.getAttribute("join");

    if(join==null)
    {
        response.sendRedirect("login.jsp");
        return;
    }
    int flag= ParamUtil.getIntParameter(request,"flag",-1);
    if(flag==1)
    {
       String joinname=ParamUtil.getParameter(request,"joinname");
       String address=ParamUtil.getParameter(request,"address");
       String lianxipeople=ParamUtil.getParameter(request,"lianxipeople");
       String phone=ParamUtil.getParameter(request,"phone");
       String fax=ParamUtil.getParameter(request,"fax");
       String email=ParamUtil.getParameter(request,"email");
       String zhizhaonumber=ParamUtil.getParameter(request,"zhizhaonumber");
     //  String password=ParamUtil.getParameter(request,"password");
       String question=ParamUtil.getParameter(request,"question");
       String answer=ParamUtil.getParameter(request,"answer");
         int i=0;
       

       join.setJoinname(joinname);
       join.setAddress(address);
       join.setLianxipeople(lianxipeople);
       join.setPhone(phone);
       join.setFax(fax);
       join.setEmail(email);
       join.setZhizhaonumber(zhizhaonumber);
      // join.setPassword(password);
     //  join.setQuestion(question);
     //  join.setAnswer(answer);
       i= jpeer.updateJoin(join);
         if (i == 1) {

            session.setAttribute("join",join);
            out.write("<script type=\"text/javascript\">alert('提交成功');window.location='update.jsp'</script>");
        } else {
            out.write("<script type=\"text/javascript\">alert('提交失败');window.location='update.jsp'</script>");
        }
    }
    if(flag==2)
    {
        int i=0;
        String password=ParamUtil.getParameter(request,"password");
        String password1=ParamUtil.getParameter(request,"password1");
        String password2=ParamUtil.getParameter(request,"password2");
        System.out.println("pass="+password1);
        join.setPassword(password1);
        i= jpeer.updatepass(join,password);
         if (i == 1) {

            
            out.write("<script type=\"text/javascript\">alert('密码修改成功');window.location='update.jsp'</script>");
        } else {
            out.write("<script type=\"text/javascript\">alert('密码修改失败');window.location='update.jsp'</script>");
        }

    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<link href="coositecss.css" rel="stylesheet" type="text/css" />
 <script type="text/javascript" xml:space="preserve">
        var falg = false;
        var sucess = "";
        function ismail(mail)
        {

            falg = (new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(mail));
            if (falg)
            {
                sucess = "sucess";
            }
        }
        function tijiao()
        {
            var joinname=document.getElementById("joinname").value;
            var address=document.getElementById("address").value;
            var email=document.getElementById("email").value;
            var phone=document.getElementById("phone").value;
            var fax=document.getElementById("fax").value;
           
            var zhizhaonumber=document.getElementById("zhizhaonumber").value;
            var lianxipeople=document.getElementById("lianxipeople").value;
           // var password=document.getElementById("password").value;
            if(joinname=="")
            {
                alert("加盟商名称不能为空");
                return;
            }
            if(address=="")
            {
                alert("地址不能为空");
                return ;
            }
            if(lianxipeople=="")
            {
                alert("联系人不能为空");
                return ;
            }
            if(phone=="")
            {
                alert("手机不能为空");
                return;
            }
            if(phone.length!=11)
            {
                 alert("手机位数不正确");
                return;
            }
            if(fax=="")
            {
                alert("传真不能为空");
                return;
            }
            if(zhizhaonumber=="")
            {
                alert("企业执照不能为空");
                return;
            }

            ismail(email)
            if (sucess == "")
            {
                alert("E-mail格式不正确");
                return ;
            }

            document.form1.action = "update.jsp";
            document.form1.submit();

        }
        function updatepass()
        {
             var password=document.getElementById("password").value;
             var password1=document.getElementById("password1").value;
             var password2=document.getElementById("password2").value;
             if(password1!=password2)
             {
                 alert("俩次密码不一致，请重新输入");
                 return;
             }
            document.form2.action = "update.jsp";
            document.form2.submit();
        }
    </script>
 <style type="text/css">
<!--
.STYLE5 {font-size: 18px}
-->
 </style>
</head>

<body>
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="25">&nbsp;</td>
    <td width="223" align="left" valign="top"><img src="images/logo_331.jpg" width="217" height="84" vspace="10" /></td>
    <td width="261" align="left" valign="top"><img src="images/Preview_331.jpg" width="261" height="152" /></td>
    <td width="491" align="left" valign="top"><table width="465" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20" height="30">&nbsp;</td>
        <td width="435">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="right" valign="middle"><a href="#" class="inde"><br />
    设为首页 |</a><a href="#" class="inde"> 加为收藏 &nbsp;</a><a href="#"> </a></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td align="left" valign="top">
		<img src="images/coosite_14.gif" width="48" height="17" /><img src="images/coosite_15.gif" width="80" height="17" /><img src="images/coosite_16.gif" width="80" height="17" /><img src="images/coosite_17.gif" /><img src="images/coosite_18.gif" width="81" height="17" /><img src="images/coosite_19.gif" width="78" height="17" />
		</td>
      </tr>
    </table></td>
  </tr>
</table>
<table width="688" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="688" height="79" align="left" valign="top"><table width="688" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="309"  height="65" style="color:#FF0000; font:larger"><span class="STYLE5"><%=join.getJoinid()%>(请您牢记,登陆时用) !!</span></td>
        <td width="379">&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        
      </tr>
    </table></td>
  </tr>
  <tr>
    <td align="left" valign="top" >
		<table width="688" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="54">&nbsp;</td>
			<td width="298" align="left" valign="top">
			
			
			
		   <a href="../register/register.jsp">创建站点</a><br/><br/><br/><br/>
		      <a href="../joincompany/updates.jsp">修改注册信息</a><br/>
		      <br/><br/><br/>
			  <a href="../register/index.jsp">进入CMS</a><br/> <br/><br/><br/>
		     <a href="../joincompany/sitlist.jsp">查看站点信息</a><br/><br/><br/><br/>
		  
		
			
			</td>
			<td width="336" align="left" valign="top"><br />
		    <img src="images/coosite_login_4.gif" width="268" height="156" hspace="20" /></td>
		  </tr>
	  </table>
	</td>
  </tr>
  <tr>
    <td align="left" valign="top"></td>
  </tr>
</table>
</body>
</html>
