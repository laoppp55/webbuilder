<%@ page import="java.sql.*,
                 java.util.*,
                 java.util.regex.*,
                 com.xml.TreatmentXML,
                 com.bizwink.cms.modelManager.*,
                 com.bizwink.cms.security.*,
                 com.bizwink.cms.register.*,
                 com.bizwink.cms.util.*"
         contentType="text/html;charset=utf-8"
%>
<!--%@ taglib uri="/FCKeditor" prefix="FCK" %-->
<%
    request.setCharacterEncoding("utf-8");
    Auth authToken = SessionUtil.getUserAuthorization(request,response,session);
    if (authToken == null)
    {
        response.sendRedirect(response.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }

    boolean errors        = false;
    boolean success       = false;
    String sitename       = authToken.getSitename();
    int samsiteid         = authToken.getSamSiteid();
    boolean doUpdate     = ParamUtil.getBooleanParameter(request, "doUpdate");
    int shareType         = ParamUtil.getIntParameter(request, "shareType", 1);
    int     ID            = ParamUtil.getIntParameter(request, "template", 0);
    int     rightid       = ParamUtil.getIntParameter(request, "rightid", 0);
    int     modelType     = ParamUtil.getIntParameter(request,"modelType",0);
    int     columnID      = ParamUtil.getIntParameter(request, "column", 0);
    String  content       = ParamUtil.getParameter(request, "content");
    String  cname         = ParamUtil.getParameter(request, "cname");
    String  modelname     = ParamUtil.getParameter(request, "modelname");
    String  modelnameBak  = ParamUtil.getParameter(request, "modelnameBak");
    String  username      = authToken.getUserID();
    int siteid            = authToken.getSiteID();
    String baseDir = application.getRealPath("/");
    int tempnum=ParamUtil.getIntParameter(request,"tempnum",0);
    IRegisterManager regMgr = RegisterPeer.getInstance();
    IModelManager modelManager = ModelPeer.getInstance();
    Model model = modelManager.getModel(ID, username);

    if (!doUpdate)
    {
        modelType = model.getIsArticle();
        content = model.getContent();
        int posi = -1;
        while ((posi=content.toLowerCase().indexOf("<textarea")) > -1)
            content = content.substring(0,posi) + "<cmstextarea" + content.substring(posi+9);
        while ((posi=content.toLowerCase().indexOf("</textarea>")) > -1)
            content = content.substring(0,posi) + "</cmstextarea>" + content.substring(posi+11);
        content = StringUtil.gb2iso4View(content);
        cname = model.getChineseName();
        cname = StringUtil.gb2iso4View(cname);
        modelname = model.getTemplateName();
    }

    String columnName = "程序模板管理";

    if (doUpdate)
    {
        try
        {
            if (modelname == null || modelname.length() < 1)
            {
                out.println("模板文件名不能为空！请<a href=javascript:history.go(-1);>返回</a>");
                return;
            } else {
                if (modelManager.hasSameModelNameForProgram(siteid,columnID,ID,modelname))
                {
                    out.println("模板文件名称不能重复！请<a href=javascript:history.go(-1);>返回</a>");
                    return;
                }
            }

            int posi = -1;
            while ((posi=content.toLowerCase().indexOf("<cmstextarea")) > -1)
                content = content.substring(0,posi) + "<textarea" + content.substring(posi+12);
            while ((posi=content.toLowerCase().indexOf("</cmstextarea>")) > -1)
                content = content.substring(0,posi) + "</textarea>" + content.substring(posi+14);

            model.setID(ID);
            model.setIsArticle(modelType);
            model.setContent(content);
            model.setEditor(username);
            model.setLastupdated(new Timestamp(System.currentTimeMillis()));
            model.setLockstatus(0);
            model.setChineseName(cname);
            model.setTemplateName(modelname);
            model.setTempnum(tempnum);
            modelManager.Update(model,siteid,samsiteid,0);
            //有表单建设xml文件
            if(content.indexOf("</form>")!=-1)
            {
                //创建xml文件与表
                List startformlist=new ArrayList();
                List endformlist =new ArrayList();
                Pattern p = Pattern.compile("<\\s*form[^<>]*>", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(content);
                String srcstr = "";
                String newstrsrc = "";
                while (m.find()) {
                    String s = m.group();
                    //System.out.println("s="+s);
                    int start = m.start();
                    int end = m.end();
                    srcstr = content.substring(start, end);
                    //System.out.println("old="+srcstr+"   start="+start);
                    Integer startformtint=new Integer(start);
                    startformlist.add(startformtint);
                    /* Pattern p1=Pattern.compile("<\\s*input[^<>]*>");
                    Matcher m1=p1.matcher(srcstr);
                    while(m1.find())
                    {
                      int start1 = m1.start();
                      int end1 = m1.end();
                      newstrsrc=srcstr.substring(start1,end1);
                      System.out.println("new strsrc="+newstrsrc);
                    }
                    System.out.println("old="+srcstr);
                   System.out.println("===========================");  */
                }
                p = Pattern.compile("</form>", Pattern.CASE_INSENSITIVE);
                m = p.matcher(content);

                while (m.find()) {
                    int start = m.start();
                    int end = m.end();
                    srcstr = content.substring(start, end);
                    //System.out.println("old="+srcstr+"   end="+end);
                    Integer startformtint=new Integer(end);
                    endformlist.add(startformtint);
                    /* Pattern p1=Pattern.compile("<\\s*input[^<>]*>");
                    Matcher m1=p1.matcher(srcstr);
                    while(m1.find())
                    {
                      int start1 = m1.start();
                      int end1 = m1.end();
                      newstrsrc=srcstr.substring(start1,end1);
                      System.out.println("new strsrc="+newstrsrc);
                    }
                    System.out.println("old="+srcstr);
                    System.out.println("===========================");  */
                }

                if(startformlist.size()==endformlist.size())
                {
                    String forms[]=new String[startformlist.size()];
                    for(int i=0;i<forms.length;i++)
                    {
                        if((Integer)startformlist.get(i)<(Integer)endformlist.get(i)){
                            forms[i]=content.substring((Integer)startformlist.get(i),(Integer)endformlist.get(i));
                            //System.out.println("forms[i]="+forms[i]);
                        }
                    }
                    TreatmentXML txml=new TreatmentXML();

                    //先查找有没有xml文件 ，如果有xml文件就修改xml文件
                    //boolean flag=txml.readXMLFile(forms,authToken.getSiteID(),authToken.getSitename(),ID);
                    //if(flag){
                    txml.createXML(forms,authToken.getSiteID(),authToken.getSitename(),ID,baseDir);
                    // }
                }
                //System.out.println("listlength="+startformlist.size());


                //  txml.readXMLFile("E:\\works\\webbuilder5\\webapps\\cms\\sites\\test1_coosite_com\\_progInput.xml");
                //   txml.writeXMLFile("");
            }


            success = true;
        }
        catch (ModelException e)
        {
            e.printStackTrace();
            errors = true;
        }
    }

    if (success)
    {
        response.sendRedirect(response.encodeRedirectURL("closewin.jsp?id="+ID+"&column="+columnID+"&rightid="+rightid));
        return;
    }
%>

<html>
<head>
    <title>修改模板</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel=stylesheet type="text/css" href="../style/editor.css">
    <script type="text/javascript" src="../toolbars/btnclick.js"></script>
    <script type="text/javascript" src="../js/jquery-1.12.4.js"></script>
    <script type="text/javascript" src="../ckeditor/ckeditor.js"></script>

    <script language="javascript">
        $(document).ready(function() {
            checkoption();
        });

        function checkoption() {
            var selectlens = new Array;
            selectlens[0] = 36;
            var optionsname = new Array();
            var optionsvalue = new Array();

            optionsname[0] = new Array();
            optionsname[0][0] ="选择标记";
            optionsname[0][1] = "文章列表";
            optionsname[0][2] = "栏目列表";
            optionsname[0][3] = "中文路径";
            optionsname[0][4] = "英文路径";
            optionsname[0][5] = "热点文章";
            optionsname[0][6] = "推荐文章列表";
            optionsname[0][7] = "子文章列表";
            optionsname[0][8] = "兄弟文章列表";
            optionsname[0][9] = "子栏目列表";
            optionsname[0][10] = "文章条数";
            optionsname[0][11] = "子文章条数";
            optionsname[0][12] = "导航条";
            optionsname[0][13] = "栏目名称";
            optionsname[0][14] = "父栏目名称";
            optionsname[0][15] = "栏目URL";
            optionsname[0][16] ="检索表单";
            optionsname[0][17] ="登录表单";
            optionsname[0][18] ="检索结果页";
            optionsname[0][19] ="购物车";
            optionsname[0][20] ="订单生成";
            optionsname[0][21] ="订单回显";
            optionsname[0][22] ="订单查询";
            optionsname[0][23] ="信息反馈";
            optionsname[0][24] ="文章评论";
            optionsname[0][25] ="用户注册页";
            optionsname[0][26] ="用户登录页";
            optionsname[0][27] ="订单明细查询页";
            optionsname[0][28] ="用户留言表单";
            optionsname[0][29] ="用户留言列表";
            optionsname[0][30] ="修改注册";
            optionsname[0][31] ="地图标注";
            optionsname[0][32] = "图片特效";
            optionsname[0][33] = "自定义表单";
            optionsname[0][34] = "调查表单";
            optionsname[0][35] = "加入包含文件";
            optionsname[0][36] ="选择已有标记";

            optionsvalue[0] = new Array();
            optionsvalue[0][0] = "NO_SELECT";
            optionsvalue[0][1] = "ARTICLE_LIST";
            optionsvalue[0][2] = "COLUMN_LIST";
            optionsvalue[0][3] = "CHINESE_PATH";
            optionsvalue[0][4] = "ENGLISH_PATH";
            optionsvalue[0][5] = "TOP_STORIES";
            optionsvalue[0][6] = "COMMEND_ARTICLE";
            optionsvalue[0][7] = "SUBARTICLE_LIST";
            optionsvalue[0][8] = "BROTHER_LIST";
            optionsvalue[0][9] ="SUBCOLUMN_LIST";
            optionsvalue[0][10] ="ARTICLE_COUNT";
            optionsvalue[0][11] ="SUBARTICLE_COUNT";
            optionsvalue[0][12] ="NAVBAR";
            optionsvalue[0][13] ="COLUMNNAME";
            optionsvalue[0][14] ="PARENT_COLUMNNAME";
            optionsvalue[0][15] ="COLUMNURL";
            optionsvalue[0][16] ="SEARCH_FORM";
            optionsvalue[0][17] ="PROGRRAMLOGIN_FORM";
            optionsvalue[0][18] ="SEARCH_RESULT-11";
            optionsvalue[0][19] ="SHOPPINGCAR_RESULT-12";
            optionsvalue[0][20] ="ORDER_RESULT-13";
            optionsvalue[0][21] ="ORDER_REDISPLAY-14";
            optionsvalue[0][22] ="ORDERSEARCH_RESULT-15";
            optionsvalue[0][23] ="FEEDBACK-16";
            optionsvalue[0][24] ="ARTICLE_COMMENT-17";
            optionsvalue[0][25] ="REGISTER_RESULT-18";
            optionsvalue[0][26] ="USER_LOGIN_DISPLAY-19";
            optionsvalue[0][27] ="ORDERSEARCH_DETAIL-20";
            optionsvalue[0][28] ="LEAVEMESSAGE-21";
            optionsvalue[0][29] ="LEAVEMESSAGELIST-22";
            optionsvalue[0][30] ="UPDATEREG-23";
            optionsvalue[0][31] ="MAPMARK-24";
            optionsvalue[0][32] ="XUAN_IMAGES";
            optionsvalue[0][33] ="SELF_DEFINE_FORM";
            optionsvalue[0][34] = "DEFINEINFO_FORM";
            optionsvalue[0][35] ="INCLUDE_FILE";
            optionsvalue[0][36] ="USED_MARK";

            MarkName = document.getElementById("MarkName");
            MarkName.length = 0;
            param = 0;
            for (x=0; x<=selectlens[param]; x++)
            {
                oOption = document.createElement("OPTION");
                oOption.text = optionsname[param][x];
                oOption.value = optionsvalue[param][x];
                MarkName.add(oOption);
            }
        }

        function Form_Check(form) {
            var modeltype = form.modelType.value;

            if (modeltype < 10) {
                var ret = confirm("请选择程序页面");
                return false;
            }

            if (form.cname.value == "")
            {
                alert("请输入模板中文名称！");
                return false;
            }

            if (form.modelname.value == "")
            {
                alert("请输入模板文件名！");
                return false;
            }

            return true;
        }
    </script>
</head>

<body>
<form action="edittemplateforprogram.jsp" method="post" name=createForm onsubmit="return Form_Check(createForm)">
    <input type="hidden" name=doUpdate value=true>
    <input type="hidden" name=modelnameBak value="<%=modelname%>">
    <input type="hidden" name=template value="<%=ID%>">
    <input type="hidden" name=columnCode value="<%=columnID%>">
    <input type="hidden" name=column value="<%=columnID%>">
    <input type="hidden" name=modelType value=<%=modelType%>>
    <input type="hidden" name=modelSourceCodeFlag value=0>
    <input type=hidden name=template_or_article_flag value="0">
    <input type="hidden" name=editor value="<%=username%>">
    <input type="hidden" name="username" value="<%=username%>">
    <input type="hidden" name="tempURL" value="<%=request.getRequestURL().toString()+"-"+siteid%>">

    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr height=30>
            <td width="30%">当前栏目&nbsp;>&nbsp;<%=columnName%>
            </td>
            <td width="40%"><%
                if (!success && errors) {
                    out.println("<span class=cur>修改模板失败，请重新再试。</span>");
                }
            %></td>
            <td width="30%" align=right>
                <input class=tine type=button value="  编辑  " name="editbutton" onclick="editMarkInfo();">&nbsp;&nbsp;
                <input class=tine type=submit value="  保存  " name="savebutton">&nbsp;&nbsp;
                <input class=tine type=button value="  取消  " onclick="window.location='closewin.jsp?id=<%=ID%>&column=<%=columnID%>&rightid=<%=rightid%>';">&nbsp;&nbsp;
            </td>
        </tr>
        <tr bgcolor=#003366>
            <td colspan=3 height=2></td>
        </tr>
        <tr>
            <td colspan=3 height=4></td>
        </tr>
    </table>
    <table width="100%" border=0 cellspacing=0 cellpadding=0>
        <tr>
            <td colspan=2>
                &nbsp;&nbsp;中文名称：<input type=text name="cname" size=20 value="<%=(cname==null)?"":cname%>">
                &nbsp;&nbsp;文件名：<input type=text name="modelname" size=20 value="<%=modelname%>">
                &nbsp;&nbsp;<select ID="MarkName" onchange="return MarkName_Add(<%=columnID%>,'<%=sitename%>')"></select>
                &nbsp;模版号<input type=text name="tempnum" size="3" value="<%=model.getTempnum()%>"> </td>
        </tr>
        <tr style="height: 100%">
            <td id="selhtmlcode" valign="top"><input type="hidden" name="hcode" id="htmlcodeid" value=""></td>
        </tr>
    </table>

    <table border="0" width="100%">
        <tr>
            <td>
                <textarea id="content" name="content" style="WIDTH: 100%; HEIGHT: 530px"><%=content%></textarea>
                <script type="text/javascript">
                    CKEDITOR.replace('content');
                    //var oFCKeditor = new FCKeditor('content') ;
                    //oFCKeditor.BasePath = "../fckeditor/";
                    //oFCKeditor.Height = 530;
                    //oFCKeditor.ToolbarSet = "ProgramDefault";
                    //oFCKeditor.ReplaceTextarea();
                </script>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
