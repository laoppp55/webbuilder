package com.xml;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.extendAttr.IExtendAttrManager;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.webapps.leaveword.Word;
import com.bizwink.webapps.leaveword.WordException;
import com.transferBjrabData.Gwcase;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-11
 * Time: 12:54:13
 * To change this template use File | Settings | File Templates.
 */
public class FormPeer implements IFormManager {
    PoolServer cpool;

    public FormPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IFormManager getInstance() {
        return CmsServer.getInstance().getFactory().getFormManager();
    }

    //创建表语句
    public int createTable(List list) {
        int i = 0;
        String sequence = "";
        String createtable = "";
        String tablename = "";
        String aa = "";
        String strtable = "";
        Connection con = null;
        PreparedStatement pstmt = null;
        String templateid = "";
        ResultSet res = null;
        boolean tableflag = false;
        int siteid = 0;
        String columnnameform="";
        String editoldname="";
        List oldcolumnnamelist=new ArrayList();//存取columnname的内容
        List newcolumnnamelist=new ArrayList();//new ArrayList();;
        List editoldnamelist=new ArrayList();
        System.out.println(""+list.size());
        String typestr="";
        try {
            for (i = 0; i < list.size(); i++) {
                // System.out.println(" list="+list.size());
                Form form = (Form) list.get(i);
                tablename = form.getCreateformname();

                if (tablename.indexOf("[table]") == -1) {


                    //System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb");
                    aa = form.getCreateformname();
                    String defaultvalue = "  default  " + form.getInputvalue();
                    try {
                        Integer.parseInt(defaultvalue);
                    } catch (Exception es) {
                        defaultvalue = "  default  '" + form.getInputvalue() + "'";
                    }
                    if (form.getInputvalue() == "" || form.getInputvalue() == " ") {
                        defaultvalue = "";
                    }

                    System.out.println("typestr="+typestr);
                    if(typestr.indexOf(form.getColumnname())==-1)
                    {
                        createtable = createtable + form.getColumnname() + "  VARCHAR2(200)    " + defaultvalue + "  , \n";
                    }
                    if(form.getInputtype().equals("radio"))
                    {
                        typestr=typestr+"-"+form.getColumnname();
                    }
                    siteid = form.getSiteid();
                    templateid = form.getTemplateid();
                    columnnameform=form.getColumnname();
                    //  System.out.println("aa="+aa+"   tablename="+tablename);
                    //   System.out.println("columnname="+columnnameform);
                    //String vv=form.getColumnname();
                    editoldname=form.getOldname();
                    newcolumnnamelist.add(columnnameform);
                    editoldnamelist.add(editoldname);

                } else {

                    // newcolumnnamelist.add("[table]");
                    //newcolumnnamelist=new ArrayList();
                    columnnameform="";
                    if (aa != "") {
                        strtable = "";
                        con = cpool.getConnection();
                        con.setAutoCommit(false);
                        //判断有没有表

                        String istablesql = "select * from tab where  tname=upper('" + aa + "')";
                        // System.out.println("=================="+istablesql);
                        pstmt = con.prepareStatement(istablesql);
                        res = pstmt.executeQuery();
                        if (res.next()) {
                            tableflag = true;
                        }
                        else{
                            tableflag=false;
                        }
                        res.close();
                        pstmt.close();
                        System.out.println("tableflag="+tableflag+"    sql="+istablesql);
                        if (tableflag) {
                            System.out.println("数据库存在表");
                            //修改数据库的结构
                            String tablecolumnnamesql = "SELECT column_name FROM user_tab_columns  WHERE table_name=upper('" + aa + "')  and column_name!=upper('templateid') and column_name!=upper('siteid') and column_name!=upper('id')";
                            //查询数据库里面的字段名称  SELECT column_name FROM user_tab_columns  WHERE table_name=upper('tbl_test1_coosite_com_127923_2');
                            pstmt=con.prepareStatement(tablecolumnnamesql);
                            res=pstmt.executeQuery();
                            while(res.next())
                            {
                                String columnname=(String)res.getString(1);
                                oldcolumnnamelist.add(columnname) ;

                            }
                            res.close();
                            pstmt.close();
                            //  oldcolumnnamelist=new ArrayList();
                            //oldcolumnnamelist.add("[table]") ;
                            //增加字段与修改字段   alter table A rename column a to c  alter table docdsp add dspcode char(200)
                            //  System.out.println("进入修改字段程序=");
                            //在原有字段基础上修改字段
                            System.out.println("oldsize="+oldcolumnnamelist.size()+"  newsize="+newcolumnnamelist.size() );
                            if(oldcolumnnamelist.size()==newcolumnnamelist.size()){
                                //   System.out.println(" 有相同的字段 进行修改");
                                for(int k=oldcolumnnamelist.size()-1;k>=0;k--){
                                    String oldcolumnname=(String)oldcolumnnamelist.get(k);
                                    String newcolumnname=(String)newcolumnnamelist.get(k);
                                    String editcolumnoldname=(String)editoldnamelist.get(k);
                                    newcolumnnamelist.remove(k);
                                    oldcolumnnamelist.remove(k);
                                    //    System.out.println("oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                    if(!(oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase()))){

                                        System.out.println(" 旧的字段大于新的字段 进行修改 名字不同进行修改");
                                        String columnnameupdate="alter table "+aa+" rename column "+oldcolumnname+" to "+newcolumnname;
                                        System.out.println(columnnameupdate+ "      oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                        pstmt=con.prepareStatement(columnnameupdate);
                                        pstmt.executeUpdate();
                                        pstmt.close();
                                        con.commit();

                                        /* String columnnameupdate="alter table "+aa+" rename column "+oldcolumnname+" to "+newcolumnname;

                                      pstmt=con.prepareStatement(columnnameupdate);
                                      pstmt.executeUpdate();
                                      pstmt.close();
                                      con.commit();  */
                                    }
                                }
                            }
                            //在原有的基础上增加字段

                            if(oldcolumnnamelist.size()>newcolumnnamelist.size())
                            {
                                for(int old=0;old<oldcolumnnamelist.size();old++)
                                {
                                    // newcolumnnamelist.remove(news);
                                    // oldcolumnnamelist.remove(old);
                                    for(int news=0;news<newcolumnnamelist.size();news++)
                                    {
                                        String oldcolumnname="";
                                        String newcolumnname="";
                                        //  newcolumnnamelist.remove(news);
                                        //System.out.println("old="+old+"    news="+news);
                                        /*if(old==news){
                                                if(!(oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase()))){
                                                      String columnnameupdate="alter table "+aa+" rename column "+oldcolumnname+" to "+columnnameform;
                                                      // System.out.println("oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                                      pstmt=con.prepareStatement(columnnameupdate);
                                                      pstmt.executeUpdate();
                                                      pstmt.close();
                                                      con.commit();
                                              }
                                        }   */

                                        oldcolumnname=(String)oldcolumnnamelist.get(news);
                                        newcolumnname=(String)newcolumnnamelist.get(news);

                                        // System.out.println("size====="+oldcolumnnamelist.size()+"  size===="+newcolumnnamelist.size()+"oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase()+"  news="+news);
                                        //System.out.println("old="+old+"  news="+news);
                                        if(news<oldcolumnnamelist.size()-newcolumnnamelist.size())
                                        {
                                            if(!(oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase()))){
                                                oldcolumnnamelist.remove(news);
                                                newcolumnnamelist.remove(news);

                                                System.out.println(" 旧的字段大于新的字段 进行修改 名字不同进行修改");
                                                String columnnameupdate="alter table "+aa+" rename column "+oldcolumnname+" to "+newcolumnname;
                                                System.out.println(columnnameupdate+ "      oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                                pstmt=con.prepareStatement(columnnameupdate);
                                                pstmt.executeUpdate();
                                                pstmt.close();
                                                con.commit();

                                            }
                                            else if((oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase())))
                                            {

                                            }
                                        }
                                        if((old>newcolumnnamelist.size()-1)&&news==newcolumnnamelist.size()-1){
                                            // System.out.println("ccccccccccccccc");
                                            oldcolumnname=(String)oldcolumnnamelist.get(old);
                                            System.out.println(" 旧的字段大于新的字段 进行修改 删除");
                                            //删除表中的字段alter   table   tbl_test1_coosite_com_127923_2   drop   column   lasttext;
                                            String columnnameupdate="alter table "+aa+" drop column  "+oldcolumnname;
                                            System.out.println("columnnameupdate="+columnnameupdate);
                                            // System.out.println("oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                            pstmt=con.prepareStatement(columnnameupdate);
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            con.commit();
                                        }



                                    }


                                }
                            }
                            //新增加字段
                            if(oldcolumnnamelist.size()<newcolumnnamelist.size()){
                                for(int news=0;news<newcolumnnamelist.size();news++)
                                {
                                    String newcolumnname=(String)newcolumnnamelist.get(news);
                                    String editcolumnoldname=(String)editoldnamelist.get(news);
                                    String iscolumnsql="SELECT column_name FROM user_tab_columns  WHERE table_name=upper('" + aa + "')  and column_name=upper('"+newcolumnname+"')";
                                    pstmt=con.prepareStatement(iscolumnsql);
                                    //   System.out.println("iscolumnsql="+iscolumnsql);
                                    res=pstmt.executeQuery();
                                    if(res.next())
                                    {

                                    }
                                    else{
                                        if(editcolumnoldname!="")
                                        {
                                            String columnnameupdate="alter table "+aa+" rename column "+editcolumnoldname+" to "+newcolumnname;
                                            System.out.println(columnnameupdate+ "      oldcolumnname="+editcolumnoldname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                            pstmt=con.prepareStatement(columnnameupdate);
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            con.commit();
                                        }else{
                                            String columnnameupdate="alter table "+aa+" add  "+newcolumnname+"  varchar(400)";
                                            System.out.println(columnnameupdate);
                                            pstmt=con.prepareStatement(columnnameupdate);
                                            pstmt.executeUpdate();
                                            pstmt.close();
                                            con.commit();
                                        }
                                    }
                                }
                            }
                            /*     if(oldcolumnnamelist.size()<newcolumnnamelist.size())
                          {
                              System.out.println(" 增加新字段");
                              for(int old=0;old<oldcolumnnamelist.size();old++)
                              {


                                  for(int news=0;news<newcolumnnamelist.size();news++)
                                  {

                                       String oldcolumnname=(String)oldcolumnnamelist.get(old);
                                       String newcolumnname= newcolumnname=(String)newcolumnnamelist.get(old);
                                       String editcolumnoldname=(String)editoldnamelist.get(old);

                                       if(old<newcolumnnamelist.size()-newcolumnnamelist.size())
                                       {

                                          //alter table tbl_test1_coosite_com_127923_2 add last char(200)
                                           if(!(oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase()))){
                                                     oldcolumnnamelist.remove(old);
                                                     newcolumnnamelist.remove(old);
                                                     editoldnamelist.remove(old);

                                                        System.out.println(" 旧的字段大于新的字段 进行修改 名字不同进行修改");

                                                        String columnnameupdate="alter table "+aa+" rename column "+oldcolumnname+" to "+newcolumnname;
                                                        System.out.println(columnnameupdate+ "      oldcolumnname="+oldcolumnname.toUpperCase()+"   newcolumnname="+newcolumnname.toUpperCase());
                                                        pstmt=con.prepareStatement(columnnameupdate);
                                                        pstmt.executeUpdate();
                                                        pstmt.close();
                                                        con.commit();

                                            }
                                            else if((oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase())))
                                           {

                                           }
                                      }

                                      if(old<oldcolumnnamelist.size()&news<oldcolumnnamelist.size())
                                      {
                                          if((oldcolumnname.toUpperCase().equals(newcolumnname.toUpperCase())))
                                          {
                                                     oldcolumnnamelist.remove(old);
                                                     newcolumnnamelist.remove(old);
                                          }
                                          else{
                                                    newcolumnnamelist.remove(old);
                                                    System.out.println("newcolumnname="+newcolumnname);
                                                   // newcolumnname=(String)newcolumnnamelist.get(news);
                                                    String columnnameupdate="alter table "+aa+" add  "+newcolumnname+"  varchar(400)";
                                                    System.out.println(columnnameupdate);
                                                    pstmt=con.prepareStatement(columnnameupdate);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();
                                                    con.commit();

                                          }
                                      }

                                    //   System.out.println("============="+newcolumnname);
                                      if((news>oldcolumnnamelist.size()-1)&&old==oldcolumnnamelist.size()-1){
                                                    newcolumnname=(String)newcolumnnamelist.get(news);
                                                    String columnnameupdate="alter table "+aa+" add  "+newcolumnname+"  varchar(400)";
                                                    System.out.println(columnnameupdate);
                                                    pstmt=con.prepareStatement(columnnameupdate);
                                                    pstmt.executeUpdate();
                                                    pstmt.close();
                                                    con.commit();
                                            }

                                  }

                              }
                          }  */

                        }
                        //-------------------------------------
                        else {
                            if (aa != "") {
                                System.out.println("创建表");
                                strtable = "create  table   " + aa + " (  ID            NUMBER not null,  " + createtable + " siteid  NUMBER default " + siteid + " ,  templateid number default " + templateid + "  )";
                                sequence = "create sequence " + aa.substring(4) + "_ID" + "\n" +
                                        "minvalue 1\n" +
                                        "maxvalue 9999999999999999999999999\n" +
                                        "start with 1\n" +
                                        "increment by 1\n" +
                                        "cache 20";
                                typestr="";
                                pstmt = con.prepareStatement(strtable);
                                pstmt.executeUpdate();
                                pstmt.close();
                                pstmt = con.prepareStatement(sequence);
                                pstmt.executeUpdate();
                                pstmt.close();
                                con.commit();
                                createtable = "";
                            } else {
                                //System.out.println("++++++++++++++++++++++++");
                                createtable = "";
                            }
                        }

                    }
                    for(int z=0;z<oldcolumnnamelist.size();z++)
                    {
                        String kk=(String)oldcolumnnamelist.get(z);
                        // System.out.print("oldname="+kk);
                    }
                    System.out.println("   ");
                    for(int z=0;z<newcolumnnamelist.size();z++)
                    {
                        String kk=(String)newcolumnnamelist.get(z);
                        //    System.out.print("newname="+kk);
                    }
                    newcolumnnamelist=new ArrayList();
                    oldcolumnnamelist=new ArrayList();
                    editoldnamelist=new ArrayList();

                    System.out.println("================================================");
                    //newcolumnnamelist.add("[table]");
                }
            }

        } catch (Exception e) {
            System.out.println("" + e.toString());
        } finally {
            if (con != null) {
                try {
                    cpool.freeConnection(con);
                } catch (Exception e) {
                    System.out.println("" + e.toString());
                }
            }
        }
        /*
        String sequence="create sequence test_id\n" +
                "minvalue 1\n" +
                "maxvalue 9999999999999999999999999\n" +
                "start with 1\n" +
                "increment by 1\n" +
                "cache 20";
        String createtable="create table test\n" +
                    "(\n" +
                    "  ID            NUMBER not null,\n" +
                    "  JOINID        VARCHAR2(200) not null,\n" +
                    "  JOINNAME      VARCHAR2(300),\n" +
                    "  ADDRESS       VARCHAR2(600),\n" +
                    "  EMAIL         VARCHAR2(200),\n" +
                    "  PHONE         VARCHAR2(200),\n" +
                    "  COMPANY       VARCHAR2(400),\n" +
                    "  PASSWORD      VARCHAR2(300),\n" +
                    "  ANSWER        VARCHAR2(300),\n" +
                    "  QUESTION      VARCHAR2(300),\n" +
                    "  ZHIZHAONUMBER VARCHAR2(200),\n" +
                    "  FAX           VARCHAR2(100),\n" +
                    "  LIANXIPEOPLE  VARCHAR2(300),\n" +
                    "  CREATEDATE    DATE default sysdate,\n" +
                    "  BUYFLAG       VARCHAR2(100)\n" +
                    ")";

        */
        return i;
    }

