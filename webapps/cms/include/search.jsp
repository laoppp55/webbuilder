<%@ page import="java.sql.*,
                 java.util.*,
                 com.bizwink.cms.util.*,
                 org.apache.lucene.document.*,
               	 com.bizwink.search.*"
%>

<%
  String searchstr = ParamUtil.getParameter(request, "content");
  int pages        = ParamUtil.getIntParameter(request, "pages", 1);

  List list = new ArrayList();
  SearchFilesServlet searchFile = new SearchFilesServlet();
  if (searchstr != "" && searchstr != null)
  {
    list = searchFile.getFiles(searchstr,pages);
  }
%>
<HTML>
<HEAD>
<TITLE>搜索</TITLE>
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=gb2312">
<link href="images/ui.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	background-color: #FFFFFF;
}
.style1 {color: #0000FF}
.style3 {color: #000000}
-->
</style>
</HEAD>

<BODY LEFTMARGIN=0 TOPMARGIN=0 MARGINWIDTH=0 MARGINHEIGHT=0>
<%@ include file="head.html" %>
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="9" background="images/lspd_04.gif"></td>
  </tr></table>
<%@include file="ad/third/index.html"%>
<table width="760" height="25" border="1" align="center" cellpadding="2" cellspacing="2" bordercolor="#009999">
  <tr>
    <td bgcolor="#99CCCC"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <form method="post" action="search.jsp" name="form2">
        <td width="11%"><span class="style3">请输入关键字:</span></td>
        <td width="18%"><input name="content" type="text" size="20" class="select"></td>
        <td width="55%"><input type="submit" name="Submit" value="搜索" class="select"></td>
        <td width="16%">&nbsp;</td>
        </form>
      </tr>
    </table></td>
  </tr>
</table>
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="3"></td>
  </tr>
</table>
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">

  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td valign="top" bgcolor="B4B4B4"><table width="100%"  border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td bgcolor="#FFFFFF"><table width="100%"  border="0" cellspacing="4" cellpadding="0">
                  <tr>
                    <td align="center" bgcolor="ECECEC"><table width="98%"  border="0" cellspacing="0" cellpadding="0">
                        <tr>
<%
  int listsize = searchFile.getNums(searchstr);
  int totalpages = 0;
  String content = "";

  if(listsize%10 ==0)
    totalpages = listsize/10;
  else
    totalpages = listsize/10+1;
  if(totalpages == 0)
    totalpages = 1;
%>
                          <td width="20"><img src="images/point4.gif" width="11" height="11"></td>
                          <td>　首页 &gt; 搜索“<%=searchstr%>”的结果 共有<%=listsize%>条</td>
                          <td align="right">
                          <a href="search.jsp?pages=1&content=<%=searchstr%>">&lt;&lt; 首页</a>
<%
if(pages>1){
%>
                  <a href="search.jsp?pages=<%=pages-1%>&content=<%=searchstr%>"> &lt; 上一页</a>
<%}
if((pages+1)<=totalpages){
%>
                  <a href="search.jsp?pages=<%=pages+1%>&content=<%=searchstr%>"> 下一页 &gt;</a>
<%}%>
                          <a href="search.jsp?pages=<%=totalpages%>&content=<%=searchstr%>"> 尾页 &gt;&gt;</a>
                          </td>
                        </tr>
                    </table></td>
                  </tr>
              </table></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td align="center" class="normal"><table width="90%"  border="0" cellspacing="2" cellpadding="0">
          <tr>
            <td colspan="2" class="list">&nbsp;</td>
            </tr>
<%
  String rootPath = searchFile.getProperties("rootPath");
  String siteurl = searchFile.getProperties("url");

  for(int i=0;i<list.size();i++)
  {
    Document doc = (Document)list.get(i);
    String url = doc.get("url");
    url = url.substring(rootPath.length(), url.length());
    url = siteurl + "/" + url;
    content = doc.get("desc");

    int posi = 0;
    if (content.length() > 200)
    {
      posi = content.indexOf("。");
      if (posi > 1) content = content.substring(0,posi);
      else content = content.substring(0,200);
    }

    String title = "";
    title = doc.get("title");
%>
          <tr>
            <td width="10" height="18" align="center"><img src="images/point3.gif" width="4" height="3"></td>
            <td height="18"><a href="<%=url%>" class="style1" target="_blank"><u><%=title%></u></a></td>
          </tr>
          <tr>
            <td width="10" height="18" align="center">&nbsp;</td>
            <td height="18"><%=content%><br></td>
          </tr>
<%}%>
          <tr>
            <td height="18" colspan="2">&nbsp;</td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td align="center" valign="top" bgcolor="B4B4B4"><table width="100%"  border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td bgcolor="#FFFFFF"><table width="100%"  border="0" cellspacing="4" cellpadding="0">
                <tr>
                  <td align="right" bgcolor="ECECEC">
                  <a href="search.jsp?pages=1&content=<%=searchstr%>">&lt;&lt; 首页</a>
<%
if(pages>1){
%>
                  <a href="search.jsp?pages=<%=pages-1%>&content=<%=searchstr%>"> &lt; 上一页</a>
<%}
if((pages+1)<=totalpages){
%>
                  <a href="search.jsp?pages=<%=pages+1%>&content=<%=searchstr%>"> 下一页 &gt;</a>
<%}%>

                  <a href="search.jsp?pages=<%=totalpages%>&content=<%=searchstr%>"> 尾页 &gt;&gt;</a>
                  </td>
                </tr>
              </table></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="8" valign="top"></td>
      </tr>
      <tr>
        <td height="8" valign="top"><table width="760" height="25" border="1" align="center" cellpadding="2" cellspacing="2" bordercolor="#009999">
          <tr>
            <td bgcolor="#99CCCC"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <form method="post" action="search.jsp" name="form2">
                  <td width="11%"><span class="style3">请输入关键字:</span></td>
                  <td width="18%"><input name="content" type="text" size="20" class="select"></td>
                  <td width="55%"><input type="submit" name="Submit2" value="搜索" class="select"></td>
                  <td width="16%">&nbsp;</td>
                  </form>
                </tr>
            </table></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td height="8" valign="top"></td>
      </tr>
    </table></td>
  </tr>

</table>
<!-- banner -->
<table width="760" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td height="20" background="images/line7.gif">&nbsp;</td>
  </tr>
  <tr>
    <td>&nbsp;</td>
</tr></table>

<%@ include file="tail.html" %>
</BODY>
</HTML>
