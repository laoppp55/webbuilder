<%@ page contentType="text/html;charset=gbk" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%
    int id= ParamUtil.getIntParameter(request,"id",-1);
    
%>
<!--
 * FCKeditor - The text editor for Internet - http://www.fckeditor.net
 * Copyright (C) 2003-2009 Frederico Caldeira Knabben
 *
 * == BEGIN LICENSE ==
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU General Public License Version 2 or later (the "GPL")
 *    http://www.gnu.org/licenses/gpl.html
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 *
 * == END LICENSE ==
 *
 * Text field dialog window.
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta content="noindex, nofollow" name="robots" />
    <script type="text/javascript" src="fckeditor/fckeditor.js"></script>
    <script src="common/fck_dialog_common.js" type="text/javascript"></script>
	<script type="text/javascript">

var dialog	= window.parent ;
var oEditor = dialog.InnerDialogLoaded() ;

// Gets the document DOM
var oDOM = oEditor.FCK.EditorDocument ;

var oActiveEl = dialog.Selection.GetSelectedElement() ;
var oldname="";
window.onload = function()
{
	// First of all, translate the dialog box texts

    oEditor.FCKLanguageManager.TranslatePage(document) ;
    var content=oEditor.FCK.EditorDocument.body.innerHTML;
   //alert(content);
    <%
      if(id==0){
    %>
      if(content.indexOf("FORM")>-1&&content.indexOf("action=")>-1&&content.indexOf(".jsp")>-1){
        //content=content.substring(content.indexOf("action=")+"action=".length,content.indexOf(".jsp")+".jsp".length);

          //  alert(content);
          var getInputlist=content.split("INPUT");
        for(var i=0;i<getInputlist.length;i++)
        {

             if(content.indexOf("name=doLogin")==-1)
            {

               GetE('txtName').value	= "doLogin" ;
               GetE('txtValue').value	= "true" ;
               GetE('txtType').value   ="hidden";
               GetE('txtValue').disabled="disabled"
               GetE('txtName').disabled="disabled";
            }
            else{
               GetE('txtValue').value	= "" ;
            }
            if(content.indexOf("name=password")==-1)
            {

               GetE('txtName').value	= "password" ;
               GetE('txtName').disabled="disabled";
               GetE('txtType').value   ="password";   
            }
            else{
               GetE('txtValue').value	= "" ;
            }
             if(content.indexOf("name=username")==-1)
            {
               GetE('txtName').value	= "username" ;
               GetE('txtName').disabled="disabled";
               GetE('txtType').value   ="text";
            }
            else{
               GetE('txtValue').value	= "" ;
            }

            if(i>3)
            {
               GetE('txtName').value	= "" ;
               GetE('txtName').disabled="";
               GetE('txtType').value   ="text";
              
            }
        }
      }
    <%
       }
       if(id==1){
    %>

    //alert(oEditor.FCKeditorAPI.GetInstance("content").GetHTML());

   // alert(window.parent.document.domain);
        var getInputlist=content.split("INPUT");
      
        for(var i=0;i<getInputlist.length;i++)
        {


            if(content.indexOf("name=startflag")==-1)
            {

               GetE('txtName').value	= "startflag" ;
               GetE('txtValue').value	= "1" ;
               GetE('txtType').value   ="hidden";
               GetE('txtName').disabled="disabled" ;
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>隐藏字段判断注册用的条件</b></font>";
            }
             
            if(content.indexOf("name=doCreate")==-1)
            {
               GetE('txtName').value	= "doCreate" ;
               GetE('txtValue').value	= "1" ;
               GetE('txtType').value   ="hidden";
               GetE('txtName').disabled="disabled";
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>隐藏字段判断注册用的条件</b></font>";
            }
           

             if(content.indexOf("name=txtVerify")==-1)
            {
               GetE('txtName').value	= "txtVerify" ;
               GetE('txtType').value   ="text";
               GetE('txtValue').value	= "" ;
               GetE('txtName').disabled="disabled" ;
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>验证码</b></font>";
            }
            if(content.indexOf("name=passWord2")==-1)
            {
               GetE('txtName').value	= "passWord2" ;
               GetE('txtType').value   ="password";
               GetE('txtValue').value	= "" ;
               GetE('txtName').disabled="disabled" ;
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>确认密码</b></font>";
            }
             if(content.indexOf("name=passWord1")==-1)
            {
               GetE('txtName').value	= "passWord1" ;
               GetE('txtType').value   ="password";
               GetE('txtValue').value	= "" ;
               GetE('txtName').disabled="disabled"  ;
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>密码</b></font>";
            }
             if(content.indexOf("name=email")==-1)
            {
               GetE('txtName').value	= "email" ;
               GetE('txtType').value   ="text";
               GetE('txtValue').value	= "" ;
               //GetE('txtValue').disabled="disabled";
                GetE('txtName').disabled="disabled";
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>邮件</b></font>";
            }
            if(content.indexOf("name=username")==-1)
            {
               GetE('txtName').value	= "username" ;
               GetE('txtType').value   ="text";
               GetE('txtValue').value	= "请输入用户名" ;
             //  GetE('txtValue').disabled="disabled" ;
               GetE('txtName').disabled="disabled";
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>用户名</b></font>";
            }
            


         }
    
    <%}%>
    //alert(content);
    <%if(id==2){%>
         var getInputlist=content.split("INPUT");
         var left="<";
         var middle="%";
         var right=">"
        for(var i=0;i<getInputlist.length;i++)
        {
             if(content.indexOf("name=doUpdate")==-1)
            {

               GetE('txtName').value	= "doUpdate" ;
               GetE('txtValue').value	= "1" ;
               GetE('txtType').value   ="hidden";
               GetE('txtName').disabled="disabled" ;
                document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>隐藏字段判断注册用的条件</b></font>";
            }
           
            if(content.indexOf("name=Rname")==-1)
            {

               GetE('txtName').value	= "Rname" ;
               GetE('txtValue').value	= left+middle+"=UserName"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>真实名字字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=pass0")==-1)
            {
               GetE('txtName').value	= "pass0" ;
               GetE('txtValue').value	= "";
               GetE('txtType').value   ="password";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>旧密码字段判断注册用的条件</b></font>";
            }
            //alert(content);
            if(content.indexOf("name=pass")==-1||content.indexOf("<INPUT id=pass type=password name=pass")==-1)
            {
                GetE('txtName').value	= "pass" ;
               GetE('txtValue').value	= "";
               GetE('txtType').value   ="password";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>新密码字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=pass1")==-1)
            {
                GetE('txtName').value	= "pass1" ;
               GetE('txtValue').value	= "";
               GetE('txtType').value   ="password";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>确认新密码字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=lianxi")==-1)
            {
                GetE('txtName').value	= "lianxi" ;
               GetE('txtValue').value	= left+middle+"=lianxi"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>联系人字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=con")==-1)
            {
                GetE('txtName').value	= "con" ;
               GetE('txtValue').value	= left+middle+"=con"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>国家字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=city")==-1)
            {
                GetE('txtName').value	= "city" ;
               GetE('txtValue').value	= left+middle+"=city"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>城市字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=post")==-1)
            {
                GetE('txtName').value	= "post" ;
               GetE('txtValue').value	= left+middle+"=post"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>邮编字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=phone")==-1)
            {
                GetE('txtName').value	= "phone" ;
               GetE('txtValue').value	= left+middle+"=phone"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>电话号码字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=yphone")==-1)
            {
                GetE('txtName').value	= "yphone" ;
               GetE('txtValue').value	= left+middle+"=yphone"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>手机字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=chuanzhen")==-1)
            {
                GetE('txtName').value	= "chuanzhen" ;
               GetE('txtValue').value	= left+middle+"=chuanzhen"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>传真字段判断注册用的条件</b></font>";
            }
            if(content.indexOf("name=birth")==-1)
            {
                GetE('txtName').value	= "birth" ;
               GetE('txtValue').value	= left+middle+"=birth"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>生日字段判断注册用的条件</b></font>";
            }
             if(content.indexOf("name=0")==-1)
            {
                GetE('txtName').value	= "0" ;
               GetE('txtValue').value	= left+middle+"=memberid"+middle+right;
               GetE('txtType').value   ="text";
               GetE('txtName').disabled="disabled" ;
               document.getElementById("leixing").innerHTML="<font color=#ff0000 ><b>生日字段判断注册用的条件</b></font>";
            }
        }
    <%}%>
    if ( oActiveEl && oActiveEl.tagName == 'INPUT' && ( oActiveEl.type == 'text' || oActiveEl.type == 'password' ) )
	{
		GetE('txtName').value	= oActiveEl.name ;
		GetE('txtValue').value	= oActiveEl.value ;
		GetE('txtSize').value	= GetAttribute( oActiveEl, 'size' ) ;
		GetE('txtMax').value	= GetAttribute( oActiveEl, 'maxLength' ) ;
        GetE('chinesename').value=GetAttribute( oActiveEl, 'alt' ) ;
        GetE('txtType').value	= oActiveEl.type;
	}
	else
		oActiveEl = null ;
	dialog.SetOkButton( true ) ;
	dialog.SetAutoSize( true ) ;
	SelectField( 'txtName' ) ;

     oldname=GetE('txtName').value;
}

function Ok()
{
     <%
        if(id==-1){
        %>
       if(GetE('txtName').value=="")
       {
          alert("名称不能为空");
           return false;
       }
       if(/[^a-z0-9\.\'\"]/i.test(GetE('txtName').value))
       {
           alert("名称必须为英文字符");
           return false;
       }
       var oEditor1 = oEditor.FCKeditorAPI.GetInstance("content");
       var content= oEditor1.GetXHTML(true);
       if(content.indexOf(GetE('txtName').value)!=-1)
       {
           alert("名字重复请重新填写");
           return false;
       }



       if(GetE('chinesename').value=="")
       {
           alert("请填写中文名称");
           return false;
       }


     <%
      }
    %>




    if ( isNaN( GetE('txtMax').value ) || GetE('txtMax').value < 0 )
	{
		alert( "Maximum characters must be a positive number." ) ;
		GetE('txtMax').focus() ;
		return false ;
	}
	else if( isNaN( GetE('txtSize').value ) || GetE('txtSize').value < 0 )
	{
		alert( "Width must be a positive number." ) ;
		GetE('txtSize').focus() ;
		return false ;
	}

	oEditor.FCKUndo.SaveUndoStep() ;
    if(oldname==""){
    oEditor.FCKeditorAPI.GetInstance("content").InsertHtml(GetE('chinesename').value+"：");
    }
    oActiveEl = CreateNamedElement( oEditor, oActiveEl, 'INPUT', {name: GetE('txtName').value, type: GetE('txtType').value } ) ;

    SetAttribute( oActiveEl, 'old'	, oldname ) ;
    SetAttribute( oActiveEl, 'value'	, GetE('txtValue').value ) ;
	SetAttribute( oActiveEl, 'size'		, GetE('txtSize').value ) ;
	SetAttribute( oActiveEl, 'maxlength', GetE('txtMax').value ) ;
    SetAttribute( oActiveEl, 'alt', GetE('chinesename').value ) ;
   // if(oldname!="")
   // {
    //   alert(oldname);

   // }
    <%if(id==1){%>
    //设置表单的javascript方法
   // alert(GetE('txtName').value);
    if(GetE('txtName').value=="username") {
    SetAttribute( oActiveEl, 'id'	, "username" ) ;
    SetAttribute( oActiveEl, 'onFocus'	, "if (value =='请输入用户名'){value =''}" ) ;
    SetAttribute( oActiveEl, 'onBlur'	, "check_user()" ) ;
    oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<div id=\"u_mag\"></div>");
    }
    if(GetE('txtName').value=="email")
    {
     SetAttribute( oActiveEl, 'id'	, "email" ) ;
     SetAttribute( oActiveEl, 'onBlur'	, "check_email()" ) ;
      oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<div id=\"e_mag\"></div>");
    }
    if(GetE('txtName').value=="passWord1")
    {
     SetAttribute( oActiveEl, 'id'	, "passWord1" ) ;
     SetAttribute( oActiveEl, 'onBlur'	, "javascript:checkPassword1()" ) ;
      oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<div id=\"p_mag1\"></div>");
    }
    if(GetE('txtName').value=="passWord2")
    {
     SetAttribute( oActiveEl, 'id'	, "passWord2" ) ;
     SetAttribute( oActiveEl, 'onBlur'	, "javascript:checkPassword2()" ) ;
        oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<div id=\"p_mag2\"></div>");
    }
    if(GetE('txtName').value=="txtVerify")
    {
       SetAttribute( oActiveEl, 'id'	, "txtVerify" ) ;
       oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<img  border=\"1\" name=\"cod\" src=\"/_commons/drawImage.jsp\" />&nbsp;&nbsp;<a  href=\"javascript:shuaxin()\">看不清楚?换下一张</a>");
    }
    <%}
      if(id==2){
    %>
     if(GetE('txtName').value=="Rname")
    {
       SetAttribute( oActiveEl, 'id'	, "Rname" ) ;

    }
     if(GetE('txtName').value=="pass0")
    {
       SetAttribute( oActiveEl, 'id'	, "pass0" ) ;
       oEditor.FCKeditorAPI.GetInstance("content").InsertHtml("<div id=\"tishi\"></div>");
    }
     if(GetE('txtName').value=="pass")
    {
       SetAttribute( oActiveEl, 'id'	, "pass" ) ;

    }
     if(GetE('txtName').value=="pass1")
    {
       SetAttribute( oActiveEl, 'id'	, "pass1" ) ;

    }
     if(GetE('txtName').value=="lianxi")
    {
       SetAttribute( oActiveEl, 'id'	, "lianxi" ) ;

    }
     if(GetE('txtName').value=="con")
    {
       SetAttribute( oActiveEl, 'id'	, "con" ) ;

    }
     if(GetE('txtName').value=="city")
    {
       SetAttribute( oActiveEl, 'id'	, "city" ) ;

    }
     if(GetE('txtName').value=="post")
    {
       SetAttribute( oActiveEl, 'id'	, "post" ) ;

    }
     if(GetE('txtName').value=="phone")
    {
       SetAttribute( oActiveEl, 'id'	, "phone" ) ;

    }
     if(GetE('txtName').value=="yphone")
    {
       SetAttribute( oActiveEl, 'id'	, "yphone" ) ;
       
    }
    if(GetE('txtName').value=="chuanzhen")
    {
       SetAttribute( oActiveEl, 'id'	, "chuanzhen" ) ;

    }
    if(GetE('txtName').value=="birth")
    {
       SetAttribute( oActiveEl, 'id'	, "birth" ) ;

    }
    if(GetE('txtName').value=="0")
    {
       SetAttribute( oActiveEl, 'disabled'	, "disabled" ) ;
        SetAttribute( oActiveEl, 'id'	, "0" ) ;

    }
   
    <%}%>
   
    return true ;
}

	</script>
</head>
<body style="overflow: hidden">
	<table width="100%" style="height: 100%">
		<tr>
			<td align="center"><div id="leixing"></div>
				<table cellspacing="0" cellpadding="0" border="0">
					<tr>
						<td>
							<span fcklang="DlgTextName">Name</span><br />
							<input id="txtName" type="text" size="20" />
						</td>
						<td>
						</td>
						<td>
							<span fcklang="DlgTextValue">Value</span><br />
							<input id="txtValue" type="text" size="25" />
						</td>
					</tr>
					<tr>
						<td>
							<span fcklang="DlgTextCharWidth">Character Width</span><br />
							<input id="txtSize" type="text" size="5" />
						</td>
						<td>
						</td>
						<td>
							<span fcklang="DlgTextMaxChars">Maximum Characters</span><br />
							<input id="txtMax" type="text" size="5" />
						</td>
					</tr>
					<tr>
						<td>
							<span fcklang="DlgTextType">Type</span><br />
							<select id="txtType">
								<option value="text" selected="selected" fcklang="DlgTextTypeText">Text</option>
								<option value="password" fcklang="DlgTextTypePass">Password</option>
                                <option value="hidden" fcklang="DlgTextTypeHidden">hidden</option>
                            </select>
						</td>
						<td>
							&nbsp;</td>
						<td>
                             中文名称<br><input type="text" name="chinesename" size="5"> 
                        </td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
