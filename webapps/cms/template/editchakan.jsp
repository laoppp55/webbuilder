<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=utf-8"%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    int ii = ParamUtil.getIntParameter(request,"i",0);
    List list = new ArrayList();
    String url = application.getRealPath("/") + "sites" + java.io.File.separator + authToken.getSitename() + java.io.File.separator + "css\\menustyle.css";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(url)));
        String css = "";
        while ((css = br.readLine()) != null) {
            list.add(css);
        }
     String yangshi = "";
    if(ii < 0){}else{
        yangshi = (String)list.get(ii+1).toString().substring(1);
    }
%>
<input type="hidden" name="i" value="<%=ii%>" >
<table align="center"><tr><td align="center" >
<input type="text" name="" size="120" value="<%=yangshi%>" readonly>
</td></tr></table>
