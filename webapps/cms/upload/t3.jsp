<%@ page import="java.io.*,
                 com.bizwink.cms.toolkit.companyinfo.*,
                 com.bizwink.cms.util.*" contentType="text/html;charset=utf-8"
%>
<%
    String baseDir = application.getRealPath("/");
    baseDir = baseDir + java.io.File.separator + "sites"+java.io.File.separator;
    ICompanyinfoManager companyManager = CompanyinfoPeer.getInstance();

    String username = null;
    String password = null;
    String doflag = null;
    String name = null;
    String description=null;
    String companyname = null;
    int doupload = ParamUtil.getIntParameter(request,"doupload",0);
    if (doupload == 1) {
        username = ParamUtil.getParameter(request,"username");
        password = ParamUtil.getParameter(request,"password");
        doflag = ParamUtil.getParameter(request,"do");
        companyname = ParamUtil.getParameter(request,"company");
        name = ParamUtil.getParameter(request,"name");
        description = ParamUtil.getParameter(request,"description");
        String lat = ParamUtil.getParameter(request,"lat");
        String lon = ParamUtil.getParameter(request,"lon");
        int siteid = ParamUtil.getIntParameter(request,"siteid",0);

        System.out.println("lat="+lat);
        System.out.println("lon="+lon);

        int posi = lat.indexOf(" ");
        System.out.println("lat posi=" + posi);
        lat = lat.substring(posi+1).trim();
        double lat_d=Double.parseDouble(lat);
        System.out.println("lat_d="+lat_d);

        posi = lon.indexOf(" ");
        System.out.println("lon posi=" + posi);
        lon = lon.substring(posi+1).trim();
        double lon_d = Double.parseDouble(lon);
        System.out.println("lon_d="+lon_d);


        Companyinfo company = new Companyinfo();
        company.setSiteid(siteid);
        company.setCompanyname(companyname);
        company.setCompanyaddress("公司地址！！！！");
        company.setClassification("学校");
        company.setCompanyclassid(537);
        company.setCompanylatitude(lat_d + 0.0011d);
        company.setCompanylongitude(lon_d + 0.0061d);
        company.setSummary(description);
        retCompany retc = companyManager.addCompanyInfo(company,baseDir);          //retCompany类保存生成的ID和生成的每个多媒体文件的名字

       // System.out.println("myname = " + myname);
        System.out.println("doflag = " + doflag);
        System.out.println("companyname = " + companyname);
        System.out.println("username = " + username);
        System.out.println("description = " + description);

        out.write("OK");
        out.flush();
    }
%>
<body>
<form method="post" action="t3.jsp?doupload=1" name=uploadfile id="ufid">
    <input type="hidden" name="siteid" value="1152">
    <table width="100%" border="0" align="center">
        <tr>
            <td colspan="2" height="36">我的名称：<input type="text" name="myname" value="hello name"> </td>
        </tr>
        <tr>
            <td colspan="2" height="36">用户名：<input type="text" name="username" value="peter song"></td>
        </tr>
        <tr>
            <td colspan="2" height="36">口  令：<input type="password" name="password"></td>
        </tr>
        <tr>
            <td colspan="2" height="36">公司名称：<input type="text" name="company" value=""></td>
        </tr>
        <tr>
            <td colspan="2" height="36">描  述：<input type="text" name="description" value="description of company"></td>
        </tr>
        <tr>
            <td colspan="2" height="36">经  度：<input type="text" name="lon"></td>
        </tr>
        <tr>
            <td colspan="2" height="36">纬  度：<input type="text" name="lat" value=""></td>
        </tr>
        <tr>
            <td>注:请上传图片的 <font color=red><b>ZIP</b></font> 压缩包文件</td></tr>
        <tr>
            <td colspan="2" align="center" height=60>
                <input type="submit" value="  上传  " class=tine>&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="  取消  " class=tine onclick="window.close();">
            </td>
        </tr>
    </table>
</form>

</body>
</html>
