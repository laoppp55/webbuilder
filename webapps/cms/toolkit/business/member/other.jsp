<%@ page import="com.bizwink.cms.util.StringUtil" %>
<table width="100%" border="0" cellspacing="1" cellpadding="5">
<tr bgcolor="#FFFFFF">
<%
    forread = forread==null?"":new String(forread.getBytes("iso8859_1"),"GBK");
    String[] read = forread.split("&");
    boolean readflag = false;
    String other = "";
    if(StringUtil.iso2gb(read[read.length-1]).indexOf("[") != -1)
    {
        other = StringUtil.iso2gb(read[read.length-1]);
        other = other.substring(1, other.length()-1);
    }
    else
        other = "";
%>
<td bgcolor="#FFFFFF" width="80%" class="txt">阅读兴趣：<br>
<%
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("文学"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="文学" checked>
<%
} else {
%>
<input type="checkbox" name="read" value="文学">
<%
    }
%>
文学
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("历史"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="历史" checked>
<%
} else { %>
<input type="checkbox" name="read" value="历史">
<%
    }
%>
历史
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("哲学"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="哲学" checked>
<%
} else {
%>
<input type="checkbox" name="read" value="哲学">
<%
    }
%>

哲学
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("艺术"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="艺术" checked>
<%
} else {
%>
<input type="checkbox" name="read" value="艺术">
<%
    }
%>
艺术
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("语言"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="语言" checked>
<%  } else { %>
<input type="checkbox" name="read" value="语言">
<%
    }
%>
语言
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("文化"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="文化" checked>
<%  } else { %>
<input type="checkbox" name="read" value="文化">
<%
    }
%>
<br>     文化
<%
    readflag = false;
    for(int i=0;i<read.length;i++)
    {
        if(StringUtil.iso2gb(read[i]).equals("工具书"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="read" value="工具书" checked>
<%  } else { %>
<input type="checkbox" name="read" value="工具书">
<%
    }
%>
工具书<br>
其他：<br>
<input type="text" name="readother" size="30" value="<%=other%>">
<br>
<br>
收藏兴趣： <br>
<%
    forsave= forsave==null?"":new String(forsave.getBytes("iso8859_1"),"GBK");

    String[] save = forsave.split("&");

    if(StringUtil.iso2gb(save[save.length-1]).indexOf("[") != -1)
    {
        other = StringUtil.iso2gb(save[save.length-1]);
        other = other.substring(1, other.length()-1);
    }
    else
        other = "";
%>
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("古籍"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>
<input type="checkbox" name="save" value="古籍" checked>
<%  } else { %>
<input type="checkbox" name="save" value="古籍">
<%
    }
%>
古籍
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("签名本"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="签名本" checked>
<%  } else { %>
<input type="checkbox" name="save" value="签名本">
<%
    }
%>
签名本
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("新文学"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="新文学" checked>
<%  } else { %>
<input type="checkbox" name="save" value="新文学">
<%
    }
%>
新文学
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("革命文献"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="革命文献" checked>
<%  } else { %>
<input type="checkbox" name="save" value="革命文献">
<%
    }
%>
革命文献
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("信札"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="信札" checked>
<%  } else { %>
<input type="checkbox" name="save" value="信札">
<%
    }
%>
信札
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("手稿画稿"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="手稿画稿" checked>
<%  } else { %>
<input type="checkbox" name="save" value="手稿画稿">
<%
    }
%>
<br>     手稿画稿
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("画册"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="画册" checked>
<%  } else { %>
<input type="checkbox" name="save" value="画册">
<%
    }
%>
画册
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("连环画"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="连环画" checked>
<%  } else { %>
<input type="checkbox" name="save" value="连环画">
<%
    }
%>
连环画
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("创刊号"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="创刊号" checked>
<%  } else { %>
<input type="checkbox" name="save" value="创刊号">
<%
    }
%>
创刊号
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("地图"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="地图" checked>
<%  } else { %>
<input type="checkbox" name="save" value="地图">
<%
    }
%>
<br>    地图
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("印谱"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="印谱" checked>
<%  } else { %>
<input type="checkbox" name="save" value="印谱">
<%
    }
%>
印谱
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("报刊"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="报刊" checked>
<%  } else { %>
<input type="checkbox" name="save" value="报刊">
<%
    }
%>
报刊
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("民国书"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="民国书" checked>
<%  } else { %>
<input type="checkbox" name="save" value="民国书">
<%
    }
%>
民国书
<%
    readflag = false;
    for(int i=0;i<save.length;i++)
    {
        if(StringUtil.iso2gb(save[i]).equals("碑帖"))
        {
            readflag = true;
            break;
        }
    }
    if(readflag){
%>

<input type="checkbox" name="save" value="碑帖" checked>
<%  } else { %>
<input type="checkbox" name="save" value="碑帖">
<%
    }
%>
碑帖 <br>
其他：<br>
<input type="text" name="saveother" size="30" value="<%=other%>">
</td>
</tr>
</table>