<%@ page import="com.bizwink.cms.security.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
        %>
<%
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    String src = ParamUtil.getParameter(request, "src");
    String width = ParamUtil.getParameter(request, "width");
    String height = ParamUtil.getParameter(request, "height");
    String vspace = ParamUtil.getParameter(request, "vspace");
    String hspace = ParamUtil.getParameter(request, "hspace");
    String border = ParamUtil.getParameter(request, "border");
    String align = ParamUtil.getParameter(request, "align");
    String alt = ParamUtil.getParameter(request, "alt");
    alt = alt == null?"":alt;
    if(alt.equals("null")) alt = "";
    border = border == null?"":border;
    if(border.equals("null")) border = "";
    vspace = vspace == null?"":vspace;
    if(vspace.equals("null")) vspace = "";
    if(vspace.equals("-1")) vspace = "";
    hspace = hspace == null?"":hspace;
    if(hspace.equals("null")) hspace = "";
    if(hspace.equals("-1")) hspace = "";
%>

<html>
<head>
<title>图片修改</title>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel=stylesheet type="text/css" href="/webbuilder/style/global.css">
<script language="javascript">
function updatepic()
{
    var ebwidth,ebheight,evspace,ehspace,eborder,ealign,ealt;
    ebwidth = document.getElementById("ebwidth").value;
    ebheight = document.getElementById("ebheight").value;
    evspace = document.getElementById("evspace").value;
    ehspace = document.getElementById("ehspace").value;
    eborder = document.getElementById("eborder").value;
    ealign = document.getElementById("ealign").value;
    ealt = document.getElementById("ealt").value;

    var e = window.parent.opener.top.FCKeditorAPI.GetInstance("content").Selection.GetSelectedElement();
    e.setAttribute("alt"   , ealt ) ;
	e.setAttribute("width" , ebwidth ) ;
	e.setAttribute("height", ebheight ) ;
	e.setAttribute("vspace", evspace ) ;
	e.setAttribute("hspace", ehspace ) ;
	e.setAttribute("border", eborder ) ;
	e.setAttribute("align" , ealign ) ;
    top.close();
}
</script>
</head>

<body>
<form name="form1" action="editimg.jsp" method="post">
    <table align="center" width="596">
        <tr>
            <TD>
                <table width="100%" border="1" cellspacing=1 cellpadding=1>
                    <tr height=20>
                        <td width="30%">图片布局</td>
                        <td width="70%">图片预览</td>
                    </tr>
                    <tr>
                        <td valign=top>
                            <table border=0 width="100%" cellspacing=2 cellpadding=2>
                                <TR height=30>
                                    <TD>替换文字：<input id="ealt" name="ealt" size="12" value="<%=alt%>"></TD>
                                </TR>
                                <tr height=30>
                                    <td>对齐方式：<select id="ealign" name="ealign" size="1" style="width:95">
                                        <option value="" selected>不设置</option>
                                        <option value="left" <%if(align.equals("left")){%>selected<%}%>>左</option>
                                        <option value="right" <%if(align.equals("right")){%>selected<%}%>>右</option>
                                        <option value="top" <%if(align.equals("top")){%>selected<%}%>>顶部</option>
                                        <option value="center" <%if(align.equals("center")){%>selected<%}%>>中</option>
                                        <option value="bottom" <%if(align.equals("bottom")){%>selected<%}%>>底部</option>
                                        <option value="absMiddle" <%if(align.equals("absMiddle")){%>selected<%}%>>绝对中间
                                        </option>
                                        <option value="absBottom" <%if(align.equals("absBottom")){%>selected<%}%>>绝对底部
                                        </option>
                                        <option value="textTop" <%if(align.equals("textTop")){%>selected<%}%>>文本顶部
                                        </option>
                                        <option value="basicline" <%if(align.equals("basicline")){%>selected<%}%>>基线
                                        </option>
                                    </select>
                                    </td>
                                </tr>
                                <tr height=30>
                                    <td>图像宽度：<input id="ebwidth" name="ebwidth" size="12"
                                                    value="<%=String.valueOf(width)%>"></td>
                                </tr>
                                <tr height=30>
                                    <td>图像高度：<input id="ebheight" name="ebheight" size="12"
                                                    value="<%=String.valueOf(height)%>"></td>
                                </tr>
                                <tr height=30>
                                    <td>边框宽度：<input id="eborder" name="eborder" size="12" value="<%=border%>"></td>
                                </tr>
                                <tr height=30>
                                    <td>水平间隔：<input id="ehspace" name="ehspace" size="12" value="<%=hspace%>"></td>
                                </tr>
                                <tr height=30>
                                    <td>垂直间隔：<input id="evspace" name="evspace" size="12" value="<%=vspace%>"></td>
                                </tr>
                                <tr>
                                    <td height=40 align=center><input type=button value="  修改  " onclick="updatepic();"
                                                                      class=tine></td>
                                </tr>
                            </table>
                        </td>
                        <td valign="top">
                            <img src="<%=src%>" border="0">
                        </td>
                    </tr>
                </table>
            </TD>
        </TR>
    </table>
</form>

</body>
</html>