<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Upload Page</title>
</head>
<body>

URL of this page is http://localhost:8080/webbuilder/upload/fileUploadApplet.jsp
<BR>

<applet
	code="com/bizwink/jupload/startup.class"
	archive="../com/bizwink/applets/JUpload.jar"
	width="900"
	height="500">

	<PARAM NAME="debug" VALUE="true">
	<PARAM NAME="fixJakartaBug" VALUE="true">

	<param name="actionURL" value="http://localhost:8080/webbuilder/upload/fileUploadAppletProcessor.jsp">
	<param name="imageURL" value="/webbuilder/images/JUpload.gif">
	<PARAM NAME="checkResponse" VALUE="true">
</applet>


</body>
</html>