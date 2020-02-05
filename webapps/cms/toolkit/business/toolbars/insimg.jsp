<%@ page import="java.sql.*,
                 java.io.*,
             com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
         com.jspsmart.upload.*,
                 com.bizwink.cms.publish.*,
         com.bizwink.cms.util.*" contentType="text/html;charset=gbk"%>
<%  ////////////////////////////////
    // Retreive parameters
   Auth authToken = SessionUtil.getUserAuthorization( request,  response, session);
   if( authToken == null ) {
     response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
     return;
   }

   int columnID  = ParamUtil.getIntParameter(request, "column", 0);
%>

<HTML>
<HEAD>
<TITLE>插入图片</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel=stylesheet type=text/css href="../style/global.css">

<STYLE TYPE="text/css">
 BODY   {margin-left:10; font-family:Verdana; font-size:12; background:menu}
 BUTTON {width:5em}
 TABLE  {font-family:Verdana; font-size:12}
 P      {text-align:center}
</STYLE>

<SCRIPT LANGUAGE="JavaScript" >
<!--
function upload() {
  if ((uploadpic.filenames.value !="") &&(uploadpic.filenames.value !=null) && (uploadpic.alttext.value != "")) {
  	 window.returnValue =uploadpic.filenames.value+"-"+uploadpic.alttext.value+"-"+uploadpic.aju.value+"-"+uploadpic.bw.value+"-"+uploadpic.hp.value+"-"+uploadpic.vp.value+"-"+uploadpic.picw.value+"-"+uploadpic.pich.value;
         uploadpic.action = '../upload/uploadpic.jsp?column='+ '<%=columnID%>';
  	 uploadpic.submit();
  	 window.close();
  }else {
  	alert("请选择要插入的图片或者是没有选择可替换的文本");
  	uploadpic.filenames.focus();
  }
}
// -->
</SCRIPT>

</HEAD>

<BODY onload="uploadpic.filenames.focus()">
<p>&nbsp;</p>
<Form  method="post" name=uploadpic enctype=multipart/form-data target="temp">
  <TABLE CELLSPACING=10 align=center width="442" border="1">
    <TR>
      <TD height="34">请选择插入的图片
      <TD height="34">
        <input type=file id="filenames"  size=18 name="filenames" >
    </TR>
    <TR>
      <TD colspan=2 align=center>
        <div align="left"> 替换文字：
          <input type="text" name="alttext" size="40">
        </div>
      </TD>
    </TR>
    <TR>
      <TD align=center>
        <div align="left">
          <p>对齐方式：
            <select name="aju" size="1">
              <option value="novalue">不设置</option>
              <option value="left" selected>左</option>
              <option value="right">右</option>
              <option value="center">中间</option>
              <option value="absMiddle">正中央</option>
              <option value="textTop">文本上方</option>
              <option value="basicline">基线</option>
              <option value="absBottom">正下方</option>
              <option value="bottom">下</option>
              <option value="middle">中</option>
              <option value="top">上</option>
            </select>
          </p>
          <p> 边框宽度：
            <input type="text" name="bw" size="15" value=0>
          </p>
          </div>
      </TD>
      <TD align=center>
        <p>水平间隔：
          <input type="text" name="hp" size="15" value=0>
        </p>
        <p>垂直间隔：
          <input type="text" name="vp" size="15" value=0>
        </p>
      </TD>
    </TR>
    <TR>
      <TD colspan=2 align=center>
         <p>图片宽度：
          <input type="text" name="picw" size="15" value=0>
          图片高度：
          <input type="text" name="pich" size="15" value=0>
        </p>
      </TD>
    </TR>
    <TR>
      <TD colspan=2 align=center>
        <input type="button" ID=Ok name=ok onclick="javascript:upload()" value="确定">
        <input type="button" ONCLICK="javascript:window.close();" value="取消">
      </TD>
    </TR>
  </TABLE>
</FORM>

</BODY>
</HTML>