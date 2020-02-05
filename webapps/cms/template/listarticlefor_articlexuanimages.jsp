<%@ page import="com.bizwink.cms.news.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.markManager.*,
                 com.bizwink.cms.sitesetting.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    int siteID = authToken.getSiteID();
    ISiteInfoManager siteInfoMgr = SiteInfoPeer.getInstance();
    SiteInfo siteinfo = siteInfoMgr.getSiteInfo(siteID);

    String sitename = authToken.getSitename();
    String username = authToken.getUserID();
    int columnID = ParamUtil.getIntParameter(request, "column", 0);
    int markID = ParamUtil.getIntParameter(request, "mark", 0);
    int modeltype = ParamUtil.getIntParameter(request, "modeltype", 0);
    int start = ParamUtil.getIntParameter(request, "start", 0);
    int range = ParamUtil.getIntParameter(request, "range", 20);
    int h = ParamUtil.getIntParameter(request, "height", 0);
    int w = ParamUtil.getIntParameter(request, "width", 0);
    int ph = ParamUtil.getIntParameter(request, "picheight", 0);
    int pw = ParamUtil.getIntParameter(request, "picwidth", 0);
    int px = ParamUtil.getIntParameter(request,"px",0);
    String msg = ParamUtil.getParameter(request, "msg");
    String pictype = ParamUtil.getParameter(request, "picturetype");
    String disp_pictype = ParamUtil.getParameter(request, "disp_picturetype");
    String keyword = ParamUtil.getParameter(request, "keyword");
    int pic_texiao_type = ParamUtil.getIntParameter(request,"pictxtype",12);
    int scrolltitle = ParamUtil.getIntParameter(request,"scrolltitle",0);
    String doaction = ParamUtil.getParameter(request, "doaction");
    int total = 0;
    int articleCount = 0;
    String CName = null;
    Column column = null;
    String dirname = null;
    String content = null;
    int innerFlag = 0;

    String notes = "";
    String cname = "文章图片列表";
    String extname = "";
    StringBuffer sb = new StringBuffer();
    IMarkManager markMgr = markPeer.getInstance();

    if (doaction.equals("确定")){
        markID = ParamUtil.getIntParameter(request, "mark", 0);
        boolean saveas = ParamUtil.getBooleanParameter(request, "saveas");
        pic_texiao_type = ParamUtil.getIntParameter(request,"pictxtype",2);

        cname = ParamUtil.getParameter(request, "chineseName");
        notes = ParamUtil.getParameter(request, "notes");
        innerFlag = ParamUtil.getIntParameter(request, "innerFlag", 0);
        int listType = ParamUtil.getIntParameter(request, "listType", 0);
        int orgmarkID = markID;

        String viewer = request.getHeader("user-agent");
        String markname = "";
        markname = "文章图片特效";

        if ( pic_texiao_type >=0 && pic_texiao_type<=25) {
            markname = "文章图片特效";

            content = "[TAG][TURN_PIC][MARKTYPE]10[/MARKTYPE][TEXIAOTYPE]"+pic_texiao_type +"[/TEXIAOTYPE][PICTYPE]" + pictype + "[/PICTYPE][DISPPICTYPE]" + disp_pictype + "[/DISPPICTYPE][PH]" + ph + "[/PH][PW]" + pw + "[/PW][H]" + h +"[/H][W]" + w + "[/W][PX]" + px + "[/PX][STYLES][/STYLES][CONTENT][/CONTENT][/TURN_PIC][/TAG]";
            mark mark = new mark();
            mark.setID(markID);
            mark.setColumnID(columnID);
            mark.setSiteID(siteID);
            mark.setContent(content);
            mark.setChinesename(cname);
            mark.setNotes(notes);
            mark.setInnerHTMLFlag(innerFlag);
            mark.setFormatFileNum(listType);
            mark.setMarkType(10);
            if (orgmarkID > 0 && !saveas)
                markMgr.Update(mark);
            else
                markID = markMgr.Create(mark);
        }

        if (orgmarkID > 0 && !saveas) {
            out.println("<script>top.close();</script>");
        } else {
            String returnvalue = "[TAG][MARKID]" + markID + "_" + columnID + "[/MARKID][/TAG]";
            out.println("<script>var returnvalue = \"<INPUT name='" + returnvalue + "' type=button value='[" + markname + "]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";" +
                    "window.parent.opener.InsertHTML(returnvalue);top.close();</script>");
        }
        return;
    }

    IColumnManager columnManager = ColumnPeer.getInstance();
    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        if (column != null) {
            dirname = column.getDirName();
            extname = column.getExtname();
        }
    } else {
        dirname = "/_prog/";
    }

    if (columnID > 0) {
        column = columnManager.getColumn(columnID);
        if (column != null)
            CName = column.getCName();
        else
            CName = "首页";
    } else {
        CName = "程序模板";
    }
%>

<html>
<head>
    <title>文章附属图片列表</title>
    <meta http-equiv=Content-Type content="text/html; charset=utf-8">
    <link rel=stylesheet type=text/css href="../style/global.css">
</head>
<script language="JavaScript">
    function onclickthehref(form,start) {
        var obj;
        obj=document.getElementsByName("pictxtype");
        if(obj!=null){
            var i,ptxvalue;
            for(i=0;i<obj.length;i++){
                if(obj[i].checked){
                    ptxvalue = obj[i].value;
                    break;
                }
            }
        }

        var imgW = document.selarticleform.picwidth.value;
        var W = document.selarticleform.width.value;
        if(ptxvalue<=1){
            if(W>imgW*2){
                alert('滚动区宽度不能大于图片宽度的2倍');
                return false;
            }
        }
        form.method="post";
        form.action="listarticlefor_articlexuanimages.jsp?start=" + start + "&pictxtype=" + ptxvalue;
        form.submit();
        //return true;
    }

    function closewin() {
        //top.opener.document.createForm.MarkName.value = "";
        top.window.close();
    }
