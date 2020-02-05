<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.util.SessionUtil" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>无标题文档</title>
    <style type="text/css">
        body {
            margin-left: 0px;
            margin-top: 0px;
            margin-right: 0px;
            margin-bottom: 0px;
            font-size:14px;
        }
        a{ text-decoration:none; color:#000;}
        a:hover{ color:#03F;}

        .txtsel{ height:25px;}
        .txtinp{ height:21px;}
        .button_cx{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#db4b03; border:0px;}
        .button_blue{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#619cd9; border:0px;}
        .button_grey{ width:65px; height:25px; color:#FFF; cursor:pointer; background-color:#666666; border:0px;}
    </style>

</head>

<body>
<table width="96%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td align="center" valign="top"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td width="130"  height="60">检索：<select name="" class="txtsel">
                    <option>标题</option>
                    <option>查询码</option>
                    <option>信件编码</option>
                </select></td>
                <td width="253">
                    <input name="textfield" type="text" id="textfield" size="30"   class="txtinp"/></td>
                <td width="549" ><input type="submit" name="button" id="button" value="查询"  class="button_cx"/></td>

                <td width="68"><input type="submit" name="button" id="button" value="返回"  class="button_grey"/></td>
            </tr>
        </table>
            <table width="100%"  align="center" cellpadding="0" cellspacing="1" bgcolor="#999999">
                <tr>
                    <td width="81" height="50" align="center" bgcolor="#e0f0fe"><input type="submit" name="button2" id="button2" value="批量导出" class="button_blue" /></td>
                    <td colspan="6" align="right" bgcolor="#e0f0fe"><input type="submit" name="button3" id="button3" value="待处理" class="button_blue" />&nbsp;&nbsp;<input type="submit" name="button3" id="button3" value="已经处理" class="button_blue"  />&nbsp;&nbsp;<input type="submit" name="button3" id="button3" value="垃圾箱" class="button_blue" />&nbsp;&nbsp; </td>
                </tr>
                <tr>
                    <td width="81" align="center" bgcolor="#cae4ff"><input type="checkbox" name="checkbox2" id="checkbox2" />全选</td>
                    <td width="80" height="30" align="center" bgcolor="#cae4ff"><strong>信件编码</strong></td>
                    <td width="461" align="center" bgcolor="#cae4ff"><strong>标题</strong></td>
                    <td width="146" align="center" bgcolor="#cae4ff"><strong>提交时间</strong></td>
                    <td width="79" align="center" bgcolor="#cae4ff"><strong>受理状态</strong></td>
                    <td width="79" align="center" bgcolor="#cae4ff"><strong>浏览操作</strong></td>
                    <td width="64" align="center" bgcolor="#cae4ff"><strong>删除</strong></td>

                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="images/gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="images/gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>        <tr>
                <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                <td align="center" bgcolor="#e0f0fe">未受理</td>
                <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
            </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>
                <tr>
                    <td align="center" bgcolor="#e0f0fe"><input type="checkbox" name="checkbox" id="checkbox" /></td>
                    <td height="30" align="center" bgcolor="#e0f0fe">zm821</td>
                    <td align="center" bgcolor="#e0f0fe">关于石景山区鲁谷路重兴嘉园东区出口出入不畅问题</td>
                    <td align="center" bgcolor="#e0f0fe">2019-01-15 16:32.43</td>
                    <td align="center" bgcolor="#e0f0fe">未受理</td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_039.gif" width="25" height="25" /></a></td>
                    <td align="center" bgcolor="#e0f0fe"><a href="#"><img src="gif_45_037.gif" width="25" height="25" /></a></td>
                </tr>

                <tr>
                    <td height="50" colspan="7" align="center" bgcolor="#e0f0fe"><a href="#">首页</a> &nbsp;&nbsp;| &nbsp;&nbsp; <a href="#">上一页</a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="#">下一页</a> &nbsp;&nbsp;| &nbsp;&nbsp;<a href="#">尾页</a>  &nbsp;&nbsp; &nbsp;&nbsp;当前为第2页 &nbsp;&nbsp;共10页 &nbsp;&nbsp;共200条</td>
                </tr>
            </table></td>
    </tr>
</table>
</body>
</html>
