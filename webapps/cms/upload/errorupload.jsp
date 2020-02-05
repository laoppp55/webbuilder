<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="com.bizwink.cms.news.ColumnPeer"%>
<%@ page import="com.bizwink.cms.util.SessionUtil"%>
<%@ page import="com.bizwink.cms.news.IColumnManager"%>
<%@ page import="com.bizwink.cms.util.ParamUtil"%>
<%@ page import="com.bizwink.cms.security.Auth"%>
<%@ page import="com.bizwink.upload.MultipartFormHandle"%>
<%@ page import="com.bizwink.upload.uploaderrormsg"%>
<%@ page import="com.bizwink.cms.util.StringUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="org.apache.tools.zip.ZipFile"%>
<%@ page import="org.apache.tools.zip.ZipEntry"%>

<%@ page import="java.io.*"%>
<%@ page import="com.bizwink.upload.FileDeal" %>
<%@ page import="com.bizwink.cms.pic.Pic" %>
<%@ page import="com.bizwink.cms.pic.IPicManager" %>
<%@ page import="com.bizwink.cms.pic.PicPeer" %>

<%
	Auth authToken = SessionUtil.getUserAuthorization(request,
			response, session);
	if (authToken == null) {

		response.sendRedirect(response
				.encodeRedirectURL("../login.jsp?msg=系统超时，请重新登陆!"));
		return;
	}

	String username = authToken.getUserID();
	String sitename = authToken.getSitename();
	int siteid = authToken.getSiteID();
	String pubsite = request.getParameter("pubsite");
	String picnamessss = request.getParameter("picname");

	String dir = "";
	String tempDir = request.getParameter("fileDir");
	int columnID = 0;
	String Message = "";
	String baseDir = application.getRealPath("/");
	String filename = request.getParameter("picname");
	columnID = ParamUtil.getIntParameter(request, "column", 0);
	IColumnManager columnMgr = ColumnPeer.getInstance();

	uploaderrormsg errormsg = (uploaderrormsg) request.getAttribute("UploadMsg");

	String sitenameBuf = StringUtil.replace(sitename, "_", ".");
	int posis = tempDir.indexOf(sitenameBuf);
	if (posis != -1) tempDir = tempDir.substring(posis + sitenameBuf.length());
	dir = baseDir + "sites" + java.io.File.separator + sitename;

	int flag = ParamUtil.getIntParameter(request, "flag", -1);
	if (flag == 1) {
		IPicManager pictureMgr = PicPeer.getInstance();
		Enumeration enumer = request.getParameterNames();
		HashMap map = new HashMap();
		String pic = "";
		String str = "";
		while (enumer.hasMoreElements()) {
			String aa = (String) enumer.nextElement();

			if (aa.indexOf("chec") > -1) {
				Timestamp oredertime = new Timestamp(System
						.currentTimeMillis());

				str = String.valueOf(oredertime);
				String ostr = "8";
				ostr = ostr + str.substring(2, 4) + str.substring(5, 7)
						+ str.substring(8, 10);
				String random = String.valueOf(Math.random());
				random = random.substring(random.indexOf(".") + 1,
						random.indexOf(".") + 7);
				ostr = ostr + random;

				pic = request.getParameter(aa);
				String piclastindex = "";
				if (pic.lastIndexOf(".") > -1) {
					piclastindex = pic.substring(pic.lastIndexOf("."),
							pic.length());
				}

				map.put(pic, ostr + piclastindex);

			}
			if (aa.indexOf("box") > -1) {
				pic = request.getParameter(aa);
				map.put(pic, "no");
			}
		}
		//获得压缩文件名字

		Set set = map.keySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			String a = (String) iterator.next();

			if (map.get(a).equals("no")) {
				//覆盖
				System.out.println("覆盖的图片a=" + a);
			} else {
				//重命名
				System.out.println(a + "重命名=" + map.get(a));
			}
		}

		ZipFile zf;
		final int EOF = -1;
		Enumeration enums;
		List fileNameList = new ArrayList();
		//解压 上传的images 文件   获得上传  文件名字
		try {
			zf = new ZipFile(dir + File.separator + filename);
			enums = zf.getEntries();
			while (enums.hasMoreElements()) {
				List dirList = new ArrayList();
				ZipEntry target = (ZipEntry) enums.nextElement();
				String name = target.getName();
				String cname = "";
				//    System.out.println("ddddd" + map.get(name) + "   " + name);
				if (map.get(name) != null) {
					if (!map.get(target.getName()).equals("no")) {

						cname = (String) map.get(name);

						Pic pics = new Pic();
						String dirtem="";
						if(dir.indexOf(baseDir)>-1){
							dirtem=dir.substring(dir.indexOf(baseDir)+baseDir.length(),dir.length());
						}
						try {
							pics.setSiteid(siteid);
							pics.setColumnid(columnID);
							pics.setHeight(0);
							pics.setWidth(0);
							pics.setPicsize(0);
							pics.setPicname(StringUtil.gb2iso4View((String)map.get(name)));
							String imgurl="/webbuilder/"+dirtem+ tempDir+ "images"+ File.separator+ StringUtil.gb2iso4View((String)map.get(name));
							imgurl=StringUtil.replace(imgurl,java.io.File.separator,"/");
							// imgurl=imgurl.replaceAll(File.separator,"/");
							System.out.println("imgurl="+imgurl);
							pics.setImgurl(imgurl);
							pics.setCreatedate(new Timestamp(System.currentTimeMillis()));
							String picfilename= pictureMgr.saveOnePicture(pics);

						} catch (Exception pex) {
							System.out.println(""+pex.toString());
						}

						String filen = dir + tempDir + "images"
								+ File.separator
								+ StringUtil.gb2iso4View(cname);

						File file = new File(filen);

						if (target.isDirectory()) {
							dirList.add(file);
							// fileNameList = getSubDirectoryFiles(fileNameList, dirList, sitename);
						} else {
							InputStream is = zf.getInputStream(target);
							BufferedInputStream bis = new BufferedInputStream(
									is);
							File dirc = new File(file.getParent());
							dirc.mkdirs();
							FileOutputStream fos = new FileOutputStream(
									file);
							BufferedOutputStream bos = new BufferedOutputStream(
									fos);

							int c;
							while ((c = bis.read()) != EOF) {
								bos.write((byte) c);
							}
							bos.close();
							fos.close();

							String filePath = StringUtil.replace(file
									.getPath(), "/", File.separator);
							String fileDir = filePath
									.substring(
											filePath.indexOf(sitename)
													+ sitename.length(),
											filePath
													.lastIndexOf(File.separator) + 1);
							fileNameList.add(filePath + "," + fileDir);

						}
						//把摸版的替换   读取摸版
						if(errormsg.getTemplatename()!=null){
							FileInputStream fis = new FileInputStream(
									errormsg.getTemplatename());
							InputStreamReader isr = new InputStreamReader(
									fis);
							BufferedReader br = new BufferedReader(isr);
							String data = null;
							String content = "";

							while ((data = br.readLine()) != null) {
								data = StringUtil
										.replace(data, name, cname);
								content = content + data;
								//
							}
							//System.out.println("content=" + content);

							PrintWriter pw = new PrintWriter(
									new FileOutputStream(errormsg
											.getTemplatename()));
							pw.write(content);
							pw.close();

							fis.close();
							isr.close();
							br.close();
						}
					}
					//是覆盖的就覆盖
					if (map.get(target.getName()).equals("no")) {

						String filen = dir
								+ tempDir
								+ "images"
								+ File.separator
								+ StringUtil.gb2iso4View(target
								.getName());

						File file = new File(filen);

						if (target.isDirectory()) {
							dirList.add(file);
							// fileNameList = getSubDirectoryFiles(fileNameList, dirList, sitename);
						} else {
							InputStream is = zf.getInputStream(target);
							BufferedInputStream bis = new BufferedInputStream(
									is);
							File dirc = new File(file.getParent());
							dirc.mkdirs();
							FileOutputStream fos = new FileOutputStream(
									file);
							BufferedOutputStream bos = new BufferedOutputStream(
									fos);

							int c;
							while ((c = bis.read()) != EOF) {
								bos.write((byte) c);
							}
							bos.close();
							fos.close();

							String filePath = StringUtil.replace(file
									.getPath(), "/", File.separator);
							String fileDir = filePath
									.substring(
											filePath.indexOf(sitename)
													+ sitename.length(),
											filePath
													.lastIndexOf(File.separator) + 1);
							fileNameList.add(filePath + "," + fileDir);

						}
					}
				}
			}
			zf.close();

		} catch (FileNotFoundException e) {
			System.out.println("zipfile not found");
		} catch (IOException e) {
			System.out.println("IO error...");
			if (map.get(filename) != null) {
				if (!map.get(filename).equals("no")) {
					Pic pics = new Pic();
					String dirtem="";
					if(dir.indexOf(baseDir)>-1){
						dirtem=dir.substring(dir.indexOf(baseDir)+baseDir.length(),dir.length());
					}
					try {
						pics.setSiteid(siteid);
						pics.setColumnid(columnID);
						pics.setHeight(0);
						pics.setWidth(0);
						pics.setPicsize(0);
						pics.setPicname(StringUtil.gb2iso4View((String)map.get(filename)));
						String imgurl="/webbuilder/"+dirtem+ tempDir+ "images"+ File.separator+ StringUtil.gb2iso4View((String)map.get(filename));
						imgurl=StringUtil.replace(imgurl,java.io.File.separator,"/");
						// imgurl=imgurl.replaceAll(File.separator,"/");
						System.out.println("imgurl="+imgurl);
						pics.setImgurl(imgurl);
						pics.setCreatedate(new Timestamp(System.currentTimeMillis()));
						String picfilename= pictureMgr.saveOnePicture(pics);

					} catch (Exception pex) {
						System.out.println(""+pex.toString());
					}

					FileDeal.copy(dir+File.separator+filename, dir+tempDir+"images"+ File.separator+ map.get(filename), 0);
				}
				if (map.get(filename).equals("no")) {
					System.out.println("filename="+filename+"  "+map.get(filename));
					FileDeal.copy(dir+File.separator+filename, dir+tempDir+"images"+ File.separator+ filename, 0);
				}
			}
		} finally {
			File file = new File(dir + File.separator + filename);
			file.delete();
			response.sendRedirect("do_modelupload.jsp");
		}

	}
