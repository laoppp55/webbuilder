package com.bizwink.joincompany;

import com.bizwink.cms.server.PoolServer;
import com.bizwink.cms.server.CmsServer;
import com.bizwink.cms.util.ISequenceManager;
import com.bizwink.cms.util.SequencePeer;
import com.bizwink.cms.register.Register;
import com.bizwink.cms.sitesetting.SiteInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2009-5-8
 * Time: 9:58:42
 * To change this template use File | Settings | File Templates.
 */
public class JoincompanyPeer implements IJoincompanyManager{
    PoolServer cpool;

    public JoincompanyPeer(PoolServer cpool) {
        this.cpool = cpool;
    }

    public static IJoincompanyManager getInstance() {
        return CmsServer.getInstance().getFactory().getJoincompanyManager();
    }
    public int insertJoincompany(Joincompany join,int ID)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{



            con=cpool.getConnection();

            con.setAutoCommit(false);

            String sql="insert into joincompany(id,joinid,joinname,address,email,phone,password,answer,question,zhizhaonumber,fax,lianxipeople,buyflag)values(?,?,?,?,?,?,?,?,?,?,?,?,?)";




            pstmt=con.prepareStatement(sql);
            pstmt.setInt(1,ID);





            pstmt.setString(2,join.getJoinid());
            pstmt.setString(3,join.getJoinname());
            pstmt.setString(4,join.getAddress());
            pstmt.setString(5,join.getEmail());
            pstmt.setString(6,join.getPhone());
            pstmt.setString(7,join.getPassword());
            pstmt.setString(8,join.getAnswer());
            pstmt.setString(9,join.getQuestion());
            pstmt.setString(10,join.getZhizhaonumber());
            pstmt.setString(11,join.getFax());
            pstmt.setString(12,join.getLianxipeople());
            pstmt.setString(13,join.getBuyflag());
            i=pstmt.executeUpdate();
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
    public int updateJoin(Joincompany join)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            String sql="update joincompany set joinname=?,address=?,email=?,phone=?,password=?,answer=?,question=?,zhizhaonumber=?,fax=?,lianxipeople=? where id="+join.getId();
            con=cpool.getConnection();
            con.setAutoCommit(false);
            pstmt=con.prepareStatement(sql);
            pstmt.setString(1,join.getJoinname());
            pstmt.setString(2,join.getAddress());
            pstmt.setString(3,join.getEmail());
            pstmt.setString(4,join.getPhone());
            pstmt.setString(5,join.getPassword());
            pstmt.setString(6,join.getAnswer());
            pstmt.setString(7,join.getQuestion());
            pstmt.setString(8,join.getZhizhaonumber());
            pstmt.setString(9,join.getFax());
            pstmt.setString(10,join.getLianxipeople());
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
    public int updatepass(Joincompany join,String password)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            con.setAutoCommit(false);
            String sql="update joincompany set password='"+join.getPassword()+"' where joinid='"+join.getJoinid()+"' and password='"+password+"'";
            pstmt=con.prepareStatement(sql);
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();

        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
    public Joincompany getJoin(String name,String pass)
    {
        Joincompany join=null;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            String sql="select *from  joincompany  where joinid='"+name+"' and password='"+pass+"'";
            pstmt=con.prepareStatement(sql);
            ResultSet res=pstmt.executeQuery();
            if(res.next())
            {
                join=new Joincompany();
                join.setJoinid(res.getString("joinid"));
                join.setEmail(res.getString("email"));
                join.setId(res.getInt("id"));
                join.setJoinname(res.getString("joinname"));
                join.setAddress(res.getString("address"));
                join.setLianxipeople(res.getString("Lianxipeople"));
                join.setPhone(res.getString("Phone"));
                join.setFax(res.getString("Fax"));
                join.setEmail(res.getString("Email"));
                join.setZhizhaonumber(res.getString("Zhizhaonumber"));
                join.setPassword(res.getString("Password"));
                join.setQuestion(res.getString("Question"));
                join.setAnswer(res.getString("Answer"));

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

                }
            }
        }
        return join;
    }
    public List getPageJoin(int ipage)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        int num = 20 * (ipage - 1);
        try{
            con=cpool.getConnection();
            // String sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from tbl_article  b where   columnid=550   order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 20) + "  ) WHERE  RN  >   " + num + " ";
            String sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from joincompany  b    order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 20) + "  ) WHERE  RN  >   " + num + " ";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                Joincompany join=new Joincompany();
                join.setJoinid(res.getString("joinid"));
                join.setEmail(res.getString("email"));
                join.setId(res.getInt("id"));
                join.setJoinname(res.getString("joinname"));
                join.setAddress(res.getString("address"));
                join.setLianxipeople(res.getString("Lianxipeople"));
                join.setPhone(res.getString("Phone"));
                join.setFax(res.getString("Fax"));
                join.setEmail(res.getString("Email"));
                join.setZhizhaonumber(res.getString("Zhizhaonumber"));
                join.setPassword(res.getString("Password"));
                join.setQuestion(res.getString("Question"));
                join.setAnswer(res.getString("Answer"));
                list.add(join);
            }


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
        return list;
    }
    public int getCountJoin()
    {
        int count =0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;

        try{
            con=cpool.getConnection();
            String sql="select count(*) from joincompany";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                count= res.getInt(1);
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

                }
            }
        }
        return count;
    }
    public List searchJoin(String name,int ipage)
    {
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        List list=new ArrayList();
        int num = 20 * (ipage - 1);
        try{
            con=cpool.getConnection();
            String sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from joincompany  b where   joinname like'%"+name+"%'   order by id desc)  A  WHERE  ROWNUM  <=   " + (num + 20) + "  ) WHERE  RN  >   " + num + " ";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                Joincompany join=new Joincompany();
                join.setJoinid(res.getString("joinid"));
                join.setEmail(res.getString("email"));
                join.setId(res.getInt("id"));
                join.setJoinname(res.getString("joinname"));
                join.setAddress(res.getString("address"));
                join.setLianxipeople(res.getString("Lianxipeople"));
                join.setPhone(res.getString("Phone"));
                join.setFax(res.getString("Fax"));
                join.setEmail(res.getString("Email"));
                join.setZhizhaonumber(res.getString("Zhizhaonumber"));
                join.setPassword(res.getString("Password"));
                join.setQuestion(res.getString("Question"));
                join.setAnswer(res.getString("Answer"));
                list.add(join);
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

                }
            }
        }
        return list;
    }
    public int getSearchCountJoin(String name)
    {
        int count =0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;

        try{
            con=cpool.getConnection();
            String sql="select count(*) from joincompany where joinname like '%"+name+"%'";
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                count= res.getInt(1);
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

                }
            }
        }
        return count;
    }
    public List getCMSMembersPage(int ipage,int id,int dflag)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        int num = 20 * (ipage - 1);
        try{
            String sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from tbl_members  b where   joincompanyid ="+id+" and dflag=0   order by userid desc)  A  WHERE  ROWNUM  <=   " + (num + 20) + "  ) WHERE  RN  >   " + num + " ";
            con=cpool.getConnection();
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                Register register=new Register();
                register.setJoincompanyid(res.getInt("Joincompanyid"));
                register.setJoinid(res.getString("joinid"));
                register.setUserID(res.getString("userid"));
                register.setEmail(res.getString("email"));
                register.setSiteID(res.getInt("siteid"));
                list.add(register);
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return list;
    }
    public int  getCMSMembersCount(int ipage ,int id,int dflag)
    {
        int count=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;

        try{
            con=cpool.getConnection();
            String sql="select count(*) as zong from tbl_members  where   joincompanyid ="+id+" and dflag=0 ";

            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                count=res.getInt("zong");
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return count;
    }
    public List getSiteInfo(int siteid)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        try{
            con=cpool.getConnection();
            String sql=" select * from tbl_siteinfo where siteid="+siteid;
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                SiteInfo site=new SiteInfo();
                site.setSiteid(res.getInt("siteid"));
                site.setDomainName(res.getString("sitename"));
                site.setCreatedate(res.getString("createdate"));
                list.add(site);
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return list;
    }
    public int updateMembers(String userid)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;

        try{
            String sql="update tbl_members set dflag=1 where userid='"+userid+"'";
            con=cpool.getConnection();
            con.setAutoCommit(false);
            pstmt=con.prepareStatement(sql);

            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
    //后台显示所有加盟商用户
    public List getCMSMembersPage(int ipage,int id)
    {
        List list=new ArrayList();
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;
        int num = 20 * (ipage - 1);
        try{
            String sql="SELECT   *   FROM  (SELECT  A. * , ROWNUM RN FROM   (select b.* from tbl_members  b where   joincompanyid ="+id+"    order by userid desc)  A  WHERE  ROWNUM  <=   " + (num + 20) + "  ) WHERE  RN  >   " + num + " ";
            con=cpool.getConnection();
            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            while(res.next())
            {
                Register register=new Register();
                register.setJoincompanyid(res.getInt("Joincompanyid"));
                register.setJoinid(res.getString("joinid"));
                register.setUserID(res.getString("userid"));
                register.setEmail(res.getString("email"));
                register.setSiteID(res.getInt("siteid"));
                register.setDflag(res.getInt("dflag")); //判断加盟商是否删除用户
                list.add(register);
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return list;
    }
    public int  getCMSMembersCount(int ipage ,int id)
    {
        int count=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        ResultSet res=null;

        try{
            con=cpool.getConnection();
            String sql="select count(*) as zong from tbl_members  where   joincompanyid ="+id+"  ";

            pstmt=con.prepareStatement(sql);
            res=pstmt.executeQuery();
            if(res.next())
            {
                count=res.getInt("zong");
            }
            res.close();
            pstmt.close();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return count;
    }
    //后台删除此站点
    public int deleteJoincompany(int id)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            String sql="delete from joincompany where id="+id;
            con.setAutoCommit(false);
            pstmt=con.prepareStatement(sql);
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
    //后台给加盟商重置密码
    public int updateJoincompanyPass(String password,int id)
    {
        int i=0;
        Connection con=null;
        PreparedStatement pstmt=null;
        try{
            con=cpool.getConnection();
            con.setAutoCommit(false);
            String sql="update joincompany set password='"+password+"' where id="+id;
            pstmt=con.prepareStatement(sql);
            i=pstmt.executeUpdate();
            pstmt.close();
            con.commit();
        }catch(Exception e){
            System.out.println(""+e.toString());
        }finally{
            if(con!=null)
            {
                cpool.freeConnection(con);
            }
        }
        return i;
    }
}
