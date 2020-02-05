package com.transferBjrabData;

import com.bizwink.cms.news.Article;
import com.bizwink.cms.util.DBUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

import jxl.Sheet;
import jxl.Workbook;
import jxl.Cell;

public class transferNsbdData {

    private static Connection createConnection(String ip, String username, String password, String server,int port,int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;

            if (flag == 0) {
                String connectionUrl = "jdbc:sqlserver://" + dbip + ":" + port + ";databaseName=" + server + ";integratedSecurity=true;";
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conn = DriverManager.getConnection(connectionUrl);
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                //conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":" + port, dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":" + port + ":"+server, dbusername, dbpassword);
            } else {
                Class.forName("org.gjt.mm.mysql.Driver").newInstance();
                conn = DriverManager.getConnection("jdbc:mysql://" + dbip + ":" + port + "/" + server + "?characterEncoding=gbk", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

    private static String copyPics(String cmsroot,String webroot,String sourcewebroot,String content,String sitename,String dirname) {
        String buf = content;
        Pattern p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|swf)\")|('[^',]*\\.(gif|jpg|jpeg|png|swf)')",Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = p.matcher(content);
        String matchStr=null;
        while (matcher.find()) {
            matchStr = content.substring(matcher.start(), matcher.end());
            if (matchStr.startsWith("\"")) matchStr = matchStr.substring(1);
            if (matchStr.endsWith("\"")) matchStr = matchStr.substring(0,matchStr.length()-1);
            if (matchStr.startsWith("'")) matchStr = matchStr.substring(1);
            if (matchStr.endsWith("'")) matchStr = matchStr.substring(0,matchStr.length()-1);

            //原始内容中的图片URL，判断是否是否需要处理图片的路径
            boolean makeNewPicURLFlag = true;
            String old_picurl = matchStr;
            if (old_picurl.startsWith("http://" + sitename))
                old_picurl = old_picurl.substring(("http://" + sitename).length());
            else if (old_picurl.startsWith("http://") && !old_picurl.startsWith("http://" + sitename))
                makeNewPicURLFlag = false;;

            if (makeNewPicURLFlag == true) {
                //原始文件带有全路径的文件名
                String full_picfilename = sourcewebroot + matchStr.replace("/", File.separator);
                full_picfilename = full_picfilename.replaceAll("\\{@ModelPath\\}","/upload/news/");
                System.out.println("source pic==" + full_picfilename);
                System.out.println("old_picurl==" + old_picurl);
                //获取不带有路径的文件名
                String picfilename = null;
                int posi = full_picfilename.lastIndexOf(File.separator);
                if (posi >-1)
                    picfilename = full_picfilename.substring(posi+1);
                else
                    picfilename = System.currentTimeMillis() + ".jpg";

                //WEB路径下的全路径文件名
                String web_full_filename = webroot + dirname.replace("/",File.separator) + "images" + File.separator;

                //新系统的图片URL
                String picurl = dirname + "images/" + picfilename;

                //获取CMS系统全路径的文件名
                String cms_full_filename = null;
                if (cmsroot.endsWith(File.separator))
                    cms_full_filename = cmsroot + "sites" +File.separator + sitename.replace(".","_") + dirname.replace("/",File.separator) + "images" + File.separator;
                else
                    cms_full_filename = cmsroot + File.separator + "sites" +File.separator + sitename.replace(".","_") + dirname.replace("/",File.separator) + "images" + File.separator;

                //判断CMS存放文件的路径是否存才，如果不存在则创建该目录
                File cmsfile = new File(cms_full_filename);
                if (!cmsfile.exists()) cmsfile.mkdirs();
                //判断WEB存放文件的路径是否存才，如果不存在则创建该目录
                File webfile = new File(web_full_filename);
                if (!webfile.exists()) webfile.mkdirs();
                try{
                    FileInputStream f_in = new FileInputStream(new File(full_picfilename));
                    FileOutputStream cms_out = new FileOutputStream(new File(cms_full_filename + picfilename));
                    FileOutputStream web_out = new FileOutputStream(new File(web_full_filename + picfilename));
                    byte[] read = new byte[1024];
                    int len = 0;
                    while((len = f_in.read(read))!= -1){
                        cms_out.write(read, 0, len);
                        web_out.write(read,0,len);
                    }
                    f_in.close();
                    cms_out.flush();
                    cms_out.close();
                    web_out.flush();
                    web_out.close();
                    System.out.println(picurl + "===" + old_picurl);
                    buf = buf.replace(old_picurl,picurl);
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        }

        //System.out.println(buf);
        return buf;
    }

    private static final String SQL_CREATE_ARTICLE_FOR_ORACLE =
            "INSERT INTO TBL_Article (siteid,ColumnID,SortID,MainTitle,Vicetitle,Summary,Keyword,Source,Content," +
                    "Emptycontentflag,Author,CreateDate,Lastupdated,FileName,downfilename,Doclevel,PubFlag,Status,Auditflag,Subscriber," +
                    "Editor,creator,DirName,Publishtime,SalePrice,vipprice,InPrice,MarketPrice,score,voucher,Weight,StockNum,Brand,Pic,BigPic,RelatedArtID," +
                    "LockStatus,isPublished,IndexFlag,ViceDocLevel,isJoinRSS,ClickNum,ReferID,ModelID,articlepic,urltype," +
                    "defineurl,t1,t2,t3,t4,t5,deptid,beidate,changepic,auditor,notearticleid,fromsiteid,sarticleid,mediafile,ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?," +
                    " ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_ARTICLE_FOR_ORACLE =
            "update tbl_article set columnid=?,DirName=?,content=?,CreateDate=?,Lastupdated=?,Publishtime=?,Editor=?,creator=?,sarticleid=? where id=?";

    public static void main(String[] args)
    {
        //测试图片处理
        /*try{
            List<String> lines = FileUtil.readFileByEncoding("C:\\data\\sourcewebroot\\11.txt","gbk");
            String content = "";
            for(int ii=0;ii<lines.size();ii++) {
                content = content + lines.get(ii);
            }
            String buf = copyPics("C:\\projects\\webbuilder\\webapps\\cms","C:\\data\\webroot","C:\\data\\sourcewebroot",content,"www.abc.com" , "/dangzhengyaowen/toutiao/");
            System.out.println(buf);
        } catch(IOException exp) {
            exp.printStackTrace();
        }*/

        if (args.length != 2) {
            System.out.println("命令名 cms栏目ID 老系统栏目ID");
            System.exit(0);
        } else {
            System.out.println("第一个参数==" + args[0]);
            System.out.println("第二个参数==" + args[1]);
        }

        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs = null;
        int siteid = 0;
        Article article = new Article();
        List<Article> articles = new ArrayList<Article>();
        try
        {
            //获取CMS的栏目目录、栏目ID等信息
            String t_dbip = "47.104.82.158";
            String t_username = "nsbdadmin";
            String t_password = "qazwsxokm";
            String t_server = "orcl11g";
            String dirname = null;
            int columnid = Integer.parseInt(args[0].trim());
            conn = createConnection(t_dbip, t_username, t_password,t_server,1522,1);
            pstmt = conn.prepareStatement("select id,siteid,dirname from tbl_column where id = " + columnid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dirname = rs.getString("dirname");
                siteid = rs.getInt("siteid");
            }
            rs.close();
            pstmt.close();

            //获取sitename
            String sitename = null;
            pstmt = conn.prepareStatement("select t.sitename from tbl_siteinfo t where t.siteid=" + siteid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sitename = rs.getString("sitename");
            }
            rs.close();
            pstmt.close();
            conn.close();

            String s_dbip = "localhost";
            String s_username = "mycmsadmin";
            String s_password = "Zaq!2w3e4r";
            String s_server = "mycms";
            String content = null;
            int scolid = Integer.parseInt(args[1].trim());
            StringBuffer aids = new StringBuffer();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            conn = createConnection(s_dbip, s_username, s_password,s_server,1433,0);
            pstmt = conn.prepareStatement("select Id,Title,LongTitle,Content,ShortContent,Source,UName,AddTime,UpdateTime from MC_article where Colid=" + scolid);
            //pstmt = conn.prepareStatement("select Id,Title,LongTitle,Content,ShortContent,Source,UName,AddTime,UpdateTime from MC_article where title like '%开放日%'");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setSiteID(siteid);
                article.setColumnID(columnid);
                content = rs.getString("Content");
                //if (content.toLowerCase().indexOf(".jpeg")>0 || content.toLowerCase().indexOf(".jpg")>0) aids = aids.append(rs.getInt("aid") + ";");
                article.setContent(content);
                article.setMainTitle(rs.getString("Title"));
                //article.setViceTitle(rs.getString("shorttitle"));
                //article.setKeyword(rs.getString("keywords"));
                //article.setSummary(rs.getString("description"));
                article.setSource(rs.getString("source"));
                article.setCreator(rs.getString("UName"));
                article.setEditor(rs.getString("UName"));
                article.setCreateDate(rs.getTimestamp("AddTime"));
                article.setPublishTime(rs.getTimestamp("AddTime"));
                article.setSarticleid(String.valueOf(rs.getInt("Id")));
                article.setLastUpdated(rs.getTimestamp("UpdateTime"));
                article.setDirName(dirname);
                article.setNullContent(0);
                article.setPubFlag(1);
                article.setMultimediatype(0);
                article.setDocLevel(0);
                article.setViceDocLevel(0);
                article.setAuditFlag(0);
                article.setStatus(1);
                article.setModelID(0);
                article.setSubscriber(0);
                article.setUrltype(0);
                article.setLockStatus(0);
                article.setIndexflag(0);
                articles.add(article);
                System.out.println(article.getMainTitle());
            }
            //FileUtil.writeFile(aids,"c:\\data\\aids.txt");
            rs.close();
            pstmt.close();
            conn.close();

            //处理文本中的图像文件

            //信息写入CMS数据库
            System.out.println("sitename==" + sitename);
            conn = createConnection(t_dbip, t_username, t_password, t_server, 1522, 1);
            for(int ii=0; ii<articles.size(); ii++) {
                article = articles.get(ii);
                //content = copyPics("C:\\projects\\webbuilder\\webapps\\cms","C:\\data\\webroot","C:\\data\\sourcewebroot",article.getContent(),sitename, article.getDirName());
                content = copyPics("C:\\website\\nsbd\\cms\\","C:\\website\\nsbd\\","C:\\website\\nsbd\\oldwebsite\\WebSite\\",article.getContent(),sitename, article.getDirName());

                int articleid = 0;
                pstmt = conn.prepareStatement("select id from tbl_article where maintitle = ? and columnid=?");
                pstmt.setString(1,article.getMainTitle());
                pstmt.setInt(2,columnid);
                rs = pstmt.executeQuery();
                if (rs.next()) articleid = rs.getInt("id");
                rs.close();
                pstmt.close();

                //文章在数据库里面不存在，在数据库增加该篇文章
                if (articleid == 0) {
                    pstmt = conn.prepareStatement("select tbl_article_id.NEXTVAL from dual");
                    rs = pstmt.executeQuery();
                    if (rs.next()) articleid = rs.getInt(1);
                    rs.close();
                    pstmt.close();

                    pstmt = conn.prepareStatement(SQL_CREATE_ARTICLE_FOR_ORACLE);
                    pstmt.setInt(1, article.getSiteID());
                    pstmt.setInt(2, article.getColumnID());
                    pstmt.setInt(3, article.getSortID());
                    if (!article.getMainTitle().isEmpty()){
                        pstmt.setString(4, article.getMainTitle());
                        pstmt.setString(5, article.getViceTitle());
                        pstmt.setString(6, article.getSummary());
                        pstmt.setString(7, article.getKeyword());
                        pstmt.setString(8, article.getSource());
                        DBUtil.setBigString("oracle", pstmt, 9, content);
                        pstmt.setInt(10, article.getNullContent());
                        if (article.getEditor()!=null)
                            pstmt.setString(11, article.getEditor());
                        else
                            pstmt.setString(11, "nsbdadmin");
                        pstmt.setTimestamp(12, article.getCreateDate());
                        pstmt.setTimestamp(13, article.getLastUpdated());
                        pstmt.setString(14, "");
                        pstmt.setString(15, article.getDownfilename());
                        pstmt.setInt(16, article.getDocLevel());
                        pstmt.setInt(17, article.getPubFlag());
                        pstmt.setInt(18, article.getStatus());
                        pstmt.setInt(19, article.getAuditFlag());
                        pstmt.setInt(20, article.getSubscriber());
                        if (!article.getEditor().isEmpty())
                            pstmt.setString(21, article.getEditor());
                        else
                            pstmt.setString(21, "nsbdadmin");

                        if (!article.getCreator().isEmpty())
                            pstmt.setString(22, article.getCreator());
                        else
                            pstmt.setString(22, "dzlladmin");
                        pstmt.setString(23, article.getDirName());
                        pstmt.setTimestamp(24, article.getPublishTime());
                        pstmt.setFloat(25, article.getSalePrice());
                        pstmt.setFloat(26, article.getVIPPrice());
                        pstmt.setFloat(27, article.getInPrice());
                        pstmt.setFloat(28, article.getMarketPrice());
                        pstmt.setInt(29, article.getScore());
                        pstmt.setInt(30, article.getVoucher());
                        pstmt.setFloat(31, article.getProductWeight());
                        pstmt.setInt(32, article.getStockNum());
                        pstmt.setString(33, "");
                        pstmt.setString(34, article.getProductPic());
                        pstmt.setString(35, article.getProductBigPic());
                        pstmt.setString(36, article.getRelatedArtID());
                        pstmt.setInt(37, 0);
                        pstmt.setInt(38, 0);
                        pstmt.setInt(39, 0);
                        pstmt.setInt(40, article.getViceDocLevel());
                        pstmt.setInt(41, 0);
                        pstmt.setInt(42, 0);
                        pstmt.setInt(43, article.getReferArticleID());
                        pstmt.setInt(44, article.getModelID());
                        pstmt.setString(45, article.getArticlepic());
                        pstmt.setInt(46, article.getUrltype());
                        pstmt.setString(47, article.getOtherurl());
                        pstmt.setInt(48, article.getT1());
                        pstmt.setInt(49, article.getT2());
                        pstmt.setInt(50, article.getT3());
                        pstmt.setInt(51, article.getT4());
                        pstmt.setInt(52, article.getT5());
                        pstmt.setString(53,article.getDeptid());
                        pstmt.setDate(54,article.getBbdate());
                        pstmt.setInt(55,article.getChangepic());
                        pstmt.setString(56,"");
                        pstmt.setInt(57,article.getNotes());
                        pstmt.setInt(58,article.getFromsiteid());
                        pstmt.setString(59,article.getSarticleid());
                        pstmt.setString(60,"");
                        pstmt.setInt(61, articleid);
                        pstmt.executeUpdate();
                        pstmt.close();
                        System.out.println("增加：" + article.getMainTitle() + "=" + article.getCreateDate() + "=" +article.getPublishTime() + "=" +article.getSource());
                    }
                } else {
                    //如果该文章存在，则修改该篇文章
                    pstmt = conn.prepareStatement(SQL_UPDATE_ARTICLE_FOR_ORACLE);
                    pstmt.setInt(1, article.getColumnID());
                    pstmt.setString(2, article.getDirName());
                    DBUtil.setBigString("oracle", pstmt, 3, content);
                    pstmt.setTimestamp(4, article.getCreateDate());
                    pstmt.setTimestamp(5, article.getLastUpdated());
                    pstmt.setTimestamp(6, article.getPublishTime());
                    if (!article.getEditor().isEmpty())
                        pstmt.setString(7, article.getEditor());
                    else
                        pstmt.setString(7, "nsbdadmin");

                    if (!article.getCreator().isEmpty())
                        pstmt.setString(8, article.getCreator());
                    else
                        pstmt.setString(8, "dzlladmin");
                    pstmt.setString(9,article.getSarticleid());
                    pstmt.setInt(10,articleid);
                    pstmt.executeUpdate();
                    pstmt.close();
                    System.out.println("修改：" + article.getMainTitle() + "=" + article.getCreateDate() + "=" +article.getPublishTime() + "=" +article.getSource());
                }
            }
            conn.commit();
            conn.close();
        } catch (SQLException exp1) {
            exp1.printStackTrace();
        }
    }
}