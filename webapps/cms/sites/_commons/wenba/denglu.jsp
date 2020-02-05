<%@ page contentType="text/html;charset=GBK" language="java" import="java.sql.*" errorPage="" %>
<HTML>
<HEAD>
<META NAME="GENERATOR" Content="Microsoft DHTML Editing Control">
<TITLE></TITLE>
<script type="text/javascript">
function s(){

    var name=document.getElementById("name").value;
    var pass=document.getElementById("pass").value;
	
	if(name==""){
		alert('请填写用户名!');
		return ;
	}
	if(pass==""){
		alert('请填写用户密码!');
		return ;
	}

	        var objXml1;
            if (window.ActiveXObject)
            {
                objXml1 = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest)
            {
                objXml1 = new XMLHttpRequest();
            }
            objXml1.open("POST", "/vvvvxian.jsp?name=" + name + "&pass=" + pass, false);
            objXml1.send(null);
            
            var retstrs = objXml1.responseText;
            var re = retstrs.split(','); 
            var retstr = re[0]; //用户名              
            var retstr1 = re[1]; //用户积分 
            if(retstr1>1&&retstr1<200){
            	retstr1 = "HR学员";
            }	
            if(retstr1>201&&retstr1<500){
            	retstr1 = "HR助理";
            }  
            if(retstr1>501&&retstr1<800){
            	retstr1 = "HR专员";
            }
            if(retstr1>801&&retstr1<1000){
            	retstr1 = "HR主管";
            }
            if(retstr1>1001&&retstr1<1500){
            	retstr1 = "HR经理";
            }
            if(retstr1>1501&&retstr1<2000){
            	retstr1 = "HR高级经理";
            }
            if(retstr1>2001){
            	retstr1 = "HR总监";
            }
            if (retstr != null && retstr.length > 0) {
			     if(retstr.indexOf("用户名或密码错误")!=-1)
				 {
				    alert("用户名或错误");
					return;
				 }
                if(retstr.indexOf("没有被激活")!=-1)
				{
				   alert("您还没有被激活，请到您的邮箱中激活您的用户名!");
				   return;
				}
				if(retstr.indexOf("登陆成功")!=-1){
                    var names=retstr.substring(retstr.indexOf("-")+1,retstr.length);
                    //alert(retstr1);
                document.getElementById("vvvvxian").innerHTML = "<TABLE cellSpacing=0 cellPadding=2 width=\"100%\" bgColor=#edf1f4 border=0>\n" +
"  <TBODY>\n" +
"  \n" +
"  <TR>\n" +
"    <TD align=left width=70>&nbsp;&nbsp;您好：</TD>\n" +
"    <TD align=left width=110>"+names+"</TD>\n" +
"    <TD align=left></TD></TR>\n" +
"  <TR>\n" +
"    <TD align=left>&nbsp;&nbsp;等级：</TD>\n" +
"    <TD align=left>"+retstr1+"</TD>\n" +
"    <TD align=left></TD></TR>\n" +
"  <TR>\n" +
"    <TD align=left width=70><a href=/uermanager/edit/yonghuziliao.jsp>&nbsp;&nbsp;查看资料</a></TD>\n"  +
"    <TD align=left><a href=# >退出</a></TD>\n" +
"    <TD align=left>&nbsp;</TD></TR></TBODY></TABLE>";
            }
        }

}
function bbb()
{
	
            var objXml;
            if (window.ActiveXObject)
            {
                objXml = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest)
            {
                objXml = new XMLHttpRequest();
            }
            objXml.open("POST", "/vvvvxian.jsp", false);
            objXml.send(null);
            //var retstr = objXml.responseText;
			var retstrss = objXml.responseText;
            var re = retstrss.split(','); 
            var retstr = re[0];//用户名              
            var retstr1 = re[1];//用户积分 
            if(retstr1>1&&retstr1<200){
            	retstr1 = "HR学员";
            }	
            if(retstr1>201&&retstr1<500){
            	retstr1 = "HR助理";
            }  
            if(retstr1>501&&retstr1<800){
            	retstr1 = "HR专员";
            }
            if(retstr1>801&&retstr1<1000){
            	retstr1 = "HR主管";
            }
            if(retstr1>1001&&retstr1<1500){
            	retstr1 = "HR经理";
            }
            if(retstr1>1501&&retstr1<2000){
            	retstr1 = "HR高级经理";
            }
            if(retstr1>2001){
            	retstr1 = "HR总监";
            }
            if (retstr != null && retstr.length > 0) {
				//alert(retstr1);
				if(retstr.indexOf("登陆成功")!=-1){
                  var namess=retstr.substring(retstr.indexOf("-")+1,retstr.length);
                document.getElementById("vvvvxian").innerHTML = "<TABLE cellSpacing=0 cellPadding=2 width=\"100%\" bgColor=#edf1f4 border=0>\n" +
"  <TBODY>\n" +
"  \n" +
"  <TR>\n" +
"    <TD align=left width=70>&nbsp;&nbsp;您好：</TD>\n" +
"    <TD align=left width=110>"+namess+"</TD>\n" +
"    <TD align=left></TD></TR>\n" +
"  <TR>\n" +
"    <TD align=left>&nbsp;&nbsp;等级：</TD>\n" +
"    <TD align=left>"+retstr1+"</TD>\n" +
"    <TD align=left></TD></TR>\n" +
"  <TR>\n" +
"    <TD align=left><a href=/uermanager/edit/yonghuziliao.jsp>&nbsp;&nbsp;查看资料</a></TD>\n"  +
"    <TD align=left><a href=# >退出</a></TD>\n" +
"    <TD align=left>&nbsp;</TD></TR></TBODY></TABLE>";
            }
        }

}

function ccc(){
	        var objXmlc;
            if (window.ActiveXObject)
            {
                objXmlc = new ActiveXObject("Microsoft.XMLHTTP");
            }
            else if (window.XMLHttpRequest)
            {
                objXmlc = new XMLHttpRequest();
            }
            objXmlc.open("POST", "/tuichu.jsp?type=1", false);
            objXmlc.send(null);           
            var retstr = objXmlc.responseText;
			if (retstr == null && retstr.length == 0) {	
					window.history.go(0);		          
            }
}
</script>
</HEAD>
<BODY>

<DIV id="vvvvxian">

<TABLE cellSpacing=0 cellPadding=2 width="100%" bgColor=#edf1f4 border=0>
  <TBODY>

  <TR>
    <TD align=right width=60>用户名：</TD>
    <TD align=left width=110><INPUT id="name" class=us-name name=username> </TD>
    <TD align=left></TD></TR>
  <TR>
    <TD align=right>密码：</TD>
    <TD align=left><INPUT id="pass" class=us-name name=userpassword type=password></TD>
    <TD align=left>&nbsp;</TD></TR>
  <TR>
    <TD align=right>&nbsp;</TD>
    <TD align=left>
      <TABLE cellSpacing=0 cellPadding=2 border=0>
        <TBODY>
        <TR>
          <TD><IMG height=20 src="/webbuilder/sites/www_fawu_com/images/button-login.gif" width=36 style="cursor:hand" onClick="s()"></TD>
          <TD><a href="/uermanager/regist/zhuce.jsp"><IMG height=20 src="/webbuilder/sites/www_fawu_com/images/button-sighin.gif" width=72 border=0></a></TD></TR></TBODY></TABLE></TD>
    <TD align=left>&nbsp;</TD></TR></TBODY></TABLE><script language="javascript">bbb();</script></DIV>

</BODY>
</HTML>
