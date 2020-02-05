<%@page import="com.bizwink.cms.security.Auth,
                com.bizwink.cms.util.ParamUtil,
                com.bizwink.cms.util.SessionUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%@ page import="com.bizwink.cms.kings.jspsmart.upload.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--<%@ taglib uri="/FCKeditor" prefix="FCK" %>--%>
<script language="JavaScript" src="../images/setday.js"></script>
<%
    /*Auth authToken = SessionUtil.getUserAuthorization(request, response, session);
    if (authToken == null) {
        response.sendRedirect(response.encodeRedirectURL("../../login.jsp?msg=系统超时，请重新登陆!"));
        return;
    }*/

    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    BasisEntity basisentity = new BasisEntity();
    CatgEntity catg = new CatgEntity();
    List catglist = new ArrayList();
    List deptlist = new ArrayList();
    List list = new ArrayList();
    catglist = wsbsMgr.getAllCatgEntity(0,1);
    deptlist = wsbsMgr.getAllCatgEntity(2,3);
    //int siteid = authToken.getSiteID();
    int startflag = 0;
    String name = "",serviceobject = "",basis = "",chargestandard = "",timelimited = "",timelimit = "",orgnization = "",workaddress = "",
           ridingroute = "",relatephone = "",memo = "",workprocedure = "",	link_zxbl = "",	link_jgfk = "",	link_zxzx = "",	standby = "",
            coun = "",stuff = "",item_condition = "",item_times = "",code = "",classified = "",main = "",	jurisdiction = "",answer = "",
            supervision_telephone = "",	job_description = "";
    int catgid = 0;
    int depid = 0;
    String [] gistname = {};
    int category = 0,category33 = 0,category38 = 0,category43 = 0,category48 = 0;
    String content = "",content33 = "",content38 = "",content43 = "",content48 = "";
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    String rq = sf.format(System.currentTimeMillis());
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    String baseDir = request.getRealPath("/");
    String dir = wsbsMgr.getConfig() + java.io.File.separator + rq + java.io.File.separator;
    //String imgurl = wsbsMgr.getConfig();    
    String imgurl = wsbsMgr.getConfig().substring(wsbsMgr.getConfig().indexOf("/upload"),wsbsMgr.getConfig().length())+"/"+rq+"/";
    boolean error = false;
    String filename = "";
    String tupian = "";
    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(),request,response);
        com.jspsmart.upload.File  tmpFile = null;
        try {
            mySmartUpload.upload();
            Files uploadFiles = mySmartUpload.getFiles();
            startflag = Integer.parseInt(mySmartUpload.getRequest().getParameter("startflag"));
            name = mySmartUpload.getRequest().getParameter("name");
            catgid = Integer.parseInt(mySmartUpload.getRequest().getParameter("catgid"));
            depid = Integer.parseInt(mySmartUpload.getRequest().getParameter("depid"));
            serviceobject = mySmartUpload.getRequest().getParameter("serviceobject");
            basis = mySmartUpload.getRequest().getParameter("basis");
            chargestandard = mySmartUpload.getRequest().getParameter("chargestandard");
            timelimited = mySmartUpload.getRequest().getParameter("timelimited");
            timelimit = mySmartUpload.getRequest().getParameter("timelimit");
            orgnization = mySmartUpload.getRequest().getParameter("orgnization");
            workaddress = mySmartUpload.getRequest().getParameter("workaddress");
            ridingroute = mySmartUpload.getRequest().getParameter("ridingroute");
            relatephone = mySmartUpload.getRequest().getParameter("relatephone");
            memo = mySmartUpload.getRequest().getParameter("memo");
            workprocedure = mySmartUpload.getRequest().getParameter("workprocedure");
            link_zxbl = mySmartUpload.getRequest().getParameter("link_zxbl");
            link_jgfk = mySmartUpload.getRequest().getParameter("link_jgfk");
            link_zxzx = mySmartUpload.getRequest().getParameter("link_zxzx");
            standby = mySmartUpload.getRequest().getParameter("standby");
            coun = mySmartUpload.getRequest().getParameter("coun");
            stuff = mySmartUpload.getRequest().getParameter("stuff");
            item_condition = mySmartUpload.getRequest().getParameter("item_condition");
            item_times = mySmartUpload.getRequest().getParameter("item_times");
            code = mySmartUpload.getRequest().getParameter("code");
            classified = mySmartUpload.getRequest().getParameter("classified");
            main = mySmartUpload.getRequest().getParameter("main");
            jurisdiction = mySmartUpload.getRequest().getParameter("jurisdiction");
            answer = mySmartUpload.getRequest().getParameter("answer");
            supervision_telephone = mySmartUpload.getRequest().getParameter("supervision_telephone");
            job_description = mySmartUpload.getRequest().getParameter("job_description");

            gistname = mySmartUpload.getRequest().getParameterValues("gistname");
            category = Integer.parseInt(mySmartUpload.getRequest().getParameter("category"));
            if(mySmartUpload.getRequest().getParameter("category33") != null){
                category33 = Integer.parseInt(mySmartUpload.getRequest().getParameter("category33"));
            }
            if(mySmartUpload.getRequest().getParameter("category38") != null){
                category38 = Integer.parseInt(mySmartUpload.getRequest().getParameter("category38"));
            }
            if(mySmartUpload.getRequest().getParameter("category43") != null){
                category43 = Integer.parseInt(mySmartUpload.getRequest().getParameter("category43"));
            }
            if(mySmartUpload.getRequest().getParameter("category48") != null){
                category48 = Integer.parseInt(mySmartUpload.getRequest().getParameter("category48"));
            }             
            content = mySmartUpload.getRequest().getParameter("content");
            content33 = mySmartUpload.getRequest().getParameter("content33");
            content38 = mySmartUpload.getRequest().getParameter("content38");
            content43 = mySmartUpload.getRequest().getParameter("content43");
            content48 = mySmartUpload.getRequest().getParameter("content48");
            for (int i = 0; i < mySmartUpload.getFiles().getCount(); i++) {
                File tempFile = mySmartUpload.getFiles().getFile(i);   
                if (!tempFile.isMissing()) {
                    filename = tempFile.getFileName();
                    String ext = tempFile.getFileExt();

                    String newfilename = String.valueOf(System.currentTimeMillis()) + "_" + i + "." + ext;
                    tupian += "-----" + newfilename;

                    java.io.File dirFile = new java.io.File( dir );
                    if ( !dirFile.exists()) {
                        dirFile.mkdirs();
                    }

                    tempFile.saveAs(dir + newfilename);

                    //mySmartUpload.save(uploadPath);
                }else{
                    tupian +="-----1111111";
                }
            }            
            mySmartUpload.stop();            
            String tp[] = tupian.split("-----");
            for(int k=1;k<tp.length;k++){
                //System.out.println("tp[k] = " + tp[k].indexOf("1111111")>-1);
                if(tp[k].indexOf("1111111")==-1){
                    if(k==1){
                        if(category == 1){
                            content = imgurl+tp[k];
                        }
                    }
                    if(k==2){
                        if(category33 == 1){
                            content33 = imgurl+tp[k];
                        }
                    }
                    if(k==3){
                        if(category38 == 1){
                            content38 = imgurl+tp[k];
                        }
                    }
                    if(k==4){
                        if(category43 == 1){
                            content43 = imgurl+tp[k];
                        }
                    }
                    if(k==5){
                        if(category48 == 1){
                            content48 = imgurl+tp[k];
                        }
                    }
                }
            }

        }catch ( Exception e ) {
            //System.out.println(e.getMessage());
            error = true;
        } finally {
            mySmartUpload.stop();
        }
    }
    //System.out.println("tupian = " + tupian);
    if (startflag == 1) {
        WsbsEntity wsbs = new WsbsEntity();
        wsbs.setCatgid(catgid);
        wsbs.setDepid(depid);        
        wsbs.setName(name);
        wsbs.setServiceobject(serviceobject);
        wsbs.setBasis(basis);
        wsbs.setChargestandard(chargestandard);
        wsbs.setTimelimited(timelimited);
        wsbs.setTimelimit(timelimit);
        wsbs.setOrgnization(orgnization);
        wsbs.setWorkaddress(workaddress);
        wsbs.setRidingroute(ridingroute);
        wsbs.setRelatephone(relatephone);
        wsbs.setMemo(memo);
        wsbs.setWorkprocedure(workprocedure);
        wsbs.setLink_zxbl(link_zxbl);
        wsbs.setLink_jgfk(link_jgfk);
        wsbs.setLink_zxzx(link_zxzx);
        wsbs.setStandby(standby);
        wsbs.setCoun(coun);
        wsbs.setStuff(stuff);
        wsbs.setItem_condition(item_condition);
        wsbs.setItem_times(item_times);
        wsbs.setCode(code);
        wsbs.setClassified(classified);
        wsbs.setMain(main);
        wsbs.setJurisdiction(jurisdiction);
        wsbs.setAnswer(answer);
        wsbs.setSupervision_telephone(supervision_telephone);
        wsbs.setJob_description(job_description);

        for(int i = 0; i < gistname.length; i++){
            //String tp[] = tupian.split("-----");
            basisentity = new BasisEntity();
            basisentity.setName(gistname[i]);
            //处理循环的fckeditor
            if(i==0){
                basisentity.setCategory(category);
                basisentity.setContent(content);
            }else if(i==1){
                basisentity.setCategory(category33);
                basisentity.setContent(content33);
            }else if(i==2){
                basisentity.setCategory(category38);
                basisentity.setContent(content38);                
            }else if(i==3){
                basisentity.setCategory(category43);
                basisentity.setContent(content43);
            }else {
                basisentity.setCategory(category48);
                basisentity.setContent(content48);
            }
            list.add(basisentity);
        }

        wsbsMgr.insertWsbs(wsbs,list);
        response.sendRedirect("index.jsp");
    }
