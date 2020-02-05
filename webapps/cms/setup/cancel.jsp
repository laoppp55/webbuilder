<%@ page contentType="text/html;charset=gbk"%>
<!-- ---------------- JSP DECLARATIONS ----------------- -->
<jsp:useBean id="Bean" class="com.bizwink.boot.CmsSetup" scope="session" />
<jsp:useBean id="Thread" class="com.bizwink.boot.CmsSetupThread" scope="session"/>
<%
	/* stop possible running threads */
	Thread.stopLoggingThread();
	Thread.stop();
	/* invalidate the sessions */
	request.getSession().invalidate();
	/* next page to be accessed */
	String nextPage = "";
%>
<!-- -------------------------------------------------------- -->
<html>
<head>
	<title>WebBuilder Setup Wizard</title>
	<meta http-equiv=Content-Type content=text/html;charset=gb2312>
	<link rel=Stylesheet type=text/css href=style.css>
</head>
<body>
<table width=100% height=100% border=0 cellpadding=0 cellspacing=0>
<tr>
<td align=center valign=middle>
<table border=1 cellpadding=0 cellspacing=0>
<tr>
	<td><form action="<%= nextPage %>" method=POST>
		<table class=background width=700 height=500 border=0 cellpadding=5 cellspacing=0>
			<tr>
				<td class=title height=25>WebBuilder Setup Wizard</td>
			</tr>

			<tr>
				<td height=50 align=right><img src=../images/opencms.gif alt=WebBuilder border=0></td>
			</tr>
			<tr>
				<td height=375 align=center valign=middle>
					<strong>安装取消!</strong><br><br>请点击 <a href=''>这里</a> 重新开始
				</td>
			</tr>
			<tr>
				<td height=50 align=center>
					<table border=0>
						<tr>
							<td width=200 align=right>
								<input type=button disabled class=button style=width:150px; width=150 value="&#060;&#060; 上一步">
							</td>
							<td width=200 align=left>
								<input type=button disabled class=button style=width:150px; width=150 value="下一步 &#062;&#062;">
							</td>
							<td width=200 align=center>
								<input type=button disabled class=button style=width:150px; width=150 value=取消 >
							</td>
						</tr>
					</table>
				</td>
			</tr>
			</form>
			</table>
		</td>
	</tr>
</table>
</td>
</tr>
</table>
</body>
</html>