    //处理前台插入的程序
    public int insertForm(List list,String tablename,int siteid,int templateid,List listform)
    {
        int k=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        String sql="insert into "+tablename+"  (";
        String temp="";
        String wenhao="";
        ResultSet res=null;
        int id=0;
        String typestr="";
        try{
            for(int i=0;i<list.size();i++)
            {
                String str=(String)list.get(i);
                Form form=(Form)listform.get(i);
                System.out.println("type="+form.getInputtype());
                if(str.indexOf("_")!=-1)
                {
                    temp=str.substring(0,str.indexOf("_"));
                    if(!form.getInputtype().equals("radio"))
                    {
                        typestr="";
                    }
                    if(typestr.indexOf(temp)==-1){
                        sql=sql+" "+temp+", ";
                        wenhao=wenhao+"  ?, ";
                    }
                    if(form.getInputtype().equals("radio"))
                    {
                        typestr=typestr+temp;
                    }
                }
                // System.out.println("str="+str);
            }
            sql=sql+"siteid,templateid ,id ) values("+wenhao+"  ? ,  ? ,  ? )";
            System.out.println("sql="+sql);
            con=cpool.getConnection();
            con.setAutoCommit(false);
            pstmt = con.prepareStatement("SELECT "+tablename.substring(4) + "_ID"+".nextval FROM dual");
            res = pstmt.executeQuery();
            if(res.next()){
                id = res.getInt(1);
            }
            res.close();
            pstmt.close();

            pstmt=con.prepareStatement(sql);
            int j=0;
            for(int i=0;i<list.size();i++)
            {

                String str=(String)list.get(i);
                Form form=(Form)listform.get(i);

                str=str.substring(str.indexOf("_")+1,str.length());

                if(!form.getInputtype().equals("radio"))
                {
                    typestr="";

                }
                // System.out.println("j="+j);
                if(typestr.indexOf(str)==-1){
                    j++;
                    pstmt.setString(j,str);
                    System.out.println("str="+str+"  j="+j);
                }
                if(form.getInputtype().equals("radio"))
                {

                    typestr=typestr+str;
                    //list.remove(i);
                }
            }
            // System.out.println("size="+list.size());
            pstmt.setInt(j+1,siteid);
            pstmt.setInt(j+2,templateid);
            pstmt.setInt(j+3,id);
            pstmt.executeUpdate();
            pstmt.close();
            con.commit();

            //SELECT fawu_user_id.nextval FROM dual


        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){
                    System.out.println(""+e.toString());
                }
            }
        }
        return k;
    }
    public List getTableFormList(String tablename,int siteid, List columnlist,int ipage,int onepagenum)
    {
        List list =null;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        List rowlist=new ArrayList();
        try{
            con=cpool.getConnection();
            /*     int count =0;
          String countsql="select count(*)from "+tablename+"  order by id desc";
           pstmt=con.prepareStatement(countsql);
           res=pstmt.executeQuery();
           if(res.next())
           {
               count=res.getInt(1);
           }
          res.close();
          pstmt.close();  */
            int num = onepagenum * (ipage - 1);

            String sql="select *from "+tablename+"  order by id desc";
            sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from "+tablename+"  b order by id desc)  A  WHERE  ROWNUM  <=   " + (num +onepagenum) + " and siteid="+siteid+"   ) WHERE  RN  >   " + num + " ";

            System.out.println("sql="+sql);
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();

            while(res.next())
            {   list =new ArrayList();
                for(int i=0;i<columnlist.size();i++)
                {

                    Form form=(Form)columnlist.get(i);
                    res.getString(form.getColumnname());
                    list.add(form.getColumnname()+"_"+res.getString(form.getColumnname()));

                    System.out.println("columnname="+form.getColumnname());
                }
                list.add("id"+"_"+res.getString("id"));
                System.out.println("listsize="+list.size());
                //list.add("[fenhang]");
                rowlist.add(list);
            }
            res.close();
            pstmt.close();

        }catch(Exception e){

        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){
                    System.out.println(""+e.toString());
                }
            }
        }
        return rowlist;
    }
    //获得总数
    public int  getTableFormCount(String tablename,int siteid)
    {
        int count=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String countsql="select count(*)from "+tablename+" where siteid="+siteid+" ";
            System.out.println("countsql="+countsql);
            pstmt=con.prepareStatement(countsql);

            res=pstmt.executeQuery();
            if(res.next())
            {
                count=res.getInt(1);
            }
            res.close();
            pstmt.close();
        }catch(Exception e)
        {
            System.out.println(""+e.toString());
        } finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){
                    System.out.println(""+e.toString());
                }
            }
        }
        return count;
    }
    //取得目录下的xml文件
    public List getFileXML(String root)
    {

        List list =new ArrayList();
        File file = new File(root);
        File[] subFile = file.listFiles();
        if (subFile != null) {
            for (int i = 0; i < subFile.length; i++) {
                if (subFile[i].isDirectory()) {
                    System.out.println("目录: " + subFile[i].getName());
                }else{
                    if(subFile[i].getName().indexOf(".xml")!=-1)
                    {
                        list.add(subFile[i].getName());
                        System.out.println("文件: " + subFile[i].getName());
                    }
                }
            }
        }

        return list;
    }
    public int updateTableForm(int id,String tablename,List listform,List updatelist)
    {
        int g=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        String typestr="";
        try{
            con=cpool.getConnection();
            String sql="update  "+tablename+"  set   ";
            for(int i=0;i<listform.size();i++)
            {
                Form form=(Form)listform.get(i);

                if(!form.getInputtype().equals("radio"))
                {
                    typestr="";
                }
                if(typestr.indexOf("radio")==-1){
                    sql=sql+"  "+form.getColumnname()+"=? , ";
                }
                if(form.getInputtype().equals("radio"))
                {
                    typestr=typestr+"_radio";
                }
            }
            sql=sql.substring(0,sql.lastIndexOf(","));
            sql=sql+"   where id="+id;
            System.out.println("sql="+sql);
            typestr="";
            pstmt=con.prepareStatement(sql);
            int j=0;
            for(int i=0;i<updatelist.size();i++)
            {

                Form form=(Form)listform.get(i);
                String formvalue=(String)updatelist.get(i);

                if(!form.getInputtype().equals("radio"))
                {
                    typestr="";

                }
                System.out.println("formvalue="+formvalue);
                if(typestr.indexOf("radio")==-1){
                    j++;
                    pstmt.setString(j,formvalue);
                    System.out.println("j="+j+"   formvalue="+formvalue);
                }
                if(form.getInputtype().equals("radio"))
                {
                    typestr=typestr+"_radio";
                    //updatelist.remove(i);
                }
            }
            g=pstmt.executeUpdate();
            pstmt.close();
            con.commit();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return g;
    }
    public int deleteTableForm(int id,String tablename)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            String sql="delete from "+tablename+"  where id="+id;
            pstmt=con.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
            con.commit();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){

                }
            }
        }
        return i;
    }
    public List getOneTableFormresult(int id,String tablename,List listform)
    {
        List list =new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql="select *from "+tablename+"  where id="+id+"  order by  id desc";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                for(int i=0;i<listform.size();i++)
                {

                    Form form=(Form)listform.get(i);
                    res.getString(form.getColumnname());
                    list.add(form.getColumnname()+"_"+res.getString(form.getColumnname()));
                }
                list.add("id"+"_"+res.getString("id"));
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                try{
                    cpool.freeConnection(con);
                }catch(Exception e){
                    System.out.println(""+e.toString());
                }
            }
        }
        return list;
    }

    //免费赠书
    private static final String INSERT_FORM = "INSERT INTO tbl_www_shwzg_com_130322_1(ID,bookname,username,telephone,postcode,address,createdate) " +
            "VALUES(?, ?, ?, ?, ?, ?, ?)";

    public boolean insertZhengshu(formfields ff) throws WordException {
        boolean errcode = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager sequenceMgr = SequencePeer.getInstance();
        int id = 0;
        if (cpool.getType().equals("oracle")) {
            id = sequenceMgr.getSequenceNum("SelfDefine");
        } else {
            id = sequenceMgr.nextID("SelfDefine");
        }
        try {
            try {
                conn = cpool.getConnection();
                conn.setAutoCommit(false);

                pstmt = conn.prepareStatement(INSERT_FORM);
                pstmt.setInt(1, id);
                pstmt.setString(2,ff.getBookname());
                pstmt.setString(3, ff.getUsername());
                pstmt.setString(4, ff.getTelephone());
                pstmt.setString(5, ff.getPostcode());
                pstmt.setString(6, ff.getAddress());
                pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                pstmt.executeUpdate();
                pstmt.close();
                conn.commit();
            }
            catch (Exception e) {
                conn.rollback();
                e.printStackTrace();
                errcode = true;
            }
            finally {
                if (conn != null) {
                    try {

                        cpool.freeConnection(conn);
                    } catch (Exception e) {
                        System.out.println("Error in closing the pooled connection " + e.toString());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return errcode;
    }

    public List getBookList(int start, int range,String sql){
        List list = new ArrayList();
        formfields book = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!sql.equals("")) {
            sql = sql.replaceAll("@", "%");
        }
        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            for(int i = 0 ; i < start ; i++){
                rs.next();
            }
            for(int i = 0; i < range && rs.next(); i++){
                book = load(rs);
                list.add(book);
            }
            rs.close();
            pstmt.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if(conn != null) {
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        return list;
    }


    private formfields load(ResultSet rs)  throws Exception{
        formfields book = new formfields();

        try{
            book.setId(Integer.valueOf(rs.getInt("id")));
            book.setBookname(rs.getString("bookname"));
            book.setUsername(rs.getString("username"));
            book.setTelephone(rs.getString("telephone"));
            book.setPostcode(rs.getString("postcode"));
            book.setAddress(rs.getString("address"));
            book.setCreatedate(rs.getTimestamp("createdate"));

        }catch(Exception e){
            e.printStackTrace();
        }

        return book;
    }

    public  void deleteBook(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_www_shwzg_com_130322_1 where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();

        }catch(Exception e){
            e.printStackTrace();
        } finally{
            if(conn != null){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

    }

    public int getBookCount(String sql){
        int count = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (!sql.equals("")) {
            sql = sql.replaceAll("@", "%");
        }

        try{
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            if( null!= conn){
                try{
                    cpool.freeConnection(conn);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    //无线电干扰申诉表
    public List getAllGanRao(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs ;
        List list = new ArrayList();
        try {
            conn  = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_ganrao order by id desc");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Form form = new Form();
                form.setId(rs.getInt("id"));
                form.setSusongdanwei(rs.getString("susongdanwei"));
                form.setLianxiren1(rs.getString("lianxiren1"));
                form.setLianxiren2(rs.getString("lianxiren2"));
                form.setPhone(rs.getString("phone"));
                form.setTaizhanweizhi(rs.getString("taizhanweizhi"));
                form.setShebeixinghao(rs.getString("shebeixinghao"));
                form.setGongzuofangshi(rs.getString("gongzuofangshi"));
                form.setGanraoleixing(rs.getString("ganraoleixing"));
                form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
                form.setGanraopinlv(rs.getString("ganraopinlv"));
                form.setGanraoquyu(rs.getString("ganraoquyu"));
                form.setGanraoshiduan(rs.getString("ganraoshiduan"));
                form.setGanraoshijian(rs.getString("ganraoshijian"));
                form.setTezhengmiaoshu(rs.getString("tezhengmiaoshu"));
                form.setShenshuyaoqiu(rs.getString("shenshuyaoqiu"));
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentGanRaoList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from tbl_ganrao order by id desc");
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentQureyGanRaoList(String sqlstr, int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getAllGanRaoNum(){
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(id) from tbl_ganrao");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_INSERT_GANRAO = "insert into tbl_ganrao(id,susongdanwei,lianxiren1,lianxiren2,phone,taizhanweizhi,shebeixinghao,gongzuofangshi,"+
            "ganraoleixing,zhizhaobianhao,ganraopinlv,ganraoquyu,ganraoshiduan,ganraoshijian,tezhengmiaoshu,shenshuyaoqiu) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public int insertGanRao(Form form){
        int code = 0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select max(id) from tbl_ganrao");
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
            id++;
            rs.close();
            pstmt.close();
            pstmt = conn.prepareStatement(GET_INSERT_GANRAO);
            pstmt.setInt(1,id);
            pstmt.setString(2,form.getSusongdanwei());
            pstmt.setString(3,form.getLianxiren1());
            pstmt.setString(4,form.getLianxiren2());
            pstmt.setString(5,form.getPhone());
            pstmt.setString(6,form.getTaizhanweizhi());
            pstmt.setString(7,form.getShebeixinghao());
            pstmt.setString(8,form.getGongzuofangshi());
            pstmt.setString(9,form.getGanraoleixing());
            pstmt.setString(10,form.getZhizhaobianhao());
            pstmt.setString(11,form.getGanraopinlv());
            pstmt.setString(12,form.getGanraoquyu());
            pstmt.setString(13,form.getGanraoshiduan());
            pstmt.setString(14,form.getGanraoshijian());
            pstmt.setString(15,form.getTezhengmiaoshu());
            pstmt.setString(16,form.getShenshuyaoqiu());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally{
                if(conn != null){
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return code;
    }

    private static String GET_BYID_GANRAO = "select * from tbl_ganrao where id=?";

    public Form getByIdganrao(int id){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Form form = new Form();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement(GET_BYID_GANRAO);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                form.setId(rs.getInt("id"));
                form.setSusongdanwei(rs.getString("susongdanwei"));
                form.setLianxiren1(rs.getString("lianxiren1"));
                form.setLianxiren2(rs.getString("lianxiren2"));
                form.setPhone(rs.getString("phone"));
                form.setTaizhanweizhi(rs.getString("taizhanweizhi"));
                form.setShebeixinghao(rs.getString("shebeixinghao"));
                form.setGongzuofangshi(rs.getString("gongzuofangshi"));
                form.setGanraoleixing(rs.getString("ganraoleixing"));
                form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
                form.setGanraopinlv(rs.getString("ganraopinlv"));
                form.setGanraoquyu(rs.getString("ganraoquyu"));
                form.setGanraoshiduan(rs.getString("ganraoshiduan"));
                form.setGanraoshijian(rs.getString("ganraoshijian"));
                form.setTezhengmiaoshu(rs.getString("tezhengmiaoshu"));
                form.setShenshuyaoqiu(rs.getString("shenshuyaoqiu"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return form;
    }

    private static String GET_UPDATE_GANRAO = "update tbl_ganrao set susongdanwei=?,lianxiren1=?,lianxiren2=?,phone=?,taizhanweizhi=?,shebeixinghao=?,"+
            "gongzuofangshi=?,ganraoleixing=?,zhizhaobianhao=?,ganraopinlv=?,ganraoquyu=?,ganraoshiduan=?,ganraoshijian=?,tezhengmiaoshu=?,shenshuyaoqiu=? where id=?";
    public void updateGanRao(Form form, int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_GANRAO);
            pstmt.setString(1,form.getSusongdanwei());
            pstmt.setString(2,form.getLianxiren1());
            pstmt.setString(3,form.getLianxiren2());
            pstmt.setString(4,form.getPhone());
            pstmt.setString(5,form.getTaizhanweizhi());
            pstmt.setString(6,form.getShebeixinghao());
            pstmt.setString(7,form.getGongzuofangshi());
            pstmt.setString(8,form.getGanraoleixing());
            pstmt.setString(9,form.getZhizhaobianhao());
            pstmt.setString(10,form.getGanraopinlv());
            pstmt.setString(11,form.getGanraoquyu());
            pstmt.setString(12,form.getGanraoshiduan());
            pstmt.setString(13,form.getGanraoshijian());
            pstmt.setString(14,form.getTezhengmiaoshu());
            pstmt.setString(15,form.getShenshuyaoqiu());
            pstmt.setInt(16,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteGanRao(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_ganrao where id = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            cpool.freeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Form loadsurvey(ResultSet rs) {
        Form form = new Form();

        try {
            form.setId(rs.getInt("id"));
            form.setSusongdanwei(rs.getString("susongdanwei"));
            form.setLianxiren1(rs.getString("lianxiren1"));
            form.setLianxiren2(rs.getString("lianxiren2"));
            form.setPhone(rs.getString("phone"));
            form.setTaizhanweizhi(rs.getString("taizhanweizhi"));
            form.setShebeixinghao(rs.getString("shebeixinghao"));
            form.setGongzuofangshi(rs.getString("gongzuofangshi"));
            form.setGanraoleixing(rs.getString("ganraoleixing"));
            form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
            form.setGanraopinlv(rs.getString("ganraopinlv"));
            form.setGanraoquyu(rs.getString("ganraoquyu"));
            form.setGanraoshiduan(rs.getString("ganraoshiduan"));
            form.setGanraoshijian(rs.getString("ganraoshijian"));
            form.setTezhengmiaoshu(rs.getString("tezhengmiaoshu"));
            form.setShenshuyaoqiu(rs.getString("shenshuyaoqiu"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return form;
    }

    //设置无线电台申报表
    public List getAllDuiJiangJi(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        List list = new ArrayList();
        ResultSet rs;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_zhuduijiangji order by djjid desc");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Form form = new Form();
                form.setDjjid(rs.getInt("djjid"));
                form.setShetaidanwei(rs.getString("shetaidanwei"));
                form.setSuoshuxitong(rs.getString("suoshuxitong"));
                form.setAddress(rs.getString("address"));
                form.setLianxiren(rs.getString("lianxiren"));
                form.setPhone(rs.getString("phone"));
                form.setChuanzhen(rs.getString("chuanzhen"));
                form.setShiyongdiqu(rs.getString("shiyongdiqu"));
                form.setDiantaishuliang(rs.getString("diantaishuliang"));
                form.setDiantaileibie(rs.getString("diantaileibie"));
                form.setShetailiyou(rs.getString("shetailiyou"));
                form.setLingdaoyijian(rs.getString("lingdaoyijian"));
                form.setGuanlijuyijian(rs.getString("guanlijuyijian"));
                form.setXitongbianhao(rs.getString("xitongbianhao"));
                form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentDuiJiangJiList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from tbl_zhuduijiangji order by djjid desc");
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey1(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentQureyDuiJiangJiList(String sqlstr, int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey1(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getAllDuiJiangJiNum(){
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(djjid) from tbl_zhuduijiangji");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_INSERT_DUIJIANGJI = "insert into tbl_zhuduijiangji(djjid,shetaidanwei,suoshuxitong,address,lianxiren,phone,chuanzhen,shiyongdiqu,"+
            "diantaishuliang,diantaileibie,shetailiyou,lingdaoyijian,guanlijuyijian,xitongbianhao,zhizhaobianhao) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public int insertDuiJiangJi(Form form){
        int code =0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement("select max(djjid) from tbl_zhuduijiangji");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
            id++;
            rs.close();
            pstmt.close();
            pstmt = conn.prepareStatement(GET_INSERT_DUIJIANGJI);
            pstmt.setInt(1,id);
            pstmt.setString(2,form.getShetaidanwei());
            pstmt.setString(3,form.getSuoshuxitong());
            pstmt.setString(4,form.getAddress());
            pstmt.setString(5,form.getLianxiren());
            pstmt.setString(6,form.getPhone());
            pstmt.setString(7,form.getChuanzhen());
            pstmt.setString(8,form.getShiyongdiqu());
            pstmt.setString(9,form.getDiantaishuliang());
            pstmt.setString(10,form.getDiantaileibie());
            pstmt.setString(11,form.getShetailiyou());
            pstmt.setString(12,form.getLingdaoyijian());
            pstmt.setString(13,form.getGuanlijuyijian());
            pstmt.setString(14,form.getXitongbianhao());
            pstmt.setString(15,form.getZhizhaobianhao());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally{
                if(conn != null){
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return id;
    }

    public Form getByIdduijiangji(int id){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Form form = new Form();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_zhuduijiangji where djjid=?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                form.setDjjid(rs.getInt("djjid"));
                form.setShetaidanwei(rs.getString("shetaidanwei"));
                form.setSuoshuxitong(rs.getString("suoshuxitong"));
                form.setAddress(rs.getString("address"));
                form.setLianxiren(rs.getString("lianxiren"));
                form.setPhone(rs.getString("phone"));
                form.setChuanzhen(rs.getString("chuanzhen"));
                form.setShiyongdiqu(rs.getString("shiyongdiqu"));
                form.setDiantaishuliang(rs.getString("diantaishuliang"));
                form.setDiantaileibie(rs.getString("diantaileibie"));
                form.setShetailiyou(rs.getString("shetailiyou"));
                form.setLingdaoyijian(rs.getString("lingdaoyijian"));
                form.setGuanlijuyijian(rs.getString("guanlijuyijian"));
                form.setXitongbianhao(rs.getString("xitongbianhao"));
                form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return form;
    }

    private static String GET_UPDATE_DUIJIANGJI = "update tbl_zhuduijiangji set shetaidanwei=?,suoshuxitong=?,address=?,lianxiren=?,phone=?,chuanzhen=?,shiyongdiqu=?,"+
            "diantaishuliang=?,diantaileibie=?,shetailiyou=?,lingdaoyijian=?,guanlijuyijian=?,xitongbianhao=?,zhizhaobianhao=? where djjid = ?";
    public void updateDuiJiangJi(Form form, int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_DUIJIANGJI);
            pstmt.setString(1,form.getShetaidanwei());
            pstmt.setString(2,form.getSuoshuxitong());
            pstmt.setString(3,form.getAddress());
            pstmt.setString(4,form.getLianxiren());
            pstmt.setString(5,form.getPhone());
            pstmt.setString(6,form.getChuanzhen());
            pstmt.setString(7,form.getShiyongdiqu());
            pstmt.setString(8,form.getDiantaishuliang());
            pstmt.setString(9,form.getDiantaileibie());
            pstmt.setString(10,form.getShetailiyou());
            pstmt.setString(11,form.getLingdaoyijian());
            pstmt.setString(12,form.getGuanlijuyijian());
            pstmt.setString(13,form.getXitongbianhao());
            pstmt.setString(14,form.getZhizhaobianhao());
            pstmt.setInt(15,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
    }

    public void deleteDuiJiangJi(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_zhuduijiangji where djjid = ?");
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            pstmt.close();
            cpool.freeConnection(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Form loadsurvey1(ResultSet rs) {
        Form form = new Form();

        try {
            form.setDjjid(rs.getInt("djjid"));
            form.setShetaidanwei(rs.getString("shetaidanwei"));
            form.setSuoshuxitong(rs.getString("suoshuxitong"));
            form.setAddress(rs.getString("address"));
            form.setLianxiren(rs.getString("lianxiren"));
            form.setPhone(rs.getString("phone"));
            form.setChuanzhen(rs.getString("chuanzhen"));
            form.setShiyongdiqu(rs.getString("shiyongdiqu"));
            form.setDiantaishuliang(rs.getString("diantaishuliang"));
            form.setDiantaileibie(rs.getString("diantaileibie"));
            form.setShetailiyou(rs.getString("shetailiyou"));
            form.setLingdaoyijian(rs.getString("lingdaoyijian"));
            form.setGuanlijuyijian(rs.getString("guanlijuyijian"));
            form.setXitongbianhao(rs.getString("xitongbianhao"));
            form.setZhizhaobianhao(rs.getString("zhizhaobianhao"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return form;
    }

    public List getAllZiliao(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_diantaiziliao order by id desc");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Form form = new Form();
                form.setId(rs.getInt("id"));
                form.setDtzlid(rs.getInt("dtzlid"));
                form.setShebeixinghao(rs.getString("shebeixinghao"));
                form.setChuchanghaoma(rs.getString("chuchanghaoma"));
                form.setBeizhu(rs.getString("beizhu"));
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentZiLiaoList(int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from tbl_diantaiziliao order by id desc");
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }

            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey2(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public List getCurrentQureyZiLiaoList(String sqlstr, int startrow, int range){
        Connection conn = null;
        List list = new ArrayList();
        sqlstr = sqlstr.replaceAll("@", "%");
        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sqlstr);
            ResultSet rs = pstmt.executeQuery();
            for (int i = 0; i < startrow; i++) {
                rs.next();
            }
            for (int i = 0; i < range && rs.next(); i++) {
                Form form = loadsurvey2(rs);
                list.add(form);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return list;
    }

    public int getAllZiLiaoNum(){
        Connection conn = null;
        int count = 0;

        try {
            conn = cpool.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select count(id) from tbl_diantaiziliao");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            if (conn != null) {
                cpool.freeConnection(conn);
            }
        }
        return count;
    }

    private static String GET_INSERT_ZILIAO ="insert into tbl_diantaiziliao(id,dtzlid,shebeixinghao,chuchanghaoma,beizhu) values(?,?,?,?,?)";
    public int insertZiliao(Form form){
        int code = 0;
        int id = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_INSERT_ZILIAO);
            pstmt.setInt(1, seqMgr.getSequenceNum("DianTaiZiLiao"));
            pstmt.setInt(2,form.getDtzlid());
            pstmt.setString(3,form.getShebeixinghao());
            pstmt.setString(4,form.getChuchanghaoma());
            pstmt.setString(5,form.getBeizhu());
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally{
                if(conn != null){
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return code;
    }

    public int insertZiliao(Form form,List list){
        int code = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ISequenceManager seqMgr = SequencePeer.getInstance();
        int id = seqMgr.getSequenceNum("DuiJiangJi");
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_INSERT_DUIJIANGJI);
            pstmt.setInt(1,id);
            pstmt.setString(2,form.getShetaidanwei());
            pstmt.setString(3,form.getSuoshuxitong());
            pstmt.setString(4,form.getAddress());
            pstmt.setString(5,form.getLianxiren());
            pstmt.setString(6,form.getPhone());
            pstmt.setString(7,form.getChuanzhen());
            pstmt.setString(8,form.getShiyongdiqu());
            pstmt.setString(9,form.getDiantaishuliang());
            pstmt.setString(10,form.getDiantaileibie());
            pstmt.setString(11,form.getShetailiyou());
            pstmt.setString(12,form.getLingdaoyijian());
            pstmt.setString(13,form.getGuanlijuyijian());
            pstmt.setString(14,form.getXitongbianhao());
            pstmt.setString(15,form.getZhizhaobianhao());
            pstmt.executeUpdate();
            pstmt.close();
            for(int i = 0;i < list.size();i++){
                Form f = (Form)list.get(i);
                pstmt = conn.prepareStatement(GET_INSERT_ZILIAO);
                pstmt.setInt(1, seqMgr.getSequenceNum("DianTaiZiLiao"));
                pstmt.setInt(2,id);
                pstmt.setString(3,f.getShebeixinghao());
                pstmt.setString(4,f.getChuchanghaoma());
                pstmt.setString(5,f.getBeizhu());
                pstmt.executeUpdate();
                pstmt.close();
            }
            conn.commit();
        } catch (SQLException e) {
            code = 1;
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }finally{
                if(conn != null){
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
        return code;
    }

    public Form getByIdziliao(int id){
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs;
        Form form = new Form();
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select * from tbl_diantaiziliao where id=?");
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while(rs.next()){
                form.setId(rs.getInt("id"));
                form.setDtzlid(rs.getInt("dtzlid"));
                form.setShebeixinghao(rs.getString("shebeixinghao"));
                form.setChuchanghaoma(rs.getString("chuchanghaoma"));
                form.setBeizhu(rs.getString("beizhu"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
        return form;
    }

    private static String GET_UPDATE_ZILIAO = "update tbl_diantaiziliao set dtzlid=?,shebeixinghao=?,chuchanghaoma=?,beizhu=? where id=?";
    public void updateZiliao(Form form, int id){
        Connection conn = null;
        PreparedStatement pstmt;
        try {
            conn = cpool.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(GET_UPDATE_ZILIAO);
            pstmt.setInt(1,form.getDtzlid());
            pstmt.setString(2,form.getShebeixinghao());
            pstmt.setString(3,form.getChuchanghaoma());
            pstmt.setString(4,form.getBeizhu());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                if (conn != null) {
                    cpool.freeConnection(conn);
                }
            }
            e.printStackTrace();
        }
    }
    public void deleteZiliao(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("delete from tbl_diantaiziliao where id=?");
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }
    }

    private Form loadsurvey2(ResultSet rs) {
        Form form = new Form();

        try {
            form.setId(rs.getInt("id"));
            form.setDtzlid(rs.getInt("dtzlid"));
            form.setShebeixinghao(rs.getString("shebeixinghao"));
            form.setChuchanghaoma(rs.getString("chuchanghaoma"));
            form.setBeizhu(rs.getString("beizhu"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return form;
    }

    public int existFilename(String filename){
        Connection conn = null;
        PreparedStatement pstmt = null;
        int count =0 ;
        try {
            conn = cpool.getConnection();
            pstmt = conn.prepareStatement("select count(*) from tbl_accessmdb where filename=?");
            pstmt.setString(1,filename);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next())  count = rs.getInt(1);
            System.out.println("count=" + count);
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            count=-1;
        }finally{
            if(conn != null){
                cpool.freeConnection(conn);
            }
        }

        return count;
    }
}