%>
<HTML>
<HEAD><TITLE>网上办事信息录入</TITLE>
    <META http-equiv=Content-Type content="text/html; charset=gb2312">
    <LINK href="images/common.css" type=text/css rel=stylesheet>
    <LINK href="images/forum.css" type=text/css rel=stylesheet>
    <script type="text/javascript" src="../fckeditor/fckeditor.js"></script>
    <script src="../../js/jquery-1.6.1.min.js"></script>
    <SCRIPT language=javascript>
        function check()
        {
            if (createForm.name.value == "")
            {
                alert("请输入事项名称！");
                return false;
            }
            return true;
        }

        function goto()
        {
            createForm.action = "index.jsp";
            createForm.submit();
        }

        function catecheck(type){
             
            if(type == 0){
                document.getElementById("category0").style.display = "";
                document.getElementById("category1").style.display = "none";
            }
            if(type == 1){
                document.getElementById("category0").style.display = "none"; 
                document.getElementById("category1").style.display = "";
            }
            var flag = type -33;

            if(flag%5 == 0){
                var s = type+1;
                document.getElementById("categorys"+type).style.display = "";
                document.getElementById("categorys"+s).style.display = "none";
            }
            if(flag%5 == 1){
                var g = type-1;
                document.getElementById("categorys"+g).style.display = "none";
                document.getElementById("categorys"+type).style.display = "";

            }
        }

        var row_count = 33;
        function addNew()
        {
            if(row_count > 48){
                alert("最多可添加5个！");
                return true;
            }
            //<input type='checkbox' name='count' value='New'>&nbsp;&nbsp;&nbsp;
            var flag = row_count+1;
            $("#nowamagic tr:eq("+row_count+")").after("<TR height=32><TD align=right width=30% height=32>说明名称：</TD>" +
            "<TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=gistname> </TD></TR><TR height=32><TD align=right width=30% height=32>选择：</TD>"
            +"<TD align=left width=70% height=32>&nbsp;<INPUT type=\"radio\" checked name=category"+row_count+" value=\"0\" onclick=\"javascript:catecheck("+row_count+");\">法规依据"
            +"<INPUT type=\"radio\" name=category"+row_count+" value=\"1\" onclick=\"javascript:catecheck("+flag+");\">相关附件</TD></TR><TR height=32>"
            +"<TD align=right width=30% height=32>说明内容：</TD><TD align=left width=70% height=32><div id=\"categorys"+row_count+"\"><TABLE width=\"100%\">"
            +"<TR height=32><TD align=left width=100% height=32>&nbsp;<textarea id=\"content"+row_count+"\" name=\"content"+row_count+"\"></textarea>"
            +"<script type=\"text/javascript\">var oFCKeditor = new FCKeditor('content"+row_count+"') ;oFCKeditor.BasePath = \"../fckeditor/\";"
            +"oFCKeditor.Width=\"95%\";oFCKeditor.Height = 300;oFCKeditor.ToolbarSet = \"Sjswsbs\";oFCKeditor.ReplaceTextarea();"
            +"<\/script></TD></TR></TABLE></div><div id=\"categorys"+flag+"\" style=\"display:none\"><TABLE width=\"100%\"><TR height=32>"
            +"<TD align=left width=100% height=32>&nbsp;<INPUT type=\"file\" name=content> </TD></TR> </TABLE></div></TD></TR>");
            alert("<TR height=32><TD align=right width=30% height=32>说明名称：</TD>" +
            "<TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=gistname> </TD></TR><TR height=32><TD align=right width=30% height=32>选择：</TD>"
            +"<TD align=left width=70% height=32>&nbsp;<INPUT type=\"radio\" checked name=category"+row_count+" value=\"0\" onclick=\"javascript:catecheck("+row_count+");\">法规依据"
            +"<INPUT type=\"radio\" name=category"+row_count+" value=\"1\" onclick=\"javascript:catecheck("+flag+");\">相关附件</TD></TR><TR height=32>"
            +"<TD align=right width=30% height=32>说明内容：</TD><TD align=left width=70% height=32><div id=\"categorys"+row_count+"\"><TABLE width=\"100%\">"
            +"<TR height=32><TD align=left width=100% height=32>&nbsp;<textarea id=\"content"+row_count+"\" name=\"content"+row_count+"\"></textarea>"
            +"<script type=\"text/javascript\">var oFCKeditor = new FCKeditor('content"+row_count+"') ;oFCKeditor.BasePath = \"../fckeditor/\";"
            +"oFCKeditor.Width=\"95%\";oFCKeditor.Height = 300;oFCKeditor.ToolbarSet = \"Sjswsbs\";oFCKeditor.ReplaceTextarea();"
            +"<\/script></TD></TR></TABLE></div><div id=\"categorys"+flag+"\" style=\"display:none\"><TABLE width=\"100%\"><TR height=32>"
            +"<TD align=left width=100% height=32>&nbsp;<INPUT type=\"file\" name=content> </TD></TR> </TABLE></div></TD></TR>");
            row_count+=5;
        }
    </SCRIPT>

    <META content="MSHTML 6.00.2800.1479" name=GENERATOR>