</script>

<body>
<table cellpadding="1" cellspacing="1" border="0">
    <form name="selarticleform" onSubmit="javascript:return onclickthehref(selarticleform,<%=start%>)">
        <input type=hidden name=doSearch value=true>
        <input type=hidden name=saveas value=false>
        <input type=hidden name=mark value="<%=markID%>">
        <input type=hidden name=column value=<%=columnID%>>
        <input type=hidden name=start value=<%=start%>>
        <input type=hidden name=range value=<%=range%>>
        <input type=hidden name=modeltype value=<%=modeltype%>>
        <tr>
            <td>特效显示类型：</td>
            <td>
                <select name="pictxtype">
                    <option value="1">Flash幻灯效果</option>
                    <option value="2">矩形缩小</option>
                    <option value="3">矩形扩大</option>
                    <option value="4">园型缩小</option>
                    <option value="5">圆形扩大</option>
                    <option value="6">竖向上滚动</option>
                    <option value="7">竖向下滚动</option>
                    <option value="8">横向左滚动</option>
                    <option value="9">横向右滚动</option>
                    <option value="10">百叶窗右滚动</option>
                    <option value="11">百叶窗下滚动</option>
                    <option value="12">栅格右滚动</option>
                    <option value="13">栅格下滚动</option>
                    <option value="14">点扩散渐变效果</option>
                    <option value="15">左右关门效果</option>
                    <option value="16">左右开门效果</option>
                    <option value="17">上下开门效果</option>
                    <option value="18">上下关门效果</option>
                    <option value="19">右上向左下切换</option>
                    <option value="20">右下向左上切换</option>
                    <option value="21">左上向右下切换</option>
                    <option value="22">左下向右上切换</option>
                    <option value="23">竖条渐变</option>
                    <option value="24">横条渐变</option>
                    <option value="25">随机变换效果</option>
                    <option value="26">图片幻灯渐变</option>
                </select>
            </td>
        </tr>
        <tr >
            <td>是否显示图片说明：</td>
            <td valign="top">
                <input type=checkbox name="scrolltitle" value="1" <%=(scrolltitle==0)?"":"checked"%>>是否显示图片说明
            </td>
        </tr>
        <tr>
            <td>图片菜单区域：</td>
            <td>
                菜单区高度：<input type=text name="height" <%=(h!=0)?"value="+h:"value=\"400\""%> size="5">px
                菜单区宽度：<input type=text name="width"  <%=(w!=0)?"value="+w:"value=\"500\""%> size="5">px&nbsp;&nbsp;&nbsp;
                图片高度： <input type=text name="picheight"  <%=(ph!=0)?"value="+ph:"value=\"\""%> size="5">px
                图片宽度： <input type=text name="picwidth"  <%=(pw!=0)?"value="+pw:"value=\"\""%> size="5">px&nbsp;
            </td>
        </tr>
        <tr>
            <td>菜单图片说明：</td>
            <td>
                菜单图片大小：<select name="picturetype" style="width: 150px">
                <option value="ts" selected><%=siteinfo.getTs_pic()%></option>
                <option value="s"><%=siteinfo.getS_pic()%></option>
                <option value="ms"><%=siteinfo.getMs_pic()%></option>
                <option value="m"><%=siteinfo.getM_pic()%></option>
                <option value="ml"><%=siteinfo.getMl_pic()%></option>
                <option value="l"><%=siteinfo.getL_pic()%></option>
                <option value="tl"><%=siteinfo.getTl_pic()%></option>
            </select>&nbsp;
                菜单图片间距：<input type=test name="px"  <%=(px!=0)?"value="+px:"value=\"10\""%> size="10">px&nbsp;&nbsp;
            </td>
        </tr>
        <tr>
            <td>内容图片说明：</td>
            <td>
                内容图片大小：<select name="disp_picturetype" style="width: 150px">
                <option value="tl" selected><%=siteinfo.getTl_pic()%></option>
                <option value="l"><%=siteinfo.getL_pic()%></option>
                <option value="ml"><%=siteinfo.getMl_pic()%></option>
                <option value="m"><%=siteinfo.getM_pic()%></option>
                <option value="ms"><%=siteinfo.getMs_pic()%></option>
                <option value="s"><%=siteinfo.getS_pic()%></option>
                <option value="ts"><%=siteinfo.getTs_pic()%></option>
            </select>
            </td>
        </tr>
        <tr>
            <td>标记是否要生成包含文件：</td>
            <td>

                <input type=radio name=innerFlag value=0 <%if(innerFlag==0){%>checked<%}%>>否
                <input type=radio name=innerFlag value=1 <%if(innerFlag==1){%>checked<%}%>>是
            </td>
        </tr>
        <tr>
            <td>标记中文名称：</td>
            <td><input name=chineseName size=20 value="<%=cname%>" class=tine></td>
        </tr>
        <tr>
            <td>标记描述：</td>
            <td><br><textarea rows="3" name="notes" id="notesid" cols="38" class=tine><%=notes%>
            </textarea></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td>
                <input type="submit"  name="doaction" value="确定">&nbsp;&nbsp;
                <input type="button"  value="取消" onClick="javascript:closewin();">
            </td></tr>
    </form>
</table>
</body>
</html>