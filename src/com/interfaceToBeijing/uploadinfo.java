package com.interfaceToBeijing;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bizwink.cms.news.Article;
import com.bizwink.cms.news.Column;
import com.bizwink.cms.server.FileProps;
import com.bizwink.util.CommUtil;
import com.bizwink.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ucap.repository.utils.DESUtil;
import com.ucap.repository.utils.HttpUtil;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class uploadinfo {


    private static String getLibsByAppId(){
        String url = "http://" + Constants.ip + ":" + Constants.port + "/getLibsByAppId";
        String post="";
        try {
            //String url = "http://" + Constants.ip + ":" + Constants.port + "/getLibsByAppId";
            System.out.println(url);
            JSONObject json = new JSONObject();
            json.put("appId", Constants.appId);
            String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", Constants.appId);
            map.put("data",encrypt);
            post= HttpUtil.doPost(url, map);
            System.out.println(post);
        } catch (Exception exp) {

        }
        return post;
    }


    //新增目录
    private static String createDir(){
       System.out.println("新增目录");
       String post="";
       Connection conn = null;
       PreparedStatement pstmt;
       ResultSet rs;
       Column column;
       List<Column> list = new ArrayList<>();
       try{
           try {
               try {
                   //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                   Class.forName("oracle.jdbc.driver.OracleDriver");
               } catch (ClassNotFoundException e) {
                   System.out.println("数据库连接异常");
                   e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
               }

               FileProps props = new FileProps("db.properties");
               //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
               String sqlurl = props.getProperty("ora_url");
               String UserName = props.getProperty("ora_username");
               String Password = props.getProperty("ora_password");
               conn = DriverManager.getConnection(sqlurl, UserName, Password);
               System.out.println("Connection Successful!");
               conn.setAutoCommit(false);

             //  pstmt = conn.prepareStatement("select TC.id,TC.PARENTID,tc.ORDERID,tc.CNAME,TC.ENAME from tbl_column tc where tc.id=?"); //测试
               //pstmt = conn.prepareStatement("select TC.id,TC.PARENTID,tc.ORDERID,tc.CNAME,TC.ENAME from tbl_column tc"); //正式
               pstmt = conn.prepareStatement("select t.* from tbl_column t where siteid =40 and t.dirname not like '%xxgk%' and t.id not in(3,5,4332) and t.parentid not in(3,5)  order by t.parentid"); //全部目录
             //  pstmt = conn.prepareStatement("select t.* from tbl_column t where siteid =40 and  t.id in(13,14,15,16) ");
             //  pstmt = conn.prepareStatement("select t.* from tbl_column t where siteid =40 and t.id=2723");
              // pstmt = conn.prepareStatement("select t.* from tbl_column t where siteid =40 and t.parentid=2001");
              // pstmt.setInt(1,columnid);
               rs = pstmt.executeQuery();
               while (rs.next()) {
                   column = new Column();
                   column.setID(rs.getInt("id"));
                   column.setParentID(rs.getInt("PARENTID"));
                   column.setOrderID(rs.getInt("ORDERID"));
                   column.setCName(rs.getString("CNAME"));
                   column.setEName(rs.getString("ENAME"));
                   list.add(column);
               }
               rs.close();
               pstmt.close();
               conn.commit();
           }catch (SQLException e) {
               e.printStackTrace();
           }finally {
               if(conn!=null){
                   try {
                       conn.close();
                   } catch (SQLException e) {
                       e.printStackTrace();
                   }
               }
           }

           for(int i=0;i<list.size();i++) {
               Column column1 = (Column) list.get(i);
               String cname = column1.getCName();
               System.out.println(cname);
               String ename = column1.getEName();
               int isOrig=0;
               int parentid = column1.getParentID();


               int orderid = column1.getOrderID();
               JSONObject json = new JSONObject();
               json.put("appId", Constants.appId);
               json.put("dirName", cname);//目录名称
               json.put("dirCode", ename);//目录编码
               json.put("seqNum", orderid);//排序号
               json.put("dirType", "");//目录类型
               if(parentid > 0){
                   json.put("dirPid", Constants.appId+"_"+parentid);//父目录id，空表示为根目录
               }
               json.put("libId", Constants.libId);//目录所属资源库ID
               json.put("origId", Constants.appId+"_"+column1.getID());//目录所属资源库ID
               json.put("isOrig", 1);
               json.put("memo", "");//目录描述
               String url = "http://"+Constants.ip+":"+ Constants.port+"/createDir";
               Map<String, String> map = new HashMap<String, String>();
               map.put("appId", Constants.appId);//服务ID
               map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
               post = HttpUtil.doPost(url, map);
               System.out.println(post);
           }
        } catch (Exception exp) {

        }
        return post;
    }
    //修改目录
    private static String updateDir(int columnid){
          String post="";
          Connection conn = null;
          PreparedStatement pstmt;
          ResultSet rs;
          Column column;
          List<Column> list = new ArrayList<>();
          try{
              try {
                  try {
                      //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                      Class.forName("oracle.jdbc.driver.OracleDriver");
                  } catch (ClassNotFoundException e) {
                      System.out.println("数据库连接异常");
                      e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                  }
                  FileProps props = new FileProps("db.properties");
                  //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
                  String sqlurl = props.getProperty("ora_url");
                  String UserName = props.getProperty("ora_username");
                  String Password = props.getProperty("ora_password");
                  conn = DriverManager.getConnection(sqlurl, UserName, Password);
                  System.out.println("Connection Successful!");
                  conn.setAutoCommit(false);
                  pstmt = conn.prepareStatement("select TC.id,TC.PARENTID,tc.ORDERID,tc.CNAME,TC.ENAME from tbl_column tc where tc.id=?"); //测试
                  //pstmt = conn.prepareStatement("select TC.id,TC.PARENTID,tc.ORDERID,tc.CNAME,TC.ENAME from tbl_column tc"); //正式
                  pstmt.setInt(1,columnid);
                  rs = pstmt.executeQuery();
                  while (rs.next()) {
                      column = new Column();
                      column.setID(rs.getInt("id"));
                      column.setParentID(rs.getInt("PARENTID"));
                      column.setOrderID(rs.getInt("ORDERID"));
                      column.setCName(rs.getString("CNAME"));
                      column.setEName(rs.getString("ENAME"));
                      list.add(column);
                  }
                  rs.close();
                  pstmt.close();
                  conn.commit();
              }catch (SQLException e) {
                  e.printStackTrace();
              }finally {
                  if(conn!=null){
                      try {
                          conn.close();
                      } catch (SQLException e) {
                          e.printStackTrace();
                      }
                  }
              }
              for(int i=0;i<list.size();i++) {
                  Column column1 = (Column) list.get(i);
                  String cname = column1.getCName();
                  //System.out.println(cname);
                  String ename = column1.getEName();

                  JSONObject json = new JSONObject();
                  json.put("isOrig", "1");
                  json.put("dirId", Constants.appId+"_"+columnid);
                  json.put("dirName", cname);
                  json.put("isShare", "0");
                  String url = "http://" + Constants.ip + ":" + Constants.port + "/updateDir";
                  Map<String, String> map = new HashMap<String, String>();
                  map.put("appId", Constants.appId);//服务ID
                  map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
                  post = HttpUtil.doPost(url, map);
                  System.out.println(post);
              }
                } catch (Exception exp) {

                }
            return post;
    }
    //删除目录
    private static String moveDirs(int columnid){
        System.out.println("删除目录"+columnid);
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("数据库连接异常");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            FileProps props = new FileProps("db.properties");
            //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
            String sqlurl = props.getProperty("ora_url");
            String UserName = props.getProperty("ora_username");
            String Password = props.getProperty("ora_password");
            conn = DriverManager.getConnection(sqlurl, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("select m.id from tbl_column m start with m.id=? connect by m.parentid=prior m.id  order by m.id desc");
            pstmt.setInt(1,columnid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                list.add(id);
            }
            rs.close();
            pstmt.close();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for(int i=0;i<list.size();i++) {
            int columnid_s = (int) list.get(i);
            String dirId=Constants.appId + "_" + columnid_s;
            System.out.println("删除目录"+dirId);
            String url = "http://" + Constants.ip + ":" + Constants.port + "/delDirs";
            String post = "";
            try {
                JSONObject json = new JSONObject();
                json.put("isOrig", "1");
                json.put("dirIds", dirId);
                Map<String, String> map = new HashMap<String, String>();
                map.put("appId", Constants.appId);//服务ID
                map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
                post = HttpUtil.doPost(url, map);
                System.out.println(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    //根据目录ID 查询目录
    private static String getDirById(int columnid){
        System.out.println("查询目录："+columnid);
        String get="";
        try {
            JSONObject json = new JSONObject();
            json.put("isOrig", "1");
            json.put("dirId", Constants.appId+"_"+columnid);
            String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
            String url = "http://"+Constants.ip+":"+ Constants.port+"/getDirById?data=" + encrypt + "&appId="+Constants.appId;
            get = HttpUtil.doGet(url, "UTF-8");
            System.out.println(get);
        }catch (Exception e){

        }
        return get;
    }

    private static String getResByDirId(int columnid){
        System.out.println("根据目录ID查询所有资源："+columnid);
        String get="";
        try {
            JSONObject json = new JSONObject();
            json.put("pageIndex", "1");
            json.put("pageSize", "20");
            json.put("isOrig", "1");
            json.put("dirId", Constants.appId+"_"+columnid);
            String encrypt = DESUtil.encrypt(json.toJSONString(),  Constants.keyCode);
            String url = "http://"+Constants.ip+":"+ Constants.port+"/getResByDirId?data=" + encrypt + "&appId="+Constants.appId;
            get = HttpUtil.doGet(url, "UTF-8");
            System.out.println(get);
        }catch (Exception e){

        }
        return get;
    }



    //新增资源
    private static String createRes(int columnid){
        System.out.println("新增资源："+ columnid);
        int resTranMode = 0;
        String post="";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
       // Column column;
        Article article;
        List<Article> list = new ArrayList<>();
        //String fileURL="group1/M00/1C/F5/oYYBAF3wnm-AD9ywAABnEta5cqo960.zip";

            try {
                try {
                    //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    System.out.println("数据库连接异常");
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                FileProps props = new FileProps("db.properties");
                //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
                String sqlurl = props.getProperty("ora_url");
                String UserName = props.getProperty("ora_username");
                String Password = props.getProperty("ora_password");
                conn = DriverManager.getConnection(sqlurl, UserName, Password);
                System.out.println("Connection Successful!");
                conn.setAutoCommit(false);
                //pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag from TBL_ARTICLE t where t.id=39091 and t.status=1 and t.pubflag=0"); //测试
                pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag  from TBL_ARTICLE t where t.columnid=? and status=1 and pubflag=0 order by id"); //正式
                pstmt.setInt(1, columnid);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    article = new Article();
                    article.setID(rs.getInt("id"));
                    article.setMainTitle(rs.getString("maintitle"));
                    article.setSummary(rs.getString("summary"));
                    article.setAuthor(rs.getString("author"));
                    article.setSource(rs.getString("source"));
                    article.setContent(rs.getString("content"));
                    article.setDirName(rs.getString("dirname"));
                    article.setFileName(rs.getString("filename"));
                    article.setArticlepic(rs.getString("articlepic"));
                    article.setStatus(rs.getInt("status"));
                    article.setPubFlag(rs.getInt("pubflag"));
                    article.setCreateDate(rs.getTimestamp("createdate"));
                    article.setColumnID(rs.getInt("columnid"));
                    list.add(article);
                }
                rs.close();
                pstmt.close();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

            for (int i = 0; i < list.size(); i++) {
                Article article1 = (Article)list.get(i);
                String dirname = article1.getDirName();
                String filename = article1.getFileName();
                String articlepic = article1.getArticlepic(); //领导照片
                String statusStr="";
                int status = article1.getStatus();
                int pubflag = article1.getPubFlag();
                if(status==1&&pubflag==0){
                    statusStr = "3"; //正式发布

                }
                if(status==1&&pubflag==1){
                    statusStr = "1"; //草搞

                }
                if(status==2&&pubflag==1){
                    statusStr = "2";  //归档  待审核

                }
                if(status==0&&pubflag==1){
                    statusStr = "2";   //未用  待审核

                }
                String createdate = CommUtil.timestamp2string(article1.getCreateDate(), "yyyyMMdd");
                String online="http://www.bjsjs.gov.cn"+dirname+createdate+"/"+article1.getID()+".shtml";
                System.out.println("online:"+online);

                JSONObject json = new JSONObject();
                json.put("origId",Constants.appId+"_"+article1.getID());
                json.put("name", article1.getMainTitle());  //名称
                json.put("resTranMode","0"); //json.put("resTranMode", "2");
                json.put("resTitle", article1.getMainTitle()); //标题
                json.put("resourceClassify", "6");
                json.put("abstracts", article1.getSummary()); //摘要
                json.put("source", "石景山信息网");  //信息资源提供方 原字段source
                json.put("author", article1.getAuthor());//作者
                json.put("keyword", article1.getKeyword());//关键字
                json.put("isOrig", "1");
                json.put("dirId", Constants.appId+"_"+ article1.getColumnID());
                json.put("status", statusStr);
                json.put("online", online);
                //增加扩展属性
                if(article1.getColumnID()==13||article1.getColumnID()==14||article1.getColumnID()==15||article1.getColumnID()==16){//领导简历
                    List ExList = new ArrayList();
                    try {
                        try {
                            //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                            Class.forName("oracle.jdbc.driver.OracleDriver");
                        } catch (ClassNotFoundException e) {
                            System.out.println("数据库连接异常");
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                        FileProps props = new FileProps("db.properties");
                        //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
                        String sqlurl = props.getProperty("ora_url");
                        String UserName = props.getProperty("ora_username");
                        String Password = props.getProperty("ora_password");
                        conn = DriverManager.getConnection(sqlurl, UserName, Password);
                        System.out.println("Connection Successful!");
                        conn.setAutoCommit(false);
                        pstmt = conn.prepareStatement("select ta.ename,ta.stringvalue from tbl_article_extendattr ta where ta.articleid=?"); //测试
                        pstmt.setInt(1, article1.getID());
                        rs = pstmt.executeQuery();
                        Article articleEx;

                        while (rs.next()) {
                            articleEx = new Article();
                            articleEx.setEname(rs.getString("ename"));
                            articleEx.setSource(rs.getString("stringvalue"));
                            ExList.add(articleEx);
                        }
                        rs.close();
                        pstmt.close();
                        conn.commit();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    String _minzu="";
                    String _chusheng="";
                    String _jiguan="";
                    String _gongzuo="";
                    String _wenhua="";
                    String _zhiwu="";
                    String _lvli="";
                    String _fengong="";
                    for(int j=0; j<ExList.size();j++ ){
                        Article articleEx1 = (Article)ExList.get(j);
                        if(articleEx1.getEname().equals("_minzu"))
                            _minzu = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_chusheng"))
                            _chusheng = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_jiguan"))
                            _jiguan = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_gongzuo"))
                            _gongzuo = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_wenhua"))
                            _wenhua = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_zhiwu"))
                            _zhiwu = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_lvli"))
                            _lvli = articleEx1.getSource();
                        if(articleEx1.getEname().equals("_fengong"))
                            _fengong = articleEx1.getSource();
                    }
                    System.out.println(_minzu);
                    System.out.println(_chusheng);
                    System.out.println(_jiguan);
                    System.out.println(_wenhua);
                    System.out.println(_zhiwu);
                    System.out.println(_lvli);
                    System.out.println(_fengong);

                    String fileURL=uploadFileimage(dirname, "images\\", article1.getArticlepic());
                    System.out.println("fileURL="+fileURL);
                    json.put("LDJL@@SJSLDXM", article1.getMainTitle());
                    json.put("LDJL@@SJSLDZP",fileURL);
                    json.put("LDJL@@SJSXB","");        //性别
                    json.put("LDJL@@SJSMZ", _minzu);   //民族
                    json.put("LDJL@@SJSCSNY", _chusheng); //出生年月
                    json.put("LDJL@@SJSJG", _jiguan);     //籍贯
                    json.put("LDJL@@SJSRDSJ","");     //入党时间
                    json.put("LDJL@@SJSCJGZSJ", _gongzuo); //参加工作时间
                    json.put("LDJL@@SJSBYYX","");   //毕业院校
                    json.put("LDJL@@SJSSXZY","");  //所学专业
                    json.put("LDJL@@SJSXL",_wenhua);    //学历
                    json.put("LDJL@@SJSXW",_wenhua);    //学位
                    json.put("LDJL@@SJSZC","");    //职称
                    json.put("LDJL@@SJSZWC", _zhiwu);   //职务
                    json.put("LDJL@@SJSGZJL",_lvli);  //工作经历
                    json.put("LDJL@@SJSGZFG",_fengong);  //工作分工
                    json.put("LDJL@@SJSZZMM", "");  //政治面貌
                    json.put("LDJL@@SJSZZJG", "");   //工作机构

                }



                if(filename!=null){   //纯文件资源
                    json.put("resTranMode", "1");
                    dirname = dirname.replace("/","\\");
                    String fileurl="E:\\sjscms\\sites\\www_bjsjs_gov_cn"+dirname+createdate+"\\download\\"+filename;
                    System.out.println("fileurl="+fileurl);
                    try {
                        File file = new File(fileurl);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
                        String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes?appId=" + Constants.appId + "&data=" + encrypt;
                        post= HttpUtil.uploadFile(url, fileInputStream, filename);
                    } catch (Exception e) {

                    }
                }else{
                    String content="";
                    if(article1.getContent()!=null)
                        content = FindmatchStr(article1.getContent());
                    json.put("content", content);//json.put("content", "http://www.gov.cn/premier/2018-08/22/content_5315707.htm");
                    json.put("resTranMode", "0"); //json.put("resTranMode", "2 ");
                    if(article1.getArticlepic() !=null){
                        String fileURL=uploadFileimage(dirname, "images\\", article1.getArticlepic());
                        JSONArray jsonArray = new JSONArray();
                        JSONObject fpCon = new JSONObject();
                        fpCon.put("name", "特效图片");
                        fpCon.put("path", fileURL);
                        jsonArray.add(fpCon);
                        json.put("attachments", jsonArray.toJSONString());//附件

                    }

                    try {
                        String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes";
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("appId", Constants.appId);
                        map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
                        post = HttpUtil.doPost(url, map);
                    } catch (Exception e) {

                    }
                }
                System.out.println(post);
            }
        return post;
    }

    //修改资源
    private static String updateRes(int columnid){
        System.out.println("修改资源:"+columnid);
        int resTranMode = 0;
        String post="";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        // Column column;
        Article article;
        List<Article> list = new ArrayList<>();
        // String fileURL="group1/M00/1C/F5/oYYBAF3wnm-AD9ywAABnEta5cqo960.zip";

        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("数据库连接异常");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            FileProps props = new FileProps("db.properties");
            //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
            String sqlurl = props.getProperty("ora_url");
            String UserName = props.getProperty("ora_username");
            String Password = props.getProperty("ora_password");
            conn = DriverManager.getConnection(sqlurl, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            if (columnid == 201) {
                pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag,t.mediafile  from TBL_ARTICLE t where t.columnid=? and id <=38361 and status=1 and pubflag=0 order by id"); //正式
            } else{
                //pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag  from TBL_ARTICLE t where  t.id=? and status=1 and pubflag=0"); //测试
                pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag,t.mediafile  from TBL_ARTICLE t where t.columnid=? and status=1 and pubflag=0 order by id"); //正式
             }
            pstmt.setInt(1, columnid);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setSummary(rs.getString("summary"));
                article.setAuthor(rs.getString("author"));
                article.setSource(rs.getString("source"));
                article.setContent(rs.getString("content"));
                article.setDirName(rs.getString("dirname"));
                article.setFileName(rs.getString("filename"));
                article.setArticlepic(rs.getString("articlepic"));
                article.setStatus(rs.getInt("status"));
                article.setPubFlag(rs.getInt("pubflag"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setColumnID(rs.getInt("columnid"));
                article.setMediafile(rs.getString("mediafile"));
                list.add(article);
            }
            rs.close();
            pstmt.close();
            conn.commit();

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        for(int i=0;i<list.size();i++) {
            Article article1= (Article)list.get(i);
            String dirname = article1.getDirName();
            String filename = article1.getFileName();
            //String articlepic = article.getArticlepic();
            String mediafile = article1.getMediafile();
            String statusStr = "";
            int status = article1.getStatus();
            int pubflag = article1.getPubFlag();
            if (status == 1 && pubflag == 0) {
                statusStr = "3"; //正式发布

            }
            if (status == 1 && pubflag == 1) {
                statusStr = "1"; //草搞

            }
            if (status == 2 && pubflag == 1) {
                statusStr = "9";  //归档

            }
            if (status == 0 && pubflag == 1) {
                statusStr = "9";   //未用

            }
            //http://www.bjsjs.gov.cn/zwgk/zt/shuanggong/shenji/20170123/5630727.shtml
            System.out.println("修改资源：id="+article1.getID()+"   columnid="+columnid);
            String createdate = CommUtil.timestamp2string(article1.getCreateDate(), "yyyyMMdd");
            String online = "http://www.bjsjs.gov.cn" + dirname + createdate + "/" + article1.getID() + ".shtml";
            //System.out.println("online:"+online);

            JSONObject json = new JSONObject();
            json.put("name", article1.getMainTitle());
            json.put("resTitle", article1.getMainTitle());
            json.put("resourceClassify", "6");
            json.put("abstracts", article1.getSummary());
            json.put("source", "石景山信息网");
            json.put("author", article1.getAuthor());
            json.put("keyword", article1.getKeyword());//项目属性
            json.put("isOrig", "1");
            json.put("resId", Constants.appId + "_" + article1.getID());
            json.put("dirId", Constants.appId + "_" + article1.getColumnID());
            json.put("status", statusStr);
            json.put("online", online);

            if (article1.getColumnID() == 13 || article1.getColumnID() == 14 || article1.getColumnID() == 15 || article1.getColumnID() == 16) {//领导简历
                List ExList = new ArrayList();
                try {
                    try {
                        //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                    } catch (ClassNotFoundException e) {
                        System.out.println("数据库连接异常");
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    FileProps props = new FileProps("db.properties");
                    //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
                    String sqlurl = props.getProperty("ora_url");
                    String UserName = props.getProperty("ora_username");
                    String Password = props.getProperty("ora_password");
                    conn = DriverManager.getConnection(sqlurl, UserName, Password);
                    System.out.println("Connection Successful!");
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement("select ta.ename,ta.stringvalue from tbl_article_extendattr ta where ta.articleid=?"); //测试
                    pstmt.setInt(1, article1.getID());
                    rs = pstmt.executeQuery();
                    Article articleEx;

                    while (rs.next()) {
                        articleEx = new Article();
                        articleEx.setEname(rs.getString("ename"));
                        articleEx.setSource(rs.getString("stringvalue"));
                        ExList.add(articleEx);
                    }
                    rs.close();
                    pstmt.close();
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String _minzu = "";
                String _chusheng = "";
                String _jiguan = "";
                String _gongzuo = "";
                String _wenhua = "";
                String _zhiwu = "";
                String _lvli = "";
                String _fengong = "";
                for (int j = 0; j < ExList.size(); j++) {
                    Article articleEx1 = (Article) ExList.get(j);
                    if (articleEx1.getEname().equals("_minzu"))
                        _minzu = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_chusheng"))
                        _chusheng = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_jiguan"))
                        _jiguan = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_gongzuo"))
                        _gongzuo = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_wenhua"))
                        _wenhua = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_zhiwu"))
                        _zhiwu = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_lvli"))
                        _lvli = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_fengong"))
                        _fengong = articleEx1.getSource();
                }
                System.out.println(_minzu);
                System.out.println(_chusheng);
                System.out.println(_jiguan);
                System.out.println(_wenhua);
                System.out.println(_zhiwu);
                System.out.println(_lvli);
                System.out.println(_fengong);

                String fileURL = uploadFileimage(dirname, "images\\", article1.getArticlepic());
                System.out.println("fileURL=" + fileURL);
                json.put("LDJL@@SJSLDXM", article1.getMainTitle());
                json.put("LDJL@@SJSLDZP", fileURL);
                json.put("LDJL@@SJSXB", "");        //性别
                json.put("LDJL@@SJSMZ", _minzu);   //民族
                json.put("LDJL@@SJSCSNY", _chusheng); //出生年月
                json.put("LDJL@@SJSJG", _jiguan);     //籍贯
                json.put("LDJL@@SJSRDSJ", "");     //入党时间
                json.put("LDJL@@SJSCJGZSJ", _gongzuo); //参加工作时间
                json.put("LDJL@@SJSBYYX", "");   //毕业院校
                json.put("LDJL@@SJSSXZY", "");  //所学专业
                json.put("LDJL@@SJSXL", _wenhua);    //学历
                json.put("LDJL@@SJSXW", _wenhua);    //学位
                json.put("LDJL@@SJSZC", "");    //职称
                json.put("LDJL@@SJSZWC", _zhiwu);   //职务
                json.put("LDJL@@SJSGZJL", _lvli);  //工作经历
                json.put("LDJL@@SJSGZFG", _fengong);  //工作分工
                json.put("LDJL@@SJSZZMM", "");  //政治面貌
                json.put("LDJL@@SJSZZJG", "");   //工作机构

            }

            if (filename != null) {   //纯文件资源
                json.put("resTranMode", "1");
                dirname = dirname.replace("/", "\\");
                try {
                    File file = new File("E:\\sjscms\\sites\\www_bjsjs_gov_cn" + dirname + createdate + "\\download\\" + filename);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
                    String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes?appId=" + Constants.appId + "&data=" + encrypt;
                    post = HttpUtil.uploadFile(url, fileInputStream, filename);
                } catch (Exception e) {

                }
            } else {
                String content="";
                if(article1.getContent()!=null)
                    content = FindmatchStr(article1.getContent());
                //System.out.println(content);
                json.put("content", content);//json.put("content", "http://www.gov.cn/premier/2018-08/22/content_5315707.htm");
                json.put("resTranMode", "0");
                json.put("content", content);//json.put("content", "http://www.gov.cn/premier/2018-08/22/content_5315707.htm");
                json.put("resTranMode", "0"); //json.put("resTranMode", "2 ");
                if (article1.getArticlepic() != null) {
                    String fileURL = uploadFileimage(dirname, "images\\", article1.getArticlepic());
                    JSONArray jsonArray = new JSONArray();
                    JSONObject fpCon = new JSONObject();
                    fpCon.put("name", "特效图片");
                    fpCon.put("path", fileURL);
                    jsonArray.add(fpCon);
                    json.put("attachments", jsonArray.toJSONString());//附件

                }
                if (mediafile != null) {
                    String fileURL = uploadFileimage(dirname, "images\\", mediafile);
                    JSONArray jsonArray = new JSONArray();
                    JSONObject fpCon = new JSONObject();
                    fpCon.put("name", "视频文件");
                    fpCon.put("path", fileURL);
                    jsonArray.add(fpCon);
                    json.put("attachments", jsonArray.toJSONString());//附件

                }
                try {
                    String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes";
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("appId", Constants.appId);
                    map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
                    post = HttpUtil.doPost(url, map);
                } catch (Exception e) {

                }
            }

            System.out.println(post);
        }
        return post;
    }

    private static String updateResById(int id,int columnid){
        System.out.println("修改资源:"+columnid);
        int resTranMode = 0;
        String post="";
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        // Column column;
        Article article;
        List<Article> list = new ArrayList<>();
       // String fileURL="group1/M00/1C/F5/oYYBAF3wnm-AD9ywAABnEta5cqo960.zip";

        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("数据库连接异常");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            FileProps props = new FileProps("db.properties");
            //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
            String sqlurl = props.getProperty("ora_url");
            String UserName = props.getProperty("ora_username");
            String Password = props.getProperty("ora_password");
            conn = DriverManager.getConnection(sqlurl, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);
            //pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag  from TBL_ARTICLE t where  t.id=? and status=1 and pubflag=0"); //测试
            pstmt = conn.prepareStatement("select t.id,t.columnid,t.maintitle,t.summary,t.keyword,t.source,t.dirname,t.articlepic,t.author,t.filename,t.content,t.createdate,t.status,t.pubflag  from TBL_ARTICLE t where t.id=? and t.columnid=? and status=1 and pubflag=0 order by id"); //正式
            pstmt.setInt(1, id);
            pstmt.setInt(2, columnid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                article = new Article();
                article.setID(rs.getInt("id"));
                article.setMainTitle(rs.getString("maintitle"));
                article.setSummary(rs.getString("summary"));
                article.setAuthor(rs.getString("author"));
                article.setSource(rs.getString("source"));
                article.setContent(rs.getString("content"));
                article.setDirName(rs.getString("dirname"));
                article.setFileName(rs.getString("filename"));
                article.setArticlepic(rs.getString("articlepic"));
                article.setStatus(rs.getInt("status"));
                article.setPubFlag(rs.getInt("pubflag"));
                article.setCreateDate(rs.getTimestamp("createdate"));
                article.setColumnID(rs.getInt("columnid"));
                list.add(article);
            }
            rs.close();
            pstmt.close();
            conn.commit();

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error in closing the pooled connection " + e.toString());
                }
            }
        }

        for(int i=0;i<list.size();i++) {
            Article article1= (Article)list.get(i);
            String dirname = article1.getDirName();
            String filename = article1.getFileName();
            //String articlepic = article.getArticlepic();
            String statusStr = "";
            int status = article1.getStatus();
            int pubflag = article1.getPubFlag();
            if (status == 1 && pubflag == 0) {
                statusStr = "3"; //正式发布

            }
            if (status == 1 && pubflag == 1) {
                statusStr = "1"; //草搞

            }
            if (status == 2 && pubflag == 1) {
                statusStr = "9";  //归档

            }
            if (status == 0 && pubflag == 1) {
                statusStr = "9";   //未用

            }
            //http://www.bjsjs.gov.cn/zwgk/zt/shuanggong/shenji/20170123/5630727.shtml
            System.out.println("id="+article1.getID()+"columnid="+columnid);
            String createdate = CommUtil.timestamp2string(article1.getCreateDate(), "yyyyMMdd");
            String online = "http://www.bjsjs.gov.cn" + dirname + createdate + "/" + article1.getID() + ".shtml";
            //System.out.println("online:"+online);

            JSONObject json = new JSONObject();
            json.put("name", article1.getMainTitle());
            json.put("resTitle", article1.getMainTitle());
            json.put("resourceClassify", "6");
            json.put("abstracts", article1.getSummary());
            json.put("source", "石景山信息网");
            json.put("author", article1.getAuthor());
            json.put("keyword", article1.getKeyword());//项目属性
            json.put("isOrig", "1");
            json.put("resId", Constants.appId + "_" + article1.getID());
            json.put("dirId", Constants.appId + "_" + article1.getColumnID());
            json.put("status", statusStr);
            json.put("online", online);

            if (article1.getColumnID() == 13 || article1.getColumnID() == 14 || article1.getColumnID() == 15 || article1.getColumnID() == 16) {//领导简历
                List ExList = new ArrayList();
                try {
                    try {
                        //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                        Class.forName("oracle.jdbc.driver.OracleDriver");
                    } catch (ClassNotFoundException e) {
                        System.out.println("数据库连接异常");
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    FileProps props = new FileProps("db.properties");
                    //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
                    String sqlurl = props.getProperty("ora_url");
                    String UserName = props.getProperty("ora_username");
                    String Password = props.getProperty("ora_password");
                    conn = DriverManager.getConnection(sqlurl, UserName, Password);
                    System.out.println("Connection Successful!");
                    conn.setAutoCommit(false);
                    pstmt = conn.prepareStatement("select ta.ename,ta.stringvalue from tbl_article_extendattr ta where ta.articleid=?"); //测试
                    pstmt.setInt(1, article1.getID());
                    rs = pstmt.executeQuery();
                    Article articleEx;

                    while (rs.next()) {
                        articleEx = new Article();
                        articleEx.setEname(rs.getString("ename"));
                        articleEx.setSource(rs.getString("stringvalue"));
                        ExList.add(articleEx);
                    }
                    rs.close();
                    pstmt.close();
                    conn.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
                String _minzu = "";
                String _chusheng = "";
                String _jiguan = "";
                String _gongzuo = "";
                String _wenhua = "";
                String _zhiwu = "";
                String _lvli = "";
                String _fengong = "";
                for (int j = 0; j < ExList.size(); j++) {
                    Article articleEx1 = (Article) ExList.get(j);
                    if (articleEx1.getEname().equals("_minzu"))
                        _minzu = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_chusheng"))
                        _chusheng = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_jiguan"))
                        _jiguan = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_gongzuo"))
                        _gongzuo = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_wenhua"))
                        _wenhua = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_zhiwu"))
                        _zhiwu = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_lvli"))
                        _lvli = articleEx1.getSource();
                    if (articleEx1.getEname().equals("_fengong"))
                        _fengong = articleEx1.getSource();
                }
                System.out.println(_minzu);
                System.out.println(_chusheng);
                System.out.println(_jiguan);
                System.out.println(_wenhua);
                System.out.println(_zhiwu);
                System.out.println(_lvli);
                System.out.println(_fengong);

                String fileURL = uploadFileimage(dirname, "images\\", article1.getArticlepic());
                System.out.println("fileURL=" + fileURL);
                json.put("LDJL@@SJSLDXM", article1.getMainTitle());
                json.put("LDJL@@SJSLDZP", fileURL);
                json.put("LDJL@@SJSXB", "");        //性别
                json.put("LDJL@@SJSMZ", _minzu);   //民族
                json.put("LDJL@@SJSCSNY", _chusheng); //出生年月
                json.put("LDJL@@SJSJG", _jiguan);     //籍贯
                json.put("LDJL@@SJSRDSJ", "");     //入党时间
                json.put("LDJL@@SJSCJGZSJ", _gongzuo); //参加工作时间
                json.put("LDJL@@SJSBYYX", "");   //毕业院校
                json.put("LDJL@@SJSSXZY", "");  //所学专业
                json.put("LDJL@@SJSXL", _wenhua);    //学历
                json.put("LDJL@@SJSXW", _wenhua);    //学位
                json.put("LDJL@@SJSZC", "");    //职称
                json.put("LDJL@@SJSZWC", _zhiwu);   //职务
                json.put("LDJL@@SJSGZJL", _lvli);  //工作经历
                json.put("LDJL@@SJSGZFG", _fengong);  //工作分工
                json.put("LDJL@@SJSZZMM", "");  //政治面貌
                json.put("LDJL@@SJSZZJG", "");   //工作机构

            }

            if (filename != null) {   //纯文件资源
                json.put("resTranMode", "1");
                dirname = dirname.replace("/", "\\");
                try {
                    File file = new File("E:\\sjscms\\sites\\www_bjsjs_gov_cn" + dirname + createdate + "\\download\\" + filename);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
                    String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes?appId=" + Constants.appId + "&data=" + encrypt;
                    post = HttpUtil.uploadFile(url, fileInputStream, filename);
                } catch (Exception e) {

                }
            } else {
                String content="";
                if(article1.getContent()!=null)
                      content = FindmatchStr(article1.getContent());
                //System.out.println(content);
                json.put("content", content);//json.put("content", "http://www.gov.cn/premier/2018-08/22/content_5315707.htm");
                json.put("resTranMode", "0");
                json.put("content", content);//json.put("content", "http://www.gov.cn/premier/2018-08/22/content_5315707.htm");
                json.put("resTranMode", "0"); //json.put("resTranMode", "2 ");
                if (article1.getArticlepic() != null) {
                    String fileURL = uploadFileimage(dirname, "images\\", article1.getArticlepic());
                    JSONArray jsonArray = new JSONArray();
                    JSONObject fpCon = new JSONObject();
                    fpCon.put("name", "特效图片");
                    fpCon.put("path", fileURL);
                    jsonArray.add(fpCon);
                    json.put("attachments", jsonArray.toJSONString());//附件

                }
                try {
                    String url = "http://" + Constants.ip + ":" + Constants.port + "/createRes";
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("appId", Constants.appId);
                    map.put("data", DESUtil.encrypt(json.toJSONString(), Constants.keyCode));
                    post = HttpUtil.doPost(url, map);
                } catch (Exception e) {

                }
            }

            System.out.println(post);
        }
        return post;
    }




    //删除资源
    private static String delRes(int columnid){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("数据库连接异常");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            FileProps props = new FileProps("db.properties");
            //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
            String sqlurl = props.getProperty("ora_url");
            String UserName = props.getProperty("ora_username");
            String Password = props.getProperty("ora_password");
            conn = DriverManager.getConnection(sqlurl, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("select ta.id id from tbl_article ta where columnid in (select m.id from tbl_column m start with m.id=? connect by m.parentid=prior m.id) ");
            pstmt.setInt(1,columnid);
            rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                list.add(id);
            }
            rs.close();
            pstmt.close();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

       for(int i=0;i<list.size();i++) {
           int id = (int) list.get(i);
           String resIds=Constants.appId + "_" + id;
           //String resIds = String.valueOf(id);
           System.out.println("删除资源:"+ i+ ":"+resIds);
           String post = "";
           try {
               JSONObject json = new JSONObject();
               json.put("appId", Constants.appId);
               json.put("isOrig", "1");
               json.put("resIds", resIds);
               String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
               String url = "http://" + Constants.ip + ":" + Constants.port + "/delRes?data=" + encrypt + "&appId=" + Constants.appId;
               post = HttpUtil.doGet(url, "UTF-8");
           } catch (Exception e) {

           }
           System.out.println(post);
       }
        return "";
    }

    private static void getColumnList(int flag) {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        List list = new ArrayList();
        try {
            try {
                //Class.forName("weblogic.jdbc.mssqlserver4.Driver");
                Class.forName("oracle.jdbc.driver.OracleDriver");
            } catch (ClassNotFoundException e) {
                System.out.println("数据库连接异常");
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            FileProps props = new FileProps("db.properties");
            //  FileProps props = new FileProps("/data/bucgdx/cms/WEB-INF/classes/com/bizwink/cms/server/config.properties");
            String sqlurl = props.getProperty("ora_url");
            String UserName = props.getProperty("ora_username");
            String Password = props.getProperty("ora_password");
            conn = DriverManager.getConnection(sqlurl, UserName, Password);
            System.out.println("Connection Successful!");
            conn.setAutoCommit(false);

            pstmt = conn.prepareStatement("select c,columnid from (select count(id) c ,columnid from tbl_article where columnid in (\n" +
                    "(select t.id from tbl_column t where siteid =40 and t.dirname not like '%xxgk%' and t.id not in(3,5,4332) and t.parentid not in(3,5,200))) and columnid <>999999 and columnid<>4101 \n" +
                    "and status=1 and pubflag=0 group by columnid \n" +
                    "order by count(id) ) where c > 433 and c<=600 ");
            rs = pstmt.executeQuery();
            while(rs.next()){
                int columnid = rs.getInt("columnid");
                list.add(columnid);
            }
            rs.close();
            pstmt.close();
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        for(int i=0;i<list.size();i++){
            int columnid = (int)list.get(i);
            if(flag==0) {
                createRes(columnid);
            }else{
                updateRes(columnid);
            }
        }

    }

    private static String uploadFile(String fileUrl,String filename){
        System.out.println("上传文件");
        //dirname = dirname.replace("/","\\");
        String fileurl="D:\\shouxin\\bjsjs_web\\bjsjs_xxw"+fileUrl;
        System.out.println(fileurl);

        //将json字符串转化成json对象
        String post="";
        String  fileURL="";
        try {
            JSONObject  json = new JSONObject();
          //  File file = new File("D:\\shouxin\\bjsjs_web\\bjsjs_xxw\\xxgk\\ztzl\\shuanggong\\shenji\\download\\石景山区审计局政处罚权力清单.zip");
            File file = new File(fileurl);
            if(!file.exists()) {
                System.out.println("文件不存在："+fileUrl);
            }else {
                FileInputStream fileInputStream = new FileInputStream(file);
                String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
                String url = "http://" + Constants.ip + ":" + Constants.port + "/uploadFile?appId=" + Constants.appId + "&data=" + encrypt;
                post = HttpUtil.uploadFile(url, fileInputStream, filename);
                System.out.println(post);
                JsonParser jp = new JsonParser();
                JsonObject jo = jp.parse(post).getAsJsonObject();
                String code = jo.get("code").getAsString();
                // System.out.println(code);

                if (code.equals("0")) {
                    fileURL = jo.get("data").getAsJsonObject().get("fileURL").getAsString();
                }
                System.out.println("fileURL：" + fileURL);
            }

        }catch (Exception e){

        }
       // System.out.println(fileURL);
        return fileURL;
    }

    private static String uploadFileimage(String dirname,String path,String filename){
        System.out.println("上传图片");
        //dirname = dirname.replace("/","\\");
        String fileurl="D:\\shouxin\\bjsjs_web\\bjsjs_xxw"+dirname+path+filename;
        System.out.println(fileurl);
        //将json字符串转化成json对象
        String post="";
        String  fileURL="";
        try {
            JSONObject  json = new JSONObject();
            //  File file = new File("D:\\shouxin\\bjsjs_web\\bjsjs_xxw\\xxgk\\ztzl\\shuanggong\\shenji\\download\\石景山区审计局政处罚权力清单.zip");
            File file = new File(fileurl);
            if(!file.exists()) {
                System.out.println("文件不存在："+fileurl);
            }else {
                FileInputStream fileInputStream = new FileInputStream(file);
                String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
                String url = "http://" + Constants.ip + ":" + Constants.port + "/uploadFile?appId=" + Constants.appId + "&data=" + encrypt;
                post = HttpUtil.uploadFile(url, fileInputStream,filename);
                System.out.println(post);
                JsonParser jp = new JsonParser();
                JsonObject jo = jp.parse(post).getAsJsonObject();
                String code = jo.get("code").getAsString();
                // System.out.println(code);

                if (code.equals("0")) {
                    fileURL = jo.get("data").getAsJsonObject().get("fileURL").getAsString();
                }
                System.out.println("fileURL：" + fileURL);
            }

        }catch (Exception e){

        }
        // System.out.println(fileURL);
        return fileURL;
    }


    private static String FindmatchStr(String content) {

        Pattern p = Pattern.compile("(\"[^\",]*\\.(gif|jpg|jpeg|png|doc|ppt|zip|rar|docx|pptx|pdf|xls|xlsx)\")|('[^',]*\\.(gif|jpg|jpeg|png|doc|ppt|zip|rar|docx|pptx|pdf|xls|xlsx)')", Pattern.CASE_INSENSITIVE);
        java.util.regex.Matcher matcher = null;
        String matchStr=null;
        matcher = p.matcher(content);
        String s_content = content;


        boolean existflag = false;
        String path="";
        String fileURL="";
        while (matcher.find()) {
            matchStr = content.substring(matcher.start(), matcher.end());
            String s_matchStr =""; //matchStr.substring(1, matchStr.lastIndexOf("\""));
            if (matchStr.lastIndexOf("\"") != -1) {
                s_matchStr = matchStr.substring(1, matchStr.lastIndexOf("\""));

                //System.out.println(matchStr);
                //System.out.println(matchStr.substring(1,matchStr.lastIndexOf("\"")));
                //matchStr="/webbuilder/sites/www_bjsjs_gov_cn/tzgg/images/pic38n6es27.jpg";
                if (s_matchStr.indexOf("/webbuilder/sites/www_bjsjs_gov_cn") != -1) {
                    s_matchStr = s_matchStr.replace("/webbuilder/sites/www_bjsjs_gov_cn", "");
                }
                if (s_matchStr.indexOf("http://www.bjsjs.gov.cn") != -1) {
                    s_matchStr = s_matchStr.replace("http://www.bjsjs.gov.cn", "");
                }
                if (s_matchStr.indexOf("http://") != -1) {
                    //s_matchStr = s_matchStr.replace("http://www.bjsjs.gov.cn","");
                }
                System.out.println(s_matchStr);
                String fileTyle = s_matchStr.substring(s_matchStr.lastIndexOf("/") + 1);
                System.out.println(fileTyle);
                fileURL = uploadFile(s_matchStr, fileTyle);
                String filePath = "\"http://192.141.1.10:7002/zuul/repo-web/manager/getManagerFile?filePath=" + fileURL + "\"";
                if (filePath != null)
                    s_content = StringUtil.replace(s_content, matchStr, filePath);
                existflag = true;
            }
        }
         return s_content;
    }


    public static void main(String args[]) {
       /* String post="{\"code\":0,\"msg\":\"鎿嶄綔鎴愬姛\",\"data\":{\"fileURL\":\"group1/M00/1C/F5/oYYBAF3wnm-AD9ywAABnEta5cqo960.zip\"}}";
        JsonParser jp = new JsonParser();
        JsonObject jo = jp.parse(post).getAsJsonObject();
        String code= jo.get("code").getAsString();
        if(code.equals("0")) {
            System.out.println(jo.get("data").getAsJsonObject().get("fileURL").getAsString());
        }*/

       // uploadFile();
        //测试链接
        //getLibsByAppId();
        //新增目录
        // createDir();
        //修改目录
        // updateDir(2921);
        //删除目录
       //  moveDirs(55);
       // moveDirs(573);
        //moveDirs(200);
        //根据目录ID 查询目录
        // getDirById(2001);

        //新增资源
         //getColumnList(0);
        // createRes(228);
        //修改资源
        //getColumnList(1);
         //updateResById(12792849,3150);
        // updateRes(201);
        //updateRes(3121);
        //删除资源
        //delRes(55);
       // delRes(573);

        //根据目录查询资源
        //getResByDirId(580);

        /*String content ="<html dir=\"ltr\">\n" +
                "<head>\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p><a href=\"/xxgk/ztzl/shuanggong/shenji/download/石景山区审计局政处罚权力清单.zip\">下载附件</a></p>\n" +
                "</body>\n" +
                "</html>";*/

       /* String content ="<html dir=\"ltr\">\n" +
                "<head>\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p><a href=\"/webbuilder/sites/www_bjsjs_gov_cn/tzgg/images/pic38n6es27.jpg\">图片</a></p>\n" +
                "</body>\n" +
                "</html>";*/

        /*String content="<html dir=\"ltr\">\n" +
                "<head>\n" +
                "<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<p>2019年春节，石景山区政府决定在公安分局南侧拆迁空地设立烟花销售及燃放点，该区域属于古城街道辖区，是石景山区内唯一指定燃放点。</p>\n" +
                "<p style=\"text-align: center\"><img border=\"0\" hspace=\"0\" alt=\"\" src=\"/webbuilder/sites/www_bjsjs_gov_cn/bxsh/jiedao/images/pica6b21640.jpg\" /></p>\n" +
                "<p>古城街道领导高度重视燃放点的安全状况，多次召开会议部署安保工作，并要求相关部门制定详细的保障方案，严格落实执行。节日期间共投入各类保障人员200余人次，每天由处级领导干部亲自带队值守一线，平安建设部安全生产检查队分上午、下午、夜间三个时段对烟花销售及燃放点进行安全检查并设专人全天候值守，安监、派出所、城管、消防、卫生等各部门通力协作，保证了节日期间烟花爆竹销售及燃放点秩序井然，未发生各类安全事故。</p>\n" +
                "<p style=\"text-align: center\"><img border=\"0\" hspace=\"0\" alt=\"\" src=\"/webbuilder/sites/www_bjsjs_gov_cn/bxsh/jiedao/images/pic5j3212kw.jpg\" /></p>\n" +
                "<p style=\"text-align: left\">同时，机关及社区干部都在社区参与节日值守和夜间巡查，全力确保辖区安全稳定。</p>\n" +
                "<p style=\"text-align: center\"><img border=\"0\" hspace=\"0\" alt=\"\" style=\"height: 446px; width: 688px\" src=\"/webbuilder/sites/www_bjsjs_gov_cn/bxsh/jiedao/images/pic7va48x44.jpg\" /></p>\n" +
                "</body>\n" +
                "</html>";
        System.out.println(FindmatchStr(content));*/

        try {
            JSONObject json = new JSONObject();
            String encrypt = DESUtil.encrypt(json.toJSONString(), Constants.keyCode);
            Map<String, String> map = new HashMap<String, String>();
            map.put("appId", Constants.appId);
            map.put("data", encrypt);
            String url = "http://" + Constants.ip + ":" + Constants.port + "/getLibsByAppId";
            String post = HttpUtil.doPost(url, map);
            System.out.println(post);
        } catch (Exception exp) {

        }
    }
}

