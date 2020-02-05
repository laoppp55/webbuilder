<%@ page import="java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%@ page import="com.bizwink.cms.markManager.IMarkManager" %>
<%@ page import="com.bizwink.cms.markManager.markPeer" %>
<%@ page import="com.bizwink.cms.markManager.mark" %>
<%@ page import="com.bizwink.cms.markManager.ArticleMark" %>

<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }
    IMarkManager markMgr = markPeer.getInstance();
    int articleid=ParamUtil.getIntParameter(request,"articleid",-1);
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    System.out.println("articleid="+articleid);
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    String msg = ParamUtil.getParameter(request, "msg");

    List list=markMgr.getArticleListMark(siteid,columnID);
  //  System.out.println("list="+list.size()+" columnid="+columnID+"  siteid="+siteid);
    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String CName = StringUtil.gb2iso4View(column.getCName());

    IArticleManager articleMgr = ArticlePeer.getInstance();
    int total = articleMgr.getRelatedArticlesNum(columnID,siteid);
    List articleList = articleMgr.getRelatedArticles(columnID,siteid, start, range);
    int articleCount = articleList.size();
    List getlist=markMgr.getArticleListQuery(articleid);
%>

<html>
<head>
    <title></title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css"></head>
 <script language="javascript" src="../js/mark.js"></script>
<script language="JavaScript">
     function a(id,maintitle)
     {
         //alert(maintitle);
         var el = document.getElementById('selectedArticle');
            var optionLen = el.options.length;

            i = 0;
            var endFlag = true;
            while (i < optionLen && endFlag)
            {
                if (el.options[i].value == id)
                    endFlag = false;
                i++;
                
            }

            if (endFlag){

                var red=document.getElementById(id+"_red").checked;
                var b=document.getElementById(id+"_b").checked;
                var top=document.getElementById(id+"_top").checked;

                if(red)
                {
                    maintitle=maintitle+"-飘红";
                    id=id+"-red"
                }
                if(b)
                {
                    maintitle=maintitle+"-加粗";
                    id=id+"-b"
                }
                if(top)
                {
                    maintitle=maintitle+"-置顶";
                    id=id+"-top"
                }
                document.getElementById('selectedArticle').options.add(new Option(maintitle, id, false, false));
            }

     }
    function ok()
    {
        var attrVal="";
        alert(markForm.selectedArticle.length);
        for (var i = 0; i < markForm.selectedArticle.length; i++)
       {
           attrVal = attrVal + markForm.selectedArticle.options[i].value + ",";
       }
         alert(attrVal);
         window.dialogArguments.document.getElementById("markid").value=attrVal;
         window.close();
    }
</script>

<BODY BGCOLOR="#ffffff" LINK="#000099" ALINK="#cc0000" VLINK="#000099" TOMARGIN=8>
<form name=markForm>
    <table width="100%" border=0 cellpadding=2 cellspacing=1 bgcolor="#CCCCCC">
    <tr bgcolor="#F5F5F5">
        <td colspan=4>当前所在栏目：<font color=red><%=CName%>
        </font></td>
    </tr>
    <tr bgcolor="#F5F5F5">
        <td align=center width="10%">选中</td>
        <td align=center width="30%">中文名称</td>
        <td align=center width="60%">英文名称</td>

    </tr>
    <%
        for(int i=0;i<list.size();i++){
            mark mark =(mark)list.get(i);
            String maintitle = mark.getChineseName();
            String bgcolor = (i % 2 == 0) ? "#ffffcc" : "#eeeeee";
            maintitle =
            maintitle = StringUtil.replace(maintitle, "\"", "&quot;");


            out.println("<tr bgcolor=" + bgcolor + ">");
            out.println("<td align=center><input type=button class=tine value=add onclick=a("+mark.getID()+",'"+mark.getChineseName()+"') ></td>");
            out.println("<td >" + maintitle + " </td>");
            out.println("<td align=center >&nbsp;&nbsp;<input type='checkbox' id="+mark.getID()+"_top name=shuxing value='top' >置顶&nbsp;&nbsp;<input type='checkbox' id="+mark.getID()+"_red name=shuxing value='red' >飘红&nbsp;&nbsp;<input type='checkbox' name=shuxing id="+mark.getID()+"_b value='b' >加粗</td>");
            out.println("</tr>");
        }
    %>
</table>


    <table border=1 borderColorDark=#ffffec borderColorLight=#5e5e00 cellPadding=0 cellSpacing=0 width="100%">
        <tr>
            <td colspan=5>当前所在栏目-->><font color=red><%=CName%></font></td>
        </tr>
     
        <tr>
            <td>文章发布的目的栏目：
                <select size=1 name="selectedArticle" id="selectedArticle" style="width:200;height:100"  multiple>
                    <%
                       for(int i=0;i<getlist.size();i++)
                       {
                           ArticleMark am=(ArticleMark)getlist.get(i);
                           int markid=am.getMarkid();
                           String fontcolor=am.getFontcolor();
                           String fontziti=am.getFontziti();
                           String istop=am.getIstop();


                           mark m=markMgr.getAMark(markid);
                           String str=m.getChineseName();
                           String maks=markid+"";
                            if(fontcolor!=null)
                           {
                              str=str+"-"+fontcolor;
                              maks=markid+"-"+fontcolor;
                           }
                           if(fontziti!=null)
                           {
                               str=str+"-"+fontziti;
                               maks=maks+"-"+fontziti;
                           }
                           if(istop!=null)
                           {
                               str=str+"-"+istop;
                               maks=maks+"-"+istop;
                           }
                    %>
                    <option value=<%=maks%>><%=str%></option>
                    <%}%>
                </select>  <input type="button" name="delete" value="删除" onclick="delArticleItem();"
                               style="height:20;width:30;font-size:9pt">
            </td>
        </tr>
    </table>

    <input type=button value="  确定  " onclick="javascript:ok();">&nbsp;&nbsp;
    <input type=button value="  取消  " onclick="top.close();">
</form>
</BODY>
</html>