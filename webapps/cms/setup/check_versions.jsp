<%@ page contentType="text/html;charset=gbk"%>
<!-- ------------- JSP DECLARATIONS -------- -->
<% /* Initialize the Bean */ %>
<jsp:useBean id="Bean" class="com.bizwink.boot.CmsSetup" scope="session" />
<% /* Set all given Properties */%>
<jsp:setProperty name="Bean" property="*" />
<% /* Import packages */ %>
<%@ page import="com.bizwink.boot.*" %>

<%

    /* next page to be accessed */
    String nextPage = "";

    /* request parameters */
    boolean submited = (request.getParameter("systemInfo") != null);
    boolean info = (request.getParameter("systemInfo") != null) && (request.getParameter("systemInfo").equals("false"));
    boolean accepted = (request.getParameter("accept") != null) && (request.getParameter("accept").equals("true"));

    /* Servlet engine */
    String servletEngine = "";
    boolean supportedServletEngine = false;
    int unsupportedServletEngine = -1;

    /* add supported engines here */
    String[] supportedEngines = {"Apache Tomcat/4.0", "Tomcat Web Server/3.3","Tomcat Web Server/3.1", "Resin/2.1.11"};

    /* add unsupported enginges here */
    String[] unsupportedEngines = {"Tomcat Web Server/3.2"};
    String[] unsEngMessages = {"WebBuilder does not work correctly with Tomcat 3.2.x. Tomcat 3.2.x uses its own XML parser which results in major errors while using WebBuilder. Please use Tomcat 4.0 instead."};

    /* JDK version */
    String requiredJDK = "1.3.0";
    String JDKVersion = "";
    boolean supportedJDK = false;

    /* true if properties are initialized */
    boolean setupOk = (Bean.getProperties()!=null);

    if(setupOk) {

        if(submited)    {
            if(Bean.getSetupType()) {
                nextPage = "advanced_1.jsp";
            }
            else    {
                nextPage = "save_properties.jsp";
            }
        }
        else    {
            /* checking versions */
            servletEngine = config.getServletContext().getServerInfo();
            JDKVersion = System.getProperty("java.version");

            CmsSetupUtils.writeVersionInfo(servletEngine, JDKVersion, config.getServletContext().getRealPath("/"));
            supportedJDK = CmsSetupUtils.compareJDKVersions(JDKVersion, requiredJDK);
            supportedServletEngine = CmsSetupUtils.supportedServletEngine(servletEngine, supportedEngines);
            unsupportedServletEngine = CmsSetupUtils.unsupportedServletEngine(servletEngine, unsupportedEngines);
        }
    }

%>
<!-- ---------------------------------------- -->

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
    <td><form method=POST>
        <table class=background width=700 height=500 border=0 cellpadding=5 cellspacing=0>
            <tr>
                <td class=title height=25>WebBuilder Setup Wizard</td>
            </tr>

            <tr>
                <td height=50 align=right><img src=../images/opencms.gif alt=WebBuilder border=0>&nbsp;</td>
            </tr>
            <% if(setupOk)  { %>
            <tr>
                <td height=375 align=center valign=middle>
                    <%  if(submited) {
                            if(info && !accepted)   {
                                out.print("<b>继续安装WebBuilder，因为你的系统还无法运行WebBuilder!");
                            }
                            else    {
                                out.print("<script language='Javascript'>document.location.href='" + nextPage + "';</script>");
                            }
                        } else { %>
                    <table border=0 cellpadding=5>
                        <tr><td class=bold width=100>JDK的版本:</td><td width=300><%= JDKVersion %></td><td width="30"><% if(supportedJDK)out.print("<img src=../images/check.gif>");else out.print("<img src=../images/cross.gif>"); %></td></tr>
                        <tr><td class=bold>Servlet引擎:</td><td><%= servletEngine %></td><td><% if(supportedServletEngine)out.print("<img src=../images/check.gif>");else if (unsupportedServletEngine > -1)out.print("<img src=../images/cross.gif>");else out.print("<img src=../images/unknown.gif>"); %></td></tr>
                        <tr><td colspan=3 height=30>&nbsp;</td></tr>
                        <tr><td align=center valign=bottom>
                        <%
                            boolean red = !supportedJDK || (unsupportedServletEngine > -1);
                            boolean yellow = !supportedServletEngine;

                            boolean systemOk = !(red || yellow);

                            if(red) {
                                out.print("<img src=../images/ampel_rot.gif>");
                            }
                            else if (yellow)        {
                                out.print("<img src=../images/ampel_gelb.gif>");
                            }
                            else    {
                                out.print("<img src=../images/ampel_gruen.gif>");
                            }
                        %>
                        </td>
                        <td colspan=2 valign=middle>
                        <%
                            if(red) {
                                out.println("<p><b>提示:</b> 你的系统还没有WebBuilder所需的组件，可能无法运行.</p>");
                                if (unsupportedServletEngine > -1) {
                                    out.println("<p>"+unsEngMessages[unsupportedServletEngine]+"</p>");
                                }
                            }
                            else if (yellow)    {
                                out.print("<b>提示:</b> 你的系统组件经过检测不符合要求，可能无法运行WebBuilder.");
                            }
                            else    {
                                out.print("你的系统组件已经经过成功检测，符合WebBuilder的要求.");
                            }
                        %></td>
                        </tr>
                        <tr><td colspan=3 height=30>&nbsp;</td></tr>
                        <% if(!systemOk)    { %>
                            <tr><td colspan=3 class=bold align=center><input type=checkbox name=accept value=true> 我已经注意到我的系统可以不需要这些组件运行WebBuilder. 继续.</td></tr>
                        <% } %>
                    </table>
                    <input type=hidden name=systemInfo value="<% if(systemOk)out.print("true");else out.print("false"); %>">
                    <% } %>
                </td>
            </tr>
            <tr>
                <td height=50 align=center>
                    <table border=0>
                        <tr>
                            <td width=200 align=right>
                                <input type=button class=button style="width:150px;" width=150 value="&#060;&#060; 上一步" onclick="history.go(-1)">
                            </td>
                            <td width=200 align=left>
                            <%  if(submited && info && !accepted)   { %>
                                <input type=button disabled name=submit class=button style="width:150px;" width=150 value="下一步 &#062;&#062;">
                            <% } else { %>
                                <input type=submit name=submit class=button style="width:150px;" width=150 value="下一步 &#062;&#062;">
                            <% } %>
                            </td>
                            <td width=200 align=center>
                                <input type=button class=button style="width:150px;" width=150 value="取消" onclick="location.href='cancel.jsp'">
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <% } else   { %>
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
</body>
</html>