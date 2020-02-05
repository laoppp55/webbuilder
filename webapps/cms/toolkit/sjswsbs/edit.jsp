<%@page import="com.bizwink.cms.util.ParamUtil" contentType="text/html;charset=GBK"
        %>
<%@ page import="com.bizwink.cms.sjswsbs.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.bizwink.cms.kings.jspsmart.upload.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--<%@ taglib uri="../FCKeditor" prefix="FCK" %>--%>
<%
    int id = ParamUtil.getIntParameter(request, "id", 0);
    WsbsEntity wsbs = new WsbsEntity();
    CatgEntity catg = new CatgEntity();
    BasisEntity basisentity = new BasisEntity();
    List list = new ArrayList();
    IWsbsManager wsbsMgr = WsbsPeer.getInstance();
    wsbs = wsbsMgr.getByIdwsbs(id);
    list = wsbsMgr.getByIdGist(id);
    List catglist = new ArrayList();
    List deptlist = new ArrayList();
    catglist = wsbsMgr.getAllCatgEntity(0,1);
    deptlist = wsbsMgr.getAllCatgEntity(2,3);
    int startflag = 0;
    String name = "",serviceobject = "",basiss = "",chargestandard = "",timelimited = "",timelimit = "",orgnization = "",workaddress = "",
           ridingroute = "",relatephone = "",memo = "",workprocedure = "",	link_zxbl = "",	link_jgfk = "",	link_zxzx = "",	standby = "",
            coun = "",stuff = "",item_condition = "",item_times = "",code = "",classified = "",main = "",	jurisdiction = "",answer = "",
            supervision_telephone = "",	job_description = "";
    int catgid = 0;
    int depid = 0;
    String [] gistname = {};
    String [] gistid = {};
    int category = 0,category33 = 0,category38 = 0,category43 = 0,category48 = 0;
    String content = "",content33 = "",content38 = "",content43 = "",content48 = "";
    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
    String rq = sf.format(System.currentTimeMillis());
    boolean doCreate = ParamUtil.getBooleanParameter(request, "doCreate");
    String baseDir = request.getRealPath("/");
    String dir = wsbsMgr.getConfig() + java.io.File.separator + rq + java.io.File.separator;
    String imgurl = wsbsMgr.getConfig().substring(wsbsMgr.getConfig().indexOf("/upload"),wsbsMgr.getConfig().length())+"/"+rq+"/";
    boolean error = false;
    String filename = "";
    String tupian = "";
    if (doCreate) {
        SmartUpload mySmartUpload = new SmartUpload();
        mySmartUpload.initialize(this.getServletConfig(),request,response);
        com.jspsmart.upload.File  tmpFile = null;
        try {
            //System.out.println("111");
            mySmartUpload.upload();
            Files uploadFiles = mySmartUpload.getFiles();
            //System.out.println("222");
            startflag = Integer.parseInt(mySmartUpload.getRequest().getParameter("startflag"));
            name = mySmartUpload.getRequest().getParameter("name");
            catgid = Integer.parseInt(mySmartUpload.getRequest().getParameter("catgid"));
            depid = Integer.parseInt(mySmartUpload.getRequest().getParameter("depid"));
            serviceobject = mySmartUpload.getRequest().getParameter("serviceobject");
            basiss = mySmartUpload.getRequest().getParameter("basis");
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
            gistid = mySmartUpload.getRequest().getParameterValues("gistid");
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
            //System.out.println("content33 = " + content33.substring(content33.indexOf("<p>")+3,content33.indexOf("</p>")));
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
    if (startflag == 1) {
        wsbs = new WsbsEntity();
        wsbs.setID(id);
        wsbs.setCatgid(catgid);
        wsbs.setDepid(depid);
        wsbs.setName(name);
        wsbs.setServiceobject(serviceobject);
        wsbs.setBasis(basiss);
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
            basisentity = new BasisEntity();
            basisentity.setName(gistname[i]);
            basisentity.setId(Integer.parseInt(gistid[i]));
            //处理循环的fckeditor
            if(i==0){
                basisentity.setCategory(category);
                if(category ==1){
                    if(content.indexOf("<p>")>-1){
                        basisentity.setContent(content.substring(content.indexOf("<p>")+3,content.indexOf("</p>")));
                    }else{
                        basisentity.setContent(content);
                    }
                }else{
                    basisentity.setContent(content);
                }                
            }else if(i==1){
                basisentity.setCategory(category33);
                if(category ==1){
                    if(content33.indexOf("<p>")>-1){
                        basisentity.setContent(content33.substring(content33.indexOf("<p>")+3,content33.indexOf("</p>")));
                    }else{
                        basisentity.setContent(content33);
                    }
                }else{
                    basisentity.setContent(content33);
                }
            }else if(i==2){
                basisentity.setCategory(category38);
                if(category ==1){
                    if(content38.indexOf("<p>")>-1){
                        basisentity.setContent(content38.substring(content38.indexOf("<p>")+3,content38.indexOf("</p>")));
                    }else{
                        basisentity.setContent(content38);
                    }
                }else{
                    basisentity.setContent(content38);
                }
            }else if(i==3){
                basisentity.setCategory(category43);
                if(category ==1){
                    if(content43.indexOf("<p>")>-1){
                        basisentity.setContent(content43.substring(content43.indexOf("<p>")+3,content43.indexOf("</p>")));
                    }else{
                        basisentity.setContent(content43);
                    }
                }else{
                    basisentity.setContent(content43);
                }
            }else {
                basisentity.setCategory(category48);
                if(category ==1){
                    if(content48.indexOf("<p>")>-1){
                        basisentity.setContent(content48.substring(content48.indexOf("<p>")+3,content48.indexOf("</p>")));
                    }else{
                        basisentity.setContent(content48);
                    }
                }else{
                    basisentity.setContent(content48);
                }
            }
            list.add(basisentity);
        }

        wsbsMgr.updateWsbs(wsbs,list);
        response.sendRedirect("index.jsp");
    }
%>
<HTML>
<HEAD><TITLE>网上办事</TITLE>
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
            //alert(type);
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
    </SCRIPT>
</HEAD>
<BODY>
<FORM name=createForm action=edit.jsp?doCreate=true method=post enctype="multipart/form-data">
    <INPUT type=hidden value=1 name=startflag>
    <input type="hidden" name="manageflag" value="1" >
    <input type="hidden" name="id" value="<%=id%>" >
<CENTER>
    <TABLE cellSpacing=0 borderColorDark=#ffffff cellPadding=0 width="98%"
           borderColorLight=#008000 border=1>
        <TBODY>
        <TR>
            <TD width=493 bgColor=#33ccff colSpan=2 height=32>
                <P align=center>修改网上办事信息</P></TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>事项名称：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=name value="<%=wsbs.getName() == null ? "" : wsbs.getName()%>"> <FONT color=red>*</FONT>
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>所属分类：</TD>
            <TD align=left width="80%" height=32>&nbsp;
                <select name="catgid">
                    <option value="-1">请选择</option>
                    <%
                        if(catglist != null){
                            for(int i = 0; i < catglist.size(); i++){
                                catg = (CatgEntity)catglist.get(i);
                    %>
                    <option value="<%=catg.getId()%>"<%if(wsbsMgr.getByIdcatg(wsbs.getCatgid()).getId()==catg.getId()){%> selected<%}%>><%=catg.getName()%></option>
                    <%}}%>
                </select>
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>所属委办局：</TD>
            <TD align=left width="80%" height=32>&nbsp;
                <select name="depid">
                    <option value="-1">请选择</option>
                    <%
                        if(deptlist != null){
                            for(int i = 0; i < deptlist.size(); i++){
                                catg = (CatgEntity)deptlist.get(i);
                    %>
                    <option value="<%=catg.getId()%>"<%if(wsbsMgr.getByIdcatg(wsbs.getDepid()).getId()==catg.getId()){%> selected<%}%>><%=catg.getName()%></option>
                    <%}}%>
                </select>                                
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>该项服务的对象：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=serviceobject value="<%=wsbs.getServiceobject() == null ? "" : wsbs.getServiceobject()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理依据：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=basis value="<%=wsbs.getBasis() == null ? "" : wsbs.getBasis()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>收费依据和标准：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=chargestandard value="<%=wsbs.getChargestandard() == null ? "" : wsbs.getChargestandard()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>法定期限：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=timelimited value="<%=wsbs.getTimelimited() == null ? "" : wsbs.getTimelimited()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>承诺期限：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=timelimit value="<%=wsbs.getTimelimit() == null ? "" : wsbs.getTimelimit()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理部门：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=orgnization value="<%=wsbs.getOrgnization() == null ? "" : wsbs.getOrgnization()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理地点：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=workaddress value="<%=wsbs.getWorkaddress() == null ? "" : wsbs.getWorkaddress()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>乘车路线：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=ridingroute value="<%=wsbs.getRidingroute() == null ? "" : wsbs.getRidingroute()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>联系电话：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=relatephone value="<%=wsbs.getRelatephone() == null ? "" : wsbs.getRelatephone()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>备注：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=memo value="<%=wsbs.getMemo() == null ? "" : wsbs.getMemo()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理程序：</TD>
            <TD align=left width="80%" height=32>&nbsp;
                <textarea id="workprocedure" name="workprocedure"><%=wsbs.getWorkprocedure()%></textarea>
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
        <TR>
            <TD align=right width="20%" height=32>在线办理链接：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=link_zxbl value="<%=wsbs.getLink_zxbl() == null ? "" : wsbs.getLink_zxbl()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>结果反馈链接：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=link_jgfk value="<%=wsbs.getLink_jgfk() == null ? "" : wsbs.getLink_jgfk()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>在线咨询链接：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=link_zxzx value="<%=wsbs.getLink_zxzx() == null ? "" : wsbs.getLink_zxzx()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>录入日期：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=standby value="<%=wsbs.getStandby() == null ? "" : wsbs.getStandby()%>" readonly>
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理量：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=coun value="<%=wsbs.getCoun() == null ? "" : wsbs.getCoun()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>所需材料：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=stuff value="<%=wsbs.getStuff() == null ? "" : wsbs.getStuff()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>受理条件：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=item_condition value="<%=wsbs.getItem_condition() == null ? "" : wsbs.getItem_condition()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理时间：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=item_times value="<%=wsbs.getItem_times() == null ? "" : wsbs.getItem_times()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>事项编码：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=code value="<%=wsbs.getCode() == null ? "" : wsbs.getCode()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>事项分类：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=classified value="<%=wsbs.getClassified() == null ? "" : wsbs.getClassified()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理主体：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=main value="<%=wsbs.getMain() == null ? "" : wsbs.getMain()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>办理权限：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=jurisdiction value="<%=wsbs.getJurisdiction() == null ? "" : wsbs.getJurisdiction()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>常见问题解答：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=answer value="<%=wsbs.getAnswer() == null ? "" : wsbs.getAnswer()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>监督电话：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=supervision_telephone value="<%=wsbs.getSupervision_telephone() == null ? "" : wsbs.getSupervision_telephone()%>">
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>岗位说明：</TD>
            <TD align=left width="80%" height=32>&nbsp;
                <textarea id="job_description" name="job_description"><%=wsbs.getJob_description()%></textarea>
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
        <TR>
            <TD colSpan=2><FONT
                    color=red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;办理依据说明：</FONT></TD>
        </TR>
        <%
            if(list != null){
                for(int i = 0; i < list.size(); i++){
                BasisEntity basis = (BasisEntity)list.get(i);
        %>
        <TR>
            <TD align=right width="20%" height=32>说明名称：</TD>
            <TD align=left width="80%" height=32>&nbsp;<INPUT size=50 name=gistname value="<%=basis.getName()%>">
                <input type="hidden" name="gistid" value="<%=basis.getId()%>" >
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>选择：</TD>
            <TD align=left width="80%" height=32>&nbsp;
                <%if(i==0){%>
                <INPUT type="radio" name=category value="0" <%if(basis.getCategory()==0){%>checked<%}%> onclick="javascript:catecheck(0);">法规依据
                <INPUT type="radio" name=category value="1" <%if(basis.getCategory()==1){%>checked<%}%> onclick="javascript:catecheck(1);">相关附件
                <%}else if(i==1){%>
                <INPUT type="radio" name=category33 value="0" <%if(basis.getCategory()==0){%>checked<%}%> onclick="javascript:catecheck(33);">法规依据
                <INPUT type="radio" name=category33 value="1" <%if(basis.getCategory()==1){%>checked<%}%> onclick="javascript:catecheck(34);">相关附件
                <%}else if(i==2){%>
                <INPUT type="radio" name=category38 value="0" <%if(basis.getCategory()==0){%>checked<%}%> onclick="javascript:catecheck(38);">法规依据
                <INPUT type="radio" name=category38 value="1" <%if(basis.getCategory()==1){%>checked<%}%> onclick="javascript:catecheck(39);">相关附件
                <%}else if(i==3){%>
                <INPUT type="radio" name=category43 value="0" <%if(basis.getCategory()==0){%>checked<%}%> onclick="javascript:catecheck(43);">法规依据
                <INPUT type="radio" name=category43 value="1" <%if(basis.getCategory()==1){%>checked<%}%> onclick="javascript:catecheck(44);">相关附件
                <%}else{%>
                <INPUT type="radio" name=category48 value="0" <%if(basis.getCategory()==0){%>checked<%}%> onclick="javascript:catecheck(48);">法规依据
                <INPUT type="radio" name=category48 value="1" <%if(basis.getCategory()==1){%>checked<%}%> onclick="javascript:catecheck(49);">相关附件                
                <%}%>
            </TD>
        </TR>
        <TR>
            <TD align=right width="20%" height=32>说明内容：</TD>
            <TD align=left width="80%" height=32>
                <%if(i==0){ if(basis.getCategory()==0){%>
                    <div id="category0" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content" name="content"><%=basis.getContent()%></textarea>
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
                    <div id="category1" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div><%}else{%>
                    <div id="category0" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content" name="content"><%=basis.getContent()%></textarea>
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
                    <div id="category1" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div>
                <%}}else if(i==1){if(basis.getCategory()==0){%>
                    <div id="categorys33" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content33" name="content33"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content33') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys34" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div><%}else{%>
                    <div id="categorys33" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content33" name="content33"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content33') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys34" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div>
                <%}}else if(i==2){if(basis.getCategory()==0){%>
                    <div id="categorys38" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content38" name="content38"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content38') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys39" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div><%}else{%>
                    <div id="categorys38" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content38" name="content38"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content38') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys39" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div>
                <%}}else if(i==3){if(basis.getCategory()==0){%>
                    <div id="categorys43" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content43" name="content43"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content43') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys44" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div><%}else{%>
                    <div id="categorys43" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content43" name="content43"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content43') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys44" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div>
                <%}}else{if(basis.getCategory()==0){%>
                    <div id="categorys48" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content48" name="content48"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content48') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys49" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div><%}else{%>
                    <div id="categorys48" <%if(basis.getCategory()==1){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;
                        <textarea id="content48" name="content48"><%=basis.getContent()%></textarea>
                        <script type="text/javascript">
                            var oFCKeditor = new FCKeditor('content48') ;
                            oFCKeditor.BasePath = "../fckeditor/";
                            oFCKeditor.Width='95%';
                            oFCKeditor.Height = 300;
                            oFCKeditor.ToolbarSet = "Sjswsbs";
                            oFCKeditor.ReplaceTextarea();
                        </script>
                        </TD>
                    </TR></TABLE></div>
                    <div id="categorys49" <%if(basis.getCategory()==0){%>style="display:none"<%}%>>
                    <TABLE width="100%">
                    <TR height=32>
                        <TD align=left width=100% height=32>&nbsp;<INPUT type="file" name=content> </TD>
                    </TR> </TABLE>
                    </div>
                <%}}%>
            </TD>
        </TR>
        <%}}%>
        </TBODY>
    </TABLE>
        <P align=center><INPUT onclick="javascript:return check();" type=submit value=" 确 认 " name=Ok>&nbsp;&nbsp;
            <INPUT onclick=javascript:goto(); type=button value=返回列表 name=golist>
        </P>    
</CENTER></FORM>
</BODY>
</HTML>
