<%@ page contentType="text/html;charset=gbk"%>
<!-- ------------------------------------------------- JSP DECLARATIONS ------------------------------------------------ -->
<% /* Initialize the Bean */ %>
<jsp:useBean id="Bean" class="com.bizwink.boot.CmsSetup" scope="session" />

<% /* Set all given Properties */%>
<jsp:setProperty name="Bean" property="*" />

<% /* Import packages */ %>
<%@ page import="com.bizwink.boot.*,java.util.*" %>

<%

	/* next page to be accessed */
//	String nextPage = "run_import.jsp";

    String nextPage = "finished.jsp";

	/* true if properties are initialized */
	boolean setupOk = (Bean.getProperties()!=null);

	/* true if there are errors */
	boolean error = false;

	Vector errors = new Vector();

	if(setupOk)	{
		/* Save Properties to file "config.properties" */
		CmsSetupUtils Utils = new CmsSetupUtils(Bean.getBasePath());

        Utils.saveProperties(Bean.getProperties(),"cms.properties",true);

        //add by hu
        Utils.saveCfgProperties(Bean.getProperties(),"config.properties",true);

		errors = Utils.getErrors();
		error = !errors.isEmpty();
	}

%>
<!-- ------------------------------------------------------------------------------------------------------------------- -->

<html>
<head>
	<title>WebBuilder Setup Wizard</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="Stylesheet" type="text/css" href="style.css">
</head>

<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
<tr>
<td align="center" valign="middle">
<table border="1" cellpadding="0" cellspacing="0">
<tr>
	<td><form action="<%= nextPage %>" method="POST">
		<table class="background" width="700" height="500" border="0" cellpadding="5" cellspacing="0">
			<tr>
				<td class="title" height="25">WebBuilder Setup Wizard</td>
			</tr>

			<tr>
				<td height="50" align="right"><img src="../images/opencms.gif" alt="WebBuilder" border="0"></td>
			</tr>
			<% if(setupOk)	{ %>
			<tr>
				<td height="375" align="center" valign="top">

					<table border="0" width="600" cellpadding="5">
						<tr>
							<td align="center" valign="top" height="125">
								保存设置 ......
								<%
									if(error)	{
										out.print("<b>失败！</b><br>");
										out.println("<textarea rows='10' cols='50'>");
										for(int i = 0; i < errors.size(); i++)	{
											out.println(errors.elementAt(i));
											out.println("-------------------------------------------");
										}
										out.println("</textarea>");
										errors.clear();
									}
									else	{
										out.print("<b>成功！</b>");
									}
								%>
							</td>
						</tr>
		<!--				<tr>
							<td align="center">
								<b>你是否想导入数据?</b><br>
							</td>
						</tr>
						<tr>
							<td class="bold" align="center">
								<input type="radio" name="importWorkplace" value="true" checked> 是
								<input type="radio" name="importWorkplace" value="false" > 否
							</td>
						</tr>   -->
					</table>
				</td>
			</tr>
			<tr>
				<td height="50" align="center">
					<table border="0">
						<tr>
							<td width="200" align="right">
								<input type="button" class="button" style="width:150px;" width="150" value="&#060;&#060; 上一步" onclick="history.go(-2)">
							</td>
							<td width="200" align="left">
								<input type="submit" name="submit" class="button" style="width:150px;" width="150" value="下一步 &#062;&#062;">
							</td>
							<td width="200" align="center">
								<input type="button" class="button" style="width:150px;" width="150" value="取消" onclick="location.href='cancel.jsp'">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<% } else	{ %>
			<tr>
				<td align="center" valign="top">
                    <p><b>错误！</b></p>
                    没有正确启动安装导航!<br>
					请点击<a href="">这儿</a> 重新启动！
				</td>
			</tr>
			<% } %>
			</form>
			</table>
		</td>
	</tr>
</table>
</body>
</html>