</HEAD>
<BODY bgColor=#ffffff>
<FORM name=createForm action=add.jsp?doCreate=true method=post enctype="multipart/form-data">
    <INPUT type=hidden value=1 name=startflag>
    <input type="hidden" name="manageflag" value="1" >
    <CENTER>
        <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width=80%
               borderColorLight=#008000 border=2 id="nowamagic">

            <TR>
                <TD bgColor=#33ccff colSpan=2 height=32>
                    <P align=center>添加网上办事信息</P></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>事项名称：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=name> <FONT color=red>*</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>所属分类：</TD>
                <TD align=left width=70% height=32>&nbsp;
                <select name="catgid">
                    <option value="-1">请选择</option>
                    <%
                        if(catglist != null){
                            for(int i = 0; i < catglist.size(); i++){
                                catg = (CatgEntity)catglist.get(i);
                    %>
                    <option value="<%=catg.getId()%>"><%=catg.getName()%></option>
                    <%}}%>
                </select>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>所属委办局：</TD>
                <TD align=left width=70% height=32>&nbsp;
                <select name="depid">
                    <option value="-1">请选择</option>
                    <%
                        if(deptlist != null){
                            for(int i = 0; i < deptlist.size(); i++){
                                catg = (CatgEntity)deptlist.get(i);
                    %>
                    <option value="<%=catg.getId()%>"><%=catg.getName()%></option>
                    <%}}%>
                </select>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>该项服务的对象：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=serviceobject> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理依据：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=basis> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>收费依据和标准：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=chargestandard> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>法定期限：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=timelimited> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>承诺期限：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=timelimit> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理部门：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=orgnization> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理地点：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=workaddress> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>乘车路线：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=ridingroute> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>联系电话：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=relatephone> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>备注：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=memo> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理程序：</TD>
                <TD align=left width=70% height=32>&nbsp;
                <textarea id="workprocedure" name="workprocedure"></textarea>
                <script type="text/javascript">
                    var oFCKeditor = new FCKeditor('workprocedure') ;
                    oFCKeditor.BasePath = "../fckeditor/";
                    oFCKeditor.Width='95%';
                    oFCKeditor.Height = 300;
                    oFCKeditor.ToolbarSet = "Sjswsbs";                 
                    oFCKeditor.ReplaceTextarea();
                </script>
                </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>在线办理链接：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=link_zxbl> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>结果反馈链接：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=link_jgfk> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>在线咨询链接：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=link_zxzx> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>录入日期：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=standby> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理量：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=coun> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>所需材料：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=stuff> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>受理条件：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=item_condition> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理时间：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=item_times> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>事项编码：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=code> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>事项分类：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=classified> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理主体：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=main> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>办理权限：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=jurisdiction> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>常见问题解答：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=answer> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>监督电话：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=supervision_telephone> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>岗位说明：</TD>
                <TD align=left width=70% height=32>&nbsp;
                <textarea id="job_description" name="job_description"></textarea>
                <script type="text/javascript">
                    var oFCKeditor = new FCKeditor('job_description') ;
                    oFCKeditor.BasePath = "../fckeditor/";
                    oFCKeditor.Width='95%';
                    oFCKeditor.Height = 300;
                    oFCKeditor.ToolbarSet = "Sjswsbs";
                    oFCKeditor.ReplaceTextarea();
                </script>
                </TD>
            </TR>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;办理依据说明：</FONT></TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>说明名称：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT size=50 name=gistname> </TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>选择：</TD>
                <TD align=left width=70% height=32>&nbsp;<INPUT type="radio" checked name=category value="0" onclick="javascript:catecheck(0);">法规依据
                    <INPUT type="radio" name=category value="1" onclick="javascript:catecheck(1);">相关附件</TD>
            </TR>
            <TR height=32>
                <TD align=right width=30% height=32>说明内容：</TD>
                <TD align=left width=70% height=32>
            <div id="category0">
            <TABLE width="100%">
            <TR height=32>
                <TD align=left width=100% height=32>&nbsp;
                <textarea id="content" name="content"></textarea>
                <script type="text/javascript">
                    var oFCKeditor = new FCKeditor('content') ;
                    oFCKeditor.BasePath = "../fckeditor/";
                    oFCKeditor.Width='95%';
                    oFCKeditor.Height = 300;
                    oFCKeditor.ToolbarSet = "Sjswsbs";
                    oFCKeditor.ReplaceTextarea();
                </script>
                </TD>
            </TR></TABLE></div>
            <div id="category1" style="display:none">
            <TABLE width="100%">
            <TR height=32>
                <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
            </TR> </TABLE>
            </div>
             </TD></TR>
            <TR height=32>
                <TD colSpan=2><div align="right"><input type="button" value="增加一个" onclick="addNew();"></div></TD>
            </TR>
            <%--<TR height=32>
                <TD align=right>最近进货日期：</TD>
                <TD align=left>&nbsp;<INPUT size=16 name=lastpurchasedate onfocus="setday(this)"></TD>
            </TR>--%>
            <TR height=32>
                <TD colSpan=2><FONT
                        color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：带有*的项为必填项</FONT></TD>
            </TR>
        </TABLE>
        <P align=center><INPUT onclick="javascript:return check();" type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:goto(); type=button value=返回列表 name=golist>
        </P>
    </CENTER>
</FORM>
</BODY>
</HTML>