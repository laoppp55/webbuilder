<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.news.*,
                 com.bizwink.cms.server.*,
                 com.bizwink.cms.tree.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*,
                 com.booyee.search.*,
                 com.booyee.other.*"
                 contentType="text/html;charset=gbk"
%>
<%
  Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
  if (authToken == null)
  {
    response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
    return;
  }
  IOtherManager otherMgr = OtherPeer.getInstance();
  String userid = authToken.getUserID();
  int siteid = 1;

  long bookid = ParamUtil.getLongParameter(request, "bookid", 0);
  int comflag = ParamUtil.getIntParameter(request, "comflag", 0);

  otherMgr.checkpic(bookid);

  ISearchManager searchMgr = SearchPeer.getInstance();
  Search search = new Search();
  String[] picture = new String[10];
  search = searchMgr.ListPic(bookid);
  picture[0] = search.getPicture1();
  picture[1] = search.getPicture2();
  picture[2] = search.getPicture3();
  picture[3] = search.getPicture4();
  picture[4] = search.getPicture5();
  picture[5] = search.getPicture6();
  picture[6] = search.getPicture7();
  picture[7] = search.getPicture8();
  picture[8] = search.getPicture9();
  picture[9] = search.getPicture10();



%>
<html>
<head>
<title>上传图片</title>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<link rel="stylesheet" href="../../images/pt9.css">
<link rel=stylesheet type=text/css href=../style/global.css>
<script language="javascript">
function upload(num,bookid)
{
  window.open("uploadfiles2.jsp?num="+num+"&bookid="+bookid,"上传图片","width=347,height=175,toolbar=no,top=200,left=200,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no");
}

function gosucc(bookid){
  window.location = "../book/ruku.jsp?bookid="+bookid;
}
function delpic(r,p){
  uploadfile.action="delpic.jsp?bookid="+r+"&picnum=picture"+p
  uploadfile.submit()
}
</script>
</head>

<body bgcolor="#FFFFFF">
<center><br>
<form name=uploadfile enctype="multipart/form-data" method="post">
        <table width="347" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td class="moduleTitle"><font color="#48758C">上传图片</font></td>
          </tr>
          <tr bgcolor="#d4d4d4" align="right">
            <td>
              <table width="100%" border="0" cellpadding="2" cellspacing="1">
                <% for(int i=1 ;i<=10 ;i++){;%>
                  <tr bgcolor="#FFFFFF">
                    <td align="center">图片<%=i%>：</td>
                    <%if(picture[i-1] == null){%>
                    <td align="center"> <input type="button" value="上传图片"+<%=i%> onclick="javascript:upload(<%=i%>,<%=bookid%>)">&nbsp;</td>
                    <td align="center"> &nbsp;</td>
                    <td align="center"> <a href="#" onclick="delpic('<%=bookid%>','<%=i%>')" >删除图片</a></td>
                    <%}else{
                      String pic = "../../../"+picture[i-1];
                    %>
                    <td align="center"> <input type="button" value="修改图片"+<%=i%> onclick="javascript:upload(<%=i%>,<%=bookid%>)">&nbsp;</td>
                    <td align="center"> <img src=<%=pic%> width="40" height="50"></td>
                    <td align="center"> <a href="#" onclick="delpic('<%=bookid%>','<%=i%>')">删除图片</a></td>
                   <%}%>
                 </tr>
                <%}%>
              </table>
            </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr align="center">
            <%
              //if(comflag == 0){
            %>
            <!--td><input type="button" name="button" value="完成" onclick="javascript:gosucc(<%//=bookid%>);"></td-->
            <%
              //}else{
            %>
            <td><input type="button" name="button" value="完成" onclick="javascript:window.close();"></td>
            <%//}%>
          </tr>
        </table>
<!--p align="center"><input type="button" name="Ok" onclick="javascript:upload()" value="开始上传"></p-->
</form>
<p>&nbsp; </p>
</center>
</body>
</html>