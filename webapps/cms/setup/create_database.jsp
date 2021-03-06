<%@ page contentType="text/html;charset=gbk"%>
<!-- --------------- JSP DECLARATIONS ----------------------------- -->
<% /* Initialize the Bean */ %>
<jsp:useBean id="Bean" class="com.bizwink.boot.CmsSetup" scope="session" />

<% /* Set all given Properties */%>
<jsp:setProperty name="Bean" property="*" />

<% /* Import packages */ %>
<%@ page import="com.bizwink.boot.*,java.util.*" %>

<%
	/* next page to be accessed */
	String nextPage = "check_versions.jsp";

	/* true if properties are initialized */
	boolean setupOk = (Bean.getProperties()!=null);

	CmsSetupDb db = null;

	boolean createDb = false;
	boolean dbExists = false;
	boolean dropDb = false;

    boolean flag=false;

	if(setupOk)	{
		String temp = session.getAttribute("createDb").toString();
		if( temp != null)	{
			createDb = temp.equals("true");
			if(createDb)	{
				db = new CmsSetupDb(Bean.getBasePath());
				temp = request.getParameter("dropDb");
				dropDb = temp != null && "是".equals(temp);

				/* check if database exists */
				if(!dropDb)	{
					db.setConnection(Bean.getDbDriver(), Bean.getDbWorkConStr(), Bean.getDbWorkUser(),Bean.getDbWorkPwd());
					dbExists = db.noErrors();
					if(dbExists)	{
						db.closeConnection();
					}
					else	{
						db.clearErrors();
					}
				}
				if(!dbExists)	{
                    db.setConnection(Bean.getDbDriver(), Bean.getDbCreateConStr(), Bean.getDbCreateUser(), Bean.getDbCreatePwd());
				}
				else	{
					nextPage = "create_database.jsp";
				}
			}
		}
	}

%>
<!-- ----------------------------------------------------- -->
<html>
<head>
	<title>WebBuilder Setup Wizard</title>
	<meta http-equiv=Content-Type content=text/html; charset=gb2312>
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
			<% if(setupOk)	{ %>
			<tr>
				<td height=375 align=center valign=center>

					<table border=0 width=600 cellpadding=5>
						<tr>
							<td align=center valign=top height=50>
							<%
								if(!createDb)	{
									out.println("<p>您还没有创建WebBuilder数据库.</p><p><b>警告: &nbsp;&nbsp;</b>没有数据库和表，你将不能成功导入!</p>");
								}
								else {
									if(dbExists && !dropDb)	{
										out.println("<p><strong><font color=#ff0000>警告: </font> 已经存在数据库. 是否删除?</strong></p>");
										out.println("<p><nobr><input type=submit name=dropDb class=button value='是' style=width:150px; width=150>&nbsp;&nbsp;&nbsp;&nbsp;<input type=\"button\" value=\"否\" onClick=\"history.go(-2)\" style=\"width:150px;\" class=\"button\" width=\"150\"></nobr></p>");
									}
									else	{
										if(dropDb)	{
											//Drop Database
											out.print("<p>正在试图删除数据库  ......");
											db.dropDatabase(Bean.getResourceBroker(), Bean.getReplacer());
                                            if(db.noErrors())	{
                                               out.println(" <b>成功！</b></p>");
											}
											else	{
												out.println(" <b>失败！</b></p>");
												Vector errors = db.getErrors();
												out.print("<textarea rows=7 cols=50 style='width:600px;height:80px;' readonly wrap=off>");
												for(int i = 0; i < errors.size(); i++)	{
													out.println(errors.elementAt(i));
													out.println("-------------------------------------------");
												}
												out.print("</textarea><br>");
												db.clearErrors();
											}
										}
							%>
							</td></tr>
							<td align=center valign=top height=50>
							<%

										//Create Database
										out.print("<p>创建数据库 ......");
										db.createDatabase(Bean.getResourceBroker(), Bean.getReplacer());
										if(db.noErrors())	{
											out.println(" <b>成功！</b></p>");
										}
										else	{
											out.println(" <b>失败</b></p>");
											Vector errors = db.getErrors();
											out.print("<textarea rows=7 cols=50 style='width:600px;height:80px;' readonly wrap=off>");
											for(int i = 0; i < errors.size(); i++)	{
												out.println(errors.elementAt(i));
												out.println("-------------------------------------------");
											}
											out.print("</textarea><br>");
											db.clearErrors();
										}

							%>
								</td></tr>
								<td align=center valign=top height=50>
							<%
										db.closeConnection();
										db.setConnection(Bean.getDbDriver(), Bean.getDbWorkConStr(), Bean.getDbWorkUser(),Bean.getDbWorkPwd());
										//Create Tables
										out.print("<p>创建数据表 ......");
										db.createTables(Bean.getResourceBroker());
										if(db.noErrors())	{
                                            flag=true;
											out.println(" <b>成功！</b></p>");
										}
										else	{
											out.println(" <b>失败！</b></p>");
											Vector errors = db.getErrors();
											out.print("<textarea rows=7 cols=50 style='width:600px;height:80px;' readonly wrap=off>");
											for(int i = 0; i < errors.size(); i++)	{
												out.println(errors.elementAt(i));
												out.println("-------------------------------------------");
											}
											out.print("</textarea><br>");
											db.clearErrors();
											db.closeConnection();
										}
									}
								}
							%>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td height=50 align=center>
					<table border=0>
						<tr>
							<td width=200 align=right>
								<input type=button class=button style="width:150px;" width=150 value="&#060;&#060; 上一步" onclick="history.go(-2)" <% if(dbExists) out.println("disabled"); %>>
							</td>
							<td width=200 align=left>
								<input type=submit name=submit class=button style="width:150px;" width=150 value="下一步 &#062;&#062;" <% if(!flag||dbExists) out.println("disabled"); %>>
							</td>
							<td width=200 align=center>
								<input type=button class=button style="width:150px;" width=150 value="取消" onclick="location.href='cancel.jsp'">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<% } else	{ %>
			<tr>
				<td align=center valign=top>
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
</td>
</tr>
</table>
</body>
</html>