%>

<html>
<head>
	<title>上传文件错误信息</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel=stylesheet type=text/css href="../style/global.css">
	<script type="text/javascript">
        function change_override(val) {
            //form1.updatename.checked = false;
            //alert("override");
            var checkedall = document.getElementsByTagName("input");
            var override = document.getElementById("boxoveride");
            var updatename = document.getElementById("checupdatename");
            var panupdate = 0;
            var panover = 0;
            if (updatename.checked)
            {
                panupdate = 1;

            }
            if (override.checked)
            {
                panover = 1;
            }
            if (panover == 1 && panupdate == 1)
            {
                alert("不能选种");
                return;
            }
            if (!override && updatename)
            {
                alert("对不起只能全选一种格式");
                return;
            }


            for (var i = 0; i < checkedall.length; i++)
            {
                if (checkedall[i].name.substr(0, 3) == "box")
                {

                    checkedall[i].checked = val;
                }
            }

            for (var i = 0; i < checkedall.length; i++)
            {
                if (checkedall[i].name.substr(0, 4) == "chec")
                {

                    checkedall[i].checked = false;
                }
                /* if(checkedall[i].name.substr(0,4)=="ppic")
              {
                  checkedall[i].disabled="disabled";
              }  */
            }
        }
        /*function change_updatename() {
        form1.override.checked = false;
        alert("update");
        }     */
        function checkedupdateall(val)
        {
            var checkedall = document.getElementsByTagName("input");
            var override = document.getElementById("boxoveride");
            var updatename = document.getElementById("checupdatename");
            var panupdate = 0;
            var panover = 0;
            if (updatename.checked)
            {
                panupdate = 1;

            }
            if (override.checked)
            {
                panover = 1;
            }
            if (panover == 1 && panupdate == 1)
            {
                alert("不能选种");
                return;
            }
            for (var i = 0; i < checkedall.length; i++)
            {

                if (checkedall[i].name.substr(0, 4) == "chec")
                {

                    checkedall[i].checked = val;

                }
                /*     if(panupdate==1){
                     if(checkedall[i].name.substr(0,4)=="ppic")
                     {
                         checkedall[i].disabled="";
                     }
                     }else{
                        if(checkedall[i].name.substr(0,4)=="ppic")
                     {
                         checkedall[i].disabled="disabled";
                     }
                     } */


            }
            for (var i = 0; i < checkedall.length; i++)
            {
                if (checkedall[i].name.substr(0, 3) == "box")
                {

                    checkedall[i].checked = false;
                }

            }
        }
        function bijiao(id)
        {
            var boxoverride = document.getElementById("boxoverride" + id);
            var checupdatename = document.getElementById("checupdatename" + id);
            var panover = 0;
            var panupdate = 0;

            if (boxoverride.checked)
            {
                panover = 1;
            }
            else {
                panover = 0
            }
            if (checupdatename.checked)
            {
                panupdate = 1;
            }
            else {
                panupdate = 0
            }
            /*   if(panover==1&&panupdate==0)
          {
              document.getElementById("ppicname"+id).disabled="disabled";
          }  */
            if (panupdate == 1 && panover == 1)
            {
                alert("不能选种");
                boxoverride.checked = false;



                // document.getElementById("picname"+id).disabled="disabled";
                return;
            }
        }

        function bijiao1(id)
        {
            var boxoverride = document.getElementById("boxoverride" + id);
            var checupdatename = document.getElementById("checupdatename" + id);
            var panover = 0;
            var panupdate = 0;
            //  document.getElementById("ppicname"+id).disabled="";
            if (boxoverride.checked)
            {
                panover = 1;
            }
            else {
                panover = 0
            }
            if (checupdatename.checked)
            {
                panupdate = 1;
            }
            else {
                panupdate = 0
            }

            /*  if(panover==0&&panupdate==1)
           {
               document.getElementById("ppicname"+id).disabled="";
           }
           else{
               document.getElementById("ppicname"+id).disabled="disabled";
           } */
            if (panupdate == 1 && panover == 1)
            {
                alert("不能选种");

                checupdatename.checked = false;

                return;
            }
        }
	</SCRIPT>
