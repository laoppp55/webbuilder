<%@ page import="java.util.*,
		             com.bizwink.cms.util.*,
		             com.bizwink.cms.audit.*,
                 com.bizwink.cms.security.*"
		 contentType="text/html;charset=gbk"
%>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
	if (authToken == null) {
		response.sendRedirect( "../login.jsp?url=member/removeMember.jsp" );
		return;
	}

	if (!SecurityCheck.hasPermission(authToken, 54)) {
		request.setAttribute("message","�޹����û���Ȩ��");
		response.sendRedirect("../error.jsp?message=�޹����û���Ȩ��");
		return;
	}

	String userID = ParamUtil.getParameter(request, "userid");
	boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
	int errcode = 1;

	if (doUpdate) {
		String pass = ParamUtil.getParameter(request, "pass1");
		IAuditManager auditMgr = AuditPeer.getInstance();
		errcode = auditMgr.updatePassword(userID, pass);
	}
%>

<html>
<head>
	<title>�޸��û�����</title>
	<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
	<link rel="stylesheet" type="text/css" href="../style/global.css">
	<link rel="stylesheet" href="../design/css/layui.css">
	<script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
	<script type="text/javascript" src="../design/layui.js"></script>
	<script>
        $(document).ready(function(){
            layui.use('element', function(){
                var element = layui.element;
            });

            var errcode = <%=errcode%>;
            if (errcode==0) {
                alert('�û������޸ĳɹ�������');
                window.close();
			}
        });
	</script>
</head>
<%
	String[][] titlebars = {
			{ "�޸�����", "" }
	};
	String [][] operations = null;
%>
<%@ include file="../inc/titlebar.jsp" %>

<p>
	&nbsp;&nbsp;&nbsp;<font class=tine>�޸��û� <b><%= userID%></b>&nbsp;������</font>
<p>

<form action="editPassword.jsp" name="updateForm" onsubmit="javascript:return CheckPass();">
	<input type="hidden" name="doUpdate" value="true">
	<input type="hidden" name="userid" value="<%=userID%>">
	&nbsp;&nbsp;
	<font class=tine>�����������룺</font><input type="password" name="pass1" size=50 class=tine>
	<br><br>
	&nbsp;&nbsp;
	<font class=tine>�ٴ��������룺</font><input type="password" name="pass2" size=50 class=tine>
	<br><br><br>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="submit" value=" ȷ�� ">&nbsp;&nbsp;
	<input type="button" value=" ȡ�� " onclick="javascript:window.close();">
</form>

<script language="JavaScript">
    function CheckPass()
    {
        var passwd = document.updateForm.pass1.value;
        var passwd2 = document.updateForm.pass2.value;

        if (passwd.length < 8){
            alert("�û����볤�ȱ���8λ����");
            return false;
        }

        var regex = /[0-9]/;
        if(!regex.test(passwd)){
            alert("��������������");
            return false;
        }

        regex = /[a-z]/;
        if(!regex.test(passwd)){
            alert("����������Сд��ĸ");
            return false;
        }

        regex = /[A-Z]/;
        if(!regex.test(passwd)){
            alert("������������д��ĸ");
            return false;
        }

        regex = /\W/;
        if(!regex.test(passwd)){
            alert("���������������ַ�");
            return false;
        }

        if (passwd == "" || passwd2 == "")
        {
            alert("���벻��Ϊ��");
            return false;
        }

        if (passwd != passwd2)
        {
            alert("������������벻��ͬ");
            return false;
        }

        return true;
    }
</script>

</body>
</html>