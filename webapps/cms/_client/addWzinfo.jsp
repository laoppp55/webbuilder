<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bizwink.util.SpringInit" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.btob.products.po.WzClassinfo" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.btob.products.prodservice.drwzclassinfo" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-5-29
  Time: 上午8:58
  To change this template use File | Settings | File Templates.
--%>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    BigDecimal siteid = BigDecimal.valueOf(authToken.getSiteID());

    ApplicationContext appContext = SpringInit.getApplicationContext();
    String wzbm_Model = "xx-xxxx-xxxxxx";
    String[] wzbm_fields = wzbm_Model.split("-");
    List<WzClassinfo> ll = null;
    if (appContext!=null) {
        drwzclassinfo drwzclassinfo = (drwzclassinfo)appContext.getBean("drwzclassinfo");
        ll =  drwzclassinfo.getInfolistFromDBByPrefix(siteid,"xx");
    }
%>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.form.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btnsubmit").click(function () {
                var myData = {
                    "doCreate": "true"
                };

                var getvalflag = false;
                var retval = "";
                var ajaxFormOption = {
                    type: "post",                                                     //提交方式
                    dataType: "html",                                                //数据类型
                    data: myData,                                                      //自定义数据参数，视情况添加
                    url: "../_client/doAddWzinfo.jsp",                             //请求url
                    success: function (data) {                                         //提交成功的回调函数
                        alert(data);
                        window.returnValue=data;
                        window.close();
                    }
                };

                //不需要submit按钮，可以是任何元素的click事件
                $("#form1").ajaxSubmit(ajaxFormOption);

                if (getvalflag == false)
                    return false;
                else {
                    window.returnValue=retval;
                    window.close();
                }
            });
        });
    </script>
</head>
<body>
<div id="infos" class="layui-form">
    <form name="wzform" id="form1" method="post" action="" enctype="multipart/form-data" target="_self">
        <input type="hidden" name="doCreate" value="true">
        商品名称：<input type="text" name="wzname" value="" size="50"><br />
        商品类别：
        <select name="wztype" style="width: 300">
            <option value="0">请选择</option>
            <%
                if (ll!=null) {
                    for(int ii=0; ii<ll.size(); ii++) {
                        WzClassinfo wzClassinfo = ll.get(ii);
                        out.println("<option value=\"" + wzClassinfo.getCLASSCODE() +"\">" + wzClassinfo.getCNAME() + "</option>");
                    }
                }
            %>
        </select><br />
        商品描述：
        <textarea rows="20" cols="50" name="wzdesc"></textarea><br />
        商品图片：<br />
        <input type="file" name="wzimg1"><br />
        <input type="file" name="wzimg2"><br />
        <input type="file" name="wzimg3"><br />
        <input type="file" name="wzimg4"><br />
        <input type="file" name="wzimg5"><br />
        <input type="button" name="uploadinfo" id="btnsubmit" value="确定">
        <input type="button" name="cancel" id="btncancel" value="取消" onclick="window.close();">
    </form>
</div>
</body>
</html>