</head>

<body bgcolor="#cccccc">
<form name="form1"
	  action="<%=request.getContextPath()%>/multipartformserv?dir=<%=dir%>"
	  method="post" enctype="multipart/form-data">
	<input type="hidden" name="<%=MultipartFormHandle.FORWARDNAME%>"
		   value="/upload/errorupload.jsp">
	<input type="hidden" name="status" value="save">
	<input type="hidden" name="picname" value="<%=filename%>">
	<input type="hidden" name="column" value="<%=columnID%>">
	<input type="hidden" name="baseDir" value="<%=baseDir%>">
	<input type="hidden" name="fileDir" value="<%=tempDir%>">
	<input type="hidden" name="sitename" value="<%=sitename%>">
	<input type="hidden" name="siteid" value="<%=siteid%>">
	<input type="hidden" name="username" value="<%=username%>">
	<input type="hidden" name="flag" value="1">
	<input type="hidden" name="pubsite" value="<%=pubsite%>">
	<table align="center" width="90%" border=0>
		<%
			String fname = "";
			if (errormsg != null) {
				List piclist = errormsg.getErrorPics();
				System.out.println("size=" + piclist.size());
				for (int i = 0; i < piclist.size(); i++) {
					String picname="";
					picname = (String) errormsg.getErrorPics().get(i);

					out.println("<tr>");


					//int posi = picname.indexOf(baseDir);
					//if (posi > -1) picname = picname.substring(posi+baseDir.length());
					//picname = StringUtil.replace(picname,java.io.File.separator,"/");
					System.out.println("picname="+picname);
					int posi = picname.lastIndexOf("/");
					if (posi > -1)
						fname = picname.substring(posi + 1);

					picname = "/webbuilder/" + picname;
					if(filename.indexOf(".jpg")>-1||filename.indexOf(".gif")>-1||filename.indexOf("png")>-1)
					{
						fname=filename;
						if(picname.indexOf(baseDir)>-1)
						{
							System.out.println("bbbbb");
							picname=picname.substring(picname.indexOf(baseDir)+baseDir.length(),picname.length());
							picname="/webbuilder/"+picname;
						}
					}

					System.out.println("fname="+fname+"    picname="+picname);
					out.println("<td><input type=checkbox onclick=bijiao(" + i
							+ ") id=boxoverride" + i + "  name=boxoverride" + i
							+ " value=\"" + fname + "\"></td>");
					out.println("<td><input type=checkbox onclick=bijiao1(" + i
							+ ") id=checupdatename" + i
							+ "  name=checupdatename" + i + " value=\"" + fname
							+ "\"></td>");
					out
							.println("<td><a href=/webbuilder/upload/prview.jsp?name="
									+ picname
									+ " target=_blank><img src=\""
									+ picname
									+ "\" height=\"40\" width=\"80\" /></a></td><td>"
									+ fname + " </td>");
					//   out.println("<td><a href=/webbuilder/upload/prview.jsp?name="+picname+" target=_blank><img src=\"" + picname + "\" height=\"40\" width=\"80\" /></a></td><td><input type=hidden name=picnames value=" + fname + " > </td>");
					out.println("</tr>");
				}
			}
		%>
		<tr height=35>
			<td>
				<input type="checkbox" name="boxoverride" id="boxoveride"
					   value="a" onclick="javascript:change_override(this.checked)">
				全选覆盖
			</td>
			<td>
				<input type="checkbox" name="checupdatename" id="checupdatename"
					   value="a" onclick="javascript:checkedupdateall(this.checked)">
				全选改名
			</td>
			<td>
				<%=errormsg.getTemplatename()%>
			</td>
		</tr>

		<tr height=35>
			<td>
				&nbsp;
			</td>
			<td align=center>
				<input type="submit" value="  确定  " class=tine>
				&nbsp;&nbsp;
				<input type="button" value="  取消  " class=tine
					   onClick="javascript:top.close();">
			</td>
			<td>
				&nbsp;
			</td>
		</tr>
	</table>
</form>
</body>
</html>
