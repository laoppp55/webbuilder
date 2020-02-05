package com.bizwink.webapps.questions;
import java.sql.*;
import java.util.*;
import java.io.PrintStream;

public class fenye{ 
    String serverName="localhost";
    String sConnStr="jdbc:oracle:thin:@localhost:1521:oemrep"; 
    String login_name="scott";
    String pwd="tiger";
    Statement stmt=null;
    Connection conn=null;
    ResultSet rs=null;
    int afint;

    public fenye()
    {
       
        try
        {
         Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException classnotfoundexception)
        {
            System.err.println(classnotfoundexception.getMessage());
        }
    }


 
 public ResultSet executequery(String sql){
  try{
   	conn = DriverManager.getConnection(sConnStr, login_name, pwd);
        stmt=conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY); 
   	rs=stmt.executeQuery(sql);
  }catch(SQLException e){
   	System.out.println("can't executeQuery");
  }
  	return rs;
 }
 
 
 public int executeupdate(String sql) throws SQLException{
   try
       {
        conn = DriverManager.getConnection(sConnStr, login_name, pwd);
  	stmt=conn.createStatement() ;
  	afint=stmt.executeUpdate(sql);
     }catch(SQLException sqlexception)
          {
            System.err.println(sqlexception.getMessage());
          }

  	return afint;
 }


public void closecon()
{
   try{
       if(rs!=null)
{
       rs.close();
}
       if(stmt!=null)
{
       stmt.close();
}
       if(conn!=null)
{
          conn.close();
}
    }catch(Exception e){}
}
}

