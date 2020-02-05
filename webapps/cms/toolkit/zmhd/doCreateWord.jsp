<%@ page contentType="text/html;charset=GBK" %>
<%@ page import="com.bizwink.cms.util.CompressedFileUtil" %>
<%@ page import="com.bizwink.cms.util.HtmlToWord" %>
<%@ page import="com.heaton.bot.RandomStrg" %>
<%@ page import="com.jspsmart.upload.SmartUpload" %>
<%@ page import="java.io.File" %>
<%
    String[] checkids = request.getParameterValues("checkids");
    //String checkId = "";
    if ((checkids != null) && (checkids.length > 0)) {
        String path="/wordfile/";
        // 检查目录是否存在
        File fileDir = new File(path);
        if (!fileDir.exists()  && !fileDir.isDirectory())
        {
            System.out.println("//不存在");
            fileDir.mkdir();
        }
        RandomStrg rstr = new RandomStrg();
        rstr.setCharset("a-z0-9");
        rstr.setLength(8);
        rstr.generateRandomObject();
        path = path+ "zmhd"+rstr.getRandom()+"/";
        for (int i = 0; i < checkids.length; i++) {
            //checkId = checkId + checkids[i] + ",";
            System.out.println(i+":"+checkids[i]);
            int id = Integer.valueOf(checkids[i]);
            HtmlToWord.writeWordFile(id,path);
        }
        CompressedFileUtil compressedFileUtil = new CompressedFileUtil();
        try {
            System.out.println(path);
            String downloadurl = compressedFileUtil.compressedFile(path, "/wordfile/");
            System.out.println(downloadurl);
            System.out.println("压缩文件已经生成...");
            if (downloadurl != null) {
                //新建一个SmartUpload对象
                SmartUpload su = new SmartUpload();
                //初始化
                su.initialize(pageContext);
                // 设定contentDisposition为null以禁止浏览器自动打开文件
                su.setContentDisposition(null);
                su.downloadFile(downloadurl);
            }
        } catch (Exception e) {
            System.out.println("压缩文件生成失败...");
            e.printStackTrace();
        }

    }

    /*if(checkId.indexOf(",") != -1)
        checkId = checkId.substring(0, (checkId.length()-1));*/

    //System.out.println(checkId);
%>