package com.bizwink.cms.toolkit.bbs;
import java.sql.*;
public class jdbc {

  public jdbc() {
  }
 Connection conn = null ;

  public java.sql.Connection getConn(){
    Connection conn = null;
    try {
      //for sqlserver
      Class.forName ("weblogic.jdbc.mssqlserver4.Driver");
      conn = DriverManager.getConnection("jdbc:weblogic:mssqlserver4:localhost:1433","webmaster","webmaster");
    } catch (Exception e2) {
      e2.printStackTrace();
    }
    return conn;
  }

  public String ex_chinese(String str){
     if(str==null){
     str  ="" ;
     }
     else{
         try {
        str = new String(str.getBytes("iso-8859-1"),"gb2312") ;
         }
         catch (Exception ex) {
         }
     }
     return str ;
  }

 public String getTime() {
    String datestr =  "" ;
    try {
    java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-M-d HH:ss") ;
    java.util.Date date = new java.util.Date() ;
    datestr = df.format(new java.util.Date()) ;
    }
    catch (Exception ex) {
    }
    return datestr ;
  }
}