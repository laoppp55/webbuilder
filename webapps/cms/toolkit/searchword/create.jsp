<%@ page import="com.bizwink.cms.security.Auth" %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="com.bizwink.cms.util.ParamUtil" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.ISearchWordManager" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.SearchWordPeer" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.SearchWord" %>
<%@ page import="com.bizwink.cms.toolkit.searchword.PinyinDemo" %>
<%@page contentType="text/html;charset=utf-8" %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
      if (authToken == null)
      {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
      }
    int startflag = ParamUtil.getIntParameter(request,"startflag",0);
    int hotflag = ParamUtil.getIntParameter(request,"hotflag",0);
    int tabooflag = ParamUtil.getIntParameter(request,"tabooflag",0);
    String cname = ParamUtil.getParameter(request,"cname");
    if(hotflag ==1)
        tabooflag =0;
    if(tabooflag == 1)
        hotflag =0;


    String searchstr = "关键词";
    if(hotflag == 1){
        searchstr = "热搜词";
    }


    if(startflag == 1){
        ISearchWordManager searchWordManager = SearchWordPeer.getInstance();
        SearchWord searchWord = searchWordManager.getSearchWordByCname(cname);

        if(searchWord.getId()>0 ){
            if(hotflag == 0 && tabooflag ==0){
                System.out.println("关键词已存在");
            }else {
                searchWord.setHotflag(hotflag);
                searchWord.setTabooflag(tabooflag);
                searchWordManager.updateSearchWord(searchWord, searchWord.getId());
            }
        }else{
            searchWord = new SearchWord();
            searchWord.setCname(cname);
            searchWord.setEname(PinyinDemo.ToPinyin(cname));
            searchWord.setSname(PinyinDemo.ToFirstChar(cname));
            searchWord.setHotflag(hotflag);
            searchWord.setTabooflag(tabooflag);
            searchWordManager.createSearchWord(searchWord);
        }
        response.sendRedirect("index.jsp");
    }

%>
<html>
<head>
<title>添加搜索关键词信息</title>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta http-equiv="Pragma" content="no-cache">
<style type="text/css">
TABLE {FONT-SIZE: 12px;word-break:break-all}
BODY {FONT-SIZE: 12px;margin-top: 0px;margin-bottom: 0px; line-height:20px;}
.TITLE {FONT-SIZE:16px; text-align:center; color:#FF0000; font-weight:bold; line-height:30px;}
.FONT01 {FONT-SIZE: 12px; color:#FFFFFF; line-height:20px;}
.FONT02 {FONT-SIZE: 12px; color:#D04407; font-weight:bold; line-height:20px;}
.FONT03 {FONT-SIZE: 14px; color:#000000; line-height:25px;}
A:link {text-decoration:none;line-height:20px;}
A:visited {text-decoration:none;line-height:20px;}
A:active {text-decoration:none;line-height:20px; font-weight:bold;}
A:hover {text-decoration:none;line-height:20px;}
.pad {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; line-height:20px;}
.form{border-bottom:#000000 1px solid; background-color:#FFFFFF; border-left:#000000 1px solid; border-right:#000000 1px solid; border-top:#000000 1px solid; font-size: 9pt; font-family:"宋体";}
.botton{border-bottom:#000000 1px solid; background-color:#F1F1F1; border-left:#FFFFFF 1px solid; border-right:#333333 1px solid; border-top:#FFFFFF 1px solid; font-size: 9pt; font-family:"宋体"; height:20px; color: #000000; padding-bottom: 1px; padding-left: 1px; padding-right: 1px; padding-top: 1px; border-style: ridge}
</style>
    <script language="javascript">
        function check(){
            if(document.form.cname.value == ""){
                alert("请输入内容！");
                return false;
            }

            document.form.action = "create.jsp";
            document.form.submit();
        }
  </script>
</head>
<body>
<form name="form" action="" method="post">
<input type="hidden" name="startflag" value="1">
<input type="hidden" name="hotflag" value="<%=hotflag%>">
<input type="hidden" name="tabooflag" value="<%=tabooflag%>">
<center>
<table width="90%" border="0" cellpadding="4" cellspacing="0" bgcolor="#FFFFFF" class="css_002">
<tr>
      <td align="center">
        <table width="70%" border="0" cellpadding="0">
          <tr bgcolor="#F4F4F4" align="center">
            <td height="30" valign="middle" bgcolor="#F4F4F4" class="css_003">增加关键词</td>
          </tr>
          <tr bgcolor="#d4d4d4" align="center">
            <td>
              <table width="100%" border="0" cellpadding="3" cellspacing="1" class="css_002">
                <tr  bgcolor="#FFFFFF" class="css_001">
                  <td width="50%" align="right"><%=searchstr%>：&nbsp;</td>
                    <td align="left" width="50%">&nbsp;<input type="text" name="cname"></td>
                </tr>

               </table>
            </td>
          </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td align="center">
                    <input type="button" name="ok" value=" 添 加 " onclick="check()">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="button" name="ok" value=" 返 回 " onclick=javascript:history.go(-1);>
                </td>
            </tr>
        </table>
      </td>
</tr>
</table>

</center>
    </form>
</body>
</html>