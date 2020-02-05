<%@ page import="java.net.*,
     com.bizwink.cms.util.*,
     com.bizwink.cms.security.*,
     com.bizwink.cms.markManager.*"
         contentType="text/html;charset=gbk"%>
<%@ page import="com.bizwink.cms.news.ColumnPeer" %>
<%@ page import="com.bizwink.cms.news.IColumnManager" %>
<%@ page import="com.bizwink.cms.news.Column" %>
<%
   /*Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null)
    {
        response.sendRedirect( "../login.jsp?url=member/removeGroup.jsp" );
        return;
    }

    String username = authToken.getUserID();
    int siteid = authToken.getSiteID();
    int samsiteid = authToken.getSamSiteid();
    String sitename = authToken.getSitename();
    String rootPath = application.getRealPath("/");
    int imgflag = authToken.getImgSaveFlag();
    */

    String username = ParamUtil.getParameter(request,"userid");
    int siteid = ParamUtil.getIntParameter(request,"siteid",0);
    String sitename = ParamUtil.getParameter(request,"sitename");
    String rootPath = ParamUtil.getParameter(request,"rp");
    int imgflag = ParamUtil.getIntParameter(request,"imgflag",0);
    int columnID = ParamUtil.getIntParameter(request,"column",0);
    int ID = ParamUtil.getIntParameter(request, "template", 0);
    int rightid = ParamUtil.getIntParameter(request, "right", 0);
    int modelType = ParamUtil.getIntParameter(request, "modeltype", 0);
    boolean doUpdate = ParamUtil.getBooleanParameter(request, "doUpdate");
    String name = ParamUtil.getParameter(request, "name");
    String cname = ParamUtil.getParameter(request, "chinesename");
    String content = ParamUtil.getParameter(request, "content1");

    IColumnManager columnManager = ColumnPeer.getInstance();
    Column column = columnManager.getColumn(columnID);
    String dirname = column.getDirName();

    int markid = ParamUtil.getIntParameter(request,"mark",0);
    String Markvalue = "";
    mark mymark = new mark();
    IMarkManager markMgr = markPeer.getInstance();
    if(markid>0){
        mymark = markMgr.getAMark(markid);
        Markvalue = mymark.getContent()==null?"":StringUtil.gb2iso4View(mymark.getContent());
        cname = mymark.getChineseName()==null?"":StringUtil.gb2iso4View(mymark.getChineseName());
    }else{
        //URLDecoder decoder = null;
        Markvalue = ParamUtil.getParameter(request, "selText");
        Markvalue = StringUtil.iso2gb(Markvalue);
        //Markvalue = decoder.decode(Markvalue);
    }

    if (doUpdate) {
        if(markid==0) {
            mymark.setChinesename(cname);
            mymark.setSiteID(siteid);
            mymark.setColumnID(columnID);
            mymark.setMarkType(6);
            mymark.setContent(content);
            markid = markMgr.Create(mymark);
        } else {
            mymark.setID(markid);
            mymark.setChinesename(cname);
            mymark.setSiteID(siteid);
            mymark.setColumnID(columnID);
            mymark.setMarkType(6);
            mymark.setContent(content);
            markMgr.Update(mymark);
        }

        out.println("<script type=\"text/javascript\">");
        out.println("var retstr = \"<INPUT name='[TAG][MARKID]"+markid+"[/MARKID][/TAG]' type=button value='[HTMLCODE]' style='BORDER-RIGHT: #808080 1px solid; BORDER-TOP: #808080 1px solid; FONT-SIZE: 12px; BORDER-LEFT: #808080 1px solid; BORDER-BOTTOM: #808080 1px solid; BACKGROUND-COLOR: #D6D3CE'>\";");
        out.println("window.opener.setHtmlcode(retstr)");
        out.println("window.close();");
        out.println("</script>");
        return;
    }
%>
<html>
<head>
    <META http-equiv=Pragma content=no-cache>
    <META http-equiv=Cache-Control content=no-cache>
    <script type="" language="javascript">
        function finish(){
            var wmp=document.getElementById("UserControl1");
            document.addMark.content1.value  = wmp.GetHtmlcode();
        }
    </script>
    <link rel=stylesheet type="text/css" href="../style/editor.css">
    <script type="text/javascript" src="../toolbars/newbtnclick.js"></script>
    <script type="text/javascript" src="../toolbars/dhtmled.js"></script>
    <script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
</head>
<body>
<form name=addMark method="post" action="editBizMark.jsp" onsubmit="finish()">
    <input type="hidden" name=doUpdate value=true>
    <input type="hidden" name="column" value="<%=columnID%>">
    <input type="hidden" name="template" value="<%=ID%>">
    <input type="hidden" name="userid" value="<%=username%>">
    <input type="hidden" name="siteid" value="<%=siteid%>">
    <input type="hidden" name="sitename" value="<%=sitename%>">
    <input type=hidden name=dirname value="<%=dirname%>">
    <input type=hidden name=rootpath value="<%=rootPath%>">
    <input type="hidden" name=imgflag value="<%=imgflag%>">
    <input type=hidden name=right value="<%=rightid%>">
    <input type=hidden name=modelType value="<%=modelType%>">

    <div id=div1 class="tbScriptlet">
        <textarea rows=20 cols=120 name=content1 wrap=virtual   size=40><%=Markvalue%></textarea>
    </div >
    <table  width="100%" border="0" align="center">
        <tr>
            <td>
                标记名称:<input type="text" name="name" value="<%=name%>" />
            </td>
            <td>
                中文名称:<input type="text" name="chinesename" value="<%=cname%>" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <object id="UserControl1" classid="clsid:42d2de86-975a-4ac1-bd46-faccd9117eda" Width="1000" Height="900"> </object>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="save" value="提交">
            </td>
            <td>
                <input type="button" name="cancel" value="关闭窗口" onclick="javascript:window.close()">
            </td>
        </tr>
    </table>
</form>
</body>
<script type="" language="javascript">
    document.addMark.content1.value = opener.document.createForm.pubcode.value;
    var wmp=document.getElementById("UserControl1");
    wmp.SetDocumentHTML(document.addMark.content1.value,document.addMark.column.value,document.addMark.template.value,document.addMark.modelType.value,document.addMark.right.value,document.addMark.userid.value,document.addMark.siteid.value,document.addMark.sitename.value,document.addMark.dirname.value,document.addMark.rootpath.value,document.addMark.imgflag.value);
</script>
</html>
