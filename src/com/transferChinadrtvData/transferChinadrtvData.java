package com.transferChinadrtvData;

import com.bizwink.cms.extendAttr.ExtendAttrPeer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.util.DBUtil;
import com.bizwink.cms.util.StringUtil;

import java.sql.*;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class transferChinadrtvData {

    //转入BUCID的数据
    public static void main(String[] args) {
        String content = "";
        Timestamp createdate = null;
        String title = "";
        String desc = "";

        File dir = new File("D:\\new_bucid\\dongtai");
        File[] file = dir.listFiles();
        for (int i=0; i<file.length; i++) {
            try {
                if (file[i].getName().indexOf(".shtml") > -1 && file[i].getName().indexOf("index") == -1 && file[i].getName().indexOf("57m4py07") == -1) {
                    FileReader reader = new FileReader("D:\\new_bucid\\dongtai\\" + file[i].getName());
                    BufferedReader br = new BufferedReader(reader);
                    String s1 = null;
                    StringBuffer buf = new StringBuffer();
                    while((s1 = br.readLine()) != null) {
                        buf.append(s1 + "\r\n");
                    }
                    br.close();
                    reader.close();

                    Pattern p = Pattern.compile("<title>[\\s\\S]*</title>",Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(buf);
                    if (m.find()) {
                        int start = m.start();
                        int end = m.end();
                        title = buf.substring(start+"<title>".length(),end-"</title>".length());
                        int posi = title.indexOf(">");
                        if (posi > -1) title = title.substring(0,posi);
                    }

                    p = Pattern.compile("<meta name=\"description\" content=\"[^<>]*\">",Pattern.CASE_INSENSITIVE);
                    m = p.matcher(buf);
                    if (m.find()) {
                        int start = m.start();
                        int end = m.end();
                        desc = buf.substring(start,end);
                        desc = StringUtil.replace(desc,"<meta name=\"description\" content=\"","");
                        desc = StringUtil.replace(desc,"\">","");
                    }

                    int posi = buf.lastIndexOf("</STRONG></P>");
                    if (posi>-1) {
                        content = buf.substring(posi + "</STRONG></P>".length());
                        posi = content.indexOf("</TD>");
                        content = content.substring(0,posi);
                        System.out.println("title========" + title + "============" + file[i].getName());
                        System.out.println(content);

                        File f = new File("D:\\new_bucid\\dongtai\\" + file[i].getName());
                        createdate = new Timestamp(f.lastModified());

                        System.out.println(createdate.toString());
                        Article article = new Article();
                        article.setMainTitle(title);
                        article.setSummary(desc);
                        article.setContent(content);
                        article.setCreateDate(createdate);
                        article.setLastUpdated(createdate);
                        article.setPublishTime(createdate);
                        article.setColumnID(45522);
                        article.setSiteID(59);
                        article.setEditor("bucid");
                        article.setPubFlag(1);
                        article.setDocLevel(0);
                        article.setViceDocLevel(0);
                        article.setAuditFlag(0);
                        article.setStatus(1);
                        article.setModelID(0);
                        article.setSubscriber(0);
                        article.setUrltype(0);
                        article.setNullContent(0);

                        IExtendAttrManager extMgr = ExtendAttrPeer.getInstance();
                        try {
                            extMgr.create(null,null,article,null,null,null,null,null);
                            f.delete();
                        } catch (Exception exp) {
                            System.out.println("error=======" + file[i].getName());
                            exp.printStackTrace();
                        }
                    }
                }
            } catch (IOException exp) {
                exp.printStackTrace();
            }
        }
    }

    //Main method
    /*public static void main(String[] args) {
        //getdata
        List list = null;
        System.out.println(System.getProperty("user.dir"));

        list = getData("E:\\webbuilder\\webapps\\cms\\WEB-INF\\classes\\com\\transferChinadrtvData\\zone.txt");
        System.out.println("导入地区！");
        for(int i = 0; i < list.size();i ++){
            String[] getnum = (String[]) list.get(i);
            System.out.println("id"+getnum[0]);
            System.out.println("zonename = "+getnum[2]);
        }
        int flag = createZone(list);
        System.out.println("flag = "+flag);
        list = null;
        list = getData("E:\\webbuilder\\webapps\\cms\\WEB-INF\\classes\\com\\transferChinadrtvData\\prov.txt");
        System.out.println("导入省份！");
        for(int i = 0; i < list.size();i ++){
            String[] getnum = (String[]) list.get(i);
            System.out.println("id"+getnum[0]);
            System.out.println("proname = "+getnum[1]);
        }
        flag = createProv(list);
        System.out.println("flag = "+flag);
        list = null;
        list = getData("E:\\webbuilder\\webapps\\cms\\WEB-INF\\classes\\com\\transferChinadrtvData\\city.txt");
        System.out.println("导入城市！");
        for(int i = 0; i < list.size();i ++){
            String[] getnum = (String[]) list.get(i);
            System.out.println("id"+getnum[0]);
            System.out.println("cityname = "+getnum[2]);
        }
        flag = createCity(list);
        System.out.println("flag = "+flag);
        //int articleflag = createArticle(list);
    }
    */

    private static List getData(String path) {
        List list = new ArrayList();
        try {
            int nLineCount = 0;//行数
            //File file = new File("d:\\dataforyr\\test1.txt");
            File file = new File(path);
            BufferedReader in = new BufferedReader(new FileReader(file));
            String strLine = "";

            while ((strLine = in.readLine()) != null) {
                nLineCount++;
                String getnum[] = strLine.split(",");
                list.add(getnum);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    private static int createZone(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip, username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_zone(id,cityid,zonename,orderid) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setInt(2,Integer.parseInt(getnum[1]));
                pstmt.setString(3,getnum[2]);
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static int createProv(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip, username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_province(id,ProvName,orderid,EmsFee) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setString(2,getnum[1]);
                pstmt.setInt(3,Integer.parseInt(getnum[2]));
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static int createCity(List list) {
        String dbip = "localhost";
        String username = "webcms";
        String password = "1qaz2wsx";
        int flag = 0;
        Connection conn = createConnection(dbip, username, password, 2);
        PreparedStatement pstmt = null;

        try {
            for (int i = 0; i < list.size(); i++) {
                String[] getnum = (String[]) list.get(i);
                conn.setAutoCommit(false);
                System.out.println("getnum[2]="+getnum[2]);
                pstmt = conn.prepareStatement("insert into en_city(id,ProvID,CityName,orderid) values(?,?,?,?)");
                pstmt.setInt(1,Integer.parseInt(getnum[0]));
                pstmt.setInt(2,Integer.parseInt(getnum[1]));
                pstmt.setString(3,getnum[2]);
                pstmt.setInt(4,Integer.parseInt(getnum[3]));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            //conn.commit();

        }
        catch (Exception e) {
            flag = 1;
            e.printStackTrace();
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    private static Connection createConnection(String ip, String username, String password, int flag) {
        Connection conn = null;
        String dbip = "";
        String dbusername = "";
        String dbpassword = "";

        try {
            dbip = ip;
            dbusername = username;
            dbpassword = password;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (flag == 0) {
                Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:" + dbip + ":1433", dbusername, dbpassword);
            } else if (flag == 1) {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbip + ":1521:orcl10g", dbusername, dbpassword);
            } else if (flag == 2) {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms?useUnicode=true&characterEncoding=GBK", dbusername, dbpassword);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return conn;
    }

}