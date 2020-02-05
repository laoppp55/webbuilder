<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    IArticleManager articleMgr = ArticlePeer.getInstance();
    int total = articleMgr.getRelatedArticlesNum(columnID,siteid);
    List articleList = articleMgr.getRelatedArticles(columnID,siteid, start, range);
    int articleCount = articleList.size();
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css"></head>
<script language="JavaScript">
    function confirm_selectedcolumns()
    {
        var msgstr = "";
        var result = "";

        for(var e=0; e<selform.selectedcolumns.length; e++) {
            value = "c" + selform.selectedcolumns[e].value;
            text = selform.selectedcolumns[e].text;
            result = result  + value + "-" + text + "\r\n";
        }

        var isMSIE = (navigator.appName == "Microsoft Internet Explorer");
        if (isMSIE) {
            window.returnValue = result;
            window.parent.close();
        }else{
           if (result != "" && result != undefined)
           {
               result = result.substring(0, result.length-2);
               var regexp =/\r\n/g;
               var rtnAry = result.split(regexp);

               if (rtnAry !=null) {
                   var len = rtnAry.length;
                   var i =0;

                   while( i < len) {
                       posi = rtnAry[i].indexOf("-");
                       value = rtnAry[i].substring(0,posi);
                       text = rtnAry[i].substring(posi+1);
                       window.parent.opener.document.getElementById("pcolumnsID").options.add(new Option(text,value));
                       i++;
                   }
               }
           }
           top.close();
        }

        /*len = selform.selectedcolumns.length;
        for(var i=0; i<len; i++) {
            //alert(text + "---" + value);
            var option = new Option();
            option.text = selform.selectedcolumns[i].text;
            option.value=selform.selectedcolumns[i].value;
            alert(window.parent.opener.document.createForm.pcolumnsID);
            //window.parent.opener.document.createForm.pcolumnsID.options.add(option);
            window.parent.opener.document.getElementById("pcolumnsID").options.add(option);
        } */
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<form name=selform>
    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
        <tr>
            <td colspan=5>当前所在栏目-->><font color=red><%=CName%></font></td>
        </tr>
        <tr>
            <td>文章发布的目的栏目：
                <select size=1 name="columns" id="selectedcolumns" style="width:200;height:100"  multiple>
                </select>
            </td>
        </tr>
    </table>

    <input type=button value="  确定  " onclick="javascript:confirm_selectedcolumns();">&nbsp;&nbsp;
    <input type=button value="  取消  " onclick="top.close();">
</form>
</BODY>
</html>