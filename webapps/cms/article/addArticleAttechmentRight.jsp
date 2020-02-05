<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    //请为程序添加必要的注释
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int articleID = ParamUtil.getIntParameter(request, "article", 0);
    String msg = ParamUtil.getParameter(request, "msg");

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
    <script type="text/javascript" src="../js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="../js/jquery.form.js"></script>
    <script type="text/javascript" src="../js/json2.js"></script>
    <script type="text/javascript" src="../js/json_parse.js"></script>
    <script language="JavaScript">
        $(document).ready(function(){     //初始化ztree对象
            htmlobj=$.ajax({
                url:"getAttechments.jsp",
                type:'post',
                dataType:'json',
                data:{
                    colid:<%=columnID%>,
                    article:<%=articleID%>
                },
                async:false,
                cache:false,
                success:function(data){
                    var json_data = "";
                    if(typeof(data.errcode)=="undefined"){                 //用于文章有附件，且是修改文章的情况
                        var s_json = top.opener.document.createForm.attechments.value;
                        if (s_json==null || s_json=="") {
                            //第一次进入该页面，将数据库中的值赋给opener的隐含字段
                            var val_in_db = JSON.stringify(data);
                            top.opener.document.createForm.attechments.value = val_in_db.substring(1,val_in_db.length-1);
                            json_data ="{\"rows\":[" +  top.opener.document.createForm.attechments.value + "]}";
                        }else
                            json_data ="{\"rows\":[" +  top.opener.document.createForm.attechments.value + "]}";
                        if (s_json!=null) {
                            var mydata = jQuery.parseJSON(json_data);
                            for(var rr=0;rr<mydata.rows.length;rr++){
                                CreateRow(mydata.rows[rr].cname,mydata.rows[rr].summary,mydata.rows[rr].createdate);
                            }
                        }
                    } else {                                                 //用于创建文章，或者编辑文章第一次增加附件
                        var s_json = top.opener.document.createForm.attechments.value;
                        json_data ="{\"rows\":[" +  top.opener.document.createForm.attechments.value + "]}";
                        if (s_json!=null) {
                            var mydata = jQuery.parseJSON(json_data);
                            for(var rr=0;rr<mydata.rows.length;rr++){
                                CreateRow(mydata.rows[rr].cname,mydata.rows[rr].summary,mydata.rows[rr].createdate);
                            }
                        }
                    }
                }
            });
        });

        function uploadAttechment(){
            var myData = {
                "column":<%=columnID%>,
                "article":<%=articleID%>
            };

            var ajaxFormOption = {
                type: "post",                                                     //提交方式
                dataType: "json",                                                //数据类型
                data: myData,                                                      //自定义数据参数，视情况添加
                url: "../upload/uploadAttechment.jsp?doUpload=true",       //请求url
                success: function (data) {                                         //提交成功的回调函数
                    var s_data = top.opener.document.createForm.attechments.value;
                    if (s_data==null || s_data=="")
                        top.opener.document.createForm.attechments.value = JSON.stringify(data);
                    else
                        top.opener.document.createForm.attechments.value=top.opener.document.createForm.attechments.value + "," + JSON.stringify(data);
                    if (data.cname!=null && data.cname!="" && data.filename!=null)
                        CreateRow(data.cname,data.summary,data.createdate);
                }
            };

            //不需要submit按钮，可以是任何元素的click事件
            $("#form1").ajaxSubmit(ajaxFormOption);
        }

        function CreateRow(cname,summary,createdate) {
            var $table = $('#table1');
            var rows = $("#table1 tr").length;
            var tr="<tr><td><input type='checkbox' name='a'" + rows + "></td><td>" + cname + "</td><td>" + summary +
                    "</td><td>" + createdate + "</td><td>修改</td><td><a href='#' onclick='javascript:DeleteRow(" + rows + ");'>删除</td></tr>";
            $table.append(tr);
        }

        function DeleteRow(row) {
            var s_json = top.opener.document.createForm.attechments.value;
            var json_data ="{\"rows\":[" +  top.opener.document.createForm.attechments.value + "]}";
            var mydata = jQuery.parseJSON(json_data);
            var result_data = "";
            for(var rr=0;rr<mydata.rows.length;rr++){
                if ((rr+1) != (row-1)) {
                    result_data = result_data + JSON.stringify(mydata.rows[rr]) + ",";
                }
            }
            if (result_data.length>0) result_data = result_data.substring(0,result_data.length-1);
            top.opener.document.createForm.attechments.value = result_data;
            alert(result_data);
            $("#table1 tr").eq(row).remove();
        }


    </script>
</head>
<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<%
    if (msg!=null) out.println("<span class=cur>" + msg + "</span>");
%>
<table  id="table1" border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
    <tr>
        <td colspan=6>当前所在栏目-->><font color=red><%=CName%></font></td>
    </tr>
    <tr class=itm bgcolor="#dddddd">
        <td align=center width="5%">选中</td>
        <td align=center width="20%">标题</td>
        <td align=center width="40%">概述</td>
        <td align=center width="15%">上传时间</td>
        <td align=center width="10%">修改</td>
        <td align=center width="10%">删除</td>
    </tr>
</table>
<p align=center>
<form enctype="multipart/form-data" id="form1">
    <table border="0" cellspace="0">
        <tr>
            <td>附件名称：</td>
            <td><input type="text" name="cname" value="" size="100"></td>
        </tr>
        <tr>
            <td>附件介绍：</td>
            <td><input type="text" name="brief" value="" size="100"></td>
        </tr>
        <tr>
            <td>附    件：</td>
            <td><input type="file" name="attfile" value=""></td>
        </tr>
        <tr>
            <td><input type=button value="  上传  " onclick="uploadAttechment();"></td>
            <td>    <input type=button value="  返回  " onclick="top.close();">            </td>
        </tr>
    </table>
</form>
</p>
</BODY>
</html>