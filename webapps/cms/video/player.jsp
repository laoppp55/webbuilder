<%@ page import="com.bizwink.util.ParamUtil" %>
<%@ page contentType="text/html;charset=GBK" import="com.bizwink.util.*"%>

<html>
	<head><title>康师傅茉莉清茶优雅沙龙—优雅生活，倾心分享</title></head>
	<%
        String getflvpath = ParamUtil.getParameter(request, "flvvalue");
    %>
	<body>
<div align="center">
  <object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"
          codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"
          id="blogmusicplayer" width="445" height="365" align="middle">
      <param name="allowScriptAccess" value="sameDomain"/>
      <param name="movie" value="FLVPlayer_Progressive.swf?flvpath=<%=getflvpath%>"/>
      <param name="quality" value="high"/>
      <param name="bgcolor" value="#ffffff"/>
      <param name="wmode" value="transparent"/>
      <embed src="FLVPlayer_Progressive.swf?flvpath=<%=getflvpath%>" width="445" height="365" align="middle" quality="high"
             bgcolor="#ffffff" swLiveConnect=true id="blogmusicplayer" name="blogmusicplayer"
             allowScriptAccess="sameDomain" type="application/x-shockwave-flash"
             pluginspage="http://www.macromedia.com/go/getflashplayer" wmode="transparent"/>
  </object>
</div>
</body>
</html>